package com.mad.studymate.validation;

import android.content.Context;

import com.mad.studymate.cardview.model.Question;

import java.util.List;

public class QAValidation {

    private Context context;

    public QAValidation(Context context) {
        this.context = context;
    }

    public boolean isFieldsEmpty(String type, List<Question> questionsList) {
        if (!questionsList.isEmpty()) {
            for (Question question : questionsList) {
                if (question.getQuestion().equals("")) {
//                     ((EditText) value).setError("Questions must be typed");
                    return true;
                } else if (type.equals("True or False") && question.getCorrectAnswers().isEmpty()) {
//                    ((RadioButton) value).setError("True or false must selected");
                    return true;
                } else if (type.equals("Single Answer") && question.getCorrectAnswers().isEmpty()) {//|| question.getAllAnswers().isEmpty()) {
//                    ((EditText) value).setError("Correct answer must be typed");
                    return true;
                } else if (type.equals("Multiple Answer") && question.getCorrectAnswers().isEmpty()) {//|| question.getAllAnswers().isEmpty()) {
//                    ((EditText) value).setError("Correct answers must be typed");
                    return true;
                }
            }
        } else {
            return true;
        }
        return false;
    }
}
