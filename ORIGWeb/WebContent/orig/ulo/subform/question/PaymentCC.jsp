<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PaymentMethodDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page contentType="text/html;charset=UTF-8"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="EntityForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler"/>

<%
	String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	String FIELD_ID_PAYMENT_METHOD = SystemConstant.getConstant("FIELD_ID_PAYMENT_METHOD");
	String FIELD_ID_PAYMENT_RATIO = SystemConstant.getConstant("FIELD_ID_PAYMENT_RATIO");
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");		
	ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)EntityForm.getObjectForm();
	String personalId = request.getParameter("PERSONAL_ID");
	PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfoId(personalId);		
	PaymentMethodDataM paymentMethod = applicationGroup.getPaymentMethodLifeCycleByPersonalId(personalId,PRODUCT_CRADIT_CARD);
	
// 	String TAG_SMART_DATA_PERSONAL = FormatUtil.getSmartDataEntryId(personalInfo.getPersonalType(),personalInfo.getSeq());
// 	String TAG_SMART_DATA_PRODUCT = FormatUtil.getSmartDataEntryId(PRODUCT_CRADIT_CARD,TAG_SMART_DATA_PERSONAL);	
	
	String productElementId = FormatUtil.getProductElementId(personalInfo,PRODUCT_CRADIT_CARD);
	
	String subformId ="IDENTIFY_QUESTION_CUSTOMER_SUBFORM";
	FormUtil formUtil = new FormUtil(subformId,request);
%>

	<div class="col-sm-12">
		<div class="form-group">
			<%=HtmlUtil.getLabel(request, "PAYMENT_METHOD_CC", "") %>
		</div>
	</div>
	<div class="clearfix"></div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getMandatoryLabel(request, "PAYMENT_METHOD","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.dropdown("PAYMENT_METHOD", productElementId, "", "", "", paymentMethod.getPaymentMethod(), "", FIELD_ID_PAYMENT_METHOD, "ALL"
			, HtmlUtil.elementTagId("PAYMENT_METHOD",productElementId), "col-sm-8 col-md-7", formUtil)%>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getFieldLabel(request, "PAYMENT_RATIO","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.dropdown("PAYMENT_RATIO", productElementId, "", FormatUtil.display(paymentMethod.getPaymentRatio()) ,"", FIELD_ID_PAYMENT_RATIO, "ALL", HtmlUtil.VIEW
			, HtmlUtil.elementTagId("PAYMENT_RATIO",productElementId), "col-sm-8 col-md-7", request)%>
		</div>
	</div>
	<div class="clearfix"></div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getFieldLabel(request, "ACCOUNT_NO","col-sm-4 col-md-5 control-label") %>
			<%=HtmlUtil.textBoxAccountNo("ACCOUNT_NO",productElementId, paymentMethod.getAccountNo(),"",HtmlUtil.VIEW
			, HtmlUtil.elementTagId("ACCOUNT_NO",productElementId),"col-sm-8 col-md-7", request)%>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.hidden("COMPLETE_DATA_"+productElementId, paymentMethod.getCompleteData()) %>
			<%=HtmlUtil.getFieldLabel(request, "ACCOUNT_NAME","col-sm-4 col-md-5 control-label")%>
			<div id="ACCOUNT_NAME_<%=productElementId %>" class="col-sm-8 col-md-7"><%=FormatUtil.display(paymentMethod.getAccountName())%></div>
		</div>
	</div>