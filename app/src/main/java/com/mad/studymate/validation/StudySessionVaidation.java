package com.mad.studymate.validation;

import android.content.Context;
import android.widget.Toast;

public class StudySessionVaidation {

    private Context context;

    public StudySessionVaidation(Context context) {
        this.context = context;
    }

    public boolean validateFieldsEmpty(String name, String description, String startTime, String endTime) {
        if (name.isEmpty() && description.isEmpty() && startTime.isEmpty() && endTime.isEmpty()) {
            Toast.makeText(context, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (name.isEmpty()) {
            Toast.makeText(context, "Name cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        } else if (description.isEmpty()) {
            Toast.makeText(context, "description cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        } else if (startTime.isEmpty() && endTime.isEmpty()) {
            Toast.makeText(context, "start and end time cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (startTime.equals(endTime)) {
            Toast.makeText(context, "You should have proper time period", Toast.LENGTH_SHORT).show();
            return false;
        } else
            return true;
    }
}
