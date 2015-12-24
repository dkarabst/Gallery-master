package com.grafixartist.gallery.vk;


import com.grafixartist.gallery.vk.endpoints.VkPhotosSearch;

import retrofit.RestAdapter;

public class VK {

    private final RestAdapter.LogLevel logLevel;

    private VkPhotosSearch usersEndpoint;


    public VK( final RestAdapter.LogLevel logLevel) {
        this.logLevel = logLevel;
    }

    public VkPhotosSearch getUsersEndpoint() {
        if (usersEndpoint == null) {
            usersEndpoint = new VkPhotosSearch( logLevel);
        }
        return usersEndpoint;
    }


}
