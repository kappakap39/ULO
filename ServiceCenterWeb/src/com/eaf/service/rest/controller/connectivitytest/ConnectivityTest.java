package com.eaf.service.rest.controller.connectivitytest;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

import org.apache.http.client.methods.HttpPost;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/service/connectivitytest")
public class ConnectivityTest {

	private static transient Logger logger = Logger.getLogger(ConnectivityTest.class);
	
	@RequestMapping(value="/rest", method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<Object> rest(@RequestBody String requestString) {
		logger.debug("/service/connectivitytest/rest :: " + requestString);
		Object response = "N/A";
		try {
			JSONObject requestJson = new JSONObject(requestString);
			String url = requestJson.getString("url");
			String header = requestJson.getString("header");
			String data = requestJson.getString("data");
			logger.debug("url :: " + url);
			logger.debug("header :: " + header);
			logger.debug("data :: " + data);
			
			JSONObject headerJson = new JSONObject(header);
			MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
			Iterator keys = headerJson.keys();
			while(keys.hasNext()) {
				String key = (String) keys.next();
				String value = headerJson.getString(key);
				headers.add(key, value);
			}
			
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
			HttpEntity<String> request = new HttpEntity<>(data, headers);
			
			response = restTemplate.postForObject(url, request, Object.class);
		} catch(Exception e) {
			logger.fatal("ERROR " + e.getMessage());
		}
		return ResponseEntity.ok(response);
	}
	
	@RequestMapping(value="/soap", method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<Object> soap(@RequestBody String requestString) {
		logger.debug("/service/connectivitytest/soap :: " + requestString);
		Object response = "N/A";
		try {
			JSONObject requestJson = new JSONObject(requestString);
			String url = requestJson.getString("url");
			String header = requestJson.getString("header");
			String data = requestJson.getString("data");
			logger.debug("url :: " + url);
			logger.debug("header :: " + header);
			logger.debug("data :: " + data);
			
			URL obj = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
			conn.setRequestProperty("SOAPAction", url);
			
			JSONObject headerJson = new JSONObject(header);
			Iterator keys = headerJson.keys();
			while(keys.hasNext()) {
				String key = (String) keys.next();
				String value = headerJson.getString(key);
				conn.setRequestProperty(key, value);
			}
			
			conn.setDoOutput(true);
			java.io.OutputStreamWriter wr = new java.io.OutputStreamWriter(conn.getOutputStream());
			wr.write(data);
			wr.flush();
			
			java.io.BufferedReader rd = new java.io.BufferedReader(new java.io.InputStreamReader(conn.getInputStream()));
			String line = "";
			StringBuilder sb = new StringBuilder();
			while((line = rd.readLine()) != null) {
				sb.append(line).append("\n");
			}
			response = sb.toString();
		} catch(Exception e) {
			logger.fatal("ERROR " + e.getMessage());
		}
		return ResponseEntity.ok(response);
	}
	
}
