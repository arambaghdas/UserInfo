package com.example.test.userinfo.model;

import com.example.test.userinfo.model.network.UserInfoResponse;
import java.util.List;

public interface ModelOps {
    void onError(String msg);
    void onReceiveUsersList(List<UserInfoResponse.Results> usersList);
}
