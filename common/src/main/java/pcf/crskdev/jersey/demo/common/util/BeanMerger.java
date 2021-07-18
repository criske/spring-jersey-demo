package pcf.crskdev.jersey.demo.common.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class BeanMerger {

    private BeanMerger() {
        throw new UnsupportedOperationException("No construction allowed");
    }

    /**
     * Merges to beans. Original is mutated by updater properties as long
     * as updater's properties are not null.
     * <br/>
     * Since this operation is performed on a mutable bean, the updating is
     * complete only if there are no error regarding excluded properties. This
     * way the original will not be a bad state.
     *
     * @param original Original bean.
     * @param updater Updater bean.
     * @param excludedProperties Excluded properties from being updated.
     * @param <T> Type of the bean
     * @return List of properties attempted to changed. If all goes well,
     * this list
     * should be empty.
     */
    public static <T> List<String> merge(
        final T original,
        final T updater,
        final String... excludedProperties
    ) {
        final List<String> attemptsOnExcluded = new ArrayList<>();
        try {
            final List<String> excludedList = Arrays.asList(excludedProperties);
            final BeanInfo info = Introspector.getBeanInfo(original.getClass());
            final List<DeferredInvocation> deferredInvocations = new ArrayList<>();

            for (var descriptor : info.getPropertyDescriptors()) {
               final Method readMethod = descriptor.getReadMethod();
               final Method writeMethod = descriptor.getWriteMethod();
               final String property = descriptor.getName();

                //check for setter - getter and defer the update invocation
                if (readMethod != null && writeMethod != null) {
                    final Object oldVal = readMethod.invoke(original);
                    final Object newVal = readMethod.invoke(updater);
                    if (newVal != null && !newVal.equals(oldVal)) {
                        if (excludedList.contains(property)) {
                            attemptsOnExcluded.add(property);
                        } else {
                            deferredInvocations.add(new DeferredInvocation(writeMethod, original, newVal));
                        }
                    }
                }

                //there are no attempts to update excluded properties
                if (attemptsOnExcluded.isEmpty()) {
                    for (final var postponed : deferredInvocations) {
                        postponed.invoke();
                    }
                }
            }
        } catch (final IntrospectionException
            | IllegalAccessException
            | InvocationTargetException e) {
            //ignore
        }
        return attemptsOnExcluded;
    }

    private static final class DeferredInvocation {
        private final Method method;
        private final Object target;
        private final Object arg;

        private DeferredInvocation(
            final Method method,
            final Object target,
            final Object arg
        ) {
            this.method = method;
            this.target = target;
            this.arg = arg;
        }

        void invoke() throws InvocationTargetException, IllegalAccessException {
            this.method.invoke(target, arg);
        }
    }

}
