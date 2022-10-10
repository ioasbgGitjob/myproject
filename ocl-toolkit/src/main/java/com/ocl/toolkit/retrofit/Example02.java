
package com.ocl.toolkit.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.HTTP;
import retrofit2.http.Path;

/**
 * [Retrofit注解详解 之 HTTP注解]源码
 */
public class Example02 {
    public interface BlogService {
        /**
         * method 表示请求的方法，区分大小写，retrofit 不会做处理
         * path表示路径
         * hasBody表示是否有请求体
         */
        @HTTP(method = "GET", path = "getinfo/{id}", hasBody = false)
        Call<ResponseBody> getBlog(@Path("id") int id);
    }

    public static void main(String[] args) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:30001/")
                .build();

        BlogService service = retrofit.create(BlogService.class);
        Call<ResponseBody> call = service.getBlog(2);
        ResponseBodyPrinter.printResponseBody(call);
    }
}
