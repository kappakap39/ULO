<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.util.FormEffects"%>
<%@page import="com.eaf.core.ulo.common.properties.FormControl"%>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<script type="text/javascript" src="orig/ulo/button/js/ButtonTemplate.js"></script>
<% 
	String roleId = ORIGForm.getRoleId();
	String formId = FormControl.getFormId(request);
	FormEffects formEffect = new FormEffects(formId,FormEffects.ConfigType.FORM,request);
%>
<div class="row">
	<div class="col-sm-3" style="width: auto;">
		<%
		    out.print(HtmlUtil.button("CALL_RECALCULATE_DEBT_BURDEN_BTN", "CALL_RECALCULATE_DEBT_BURDEN_BTN", "", "btn_icon btn_decision_recal", "tooltip title='Call Recalculate DebtBurden'", formEffect));
			out.print(HtmlUtil.button("CALL_DECISION_SERVICE_BTN", "CALL_DECISION_SERVICE_BTN", "CALL_DECISION_SERVICE_BTN", "btn_icon btn_decision", "tooltip title='Call Decision Service'", formEffect));
			out.print(HtmlUtil.button("SEND_TO_FOLLOW_UP_BTN","SEND_TO_FOLLOW_UP_BTN","","btn_icon","tooltip title='Send to Follow Up'", formEffect));
			out.print(HtmlUtil.button("CANCEL_APPLICATION_BTN","CANCEL_APPLICATION_BTN","","btn_audit","tooltip title='Cancel Application'", formEffect));
			out.print(HtmlUtil.button("SEND_BACK_APPLICATION_BTN","SEND_BACK_APPLICATION_BTN","","btn_icon flipY","tooltip title='Send Back'", formEffect));
// 			out.print(HtmlUtil.button("SUBMIT_DECISION_APPLICATION_BTN","",HtmlUtil.EDIT,"btn_icon_red", "tooltip title='Submit Decision'",request));
			out.print(HtmlUtil.button("SUBMIT_DECISION_APPLICATION_BTN","SUBMIT_DECISION_APPLICATION_BTN","","btn_icon_red","tooltip title='Submit Decision'", formEffect));
			out.print(HtmlUtil.button("SUBMIT_APPLICATION_BTN","SUBMIT_APPLICATION_BTN","","btn_icon","tooltip title='Submit Application'", formEffect));
			out.print(HtmlUtil.button("SAVE_APPLICATION_BTN","SAVE_APPLICATION_BTN","","btn_save","tooltip title='Save Application'", formEffect));		
			out.print(HtmlUtil.button("CLOSE_APPLICATION_BTN","",HtmlUtil.EDIT,"btn_close", "tooltip title='Close Application'",request));
		%>
	</div>
</div>
<%=HtmlUtil.hidden("buttonAction","")%>
<%=HtmlUtil.hidden("decisionAction","")%>
<script>
	reloadTooltip();
</script>