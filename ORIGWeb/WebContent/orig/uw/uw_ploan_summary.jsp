<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.shared.model.PersonalInfoDataM" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="java.util.Vector" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.orig.shared.model.LoanDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.xrules.shared.model.XRulesVerificationResultDataM"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<%
	com.eaf.orig.shared.model.ApplicationDataM applicationDataM = ORIGForm.getAppForm();	
	ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
	ORIGUtility utility = new ORIGUtility();
	
	MessageResourceUtil msgUtil = MessageResourceUtil.getInstance();	
	PersonalInfoDataM personalInfoDataM = utility.getPersonalInfoByType(ORIGForm.getAppForm(), OrigConstant.PERSONAL_TYPE_APPLICANT);
	Vector personalVect = utility.getVectorPersonalInfoByType(ORIGForm.getAppForm(), OrigConstant.PERSONAL_TYPE_GUARANTOR); 
	Vector loanvect = applicationDataM.getLoanVect();
	
	LoanDataM loanDataM = new LoanDataM();
	if(loanvect!= null && loanvect.size() > 0){				
		loanDataM = (LoanDataM)loanvect.get(0);	
	}
	String searchType = (String)request.getSession().getAttribute("searchType");
	String proposalMenu = (String) request.getSession().getAttribute("PROPOSAL_MENU");
	
	String appDecision = applicationDataM.getAppDecision();
	String uwDecision = applicationDataM.getUwDecision();
	if(ORIGUtility.isEmptyString(appDecision)){
		appDecision = uwDecision;
	}
	XRulesVerificationResultDataM xrulesVerificationResultDataM=personalInfoDataM.getXrulesVerification();
	if(xrulesVerificationResultDataM==null){
	xrulesVerificationResultDataM=new XRulesVerificationResultDataM();
	}
	
	if(com.eaf.orig.wf.shared.ORIGWFConstant.ApplicationDecision.APPROVE_WITH_EXCEPTION.equals(appDecision)){
		appDecision = "Sent to Exception CMR";
	}
%>

<table cellpadding="0" cellspacing="0" width="80%" align="center" bgcolor="#FFFFFF">
<tr>
	<td>
		<br>
		<table cellpadding="0" cellspacing="1" border="0" width="99%" align="center" >
		<tr bgcolor="#F4F4F4">
			<td class="textColS" colspan="4"><center><font size="2"><%=msgUtil.getTextDescription(request, "SUMMARYPAGE") %></font></center></td>
		</tr>
		<tr bgcolor="#F4F4F4">
			<td class="textColS" colspan="4"><center><font size="2" color="blue">This application has changed the status to "<%=appDecision%>".</font></center></td>
		</tr>
		<tr>
			<td class="textColS" width="23%"><%=msgUtil.getTextDescription(request, "APPLICATION_NO") %> :</td>
			<td class="inputCol" width="15%"><%= applicationDataM.getApplicationNo()%></td>
			<td class="textColS" width="18%"><%=msgUtil.getTextDescription(request, "CITIZEN_ID") %> :</td>
			<td class="inputCol" ><%= personalInfoDataM.getIdNo()%></td>
		</tr>
		<tr>
			<td class="textColS"><%=msgUtil.getTextDescription(request, "CUSTOMER_NAME") %> :</td>
			<td class="inputCol" colspan="3"><%= cacheUtil.getORIGMasterDisplayNameDataM("Title", personalInfoDataM.getThaiTitleName()) %>
			<%= ORIGDisplayFormatUtil.displayHTML(personalInfoDataM.getThaiFirstName()) %>
			<%= ORIGDisplayFormatUtil.displayHTML(personalInfoDataM.getThaiLastName()) %>
			</td>
		</tr>
		<tr>
			<td class="textColS" valign="top"><%=msgUtil.getTextDescription(request, "GUARANTOR_NAME") %> :</td>
			<td class="inputCol" colspan="3">
			<%	
				if(personalVect!= null){
					PersonalInfoDataM personalInfoDataM2;
					for (int i =0 ; i< personalVect.size() ; i++){ 
						personalInfoDataM2 = (PersonalInfoDataM)personalVect.get(i);
			%>			
			<%= cacheUtil.getORIGMasterDisplayNameDataM("Title", personalInfoDataM2.getThaiTitleName()) %>
			<%= ORIGDisplayFormatUtil.displayHTML(personalInfoDataM2.getThaiFirstName()) %>
			<%= ORIGDisplayFormatUtil.displayHTML(personalInfoDataM2.getThaiLastName()) %>
				<%if((i+1)!=personalVect.size()){
				 %>,&nbsp;<br> 	
				<%}else{
				%>
			</td>
				<%
				}
				 %>
			<%
					}
				}
			 %>
		</tr>
		<tr>
			<td class="textColS" width="20%"><%=msgUtil.getTextDescription(request, "UPDATE_BY") %> :</td>
			<td class="inputCol" width="30%"><%=ORIGDisplayFormatUtil.forHTMLTag(ORIGUser.getFirstName())%>  <%=ORIGDisplayFormatUtil.forHTMLTag(ORIGUser.getLastName())%></td>
			<td class="textColS" width="20%"><%=msgUtil.getTextDescription(request, "UPDATE_DATE") %> :</td>
			<td class="inputCol" width="25%"><%=ORIGDisplayFormatUtil.dateTimetoStringForThai(new java.util.Date())%></td>
		</tr>
		</table>
	</td>
