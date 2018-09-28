package com.mad.studymate.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NoteDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 7;
    public static final String DATABASE_NAME = "StudyMate.db";

    protected static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE IF NOT EXISTS " + StudyMateContractor.NoteEntry.TABLE_NAME + " (" +
                    StudyMateContractor.NoteEntry._ID + " INTEGER PRIMARY KEY," +
                    StudyMateContractor.NoteEntry.COLUMN_NAME_TITLE + " TEXT," +
                    StudyMateContractor.NoteEntry.COLUMN_NAME_TAG + " TEXT," +
                    StudyMateContractor.NoteEntry.COLUMN_NAME_PARAGRAPH_COUNT + " INTEGER," +
                    StudyMateContractor.NoteEntry.COLUMN_NAME_PARAGRAPH_1 + " TEXT," +
                    StudyMateContractor.NoteEntry.COLUMN_NAME_PARAGRAPH_2 + " TEXT," +
                    StudyMateContractor.NoteEntry.COLUMN_NAME_PARAGRAPH_3 + " TEXT," +
                    StudyMateContractor.NoteEntry.COLUMN_NAME_PARAGRAPH_4 + " TEXT," +
                    StudyMateContractor.NoteEntry.COLUMN_NAME_PARAGRAPH_5 + " TEXT)";

    protected static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + StudyMateContractor.NoteEntry.TABLE_NAME;

    public NoteDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
