package com.eaf.orig.shared.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.model.CollateralDataM;
import com.eaf.orig.shared.model.FeeInformationDataM;
import com.eaf.orig.shared.utility.ORIGUtility;

public class DeleteMGFeeInformationServlet extends HttpServlet implements Servlet {
	Logger logger = Logger.getLogger(DeleteMGFeeInformationServlet.class);
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public DeleteMGFeeInformationServlet() {
		super();
	}

	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest arg0, HttpServletResponse arg1)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    doPost(request, response);
	}

	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest arg0, HttpServletResponse arg1)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String MGfeeSeq = request.getParameter("MGfeeSeq");
	    logger.debug("MGfeeSeq ->" + MGfeeSeq);
	    if(MGfeeSeq != null && !"".equals(MGfeeSeq)){
			String[] arMGfeeSeq = MGfeeSeq.split(",");
	    	CollateralDataM collateralDataM = (CollateralDataM)request.getSession().getAttribute("COLLATERAL_POPUP_DATA");
	    	
	    	for (int i=arMGfeeSeq.length-1;i>=0;i--){
	    	    collateralDataM.getFeeInformationVect().removeElementAt(Integer.parseInt(arMGfeeSeq[i].trim()));
	    	}
	    	
	    	for (int i=0;i<collateralDataM.getFeeInformationVect().size();i++){
	    	    FeeInformationDataM feeInformationDataM = (FeeInformationDataM)collateralDataM.getFeeInformationVect().get(i);
	    	    feeInformationDataM.setSeq(i);
	    	}

	        ORIGUtility utility = new ORIGUtility();
	        String tableData = utility.getFeeInformationTable(collateralDataM.getFeeInformationVect(), request);

			response.setContentType("text/xml;charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache,no-store");
			response.setDateHeader("Expires", 0);
			PrintWriter pw = response.getWriter();
			pw.write(tableData.toString());
			pw.close();
			
	    	request.getSession(true).setAttribute("COLLATERAL_POPUP_DATA",collateralDataM);
	    }
	}

}