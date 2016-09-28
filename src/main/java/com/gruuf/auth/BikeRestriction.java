package com.gruuf.auth;

import com.gruuf.web.GruufActions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface BikeRestriction {

    String value() default "bikeId";

    String resultName() default GruufActions.GARAGE;

}
