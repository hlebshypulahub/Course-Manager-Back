package com.hs.coursemanagerback.model.enumeration;

public enum DocumentType {

    REPRESENTATION("Представление"),
    QUALIFICATION_SHEET("Квалификационный лист"),
    PROFESSIONAL_REPORT("Отчёт о профессиональной деятельности");

    private final String label;

    DocumentType(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }

    public String getLabel() {
        return label;
    }

    public String getName() {
        return this.name();
    }

}
