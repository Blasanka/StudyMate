package com.mad.studymate.activity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mad.studymate.R;
import com.mad.studymate.db.SessionDbHelper;
import com.mad.studymate.db.StudyMateContractor;
import com.mad.studymate.validation.StudySessionVaidation;

import java.util.Calendar;

public class AddSessionActivity extends AppCompatActivity {
    ActionBar actionBar;

    SessionDbHelper sessionDbHelper;
    Button button;
    EditText name, description, from, to, complete;
    String namet, descriptiont, fromt, tot, completet, dayt;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_session);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Add Study Sessions");

    }

    public void addinfo(View view) {

        sessionDbHelper = new SessionDbHelper(this);

        SQLiteDatabase db = sessionDbHelper.getWritableDatabase();


        name = findViewById(R.id.name);
        description = findViewById(R.id.description);
        from = findViewById(R.id.from);
        to = findViewById(R.id.uto);
        complete = findViewById(R.id.work);
        calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        namet = name.getText().toString();
        descriptiont = description.getText().toString();
        fromt = from.getText().toString();
        tot = to.getText().toString();
        completet = complete.getText().toString();

        StudySessionVaidation validate = new StudySessionVaidation(getApplicationContext());
        if (validate.validateFieldsEmpty(namet, descriptiont, fromt, tot)) {

            ContentValues values = new ContentValues();
            values.put(StudyMateContractor.SessionEntry.Col_1, namet);
            values.put(StudyMateContractor.SessionEntry.Col_2, descriptiont);
            values.put(StudyMateContractor.SessionEntry.Col_3, fromt);
            values.put(StudyMateContractor.SessionEntry.Col_4, tot);
            values.put(StudyMateContractor.SessionEntry.Col_5, completet);
            values.put(StudyMateContractor.SessionEntry.Col_6, day);


            long newRowID = db.insert(StudyMateContractor.SessionEntry.TABLE_NAME, null, values);

            if (newRowID != -1) {
                Toast.makeText(getApplicationContext(), "Inserted ", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Inserted  Failed", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return false;
    }
}
