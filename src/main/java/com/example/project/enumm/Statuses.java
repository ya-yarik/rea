package com.example.project.enumm;

public enum Statuses {
    Принят ("Принят"), Оформлен ("Оформлен"), Ожидает("Ожидает"), Получен ("Получен");
    private final String displayValue;

    public String getDisplayValue() {
        return displayValue;
    }

    Statuses(String displayValue) {
        this.displayValue = displayValue;

    }
}