package com.eaf.orig.shared.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.eaf.orig.cache.properties.IntSchemeCacheProperties;
//import com.eaf.orig.shared.dao.ORIGDAOFactory;
//import com.eaf.orig.shared.dao.utility.UtilityDAO;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

/**
 * Servlet implementation class RetrieveTierInfoForSchemeServlet
 */
public class RetrieveTierInfoForSchemeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(RetrieveTierInfoForSchemeServlet.class); 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RetrieveTierInfoForSchemeServlet() {
        super();
        // TODO Auto-generated constructor stub
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
		try {
            String schemeCode = (String) request.getParameter("scheme_code");
//            UtilityDAO dao = ORIGDAOFactory.getUtilityDAO();
//            IntSchemeCacheProperties data = dao.getIntSchemeForCode(schemeCode);
            
            IntSchemeCacheProperties data = PLORIGEJBService.getORIGDAOUtilLocal().getIntSchemeForCode(schemeCode);
            
            //Return xml
            StringBuffer sb = new StringBuffer("");
            sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?> \n");
            sb.append("<list>\n");
            sb.append("<field>");
            sb.append(data.getIntRate1()+"|"+data.getTerm1()+"|");
            sb.append(data.getIntRate2()+"|"+data.getTerm2()+"|");
            sb.append(data.getIntRate3()+"|"+data.getTerm3()+"|");
            sb.append(data.getIntRate4()+"|"+data.getTerm4());
            sb.append("</field>\n");
            sb.append("</list>");
            logger.info("Resp: "+sb.toString());

            response.setContentType("text/xml;charset=UTF-8");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache,no-store");
            response.setDateHeader("Expires", 0);
            PrintWriter pw = response.getWriter();
            pw.println(sb.toString());
            pw.close();
            
		 } catch (Exception e) {
	            logger.error("Error in RetrieveTierInfoForSchemeServlet >> ", e);
	     }
	}

}
