package org.cg.services.core.util;

import io.swagger.jaxrs.Reader;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.models.Swagger;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.cg.services.core.osgi.Activator;
import org.osgi.framework.Bundle;
import org.osgi.framework.wiring.BundleWiring;

import com.google.common.base.Strings;

/**
 * Override setScan for BeanConfig so it supports services in multiple bundles
 * @author WZ
 *
 */
public class ServiceAwareBeanConfig extends BeanConfig {

	Reader reader = new Reader(new Swagger());

	@Override
	public void setScan(boolean shouldScan) {
		if (!Strings.isNullOrEmpty(getBasePath())) {
			Set<Class<?>> classes = classes();
			if (classes != null) {
				reader.read(classes)
				.host(getHost())
				.basePath(getBasePath())
				.info(getInfo());
			}
			ServiceAwareScannerFactory.setScanner(getBasePath(), this);
		}
	}
	
	@Override
	public Set<Class<?>> classes() {
		// Set current class loader
		JoinClassLoader loader = new JoinClassLoader(Thread.currentThread().getContextClassLoader(),
				getAllBundlesClassLoaders());
		Thread.currentThread().setContextClassLoader(loader);

		return super.classes();
	}

	private ClassLoader[] getAllBundlesClassLoaders() {
		List<ClassLoader> loaders = new ArrayList<>();
		Bundle[] bundles = Activator.getBundleContext().getBundles();
		for (Bundle bundle : bundles) {
			BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);
			if (bundleWiring != null) {
				try {
					ClassLoader classLoader = bundleWiring.getClassLoader();
					if (classLoader != null) {
						loaders.add(classLoader);
					}
				} catch (ClassCastException e) {
					// Ignore as some bundles cannot get class loader
				}
			}
		}
		return loaders.toArray(new ClassLoader[loaders.size()]);
	}

}
