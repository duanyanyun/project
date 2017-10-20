package com.essay.baselibrary.ioc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Loki on 2017年9月25日.
 */
@Target(ElementType.METHOD)
//编译时注解CLASS  运行时注解RUNTIME
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckNet {
}
