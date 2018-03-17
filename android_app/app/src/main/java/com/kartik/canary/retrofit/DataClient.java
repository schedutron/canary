package com.kartik.canary.retrofit;

import java.util.List;

import retrofit2.*;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by kartik on Sat, 17/3/18 in app.
 */

public interface DataClient {

    @GET("/")
    void checkText(@Path("text") String text, Callback<List<SampleData>> callback);
}
