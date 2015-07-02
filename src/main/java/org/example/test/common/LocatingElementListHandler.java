package org.example.test.common;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class LocatingElementListHandler implements InvocationHandler {
    private final ElementLocator elementLocator;
    private final int DEFAULT_FIND_TIMEOUT = 15;

    public LocatingElementListHandler(ElementLocator elementLocator) {
        this.elementLocator = elementLocator;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] objects)
            throws Throwable {
        List<IElement> elements;
        int findTimeout;
        if (null == objects) {
            elements = elementLocator.findElements(DEFAULT_FIND_TIMEOUT);
        } else {
            int parameterCount = objects.length;
            if (parameterCount == 1) {
                elements = elementLocator
                        .findElements(DEFAULT_FIND_TIMEOUT);
            } else if (parameterCount == 2) {
                findTimeout = ((int[]) objects[parameterCount - 1])[0];
                elements = elementLocator.findElements(findTimeout);
            } else {
                elements = elementLocator
                        .findElements(DEFAULT_FIND_TIMEOUT);
            }
        }

        try {
            return method.invoke(elements, objects);
        } catch (InvocationTargetException e) {
            // Unwrap the underlying exception
            throw e.getCause();
        }
    }

    public ElementLocator getMyntElementLocator() {
        return elementLocator;
    }
}
