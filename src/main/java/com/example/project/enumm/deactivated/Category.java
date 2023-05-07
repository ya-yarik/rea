package com.example.project.enumm.deactivated;

public enum Category {
    Provider_1("Поставщик 1"),
    Provider_2("Поставщик 2"),
    Provider_3("Поставщик 3"),
    Provider_4("Поставщик 4");

    private final String displayValue;

    Category (String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}