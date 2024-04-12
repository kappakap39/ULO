<%@page import="com.eaf.core.ulo.common.properties.FormControl"%>
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
<script type="text/javascript" src="orig/ulo/popup/js/SendApplicationToFraudPopup.js"></script>
<%
	Logger logger = Logger.getLogger(this.getClass());
// 	logger.debug("Send Application(s) to Fraud Popup ..................");
	String role = FormControl.getFormRoleId(request);
// 	logger.debug("role .................."+role);
	String subformId = "POPUP_SEND_APPLICATION_TO_FRAUD_SUBFORM";
	String formName = formHandlerManager.getCurrentFormHandler();
	FormHandler currentForm = (FormHandler) (request.getSession(true).getAttribute(formName));	
	String formId = currentForm.getFormId();
	String subFormId = request.getParameter("subFormId");
	
%>

<div class="panel panel-default">
	<div class="panel-body">
		<div class="row form-horizontal">
			<div class="col-md-12">
				<div class="form-group">
				<%=HtmlUtil.getSubFormLabel(request,subformId,"FRAUD_REMARK","FRAUD_REMARK", "col-sm-4 control-label")%>
				<%=HtmlUtil.textarea("FRAUD_REMARK", "", "", "8", "30", "200", HtmlUtil.EDIT,"", "col-sm-8", request) %>
				</div>
			</div>
		</div>
	</div>
</div>
<script>
$(function() {
	// Set Popup title
	var popupFormHandler = $('#<%=formId%>');
	popupFormHandler.find('.popuptitle').text('<%=LabelUtil.getText(request, "SEND_APP_TO_FRAUD_TITLE") %>');
	popupFormHandler.find('.titlecontent').remove();
});
</script>
