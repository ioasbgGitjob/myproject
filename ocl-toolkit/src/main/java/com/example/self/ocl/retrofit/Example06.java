
package com.example.self.ocl.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.open.oclweb.retrofit.entity.Blog;
import com.open.oclweb.retrofit.entity.Result;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * [Retrofit Converter 反序化]源码
 */
public class Example06 {
    public interface BlogService {
        @GET("getinfo/{id}")
        Call<Result<Blog>> getBlog(@Path("id") int id);
    }

    public static void main(String[] args) {
        Gson gson = new GsonBuilder()
                //配置你的Gson
                .setDateFormat("yyyy-MM-dd hh:mm:ss")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:30001/")
                //可以接收自定义的Gson，当然也可以不传
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        BlogService service = retrofit.create(BlogService.class);
        Call<Result<Blog>> call = service.getBlog(2);
        call.enqueue(new Callback<Result<Blog>>() {
            @Override
            public void onResponse(Call<Result<Blog>> call, Response<Result<Blog>> response) {
                // 已经转换为想要的类型了
                Result<Blog> result = response.body();
                System.out.println(result);
            }

            @Override
            public void onFailure(Call<Result<Blog>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
