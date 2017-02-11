package com.zappos.ilovezappos;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by user on 2/5/2017.
 */


public interface GetProductAPI {
    String BASE_URL="https://api.zappos.com/";

    @GET("Search?")
    Call<Product> getProductDetails(@Query("term") String term ,@Query("key") String key);

    class Factory{
        private static GetProductAPI service;
        public static GetProductAPI getInstance() {
            if(service==null){

                Retrofit retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(BASE_URL)
                        .build();

                service = retrofit.create(GetProductAPI.class);
                return service;
            }
            else{
                return service;
            }
        }
    }

}
