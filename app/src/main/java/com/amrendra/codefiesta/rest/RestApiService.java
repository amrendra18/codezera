package com.amrendra.codefiesta.rest;

import com.amrendra.codefiesta.BuildConfig;
import com.amrendra.codefiesta.model.Contest;
import com.amrendra.codefiesta.model.Website;

import retrofit.Call;
import retrofit.http.GET;

/**
 * Created by Amrendra Kumar on 06/04/16.
 */
public interface RestApiService {

    String auth = "?username=" + BuildConfig.API_USERNAME +
            "&api_key=" + BuildConfig.API_KEY;

    @GET("/api/v1/contest/" + auth)
    Call<Contest.Response> getContestsList();

    @GET("/api/v1/contest/{id}/" + auth)
    Call<Contest.Response> getContestsById();

    @GET("/api/v1/resource/" + auth)
    Call<Website.Response> getResourceList();

    @GET("/api/v1/resource/{id}/" + auth)
    Call<Website.Response> getResourceById();


}
