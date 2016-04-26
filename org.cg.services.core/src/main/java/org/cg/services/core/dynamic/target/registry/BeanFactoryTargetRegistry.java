package org.cg.services.core.dynamic.target.registry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

public class BeanFactoryTargetRegistry<T> extends AbstractTargetRegistry<T> implements BeanFactoryAware {

	private static final Log LOG = LogFactory.getLog(BeanFactoryTargetRegistry.class);

    private BeanFactory beanFactory;
    private String prefix = "";
    private String suffix = "";

    public void setPrefix(final String prefix) {
        this.prefix = (prefix != null ? prefix : "");
    }

    public void setSuffix(final String suffix) {
        this.suffix = (suffix != null ? suffix :"");
    }

    private String getTargetName(final String context) {
        final String beanName = this.prefix + context + this.suffix;
        LOG.debug(String.format("TargetName: {%s}", beanName));
        return beanName;
    }

    /**
     * Gets the target from the {@code BeanFactory}. The name of the bean is being
     * constructed with the configured {@code prefix} and {@code suffix}.
     * 
     * @return the found object or {@code null}
     */
    @Override
    @SuppressWarnings("unchecked")
    protected T getTargetInternal(final String context) {
        final String beanName = this.getTargetName(context);
        T target = null;
        try {
        	LOG.debug(String.format("Retrieving bean '{%s}' from BeanFactory.", beanName));
            target = (T) this.beanFactory.getBean(beanName);
        } catch (final BeansException be) {
        	LOG.warn(String.format("beanNameCould not retrieve bean '{%s, %s}'", context, be));
        }
        return target;
    }

    /**
     * {@inheritDoc}
     */
    public void setBeanFactory(final BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

}