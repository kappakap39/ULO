<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.orig.ulo.app.view.util.pa.StampDutyUtil"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ModuleForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler"/>
<jsp:useBean id="formHandlerManager" scope="session" class="com.eaf.j2ee.pattern.view.form.FormHandleManager"/>

<script type="text/javascript" src="orig/pa/StampDutyPopup.js?v=1"></script>
<script type="text/javascript" src="js/pdfjs/build/pdf.js?v=1"></script>
<%
	String subformId = "STAMP_DUTY_POPUP";
	ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM) ModuleForm.getObjectForm();
	String applicationGroupNo = applicationGroup.getApplicationGroupNo();
	FormUtil formUtil = new FormUtil(subformId,request);
	String formName = formHandlerManager.getCurrentFormHandler();
	String userName = ORIGUser.getUserName();
	//applicationGroup.getClaimBy() is set to "true" if job come form myTask page in StampDutyPopupForm.java
	String enableStampButton = applicationGroup.getClaimBy(); 
	//System.out.println("isMyTask = " + isMyTask);
	String mode = (enableStampButton != null && enableStampButton.equals("true")) ? HtmlUtil.EDIT : HtmlUtil.VIEW ;
	String url = StampDutyUtil.getContextURL(request) + "getStamp?appGroupNo="+applicationGroupNo;
%>
<div class="panel panel-default">
	<div class="panel-body">
		<div class="col-md-1"></div>
		<div class="col-md-8 text-center">
			<% if(applicationGroupNo != null){ %>
				<embed name="stampPDF" src="<%=url%>" width="794" height="1123" type='application/pdf'>
			<% } %>
		</div>
		<div class="col-md-3"/>
		<div class="clearfix"></div>
		<div class="row form-horizontal">
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getFieldLabel(request, "REQUESTOR_NAME", "col-sm-4 col-md-5 control-label")%>
					<%=HtmlUtil.textBox("REQUESTOR_NAME", "", "", "", "200", mode, "","col-sm-8 col-md-7", request)%>	
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getFieldLabel(request, "REQUESTOR_POSITION", "col-sm-4 col-md-5 control-label")%>
					<%=HtmlUtil.textBox("REQUESTOR_POSITION", "", "", "", "200", mode, "","col-sm-8 col-md-7", request)%>					
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12 text-center">
				<%=HtmlUtil.button("STAMP_DUTY_COMPLETE_BTN", "COMPLETE_BTN", mode , "btn2 btn2-green", "", request)%>
				<%=HtmlUtil.button("CANCEL_BTN", "CANCEL_BTN", HtmlUtil.EDIT, "btn2", "", request)%>
			</div>
		</div>
	</div>
</div>
<%=HtmlUtil.hidden("appGroupNo",applicationGroupNo)%>
<%=HtmlUtil.hidden("enableStampButton",enableStampButton)%>
<script>
	var enableStampButton = $("[name='enableStampButton']").val();
	if(enableStampButton != "true")
	{
		$("[name='STAMP_DUTY_COMPLETE_BTN']").removeClass("btn2-green");
		$("[name='SAVE_POPUP_BTN']").hide();
	}
</script>