<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.BundleHLDataM"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ModuleForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler"/>

<script type="text/javascript" src="orig/ulo/popup/income/js/BundleHLPopup.js"></script>
<%
	Logger logger = Logger.getLogger(this.getClass());
	BundleHLDataM bundleHL = (BundleHLDataM)ModuleForm.getObjectForm();
	
	String subformId = "BUNDLING_HL_POPUP";
	FormUtil formUtil = new FormUtil(subformId, request);
%>
<div class="panel panel-default">
	<div class="panel-body">
		<div class="row form-horizontal">
			<div class="col-sm-6">
				<div class="form-group">
				<%=HtmlUtil.getSubFormLabel(request,subformId,"VERIFIED_INCOME","VERIFIED_INCOME","col-sm-4 col-md-5 control-label")%>
				<%=HtmlUtil.currencyBox("VERIFIED_INCOME","VERIFIED_INCOME","",bundleHL.getVerifiedIncome(),"",true,"15","","col-sm-8 col-md-7",formUtil)%>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group">
				<%=HtmlUtil.getSubFormLabel(request,subformId,"APPROVAL_DATE","APPROVAL_DATE","col-sm-4 col-md-5 control-label")%>
				<%=HtmlUtil.calendar("APPROVAL_DATE","APPROVAL_DATE","APPROVAL_DATE",bundleHL.getApprovedDate(),"","",HtmlUtil.EN,"col-sm-8 col-md-7",formUtil)%>
				</div>
			</div>
			<div class="clearfix"></div>
			<div class="col-sm-6">
				<div class="form-group">
				<%=HtmlUtil.getSubFormLabel(request,subformId,"CC_CREDIT_LINE","CC_CREDIT_LINE","col-sm-4 col-md-5 control-label")%>
				<%=HtmlUtil.currencyBox("CC_CREDIT_LINE","CC_CREDIT_LINE","CC_CREDIT_LINE",bundleHL.getCcApprovedAmt(),"",true,"15","","col-sm-8 col-md-7",formUtil)%>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group">
				<%=HtmlUtil.getSubFormLabel(request,subformId,"KEC_CREDIT_LINE","KEC_CREDIT_LINE","col-sm-4 col-md-5 control-label")%>
				<%=HtmlUtil.currencyBox("KEC_CREDIT_LINE","KEC_CREDIT_LINE","KEC_CREDIT_LINE",bundleHL.getKecApprovedAmt(),"",true,"15","","col-sm-8 col-md-7",formUtil)%>
				</div>
			</div>
			<div class="clearfix"></div>
			<div class="col-sm-6">
				<div class="form-group">
				<%=HtmlUtil.getSubFormLabel(request,subformId,"BUNDLING_HL_MORTGAGE_DATE","BUNDLING_HL_MORTGAGE_DATE","col-sm-4 col-md-5 control-label")%>
				<%=HtmlUtil.calendar("BUNDLING_HL_MORTGAGE_DATE","BUNDLING_HL_MORTGAGE_DATE","BUNDLING_HL_MORTGAGE_DATE",bundleHL.getMortGageDate(),"","",HtmlUtil.TH,"col-sm-8 col-md-7",formUtil)%>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group">
				<%=HtmlUtil.getSubFormLabel(request,subformId,"BUNDLING_HL_ACCOUNT_NO","BUNDLING_HL_ACCOUNT_NO","col-sm-4 col-md-5 control-label")%>
				<%=HtmlUtil.textBoxAccountNo("BUNDLING_HL_ACCOUNT_NO", "", "BUNDLING_HL_ACCOUNT_NO", "BUNDLING_HL_ACCOUNT_NO",FormatUtil.removeDash(bundleHL.getAccountNo()), "", "","19", "col-sm-8 col-md-7",null,formUtil) %>
				</div>
			</div>
			<div class="clearfix"></div>
			<div class="col-sm-6">
				<div class="form-group">
				<%=HtmlUtil.getSubFormLabel(request,subformId,"ACCOUNT_NAME","ACCOUNT_NAME","col-sm-4 col-md-5 control-label")%>
				<%=HtmlUtil.textBoxTH("ACCOUNT_NAME", "", bundleHL.getAccountName(), null, "10", HtmlUtil.VIEW, "", "col-sm-4 col-md-5 control-label", request)%>
				</div>
			</div>
				<div class="col-sm-6">
				<div class="form-group">
				<%=HtmlUtil.getSubFormLabel(request,subformId,"ACCOUNT_DATE","ACCOUNT_DATE","col-sm-4 col-md-5 control-label")%>
				<%=HtmlUtil.textBox("ACCOUNT_DATE", "", FormatUtil.display(bundleHL.getAccountOpenDate(), HtmlUtil.TH), null, "15", HtmlUtil.VIEW, "", "col-sm-4 col-md-5 control-label", request)%>
				</div>
			</div>
			
			
		</div>
	</div>
</div>