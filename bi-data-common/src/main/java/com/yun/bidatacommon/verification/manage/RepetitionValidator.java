package com.yun.bidatacommon.verification.manage;

import com.yun.bidatacommon.verification.constraints.Repetition;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Collection;

/**
 * 重复校验
 * @author Sober
 */
public class RepetitionValidator implements ConstraintValidator<Repetition, Object> {

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value instanceof Collection<?> collection){
            return collection.stream().distinct().count() == collection.size();
        }
        return false;
    }

}
