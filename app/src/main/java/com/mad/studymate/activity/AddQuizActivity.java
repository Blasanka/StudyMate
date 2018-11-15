package com.mad.studymate.activity;

import android.content.Intent;
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
import com.mad.studymate.db.QuizTableController;

public class AddQuizActivity extends AppCompatActivity {

    ActionBar actionBar;
    Button nextButton;
    EditText titleET, tagET, noOfQestionsET;
    Spinner typeSpinner;

    String title, tag, type;
    String noOfQuestions;

    //to manage quiz table(insert)
    QuizTableController quizTableController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quiz);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("New Quiz");

        //initialize editTexts and spinner
        titleET = findViewById(R.id.quizTitleET);
        tagET = findViewById(R.id.idQuizTagET);
        typeSpinner = findViewById(R.id.idQuizTypeSpinner);
        noOfQestionsET = findViewById(R.id.idNoOfquestionsET);

        //to insert new quiz to db
        quizTableController = new QuizTableController(this);

        nextButton = findViewById(R.id.idNextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //set editTexts values to variables for future use
                title = titleET.getText().toString();
                tag = tagET.getText().toString();
                type = typeSpinner.getSelectedItem().toString();
                noOfQuestions = noOfQestionsET.getText().toString();

                if (quizTableController.insertQuiz(title, tag, type, noOfQuestions)) {
                    Intent intent = new Intent(AddQuizActivity.this, QnCAsActivity.class);
                    intent.putExtra("quiz", title);
                    intent.putExtra("noOfQuestions", Integer.parseInt(noOfQuestions));
                    intent.putExtra("type", type);
                    startActivity(intent);
                } else {
                    Snackbar.make(view, "Failed to load", Snackbar.LENGTH_SHORT).show();
                }
                quizTableController.close();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
