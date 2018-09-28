package com.mad.studymate.activity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mad.studymate.R;
import com.mad.studymate.db.NoteDbHelper;
import com.mad.studymate.db.StudyMateContractor;
import com.mad.studymate.validation.NoteValidation;

public class AddNoteActivity extends AppCompatActivity {

    //Declare
    ActionBar actionBar;
    //    Spinner paraCountDropDown;
    Button addNoteBt;
    EditText titleET, tagET, paraOneET, paraTwoET, paraThreeET, paraFourET, paraFiveET;

    //to assign editText values for reuse
    String title, tag, paraOne, paraTwo, paraThree, paraFour, paraFive;
    int paraCount;

    //db helper
    NoteDbHelper mDbHelper;

    //to validate notes
    NoteValidation validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        mDbHelper = new NoteDbHelper(this);

        //initialize to validate
        validator = new NoteValidation(this);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("New Note");

        //get layout resources
        titleET = findViewById(R.id.noteTitleET);
        tagET = findViewById(R.id.noteTagET);
        paraOneET = findViewById(R.id.noteParagraphOneET);

        addNoteBt = findViewById(R.id.idNoteCreateBt);

        addNoteBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //assign editText values to variables for future use
                title = titleET.getText().toString();
                tag = tagET.getText().toString();
                paraOne = paraOneET.getText().toString();

                //validate before insert
                if (validator.validateFieldsEmpty(title, tag, paraOne)) {
                    if (insertNote() == true) {
                        Toast.makeText(getApplicationContext(), "Note Inserted!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Insert failed!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    //insert new note to the database
    public boolean insertNote() {

        getParaCount();

        //database to insert new value
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(StudyMateContractor.NoteEntry.COLUMN_NAME_TITLE, title);
        values.put(StudyMateContractor.NoteEntry.COLUMN_NAME_TAG, tag);
        values.put(StudyMateContractor.NoteEntry.COLUMN_NAME_PARAGRAPH_COUNT, paraCount);
        values.put(StudyMateContractor.NoteEntry.COLUMN_NAME_PARAGRAPH_1, paraOne);
        values.put(StudyMateContractor.NoteEntry.COLUMN_NAME_PARAGRAPH_2, paraTwo);
        values.put(StudyMateContractor.NoteEntry.COLUMN_NAME_PARAGRAPH_3, paraThree);
        values.put(StudyMateContractor.NoteEntry.COLUMN_NAME_PARAGRAPH_4, paraFour);
        values.put(StudyMateContractor.NoteEntry.COLUMN_NAME_PARAGRAPH_5, paraFive);

        long newRowId = db.insert(StudyMateContractor.NoteEntry.TABLE_NAME, null, values);

        return newRowId != -1;
    }

    //generate paragraph count based on EditTexts for paras
    private void getParaCount() {
        if (paraTwoET == null) {
            paraCount = 1;
            paraTwo = "";
        } else {
            if (paraThreeET == null) {
                paraCount = 2;
                paraThree = "";
            } else {
                if (paraFourET == null) {
                    paraCount = 3;
                    paraFour = "";
                } else {
                    if (paraFiveET == null) {
                        paraCount = 4;
                        paraFive = "";
                    } else {
                        paraCount = 5;
                        paraFive = paraFiveET.getText().toString();
                    }
                    paraCount = 4;
                    paraFour = paraFourET.getText().toString();
                }
                paraCount = 3;
                paraThree = paraThreeET.getText().toString();
            }
            paraCount = 2;
            paraTwo = paraTwoET.getText().toString();
        }
    }

    //to go back when back button pressed on action bar
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return false;
    }

    @Override
    protected void onDestroy() {
        //close the database to free memory
        mDbHelper.close();
        super.onDestroy();
    }
}
