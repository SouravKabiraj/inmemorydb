package com.database.inmemsql;

public class Element<T> {
    String id;
    String columnId;
    T value;
    ElementType elementType;

    Element(String columnId, T value, ElementType elementType) {
        this.columnId = columnId;
        this.value = value;
        this.elementType = elementType;
    }

    String getColumnId() {
        return columnId;
    }
}
