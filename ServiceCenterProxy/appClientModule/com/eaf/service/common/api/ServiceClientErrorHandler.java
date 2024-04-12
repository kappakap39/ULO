package com.eaf.service.common.api;

import java.io.IOException;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

public class ServiceClientErrorHandler extends DefaultResponseErrorHandler{
	@Override
	public void handleError(ClientHttpResponse arg0) throws IOException {
		super.handleError(arg0);
	}
	@Override
	public boolean hasError(ClientHttpResponse arg0) throws IOException {
		return false;
	}
}
