package com.eaf.service.common.utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class ResponseUtils {
	public enum STATUS {
		SUCCESS, FAILED
	}

	private ResponseUtils() {
	}

	public static void sendJsonResponse(HttpServletResponse response, Object object) throws IOException {
		response.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();

		response.setContentType("text/plain");
		PrintWriter pw = response.getWriter();
		pw.print(gson.toJson(object));;
		pw.flush();
		
	}
}
