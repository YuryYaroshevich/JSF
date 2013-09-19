package com.epam.ta.database.pool.exception;

import com.epam.ta.exception.TATechnicalException;

public class ConnectionPoolException extends TATechnicalException {
	private static final long serialVersionUID = -704878851490201700L;

	public ConnectionPoolException(Exception e) {
		super(e);
	}
}
