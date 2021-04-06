package com.java2nb.novel.core.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 */
@Component
public class SpringUtil implements ApplicationContextAware {



    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)  {
        if (SpringUtil.applicationContext == null) {
            SpringUtil.applicationContext = applicationContext;
        }
    }

    /**
     * Dapatkan applicationContext
     * */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * Dapatkan Bean dengan nama.
     * */
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    /**
     * Dapatkan Bean melalui kelas
     * */
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    /**
     * Kembalikan Bean yang ditentukan dengan nama dan Clazz
     * */
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

}
