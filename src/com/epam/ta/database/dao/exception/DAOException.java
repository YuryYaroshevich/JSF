package com.epam.ta.database.dao.exception;

import com.epam.ta.exception.TATechnicalException;

public abstract class DAOException extends TATechnicalException {
	private static final long serialVersionUID = -3808566275931777733L;

	public DAOException(Exception e) {
		super(e);
	}
}
