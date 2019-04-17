package com.wangdan.dream.framework;

import com.wangdan.dream.commons.serviceProperties.Property;
import com.wangdan.dream.commons.serviceProperties.ServicePropertiesUtil;
import com.wangdan.dream.commons.serviceProperties.ServiceProperty;
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
        if (instance instanceof ServiceBase) {
            if (childrenServices.containsKey(clazz) && childrenServices.get(clazz) != null)
                childrenServices.get(clazz).add(instance);
            else {
                List<ServiceBase> serviceBaseList = new ArrayList<ServiceBase>();
                serviceBaseList.add(instance);
                childrenServices.put(clazz, serviceBaseList);
            }
        }
    }

    public List<ServiceBase> getService(Class<?> clazz) {
        if (childrenServices.containsKey(clazz))
            return childrenServices.get(clazz);
        else {
            Optional optional = childrenServices.keySet().stream().filter(tempClass -> tempClass.isAssignableFrom(clazz)).findFirst();
            if (optional.isPresent()) {
                return childrenServices.get(optional.get());
            } else {
                if (parent != null)
                    return parent.getService(clazz);
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

    private void initializeService() {
        List<InjectService> annotations = new ArrayList<>();
        annotations.add(this.getClass().getDeclaredAnnotation(InjectService.class));
        if (this.getClass().getDeclaredAnnotation(InjectServices.class) != null)
            annotations.addAll(Arrays.asList(this.getClass().getDeclaredAnnotation(InjectServices.class).value()));
        for (InjectService injectService : annotations) {
            if (injectService == null)
                continue;
            Class<? extends ServiceBase> implementationClass = injectService.value() != ServiceBase.class ? injectService.value() : injectService.implementation();
            Constructor[] constructors = implementationClass.getConstructors();
            for (Constructor constructor : constructors) {
                if (constructor.getParameterCount() == 1 && Arrays.equals(constructor.getParameterTypes(), new Class[]{ServiceBase.class})) {
                    try {
                        ServiceBase instance = (ServiceBase) constructor.newInstance(this);
                        this.addService(injectService.accessClass() != null ? injectService.accessClass() : injectService.value(), instance);
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
                String value = serviceProperty.getString(property.value(), property.defaultValue());
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
                List<ServiceBase> serviceBaseList = getService(field.getType());
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
