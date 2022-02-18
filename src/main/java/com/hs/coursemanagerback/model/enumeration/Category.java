package com.hs.coursemanagerback.model.enumeration;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Category {

    NONE("Нет", "", ""),
    SECOND("Вторая", "Второй", "Вторую"),
    FIRST("Первая", "Первой", "Первую"),
    HIGHEST("Высшая", "Высшей", "Высшую");

    private final String label;
    private final String representationLabel;
    private final String qualificationSheetLabel;

    Category(String label, String representationLabel, String qualificationSheetLabel) {
        this.label = label;
        this.representationLabel = representationLabel;
        this.qualificationSheetLabel = qualificationSheetLabel;
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

    public String getQualificationSheetLabel() {
        return qualificationSheetLabel;
    }
}
