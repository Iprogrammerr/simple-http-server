package com.iprogrammerr.bright.server.method;

public class OptionsMethod implements RequestMethod {

    private static final String OPTIONS = "OPTIONS";

    @Override
    public boolean is(String requestMethod) {
	return OPTIONS.equalsIgnoreCase(requestMethod);
    }

}