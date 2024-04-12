<%@ page contentType="text/html;charset=UTF-8"%>

<%@ page import="java.util.*" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.shared.model.LoanDataM" %>
<%@ page import="com.eaf.orig.shared.model.ApplicationDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
<%@ page import="com.eaf.orig.shared.model.SearchDataM" %> 
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.orig.cache.CacheDataInf" %>


<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>
<html>
<HEAD>
<% 
	ApplicationDataM applicationDataM = ORIGForm.getAppForm();
	if(applicationDataM == null){
		applicationDataM = new ApplicationDataM();
	}
	ORIGCacheUtil cacheUtil = ORIGCacheUtil.getInstance();
	ORIGFormUtil formUtil = new ORIGFormUtil();
	String searchType = (String) request.getSession().getAttribute("searchType");
	String displayMode = "EDIT";
	
	SearchDataM SEARCH_DE_DATAM = (SearchDataM) request.getSession().getAttribute("SEARCH_DE_DATAM");
	if(SEARCH_DE_DATAM == null){
		SEARCH_DE_DATAM = new SearchDataM();
	}

	MessageResourceUtil msgUtil = MessageResourceUtil.getInstance();
	
	ORIGUtility utility = new ORIGUtility();
	Vector officeVect = utility.loadCacheByName("OfficeInformation");		
	LoanDataM loanDataM = (LoanDataM) request.getSession().getAttribute("POPUP_DATA");
	if(loanDataM == null){
		loanDataM = new LoanDataM();
	}
	Vector positionVect = cacheUtil.getNaosCacheDataMs("AL_ALL_ALL",10);  	 		  
%>
<META http-equiv="Content-Style-Type" content="text/css">
<LINK href="../../theme/Master.css" rel="stylesheet" type="text/css">
<TITLE></TITLE>
</HEAD>
<BODY>
<TABLE cellspacing="1" cellpadding="0" width="100%" border="0">
	<TR>
		<TD class="textColS" width="20%"></TD>
		<TD class="inputCol" width="80%"></TD>
	</TR>
	<TR>
		<td class="textColS" width="142" align="right"><FONT color="black"><B>Application No. :</B></FONT></td>
		<TD class="inputCol" width="552"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction("", displayMode, "", "app_no", "textbox", "", "")%></TD>
	</TR>
	<TR>
		<TD class="textColS" width="142" align="right"><FONT color="black"><B>Guarantor ID:</B></FONT></TD>
		<TD class="inputCol" width="552"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction("", displayMode, "", "guarantor_id", "textbox", "", "")%></TD>
	</TR>	 
	<TR>
		<TD class="textColS" width="142"></TD>
		<TD class="inputCol" align="left" width="552"><input name="search" type="button" class="button_text" value="search" onclick="searchReport();"></TD>
	</TR>
</TABLE>


<!-- initial form param -->
<!-- form action="FrontController" method="post" name="frmrefresh">
	<input type="hidden" name="action" value="RefreshReportPage">
	<input type="hidden" name="P_CHANNEL" value=""> 
    <input type="hidden" name="P_PRODUCT" value=""> 
    <input type="hidden" name="P_ORG_ID" value=""> 
    <input type="hidden" name="page" value="RPT_CA_LETTER">
</form-->

<script language="javaScript" type=text/JavaScript>


function searchReport(){

if(	verifyForm()== true && verappno()== true)
{
	reportForm.target='reportWindow';
	reportForm.app_no.value = appFormName.app_no.value;
	//reportForm.com_ins.value = appFormName.com_ins.value;
	reportForm.guarantor_id.value = appFormName.guarantor_id.value;
	reportForm.print_user.value = "<%=ORIGUser.getFirstName()%> <%=ORIGUser.getLastName()%>";
	<%
	String position=ORIGUser.getPosition();
	if(position==null||"".equals(position)){
	 position="-";
	}else{
	  for(int i=0;i<positionVect.size();i++){
	  CacheDataInf cacheDataM=(CacheDataInf)positionVect.get(i);
	  if( cacheDataM.getCode().equals(position)){
	    position=cacheDataM.getThDesc();
	     break;
	   }
	  }
	}
	%>
	reportForm.print_pos.value = "<%=position%>";
	if(<%=ORIGUser.getTelephone()%> == null){
		reportForm.print_tel.value = "...";}
	else{
		reportForm.print_tel.value = "<%=ORIGUser.getTelephone()%>";}	 
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
			var url = "";
 			var feat = "height=650px,width=850px,top=10px,left=50px,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars=yes";
			window.open(url, "reportWindow", feat);	
			return true;
}

//check null
function verappno()
{
	var sourceFrm = document.appFormName;
	if(sourceFrm.app_no == null || sourceFrm.app_no.value == '')
	{ 
		alert('Please Insert Application No.!');
		sourceFrm.app_no.focus();
	}else if(sourceFrm.guarantor_id == null || sourceFrm.guarantor_id.value == '')
	{
		alert('Please Insert guarantor_id!');
		sourceFrm.guarantor_id.focus();
	} else if(sourceFrm.app_no != null && sourceFrm.guarantor_id != null )
		{return true;}
		else
		return false;
	
	
}
</script>

</form>
<form action="ORIGReportUtil" method="post" name="reportForm">
	<input type="hidden" name="app_no" value="">
	<input type="hidden" name="guarantor_id" value="">
	<input type="hidden" name="com_fa" value="">
	<input type="hidden" name="print_user" value="">
	<input type="hidden" name="print_pos" value="">
	<input type="hidden" name="print_tel" value="">
	<input type="hidden" name="report_type" value="rep_pre_contrac_g">
<!--<input type="hidden" name="P_CHANNEL" value=""> 
    <input type="hidden" name="P_PRODUCT" value=""> 
    <input type="hidden" name="P_ORG_ID" value=""> 
    <input type="hidden" name="page" value="RPT_CA_LETTER">-->



</BODY>
</html>
