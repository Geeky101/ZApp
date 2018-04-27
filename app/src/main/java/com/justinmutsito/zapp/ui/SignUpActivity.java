package com.justinmutsito.zapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.justinmutsito.zapp.R;
import com.justinmutsito.zapp.preferences.Preferences;
import com.justinmutsito.zapp.util.Verify;

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
        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        final String name = mNameField.getText().toString();
        final String surname = mSurnameField.getText().toString();
        String email = mEmailField.getText().toString().trim();
        String password = mPasswordField.getText().toString().trim();

        boolean validEmail = Verify.checkEmail(email);
        boolean validPassword = Verify.checkPassword(password);
        boolean validUsername = Verify.checkUsername(name,surname);


        if (validEmail && validPassword && validUsername) {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String userName = name + " " + surname;
                                Preferences preferences = new Preferences(SignUpActivity.this);
                                preferences.setUsername(userName);
                                goToMain();
                            } else {
                                Toast.makeText(SignUpActivity.this, "Oops something went wrong, check your email and password", Toast.LENGTH_SHORT).show();
                                //Todo : alert  server error

                            }
                        }
                    });
        } else {
            if (!validEmail) {
                Toast.makeText(this, "Enter a valid email", Toast.LENGTH_SHORT).show();
            }
            if (!validPassword) {
                Toast.makeText(this, "Enter a valid 8 letter password", Toast.LENGTH_SHORT).show();
            }
            if (!validUsername) {
                Toast.makeText(this, "Enter a valid name and surname", Toast.LENGTH_SHORT).show();
            }
        }


    }



    private void goToMain() {
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}
