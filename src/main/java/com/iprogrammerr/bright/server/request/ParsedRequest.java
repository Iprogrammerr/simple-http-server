package com.iprogrammerr.bright.server.request;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.iprogrammerr.bright.server.header.Header;

public final class ParsedRequest implements Request {

	private String url;
	private final String method;
	private final List<Header> headers;
	private final byte[] body;

	public ParsedRequest(String url, String method, List<Header> headers, byte[] body) {
		this.url = url;
		this.method = method;
		this.headers = headers;
		this.body = body;
	}

	public ParsedRequest(String url, String method, List<Header> headers) {
		this(url, method, headers, new byte[0]);
	}

	public ParsedRequest(String url, String method) {
		this(url, method, new ArrayList<>());
	}

	@Override
	public String url() {
		return this.url;
	}

	@Override
	public String method() {
		return this.method;
	}

	@Override
	public List<Header> headers() {
		return this.headers;
	}

	@Override
	public byte[] body() {
		return this.body;
	}

	@Override
	public boolean hasHeader(String key) {
		for (Header header : this.headers) {
			if (header.is(key)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String header(String key) {
		String header = "";
		for (Header h : this.headers) {
			if (h.is(key)) {
				header = h.value();
				break;
			}
		}
		return header;
	}

	@Override
	public void removeContext(String context) {
		if (this.url.startsWith(context) && !context.isEmpty()) {
			this.url = this.url.replace(context + "/", "");
		}

	}

	@Override
	public boolean equals(Object object) {
		boolean equal;
		if (!Request.class.isAssignableFrom(object.getClass())) {
			equal = false;
		} else if (object == this) {
			equal = true;
		} else {
			Request other = (Request) object;
			equal = this.url.equals(other.url()) && this.method.equalsIgnoreCase(other.method())
					&& Arrays.equals(this.body, other.body());
			if (equal && this.headers.size() == other.headers().size()) {
				Collections.sort(this.headers, (f, s) -> f.key().compareTo(s.key()));
				Collections.sort(other.headers(), (f, s) -> f.key().compareTo(s.key()));
				for (int i = 0; i < this.headers.size(); ++i) {
					if (!this.headers.get(i).equals(other.headers().get(i))) {
						equal = false;
						break;
					}
				}
			}
		}
		return equal;
	}

	@Override
	public String toString() {
		return "ParsedRequest [url=" + url + ", method=" + method + ", headers=" + headers + ", body size="
				+ body.length + "]";
	}
}
