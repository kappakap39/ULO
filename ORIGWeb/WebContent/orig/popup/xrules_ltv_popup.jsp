
<%@ page import="java.util.Vector" %>
<%@ page import="com.eaf.orig.shared.model.ApplicationDataM" %>
<%@ page import="com.eaf.orig.shared.model.PersonalInfoDataM" %>
<%@ page import="com.eaf.orig.shared.model.LoanDataM" %>
<%@ page import="com.eaf.orig.shared.model.CollateralDataM" %>
<%@ page import="com.eaf.orig.shared.model.AppraisalDataM" %>
<%@ page import="com.eaf.orig.shared.model.AddressDataM" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.xrules.shared.constant.XRulesConstant"%>
<%@ page import="com.eaf.xrules.shared.model.XRulesVerificationResultDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>

<%
ApplicationDataM  appForm = ORIGForm.getAppForm();
if(appForm==null){
	appForm = new ApplicationDataM();
}
ORIGUtility utility = new ORIGUtility();
PersonalInfoDataM personalInfoDataM;
String personalType = (String) request.getSession().getAttribute("PersonalType");
System.out.println(">>xrules_eligibility_popup.jsp personalType="+personalType);
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
   
XRulesVerificationResultDataM  xrulesVer=personalInfoDataM.getXrulesVerification();
String ltvResult=xrulesVer.getLtvResult();


String ltv = "";
String popDev = "";

Vector collateralVect = ORIGForm.getAppForm().getCollateralVect();
if(collateralVect!=null && collateralVect.size()>0){
	CollateralDataM collateralM = (CollateralDataM)collateralVect.elementAt(0);
	AppraisalDataM appraisalM = collateralM.getAppraisal();
	ltv = String.valueOf(appraisalM.getLtv());
	popDev = collateralM.getPropertyDevelopers();
}
System.out.println(">>> ltv="+ltv);
System.out.println(">>> popDev="+popDev);


String chkDisable="";
String  displayMode=ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
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
                          	<tr>
	                          	<td class="text-header-detail" nowrap="nowrap">LTV Result Screen</td>
	                            <td width="330">
	                            	<table width="60" border="0" align="right" cellpadding="2" cellspacing="0">
	                              		<tr height="30"><td>
		                              		<%if("".equals(chkDisable)){ %>
												<INPUT class="button" type="button" value="<%=MessageResourceUtil.getTextDescription(request, "SAVE") %>" onClick="savePolicyRulesVerification()">
											<%} %>
	                              			 </td><td>
												<INPUT class="button" type="button" value="<%=MessageResourceUtil.getTextDescription(request, "CLOSE") %>" onClick="window.close()">
	                                    </td></tr>
	                                </table>
	                            </td></tr>
                            </table>
                        </td></tr>
                        <tr> <td  height="15"></td></tr>
                        <tr class="sidebar10"> <td align="center">
							<table cellpadding="0" cellspacing="0" width="100%" align="center">	 
								<tr>
									<td class="textColS" width="50%" align="right"><b><%=MessageResourceUtil.getTextDescription(request, "FINAL_RESULT") %></b></td>
									<td class="textColS" width="20%">&nbsp;</td>
									<td class="textColS" align="left" width="30%">
										<%if("".equals(chkDisable)){ %>
										 	<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
										 	<tr><td><font class="textColS">
									           Pass</font> </td> 
									        <td>             
									        	<%=ORIGDisplayFormatUtil.displayRadioTag(XRulesConstant.ExecutionResultString.RESULT_PASS,displayMode,"ltvResult",ltvResult,
									        		XRulesConstant.ExecutionResultString.RESULT_PASS,XRulesConstant.ExecutionResultString.RESULT_PASS) %>
									        </td><td><font class="textColS">
									        	Fail</font>
									        </td><td>
									        	<%=ORIGDisplayFormatUtil.displayRadioTag(XRulesConstant.ExecutionResultString.RESULT_FAIL,displayMode,"ltvResult",ltvResult,
									        		XRulesConstant.ExecutionResultString.RESULT_FAIL,XRulesConstant.ExecutionResultString.RESULT_FAIL) %>
									        </td></tr></table> 
									     <%}else{ %>
									     	&nbsp;&nbsp;<%//=ORIGDisplayFormatUtil.displayHTML(policyRulesResult) %>
									     <%} %> 
									</td>
								</tr>
								<tr><td>&nbsp;</td></tr>
							</table>
							<TABLE border="0" width="100%" cellpadding="1" cellspacing="1" id="KLTable">
								<TR>
									<TD align="center" class="Bigtodotext3"><b><%=MessageResourceUtil.getTextDescription(request, "CRITERIA") %></b></TD>
									<TD width="200" align="center" class="Bigtodotext3" ><b><%=MessageResourceUtil.getTextDescription(request, "SYSTEM_RESULT") %></b></TD>
								</TR>
								<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
								<TR>
									<TD class="textColS" nowrap >&nbsp;<%=ORIGDisplayFormatUtil.displayHTML("Loan To Value") %></TD>
									<TD class="textColS" width="200" nowrap id="ltv01">&nbsp;</TD>
								</TR>						
							</TABLE>
						</TD></TR>
					</table>
					</td></tr>
				</table>
			</td></tr>
		</table>
	</td></tr>
</table>


<script language="javascript">
///Mock up
/*var data = "REJECT,Red";
	
	var result = "";
	var color = "";
	var elResults = data.split('|');
	
	if(elResults!=null && elResults.length > 0){
		for(i=0; i<elResults.length; i++){
			var elResultData = elResults[i].split(',');
			result = elResultData[0];
			color = elResultData[1];
			var resultDiv = document.getElementById("ltv0"+(i+1));
			resultDiv.innerHTML = "<div style='color:"+color+"' align='center'><B>"+result+"</B></div>";
		}
	}*/
///Mock up
var req = new DataRequestor();
//var url = "http://larukunb:8080/pau/EligibilityService.jsp";
var url = "http://vmwas61:8080/pau/EligibilityService.jsp";


req.addArg(_POST, "actionType", "L");
<%
if("".equals(ltv)){
	ltv = "0";
}
%>
req.addArg(_POST, "LTV", "<%=ltv%>");
req.addArg(_POST, "popDev", "<%=popDev%>");
req.getURLForThai(url, _RETURN_AS_TEXT);

req.onload = function (data, obj) {		
	var result = "";
	var color = "";
	var elResults = data.split('|');
	
	if(elResults!=null && elResults.length > 0){
		for(i=0; i<elResults.length; i++){
			var elResultData = elResults[i].split(',');
			result = elResultData[0];
			color = elResultData[1];
			var resultDiv = document.getElementById("ltv0"+(i+1));
			resultDiv.innerHTML = "<div style='background-color:"+color+"' align='center'><B>"+result+"</B></div>";
		}
	}
	
}

window.onBlur = self.focus();
function savePolicyRulesVerification(){  
    var  form = document.appFormName;
	form.action.value ="SaveXrulesLTVPopup";
	form.handleForm.value = "N";
	if(form.ltvResult[0].checked ||form.ltvResult[1].checked){
		form.submit();
	}else{
	 alert("Please Verify Result");
	}
}

</script>