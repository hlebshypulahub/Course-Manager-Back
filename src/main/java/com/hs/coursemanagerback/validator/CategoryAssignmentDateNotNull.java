package com.hs.coursemanagerback.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {CategoryAssignmentDateNotNullValidator.class})
public @interface CategoryAssignmentDateNotNull {
    String message() default "CategoryAssignmentDateNotNull constraint violation";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
