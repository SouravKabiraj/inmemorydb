package com.database.inmemsql;


import com.database.inmemsql.exceptions.DBError;

import java.util.*;

public class InMemMySql implements Database {
    Map<String, Table> tableMap;

    InMemMySql() {
        tableMap = new HashMap<>();
    }

    @Override
    public synchronized void createTable(String tableName, List<Column> columns) throws DBError {
        if (tableMap.containsKey(tableName)) throw new DBError();
        tableMap.put(tableName, new Table(tableName, columns));
    }

    @Override
    public synchronized void removeTable(String tableName) throws DBError {
        if (!tableMap.containsKey(tableName)) throw new DBError();
        tableMap.remove(tableName);
    }

    @Override
    public String insert(String tableName, Row row) throws DBError {
        Table table = tableMap.get(tableName);
        Row insertedRow = table.insert(row);
        return insertedRow.id;
    }

    @Override
    public void delete(String tableName, String rowId) {
        Table table = tableMap.get(tableName);
        table.rows.removeIf(r -> r.id.equals(rowId));
    }

    @Override
    public List<Row> select(String tableName) {
        Table table = tableMap.get(tableName);
        return table.rows;
    }

    public Set<Row> select(String tableName, List<DBFilter> filetrs) {
        Set<Row> result = (Set<Row>) tableMap.get(tableName).rows;

        for ()
    }

    @Override
    public Set<Row> select(String tableName, DBFilter filetr) {
        // filter1 && filter2 || filter3
        Table table = tableMap.get(tableName);
        Set<Row> result = new HashSet<>();
        for (Row r : table.rows) {
            for (Element ele : r.elements) {
                if (columnId.equals(ele.columnId) && value.equals(ele.value)) {
                    result.add(r);
                    continue;
                }
            }
        }
        return result;
    }
}
