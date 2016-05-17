package org.o7planning.tutorial.springmvc;

import org.baeldung.tutorial.springmvcform.Employee;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class EmployeeValidator implements Validator {
	
	public boolean supports(Class clazz) {
        return Employee.class.equals(clazz);
    }

	public void validate(Object obj, Errors e) {
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "name", "required.name");
		
	}

}