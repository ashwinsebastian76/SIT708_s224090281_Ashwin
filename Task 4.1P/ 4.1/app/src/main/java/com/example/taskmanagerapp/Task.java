package com.example.taskmanagerapp;

public class Task {
    private int id;
    private String name;
    private String desc;
    private String date;

    public Task(int id, String name, String desc, String date) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return desc;
    }

    public String getDueDate() {
        return date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String desc) {
        this.desc = desc;
    }

    public void setDueDate(String date) {
        this.date = date;
    }
}
