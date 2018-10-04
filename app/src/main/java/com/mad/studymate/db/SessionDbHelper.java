package com.mad.studymate.db;

import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class SessionDbHelper extends SQLiteOpenHelper {


    protected static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + StudyMateContractor.SessionEntry.TABLE_NAME;

    public SessionDbHelper(Context context) {
        super(context, StudyMateContractor.DATABASE_NAME, null, StudyMateContractor.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String SQL_Create =
                "Create Table if not exists " + StudyMateContractor.SessionEntry.TABLE_NAME
                        + "( " + StudyMateContractor.SessionEntry._ID + " INTEGER PRIMARY KEY ," +
                        StudyMateContractor.SessionEntry.Col_1 + " TEXT , " +
                        StudyMateContractor.SessionEntry.Col_2 + " TEXT , " +
                        StudyMateContractor.SessionEntry.Col_3 + " TEXT , " +
                        StudyMateContractor.SessionEntry.Col_4 + " TEXT , " +
                        StudyMateContractor.SessionEntry.Col_5 + " INTEGER , " +
                        StudyMateContractor.SessionEntry.Col_6 + " TEXT)";
        sqLiteDatabase.execSQL(SQL_Create);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        onCreate(db);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public long getSessionsCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, StudyMateContractor.SessionEntry.TABLE_NAME);
        db.close();
        return count;
    }


}
