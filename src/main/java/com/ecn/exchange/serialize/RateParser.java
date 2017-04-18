package com.ecn.exchange.serialize;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.ecn.exchange.provider.UnknownCurrencyException;

public class RateParser {

	private final JSONParser parser = new JSONParser();
	
	public Double parseResponse(final String response, final String targetCurrency) {
		
		JSONObject root;
		try {
			root = (JSONObject) parser.parse(response);
		} catch (final ParseException e) {
			throw new JsonFormatException(String.format("Cannot parse Json response from website:%s", response), e);
		}

		final JSONObject rateNode = (JSONObject) root.get("rates");
		final Object result = rateNode.get(targetCurrency.toUpperCase());
		
		if(result == null) {
			throw new UnknownCurrencyException(targetCurrency);
		}
		return (Double) result;
	}
}
