<%-- <%@page import="java.util.Arrays"%> --%>
<%-- <%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %> --%>
<%-- <%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %> --%>
<%-- <%@ page import="com.eaf.orig.shared.model.ApplicationDataM" %> --%>
<%-- <%@ page import="com.eaf.orig.shared.model.PersonalInfoDataM" %> --%>
<%-- <%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %> --%>
<%-- <%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>  --%>
<%-- <%@ page import="com.eaf.ncb.model.output.HSRespM" %> --%>
<%-- <%@ page import="com.eaf.ncb.model.output.TLRespM" %> --%>
<%-- <%@ page import="com.eaf.xrules.shared.utility.XRulesUtil" %> --%>
<%-- <%@ page import="com.eaf.orig.shared.utility.OrigXRulesUtil" %> --%>
<%-- <%@ page import="com.eaf.xrules.shared.constant.XRulesConstant"%> --%>
<%-- <%@ page import="com.eaf.xrules.shared.model.XRulesVerificationResultDataM"%> --%>
<%-- <%@ page import="com.eaf.xrules.shared.model.XRulesNCBAdjustDataM"%> --%>
<%-- <%@ page import="com.eaf.ncb.model.output.PNRespM"%> --%>
<%-- <%@ page import="com.eaf.ncb.model.output.IDRespM"%> --%>
<%-- <%@ page import="java.util.Vector"%> --%>
<%-- <%@ page import="java.util.Calendar"%> --%>
<%-- <%@ page import="java.util.HashMap"%> --%>
<%-- <%@ page import="java.math.BigDecimal"%> --%>
<%-- <%@ page import="com.eaf.ncb.model.output.NCBOutputDataM"%> --%>


<%-- <jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/> --%>
<%-- <jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/> --%>

<%-- <%  --%>
// 	   MessageResourceUtil msgUtil = MessageResourceUtil.getInstance();
// 		ORIGUtility utility = new ORIGUtility();
// 		OrigXRulesUtil origXruleUtil=OrigXRulesUtil.getInstance();
//     NCBOutputDataM ncbOutputDataM=(NCBOutputDataM)session.getAttribute("NCBOutput"); 
//     com.eaf.ncb.model.NCBReqRespConsentDataM  ncbReqResp=ncbOutputDataM.getNcbReqResp();  
//     java.util.Date ncbEnquiryDate=ncbReqResp.getDateOfResponse();
<%-- %> --%>

<%-- <% --%>
// System.out.println("==> in Xrules NCB Payment Detail.jsp");

// ApplicationDataM  appForm = ORIGForm.getAppForm();
// //get Personal
// 	PersonalInfoDataM personalInfoDataM;
// 		String personalType = (String) request.getSession().getAttribute("PersonalType");
// 	if(OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)){
// 		personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("MAIN_POPUP_DATA");
// 	}else if(OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType)){
// 		personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("SUPCARD_POPUP_DATA");
// 	}else{
// 		personalInfoDataM = utility.getPersonalInfoByType(ORIGForm.getAppForm(),OrigConstant.PERSONAL_TYPE_APPLICANT);
// 	}
// 	if(personalInfoDataM == null){
// 		personalInfoDataM = new PersonalInfoDataM();
// 	}
//     Vector vAccountPaymentHistoryDetail=(Vector)session.getAttribute("NCBAccountPaymentDetail");
//     if(vAccountPaymentHistoryDetail==null){vAccountPaymentHistoryDetail=new Vector();}
//     TLRespM currentAccountM=(TLRespM)session.getAttribute("NCBAccount");
//     String ncbAccountType="";
//     if(currentAccountM!=null){
//       ncbAccountType=currentAccountM.getAccountType();
//     }else{
//     currentAccountM=new TLRespM();
//     }
//     System.out.println("==vAccountPaymentHistoryDetail size "+vAccountPaymentHistoryDetail.size());
//     String asOfDateColor=OrigConstant.NCBcolor.BLACK;      
//     int dateDiff = ORIGDisplayFormatUtil.calcDateDifference(Calendar.DAY_OF_MONTH, currentAccountM.getAsOfDate(), ncbEnquiryDate);
//     if(!Arrays.asList(OrigConstant.AccountStatus.AccountClose).contains(currentAccountM.getAccountStatus())&&dateDiff>60 ){
//        asOfDateColor=OrigConstant.NCBcolor.ORANGE;
//     }
    
