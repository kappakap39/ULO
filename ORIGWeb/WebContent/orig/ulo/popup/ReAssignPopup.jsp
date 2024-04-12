<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page contentType="text/html;charset=UTF-8"%>
<script type="text/javascript" src="orig/ulo/popup/js/ReAssignPopup.js"></script>
<%String CONFIG_ID_FILTER_USER_IS_LOG_ON = SystemConstant.getConstant("CONFIG_ID_FILTER_USER_IS_LOG_ON"); %>
<div class="panel panel-default">
<div class="panel-heading"><%=LabelUtil.getText(request, "RE_ASSIGN")%></div>
<div class="panel-body padding-top">
<div class="row form-horizontal">
	<div class="col-sm-10">
		<div class="form-group">
			<%=HtmlUtil.getFieldLabel(request, "REASSIGN_TO", "col-sm-3 control-label")%>
			<div class="col-sm-8 col-md-7"><%=HtmlUtil.dropdown("REASSIGN_TO", "", CONFIG_ID_FILTER_USER_IS_LOG_ON, "", "", SystemConstant.getConstant("CACH_NAME_EMPLOYEE"), "", HtmlUtil.EDIT, HtmlUtil.elementTagId("REASSIGN_TO"), "", request)%></div>
		</div>
	</div>
	<div class="col-md-10">
		<div class="form-group" style="float: right;">
			<%=HtmlUtil.button("OK_BTN","OK_BTN","","btn btn-primary","",request) %><%=HtmlUtil.button("CANCEL_BTN","CANCEL_BTN","","btn btn-primary","",request) %>
		</div>
	</div>
</div>
</div>
</div>
