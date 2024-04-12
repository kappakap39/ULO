<%@ page import="java.util.Vector" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.model.ApplicationDataM" %>
<%@ page import="com.eaf.orig.shared.model.PersonalInfoDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.xrules.shared.model.XRulesVerificationResultDataM" %>
<%@ page import="com.eaf.xrules.shared.model.XRulesDebtBdDataM" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.HashMap" %>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>

<% 
	//MessageResourceUtil msgUtil = MessageResourceUtil.getInstance();
		ORIGUtility utility = new ORIGUtility();
%>

<%
System.out.println("==> in Xrules debtbd.jsp");

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
	 String  diplayMode=ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
             System.out.println("viewFromReport "+request.getParameter("viewFromReport"));
                String chkDisable="";
              if(request.getParameter("viewFromReport")!=null){
                  if( session.getAttribute("personalApplicatinVerification")!=null){
                     personalInfoDataM=(PersonalInfoDataM)session.getAttribute("personalApplicatinVerification");
                     }
                    diplayMode=ORIGDisplayFormatUtil.DISPLAY_MODE_VIEW;
                   chkDisable=" disabled";
                 }   	
	if(personalInfoDataM == null){
		personalInfoDataM = new PersonalInfoDataM();
	}
   XRulesVerificationResultDataM  xrulesVer=personalInfoDataM.getXrulesVerification();
   XRulesDebtBdDataM xrulesDebtbdDataM=xrulesVer.getXRulesDebtBdDataM();
   if(xrulesDebtbdDataM==null){
   xrulesDebtbdDataM=new XRulesDebtBdDataM();
   }
   ApplicationDataM  applicationDataM=ORIGForm.getAppForm();
   Vector vPersonalInfoDataMs=applicationDataM.getPersonalInfoVect();
   //Check data from session
    System.out.println("debtCalulateItem");
    HashMap hDebtbdCheckbox=(HashMap)session.getAttribute("debtCalulateItem");
%>
<HTML><HEAD><TITLE>Debt Burden </TITLE>
<SCRIPT language="JavaScript"> 
window.onBlur = self.focus(); 
function saveDebtbdVerification(){
    form = document.appFormName;
	form.action.value = "SaveXrulesDebtBurdenPopup";	                     
	form.handleForm.value = "N"; 
	form.submit();
 }
function calulcateDebtbdVerification(){
    form = document.appFormName;
    form.action.value = "CalculateXrulesDebtBurdenPopup";
	form.handleForm.value = "N"; 
	form.submit();
 }

 
</SCRIPT>
</HEAD>
<table width="100%" border=0 cellpadding="0" cellspacing="0">
	<TR><TD height="5" colspan="3"></TD></TR>
	<TR><TD height="2" bgcolor="#3399cc" colspan="3"></TD></TR>
</table>
<div style="background-color: #F4F4F4">
	<SPAN style="background-color: #3399cc; vertical-align: top; height: 16">&nbsp;&nbsp;<font color="#FFFFFF"><b>Debt burden</b></font>&nbsp;</SPAN><SPAN><img src="en_US/images/SFCorner.gif"></SPAN>&nbsp;
