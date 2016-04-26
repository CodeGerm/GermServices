package org.cg.services.core.dynamic.target.registry;

/**
 * 
 * @author WZ
 *
 * @param <T>
 */
public interface TargetRegistry<T> {

    /**
     * Returns the Target object for the given context, or {@code null} when none can be found.
     * 
     * @param context
     * @return
     */
    T getTarget(final String context);

}
