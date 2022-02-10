package com.hs.coursemanagerback.model.enumeration;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Category {

    NONE("Нет", ""),
    SECOND("Вторая", "Второй"),
    FIRST("Первая", "Первой"),
    HIGHEST("Высшая", "Высшей");

    private final String label;
    private final String representationLabel;

    Category(String label, String representationLabel) {
        this.label = label;
        this.representationLabel = representationLabel;
    }

    @Override
    public String toString() {
        return label;
    }

    public String getLabel() {
        return label;
    }

    public String getRepresentationLabel() {
        return representationLabel;
    }

    public String getName() {
        return this.name();
    }
}
