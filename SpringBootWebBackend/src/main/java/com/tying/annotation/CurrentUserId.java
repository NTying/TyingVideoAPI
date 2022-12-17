package com.tying.annotation;

import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * @author Tying
 * @version 1.0
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrentUserId {
}
