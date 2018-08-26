package com.iprogrammerr.simple.http.server.example;

import java.util.ArrayList;
import java.util.List;

import com.iprogrammerr.simple.http.server.constants.RequestMethod;
import com.iprogrammerr.simple.http.server.constants.ResponseCode;
import com.iprogrammerr.simple.http.server.controller.Controller;
import com.iprogrammerr.simple.http.server.model.Header;
import com.iprogrammerr.simple.http.server.resolver.RequestResolver;

public class SimpleController implements Controller {

    private static final String MAIN_PATH = "simple";
    private final List<RequestResolver> requestsResolvers = new ArrayList<>();

    public SimpleController() {
	requestsResolvers.add(getSimpleGetResolver());
	requestsResolvers.add(getSimplePostResolver());
    }

    private RequestResolver getSimpleGetResolver() {
	return new RequestResolver(MAIN_PATH + "/user/{id:int}/search?scale=double", RequestMethod.GET,
		(request, response) -> {
		    System.out.println("SimpleController.getSimpleGetResolver()");
		    int id = request.getPathVariable("id", Integer.class);
		    System.out.println("Have id = " + id);
		    double scale = request.getParameter("scale", Double.class);
		    System.out.println("Have scale = " + scale);
		    String text = "{\"message\": \"Hello\"!}";
		    response.setContentTypeHeader(Header.createJsonContentType());
		    response.setBody(text.getBytes());
		    response.setCode(ResponseCode.OK);
		});
    }

    private RequestResolver getSimplePostResolver() {
	return new RequestResolver(MAIN_PATH + "/user", RequestMethod.POST, (request, response) -> {
	    System.out.println("SimpleController.getSimplePostResolver()");
	    String text = "Hello!";
	    response.setBody(text.getBytes());
	    response.setCode(ResponseCode.OK);
	});
    }

    @Override
    public List<RequestResolver> getRequestResolvers() {
	return requestsResolvers;
    }

}
