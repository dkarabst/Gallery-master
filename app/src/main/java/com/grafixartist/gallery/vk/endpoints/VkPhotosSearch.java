package com.grafixartist.gallery.vk.endpoints;


import com.grafixartist.gallery.vk.model.VkResponse;

import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Dmitriy on 10/13/2015.
 */
public class VkPhotosSearch extends BaseEndpointVk {
    private static interface MediaService {

        @GET("/photos.search")
        public VkResponse search(
                @Query("lat") Double latitude,
                @Query("long") Double longitude,
                @Query("count") Integer count,
                @Query("radius") Integer radius,
                @Query("v") Double version);

    }

    private final MediaService mediaService;

    public VkPhotosSearch(final RestAdapter.LogLevel logLevel) {
        super(logLevel);
        final RestAdapter restAdapter = new RestAdapter.Builder().setLogLevel(logLevel).setEndpoint(BASE_URL).build();
        mediaService = restAdapter.create(MediaService.class);
    }


    public VkResponse search(final Double latitude, final Double longitude, final Integer count, final Integer radius, final Double version) {
        return mediaService.search(latitude, longitude, count, radius, version);
    }



}
