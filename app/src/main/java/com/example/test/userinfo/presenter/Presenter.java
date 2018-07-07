package com.example.test.userinfo.presenter;

import android.content.Context;
import com.example.test.userinfo.model.AppDataManager;
import com.example.test.userinfo.model.ModelOps;
import com.example.test.userinfo.model.network.UserInfoResponse;
import com.example.test.userinfo.ui.View;
import java.util.List;

public class Presenter implements ModelOps {

    private View view;
    private AppDataManager appDataManager;
    private boolean loadMore = false;

    public Presenter(View view, Context mContext) {
        this.view = view;
        appDataManager = new AppDataManager(this, mContext);
    }
    public void getUsers(boolean loadMore) {
        this.loadMore = loadMore;
        if (loadMore)
            view.addProgressItem();
        appDataManager.getUsers(loadMore);
    }

    @Override
    public void onError(String msg) {
        removeLastItem();
        view.showErrorMsg(msg);
    }

    @Override
    public void onReceiveUsersList(List<UserInfoResponse.Results> usersList) {
        removeLastItem();
        view.displayUsersList(usersList);
    }

    public int getEndOfList(List<UserInfoResponse.Results> list){
        if (list != null)
            return list.size();
        return -1;
    }
    private void removeLastItem() {
        if (loadMore) {
            loadMore = false;
            view.removeLastItem();
        }

    }
}
