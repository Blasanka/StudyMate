package com.mad.studymate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mad.studymate.R;
import com.mad.studymate.validation.Authentication;
import com.mad.studymate.validation.LoginValidation;

public class LoginActivity extends AppCompatActivity {

    EditText usernameET;
    EditText passwordET;
    Button loginBt;
    TextView signupTV;

    //authenticate login user
    Authentication auth;

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
                    validate();
                }
                return false;
            }
        });

        //login button pressed
        loginBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
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

    private void validate() {
        auth = new LoginValidation(this);
        //validate and show home screen
        if (auth.validate(usernameET.getText().toString(), passwordET.getText().toString())) {
            //to open up home activity
            Intent homeIntent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(homeIntent);
            finish();
        }
    }
}
