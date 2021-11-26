package com.database.inmemsql;

import com.database.inmemsql.exceptions.DBError;

import java.util.List;
import java.util.Set;

public interface Database {
    void createTable(String tableName, List<Column> columns) throws DBError;

    void removeTable(String tableName) throws DBError;

    String insert(String tableName, Row row) throws DBError;

    void delete(String tableName, String rowId);

    List<Row> select(String tableName);

    Set<Row> select(String tableName, String columnName, String value);
}
