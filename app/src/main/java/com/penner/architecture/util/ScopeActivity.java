package com.penner.architecture.util;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by penneryu on 16/8/5.
 */
@Documented
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ScopeActivity {
}
