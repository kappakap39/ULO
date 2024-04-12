<%@page import="org.restlet.engine.util.FormUtils"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.BundleSMEDataM"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="EntityForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler"/>
<jsp:useBean id="ModuleForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler"/>
<script type="text/javascript" src="orig/ulo/popup/income/js/BundleSMEPopup.js"></script>
<%
	Logger logger = Logger.getLogger(this.getClass());
	BundleSMEDataM bundleSME = (BundleSMEDataM)ModuleForm.getObjectForm();
	ApplicationGroupDataM appGroupM = (ApplicationGroupDataM) EntityForm.getObjectForm();
	ApplicationDataM appM = appGroupM.getApplicationById(bundleSME.getApplicationRecordId());
	logger.debug("bundleSME applicant qulity >>"+bundleSME.getApplicantQuality());
	String FIELD_ID_BORROWING_TYPE = SystemConstant.getConstant("FIELD_ID_BORROWING_TYPE");
	String FIELD_ID_TYPE_LIMIT = SystemConstant.getConstant("FIELD_ID_TYPE_LIMIT");
	String FIELD_ID_APPLICANT_QUALITY = SystemConstant.getConstant("FIELD_ID_APPLICANT_QUALITY");
	String SEARCH_DIH_BUSINESS_CODE = SystemConstant.getConstant("SEARCH_DIH_BUSINESS_CODE");
	String BUSINESS_CODE_CACHE = SystemConstant.getConstant("BUSINESS_CODE_CACHE");
	String PRODUCT_K_EXPRESS_CASH = SystemConstant.getConstant("PRODUCT_K_EXPRESS_CASH");
	String subformId = "BUNDLING_SME_POPUP";
	if(PRODUCT_K_EXPRESS_CASH.equals(appM.getProduct())) {
		subformId = "BUNDLING_SME_KEC_POPUP";
	}
	FormUtil formUtil = new FormUtil(subformId, request);
%>
<div class="panel panel-default">
	<div class="panel-body">
		<div class="row form-horizontal">
			<div class="col-sm-6">
				<div class="form-group">
				<%=HtmlUtil.getSubFormLabel(request,subformId,"BORROWING_TYPE","BORROWING_TYPE","col-sm-4 col-md-5 control-label")%>
				<%=HtmlUtil.dropdown("BORROWING_TYPE","BORROWING_TYPE","BORROWING_TYPE","",bundleSME.getBorrowingType(),"",FIELD_ID_BORROWING_TYPE,"ALL_ALL_ALL","","col-sm-8 col-md-7",formUtil)%>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group">
				<%=HtmlUtil.getSubFormLabel(request,subformId,"APPLICANT_QUALITY","APPLICANT_QUALITY","col-sm-4 col-md-5 control-label")%>
				<%=HtmlUtil.dropdown("APPLICANT_QUALITY","APPLICANT_QUALITY","APPLICANT_QUALITY","",bundleSME.getApplicantQuality(),"",FIELD_ID_APPLICANT_QUALITY,"ALL_ALL_ALL","","col-sm-8 col-md-7",formUtil)%>
				</div>
			</div>
			<div class="clearfix"></div>
			<div class="col-sm-6">
				<div class="form-group">
				<%=HtmlUtil.getSubFormLabel(request,subformId,"BUSINESS_CODE","BUSINESS_CODE","col-sm-4 col-md-5 control-label")%>
