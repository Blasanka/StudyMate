package com.mad.studymate.db;

import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TaskDbHelper extends SQLiteOpenHelper {

    protected static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE IF NOT EXISTS " + StudyMateContractor.TaskEntry.TABLE_NAME + " (" +
                    StudyMateContractor.TaskEntry._ID + " INTEGER PRIMARY KEY," +
                    StudyMateContractor.TaskEntry.COLUMN_NAME_TITLE + " TEXT," +
                    StudyMateContractor.TaskEntry.COLUMN_NAME_TIME_PERIOD + " TEXT," +
                    StudyMateContractor.TaskEntry.COLUMN_NAME_PRIORITY_NO + " INTEGER," +
                    StudyMateContractor.TaskEntry.COLUMN_NAME_DESCRIPTION + " TEXT," +
                    StudyMateContractor.TaskEntry.COLUMN_NAME_IS_DONE + " INTEGER DEFAULT 0)";

    protected static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + StudyMateContractor.TaskEntry.TABLE_NAME;

    public TaskDbHelper(Context context) {
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

    public long getTasksCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, StudyMateContractor.TaskEntry.TABLE_NAME);
        db.close();
        return count;
    }
}
