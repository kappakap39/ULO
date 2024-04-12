<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<script type="text/javascript" src="wm/PopupWMTaskViewError.js"></script>
<jsp:useBean id="ModuleForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler"/>
<% 
	Logger logger = Logger.getLogger(this.getClass());
	String message = (String) ModuleForm.getObjectForm();
%>
<div style="height: 100%; width: 100%;">
	<div class="row">
		<div class="popupheader" style="float: left;">
			<h3 class="popuptitle" style="margin-top: 1px;"></h3>
		</div>
		<section class='work_tools_area  formheaderbuttonswrapper' style="width: auto; margin: 0; padding-right: 15px;">
			<%=HtmlUtil.button("CLOSE_POPUP_BTN","",HtmlUtil.EDIT, "btn_close", "tooltip title='Close'", request) %>
		</section>
	</div>
	
	<div class="row" style="height: 100%; width: 100%; margin: 0px; padding-top: 10px;">
		<%=message %>
	</div>
</div>