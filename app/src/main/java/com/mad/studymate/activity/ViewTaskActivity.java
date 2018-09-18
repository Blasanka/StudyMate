package com.mad.studymate.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.mad.studymate.R;

public class ViewTaskActivity extends AppCompatActivity {

    ActionBar actionBar;
    TextView viewTitleTV, viewPriorityTV, viewTimePeriodTV, viewDescripitonTV;

    String taskTitle = "", timePeriod, description;
    int priority;
    boolean isDone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

//        viewTitleTV = findViewById(R.id.idViewTitle);
        viewPriorityTV = findViewById(R.id.idViewPriorityNo);
        viewTimePeriodTV = findViewById(R.id.idViewTimePeriod);
        viewDescripitonTV = findViewById(R.id.idViewDescription);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            taskTitle = extras.getString("title");
            actionBar.setTitle(taskTitle);
        }

//        viewTitleTV.setText(taskTitle);
        viewPriorityTV.setText(extras.getInt("priorityNo") + "");
        viewTimePeriodTV.setText(extras.getString("timePeriod"));
        viewDescripitonTV.setText(extras.getString("description"));
        isDone = extras.getBoolean("isDone");
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return false;
    }
}
