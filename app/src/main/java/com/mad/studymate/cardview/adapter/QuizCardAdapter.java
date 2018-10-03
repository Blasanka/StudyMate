package com.mad.studymate.cardview.adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mad.jsons.JsonHandler;
import com.mad.studymate.R;
import com.mad.studymate.cardview.model.Quiz;
import com.mad.studymate.db.QuizDbHelper;
import com.mad.studymate.db.StudyMateContractor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuizCardAdapter extends RecyclerView.Adapter<QuizCardAdapter.QuizViewHolder>{
    Context context;

    //database helper to get every notes
    QuizDbHelper mDbHelper;

    //card view clickable
    private OnItemClickListener mListener;

    //when quiz card clicked
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listner) {
        mListener = listner;
    }

    //to different color for each card set color
    Random random = new Random(20);
    private List<Quiz> quizItemList;

    public QuizCardAdapter(List<Quiz> quizItemList, Context context) {
        this.quizItemList = quizItemList;
        this.context = context;
    }

    @Override
    public QuizCardAdapter.QuizViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //get notes from database
        mDbHelper = new QuizDbHelper(parent.getContext());

        //inflate the layout file
        View quizView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_quiz_card, parent, false);
        QuizCardAdapter.QuizViewHolder gvh = new QuizCardAdapter.QuizViewHolder(quizView, mListener);
        return gvh;
    }

    @Override
    public void onBindViewHolder(QuizCardAdapter.QuizViewHolder holder, final int position) {
        holder.txtquestionCount.setText(quizItemList.get(position).getQuestionCount()+"");
        holder.txtTitle.setText(quizItemList.get(position).getTitle()+"");
        holder.txtTag.setText(quizItemList.get(position).getQuizTag()+"");
        holder.txtType.setText(quizItemList.get(position).getQuizType()+"");
        holder.txtTakenCount.setText(quizItemList.get(position).getTimesTaken()+"");
        holder.txtScoreCount.setText(quizItemList.get(position).getScoresOfQuiz()+"");
    }

    @Override
    public int getItemCount() {
        return quizItemList.size();
    }


    public class QuizViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle;
        TextView txtquestionCount;
        TextView txtTag;
        TextView txtType;
        TextView txtTakenCount;
        TextView txtScoreCount;

        //to different color for each card set color
        LinearLayout layout;

        public QuizViewHolder(View view, final OnItemClickListener listener) {
            super(view);

            txtTitle=view.findViewById(R.id.idTxtTitle);
            txtquestionCount = view.findViewById(R.id.idQuestionCount);
            txtTag = view.findViewById(R.id.idTxtTag);
            txtType = view.findViewById(R.id.idTxtType);
            txtTakenCount = view.findViewById(R.id.idTxtTakenCount);
            txtScoreCount = view.findViewById(R.id.idTxtScoreCount);
            //to different color for each card set color
            layout = view.findViewById(R.id.idFirstPortionLayout);
            layout.setBackgroundColor(random.nextInt());

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            openOptionMenu(view, position);
                        }
                    }
                    return true;
                }
            });

        }
    }

    public void openOptionMenu(final View view, final int position){
        PopupMenu popup = new PopupMenu(view.getContext(), view);
        popup.getMenuInflater().inflate(R.menu.menu_popup_items, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Quiz quiz = quizItemList.get(position);
                switch (item.getItemId()){
                    case R.id.update_item_option:
                        Snackbar.make(view, "update pressed", Snackbar.LENGTH_SHORT).show();
                        break;
                    case R.id.delete_item_option:

                        JsonHandler deleteFile = new JsonHandler(context.getApplicationContext());
                        deleteFile.deleteFile();

                        deleteQuiz(quiz.getTitle(), view);
                        quizItemList.remove(position);
                        notifyItemRemoved(position);
                        break;
                    default:
                        Snackbar.make(view, "nothing pressed", Snackbar.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });

        popup.show();
    }

    private void deleteQuiz(String quizTitle, View view) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        // Define 'where' part of query.
        String selection = StudyMateContractor.QuizEntry.COLUMN_NAME_TITLE + " = ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = {quizTitle};
        // Issue SQL statement.
        int deletedRow = db.delete(StudyMateContractor.QuizEntry.TABLE_NAME, selection, selectionArgs);

        //delete from db
        if (deletedRow != 0) {
            Snackbar.make(view, "Successfully deleted!", Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(view, "failed to delete!", Snackbar.LENGTH_SHORT).show();
        }
    }

    //TODO: not functioning properly when no values in the search box -> best way to fetch from db
    public void filter(String text) {
        List<Quiz> tempList = new ArrayList();
        tempList.addAll(quizItemList);
        quizItemList.clear();
        if(text.isEmpty()) {
            quizItemList.addAll(tempList);
        } else {
            text = text.toLowerCase();
            for(Quiz item: tempList){
                if(item.getTitle().toLowerCase().contains(text) || item.getQuizTag().toLowerCase().contains(text)){
                    quizItemList.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }
}