package com.eaf.orig.ulo.pl.ajax;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.utility.ORIGUtility;


public class AjaxDisplayServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	Logger logger = Logger.getLogger(this.getClass());
	
	public class Package{
		public static final int DEFAULT 			= 0;
		public static final int ORIG_SHARE 			= 1;
		public static final int ORIG_PL 			= 2;
		public static final int ORIG_RULES 			= 3;
		public static final int ORIG_PL_MANDATORY 	= 4;
		public static final int ORIG_PL_PRODUCT 	= 5;
	}
	public class PackageName{
		public static final String DEFAULT 			 = "com.eaf.orig.ulo.pl.ajax";
		public static final String ORIG_SHARE 		 = "com.eaf.orig.shared.servlet";
		public static final String ORIG_PL			 = "com.eaf.orig.ulo.pl.app.servlet";
		public static final String ORIG_RULES 		 = "com.eaf.orig.ulo.pl.app.rule.servlet";
		public static final String ORIG_PL_MANDATORY = "com.eaf.orig.ulo.pl.mandatory";
		public static final String ORIG_PL_PRODUCT	 = "com.eaf.orig.ulo.pl.product.ajax";
	}
	public class returnType{
		public static final int JSON = 0;
		public static final int HTML = 1;
	}
    public AjaxDisplayServlet() {
        super();       
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		logger.debug("[doPost]..");
				
		
		
		String strObj = "";		
		String packAge	= request.getParameter("packAge");		
		String className = getClassName(packAge, request.getParameter("className"));
		
		if(OrigUtil.isEmptyString(packAge)){
			className =  request.getParameter("className");
		}
		
		String returnType 	= request.getParameter("returnType");
				
//		logger.debug("[PackAge].. "+packAge);
		logger.debug("[ClassName].. "+className);
//		logger.debug("[ReturnType].. "+returnType);
		
		AjaxDisplayGenerateInf ajaxDisplay = null; 
		 
	    try{
	    	
	    	ajaxDisplay = (AjaxDisplayGenerateInf)Class.forName(className).newInstance();
	    	
		} catch (Exception ex) {
			logger.debug("[error loading]:  " + className + " :" + ex);
		}		
		
		if(ajaxDisplay!=null){
			try {
				strObj = ajaxDisplay.getDisplayObject(request);
			} catch (Exception e){
				logger.fatal("Error ",e);
			}
		}	
				
		if(!ORIGUtility.isEmptyString(strObj)){
			responseObject(strObj, returnType, response);
		}
	}
	
	public String getClassName(String packAge ,String className){
		
		if(ORIGUtility.isEmptyString(packAge)) packAge = "0";
		
		switch (Integer.parseInt(packAge)) {
			case Package.DEFAULT:
				return className = PackageName.DEFAULT+"."+className;
			case Package.ORIG_SHARE:
				return className = PackageName.ORIG_SHARE+"."+className;
			case Package.ORIG_PL:
				return className = PackageName.ORIG_PL+"."+className;
			case Package.ORIG_RULES:
				return className = PackageName.ORIG_RULES+"."+className;
			case Package.ORIG_PL_MANDATORY:
				return className = PackageName.ORIG_PL_MANDATORY+"."+className;
			case Package.ORIG_PL_PRODUCT:
				return className = PackageName.ORIG_PL_PRODUCT+"."+className;
			default:
				break;
		}
		
		return "";		
	}
	
	public void responseObject(String obj ,String type , HttpServletResponse response) throws IOException{
		
//		logger.debug("[response] "+obj);
		
		if(ORIGUtility.isEmptyString(type)) type = "0";
		PrintWriter pw  = null;
		switch (Integer.parseInt(type)) {
			case returnType.JSON:
				response.setContentType("application/json;charset=UTF-8");
				pw = response.getWriter();		
				pw.write(obj);
				pw.close();
				break;
			case returnType.HTML:
				response.setContentType("text;charset=UTF-8");
				response.setHeader("Pragma", "No-cache");
				response.setHeader("Cache-Control", "no-cache,no-store");
				response.setDateHeader("Expires", 0);
				pw = response.getWriter();
			    pw.write(obj);
				pw.close();
				break;
			default:
				break;
		}
	}
}
