package com.mad.studymate.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

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
}
