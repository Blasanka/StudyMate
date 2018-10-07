package com.mad.studymate.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.design.widget.FloatingActionButton;
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
import com.mad.studymate.activity.AddNoteActivity;
import com.mad.studymate.activity.ViewNoteActivity;
import com.mad.studymate.cardview.adapter.NoteCardAdapter;
import com.mad.studymate.cardview.model.Note;
import com.mad.studymate.db.NoteDbHelper;
import com.mad.studymate.db.StudyMateContractor;

import java.util.ArrayList;
import java.util.List;

public class NotesFragment extends Fragment {

    private Menu menu;
    private SearchView mSearchView;

    //add notes fab
    FloatingActionButton fab;

    private RecyclerView mRecyclerView;
    private NoteCardAdapter mAdapter;
    private List<Note> noteList;

    private OnFragmentInteractionListener mListener;

    //database helper to get every notes
    NoteDbHelper mDbHelper;

    public NotesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notes, container, false);

        //getting the recyclerview from xml
        mRecyclerView = view.findViewById(R.id.idNotesRecyclerView);
        //mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Populate the groups
        noteList = new ArrayList<>();
//        noteList.add(new Note("How to write good code", "Programming", 5));
//        noteList.add(new Note("Software Development Life Cycle(SDLC)","Software Engineering", 3));
//        noteList.add(new Note("How to learn faster", "Mind", 1));

        //get notes from database
        Cursor cursor = retrieveAllNotes();
        while(cursor.moveToNext()) {
            noteList.add(new Note(cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getString(4), cursor.getString(5)));
        }
        cursor.close();

        //set adapter to recyclerview
        mAdapter = new NoteCardAdapter(noteList, getActivity());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        setHasOptionsMenu(true);

        //card clicked event with sending necessary data to the view activity.
        mAdapter.setOnItemClickListener(new NoteCardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Note note = noteList.get(position);
                Intent intent = new Intent(getActivity(), ViewNoteActivity.class);
                intent.putExtra("title", note.getNoteTitle());
                intent.putExtra("noteTag", note.getNoteTag());
                intent.putExtra("noteParaCount", note.getParagraphCount());
                intent.putExtra("noteParaOne", note.getParagraphOne());
                intent.putExtra("noteParaTwo", note.getParagraphTwo());
                startActivity(intent);
            }
        });

        //fab click listner to open add note activity
        fab = view.findViewById(R.id.idNoteAddFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddNoteActivity.class);
                startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private Cursor retrieveAllNotes() {
        //get notes from database
        mDbHelper = new NoteDbHelper(getContext());
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                StudyMateContractor.NoteEntry.COLUMN_NAME_TITLE,
                StudyMateContractor.NoteEntry.COLUMN_NAME_TAG,
                StudyMateContractor.NoteEntry.COLUMN_NAME_PARAGRAPH_COUNT,
                StudyMateContractor.NoteEntry.COLUMN_NAME_PARAGRAPH_1,
                StudyMateContractor.NoteEntry.COLUMN_NAME_PARAGRAPH_2
        };

        // Filter results WHERE "title" = 'My Title'
//        String selection = StudyMateContractor.NoteEntry.COLUMN_NAME_TITLE + " = ?";
        //String[] selectionArgs = { "How to Code" };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                StudyMateContractor.NoteEntry._ID + " DESC";

        Cursor cursor = db.query(
                StudyMateContractor.NoteEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        return cursor;
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
        mDbHelper.close();
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
