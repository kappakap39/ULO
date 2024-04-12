<%@page import="com.eaf.core.ulo.common.util.NotifyForm"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.DMFormHandler"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<jsp:useBean id="formHandlerManager" scope="session" class="com.eaf.j2ee.pattern.view.form.FormHandleManager" />
<jsp:setProperty name="formHandlerManager" property="currentFormHandler" value="<%=DMFormHandler.DMForm%>"/>
<jsp:useBean id="DMForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.DMFormHandler"/>
<jsp:include page="/orig/ulo/button/DMBorrowButton.jsp" flush="true"/>
<%
	String formId = DMForm.getFormId();
	String formName = DMForm.getFormName();
%>
<div id="FormHandlerManager" class="FormHandlerManager">
	<%=HtmlUtil.hidden("formName",formName)%>
	<%=HtmlUtil.hidden("formId",formId)%>
	<div id="<%=formId%>" class="<%=formId%>">
		<div id="<%=formId%>ErrorFormHandlerManager" class="ErrorFormHandlerManager">
		<%NotifyForm.write(request,out);%>
		</div>
		<section class="work_area">
			<header class="titlecontent"><%=LabelUtil.getText(request,"DM_APP_INFO_FORM")%></header>
			<div class="row">
				<div class="col-xs-12">
					<jsp:include page="/orig/ulo/subform/DMApplicationInfoSubForm.jsp" flush="true"/>
				</div>
			</div>
			<header class="titlecontent"><%=LabelUtil.getText(request,"DM_WAREHOUSE_INFO")%></header>
			<div class="row">
				<div class="col-xs-12">
					<jsp:include page="/orig/ulo/subform/DMWareHouseInfoSubForm.jsp" flush="true"/>
				</div>
			</div>
			<header class="titlecontent"><%=LabelUtil.getText(request,"DM_DOC_MANAGEMENT")%></header>
			<div class="row">
				<div class="col-xs-12">
					<jsp:include page="/orig/ulo/subform/DMDocumentManage.jsp" flush="true"/>
				</div>
			</div>
			<header class="titlecontent"><%=LabelUtil.getText(request,"DM_DOC_HISTORY")%></header>
			<div class="row">
				<div class="col-xs-12">
					<jsp:include page="/orig/ulo/subform/DMDocumentHistory.jsp" flush="true"/>
				</div>
			</div>
		</section>	
	</div>
</div>