package com.example.test.userinfo.model.db;

import com.example.test.userinfo.model.network.UserInfoResponse;
import java.util.List;

public interface DbOps {
    void onGetDbError(String msg);
    void onReceiveUsersFromDb(List<UserInfoResponse.Results> usersList);
}
