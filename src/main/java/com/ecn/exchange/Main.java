package com.ecn.exchange;

import java.util.List;

import com.ecn.exchange.regression.SimpleRegression;
import com.ecn.exchange.regression.TimeSeriesLag;

public class Main {

	public static void main(final String[] args) {
		final ExchangeRateSampleProvider provider = new ExchangeRateSampleProvider("http://api.fixer.io/");

		final String fromCurrency = "USD";
		final String targetCurrency = "EUR";

		final List<Double> sampleRates = provider.provideMonthlySamplesAtDay(15, 2016, fromCurrency, targetCurrency);
		System.out.println(sampleRates);

		final SimpleRegression model = new SimpleRegression(TimeSeriesLag.lag(sampleRates));
		final Double predictRate = model.predict(sampleRates.get(sampleRates.size() - 1));
		
		System.out.println(String.format("The predicted currency exchange from %s to %s for 15/1/2017 is %s.", fromCurrency, targetCurrency, predictRate));
	}

}
