package com.mad.studymate.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AttemptedQuizDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 7;
    public static final String DATABASE_NAME = "StudyMate.db";

    protected static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + StudyMateContractor.AttemtedQuizEntry.TABLE_NAME + " (" +
                    StudyMateContractor.AttemtedQuizEntry._ID + " INTEGER PRIMARY KEY," +
                    StudyMateContractor.AttemtedQuizEntry.COLUMN_NAME_TITLE + " TEXT," +
                    StudyMateContractor.AttemtedQuizEntry.COLUMN_NAME_TAG + " TEXT," +
                    StudyMateContractor.AttemtedQuizEntry.COLUMN_NAME_TYPE + " TEXT," +
                    StudyMateContractor.AttemtedQuizEntry.COLUMN_NAME_QUESTIONS_COUNT + " INTEGER," +
                    StudyMateContractor.AttemtedQuizEntry.COLUMN_NAME_ATTEMPT_COUNT + " INTEGER," +
                    StudyMateContractor.AttemtedQuizEntry.COLUMN_NAME_QUIZ_SCORES + " REAL)";

    protected static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + StudyMateContractor.AttemtedQuizEntry.TABLE_NAME;

    public AttemptedQuizDbHelper(Context context) {
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
