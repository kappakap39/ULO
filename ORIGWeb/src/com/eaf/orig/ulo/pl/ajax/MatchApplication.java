package com.eaf.orig.ulo.pl.ajax;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;

public class MatchApplication implements AjaxDisplayGenerateInf {
	
	static Logger logger = Logger.getLogger(MatchApplication.class);
	
	@Override
	public String getDisplayObject(HttpServletRequest request)throws PLOrigApplicationException{
		String message = "";
		String[] checks = request.getParameterValues("check_apprecid");
		if(null != checks){	
			int i = 0;
			String m_role = null;
			String m_ptid = null;
			boolean match = true;
			for(String appRecID : checks){
				String role = request.getParameter("role-"+appRecID);
				String ptid = request.getParameter("ptid-"+appRecID);
				if(i == 0){
					m_role = role;
					if(null == m_role) m_role = "";
					m_ptid = ptid;
					if(null == m_ptid) m_ptid = "";
				}
				if(!m_role.equals(role) || !m_ptid.equals(ptid)){
					match = false;
					break;
				}
				i++;
			}
			if(!match){
				message = "ERROR";
			}
		}
		return message;
	}
	
}
