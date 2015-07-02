package org.example.test.common;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class LocatingElementHandler implements InvocationHandler {

    private final ElementLocator elementLocator;
    private final int DEFAULT_FIND_TIMEOUT = 15;
    private final int DEFAULT_VISIBILITY_TIMEOUT = 6;

    public LocatingElementHandler(ElementLocator elementLocator) {
        this.elementLocator = elementLocator;
    }

    @Override
    public Object invoke(Object proxy, final Method method,
                         final Object[] objects) throws Throwable {
        IElement element = null;
        if (null == objects) {
            element = elementLocator.findElement(DEFAULT_FIND_TIMEOUT,
                    DEFAULT_VISIBILITY_TIMEOUT);
        } else {
            int parameterCount = objects.length;
            int[] timeoutParameters = (int[]) objects[parameterCount - 1];
            if (timeoutParameters.length == 1) {
                element = elementLocator.findElement(
                        (int) (timeoutParameters)[0],
                        DEFAULT_VISIBILITY_TIMEOUT);
            } else if (timeoutParameters.length == 2) {
                element = elementLocator.findElement(
                        (int) (timeoutParameters)[0],
                        (int) (timeoutParameters)[1]);
            } else {
                element = elementLocator.findElement(DEFAULT_FIND_TIMEOUT,
                        DEFAULT_VISIBILITY_TIMEOUT);
            }
        }
        if ("getWrappedElement".equals(method.getName())) {
            return element;
        }
        try {
            return method.invoke(element, objects);
        } catch (InvocationTargetException e) {
            // Unwrap the underlying exception
            throw e.getCause();
        }
    }
}