</div>
<table width="100%"   border="0" cellpadding="1" cellspacing="1" bgcolor="#333333">
  <tr>
    <td bgcolor="#FFFFFF" class="TableHeader"  colspan="5"><strong>&nbsp;Application Debt burden  comprise of :</strong></td>    
  </tr>
  <%   
  // for(int i=0;i<vXrulesDedup.size();i++){
 // XRulesDebtBdDataM xrulesDedupDataM=(XRulesDebtBdDataM)vXrulesDedup.get(i);    
    if(OrigConstant.PERSONAL_TYPE_APPLICANT.equalsIgnoreCase(personalInfoDataM.getPersonalType())) {
    XRulesVerificationResultDataM  prmXRulesVerificationResultDataM =personalInfoDataM.getXrulesVerification();
     BigDecimal  finalDebtScore=null;
    if( session.getAttribute("debtCalulateScore")!=null){
         finalDebtScore=(BigDecimal)session.getAttribute("debtCalulateScore");
        }else{
        finalDebtScore=prmXRulesVerificationResultDataM.getDEBT_BDScore();
      }
     
   %>
  <tr>
    <td bgcolor="#FFFFFF" width="20">&nbsp;
    <td bgcolor="#FFFFFF" width="120">&nbsp;  Main Customer</td>
    <td bgcolor="#FFFFFF" width="140">&nbsp;<%=ORIGDisplayFormatUtil.displayCommaNumber(xrulesDebtbdDataM.getDebtBurdentScore())%> %</td>
    <td bgcolor="#FFFFFF" width="220">&nbsp;<%if("".equals(chkDisable)){ %><INPUT type="button" class="button_text" name="btnCalulateDebtBd" value="Calculate Application Debt Burdent" onClick="calulcateDebtbdVerification()"><%}%> </td>
    <td bgcolor="#FFFFFF" width="120" align="right">&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(finalDebtScore),diplayMode,"20","finalDebtdb","textboxCurrency",chkDisable) %>
     </td>   
  </tr>
  <%}%>
  <%
   System.out.println("guarantor");
    int guarantorCount=1;
    for(int i=0;i<vPersonalInfoDataMs.size();i++){ 
    PersonalInfoDataM  prmPersonalInfoDataM=(PersonalInfoDataM)vPersonalInfoDataMs.get(i);
    if(OrigConstant.PERSONAL_TYPE_GUARANTOR.equalsIgnoreCase(prmPersonalInfoDataM.getPersonalType())){
    XRulesVerificationResultDataM  prmXRulesVerificationResultDataM =prmPersonalInfoDataM.getXrulesVerification();
     XRulesDebtBdDataM  prmXRulesDebtbdDataM=null;
     if(prmXRulesVerificationResultDataM!=null){
        prmXRulesDebtbdDataM=prmXRulesVerificationResultDataM.getXRulesDebtBdDataM();
      }
      if( prmXRulesDebtbdDataM==null){
      prmXRulesDebtbdDataM=new XRulesDebtBdDataM();
      }
      String  guarantorDebtbdUseFlag=prmXRulesDebtbdDataM.getUseFlag();
      //System.out.println("Chkbox "+(String)hDebtbdCheckbox.get("chkguarantorUseFlag_"+i));
      if(hDebtbdCheckbox!=null && OrigConstant.ORIG_FLAG_Y.equalsIgnoreCase((String)hDebtbdCheckbox.get("chkGuarantorUseFlag_"+i))){
         guarantorDebtbdUseFlag=OrigConstant.ORIG_FLAG_Y;
      }
      if (prmXRulesDebtbdDataM!=null&&prmXRulesDebtbdDataM.getDebtBurdentScore()!=null&& prmXRulesDebtbdDataM.getDebtBurdentScore().doubleValue()>0d){            
    //get verification list
  %>  
   <tr>
    <td bgcolor="#FFFFFF" width="20">&nbsp;
    <%=ORIGDisplayFormatUtil.displayCheckBox( guarantorDebtbdUseFlag,"chkguarantorUseFlag_"+i,OrigConstant.ORIG_FLAG_Y,"") %>             
    </td>
    <td bgcolor="#FFFFFF" width="120">&nbsp; Guarantor <%=guarantorCount++%>  </td>
    <td bgcolor="#FFFFFF" width="140">&nbsp;<%=ORIGDisplayFormatUtil.displayCommaNumber(prmXRulesDebtbdDataM.getDebtBurdentScore())%> %</td>
    <td bgcolor="#FFFFFF" width="220">&nbsp;</td>
    <td bgcolor="#FFFFFF" width="120">&nbsp;</td>   
  </tr>
  <%  }
     }//if garuntor
    }//fo
   %>
  <tr height="30" >
		<td colspan="5" align="center" bgcolor="#FFFFFF">&nbsp;
		    <%if("".equals(chkDisable)){ %>
   			  <INPUT class="button_text" type="button" value="<%=MessageResourceUtil.getTextDescription(request, "SAVE") %>" onClick="saveDebtbdVerification()">&nbsp;	 
   			<% }
   			
   			 System.out.println("chkDisable Save");
   			%>
			<INPUT class="button_text" type="button" value="<%=MessageResourceUtil.getTextDescription(request, "CLOSE") %>" onClick="window.close()">	</td>
  </tr>
  
  
</table> 
<%	//set current screen to main Form
	com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager =
	(com.eaf.j2ee.pattern.control.ScreenFlowManager) request.getSession(true).getAttribute(
		"screenFlowManager");
	screenFlowManager.setCurrentScreen("MAIN_APPFORM");
%>
