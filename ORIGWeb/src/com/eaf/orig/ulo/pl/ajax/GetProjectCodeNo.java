package com.eaf.orig.ulo.pl.ajax;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.PLProjectCodeDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

public class GetProjectCodeNo implements AjaxDisplayGenerateInf {

	Logger log = Logger.getLogger(this.getClass());
	@Override
	public String getDisplayObject(HttpServletRequest request) throws PLOrigApplicationException{
		String project_code = (String)request.getParameter("project_code");
		String result = "NOT_Found";
		if(project_code!=null && !"".equals(project_code)){
			PLProjectCodeDataM projectCodeDataM = new PLProjectCodeDataM();
            try{
            	projectCodeDataM = PLORIGEJBService.getORIGDAOUtilLocal().Loadtable(project_code);
            	
				if (projectCodeDataM.getProjectcode() != null) {
					return projectCodeDataM.getProjectcode();
	            }
				
	            
            } catch(Exception e){
            	throw new PLOrigApplicationException(e.getMessage());
            }
		}
		return result;
	}
	

}
