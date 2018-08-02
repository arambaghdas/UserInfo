package com.example.test.userinfo.ui;

import com.example.test.userinfo.model.network.UserInfoResponse;
import java.util.List;

public interface View {
    void notifyItemChanged(int pos);
    void showErrorMsg(String msg);
    void moveToPosition(int pos);
    void displayUsersList(List<UserInfoResponse.Results> usersList);
    void showProgressBar();
    void hideProgressBar();
}
