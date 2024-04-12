<%@ page import="java.util.*" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.model.LoanDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.orig.shared.model.ApplicationDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant"%>
<%@ page import="com.eaf.orig.shared.model.CollateralDataM" %>
<%@ page import="com.eaf.orig.shared.model.AppraisalDataM" %>
<%@ page import="com.eaf.orig.shared.model.AddressDataM" %>


<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>

<% 
	ApplicationDataM applicationDataM = ORIGForm.getAppForm();
	if(applicationDataM == null){
		applicationDataM = new ApplicationDataM();
	}
	ORIGFormUtil formUtil = new ORIGFormUtil();
	String searchType = (String) request.getSession().getAttribute("searchType");
	String displayMode = formUtil.getDisplayMode("MG_COLLATERAL_SUBFORM", ORIGUser.getRoles(), applicationDataM.getJobState(), ORIGForm.getFormID(), searchType);	
	System.out.println("displayMode = "+displayMode);
	MessageResourceUtil msgUtil = MessageResourceUtil.getInstance();
//	String displayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
	ORIGCacheUtil cacheUtil = ORIGCacheUtil.getInstance();   
	Vector propertyTypeVect = cacheUtil.getNaosCacheDataMs("AL_ALL_ALL",29);
%>

<table cellpadding="" cellspacing="0" width="100%" align="center" bgcolor="#FFFFFF">
	<tr> 
		<td colspan="4">
			<div id="KLTable">
				<div id="Collateral">
					<table class="gumayframe3" cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
						<tr><td class="TableHeader">
							<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
								<tr>
									<td class="Bigtodotext3" align="center" width="5%"></td>
									<td class="Bigtodotext3" align="center" width="19%"><%=msgUtil.getTextDescription(request, "PROPERTY_TYPE") %></td>
									<td class="Bigtodotext3" align="center" width="24%"><%=msgUtil.getTextDescription(request, "LOCATION_OF_PROPERTY") %></td>
									<td class="Bigtodotext3" align="center" width="24%"><%=msgUtil.getTextDescription(request, "ACCREDITED_PROPERTY") %></td>
									<td class="Bigtodotext3" align="center" width="14%"><%=msgUtil.getTextDescription(request, "FMV") %></td>
									<td class="Bigtodotext3" align="center" width="14%"><%=msgUtil.getTextDescription(request, "LTV_PERCENT") %></td>
								</tr>
							</table> 
						</td></tr>
						<%Vector collateralVect = ORIGForm.getAppForm().getCollateralVect();
						if (collateralVect!=null && collateralVect.size()>0){
							String disabledChk = ""; 
							if(ORIGDisplayFormatUtil.DISPLAY_MODE_VIEW.equals(displayMode)){
								disabledChk = "disabled";
							}
							for (int i=0;i<collateralVect.size();i++){
								CollateralDataM collateralDataM = (CollateralDataM)collateralVect.get(i);
								AppraisalDataM appraisalDataM = collateralDataM.getAppraisal();
								if (appraisalDataM==null){
									appraisalDataM = new AppraisalDataM();
								}
								System.out.println(">>>>getPropertyType="+collateralDataM.getPropertyType());
								AddressDataM addressDataM = collateralDataM.getAddress();
								
						%>
							<tr><td align="center" class="gumaygrey2">
							<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
								<tr>
									<td class="jobopening2" align="center" width="5%"><%=ORIGDisplayFormatUtil.displayCheckBox("delete","MGloanSeq",String.valueOf(collateralDataM.getSeq()),disabledChk) %></td>
									<td class="jobopening2" align="center" width="19%"><a href="javascript:loadPopup('loan','LoadMortgagePopup','1150','680','0','0',<%=collateralDataM.getSeq() %>,'<%=collateralDataM.getPropertyType() %>','')"><%=ORIGDisplayFormatUtil.displaySelectTag(propertyTypeVect,collateralDataM.getPropertyType(),"propertyType",ORIGDisplayFormatUtil.DISPLAY_MODE_VIEW) %></a></td>
									<td class="jobopening2" align="center" width="24%"><%=ORIGDisplayFormatUtil.displayHTML(cacheUtil.getORIGMasterDisplayNameDataM("Province", addressDataM.getProvince())) %></td>
									<td class="jobopening2" align="center" width="24%"><%=ORIGDisplayFormatUtil.displayHTML(collateralDataM.getAccreditedProperty()) %></td>
									<td class="jobopening2" align="center" width="14%"><%=appraisalDataM.getTotalFMV() %></td>
									<td class="jobopening2" align="center" width="14%"><%=appraisalDataM.getTotalLoanableValue() %></td>
								</tr>
							</table> 
						</td></tr>
							<%} %>
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
		<td align="right" width="7%">
			<INPUT type="button" name="addMGLoanBnt" value=" <%=MessageResourceUtil.getTextDescription(request, "ADD") %> " onClick="javascript:loadPopupMortgageLoan()" class="button_text" >
		</td><td align="right" width="7%">  	
		 	<INPUT type="button" name="deleteMGLoanBnt" value=" <%=MessageResourceUtil.getTextDescription(request, "DELETE") %> " onClick="javascript:deleteTableList('MGloanSeq','Collateral','DeleteMortgageServlet')" class="button_text"></td>
		<td width="2%"></td>
	</tr>
	<%}%>
</table>

<SCRIPT language="JavaScript">
//<!--
	function loadPopupMortgageLoan(){
		var form = document.appFormName;
		var req = new DataRequestor();
		var url = "VerifyRequiredLoanServlet";
		req.getURL(url);
		req.onload = function (data, obj) {
			if(data == '1'){
				loadPopup('loan','LoadMortgagePopup','1150','680','0','0','','<%=applicationDataM.getLoanType() %>','');
			}else{
				alert("Loan is required.");
			}
		}
	
	}
//-->
</SCRIPT>