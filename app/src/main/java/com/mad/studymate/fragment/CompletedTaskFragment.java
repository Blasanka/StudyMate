package com.mad.studymate.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

// Instances of this class are fragments representing a single
// object in our collection.
public class CompletedTaskFragment extends Fragment {

    ActionBar actionBar;

    private Menu menu;
    private SearchView mSearchView;

    //add notes fab
    FloatingActionButton fab;

    private RecyclerView mRecyclerView;
    private TaskCardAdapter mAdapter;
    private List<Task> doneTaskList;
    //to check if task is done
    boolean isDone = true;
    //database helper to get every task
    TaskDbHelper mDbHelper;
    private TasksFragment.OnFragmentInteractionListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // The last two arguments ensure LayoutParams are inflated
        // properly.
        View view = inflater.inflate(
                R.layout.fragment_task_completed, container, false);// Inflate the layout for this fragment


        //getting the recyclerview from xml
        mRecyclerView = view.findViewById(R.id.idTasksRecyclerView);
        //mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Populate the groups
        doneTaskList = new ArrayList<>();

        //for now to show layout I'm adding isDone to true
        doneTaskList.add(new Task("Attend to MAD lecture", 3, "8:30 to 10:30", true));

        //get notes from database
        Cursor cursor = retrieveAllTasks();
        while (cursor.moveToNext()) {
            isDone = cursor.getInt(5) == 1;
            if (isDone)
                doneTaskList.add(new Task(cursor.getString(1), cursor.getInt(2), cursor.getString(3), cursor.getString(4), isDone));
        }
        cursor.close();

        //set adapter to recyclerview
        mAdapter = new TaskCardAdapter(doneTaskList, getActivity());
        mRecyclerView.setAdapter(mAdapter);

        setHasOptionsMenu(true);

        //card clicked event with sending necessary data to the answering activity.
        mAdapter.setOnItemClickListener(new TaskCardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Task task = doneTaskList.get(position);
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
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private Cursor retrieveAllTasks() {
        mDbHelper = new TaskDbHelper(getContext());
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

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

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mDbHelper.close();
    }
}