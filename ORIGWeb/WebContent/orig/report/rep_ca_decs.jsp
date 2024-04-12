<%@ page contentType="text/html;charset=UTF-8"%>

<%@ page import="java.util.*" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.shared.model.LoanDataM" %>
<%@ page import="com.eaf.orig.shared.model.ApplicationDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>



<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>
<html>
<HEAD>
<% 
	ApplicationDataM applicationDataM = ORIGForm.getAppForm();
	if(applicationDataM == null){
		applicationDataM = new ApplicationDataM();
	}
	ORIGFormUtil formUtil = new ORIGFormUtil();
	String searchType = (String) request.getSession().getAttribute("searchType");
	String displayMode = "EDIT";
	
	ORIGUtility utility = new ORIGUtility();
	Vector officeVect = utility.loadCacheByName("OfficeInformation");

		
	LoanDataM loanDataM = (LoanDataM) request.getSession().getAttribute("POPUP_DATA");
	if(loanDataM == null){
		loanDataM = new LoanDataM();
	}

%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM Software Development Platform">
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
												<td><input name="search" type="button" class="button" value="search" onclick="searchReport();"></td>
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
								<td class="textColS" width="142">FromOffice</td>
								<TD class="inputCol" width="552"><%=ORIGDisplayFormatUtil.displayPopUpTagScriptAction("01",displayMode,"5","foffice_code","25","office_code_desc1","textbox","","30","...","button_text","LoadRepfOfficeCode","สำนักงานใหญ่","OfficeInformation") %></TD>
							</TR>
							<TR>
								<TD class="textColS">To Office</TD>
								<TD class="inputCol"><%=ORIGDisplayFormatUtil.displayPopUpTagScriptAction(loanDataM.getOfficeCode(),displayMode,"5","toffice_code","25","office_code_desc2","textbox","","30","...","button_text","LoadReptOfficeCode","","OfficeInformation") %></TD>
							</TR>
							<TR>
								<TD class="textColS">From CA</TD>
								<TD class="inputCol"><%=ORIGDisplayFormatUtil.displayPopUpTagScriptAction(loanDataM.getCreditApproval(),displayMode,"5","fca_code_cr","25","mkt_code_desc1","textbox","","30","...","button_text","LoadRepfCaCodeCredit","","CREDITAPRV") %></TD>
							</TR>
							<TR>
								<TD class="textColS">To CA</TD>						
								<TD class="inputCol"><%=ORIGDisplayFormatUtil.displayPopUpTagScriptAction(loanDataM.getCreditApproval(),displayMode,"5","tca_code_cr","25","mkt_code_desc2","textbox","","30","...","button_text","LoadReptCaCodeCredit","","CREDITAPRV") %></TD>
							</TR>
							<TR>
								<TD class="textColS">Create Date From</TD>
								<TD class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagJsDate("appFormName",ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(new Date())),displayMode,"","f_c_date","textbox","right","onblur=\"javascript:checkDate('f_c_date')\" onkeydown=\"javascript:DateFormat(this,this.value,event,false,'3')\" maxlength =\"10\"")%></TD>
							</TR>
							<TR>
								<TD class="textColS">Create Date To</TD>
								<TD class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagJsDate("appFormName","",displayMode,"","t_c_date","textbox","right","onblur=\"javascript:checkDate('t_c_date')\" onkeydown=\"javascript:DateFormat(this,this.value,event,false,'3')\" maxlength =\"10\"")%></TD>
							</TR>
							</TABLE>
						</td></tr>
						</table>
					</td></tr>
					</table>
				</td></tr>
			</table>
		</td> 
	</tr>
</table>


<!-- initial form param -->
<!-- form action="FrontController" method="post" name="frmrefresh">
	<input type="hidden" name="action" value="RefreshReportPage">
	<input type="hidden" name="P_CHANNEL" value=""> 
    <input type="hidden" name="P_PRODUCT" value=""> 
    <input type="hidden" name="P_ORG_ID" value=""> 
    <input type="hidden" name="page" value="RPT_CA_DECS">
