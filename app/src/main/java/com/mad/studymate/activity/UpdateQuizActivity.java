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
import com.mad.studymate.validation.QuizValidation;

public class UpdateQuizActivity extends AppCompatActivity {

    ActionBar actionBar;
    Button nextButton;
    EditText titleET, tagET, noOfQestionsET;
    Spinner typeSpinner;

    String title, oldTitle, tag, type;
    String noOfQuestions;

    //to manage quiz table(insert)
    QuizTableController quizTableController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_quiz);

        Bundle extras = getIntent().getExtras();

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Update Quiz");

        //initialize editTexts and spinner
        titleET = findViewById(R.id.quizTitleET);
        tagET = findViewById(R.id.idQuizTagET);
        typeSpinner = findViewById(R.id.idQuizTypeSpinner);
        noOfQestionsET = findViewById(R.id.idNoOfquestionsET);

        oldTitle = extras.getString("quizTitle");
        titleET.setText(oldTitle);
        tagET.setText(extras.getString("quizTag") + "");
        type = extras.getString("quizType");
        noOfQestionsET.setText(String.valueOf(extras.getInt("noOfQuestion")));

        switch (type) {
            case "Single Answer":
                typeSpinner.setSelection(0);
                break;
            case "Multiple Answer":
                typeSpinner.setSelection(1);
                break;
            case "True or False":
                typeSpinner.setSelection(2);
                break;

        }

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

                //validate if fields empty before upate
                QuizValidation validator = new QuizValidation(getApplicationContext());
                if (validator.isFieldsEmpty(title, tag, type, noOfQuestions)) {
                    return;
                }

                if (quizTableController.updateQuiz(oldTitle, title, tag, type, noOfQuestions) != -1) {
                    Intent intent = new Intent(UpdateQuizActivity.this, UpdateQnCAsActivity.class);
                    intent.putExtra("quiz", titleET.getText().toString());
                    startActivity(intent);
                } else {
                    Snackbar.make(view, "Failed to load", Snackbar.LENGTH_SHORT).show();
                }
                quizTableController.close();
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
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
