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

import flp.iib.IIBResponseTime;
import flp.model.log.DataSegment;
import flp.model.log.TransactionLogInput;
import flp.model.log.TransactionLogOutput;

/**
 * Servlet implementation class MonitorBlobData
 */
@WebServlet("/monitorBlobData")
public class MonitorBlobData extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static transient Logger logger = Logger.getLogger(MonitorBlobData.class);
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MonitorBlobData() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader br = new BufferedReader( new InputStreamReader( request.getInputStream() ) );
		String json = "";
		if(null != br){
			json = br.readLine();
		}
		System.out.println("json input binary : " + json);
		Gson gson = new Gson();
		TransactionLogInput transactionInput = gson.fromJson(json, TransactionLogInput.class);
		IIBResponseTime iib = new IIBResponseTime();
		TransactionLogOutput transactionLogOutput = new TransactionLogOutput();
		DataSegment datasegment = iib.getBlobData(transactionInput);
		transactionLogOutput.setDatasegment(datasegment);
		System.out.println( "json out : " + gson.toJson( transactionLogOutput ) );
		
		response.setContentType("application/json");
		response.getWriter().write( gson.toJson( transactionLogOutput ) );
	}

}
