<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache,no-store");
	response.setDateHeader("Expires", 0);
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
<div>
<div class="row">
<script>
	// Need to hide scroll bar
	$('.page-frame').css('overflow', 'hidden');
	$('body').css('overflow-x', 'hidden');
</script>
<div class="">
	<div id="FormHeaderManager" class="FormHeaderManager col-md-12 right-side" style="padding-top: 5px;">
		<div class="row formheaderbuttonswrapper">
			<div class="col-xs-12">
				<div class="tab-container"></div>
				<div class="groupList clear" id="FormButtonAction" style="float: right"></div>
			</div>
		</div>
	</div>
</div>
<div class="col-md-12 nopadding-right" style="background-color: #fff;">
	<div id="FormHandlerManager" class="FormHandlerManager right-side">
		<script>resizeFormTemplate();</script>
			
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
<%
	String buttonPath = request.getContextPath()+currentForm.getButtonFile();
	logger.debug("buttonPath >> "+buttonPath);
	out.print("<script>");
		out.print("$('#FormButtonAction').load('"+buttonPath+"');");
		out.print("$('#formActionName').val('"+formName+"');");
	out.print("</script>");
%>
<%=HtmlUtil.hidden("formName",formName)%>
<%=HtmlUtil.hidden("formId",formId)%>
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
<%-- 					<header class="titlecontent"><%=subformHeader%></header> --%>
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
</div>
</div>
</div>
</div>