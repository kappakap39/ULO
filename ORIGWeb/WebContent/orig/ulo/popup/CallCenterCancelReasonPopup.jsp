<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList" %>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.j2ee.pattern.view.form.FormHandler"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.ListBoxControl" %>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.core.ulo.common.engine.SQLQueryEngine" %>
<%@page import="com.eaf.core.ulo.common.display.ListBoxFilter" %>
<%@page import="com.eaf.core.ulo.common.display.ListBoxFilterInf" %>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ModuleForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler"/>
<jsp:useBean id="formHandlerManager" scope="session" class="com.eaf.j2ee.pattern.view.form.FormHandleManager"/>
<script type="text/javascript" src="orig/ulo/popup/js/CallCenterCancelReasonPopup.js"></script>
<%
	Logger logger = Logger.getLogger(this.getClass());
	logger.debug("CallCenter Cancel Reason Popup ..................");	
	String formName = formHandlerManager.getCurrentFormHandler();
	FormHandler currentForm = (FormHandler) (request.getSession(true).getAttribute(formName));	
	String formId = currentForm.getFormId();
	String subFormId = "POPUP_CALLCENTER_CANCEL_REASON_SUBFORM";	
	FormUtil formUtil = new FormUtil(subFormId,request);
%>
<div class="panel panel-default">
	<div class="panel-body">
		<div class="row form-horizontal">
			<div class="col-md-12">
				<div class="col-md-8">
					<div class="form-group">
						<%=HtmlUtil.getSubFormLabel(request,subFormId,"REASON","REASON", "col-sm-4 control-label")%>
						<%=HtmlUtil.dropdown("REASON_CODE","","55","","","55","","","","col-sm-8 ",request)%></div>
				</div>
				<div class="col-md-4">
					<div class="form-group">				
						<%=HtmlUtil.textBox("REASON_OTH_DESC","","","","100","","","col-sm-12",request)%>
					</div>
				</div>
			</div>
		</div>
		<div class="row form-horizontal">
			<div class="col-md-12">
				<div class="col-md-8">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request,subFormId,"REMARK","REMARK", "col-sm-4 control-label")%>
					<%=HtmlUtil.textarea("REMARK","","","6","30","200","",null,"col-sm-8",request) %>
				</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script>
$(function() {
	// Set Popup title
	var popupFormHandler = $('#<%=formId%>');
	popupFormHandler.find('.popuptitle').text('<%=LabelUtil.getText(request, "CALNCEL_REASON") %>');
	popupFormHandler.find('.titlecontent').remove();
});
</script>