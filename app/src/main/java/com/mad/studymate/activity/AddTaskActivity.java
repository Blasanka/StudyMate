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

public class AddTaskActivity extends AppCompatActivity {

    ActionBar actionBar;
    Button addTaskBt;
    EditText titleET, priorityET, startTimeET, endTimeET, descriptionET;

    String title, timePeriod, description;
    int priorityNo;

    //db helper
    NoteDbHelper mDbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("New Task");

        mDbHelper = new NoteDbHelper(this);

        titleET = findViewById(R.id.noteTitleET);
        priorityET = findViewById(R.id.idPriorityET);
        startTimeET = findViewById(R.id.startTimeET);
        endTimeET = findViewById(R.id.endTimeET);
        descriptionET = findViewById(R.id.descriptionET);

        addTaskBt = findViewById(R.id.addTaskBt);

        addTaskBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (insertTask() == true) {
                    Toast.makeText(getApplicationContext(), "Task Inserted!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Insert failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean insertTask() {

        title = titleET.getText().toString();
        priorityNo = Integer.parseInt(priorityET.getText().toString());
        timePeriod = startTimeET.getText().toString();
        timePeriod += " to " + endTimeET.getText().toString();
        description = descriptionET.getText().toString();

        //database
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(StudyMateContractor.TaskEntry.COLUMN_NAME_TITLE, title);
        values.put(StudyMateContractor.TaskEntry.COLUMN_NAME_PRIORITY_NO, priorityNo);
        values.put(StudyMateContractor.TaskEntry.COLUMN_NAME_TIME_PERIOD, timePeriod);
        values.put(StudyMateContractor.TaskEntry.COLUMN_NAME_DESCRIPTION, description);

        long newRowId = db.insert(StudyMateContractor.TaskEntry.TABLE_NAME, null, values);

        return newRowId != -1;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return false;
    }

    @Override
    protected void onDestroy() {
        mDbHelper.close();
        super.onDestroy();
    }
}
