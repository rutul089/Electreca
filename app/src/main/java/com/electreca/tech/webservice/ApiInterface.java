package com.electreca.tech.webservice;

import com.electreca.tech.model.products.ProductResponseDataModel;
import com.electreca.tech.model.users.LoginRequestModel;
import com.electreca.tech.model.users.LoginResponseDataModel;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

public interface ApiInterface {
    String LOGIN = "user/login";
    String PRODUCT = "product";
    String SIGNUP = "user/signup";

    @POST(LOGIN)
    Call<LoginResponseDataModel> loginCall(@HeaderMap Map<String, String> headers, @Body LoginRequestModel loginRequestModel);
//
//    @POST(SIGNUP)
//    Call<BaseResponse> registerCall(@HeaderMap Map<String, String> headers, @Body RegisterRequestModel registerRequestModel);

    @GET(PRODUCT)
    Call<ProductResponseDataModel> fetchProduct(@HeaderMap Map<String, String> headers);

//    @POST(PRODUCT)
//    Call<BaseResponse> addNewProduct(@HeaderMap Map<String, String> headers, @Body AddProductRequestModel addProductRequestModel);
}
