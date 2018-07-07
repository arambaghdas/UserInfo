package com.example.test.userinfo.model.network;

import java.util.List;

public interface NetworkOps {
    void onGetNetworkError(String msg);
    void onReceiveUsersFromApi(List<UserInfoResponse.Results> usersList);
}
