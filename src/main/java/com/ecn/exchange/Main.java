package com.ecn.exchange;

import java.util.List;

import com.ecn.exchange.provider.FixerSampleRatesProvider;
import com.ecn.exchange.regression.SimpleRegression;
import com.ecn.exchange.regression.TimeSeriesLag;

public class Main {

	public static void main(final String[] args) {
		
		//TODO For simplicity, I just let the exception printed in the console. In PROD, hide these details in log file, and just print meaningful message to console
		final FixerSampleRatesProvider provider = new FixerSampleRatesProvider();

		final String fromCurrency = "USD";
		final String targetCurrency = "EUR";

		final List<Double> sampleRates = provider.provideMonthlySamplesAtDay(15, 2032, fromCurrency, targetCurrency);
		
		//FIXME This is only for debug purpose, have to be replaced by log.debug in PROD
		System.out.println(sampleRates);

		final SimpleRegression model = new SimpleRegression(TimeSeriesLag.lag(sampleRates));
		final Double predictRate = model.predict(sampleRates.get(sampleRates.size() - 1));
		
		//FIXME These println should be replaced by log.info
		System.out.println(String.format("Linear equation: y=%s + %s*x", model.getIntercept(), model.getSlope()));
		
		System.out.println(String.format("The predicted currency exchange from %s to %s for 15/1/2017 is %s.", fromCurrency, targetCurrency, predictRate));
	}

}
