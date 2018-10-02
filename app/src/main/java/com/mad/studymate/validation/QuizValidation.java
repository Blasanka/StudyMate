package com.mad.studymate.validation;

import android.content.Context;
import android.widget.Toast;

public class QuizValidation {

    private Context context;

    public QuizValidation(Context context) {
        this.context = context;
    }

    public boolean isFieldsEmpty(String title, String quizTag, String quizType, String qCount) {
        if (quizTag.isEmpty() && title.isEmpty() && qCount.isEmpty()) {
            Toast.makeText(context, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
            return true;
        } else if (title.isEmpty()) {
            Toast.makeText(context, "Title cannot be empty", Toast.LENGTH_SHORT).show();
            return true;
        } else if (quizTag.isEmpty()) {
            Toast.makeText(context, "Tag cannot be empty", Toast.LENGTH_SHORT).show();
            return true;
        } else if (quizType.isEmpty()) {
            Toast.makeText(context, "Type should select", Toast.LENGTH_SHORT).show();
            return true;
        } else if (qCount.isEmpty()) {
            Toast.makeText(context, "question count cannot be empty", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            return validateQesCount(Integer.parseInt(qCount));
        }
    }

    public boolean validateQesCount(int qCount) {
        if (qCount <= 0) {
            Toast.makeText(context, "At least one question must", Toast.LENGTH_SHORT).show();
            return true;
        }

        return false;
    }
}
