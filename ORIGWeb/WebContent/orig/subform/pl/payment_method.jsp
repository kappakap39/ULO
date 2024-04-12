<%@page import="com.eaf.orig.shared.util.OrigUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.DataFormatUtility"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.PLOrigUtility"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.PLMessageResourceUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
<%@ page import="java.util.Vector"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLPaymentMethodDataM"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLApplicationDataM"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil"%>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant"%>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM"%>
<%@ page import="com.eaf.orig.shared.dao.utility.OrigApplicationUtil"%>
<%@ page import="com.eaf.orig.cache.properties.CycleCutProperties" %>
<%@ page contentType="text/html;charset=UTF-8"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM" />
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler" /> 

<script type="text/javascript" src="orig/js/subform/pl/payment_method.js" ></script>

<% 
	org.apache.log4j.Logger log =  org.apache.log4j.Logger.getLogger("orig/subform/pl/payment_method.jsp");	
	PLApplicationDataM applicationM = PLORIGForm.getAppForm();
	PLPaymentMethodDataM  paymentMethodM = applicationM.getPaymentMethod();
	
// 	if(paymentMethodM==null){paymentMethodM = new PLPaymentMethodDataM();
// 		applicationM.setPaymentMethod(paymentMethodM);
// 	}
	if(null == paymentMethodM){
		paymentMethodM = new PLPaymentMethodDataM();
	}
	
	ORIGFormUtil formUtil = new ORIGFormUtil();
	String searchType = (String) request.getSession().getAttribute("searchType");
	PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
	String displayMode = formUtil.getDisplayMode("PAYMENT_METHOD_SUBFORM", ORIGUser.getRoles(), applicationM.getJobState(), PLORIGForm.getFormID(), searchType);
// 	log.debug("PAYMENT_METHOD_SUBFORM displayMode= "+displayMode);
	ORIGCacheUtil cacheUtil = ORIGCacheUtil.getInstance();
//	Vector paymentMasterVect = cacheUtil.getNaosCacheDataMs(applicationM.getBusinessClassId(),37);
//	Vector ratioVect=cacheUtil.getNaosCacheDataMs(applicationM.getBusinessClassId(),38);
	Vector cycleVect=cacheUtil.getCycleCutCacheCheckBusClass(applicationM.getBusinessClassId());
	PLOrigUtility util = PLOrigUtility.getInstance();
		util.setDefaultPaymentMethod(applicationM);
  //default cycle cut
	String cycleCut = paymentMethodM.getDueCycle();
	if(OrigUtil.isEmptyString(cycleCut)){
		cycleCut = OrigApplicationUtil.getInstance().getDefaultCycleCut(applicationM.getBusinessClassId());
	}else if(HTMLRenderUtil.DISPLAY_MODE_VIEW.equals(displayMode)){
		CycleCutProperties data = new CycleCutProperties();
		data.setCycleCutDate(Integer.parseInt(cycleCut));
		cycleVect.add(data);
	}
  	log.debug("@@@@@ cycleCut:" + cycleCut);
%>
<table class="FormFrame">
	<tr height="25">
    	<td class="textR" align="right" width="20%"><%=PLMessageResourceUtil.getTextDescription(request, "PAYMENT_METHOD_PAY",ORIGUser,personalM.getCustomerType(),"PAYMENT_METHOD_SUBFORM","payment_method_pay")%><%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"PAYMENT_METHOD_SUBFORM","payment_method_pay")%>&nbsp;:&nbsp;</td>
        <td class="inputL" width="25%"><%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(37,applicationM.getBusinessClassId(),HTMLRenderUtil.displayHTML(paymentMethodM.getPayMethod()),"payment_method_pay",displayMode,"onchange=\"javascript:disableAccountId(this);\" ") %>
        </td>
        <%DataFormatUtility.displayNumberInteger(paymentMethodM.getPayRatio()); %>
        <td class="textR" align="right" width="25%"><%=PLMessageResourceUtil.getTextDescription(request, "PAYMENT_METHOD_RATIO",ORIGUser,personalM.getCustomerType(),"PAYMENT_METHOD_SUBFORM","payment_method_ratio")%><span name="ManPayment"><font color="#ff0000">*</font></span>&nbsp;:&nbsp;</td>
        <td class="inputL" width="30%"><%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(38,applicationM.getBusinessClassId(),DataFormatUtility.displayNumberInteger(paymentMethodM.getPayRatio()),"payment_method_ratio",displayMode," ") %>
        </td>
	</tr>
    <tr height="25">
    	<td class="textR" align="right" width="20%" style="white-space:nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "PAYMENT_METHOD_BANK_ACCOUNT",ORIGUser,personalM.getCustomerType(),"PAYMENT_METHOD_SUBFORM","payment_method_bankAccountNo")%><span name="ManPayment"><font color="#ff0000">*</font></span>&nbsp;:&nbsp;</td>
        <td class="inputL" width="30%"><%=HTMLRenderUtil.displayInputTagScriptAction(paymentMethodM.getAccNo(),displayMode,"","payment_method_bankAccountNo","textbox"," onblur=\"javascript:getPaymentMethodAccountName(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onKeyPress=\"keyPressInteger();\" ","10") %></td>
        <td class="textR" align="right" width="20%"><%=PLMessageResourceUtil.getTextDescription(request, "BANK_ACCOUNT_NAME",ORIGUser,personalM.getCustomerType(),"PAYMENT_METHOD_SUBFORM","payment_method_bankAccountName")%>&nbsp;:&nbsp;</td>
        <td class="inputL" width="30%"><%=HTMLRenderUtil.displayInputTagScriptAction(paymentMethodM.getAccName(),displayMode,"40","payment_method_bankAccountName","textbox"," readonly ","20") %></td>
    </tr>
    <tr height="25">
    	<td class="textR" align="right" width="20%"><%=PLMessageResourceUtil.getTextDescription(request, "DUE_CYCLE",ORIGUser,personalM.getCustomerType(),"PAYMENT_METHOD_SUBFORM","payment_method_dueCycle")%><%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"CARD_INFORMATION_SUBFORM","payment_method_pay")%>&nbsp;:&nbsp;</td>
        <td class="inputL" width="30%"><%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(cycleVect,HTMLRenderUtil.displayHTML(cycleCut),"payment_method_dueCycle",displayMode," ") %>
        </td>
        <td class="textR" align="right" width="20%"><%=PLMessageResourceUtil.getTextDescription(request, "PAYMENT_METHOD_SMS_FLAG",ORIGUser,personalM.getCustomerType(),"PAYMENT_METHOD_SUBFORM","payment_method_smsAletFlag")%>&nbsp;:&nbsp;</td>
        <td class="inputL" width="30%"><%=HTMLRenderUtil.displayCheckBox(paymentMethodM.getSmsDueAlert(),"payment_method_smsAletFlag","Y",displayMode,displayMode) %> </td>
    </tr>
</table>

<%=HTMLRenderUtil.displayHiddenField(ORIGUser.getCurrentRole(), "payment_role") %>
<%=HTMLRenderUtil.displayHiddenField(displayMode, "payment_method_displaymode") %>
<script type="text/javascript">
function disableAccountId(obj){
	if(<%=OrigConstant.PAYMENT_CASH%> == obj.value){
		document.appFormName.payment_method_bankAccountNo.value = "";
		document.appFormName.payment_method_bankAccountName.value= "";
		document.appFormName.payment_method_bankAccountNo.disabled = true;		
	}else if(<%=OrigConstant.PAYMENT_ACCOUNT%> == obj.value){
		document.appFormName.payment_method_bankAccountNo.disabled = false;
	}
}
</script>