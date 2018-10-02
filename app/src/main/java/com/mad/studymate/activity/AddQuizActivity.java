package com.mad.studymate.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.mad.studymate.R;
import com.mad.studymate.db.QuizDbHelper;
import com.mad.studymate.db.StudyMateContractor;
import com.mad.studymate.validation.QuizValidation;

public class AddQuizActivity extends AppCompatActivity {

    ActionBar actionBar;
    Button nextButton;
    EditText titleET, tagET, noOfQestionsET;
    Spinner typeSpinner;

    String title, tag, type;
    int noOfQuestions;

    //to validate notes
    QuizValidation validator;

    //db helper
    QuizDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quiz);

        mDbHelper = new QuizDbHelper(this);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("New Quiz");

        //initialize editTexts and spinner
        titleET = findViewById(R.id.quizTitleET);
        tagET = findViewById(R.id.idQuizTagET);
        typeSpinner = findViewById(R.id.idQuizTypeSpinner);
        noOfQestionsET = findViewById(R.id.idNoOfquestionsET);


        //initialize to validate
        validator = new QuizValidation(this);

        nextButton = findViewById(R.id.idNextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (insertQuiz()) {
                    Intent intent = new Intent(AddQuizActivity.this, QnCAsActivity.class);
                    intent.putExtra("quiz", titleET.getText().toString());
                    startActivity(intent);
                } else {
                    Snackbar.make(view, "Failed to load", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return false;
    }

    public boolean insertQuiz() {

        //set editTexts values to variables for future use
        title = titleET.getText().toString();
        tag = tagET.getText().toString();
        type = typeSpinner.getSelectedItem().toString();

        //validate if edit texts are empty dont try to insert to db
        if (validator.isFieldsEmpty(title, tag, type, noOfQestionsET.getText().toString())) {
            return false;
        }

        //before trying to convert to integer ensure field is not empty otherwise have handle the err
        noOfQuestions = Integer.parseInt(noOfQestionsET.getText().toString());

        //get writable access to database
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(StudyMateContractor.QuizEntry.COLUMN_NAME_TITLE, title);
        values.put(StudyMateContractor.QuizEntry.COLUMN_NAME_TAG, tag);
        values.put(StudyMateContractor.QuizEntry.COLUMN_NAME_TYPE, type);
        values.put(StudyMateContractor.QuizEntry.COLUMN_NAME_QUESTIONS_COUNT, noOfQuestions);

        long newRowId = db.insert(StudyMateContractor.QuizEntry.TABLE_NAME, null, values);
        return newRowId != -1;
    }

    @Override
    protected void onDestroy() {
        mDbHelper.close();
        super.onDestroy();
    }
}
