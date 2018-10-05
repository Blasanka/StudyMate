package com.mad.studymate.validation;

import android.content.Context;
import android.widget.Toast;

public class QAValidation {

    private Context context;

    public QAValidation(Context context) {
        this.context = context;
    }

    public boolean isFieldsEmpty(String question1, String question2, boolean isSelectedFirstGroup, boolean isSelectedSecGroup) {
        if (question1.isEmpty() && question2.isEmpty() && isSelectedFirstGroup && isSelectedSecGroup) {
            Toast.makeText(context, "All questions and answers must be filled", Toast.LENGTH_SHORT).show();
            return true;
        } else if (question1.isEmpty()) {
            Toast.makeText(context, "Question 1 cannot be empty", Toast.LENGTH_SHORT).show();
            return true;
        } else if (question2.isEmpty()) {
            Toast.makeText(context, "Question 2 cannot be empty", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (!isSelectedFirstGroup) {
            Toast.makeText(context, "Correct answer must select", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (!isSelectedSecGroup) {
            Toast.makeText(context, "Correct answer must select", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
}
