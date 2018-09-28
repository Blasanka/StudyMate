package com.mad.studymate.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.mad.studymate.R;
import com.mad.studymate.activity.FridaySessionActivity;
import com.mad.studymate.activity.MainActivity;
import com.mad.studymate.activity.MondaySessionActivity;
import com.mad.studymate.activity.SaturdaySessionActivity;
import com.mad.studymate.activity.SundaySessionActivity;
import com.mad.studymate.activity.ThursdaySessionActivity;
import com.mad.studymate.activity.TuesdaySessionActivity;
import com.mad.studymate.activity.WendsdaySessionActivity;

public class StudySessionFragment extends Fragment {
    //List view for days of week
    ListView lv;
    private OnFragmentInteractionListener mListener;

    public StudySessionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_study_session, container, false);

        String[] daysOfWeek = getResources().getStringArray(R.array.study_session_list_items);

        ListView lv = view.findViewById(R.id.studySessionListVIew);

        lv.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.days_textview_listview, daysOfWeek));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
                Intent sessionActivity;
                switch (position) {
                    case 0:
                        sessionActivity = new Intent(getActivity(), MondaySessionActivity.class);
                        startActivity(sessionActivity);
                        break;
                    case 1:
                        sessionActivity = new Intent(getActivity(), TuesdaySessionActivity.class);
                        startActivity(sessionActivity);
                        break;
                    case 2:
                        sessionActivity = new Intent(getActivity(), WendsdaySessionActivity.class);
                        startActivity(sessionActivity);
                        break;
                    case 3:
                        sessionActivity = new Intent(getActivity(), ThursdaySessionActivity.class);
                        startActivity(sessionActivity);
                        break;
                    case 4:
                        sessionActivity = new Intent(getActivity(), FridaySessionActivity.class);
                        startActivity(sessionActivity);
                        break;
                    case 5:
                        sessionActivity = new Intent(getActivity(), SaturdaySessionActivity.class);
                        startActivity(sessionActivity);
                        break;
                    case 6:
                        sessionActivity = new Intent(getActivity(), SundaySessionActivity.class);
                        startActivity(sessionActivity);
                        break;
                }

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
}
