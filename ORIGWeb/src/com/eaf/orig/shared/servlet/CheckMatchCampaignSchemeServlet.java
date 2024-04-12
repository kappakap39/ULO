package com.eaf.orig.shared.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Vector;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.eaf.cache.TableLookupCache;
import com.eaf.orig.cache.properties.CampaignSchemeCacheProperties;

public class CheckMatchCampaignSchemeServlet extends HttpServlet implements Servlet {
    Logger logger = Logger.getLogger(CheckMatchCampaignSchemeServlet.class);
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public CheckMatchCampaignSchemeServlet() {
		super();
	}

	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest arg0, HttpServletResponse arg1)
	 */
	protected void doGet(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
	    doPost(arg0,arg1);
	}

	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest arg0, HttpServletResponse arg1)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String campaign = (String)request.getParameter("campaign");
            String scheme_code = (String)request.getParameter("scheme_code");
            String isMatch = "0";
            
    		HashMap h = TableLookupCache.getCacheStructure();
    		Vector dataVect = (Vector) (h.get("CampaignScheme"));
			for(int i=0; i<dataVect.size(); i++){
			    CampaignSchemeCacheProperties mktSchemeM = (CampaignSchemeCacheProperties)dataVect.get(i);
			    if (mktSchemeM!=null){
					if(mktSchemeM.getMktCode()!=null && mktSchemeM.getMktCode().equals(campaign)){
						if(mktSchemeM.getSchemeCode()!=null && mktSchemeM.getSchemeCode().equals(scheme_code)){
						    isMatch = "1";
						    break;
						}
					}
			    }
			}

            StringBuffer sb = new StringBuffer("");
            sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            sb.append("<list>");
            sb.append("<field name=\"result\">"+ isMatch + "</field>");
            sb.append("</list>");
            response.setContentType("text/xml;charset=UTF-8");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache,no-store");
            response.setDateHeader("Expires", 0);
            PrintWriter pw = response.getWriter();
            pw.write(sb.toString());
            pw.close();
        }catch (Exception e) {
            logger.error("Error in CalculatePLoanServlet >> ", e);
        }
	}

}