//   Vector iasObject=(Vector)session.getAttribute("iamObjects");
//   XRulesVerificationResultDataM  xruesVer= personalInfoDataM.getXrulesVerification();
//   boolean canAdjustNCB=false;
//   if(iasObject==null){iasObject=new Vector();}
//   XRulesNCBAdjustDataM xruleNCBAdjust=null;
//   String searchType = (String)session.getAttribute("searchType");
//   if(!"Enquiry".equals(searchType)){
<!-- //   for(int i=0;i<iasObject.size();i++){ -->
<!-- //      com.eaf.ias.shared.model.ObjectM  obj=(com.eaf.ias.shared.model.ObjectM)iasObject.get(i); -->
<!-- //      if("AdjustNCB".equals(obj.getObjectName())){       -->
<!-- //       Vector  vNcbAdjust=xruesVer.getVNCBAdjust(); -->
<!-- //       xruleNCBAdjust=origXruleUtil.getNCBAdjust(vNcbAdjust,currentAccountM); -->
<!-- //       canAdjustNCB=true; -->
<!-- //       break; -->
<!-- //      } -->
<!-- //   } -->
<!-- //   } -->
<!-- //   if(xruleNCBAdjust==null){ -->
<!-- //     xruleNCBAdjust=new XRulesNCBAdjustDataM(); -->
<!-- //   } -->
<!-- //   String groupSeq=String.valueOf(currentAccountM.getGroupSeq()); -->
<!-- //   HashMap   hNCBNameGroup=(HashMap)session.getAttribute("NCBNameGroup"); -->
<!-- //   HashMap   hNCBIDGroup=(HashMap)session.getAttribute("NCBIdGroup"); -->
<!-- //   Vector vNCBDetailName=(Vector)hNCBNameGroup.get(groupSeq); -->
<!-- //   Vector vNCBDetailCard=(Vector)hNCBIDGroup.get(groupSeq); -->
<!-- //   //Get Xrules Verificaton -->
 
<%-- %> --%>


<!-- <HEAD><TITLE>NCB : NCB Payment History Details</TITLE> -->
<!-- <META http-equiv=content-type content="text/html; charset=UTF-8"> -->
<!-- <SCRIPT language="JavaScript"> -->
// window.onBlur = self.focus();
// function saveNCBInstallmentAdjust(){  
//    var form =window.appFormName;
//    form.action.value = "SaveXruleNCBPaymentDetailPopup";
//    form.handleForm.value = "N";
//    form.submit();   
//  }
 
// function clearNCBInstallmentAdjust(){
//    var form =window.appFormName;
//    form.ncbAdjust.value='-';
//    form.action.value = "SaveXruleNCBPaymentDetailPopup";
//    form.handleForm.value = "N";
//    form.submit(); 
// } 
<%--  <%if(xruesVer.getVNCBAdjust()!=null ){%>  --%>
//    //alert('Adjust'); 
<%--    <%if(xruesVer.getVNCBAdjust().size()>0){%> --%>
//    var imgURL='./en_US/images/adjust/adjust.gif'
<%--    <%}else {%> --%>
//     var imgURL='./en_US/images/adjust/adjustBack.gif'
<%--    <%}%> --%>
//    var   imgAdjust=window.opener.opener.opener.document.getElementById('imgAdjust');   
//    if(imgAdjust){
//     // alert('Change Iamge');
//      imgAdjust.src=imgURL;
//    }
<%--    var   debtAmt=window.opener.opener.opener.document.getElementById('<%=(String)XRulesConstant.hResultBoxName.get( String.valueOf(XRulesConstant.ServiceID.DEBT_AMOUNT))%>'); --%>
<%--    var   debtAmtAdjust=window.opener.opener.opener.document.getElementById('<%=(String)XRulesConstant.hResultBoxName.get( String.valueOf(XRulesConstant.ServiceID.DEBT_AMOUNT))%>Adjust'); --%>
//    if(debtAmt){
//     debtAmt.value='';
//    }
//    if(debtAmtAdjust){
//     debtAmtAdjust.value='';
//    }  
<%--  <%} %> --%>
 
 
 
<!-- </SCRIPT> -->
<!-- </HEAD> -->
<!-- <table width="100%" border=0 cellpadding="0" cellspacing="0"> -->
<!-- 	<TR><TD height="5" colspan="3"></TD></TR> -->
<!-- 	<TR><TD height="2" bgcolor="#3399cc" colspan="3"></TD></TR> -->
<!-- </table> -->
<!-- <div style="background-color: #F4F4F4"> -->
<%-- 	<SPAN style="background-color: #3399cc; vertical-align: top; height: 16">&nbsp;&nbsp;<font color="#FFFFFF"><b><%=MessageResourceUtil.getTextDescription(request, "ACCOUNT_DETAIL") %></b></font>&nbsp;</SPAN><SPAN><img src="en_US/images/SFCorner.gif"></SPAN>&nbsp; --%>
<!-- </div> -->

