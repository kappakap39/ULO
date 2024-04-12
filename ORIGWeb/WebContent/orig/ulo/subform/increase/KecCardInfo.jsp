<%@page import="com.eaf.core.ulo.common.util.FormEffects"%>
<%@page import="com.eaf.core.ulo.common.properties.FormControl"%>
<%@page import="com.eaf.core.ulo.common.util.SessionControl"%>
<%@page import="com.eaf.orig.ulo.model.control.FlowControlDataM"%>
<%@page import="com.eaf.core.ulo.common.properties.ListBoxControl"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.security.encryptor.EncryptorFactory"%>
<%@page import="com.eaf.core.ulo.security.encryptor.Encryptor"%>
<%@page import="com.eaf.orig.ulo.model.app.CardDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.LoanDataM"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page contentType="text/html;charset=UTF-8"%>

<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>

<%
	String subformId = "INCREASE_CARD_PRODUCT_INFO_SUBFROM";
	Logger logger = Logger.getLogger(this.getClass());
	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String PRODUCT = request.getParameter("PRODUCT");
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	
	String FIELD_ID_APPLICATION_CARD_TYPE = SystemConstant.getConstant("FIELD_ID_APPLICATION_CARD_TYPE");
	FlowControlDataM flowControl = (FlowControlDataM) request.getSession().getAttribute(SessionControl.FlowControl);
	String ACTION_TYPE = flowControl.getActionType();
	String PRODUCT_K_EXPRESS_CASH = SystemConstant.getConstant("PRODUCT_K_EXPRESS_CASH");
	FormUtil formUtil = new FormUtil(subformId,request);
	String configId = "INCREASE_APPLICATION_FORM_INCREASE_CARD_REQUEST_INFO_SUBFROM_1_"+FormControl.getFormRoleId(request);
	FormEffects formEffect = new FormEffects(configId , FormEffects.ConfigType.ELEMENT, request);
%>

	<table class="table table-striped increaseproduct">
		<%
			int ROWNUM = 1;
			ArrayList<ApplicationDataM> applications  = new ArrayList<ApplicationDataM>();
			if(SystemConstant.lookup("ACTION_TYPE_ENQUIRY", ACTION_TYPE)){
				applications = applicationGroup.filterDisplayEnquiryApplicationsProduct();
			}else{
				applications = applicationGroup.filterApplicationLifeCycle();
			}
			if(!Util.empty(applications)){
				for(ApplicationDataM applicationM : applications){
					if(PRODUCT.equals(applicationM.getProduct())){
				 		String applicationRecordId = applicationM.getApplicationRecordId();
				 		LoanDataM loan = applicationM.getLoan();
				 		CardDataM card = applicationM.getCard();
				 		String CARD_ENCRP = card.getCardNo();
				 		String CARD_NO = "";
				 		if(!Util.empty(CARD_ENCRP)){
				 			Encryptor enc = EncryptorFactory.getDIHEncryptor();
				 			try{
								CARD_NO = enc.decrypt(CARD_ENCRP);
							}catch(Exception e){
							}
				 		}
						String onclickActionJS="onclick=DELETE_INCREASE_CARDActionJS('"+applicationRecordId+"')";
				%>
					<tr>
						<td width="5%"><%=HtmlUtil.icon("DELETE_INCREASE_CARD", "DELETE_INCREASE_CARD", "btnsmall_delete",onclickActionJS, formEffect)%></td>
						<td width="5%"><%=ROWNUM++%></td>
						<td><%=FormatUtil.getCardNo(CARD_NO)%></td>
						<td><%=ListBoxControl.getName(FIELD_ID_APPLICATION_CARD_TYPE, "CHOICE_NO", card.getApplicationType(), "DISPLAY_NAME")%></td>
						<td><%=card.getMainCardHolderName() %></td>
						<td></td>
					</tr>
					<tr>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td><%=HtmlUtil.getSubFormLabel(request,subformId,"IC_CURRENT_CREDIT_LIMIT","IC_CURRENT_CREDIT_LIMIT","")%></td>
						
						<td>
						<%=HtmlUtil.currencyBox("REQUEST_LOAN_AMT",applicationRecordId,"REQUEST_LOAN_AMT", "REQUESTED_CREDIT_LIMIT", loan.getRequestLoanAmt(), "", true, "15", "", "col-xs-9 col-xs-padding",applicationM, formUtil) %>
						</td>
					</tr>
				<%}
				}
			}else{%>
			 	<tr>
			 		<td align="center" colspan="6"><%=LabelUtil.getText(request,"NO_RECORD_FOUND")%></td>
			 	</tr>
			 <%}%>
	</table>
