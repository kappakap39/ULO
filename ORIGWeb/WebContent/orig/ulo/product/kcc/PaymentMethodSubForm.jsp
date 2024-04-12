<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.properties.FormControl"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.PaymentMethodDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<script type="text/javascript" src="orig/ulo/product/js/PaymentMethodSubForm.js"></script>
<%
	String subformId = "KCC_PAYMENT_METHOD_SUBFORM";
	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");	
	Logger logger = Logger.getLogger(this.getClass());
	String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	String FIELD_ID_PAYMENT_METHOD = SystemConstant.getConstant("FIELD_ID_PAYMENT_METHOD");
	String FIELD_ID_PAYMENT_RATIO = SystemConstant.getConstant("FIELD_ID_PAYMENT_RATIO");
	String BORROWER = SystemConstant.getConstant("APPLICATION_CARD_TYPE_BORROWER");
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	logger.debug("applicationGroup >>> "+applicationGroup);
	String businessClassId = "ALL_ALL_ALL";
	ApplicationDataM applicationItem = applicationGroup.getApplicationProduct(PRODUCT_CRADIT_CARD);
	if(!Util.empty(applicationItem)) {
		businessClassId = applicationItem.getBusinessClassId();
	}
	PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
	if(null == personalInfo){
		personalInfo = new PersonalInfoDataM();
	}
	String personalId = personalInfo.getPersonalId();
	logger.debug("personalId >> "+personalId);
	PaymentMethodDataM paymentMethod = applicationGroup.getPaymentMethodLifeCycleByPersonalId(personalId,PRODUCT_CRADIT_CARD);
	if(null == paymentMethod){
		paymentMethod = new PaymentMethodDataM();
	}	
	int PERSONAL_SEQ = personalInfo.getSeq();
	String displayMode = HtmlUtil.EDIT;
	
// 	String TAG_SMART_DATA_PERSONAL = FormatUtil.getSmartDataEntryId(personalInfo.getPersonalType(),PERSONAL_SEQ);
// 	String TAG_SMART_DATA_PRODUCT = FormatUtil.getSmartDataEntryId(PRODUCT_CRADIT_CARD,TAG_SMART_DATA_PERSONAL);
	
	String productElementId = FormatUtil.getProductElementId(personalInfo,PRODUCT_CRADIT_CARD);
	
	logger.debug("paymentMethod.getAccountNo() >>> "+paymentMethod.getAccountNo());
	
	FormUtil formUtil = new FormUtil("PRODUCT_FORM_CC", subformId,request);
%>
<div class="panel panel-default">
	<div class="panel-body">
		<div class="row form-horizontal">
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request,subformId,"PAYMENT_METHOD","PAYMENT_METHOD","col-sm-4 col-md-5 control-label")%>
					<%=HtmlUtil.dropdown("PAYMENT_METHOD",PRODUCT_CRADIT_CARD, "PAYMENT_METHOD_"+productElementId, "PAYMENT_METHOD", "", 
						paymentMethod.getPaymentMethod(), "",FIELD_ID_PAYMENT_METHOD,businessClassId, "", "col-sm-8 col-md-7",paymentMethod, formUtil)%>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request,subformId,"PAYMENT_RATIO","PAYMENT_RATIO","col-sm-4 col-md-5 control-label")%>
					<%=HtmlUtil.dropdown("PAYMENT_RATIO",PRODUCT_CRADIT_CARD, "PAYMENT_RATIO_"+productElementId, "PAYMENT_RATIO", "", 
						FormatUtil.display(paymentMethod.getPaymentRatio()), "",FIELD_ID_PAYMENT_RATIO,businessClassId, "", "col-sm-8 col-md-7",paymentMethod, formUtil)%>
				</div>
			</div>
			<div class="clearfix"></div>
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request,subformId,"ACCOUNT_NO","ACCOUNT_NO","col-sm-4 col-md-5 control-label")%>
					<%=HtmlUtil.textBoxAccountNo("ACCOUNT_NO", PRODUCT_CRADIT_CARD, "ACCOUNT_NO_"+productElementId, "ACCOUNT_NO",
					FormatUtil.removeDash(paymentMethod.getAccountNo()), "", "", "col-sm-8 col-md-7",paymentMethod, formUtil) %>
				</div>
			</div>
			 <div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.hidden("COMPLETE_DATA_"+PRODUCT_CRADIT_CARD, paymentMethod.getCompleteData()) %>
					<%=HtmlUtil.getSubFormLabel(request,subformId,"ACCOUNT_NAME","ACCOUNT_NAME","col-sm-4 col-md-5 control-label")%>
					<div id="ACCOUNT_NAME_<%=PRODUCT_CRADIT_CARD %>" class="col-sm-8 col-md-7"><%=FormatUtil.display(paymentMethod.getAccountName())%></div>
				</div>
			</div>  
		</div>
	</div>
</div>