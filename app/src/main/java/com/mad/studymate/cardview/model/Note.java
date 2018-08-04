package com.mad.studymate.cardview.model;

public class Note {
    private String noteTitle;
    private String noteTag;
    private int paragraphCount;

    public Note(String noteTitle, String noteTag, int paragraphCount) {
        this.noteTitle = noteTitle;
        this.noteTag = noteTag;
        this.paragraphCount = paragraphCount;
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
}
