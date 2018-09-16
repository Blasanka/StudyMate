package com.mad.studymate.db;

import android.provider.BaseColumns;

public class StudyMateContractor {
    private StudyMateContractor() {}

    public static class NoteEntry implements BaseColumns {
        public static final String TABLE_NAME = "notes";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_TAG = "tags";
        public static final String COLUMN_NAME_PARAGRAPH_COUNT = "paragraphs_count";
        public static final String COLUMN_NAME_PARAGRAPH_1 = "paragraph_1";
        public static final String COLUMN_NAME_PARAGRAPH_2 = "paragraph_2";
        public static final String COLUMN_NAME_PARAGRAPH_3 = "paragraph_3";
        public static final String COLUMN_NAME_PARAGRAPH_4 = "paragraph_4";
        public static final String COLUMN_NAME_PARAGRAPH_5 = "paragraph_5";
    }

    protected static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + StudyMateContractor.NoteEntry.TABLE_NAME + " (" +
                    NoteEntry._ID + " INTEGER PRIMARY KEY," +
                    NoteEntry.COLUMN_NAME_TITLE + " TEXT," +
                    NoteEntry.COLUMN_NAME_TAG + " TEXT," +
                    NoteEntry.COLUMN_NAME_PARAGRAPH_COUNT + " INTEGER," +
                    NoteEntry.COLUMN_NAME_PARAGRAPH_1 + " TEXT," +
                    NoteEntry.COLUMN_NAME_PARAGRAPH_2 + " TEXT," +
                    NoteEntry.COLUMN_NAME_PARAGRAPH_3 + " TEXT," +
                    NoteEntry.COLUMN_NAME_PARAGRAPH_4 + " TEXT," +
                    NoteEntry.COLUMN_NAME_PARAGRAPH_5 + " TEXT)";

    protected static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + StudyMateContractor.NoteEntry.TABLE_NAME;
}
