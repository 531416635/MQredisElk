package com.zto.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath*:spring-mvc.xml")
public class ConsumerTest /*implements ApplicationListener<ContextRefreshedEvent>*/ {

	private static Logger log = LoggerFactory.getLogger(ConsumerTest.class);

	/*@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		// TODO Auto-generated method stub
		if (event.getApplicationContext().getParent() == null) {
			try {
				afterPropertiesSet();
			} catch (Exception e) {
				// TODO: handle exception
				log.error(e.getMessage(), e);
			}
		}
	}*/

	@Test
	public void afterPropertiesSet() {
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(
				"consumerGroupName");
		consumer.setNamesrvAddr("10.10.19.14:9876");
		consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
		// consumer.subscribe("consumerTopic", "consumerTags");
		try {
			consumer.subscribe("AccountTopic", "AccountTags");
		} catch (MQClientException e) {
			log.info("订阅失败"+e.getErrorMessage());
		};
		consumer.registerMessageListener(new MessageListenerConcurrently() {
			public ConsumeConcurrentlyStatus consumeMessage(
					List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
				Map<String, List<MessageExt>> msgListMap = new HashMap<String, List<MessageExt>>();
				log.info("23432");
				for (MessageExt msg : msgs) {
					if (msg.getTopic().equals("AccountTopic")) {
						if (msg.getTags() == null) {
							continue;
						}
						String type = null;
						if ("AccountTags".equals(msg.getTags())) {
							// 执行tag为1的消息
							type = "Accounts";
						}
						List<MessageExt> listMsg = msgListMap.get(type);
						if (listMsg == null) {
							listMsg = new ArrayList<>();
						}
						listMsg.add(msg);
						msgListMap.put(type, listMsg);
					}
				}
				return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
			}
		});
		try {
			consumer.start();
		} catch (MQClientException e) {
			log.info("start"+e.getErrorMessage());
		}
		log.info("MQ comsumer getConsumerGroup()  started!");
	}
}
