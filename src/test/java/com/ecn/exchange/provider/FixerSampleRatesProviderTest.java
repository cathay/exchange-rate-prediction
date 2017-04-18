package com.ecn.exchange.provider;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class FixerSampleRatesProviderTest {

	private final FixerSampleRatesProvider provider = new FixerSampleRatesProvider();
	
	@Test(expected = UnknownCurrencyException.class)
	public void testWithUnknownBaseCurrency() {
		provider.provideMonthlySamplesAtDay(15, 2016, "USD1", "EUR");
	}
	
	@Test
	public void testRetrievingRatesEvery15DateOfYear2016FromUSDToEUR() {
		final List<Double> sampleRates = provider.provideMonthlySamplesAtDay(15, 2016, "USD", "EUR");
		assertEquals(
				Arrays.asList(
						0.91625, 0.89445, 0.90017, 0.88621, 0.88121, 0.89047, 0.89863, 0.89445, 0.88857, 0.90893, 0.92894, 0.95979),
				sampleRates);
	}
	
	@Test(expected = UnknownCurrencyException.class)
	public void testWithUnknownTargetCurrency() {
		provider.provideMonthlySamplesAtDay(15, 2016, "USD", "EU1");
	}
	
}
