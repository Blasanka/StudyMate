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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link QuizFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link QuizFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuizFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //add notes fab
    FloatingActionButton fab;

    private RecyclerView mRecyclerView;
    private QuizCardAdapter mAdapter;
    private List<Quiz> quizList;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public QuizFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuizFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuizFragment newInstance(String param1, String param2) {
        QuizFragment fragment = new QuizFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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

    // TODO: Rename method, update argument and hook method into UI event
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
