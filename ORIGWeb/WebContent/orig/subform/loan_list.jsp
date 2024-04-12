<%@ page import="java.util.*" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.model.LoanDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.orig.shared.model.ApplicationDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
<%@ page import="com.eaf.orig.shared.model.VehicleDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant"%>


<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>

<% 
	ApplicationDataM applicationDataM = ORIGForm.getAppForm();
	if(applicationDataM == null){
		applicationDataM = new ApplicationDataM();
	}
	ORIGFormUtil formUtil = new ORIGFormUtil();
	String searchType = (String) request.getSession().getAttribute("searchType");
	String displayMode = formUtil.getDisplayMode("LOAN_SUBFORM", ORIGUser.getRoles(), applicationDataM.getJobState(), ORIGForm.getFormID(), searchType);	
	System.out.println("displayMode = "+displayMode);
	Vector loanVect = ORIGForm.getAppForm().getLoanVect();
	LoanDataM loanDataM = null;
	if(loanVect!=null&&loanVect.size() > 0){
		loanDataM = (LoanDataM) ORIGForm.getAppForm().getLoanVect().elementAt(0);
	}
	ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
	VehicleDataM vehicleDataM = applicationDataM.getVehicleDataM();
	if(vehicleDataM == null){
		vehicleDataM = new VehicleDataM();
	}else{
		if(!"NEW".equals(vehicleDataM.getDrawDownStatus()) && !ORIGUtility.isEmptyString(vehicleDataM.getDrawDownStatus())){
			displayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_VIEW;
		}
	}    
%>

<table cellpadding="" cellspacing="0" width="100%" align="center" bgcolor="#FFFFFF">
	<tr> 
		<td colspan="4">
			<div id="KLTable">
				<div id="Loan">
					<table class="gumayframe3" cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
						<tr><td class="TableHeader">
							<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
								<tr>
									<td class="Bigtodotext3" align="center" width="5%"></td>
									<td class="Bigtodotext3" align="center" width="25%"><%=MessageResourceUtil.getTextDescription(request, "MKT_NAME") %></td>
									<td class="Bigtodotext3" align="center" width="40%"><%=MessageResourceUtil.getTextDescription(request, "CAMPAIGN") %></td>
									<td class="Bigtodotext3" align="center" width="30%"><%=MessageResourceUtil.getTextDescription(request, "INSTALLMENT") %></td>
								</tr>
							</table> 
						</td></tr>
						<% if(loanDataM != null){ 
							String campaignDesc = cacheUtil.getORIGMasterDisplayNameDataM("Campaign", loanDataM.getCampaign());
							String mktDesc = cacheUtil.getORIGMasterDisplayNameDataM("LoanOfficer", loanDataM.getMarketingCode());
							String disabledChk = ""; 
							if(ORIGDisplayFormatUtil.DISPLAY_MODE_VIEW.equals(displayMode)){
								disabledChk = "disabled";
							}
						%>
						<tr><td align="center" class="gumaygrey2">
							<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
							<tr>
								<td class="jobopening2" align="center" width="5%"><%=ORIGDisplayFormatUtil.displayCheckBox("delete","loanSeq",String.valueOf(loanDataM.getSeq()),disabledChk) %></td>
								<td class="jobopening2" align="center" width="25%"><a href="javascript:loadPopup('loan','LoadLoanPopup','1150','680','0','0','','<%=loanDataM.getLoanType() %>','')"><u><%=ORIGDisplayFormatUtil.displayHTML(mktDesc) %></u></a></td>
								<td class="jobopening2" align="center" width="40%"><%=ORIGDisplayFormatUtil.displayHTML(campaignDesc) %></td>
								<%if(com.eaf.orig.shared.constant.OrigConstant.INSTALLMENT_TYPE_STEP.equals(loanDataM.getInstallmentFlag())){%>
								<td class="jobopening2" align="center" width="30%">Step Installment</td>
								<%}else{ %>
								<td class="jobopening2" align="center" width="30%"><%=ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getTotalOfInstallment1()) %></td>
								<%}%>
							</tr>
							</table> 
						</td></tr>
						<% }else{%>
						<tr><td align="center" class="gumaygrey2">
							<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
							<tr>
						  		<td class="jobopening2" align="center">No Record</td>
						  	</tr>
							</table> 
						</td></tr>
						<% }%>
					</table>
				</div>
			</div>
		</td>
	</tr>
	<tr><td height="20"></td></tr>
	<% if(ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT.equals(displayMode) && !("Y").equals(applicationDataM.getDrawDownFlag())){%>
	<tr>
		<td width="84%"></td>
		<td align="right" width="7%"><INPUT type="button" name="addLoanBnt" value=" <%=MessageResourceUtil.getTextDescription(request, "ADD") %> " onClick="javascript:checkCarType()" class="button_text" >
		<%//=ORIGDisplayFormatUtil.displayButtonTagScriptAction(msgUtil.getTextDescription(request, "ADD"), displayMode,"button", "addICBnt", "button_text", "onClick=loadPopup('address','LoadLoanPopup','500','400','150','100','')", "") %>
		</td><td align="right" width="7%"> 
		 <INPUT type="button" name="deleteLoanBnt" value=" <%=MessageResourceUtil.getTextDescription(request, "DELETE") %> " onClick="javascript:deleteTableList('loanSeq','Loan','DeleteLoanServlet')" class="button_text"></td>
		<td width="2%"></td>
	</tr>
	<%}%>
</table>

<SCRIPT language="JavaScript">
//<!--
	function checkCarType(){
		var form = document.appFormName;
		var carType = form.car.value;
		if(carType != '' && carType != null){
			loadPopup('loan','LoadLoanPopup','1250','680','0','0','','<%=applicationDataM.getLoanType() %>&car='+carType,'');
		}else{
			alert("Please select Car(New Car or Used Car).");
		}
	}
//-->
</SCRIPT>