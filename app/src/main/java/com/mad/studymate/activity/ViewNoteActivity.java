package com.mad.studymate.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.mad.studymate.R;

public class ViewNoteActivity extends AppCompatActivity {

    ActionBar actionBar;

    TextView viewTitleTV, viewTagTV, viewParaOneTV, vieweParaTwoTV, viewParaThreeTV, viewParaFourTV, viewParaFiveTV;

    String noteTitle = "", tag = "", paraOne = "", paraTwo, paraThree, paraFour, paraFive;
    int paraCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //viewTitleTV = findViewById(R.id.viewNoteTagId);
        viewTagTV = findViewById(R.id.viewNoteTagId);
        viewParaOneTV = findViewById(R.id.viewParagraphOneId);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            noteTitle = extras.getString("title");
            viewTagTV.setText(extras.getString("noteTag"));
            viewParaOneTV.setText(extras.getString("noteParaOne"));

            paraCount = extras.getInt("noteParaCount", 1);

            actionBar.setTitle(noteTitle);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return false;
    }
}
