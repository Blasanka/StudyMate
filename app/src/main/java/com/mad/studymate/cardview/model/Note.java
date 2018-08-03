package com.mad.studymate.cardview.model;

public class Note {
    private String groupName;
    private int noOfQuizes;
    private int noOfMembers;

    public Note(String groupName, int noOfQuizes, int noOfMembers) {
        this.groupName = groupName;
        this.noOfQuizes = noOfQuizes;
        this.noOfMembers = noOfMembers;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getNoOfQuizes() {
        return noOfQuizes;
    }

    public void setNoOfQuizes(int noOfQuizes) {
        this.noOfQuizes = noOfQuizes;
    }

    public int getNoOfMembers() {
        return noOfMembers;
    }

    public void setNoOfMembers(int noOfMembers) {
        this.noOfMembers = noOfMembers;
    }
}
