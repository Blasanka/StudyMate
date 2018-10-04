package com.mad.studymate.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mad.studymate.R;

public class ViewSessionActivity extends AppCompatActivity {
    ActionBar actionBar;

    TextView name, desc, from, to, complete;
    Integer comtext;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_session_card);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("View Study Sessions");

        name = findViewById(R.id.vname);
        desc = findViewById(R.id.vdescription);
        from = findViewById(R.id.vfrom);
        to = findViewById(R.id.uto);
        complete = findViewById(R.id.vwork);
        progressBar = findViewById(R.id.progressBar);

        Bundle extras = getIntent().getExtras();
        name.setText(extras.getString("LessonName"));
        desc.setText(extras.getString("Desc"));
        from.setText(extras.getString("From"));
        to.setText(extras.getString("To"));
        complete.setText(extras.getInt("Comp") + "%");

        comtext = extras.getInt("Comp");
        progressBar.setProgress(comtext);

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return false;
    }
}
