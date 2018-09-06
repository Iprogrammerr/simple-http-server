package com.iprogrammerr.bright.server.request;

import com.iprogrammerr.bright.server.configuration.ServerConfiguration;

public class ConfigurableCors implements Cors {

    private static final String ALLOW_ALL = "*";
    private static final String ACCESS_CONTROL_ORIGIN = "Origin";
    private static final String ACCESS_CONTROL_REQUEST_HEADERS = "Access-Control-Request-Headers";
    private static final String ACCESS_CONTROL_REQUEST_METHOD = "Access-Control-Request-Method";
    private String accessControllAllowOrigin;
    private String accessControllAllowHeaders;
    private String accessControllAllowMethods;

    public ConfigurableCors(String accessControllAllowOrigin, String accessControllAllowHeaders,
	    String accessControllAllowMethods) {
	this.accessControllAllowOrigin = accessControllAllowOrigin;
	this.accessControllAllowHeaders = accessControllAllowHeaders;
	this.accessControllAllowMethods = accessControllAllowMethods;
    }

    public ConfigurableCors(ServerConfiguration serverConfiguration) {
	this(serverConfiguration.allowedOrigin(), serverConfiguration.allowedHeaders(),
		serverConfiguration.allowedMethods());
    }

    @Override
    public boolean validate(Request request) {
	boolean haveRequiredHeaders = request.hasHeader(ACCESS_CONTROL_ORIGIN)
		&& request.hasHeader(ACCESS_CONTROL_REQUEST_HEADERS)
		&& request.hasHeader(ACCESS_CONTROL_REQUEST_METHOD);
	if (!haveRequiredHeaders) {
	    System.out.println("There is no required headers!");
	    return false;
	}
	try {
	    boolean originAllowed = accessControllAllowOrigin.equals(ALLOW_ALL)
		    || accessControllAllowOrigin.equals(request.header(ACCESS_CONTROL_ORIGIN));
	    if (!originAllowed) {
		return false;
	    }
	    boolean methodAllowed = accessControllAllowMethods.equals(ALLOW_ALL)
		    || accessControllAllowMethods.contains(request.header(ACCESS_CONTROL_REQUEST_METHOD));
	    if (!methodAllowed) {
		return false;
	    }
	    boolean headersAllowed = accessControllAllowHeaders.equals(ALLOW_ALL)
		    || accessControllAllowHeaders.contains(request.header(ACCESS_CONTROL_REQUEST_HEADERS));
	    if (!headersAllowed) {
		return false;
	    }
	} catch (Exception exception) {
	    return false;
	}
	return true;
    }

}
