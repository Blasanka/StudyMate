package com.mad.studymate.cardview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mad.studymate.R;
import com.mad.studymate.cardview.model.Quiz;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AttemptedQuizCardAdapter extends RecyclerView.Adapter<AttemptedQuizCardAdapter.AttemptedQuizViewHolder> {
    Context context;

    //to different color for each card set color
    Random random = new Random(20);
    //card view clickable
    private OnItemClickListener mListener;
    private List<Quiz> attemtedQuizItemList;

    public AttemptedQuizCardAdapter(List<Quiz> attemtedQuizItemList, Context context) {
        this.attemtedQuizItemList = attemtedQuizItemList;
        this.context = context;
    }

    public void setOnItemClickListener(AttemptedQuizCardAdapter.OnItemClickListener listner) {
        mListener = listner;
    }

    @Override
    public AttemptedQuizCardAdapter.AttemptedQuizViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //inflate the layout file
        View quizView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_quiz_card, parent, false);
        AttemptedQuizCardAdapter.AttemptedQuizViewHolder gvh = new AttemptedQuizCardAdapter.AttemptedQuizViewHolder(quizView, mListener);
        return gvh;
    }

    @Override
    public void onBindViewHolder(AttemptedQuizCardAdapter.AttemptedQuizViewHolder holder, final int position) {
        holder.txtquestionCount.setText(attemtedQuizItemList.get(position).getQuestionCount() + "");
        holder.txtTitle.setText(attemtedQuizItemList.get(position).getTitle() + "");
        holder.txtTag.setText(attemtedQuizItemList.get(position).getQuizTag() + "");
        holder.txtType.setText(attemtedQuizItemList.get(position).getQuizType() + "");
        holder.txtTakenCount.setText(attemtedQuizItemList.get(position).getTimesTaken() + "");
        holder.txtScoreCount.setText(attemtedQuizItemList.get(position).getScoresOfQuiz() + "");
    }

    @Override
    public int getItemCount() {
        return attemtedQuizItemList.size();
    }

    //when quiz card clicked
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public class AttemptedQuizViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle;
        TextView txtquestionCount;
        TextView txtTag;
        TextView txtType;
        TextView txtTakenCount;
        TextView txtScoreCount;

        //to different color for each card set color
        LinearLayout layout;

        public AttemptedQuizViewHolder(View view, final AttemptedQuizCardAdapter.OnItemClickListener listener) {
            super(view);

            txtTitle = view.findViewById(R.id.idAttemptedTxtTitle);
            txtquestionCount = view.findViewById(R.id.idAttemptedQuestionCount);
            txtTag = view.findViewById(R.id.idAttemptedTxtTag);
            txtType = view.findViewById(R.id.idAttemptedTxtType);
            txtTakenCount = view.findViewById(R.id.idAttemptedTxtTakenCount);
            txtScoreCount = view.findViewById(R.id.idAttemptedTxtScoreCount);
            //to different color for each card set color
            layout = view.findViewById(R.id.idAttemptedFirstPortionLayout);
            layout.setBackgroundColor(random.nextInt());

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    //TODO: not functioning properly when no values in the search box -> best way to fetch from db
    public void filter(String text) {
        List<Quiz> tempList = new ArrayList();
        tempList.addAll(attemtedQuizItemList);
        attemtedQuizItemList.clear();
        if(text.isEmpty()) {
            attemtedQuizItemList.addAll(tempList);
        } else {
            text = text.toLowerCase();
            for(Quiz item: tempList){
                if(item.getTitle().toLowerCase().contains(text) || item.getQuizTag().toLowerCase().contains(text)){
                    attemtedQuizItemList.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }
}