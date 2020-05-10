package com.example.api_rest_call;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AutoService {


    String API_ROUTE= "app/api/read";
    @GET(API_ROUTE)
    Call<List<Auto>> getAutos();



    String API_ROUTE_GET_ITEM= "app/api/read/{id}";
    @GET(API_ROUTE_GET_ITEM)
    Call<Auto> getAuto(@Path("id") String id);



    String API_ROUTE_SAVE_ITEM = "app/api/update/{id}";
    @FormUrlEncoded
    @PUT(API_ROUTE_SAVE_ITEM)
    Call<Void> saveAuto(
            @Path("id") String id,
            @Field("marca") String marca,
            @Field("modelo") String modelo
    );


    String API_ROUTE_ADD_ITEM = "app/api/create";
    @POST(API_ROUTE_ADD_ITEM)
    Call<Void> addAuto(@Body Auto auto);



    String API_ROUTE_DELETE_ITEM = "app/api/delete/{id}";
    @DELETE(API_ROUTE_DELETE_ITEM)
    Call<Void> deleteAuto (@Path("id") String id);
}
