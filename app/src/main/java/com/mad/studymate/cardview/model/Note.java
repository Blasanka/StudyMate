package com.mad.studymate.cardview.model;

public class Note {
    private String noteTitle;
    private String noteTag;
    private int paragraphCount;
    private String paragraphOne;
    private String paragraphTwo;

    public Note(String noteTitle, String noteTag, int paragraphCount) {
        this.noteTitle = noteTitle;
        this.noteTag = noteTag;
        this.paragraphCount = paragraphCount;
    }

    public Note(String noteTitle, String noteTag, int paragraphCount, String paragraphOne, String paragraphTwo) {
        this.noteTitle = noteTitle;
        this.noteTag = noteTag;
        this.paragraphCount = paragraphCount;
        this.paragraphOne = paragraphOne;
        this.paragraphTwo = paragraphTwo;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteTag() {
        return noteTag;
    }

    public void setNoteTag(String noteTag) {
        this.noteTag = noteTag;
    }

    public int getParagraphCount() {
        return paragraphCount;
    }

    public void setParagraphCount(int paragraphCount) {
        this.paragraphCount = paragraphCount;
    }

    public String getParagraphOne() {
        return paragraphOne;
    }

    public void setParagraphOne(String paragraphOne) {
        this.paragraphOne = paragraphOne;
    }

    public String getParagraphTwo() {
        return paragraphTwo;
    }
}
