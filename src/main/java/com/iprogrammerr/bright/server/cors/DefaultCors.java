package com.iprogrammerr.bright.server.cors;

import java.util.ArrayList;
import java.util.List;

import com.iprogrammerr.bright.server.header.Header;
import com.iprogrammerr.bright.server.request.Request;

public final class DefaultCors implements Cors {

    private final List<Header> headers;

    public DefaultCors() {
	this.headers = new ArrayList<>();
    }

    @Override
    public boolean isValid(Request request) {
	return true;
    }

    @Override
    public List<Header> toAddHeaders() {
	return this.headers;
    }

}
