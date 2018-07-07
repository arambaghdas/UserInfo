package com.example.test.userinfo.ui;

import com.example.test.userinfo.model.network.UserInfoResponse;
import java.util.List;

public interface View {
    void removeLastItem();
    void addProgressItem();
    void showErrorMsg(String msg);
    void displayUsersList(List<UserInfoResponse.Results> usersList);
}
