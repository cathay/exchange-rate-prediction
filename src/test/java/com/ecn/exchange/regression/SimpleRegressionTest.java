package com.ecn.exchange.regression;

import static org.junit.Assert.assertEquals;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class SimpleRegressionTest {

	@Test
	public void testSlopeAndInterceptOfRandomSamplePoints() {
		
		//This test data get from Internet sample
		final List<Point> samplePoints = Arrays.asList(
			new Point(2,21.05),
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
		
		final DecimalFormat df = new DecimalFormat("#.####"); 
		df.setRoundingMode(RoundingMode.CEILING);
		
		assertEquals(9.4763,Double.valueOf(df.format(model.getIntercept())),0);
		assertEquals(4.1939,Double.valueOf(df.format(model.getSlope())),0);
	}
	
	@Test
	public void testSlopeAndInterceptOfBeautifulSamplePoints() {
		final List<Point> samplePoints = Arrays.asList(
				new Point(1,3),
				new Point(2,1),
				new Point(3,2)
				);
		
		final SimpleRegression model  = new SimpleRegression(samplePoints);
		model.build();
		
		assertEquals(-0.5, model.getSlope(),0);
		assertEquals(3, model.getIntercept(),0);
	}
	
	@Test
	public void testPredictionOfBeautifulSamplePoints() {
		final List<Point> samplePoints = Arrays.asList(
				new Point(1,3),
				new Point(2,1),
				new Point(3,2)
				);
		
		final SimpleRegression model  = new SimpleRegression(samplePoints);
		
		assertEquals(3, model.predict(0),0);
		assertEquals(2, model.predict(2),0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testExceptionThrownIfShortSamplePointsSize() {
		new SimpleRegression(Arrays.asList(new Point(2,3)));
	}
}
