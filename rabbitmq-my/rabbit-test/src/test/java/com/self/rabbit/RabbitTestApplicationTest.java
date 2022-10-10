package com.self.rabbit;

import com.self.rabbit.api.Message;
import com.self.rabbit.api.MessageType;
import com.self.rabbit.producer.broker.MessageClient;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitTestApplicationTest {

	// Apple@szy.1 789780
	@Autowired
	MessageClient messageClient;
	
	@Test
	public void testProducerClient() throws Exception {
		
		for(int i = 0 ; i < 1; i ++) {
			String uniqueId = UUID.randomUUID().toString();
			Map<String, Object> attributes = new HashMap<>();
			attributes.put("name", "张三");
			attributes.put("age", "18");
			Message message = new Message(
					uniqueId, 
					"exchange_2",
					"springboot.abc", 
					attributes, 
					0);
			message.setMessageType(MessageType.RAPID);
//			message.setDelayMills(15000);
			messageClient.sendMessage(message);
		}

		Thread.sleep(1);
	}
	
	@Test
	public void testProducerClient2() throws Exception {
		
		for(int i = 0 ; i < 1; i ++) {
			String uniqueId = UUID.randomUUID().toString();
			Map<String, Object> attributes = new HashMap<>();
			attributes.put("name", "张三");
			attributes.put("age", "18");
			Message message = new Message(
					uniqueId, 
					"delay-exchange", 
					"delay.abc", 
					attributes, 
					15000);
			message.setMessageType(MessageType.RELIANT);
			messageClient.sendMessage(message);
		}

		Thread.sleep(100000);
	}

	
	
	
}
