<%@page import="com.eaf.core.ulo.common.util.FormEffects"%>
<%@page import="com.eaf.core.ulo.common.properties.FormControl"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<script type="text/javascript" src="orig/ulo/button/js/PopupButtonSubmitTemplate.js"></script>
<% 
	Logger logger = Logger.getLogger(this.getClass());
	String roleId = ORIGForm.getRoleId();
	String formId = FormControl.getFormId(request);
	logger.debug("roleId >> "+roleId);
	logger.debug("formId >> "+formId);
	FormEffects formEffect = new FormEffects(formId,FormEffects.ConfigType.FORM,request);
%>
<div class="popupheader" style="float: left;">
	<h3 class="popuptitle" style="margin-top: 1px;"></h3>
</div>
<section class='work_tools_area  formheaderbuttonswrapper' style="width: auto; margin: 0">
	<%=HtmlUtil.button("SAVE_POPUP_BTN", "SAVE_POPUP_BTN", "", "btn_icon","tooltip title='Submit Application'",formEffect) %>
	<%=HtmlUtil.button("CLOSE_POPUP_BTN","",HtmlUtil.EDIT, "btn_close", "tooltip title='Close'", request) %>
</section>