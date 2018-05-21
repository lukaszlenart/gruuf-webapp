package com.gruuf.auth;

import com.gruuf.web.GlobalResult;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface BikeRestriction {

    String value() default "bikeId";

    String resultName() default GlobalResult.GARAGE;

    boolean ignoreNullParam() default true;

    Token[] allowedBy() default {};

}
