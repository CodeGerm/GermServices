package org.cg.services.core.swagger.example;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cg.services.core.exception.InvalidParameterException;
import org.joda.time.DateTime;

import com.google.common.base.Strings;

/**
 * A Facad for CGCoreService
 * @author WZ
 *
 */
public class CGCoreServiceImpl implements CGCoreService {
	
	/**
	 * Logger
	 */
	private static final Log LOG = LogFactory.getLog(CGCoreServiceImpl.class);
	
	@Override
	public GreetingMessage greetings(String message, Boolean formatTime) {
		if (Strings.isNullOrEmpty(message)) {
			LOG.info(String.format("[greetings]message is null or empty"));
			throw new InvalidParameterException("message must be provided");
		}
		GreetingMessage result = new GreetingMessage(message, DateTime.now());
		/********** format timestamp if in need **********/
		if(formatTime!=null && formatTime == true){
			result.setFormated(true);
		}
		return result;
	}

}
