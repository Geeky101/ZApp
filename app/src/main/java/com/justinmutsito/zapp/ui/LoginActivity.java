package com.justinmutsito.zapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.justinmutsito.zapp.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.emailField)
    AppCompatEditText mEmailField;
    @BindView(R.id.passwordField)
    AppCompatEditText mPasswordField;
    @BindView(R.id.loginLayout)
    LinearLayout mLoginLayout;
    @BindView(R.id.noAccountLabel)
    TextView mNoAccountLabel;
    @BindView(R.id.signUpLabel)
    TextView mSignUpLabel;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();


    }


    @OnClick(R.id.loginLayout)
    public void onLoginLayoutClicked() {

        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();

        boolean validEmail = checkEmail(email);
        boolean validPassword = checkPassword(password);

        if (validEmail && validPassword) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                goToMain();
                            } else {
                                Toast.makeText(LoginActivity.this, "Oops something went wrong, check your email and password", Toast.LENGTH_SHORT).show();
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

        }


    }

    private boolean checkEmail(String email) {
        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher mat = pattern.matcher(email);

        return mat.matches();

    }

    private boolean checkPassword(String password) {
        if (password.length() < 8) {
            return false;
        } else {
            char c;
            int count = 0;
            for (int i = 0; i < password.length(); i++) {
                c = password.charAt(i);
                if (!Character.isLetterOrDigit(c)) {
                    return false;
                } else if (Character.isDigit(c)) {
                    count++;
                }
            }
            if (count < 2) {
                return false;
            }
        }
        return true;
    }

    private void goToMain() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @OnClick({R.id.signUpLabel,R.id.noAccountLabel})
    public void onsignUpClicked() {
        startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
    }
}
