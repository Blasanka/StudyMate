package com.mad.studymate.db;

import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDbHelper extends SQLiteOpenHelper {

    protected static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE IF NOT EXISTS " + StudyMateContractor.UserEntry.TABLE_NAME + " (" +
                    StudyMateContractor.UserEntry._ID + " INTEGER PRIMARY KEY," +
                    StudyMateContractor.UserEntry.COLUMN_USERNAME + " TEXT," +
                    StudyMateContractor.UserEntry.COLUMN_EMAIL + " TEXT," +
                    StudyMateContractor.UserEntry.COLUMN_PASS + " TEXT," +
                    StudyMateContractor.UserEntry.COLUMN_IS_ACTIVE + " INTEGER," +
                    StudyMateContractor.UserEntry.COLUMN_ALL_SCORES + " REAL )";

    protected static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + StudyMateContractor.UserEntry.TABLE_NAME;

    public UserDbHelper(Context context) {
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

    public long getNotesCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, StudyMateContractor.UserEntry.TABLE_NAME);
        db.close();
        return count;
    }
}
