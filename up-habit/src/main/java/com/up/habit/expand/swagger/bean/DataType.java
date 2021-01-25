package com.up.habit.expand.swagger.bean;

public enum DataType {
    String("String"), Integer("Integer"), File("File");

    private String value;

    DataType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
