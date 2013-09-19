package com.epam.ta.exception;

public abstract class TATechnicalException extends TAException {
	private static final long serialVersionUID = 4441235527304074970L;

	public TATechnicalException(Exception e) {
		super(e);
	}
}
