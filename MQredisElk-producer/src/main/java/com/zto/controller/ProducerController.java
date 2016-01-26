package com.zto.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.rocketmq.client.producer.SendResult;
import com.zto.model.Account;
import com.zto.service.AccountService;

@Controller
@RequestMapping("/producer")
public class ProducerController {
	@Autowired
	AccountService accountService;
	@Autowired
	ProducerHelper producerHelper;
	private static final Logger log = LoggerFactory
			.getLogger(ProducerController.class);

	@RequestMapping("/toProducer")
	public String getProducer() {
		return "producer";
	}

	@RequestMapping("/Producer")
	@ResponseBody
	public String Producer(Integer begin, Integer end) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("begin", begin);
		map.put("end", end);
		List<Account> accountList = accountService.selectList(map);
		List<SendResult> pro = producerHelper.sendMessage(accountList);
		String send = JSONArray.fromObject(pro).toString();
		log.info(send);
		return send;
	}
}
