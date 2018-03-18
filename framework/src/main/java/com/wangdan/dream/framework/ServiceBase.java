package com.wangdan.dream.framework;

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
    private Map<Class<? extends ServiceBase>, List<ServiceBase>> childrenServices = new HashMap<>();
    private Logger logger = LoggerFactory.getLogger(getClass());
    private ServiceBase parent;

    public ServiceBase(ServiceBase parent) {
        this.parent = parent;
    }

    public void start() {
        initialize();
        inject();
    }

    private void initialize() {
        injectService();
        injectProperties();
    }

    private void injectProperties() {
    }

    private void injectService(){
        List<InjectService> annotations = new ArrayList<>();
        annotations.add(this.getClass().getDeclaredAnnotation(InjectService.class));
        if (this.getClass().getDeclaredAnnotation(InjectServices.class) != null)
            annotations.addAll(Arrays.asList(this.getClass().getDeclaredAnnotation(InjectServices.class).value()));
        for (InjectService injectService : annotations) {
            if (injectService == null)
                continue;
            Class<? extends ServiceBase> implementationClass = injectService.implementation();
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
        injectField();
    }

    private void injectField() {
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            Service service = field.getAnnotation(Service.class);
            if (service != null) {
                List<ServiceBase> serviceBaseList = getService(field.getDeclaringClass());
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
    }

    public void addService(Class<? extends ServiceBase> clazz, ServiceBase instance) {
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
            Optional<Class<? extends ServiceBase>> targetClassOptional = childrenServices.keySet().stream().findFirst();
            if (targetClassOptional.isPresent())
                return childrenServices.get(targetClassOptional.get());
            else
                return null;
        }
    }

}