<!-- <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#FFFFFF"> -->
<!--  <tr bgcolor="#FFFFFF"> -->
<%--     <td class="tableHeaderXrules" width="10%">&nbsp;<b><%=MessageResourceUtil.getTextDescription(request, "BL_IDNO") %></b></td> --%>
<%--     <td class="inputCol" colspan="2">&nbsp; <% --%>
<!-- //      if(vNCBDetailCard!=null&&vNCBDetailCard.size()>0){ -->
<!-- //            for(int i=0;i<vNCBDetailCard.size();i++){ -->
<!-- //             IDRespM  ncbIdCard=(IDRespM)vNCBDetailCard.get(i); -->
<%--          %> --%>
<%--          <%=ORIGDisplayFormatUtil.displayHTML( origXruleUtil.getNCBParamEngDescription("ID","IDType",ncbIdCard.getIdType()))%> :  --%>
<%--          <%=ORIGDisplayFormatUtil.displayHTML(ncbIdCard.getIdNumber())%>    --%>
<%--        <% } }%></td> --%>
<!--     <td class="tableHeaderXrules" width="10%">&nbsp;<b>Name</b></td> -->
<%--     <td class="inputCol" colspan="4">&nbsp;<%  --%>
<!-- //   PNRespM pnNameDataM=null; -->
<!-- //   if(vNCBDetailName!=null&&vNCBDetailName.size()>0){ -->
<!-- //   for(int i=0;i<vNCBDetailName.size();i++){ -->
<!-- //        pnNameDataM=(PNRespM)vNCBDetailName.get(i); -->
<%--   %>   --%>
<%--   <%=ORIGDisplayFormatUtil.displayHTML(pnNameDataM.getTitle())%> <%=ORIGDisplayFormatUtil.displayHTML(pnNameDataM.getFirstName())%> <%=ORIGDisplayFormatUtil.displayHTML(pnNameDataM.getMiddle())%> <%=ORIGDisplayFormatUtil.displayHTML(pnNameDataM.getFamilyName1())%> <%=ORIGDisplayFormatUtil.displayHTML(pnNameDataM.getFamilyName2())%> --%>
<%--   <% }} %> --%>
<!--   </td>      -->
<!--   </tr>     -->
<!--   <tr bgcolor="#FFFFFF"> -->
<%--     <td class="tableHeaderXrules" width="10%">&nbsp;<b><%=MessageResourceUtil.getTextDescription(request, "MEMBER") %></b></td> --%>
<!--     <td class="tableHeaderXrules" width="15%">&nbsp;</td> -->
<%--     <td class="tableHeaderXrules" width="10%">&nbsp;<b><%=MessageResourceUtil.getTextDescription(request, "ACCOUNT") %></b></td> --%>
<!--     <td class="tableHeaderXrules" width="15%">&nbsp;</td> -->
<%--     <td class="tableHeaderXrules" width="10%">&nbsp;<b><%=MessageResourceUtil.getTextDescription(request, "AGREEMENT") %></b></td> --%>
<!--     <td class="tableHeaderXrules" width="15%">&nbsp;</td> -->
<%--     <td class="tableHeaderXrules" width="10%">&nbsp;<b><%=MessageResourceUtil.getTextDescription(request, "OUTSTANDING") %></b></td> --%>
<!--     <td class="tableHeaderXrules" width="15%">&nbsp;</td> -->
<!--   </tr> -->
<!--   <tr bgcolor="#FFFFFF"> -->
<%--     <td class="textColS" nowrap="nowrap" >&nbsp;<%=MessageResourceUtil.getTextDescription(request, "MEMBER") %> :</td> --%>
<%--     <td class="inputCol"  nowrap="nowrap">&nbsp;<%=ORIGDisplayFormatUtil.displayHTML(currentAccountM.getMemberShortName())%></td> --%>
<%--     <td class="textColS" nowrap="nowrap">&nbsp;<%=MessageResourceUtil.getTextDescription(request, "ACCOUNT_NO1") %> :</td> --%>
<!--     <td class="inputCol" nowrap="nowrap" >&nbsp;-</td> -->
<%--     <td class="textColS"  nowrap="nowrap">&nbsp;<%=MessageResourceUtil.getTextDescription(request, "TYPE_OF_CARD") %> :</td> --%>
<%--     <td class="inputCol" nowrap="nowrap">&nbsp;<%=ORIGDisplayFormatUtil.displayHTML(origXruleUtil.getNCBParamEngDescription("TL","TypeOfCreditCard",currentAccountM.getTypeOfCreditCard()))%></td> --%>
<%--     <td class="textColS"  nowrap="nowrap">&nbsp;<%=MessageResourceUtil.getTextDescription(request, "AMT_OWED") %> :</td> --%>
<%--     <td class="inputCol" align="right" nowrap="nowrap">&nbsp;<%=ORIGDisplayFormatUtil.displayCommaNumber(currentAccountM.getAmtOwed())%></td> --%>
<!--   </tr>   -->
<!--   <tr bgcolor="#FFFFFF"> -->
<!--     <td class="textColS" nowrap="nowrap">&nbsp;Member Type :</td> -->
<!--     <td class="inputCol" nowrap="nowrap">&nbsp;-</td> -->
<!--     <td class="textColS" nowrap="nowrap" >&nbsp;Account Type :</td> -->
<%--     <td class="inputCol" nowrap="nowrap">&nbsp;<%=ORIGDisplayFormatUtil.displayHTML(origXruleUtil.getNCBAccountTypeDescription(currentAccountM.getAccountType()))%></td> --%>
<!--     <td class="textColS" nowrap="nowrap">&nbsp;Ownership indicator :</td> -->
<%--     <td class="inputCol" nowrap="nowrap">&nbsp;<%=ORIGDisplayFormatUtil.displayHTML(origXruleUtil.getNCBParamEngDescription("TL","OwnershipIndicator",currentAccountM.getOwnershipIndicator()))%></td> --%>
<!--     <td class="textColS" nowrap="nowrap">&nbsp;Amount Past Due :</td> -->
<%--     <td class="inputCol" nowrap="nowrap" align="right">&nbsp;<%=ORIGDisplayFormatUtil.displayCommaNumber(currentAccountM.getAmtPastDue())%></td> --%>
<!--   </tr>   -->
<!--   <tr bgcolor="#FFFFFF"> -->
<!--     <td class="textColS" nowrap="nowrap" >&nbsp;Last Report Date :</td> -->
<%--     <td class="inputCol" nowrap="nowrap" >&nbsp;<%=ORIGDisplayFormatUtil.DateToString(ORIGDisplayFormatUtil.parseEngToThaiDate(currentAccountM.getAsOfDate()),"yyyy-MM-dd")%></td> --%>
<!--     <td class="textColS" nowrap="nowrap" >&nbsp;Date Account Opened :</td> -->
<%--     <td class="inputCol" nowrap="nowrap" >&nbsp;<%=ORIGDisplayFormatUtil.DateToString(ORIGDisplayFormatUtil.parseEngToThaiDate(currentAccountM.getDateAccOpened()),"yyyy-MM-dd")%> --%>
<!--     </td> -->
<!--     <td class="textColS" nowrap="nowrap">&nbsp;Number of Co-borrowers :</td> -->
<%--     <td class="inputCol" nowrap="nowrap" align="right">&nbsp;<%=ORIGDisplayFormatUtil.displayInteger(currentAccountM.getNumOfCoBorrower())%></td> --%>
<!--     <td class="textColS" nowrap="nowrap" >&nbsp;Date of Last Payment :</td> -->
<%--     <td class="inputCol" nowrap="nowrap" >&nbsp;<%=ORIGDisplayFormatUtil.DateToString(ORIGDisplayFormatUtil.parseEngToThaiDate(currentAccountM.getDateOfLastPay()),"yyyy-MM-dd")%></td> --%>
<!--   </tr>   -->
<!--   <tr bgcolor="#FFFFFF"> -->
<!--     <td class="textColS" nowrap="nowrap">&nbsp; </td> -->
<!--     <td class="inputCol"  nowrap="nowrap">&nbsp;</td> -->
<!--     <td class="textColS" nowrap="nowrap">&nbsp;Date of Last Debt Restructure :</td> -->
<%--     <td class="inputCol"  nowrap="nowrap">&nbsp;<%=ORIGDisplayFormatUtil.DateToString(ORIGDisplayFormatUtil.parseEngToThaiDate(currentAccountM.getDateOfLastDebtRes()),"yyyy-MM-dd")%></td> --%>
<!--     <td class="textColS" nowrap="nowrap">&nbsp;Credit Limit/Original Loan Amount :</td> -->
<%--     <td class="inputCol" nowrap="nowrap" align="right" >&nbsp;<%=ORIGDisplayFormatUtil.displayCommaNumber(currentAccountM.getCreditLimOriLoanAmt())%></td> --%>
<!--     <td class="textColS" nowrap="nowrap">&nbsp;Default Date :</td> -->
<%--     <td class="inputCol" nowrap="nowrap" >&nbsp;<%=ORIGDisplayFormatUtil.DateToString(ORIGDisplayFormatUtil.parseEngToThaiDate(currentAccountM.getDefaultDate()),"yyyy-MM-dd")%></td> --%>
<!--   </tr>   -->
<!--   <tr bgcolor="#FFFFFF"> -->
<!--     <td class="textColS" nowrap="nowrap" >&nbsp; </td> -->
<!--     <td class="inputCol" nowrap="nowrap" >&nbsp;</td> -->
<!--     <td class="textColS"  nowrap="nowrap">&nbsp;Date Account Closed :</td> -->
<%--     <td class="inputCol" nowrap="nowrap" >&nbsp;<%=ORIGDisplayFormatUtil.DateToString(ORIGDisplayFormatUtil.parseEngToThaiDate(currentAccountM.getDateAccountClosed()),"yyyy-MM-dd")%></td> --%>
<!--     <td class="textColS"  nowrap="nowrap">&nbsp;Installment Amount :</td> -->
<%--     <td class="inputCol" nowrap="nowrap" align="right">&nbsp;<%=ORIGDisplayFormatUtil.displayCommaNumber(currentAccountM.getInstallAmt())%></td> --%>
<!--     <td class="textColS" nowrap="nowrap" >&nbsp;</td> -->
<!--     <td class="inputCol" nowrap="nowrap" >&nbsp;</td> -->
<!--   </tr>   -->
<!--   <tr bgcolor="#FFFFFF"> -->
<!--     <td class="textColS" nowrap="nowrap" >&nbsp; </td> -->
<!--     <td class="inputCol" nowrap="nowrap" >&nbsp;</td> -->
<!--     <td class="textColS" nowrap="nowrap" >&nbsp;</td> -->
<!--     <td class="inputCol" nowrap="nowrap" >&nbsp;</td> -->
<!--     <td class="textColS"  nowrap="nowrap">&nbsp;Installment Number of Payment :</td> -->
<%--     <td class="inputCol" nowrap="nowrap" align="right">&nbsp;<%=ORIGDisplayFormatUtil.displayInteger( (int)currentAccountM.getInstallNOOfPay())%></td> --%>
<!--     <td class="textColS" nowrap="nowrap" >&nbsp;</td> -->
<!--     <td class="inputCol" nowrap="nowrap" >&nbsp;</td> -->
<!--   </tr>   -->
<!--   <tr bgcolor="#FFFFFF"> -->
<!--     <td class="textColS" nowrap="nowrap" >&nbsp; </td> -->
<!--     <td class="inputCol" nowrap="nowrap" >&nbsp;</td> -->
<!--     <td class="textColS" nowrap="nowrap" >&nbsp;</td> -->
<!--     <td class="inputCol" nowrap="nowrap" >&nbsp;</td> -->
<!--     <td class="textColS"  nowrap="nowrap">&nbsp;Percent Payment :</td> -->
<%--     <td class="inputCol" nowrap="nowrap" align="right">&nbsp;<%=ORIGDisplayFormatUtil.displayCommaNumber(currentAccountM.getPercentPay())%></td> --%>
<!--     <td class="textColS" nowrap="nowrap" >&nbsp;</td> -->
<!--     <td class="inputCol" nowrap="nowrap" >&nbsp;</td> -->
<!--   </tr>   -->
<!--   <tr bgcolor="#FFFFFF"> -->
<!--     <td class="textColS" nowrap="nowrap" >&nbsp; </td> -->
<!--     <td class="inputCol" nowrap="nowrap" >&nbsp;</td> -->
<!--     <td class="textColS" nowrap="nowrap" >&nbsp;</td> -->
<!--     <td class="inputCol" nowrap="nowrap" >&nbsp;</td> -->
<!--     <td class="textColS"  nowrap="nowrap">&nbsp;Unit Make :</td> -->
<%--     <td class="inputCol" nowrap="nowrap" >&nbsp;<%=ORIGDisplayFormatUtil.displayHTML(currentAccountM.getUnitMake())%></td> --%>
<!--     <td class="textColS" nowrap="nowrap" >&nbsp;</td> -->
<!--     <td class="inputCol" nowrap="nowrap" >&nbsp;</td> -->
<!--   </tr>   -->
<!--   <tr bgcolor="#FFFFFF"> -->
<!--     <td class="textColS" nowrap="nowrap" >&nbsp; </td> -->
<!--     <td class="inputCol" nowrap="nowrap">&nbsp;</td> -->
<!--     <td class="textColS" nowrap="nowrap">&nbsp;</td> -->
<!--     <td class="inputCol" nowrap="nowrap">&nbsp;</td> -->
<!--     <td class="textColS"  nowrap="nowrap">&nbsp;Unit Model :</td> -->
<%--     <td class="inputCol" nowrap="nowrap">&nbsp;<%=ORIGDisplayFormatUtil.displayHTML(currentAccountM.getUnitModel())%></td> --%>
<!--     <td class="textColS" nowrap="nowrap">&nbsp;</td> -->
<!--     <td class="inputCol" nowrap="nowrap">&nbsp;</td> -->
<!--   </tr>   -->
<!--   <tr bgcolor="#FFFFFF"> -->
<!--     <td class="textColS" nowrap="nowrap" >&nbsp; </td> -->
<!--     <td class="inputCol" nowrap="nowrap" >&nbsp;</td> -->
<!--     <td class="textColS" nowrap="nowrap" >&nbsp;</td> -->
<!--     <td class="inputCol" nowrap="nowrap" >&nbsp;</td> -->
<!--     <td class="textColS" rowspan="3" nowrap="nowrap">&nbsp;Collateral :</td> -->
<%--     <td class="inputCol" nowrap="nowrap" >&nbsp;  <%=ORIGDisplayFormatUtil.displayHTML(origXruleUtil.getNCBParamEngDescription("TL","Colatteral",currentAccountM.getCollateral1()))%></td> --%>
<!--     <td class="textColS" nowrap="nowrap" >&nbsp;</td> -->
<!--     <td class="inputCol" nowrap="nowrap" >&nbsp;</td> -->
<!--   </tr>   -->
<!--    <tr bgcolor="#FFFFFF"> -->
<!--     <td class="textColS" nowrap="nowrap">&nbsp; </td> -->
<!--     <td class="inputCol" nowrap="nowrap">&nbsp;</td> -->
<!--     <td class="textColS" nowrap="nowrap">&nbsp;</td>    -->
<!--      <td class="inputCol" nowrap="nowrap">&nbsp;</td> -->
<%--     <td class="inputCol" nowrap="nowrap">&nbsp;<%=ORIGDisplayFormatUtil.displayHTML(origXruleUtil.getNCBParamEngDescription("TL","Colatteral",currentAccountM.getCollateral2()))%></td> --%>
<!--     <td class="textColS" nowrap="nowrap">&nbsp;</td> -->
<!--     <td class="inputCol" nowrap="nowrap">&nbsp;</td> -->
<!--   </tr>   -->
<!--   <tr bgcolor="#FFFFFF"> -->
<!--     <td class="textColS" nowrap="nowrap">&nbsp; </td> -->
<!--     <td class="inputCol" nowrap="nowrap">&nbsp;</td> -->
<!--     <td class="textColS" nowrap="nowrap">&nbsp;</td>  -->
<!--     <td class="inputCol" nowrap="nowrap">&nbsp;</td> -->
<%--     <td class="inputCol" nowrap="nowrap">&nbsp; <%=ORIGDisplayFormatUtil.displayHTML(origXruleUtil.getNCBParamEngDescription("TL","Colatteral",currentAccountM.getCollateral3()))%></td> --%>
<!--     <td class="textColS" nowrap="nowrap">&nbsp;</td> -->
<!--     <td class="inputCol" nowrap="nowrap">&nbsp;</td> -->
<!--   </tr>   -->
<%--   <% --%>
//   String displayMode=ORIGDisplayFormatUtil.DISPLAY_MODE_VIEW;
//   BigDecimal  ncbAdjustAmt=null;
//   String strNCBAdjust="";
//    if(canAdjustNCB){            
//       displayMode=ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
//     if( xruleNCBAdjust.getNcbInstallmentAdjustAmount() !=null){
//       ncbAdjustAmt=xruleNCBAdjust.getNcbInstallmentAdjustAmount();
//       strNCBAdjust=ORIGDisplayFormatUtil.displayCommaNumber(ncbAdjustAmt);
//     }
//   } 
<%--    %> --%>
<%--    <%  %> --%>
<!--   <tr bgcolor="#FFFFFF"> -->
<!--     <td class="textColS" nowrap="nowrap" >&nbsp; </td> -->
<!--     <td class="inputCol" nowrap="nowrap">&nbsp;</td> -->
<!--     <td class="textColS" nowrap="nowrap">&nbsp;</td> -->
<!--     <td class="inputCol" nowrap="nowrap">&nbsp;</td> -->
<!--     <td class="textColS"  nowrap="nowrap">&nbsp; Adjust Installment :</td> -->
<%--     <td class="inputCol" nowrap="nowrap">&nbsp;=ORIGDisplayFormatUtil.displayCommaNumber(currentAccountM.getInstallAmt()) --%>
    
