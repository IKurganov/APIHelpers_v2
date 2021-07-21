package ru.otus.APIHelpers;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import ru.otus.APIHelpers.pojo.*;

public interface APIInterface {
    @GET("users/2")
    Call <User> getUserById();

    @GET("unknown/2")
    Call<Resource> getResource();

    @POST("users")
    Call<CreateUserResponse> createUser(@Body CreateUserRequest body);

}
