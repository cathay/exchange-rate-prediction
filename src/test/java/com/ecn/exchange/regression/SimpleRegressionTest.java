package com.ecn.exchange.regression;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class SimpleRegressionTest {

	@Test
	public void test() {
		final List<Point> samplePoints = Arrays.asList(
			new Point(2,21.0),
			new Point(3,23.51),
			new Point(4,24.23),
			new Point(5,27.71),
			new Point(6,30.86),
			new Point(8,45.85),
			new Point(10,52.12),
			new Point(11,55.98 )
		);
		
		final SimpleRegression model  = new SimpleRegression(samplePoints);
		model.build();
		
		assertEquals(model.getIntercept(),9.4763,0);
		assertEquals(model.getSlope(),4.1939,0);
	}
}
