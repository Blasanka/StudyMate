package com.mad.studymate.activity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.mad.studymate.R;
import com.mad.studymate.db.NoteDbHelper;
import com.mad.studymate.db.StudyMateContractor;

public class UpdateNoteActivity extends AppCompatActivity {
    ActionBar actionBar;
    Spinner paraCountDropDown;
    Button updateNoteBt;
    EditText updateTitleET, updateTagET, updateParaOneET, updateParaTwoET, updateParaThreeET, updateParaFourET, updateParaFiveET;

    String title, tag, paraOne, paraTwo, paraThree, paraFour, paraFive;
    int paraCount;

    //db helper
    NoteDbHelper mDbHelper;

    //to update the row in database
    String oldTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);

        mDbHelper = new NoteDbHelper(this);

        Bundle extras = getIntent().getExtras();

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Update Note");

        updateTitleET = findViewById(R.id.updateNoteTitleET);
        updateTagET = findViewById(R.id.updateNoteTagET);
        updateParaOneET = findViewById(R.id.updateNoteParagraphOneET);

        //set database values to relevant EditText to update
        oldTitle = extras.getString("noteTitle");
        updateTitleET.setText(oldTitle);
        updateTagET.setText(extras.getString("noteTag"));
        updateParaOneET.setText(extras.getString("noteParaOne"));
        paraCount = extras.getInt("noteParaCount", 1);

        updateNoteBt = findViewById(R.id.idNoteUpdateBt);

        updateNoteBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(updateNote() != 0) {
                    Toast.makeText(getApplicationContext(), "Note updated!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Update failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    public int updateNote() {
        title = updateTitleET.getText().toString();
        tag = updateTagET.getText().toString();
        paraOne = updateParaOneET.getText().toString();

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(StudyMateContractor.NoteEntry.COLUMN_NAME_TITLE, title);
        values.put(StudyMateContractor.NoteEntry.COLUMN_NAME_TAG, tag);
        values.put(StudyMateContractor.NoteEntry.COLUMN_NAME_PARAGRAPH_1, paraOne);

        // Which row to update, based on the title
        String selection = StudyMateContractor.NoteEntry.COLUMN_NAME_TITLE + " = ?";
        String[] selectionArgs = { oldTitle };

        int count = db.update(
                StudyMateContractor.NoteEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        return count;
    }
}
