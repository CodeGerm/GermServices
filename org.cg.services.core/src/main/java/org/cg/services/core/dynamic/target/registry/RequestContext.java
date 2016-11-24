package org.cg.services.core.dynamic.target.registry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author WZ
 *
 */
public abstract class RequestContext {
	private static final Logger LOG = LoggerFactory.getLogger(RequestContext.class);
	private static final ThreadLocal<String> holder = new ThreadLocal<String>();

	public static void setContext(final String context) {
		LOG.debug(String.format("context set: %s", context));
		holder.set(context);
	}

	public static String getContext() {
		return holder.get();
	}

	public static void clear() {
		LOG.debug("context cleared");
		holder.remove();
	}
}
