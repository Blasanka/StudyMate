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


public class UpdateSessionActivity extends AppCompatActivity {
    ActionBar actionBar;
    Button button;
    EditText name, description, from, to, complete;
    String nametxt, descriptiontxt, fromtxt, totxt;
    Integer completetxt;

    //db helper
    SessionDbHelper mDbHelper;

    //to update the row in database
    String oldTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_session);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Update Study Sessions");

        mDbHelper = new SessionDbHelper(this);

        Bundle extras = getIntent().getExtras();

        name = findViewById(R.id.uname);
        description = findViewById(R.id.udescription);
        from = findViewById(R.id.ufrom);
        to = findViewById(R.id.uto);
        complete = findViewById(R.id.uwork);
        oldTitle = extras.getString("Title");
        name.setText(oldTitle);
        description.setText(extras.getString("descript"));
        from.setText(extras.getString("from"));
        to.setText(extras.getString("to"));
        complete.setText(extras.getInt("complete") + "");

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (updateSession() != 0) {
                    Toast.makeText(getApplicationContext(), "Session updated!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Update failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public int updateSession() {
        nametxt = name.getText().toString();
        descriptiontxt = description.getText().toString();
        fromtxt = from.getText().toString();
        totxt = to.getText().toString();
        completetxt = Integer.parseInt(complete.getText().toString());

        StudySessionVaidation validate = new StudySessionVaidation(getApplicationContext());
        if (validate.validateFieldsEmpty(nametxt, descriptiontxt, fromtxt, totxt)) {
            SQLiteDatabase db = mDbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(StudyMateContractor.SessionEntry.Col_1, nametxt);
            values.put(StudyMateContractor.SessionEntry.Col_2, descriptiontxt);
            values.put(StudyMateContractor.SessionEntry.Col_3, fromtxt);
            values.put(StudyMateContractor.SessionEntry.Col_4, totxt);
            values.put(StudyMateContractor.SessionEntry.Col_5, completetxt);

            // Which row to update, based on the title
            String selection = StudyMateContractor.SessionEntry.Col_1 + " = ?";
            String[] selectionArgs = {oldTitle};

            int count = db.update(
                    StudyMateContractor.SessionEntry.TABLE_NAME,
                    values,
                    selection,
                    selectionArgs);

            return count;
        }
        return -1;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return false;
    }
}
