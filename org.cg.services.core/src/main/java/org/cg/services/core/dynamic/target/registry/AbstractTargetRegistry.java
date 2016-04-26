package org.cg.services.core.dynamic.target.registry;

import org.springframework.core.Ordered;

public abstract class AbstractTargetRegistry<T> implements TargetRegistry<T>, Ordered {

    private AbstractTargetRegistry<T> parent = null;

    private int order;

    public final void setParentRegistry(final AbstractTargetRegistry<T> registry) {
        this.parent = registry;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final T getTarget(final String context) {
        T target = this.getTargetInternal(context);
        if (target == null && this.parent != null) {
            target = this.parent.getTarget(context);
        }
        return target;
    }

    public int getOrder() {
        return this.order;
    }

    public void setOrder(final int order) {
        this.order = order;
    }

    /**
     * Retrieve the target. Subclasses must implement this method.
     * 
     * @param context the context to use for the lookup
     * @return the found target object
     *
     * @see #getTarget(String)
     */
    protected abstract T getTargetInternal(final String context);
}
