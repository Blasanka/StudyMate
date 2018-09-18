package com.mad.studymate.cardview.model;

public class Task {
    private String taskTitle;
    private int priorityNo;
    private String timePeriod;
    private String description;
    private boolean isDone = false;

    public Task(String taskTitle, int priorityNo, String timePeriod) {
        this.taskTitle = taskTitle;
        this.priorityNo = priorityNo;
        this.timePeriod = timePeriod;
    }

    //this constructor is tempral, just used to show idea
    public Task(String taskTitle, int priorityNo, String timePeriod, boolean isDone) {
        this(taskTitle, priorityNo, timePeriod);
        this.isDone= isDone;
    }

    public Task(String taskTitle, int priorityNo, String timePeriod, String description, boolean isDone) {
        this.taskTitle = taskTitle;
        this.priorityNo = priorityNo;
        this.timePeriod = timePeriod;
        this.description = description;
        this.isDone = isDone;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public int getPriorityNo() {
        return priorityNo;
    }

    public void setPriorityNo(int priorityNo) {
        this.priorityNo = priorityNo;
    }

    public String getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(String timePeriod) {
        this.timePeriod = timePeriod;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
