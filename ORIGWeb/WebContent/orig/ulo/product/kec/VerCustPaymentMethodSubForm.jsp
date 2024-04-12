<%@page import="java.math.BigDecimal"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="java.math.BigDecimal" %>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.properties.FormControl"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PaymentMethodDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler"%>
<%@page import="com.eaf.orig.ulo.model.app.LoanDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<jsp:useBean id="EntityForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler"/>
<script type="text/javascript" src="orig/ulo/product/js/VerCustPaymentMethodSubForm.js"></script>
<%
	String subformId = "VER_CUSTOMER_KEC_PAYMENT_METHOD_SUBFORM";
	Logger logger = Logger.getLogger(this.getClass());
	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");	
	String PRODUCT_K_EXPRESS_CASH = SystemConstant.getConstant("PRODUCT_K_EXPRESS_CASH");
	String PRODUCT_CODE_CC = SystemConstant.getConstant("PRODUCT_CODE_CC");
	String FIELD_ID_PAYMENT_METHOD_VER_CUST = SystemConstant.getConstant("FIELD_ID_PAYMENT_METHOD");
	String FIELD_ID_PAYMENT_RATIO = SystemConstant.getConstant("FIELD_ID_PAYMENT_RATIO");
	ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM) EntityForm.getObjectForm();
	String businessClassId = "ALL_ALL_ALL";
	ApplicationDataM applicationItem = applicationGroup.getApplicationProduct(PRODUCT_K_EXPRESS_CASH);
	if(!Util.empty(applicationItem)) {
		businessClassId = applicationItem.getBusinessClassId();
	}
	PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
	if(null == personalInfo){
		personalInfo = new PersonalInfoDataM();
	}
	LoanDataM loan = applicationItem.getLoan();
	//logger.debug("applicationItem>>>>"+applicationItem.getLoan());
	//Check Loan
    BigDecimal loanAmt = new BigDecimal(0);
    BigDecimal paymentRatio = new BigDecimal(3);
	if(null == loan){
		loan = new LoanDataM();	
	} 
	loanAmt = loan.getLoanAmt();
	String personalId = personalInfo.getPersonalId();
	PaymentMethodDataM paymentMethod = applicationGroup.getPaymentMethodLifeCycleByPersonalId(personalId,PRODUCT_K_EXPRESS_CASH);
	if(null == paymentMethod){
		paymentMethod = new PaymentMethodDataM();
	}	
	
	String fullAccountNo = "";
	if(null != paymentMethod.getAccountNo()){
		fullAccountNo = fullAccountNo+FormatUtil.getAccountNo(paymentMethod.getAccountNo());
	}
	if(null != paymentMethod.getAccountName()){
		fullAccountNo = fullAccountNo+" "+paymentMethod.getAccountName();
	}
	paymentMethod.setFullAccountNo(fullAccountNo);
	logger.debug("paymentMethod>>>>"+paymentMethod);
	logger.debug("fullAccountNo>>>>"+fullAccountNo);
	int PERSONAL_SEQ = personalInfo.getSeq();
	String displayMode = HtmlUtil.EDIT;
// 	String TAG_SMART_DATA_PERSONAL = FormatUtil.getSmartDataEntryId(personalInfo.getPersonalType(),PERSONAL_SEQ);
// 	String TAG_SMART_DATA_PRODUCT = FormatUtil.getSmartDataEntryId(PRODUCT_K_EXPRESS_CASH,TAG_SMART_DATA_PERSONAL);
	String productElementId = FormatUtil.getProductElementId(personalInfo,PRODUCT_K_EXPRESS_CASH);
	FormUtil formUtil = new FormUtil("VERIFY_CUSTOMER_FORM", subformId,request);
	if(null == paymentMethod.getPaymentRatio()){
		paymentMethod.setPaymentRatio(paymentRatio);
	}
%>
<section id="SECTION_VER_CUSTOMER_KEC_PAYMENT_METHOD_SUBFORM" >
	<header class="titlecontent"><%=LabelUtil.getText(request,"PAYMENT_METHOD_MAIN_SUBFORM_KEC")%></header>
		<section class ="work_area">
	<div class="panel panel-default">
		 <div class="panel-body">
			<div class="row form-horizontal">
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.getSubFormLabel(request,subformId,"PAYMENT_METHOD","PAYMENT_METHOD","col-sm-4 col-md-5 control-label")%>
					    <%=HtmlUtil.dropdown("PAYMENT_METHOD",PRODUCT_K_EXPRESS_CASH, "PAYMENT_METHOD_"+productElementId, "VER_CUST_PAYMENT_METHOD", "", 
							paymentMethod.getPaymentMethod(), "",FIELD_ID_PAYMENT_METHOD_VER_CUST, businessClassId, "", "col-sm-8 col-md-7",paymentMethod, formUtil)%>
					</div>
				</div>
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.getSubFormLabel(request,subformId,"PAYMENT_RATIO","PAYMENT_RATIO","col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.dropdown("PAYMENT_RATIO",PRODUCT_K_EXPRESS_CASH, "PAYMENT_RATIO_"+productElementId, "PAYMENT_RATIO", "", 
						FormatUtil.display(paymentMethod.getPaymentRatio()), "",FIELD_ID_PAYMENT_RATIO, businessClassId, "", "col-sm-8 col-md-7",paymentMethod, formUtil)%>
					</div>
				</div>
				<div class="clearfix"></div>
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.getSubFormLabel(request,subformId,"ACCOUNT_NO","ACCOUNT_NO","col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.autoComplete("ACCOUNT_NO",PRODUCT_K_EXPRESS_CASH, "ACCOUNT_NO_"+productElementId, "ACCOUNT_NO", "ACCOUNT_NO_LISTBOX",
						  paymentMethod.getFullAccountNo(), "", "","ALL_ALL_ALL","","col-sm-8 col-md-7", "100", formUtil)%>
						
					</div>
				</div>
			 	<div class="col-sm-6">
					<div class="form-group">
						
					</div>
				</div>
				<div class="clearfix"></div>
				<div class="col-sm-6">
					<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request,subformId,"APPROVAL_LIMIT","APPROVAL_LIMIT","col-sm-4 col-md-5 control-label")%>
					<%=HtmlUtil.currencyBox("APPROVAL_LIMIT", "", "APPROVAL_LIMIT_"+productElementId, "APPROVAL_LIMIT",loanAmt, "", true, "", "", "col-sm-8 col-md-7", applicationItem, formUtil) %>
					</div>
				</div>	  
			</div>
		</div>
	</div>
</section></section>
<%=HtmlUtil.hidden("HIDDEN_ACCOUNT_NAME",paymentMethod.getAccountName()) %>
<%=HtmlUtil.hidden("HIDDEN_ACCOUNT_NO",paymentMethod.getAccountNo()) %>
 
