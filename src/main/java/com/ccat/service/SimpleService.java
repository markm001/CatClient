package com.ccat.service;


import com.ccat.annotation.*;
import com.ccat.executor.Call;
import com.ccat.response.Response;

/**
 *
 * @deprecated This class is for testing purposes and should be removed in production!
 */
@Deprecated
public interface SimpleService {
    @GET(value = "posts/1")
    Call<Response<String>> getResponse();

    @POST(value = "posts", body = "request")
    Call<Response<String>> createPost(SimpleRequest request);

    @UPDATE(value = "posts", body = "update")
    Call<Response<String>> updatePost(SimpleRequest update);

    @PUT(value = "posts", body = "update")
    Call<Response<String>> putPost(SimpleRequest update);

    @DELETE(value = "posts")
    Call<Response<String>> deletePost(int index);
}
