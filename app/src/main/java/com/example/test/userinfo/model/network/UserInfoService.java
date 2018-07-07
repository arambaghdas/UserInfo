package com.example.test.userinfo.model.network;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserInfoService {
    @GET(".")
    Observable<UserInfoResponse> getUsersData(@Query("results") int count );
}
