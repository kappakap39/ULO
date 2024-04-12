package flp.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

import flp.controller.ResponseStatController;
import flp.iib.IIBResponseTime;
import flp.model.report.ReportInput;

/**
 * Servlet implementation class ReportTransaction
 */
@WebServlet("/reportTransaction")
public class ReportTransaction extends HttpServlet {
	
	private static transient Logger logger = Logger.getLogger(ReportTransaction.class);
	
	private static final long serialVersionUID = 1L;

    public ReportTransaction() {

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		BufferedReader br = new BufferedReader( new InputStreamReader( request.getInputStream() ) );
		
		String json = "";
		
		if ( null != br ) {
			json = br.readLine();
		}
		
		logger.info("json input 1 : " + json);
		
		System.out.println("json input 2 : " + json);
		
		Gson gson = new Gson();
		
		ReportInput report = gson.fromJson(json, ReportInput.class);
		
		IIBResponseTime iib = new IIBResponseTime();
		
		ResponseStatController statController = iib.getProcessTimeResponse( report );
		
		System.out.println( "json out : " + gson.toJson( statController ) );
		
		response.setContentType("application/json");
		
		response.getWriter().write( gson.toJson( statController ) );
		
	}

}
