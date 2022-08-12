package io.github.feinrasur.utils.gui.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * Sets the rule for "swap-offhand"-clicks
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SwapOffhandClickable {
    boolean value() default true;
}
