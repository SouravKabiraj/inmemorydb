package com.database.inmemsql;

public class StringColumn extends Column {
    StringColumn(String name) {
        super(name, ElementType.STRING);
    }

    public StringColumn(String name, boolean isMandatory) {
        super(name, ElementType.STRING);
        this.isMandatory = isMandatory;
    }

    @Override
    boolean isValid(Element element) {
        if (element.elementType != this.type) return false;
        String value = (String) element.value;
        if (value.length() > 20)
            return false;
        return true;
    }
}
