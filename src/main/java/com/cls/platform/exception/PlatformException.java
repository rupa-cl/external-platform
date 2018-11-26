/*
 * Copyright (c) 2018,  Cloud Lending, a Q2 Company.
 * All Rights Reserved.
 */

package com.cls.platform.exception;

public class PlatformException extends Exception {

    private static final long serialVersionUID = 1L;

	public PlatformException() {
        super();
    }

	public PlatformException(String message) {
        super(message);
    }

	public PlatformException(Throwable t) {
        super(t);
    }

	public PlatformException(String message, Throwable e) {
        super(message, e);
    }
}
