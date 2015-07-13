package org.cg.services.core.osgi;

import javax.servlet.ServletContext;

import org.osgi.framework.BundleContext;
import org.springframework.osgi.web.context.support.OsgiBundleXmlWebApplicationContext;

/**
 * Override OsgiBundleXmlWebApplicationContext 
 * and set osgi bundle context in Spring web context
 * @author WZ
 *
 */
public class CGOsgiBundleXmlWebApplicationContext extends OsgiBundleXmlWebApplicationContext {

	@Override
	public void setServletContext(ServletContext servletContext) {
		if (getBundleContext() == null) {
			BundleContext context = Activator.getBundleContext();
			if (context != null) {
				setBundleContext(context);
			}
		}
		super.setServletContext(servletContext);
	}
}

