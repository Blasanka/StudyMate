package com.mad.studymate.validation;

import android.content.Context;
import android.widget.Toast;

public class TaskValidation {

    private Context context;

    public TaskValidation(Context context) {
        this.context = context;
    }

    public boolean validateFieldsEmpty(String title, String priorityNo, String startTime, String description) {
        if (priorityNo.isEmpty() && title.isEmpty() && startTime.isEmpty() && description.isEmpty()) {
            Toast.makeText(context, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        } else if (priorityNo.isEmpty()) {
            Toast.makeText(context, "Priority No. cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        } else if (title.isEmpty()) {
            Toast.makeText(context, "Title cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        } else if (startTime.isEmpty()) {
            Toast.makeText(context, "Start time cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        } else if (description.isEmpty()) {
            Toast.makeText(context, "description cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        } else
            return true;
    }

    public boolean validatePriorityNo(int priorityNo) {
        if (priorityNo <= 0) {
            Toast.makeText(context, "Priority No. cannot be less than 1", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
