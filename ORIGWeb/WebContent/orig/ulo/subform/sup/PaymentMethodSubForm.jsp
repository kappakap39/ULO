<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.PaymentMethodDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="EntityForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler"/>
<script type="text/javascript" src="orig/ulo/subform/sup/js/PaymentMethodSubForm.js"></script>
<%	
	Logger logger = Logger.getLogger(this.getClass());
	String subformId = "SUP_PAYMENT_METHOD_SUBFORM";	
	logger.debug("subformId >> "+subformId);		
 	PersonalApplicationInfoDataM personalApplicationInfo = (PersonalApplicationInfoDataM)EntityForm.getObjectForm();	
 	PersonalInfoDataM personalInfo = personalApplicationInfo.getPersonalInfo();
 	if(null == personalInfo){
 		personalInfo = new PersonalInfoDataM();
 	}
 	String personalId = personalApplicationInfo.getPersonalId();
 	logger.debug("personalId >> "+personalId);
 	int PERSONAL_SEQ = personalInfo.getSeq();
 	String PERSONAL_TYPE = personalInfo.getPersonalType();	
	String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	PaymentMethodDataM paymentMethod = null;
	String APPLICATION_TYPE_ADD_SUP = SystemConstant.getConstant("APPLICATION_TYPE_ADD_SUP");
	String applicationType = personalApplicationInfo.getApplicationType();
	logger.debug("applicationType >> "+applicationType);
	if(APPLICATION_TYPE_ADD_SUP.equals(applicationType)){
		paymentMethod = personalApplicationInfo.getPaymentMethodLifeCycleByPersonalId(personalId);
	}else{	
		paymentMethod = personalApplicationInfo.getPaymentMethodLifeCycleByPersonalId(personalId,PRODUCT_CRADIT_CARD);
	}
	if(null == paymentMethod){
		paymentMethod = new PaymentMethodDataM();
	}	
	String paymentMethodId = paymentMethod.getPaymentMethodId();
	logger.debug("paymentMethodId >> "+paymentMethodId);
	logger.debug("paymentMethod >> "+paymentMethod);
	String displayMode = HtmlUtil.EDIT;
// 	String TAG_SMART_DATA_PERSONAL = FormatUtil.getSmartDataEntryId(PERSONAL_TYPE,PERSONAL_SEQ);
// 	String TAG_SMART_DATA_PRODUCT = FormatUtil.getSmartDataEntryId(PRODUCT_CRADIT_CARD,TAG_SMART_DATA_PERSONAL);
	
	String productElementId = FormatUtil.getProductElementId(personalInfo,PRODUCT_CRADIT_CARD);
	
	String FIELD_ID_PAYMENT_METHOD = SystemConstant.getConstant("FIELD_ID_PAYMENT_METHOD");
	String FIELD_ID_PAYMENT_RATIO = SystemConstant.getConstant("FIELD_ID_PAYMENT_RATIO");
		
	FormUtil formUtil = new FormUtil("SUP_PRODUCT_FORM_CC",subformId,request);
	String businessClassId = "ALL_ALL_ALL";
	ApplicationDataM applicationItem = personalApplicationInfo.getApplicationProduct(PRODUCT_CRADIT_CARD);
	if(!Util.empty(applicationItem)) {
		businessClassId = applicationItem.getBusinessClassId();
	}
%>
<div class="panel panel-default">
<div class="panel-body">
<div class="row form-horizontal">
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"PAYMENT_METHOD","PAYMENT_METHOD","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.dropdown("PAYMENT_METHOD","","PAYMENT_METHOD_"+productElementId,"SUP_PAYMENT_METHOD", "",
				paymentMethod.getPaymentMethod(),"",FIELD_ID_PAYMENT_METHOD,"ALL_ALL_ALL","","col-sm-8 col-md-7", paymentMethod, formUtil)%>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId, "PAYMENT_RATIO", "PAYMENT_RATIO", "col-sm-4 col-md-5 control-label")%>
			 <%=HtmlUtil.dropdown("PAYMENT_RATIO","","PAYMENT_RATIO_"+productElementId, "SUP_PAYMENT_RATIO", "SupPaymentRatio",String.valueOf(paymentMethod.getPaymentRatio()), "", FIELD_ID_PAYMENT_RATIO, businessClassId,"", "col-sm-8 col-md-7",paymentMethod, formUtil) %>

		</div>
	</div>
	<div class="clearfix"></div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId, "ACCOUNT_NO", "ACCOUNT_NO", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBoxAccountNo("ACCOUNT_NO","","ACCOUNT_NO_"+productElementId,"SUP_ACCOUNT_NO",
				paymentMethod.getAccountNo(),"","","col-sm-8 col-md-7", paymentMethod,formUtil)%>
		</div>
	</div>
	<div class="col-md-6">
		<div class="form-group">
			<%=HtmlUtil.hidden("COMPLETE_DATA", paymentMethod.getCompleteData()) %>
			<%=HtmlUtil.getSubFormLabel(request,subformId,"ACCOUNT_NAME","ACCOUNT_NAME", "col-sm-4 col-md-5 control-label")%>
			<div id="ACCOUNT_NAME" class="col-sm-8 col-md-7"><%=FormatUtil.display(paymentMethod.getAccountName())%></div>
		</div>
	</div>
	<div class="clearfix"></div>
</div>
</div>
</div>
