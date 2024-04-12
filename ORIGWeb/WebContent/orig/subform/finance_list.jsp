<%@ page import="java.util.*" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.model.BankDataM" %>
<%@ page import="com.eaf.orig.shared.model.PersonalInfoDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.orig.shared.model.ApplicationDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>


<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>

<% 
	ApplicationDataM applicationDataM = ORIGForm.getAppForm();
	if(applicationDataM == null){
		applicationDataM = new ApplicationDataM();
	}
	ORIGFormUtil formUtil = new ORIGFormUtil();
	String searchType = (String) request.getSession().getAttribute("searchType");
	String displayMode = formUtil.getDisplayMode("FINANCE_SUBFORM", ORIGUser.getRoles(), applicationDataM.getJobState(), ORIGForm.getFormID(), searchType);	

	ORIGUtility utility = new ORIGUtility();
	ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
	
//	String displayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
	String personalType = (String) request.getSession().getAttribute("PersonalType");
	PersonalInfoDataM personalInfoDataM;
	if(OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)){
		personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("MAIN_POPUP_DATA");
	}else if(OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType)){
		personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("SUPCARD_POPUP_DATA");
	}else{
		personalInfoDataM = utility.getPersonalInfoByType(ORIGForm.getAppForm(),OrigConstant.PERSONAL_TYPE_APPLICANT);
	}
	if(personalInfoDataM == null){
		personalInfoDataM = new PersonalInfoDataM();
	}
	Vector financeVect = personalInfoDataM.getFinanceVect();
	
%>

<table cellpadding="" cellspacing="0" width="100%" align="center" bgcolor="#FFFFFF">
	<tr> 
		<td colspan="4">
			<div id="KLTable">
				<div id="Finance">
					<table class="gumayframe3" cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
						<tr><td class="TableHeader">
							<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
								<tr>
									<td class="Bigtodotext3" align="center" width="2%"></td>
									<td class="Bigtodotext3" align="center" width="2%"><%=MessageResourceUtil.getTextDescription(request, "SEQ") %></td>
									<td class="Bigtodotext3" align="center" width="12%"><%=MessageResourceUtil.getTextDescription(request, "FINANCIAL_TYPE") %></td>
									<td class="Bigtodotext3" align="center" width="21%"><%=MessageResourceUtil.getTextDescription(request, "BANK") %></td>
									<td class="Bigtodotext3" align="center" width="15%"><%=MessageResourceUtil.getTextDescription(request, "BRANCH") %></td>
									<td class="Bigtodotext3" align="center" width="15%"><%=MessageResourceUtil.getTextDescription(request, "ACCOUNT_NO") %></td>
									<td class="Bigtodotext3" align="center" width="10%"><%=MessageResourceUtil.getTextDescription(request, "AMOUNT") %></td>
									<td class="Bigtodotext3" align="center" width="10%"><%=MessageResourceUtil.getTextDescription(request, "OPEN_DATE") %></td>
									<td class="Bigtodotext3" align="center" width="10%"><%=MessageResourceUtil.getTextDescription(request, "EXPIRY_DATE") %></td>
								</tr>
							</table> 
						</td></tr>
						<%if(financeVect != null && financeVect.size() > 0){
							BankDataM bankDataM;
							String financeType;
							String bankDesc;
							String branchDesc;
							String disabledChk;
							String sDate;
							for(int i=0; i<financeVect.size(); i++){
								disabledChk = "";
								bankDataM = (BankDataM) financeVect.get(i);
								financeType = cacheUtil.getORIGMasterDisplayNameDataM("FinancialType", bankDataM.getFinancialType());
								bankDesc = cacheUtil.getORIGMasterDisplayNameDataM("BankInformation", bankDataM.getBankCode());
								branchDesc = cacheUtil.getORIGCacheDisplayNameFormDB(bankDataM.getBranchCode(), "Branch", bankDataM.getBankCode());
								if(("N").equals(bankDataM.getOrigOwner())){
									disabledChk = "disabled";
								}else{
									if(ORIGDisplayFormatUtil.DISPLAY_MODE_VIEW.equals(displayMode)){
										disabledChk = "disabled";
									}
								}
								if(bankDataM.getOpenDate()==null){
									sDate="";
								}else{
									sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(bankDataM.getOpenDate()));
								}
						%>
						<tr><td align="center" class="gumaygrey2">
							<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
							<tr>
								<td class="jobopening2" align="center" width="2%"><%=ORIGDisplayFormatUtil.displayCheckBox("delete","financeSeq",String.valueOf(bankDataM.getSeq()),disabledChk) %></td>
								<td class="jobopening2" align="center" width="2%"><%=(i+1) %></td>
								<td class="jobopening2" width="12%"><a href="javascript:loadPopup('finance','LoadFinancePopup','900','300','300','80','<%=bankDataM.getSeq() %>','','<%=personalType%>')"><u><%=ORIGDisplayFormatUtil.displayHTML(financeType) %></u></a></td>
								<td class="jobopening2" width="21%"><%=ORIGDisplayFormatUtil.displayHTML(bankDesc) %></td>
								<td class="jobopening2" width="15%"><%=ORIGDisplayFormatUtil.displayHTML(branchDesc) %></td>
								<td class="jobopening2" width="15%"><%=ORIGDisplayFormatUtil.displayHTML(bankDataM.getAccountNo()) %></td>
								<td class="jobopening2" width="10%" align="right"><%=ORIGDisplayFormatUtil.displayCommaNumber(bankDataM.getAmount()) %></td>
								<td class="jobopening2" width="10%" align="center"><%=sDate %></td>
								<% 
									if(bankDataM.getExpiryDate()==null){
										sDate="";
									}else{
										sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(bankDataM.getExpiryDate()));
									}
								%>
								<td class="jobopening2" width="10%" align="center"><%=sDate %></td>
							</tr>
							</table> 
						</td></tr>
						<%	} 
						  }else{%>
						<tr><td align="center" class="gumaygrey2">
							<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
							<tr>
						  		<td class="jobopening2" align="center">No Record</td>
						  	</tr>
							</table> 
						</td></tr>
						<%}%>
					</table>
				</div>
			</div>
		</td>
	</tr>
	<tr><td height="20" colspan="4"></td></tr>
	<% if(ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT.equals(displayMode)){%>
	<tr>
		<td width="84%"></td>
		<td align="right" width="7%"><INPUT type="button" name="addFinanceBnt" value=" <%=MessageResourceUtil.getTextDescription(request, "ADD") %> " onClick="javascript:loadPopup('finance','LoadFinancePopup','900','300','300','80','0','','<%=personalType%>')" class="button_text">
		</td><td  align="right" width="7%">
		 <INPUT type="button" name="deleteFinanceBnt" value=" <%=MessageResourceUtil.getTextDescription(request, "DELETE") %> " onClick="javascript:deleteTableList('financeSeq','Finance','DeleteFinanceServlet')" class="button_text"></td>
		<td width="2%"></td>
	</tr>
	<%} %>
</table>
<SCRIPT language="JavaScript">
//<!--

//-->
</SCRIPT>