<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.model.ApplicationDataM" %>
<%@ page import="com.eaf.orig.shared.model.PersonalInfoDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %> 
<%@ page import="com.eaf.ncb.model.output.TLRespM" %>
<%@ page import="com.eaf.orig.shared.utility.OrigXRulesUtil" %>
<%@ page import="com.eaf.xrules.shared.constant.XRulesConstant"%>
<%@ page import="com.eaf.ncb.model.output.PNRespM"%>
<%@ page import="com.eaf.ncb.model.output.NCBOutputDataM"%>
<%@ page import="java.util.Vector"%>
<%@ page import="java.util.HashMap"%>  
<%@ page import="java.math.BigDecimal"%>  

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>

<% 
	   MessageResourceUtil msgUtil = MessageResourceUtil.getInstance();
		ORIGUtility utility = new ORIGUtility();
		OrigXRulesUtil origXruleUtil=OrigXRulesUtil.getInstance();
%>

<%
System.out.println("==> in Xrules NCB Detail.jsp");

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
	if(personalInfoDataM == null){
		personalInfoDataM = new PersonalInfoDataM();
	}	 
    Vector vAccountDetail=(Vector)session.getAttribute("NCBAccountDetail");
    if(vAccountDetail==null){vAccountDetail=new Vector();}
     HashMap hAccountDetailAdjust=(HashMap)session.getAttribute("NCBAccountDetailAdjust");
     HashMap   hNCBNameGroup=(HashMap)session.getAttribute("NCBNameGroup");
    NCBOutputDataM ncbOutputDataM=(NCBOutputDataM)session.getAttribute("NCBOutput"); 
    com.eaf.ncb.model.NCBReqRespConsentDataM  ncbReqResp=ncbOutputDataM.getNcbReqResp();  
    java.util.Date ncbEnquiryDate=ncbReqResp.getDateOfResponse();
   
%>


<HEAD><TITLE>NCB : NCB Details</TITLE>
<META http-equiv=content-type content="text/html; charset=UTF-8">
<SCRIPT language="JavaScript">
window.onBlur = self.focus();
function getPostionPopupX(popupWidth){   	 
    var frameWidth =  screen.width;
    var frameHeight =  screen.height;	 
	var result=(frameWidth/2)-(popupWidth/2);
	return result;	
 }
function displayNCBPaymentHistory(accountType,segmentValue,groupSeq){  
    var popupWebAction='LoadXruleNCBPaymentDetailPopup&NCBaccountType='+accountType+'&segmentValue='+segmentValue+'&groupSeq='+groupSeq;
    var popupWidth='1024';
    var popupHeight='655';
    loadPopup('NCBPaymentDetail',popupWebAction,popupWidth,popupHeight,'100',getPostionPopupX(popupWidth),'','','<%=personalType%>');
}
 
</SCRIPT>
</HEAD>
<table width="100%" border=0 cellpadding="0" cellspacing="0">
	<TR><TD height="5" colspan="3"></TD></TR>
	<TR><TD height="2" bgcolor="#3399cc" colspan="3"></TD></TR>
</table>
<div style="background-color: #F4F4F4">
	<SPAN style="background-color: #3399cc; vertical-align: top; height: 16">&nbsp;&nbsp;<font color="#FFFFFF"><b>NCB Detail</b></font>&nbsp;</SPAN><SPAN><img src="en_US/images/SFCorner.gif"></SPAN>&nbsp;
