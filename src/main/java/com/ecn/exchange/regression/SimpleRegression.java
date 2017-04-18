package com.ecn.exchange.regression;

import java.util.List;

public class SimpleRegression {

	private double slope;
	private double intercept;

	private List<Point> samplePoints;
	private double xMean;
	private double yMean;
	
	private boolean computed = false;

	public SimpleRegression(final List<Point> samplePoints) {

		if (samplePoints.size() <= 2) {
			throw new IllegalArgumentException("Model is not trained with sample points size less than 3");
		}

		this.samplePoints = samplePoints;
		xMean = samplePoints.stream().mapToDouble(p -> p.getX()).sum()/samplePoints.size();
		yMean = samplePoints.stream().mapToDouble(p -> p.getY()).sum()/samplePoints.size();
	}

	private double covariance() {
		final double product = samplePoints.stream()
				.map(p -> new Point(p.getX() - xMean, p.getY() - yMean))
				.mapToDouble(p -> p.getX() * p.getY()).sum();
		
		return product / (samplePoints.size() -1);
	}

	private double varianceX() {
		final double sumOfSquaredDeviations = samplePoints.stream()
				.mapToDouble(p -> Math.pow(p.getX() - xMean, 2))
				.sum();
		
		return sumOfSquaredDeviations / (samplePoints.size() - 1);
	}

	public void build() {
		if(computed) {
			return;
		}
		slope = covariance() / varianceX();
		intercept = yMean - slope * xMean;
		computed = true;
	}
	
	public double predict(final double x) {
		build();
		return intercept + x*slope;
	}

	public double getSlope() {
		return slope;
	}

	public double getIntercept() {
		return intercept;
	}
}
