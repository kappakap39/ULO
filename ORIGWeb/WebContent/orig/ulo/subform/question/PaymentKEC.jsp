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
	String displayMode = HtmlUtil.EDIT;	
	String PRODUCT_K_EXPRESS_CASH = SystemConstant.getConstant("PRODUCT_K_EXPRESS_CASH");
	String FIELD_ID_PAYMENT_METHOD = SystemConstant.getConstant("FIELD_ID_PAYMENT_METHOD");
	String FIELD_ID_PAYMENT_RATIO = SystemConstant.getConstant("FIELD_ID_PAYMENT_RATIO");
	ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)EntityForm.getObjectForm();
	String personalId = request.getParameter("PERSONAL_ID");
	PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfoId(personalId);
	PaymentMethodDataM paymentMethod = applicationGroup.getPaymentMethodLifeCycleByPersonalId(personalId,PRODUCT_K_EXPRESS_CASH);	
	String productElementId = FormatUtil.getProductElementId(personalInfo,PRODUCT_K_EXPRESS_CASH);
%>
	<div class="col-sm-12">
		<div class="form-group">
			<%=HtmlUtil.getLabel(request, "PAYMENT_METHOD_KEC","")%>
		</div>
	</div>
	<div class="clearfix"></div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getMandatoryLabel(request, "PAYMENT_METHOD","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.dropdown("PAYMENT_METHOD", PRODUCT_K_EXPRESS_CASH, "", paymentMethod.getPaymentMethod(),"", FIELD_ID_PAYMENT_METHOD, "ALL", displayMode
			, HtmlUtil.elementTagId("PAYMENT_METHOD",productElementId), "col-sm-8 col-md-7", request)%>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getFieldLabel(request, "PAYMENT_RATIO","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.dropdown("PAYMENT_RATIO", PRODUCT_K_EXPRESS_CASH, "", FormatUtil.display(paymentMethod.getPaymentRatio()) ,"", FIELD_ID_PAYMENT_RATIO, "ALL", HtmlUtil.VIEW
			, HtmlUtil.elementTagId("PAYMENT_RATIO",productElementId), "col-sm-8 col-md-7", request)%>
		</div>
	</div>
	<div class="clearfix"></div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getFieldLabel(request, "ACCOUNT_NO","col-sm-4 col-md-5 control-label") %>
			<%=HtmlUtil.textBoxAccountNo("ACCOUNT_NO",PRODUCT_K_EXPRESS_CASH, paymentMethod.getAccountNo(),"",HtmlUtil.VIEW
			, HtmlUtil.elementTagId("ACCOUNT_NO",productElementId),"col-sm-8 col-md-7", request)%>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.hidden("COMPLETE_DATA_"+PRODUCT_K_EXPRESS_CASH, paymentMethod.getCompleteData()) %>
			<%=HtmlUtil.getFieldLabel(request, "ACCOUNT_NAME","col-sm-4 col-md-5 control-label")%>
			<div id="ACCOUNT_NAME_<%=PRODUCT_K_EXPRESS_CASH %>" class="col-sm-8 col-md-7"><%=FormatUtil.display(paymentMethod.getAccountName())%></div>
		</div>
	</div>
	<div class="clearfix"></div>