</tr>
<%if(!OrigConstant.SEARCH_TYPE_PROPOSAL.equals(searchType) && !("Y").equals(proposalMenu)){ %>
<tr>
	<td>
			<fieldset><LEGEND><font class="font2"><strong><%=msgUtil.getTextDescription(request, "LOAN_DETAIL") %></strong></font></Legend><br>
			<table cellpadding="0" cellspacing="1" border="0" width="99%" align="center" >		
			<tr bgcolor="#F4F4F4">
				<td class="textColS" width="23%"><%=msgUtil.getTextDescription(request, "CAR_PRICE") %> :</td>
				<td class="inputCol" width="15%" align="right"><%=ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getCostOfCarPrice())%></td>
				<td class="textColS" width="18%"><%=msgUtil.getTextDescription(request, "TERM") %> :</td>
				<td class="inputCol" width="15%" align="right"><%=ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getInstallment1())%></td>
				<td class="textColS"></td>
			</tr>
			<tr>
				<td class="textColS" ><%=msgUtil.getTextDescription(request, "DOWN_PAYMENT") %> :</td>
				<td class="inputCol" align="right"><%= ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getCostOfDownPayment())%></td>
				<td class="textColS" ><%=msgUtil.getTextDescription(request, "EFFECTIVE_RATE") %> :</td>
				<td class="inputCol" align="right"><%= ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getEffectiveRate())%></td>
				<td class="textColS"></td>
			</tr>
			<tr>
				<td class="textColS" ><%=msgUtil.getTextDescription(request, "FINANCIAL_AMT") %> :</td>
				<td class="inputCol" align="right"><%= ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getCostOfFinancialAmt())%></td>
				<td class="textColS" ><%=msgUtil.getTextDescription(request, "IRR_RATE") %> :</td>
				<td class="inputCol" align="right"><%= ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getIRRRate())%></td>
				<td class="textColS"></td>
			</tr>
			<tr>
				<td class="textColS" ><%=msgUtil.getTextDescription(request, "BALLOON_AMT") %> :</td>
				<td class="inputCol" align="right"><%= ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getCostOfBalloonAmt())%></td>
				<td class="textColS" colspan="3"></td>
			</tr>
			<tr>
				<td class="textColS" ><%=msgUtil.getTextDescription(request, "RATE") %> :</td>
				<td class="inputCol" align="right"><%= ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getRate1())%></td>
				<td class="textColS" colspan="3"></td>
			</tr>
			<tr>
				<td class="textColS" ><%=msgUtil.getTextDescription(request, "INSTALLMENT") %> :</td>
				<%if(com.eaf.orig.shared.constant.OrigConstant.INSTALLMENT_TYPE_STEP.equals(loanDataM.getInstallmentFlag())){%>
				    <td class="inputCol" align="right" colspan="3"><%=utility.getStepInstallmentTable(loanDataM.getStepInstallmentVect(),request)%></td>
				    <td class="textColS" colspan="1">&nbsp;</td>
			  	<%}else{ %>
				<td class="inputCol" align="right"><%= ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getCostOfInstallment1())%></td>
				<td class="textColS" colspan="3"></td>
				<%}%>
			</tr>
			<tr>
				<td class="textColS" ><%=msgUtil.getTextDescription(request, "RV") %> :</td>
				<td class="inputCol" align="right"><%=ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getCostOfRV()) %></td>
				<td class="textColS" colspan="3"></td>
			</tr>
			<tr>
				<td class="textColS" ><%=msgUtil.getTextDescription(request, "HIRE_PURCHASE_AMT") %>/<%=msgUtil.getTextDescription(request, "LEASE_AMT") %> :</td>
				<td class="inputCol" align="right"><%= ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getCostOfHairPurchaseAmt())%></td>
				<td class="textColS" colspan="3"></td>
			</tr>
			<tr>
				<td colspan="5" height="3"></td>
			</tr>			
			</table>
			</fieldset>

		</td>
	</tr>
