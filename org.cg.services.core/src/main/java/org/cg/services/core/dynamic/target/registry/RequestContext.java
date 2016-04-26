package org.cg.services.core.dynamic.target.registry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author WZ
 *
 */
public abstract class RequestContext {
	private static final Log LOG = LogFactory.getLog(RequestContext.class);
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
