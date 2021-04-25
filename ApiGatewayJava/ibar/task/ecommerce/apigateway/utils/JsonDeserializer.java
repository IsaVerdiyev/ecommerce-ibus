package ibar.task.ecommerce.apigateway.utils;

import ibar.task.ecommerce.apigateway.exceptions.InvalidContentException;

import java.io.IOException;
import java.util.Set;

import javax.validation.ConstraintViolation;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

public class JsonDeserializer {
	ApplicationContext context;

	public JsonDeserializer(ApplicationContext context) {
		this.context = context;
	}

	public <T> T deserializeToObjectIfValid(String json, Class<T> classType)
			throws JsonParseException, JsonMappingException, IOException,
			InvalidContentException {
		T deserializedObject;
		try {
			deserializedObject = context.getObjectMapper().readValue(json,
					classType);
		} catch (UnrecognizedPropertyException e) {
			throw new InvalidContentException(e.getPropertyName()
					+ " is not recognized", e);
		} catch (InvalidFormatException e) {
			throw new InvalidContentException("Invalid format of "
					+ e.getPath().get(e.getPath().size() - 1).getFieldName()
					+ ". Invalid value " + e.getValue(), e);
		} finally {
			context.getObjectMapper().configure(
					DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			context.getObjectMapper().addHandler(
					new NullSettingDesirializationProblemHandler());
		}
		Set<ConstraintViolation<T>> violations = context.getValidator()
				.validate(deserializedObject);
		for (ConstraintViolation<T> violation : violations) {
			throw new InvalidContentException(violation.getPropertyPath() + " "
					+ violation.getMessage() + ". Invalid value "
					+ violation.getInvalidValue());
		}
		return deserializedObject;
	}
}
