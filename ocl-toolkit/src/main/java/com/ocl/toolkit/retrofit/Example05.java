
package com.ocl.toolkit.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * [Retrofit注解详解 之 FormUrlEncoded/Field/FieldMap注解]源码
 */
public class Example05 {
    public interface BlogService {
        /**
         * 当GET、POST...HTTP等方法中没有设置Url时，则必须使用 {@link Url}提供
         * 对于Query和QueryMap，如果不是String（或Map的第二个泛型参数不是String）时
         * 会被默认会调用toString转换成String类型
         * Url支持的类型有 okhttp3.HttpUrl, String, java.net.URI, android.net.Uri
         * {@link retrofit2.http.QueryMap} 用法和{@link retrofit2.http.FieldMap} 用法一样，不再说明
         */
        @GET //当有URL注解时，这里的URL就省略了
        Call<ResponseBody> testUrlAndQuery(@Url String url, @Query("showAll") boolean showAll);

    }

    public static void main(String[] args) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:30001/")
                .build();

        BlogService service = retrofit.create(BlogService.class);

        //演示 @Headers 和 @Header
        Call<ResponseBody> call1 = service.testUrlAndQuery("headers",false);
        ResponseBodyPrinter.printResponseBody(call1);
    }
}
