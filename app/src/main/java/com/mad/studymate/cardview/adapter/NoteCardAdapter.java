package com.mad.studymate.cardview.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mad.studymate.R;
import com.mad.studymate.activity.UpdateNoteActivity;
import com.mad.studymate.cardview.model.Note;
import com.mad.studymate.db.NoteDbHelper;
import com.mad.studymate.db.StudyMateContractor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NoteCardAdapter extends RecyclerView.Adapter<NoteCardAdapter.NoteViewHolder>{
    Context context;

    //database helper to get every notes
    NoteDbHelper mDbHelper;

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
        //get notes from database
        mDbHelper = new NoteDbHelper(parent.getContext());

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
                Note note = noteItemList.get(position);
                switch (item.getItemId()){
                    case R.id.update_item_option:
                        Intent intent = new Intent(context, UpdateNoteActivity.class);
                        intent.putExtra("noteTitle", note.getNoteTitle());
                        intent.putExtra("noteTag", note.getNoteTag());
                        intent.putExtra("noteParaCount", note.getParagraphCount());
                        intent.putExtra("noteParaOne", note.getParagraphOne());
                        intent.putExtra("noteParaTwo", note.getParagraphTwo());
                        context.startActivity(intent);
                        break;
                    case R.id.delete_item_option:
                        SQLiteDatabase db = mDbHelper.getReadableDatabase();
                        // Define 'where' part of query.
                        String selection = StudyMateContractor.NoteEntry.COLUMN_NAME_TITLE + " = ?";
                        // Specify arguments in placeholder order.
                        String[] selectionArgs = { note.getNoteTitle() };
                        // Issue SQL statement.
                        int deletedRow = db.delete(StudyMateContractor.NoteEntry.TABLE_NAME, selection, selectionArgs);
                        //delete item from list
                        noteItemList.remove(position);
                        //update recycleview
                        notifyItemRemoved(position);

                        //delete from db
                        if(deletedRow != 0) {
                            Toast.makeText(view.getContext(), "Successfully deleted!", Toast.LENGTH_SHORT).show();
                        } else  {
                            Toast.makeText(view.getContext(), "failed to delete!", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    default:
                        Toast.makeText(view.getContext(), "nothing pressed", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });

        popup.show();
    }

    //TODO: not functioning properly when no values in the search box -> best way to fetch from db
    public void filter(String text) {
        List<Note> tempList = new ArrayList();
        tempList.addAll(noteItemList);
        noteItemList.clear();
        if(text.isEmpty()) {
            noteItemList.addAll(tempList);
        } else {
            text = text.toLowerCase();
            for(Note item: tempList){
                if(item.getNoteTitle().toLowerCase().contains(text) || item.getNoteTag().toLowerCase().contains(text)){
                    noteItemList.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }
}