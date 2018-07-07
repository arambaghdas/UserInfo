package com.example.test.userinfo.model.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import java.util.List;
import io.reactivex.Flowable;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    Flowable<List<User>> getAllUsers();

    @Insert
    Long insertUserInfo(User user);
}