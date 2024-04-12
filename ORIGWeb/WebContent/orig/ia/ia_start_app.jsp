<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.date.ApplicationDate"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%
	String FIELD_ID_COVER_PAGE_TYPE = SystemConstant.getConstant("FIELD_ID_COVER_PAGE_TYPE");
	String SEARCH_BRANCH_INFO = SystemConstant.getConstant("SEARCH_BRANCH_INFO");
%>
<div class="row col-xs-6">
<div class="panel">
	<div id='createCaseForm'>
		<div class="row form-horizontal padding-top">
			<div class="clearfix"></div>
			<div class="col-sm-12">
				<div class="form-group">
					<div class="col-sm-4 col-md-5 control-label"><%=HtmlUtil.getMandatoryLabel(request, "QR_APPLY_TYPE") %></div>
					<%=HtmlUtil.dropdown("QR_APPLY_TYPE", "", "", "", "", FIELD_ID_COVER_PAGE_TYPE, "ALL", HtmlUtil.EDIT, "", "col-sm-8 col-md-7", request)%>
				</div>
			</div>
			<div class="clearfix"></div>
			<div class="col-sm-12">
				<div class="form-group">
					<div class="col-sm-4 col-md-5 control-label"><%=HtmlUtil.getMandatoryLabel(request, "QR1_PRE_PRINTED") %></div>
					<%=HtmlUtil.dropdown("QR1_PRE_PRINTED", "", "", "", "", "QR1", "ALL", HtmlUtil.EDIT, "", "col-sm-8 col-md-7", request)%>
				</div>				
			</div>
			<div class="clearfix"></div>
			<div class="col-sm-12">
				<div class="form-group">
					<div class="col-sm-4 col-md-5 control-label"><%=HtmlUtil.getMandatoryLabel(request, "QR2_RUNNING_NO") %></div>
					<%=HtmlUtil.textBox("QR2_RUNNING_NO", "", "", "", "50", HtmlUtil.EDIT, "", "col-sm-8 col-md-7", request)%>
				</div>				
			</div>
			<div class="clearfix"></div>
			<div class="col-sm-12">
				<div class="form-group">
					<div class="col-sm-4 col-md-5 control-label"><%=HtmlUtil.getMandatoryLabel(request, "RC_CODE") %></div>
					<%=HtmlUtil.dropdown("RC_CODE", "", "", "", "", "", "ALL", HtmlUtil.EDIT, "", "col-sm-8 col-md-7", request)%>
				</div>				
			</div>
			<div class="clearfix"></div>
			<div class="col-sm-12">
				<div class="form-group">
					<div class="col-sm-4 col-md-5 control-label"><%=HtmlUtil.getMandatoryLabel(request, "QR_CHANNEL") %></div>
					<%=HtmlUtil.dropdown("CHANNEL", "", "", "", "", "54", "ALL", HtmlUtil.EDIT, "", "col-sm-8 col-md-7", request)%>
				</div>				
			</div>
			<div class="clearfix"></div>
			<div class="col-sm-12">
				<div class="form-group">
					<div class="col-sm-4 col-md-5 control-label"><%=HtmlUtil.getMandatoryLabel(request, "QR_BRANCH") %></div>
					<%=HtmlUtil.search("BRANCH_NO", "", SEARCH_BRANCH_INFO, "", "", "", "ALL", HtmlUtil.EDIT, "", "col-sm-8 col-md-7", request)%>
				</div>				
			</div>
			<div class="clearfix"></div>
			<div class="col-sm-12">
				<div class="form-group">
					<div class="col-sm-4 col-md-5 control-label"><%=HtmlUtil.getMandatoryLabel(request, "QR_SCAN_DATE") %></div>
					<%=HtmlUtil.calendar("SCAN_DATE","","SCAN_DATE",ApplicationDate.getDate(),"",HtmlUtil.EDIT,"",HtmlUtil.TH,"col-sm-8 col-md-7",request) %>
				</div>
			</div>
			<div class="clearfix"></div>
			<div class="col-sm-12">
				<%=HtmlUtil.button("CREATE_CASE","Create Case",HtmlUtil.EDIT,"btn btn-default", "style='float: right;'",request)%>
			</div>
		</div>
	</div>
</div>
</div>
<div class="row col-xs-6">
	<div class="panel" style="padding: 10px;">
		<pre style="display:none;background-color: #fff;" id='result-form'></pre>
	</div>
