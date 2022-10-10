package com.example.self.ocl.retrofit;

import com.google.gson.JsonObject;
import com.open.oclweb.retrofit.entity.Blog;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

/**
 * @author szy
 * @version 1.0
 * @description
 * @date 2021-11-10 16:16:33
 */

@RestController
public class RetrofitController {

    @GetMapping("getinfo/{id}")
    public String getMeg(@PathParam("id") String id) {
        System.out.println(id);
        JsonObject js = new JsonObject();
        js.addProperty("title", "哇呀呀");
//
        JsonObject res = new JsonObject();
        res.add("data", js);
        res.addProperty("code", 1);
        res.addProperty("msg", "返回结果");
        return res.toString();
    }


    @GetMapping("headers")
    public String getMeg(boolean showAll) {
        System.out.println(showAll);
        return "you get it! headers:" + showAll;
    }

    @PostMapping("blog")
    public String blog(@RequestBody Blog blog) {
        System.out.println(blog);

        JsonObject res = new JsonObject();
        res.addProperty("code", 1);
        res.addProperty("msg", "返回结果");
        return res.toString();
    }
}
