package com.example.test.userinfo.model.db;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.util.Log;
import com.example.test.userinfo.model.network.UserInfoResponse;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class DbHelper {
    private AppDatabase db;
    private DbOps dpOps;
    private String TAG = "DbMessages";

    public DbHelper(DbOps dpOps, Context mContext) {
        this.dpOps = dpOps;
        db = Room.databaseBuilder(mContext,
                AppDatabase.class, "database-users").build();
    }

    public void onSaveUserInfoInRoomDb(User user) {
        CompositeDisposable mDisposable = new CompositeDisposable();
        mDisposable.add(Single.fromCallable(() -> db.userDao().insertUserInfo(user)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(id -> {
        } ,throwable -> Log.e(TAG, "Error msg", throwable)));
    }

    public void getUsersFromDb() {
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(db.userDao().getAllUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(users-> {
                    if (users != null) {
                        List<UserInfoResponse.Results> usersList = new ArrayList<>();
                           for (User user : users) {
                            UserInfoResponse userInfoResponse = new  UserInfoResponse();
                            UserInfoResponse.Results result = userInfoResponse.getResults(user);
                            usersList.add(result);
                        }
                        dpOps.onReceiveUsersFromDb(usersList);
                    }
                }, error -> {
                    dpOps.onGetDbError(error.getMessage());
                }));
    }

}
