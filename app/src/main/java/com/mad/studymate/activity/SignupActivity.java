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

public class SignupActivity extends AppCompatActivity {

    EditText emailET;
    EditText usernameET;
    EditText passwordET;
    Button signupBt;
    TextView signTV;

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
                    //validate
                    validate(usernameET.getText().toString(), passwordET.getText().toString(), emailET.getText().toString());
                }
                return false;
            }
        });

        //login button pressed
        signupBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //validate
                validate(usernameET.getText().toString(), passwordET.getText().toString(), emailET.getText().toString());
            }
        });
    }

    private void validate(String username, String password, String email) {
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
                    Intent homeIntent = new Intent(SignupActivity.this, MainActivity.class);
                    startActivity(homeIntent);
                    finish();
                }
            }
        }
    }

    private void generateError(String message) {
        Toast.makeText(SignupActivity.this, message, Toast.LENGTH_LONG).show();
    }
}
