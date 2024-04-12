<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.eaf.core.ulo.security.encryptor.EncryptorFactory"%>
<%@page import="com.eaf.core.ulo.security.encryptor.Encryptor"%>
<%@page import="com.eaf.core.ulo.common.util.SessionControl"%>
<%@page import="com.eaf.orig.ulo.model.control.FlowControlDataM"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationIncreaseDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.LoanDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.CardDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM" %>
<%@page import="com.eaf.core.ulo.common.properties.ListBoxControl"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="java.util.ArrayList"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<script type="text/javascript" src="orig/ulo/subform/js/CardRequestInfoSubForm.js"></script>
<%
	Encryptor encryptor = EncryptorFactory.getDIHEncryptor();
	String subformId = "IA_INCREASE_CARD_REQUEST_INFO_SUBFROM";
	Logger logger = Logger.getLogger(this.getClass());
	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String FIELD_ID_APPLICATION_CARD_TYPE = SystemConstant.getConstant("FIELD_ID_APPLICATION_CARD_TYPE");
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	FlowControlDataM flowControl = (FlowControlDataM) request.getSession().getAttribute(SessionControl.FlowControl);
	String ACTION_TYPE = flowControl.getActionType();
	
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();		
	PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
	if(null == personalInfo){
		personalInfo = new PersonalInfoDataM();
	}
	int PERSONAL_SEQ = personalInfo.getSeq();		
	String displayMode = HtmlUtil.EDIT;	
// 	String TAG_SMART_DATA_PERSONAL = FormatUtil.getSmartDataEntryId(PERSONAL_TYPE,PERSONAL_SEQ);	
	
	String personalElementId = FormatUtil.getPersonalElementId(personalInfo);
	
	FormUtil formUtil = new FormUtil(subformId,request);
%>
<div class="panel panel-default">
<div class="panel-body"> 
<div class="row form-horizontal">
	<div class="col-sm-12">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"CARD_NO","CARD_NO","col-sm-2 col-md-5 marge-label control-label")%>
			<div class="col-sm-10 col-md-9 marge-field">
				<div class="row">
					<div class="col-xs-12">
						<%=HtmlUtil.textBoxCardNo("CARD_NO", "", "CARD_NO_"+personalElementId, "CARD_NO", 
							null, "", "", "col-sm-8 col-md-7",formUtil) %>
						<div class="col-xs-2"><%=HtmlUtil.button("BTN_ADD_CARD","","","btnsmall_add","",request) %></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
