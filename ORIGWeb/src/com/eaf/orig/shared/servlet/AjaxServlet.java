package com.eaf.orig.shared.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;

public class AjaxServlet extends HttpServlet implements Servlet {
    
    Logger log = Logger.getLogger(this.getClass());
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public AjaxServlet() {
		super();
	}

	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest arg0, HttpServletResponse arg1)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    doPost(req,resp);
	}

	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest arg0, HttpServletResponse arg1)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	    String xmlStr = "";
	    String classPackage = (String)req.getParameter("ClassPackage");
	    if(classPackage==null || "".equals(classPackage)){
	        classPackage = "com.eaf.orig.shared.ajax";
	    }
	    String className = classPackage+"."+(String)req.getParameter("ClassName");
	    log.info(">>> find Class Name="+className);
	    AjaxDisplayGenerateInf ajaxDisplay = null; 
	    try{
	    	ajaxDisplay = (AjaxDisplayGenerateInf)Class.forName(className).newInstance();
		} catch (Exception ex) {
			log.fatal("AjaxServlet: error loading " + className + " :" + ex);
		}
		
		if(ajaxDisplay!=null){
			try {
				xmlStr = ajaxDisplay.getDisplayObject(req);
			} catch (PLOrigApplicationException e){
				log.fatal("Error ",e);
			}
		}
		
//		log.debug(">>> Return xmlStr="+xmlStr);
		if (xmlStr!=null){
		    resp.setContentType("text;charset=UTF-8");
		    resp.setHeader("Cache-Control", "no-cache");
		    PrintWriter pw = resp.getWriter();
		    pw.write(xmlStr);
			pw.close();
		}
	}

}