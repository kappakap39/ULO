<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ModuleForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler"/>
<script type="text/javascript" src="orig/ulo/popup/js/CancelReasonPopup.js"></script>
<%
	String subFormId = "POPUP_CANCEL_REASON_SUBFORM";	
	FormUtil formUtil = new FormUtil(subFormId,request);
%>
<div class="panel panel-default">
	<div class="panel-body">
		<div class="row form-horizontal">
			<div class="col-md-12">
				<div class="col-md-8">
					<div class="form-group">
						<%=HtmlUtil.getSubFormLabel(request,subFormId,"REASON","REASON", "col-sm-4 control-label")%>
						<%=HtmlUtil.dropdown("REASON_CODE", "REASON_CODE", "55", "", "", "", "55","ALL_ALL_ALL", "", "col-sm-8", formUtil)%>
				</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">				
						<%=HtmlUtil.textBox("REASON_OTH_DESC","","","","","100","","col-sm-12",formUtil)%>
					</div>
				</div>
			</div>
		</div>
		<div class="row form-horizontal">
			<div class="col-md-12">
				<div class="col-md-8">
					<div class="form-group">
						<%=HtmlUtil.getSubFormLabel(request,subFormId,"REMARK","REMARK", "col-sm-4 control-label")%>
						<%=HtmlUtil.textarea("REMARK", "", "", "3", "30", "200", "", null, "col-sm-8", request) %>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>