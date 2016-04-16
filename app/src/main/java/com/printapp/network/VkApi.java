package com.printapp.network;

import com.printapp.models.SearchGroups;
import com.printapp.models.SearchPhotos;
import com.printapp.models.SearchUsers;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface VkApi {
    @GET("/method/users.search")
    Call<SearchUsers> searchUsers(
            @Query("q") String query,
            @Query("v") String version,
            @Query("access_token") String token,
            @Query("fields") String fields
    );
    @GET("/method/groups.search")
    Call<SearchGroups> searchGroups(
            @Query("q") String query,
            @Query("v") String version,
            @Query("access_token") String token
    );
    @GET("/method/photos.getAll")
    Call<SearchPhotos> getPhotos(
            @Query("count") int count,
            @Query("owner_id") String owner_id,
            @Query("v") String version,
            @Query("access_token") String token
    );
    @GET("/method/photos.search")
    Call<SearchPhotos> searchPhotos(
            @Query("q") String query,
            @Query("v") String version,
            @Query("access_token") String token
    );
}
