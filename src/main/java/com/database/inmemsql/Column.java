package com.database.inmemsql;

import java.time.LocalDateTime;

public abstract class Column {
    String id;
    String name;
    boolean isMandatory;
    ElementType type;

    Column(String name, ElementType type) {
        this.id = String.valueOf(LocalDateTime.now());
        this.type = type;
        this.name = name;
    }

     boolean isValid(Element element) {
        switch ( element.elementType) {
            case STRING:
            case INT:
        }
     }
}
