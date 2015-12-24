package com.grafixartist.gallery.retrogram.endpoints;


import com.grafixartist.gallery.retrogram.model.*;
import retrofit.RestAdapter;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

public class LikesEndpoint extends BaseEndpoint {

    private static interface LikesService {

        @GET("/media/{media_id}/likes")
        public Likes getLikes(
                @Path("media_id") String mediaId,
                @Query("access_token") String accessToken);

        @POST("/media/{media_id}/likes")
        public LikeResponse postLike(
                @Path("media_id") String mediaId,
                @Query("access_token") String accessToken);

        @DELETE("/media/{media_id}/likes")
        public DeleteLikeResponse deleteLike(
                @Path("media_id") String mediaId,
                @Query("access_token") String accessToken);

    }

    private final LikesService likesService;

    public LikesEndpoint(final String accessToken,final RestAdapter.LogLevel logLevel) {
        super(accessToken, logLevel);
        final RestAdapter restAdapter = new RestAdapter.Builder().setLogLevel(logLevel).setEndpoint(BASE_URL).build();
        likesService = restAdapter.create(LikesService.class);
    }

    public Likes getLikes(final String mediaId) {
        return likesService.getLikes(mediaId, accessToken);
    }

    public boolean like(final String mediaId) {
        return likesService.postLike(mediaId, accessToken).isSuccessfull();
    }

    public boolean unlike(final String mediaId) {
        return likesService.deleteLike(mediaId, accessToken).isSuccessfull();
    }

}
