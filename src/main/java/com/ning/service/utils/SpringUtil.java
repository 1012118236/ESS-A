package com.ning.service.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * @author shenjiang
 * @Description:
 * @Date: 2019/7/15 09:47
 */
@Component
public class SpringUtil implements BeanFactoryPostProcessor {

    private static ConfigurableListableBeanFactory beanFactory;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    /**
     * 获取spring Bean对象
     * @param name
     * @return
     */
    public static Object getBean(String name){
        return beanFactory.getBean(name);
    }

    public static <T> T getBean(Class<T> clazz){
        return beanFactory.getBean(clazz);
    }
}
