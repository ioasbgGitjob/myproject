package com.example.self.ocl.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

/**
 * [Retrofit注解详解 之 FormUrlEncoded/Field/FieldMap注解]源码
 */
public class Example04 {
    public interface BlogService {
        @GET("/headers?showAll=true")
        @Headers({"CustomHeader1: customHeaderValue1", "CustomHeader2: customHeaderValue2"})
        Call<ResponseBody> testHeader(@Header("CustomHeader3") String customHeaderValue3);

    }

    public static void main(String[] args) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:30001/")
                .build();

        BlogService service = retrofit.create(BlogService.class);

        //演示 @Headers 和 @Header
        Call<ResponseBody> call1 = service.testHeader("ikidou");
        ResponseBodyPrinter.printResponseBody(call1);
    }
}
