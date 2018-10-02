package com.mad.studymate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.mad.jsons.JsonHandler;
import com.mad.studymate.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class QnCAsActivity extends AppCompatActivity {

    ActionBar actionBar;
    Button addQuizButton;

    String title;

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
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Add Questions and Correct Answers");

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            title = extras.getString("quiz");
        }

        questionEt1 = findViewById(R.id.questionTV);
        radioButtonTrue1 = findViewById(R.id.idTrueRadio);
        radioButtonFalse1 = findViewById(R.id.idFalseRadio);

        questionEt2 = findViewById(R.id.questionTV2);
        radioButtonTrue2 = findViewById(R.id.idTrueRadio2);
        radioButtonFalse2 = findViewById(R.id.idFalseRadio2);

        addQuizButton = findViewById(R.id.idAddQuizButton);
        addQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //TODO: fixed size of components and json values are from fixed varables
                JSONObject parent = new JSONObject();

                JSONObject question = new JSONObject();

                JSONObject question2 = new JSONObject();

                JSONArray questionsArray = new JSONArray();

                JSONArray answersArray = new JSONArray();
                JSONArray correctAnswersArray = new JSONArray();

                JSONArray answersArray2 = new JSONArray();
                JSONArray correctAnswersArray2 = new JSONArray();
                try {

                    //question 1


                    //question
                    question.put("question", questionEt1.getText().toString());

                    //All answers to one question
                    answersArray.put(radioButtonTrue1.isChecked());
                    answersArray.put(radioButtonFalse1.isChecked());
                    question.put("answers", answersArray);

                    //correct answers to one question
                    correctAnswersArray.put(true);
                    question.put("correctAnswers", correctAnswersArray);


                    //question 2

                    ////question
                    question2.put("question", questionEt2.getText().toString());

                    //All answers to one question
                    answersArray2.put(radioButtonTrue2.isChecked());
                    answersArray2.put(radioButtonFalse2.isChecked());
                    question2.put("answers", answersArray2);

                    //correct answers to one question
                    correctAnswersArray2.put(radioButtonFalse2.isChecked());
                    question2.put("correctAnswers", correctAnswersArray2);

                    //question
                    questionsArray.put(question);
                    questionsArray.put(question2);

                    //array of questions to parent node
                    parent.put(title, questionsArray);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonHandler json = new JsonHandler(getApplicationContext());
//                json.writeObject(object, "quiz1");
                json.writeJsonFile(parent.toString());

                Intent intent = new Intent(QnCAsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
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
