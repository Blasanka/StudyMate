package com.mad.studymate.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AttemptedQuizDbHelper extends SQLiteOpenHelper {

    protected static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE IF NOT EXISTS " + StudyMateContractor.AttemtedQuizEntry.TABLE_NAME + " (" +
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
        super(context, StudyMateContractor.DATABASE_NAME, null, StudyMateContractor.DATABASE_VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        onCreate(db);
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
