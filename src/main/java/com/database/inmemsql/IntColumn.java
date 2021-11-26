package com.database.inmemsql;

public class IntColumn extends Column {
    IntColumn(String name) {
        super(name, ElementType.INT);
    }

    @Override
    boolean isValid(Element element) {
        if (element.elementType != this.type) return false;
        int value = (Integer) element.value;
        if (value < -1024 || value > 1024)
            return false;
        return true;
    }
}
