<%@page import="com.eaf.core.ulo.common.util.FormDisplayModeUtil"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.PaymentMethodDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page contentType="text/html;charset=UTF-8"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="EntityForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler"/>

<%
	String PRODUCT_K_PERSONAL_LOAN = SystemConstant.getConstant("PRODUCT_K_PERSONAL_LOAN"); 
	ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)EntityForm.getObjectForm();
	String personalId = request.getParameter("PERSONAL_ID");
	PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfoId(personalId);
	if(Util.empty(personalInfo)){
		personalInfo = new PersonalInfoDataM();
	}
	PaymentMethodDataM paymentMethod = applicationGroup.getPaymentMethodLifeCycleByPersonalId(personalId, PRODUCT_K_PERSONAL_LOAN);			
	String subformId ="IDENTIFY_QUESTION_CUSTOMER_SUBFORM";
	FormUtil formUtil = new FormUtil(subformId,request);
	String displayMode =FormDisplayModeUtil.getDisplayMode("", "", formUtil);
	String productElementId = FormatUtil.getProductElementId(personalInfo,PRODUCT_K_PERSONAL_LOAN);
%>
	<div class="col-sm-12">
		<div class="form-group">
			<%=HtmlUtil.getLabel(request, "PAYMENT_METHOD_KPL","")%>
		</div>
	</div>
	<div class="clearfix"></div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getFieldLabel(request, "ACCOUNT_NO","col-sm-4 col-md-5 control-label") %>
			<%=HtmlUtil.textBoxAccountNo("ACCOUNT_NO",PRODUCT_K_PERSONAL_LOAN, paymentMethod.getAccountNo(),"",displayMode
			, HtmlUtil.elementTagId("ACCOUNT_NO",productElementId),"col-sm-8 col-md-7", request)%>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.hidden("COMPLETE_DATA_"+PRODUCT_K_PERSONAL_LOAN, paymentMethod.getCompleteData()) %>
			<%=HtmlUtil.getFieldLabel(request, "ACCOUNT_NAME","col-sm-4 col-md-5 control-label")%>
			<div id="ACCOUNT_NAME_<%=PRODUCT_K_PERSONAL_LOAN %>" class="col-sm-8 col-md-7"><%=FormatUtil.display(paymentMethod.getAccountName())%></div>
		</div>
	</div>