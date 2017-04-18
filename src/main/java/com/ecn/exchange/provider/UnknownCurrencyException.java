package com.ecn.exchange.provider;

public class UnknownCurrencyException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	public UnknownCurrencyException(final String currency){
		super(String.format("Unknown currency: %s", currency));
	}
}
