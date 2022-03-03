package com.hs.coursemanagerback.model.enumeration;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Exemption {

    /// Может сделать, что когда ставишь старт дату, то автоматом ставится дата конца как { старт дата + 1 год }?
    LESS_THAN_YEAR_WORK("Работа в соответствующей должности служащего менее года", 0, 12),
    PREGNANCY("Беременность", 12),
    CONSCRIPTION("Принят на прежнее место работы после прохождения военной службы по призыву, альтернативной службы", 12),
    TREATMENT("Нахождение на длительном (более четырех месяцев) излечении", 6, 4),
    BUSINESS_TRIP("Нахождение в заграничных командировках более четырех месяцев", 6, 4),
    STUDIES("Прохождение обучения в аспирантуре, докторантуре, подготовки в резидентуре в очной форме", 6),
    MATERNITY_LEAVE("Нахождение в отпуске по беременности и родам, в отпуске по уходу за ребенком до достижения им возраста трех лет", 12);

    private final String label;
    private final int monthsDuration;
    private final int monthsOfExemption;

    Exemption(String label, int monthsOfExemption, int monthsDuration) {
        this.label = label;
        this.monthsOfExemption = monthsOfExemption;
        this.monthsDuration = monthsDuration;
    }

    Exemption(String label, int monthsOfExemption) {
        this.label = label;
        this.monthsOfExemption = monthsOfExemption;
        this.monthsDuration = 0;
    }

    Exemption(String label) {
        this.label = label;
        this.monthsOfExemption = 0;
        this.monthsDuration = 0;
    }

    @Override
    public String toString() {
        return label;
    }

    public String getLabel() {
        return label;
    }

    public int getMonthsOfExemption() {
        return monthsOfExemption;
    }

    public int getMonthsDuration() {
        return monthsDuration;
    }

    public String getName() {
        return this.name();
    }
}