<%--     <%=ORIGDisplayFormatUtil.displayInputTagScriptAction(strNCBAdjust,displayMode,"","ncbAdjust","textboxCurrency","onblur=\"javascript:addComma(this);addDecimalFormat(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\" ","20") %> --%>
<%--     &nbsp;<%if(canAdjustNCB) {%><INPUT class="button_text" type="button" value="<%=MessageResourceUtil.getTextDescription(request, "SAVE") %>" onClick="saveNCBInstallmentAdjust()"> --%>
<!--     <INPUT class="button_text" type="button" value="Clear Adjust" onClick="clearNCBInstallmentAdjust()">     -->
<%--     <%}%>	 --%>
<%--     <input type="hidden" name="ncbAccountSegmentCode" value="<%=currentAccountM.getSegmentValue()%>"> --%>
<%--      <input type="hidden" name="ncbAccountGroupSeq" value="<%=currentAccountM.getGroupSeq()%>"> --%>
<!--     </td> -->
<!--     <td class="textColS" nowrap="nowrap">&nbsp;</td> -->
<!--     <td class="inputCol" nowrap="nowrap">&nbsp;</td> -->
<!--   </tr>   -->
  
<!-- </table> -->
<!-- <table width="100%" border="0" cellpadding="0" cellspacing="0"> -->
<!-- <tr bgcolor="#3399cc" height="18"> -->
<!-- 		<td ><font color="white"><b>&nbsp;Account Payment History Detail</b></font></td> -->
<!-- 	</tr> -->
<!-- </table>  -->
<!-- <table  width="100%" border="0" cellpadding="1" cellspacing="1" bgcolor="#333333">   -->
<!--   <tr> -->
<!--     <td bgcolor="#FFFFFF" class="tableHeaderXrules"   ><strong>&nbsp;NO </strong></td> -->
<%--     <td bgcolor="#FFFFFF" class="tableHeaderXrules"  ><strong>&nbsp;<%=msgUtil.getTextDescription(request, "AS_OF_DATE") %></strong></td> --%>
<%--     <td bgcolor="#FFFFFF" class="tableHeaderXrules" ><strong>&nbsp;<%=msgUtil.getTextDescription(request, "OVER_DUE_MONTH_PAYMENT") %></strong></td>    --%>
<%--     <td bgcolor="#FFFFFF" class="tableHeaderXrules"  ><strong>&nbsp;<%=msgUtil.getTextDescription(request, "LOAN_AMOUNT") %></strong></td> --%>
<%--     <td bgcolor="#FFFFFF" class="tableHeaderXrules"  ><strong>&nbsp;<%=msgUtil.getTextDescription(request, "OS_BALANCE") %></strong></td> --%>
<%--     <td bgcolor="#FFFFFF" class="tableHeaderXrules"  ><strong>&nbsp;<%=msgUtil.getTextDescription(request, "CREDIT_LIMIT_NCB") %></strong></td>  --%>
<%--     <td bgcolor="#FFFFFF" class="tableHeaderXrules"  ><strong>&nbsp;<%=msgUtil.getTextDescription(request, "CREDIT_USE") %></strong></td>     --%>
<!--   </tr> -->
<%--   <%    --%>
<!-- //    for(int i=0;i<vAccountPaymentHistoryDetail.size();i++){ -->
<!-- //         HSRespM  historyRespM=(HSRespM)vAccountPaymentHistoryDetail.get(i);    -->
<!-- //         String bgColor=(String)XRulesConstant.hNCBColorDisplay.get( XRulesUtil.getOverDueMounthColor(historyRespM.getOverDueMonths()));      -->
<!-- //         if(OrigConstant.NCBcolor.ORANGE.equals(asOfDateColor)){ -->
<!-- //          bgColor=(String)XRulesConstant.hNCBColorDisplay.get(asOfDateColor); -->
<!-- //         } -->
<!-- //         double  loanAmt=0d; -->
<!-- //         double  creditLimit=0d; -->
<!-- //         double  creditUse=0d; -->
<!-- //         double  osBalance=0d; -->
<!-- //          if(OrigConstant.AccountType.CreditCard.equalsIgnoreCase(ncbAccountType) -->
<!-- //          ||OrigConstant.AccountType.OverDraft.equalsIgnoreCase(ncbAccountType) -->
<!-- //          ||OrigConstant.AccountType.Revolving.equalsIgnoreCase(ncbAccountType) -->
<!-- //          ){ -->
<!-- //          creditLimit=historyRespM.getCreditLimOriLoanAmt(); -->
<!-- //          creditUse=historyRespM.getAmtOwed(); -->
<!-- //          }else{ -->
<!-- //          loanAmt=historyRespM.getCreditLimOriLoanAmt(); -->
<!-- //          osBalance=historyRespM.getAmtOwed(); -->
<!-- //          } -->
<%--    %> --%>
<!--   <tr> -->
<%--     <td bgcolor="<%=bgColor%>" nowrap>&nbsp;<%=ORIGDisplayFormatUtil.displayHTML(String.valueOf(i+1)+".")%>&nbsp;</td>          --%>
<%--     <td bgcolor="<%=bgColor%>" nowrap>&nbsp;<%=ORIGDisplayFormatUtil.displayHTML(ORIGDisplayFormatUtil.datetoString(ORIGDisplayFormatUtil.parseEngToThaiDate(historyRespM.getAsOfDate())))%>&nbsp;</td> --%>
<%--     <td bgcolor="<%=bgColor%>" align="right" nowrap >&nbsp;<%=ORIGDisplayFormatUtil.displayHTML(origXruleUtil.getOverDueDescription(historyRespM.getOverDueMonths()))%>&nbsp;</td> --%>
<%--     <td bgcolor="<%=bgColor%>" align="right" nowrap >&nbsp;<%=ORIGDisplayFormatUtil.displayCommaNumber(loanAmt)%>&nbsp;</td> --%>
<%--     <td bgcolor="<%=bgColor%>" align="right" nowrap>&nbsp;<%=ORIGDisplayFormatUtil.displayCommaNumber(osBalance)%>&nbsp;</td>  --%>
<%--     <td bgcolor="<%=bgColor%>" align="right" nowrap>&nbsp;<%=ORIGDisplayFormatUtil.displayCommaNumber(creditLimit)%>&nbsp;</td> --%>
<%--     <td bgcolor="<%=bgColor%>" align="right" nowrap>&nbsp;<%=ORIGDisplayFormatUtil.displayCommaNumber(creditUse)%>&nbsp;</td>    --%>
<!--   </tr> -->
<%--   <% }%> --%>
<!--   <tr height="30" > -->
<!-- 		<td colspan="7" align="center" bgcolor="#FFFFFF">&nbsp; -->
<%-- 				<INPUT class="button_text" type="button" value="<%=MessageResourceUtil.getTextDescription(request, "CLOSE") %>" onClick="window.close()">	</td> --%>
<!--   </tr> -->
<!-- </table>  -->
<%-- <%--  --%>
<%-- for(int i=0;i<iasObject.size();i++){ --%>
<%-- com.eaf.ias.shared.model.ObjectM  obj=(com.eaf.ias.shared.model.ObjectM)iasObject.get(i); --%>
<%--   out.print("name="+obj.getObjectName()+" Type"+ obj.getObjectType()); --%>
<%--   //if(obj.getObjectAccess()!=null){ --%>
<%--   //   out.print("Access size->"+obj.getObjectAccess().size());  --%>
<%--   //   for(int j=0;j<obj.getObjectAccess().size();j++){          --%>
<%--   //   } --%>
<%--   //} --%>
<%--   out.print("<br>"); --%>
<%-- } --%>

<%-- --%> --%>
<%-- <%	//set current screen to main Form --%>
// 	com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager =
// 	(com.eaf.j2ee.pattern.control.ScreenFlowManager) request.getSession(true).getAttribute(
// 		"screenFlowManager");
// 	screenFlowManager.setCurrentScreen("MAIN_APPFORM");
<%-- %> --%>
