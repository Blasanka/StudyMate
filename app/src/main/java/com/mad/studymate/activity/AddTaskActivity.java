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
import com.mad.studymate.db.StudyMateContractor;
import com.mad.studymate.db.TaskDbHelper;
import com.mad.studymate.validation.TaskValidation;

public class AddTaskActivity extends AppCompatActivity {

    ActionBar actionBar;

    //layout views
    Button addTaskBt;
    EditText titleET, priorityET, startTimeET, endTimeET, descriptionET;

    //variables for editTexts values
    String title, timePeriod, description;
    int priorityNo;

    //db helper
    TaskDbHelper mDbHelper;

    //to validate tasks
    TaskValidation taskValidate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("New Task");

        //initialize database helper
        mDbHelper = new TaskDbHelper(this);

        //initialize validate class
        taskValidate = new TaskValidation(this);

        //get layout components
        titleET = findViewById(R.id.taskTitleET);
        priorityET = findViewById(R.id.idPriorityET);
        startTimeET = findViewById(R.id.startTimeET);
        endTimeET = findViewById(R.id.endTimeET);
        descriptionET = findViewById(R.id.descriptionET);

        addTaskBt = findViewById(R.id.addTaskBt);

        //when button clicked
        addTaskBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //assign EditTexts values to variables
                title = titleET.getText().toString();
                timePeriod = startTimeET.getText().toString();
                timePeriod += " to " + endTimeET.getText().toString();
                description = descriptionET.getText().toString();

                //validate fields before insert to db
                if (taskValidate.validateFieldsEmpty(title,
                        priorityET.getText().toString(),
                        startTimeET.getText().toString(),
                        description)) {

                    //to prevent number format exception if value not typed in edit text
                    priorityNo = Integer.parseInt(priorityET.getText().toString());

                    if(taskValidate.validatePriorityNo(priorityNo)) {
                        //insert to database
                        if (insertTask() == true) {
                            Toast.makeText(getApplicationContext(), "Task Inserted!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Insert failed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

    }

    private boolean insertTask() {

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
