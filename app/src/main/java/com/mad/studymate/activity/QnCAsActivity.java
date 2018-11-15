package com.mad.studymate.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.mad.studymate.R;
import com.mad.studymate.db.QuizTableController;
import com.mad.studymate.jsons.JsonCrud;
import com.mad.studymate.validation.QAValidation;

public class QnCAsActivity extends AppCompatActivity {

    ActionBar actionBar;
    Button addQuizButton, deleteQuizBt;
    ImageButton addOneMoreAnsBt = null;

    String title;
    String type;
    int noOfQuestions;

    EditText questionEt1;
    RadioButton radioButtonTrue1;
    RadioButton radioButtonFalse1;

    EditText questionEt2;
    RadioButton radioButtonTrue2;
    RadioButton radioButtonFalse2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qn_cas);

        actionBar = getSupportActionBar();
        actionBar.setTitle("Add Questions and Correct Answers");

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            title = extras.getString("quiz");
            type = extras.getString("type");
            noOfQuestions = extras.getInt("noOfQuestions");
        }

        questionEt1 = findViewById(R.id.idQuestionET);
        radioButtonTrue1 = findViewById(R.id.idTrueRadio);
        radioButtonFalse1 = findViewById(R.id.idFalseRadio);

        questionEt2 = findViewById(R.id.idQuestionET2);
        radioButtonTrue2 = findViewById(R.id.idTrueRadio2);
        radioButtonFalse2 = findViewById(R.id.idFalseRadio2);


        //dynamic views constraint and set to this layout
        final LinearLayout linearLayout = findViewById(R.id.idQueslinearLayout);
        final LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //one more answer button properties
        final LinearLayout.LayoutParams oneMoreBtParam = new LinearLayout.LayoutParams(150, ViewGroup.LayoutParams.WRAP_CONTENT);

        //generate views for noOfQuestions
        for (int i = 1; i <= noOfQuestions; i++) {
            //Add TextView for Question number
            TextView questionTv = new TextView(this);
            questionTv.setId(i * 100);//100
            questionTv.setTextSize(18);
            questionTv.setTextColor(Color.rgb(3, 3, 3));
            questionTv.setText("Question " + i + ":");

            //Add EditText for question
            final EditText et = new EditText(this);
            et.setId(i * 200);//200
            et.setHintTextColor(Color.GRAY);
            et.setHint("What is the question ?");
            et.setMaxLines(5);

            //question
            TextView answerTv = new TextView(this);
            answerTv.setId(i * 300);
            answerTv.setTextSize(18);
            answerTv.setTextColor(Color.rgb(3, 3, 3));
            answerTv.setText("Answer");

            questionTv.setLayoutParams(p);
            et.setLayoutParams(p);
            answerTv.setLayoutParams(p);

            linearLayout.addView(questionTv);
            linearLayout.addView(et);
            linearLayout.addView(answerTv);

            //answer type set according to the quiz type
            switch (type) {
                case "Single Answer":
                    //Add EditText for answer
                    EditText etSingleAnswer = new EditText(this);
                    etSingleAnswer.setId(i * 400);//400
                    etSingleAnswer.setHintTextColor(Color.GRAY);
                    etSingleAnswer.setHint("What is the question ?");
                    etSingleAnswer.setMaxLines(5);
                    etSingleAnswer.setLayoutParams(p);
                    linearLayout.addView(etSingleAnswer);
                    break;
                case "Multiple Answer":
                    final EditText etMultipleAnswer = new EditText(this);
                    etMultipleAnswer.setId(i * 500);//400
                    etMultipleAnswer.setHintTextColor(Color.GRAY);
                    etMultipleAnswer.setHint("Answer 1");
                    etMultipleAnswer.setMaxLines(5);
                    etMultipleAnswer.setLayoutParams(p);
                    linearLayout.addView(etMultipleAnswer);

                    // multiple answer button
                    final int etMulti = i;
                    final int[] onClickIncrement = {1};
                    addOneMoreAnsBt = new ImageButton(this);
                    addOneMoreAnsBt.setImageResource(R.drawable.ic_add_black_24dp);
                    addOneMoreAnsBt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            EditText etMultipleAnswer = new EditText(QnCAsActivity.this);
                            etMultipleAnswer.setId(etMulti * 700 + onClickIncrement[0]++);//410
                            etMultipleAnswer.setHintTextColor(Color.GRAY);
                            etMultipleAnswer.setHint("Answer " + onClickIncrement[0]);
                            etMultipleAnswer.setMaxLines(5);
                            etMultipleAnswer.setLayoutParams(p);

                            //get the index of the clicked button
                            int position = linearLayout.indexOfChild(v);
                            linearLayout.addView(etMultipleAnswer, position);
                        }
                    });
                    addOneMoreAnsBt.setLayoutParams(oneMoreBtParam);
                    linearLayout.addView(addOneMoreAnsBt);
                    break;
                case "True or False":
                    RadioGroup rg = new RadioGroup(this);
                    rg.setId(i * 600);//600

                    RadioButton rb1 = new RadioButton(this);
                    rb1.setId(i * 610);//610
                    rb1.setText("True");

                    RadioButton rb2 = new RadioButton(this);
                    rb2.setId(i * 620);//620
                    rb2.setText("False");

                    rg.addView(rb1);
                    rg.addView(rb2);
                    rg.setLayoutParams(p);
                    linearLayout.addView(rg);
                    break;
                default:
                    //TODO: default
                    break;
            }
        }

        addQuizButton = findViewById(R.id.idAddQuizButton);
        addQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //to check if true or false answer is selected
                boolean firstRadioGroup = radioButtonTrue1.isChecked() || radioButtonFalse1.isChecked();
                boolean secondRadioGroup = radioButtonTrue2.isChecked() || radioButtonFalse2.isChecked();

                //validate before update QnAs
                QAValidation validator = new QAValidation(getApplicationContext());
                if (validator.isFieldsEmpty(questionEt1.getText().toString(),
                        questionEt2.getText().toString(),
                        firstRadioGroup, secondRadioGroup)) {
                    return;
                }

                JsonCrud jsonCrud = new JsonCrud(getApplicationContext());
                jsonCrud.insert(title, questionEt1, questionEt2,
                        radioButtonTrue1, radioButtonTrue2, radioButtonFalse1, radioButtonFalse2);
                Intent intent = new Intent(QnCAsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        deleteQuizBt = findViewById(R.id.idCloseQuizButton);
        deleteQuizBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuizTableController quizController = new QuizTableController(getApplicationContext());
                quizController.deleteQuiz(title, v);
                quizController.close();
                finish();
            }
        });
    }

    //to disable os back button
    @Override
    public void onBackPressed() {
    }
}
