package com.iprogrammerr.simple.http.server.model;

import java.util.ArrayList;
import java.util.List;

import com.iprogrammerr.simple.http.server.constants.ResponseCode;

public class Response {

    private final List<Header> headers = new ArrayList<>();
    private byte[] body;
    private ResponseCode code = ResponseCode.NOT_FOUND;

    public List<Header> getHeaders() {
	return headers;
    }

    public void setBody(byte[] body) {
	this.body = body;
    }

    public byte[] getBody() {
	return body;
    }

    public ResponseCode getCode() {
	return code;
    }

    public void addHeader(Header header) {
	headers.add(header);
    }

    public void setCode(ResponseCode code) {
	this.code = code;
    }

    public boolean hasBody() {
	return body != null && body.length > 0;
    }

}