<%} %>
	<tr>
		<td>

				<fieldset><LEGEND><font class="font2"><strong><%=msgUtil.getTextDescription(request, "VERIFICATION_LIST_RESULT") %></strong></font></Legend><br>
				<table cellpadding="0" cellspacing="1" border="0" width="99%" align="center" >		
				<tr bgcolor="#F4F4F4">
					<td class="textColS" width="23%"><%=msgUtil.getTextDescription(request, "SCORING_RESULT") %> :</td>
					<td class="inputCol" colspan="3">&nbsp;<%=ORIGDisplayFormatUtil.displayHTML(	applicationDataM.getScorringResult())%></td>			
				</tr>
				<tr>
					<td class="textColS" ><%=msgUtil.getTextDescription(request, "APP_DEBT_BURDEN") %> :</td>
					<td class="inputCol" colspan="3">&nbsp;<%=ORIGDisplayFormatUtil.displayHTML(xrulesVerificationResultDataM.getDEBT_BDResult() )%></td>			
				</tr>
				<tr>
					<td class="textColS" ><%=msgUtil.getTextDescription(request, "POLICY_RULE_HIT_RESULT") %> :</td>
					<td class="inputCol" colspan="3">&nbsp;<%=ORIGDisplayFormatUtil.displayHTML(xrulesVerificationResultDataM.getPolicyRulesResult())%></td>			
				</tr>
				<tr>
					<td colspan="4" height="3"></td>
				</tr>
				</table>
				</fieldset>

	</td>
</tr>
<tr>
	<td>
		<table cellpadding="0" cellspacing="1" border="0" width="100%" align="center" >
		<tr bgcolor="#FFFFFF">
			<td align="right" colspan="4"><INPUT type="button" name="pritBtn" value=" <%=MessageResourceUtil.getTextDescription(request, "PRINT") %> " onClick="javascript:print()" class="button_text">&nbsp;<INPUT type="button" name="okayBtn" value=" OK " onClick="javascript:backToSearchType()" class="button_text"></td>
		</tr>		
		</table>
	</td>
</tr>
</table>

<script language="JavaScript">
<% System.out.println(">>> searchType="+searchType);%>
function backToSearchType(){
	<%if(("Y").equals(proposalMenu)){%>
		appFormName.action.value="FirstAccess";
	<%}else{%>
		appFormName.action.value="FristApp";
	<%}%>
	appFormName.handleForm.value = "N";
	appFormName.submit(); 
}
</script>
