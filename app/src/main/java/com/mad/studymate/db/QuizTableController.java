package com.mad.studymate.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.mad.studymate.validation.QuizValidation;

public class QuizTableController extends QuizDbHelper {

    //to validate notes
    QuizValidation validator;

    public QuizTableController(Context context) {
        super(context);
        //initialize to validate
        validator = new QuizValidation(context);
    }

    public boolean insertQuiz(String title, String tag, String type, String questionCount) {
        //validate if edit texts are empty dont try to insert to db
        if (validator.isFieldsEmpty(title, tag, type, questionCount)) {
            return false;
        }
        //before trying to convert to integer ensure field is not empty otherwise have handle the err
        int noOfQuestions = Integer.parseInt(questionCount);

        //get writable access to database
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(StudyMateContractor.QuizEntry.COLUMN_NAME_TITLE, title);
        values.put(StudyMateContractor.QuizEntry.COLUMN_NAME_TAG, tag);
        values.put(StudyMateContractor.QuizEntry.COLUMN_NAME_TYPE, type);
        values.put(StudyMateContractor.QuizEntry.COLUMN_NAME_QUESTIONS_COUNT, noOfQuestions);

        long newRowId = db.insert(StudyMateContractor.QuizEntry.TABLE_NAME, null, values);
        db.close();
        close();
        return newRowId != -1;
    }


    public Cursor retrieveAllQuizes() {
        SQLiteDatabase db = getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                StudyMateContractor.QuizEntry.COLUMN_NAME_TITLE,
                StudyMateContractor.QuizEntry.COLUMN_NAME_TAG,
                StudyMateContractor.QuizEntry.COLUMN_NAME_TYPE,
                StudyMateContractor.QuizEntry.COLUMN_NAME_QUESTIONS_COUNT,
                StudyMateContractor.QuizEntry.COLUMN_NAME_ATTEMPT_COUNT,
                StudyMateContractor.QuizEntry.COLUMN_NAME_QUIZ_SCORES
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                StudyMateContractor.QuizEntry._ID + " DESC";

        Cursor cursor = db.query(
                StudyMateContractor.QuizEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );
        return cursor;
    }


    public Cursor retrieveAllAttemptedQuizes() {
        SQLiteDatabase db = getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                StudyMateContractor.QuizEntry.COLUMN_NAME_TITLE,
                StudyMateContractor.QuizEntry.COLUMN_NAME_TAG,
                StudyMateContractor.QuizEntry.COLUMN_NAME_TYPE,
                StudyMateContractor.QuizEntry.COLUMN_NAME_QUESTIONS_COUNT,
                StudyMateContractor.QuizEntry.COLUMN_NAME_ATTEMPT_COUNT,
                StudyMateContractor.QuizEntry.COLUMN_NAME_QUIZ_SCORES
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = StudyMateContractor.QuizEntry.COLUMN_NAME_ATTEMPT_COUNT + " >= ?";
        String[] selectionArgs = {1 + ""};

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                StudyMateContractor.QuizEntry._ID + " DESC";

        Cursor cursor = db.query(
                StudyMateContractor.QuizEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );
        return cursor;
    }

    public void deleteQuiz(String quizTitle, View view) {
        SQLiteDatabase db = getReadableDatabase();
        // Define 'where' part of query.
        String selection = StudyMateContractor.QuizEntry.COLUMN_NAME_TITLE + " = ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = {quizTitle};
        // Issue SQL statement.
        int deletedRow = db.delete(StudyMateContractor.QuizEntry.TABLE_NAME, selection, selectionArgs);

        //delete from db
        if (deletedRow != 0) {
            Snackbar.make(view, "Successfully deleted!", Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(view, "failed to delete!", Snackbar.LENGTH_SHORT).show();
        }
    }

    public int updateQuiz(String oldTitle, String title, String tag, String type,
                          String noOfquestions) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(StudyMateContractor.QuizEntry.COLUMN_NAME_TITLE, title);
        values.put(StudyMateContractor.QuizEntry.COLUMN_NAME_TAG, tag);
        values.put(StudyMateContractor.QuizEntry.COLUMN_NAME_TYPE, type);
        values.put(StudyMateContractor.QuizEntry.COLUMN_NAME_QUESTIONS_COUNT, noOfquestions);

        // Which row to update, based on the title
        String selection = StudyMateContractor.QuizEntry.COLUMN_NAME_TITLE + " = ?";
        String[] selectionArgs = {oldTitle};

        int count = db.update(
                StudyMateContractor.QuizEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        return count;
    }
}
