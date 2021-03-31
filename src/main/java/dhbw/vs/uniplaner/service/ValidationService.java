package dhbw.vs.uniplaner.service;

import dhbw.vs.uniplaner.interfaces.IValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;

public class ValidationService implements ConstraintValidator<IValidation, Object> {
    private String firstFieldName;
    private String secondFieldName;

    @Override
    public void initialize(final IValidation constraintAnnotation) {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        try {
            final Object firstObj = BeanUtils.getProperty(value, firstFieldName);
            final Object secondObj = BeanUtils.getProperty(value, secondFieldName);
            return firstObj == null && secondObj == null || firstObj != null && firstObj.equals(secondObj);
        } catch (final Exception ignore) {}
        return true;
    }
}
