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
import com.mad.studymate.validation.SignupValidation;

public class SignupActivity extends AppCompatActivity {

    EditText emailET;
    EditText usernameET;
    EditText passwordET;
    Button signupBt;
    TextView signTV;

    //authenticate login user
    Authentication auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //email field
        emailET = findViewById(R.id.emailSignTF);
        //username field
        usernameET = findViewById(R.id.usernameSignTF);
        //password field
        passwordET = findViewById(R.id.passwordSignlTF);
        //login button
        signupBt = findViewById(R.id.signupBt);

        //or log in on click
        signTV = findViewById(R.id.signupLabelBt);

        signTV.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

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
        signupBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //validate signup user, if keyboard done button
                validate();
            }
        });
    }

    private void validate() {

        auth = new SignupValidation(this);

        //validate signup user
        if (auth.validate(usernameET.getText().toString(),
                passwordET.getText().toString(),
                emailET.getText().toString())) {

            Intent homeIntent = new Intent(SignupActivity.this, MainActivity.class);
            startActivity(homeIntent);
            finish();
        }
    }
}
