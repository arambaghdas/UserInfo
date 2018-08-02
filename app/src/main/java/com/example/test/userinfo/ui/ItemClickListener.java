package com.example.test.userinfo.ui;

import android.widget.ImageView;

import com.example.test.userinfo.model.network.UserInfoResponse;

public interface ItemClickListener {
    void onItemClick(UserInfoResponse.Results userInfo, ImageView imageView);
}
