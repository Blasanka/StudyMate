package com.mad.studymate.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.mad.studymate.R;
import com.mad.studymate.activity.AttemptsActivity;
import com.mad.studymate.cardview.adapter.QuizCardAdapter;
import com.mad.studymate.cardview.model.Quiz;
import com.mad.studymate.db.QuizTableController;

import java.util.ArrayList;
import java.util.List;

public class AttemptedQuizesFragment extends Fragment {

    private Menu menu;
    private SearchView mSearchView;

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

        QuizTableController quizTableController = new QuizTableController(getContext());
        //get notes from database
        Cursor cursor = quizTableController.retrieveAllAttemptedQuizes();
        while (cursor.moveToNext()) {
            quizList.add(new Quiz(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4), cursor.getInt(5), cursor.getDouble(6)));
        }
        cursor.close();
        quizTableController.close();

        //set adapter to recyclerview
        mAdapter = new QuizCardAdapter(quizList, getActivity());
        mRecyclerView.setAdapter(mAdapter);

        setHasOptionsMenu(true);

        //card clicked event with sending necessary data to the answering activity.
        mAdapter.setOnItemClickListener(new QuizCardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Quiz quiz = quizList.get(position);
                attemptedCount = quiz.getTimesTaken();
                Intent intent = new Intent(getActivity(), AttemptsActivity.class);
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        super.onCreateOptionsMenu(menu,inflater);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mAdapter.filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.filter(newText);
                return true;
            }
        });
    }
}
