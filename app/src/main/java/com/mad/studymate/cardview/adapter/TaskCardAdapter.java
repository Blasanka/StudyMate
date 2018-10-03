package com.mad.studymate.cardview.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mad.studymate.R;
import com.mad.studymate.activity.UpdateTaskActivity;
import com.mad.studymate.cardview.model.Note;
import com.mad.studymate.cardview.model.Task;
import com.mad.studymate.db.StudyMateContractor;
import com.mad.studymate.db.TaskDbHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TaskCardAdapter extends RecyclerView.Adapter<TaskCardAdapter.TaskViewHolder>{
    Context context;

    //database helper to get every notes
    TaskDbHelper mDbHelper;
    //to check task is done or not
    boolean isDone = false;

    //card view clickable
    private TaskCardAdapter.OnItemClickListener mListener;

    //when quiz card clicked
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(TaskCardAdapter.OnItemClickListener listner) {
        mListener = listner;
    }

    //to different color for each card set color
    Random random = new Random(20);
    private List<Task> taskItemList;

    public TaskCardAdapter(List<Task> taskItemList, Context context) {
        this.taskItemList = taskItemList;
        this.context = context;
    }

    @Override
    public TaskCardAdapter.TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //get notes from database
        mDbHelper = new TaskDbHelper(parent.getContext());

        //inflate the layout file
        View taskView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_task_card, parent, false);
        TaskCardAdapter.TaskViewHolder gvh = new TaskCardAdapter.TaskViewHolder(taskView, mListener);
        return gvh;
    }

    @Override
    public void onBindViewHolder(TaskCardAdapter.TaskViewHolder holder, final int position) {
        holder.txtTaskTitle.setText(taskItemList.get(position).getTaskTitle()+"");
        holder.txtPriorityNum.setText(taskItemList.get(position).getPriorityNo()+"");
        holder.txtTimePeriod.setText(taskItemList.get(position).getTimePeriod()+"");

        //If task is done, it can be deleted using delete image button
        int doneRes = R.drawable.ic_done_green_24dp;
        int unDoneRes = R.drawable.ic_delete_red_24dp;
        holder.btDoneCheck.setImageResource(taskItemList.get(position).isDone() ? unDoneRes : doneRes);
    }

    @Override
    public int getItemCount() {
        return taskItemList.size();
    }

    public void openOptionMenu(final View view, final int position) {
        PopupMenu popup = new PopupMenu(view.getContext(), view);
        popup.getMenuInflater().inflate(R.menu.menu_popup_items, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Task task = taskItemList.get(position);
                switch (item.getItemId()) {
                    case R.id.update_item_option:
                        Intent intent = new Intent(context, UpdateTaskActivity.class);
                        intent.putExtra("taskTitle", task.getTaskTitle());
                        intent.putExtra("priorityNo", task.getPriorityNo());
                        intent.putExtra("timePeriod", task.getTimePeriod());
                        intent.putExtra("description", task.getDescription());
                        intent.putExtra("isDone", task.isDone());
                        context.startActivity(intent);
                        break;
                    case R.id.delete_item_option:
                        deleteTask(task.getTaskTitle(), view);
                        taskItemList.remove(position);
                        notifyItemRemoved(position);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        popup.show();
    }

    //update task status in db when done button pressed
    public int updateTask(String title) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(StudyMateContractor.TaskEntry.COLUMN_NAME_IS_DONE, isDone);

        // Which row to update, based on the title
        String selection = StudyMateContractor.TaskEntry.COLUMN_NAME_TITLE + " = ?";
        String[] selectionArgs = {title};

        int count = db.update(
                StudyMateContractor.TaskEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        return count;
    }

    private void deleteTask(String taskTitle, View view) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        // Define 'where' part of query.
        String selection = StudyMateContractor.TaskEntry.COLUMN_NAME_TITLE + " = ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = {taskTitle};
        // Issue SQL statement.
        int deletedRow = db.delete(StudyMateContractor.TaskEntry.TABLE_NAME, selection, selectionArgs);

        //delete from db
        if (deletedRow != 0) {
//                            Snackbar.make(view, "Successfully deleted!", Snackbar.LENGTH_SHORT).show();
            Toast.makeText(view.getContext(), "Successfully deleted!", Toast.LENGTH_SHORT).show();
        } else {
//                            Snackbar.make(view, "failed to delete!", Snackbar.LENGTH_SHORT).show();
            Toast.makeText(view.getContext(), "failed to delete!", Toast.LENGTH_SHORT).show();
        }
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView txtTaskTitle;
        TextView txtPriorityNum;
        TextView txtTimePeriod;
        ImageButton btDoneCheck;

        //to different color for each card set color
        LinearLayout layout;

        public TaskViewHolder(View view, final TaskCardAdapter.OnItemClickListener listener) {
            super(view);

            txtTaskTitle=view.findViewById(R.id.idTaskTitle);
            txtPriorityNum = view.findViewById(R.id.idPriorityNo);
            txtTimePeriod = view.findViewById(R.id.idTimePeriod);
            btDoneCheck = view.findViewById(R.id.idMarkDoneCheck);

            //to different color for each card set color
            layout = view.findViewById(R.id.idTaskFirstPortionLayout);
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

            btDoneCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (!taskItemList.get(position).isDone()) {
                            isDone = true;
                            taskItemList.get(position).setDone(isDone);
                            updateTask(taskItemList.get(position).getTaskTitle());
//                        notifyItemChanged(position);
                            taskItemList.remove(position);
                            notifyItemRemoved(position);
                        } else {
                            deleteTask(taskItemList.get(position).getTaskTitle(), view);
                            Log.d("Missing", taskItemList.get(position).getTaskTitle());
//                        notifyItemChanged(position);
                            taskItemList.remove(position);
                            notifyItemRemoved(position);
                        }
                    }
                }
            });
        }
    }

    //TODO: not functioning properly when no values in the search box -> best way to fetch from db
    public void filter(String text) {
        List<Task> tempList = new ArrayList();
        tempList.addAll(taskItemList);
        taskItemList.clear();
        if(text.isEmpty()) {
            taskItemList.addAll(tempList);
        } else {
            text = text.toLowerCase();
            for(Task item: tempList){
                if(item.getTaskTitle().toLowerCase().contains(text) || String.valueOf(item.getPriorityNo()).contains(text)){
                    taskItemList.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }
}