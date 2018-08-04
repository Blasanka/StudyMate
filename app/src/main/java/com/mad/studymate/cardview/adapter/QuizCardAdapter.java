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

import java.util.List;
import java.util.Random;

public class QuizCardAdapter extends RecyclerView.Adapter<QuizCardAdapter.QuizViewHolder>{
    Context context;

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
        }
    }
}