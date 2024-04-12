<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.util.FormEffects"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler" />
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM" />
<%
	Logger logger = Logger.getLogger(this.getClass());
	String formId = ORIGForm.getFormId();
	String CACHE_PRE_TIME = SystemConstant.getConstant("CACHE_PRE_TIME");
	String roleId = ORIGUser.getRoleMenu();	
	logger.debug("formId header >> "+formId);
	logger.debug("formId header >> roleId"+roleId);
	FormEffects formEffect = new FormEffects(formId,FormEffects.ConfigType.FORM,request);
%>
<%=HtmlUtil.button("COMMENT","COMMENT","COMMENT","button green","popover='popover'",formEffect)%>
<%=HtmlUtil.button("HISTORY","HISTORY","HISTORY","button green","popover='popover'", formEffect)%>
<%=HtmlUtil.button("AUDIT_TRAIL","AUDIT_TRAIL","AUDIT_TRAIL","button green","popover='popover'", formEffect)%>
<%=HtmlUtil.button("DOCUMENT_LIST","DOCUMENT_LIST","DOCUMENT_LIST","button green","popover='popover'", formEffect)%>
<%=HtmlUtil.button("DOCUMENT_HEADER_FLAG","DOCUMENT_HEADER_FLAG","NOTIFY_TEXT","badge badge-header-position","", formEffect)%>
<%=HtmlUtil.button("COMPARE_SIGNATURE","COMPARE_SIGNATURE","COMPARE_SIGNATURE","button green","popover='popover'", formEffect)%>	