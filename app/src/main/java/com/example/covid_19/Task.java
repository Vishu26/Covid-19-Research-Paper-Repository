package com.example.covid_19;

public class Task {

    private String name;
    private String desc;
    private String date;
    private String link;

    public Task(String name, String desc, String date, String link) {
        this.name = name;
        this.desc = desc;
        this.date = date;
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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
