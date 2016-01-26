package com.zto.controller;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.zto.model.Account;

@Service("producerHelper")
public class ProducerHelper {

	private final static Logger log = LoggerFactory
			.getLogger(ProducerHelper.class);
	
	static int items=0;

	List<SendResult> sendMessage(List<Account> list) {

		DefaultMQProducer producer = new DefaultMQProducer("ProducerGroupName");
		producer.setNamesrvAddr("10.10.19.14:9876");
		//producer.setInstanceName("Producer");
		List<SendResult> resultList = new ArrayList<SendResult>();
		try {
			producer.start();
			for (int i = 0; i < list.size(); i++) {
				Message msg = new Message("AccountTopic", "AccountTags",
						"key"+items,JSONArray.fromObject(list.get(i))
								.toString().getBytes());
				items++;
				SendResult send;
				try {
					send = producer.send(msg);
					resultList.add(send);
				} catch (RemotingException | MQBrokerException
						| InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					log.info("Producer 发送消息失败：" + e.getMessage());
				}

			}
			producer.shutdown();
		} catch (MQClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.info("Producer 启动失败：" + e.getErrorMessage());
		}

		return resultList;

	}
}
