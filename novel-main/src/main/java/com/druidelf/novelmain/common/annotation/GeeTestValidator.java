package com.druidelf.novelmain.common.annotation;

import com.druidelf.novelmain.common.utils.GeeTestValidatorUtil;
import com.druidelf.novelmain.request.userParam.GeeTestParam;
import lombok.extern.log4j.Log4j2;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 极验验证
 */
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = GeeTestValidator.Validator.class)
public @interface GeeTestValidator {


    String message() default "{行为验证不正确}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Log4j2
    class Validator implements ConstraintValidator<GeeTestValidator, Object> {

        private Class<? extends Enum<?>> enumClass;
        private String enumField;


        @Override
        public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
            if (value == null) {
                return Boolean.FALSE;
            }

            if (!(value instanceof GeeTestParam)){
                return Boolean.FALSE;
            }
            GeeTestValidatorUtil geeTestValidatorUtil = new GeeTestValidatorUtil();
            GeeTestParam geeTestParam = (GeeTestParam) value;
            if (geeTestValidatorUtil.secondaryValidation( geeTestParam )) {
                return Boolean.TRUE;
            }

            return Boolean.FALSE;
        }
    }
}




