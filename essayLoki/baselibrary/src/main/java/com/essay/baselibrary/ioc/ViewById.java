package com.essay.baselibrary.ioc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Loki on 2017年9月21日.
 * View注解的Annotation
 */
//@Target(ElementType.FIELD) 代表Annotation的位置  FIELD属性上  Type类上    CONSTTRUCTOR构造函数上
@Target(ElementType.FIELD)
//编译时注解CLASS  运行时注解RUNTIME
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewById {
    int value();
}
