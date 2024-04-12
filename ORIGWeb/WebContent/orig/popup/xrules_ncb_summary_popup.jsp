<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.model.ApplicationDataM" %>
<%@ page import="com.eaf.orig.shared.model.PersonalInfoDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.xrules.shared.model.XRulesVerificationResultDataM" %>
<%@ page import="com.eaf.xrules.shared.model.XRulesNCBDataM" %>
<%@ page import="java.util.Vector"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="com.eaf.orig.shared.utility.OrigXRulesUtil"%>
<%@ page import="com.eaf.xrules.shared.constant.XRulesConstant"%>
<%@ page import="com.eaf.ncb.model.output.IQRespM"%>
<%@ page import="com.eaf.ncb.model.output.NCBOutputDataM"%>
<%@ page import="com.eaf.ncb.model.output.PNRespM"%>
<%@ page import="com.eaf.ncb.model.output.IDRespM"%>
<%@ page import="com.eaf.ncb.model.output.PARespM"%>
         
  

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>

<% 
	ORIGUtility utility = new ORIGUtility();
	OrigXRulesUtil origXrulesUtil=OrigXRulesUtil.getInstance();
%>

<%
	ApplicationDataM  appForm = ORIGForm.getAppForm();
//get Personal
	PersonalInfoDataM personalInfoDataM;
		String personalType = (String) request.getSession().getAttribute("PersonalType");
	if(OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)){
		personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("MAIN_POPUP_DATA");
	}else if(OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType)){
		personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("SUPCARD_POPUP_DATA");
	}else{
		personalInfoDataM = utility.getPersonalInfoByType(ORIGForm.getAppForm(),OrigConstant.PERSONAL_TYPE_APPLICANT);
	}
   String  displayMode=ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
   //System.out.println("viewFromReport "+request.getParameter("viewFromReport"));
   String chkDisable="";
    //=============================================
    if(request.getParameter("viewFromReport")!=null){   
    if(session.getAttribute("applicationVerification")!=null){ 
     ApplicationDataM applicationDataM=(ApplicationDataM)session.getAttribute("applicationVerification"); 	     
      String reportPersonalSeq=request.getParameter("reportPersonalSeq");     
      String reportPersonalType=request.getParameter("reportPersonalType");            
      int personalSeq=utility.stringToInt(reportPersonalSeq);
      personalInfoDataM=utility.getPersonalInfoByTypeAndSeq( applicationDataM,reportPersonalType,personalSeq);     
     } 
      displayMode=ORIGDisplayFormatUtil.DISPLAY_MODE_VIEW;
      //chkDisable=" disabled";
    }
    //=============================================
	if(personalInfoDataM == null){
		personalInfoDataM = new PersonalInfoDataM();
	}
   XRulesVerificationResultDataM  xrulesVer=personalInfoDataM.getXrulesVerification();
   Vector  vXrulesNCB=xrulesVer.getVXRulesNCBDataM();
   if(vXrulesNCB==null){
   vXrulesNCB=new Vector();
   }
  Vector vRoles=ORIGUser.getRoles();
  if( vRoles!=null &&vRoles.get(0)!=null&& (vRoles.get(0).equals(OrigConstant.ROLE_UW)||  vRoles.get(0).equals(OrigConstant.ROLE_XUW))){
        displayMode=ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
        chkDisable="";
  }else{
     displayMode=ORIGDisplayFormatUtil.DISPLAY_MODE_VIEW;
     chkDisable=" disabled";
  }
  NCBOutputDataM ncbOutputDataM=(NCBOutputDataM)session.getAttribute("NCBOutput"); 
  Vector vNCBEnquiry=null;
  Vector vNCBNames=null;
  Vector vNCBAddress=null;
  Vector vNCBCards=null;   
  if(ncbOutputDataM!=null){
    vNCBEnquiry=ncbOutputDataM.getIqRespMs();
    vNCBNames=ncbOutputDataM.getPnRespMs();
    vNCBAddress=ncbOutputDataM.getPaRespMs();
    vNCBCards=ncbOutputDataM.getIdRespMs();
  }
 HashMap   hNCBNameGroup=(HashMap)session.getAttribute("NCBNameGroup");
 HashMap   hNCBIDGroup=(HashMap)session.getAttribute("NCBIdGroup");
 if(hNCBIDGroup==null){
  hNCBIDGroup=new HashMap();
 }
