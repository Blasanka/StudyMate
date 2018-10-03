package com.mad.studymate.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
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
import com.mad.studymate.activity.AddTaskActivity;
import com.mad.studymate.activity.ViewTaskActivity;
import com.mad.studymate.cardview.adapter.TaskCardAdapter;
import com.mad.studymate.cardview.model.Task;
import com.mad.studymate.db.StudyMateContractor;
import com.mad.studymate.db.TaskDbHelper;

import java.util.ArrayList;
import java.util.List;

public class TasksFragment extends Fragment {

    ActionBar actionBar;

    private Menu menu;
    private SearchView mSearchView;

    //add notes fab
    FloatingActionButton fab;

    private RecyclerView mRecyclerView;
    private TaskCardAdapter mAdapter;
    private List<Task> taskList;
    boolean isDone = false;

    private OnFragmentInteractionListener mListener;


    //database helper to get every task
    TaskDbHelper mDbHelper;

    public TasksFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle("Not Completed Tasks");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);

        //getting the recyclerview from xml
        mRecyclerView = view.findViewById(R.id.idTasksRecyclerView);
        //mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Populate the groups
        taskList = new ArrayList<>();
        taskList.add(new Task("Read JS book", 1, "4:30 to 5:30"));
        taskList.add(new Task("Write a letter", 2, "5:30 to 6:00"));

        //get notes from database
        Cursor cursor = retrieveAllTasks();
        while (cursor.moveToNext()) {
            isDone = cursor.getInt(5) == 1;
            if (!isDone)
                taskList.add(new Task(cursor.getString(1), cursor.getInt(2), cursor.getString(3),
                        cursor.getString(4), isDone));
        }
        cursor.close();

        //set adapter to recyclerview
        mAdapter = new TaskCardAdapter(taskList, getActivity());
        mRecyclerView.setAdapter(mAdapter);

        setHasOptionsMenu(true);

        //card clicked event with sending necessary data to the task view activity.
        mAdapter.setOnItemClickListener(new TaskCardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Task task = taskList.get(position);
                Intent intent = new Intent(getActivity(), ViewTaskActivity.class);
                intent.putExtra("title", task.getTaskTitle());
                intent.putExtra("priorityNo", task.getPriorityNo());
                intent.putExtra("timePeriod", task.getTimePeriod());
                intent.putExtra("description", task.getDescription());
                intent.putExtra("isDone", task.isDone());
                startActivity(intent);
            }
        });

        //fab click listner to open add note activity
        fab = view.findViewById(R.id.idTaskAddFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddTaskActivity.class);
                startActivity(intent);
            }
        });

        //bottom navigation
        BottomNavigationView navigation = view.findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        return view;
    }

    private Cursor retrieveAllTasks() {
        //access to the database
        mDbHelper = new TaskDbHelper(getContext());
        //to get values from database
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        //columns I want to fetch values
        String[] projection = {
                BaseColumns._ID,
                StudyMateContractor.TaskEntry.COLUMN_NAME_TITLE,
                StudyMateContractor.TaskEntry.COLUMN_NAME_PRIORITY_NO,
                StudyMateContractor.TaskEntry.COLUMN_NAME_TIME_PERIOD,
                StudyMateContractor.TaskEntry.COLUMN_NAME_DESCRIPTION,
                StudyMateContractor.TaskEntry.COLUMN_NAME_IS_DONE
        };

        String sortOrder =
                StudyMateContractor.TaskEntry._ID + " DESC";

        Cursor cursor = db.query(
                StudyMateContractor.TaskEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        return cursor;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.action_groups:
                    actionBar.setTitle("Not Completed Tasks");
                    fragment = new TasksFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.action_users:
                    actionBar.setTitle("Completed Tasks");
                    fragment = new CompletedTaskFragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();
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
        // TODO: Update argument type and name
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
