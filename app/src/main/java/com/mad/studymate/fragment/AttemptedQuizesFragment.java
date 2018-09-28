package com.mad.studymate.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mad.studymate.R;
import com.mad.studymate.activity.AnswerQuizActivity;
import com.mad.studymate.cardview.adapter.QuizCardAdapter;
import com.mad.studymate.cardview.model.Quiz;
import com.mad.studymate.db.AttemptedQuizDbHelper;
import com.mad.studymate.db.StudyMateContractor;

import java.util.ArrayList;
import java.util.List;

public class AttemptedQuizesFragment extends Fragment {
    //database helper to get each and every quiz
    AttemptedQuizDbHelper mDbHelper;
    int attemptedCount;
    private RecyclerView mRecyclerView;
    private QuizCardAdapter mAdapter;
    private List<Quiz> quizList;
    private OnFragmentInteractionListener mListener;

    public AttemptedQuizesFragment() {
        // Required empty public constructor
    }

    public static AttemptedQuizesFragment newInstance() {
        AttemptedQuizesFragment fragment = new AttemptedQuizesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attempted_quizes, container, false);

        //getting the recyclerview from xml
        mRecyclerView = view.findViewById(R.id.idAttemtedQuizRecyclerView);
        //mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Populate the groups
        quizList = new ArrayList<>();
        quizList.add(new Quiz("Programming basics", "programming", "Multiple Answer", 27, 2, 0));

        //get notes from database
        Cursor cursor = retrieveAllQuizes();
        while (cursor.moveToNext()) {
            if (cursor.getInt(5) >= 1) {
                quizList.add(new Quiz(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4), cursor.getInt(5), cursor.getDouble(6)));
            }
        }
        cursor.close();

        //set adapter to recyclerview
        mAdapter = new QuizCardAdapter(quizList, getActivity());
        mRecyclerView.setAdapter(mAdapter);

        //card clicked event with sending necessary data to the answering activity.
        mAdapter.setOnItemClickListener(new QuizCardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Quiz quiz = quizList.get(position);
                attemptedCount = quiz.getTimesTaken();
                Intent intent = new Intent(getActivity(), AnswerQuizActivity.class);
                intent.putExtra("title", quiz.getTitle());
                intent.putExtra("quizTag", quiz.getQuizTag());
                intent.putExtra("quizType", quiz.getQuizType());
                intent.putExtra("questionCount", quiz.getQuestionCount());
                intent.putExtra("quizScores", quiz.getScoresOfQuiz());
                intent.putExtra("timesTaken", quiz.getTimesTaken());
                startActivity(intent);
            }
        });

        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private Cursor retrieveAllQuizes() {
        //get quiz from database
        mDbHelper = new AttemptedQuizDbHelper(getContext());
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                StudyMateContractor.AttemtedQuizEntry.COLUMN_NAME_TITLE,
                StudyMateContractor.AttemtedQuizEntry.COLUMN_NAME_TAG,
                StudyMateContractor.AttemtedQuizEntry.COLUMN_NAME_TYPE,
                StudyMateContractor.AttemtedQuizEntry.COLUMN_NAME_QUESTIONS_COUNT,
                StudyMateContractor.AttemtedQuizEntry.COLUMN_NAME_ATTEMPT_COUNT,
                StudyMateContractor.AttemtedQuizEntry.COLUMN_NAME_QUIZ_SCORES
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = StudyMateContractor.AttemtedQuizEntry.COLUMN_NAME_TITLE + " = ?";
        String[] selectionArgs = {attemptedCount + ""};

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                StudyMateContractor.AttemtedQuizEntry._ID + " DESC";

        Cursor cursor = db.query(
                StudyMateContractor.AttemtedQuizEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        return cursor;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
