package com.justinmutsito.zapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.justinmutsito.zapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends AppCompatActivity {

    @BindView(R.id.backIcon)
    ImageView mBackIcon;
    @BindView(R.id.signUpLabel)
    TextView mSignUpLabel;
    @BindView(R.id.accountImage)
    ImageView mAccountImage;
    @BindView(R.id.nameField)
    AppCompatEditText mNameField;
    @BindView(R.id.surnameField)
    AppCompatEditText mSurnameField;
    @BindView(R.id.emailField)
    AppCompatEditText mEmailField;
    @BindView(R.id.passwordField)
    AppCompatEditText mPasswordField;
    @BindView(R.id.signUpLayout)
    LinearLayout mSignUpLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_activity);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.backIcon)
    public void onBackIconClicked() {
        onBackPressed();
    }

    @OnClick(R.id.signUpLayout)
    public void onSignUpLayoutClicked() {
        String name = mNameField.getText().toString();
        String surname = mSurnameField.getText().toString();
        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();


        if (name.equals("") || surname.equals("") || email.equals("") || password.equals("")) {
            //Treat sign up error
        } else {
            // create new user
        }

//        auth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            goToSignIn();
//                        } else {
//                            Toast.makeText(SignUpActivity.this, task.getException() + " ???",
//                                    Toast.LENGTH_SHORT).show();
//                            //Todo : alert  server error
//
//                        }
//                    }
//                });
    }
}
