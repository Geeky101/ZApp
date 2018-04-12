package com.justinmutsito.zapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.justinmutsito.zapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.emailField)
    AppCompatEditText mEmailField;
    @BindView(R.id.passwordField)
    AppCompatEditText mPasswordField;
    @BindView(R.id.noAccountLabel)
    TextView mNoAccountLabel;
    @BindView(R.id.signUpLabel)
    TextView mSignUpLabel;
    @BindView(R.id.loginLayout)
    LinearLayout mLoginLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.noAccountLabel, R.id.signUpLabel})
    public void onNoAccountLabelClicked() {
        startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
    }

    @OnClick(R.id.loginLayout)
    public void onLoginLayoutClicked() {

        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();

        if (email.equals("") || password.equals("")) {
            //Treat login error
        } else {
            //Login user
        }

    }
}
