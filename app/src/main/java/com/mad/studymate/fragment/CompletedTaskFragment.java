package com.mad.studymate.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.mad.studymate.R;
import com.mad.studymate.activity.AddTaskActivity;
import com.mad.studymate.activity.ViewTaskActivity;
import com.mad.studymate.cardview.adapter.TaskCardAdapter;
import com.mad.studymate.cardview.model.Task;

import java.util.ArrayList;
import java.util.List;

// Instances of this class are fragments representing a single
// object in our collection.
public class CompletedTaskFragment extends Fragment {

    ActionBar actionBar;

    //add notes fab
    FloatingActionButton fab;

    private RecyclerView mRecyclerView;
    private TaskCardAdapter mAdapter;
    private List<Task> doneTaskList;

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

        //set adapter to recyclerview
        mAdapter = new TaskCardAdapter(doneTaskList, getActivity());
        mRecyclerView.setAdapter(mAdapter);

        //card clicked event with sending necessary data to the answering activity.
        mAdapter.setOnItemClickListener(new TaskCardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Task task = doneTaskList.get(position);
                Intent intent = new Intent(getActivity(), ViewTaskActivity.class);
                intent.putExtra("title", task.getTaskTitle());
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
}