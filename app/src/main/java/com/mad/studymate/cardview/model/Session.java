package com.mad.studymate.cardview.model;

public class Session {
    private String name;
    private String description;
    private String from;
    private String to;
    private int Complete;

    public Session(String name, String from, String to, int complete, String description) {
        this.name = name;
        this.description = description;
        this.from = from;
        this.to = to;
        this.Complete = complete;
    }

    public Session(String name, String from, String to, int complete) {
        this.name = name;
        this.from = from;
        this.to = to;
        this.Complete = complete;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(int complete) {
        this.Complete = complete;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public int getComplete() {
        return Complete;
    }


}


