<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.properties.ImplementControl"%>
<%@page import="com.eaf.core.ulo.common.display.ElementInf"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<%
	Logger logger = Logger.getLogger(this.getClass());
	String displayMode = HtmlUtil.EDIT;
	String roleId = ORIGForm.getRoleId();
	String[] elementSubform = SystemConstant.getArrayConstant("VERIFICATION_CUSTOMER_INFO_"+roleId);
	logger.debug("==role=="+roleId);
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
%>
 
	<% if(null!=elementSubform && elementSubform.length>0 ) {
		for(int i=0;i<elementSubform.length;i++){
			String elementId =elementSubform[i].trim();
			if(!Util.empty(elementId)){
			logger.debug("elementId >>>>> "+elementId);
				ElementInf element = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.VERIFICATION,elementId);
				%>
				<%if(!MConstant.FLAG_N.equals(element.renderElementFlag(request,applicationGroup))){%>
					<% element.writeElement(pageContext,null);%>			
			<%	 }
			}		
		}
	} %>