package com.example.test.userinfo.model;

import android.content.Context;

import com.example.test.userinfo.R;
import com.example.test.userinfo.model.db.DbHelper;
import com.example.test.userinfo.model.db.DbOps;
import com.example.test.userinfo.model.db.User;
import com.example.test.userinfo.model.network.CheckNetwork;
import com.example.test.userinfo.model.network.UserInfoResponse;
import com.example.test.userinfo.model.network.NetworkHelper;
import com.example.test.userinfo.model.network.NetworkOps;

import java.util.List;

public class AppDataManager implements DbOps, NetworkOps {

    private DbHelper dbRepository;
    private NetworkHelper networkRepository;
    private ModelOps modelOps;
    private Context mContext;

    public AppDataManager(ModelOps modelOps, Context mContext) {
        this.modelOps = modelOps;
        this.mContext = mContext;
        dbRepository = new DbHelper(this, mContext);
        networkRepository = new NetworkHelper(this);
    }

    public void getUsers(boolean loadMore) {
        boolean isNetworkConnected = CheckNetwork.isNetworkConnected(mContext);
        if (isNetworkConnected)
            networkRepository.getUsersFromApi();
        else {
            if (!loadMore) {
                dbRepository.getUsersFromDb();
                modelOps.onError(mContext.getString(R.string.offline));
            } else {
                modelOps.onError(mContext.getString(R.string.no_network));
            }
        }
    }

    @Override
    public void onGetDbError(String msg) {
        modelOps.onError(msg);
    }

    @Override
    public void onReceiveUsersFromDb(List<UserInfoResponse.Results> usersList) {
        modelOps.onReceiveUsersList(usersList);
    }

    @Override
    public void onGetNetworkError(String msg) {
        modelOps.onError(msg);
    }

    @Override
    public void onReceiveUsersFromApi(List<UserInfoResponse.Results> usersList) {
        for (UserInfoResponse.Results userInfo : usersList) {
             User user = new User();
             user.setFirstName(userInfo.getName().getFirstName());
             user.setLastName(userInfo.getName().getLastName());
             user.setTitle(userInfo.getName().getTitle());
             user.setGender(userInfo.getGender());
             user.setPhone(userInfo.getPhone());
             user.setCell(userInfo.getCell());
             user.setNat(userInfo.getNat());
             user.setEmail(userInfo.getEmail());
             user.setStreet(userInfo.getLocation().getStreet());
             user.setCity(userInfo.getLocation().getCity());
             user.setState(userInfo.getLocation().getState());
             user.setLargeImage(userInfo.getPicture().getLargeImage());
             dbRepository.onSaveUserInfoInRoomDb(user);
        }
        modelOps.onReceiveUsersList(usersList);
    }
}
