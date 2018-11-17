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
import android.widget.Toast;

import com.mad.studymate.R;
import com.mad.studymate.cardview.model.Question;
import com.mad.studymate.db.QuizTableController;
import com.mad.studymate.jsons.JsonCrud;
import com.mad.studymate.validation.QAValidation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QnCAsActivity extends AppCompatActivity {

    ActionBar actionBar;
    Button addQuizButton, deleteQuizBt;
    ImageButton addOneMoreAnsBt = null;

    //collect all generated views to vaidate and add to json file
    List<Question> questionsList;
    Map<String, View> dynamicViewMap;

    String title;
    String type;
    int noOfQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qn_cas);

        actionBar = getSupportActionBar();
        actionBar.setTitle("Add Questions and Correct Answers");

        questionsList = new ArrayList<>();
        dynamicViewMap = new HashMap<>();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            title = extras.getString("quiz");
            type = extras.getString("type");
            noOfQuestions = extras.getInt("noOfQuestions");
        }

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
//            dynamicViewMap.put("questionTv"+i * 100, questionTv);

            //Add EditText for question
            final EditText et = new EditText(this);
            et.setId(i * 200);//200
            et.setHintTextColor(Color.GRAY);
            et.setHint("What is the question ?");
            et.setMaxLines(5);
            dynamicViewMap.put("et_question_" + i, et);
//            questionObject.setQuestion(et.getText().toString());

            //correct answers of choice
            TextView correctAnswersTv = new TextView(this);
            correctAnswersTv.setId(i * 300);
            correctAnswersTv.setTextSize(18);
            correctAnswersTv.setTextColor(Color.rgb(3, 3, 3));
            correctAnswersTv.setText("Correct Answer");
//            dynamicViewMap.put("correctAnswersTv"+i * 300, correctAnswersTv);

            questionTv.setLayoutParams(p);
            et.setLayoutParams(p);
            correctAnswersTv.setLayoutParams(p);

            linearLayout.addView(questionTv);
            linearLayout.addView(et);
            linearLayout.addView(correctAnswersTv);
//            generateCorrectAnswerViews(linearLayout, p, oneMoreBtParam, i);

            generateAnswerViews(linearLayout, p, oneMoreBtParam, i);
//            questionsList.add(questionObject);
        }

        addQuizButton = findViewById(R.id.idAddQuizButton);
        addQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Map.Entry<String, View> entry : dynamicViewMap.entrySet()) {
                    String key = entry.getKey();
                    View value = entry.getValue();

                    if (key.contains("et")) {
                        Question questionObject = new Question(((EditText) value).getText().toString());
                        questionsList.add(questionObject);
                    }
                }

                for (int i = 1; i <= questionsList.size(); i++) {
                    for (Map.Entry<String, View> entry : dynamicViewMap.entrySet()) {
                        String key = entry.getKey();
                        View value = entry.getValue();
                        if (key.contains(i + "")) {
                            if (key.contains("singleAns") || key.contains("multiAns")) {
                                questionsList.get(i - 1).setCorrectAnswer(((EditText) value).getText().toString());
                            } else if (key.contains("rbTrue") || key.contains("rbFalse")) {
                                questionsList.get(i - 1).setCorrectAnswer(((RadioButton) value).isChecked() + "");
                            }
                        }
                    }
                }
                //validate before update QnAs
                QAValidation validator = new QAValidation(getApplicationContext());
                if (validator.isFieldsEmpty(type, questionsList)) {
                    Toast.makeText(QnCAsActivity.this, "All fields must filled", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    JsonCrud jsonCrud = new JsonCrud(getApplicationContext());
                    jsonCrud.insert(title, questionsList);
                    Intent intent = new Intent(QnCAsActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
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

    private void generateAnswerViews(final LinearLayout linearLayout, final LinearLayout.LayoutParams p, LinearLayout.LayoutParams oneMoreBtParam, int i) {
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
//                questionObject.setAnswer(etSingleAnswer.getText().toString(), i);
                dynamicViewMap.put("singleAns" + i, etSingleAnswer);
                break;
            case "Multiple Answer":
                final EditText etMultipleAnswer = new EditText(this);
                etMultipleAnswer.setId(i * 500);//400
                etMultipleAnswer.setHintTextColor(Color.GRAY);
                etMultipleAnswer.setHint("Answer 1");
                etMultipleAnswer.setMaxLines(5);
                etMultipleAnswer.setLayoutParams(p);
                linearLayout.addView(etMultipleAnswer);
//                questionObject.setAnswer(etMultipleAnswer.getText().toString(), i);
                dynamicViewMap.put("multiAns" + i, etMultipleAnswer);

                // multiple answer button
                final int etMulti = i;
                final int[] onClickIncrement = {2};
                addOneMoreAnsBt = new ImageButton(this);
                addOneMoreAnsBt.setImageResource(R.drawable.ic_add_black_24dp);
                addOneMoreAnsBt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText etMultipleAnswer = new EditText(QnCAsActivity.this);
                        etMultipleAnswer.setId(etMulti * 700 + onClickIncrement[0]);//701
                        etMultipleAnswer.setHintTextColor(Color.GRAY);
                        etMultipleAnswer.setHint("Answer " + onClickIncrement[0]);
                        etMultipleAnswer.setMaxLines(5);
                        etMultipleAnswer.setLayoutParams(p);
//                        questionObject.setAnswer(etMultipleAnswer.getText().toString(), etMulti);
                        //TODO: last answer added to one above question if have mutiple answers (bug)
                        dynamicViewMap.put("multiAns" + etMulti + 1, etMultipleAnswer);
                        onClickIncrement[0]++;
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
//                questionObject.setAnswer(rb1.isChecked(), i);
                dynamicViewMap.put("rbTrue" + i, rb1);

                RadioButton rb2 = new RadioButton(this);
                rb2.setId(i * 620);//620
                rb2.setText("False");
//                questionObject.setAnswer(rb2.isChecked(), i);
                dynamicViewMap.put("rbFalse" + i, rb2);

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

    //to disable os back button
    @Override
    public void onBackPressed() {
    }
}
