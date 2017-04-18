package com.ecn.exchange.regression.serialize;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.ecn.exchange.serialize.RateParser;

public class RateParserTest {

	@Test
	public void testGetRate() {
		final RateParser parser = new RateParser();
		final Double result = parser.parseResponse("{\"base\":\"USD\",\"date\":\"2016-07-15\",\"rates\":{\"EUR\":0.89863}}", "EUR");
		assertEquals(0.89863, result,0);
	}
}
