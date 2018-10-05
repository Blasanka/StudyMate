package com.mad.studymate.cardview.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mad.studymate.R;
import com.mad.studymate.activity.UpdateQuizActivity;
import com.mad.studymate.cardview.model.Quiz;
import com.mad.studymate.db.QuizTableController;
import com.mad.studymate.jsons.JsonHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuizCardAdapter extends RecyclerView.Adapter<QuizCardAdapter.QuizViewHolder>{
    Context context;

    //to manage quiz table(update, delete)
    QuizTableController quizTableController;

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
        quizTableController = new QuizTableController(parent.getContext());

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
                        Intent intent = new Intent(context, UpdateQuizActivity.class);
                        intent.putExtra("quizTitle", quiz.getTitle());
                        intent.putExtra("quizTag", quiz.getQuizTag());
                        intent.putExtra("quizType", quiz.getQuizType());
                        intent.putExtra("noOfQuestion", quiz.getQuestionCount());
                        context.startActivity(intent);
                        break;
                    case R.id.delete_item_option:

                        //to delete the quiz related json file
                        JsonHandler file = new JsonHandler(context.getApplicationContext());
                        file.deleteFile(quizItemList.get(position).getTitle());

                        quizTableController.deleteQuiz(quiz.getTitle(), view);
                        quizTableController.close();
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