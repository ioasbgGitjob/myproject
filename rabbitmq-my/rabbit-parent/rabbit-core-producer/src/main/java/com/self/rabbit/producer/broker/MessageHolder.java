package com.self.rabbit.producer.broker;

import java.util.List;

import com.google.common.collect.Lists;
import com.self.rabbit.api.Message;

/**
 * 批量消息
 */
public class MessageHolder {

	private List<Message> messages = Lists.newArrayList();

	@SuppressWarnings({"rawtypes", "unchecked"})
	public static final ThreadLocal<MessageHolder> holder = new ThreadLocal<>(){
		@Override
		protected MessageHolder initialValue() {
			return new MessageHolder();
		}
	};
	public static void add(Message message) {
		holder.get().messages.add(message);
	}
	
	public static List<Message> clear() {
		List<Message> tmp = Lists.newArrayList(holder.get().messages);
		holder.remove();
		return tmp;
	}
	
}
