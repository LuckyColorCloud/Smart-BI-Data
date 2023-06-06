package com.yun.bidatacommon.verification.constraints;

import com.yun.bidatacommon.verification.manage.RepetitionValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 重复校验注解
 * @author Sober
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {RepetitionValidator.class})
public @interface Repetition {

    /**
     * 错误消息
     */
    String message() default "存在重复值";

    /**
     * 分组
     */
    Class<?>[] groups() default {};

    /**
     * 负载
     */
    Class<? extends Payload>[] payload() default {};

    /**
     * 指定多个时使用
     */
    @Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        Repetition[] value();
    }
}
