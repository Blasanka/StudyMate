package com.mad.studymate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mad.studymate.R;

public class LoginActivity extends AppCompatActivity {

    EditText usernameET;
    EditText passwordET;
    Button loginBt;
    TextView signupTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //username field
        usernameET = findViewById(R.id.usernameField);
        //password field
        passwordET = findViewById(R.id.passwordField);
        //login button
        loginBt = findViewById(R.id.signinBt);
        //or log in text view
        signupTV = findViewById(R.id.signUpLabelBt);

        //after password typed and pressed done/enter button in soft keyboard, validate and launch home screen
        passwordET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    //validate
                    validate(usernameET.getText().toString(), passwordET.getText().toString());
                }
                return false;
            }
        });

        //login button pressed
        loginBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //validate
                validate(usernameET.getText().toString(), passwordET.getText().toString());
            }
        });

        //or log in on click
        signupTV.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void validate(String username, String password) {
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
                    Intent homeIntent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(homeIntent);
                    finish();
                }
            }
        }
    }

    private void generateError(String message) {
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
    }
}
