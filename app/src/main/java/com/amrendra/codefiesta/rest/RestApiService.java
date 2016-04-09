package com.amrendra.codefiesta.rest;

import com.amrendra.codefiesta.model.Contest;
import com.amrendra.codefiesta.model.Website;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by Amrendra Kumar on 06/04/16.
 */
public interface RestApiService {

    @GET("/api/v1/contest/")
    Observable<Contest.Response> getContestsList(
            @Query("limit") int limit,
            @Query("end__gt") String date,
            @Query("order_by") String orderBy,
            @Query("username") String userName,
            @Query("api_key") String userKey
    );

    @GET("/api/v1/contest/{id}/")
    Observable<Contest.Response> getContestsById(
            @Path("id") int id,
            @Query("username") String userName,
            @Query("api_key") String userKey
    );

    @GET("/api/v1/resource/")
    Observable<Website.Response> getResourceList(
            @Query("username") String userName,
            @Query("api_key") String userKey
    );

    @GET("/api/v1/resource/{id}/")
    Observable<Website.Response> getResourceById(
            @Path("id") int id,
            @Query("username") String userName,
            @Query("api_key") String userKey
    );

}
