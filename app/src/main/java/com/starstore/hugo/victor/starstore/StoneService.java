package com.starstore.hugo.victor.starstore;

import com.starstore.hugo.victor.starstore.models.ProductsCatalog;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Victor Hugo on 17/01/2018.
 */

public interface StoneService {

    String BASE_URL = "https://private-841202-stonechallenge.apiary-mock.com/";

    @GET("products")
    Call<List<ProductsCatalog>> listProducts();
}