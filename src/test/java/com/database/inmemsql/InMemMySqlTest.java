package com.database.inmemsql;

import com.database.inmemsql.exceptions.DBError;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class InMemMySqlTest {
    InMemMySql inMemMySql;

    @BeforeEach
    void setUp() {
        inMemMySql = new InMemMySql();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createTableShouldCreateTableWithNoColumns() throws DBError {
        int prevSize = inMemMySql.tableMap.size();
        ArrayList<Column> columns = new ArrayList<>();
        inMemMySql.createTable("table1", columns);
        assertEquals(prevSize + 1, inMemMySql.tableMap.size());
    }

    @Test
    void removeTableShouldRemoveTable() throws DBError {
        ArrayList<Column> columns = new ArrayList<>();
        inMemMySql.createTable("table1", columns);
        int prevSize = inMemMySql.tableMap.size();
        inMemMySql.removeTable("table1");
        assertEquals(prevSize - 1, inMemMySql.tableMap.size());
    }

    @Test
    void insertShouldAddRowToSelectedTable() throws DBError {
        ArrayList<Column> columns = new ArrayList<>();
        Column nameCol = new StringColumn("name");
        nameCol.isMandatory = true;
        Column ageCol = new IntColumn("age");
        Column deptCol = new StringColumn("dept");
        deptCol.isMandatory = true;
        columns.add(nameCol);
        columns.add(ageCol);
        columns.add(deptCol);
        inMemMySql.createTable("table1", columns);


        ArrayList<Element> elementsForRow1 = new ArrayList<>();
        elementsForRow1.add(new Element<>(nameCol.id, "AG", ElementType.STRING));
        elementsForRow1.add(new Element<Integer>(ageCol.id, 10, ElementType.INT));
        elementsForRow1.add(new Element<>(deptCol.id, "IT", ElementType.STRING));

        ArrayList<Element> elementsForRow2 = new ArrayList<>();
        elementsForRow1.add(new Element<>(nameCol.id, "AXT", ElementType.STRING));
        elementsForRow1.add(new Element<Integer>(ageCol.id, 10, ElementType.INT));
        elementsForRow1.add(new Element<>(deptCol.id, "IT", ElementType.STRING));

        ArrayList<Element> elementsForRow3 = new ArrayList<>();
        elementsForRow1.add(new Element<>(nameCol.id, "AXT1", ElementType.STRING));
        elementsForRow1.add(new Element<Integer>(ageCol.id, 20, ElementType.INT));
        elementsForRow1.add(new Element<>(deptCol.id, "IT", ElementType.STRING));


        inMemMySql.insert("table1", new Row(elementsForRow1));
        inMemMySql.insert("table1", new Row(elementsForRow2));
        inMemMySql.insert("table1", new Row(elementsForRow3));


        assertEquals(1, inMemMySql.tableMap.get("table1").rows.size());
    }

    @Test
    void insertShouldReturnValidationErrorWhileMandatoryFieldMissing() throws DBError {
        ArrayList<Column> columns = new ArrayList<>();
        StringColumn strCol1 = new StringColumn("strCol1", true);
        columns.add(strCol1);
        inMemMySql.createTable("table1", columns);
        ArrayList<Element> elements = new ArrayList<>();
        elements.add(new Element<String>(strCol1.id, "rowValue1", ElementType.STRING));
        try {
            inMemMySql.insert("table1", new Row(elements));
        } catch (DBError e) {
            assertEquals(DBError.class, e.getClass());
        }
    }

    @Test
    void deleteShouldRemoveRowByTableNameAndRowId() throws DBError {
        ArrayList<Column> columns = new ArrayList<>();
        StringColumn strCol1 = new StringColumn("strCol1", true);
        columns.add(strCol1);
        inMemMySql.createTable("table1", columns);
        ArrayList<Element> elements = new ArrayList<>();
        elements.add(new Element<String>(strCol1.id, "rowValue1", ElementType.STRING));
        Row row = new Row(elements);
        inMemMySql.insert("table1", row);
        int prevRowCount = inMemMySql.tableMap.get("table1").rows.size();
        inMemMySql.delete("table1", row.id);
        assertEquals(prevRowCount - 1, inMemMySql.tableMap.get("table1").rows.size());
    }

    @Test
    void selectShouldReturnAllRows() throws DBError {
        ArrayList<Column> columns = new ArrayList<>();
        StringColumn strCol1 = new StringColumn("strCol1");
        columns.add(strCol1);
        inMemMySql.createTable("table1", columns);
        ArrayList<Element> elements1 = new ArrayList<>();
        ArrayList<Element> elements2 = new ArrayList<>();
        ArrayList<Element> elements3 = new ArrayList<>();
        elements1.add(new Element<String>(strCol1.id, "rowValue1", ElementType.STRING));
        elements2.add(new Element<String>(strCol1.id, "rowValue2", ElementType.STRING));
        elements3.add(new Element<String>(strCol1.id, "rowValue3", ElementType.STRING));
        inMemMySql.insert("table1", new Row(elements1));
        inMemMySql.insert("table1", new Row(elements2));
        inMemMySql.insert("table1", new Row(elements3));
        assertEquals(3, inMemMySql.select("table1").size());
    }


    @Test
    void selectShouldReturnAllRowsThatFulfillsCondition() throws DBError {
        ArrayList<Column> columns = new ArrayList<>();
        StringColumn strCol1 = new StringColumn("strCol1");
        columns.add(strCol1);
        inMemMySql.createTable("table1", columns);


        ArrayList<Element> elements1 = new ArrayList<>();
        ArrayList<Element> elements2 = new ArrayList<>();
        ArrayList<Element> elements3 = new ArrayList<>();
        elements1.add(new Element<String>(strCol1.id, "rowValue1", ElementType.STRING));
        elements2.add(new Element<String>(strCol1.id, "rowValue2", ElementType.STRING));
        elements3.add(new Element<String>(strCol1.id, "rowValue3", ElementType.STRING));
        inMemMySql.insert("table1", new Row(elements1));
        inMemMySql.insert("table1", new Row(elements2));
        inMemMySql.insert("table1", new Row(elements3));
        assertEquals(1, inMemMySql.select("table1", strCol1.id, "rowValue2").size());
    }
}