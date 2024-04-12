package com.eaf.orig.shared.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.CollateralDataM;
import com.eaf.orig.shared.utility.ORIGUtility;

public class DeleteMortgageServlet extends HttpServlet implements Servlet {
    Logger logger = Logger.getLogger(DeleteMortgageServlet.class);
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public DeleteMortgageServlet() {
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
	    String MGloanSeq = request.getParameter("MGloanSeq");
	    logger.debug("MGloanSeq ->" + MGloanSeq);
	    if(MGloanSeq != null && !"".equals(MGloanSeq)){
			String[] arMGloanSeq = MGloanSeq.split(",");
			ORIGFormHandler ORIGForm = (ORIGFormHandler) request.getSession().getAttribute("ORIGForm");
			
			ApplicationDataM applicationDataM = ORIGForm.getAppForm();
            Vector collateralV = applicationDataM.getCollateralVect();
            if(collateralV==null){
                collateralV = new Vector();
                applicationDataM.setCollateralVect(collateralV);
            }
            logger.debug("arMGloanSeq.length ->" + arMGloanSeq.length);
            logger.debug("arMGloanSeq ->" + arMGloanSeq);
            logger.debug("collateralV.size() ->" + collateralV.size());
	    	for (int i=arMGloanSeq.length;i>0;i--){
	    	    logger.debug("collateralV.removeElementAt ->" + arMGloanSeq[i-1].trim()+" -1");
	    	    collateralV.removeElementAt(Integer.parseInt(arMGloanSeq[i-1].trim())-1);
	    	}
	    	logger.debug("collateralV.size() after delete ->" + collateralV.size());
	    	for (int i=0;i<collateralV.size();i++){
	    	    CollateralDataM collateralDataM = (CollateralDataM)collateralV.get(i);
	    	    collateralDataM.setSeq(i+1);
	    	}

	        ORIGUtility utility = new ORIGUtility();
	        String tableData = utility.getCollateralTable(collateralV, request);

			response.setContentType("text/xml;charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache,no-store");
			response.setDateHeader("Expires", 0);
			PrintWriter pw = response.getWriter();
			pw.write(tableData.toString());
			pw.close();
			
	    }
	}

}