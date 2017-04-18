package com.ecn.exchange.serialize;

public class JsonFormatException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public JsonFormatException(Throwable e){
		super(e.getMessage(), e);
	}
	
	public JsonFormatException(final String message, final Throwable e){
		super(message, e);
	}
}
