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
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.shared.utility.ORIGDisplayFormatUtil;
import com.eaf.orig.shared.utility.ORIGUtility;

public class LookupTitleNameServlet extends HttpServlet implements Servlet {
	Logger logger = Logger.getLogger(LookupTitleNameServlet.class);

	
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		ORIGFormHandler formHandler = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
		ORIGUtility utility = new ORIGUtility();
		try{
			ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
			
			String code = request.getParameter("code");
			String cacheName = request.getParameter("cacheName");
			String field1 = request.getParameter("fieldDesc");
			String filedRelate1 = request.getParameter("fieldrelate1");
			String filedRelate2 = request.getParameter("fieldrelate2");
			String btnName = request.getParameter("buttonName");
			logger.debug("code = "+code);
			logger.debug("cacheName = "+cacheName);
			logger.debug("field1 = "+field1);
			logger.debug("filedRelate1 = "+filedRelate1);
			logger.debug("fieldRelate2 = "+filedRelate2);
			Vector vDesc = new Vector();
			String description1 = "";
			String description2 = "";
			if(OrigConstant.CacheName.CACHE_NAME_TITLE_ENG.equals(cacheName) || OrigConstant.CacheName.CACHE_NAME_TITLE_THAI.equals(cacheName)){
				vDesc = cacheUtil.getORIGTitleCodeByDesc(cacheName, code);
			}
			if(vDesc.size()>0){
				description1 = ORIGDisplayFormatUtil.displayHTML(vDesc.get(0));
			}
			if(vDesc.size()>1){
				description2 = ORIGDisplayFormatUtil.displayHTML(vDesc.get(1));
			}
			
			logger.debug(">>>description1="+description1);
			logger.debug(">>>description2="+description2);
			
			response.setContentType("text/html;charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache,no-store");
			response.setDateHeader("Expires", 0);
			
			PrintWriter out = response.getWriter();
						
			out.println("<html>");
			out.println("<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\"> ");
			out.println("<head><title>Do not close</title></head><body> ");
			out.println("<script language=\"JavaScript\">");
			if(description1!=null && !description1.equals("")){
				out.println("if(window.opener.appFormName."+field1+"!=null){");
				out.println("window.opener.appFormName."+field1+".value='"+description1+"';");
				out.println("}");
				out.println("if(window.opener.appFormName."+filedRelate1+"!=null){");
				out.println("window.opener.appFormName."+filedRelate1+".value='"+description2+"';");
				out.println("}");
				out.println("if(window.opener.appFormName."+filedRelate2+"!=null){");
				out.println("window.opener.appFormName."+filedRelate2+".value='"+description1+"';");
				out.println("}");
			}else{
				out.println("if(window.opener.appFormName."+field1+"!=null){");
				out.println("window.opener.appFormName."+field1+".value='';");
				out.println("}");
				out.println("if(window.opener.appFormName."+filedRelate1+"!=null){");
				out.println("window.opener.appFormName."+filedRelate1+".value='';");
				out.println("}");
				out.println("if(window.opener.appFormName."+filedRelate2+"!=null){");
				out.println("window.opener.appFormName."+filedRelate2+".value='';");
				out.println("}");
				out.println("window.opener.appFormName."+btnName+".click();");
			}
			out.println("window.close();");
			
			out.println("</script>");
			out.println("</body></html>\n");
//			pw.write(description1);
//			pw.close();
		}catch(Exception e){
			logger.error("Error in LookupDescriptionServlet >> ", e);
		}
	}

}