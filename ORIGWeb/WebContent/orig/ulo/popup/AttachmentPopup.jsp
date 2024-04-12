<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="org.apache.tools.ant.taskdefs.Javadoc.Html"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>

<script type="text/javascript" src="orig/ulo/popup/js/AttachmentPopup.js"></script>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ModuleForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler"/>

<%
	String subformId="ATTACHMENT_POPUP";
	ApplicationGroupDataM appGroupM = (ApplicationGroupDataM)ModuleForm.getObjectForm();
	String displayMode = HtmlUtil.EDIT;
%>
<div class="panel panel-default">
	<div class="panel-heading"><%=LabelUtil.getText(request, "ATTACH_FILE")%></div>
	<div class="panel-body">
		<div class="row form-horizontal">
			<div class="col-md-12" class="ATTACHMENT_INPUT">
				<div class="col-sm-8">
					<div class="form-group">
						<%=HtmlUtil.getSubFormLabel(request,subformId,"FILE_TYPE","FILE_TYPE","col-sm-3 col-md-4 control-label")%>
						<%=HtmlUtil.dropdown("FILE_TYPE", "", "", "", "", SystemConstant.getConstant("FIELD_ID_FILE_TYPE"), "", displayMode, HtmlUtil.elementTagId("FILE_TYPE"), "col-xs-5 col-xs-padding attatchment-dropdawn", request)%>
					</div>
				</div>
				<div class="col-sm-4">
					<div class="form-group">
						<%=HtmlUtil.textBox("OTHER_FILE_TYPE", "", "", "", "10", displayMode, HtmlUtil.elementTagId("OTHER_FILE_TYPE"), "col-xs-12", request) %>  				
					</div>
				</div>
			<div class="clearfix"></div>
			<div class="col-md-12">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request,subformId,"FILE_NAME","FILE_NAME","col-sm-3 col-md-4 control-label")%>
					<div class="col-sm-4 col-md-8"><div class="col-sm-12" id="IMPORT_ATTACH_FILE"><%=LabelUtil.getText(request,"BROWSE_BTN")%></div></div>
					<div class="col-sm-3 col-md-4"><%=HtmlUtil.hidden("FILE_NAME", "") %></div>
				</div>
			</div>
			<div class="col-md-12">
				<div class="form-group">
				<div class="col-sm-4 col-md-12"><%=HtmlUtil.getText(request, "ATTACH_FILE_TYPE_UPLOAD")%>&nbsp;<%=SystemConstant.getConstant("ATTACHMENT_FILE_TYPE") %></div>
				
				</div>
			</div>
		</div>
	</div>
</div>
</div>
<script>
	initAttachFileUploader();
</script>