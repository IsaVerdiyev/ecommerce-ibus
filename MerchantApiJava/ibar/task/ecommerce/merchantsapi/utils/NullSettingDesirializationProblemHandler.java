package ibar.task.ecommerce.merchantsapi.utils;



import java.io.IOException;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;

public class NullSettingDesirializationProblemHandler extends DeserializationProblemHandler {
	@Override
	public Object handleWeirdStringValue(DeserializationContext ctxt, Class<?> targetType, String valueToConvert,
			String failureMsg) throws IOException {
		return null;
	}

	@Override
	public Object handleWeirdNumberValue(DeserializationContext ctxt, Class<?> targetType, Number valueToConvert,
			String failureMsg) throws IOException {
		return null;
	}
}
