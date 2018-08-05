package com.mad.studymate.cardview.adapter;

import android.content.Context;
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
import com.mad.studymate.cardview.model.Note;

import java.util.List;
import java.util.Random;

public class NoteCardAdapter extends RecyclerView.Adapter<NoteCardAdapter.NoteViewHolder>{
    Context context;

    //card view clickable
    private OnItemClickListener mListener;

    //when note card clicked
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listner) {
        mListener = listner;
    }

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
        NoteCardAdapter.NoteViewHolder gvh = new NoteCardAdapter.NoteViewHolder(quizView, mListener);
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


    public class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle;
        TextView txtParaCount;
        TextView txtTag;

        //to different color for each card set color
        LinearLayout layout;

        //card view clickable
        private NoteCardAdapter.OnItemClickListener mListener;

        public NoteViewHolder(View view, final NoteCardAdapter.OnItemClickListener listener) {
            super(view);

            txtTitle=view.findViewById(R.id.idNoteTitle);
            txtParaCount = view.findViewById(R.id.idParagraphCount);
            txtTag = view.findViewById(R.id.idNoteTag);
            //to different color for each card set color
            layout = view.findViewById(R.id.idNoteFirstPortionLayout);
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
                switch (item.getItemId()){
                    case R.id.update_item_option:
                        Snackbar.make(view, "update pressed", Snackbar.LENGTH_SHORT).show();
                        break;
                    case R.id.delete_item_option:
                        Snackbar.make(view, "delete pressed", Snackbar.LENGTH_SHORT).show();
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
}