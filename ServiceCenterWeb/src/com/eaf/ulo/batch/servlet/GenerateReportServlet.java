package com.eaf.ulo.batch.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.api.InfBatchManager;
import com.eaf.core.ulo.common.model.InfBatchRequestDataM;
import com.eaf.ulo.cache.constant.CacheConstant;


@WebServlet("/GenerateReportServlet")
public class GenerateReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static transient Logger logger = Logger.getLogger(GenerateReportServlet.class);	
	public GenerateReportServlet() {
        super();
    }	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		System.out.println("GenerateReportServlet");
		String batchId = request.getParameter("batchId");
		System.out.println("batchId = "+batchId);
		InfBatchRequestDataM infBatchRequest = new InfBatchRequestDataM();
			infBatchRequest.setBatchId(batchId);
			infBatchRequest.setLoadCache(true);
			infBatchRequest.setRuntime(CacheConstant.Runtime.SERVER);		
		InfBatchManager.execute(infBatchRequest);		
		System.out.println("Success");
	}
}
