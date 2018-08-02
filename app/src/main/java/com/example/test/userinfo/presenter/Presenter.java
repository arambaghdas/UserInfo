package com.example.test.userinfo.presenter;

import android.content.Context;
import com.example.test.userinfo.model.AppDataManager;
import com.example.test.userinfo.model.ModelOps;
import com.example.test.userinfo.model.network.UserInfoResponse;
import com.example.test.userinfo.ui.View;
import java.util.ArrayList;
import java.util.List;

public class Presenter implements ModelOps {

    private View view;
    private AppDataManager appDataManager;
    private boolean loadMore = false;
    private List<UserInfoResponse.Results> usersListAllItems;

    public Presenter(View view, Context mContext) {
        this.view = view;
        appDataManager = new AppDataManager(this, mContext);
        usersListAllItems = new ArrayList<>();
    }
    public void getUsers(boolean loadMore) {
        this.loadMore = loadMore;
        if (loadMore) {
            int pos = getEndOfList();
            usersListAllItems.add(pos, null);
            view.notifyItemChanged(pos);
        } else {
            view.showProgressBar();
        }
        appDataManager.getUsers(loadMore);
    }

    @Override
    public void onError(String msg) {
        removeProgressItem();
        view.hideProgressBar();
        view.showErrorMsg(msg);
    }

    @Override
    public void onReceiveUsersList(List<UserInfoResponse.Results> usersList) {
        removeProgressItem();
        int position = getEndOfList();
        view.moveToPosition(position);
        for (UserInfoResponse.Results obj : usersList)
            usersListAllItems.add(getEndOfList(), obj);
        view.hideProgressBar();
        view.displayUsersList(usersListAllItems);
    }

    private void removeProgressItem() {
        if (loadMore) {
            loadMore = false;
            int pos = usersListAllItems.size();
            usersListAllItems.remove(pos - 1);
            view.notifyItemChanged(pos);
        }
    }
    private int getEndOfList(){
        if (usersListAllItems != null)
            return usersListAllItems.size();
        return -1;
    }
}
