package com.ecn.exchange.provider;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.ecn.exchange.serialize.RateParser;

public class FixerSampleRatesProvider {

	private static final String REST_URL = "http://api.fixer.io/";

	// TODO In production, I would use pooling manager to manage resources. In
	// this test, for simplicity I would open and close resources
	private final CloseableHttpClient httpClient = HttpClients.createDefault();
	
	private RateParser parser = new RateParser();

	// TODO This method could be enhanced to make it more generic. Currently, I
	// assume that we only need API to get month samples for a specific day in a
	// specific year
	public List<Double> provideMonthlySamplesAtDay(final int day, final int year, final String fromCurrency,
			final String targetCurrency) {

		//TODO For simplicity, I ignored validation of year
		final List<LocalDate> sampleDates = new ArrayList<LocalDate>();
		LocalDate date = LocalDate.of(year, Month.JANUARY, day);

		while (date.isBefore(LocalDate.of(year, Month.DECEMBER, day + 1))) {
			sampleDates.add(date);
			date = date.plusMonths(1);
		}

		return fetchExchangeRates(sampleDates, fromCurrency, targetCurrency);
	}

	private List<Double> fetchExchangeRates(final List<LocalDate> sampleDates, final String fromCurrency,
			final String targetCurrency) {

		return sampleDates.stream()
				.map(sampleDate -> fetchExchangeRate(sampleDate, fromCurrency, targetCurrency))
				.collect(Collectors.toList());

	}

	private Double fetchExchangeRate(final LocalDate sampleDate, final String fromCurrency,
			final String targetCurrency) {

		try {
			final HttpGet fetchRequest = new HttpGet(String.format("%s/%s?base=%s&symbols=%s", REST_URL,
					sampleDate.toString(), fromCurrency, targetCurrency));

			final String response = httpClient.execute(fetchRequest, new ExchangeRateResponseHandler(fromCurrency));
			//FIXME This is only for debug purpose, have to be replaced by log.debug in PROD
			System.out.println(response);
			return parser.parseResponse(response, targetCurrency);
		} catch (final IOException e) {
			throw new RateProviderException(e);
		}
	}
	
	private static class ExchangeRateResponseHandler implements ResponseHandler<String> {

		private String baseCurrency;
		
		public ExchangeRateResponseHandler(final String baseCurrency) {
			this.baseCurrency = baseCurrency;
		}

		@Override
		public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
			final int status = response.getStatusLine().getStatusCode();
			if (status >= 200 && status < 300) {
				final HttpEntity entity = response.getEntity();
				return entity != null ? EntityUtils.toString(entity) : null;
			} else if (status == 422) {
				throw new UnknownCurrencyException(baseCurrency);
			} else {
				throw new ClientProtocolException("Unexpected response status: " + status);
			}
		}
	}
}
