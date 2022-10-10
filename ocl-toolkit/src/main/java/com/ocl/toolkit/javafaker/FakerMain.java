package com.ocl.toolkit.javafaker;

import com.ocl.toolkit.User;
import com.github.javafaker.Faker;
import org.junit.Test;

import java.util.Locale;
import java.util.stream.Stream;

/**
 * @author szy
 * @version 1.0
 * @description
 * @date 2021-11-11 17:34:36
 */

public class FakerMain {

    public static Faker FAKER = new Faker(Locale.CHINA);

    @Test
    public void getUser() {

        int num = 12;// 生成12个用户

        Stream.generate(() -> User.builder()
                        .name(FAKER.name().fullName())
                        .address(FAKER.address().fullAddress()))
                .limit(num)
                .forEach(System.out::println);
    }


}
