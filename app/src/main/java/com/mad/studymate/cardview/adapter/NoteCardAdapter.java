package com.mad.studymate.cardview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mad.studymate.R;
import com.mad.studymate.cardview.model.Note;

import java.util.List;
import java.util.Random;

public class NoteCardAdapter extends RecyclerView.Adapter<NoteCardAdapter.NoteViewHolder>{
    Context context;
    //to different color for each card set color
    Random random = new Random(20);
    private List<Note> noteItemList;

    public NoteCardAdapter(List<Note> noteItemList, Context context) {
        this.noteItemList = noteItemList;
        this.context = context;
    }

    @Override
    public NoteCardAdapter.NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout file
        View quizView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_note_card, parent, false);
        NoteCardAdapter.NoteViewHolder gvh = new NoteCardAdapter.NoteViewHolder(quizView);
        return gvh;
    }

    @Override
    public void onBindViewHolder(NoteCardAdapter.NoteViewHolder holder, final int position) {
        holder.txtParaCount.setText(noteItemList.get(position).getParagraphCount()+"");
        holder.txtTitle.setText(noteItemList.get(position).getNoteTitle()+"");
        holder.txtTag.setText(noteItemList.get(position).getNoteTag()+"");
    }

    @Override
    public int getItemCount() {
        return noteItemList.size();
    }


    //when quiz card clicked
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }


    public class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtTitle;
        TextView txtParaCount;
        TextView txtTag;

        //to different color for each card set color
        LinearLayout layout;

        //card view clickable
        private NoteCardAdapter.OnItemClickListener mListener;

        public NoteViewHolder(View view) {
            super(view);
            itemView.setOnClickListener(this);
            txtTitle=view.findViewById(R.id.idNoteTitle);
            txtParaCount = view.findViewById(R.id.idParagraphCount);
            txtTag = view.findViewById(R.id.idNoteTag);
            //to different color for each card set color
            layout = view.findViewById(R.id.idNoteFirstPortionLayout);
            layout.setBackgroundColor(random.nextInt());
        }



        public NoteViewHolder(View view, NoteCardAdapter.OnItemClickListener listener) {
            this(view);
            mListener = listener;
        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(view, getPosition());
            }
        }
    }
}