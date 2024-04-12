<%@ page contentType="text/html;charset=UTF-8"%>

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

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>
<jsp:useBean id="VALUE_LIST" scope="session" class="com.eaf.orig.shared.model.ValueListM" />
<html>
<HEAD>
<% 
	ApplicationDataM applicationDataM = ORIGForm.getAppForm();
	if(applicationDataM == null){
		applicationDataM = new ApplicationDataM();
	}
	ORIGFormUtil formUtil = new ORIGFormUtil();
	ORIGCacheUtil cacheUtil = ORIGCacheUtil.getInstance();
	String searchType = (String) request.getSession().getAttribute("searchType");
	String displayMode = "EDIT";
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
  }else{
  searchConsentDataM=new SearchConsentDataM();
  }
  String mktDesc = cacheUtil.getORIGMasterDisplayNameDataM("LoanOfficer",cmrCode);    
  if("".equals(consentOption)){
    consentOption="All";
  }
%>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK href="../../theme/Master.css" rel="stylesheet" type="text/css">

<TITLE></TITLE>
</HEAD>
<BODY>

<table width="100%" border=0 cellpadding="0" cellspacing="0" align="left" onkeydown="testKeyDown()">
	<tr><td height="20">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" align="left" bgcolor="#FFFFFF">
			<tr><td colspan="3" class="sidebar8">
			<table cellSpacing="0" cellPadding="0" width="100%" border="0"> 
		    	<tr><td class="sidebar9">
					<table cellspacing=0 cellpadding=0 width="100%" align=center border=0>	     		
			 			<tr> <td  height="10">
			 				<table width="100%" border="0" cellspacing="0" cellpadding="0">
                          	<tr><td class="text-header-detail">&nbsp; </td>
                            <td width="330">
                            	<table width="50" border="0" align="right" cellpadding="2" cellspacing="0">
                              		<tr height="30"><td>
	                            		<table width="100%">
											<tr align="right">
												<td><input name="search" type="button" class="button" value="search" onclick="searchReport()"></td>
											</tr>
										</table>										
                                    </td></tr>
                                </table>
                            </td></tr>	
                            </table>
                        </td></tr>
						<tr class="sidebar10"> <td align="center">
							<table cellpadding="0" cellspacing="0" width="100%" align="center">	
							<tr>
								<td class="textColS" width="15%">CMR Code-Name</td>
								<TD class="inputCol" width="25%"><%=ORIGDisplayFormatUtil.displayPopUpTagScriptAction(cmrCode,displayMode,"5","fcmr_code","25","mkt_code_desc1","textbox","","30","...","button_text","LoadRepfCmrCode",mktDesc,"LoanOfficer") %></TD>
								<TD class="textColS" width="15%">&nbsp;</TD>
								<TD class="inputCol" width="45%">&nbsp;</TD>
							</TR>
							<TR>
								<TD class="textColS">From NCB Submit Date</TD>
								<TD class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagJsDate("appFormName",bookingDateFrom,displayMode,"","f_book_date","textbox","right","onblur=\"javascript:checkDate('f_book_date')\" onkeydown=\"javascript:DateFormat(this,this.value,event,false,'3')\" maxlength =\"10\"")%></TD>
								<TD class="textColS">To	NCB Submit Date</TD>
								<TD class="inputCol" align="left"><%=ORIGDisplayFormatUtil.displayInputTagJsDate("appFormName",bookingDateTo,displayMode,"","t_book_date","textbox","right","onblur=\"javascript:checkDate('t_book_date')\" onkeydown=\"javascript:DateFormat(this,this.value,event,false,'3')\" maxlength =\"10\"")%></TD>
							</TR>
							<TR>
								<TD class="textColS">Citizen ID</td>
								<TD class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction( searchConsentDataM.getIdentificationNo(),displayMode,"","identificationNo","textbox","  style=\"width:100;\" ","15") %></TD>
								<TD class="inputCol" colspan="2">&nbsp;</TD>
							</TR>
							<TR>
								<TD class="textColS">&nbsp;</TD>
								<TD class="inputCol"><%=ORIGDisplayFormatUtil.displayRadioTag("All",displayMode,"consent_option",consentOption,"All","All") %> All Enquiry</TD>
								<TD class="inputCol" colspan="2">&nbsp;</TD>
							</TR>
							<TR>
								<TD class="textColS">&nbsp;</TD>
								<TD class="inputCol"><%=ORIGDisplayFormatUtil.displayRadioTag("N",displayMode ,"consent_option",consentOption,"N","N") %>Unreceived Consent</TD>
								<TD class="inputCol" colspan="2">&nbsp;</TD>
							</TR>
							<TR>
								<TD class="textColS">&nbsp;</TD>
								<TD class="inputCol"><%=ORIGDisplayFormatUtil.displayRadioTag("Y",displayMode,"consent_option",consentOption,"Y","Y") %>Received Consent</TD>
								<TD class="inputCol" colspan="2">&nbsp;</TD>
							</TR>
							</TABLE>
						</td></tr>
					</table>
				</td></tr>
			</table>
		</td></tr>
	</table>
	</td></tr>
