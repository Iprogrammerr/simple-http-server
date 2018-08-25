package com.iprogrammerr.simple.http.server.parser;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.iprogrammerr.simple.http.server.constants.RequestResponseConstants;
import com.iprogrammerr.simple.http.server.exception.RequestException;
import com.iprogrammerr.simple.http.server.model.Header;
import com.iprogrammerr.simple.http.server.model.Parameter;
import com.iprogrammerr.simple.http.server.model.Request;

public class RequestParser {

    private UrlParser urlParser;

    public RequestParser(UrlParser urlParser) {
	this.urlParser = urlParser;
    }

    public Request getRequest(InputStream inputStream) throws IOException {
	String[] requestLines = readRequest(inputStream);
	if (requestLines.length < 1
		|| requestLines[0].length() < RequestResponseConstants.MIN_VALID_FIRST_LINE_LENGTH) {
	    throw new RequestException();
	}
	System.out.println("First line = " + requestLines[0]);
	String method = getMethod(requestLines[0]);
	System.out.println("Method = " + method);
	String path = getPath(requestLines[0]);
	System.out.println("path = " + path);
	List<Parameter> parameters = urlParser.getParameters(path);
	List<Header> headers = new ArrayList<>();
	int i;
	for (i = 1; i < requestLines.length; i++) {
	    String line = requestLines[i];
	    if (line.isEmpty() || line.equals(RequestResponseConstants.HEADERS_BODY_PARSED_SEPARATOR)) {
		break;
	    }
	    headers.add(getHeader(line));
	}
	byte[] body = new byte[0];
	if ((i + 1) < requestLines.length
		&& requestLines[i].equals(RequestResponseConstants.HEADERS_BODY_PARSED_SEPARATOR)) {
	    body = requestLines[i + 1].getBytes();
	}
	return new Request(method, path, headers, parameters, body);
    }

    private String[] readRequest(InputStream inputStream) throws IOException {
	BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
	int bytesToRead = bufferedInputStream.available();
	byte[] bytes = new byte[bytesToRead];
	bufferedInputStream.read(bytes, 0, bytes.length);
	String request = new String(bytes);
	if (request.isEmpty()) {
	    throw new RequestException();
	}
	return request.split(RequestResponseConstants.NEW_LINE_SEPARATOR);
    }

    private String getMethod(String firstLine) {
	int indexOfSeparator = firstLine.indexOf("/");
	if (indexOfSeparator <= RequestResponseConstants.MIN_REQUEST_METHOD_LENGTH) {
	    throw new RequestException();
	}
	return firstLine.substring(0, indexOfSeparator - 1);
    }

    private String getPath(String firstLine) {
	int indexOfSeparator = firstLine.indexOf("/");
	if (indexOfSeparator <= RequestResponseConstants.MIN_REQUEST_METHOD_LENGTH) {
	    throw new RequestException();
	}
	int indexOfHttp = firstLine.indexOf("HTTP");
	if (indexOfHttp <= RequestResponseConstants.MIN_REQUEST_METHOD_LENGTH) {
	    throw new RequestException();
	}
	return firstLine.substring(indexOfSeparator + 1, indexOfHttp).trim();
    }

    private Header getHeader(String headerToParse) {
	String[] keyValue = headerToParse.split(":");
	if (keyValue.length < 2) {
	    throw new RequestException();
	}
	return new Header(keyValue[0], keyValue[1]);
    }

}