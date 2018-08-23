package com.iprogrammerr.simpleserver.model;

public class Header {

    private String key;
    private String value;

    public Header(String key, String value) {
	this.key = key;
	this.value = value;
    }

    public String getKey() {
	return key;
    }

    public String getValue() {
	return value;
    }

    public void setKey(String key) {
	this.key = key;
    }

    public void setValue(String value) {
	this.value = value;
    }

    @Override
    public String toString() {
	return "Header [key=" + key + ", value=" + value + "]";
    }

}