</div>
<style>
.panel{
	box-shadow:inherit;
}
</style>
<script type="text/javascript">	
// 	$('[name="DOC_SET_NO"]').click(function(){ $('#start_new_app').prop("disabled", false);});
// 	$('[name="COVERPAGE_TYPE"]').change(function(){ $('#start_new_app').prop("disabled", false);});	
function QR_APPLY_TYPEActionJS(){
	var QR_APPLY_TYPE = $('[name="QR_APPLY_TYPE"]').val();
	if(QR_APPLY_TYPE == COVER_PAGE_TYPE_VETO) {
		targetDisplayHtml('QR2_RUNNING_NO',MODE_EDIT,'QR2_RUNNING_NO','Y');
		targetDisplayHtml('QR1_PRE_PRINTED',MODE_VIEW ,'QR1_PRE_PRINTED','Y');
		targetDisplayHtml('RC_CODE',MODE_VIEW ,'RC_CODE','Y');
		targetDisplayHtml('CHANNEL',MODE_VIEW ,'CHANNEL','Y');
		targetDisplayHtml('BRANCH_NO',MODE_VIEW ,'BRANCH_NO','Y');
// 		targetDisplayHtml('SCAN_DATE',MODE_VIEW ,'SCAN_DATE','Y');
	}else{
		targetDisplayHtml('QR2_RUNNING_NO',MODE_EDIT ,'QR2_RUNNING_NO','Y');
		targetDisplayHtml('QR1_PRE_PRINTED',MODE_EDIT ,'QR1_PRE_PRINTED','Y');
		targetDisplayHtml('RC_CODE',MODE_EDIT ,'RC_CODE','Y');
		targetDisplayHtml('CHANNEL',MODE_EDIT ,'CHANNEL','Y');
		targetDisplayHtml('BRANCH_NO',MODE_EDIT ,'BRANCH_NO','Y');
// 		targetDisplayHtml('SCAN_DATE',MODE_EDIT ,'SCAN_DATE','Y');
		ajax('com.eaf.orig.ulo.app.view.util.ajax.GetQr2No','',displayJSON);
	}
}
function CREATE_CASEActionJS(){
	if(CREATE_CASEValidateForm()){
		var dataRequest =  $('#createCaseForm *').serialize();
		ajax('com.eaf.orig.rest.controller.CreateIAApplication',dataRequest,CREATE_CASEAfterActionJS);
	}
}
function CREATE_CASEAfterActionJS(data){
	$('#result-form').html(getPrettyJson(data)).show("fast");
	targetDisplayHtml('QR_APPLY_TYPE',MODE_EDIT,'QR2_RUNNING_NO','Y');	
	targetDisplayHtml('QR1_PRE_PRINTED',MODE_EDIT ,'QR1_PRE_PRINTED','Y');
	targetDisplayHtml('QR2_RUNNING_NO',MODE_EDIT,'QR2_RUNNING_NO','Y');
	targetDisplayHtml('RC_CODE',MODE_EDIT ,'RC_CODE','Y');
	targetDisplayHtml('CHANNEL',MODE_EDIT ,'CHANNEL','Y');
	targetDisplayHtml('BRANCH_NO',MODE_EDIT ,'BRANCH_NO','Y');
}
function CREATE_CASEValidateForm(){
	var validateForm = true;
	/* $('#result-form').hide();
	$('#createCaseForm .form-group').removeClass('has-error');
	var QR_APPLY_TYPE = $('[name="QR_APPLY_TYPE"]').val();
	var QR1_PRE_PRINTED = $('[name="QR1_PRE_PRINTED"]').val();
	var QR2_RUNNING_NO = $('[name="QR2_RUNNING_NO"]').val();
	var RC_CODE = $('[name="RC_CODE"]').val();
	var CHANNEL = $('[name="CHANNEL"]').val();
	var BRANCH_NO = $('[name="BRANCH_NO"]').val();
	var SCAN_DATE = $('[name="SCAN_DATE"]').val();
	if(isEmpty(QR_APPLY_TYPE)){
		validateForm = false;
		var formElement = $("[name='QR_APPLY_TYPE']");
		formElement.closest('.form-group').addClass('has-error');
	}
	if(isEmpty(QR1_PRE_PRINTED) && QR_APPLY_TYPE != COVER_PAGE_TYPE_VETO){
		validateForm = false;
		var formElement = $("[name='QR1_PRE_PRINTED']");
		formElement.closest('.form-group').addClass('has-error');
	}
	if(isEmpty(QR2_RUNNING_NO)){
		validateForm = false;
		var formElement = $("[name='QR2_RUNNING_NO']");
		formElement.closest('.form-group').addClass('has-error');
	}*/
// 	#rawi comment
// 	if(isEmpty(RC_CODE)){
// 		validateForm = false;
// 		var formElement = $("[name='RC_CODE']");
// 		formElement.closest('.form-group').addClass('has-error');
// 	}
	/*if(isEmpty(CHANNEL)&& QR_APPLY_TYPE != COVER_PAGE_TYPE_VETO){
		validateForm = false;
		var formElement = $("[name='CHANNEL']");
		formElement.closest('.form-group').addClass('has-error');
	}
	if(isEmpty(BRANCH_NO)&& QR_APPLY_TYPE != COVER_PAGE_TYPE_VETO){
		validateForm = false;
		var formElement = $("[name='BRANCH_NO']");
		formElement.closest('.form-group').addClass('has-error');
	}
	if(isEmpty(SCAN_DATE)&& QR_APPLY_TYPE != COVER_PAGE_TYPE_VETO){
		validateForm = false;
		var formElement = $("[name='SCAN_DATE']");
		formElement.closest('.form-group').addClass('has-error');
	} */
	return validateForm;
}
</script>