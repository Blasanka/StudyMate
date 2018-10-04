package com.mad.studymate.validation;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

public class LoginValidation implements Authentication {
    Context context;

    public LoginValidation(Context context) {
        this.context = context;
    }

    @Override
    public boolean validate(String username, String password) {
        if (username.equals("") || password.equals("")) {
            generateError("Type username and password");
        } else if (username.equals("username") || password.equals("password")) {
            generateError("You should try strong username and password");
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
    public boolean validate(String username, String password, String email) {
        return false;
    }

    @Override
    public void generateError(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
