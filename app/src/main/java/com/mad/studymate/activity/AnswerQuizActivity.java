package com.mad.studymate.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.mad.studymate.R;
import com.mad.studymate.fragment.HomeFragment;
import com.mad.studymate.fragment.QuizFragment;

public class AnswerQuizActivity extends AppCompatActivity {

    ActionBar actionBar;
    Button finishQuizButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_quiz);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        String quizTitle = "";
        if (extras != null) {
            quizTitle = extras.getString("title");
            actionBar.setTitle(quizTitle);
        }


        finishQuizButton = findViewById(R.id.idFinishQuizButton);
        finishQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AnswerQuizActivity.this, ScoreBoardActivity.class);
                startActivity(intent);
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
}
