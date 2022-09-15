package com.ibm.meuapp.data.remote;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;



public interface RegisterService {

    @POST("register-users/register/")
    Call<RegisterResponse> saveUsers(@Body RegisterRequest Request);


    @POST("register-users/login/")
    Call<RegisterResponse> loginUsers(@Body UserLoginObject userLoginObject);
}