</form-->

<script language="javaScript" type=text/JavaScript>


function searchReport(){

	
if(	verifyForm()== true &&  veroff()== true)
{	reportForm.target='reportWindow';
	reportForm.foffice_code.value = appFormName.foffice_code.value;
	reportForm.toffice_code.value = appFormName.toffice_code.value;	

    if(appFormName.fca_code_cr == null || appFormName.fca_code_cr.value == '')
		{reportForm.fca_code.value = '0001';}
    else
    	{reportForm.fca_code.value = appFormName.fca_code_cr.value;}
    if(appFormName.tca_code_cr == null || appFormName.tca_code_cr.value == '')
    	{reportForm.tca_code.value = 'zzzz'}
    else
    	{reportForm.tca_code.value = appFormName.tca_code_cr.value;}
	reportForm.f_c_date.value = appFormName.f_c_date.value;
	reportForm.t_c_date.value = appFormName.t_c_date.value;
	reportForm.submit();
	return true;}
	else
	return false;
}

var reportWindow;
//-- check fill in form for * --
function verifyForm()
{

	var sourceFrm = document.appFormName;
	if (sourceFrm.f_c_date == null || sourceFrm.f_c_date.value == '' ) 
	{
		alert('Please Select Create Date From !');
		sourceFrm.f_c_date.focus();
	} 
	else if(sourceFrm.t_c_date == null || sourceFrm.t_c_date.value == '' ) 
	{
		alert('Please Select Create Date To !');
		sourceFrm.t_c_date.focus();	
	}
	else if(sourceFrm.t_c_date != null && sourceFrm.f_c_date != null)
	{
		var nStart = date2Number(sourceFrm.f_c_date.value); 
		var nEnd = date2Number(sourceFrm.t_c_date.value);
		if (nEnd<nStart && nEnd!=0 && nStart!=0)
		{
			alert('Please Select Create Date To Equal or Greater Than Created Date From !');
			return false;
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
//check cmr code
function vercacode()
{
	var sourceFrm = document.appFormName;
	if(sourceFrm.fca_code_cr == null || sourceFrm.fca_code_cr.value == '')
	{
			alert('Please Select From CA!');
		
	}else if(sourceFrm.tca_code_cr == null || sourceFrm.tca_code_cr.value == '')
	{
				alert('Please Select To CA!');
		
	}else if(sourceFrm.fca_code_cr != null && sourceFrm.tca_code_cr != null)
	{
		return true;
	}
	return false;
}
//check office 
function veroff()
{
	var sourceFrm = document.appFormName;
	if(sourceFrm.foffice_code == null || sourceFrm.foffice_code.value == '')
	{ alert('Please Select From Office!'); }
	else if(sourceFrm.toffice_code == null || sourceFrm.toffice_code.value == '')
	{ alert('Please Select To Office!'); }
	else if(sourceFrm.foffice_code != null && sourceFrm.toffice_code != null)
	{ return true; }
	return false;
}

</script>

</form>
<form action="ORIGReportUtil" method="post" name="reportForm">
	<input type="hidden" name="foffice_code" value="">
	<input type="hidden" name="toffice_code" value="">
	<input type="hidden" name="fca_code" value="">
	<input type="hidden" name="tca_code" value="">
	<input type="hidden" name="f_c_date" value="">
	<input type="hidden" name="t_c_date" value="">
	<input type="hidden" name="report_type" value="rep_ca_decs">
<!--<input type="hidden" name="P_CHANNEL" value=""> 
    <input type="hidden" name="P_PRODUCT" value=""> 
    <input type="hidden" name="P_ORG_ID" value=""> 
    <input type="hidden" name="page" value="RPT_CA_DECS">-->



</BODY>
</html>
