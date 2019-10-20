package com.wangdan.dream.framework;

import com.wangdan.dream.commons.serviceProperties.property.Property;
import com.wangdan.dream.commons.serviceProperties.property.ServicePropertiesUtil;
import com.wangdan.dream.commons.serviceProperties.property.ServiceProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ServiceBase {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    protected ServiceProperty serviceProperty = null;
    private Map<Class<?>, List<ServiceBase>> childrenServices = new HashMap<>();
    private ServiceBase parent;

    public ServiceBase(ServiceBase parent) {
        this.parent = parent;
    }

    public void addService(Class<?> clazz, ServiceBase instance) {
        if (childrenServices.get(clazz) == null)
            childrenServices.put(clazz, new ArrayList<>());
        childrenServices.get(clazz).add(instance);
    }

    private List<InjectService> getInjectServiceAnnotations() {
        List<InjectService> annotations = new ArrayList<>();
        Class<?> clazz = this.getClass();
        while (clazz != null) {
            if (clazz.getDeclaredAnnotation(InjectService.class) != null)
                annotations.add(clazz.getDeclaredAnnotation(InjectService.class));
            if (clazz.getAnnotation(InjectServices.class) != null)
                annotations.addAll(Arrays.asList(clazz.getAnnotation(InjectServices.class).value()));
            clazz = clazz.getSuperclass();
        }
        return annotations;
    }

    public ServiceBase getService(Class<?> clazz) {
        List<ServiceBase> serviceBaseList = getServices(clazz);
        if (serviceBaseList != null && serviceBaseList.size() >= 1)
            return serviceBaseList.iterator().next();
        else
            return null;
    }

    protected ServiceProperty getServiceProperty() {
        return this.serviceProperty;
    }

    public List<ServiceBase> getServices(Class<?> clazz) {
        if (childrenServices.containsKey(clazz))
            return childrenServices.get(clazz);
        else {
            Optional optional = childrenServices.keySet().stream().filter(tempClass -> tempClass.isAssignableFrom(clazz)).findFirst();
            if (optional.isPresent()) {
                return childrenServices.get(optional.get());
            } else {
                if (parent != null)
                    return parent.getServices(clazz);
                else
                    return null;
            }
        }
    }

    public void initialize() {
        initializeService();
        initializeProperties();
    }

    private void initializeProperties() {
        this.serviceProperty = ServicePropertiesUtil.getServiceProperty(this.getClass());
    }

    protected void initializeService() {
        List<InjectService> annotations = getInjectServiceAnnotations();
        for (InjectService injectService : annotations) {
            if (injectService == null)
                continue;
            Class<? extends ServiceBase> implementationClass = injectService.value() != ServiceBase.class ? injectService.value() : injectService.implementation();
            Constructor[] constructors = implementationClass.getConstructors();
            for (Constructor constructor : constructors) {
                if (constructor.getParameterCount() == 1 && Arrays.equals(constructor.getParameterTypes(), new Class[]{ServiceBase.class})) {
                    try {
                        ServiceBase instance = (ServiceBase) constructor.newInstance(this);
                        this.addService(injectService.accessClass(), instance);
                    } catch (Exception e) {
                        logger.error("failed to create instance for {}", implementationClass, e);
                    }
                }
            }
        }
    }

    private void inject() {
        injectService();
        try {
            injectProperty();
        } catch (IllegalAccessException e) {
            logger.error("injectPrperty", e);
        }
    }

    private void injectProperty() throws IllegalAccessException {
        if (serviceProperty == null)
            return;
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            Property property = field.getAnnotation(Property.class);
            if (property != null) {
                String value = serviceProperty.getString(property.name(), property.defaultValue());
                Class fieldClass = field.getType();
                field.setAccessible(true);
                if (fieldClass.equals(String.class))
                    field.set(this, value);
                else if (fieldClass.equals(short.class))
                    field.setShort(this, Short.parseShort(value));
                else if (fieldClass.equals(int.class))
                    field.setInt(this, Integer.parseInt(value));
                else if (fieldClass.equals(Double.class))
                    field.setDouble(this, Double.parseDouble(value));
                else
                    logger.warn("failed to inject property for {}, {}", field, value);
            }
        }
    }

    private void injectService() {
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            Service service = field.getAnnotation(Service.class);
            if (service != null) {
                List<ServiceBase> serviceBaseList = getServices(field.getType());
                if (serviceBaseList != null) {
                    field.setAccessible(true);
                    try {
                        field.set(this, serviceBaseList.get(0));
                    } catch (IllegalAccessException e) {
                        logger.error("failed to inject {}", field.getName(), e);
                    }
                }
            }
        }
        for (Class<?> serviceClass : this.childrenServices.keySet()) {
            for (ServiceBase serviceBase : this.childrenServices.get(serviceClass)) {
                serviceBase.injectService();
            }
        }
    }

    public void start() {
        initialize();
        inject();
        for (Class<?> clazz : childrenServices.keySet())
            for (ServiceBase serviceBase : childrenServices.get(clazz))
                serviceBase.start();
    }
}
