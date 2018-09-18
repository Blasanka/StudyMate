package com.mad.studymate.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TaskDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "StudyMate.db";

    protected static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + StudyMateContractor.TaskEntry.TABLE_NAME + " (" +
                    StudyMateContractor.TaskEntry._ID + " INTEGER PRIMARY KEY," +
                    StudyMateContractor.TaskEntry.COLUMN_NAME_TITLE + " TEXT," +
                    StudyMateContractor.TaskEntry.COLUMN_NAME_TIME_PERIOD + " TEXT," +
                    StudyMateContractor.TaskEntry.COLUMN_NAME_PRIORITY_NO + " INTEGER," +
                    StudyMateContractor.TaskEntry.COLUMN_NAME_DESCRIPTION + " TEXT," +
                    StudyMateContractor.TaskEntry.COLUMN_NAME_IS_DONE + " INTEGER DEFAULT 0)";

    protected static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + StudyMateContractor.TaskEntry.TABLE_NAME;

    public TaskDbHelper(Context context) {
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
