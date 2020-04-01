package il.co.menora.soaarchive.client.places;

import java.util.HashMap;

public class PlaceUtils {

	public static HashMap<String, String> getParametersFromToken(String token) {
		HashMap<String, String> parametersMap = new HashMap<String, String>();
		if (token == null || token.length() == 0) {
			return parametersMap;
		}

		String[] parameters = token.split(":");
		for (int i = 0; i < parameters.length; i++) {
			String parameter = parameters[i];
			if (parameter != null && parameter.length() > 0) {
				String parameterName = parameter;
				String parameterValue = "";
				int indexOfFirstDash = parameter.indexOf("-");
				if (indexOfFirstDash > -1) {
					parameterName = parameter.substring(0, indexOfFirstDash);
					parameterValue = decodeURIComponent(parameter.substring(indexOfFirstDash + 1));
				}
				parametersMap.put(parameterName, parameterValue);
			}
		}

		return parametersMap;
	}

	private static String encodeParameterValue(String decodedString) {
		return encodeURIComponent(decodedString).replaceAll("\\:", "%3A").replaceAll("\\-", "%2D");
	}

	public static String getEncodedParameter(String parameterName, String parameterValue) {
		if (parameterValue != null && parameterValue.length() > 0) {
			return ":" + parameterName + "-" + PlaceUtils.encodeParameterValue(parameterValue);
		} else {
			return "";
		}
	}

	private static native String encodeURIComponent(String decodedString)
	/*-{
		return encodeURIComponent(decodedString);
	}-*/;

	private static native String decodeURIComponent(String encodedString)
	/*-{
		return decodeURIComponent(encodedString);
	}-*/;
}
