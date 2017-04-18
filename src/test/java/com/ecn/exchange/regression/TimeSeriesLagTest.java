package com.ecn.exchange.regression;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class TimeSeriesLagTest {

	@Test
	public void testLag1Function() {
		final List<Double> inputs = Arrays.asList(1d, 2d, 3d, 4d, 5d, 6d);

		assertEquals(Arrays.asList(
				new Point(1, 2), 
				new Point(2, 3), 
				new Point(3, 4), 
				new Point(4, 5), 
				new Point(5, 6)),
				TimeSeriesLag.lag(inputs));
	}
}
