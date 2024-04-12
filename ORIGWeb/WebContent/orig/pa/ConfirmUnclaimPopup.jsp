<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.AddressDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.orig.ulo.app.view.util.kpl.KPLUtil"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ModuleForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>

<script type="text/javascript" src="orig/pa/ConfirmUnclaimPopup.js"></script>
<%
	String subformId = "CONFIRM_UNCLAIM_POPUP";
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	
	FormUtil formUtil = new FormUtil(subformId,request);
%>

<div class="panel panel-default">
	<div class="panel-heading"><%=LabelUtil.getText(request, "CONFIRM_UNCLAIM_LABEL")%></div>
	<div class="panel-body">
		<div class="col-sm-6">
			<%=LabelUtil.getText(request, "WAITING_UNCLIAM_TEXT")%>
		</div>
		<div class="clearfix"></div>
		<div class="col-sm-6">
			<%=LabelUtil.getText(request, "LIMIT_SPECIFY")%>	
		</div>
		<div class="col-sm-6">
			<%=HtmlUtil.radio("LIMIT_CANCEL_CONFIRM", "", "VAULE", "CHECK", "", "", "LIMIT_CANCEL_CONFIRM", request) %>
		</div>
		<div class="clearfix"></div>
		<div class="col-sm-6"></div>
		<div class="col-sm-6">
			<%=HtmlUtil.radio("CANNOT_CANCEL_LIMIT", "", "value", "check", "", "", "CANNOT_CANCEL_LIMIT", request) %>
			(กรุณาระบุเหตุผล : <%=HtmlUtil.textBox("CANNOT_CANCEL_LIMIT_TEXT_BOX", "", "VAULE", "", "20", "", "", "col-sm-8 col-md-7", request)%> )	
		</div>
</div>