</table>
					
<%if(searchConsentDataM!=null){ %>
<table cellpadding="0" cellspacing="0" width="100%" align="center" bgcolor="#FFFFFF">
	<tr><td>
		<table class="gumayframe3" cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
			<tr><td class="TableHeader">
				<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
					<tr>
 					    <td class="Bigtodotext3" width="100" nowrap>Application No</td>
 					    <td class="Bigtodotext3" width="100" nowrap>Tracking Code</td>
 					    <td class="Bigtodotext3" width="100" nowrap>Applicant Type</td>
						<td class="Bigtodotext3" width="100" nowrap>Thai First Name</td>
						<td class="Bigtodotext3" width="100" nowrap >Thai Last Name</td>
						<td class="Bigtodotext3" width="100" nowrap>Citizen ID</td>
						<td class="Bigtodotext3" width="100" nowrap>Submit By</td>
						<td class="Bigtodotext3" width="100" nowrap>Submit Date</td>
						<td class="Bigtodotext3" width="100" nowrap>Marketing Code (PreScore)</td>
						<td class="Bigtodotext3" width="100" nowrap>Marketing Code (Loan)</td>
						<td class="Bigtodotext3" width="100" nowrap>Lastest Update User</td>
						<td class="Bigtodotext3" width="100" nowrap>Lastest Update Date Time</td>
						<td class="Bigtodotext3" width="100" nowrap>Receive Consent</td>
						<td class="Bigtodotext3" width="100" nowrap>Remark</td>						
					</tr>
				</table> 
			</td></tr>
					
			<%
				Vector valueList = VALUE_LIST.getResult(); 
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
				<tr><td align="center" class="gumaygrey2">
					<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
						<tr onmouseover="this.style.cursor='hand' ;this.style.background='#D7D6D7'" onmouseout=" this.style.background='#FFFFFF'"> 
							<td class="jobopening2" width="100"><%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(12)) %></td>
							<td class="jobopening2" width="100"><%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(8)) %></td>
							<td class="jobopening2" width="100"><%=applicant%></td>
							<td class="jobopening2" width="100"><%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(1)) %></td> 
							<td class="jobopening2" width="100"><%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(2)) %></td>
							<td class="jobopening2" width="100"><%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(3)) %></td>
							<td class="jobopening2" width="100"><%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(9)) %></td>
							<td class="jobopening2" width="100"><%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(13)) %></td>									
							<td class="jobopening2" width="100"><%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(10)) %></td>
							<td class="jobopening2" width="100"><%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(11)) %></td>									
							<td class="jobopening2" width="100"><%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(4)) %></td>
							<td class="jobopening2" width="100"><%=ORIGDisplayFormatUtil.displayHTML( elementList.elementAt(5))%></td>	 
							<td class="jobopening2" width="100" align="center"><%=ORIGDisplayFormatUtil.displayCheckBox(consentFlag ,chkConsentName,OrigConstant.ORIG_FLAG_Y,consentDisable) %></td> 
							<td class="jobopening2" width="100"><%if(OrigConstant.ORIG_FLAG_Y.equals(consentFlag)){
								 out.print(ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(7)));
								   }else{
								    out.print(ORIGDisplayFormatUtil.displayInputTag("",ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT,"25",remarkConsentName,"textbox",""));
								   } 
								   %><td> 
						</tr>
					</table> 
				</td></tr>						
				<% 
						}
					}else{
				%>
				<tr><td align="center" class="gumaygrey2">
					<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
						<tr>
				  			<td class="jobopening2" colspan="15" align="center">Results Not Found.</td>
				  		</tr>
					</table> 
				</td></tr>
				<%
					}
				%>
				<tr>
					<td class="gumaygrey2" align="right" height="50">
						<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
							<tr><td>
                                <INPUT type="button" name="btnSave" value="Save" onClick="saveConsent()" class="button_text">
                            </td><td>
                                <INPUT type="button" name="btnCancel" value="Cancel" onClick="CancelSave()" class="button_text">
                            </td><td>
                                <INPUT type="button" name="btnPrint" value="Export To xls" onClick="printReport()" class="button_text">
                            </td></tr>
                         </table>
					</td>
				</tr>
				<tr>
					<td align="right" height="50">
						<div align="center"><span class="textColS">
							<jsp:include page="../appform/valuelist.jsp" flush="true" />
						</span></div>
					</td>
				</tr>
			</table>
		</td></tr>
	</table>
								
