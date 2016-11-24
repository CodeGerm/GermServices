package org.cg.services.core.dynamic.target.registry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.TargetSource;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.OrderComparator;
import org.springframework.util.Assert;

import java.util.*;

public class DynamicTargetSource implements TargetSource, InitializingBean, ApplicationContextAware {
	
	private static final Logger LOG = LoggerFactory.getLogger(DynamicTargetSource.class);

    private final List<TargetRegistry<?>> registries = new LinkedList<TargetRegistry<?>>();

    private final Class<?> targetClass;

    private boolean alwaysReturnTarget = false;

    private Object defaultTarget;

    private ApplicationContext context;

    public DynamicTargetSource(final Class<?> targetClass) {
        super();
        this.targetClass = targetClass;
    }

    @Override
    public Object getTarget() throws Exception {
        final String contextName = RequestContext.getContext();
        LOG.debug(String.format("Current context: '{%s}'", contextName));

        final Object target = this.getTarget(contextName);

        if (target == null) {
        	LOG.error(String.format("Cannot locate a target of type '{%s}' for context '{%s}'", this.targetClass.getName(), contextName));
            throw new TargetLookupFailureException("Cannot locate a target for context '" + contextName + "'");
        }

        if (!this.targetClass.isAssignableFrom(target.getClass())) {
            throw new TargetLookupFailureException("The target for '" + contextName + "' is not of the required type."
                    + "Expected '" + this.targetClass.getName() + "' and got '" + target.getClass().getName() + "'");
        }
        return target;
    }

    @Override
    public final Class<?> getTargetClass() {
        return this.targetClass;
    }

    @Override
    public final boolean isStatic() {
        return false;
    }

    private Object getTarget(final String context) {
        Object target = this.resolveTarget(context);
        if (target == null && this.alwaysReturnTarget) {
        	LOG.debug(String.format("Return default target for context '{%s}'", context));
            target = this.defaultTarget;
        }
        return target;
    }

    protected Object resolveTarget(final String context) {
        Object target = null;
        for (final TargetRegistry<?> registry : this.registries) {
        	LOG.debug(String.format("Using '{%s}' to lookup '{%s}'.", registry, context));
            target = registry.getTarget(context);
            if (target != null) {
                break;
            }
        }
        return target;
    }

    public void releaseTarget(final Object target) throws Exception {
    }

    public final void afterPropertiesSet() throws Exception {
        Assert.notNull(this.targetClass, "TargetClass property must be set!");

        this.initTargetRegistries();

        if (this.alwaysReturnTarget) {
            Assert.notNull(this.defaultTarget,
                    "The defaultTarget property is null, while alwaysReturnTarget is set to true. "
                            + "When alwaysReturnTarget is set to true a defaultTarget must be set!");
        }
    }

    @SuppressWarnings("unchecked")
    private void initTargetRegistries() {
        if (this.registries.isEmpty()) {
            @SuppressWarnings("rawtypes")
			final Map<String, ? extends TargetRegistry> matchingBeans = 
            		BeanFactoryUtils.beansOfTypeIncludingAncestors(
            				this.context, 
            				TargetRegistry.class, 
            				true, 
            				false);
            
            if (!matchingBeans.isEmpty()) {
                this.registries.addAll((Collection<? extends TargetRegistry<?>>) matchingBeans.values());
                Collections.sort(this.registries, new OrderComparator());
            } else {
                @SuppressWarnings("rawtypes")
                final BeanFactoryTargetRegistry<?> registry = new BeanFactoryTargetRegistry();
                registry.setBeanFactory(this.context);
                this.registries.add(registry);
            }
        }
    }

    public final void setAlwaysReturnTarget(final boolean alwaysReturnTarget) {
        this.alwaysReturnTarget = alwaysReturnTarget;
    }

    public final void setDefaultTarget(final Object defaultTarget) {
        this.defaultTarget = defaultTarget;
    }

    public final void setTargetRegistry(final TargetRegistry<?> registry) {
        this.registries.clear();
        this.registries.add(registry);
    }

    public final void setTargetRegistries(final List<TargetRegistry<?>> registries) {
        this.registries.addAll(registries);
    }

    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    List<TargetRegistry<?>> getTargetRegistries() {
        return this.registries;
    }

}