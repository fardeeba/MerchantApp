package com.example.merchantapp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface CallToAPI {

        @POST("api/v1/user/login")
    Call<ResponseOfLogin> userLogin(@Body RequestToLogin requestToLogin);

}