<%}%>				
<!-- initial form param -->
<!-- form action="FrontController" method="post" name="frmrefresh">
	<input type="hidden" name="action" value="RefreshReportPage">
	<input type="hidden" name="P_CHANNEL" value=""> 
    <input type="hidden" name="P_PRODUCT" value=""> 
    <input type="hidden" name="P_ORG_ID" value=""> 
    <input type="hidden" name="page" value="RPT_CONSENT">
</form-->

<script language="javaScript" type=text/JavaScript>


function searchReport(){
//alert(appFormName.action);	
	//verifyForm();
	var appForm=document.appFormName;
	appForm.action.value="SearchConsentReport";
	appFormName.handleForm.value = "N";
    appFormName.fromSearch.value = "Y";
	appForm.submit();
	//searhConsentReport
	/*reportForm.target='reportWindow';
	reportForm.foffice_code.value = appFormName.foffice_code.value;
	reportForm.f_book_date.value = appFormName.f_book_date.value;
	reportForm.t_book_date.value = appFormName.t_book_date.value;
	reportForm.submit();*/
}

var reportWindow;
//-- check fill in form for * --
function verifyForm()
{

	var sourceFrm = document.appFormName;
	if (sourceFrm.f_book_date == null || sourceFrm.f_book_date.value == '' ) 
	{
		alert('Please Select Booking Date From !');
		sourceFrm.f_book_date.focus();
	} 
	else if(sourceFrm.t_book_date == null || sourceFrm.t_book_date.value == '' ) 
	{
		alert('Please Select Booking Date To !');
		sourceFrm.t_book_date.focus();	
	}
	else if(sourceFrm.t_book_date != null && sourceFrm.f_book_date != null)
	{
		var nStart = date2Number(sourceFrm.f_book_date.value); 
		var nEnd = date2Number(sourceFrm.t_book_date.value);
		if (nEnd<nStart && nEnd!=0 && nStart!=0)
		{
			alert('Please Select Booked Date To Equal or Greater Than Booked Date From !');
		}
		else 
		{
			var url = "";
 			var feat = "height=650px,width=850px,top=10px,left=50px,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars=yes";
			window.open(url, "reportWindow", feat);	
			return true;
		}
	}
	return false;
}
function saveConsent(){
	var appForm=document.appFormName;
	appForm.action.value="SaveConsentReport";
	appFormName.handleForm.value = "N";
    appFormName.fromSearch.value = "Y";
	appForm.submit();

}
function CancelSave(){
   window.location='FrontController?action=Menu_Show&handleForm=N&menuSequence=703'
}
function printReport(){
  //window.print();
  	var appForm=document.appFormName;
	var oldAction=appForm.action; 
    var param="";
    
    var aDialog =window.open("orig/report/rep_consent_print.jsp","excelExport");//,'width=100,height=100,status=0,toolbar=0');	
   // aDialog.close();
    //var popup=window.open("","execlExport");	
    //var oldTarget=appForm.target;
    //appForm.target="execlExport";
	//appForm.submit();		   
	//appForm.target=oldTarget;	
	//appForm.action=oldAction;
}
</script>
<script language="JavaScript" type="text/JavaScript">
/*function searchClientGroup(){
	appFormName.action.value="SearchClientGroup";
	appFormName.handleForm.value = "N";
	appFormName.submit();	
}

function gotoAppForm(clientGroup){
	appFormName.action.value="LoadCarDetail";
	appFormName.selectClientGroup.value=clientGroup;
	appFormName.handleForm.value = "N";
	appFormName.submit();	
}*/

function clickPageList(atPage){
	var form=document.appFormName;
	form.atPage.value = atPage;
	form.action.value = "ValueListWebAction";
	form.handleForm.value = "N";
	form.submit();
}
function clickItemPerPageList(atItem){
   var form=document.appFormName;
	form.itemsPerPage.value = atItem;
	form.action.value = "ValueListWebAction";
	form.handleForm.value = "N";
	form.submit();  
}
</script>	  		
<input type="hidden" name="fromSearch" value="N"> 
</BODY>
</html>
