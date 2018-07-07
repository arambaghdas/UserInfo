package com.example.test.userinfo.model.network;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class NetworkHelper {
    private NetworkOps networkOps;
    public NetworkHelper(NetworkOps networkOps) {
        this.networkOps = networkOps;
    }

    public void getUsersFromApi() {
        UserInfoService userInfoService = NetworkClient.getRetrofit().create(UserInfoService.class);
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(userInfoService.getUsersData(10).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response-> {
                    if (response != null) {
                        networkOps.onReceiveUsersFromApi(response.results);
                    }
                }, error -> {
                    networkOps.onGetNetworkError(error.getMessage());
                }));
    }
}
