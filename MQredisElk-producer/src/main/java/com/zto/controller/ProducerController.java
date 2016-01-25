package com.zto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/producer")
public class ProducerController {

	@RequestMapping("/toProducer")
	public String getProducer(){
		
		return "producer";
	}
}
