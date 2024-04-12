<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.shared.model.PersonalInfoDataM" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="java.util.Vector" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.orig.shared.model.LoanDataM" %>
<%@ page import="com.eaf.orig.shared.model.VehicleDataM" %>
<%@ page import="com.eaf.orig.shared.model.AddressDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>

<%
	com.eaf.orig.shared.model.ApplicationDataM applicationDataM = ORIGForm.getAppForm();	
	ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
	ORIGUtility utility = new ORIGUtility();
	
	PersonalInfoDataM personalInfoDataM = utility.getPersonalInfoByType(ORIGForm.getAppForm(), OrigConstant.PERSONAL_TYPE_APPLICANT);
	//Vector personalVect = utility.getVectorPersonalInfoByType(ORIGForm.getAppForm(), OrigConstant.PERSONAL_TYPE_GUARANTOR); 
	Vector loanvect = applicationDataM.getLoanVect();
	VehicleDataM vehicleDataM = applicationDataM.getVehicleDataM();
	AddressDataM addressDataM_IC = utility.getAddressByType(personalInfoDataM, OrigConstant.ADDRESS_TYPE_IC);
	if(addressDataM_IC == null){
		addressDataM_IC = new AddressDataM();
	}
	AddressDataM addressDataM_PRESENT = utility.getAddressByType(personalInfoDataM, OrigConstant.ADDRESS_TYPE_HOME);
	if(addressDataM_PRESENT == null){
		addressDataM_PRESENT = new AddressDataM();
	}
	AddressDataM addressDataM_OFFICE = utility.getAddressByType(personalInfoDataM, OrigConstant.ADDRESS_TYPE_EMP);
	if(addressDataM_OFFICE == null){
		addressDataM_OFFICE = new AddressDataM();
	}
	AddressDataM addressDataM_MAIL = utility.getAddressByType(personalInfoDataM, OrigConstant.ADDRESS_TYPE_SEND_DOC);
	if(addressDataM_MAIL == null){
		addressDataM_MAIL = new AddressDataM();
	}
	
	LoanDataM loanDataM = new LoanDataM();
	if(loanvect!= null && loanvect.size()>0){				
		loanDataM = (LoanDataM)loanvect.get(0);	
	}
	
	String appDecision = applicationDataM.getAppDecision();
	String pdDecision = applicationDataM.getPdDecision();
	if(ORIGUtility.isEmptyString(appDecision)){
		appDecision = pdDecision;
	}
	if(ORIGUtility.isEmptyString(appDecision)){
		appDecision = "Submitted";
	}
	System.out.println(">>>> 1");
	String mktDesc = cacheUtil.getORIGMasterDisplayNameDataM("LoanOfficer", loanDataM.getMarketingCode());
	String dealer = cacheUtil.getORIGCacheDisplayNameFormDB(applicationDataM.getDealerCode(), OrigConstant.CacheName.CACHE_NAME_DEALER);
	String occupation = cacheUtil.getORIGMasterDisplayNameDataM("Occupation", personalInfoDataM.getOccupation());
	String race = cacheUtil.getORIGMasterDisplayNameDataM("Race", personalInfoDataM.getRace());

	String paymentType = cacheUtil.getNaosCacheDisplayNameDataM(8, loanDataM.getPaymentType());
