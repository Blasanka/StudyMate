package com.mad.studymate.cardview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mad.studymate.R;
import com.mad.studymate.db.NoteDbHelper;
import com.mad.studymate.db.QuizDbHelper;
import com.mad.studymate.db.SessionDbHelper;
import com.mad.studymate.db.TaskDbHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeCardAdapter extends BaseAdapter {
    Context context;

    private List<String> labelList;
    private List<Long> countList;

    public HomeCardAdapter(Context context) {
        this.context = context;

        //set values to cards in home fragment
        this.labelList = new ArrayList();
        labelList.addAll(Arrays.asList("Notes", "Quizes", "Quiz Attempts", "Tasks", "High Priority Tasks", "Study Sessions"));
        getDbCounts();
    }

    @Override
    public int getCount() {
        return countList.size();
    }

    @Override
    public Object getItem(int position) {
        return labelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return countList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View myView;

        if (convertView == null) {

            myView = new View(context);

            // get layout from mobile.xml
            myView = inflater.inflate(R.layout.home_square_item, null);

            TextView txtCount = myView.findViewById(R.id.idTxCount);
            TextView txtLabel = myView.findViewById(R.id.idTxtLabel);
            //to different color for each card set color
            LinearLayout layout = myView.findViewById(R.id.idHomeItemLinearLayout);

            int color = context.getResources().getColor(R.color.gridcard_light_background);
            layout.setBackgroundColor(color);
            txtCount.setText(String.valueOf(countList.get(position)));
            txtLabel.setText(labelList.get(position) + "");
        } else {
            myView = convertView;
        }

        return myView;
    }

    //TODO: where is oop
    //get row counts of every table
    public void getDbCounts() {
        countList = new ArrayList();

        //notes count
        NoteDbHelper noteDbHelper = new NoteDbHelper(context);
        countList.add(noteDbHelper.getNotesCount());
        noteDbHelper.close();

        //quiz count
        QuizDbHelper quizDbHelper = new QuizDbHelper(context);
        countList.add(quizDbHelper.getQuizCount());
        //TODO: fetch attempts
        //quiz attempts count
        countList.add(quizDbHelper.getQuizCount() + 5);
        quizDbHelper.close();

        //tasks count
        TaskDbHelper taskDbHelper = new TaskDbHelper(context);
        countList.add(taskDbHelper.getTasksCount());
        taskDbHelper.close();

        SessionDbHelper sessionDbHelper = new SessionDbHelper(context);
        //study session count
        countList.add(sessionDbHelper.getSessionsCount());
        sessionDbHelper.close();

        //high priority
        countList.add(new Long(2));
    }
}