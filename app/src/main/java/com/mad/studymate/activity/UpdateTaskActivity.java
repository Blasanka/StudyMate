package com.mad.studymate.activity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mad.studymate.R;
import com.mad.studymate.db.StudyMateContractor;
import com.mad.studymate.db.TaskDbHelper;

public class UpdateTaskActivity extends AppCompatActivity {
    ActionBar actionBar;
    Button updateTaskBt;
    EditText updateTitleET, updatePriorityET, updateStartTimeET, updateEndTimeET, updateDescripitonET;

    String title, timePeriod, description;
    int priority;
    boolean isDone;

    //db helper
    TaskDbHelper mDbHelper;

    //to update the row in database
    String oldTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);

        mDbHelper = new TaskDbHelper(this);

        Bundle extras = getIntent().getExtras();

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Update Task");

        updateTitleET = findViewById(R.id.noteTitleUpdateET);
        updatePriorityET = findViewById(R.id.idUpdatePriorityET);
        updateStartTimeET = findViewById(R.id.startTimeUpdateET);
        updateEndTimeET = findViewById(R.id.endTimeUpdateET);
        updateDescripitonET = findViewById(R.id.descriptionUpdateET);

        //set database values to relevant EditText to update
        String timeP = extras.getString("timePeriod");
        String startTime = timeP.substring(0, timeP.indexOf(" "));
        String endTime = timeP.substring(timeP.indexOf("to ") + 3);

        oldTitle = extras.getString("taskTitle");
        updateTitleET.setText(oldTitle);
        updatePriorityET.setText(extras.getInt("priorityNo") + "");
        updateStartTimeET.setText(startTime.trim());
        updateEndTimeET.setText(endTime.trim());
        updateDescripitonET.setText(extras.getString("description"));
        isDone = extras.getBoolean("isDone");

        updateTaskBt = findViewById(R.id.updateTaskBt);

        updateTaskBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (updateTask() != 0) {
                    Toast.makeText(getApplicationContext(), "Task updated!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Update failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public int updateTask() {
        title = updateTitleET.getText().toString();
        priority = Integer.parseInt(updatePriorityET.getText().toString());
        timePeriod = updateStartTimeET.getText().toString() + " to " + updateEndTimeET.getText().toString();
        description = updateDescripitonET.getText().toString();

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(StudyMateContractor.TaskEntry.COLUMN_NAME_TITLE, title);
        values.put(StudyMateContractor.TaskEntry.COLUMN_NAME_PRIORITY_NO, priority);
        values.put(StudyMateContractor.TaskEntry.COLUMN_NAME_TIME_PERIOD, timePeriod);
        values.put(StudyMateContractor.TaskEntry.COLUMN_NAME_DESCRIPTION, description);

        // Which row to update, based on the title
        String selection = StudyMateContractor.TaskEntry.COLUMN_NAME_TITLE + " = ?";
        String[] selectionArgs = {oldTitle};

        int count = db.update(
                StudyMateContractor.TaskEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        return count;
    }
}
