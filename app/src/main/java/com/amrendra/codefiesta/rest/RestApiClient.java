package com.amrendra.codefiesta.rest;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Amrendra Kumar on 06/04/16.
 */
public class RestApiClient {
    public static final String API_BASE_URL = "http://clist.by:80/";

    private static RestApiService restApiService = null;

    private RestApiClient() {
    }

    public static RestApiService getInstance() {
        if (restApiService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            restApiService = retrofit.create(RestApiService.class);
        }
        return restApiService;
    }
}
