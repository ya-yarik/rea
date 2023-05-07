package com.example.project.enumm;

public enum Roles {
    ROLE_USER ("Пользователь"),
    ROLE_ADMIN ("Администратор");


    private final String displayValue;

    public String getDisplayValue() {
        return displayValue;
    }

    Roles(String displayValue) {
        this.displayValue = displayValue;

    }
}
