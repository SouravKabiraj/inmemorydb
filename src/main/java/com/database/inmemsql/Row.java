package com.database.inmemsql;

import java.time.LocalDateTime;
import java.util.List;

public class Row {
    String id;
    List<Element> elements;

    Row(List<Element> element) {
        this.id = String.valueOf(LocalDateTime.now());
        this.elements = element;
    }
}
