<%@page import="com.eaf.core.ulo.common.util.FormEffects"%>
<%@page import="com.eaf.core.ulo.common.properties.FormControl"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<script type="text/javascript" src="orig/ulo/button/js/IncomeScreenButton.js"></script>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<% 
	String roleId = ORIGForm.getRoleId();
	String formId = FormControl.getFormId(request);
	FormEffects formEffect = new FormEffects(formId,FormEffects.ConfigType.FORM,request);
%>
<div class="row">
	<div class="col-sm-3" style="width: auto; float: right;">
		<%
			out.print(HtmlUtil.button("SAVE_TAB_INCOME_SCREEN_BTN","SAVE_TAB_INCOME_SCREEN_BTN","","btn_save","tooltip title='Save'", formEffect));
			out.print(HtmlUtil.button("CLOSE_TAB_INCOME_SCREEN_BTN","",HtmlUtil.EDIT,"btn_close", "tooltip title='Cancel'",request));
		%>
	</div>
</div>
<%=HtmlUtil.hidden("buttonAction","")%>
<script>
	reloadTooltip();
</script>