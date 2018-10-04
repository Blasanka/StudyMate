package com.mad.studymate.validation;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

public class SignupValidation implements Authentication {
    Context context;

    public SignupValidation(Context context) {
        this.context = context;
    }

    @Override
    public boolean validate(String username, String password) {
        return false;
    }

    @Override
    public boolean validate(String username, String password, String email) {
        if (username.equals("") || password.equals("") || email.equals("")) {
            generateError("All fields must be filled");
        } else if (username.equals("username") || password.equals("password") || email.equals("email")) {
            generateError("hoo hoooo anyone can guess your details, use valid details");
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            generateError("Not an email address");
        } else if (username.equals(password)) {
            generateError("Username cannot be equal to password");
        } else if (password.equals("12345678")) {
            generateError("You should try strong password");
        } else {
            if (password.length() < 8) {
                generateError("Password must contain more than 7 character");
            } else {
                if (TextUtils.isDigitsOnly(password)) {
                    generateError("Password should contain characters too");
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void generateError(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
