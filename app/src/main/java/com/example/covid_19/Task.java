package com.example.covid_19;

public class Task {

    private String name;
    private String desc;
    private String date;

    public Task(String name, String desc, String date) {
        this.name = name;
        this.desc = desc;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
