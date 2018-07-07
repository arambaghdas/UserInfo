package com.example.test.userinfo.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.test.userinfo.R;
import com.example.test.userinfo.model.network.UserInfoResponse;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserInfoActivity extends Activity {

    @BindView(R.id.profile)
    ImageView profileImageView;

    @BindView(R.id.first_name)
    TextView firstNameTextView;

    @BindView(R.id.last_name)
    TextView lastNameTextView;

    @BindView(R.id.gender)
    TextView genderTextView;

    @BindView(R.id.street)
    TextView streetTextView;

    @BindView(R.id.city)
    TextView cityTextView;

    @BindView(R.id.state)
    TextView stateTextView;

    @BindView(R.id.phone)
    TextView phoneTextView;

    @BindView(R.id.cell)
    TextView cellTextView;

    @BindView(R.id.email)
    TextView emailTextView;

    @BindView(R.id.nat)
    TextView natTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info_layout);

        ButterKnife.bind(this);
        Intent i = getIntent();
        UserInfoResponse.Results userObject = (UserInfoResponse.Results)i.getSerializableExtra("userObject");

        if (userObject != null) {
            Glide.with(this).load(userObject.getPicture().getLargeImage()).into(profileImageView);
            firstNameTextView.setText(userObject.getName().getFirstName());
            lastNameTextView.setText(userObject.getName().getLastName());
            genderTextView.setText(userObject.getGender());
            streetTextView.setText(userObject.getLocation().getStreet());
            cityTextView.setText(userObject.getLocation().getCity());
            stateTextView.setText(userObject.getLocation().getState());
            phoneTextView.setText(userObject.getPhone());
            cellTextView.setText(userObject.getCell());
            emailTextView.setText(userObject.getEmail());
            natTextView.setText(userObject.getNat());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAfterTransition();
    }
}
