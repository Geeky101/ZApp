package com.justinmutsito.zapp.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.justinmutsito.zapp.R;
import com.justinmutsito.zapp.preferences.Preferences;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileActivity extends AppCompatActivity {

    @BindView(R.id.userAvatar)
    CircleImageView mUserAvatar;
    @BindView(R.id.userNameField)
    AppCompatEditText mUserNameField;
    @BindView(R.id.saveBtn)
    Button mSaveBtn;

    private String mUserName;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mStorageReference;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mUsersReference;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mUser;
    private Preferences mPreferences;
    private final int RC_PHOTO_PICKER = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mPreferences = new Preferences(this);
        mUserName = mPreferences.getUsername();
        mUserNameField.setText(mUserName);

        mFirebaseStorage = FirebaseStorage.getInstance();
        mStorageReference = mFirebaseStorage.getReference();

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mUsersReference = mFirebaseDatabase.getReference().child("users");

        mFirebaseAuth = FirebaseAuth.getInstance();
        mUser = mFirebaseAuth.getCurrentUser();


        boolean newUser = mUser.getPhotoUrl() == null;

        if (newUser) {
            Toast.makeText(this, "Choose a profile photo", Toast.LENGTH_LONG).show();
            showPhotoPicker();

        } else {
            Picasso.get()
                    .load(mUser.getPhotoUrl().toString())
                    .resize(800, 600)
                    .placeholder(R.drawable.ic_account_circle_whte)
                    .error(R.drawable.ic_account_circle_whte)
                    .into(mUserAvatar);

        }

    }

    private void showPhotoPicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/jpeg");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_PHOTO_PICKER) {
            Uri imageUri = data.getData();

            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();
            StorageReference userImagesRef = storageRef.child("images/" + mUserName + ".jpg");
            userImagesRef.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(mUserName)
                                .setPhotoUri(Uri.parse(downloadUrl.toString()))
                                .build();

                        mUser.updateProfile(profileUpdates)
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        Picasso.get()
                                                .load(mUser.getPhotoUrl())
                                                .resize(800, 600)
                                                .placeholder(R.drawable.ic_account_circle_whte)
                                                .error(R.drawable.ic_account_circle_whte)
                                                .into(mUserAvatar);
                                    }
                                });

                    }

            );


        }

    }

    @OnClick(R.id.userAvatar)
    public void onUserAvatarClicked() {
        showPhotoPicker();
    }

    @OnClick(R.id.saveBtn)
    public void onSaveBtnClicked() {
        mPreferences.setUsername(mUserNameField.getText().toString());
        goToMain();
    }
    private void goToMain() {
        Intent intent = new Intent(UserProfileActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
