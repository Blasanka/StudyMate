package com.mad.studymate.cardview.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mad.studymate.R;
import com.mad.studymate.activity.UpdateSessionActivity;
import com.mad.studymate.cardview.model.Session;
import com.mad.studymate.db.SessionDbHelper;
import com.mad.studymate.db.StudyMateContractor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SessionCardAdapter extends RecyclerView.Adapter<SessionCardAdapter.SessionViewHolder> {
    Context context;
    SessionDbHelper sessionDbHelper;
    private OnItemClickListener onItemClickListener;
    //to different color for each card set color
    Random random = new Random(20);
    private List<Session> sessionItemList;

    public SessionCardAdapter(List<Session> sessionItemList, Context context) {
        this.sessionItemList = sessionItemList;
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    @NonNull
    @Override
    public SessionCardAdapter.SessionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        sessionDbHelper = new SessionDbHelper(viewGroup.getContext());
        View SessionView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_session_card, viewGroup, false);
        SessionCardAdapter.SessionViewHolder as = new SessionCardAdapter.SessionViewHolder(SessionView, onItemClickListener);
        return as;
    }

    @Override
    public void onBindViewHolder(@NonNull SessionCardAdapter.SessionViewHolder sessionViewHolder, final int i) {

        sessionViewHolder.sessionTitleTv.setText(sessionItemList.get(i).getName() + "");
        sessionViewHolder.fromTimeTv.setText(sessionItemList.get(i).getFrom() + "");
        sessionViewHolder.toTimeTv.setText(sessionItemList.get(i).getTo() + "");
        sessionViewHolder.progressBar.setProgress(sessionItemList.get(i).getComplete());

    }

    @Override
    public int getItemCount() {
        return sessionItemList.size();
    }

    public void openOptionMenu(final View view, final int position) {
        PopupMenu popup = new PopupMenu(view.getContext(), view);
        popup.getMenuInflater().inflate(R.menu.menu_popup_items, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Session session = sessionItemList.get(position);
                switch (item.getItemId()) {
                    case R.id.update_item_option:
                        Intent intent = new Intent(context, UpdateSessionActivity.class);
                        intent.putExtra("Title", session.getName());
                        intent.putExtra("descript", session.getDescription());
                        intent.putExtra("from", session.getFrom());
                        intent.putExtra("to", session.getTo());
                        intent.putExtra("complete", session.getComplete());

                        context.startActivity(intent);
                        break;
                    case R.id.delete_item_option:
                        SQLiteDatabase db = sessionDbHelper.getReadableDatabase();
                        // Define 'where' part of query.
                        String selection = StudyMateContractor.SessionEntry.Col_1 + " = ?";
                        // Specify arguments in placeholder order.
                        String[] selectionArgs = {session.getName()};
                        // Issue SQL statement.
                        int deletedRow = db.delete(StudyMateContractor.SessionEntry.TABLE_NAME, selection, selectionArgs);
                        //delete item from list
                        sessionItemList.remove(position);
                        //update recycleview
                        notifyItemRemoved(position);

                        //delete from db
                        if (deletedRow != 0) {
                            Toast.makeText(view.getContext(), "Successfully deleted!", Toast.LENGTH_SHORT).show();
                        } else {
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

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    //TODO: not functioning properly when no values in the search box -> best way to fetch from db
    public void filter(String text) {
        List<Session> tempList = new ArrayList();
        tempList.addAll(sessionItemList);
        sessionItemList.clear();
        if (text.isEmpty()) {
            sessionItemList.addAll(tempList);
        } else {
            text = text.toLowerCase();
            for (Session item : tempList) {
                if (item.getName().toLowerCase().contains(text) || item.getFrom().toLowerCase().contains(text)) {
                    sessionItemList.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class SessionViewHolder extends RecyclerView.ViewHolder {
        TextView sessionTitleTv, fromTimeTv, toTimeTv;
        ProgressBar progressBar;

        //to different color for each card set color
        LinearLayout layout;

        public SessionViewHolder(@NonNull View itemView, final SessionCardAdapter.OnItemClickListener listener) {
            super(itemView);
            sessionTitleTv = itemView.findViewById(R.id.idSessionTitle);
            fromTimeTv = itemView.findViewById(R.id.idFromTime);
            toTimeTv = itemView.findViewById(R.id.idToTime);
            progressBar = itemView.findViewById(R.id.idSessionProgress);

            //to different color for each card set color
            layout = itemView.findViewById(R.id.idFirstPortionLayout);
            layout.setBackgroundColor(random.nextInt());

            itemView.setOnClickListener(new View.OnClickListener() {
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

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
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
}
