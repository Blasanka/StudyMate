package com.mad.studymate.cardview.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mad.studymate.R;
import com.mad.studymate.cardview.model.Task;

import java.util.List;
import java.util.Random;

public class TaskCardAdapter extends RecyclerView.Adapter<TaskCardAdapter.TaskViewHolder>{
    Context context;

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

            boolean isDone = false;
            btDoneCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        boolean isDone = true;
                    }
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