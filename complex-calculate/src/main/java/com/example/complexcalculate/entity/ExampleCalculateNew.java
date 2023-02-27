package com.example.complexcalculate.entity;

import com.ismail.mxreflection.annotations.Arg;
import com.ismail.mxreflection.annotations.Expression;
import lombok.Data;

/**
 * @author szy
 * @version 1.0
 * @date 2023-02-26 19:20:59
 * @description service改的是ExampleCalculate对象, 所以该对象不受影响
 */
@Data
public class ExampleCalculateNew {

    @Arg("f1")

    String field1;

    @Arg("f2")
    int field2;

    //    @Expression("f1 * sin(f2) * log2(f1 + f2) + der(cos(f1), f1) * pi + int(tan(f2), f2, 0, e)")
    @Expression(value = "f1+f2")
    double field3;

}

