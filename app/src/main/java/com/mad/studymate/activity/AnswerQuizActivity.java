package com.mad.studymate.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.mad.studymate.jsons.JsonHandler;
import com.mad.studymate.R;
import com.mad.studymate.db.QuizDbHelper;
import com.mad.studymate.db.StudyMateContractor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AnswerQuizActivity extends AppCompatActivity {

    ActionBar actionBar;

    TextView idQuestionTV;
    TextView idQuestionTV2;
    RadioButton idTrueRadio, idFalseRadio, idTrueRadio2, idFalseRadio2;

    //db helper
    QuizDbHelper mDbHelper;

    List<JSONObject> quizObjects;
    String quizTitle = "";
    double scores = 0;

    Button finishQuizButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_quiz);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        mDbHelper = new QuizDbHelper(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            quizTitle = extras.getString("title");
            actionBar.setTitle(quizTitle);
        }

        //TODO: fixed size of components
        idQuestionTV = findViewById(R.id.idQuestionTV);
        idTrueRadio = findViewById(R.id.idTrueRadio);
        idFalseRadio = findViewById(R.id.idFalseRadio);

        idQuestionTV2 = findViewById(R.id.idQuestionTV2);
        idTrueRadio2 = findViewById(R.id.idTrueRadio2);
        idFalseRadio2 = findViewById(R.id.idFalseRadio2);

        //TODO: only fetching two json obj
        quizObjects = new ArrayList();
        JsonHandler json = new JsonHandler(getApplicationContext());
        try {
            if(json.readJsonFile() != null) {
                JSONObject obj = new JSONObject(json.readJsonFile());
                JSONArray qustionArray = obj.getJSONArray(quizTitle);

            for (int a = 0; a < qustionArray.length(); a++) {
                quizObjects.add((JSONObject) qustionArray.get(a));
            }

            idQuestionTV.setText(quizObjects.get(0).get("question").toString());
            idQuestionTV2.setText(quizObjects.get(1).get("question").toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        finishQuizButton = findViewById(R.id.idFinishQuizButton);
        finishQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AnswerQuizActivity.this, ScoreBoardActivity.class);
                //TODO: answers, scores not dynamic
                List<Boolean> userSelected = new ArrayList();
                userSelected.add(idTrueRadio.isChecked());
                userSelected.add(idTrueRadio2.isChecked());
                try {
                    for (int i = 0; i < quizObjects.size(); i++) {
                        boolean correctAns = quizObjects.get(i).getJSONArray("correctAnswers").getBoolean(0);
                        if (correctAns == userSelected.get(i)) {
                            scores++;
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                updateQuiz();
                intent.putExtra("scores", scores);
                startActivity(intent);
            }
        });
    }

    //TODO: if go back lastly added row to the quiz table should be deleted.
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return false;
    }

    //TODO: where is OOP
    public int updateQuiz() {

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(StudyMateContractor.QuizEntry.COLUMN_NAME_ATTEMPT_COUNT, retrieveAttemptCount() + 1);
        values.put(StudyMateContractor.QuizEntry.COLUMN_NAME_QUIZ_SCORES, scores);

        // Which row to update, based on the title
        String selection = StudyMateContractor.QuizEntry.COLUMN_NAME_TITLE + " = ?";
        String[] selectionArgs = {quizTitle};

        int count = db.update(
                StudyMateContractor.QuizEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        return count;
    }

    public int retrieveAttemptCount() {
        //get notes from database
        mDbHelper = new QuizDbHelper(this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                StudyMateContractor.QuizEntry.COLUMN_NAME_ATTEMPT_COUNT,
        };

        String selection = StudyMateContractor.QuizEntry.COLUMN_NAME_TITLE + " = ?";
        String[] selectionArgs = {quizTitle};

        String sortOrder =
                StudyMateContractor.QuizEntry._ID + " DESC";

        Cursor cursor = db.query(
                StudyMateContractor.QuizEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

        int attempts = 0;
        if (null != cursor && cursor.moveToFirst()) {
            attempts = cursor.getInt(1);
        }

        return attempts;
    }
}
