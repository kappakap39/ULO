<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache,no-store");
	response.setDateHeader("Expires", 0);
%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.orig.shared.view.form.ORIGSubForm"%>
<%@page import="com.eaf.core.ulo.common.properties.FormControl"%>
<%@page import="java.util.ArrayList"%>
<jsp:useBean id="FlowControl" scope="session" class="com.eaf.orig.ulo.model.control.FlowControlDataM"/>
<%
	Logger logger = Logger.getLogger(this.getClass());
	String formId = request.getParameter("formId");
	String roleId = FormControl.getFormRoleId(request);
	logger.debug("formId >> "+formId);
	logger.debug("roleId >> "+roleId);
	ArrayList<ORIGSubForm> subforms = FormControl.getSubForm(formId, roleId);
%>
<%if(!Util.empty(subforms)){%>
<%for(ORIGSubForm subForm:subforms){
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
		<%=HtmlUtil.hidden("headerFormTemplate","Y")%>
	<section class="work_area" id="SECTION_<%=subformId%>">
		<header class="titlecontent"><%=LabelUtil.getText(request,subForm.getSubformDesc())%></header>
		<div class="row">
			<div class="col-xs-12">
				<%pageContext.include(subForm.getFileName()+"?formTemplateType=HEADER",false);%>
				</div>
			</div>
		</section>
	</div>
</section>
<%}%>
<%}%>