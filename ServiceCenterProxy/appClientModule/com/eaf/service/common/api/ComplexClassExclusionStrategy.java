package com.eaf.service.common.api;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class ComplexClassExclusionStrategy implements ExclusionStrategy {
	@Override
	public boolean shouldSkipClass(Class<?> arg0) {
		if(arg0 == byte[].class){
			return true;
		}
		return false;
	}

	@Override
	public boolean shouldSkipField(FieldAttributes arg0) {
		return false;
	}

}
