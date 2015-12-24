package com.grafixartist.gallery.vk.endpoints;

import retrofit.RestAdapter;

public abstract class BaseEndpointVk {

    protected static final String BASE_URL = "https://api.vk.com/method";

    protected final RestAdapter.LogLevel logLevel;

    protected BaseEndpointVk(final RestAdapter.LogLevel logLevel) {
        this.logLevel = logLevel;
    }

}
