package com.ecn.exchange.provider;

public class RateProviderException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RateProviderException(final Throwable e){
		super(e.getMessage(), e);
	}

	public RateProviderException(final String message, final Throwable e){
		super(message, e);
	}
}
