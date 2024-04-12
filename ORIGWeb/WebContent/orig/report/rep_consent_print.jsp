<%@page import="com.eaf.orig.ulo.pl.service.PLORIGEJBService"%>
<%
//response.setHeader("Pragma", "No-cache");
//response.setHeader("Cache-Control", "no-cache");  
response.setDateHeader("Expires", 0);
response.setContentType("application/vnd.ms-excel");
response.setHeader("Content-disposition","inline;ConsentReport.xls");
%>
<%	//set current screen to main Form
	com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager =
	(com.eaf.j2ee.pattern.control.ScreenFlowManager) request.getSession(true).getAttribute(
		"screenFlowManager");
	screenFlowManager.setCurrentScreen("MAIN_APPFORM");
%>
<%@ page language="java" contentType="application/vnd.ms-excel; charset=UTF-8" %> 
<%--@ page contentType="text/html;charset=UTF-8"--%>
<%@ page import="java.util.*" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.shared.model.LoanDataM" %>
<%@ page import="com.eaf.orig.shared.model.ApplicationDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
<%@ page import="com.eaf.orig.shared.model.SearchConsentDataM"%>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %> 
<%@ page import="com.eaf.orig.shared.model.ValueListM" %> 
<%-- <%@ page import="com.eaf.orig.shared.dao.ORIGDAOFactory" %>  --%>
<%-- <%@ page import="com.eaf.orig.shared.dao.ValueListDAO" %>  --%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>
<jsp:useBean id="VALUE_LIST" scope="session" class="com.eaf.orig.shared.model.ValueListM" />
<html>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<% 
    System.out.println("=============XLS Consent Report============");
	ApplicationDataM applicationDataM = ORIGForm.getAppForm();
	if(applicationDataM == null){
		applicationDataM = new ApplicationDataM();
	}
	ORIGFormUtil formUtil = new ORIGFormUtil();
	ORIGCacheUtil cacheUtil = ORIGCacheUtil.getInstance();
	String searchType = (String) request.getSession().getAttribute("searchType");
	String displayMode = "VIEW";
	MessageResourceUtil msgUtil = MessageResourceUtil.getInstance();	
	ORIGUtility utility = new ORIGUtility();
	Vector officeVect = utility.loadCacheByName("OfficeInformation");
    SearchConsentDataM   searchConsentDataM=(SearchConsentDataM)request.getSession().getAttribute("SEARCH_CONSENT_DATAM");
    //remove session
    //request.getSession().removeAttribute("SEARCH_CONSENT_DATAM");
	LoanDataM loanDataM = (LoanDataM) request.getSession().getAttribute("POPUP_DATA");
	if(loanDataM == null){
		loanDataM = new LoanDataM();
	}
  //String displayMode=ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
  String consentOption="";
  String cmrCode="";
  String bookingDateFrom="";
  String bookingDateTo="";
  if(searchConsentDataM!=null){
  cmrCode=searchConsentDataM.getCmrCodeName();
  consentOption=searchConsentDataM.getConsentOption();
  System.out.print("Date From"+searchConsentDataM.getBookingDateFrom());
  System.out.print("Date To"+searchConsentDataM.getBookingDateTo());
  bookingDateFrom=ORIGDisplayFormatUtil.DateToString(ORIGDisplayFormatUtil.parseEngToThaiDate(searchConsentDataM.getBookingDateFrom()),"dd/MM/yyyy");
  bookingDateTo=ORIGDisplayFormatUtil.DateToString(ORIGDisplayFormatUtil.parseEngToThaiDate(searchConsentDataM.getBookingDateTo()),"dd/MM/yyyy");
  }
   System.out.println("=============XLS Consent Report============");
  String mktDesc = cacheUtil.getORIGMasterDisplayNameDataM("LoanOfficer",cmrCode);    
  if("".equals(consentOption)){
    consentOption="All";
  }
  ValueListM valueListReport=new ValueListM();
  valueListReport.setSQL(VALUE_LIST.getSQL());    
  valueListReport.setAtPage(0);
  valueListReport.setItemsPerPage(10000);
  valueListReport.getSqlCriteria().putAll(VALUE_LIST.getSqlCriteria());
//    ValueListDAO valueListDAO = ORIGDAOFactory.getValueListDAO();
     	if(valueListReport.getSqlCriteria() != null && !valueListReport.getSqlCriteria().isEmpty()){
// 				valueListReport = valueListDAO.getResult2(valueListReport);
				//System.out.println("num of result :" + valueListM.getResult().size()); //pae debug
			valueListReport	= PLORIGEJBService.getORIGDAOUtilLocal().getResult2(valueListReport);
		}else{
	// 		valueListReport = valueListDAO.getResult(valueListReport);
			valueListReport	= PLORIGEJBService.getORIGDAOUtilLocal().getResult(valueListReport);
		}