//  if(hNCBIDGroup==null){
//   hNCBNameGroup=new HashMap();
//  }
%>


<HEAD><TITLE>NCB : NCB Result </TITLE>
<META http-equiv=content-type content="text/html; charset=UTF-8">
<SCRIPT language="JavaScript">
window.onBlur = self.focus();
 function getPostionPopupX(popupWidth){   	 
    var frameWidth =  screen.width;
    var frameHeight =  screen.height;	 
	var result=(frameWidth/2)-(popupWidth/2);
	return result;	
 } 
function displayLoanType(accountType){
    var popupWebAction='LoadXruleNCBDetailPopup&NCBaccountType='+accountType;
    var popupWidth='1024';
    var popupHeight='650';
//    getPostionPopupX(popupWidth)
    loadPopup('NCBDetail',popupWebAction,popupWidth,popupHeight,'100',getPostionPopupX(popupWidth),'','','<%=personalType%>');
}
 
</SCRIPT>
</HEAD> 
<!--Personal information  --> 
<TABLE cellSpacing=0 cellPadding=0 width="100%"  border="0">
	<TR><TD vAlign=top>
	<table cellpadding="0" cellspacing="0" width="100%" align="center" bgcolor="#FFFFFF">
		<tr><td colspan="2" class="sidebar8">
			<table cellSpacing="1" cellPadding="0" width="100%" border="0"> 
		    	<tr><td class="sidebar9">
					<table cellspacing=0 cellpadding=0 width="100%" align=center border=0>	     		
			 			<tr> <td  height="10">
			 				<table width="100%" border="0" cellspacing="0" cellpadding="0">
                          	<tr>
	                          	<td class="text-header-detail"><%=MessageResourceUtil.getTextDescription(request, "PERSONAL_INFO") %> </td>
	                            <td width="330">
	                            	<table width="50" border="0" align="right" cellpadding="2" cellspacing="0">
	                              		<tr height="30"><td>
	                              			<INPUT class="button" type="button" value="<%=MessageResourceUtil.getTextDescription(request, "CLOSE") %>" onClick="window.close()">
	                                    </td></tr>
	                                </table>
	                            </td></tr>
                            </table>
                        </td></tr>
                        <tr> <td  height="15"></td></tr>                        
						<tr class="sidebar10"> <td align="center">
						 	 <table cellpadding="" cellspacing="0" width="100%" align="center" bgcolor="#FFFFFF">
						 	 	<tr><td>
						 	 		<table class="gumayframe3" cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
									<tr><td class="TableHeader">
										<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
											<tr> 
											    <td width="27%" align="center" class="Bigtodotext3"><%=MessageResourceUtil.getTextDescription(request, "NAME") %></td>
											    <td width="17%" class="Bigtodotext3">&nbsp;</td>
											    <td width="11%" class="Bigtodotext3">&nbsp;</td>
											    <td width="14%" class="Bigtodotext3">&nbsp;</td>
											    <td width="31%" class="Bigtodotext3">&nbsp;</td>
											  </tr>
										</table> 
									</td></tr>
						  <% PNRespM pnNameDataM=null;
						  if(hNCBNameGroup!=null&&!hNCBNameGroup.isEmpty()){
						  Iterator itNCBNameKey=hNCBNameGroup.keySet().iterator();
						    
						  	while(itNCBNameKey.hasNext()){ 
						  		String groupSeq=(String)itNCBNameKey.next();
						  		vNCBNames=(Vector)hNCBNameGroup.get(groupSeq);  
						  		if(vNCBNames!=null&&vNCBNames.size()>0){
						  		for(int i=0;i<vNCBNames.size();i++){
						       		pnNameDataM=(PNRespM)vNCBNames.get(i);
						  %>	  							
	  								<tr><td align="center" class="gumaygrey2">
										<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
											<tr>
											  <td class="jobopening2" width="27%"><%=ORIGDisplayFormatUtil.displayHTML(pnNameDataM.getTitle())%>&nbsp;<%=ORIGDisplayFormatUtil.displayHTML(pnNameDataM.getFirstName())%>&nbsp;<%=ORIGDisplayFormatUtil.displayHTML(pnNameDataM.getMiddle())%>&nbsp;<%=ORIGDisplayFormatUtil.displayHTML(pnNameDataM.getFamilyName1())%>&nbsp;<%=ORIGDisplayFormatUtil.displayHTML(pnNameDataM.getFamilyName2())%>&nbsp;</td>
											  <td class="jobopening2" width="17%">&nbsp;</td>
											  <td class="jobopening2" width="11%">&nbsp;</td>
											  <td class="jobopening2" width="14%">&nbsp;</td>
											  <td class="jobopening2" width="31%">&nbsp;</td>
											</tr>
										</table> 
									</td></tr>
						  <% }%>
						  <% }else {%>
									  <tr><td align="center" class="gumaygrey2">
										<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
											<tr>
										  		<td class="jobopening2" align="center">Results Not Found.</td>
										  	</tr>
										</table> 
									</td></tr>
						  <%}  %>
						  			<tr><td align="center" class="gumaygrey2">
										<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
											<tr class="TableHeader"> 
											    <td width="50%" align="left" class="jobopening2"><b> <%=MessageResourceUtil.getTextDescription(request, "CARD_INFO") %></b></td>
											    <td width="50%" class="jobopening2">&nbsp;</td>
											  </tr>
										</table> 
									</td></tr>        
					        <% vNCBCards=(Vector)hNCBIDGroup.get(groupSeq);
					         if(vNCBCards!=null&&vNCBCards.size()>0){
					           for(int i=0;i<vNCBCards.size();i++){
					            IDRespM  ncbIdCard=(IDRespM)vNCBCards.get(i);
					         %>
							        <tr><td align="center" class="gumaygrey2">
										<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
											<tr>
									          <td width="50%" class="jobopening2"><%=ORIGDisplayFormatUtil.displayHTML( origXrulesUtil.getNCBParamEngDescription("ID","IDType",ncbIdCard.getIdType()))%> :</td>
									          <td width="50%" class="jobopening2"><%=ORIGDisplayFormatUtil.displayHTML(ncbIdCard.getIdNumber())%></td>
									        </tr>
										</table> 
									</td></tr> 
					       <% }%>
					       <% }else{%>
								    <tr><td align="center" class="gumaygrey2">
										<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
											<tr>
										  		<td class="jobopening2" align="center">Results Not Found.</td>
										  	</tr>
										</table> 
									</td></tr>
					       <%} %> 					       		
						  	<%  } } %>
	      					</table>
	      				</td></tr>
      					<tr class="sidebar10"> <td align="center">
      						<table cellpadding="0" cellspacing="0" width="100%">
								<tr>
								    <td class="Bigtodotext3"><%=MessageResourceUtil.getTextDescription(request, "PERSONAL_INFO") %></td>
								    <td class="Bigtodotext3">&nbsp;</td>
								 </tr>
								  <% if(pnNameDataM==null){
								     pnNameDataM=new PNRespM();
								  } %>
								  <tr> 
								    <td class="textColS"><B><%=MessageResourceUtil.getTextDescription(request, "BIRTH_DATE") %> :</B></td>
								    <td class="inputCol"><%=ORIGDisplayFormatUtil.datetoString(pnNameDataM.getDateOfBirth()) %></td>
								  </tr>
								  <tr> 
								    <td class="textColS"><B><%=MessageResourceUtil.getTextDescription(request, "NATIONALITY") %> :</B></td>
								    <td class="inputCol"><%=ORIGDisplayFormatUtil.displayHTML(origXrulesUtil.getNCBParamEngDescription("PN","Nationtality",pnNameDataM.getNationality()))%></td>
								  </tr>
								  <tr> 
								    <td class="textColS"><B><%=MessageResourceUtil.getTextDescription(request, "GENDER") %> :</B></td>
								    <td class="inputCol"><%=ORIGDisplayFormatUtil.displayHTML(origXrulesUtil.getNCBParamEngDescription("PN","Gender",String.valueOf(pnNameDataM.getGender())))%></td>
								  </tr>
								  <tr> 
								    <td class="textColS"><B><%=MessageResourceUtil.getTextDescription(request, "MARITAL_STATUS") %> :</B></td>
								    <td class="inputCol"><%=ORIGDisplayFormatUtil.displayHTML( origXrulesUtil.getNCBParamEngDescription("PN","MarialStatus",pnNameDataM.getMaritalStatus()))%></td>
								  </tr>
								  <tr> 
								    <td class="textColS"><B><%=MessageResourceUtil.getTextDescription(request, "NO_OF_CHILDREN") %> :</B></td>
								    <td class="inputCol"><%=ORIGDisplayFormatUtil.displayHTML(String.valueOf(pnNameDataM.getNumberOfChild()))%></td>
								  </tr>
								  <tr> 
								    <td class="textColS"><B><%=MessageResourceUtil.getTextDescription(request, "OCCUPATION") %> :</B></td>
								    <td class="inputCol"><%=ORIGDisplayFormatUtil.displayHTML( origXrulesUtil.getNCBParamEngDescription("PN","Occupation",pnNameDataM.getOccupation()))%></td>
								  </tr>
								  <tr> 
								    <td class="textColS">&nbsp;</td>
								    <td class="inputCol">&nbsp;</td>
								  </tr>
							</table>
						</td></tr>
						<tr class="sidebar10"> <td align="center">
      						<table cellpadding="0" cellspacing="0" width="100%">
								<tr>
								    <td class="Bigtodotext3" width="25%"><div align="left"><strong>Consent Information</strong></div></td>
								    <td class="Bigtodotext3">&nbsp;</td>
								    <td class="Bigtodotext3">&nbsp;</td>
								    <td class="Bigtodotext3">&nbsp;</td>
								    <td class="Bigtodotext3">&nbsp;</td>
								</tr>
								<tr>
								    <td class="textColS" width="25%">Consent to Enquiry :</td>
								    <td class="inputCol" align="left"><%       
								     if(pnNameDataM!=null&&pnNameDataM.getConsentToEnq()!=null){      
								     	out.print(  ORIGDisplayFormatUtil.displayHTML(origXrulesUtil.getNCBParamEngDescription("PN","ConsentTo",pnNameDataM.getConsentToEnq())));
								     }
								     %></td>
								    <td class="textColS">&nbsp;</td>
								    <td class="textColS">&nbsp;</td>
								    <td class="textColS">&nbsp;</td>
								</tr>
							</table>
						</td></tr>
						<tr class="sidebar10"> <td align="center">
      						<table cellpadding="0" cellspacing="0" width="100%">
								<tr>
								<!-- Address Information -->
									<td class="Bigtodotext3" width="25%"><div align="left"><strong>Address Information</strong></div></td>
								    <td class="Bigtodotext3">&nbsp;</td>
								    <td class="Bigtodotext3">&nbsp;</td>
								    <td class="Bigtodotext3">&nbsp;</td>
								    <td class="Bigtodotext3">&nbsp;</td>
								</tr>
							</table>						 
							<table class="gumayframe3" width="100%" border="0" cellspacing="1" bgcolor="#FFFFFF"> 
							  <%if(vNCBAddress!=null&&vNCBAddress.size()>0){ 
							  for(int i=0;i<vNCBAddress.size();i++){
							   PARespM  addressDataM=(PARespM)vNCBAddress.get(i);   
							  %>   
							  <tr> 
							    <td width="15%" class="textColS" >Address :</td>
							    <td width="36%" class="inputCol" rowspan="4" valign="top"><%=ORIGDisplayFormatUtil.displayHTML(addressDataM.getAddressLine1())%>&nbsp;<%=ORIGDisplayFormatUtil.displayHTML(addressDataM.getAddressLine2())%>&nbsp;<%=ORIGDisplayFormatUtil.displayHTML(addressDataM.getAddressLine3())%>&nbsp;
							    <%=ORIGDisplayFormatUtil.displayHTML(addressDataM.getSubDistrict())%>&nbsp;<%=ORIGDisplayFormatUtil.displayHTML(addressDataM.getDistrict())%>&nbsp;<%=ORIGDisplayFormatUtil.displayHTML(addressDataM.getProvince())%>&nbsp;<%=ORIGDisplayFormatUtil.displayHTML(addressDataM.getPostCode()) %></td>
							    <td width="19%" class="textColS">Address Type :<br></td>
							    <td width="30%" class="inputCol"><%=ORIGDisplayFormatUtil.displayHTML(origXrulesUtil.getNCBParamEngDescription("PA","AddressType",addressDataM.getAddressType())) %></td>
							  </tr>
							  <tr> 
							    <td class="textColS">&nbsp;</td>
							    <td class="textColS">Residential Status :<br></td>
							    <td class="inputCol" colspan="2"><%=ORIGDisplayFormatUtil.displayHTML(origXrulesUtil.getNCBParamEngDescription("PA","ResidentialStatus",addressDataM.getResidentStatus())) %></td>
							  </tr> 
							  <tr> 
							    <td class="textColS">&nbsp;</td>
							    <td class="textColS"><%=ORIGDisplayFormatUtil.displayHTML(origXrulesUtil.getNCBParamEngDescription("PA","TelephoneType",addressDataM.getTelephoneType())) %> Telephone :</td>
							    <td class="inputCol" colspan="2"><%=ORIGDisplayFormatUtil.displayHTML(addressDataM.getTelephoneNO()) %></td>
							  </tr>
							  <tr> 
							    <td class="textColS">&nbsp;</td>
							    <td class="textColS">Report Date :</td>
							    <td class="inputCol" colspan="2"><%=ORIGDisplayFormatUtil.datetoString(addressDataM.getReportDate()) %></td>
							  </tr>
							  <tr><td colspan="4" class="textColS" ><hr/></td></tr>
							  <% } %>
							  <%}else{ %>
							  <tr height="30" class="gumaygrey2">
									<td class="jobopening2" colspan="4" align="center">Results Not Found.</td>
							  </tr>
							  <%} %>
							</table>
						</td></tr>
						<!-- NCB Account Summary -->  
						<tr class="sidebar10"> <td align="center">
      						<table cellpadding="0" cellspacing="0" width="100%">
								<tr>
								<!-- NCB Summary -->
									<td class="Bigtodotext3" width="25%"><div align="left"><strong>NCB Summary</strong></div></td>
								    <td class="Bigtodotext3">&nbsp;</td>
								    <td class="Bigtodotext3">&nbsp;</td>
								    <td class="Bigtodotext3">&nbsp;</td>
								    <td class="Bigtodotext3">&nbsp;</td>
								</tr>
							</table>
							<table cellpadding="" cellspacing="0" width="100%" align="center" bgcolor="#FFFFFF">
						 	 	<tr><td>
						 	 		<table class="gumayframe3" cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
									<tr><td class="TableHeader">
										<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
											<tr> 
											    <td class="Bigtodotext3" width="4%" align="center"><%=MessageResourceUtil.getTextDescription(request,"NO")%></td>
											    <td class="Bigtodotext3" width="9%" align="center"><%=MessageResourceUtil.getTextDescription(request, "ACCOUNT") %></td>
											    <td class="Bigtodotext3" width="15%" align="center"><%=MessageResourceUtil.getTextDescription(request, "LOAN_TYPE") %></td>
											    <td class="Bigtodotext3" width="13%" align="center"><%=MessageResourceUtil.getTextDescription(request, "LOAN_AMOUNT") %></td>
											    <td class="Bigtodotext3" width="12%" align="center"><%=MessageResourceUtil.getTextDescription(request, "CREDIT_LIMIT_NCB") %></td>
											    <td class="Bigtodotext3" width="12%" align="center"><%=MessageResourceUtil.getTextDescription(request, "CREDIT_USE") %></td> 
											    <td class="Bigtodotext3" width="12%" align="center"><%=MessageResourceUtil.getTextDescription(request, "INSTALL_AMT") %></td>
											    <td class="Bigtodotext3" width="10%" align="center"><%=MessageResourceUtil.getTextDescription(request,"DEBT") %></td>
											    <td class="Bigtodotext3" width="13%" align="center"><%=MessageResourceUtil.getTextDescription(request, "OS_BALANCE") %></td>
											</tr>
										</table>
									</td></tr>									
											
								  <% 
								    if(  vXrulesNCB.size()>0){
								  
								       BigDecimal totalInsallAmount=new BigDecimal(0);
								       //--new BigDecimal(0);
								        BigDecimal totalOsBalance=new BigDecimal(0);
								         HashMap hNCBSummyDebt=(HashMap)session.getAttribute("NCBSummaryInstallmentAdjust"); %>
								<%
								   for(int i=0;i<vXrulesNCB.size();i++){
								        XRulesNCBDataM  xrulesNCBDataM=(XRulesNCBDataM)vXrulesNCB.get(i);
								        String bgColor=(String)XRulesConstant.hNCBColorDisplay.get(xrulesNCBDataM.getColor());
								        totalInsallAmount=totalInsallAmount.add(xrulesNCBDataM.getInstallationAmount());
								        totalOsBalance=totalOsBalance.add(xrulesNCBDataM.getOSBalance());
								        BigDecimal debt=(BigDecimal)hNCBSummyDebt.get(xrulesNCBDataM.getLoanType());
								        if(debt==null){debt=new BigDecimal(0.0);}
								   %>
								   		<tr><td align="center" class="gumaygrey2">
										<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
											<tr  
										    <%if("".equals(chkDisable) ){%> onclick="displayLoanType('<%=xrulesNCBDataM.getLoanType()%>')"    
										      onmouseover="this.style.cursor='hand'" onmouseout=""
										    <%}%> >
											    <td class="jobopening2" width="4%" nowrap align="center"><%=ORIGDisplayFormatUtil.displayHTML(String.valueOf(i+1))%></td>
											    <td class="jobopening2" width="9%" nowrap><%=ORIGDisplayFormatUtil.displayHTML(String.valueOf(xrulesNCBDataM.getSumUnit()))%></td>
											    <td class="jobopening2" width="15%" nowrap><%=ORIGDisplayFormatUtil.displayHTML( origXrulesUtil.getNCBAccountTypeDescription( xrulesNCBDataM.getLoanType()))%></td>
											    <td class="jobopening2" width="13%" align="right" nowrap><%=ORIGDisplayFormatUtil.displayCommaNumber(xrulesNCBDataM.getLoanAmount())%></td>
											    <td class="jobopening2" width="12%" align="right" nowrap><%=ORIGDisplayFormatUtil.displayCommaNumber(xrulesNCBDataM.getCreditLimit())%></td>
											    <td class="jobopening2" width="12%" align="right" nowrap><%=ORIGDisplayFormatUtil.displayCommaNumber(xrulesNCBDataM.getCreditUse())%></td>
											    <td class="jobopening2" width="12%" align="right" nowrap><%=ORIGDisplayFormatUtil.displayCommaNumber(xrulesNCBDataM.getInstallationAmount())%></td>
											    <td class="jobopening2" width="10%" align="right" nowrap><%=ORIGDisplayFormatUtil.displayCommaNumber(debt)%></td>
											    <td class="jobopening2" width="13%" align="right" nowrap><%=ORIGDisplayFormatUtil.displayCommaNumber(xrulesNCBDataM.getOSBalance())%></td>
											</tr>
										</table> 
										</td></tr>
											  <% } %> 
										<tr><td align="center" class="gumaygrey2">
										<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0"> 
											<tr>    
											    <td class="Bigtodotext3" align="right" width="65%">Grand Total</td>
											    <td class="jobopening2" align="right" width="12%"><%=ORIGDisplayFormatUtil.displayCommaNumber(totalInsallAmount)%></td>
											    <td class="jobopening2" align="right" width="10%"><%=session.getAttribute("NCBSummaryInstallment")%></td>
											    <td class="jobopening2" align="right" width="13%"><%=ORIGDisplayFormatUtil.displayCommaNumber(totalOsBalance)%></td>
											</tr>
										</table> 
										</td></tr>	
										
											  <% }else {%> 
									 	<tr><td align="center" class="gumaygrey2">
										<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
											 <tr>
											 	<td class="jobopening2" colspan="9" align="center">Results Not Found.</td>
											 </tr>
										</table>
										</td></tr>	
											  <% } %>
										</table> 
										</td></tr>
									</table>
								</td></tr>
								<tr class="sidebar10"> <td align="center">
		      						<table cellpadding="0" cellspacing="0" width="100%">
										<tr>
										<!-- Tracing Report -->
											<td class="Bigtodotext3" width="25%"><div align="left"><strong>Tracing Report</strong></div></td>
										    <td class="Bigtodotext3">&nbsp;</td>
										    <td class="Bigtodotext3">&nbsp;</td>
										    <td class="Bigtodotext3">&nbsp;</td>
										    <td class="Bigtodotext3">&nbsp;</td>
										</tr>
									</table>
									<table cellpadding="" cellspacing="0" width="100%" align="center" bgcolor="#FFFFFF">
								 	 	<tr><td>
								 	 		<table class="gumayframe3" cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
												<tr><td class="TableHeader">
													<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
														<tr> 
														    <td class="Bigtodotext3" width="18%"><%=MessageResourceUtil.getTextDescription(request,"NCB_DATE_TIME")%> </td>
														    <td class="Bigtodotext3" width="25%"><%=MessageResourceUtil.getTextDescription(request, "NCB_TRACER") %></td>
														    <td class="Bigtodotext3" width="25%"><%=MessageResourceUtil.getTextDescription(request, "NCB_CATEGORY") %> </td>
														    <td class="Bigtodotext3" width="32%"><%=MessageResourceUtil.getTextDescription(request, "NCB_PURPOSE") %></td>     
														</tr>
													</table>
												</td></tr>
													  <%if(vNCBEnquiry!=null&&vNCBEnquiry.size()>0){
													    for(int i=0;i<vNCBEnquiry.size();i++){
													       IQRespM  ncbIQRespM=(IQRespM)vNCBEnquiry.get(i);
													    %>
												<tr><td align="center" class="gumaygrey2">
													<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
														<tr>
														     <td class="jobopening2" width="18%" nowrap><%=ORIGDisplayFormatUtil.displayHTML(ORIGDisplayFormatUtil.datetoString(ORIGDisplayFormatUtil.parseEngToThaiDate(ncbIQRespM.getDateOfEnq())))%>&nbsp;<%=ORIGDisplayFormatUtil.displayHTML(origXrulesUtil.displayNCBDate(ncbIQRespM.getTimeOfEnq()))%></td>
														     <td class="jobopening2" width="25%" nowrap><%=ORIGDisplayFormatUtil.displayHTML( ncbIQRespM.getMemberShortName())%>&nbsp;</td>
														     <td class="jobopening2" width="25%" nowrap>&nbsp;-&nbsp;</td>
														     <td class="jobopening2" width="32%" nowrap><%=ORIGDisplayFormatUtil.displayHTML( ncbIQRespM.getEnqPurpose()+" : "+MessageResourceUtil.getTextDescription(request,"NCB_PURPOSE_"+ncbIQRespM.getEnqPurpose()) )%>&nbsp;</td>
														</tr>
													</table>
												</td></tr>											
													  <%}//for
													   }else{%>
												<tr><td align="center" class="gumaygrey2">
													<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
														<tr>
															<td class="jobopening2" colspan="4" align="center">Results Not Found.</td>
														</tr>
													</table>
												</td></tr>												
													   <%}%>										
										</table>
									</td></tr>
								</table>
							</td></tr>
						</table>
					</td></tr> 						
				</table>
			</td></tr>
		</table>
	</td></tr>
</table>

<% //set current screen to main Form
	com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager =
	(com.eaf.j2ee.pattern.control.ScreenFlowManager) request.getSession(true).getAttribute(
		"screenFlowManager");
	screenFlowManager.setCurrentScreen("MAIN_APPFORM");
%>
