package com.ecn.exchange.regression;

import java.util.ArrayList;
import java.util.List;

//TODO This class can be implemented more generic, but for simplicity I just implement as lag1
public class TimeSeriesLag {

	private TimeSeriesLag() {

	}

	public static List<Point> lag(final List<Double> values) {
		final List<Double> predictors = values.subList(0, values.size() - 1);
		final List<Double> outputs = values.subList(1, values.size());

		// TODO I don't like this way, but it seems Java does not support zip
		// function as Scala does. That's too bad for me :(
		final List<Point> points = new ArrayList<>();
		for (int i = 0; i < predictors.size(); i++) {
			points.add(new Point(predictors.get(i), outputs.get(i)));
		}

		return points;
	}
}
