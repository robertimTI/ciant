package com.robertim.ciandt.api.validator;

import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FutureOrPresentValidator implements ConstraintValidator<FutureOrPresent, Date> {

	@Override
	public void initialize(FutureOrPresent constraintAnnotation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isValid(Date value, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
