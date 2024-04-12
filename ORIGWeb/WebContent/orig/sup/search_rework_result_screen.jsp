<%@page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.Vector"%>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.ulo.pl.model.app.search.PLSearchDataM" %>

<script language="javascript" src="../../displayLoading.js"></script>  
<script language="javascript" src="../../DisableOpenNewWindows.js"></script>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>
<jsp:useBean id="VALUE_LIST" scope="session" class="com.eaf.orig.shared.model.ValueListM" />
<HTML>
<HEAD>
<LINK rel="StyleSheet" href="../../css/MainStylesheet.css" type="text/css">
</HEAD>
<BODY style="background-color:transparent" >
<%
	PLSearchDataM searchReopenDataM = (PLSearchDataM) request.getSession().getAttribute("SEARCH_REOPEN_DATAM");
	if(searchReopenDataM == null){
		searchReopenDataM = new PLSearchDataM();
	}
	String displayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
	String errMsg = (String) request.getSession(true).getAttribute(OrigConstant.ERR_MESSAGE_SESSION);
	if (errMsg != null && !"".equals(errMsg)) {
		out.println("<font class=TextWarningNormal>*&nbsp;"+errMsg+"</font>");
		request.getSession(true).removeAttribute(OrigConstant.ERR_MESSAGE_SESSION);
	}	
	ORIGUtility utility = new ORIGUtility();
	ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
%>
<form name="appFormName" method="post" action="/ORIGWeb/FrontController">
<input type="hidden" name="action" value=""> 
<input type="hidden" name="handleForm" value="Y"> 
<input type="hidden" name="page" value="">
<input type="hidden" name="tab_ID" value="">
<input type="hidden" name="formID" value="">
<input type="hidden" name="currentTab" value="">
<input type="hidden" name="flagEventClose">
<input type="hidden" name="menuSequence">
<input name="appRecordID" type="hidden" value="">
<input name="appStatus" type="hidden" value="">
<input name="jobState" type="hidden" value="">
<input name="typeReopen" type="hidden" value="">
<table cellpadding="" cellspacing="0" width="100%" align="center" bgcolor="#FFFFFF">
	<tr> 
		<td>
			<div id="KLTable">
				<table class="gumayframe3" cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
						<tr><td class="TableHeader">
							<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
								<tr>
									<td width="10%" class="Bigtodotext3" nowrap><%=MessageResourceUtil.getTextDescription(request, "PRIORITY") %></td>
									<td width="10%" class="Bigtodotext3" nowrap><%=MessageResourceUtil.getTextDescription(request, "REFERENCE_NO") %></td>
									<td width="10%" class="Bigtodotext3" nowrap><%=MessageResourceUtil.getTextDescription(request, "APP_STATUS") %></td>
									<td width="10%" class="Bigtodotext3" nowrap><%=MessageResourceUtil.getTextDescription(request, "NAME_SURNAME") %></td>
									<td width="10%" class="Bigtodotext3" nowrap><%=MessageResourceUtil.getTextDescription(request, "ID_NO") %></td>
									<td width="10%" class="Bigtodotext3" nowrap><%=MessageResourceUtil.getTextDescription(request, "PRODUCT") %></td>
									<td width="10%" class="Bigtodotext3" nowrap><%=MessageResourceUtil.getTextDescription(request, "TYPE_OF_SALE") %></td>
									<td width="10%" class="Bigtodotext3" nowrap><%=MessageResourceUtil.getTextDescription(request, "DATE_RECEIVE_APP") %></td>
									<td width="10%" class="Bigtodotext3" nowrap><%=MessageResourceUtil.getTextDescription(request, "LAST_UPDATE_DATE") %></td>
									<td width="10%" class="Bigtodotext3" nowrap><%=MessageResourceUtil.getTextDescription(request, "LAST_UPDATE_BY") %></td>
								</tr>
							</table> 
						</td></tr>
					<%
						Vector valueList = VALUE_LIST.getResult(); 
						request.getSession().setAttribute("resultSearchVec",valueList);
						if(valueList != null && valueList.size() > 1){
							for(int i=1;i<valueList.size();i++){
								Vector elementList = (Vector)valueList.get(i);
					%>
						<tr><td align="center" class="gumaygrey2">
							<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
								<tr onmouseover="this.style.cursor='hand' ;this.style.background='#D7D6D7'" onmouseout=" this.style.background='#FFFFFF'" onclick="loadApplication('<%=elementList.elementAt(11)%>','','')">
									<td width="10%" class="jobopening2" align="center"><%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(1)) %></td>
									<td width="10%" class="jobopening2" align="center"><%=HTMLRenderUtil.displayHTML(utility.getPriorityDescription((String) elementList.elementAt(2))) %></td>
									<td width="10%" class="jobopening2" align="center"><%=HTMLRenderUtil.displayHTML(elementList.elementAt(3)) %></td>
									<td width="10%" class="jobopening2">&nbsp;<%=HTMLRenderUtil.displayHTML(elementList.elementAt(4)) %></td>
									<td width="10%" class="jobopening2">&nbsp;<%=HTMLRenderUtil.displayHTML(elementList.elementAt(5)) %></td>
									<td width="10%" class="jobopening2" align="center"><%=HTMLRenderUtil.displayHTML(elementList.elementAt(6)) %></td>
									<td width="10%" class="jobopening2">&nbsp;<%=HTMLRenderUtil.displayHTML(elementList.elementAt(7)) %></td>
									<td width="10%" class="jobopening2">&nbsp;<%=HTMLRenderUtil.displayHTML(elementList.elementAt(8)) %></td> 
									<td width="10%" class="jobopening2">&nbsp;<%=HTMLRenderUtil.displayHTML(elementList.elementAt(9)) %></td>
									<td width="10%" class="jobopening2">&nbsp;<%=HTMLRenderUtil.displayHTML(elementList.elementAt(10)) %></td>
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
						  			<td class="jobopening2" colspan="16" align="center">No Record</td>
						  		</tr>
							</table> 
						</td></tr>
					<%
						}
					%>
						<tr><td align="center" class="gumaygrey2">
							<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
								<tr>
									<td colspan="17" align="right" height="50">
										<div align="right"><span class="textColS">
											<jsp:include page="../appform/valuelist.jsp" flush="true" />
										</span></div>
									</td>
								</tr>
							</table> 
						</td></tr>
				</table>
			</div>
		</td>
	</tr>
</table>
<input type="hidden" name="fromSearch" value="N">
</form>
</BODY>
</HTML>
<script language="JavaScript">
function loadApplication(appRecordID,appStatus,jobState){
    var form=document.appFormName;
	//displayLoading('Loading...');
	form.formID.value="KEC_FORM";
	form.currentTab.value="MAIN_TAB";
	form.action.value="LoadPLAppForm";
	form.handleForm.value = "N";
	form.appRecordID.value = appRecordID;
	form.appStatus.value = appStatus;
	form.jobState.value = jobState;
	form.typeReopen.value = "Y";
	form.target = "_parent";
	form.submit();	
}
function clickPageList(atPage){
	var form=window.parent.document.appFormName;
	form.atPage.value = atPage;
	form.action.value = "ValueListWebAction";
	form.handleForm.value = "N";
	form.submit();
}
function clickItemPerPageList(atItem){
	var form=window.parent.document.appFormName;
	form.itemsPerPage.value = atItem;
	form.action.value = "ValueListWebAction";
	form.handleForm.value = "N";
	form.submit();  
}
</script>
