package com.database.inmemsql;

import com.database.inmemsql.exceptions.DBError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Table {
    String id;
    String name;
    List<Row> rows;
    HashMap<String, Column> columnsMap;

    Table(String name, List<Column> columns) {
        this.name = name;
        this.columnsMap = new HashMap<>();
        for (Column c : columns) {
            columnsMap.put(c.id, c);
        }
        rows = new ArrayList<>();
    }

    public Row insert(Row row) throws DBError {
        boolean validate = isValidate(row);
        System.out.println("IS VALID:" + validate);
        if (!validate) {
            throw new DBError();
        }
        rows.add(row);
        return row;
    }

    private boolean isValidate(Row row) {
        for (Element e : row.elements) {
            if (!columnsMap.containsKey(e.columnId)) {
                return false;
            }
            Column column = columnsMap.get(e.columnId);
            if (!column.isValid(e)) {
                return false;
            }
        }
        for (String colId : columnsMap.keySet()) {
            if (columnsMap.get(colId).isMandatory) {
                boolean found = false;
                for (Element e : row.elements) {
                    if (e.columnId == colId) {
                        found = true;
                    }
                }
                if (!found) return false;
            }
        }
        return true;
    }
}
