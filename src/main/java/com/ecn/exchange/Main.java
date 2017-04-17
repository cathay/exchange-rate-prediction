package com.ecn.exchange;

public class Main {

	public static void main(final String[] args) {
		final ExchangeRateSampleProvider provider = new ExchangeRateSampleProvider("http://api.fixer.io/");

		provider.provideMonthlySamplesAtDay(15, 2016, "USD", "EUR");
	}

}
