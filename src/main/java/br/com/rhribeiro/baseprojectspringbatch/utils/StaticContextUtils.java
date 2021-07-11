package br.com.rhribeiro.baseprojectspringbatch.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Class Static Context Utils
 *
 * @author Renan Ribeiro
 * @since 11/07/2021
 */

@Component
public class StaticContextUtils {

    private static StaticContextUtils instance;

    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    public void registerInstance() {
        instance = this;
    }

    public static <T> T getBean(Class<T> clazz) {
        return instance.applicationContext.getBean(clazz);
    }

}
