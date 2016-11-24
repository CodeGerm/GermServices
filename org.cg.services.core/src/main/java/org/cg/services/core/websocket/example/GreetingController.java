package org.cg.services.core.websocket.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

/**
 * 
 * @author WZ
 *
 */
@Controller
public class GreetingController {

	private static final Logger LOG = LoggerFactory.getLogger(GreetingController.class);

	@Autowired
	public SimpMessageSendingOperations messagingTemplate;
	
	@MessageMapping("/hello")
	public void greeting(HelloMessage message) throws Exception {
		LOG.info("Ping client");
		Thread.sleep(1000); // simulated delay
		messagingTemplate.convertAndSend("/cg/websockets/topic/greetings", 
				new Greeting("Hello, " + message.getName() + "!"));
	}
}	
