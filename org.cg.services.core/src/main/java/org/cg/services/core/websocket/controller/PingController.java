package org.cg.services.core.websocket.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
public class PingController {

	private static final Log LOG = LogFactory.getLog(PingController.class);

	@Autowired
	@Qualifier("org.springframework.messaging.simp.SimpMessagingTemplate#1")
	public SimpMessageSendingOperations messagingTemplate;

	@MessageMapping("/ping")
	public void greeting(PingMessage message) throws Exception {
		LOG.info("Ping client");
		Thread.sleep(1000); // simulated delay
		messagingTemplate.convertAndSend("/topic/ping", new Ping("Ping "
				+ System.currentTimeMillis() + " " + message.getName() + "!"));

	}
}