%>
<TABLE cellSpacing=0 cellPadding=0 width="100%"  border="0">
	<TR><TD vAlign=top>
	<table cellpadding="0" cellspacing="0" width="100%" align="center" bgcolor="#FFFFFF">
		<tr><td colspan="2" class="sidebar8">
			<table cellSpacing="1" cellPadding="0" width="100%" border="0"> 
		    	<tr><td class="sidebar9">
					<table cellspacing=0 cellpadding=0 width="100%" align=center border=0>	     		
			 			<tr> <td  height="10">
			 				<table width="100%" border="0" cellspacing="0" cellpadding="0">
                          	<tr><td class="text-header-detail"><%=MessageResourceUtil.getTextDescription(request, "SUMMARYPAGE") %> </td>
	                            <td width="330">
	                            	<table width="50" border="0" align="right" cellpadding="2" cellspacing="0">
	                              		<tr height="30"><td>
	                                    	<INPUT type="button" name="pritBtn" value=" <%=MessageResourceUtil.getTextDescription(request, "PRINT") %> " onClick="javascript:print()" class="button">
	                                    </td><td>
	                                    	<INPUT type="button" name="okayBtn" value=" <%=MessageResourceUtil.getTextDescription(request, "OK") %> " onClick="javascript:backToSearchType()" class="button">
	                                    </td></tr>
	                                </table>
	                            </td>
	                        </tr>
                            <tr>
                            	<td class="Bigtodotext3" colspan="2" align="center">This application has been <%=appDecision%></td>
                            </tr>
                            </table>
                        </td></tr>
                        <tr> <td  height="15"></td></tr>                        
						<tr class="sidebar10"> <td align="center">
							<table width="100%" align=center cellpadding="0" cellspacing="1" border="0">
								<tr>
									<td class="textColS" width="25%"><%=MessageResourceUtil.getTextDescription(request, "APPLICATION_NO") %> :</td>
									<td class="inputCol" width="30%"><%=ORIGDisplayFormatUtil.displayHTML(applicationDataM.getApplicationNo())%></td>
									<td class="textColS" width="20%"><%=MessageResourceUtil.getTextDescription(request, "CMR_NAME") %> :</td>
									<td class="inputCol" width="25%" ><%=ORIGDisplayFormatUtil.displayHTML(mktDesc)%></td>
								</tr>
								<tr>
									<td class="textColS" ><%=MessageResourceUtil.getTextDescription(request, "DEAL_NAME") %> :</td>
									<td class="inputCol" ><%=ORIGDisplayFormatUtil.displayHTML(dealer) %></td>
									<td class="textColS" ><%=MessageResourceUtil.getTextDescription(request, "CMR_CODE") %> :</td>
									<td class="inputCol" ><%=ORIGDisplayFormatUtil.displayHTML(loanDataM.getMarketingCode()) %></td>			
								</tr>	
								<tr>
									<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "UPDATE_BY") %> :</td>
									<td class="inputCol"><%=ORIGDisplayFormatUtil.forHTMLTag(ORIGUser.getFirstName())%>  <%=ORIGDisplayFormatUtil.forHTMLTag(ORIGUser.getLastName())%></td>
									<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "UPDATE_DATE") %> :</td>
									<td class="inputCol"><%=ORIGDisplayFormatUtil.dateTimetoStringForThai(new java.util.Date())%></td>
								</tr>
							</table>
								
							<fieldset><LEGEND><font class="Bigtodotext3"><%=MessageResourceUtil.getTextDescription(request, "CUSTOMER") %></font></Legend><br>
							<table cellpadding="0" cellspacing="1" border="0" width="99%" align="center" >		
							<tr bgcolor="#F4F4F4">
								<td class="textColS" width="26%"><%=MessageResourceUtil.getTextDescription(request, "NAME") %> :</td>
								<td colspan="3"><%= cacheUtil.getORIGMasterDisplayNameDataM("Title", personalInfoDataM.getThaiTitleName()) %>
								<%= ORIGDisplayFormatUtil.displayHTML(personalInfoDataM.getThaiFirstName()) %>
								<%= ORIGDisplayFormatUtil.displayHTML(personalInfoDataM.getThaiLastName()) %>	</td>		
							</tr>
							<tr>
								<td class="textColS" ><%=MessageResourceUtil.getTextDescription(request, "CITIZEN_ID") %> :</td>
								<td class="inputCol" width="20%"><%=personalInfoDataM.getIdNo() %></td>
								<td class="textColS" width="30%"><%=MessageResourceUtil.getTextDescription(request, "RACE") %> :</td>
								<td class="inputCol" ><%=ORIGDisplayFormatUtil.displayHTML(race) %></td>			
							</tr>
							<tr bgcolor="#F4F4F4">
								<td class="textColS" ><%=MessageResourceUtil.getTextDescription(request, "OCCUPATION") %> :</td>
								<td class="inputCol" colspan="3"><%=ORIGDisplayFormatUtil.displayHTML(occupation) %></td>			
							</tr>
							<tr>
								<td class="textColS" ><%=MessageResourceUtil.getTextDescription(request, "IC_ADDRESS_TEL_NO") %> :</td>
								<td class="inputCol" ><%=ORIGDisplayFormatUtil.displayHTML(addressDataM_IC.getPhoneNo1()) %></td>
								<td class="textColS" ><%=MessageResourceUtil.getTextDescription(request, "PRESENT_ADDRESS_TEL_NO") %> :</td>
								<td class="inputCol" ><%=ORIGDisplayFormatUtil.displayHTML(addressDataM_PRESENT.getPhoneNo1()) %></td>			
							</tr>
							<tr>
								<td class="textColS" ><%=MessageResourceUtil.getTextDescription(request, "OFFICE_TEL_NO") %> :</td>
								<td class="inputCol" ><%=ORIGDisplayFormatUtil.displayHTML(addressDataM_OFFICE.getPhoneNo1()) %></td>
								<td class="textColS" ><%=MessageResourceUtil.getTextDescription(request, "MAILING_ADDRESS_TEL_NO") %> :</td>
								<td class="inputCol" ><%=ORIGDisplayFormatUtil.displayHTML(addressDataM_MAIL.getPhoneNo1()) %></td>			
							</tr>
							<tr>
								<td colspan="4" height="3"></td>
							</tr>
							</table>
							</fieldset>
	
							<fieldset><LEGEND><font class="Bigtodotext3"><strong><%=MessageResourceUtil.getTextDescription(request, "LOAN_DETAIL") %></strong></font></Legend><br>
							<table cellpadding="0" cellspacing="1" border="0" width="99%" align="center" >		
							<tr bgcolor="#F4F4F4">
								<td class="textColS" width="35%"><%=MessageResourceUtil.getTextDescription(request, "LOAN_AMOUNT_APPLIED") %> :</td>
								<td class="inputCol" width="10%" align="right"><%=ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getLoanAmt())%></td>
								<td class="textColS" width="25%">&nbsp;</td>
								<td class="inputCol" width="10%" align="right">&nbsp;</td>
								<td class="textColS" width="15%">&nbsp;</td>
								<td class="inputCol" width="10%" align="right">&nbsp;</td>
							</tr>
							<tr>
								<td class="textColS" >1st <%=MessageResourceUtil.getTextDescription(request, "MONTHLY_INSTALMENT_AMOUNT") %> :</td>
								<td class="inputCol" align="right"><%= ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getMonthlyIntalmentAmtOne())%></td>
								<td class="textColS" ><%=MessageResourceUtil.getTextDescription(request, "FIRST_INTEREST_RATE") %> :</td>
								<td class="inputCol" align="right"><%= ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getFirstTierRate())%></td>
								<td class="textColS" ><%=MessageResourceUtil.getTextDescription(request, "FIRST_TIER_TERM") %> :</td>
								<td class="inputCol" align="right"><%= ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getFirstTierTerm())%></td>
								
							</tr>
							<tr>
								<td class="textColS" >2nd <%=MessageResourceUtil.getTextDescription(request, "MONTHLY_INSTALMENT_AMOUNT") %> :</td>
								<td class="inputCol" align="right"><%= ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getMonthlyIntalmentAmtTwo())%></td>
								<td class="textColS" ><%=MessageResourceUtil.getTextDescription(request, "SECOND_INTEREST_RATE") %> :</td>
								<td class="inputCol" align="right"><%= ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getSecondTierRate())%></td>
								<td class="textColS" ><%=MessageResourceUtil.getTextDescription(request, "SECOND_TIER_TERM") %> :</td>
								<td class="inputCol" align="right"><%= ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getSecondTierTerm())%></td>
								
							</tr>
							<tr>
								<td class="textColS" >3nd <%=MessageResourceUtil.getTextDescription(request, "MONTHLY_INSTALMENT_AMOUNT") %> :</td>
								<td class="inputCol" align="right"><%= ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getMonthlyIntalmentAmtThree())%></td>
								<td class="textColS" ><%=MessageResourceUtil.getTextDescription(request, "THIRD_INTEREST_RATE") %> :</td>
								<td class="inputCol" align="right"><%= ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getThirdTierRate())%></td>
								<td class="textColS" ><%=MessageResourceUtil.getTextDescription(request, "THIRD_TIER_TERM") %> :</td>
								<td class="inputCol" align="right"><%= ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getThirdTierTerm())%></td>
							</tr>
							<tr>
								<td class="textColS" >4nd <%=MessageResourceUtil.getTextDescription(request, "MONTHLY_INSTALMENT_AMOUNT") %> :</td>
								<td class="inputCol" align="right"><%= ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getMonthlyIntalmentAmtFour())%></td>
								<td class="textColS" ><%=MessageResourceUtil.getTextDescription(request, "FORTH_INTEREST_RATE") %> :</td>
								<td class="inputCol" align="right"><%= ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getForthTierRate())%></td>
								<td class="textColS" ><%=MessageResourceUtil.getTextDescription(request, "FORTH_TIER_TERM") %> :</td>
								<td class="inputCol" align="right"><%= ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getForthTierTerm())%></td>
							</tr>
							<tr>
								<td colspan="7" height="3"></td>
							</tr>			
							</table>
						</fieldset>						
					</td></tr>
				</table>
			</td></tr>
			</table>
		</td></tr>		
	</table>
	</td></tr>
</table>

<script language="JavaScript">

function backToSearchType(){

	appFormName.action.value="FristApp";
	appFormName.handleForm.value = "N";
	appFormName.submit(); 
}
</script>
