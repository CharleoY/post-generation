package com.umidbek.data.access.validator;

import com.umidbek.data.access.profile.CreateProfileDto;
import com.umidbek.data.access.validator.annotation.CreateProfileValidation;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class CreateProfileValidator implements ConstraintValidator<CreateProfileValidation, CreateProfileDto> {

    private static final Logger LOGGER = Logger.getLogger(CreateProfileValidator.class.getCanonicalName());
    private String pattern = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\\\.[A-Z]{2,6}$";


    @Override
    public boolean isValid(CreateProfileDto dto, ConstraintValidatorContext constraintValidatorContext) {
        if (dto == null) {
            return false;
        }

        if (StringUtils.isBlank(dto.getPassword()) ||
            StringUtils.isBlank(dto.getReEnterPassword())) {
            return false;
        }

        if (!dto.getPassword().equals(dto.getReEnterPassword())) {
            return false;
        }

        if (StringUtils.isBlank(dto.getEmail()) || Pattern.matches(pattern, dto.getEmail())) {
            return false;
        }

        if (StringUtils.isBlank(dto.getUsername())) {
            return false;
        }

        return true;
    }
}
