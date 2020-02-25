package com.electreca.tech.webservice;

import com.electreca.tech.model.products.AddProductRequestModel;
import com.electreca.tech.model.products.BaseResponse;
import com.electreca.tech.model.products.ProductFromIDModel;
import com.electreca.tech.model.products.ProductResponseDataModel;
import com.electreca.tech.model.products.UpdateProductModel;
import com.electreca.tech.model.specificuser.SpecificUserResponseModel;
import com.electreca.tech.model.users.ChangePwdRequestModel;
import com.electreca.tech.model.users.LoginRequestModel;
import com.electreca.tech.model.users.LoginResponseDataModel;
import com.electreca.tech.model.users.RegisterRequestModel;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {
    String LOGIN = "user/login";
    String PRODUCT = "product";
    String SIGNUP = "user/signup";
    String PRODUCT_UPDATE = "product/updatProduct";
    String CHANGE_PWD = "user/changepassword";

    @POST(LOGIN)
    Call<LoginResponseDataModel> loginCall(@HeaderMap Map<String, String> headers, @Body LoginRequestModel loginRequestModel);

    @POST(SIGNUP)
    Call<BaseResponse> registerCall(@HeaderMap Map<String, String> headers, @Body RegisterRequestModel registerRequestModel);

    @GET(PRODUCT)
    Call<ProductResponseDataModel> fetchProduct(@HeaderMap Map<String, String> headers);

    @GET(PRODUCT + "/{productID}")
    Call<ProductFromIDModel> fetchProductFromID(@HeaderMap Map<String, String> headers, @Path("productID") int productID);

    @POST(PRODUCT_UPDATE + "/{productID}")
    Call<BaseResponse> callProductUpdate(@HeaderMap Map<String, String> headers, @Path("productID") int productID, @Body UpdateProductModel updateProductModel);

    @POST(PRODUCT)
    Call<BaseResponse> addNewProduct(@HeaderMap Map<String, String> headers, @Body AddProductRequestModel addProductRequestModel);

    @GET("USER" + "/{userID}")
    Call<SpecificUserResponseModel> fetchUserFromID(@HeaderMap Map<String, String> headers, @Path("userID") int userID);

    @POST(CHANGE_PWD)
    Call<BaseResponse> callChangePwd(@HeaderMap Map<String, String> headers, @Body ChangePwdRequestModel changePwdRequestModel);
}
