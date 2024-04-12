package com.eaf.core.ulo.common.rest;

//import java.io.IOException;
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.sql.Timestamp;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.Set;

//import org.apache.log4j.Logger;
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.restlet.Client;
//import org.restlet.Context;
//import org.restlet.Request;
//import org.restlet.Response;
//import org.restlet.data.ChallengeResponse;
//import org.restlet.data.ChallengeScheme;
//import org.restlet.data.CharacterSet;
//import org.restlet.data.Form;
//import org.restlet.data.MediaType;
//import org.restlet.data.Parameter;
//import org.restlet.data.Preference;
//import org.restlet.data.Protocol;
//import org.restlet.data.Status;
//import org.restlet.engine.header.Header;
//import org.restlet.engine.header.HeaderConstants;
//import org.restlet.ext.json.JsonRepresentation;
//import org.restlet.representation.Representation;
//import org.restlet.util.Series;

//import bpm.rest.client.authentication.AuthenticationTokenHandler;
//import bpm.rest.client.authentication.AuthenticationTokenHandlerException;

//import com.eaf.core.ulo.common.cache.InfBatchProperty;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;

@Deprecated
public class RESTClient {
//	private static transient Logger logger = Logger.getLogger(RESTClient.class);
//	private String readTimeout = InfBatchProperty.getInfBatchConfig("REST_READ_TIME_OUT");
//	private String socketConnectTimeoutMs = InfBatchProperty.getInfBatchConfig("REST_CONNECTION_TIME_OUT");	
//	public RESTClient(){
//		super();
//	}
//	private void addAuthenticationChallengeResponse(Request request,AuthenticationTokenHandler handler) {
//		// Add the client authentication to the request
//		ChallengeScheme scheme = ChallengeScheme.HTTP_BASIC;
//		ChallengeResponse authentication = new ChallengeResponse(scheme,
//				handler.getUserid(), handler.getPassword());
//		request.setChallengeResponse(authentication);
//	}
//	public RESTResponse executeRESTCall(String url) throws RESTException, AuthenticationTokenHandlerException{
//		RESTRequest restRequest = new RESTRequest(url);
//		return executeRESTCall(restRequest,null);
//	}
//	public RESTResponse executeRESTCall(String url,Object requestObject) throws RESTException, AuthenticationTokenHandlerException{
//		RESTRequest restRequest = new RESTRequest(url);
//		restRequest.setRequestObject(requestObject);
//		return executeRESTCall(restRequest,null);
//	}
//	public RESTResponse executeRESTCall(RESTRequest restRequest) throws RESTException, AuthenticationTokenHandlerException{	
//		return executeRESTCall(restRequest,null);
//	}
//	public RESTResponse executeRESTCall(RESTRequest restRequest,AuthenticationTokenHandler handler) throws RESTException, AuthenticationTokenHandlerException{		
//		RESTResponse restResponse = new RESTResponse();		
//		String url = buildURL(restRequest.getUrl(),null);
//		logger.info("HTTP call: "+url);
//		Request request = new Request(restRequest.getMethod(),url);
//		// The following is to avoid 411 error on certain servers that
//		// require a content-length to be specified
//		Object requestObject = restRequest.getRequestObject();
//		if(null == requestObject){
//			request.setEntity("\n", MediaType.TEXT_PLAIN);
//		}else{
//			try{
////				#rawi add TimestampTypeAdapter,DateTypeAdapter for convert format java.sql.Timestamp,java.sql.Date type
//				GsonBuilder gsonBuilder = new GsonBuilder();
//				gsonBuilder.registerTypeAdapter(Timestamp.class,new TimestampTypeAdapter());
//				gsonBuilder.registerTypeAdapter(java.sql.Date.class,new DateTypeAdapter());
//				Gson gson = gsonBuilder.create();
//				String entity = gson.toJson(requestObject);
//				logger.debug("entity >> "+entity);
//				request.setEntity(entity, MediaType.APPLICATION_JSON);
//			}catch(Exception e){
//				logger.fatal("ERROR",e);
//			}
//		}
//		if(null != handler){
//			if (handler.foundAuthenticationToken()) {
//				// Add authentication token to request
//				handler.addAuthenticationToken(request);
//			} else if (!handler.isUsingUserIdentityInContainer()) {
//				// Add userid and password to request only if not using user
//				// identity in the container
//				addAuthenticationChallengeResponse(request,handler);
//			}
//		}
//		// Indicates the client preferences and let the server handle the best
//		// representation with content negotiation.
//		request.getClientInfo().getAcceptedMediaTypes().add(new Preference<MediaType>(MediaType.APPLICATION_JSON));
//		Client httpClient = null;
//		try {
//			Protocol protocol = ((url).toUpperCase().contains("HTTPS")) ? Protocol.HTTPS : Protocol.HTTP;
//			logger.info("Protocol >> "+protocol);
//			Context ctx = new Context();
//			Series<Parameter> pa = ctx.getParameters();
//			logger.info("readTimeout >> "+readTimeout);
//			logger.info("socketConnectTimeoutMs >> "+socketConnectTimeoutMs);
//			pa.add(new Parameter("readTimeout", readTimeout));
//			pa.add(new Parameter("socketConnectTimeoutMs", socketConnectTimeoutMs));
//			httpClient = new Client(ctx, protocol);
//			// Ask the HTTP client connector to handle the call
//			Response response = httpClient.handle(request);
//			JSONObject jsonResponse = processResponse(response);
//			restResponse.setJsonResponse(jsonResponse);
//		} catch (RESTException lcException) {
//			throw (lcException);
//		}finally{
//			try{
//				if(httpClient != null)httpClient.stop();
//			}catch(Exception e){
//				logger.fatal("ERROR",e);
//			}
//		}
//		return restResponse;
//	}
//
//	private JSONObject processResponse(Response response) throws RESTException {
//		try{
//			if (response.getStatus().isSuccess()) {
//				logger.info("HTTP request was successfully processed by the server.");
//				return getJSONObject(response);
//			} else if (response.getStatus().equals(Status.CLIENT_ERROR_FORBIDDEN)|| response.getStatus().equals(Status.CLIENT_ERROR_UNAUTHORIZED)) {
//				// Unauthorized access
//				logger.info("Could not authenticate user!");
//				throw new RESTException("The server could not authenticate the user or the user does not have enough rights to access the requested resource.",
//						response.getStatus().toString(), response.getStatus().getCode());
//			} else {
//				logResponseData(response);
//				// Unexpected status
//				JSONObject jsonObject = getJSONObject(response);
//				throw new RESTException(jsonObject.getJSONObject("Data").getString("errorMessage"));
//			}
//		}catch(JSONException je){
//			throw new RESTException(je);
//		}
//	}
//
//	private JSONObject getJSONObject(Response response) throws RESTException {
//		try{
//			if(isResponseJson(response)){
//				Representation rep = response.getEntity();
//				rep.setCharacterSet(CharacterSet.UTF_8);
//				JsonRepresentation jsonRep = new JsonRepresentation(rep);
//				JSONObject jsonObj = jsonRep.getJsonObject();
//				return jsonObj;
//			}else{
//				String respEntity = response.getEntityAsText();
//				logger.info("Non-JSON response entity\n---\n"+ respEntity + "\n---");
//				throw new RESTException("An error occurred (and server did not return JSON). Additional error information follows: "
//								+ response, response.getStatus().toString(),response.getStatus().getCode());
//			}
//		}catch(IOException|JSONException je){
//			throw new RESTException(je);
//		}
//	}
//
//	private boolean isResponseJson(Response response) {
//		Map<String, Object> attributes = response.getAttributes();
//		@SuppressWarnings("unchecked")
//		Series<Header> header = (Series<Header>) attributes.get(HeaderConstants.ATTRIBUTE_HEADERS);
//		if (header != null) {
//			String contentType = header.getValues("Content-Type");
//			if (contentType != null) {
//				return (contentType.contains("application/json"));
//			}
//		}
//		return false;
//	}
//
//	private String buildURL(String urlPath, Map<String, Object> arguments) throws RESTException{
//		try{
//			URI uriObj = new URI(urlPath);
//			String url = uriObj.toASCIIString();
//			String query = encodeArguments(arguments, true);
//			return appendQuery(url, query);
//		}catch(URISyntaxException e){
//			throw new RESTException(e);
//		}
//	}
//
//	private String appendQuery(String path, String query) {
//		return (query != null && query.length() > 0) ? (path + '?' + query): path;
//	}
//
//	private String encodeArguments(Map<String, Object> arguments,boolean includeNullValues) throws RESTException {
//		if(arguments == null){
//			return null;
//		}
//		try{
//			Form form = new Form();
//			Iterator<String> keys = arguments.keySet().iterator();
//			while(keys.hasNext()){
//				String key = keys.next();
//				Object objValue = arguments.get(key);
//				if(!includeNullValues){
//					if(objValue == null){
//						continue;
//					}
//				}
//				if(objValue == null){
//					form.add(key, JSONObject.valueToString(null));
//					continue;
//				}
//				Object[] values = (objValue.getClass().isArray())?(Object[])objValue:new Object[]{objValue};
//				for(Object value : values){
//					String strValue = (value instanceof JSONObject) ? JSONObject.valueToString(value) : value.toString();
//					form.add(key, strValue);
//				}
//			}
//			return form.getQueryString();
//		}catch(JSONException e){
//			throw new RESTException(e);
//		}
//	}
//
//	private void logResponseData(Response response) {
//		StringBuilder sb = new StringBuilder();
//		
//		@SuppressWarnings("unchecked")
//		Series<Header> hSeries = (Series<Header>) response.getAttributes().get(HeaderConstants.ATTRIBUTE_HEADERS);
//		if (hSeries != null) {
//			Set<String> hNames = hSeries.getNames();
//			if (hNames != null) {
//				sb.append("\nResponse Headers:\n");
//				for (String hName : hNames) {
//					Header thisHeader = hSeries.getFirst(hName);
//					sb.append("   name=[" + thisHeader.getName() + "] value=["+ thisHeader.getValue() + "]\n");
//				}
//				sb.append("End Response headers");
//			}
//		}
//		
//		sb.append("\nResponse status: ");
//		sb.append(response.getStatus().getDescription());
//
//		sb.append("\nResponse status code: ");
//		sb.append(response.getStatus().getCode());
//
//		sb.append("\nResponse phrase: ");
//		sb.append(response.getStatus().getReasonPhrase());
//
//		sb.append("\nResponse: ");
//		sb.append(response.getStatus().toString());
//
//		if (sb.length() > 0) {
//			logger.info("HTTP response: " + sb.toString());
//		}
//
//		if (response.getStatus().getThrowable() != null) {
//			Throwable error = response.getStatus().getThrowable();
//			StringBuffer exceptionHeader = new StringBuffer();
//			exceptionHeader.append("\nException info:\n");
//			exceptionHeader.append("Exception message: ");
//			exceptionHeader.append(error.getMessage());
//			logger.info(exceptionHeader.toString());
//			do {
//				logger.info("Exception stack trace: ", error);
//				error = error.getCause();
//			} while (error != null);
//			logger.info("\nEnd Exception info");
//		}
//	}
}
