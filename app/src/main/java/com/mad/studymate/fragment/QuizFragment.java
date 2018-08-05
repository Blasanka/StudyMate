package com.mad.studymate.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mad.studymate.R;
import com.mad.studymate.activity.AddNoteActivity;
import com.mad.studymate.activity.AddQuizActivity;
import com.mad.studymate.activity.AnswerQuizActivity;
import com.mad.studymate.cardview.adapter.QuizCardAdapter;
import com.mad.studymate.cardview.model.Quiz;

import java.util.ArrayList;
import java.util.List;

public class QuizFragment extends Fragment {

    //add notes fab
    FloatingActionButton fab;

    private RecyclerView mRecyclerView;
    private QuizCardAdapter mAdapter;
    private List<Quiz> quizList;

    private OnFragmentInteractionListener mListener;

    public QuizFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quiz, container, false);

        //getting the recyclerview from xml
        mRecyclerView = view.findViewById(R.id.idQuizRecyclerView);
        //mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Populate the groups
        quizList = new ArrayList<>();
        quizList.add(new Quiz("Programming basics","programming", "Multiple Answer", 27, 0, 0));
        quizList.add(new Quiz("Software Engineering","SE", "Single Answer", 100, 1, 100));
        quizList.add(new Quiz("Hacking Advance","hacking", "True or False", 5, 2, 34));

        //set adapter to recyclerview
        mAdapter = new QuizCardAdapter(quizList, getActivity());
        mRecyclerView.setAdapter(mAdapter);

        //card clicked event with sending necessary data to the answering activity.
        mAdapter.setOnItemClickListener(new QuizCardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Quiz quiz = quizList.get(position);
                Intent intent = new Intent(getActivity(), AnswerQuizActivity.class);
                intent.putExtra("title", quiz.getTitle());
                startActivity(intent);
            }
        });


        //fab click listner to open add quiz activity
        fab = view.findViewById(R.id.idQuizAddFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddQuizActivity.class);
                startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        return view;
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
