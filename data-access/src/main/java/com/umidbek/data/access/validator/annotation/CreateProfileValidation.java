package com.umidbek.data.access.validator.annotation;

import com.umidbek.data.access.validator.CreateProfileValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Constraint(validatedBy = {CreateProfileValidator.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CreateProfileValidation {

    String message() default "Invalid value of field";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
