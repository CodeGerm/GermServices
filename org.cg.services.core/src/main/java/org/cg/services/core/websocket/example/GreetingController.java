package org.cg.services.core.websocket.example;

import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
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
	public void greeting(SimpMessageHeaderAccessor headerAccessor, HelloMessage message) throws Exception {
		InetSocketAddress clientAddress = (InetSocketAddress)headerAccessor.getSessionAttributes().get("remoteAddress");
	    String ip = clientAddress.getAddress().getHostAddress();
	    String host = clientAddress.getHostName();
		LOG.info("Ping client");
		Thread.sleep(1000); // simulated delay
		String greetingMsg = String.format("Hello, %s! From ip: %s, hostname: %s", message.getName(), ip, host);
		messagingTemplate.convertAndSend("/cg/websockets/topic/greetings", new Greeting(greetingMsg));
	}
}	