</div>
<table  width="100%" border="0" cellpadding="1" cellspacing="1"  bgcolor="#333333">   
  <tr>
    <td bgcolor="#FFFFFF" class="tableHeaderXrules"   ><strong>&nbsp;NO </strong></td>
    <td bgcolor="#FFFFFF" class="tableHeaderXrules"  ><strong>&nbsp;<%=msgUtil.getTextDescription(request, "LOAN_TYPE") %></strong></td>
    <td bgcolor="#FFFFFF" class="tableHeaderXrules"  ><strong>&nbsp;<%=msgUtil.getTextDescription(request, "NAME_COMPANY_NAME") %></strong></td>
    <td bgcolor="#FFFFFF" class="tableHeaderXrules"  ><strong>&nbsp;<%=msgUtil.getTextDescription(request, "ACCOUNT_STATUS") %> </strong></td>
    <td bgcolor="#FFFFFF" class="tableHeaderXrules"  ><strong>&nbsp;<%=msgUtil.getTextDescription(request, "MA_DATE") %></strong></td>
    <td bgcolor="#FFFFFF" class="tableHeaderXrules"  ><strong>&nbsp;<%=msgUtil.getTextDescription(request, "LOAN_AMOUNT") %></strong></td>
    <td bgcolor="#FFFFFF" class="tableHeaderXrules"  ><strong>&nbsp;<%=msgUtil.getTextDescription(request, "CREDIT_LIMIT_NCB") %></strong></td>
    <td bgcolor="#FFFFFF" class="tableHeaderXrules"  ><strong>&nbsp;<%=msgUtil.getTextDescription(request, "CREDIT_USE") %></strong></td>
    <td bgcolor="#FFFFFF" class="tableHeaderXrules"  ><strong>&nbsp;<%=msgUtil.getTextDescription(request, "INSTALL_AMT") %></strong></td>
    <td bgcolor="#FFFFFF" class="tableHeaderXrules"  ><strong>&nbsp;<%=msgUtil.getTextDescription(request,"DEBT") %></strong></td>
    <td bgcolor="#FFFFFF" class="tableHeaderXrules"  ><strong>&nbsp;<%=msgUtil.getTextDescription(request, "OS_BALANCE") %></strong></td>
    <td bgcolor="#FFFFFF" class="tableHeaderXrules" ><strong>&nbsp;<%=msgUtil.getTextDescription(request, "OVER_DUE_MONTH") %></strong></td>
  </tr>
  <%   double totalInstallment=0; 
   for(int i=0;i<vAccountDetail.size();i++){
        TLRespM  accountRespM=(TLRespM)vAccountDetail.get(i);   
        String bgColor=null;// (String)XRulesConstant.hNCBColorDisplay.get( XRulesUtil.getNCBColor(accountRespM,ncbEnquiryDate ));     
        double  loanAmt=0d;
        double  creditLimit=0d;
        double  creditUse=0d;
        double  osBalance=0d;
         if(OrigConstant.AccountType.CreditCard.equalsIgnoreCase(accountRespM.getAccountType())
         ||OrigConstant.AccountType.OverDraft.equalsIgnoreCase(accountRespM.getAccountType())
         ||OrigConstant.AccountType.Revolving.equalsIgnoreCase(accountRespM.getAccountType())
         ){
         creditLimit=accountRespM.getCreditLimOriLoanAmt();
         creditUse=accountRespM.getAmtOwed();
         }else{
         loanAmt=accountRespM.getCreditLimOriLoanAmt();
         osBalance=accountRespM.getAmtOwed();
         }
         totalInstallment+=accountRespM.getInstallAmt();
         BigDecimal debt=(BigDecimal)hAccountDetailAdjust.get(accountRespM.getSegmentValue()+"_"+String.valueOf(accountRespM.getGroupSeq()));
         if(debt==null){debt=new BigDecimal(0.0); }
         Vector vNcbNames=(Vector)hNCBNameGroup.get(String.valueOf(accountRespM.getGroupSeq()));
         if(vNcbNames==null){vNcbNames=new Vector();}
   %>
  <tr  onClick="displayNCBPaymentHistory('<%=accountRespM.getAccountType()%>' ,'<%=accountRespM.getSegmentValue()%>','<%=accountRespM.getGroupSeq()%>')"  onmouseover="this.style.cursor='hand'" onmouseout="">
    <td bgcolor="<%=bgColor%>" nowrap>&nbsp;<%=ORIGDisplayFormatUtil.displayHTML(String.valueOf(i+1)+".")%>&nbsp;</td>    
    <td bgcolor="<%=bgColor%>" nowrap>&nbsp;<%=ORIGDisplayFormatUtil.displayHTML(origXruleUtil.getNCBAccountTypeDescription(accountRespM.getAccountType()))%>&nbsp;</td>
        <td bgcolor="<%=bgColor%>" nowrap>&nbsp;
        <% for(int j=0;j<vNcbNames.size();j++){
        PNRespM pnNameDataM=(PNRespM)vNcbNames.get(j);
         %>
       <%=ORIGDisplayFormatUtil.displayHTML(pnNameDataM.getTitle())%> <%=ORIGDisplayFormatUtil.displayHTML(pnNameDataM.getFirstName())%> <%=ORIGDisplayFormatUtil.displayHTML(pnNameDataM.getMiddle())%> <%=ORIGDisplayFormatUtil.displayHTML(pnNameDataM.getFamilyName1())%> <%=ORIGDisplayFormatUtil.displayHTML(pnNameDataM.getFamilyName2())%>
        <%} %>
        &nbsp;</td>
    <td bgcolor="<%=bgColor%>" nowrap>&nbsp;<%=ORIGDisplayFormatUtil.displayHTML(accountRespM.getAccountStatus()+" : "+origXruleUtil.getNCBAccountStatusDescription(accountRespM.getAccountStatus()))%>&nbsp;</td>
    <td bgcolor="<%=bgColor%>" nowrap>&nbsp;<%=ORIGDisplayFormatUtil.displayHTML(ORIGDisplayFormatUtil.datetoString(ORIGDisplayFormatUtil.parseEngToThaiDate(accountRespM.getAsOfDate())))%>&nbsp;</td>
    <td bgcolor="<%=bgColor%>" align="right" nowrap >&nbsp;<%=ORIGDisplayFormatUtil.displayCommaNumber(loanAmt)%>&nbsp;</td>
    <td bgcolor="<%=bgColor%>" align="right" nowrap>&nbsp;<%=ORIGDisplayFormatUtil.displayCommaNumber(creditLimit)%>&nbsp;</td>
    <td bgcolor="<%=bgColor%>" align="right" nowrap>&nbsp;<%=ORIGDisplayFormatUtil.displayCommaNumber(creditUse)%>&nbsp;</td>
    <td bgcolor="<%=bgColor%>" align="right" nowrap>&nbsp;<%=ORIGDisplayFormatUtil.displayCommaNumber(accountRespM.getInstallAmt())%>&nbsp;</td>
    <td bgcolor="<%=bgColor%>" align="right" nowrap>&nbsp;<%=ORIGDisplayFormatUtil.displayCommaNumber(debt)%>&nbsp;</td> 
    <td bgcolor="<%=bgColor%>" align="right" nowrap>&nbsp;<%=ORIGDisplayFormatUtil.displayCommaNumber(osBalance)%>&nbsp;</td> 
    <td bgcolor="<%=bgColor%>"  >&nbsp;<%=ORIGDisplayFormatUtil.displayHTML( origXruleUtil.convertPaymentHistoryDisplay(accountRespM.getPaymentHist1(),accountRespM.getPaymentHist2()))%>&nbsp;</td>
  </tr>
  <% }%>
  <tr >    
    <td bgcolor="#FFFFFF" colspan="8" align="right">&nbsp;&nbsp;Total Installment Amount &nbsp;</td>
    <td bgcolor="#FFFFFF" align="right" nowrap>&nbsp;&nbsp;<%=ORIGDisplayFormatUtil.displayCommaNumber(totalInstallment)%></td>
    <td bgcolor="#FFFFFF" align="right" nowrap>&nbsp;&nbsp;<%=session.getAttribute("NCBDetailInstallment")%></td>
    <td bgcolor="#FFFFFF"  colspan="2">&nbsp; &nbsp;</td>
  </tr>
  
  <tr height="30" >
		<td colspan="12" align="center" bgcolor="#FFFFFF">&nbsp;
				<INPUT class="button_text" type=button value="<%=MessageResourceUtil.getTextDescription(request, "CLOSE") %>" onClick="window.close()">	</td>
  </tr>
</table> 

<%	//set current screen to main Form
	com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager =
	(com.eaf.j2ee.pattern.control.ScreenFlowManager) request.getSession(true).getAttribute(
		"screenFlowManager");
	screenFlowManager.setCurrentScreen("MAIN_APPFORM");
%>
