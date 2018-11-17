package com.mad.studymate.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.mad.studymate.R;
import com.mad.studymate.jsons.JsonCrud;
import com.mad.studymate.validation.QAValidation;

import java.util.List;

public class UpdateQnCAsActivity extends AppCompatActivity {


    ActionBar actionBar;
    Button updateQuizButton;

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
        setContentView(R.layout.activity_update_qn_cas);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Update Questions and Correct Answers");

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            title = extras.getString("quiz");
        }

        questionEt1 = findViewById(R.id.idQuestionET);
        radioButtonTrue1 = findViewById(R.id.idTrueRadio);
        radioButtonFalse1 = findViewById(R.id.idFalseRadio);

        questionEt2 = findViewById(R.id.idQuestionET2);
        radioButtonTrue2 = findViewById(R.id.idTrueRadio2);
        radioButtonFalse2 = findViewById(R.id.idFalseRadio2);

        //to read json file and place in editText and update
        final JsonCrud jsonCrud = new JsonCrud(getApplicationContext());

        //TODO: hardcoded json values should fetch all question and answers to update activity
        List<String> questionsList = jsonCrud.read(title);
        questionEt1.setText(questionsList.get(0));
        questionEt2.setText(questionsList.get(1));

        updateQuizButton = findViewById(R.id.idUpdateQuizButton);
        updateQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //to check if true or false answer is selected
                boolean firstRadioGroup = radioButtonTrue1.isChecked() || radioButtonFalse1.isChecked();
                boolean secondRadioGroup = radioButtonTrue2.isChecked() || radioButtonFalse2.isChecked();

                //valudate before update QnAs
                QAValidation validator = new QAValidation(getApplicationContext());
//                if (validator.isFieldsEmpty(questionEt1.getText().toString(),
//                        questionEt2.getText().toString(),
//                        firstRadioGroup, secondRadioGroup)) {
//                    return;
//                }

//                jsonCrud.insert(title, questionEt1, questionEt2,
//                        radioButtonTrue1, radioButtonTrue2, radioButtonFalse1, radioButtonFalse2);
//                Intent intent = new Intent(UpdateQnCAsActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish();
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
}
