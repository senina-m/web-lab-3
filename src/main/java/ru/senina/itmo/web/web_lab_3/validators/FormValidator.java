package ru.senina.itmo.web.web_lab_3.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.LongRangeValidator;
import javax.faces.validator.ValidatorException;

public class FormValidator extends LongRangeValidator {
    public void validate(FacesContext context, UIComponent component, Object convertedValue) throws ValidatorException {
        setMinimum(0);
        setMaximum(42);

        try {
            super.validate(context, component, convertedValue);
        } catch (ValidatorException e) {
            String message = (String) component.getAttributes().get("longRangeValidatorMessage");
            throw new ValidatorException(new FacesMessage(message));
        }
    }
}