<%-- 				<%=HtmlUtil.dropdown("BUSINESS_CODE", "", "BUSINESS_CODE", "BUSINESS_CODE", "", bundleSME.getBusinessCode(), "", BUSINESS_CODE_CACHE, "", "", "col-sm-8 col-md-7", formUtil)%> --%>
				<%=HtmlUtil.search("BUSINESS_CODE","BUSINESS_CODE","",SEARCH_DIH_BUSINESS_CODE,bundleSME.getBusinessCode(),"","","","","col-sm-8 col-md-7",formUtil)%>
				</div>
			</div>
			<div class="col-sm-6">
			</div>
			<div class="clearfix"></div>
			<div class="col-sm-6">
				<div class="form-group">
				<%=HtmlUtil.getSubFormLabel(request,subformId,"TYPE_LIMIT","TYPE_LIMIT","col-sm-4 col-md-5 control-label")%>
				<%=HtmlUtil.dropdown("TYPE_LIMIT", "TYPE_LIMIT", "TYPE_LIMIT","", bundleSME.getTypeLimit(), "", FIELD_ID_TYPE_LIMIT, "ALL_ALL_ALL","","col-sm-8 col-md-7", formUtil)%>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group">
				<%=HtmlUtil.getSubFormLabel(request,subformId,"BUNDLE_ACCOUNT_NO","BUNDLE_ACCOUNT_NO","col-sm-4 col-md-5 control-label")%>
				<%=HtmlUtil.textBoxAccountNo("BUNDLE_ACCOUNT_NO", "", "BUNDLE_ACCOUNT_NO", "BUNDLE_ACCOUNT_NO",FormatUtil.removeDash(bundleSME.getAccountNo()), "", "","19", "col-sm-8 col-md-7",appGroupM,formUtil) %>
				</div>
			</div>
			<div class="clearfix"></div>
			<div class="col-sm-6">
				<div class="form-group">
				<%=HtmlUtil.getSubFormLabel(request,subformId,"ACCOUNT_NAME","ACCOUNT_NAME","col-sm-4 col-md-5 control-label")%>
				<%=HtmlUtil.textBoxTH("ACCOUNT_NAME", "", bundleSME.getAccountName(), null, "10", HtmlUtil.VIEW, "", "col-sm-4 col-md-5 control-label", request)%>
				</div>
			</div>
				<div class="col-sm-6">
				<div class="form-group">
				<%=HtmlUtil.getSubFormLabel(request,subformId,"ACCOUNT_DATE","ACCOUNT_DATE","col-sm-4 col-md-5 control-label")%>
				<%=HtmlUtil.textBox("ACCOUNT_DATE", "", FormatUtil.display(bundleSME.getAccountDate(), HtmlUtil.TH), null, "15", HtmlUtil.VIEW, "", "col-sm-4 col-md-5 control-label", request)%>
				</div>
			</div>
			<div class="clearfix"></div>
			<div class="col-sm-6">
				<div class="form-group">
				<%=HtmlUtil.getSubFormLabel(request,subformId,"APPROVAL_DATE","APPROVAL_DATE","col-sm-4 col-md-5 control-label")%>
				<%=HtmlUtil.calendar("APPROVAL_DATE","APPROVAL_DATE","APPROVAL_DATE",bundleSME.getApprovalDate(),"","",HtmlUtil.TH,"col-sm-8 col-md-7",formUtil)%>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group">
				<%=HtmlUtil.getSubFormLabel(request,subformId,"APPROVAL_LIMIT","APPROVAL_LIMIT","col-sm-4 col-md-5 control-label")%>
				<%=HtmlUtil.currencyBox("APPROVAL_LIMIT","APPROVAL_LIMIT","APPROVAL_LIMIT",bundleSME.getApprovalLimit(),"",true,"15","","col-sm-8 col-md-7",formUtil)%>
				</div>
			</div>	
			<div class="clearfix"></div>
			<div class="col-sm-6">
				<div class="form-group">
				<%=HtmlUtil.getSubFormLabel(request,subformId,"INDIVIDUAL_RATIO","INDIVIDUAL_RATIO","col-sm-4 col-md-5 control-label")%>
				<%=HtmlUtil.numberBox("INDIVIDUAL_RATIO","INDIVIDUAL_RATIO","RATIO_FIELD",bundleSME.getIndividualRatio(),"","###",true,"3","","col-sm-8 col-md-7",formUtil)%>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group">
				<%=HtmlUtil.getSubFormLabel(request,subformId,"CORPORATE_RATIO","CORPORATE_RATIO","col-sm-4 col-md-5 control-label")%>
				<%=HtmlUtil.numberBox("CORPORATE_RATIO","CORPORATE_RATIO","RATIO_FIELD",bundleSME.getCorporateRatio(),"","###",true,"3","","col-sm-8 col-md-7",formUtil)%>
				</div>
			</div>
			<div class="clearfix"></div>
			<div class="col-sm-6">
				<div class="form-group">
				<%=HtmlUtil.getSubFormLabel(request,subformId,"","VERIFIED_INCOME_SME","col-sm-4 col-md-5 control-label")%>
				<%=HtmlUtil.currencyBox("VERIFIED_INCOME","VERIFIED_INCOME","VER_INCOME_FIELD",bundleSME.getVerifiedIncome(),"",true,"15","","col-sm-8 col-md-7",formUtil)%>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group">
				<%=HtmlUtil.getSubFormLabel(request,subformId,"","G_TOTAL_EXIST_PAYMENT","col-sm-4 col-md-5 control-label")%>
				<%=HtmlUtil.currencyBox("G_TOTAL_EXIST_PAYMENT","G_TOTAL_EXIST_PAYMENT","G_FIELD",bundleSME.getgTotExistPayment(),"",true,"15","","col-sm-8 col-md-7",formUtil)%>
				</div>
			</div>
			<div class="clearfix"></div>
			<div class="col-sm-6">
				<div class="form-group">
				<%=HtmlUtil.getSubFormLabel(request,subformId,"","G_TOTAL_NEW_PAY_REQ","col-sm-4 col-md-5 control-label")%>
				<%=HtmlUtil.currencyBox("G_TOTAL_NEW_PAY_REQ","G_TOTAL_NEW_PAY_REQ","G_FIELD",bundleSME.getgTotNewPayReq(),"",true,"15","","col-sm-8 col-md-7",formUtil)%>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group">
				<%=HtmlUtil.getSubFormLabel(request,subformId,"","G_DSCR_REQ","col-sm-4 col-md-5 control-label")%>
				<%=HtmlUtil.currencyBox("G_DSCR_REQ","G_DSCR_REQ","G_FIELD",bundleSME.getgDscrReq(),"",true,"15","","col-sm-8 col-md-7",formUtil)%>
				</div>
			</div>
			<div class="col-sm-6">
				
			</div>
			
			
		</div>
	</div>
</div>