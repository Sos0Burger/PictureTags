package org.sosoburger.validator;

import com.vaadin.flow.data.validator.RegexpValidator;

public class ConfidenceValidator extends RegexpValidator {
    public ConfidenceValidator(String errorMessage) {
        super(errorMessage, "^((\\d)|([1-9]\\d)|100)%$", true);
    }

    protected boolean isValid(String value) {
        return value != null && !value.isEmpty() && super.isValid(value);
    }
}
