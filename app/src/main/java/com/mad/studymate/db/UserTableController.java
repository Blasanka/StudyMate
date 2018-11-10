package com.mad.studymate.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

public class UserTableController extends UserDbHelper {

    public UserTableController(Context context) {
        super(context);
    }

    public boolean insertUser(String username, String email, String password, int isActive, double allScores) {

        //get writable access to database
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(StudyMateContractor.UserEntry.COLUMN_USERNAME, username);
        values.put(StudyMateContractor.UserEntry.COLUMN_EMAIL, email);
        values.put(StudyMateContractor.UserEntry.COLUMN_PASS, password);
        values.put(StudyMateContractor.UserEntry.COLUMN_IS_ACTIVE, isActive);
        values.put(StudyMateContractor.UserEntry.COLUMN_ALL_SCORES, allScores);

        long newRowId = db.insert(StudyMateContractor.UserEntry.TABLE_NAME, null, values);
        db.close();
        close();
        return newRowId != -1;
    }

    public int retrieveLoginUser(String username, String password) {
        SQLiteDatabase db = getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                StudyMateContractor.UserEntry.COLUMN_USERNAME,
                StudyMateContractor.UserEntry.COLUMN_EMAIL,
                StudyMateContractor.UserEntry.COLUMN_PASS
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = StudyMateContractor.UserEntry.COLUMN_USERNAME + " == ? AND " + StudyMateContractor.UserEntry.COLUMN_PASS + " == ?";
        String[] selectionArgs = {username, password};

        Cursor cursor = db.query(
                StudyMateContractor.UserEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );
        return cursor.getCount();
    }
}
