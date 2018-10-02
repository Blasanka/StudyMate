package com.mad.studymate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mad.studymate.R;
import com.mad.studymate.fragment.AttemptedQuizesFragment;
import com.mad.studymate.fragment.QuizFragment;

public class ScoreBoardActivity extends AppCompatActivity {

    ActionBar actionBar;

    TextView allScoresTv, quizScoresTv;

    Button homeButton, checkAnswersButton, relatedQuizButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);

        actionBar = getSupportActionBar();

        allScoresTv = findViewById(R.id.idAllQuizScoresTV);
        quizScoresTv = findViewById(R.id.idQuizScoresTV);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            actionBar.setTitle("Score board");
            quizScoresTv.setText(String.valueOf(extras.getDouble("scores")));
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


        checkAnswersButton = findViewById(R.id.idCheckAnswersBt);
        checkAnswersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentTransaction.replace(R.id.content_frame, new AttemptedQuizesFragment()).commit();
                actionBar.setTitle(R.string.nav_attempted);
                finish();
            }
        });

        relatedQuizButton = findViewById(R.id.idRelatedQuizBt);
        relatedQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentTransaction.replace(R.id.content_frame, new QuizFragment()).commit();
                actionBar.setTitle(R.string.nav_quizes);
                finish();
            }
        });

        homeButton = findViewById(R.id.idHomeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScoreBoardActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

    }

    //to disable os back button
    @Override
    public void onBackPressed() {
//        if (!shouldAllowBack()) {
//            doSomething();
//        } else {
//            super.onBackPressed();
//        }
    }
}
