<%@page contentType="text/html;charset=UTF-8"%> 
<%@page import="java.util.ArrayList"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.eaf.core.ulo.common.util.NotifyForm"%>
<%@page import="com.eaf.j2ee.pattern.view.form.FormHandler"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.orig.shared.view.form.ORIGSubForm"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler"%>
<jsp:useBean id="formHandlerManager" scope="session" class="com.eaf.j2ee.pattern.view.form.FormHandleManager"/>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<script src="orig/ulo/template/js/PopupFormTemplate.js"></script>
<%
	Logger logger = Logger.getLogger(this.getClass());
	String formName = formHandlerManager.getCurrentFormHandler();
	logger.debug("formName >> "+formName);
	FormHandler currentForm = (FormHandler) (request.getSession(true).getAttribute(formName));	
	String formId = currentForm.getFormId();
	logger.debug("formId >> "+formId);
	String popupForm = formId+" "+formName;
%>
<div id="<%=formId%>" class="<%=popupForm%>">
<%=HtmlUtil.hidden("formName",formName)%>
<%=HtmlUtil.hidden("formId",formId)%>
<%
String buttonFile = currentForm.getButtonFile();
logger.debug("buttonFile >> "+buttonFile);
if(!Util.empty(buttonFile)){
%>
	<jsp:include page="<%=buttonFile%>" flush="true"/>
<%}%>
<%
String formJS = currentForm.getFormJS();
logger.debug("formJS >> "+formJS);
if(!Util.empty(formJS)){
	out.println("<script type=\"text/javascript\" src=\""+formJS+"\"></script>");
}
%>
<%
	out.print("<script>");
		out.print("$('#formActionName').val('"+formName+"');");
	out.print("</script>");
%>
<div class="PopupFormHandlerWrapper">
	<div id="<%=formId%>NotifyFormHandlerManager" class="NotifyFormHandlerManager">
	<%
		HashMap<String, ArrayList<String>> notifyForm = currentForm.getNotifyForm();
		if(null != currentForm){
			ArrayList<String> warnings = notifyForm.get(NotifyForm.NotifyType.WARN);
			if(null != warnings){
				out.print("<div class='alert alert-warning alert-dismissible fade in' role='alert'>");
				out.print("<button type='button' class='close' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>x</span></button>");
				for(String warning:warnings){
					out.print("<div>"+warning+"</div>");
				}
				out.print("</div>");
			}
		}
	 %>
	</div>
	<div id="<%=formId%>ErrorFormHandlerManager" class="ErrorFormHandlerManager"></div>
	<%
	ArrayList<ORIGSubForm> subForms = currentForm.getSubForm();	
	if(!Util.empty(subForms)){
		for(ORIGSubForm subForm:subForms){
			logger.debug("subForm.getFileName() >> "+subForm.getFileName());
			String groupSubformId = subForm.getGroupSubFormId();
			String subformId = subForm.getSubFormID();
	%>
			<section class="work_tools_area">
				<div id="<%=groupSubformId%>" class="<%=groupSubformId%>">
					<script type="text/javascript">
						$(document).ready(function(){
							try{<%=groupSubformId%>InitSubFormJS('<%=subformId%>','<%=groupSubformId%>');}catch(e){}
						});
					</script>
						<%=HtmlUtil.hidden("groupSubformId",groupSubformId)%>
						<%=HtmlUtil.hidden("subformId",subformId)%>
					<section class="work_area" id="SECTION_<%=subformId%>">
						<header class="titlecontent"><%=LabelUtil.getText(request,subForm.getSubformDesc())%></header>
						<div class="row">
							<div class="col-xs-12">
								<% pageContext.include(subForm.getFileName()+"?subFormId="+subformId,true); %>
							</div>
						</div>
					</section>
				</div>
			</section>
		<%}%>
	<%}%>
	</div>
</div>