%> 
<TITLE></TITLE> 
</HEAD>
<BODY>
<TABLE cellspacing="1" cellpadding="0" width="100%" border="0">
	<TR>
		<TD   width="22%"></TD>
		<TD   width="99"></TD>
		<TD   width="15%"></TD>
		<TD   width="50%"></TD>
	</TR>
	<TR>
		<td  width="169"><FONT color="black"><B>CMR Code Name</B></FONT></td>
		<TD  width="214" colspan="2"><%=cmrCode%>  <%=mktDesc%> 
		<TD  width="380"></TD>
	</TR>
	<TR>
		<TD   width="169"><FONT color="black"><B>From NCB Submit
		Date</B></FONT></TD>
		<TD   align="left" width="99"><%=bookingDateFrom%> </TD>
		<TD  align="left" width="115" ><FONT color="black"><B>To	NCB Submit Date</B></FONT></TD>
		<TD   align="left" width="380"><%=bookingDateTo%></TD>
	</TR>
	<TR>
		<TD   width="22%">&nbsp;</TD>
		<TD   width="99">&nbsp;</TD>
		<TD   width="15%">&nbsp;</TD>
		<TD   width="50%">&nbsp;</TD>
	</TR>
	<TR>
		<TD   width="22%">&nbsp;</TD>
		<TD   width="99">&nbsp;</TD>
		<TD   width="15%">&nbsp;</TD>
		<TD   width="50%">&nbsp;</TD>
	</TR>
</TABLE>
<%if(searchConsentDataM!=null){ %>
<table cellSpacing=0 cellPadding=0 width="100%" border="1" >
					<tr bgcolor="#e0e0e0">
 					    <td   width="100" nowrap>&nbsp;<b>Application No</b></td>
 					    <td   width="100" nowrap>&nbsp;Tracking Code</td>
 					    <td   width="100" nowrap>&nbsp;Applicant Type</td>
						<td   width="100" nowrap><b>Thai First Name</b></td>
						<td   width="100" nowrap ><b>Thai Last Name</b></td>
						<td  width="100" nowrap><b>Citizen ID</b></td> 
						<td  width="100" nowrap><b>Submit By</b></td>
						<td  width="100" ><b>Submit Date</b></td>
						<td   width="100" nowrap><b>Marketing Code (PreScore)</b></td>
						<td   width="100" nowrap><b>Marketing Code (Loan)</b></td>
						<td  width="100" nowrap><b>Lastest Update User</b></td>
						<td   width="100" nowrap><b>Lastest Update Date Time</b></td>
						<td   width="100" nowrap><b>Receive Consent</b></td>
						<td   width="100" nowrap><b>Remark</b></td>				 
					</tr>
					<%
						Vector valueList = valueListReport.getResult();//VALUE_LIST.getResult(); 
						if(valueList != null && valueList.size() > 1){

							for(int i=1;i<valueList.size();i++){
								Vector elementList = (Vector)valueList.get(i);
								String consentFlag=(String)elementList.elementAt(6);
								String consentDisable="";
								if(OrigConstant.ORIG_FLAG_Y.equals(consentFlag)){
								consentDisable=" disabled";
								}
								String trackingCode=(String)elementList.elementAt(8);
								String chkConsentName="chk_"+trackingCode;
								String remarkConsentName="remark_"+trackingCode;
								String applicantType=(String)elementList.elementAt(14);
								String coborrowerType=(String)elementList.elementAt(15);
								String applicant="";
								if(OrigConstant.PERSONAL_TYPE_APPLICANT.equals(applicantType)){
								 applicant="Applicant";
								}else if(OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(applicantType)){
								   if(OrigConstant.COBORROWER_FLAG_ACTIVE.equals(coborrowerType)||OrigConstant.COBORROWER_FLAG_UN_ACTIVE.equals(coborrowerType)){
								    applicant="Co-Borrower";
								   }else{
								   applicant="Guarantor";
								   }								   
								}
								
					%>
								<tr> 
									<td>&nbsp;<%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(12)) %></td>
									<td>&nbsp;<%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(8)) %></td>
									<td>&nbsp;<%=applicant%></td>
									<td>&nbsp;<%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(1)) %></td> 
									<td>&nbsp;<%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(2)) %></td>
									<td>&nbsp;<%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(3)) %></td>
									<td>&nbsp;<%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(9)) %></td>
									<td>&nbsp;<%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(13)) %></td>
									<td>&nbsp;<%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(10)) %></td>
									<td>&nbsp;<%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(11)) %></td>									
									<td>&nbsp;<%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(4)) %></td>
									<td>&nbsp;<%=ORIGDisplayFormatUtil.displayHTML( elementList.elementAt(5))%></td>	 
									<td align="center"><%=consentFlag%></td>
 									<td width="100">&nbsp;<%if(OrigConstant.ORIG_FLAG_Y.equals(consentFlag)){
 										 out.print(ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(7)));
  									   } 
  									   %></td> 
								</tr>
					<% 
							}
						}else{
					%>
							<tr>
								<td colspan="12" align="center"><font color="#FF0000"><b>Results Not Found.</b></font></td>
							</tr>
					<%
						}
					%>						 
				</table>
								
<%}%>				
 
</BODY>
</html>
