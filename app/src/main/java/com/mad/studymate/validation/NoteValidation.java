package com.mad.studymate.validation;

import android.content.Context;
import android.widget.Toast;

public class NoteValidation {

    private Context context;

    public NoteValidation(Context context) {
        this.context = context;
    }

    public boolean validateFieldsEmpty(String title, String noteTag, String paraOne) {
        if (title.isEmpty() && noteTag.isEmpty() && paraOne.isEmpty()) {
            Toast.makeText(context, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (title.isEmpty()) {
            Toast.makeText(context, "Title cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        } else if (noteTag.isEmpty()) {
            Toast.makeText(context, "Tag cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        } else if (paraOne.isEmpty()) {
            Toast.makeText(context, "Paragraph one cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        } else
            return true;
    }
}
