package org.cg.services.core.websocket.example;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * 
 * @author WZ
 *
 */
@Controller
public class GreetingController {

	private static final Log LOG = LogFactory.getLog(GreetingController.class);

	@MessageMapping("/hello")
	@SendTo("/cg/websockets/topic/greetings")
	public Greeting greeting(HelloMessage message) throws Exception {
		LOG.info(String.format("Processing message: %s", message.getName()));
		Thread.sleep(3000); // simulated delay
		return new Greeting("Hello, " + message.getName() + "!");
	}
}	
