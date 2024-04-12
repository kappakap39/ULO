<%@ page import="java.util.*" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.model.LoanDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.orig.shared.model.ApplicationDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
<%@ page import="com.eaf.orig.shared.model.VehicleDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.shared.model.CollateralDataM" %>
<%@ page import="com.eaf.orig.formcontrol.view.form.ORIGFormHandler" %>
<%@ page import="com.eaf.orig.shared.model.FeeInformationDataM" %>


<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>

<% 
	ApplicationDataM applicationDataM = ORIGForm.getAppForm();
	if(applicationDataM == null){
		applicationDataM = new ApplicationDataM();
	}
	CollateralDataM collateralDataM = (CollateralDataM)request.getSession().getAttribute("COLLATERAL_POPUP_DATA");
	if (collateralDataM==null){
		collateralDataM = new CollateralDataM();
	}
	ORIGFormUtil formUtil = new ORIGFormUtil();
	String searchType = (String) request.getSession().getAttribute("searchType");
	ORIGFormHandler popupForm = (ORIGFormHandler)ORIGForm.getPopupForm();
	String displayMode = formUtil.getDisplayMode("MG_FEE_INFO_SUBFORM", ORIGUser.getRoles(), applicationDataM.getJobState(), popupForm.getFormID(), searchType);	
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
				<div id="FeeInfo">
					<table class="gumayframe3" cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
						<tr><td class="TableHeader">
							<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
								<tr>
									<td class="Bigtodotext3" align="center" width="5%"></td>
									<td class="Bigtodotext3" align="center" width="40%"><%=MessageResourceUtil.getTextDescription(request, "FEE_TYPE") %></td>
									<td class="Bigtodotext3" align="center" width="20%"><%=MessageResourceUtil.getTextDescription(request, "FEE_AMOUNT") %></td>
									<td class="Bigtodotext3" align="center" width="35%"><%=MessageResourceUtil.getTextDescription(request, "FEE_PAYMENT_OPTION") %></td>
								</tr>
							</table> 
						</td></tr>
						<%Vector collateralVect = ORIGForm.getAppForm().getCollateralVect();
						System.out.println(">>> collateralDataM.getFeeInformationVect().size()1");
						if (collateralDataM!=null && collateralDataM.getFeeInformationVect()!=null && collateralDataM.getFeeInformationVect().size()>0){
							System.out.println(">>> collateralDataM.getFeeInformationVect().size()2");
							String disabledChk = ""; 
							if(ORIGDisplayFormatUtil.DISPLAY_MODE_VIEW.equals(displayMode)){
								disabledChk = "disabled";
							}
							for (int i=0;i<collateralDataM.getFeeInformationVect().size();i++){
							System.out.println(">>> collateralDataM.getFeeInformationVect().size()3");
								FeeInformationDataM feeInformationDataM = (FeeInformationDataM)collateralDataM.getFeeInformationVect().get(i);
                				String feeType = cacheUtil.getORIGCacheDisplayNameDataM(31, feeInformationDataM.getFeeType());
                				String feeOption = cacheUtil.getORIGCacheDisplayNameDataM(32, feeInformationDataM.getFeePaymentOption());
                			System.out.println(">>> collateralDataM.getFeeInformationVect().size()4"+feeInformationDataM.getSeq());
						%>
						<tr><td align="center" class="gumaygrey2">
							<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
								<tr>
									<td class="jobopening2" align="center" width="5%"><%=ORIGDisplayFormatUtil.displayCheckBox("delete","MGfeeSeq",String.valueOf(feeInformationDataM.getSeq()),disabledChk) %></td>
									<td class="jobopening2" align="center" width="40%"><a href="javascript:loadPopup('feeInfo','LoadFeeInformationPopup','500','275','200','300','<%=i %>','','')"><u><%=ORIGDisplayFormatUtil.displayHTML(feeType) %></u></a></td>
									<td class="jobopening2" align="center" width="20%"><%=feeInformationDataM.getFeeAmount() %></td>
									<td class="jobopening2" align="center" width="35%"><%=ORIGDisplayFormatUtil.displayHTML(feeOption) %></td>
								</tr>
							</table> 
						</td></tr>
								
							<%System.out.println(">>> collateralDataM.getFeeInformationVect().size()5");} %>
						<% }else{%>
							<tr><td align="center" class="gumaygrey2">
								<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
									<tr>
							  			<td class="jobopening2" colspan="4" align="center">No Record</td>
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
		<td align="right" width="83%">&nbsp;</td>
		<td width="7%"><INPUT type="button" name="addMGLoanBnt" value=" <%=MessageResourceUtil.getTextDescription(request, "ADD") %> " onClick="javascript:loadPopupFeeInformation()" class="button_text" ></td>
		<td width="7%"><INPUT type="button" name="deleteMGLoanBnt" value=" <%=MessageResourceUtil.getTextDescription(request, "DELETE") %> " onClick="javascript:deleteTableList('MGfeeSeq','FeeInfo','DeleteMGFeeInformationServlet')" class="button_text"></td>
		<td width="3%"></td>
	</tr>
	<%}%>
</table>

<SCRIPT language="JavaScript">
//<!--
	function loadPopupFeeInformation(){
		loadPopup('feeInfo','LoadFeeInformationPopup','500','275','200','300','','<%=applicationDataM.getLoanType() %>','');
	}
//-->
</SCRIPT>