</div>
</div>
</div>
<div class="panel panel-default">
	<table class="table table-striped">
		<% 
			int ROWNUM = 1;
			ArrayList<ApplicationDataM> applications  = new ArrayList<ApplicationDataM>();
			ArrayList<ApplicationIncreaseDataM> appplicationIncreases = applicationGroup.getApplicationIncreases();
			if(SystemConstant.lookup("ACTION_TYPE_ENQUIRY",ACTION_TYPE)){
				applications = applicationGroup.getApplications();
			}else{
				applications = applicationGroup.filterApplicationLifeCycle();
				
			}
			 	 
			 if(!Util.empty(applications)){
			 	for(ApplicationDataM applicationM :applications){
			 		String applicationRecordId = applicationM.getApplicationRecordId();
			 		LoanDataM loan = applicationM.getLoan();
			 		CardDataM card= applicationM.getCard();
				String onclickActionJS="onclick=DELETE_INCREASE_CARDActionJS('"+applicationRecordId+"')";
				String KCC_CARD_ENCRP = card.getCardNo();
				String KCC_CARD_NO="";
		  		if(!Util.empty(KCC_CARD_ENCRP)){
		 			try{
						KCC_CARD_NO = encryptor.decrypt(KCC_CARD_ENCRP);
					}catch(Exception e){
						logger.debug("ERROR",e);
					}
				}
			 		
		%>
			<tr>
				<td width="5%"><%=HtmlUtil.icon("DELETE_INCREASE_CARD", "", "btnsmall_delete",onclickActionJS, request)%></td>
				<td width="5%"><%=ROWNUM++%></td>
				<td><%=FormatUtil.getCardNo(KCC_CARD_NO)%></td>
				<td><%=(Util.empty(card.getApplicationType()) ? "" :ListBoxControl.getName(FIELD_ID_APPLICATION_CARD_TYPE,"CHOICE_NO",card.getApplicationType(),"DISPLAY_NAME"))%></td>
				<td><%=card.getMainCardHolderName() %></td>
				<td></td>
			</tr>
<!-- 			<tr> -->
<!-- 				<td></td> -->
<!-- 				<td></td> -->
<!-- 				<td></td> -->
<!-- 				<td></td> -->
<%-- 				<td><%=HtmlUtil.getSubFormLabel(request,subformId,"IC_CURRENT_CREDIT_LIMIT","IC_CURRENT_CREDIT_LIMIT","")%></td> --%>
<!-- 				<td> -->
<%-- 				<%=HtmlUtil.currencyBox("REQUEST_LOAN_AMT",applicationRecordId,"", "REQUEST_LOAN_AMT", loan.getRequestLoanAmt(), "", false, "15", "", "col-xs-9 col-xs-padding", formUtil) %> --%>
<!-- 				</td> -->
<!-- 			</tr> -->
			<%}
			}
			
			
				if(!Util.empty(appplicationIncreases)){
			 	for(ApplicationIncreaseDataM appplicationIncrease:appplicationIncreases){
			 		String applicationRecordId = appplicationIncrease.getApplicationRecordId();
				String onclickActionJS="onclick=DELETE_INCREASE_CARDActionJS('"+applicationRecordId+"')";
				String KCC_CARD_ENCRP = appplicationIncrease.getCardNoEncrypted();
				String KCC_CARD_NO="";
		  		if(!Util.empty(KCC_CARD_ENCRP)){
		 			try{
						KCC_CARD_NO = encryptor.decrypt(KCC_CARD_ENCRP);
					}catch(Exception e){
						logger.debug("ERROR",e);
					}
				}
			 		
		%>
			<tr>
				<td width="5%"><%=HtmlUtil.icon("DELETE_INCREASE_CARD", "", "btnsmall_delete",onclickActionJS, request)%></td>
				<td width="5%"><%=ROWNUM++%></td>
				<td><%=FormatUtil.getCardNo(KCC_CARD_NO)%></td>
				<td><%=ListBoxControl.getName(FIELD_ID_APPLICATION_CARD_TYPE,"CHOICE_NO",PERSONAL_TYPE_SUPPLEMENTARY,"DISPLAY_NAME")%></td>
				<td><%=appplicationIncrease.getThName() %></td>
				<td></td>
			</tr>
<!-- 			<tr> -->
<!-- 				<td></td> -->
<!-- 				<td></td> -->
<!-- 				<td></td> -->
<!-- 				<td></td> -->
<%-- 				<td><%=HtmlUtil.getSubFormLabel(request,subformId,"IC_CURRENT_CREDIT_LIMIT","IC_CURRENT_CREDIT_LIMIT","")%></td> --%>
<!-- 				<td> -->
<%-- 				<%=HtmlUtil.currencyBox("REQUEST_LOAN_AMT",applicationRecordId,"", "REQUEST_LOAN_AMT", loan.getRequestLoanAmt(), "", false, "15", "", "col-xs-9 col-xs-padding", formUtil) %> --%>
<!-- 				</td> -->
<!-- 			</tr> -->
			<%}
			 	}
			
			if(Util.empty(applications) && Util.empty(appplicationIncreases)){%>
		 	<tr>
		 		<td colspan="999" align="center"><%=LabelUtil.getText(request,"NO_RECORD_FOUND")%></td>
		 	</tr>
		 <%}%>
	</table>
</div>

<div class="panel panel-default">
	<table class="table table-striped">
		 	<tr style="text-align: right;">
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td><%=HtmlUtil.button("BTN_EXECUTE","BTN_EXECUTE","","btn button green","",request) %></td>
		 	</tr>
	</table>
</div>
