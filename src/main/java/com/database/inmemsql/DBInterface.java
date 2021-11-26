package com.database.inmemsql;

import com.database.inmemsql.exceptions.DBError;

import java.util.HashMap;
import java.util.List;

public class DBInterface {
    private Database database;

    DBInterface() {
        database = new InMemMySql();
    }

    public void createTable(String tableName, List<Column> columns) throws DBError {
        database.createTable(tableName, columns);
    }

    public void removeTable(String tableName) throws DBError {
        database.removeTable(tableName);
    }

    public String insert(String tableName, Row row) throws DBError {
        return database.insert(tableName, row);
    }

    public void delete(String tableName, String rowId) {
        database.delete(tableName, rowId);
    }

    public void select(String tableName) {
        database.select(tableName);
    }
}
