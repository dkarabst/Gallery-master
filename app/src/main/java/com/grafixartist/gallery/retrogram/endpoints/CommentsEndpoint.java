package com.grafixartist.gallery.retrogram.endpoints;


import com.grafixartist.gallery.retrogram.model.*;

import retrofit.RestAdapter;
import retrofit.http.DELETE;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

public class CommentsEndpoint extends BaseEndpoint {

    private static interface CommentsService {

        @GET("/media/{media_id}/comments")
        public Comments getComments(
                @Path("media_id") String mediaId,
                @Query("access_token") String accessToken);

        @FormUrlEncoded
        @POST("/media/{media_id}/comments")
        public Comments postComment(
                @Path("media_id") String mediaId,
                @Field("text") String text,
                @Query("access_token") String accessToken
        );

        @DELETE("/media/{media_id}/comments/{comment_id}")
        public DeleteCommentResponse delete(
                @Path("media_id") String mediaId,
                @Path("comment_id") String commentId,
                @Query("access_token") String accessToken
        );

    }

    private final CommentsService commentsService;

    public CommentsEndpoint(final String accessToken, final RestAdapter.LogLevel logLevel) {
        super(accessToken, logLevel);
        final RestAdapter restAdapter = new RestAdapter.Builder().setLogLevel(logLevel).setEndpoint(BASE_URL).build();
        commentsService = restAdapter.create(CommentsService.class);
    }

    public Comments getComments(final String mediaId) {
        return commentsService.getComments(mediaId, accessToken);
    }

    public Comments comment(final String mediaId, final String text) {
        return commentsService.postComment(mediaId, text, accessToken);
    }

    public boolean delete(final String mediaId, final String commentId) {
        return commentsService.delete(mediaId, commentId, accessToken).isSuccessfull();
    }

}
