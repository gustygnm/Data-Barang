package com.gnm.myapplication.api;

import com.gnm.myapplication.model.Result;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("/barang/tampilBrgAll.php")
    Call<Result> getBarang();

    @FormUrlEncoded
    @POST("/barang/tambahBrg.php")
    Call<Result> insertBarang(@Field("nama") String nama,
                            @Field("stok") int stok,
                            @Field("image") String image);

    @FormUrlEncoded
    @POST("/barang/updateBrg.php")
    Call<Result> updateBarang(@Field("id") int id,
                           @Field("nama") String nama,
                           @Field("stok") int stok,
                           @Field("image") String image);

    @FormUrlEncoded
    @POST("/barang/hapusBrg.php")
    Call<Result> deleteBarang(@Field("id") int id);
}