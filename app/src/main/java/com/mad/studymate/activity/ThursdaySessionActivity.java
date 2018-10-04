package com.mad.studymate.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.mad.studymate.R;
import com.mad.studymate.cardview.adapter.SessionCardAdapter;
import com.mad.studymate.cardview.model.Session;
import com.mad.studymate.db.SessionDbHelper;
import com.mad.studymate.db.StudyMateContractor;

import java.util.ArrayList;
import java.util.List;

public class ThursdaySessionActivity extends AppCompatActivity {

    ActionBar actionBar;

    //add session fab
    FloatingActionButton fab;
    SessionDbHelper sessionDbHelper;
    private RecyclerView recyclerView;
    private SessionCardAdapter sessionCardAdapter;
    private List<Session> SessionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thursday_session);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Thursday Study Sessions");

        recyclerView = findViewById(R.id.SessionRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        SessionList = new ArrayList<>();
        SessionList.add(new Session("Science Homework", "02:00", "04:00", 54));

        Cursor cursor = readSessions();
        while (cursor.moveToNext()) {
            SessionList.add(new Session(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4), cursor.getString(5)));
        }
        cursor.close();
        sessionCardAdapter = new SessionCardAdapter(SessionList, this);
        recyclerView.setAdapter(sessionCardAdapter);

        sessionCardAdapter.setOnItemClickListener(new SessionCardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Session session = SessionList.get(position);
                Intent intent = new Intent(ThursdaySessionActivity.this, ViewSessionActivity.class);
                intent.putExtra("LessonName", session.getName());
                intent.putExtra("Desc", session.getDescription());
                intent.putExtra("From", session.getFrom());
                intent.putExtra("To", session.getTo());
                intent.putExtra("Comp", session.getComplete());

                startActivity(intent);
            }
        });

        //fab click listner to open add note activity
        fab = findViewById(R.id.idSessionAddFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThursdaySessionActivity.this, AddSessionActivity.class);
                startActivity(intent);
            }
        });
    }

    private Cursor readSessions() {

        sessionDbHelper = new SessionDbHelper(this);
        SQLiteDatabase sqLiteDatabase = sessionDbHelper.getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                StudyMateContractor.SessionEntry.Col_1,
                StudyMateContractor.SessionEntry.Col_3,
                StudyMateContractor.SessionEntry.Col_4,
                StudyMateContractor.SessionEntry.Col_5,
                StudyMateContractor.SessionEntry.Col_2,


        };

//         Filter results WHERE "title" = 'My Title'
        String selection = StudyMateContractor.SessionEntry.Col_6 + " = ?";
        String[] selectionArgs = {"5"};

        String sort = StudyMateContractor.SessionEntry._ID + " DESC";

        Cursor cursor = sqLiteDatabase.query(
                StudyMateContractor.SessionEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sort
        );
        return cursor;

    }

    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return false;
    }
}
