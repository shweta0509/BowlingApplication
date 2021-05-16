package com.target.game.exception;

public class BowlingException extends RuntimeException {

	public BowlingException() {
		super();
	}

	public BowlingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public BowlingException(String message, Throwable cause) {
		super(message, cause);
	}

	public BowlingException(String message) {
		super(message);
	}

	public BowlingException(Throwable cause) {
		super(cause);
	}

}
