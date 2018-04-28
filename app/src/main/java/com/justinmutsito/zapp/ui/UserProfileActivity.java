package com.justinmutsito.zapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.widget.Button;

import com.justinmutsito.zapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileActivity extends AppCompatActivity {

    @BindView(R.id.userAvatar)
    CircleImageView mUserAvatar;
    @BindView(R.id.userNameField)
    AppCompatEditText mUserNameField;
    @BindView(R.id.passwordField)
    AppCompatEditText mPasswordField;
    @BindView(R.id.saveBtn)
    Button mSaveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.userAvatar)
    public void onMUserAvatarClicked() {
    }

    @OnClick(R.id.saveBtn)
    public void onMSaveBtnClicked() {
    }
}
