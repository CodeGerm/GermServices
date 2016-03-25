package org.cg.services.core.util;

import java.util.Set;

import com.google.common.base.Strings;

import io.swagger.jaxrs.Reader;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.models.Swagger;

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
}
