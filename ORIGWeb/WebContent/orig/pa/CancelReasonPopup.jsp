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
<script type="text/javascript" src="orig/pa/CancelReasonPopup.js?v=1"></script>
<%
	String subformId = "AS_CANCEL_REASON_POPUP";
	String applicationGroupNo = (String) ModuleForm.getObjectForm();
	String FIELD_ID_CANCEL_CLAIM_REASON = SystemConstant.getConstant("FIELD_ID_CANCEL_CLAIM_REASON");
	FormUtil formUtil = new FormUtil(subformId,request);
%>

<div class="panel panel-default">
	<div class="panel-heading"><%=LabelUtil.getText(request, "AS_CANCEL_REASON_POPUP_LABEL")%></div>
	<div class="panel-body"> 
		<div class="row form-horizontal">
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getFieldLabel(request, "REASON", "col-sm-4 col-md-5 control-label")%>
					<%=HtmlUtil.dropdown("REASON", "", "", "VALUE", "",
					FIELD_ID_CANCEL_CLAIM_REASON, "", "", "", "col-sm-8 col-md-7", request)%>
				</div>
			</div>
			<div class="col-sm-4">
				<div class="form-group">
					<%=HtmlUtil.textBox("OTHER_REASON", "", "", "", "100", HtmlUtil.VIEW, "", "", request)%>
				</div>
			</div>
			<div class="col-sm-2"></div>
		</div>
		<div class="clearfix"></div>
		<div class="row form-horizontal">
			<div class="col-sm-2">
				<div class="form-group">
					<%=HtmlUtil.getFieldLabel(request, "CANCEL_REMARK", "control-label")%>
				</div>
			</div>
			<div class="col-sm-4">
			<%=HtmlUtil.textarea("CANCEL_REMARK", "", "", "5", "40", "200", "", "", "", request)%>
			</div>	
		</div>
		<div class="col-sm-12">&nbsp;</div>
	</div>
</div>
<%=HtmlUtil.hidden("appGroupNo", applicationGroupNo)%>