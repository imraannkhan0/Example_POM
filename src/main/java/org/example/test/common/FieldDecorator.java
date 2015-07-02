package org.example.test.common;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.List;

public class FieldDecorator {

    protected ElementLocatorFactory factoryRef;

    public FieldDecorator(ElementLocatorFactory factoryRef) {
        this.factoryRef = factoryRef;
    }

    public Object decorate(ClassLoader loader, Field field) {
        if (!(IElement.class.isAssignableFrom(field.getType()) || isDecoratableList(field))) {
            return null;
        }
        ElementLocator locator = factoryRef.createLocator(field);
        if (locator == null) {
            return null;
        }
        if (IElement.class.isAssignableFrom(field.getType())) {
            return proxyForLocator(loader, locator);
        } else if (List.class.isAssignableFrom(field.getType())) {
            return proxyForListLocator(loader, locator);
        } else {
            return null;
        }


    }

    private boolean isDecoratableList(Field field) {
        if (!List.class.isAssignableFrom(field.getType())) {
            return false;
        }

        // Type erasure in Java isn't complete. Attempt to discover the generic
        // type of the list.
        Type genericType = field.getGenericType();
        if (!(genericType instanceof ParameterizedType)) {
            return false;
        }

        Type listType = ((ParameterizedType) genericType)
                .getActualTypeArguments()[0];
        // TODO - Have to check if the List<MyntElement> works while using
        // MyntElement interface
        if (!IElement.class.equals(listType)) {
            return false;
        }
        return true;
    }

    private IElement proxyForLocator(ClassLoader loader,
                                     ElementLocator locator) {
        InvocationHandler handler = new LocatingElementHandler(locator);
        IElement proxy;
        proxy = (IElement) Proxy.newProxyInstance(loader,
                new Class[]{IElement.class}, handler);
        return proxy;
    }

    @SuppressWarnings("unchecked")
    private List<IElement> proxyForListLocator(ClassLoader loader,
                                               ElementLocator locator) {
        InvocationHandler handler = new LocatingElementListHandler(locator);
        List<IElement> proxy;
        proxy = (List<IElement>) Proxy.newProxyInstance(loader,
                new Class[]{List.class}, handler);
        return proxy;
    }

}
