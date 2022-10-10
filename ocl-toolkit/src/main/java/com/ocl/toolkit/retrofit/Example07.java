
package com.ocl.toolkit.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ocl.toolkit.retrofit.entity.Blog;
import com.ocl.toolkit.retrofit.entity.Result;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * [Retrofit Converter 序列化]源码
 */
public class Example07 {
    public interface BlogService {
        @POST("blog")
        Call<Result<Blog>> createBlog(@Body Blog blog);
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
        Blog blog = new Blog();
        blog.content = "新建的Blog";
        blog.title = "测试";
        blog.author = "怪盗kidou";
        Call<Result<Blog>> call = service.createBlog(blog);
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
