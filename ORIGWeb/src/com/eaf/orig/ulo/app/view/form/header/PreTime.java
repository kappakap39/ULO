package com.eaf.orig.ulo.app.view.form.header;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.control.FlowControlDataM;

public class PreTime extends ElementHelper{
	private static transient Logger logger = Logger.getLogger(PreTime.class);
	String ROLE_CA = SystemConstant.getConstant("ROLE_CA");
	ArrayList<String> ROLE_SUPERVISOR = SystemConstant.getArrayListConstant("ROLE_SUPERVISOR");
	
	@Override
	public String renderElementFlag(HttpServletRequest request,Object objectElement) {
		String roleId = (String)objectElement;
		FlowControlDataM flowControl = (FlowControlDataM)request.getSession().getAttribute("FlowControl");
		String objectRoleId = flowControl.getRole();
		String FLAG = MConstant.FLAG_Y;
		if(ROLE_SUPERVISOR.contains(objectRoleId)){
			FLAG = MConstant.FLAG_N;
		}
//		if(ROLE_CA.equals(roleId)){
//			FLAG = MConstant.FLAG_N;
//		}
		logger.debug("renderElementFlag.FLAG : "+FLAG);
		return FLAG;
	}
	
	@Override
	public String getDisplayType() {
		return ElementInf.DISPLAY_JSP;
	}
	
	@Override
	public String getElement(HttpServletRequest request, Object objectElement) {
		return "/orig/ulo/template/PreTime.jsp";
	}
}
