<%@page import="com.eaf.core.ulo.common.performance.TraceController"%>
<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache,no-store");
	response.setDateHeader("Expires", 0);
%>
<%
	String transactionId = (String)request.getSession().getAttribute("transactionId");
	TraceController trace = new TraceController("FormTemplate.jsp",transactionId);
	trace.create("FormTemplate");
%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.orig.shared.view.form.ORIGSubForm"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.j2ee.pattern.view.form.FormHandler"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.util.NotifyForm"%>
<%@page import="com.eaf.core.ulo.common.util.FormEffects"%>
<%@page import="com.eaf.core.ulo.common.util.FormEffectsObjectUtil"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="tabHandlerManager" scope="session" class="com.eaf.j2ee.pattern.view.form.TabHandleManager"/>
<%
	Logger logger = Logger.getLogger(this.getClass());	
	String tabId = tabHandlerManager.getTabId();
	ArrayList<String> tabs = tabHandlerManager.getTabs();
	String formName = tabHandlerManager.getTabId();
	logger.debug("[tabId] "+tabId);
	logger.debug("[tabs] "+tabs);
	logger.debug("formName >> "+formName);
%>
<jsp:useBean id="formHandlerManager" scope="session" class="com.eaf.j2ee.pattern.view.form.FormHandleManager"/>
<jsp:setProperty name="formHandlerManager" property="currentFormHandler" value="<%=formName%>"/>
<%
	FormHandler currentForm = (FormHandler) (request.getSession(true).getAttribute(formName));	
	String formId = currentForm.getFormId();
%>        
<div id="<%=formId%>ErrorFormHandlerManager" class="ErrorFormHandlerManager">
<%
	NotifyForm.write(request,out);
%>
</div>
<ul class="nav nav-tabs">
<%
for(String tab:tabs){
	FormHandler currentTabForm = (FormHandler) (request.getSession(true).getAttribute(tab));
	String tabFormId = currentTabForm.getFormId();
	logger.debug("tabFormId >> "+tabFormId);
	String activeTab = (tab.equals(tabId))?"active":"";
%>
  <li role="presentation" class="<%=activeTab%>"><a><%=LabelUtil.getText(request,"TAB_"+tabFormId)%></a></li>
<%}%>
</ul>
<script>
	// replace tabs here
	$('.tab-container').html('');
	$('.tab-container').append($('.FormHandlerManager .nav.nav-tabs'));
</script>
<%=HtmlUtil.hidden("formName",formName)%>
<%=HtmlUtil.hidden("formId",formId)%>
<%
FormEffects formEffect = new FormEffects(formId,FormEffects.ConfigType.FORM,request);
String mode = FormEffectsObjectUtil.getObjectFormMode("DOCUMENT_LIST",FormEffects.ObjectType.BUTTON,null,formEffect);
if(!FormEffects.Effects.HIDE.equals(mode)){
	out.print("<div id='HEADER_SECTION_DOCUMENT_HEADER_FORM' style='display:none;'>");
		pageContext.include("/orig/ulo/template/HeaderFormTemplate.jsp?formId=DOCUMENT_HEADER_FORM",true); 	
	out.print("</div>");
}
%>
<div id="<%=formId%>" class="<%=formId%>">
<%
String formJS = currentForm.getFormJS();
logger.debug("formJS >> "+formJS);
if(!Util.empty(formJS)){
	out.println("<script type=\"text/javascript\" src=\""+formJS+"\"></script>");
}
%>
<%
ArrayList<ORIGSubForm> subForms = currentForm.getSubForm();
if(!Util.empty(subForms)){
	
	for(ORIGSubForm subForm:subForms){
		logger.debug("subForm.getSubFormID() >> "+subForm.getSubFormID()+" subForm.getFileName() >> "+subForm.getFileName());
		String groupSubformId = subForm.getGroupSubFormId();
		String subformId = subForm.getSubFormID();
%>
		<section>
			<div id="<%=groupSubformId%>" class="<%=groupSubformId%>">				
				<script type="text/javascript">
					$(document).ready(function(){
						try{<%=groupSubformId%>InitSubFormJS('<%=subformId%>','<%=groupSubformId%>');}catch(e){}
					});
				</script>
				<%=HtmlUtil.hidden("groupSubformId",groupSubformId)%>
				<%=HtmlUtil.hidden("subformId",subformId)%>		
				<section class="work_area" id="SECTION_<%=subformId%>">
					<%
						String subformHeader = LabelUtil.getText(request,subForm.getSubformDesc());
					%>
					<%if(!Util.empty(subformHeader)){%>
					<header class="titlecontent"><%=subformHeader%></header>
					<%}%>
					<div class="row">
						<div class="col-xs-12">
							<%pageContext.include(subForm.getFileName()+"?subFormId="+subformId,true);%>
						</div>
					</div>
				</section>
			</div>
		</section>
	<%}%>
<%}%>
<div class="row">
  <div class="col-xs-12" style="height: 115px;visibility: hidden;display: none;"></div>
</div>
</div>
<%
	String buttonPath = request.getContextPath()+currentForm.getButtonFile();
	logger.debug("buttonPath >> "+buttonPath);
	out.print("<script>");
		out.print("$('#FormButtonAction').load('"+buttonPath+"');");
		out.print("$('#formActionName').val('"+formName+"');");
	out.print("</script>");
%>
<%
	trace.end("FormTemplate");
	trace.trace();
%>