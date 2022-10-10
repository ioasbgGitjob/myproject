package com.self.rabbit.common.serializer.impl;

import com.self.rabbit.api.Message;
import com.self.rabbit.common.serializer.Serializer;
import com.self.rabbit.common.serializer.SerializerFactory;

/**
 * @author szy
 */

public class JacksonSerializerFactory implements SerializerFactory {

    public static final JacksonSerializerFactory INSTANCE = new JacksonSerializerFactory();


    @Override
    public Serializer create() {
        return JacksonSerializer.createParametricType(Message.class);
    }
}
