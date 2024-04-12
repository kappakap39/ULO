<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.Vector"%>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.shared.model.SearchXUWDataM" %>

<script language="javascript" src="../../displayLoading.js"></script>  
<script language="javascript" src="../../DisableOpenNewWindows.js"></script>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>
<jsp:useBean id="VALUE_LIST" scope="session" class="com.eaf.orig.shared.model.ValueListM" />
<HTML>
<HEAD>
<LINK rel="StyleSheet" href="../../MainStyle.css" type="text/css">
</HEAD>
<BODY style="background-color:transparent" >
<%
	SearchXUWDataM SEARCH_XUW_DATAM = (SearchXUWDataM) request.getSession().getAttribute("SEARCH_XUW_DATAM");
	if(SEARCH_XUW_DATAM == null){
		SEARCH_XUW_DATAM = new SearchXUWDataM();
	}
	String displayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
	String errMsg = (String) request.getSession(true).getAttribute(OrigConstant.ERR_MESSAGE_SESSION);
	if (errMsg != null && !"".equals(errMsg)) {
		out.println("<font class=TextWarningNormal>*&nbsp;"+errMsg+"</font>");
		request.getSession(true).removeAttribute(OrigConstant.ERR_MESSAGE_SESSION);
	}
	String searchType = (String)session.getAttribute("searchType"); 
	ORIGUtility utility = new ORIGUtility();
	ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
	
	String dealer = cacheUtil.getORIGCacheDisplayNameFormDB(SEARCH_XUW_DATAM.getDealerCode(), OrigConstant.CacheName.CACHE_NAME_DEALER);
	
	Vector dealer_codeVect = utility.loadCacheByName("Dealer");
	Vector customer_typeVect = utility.loadCacheByName("CustomerType");
	Vector channelVect = utility.getMasterDataFormCache("SRCOFCST");	
	
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
<table cellpadding="0" cellspacing="0" width="200%" align="center" bgcolor="#FFFFFF">
	<tr>
		<td colspan="2">
			<div id="KLTable">
				<table cellSpacing=0 cellPadding=0 width="100%" border="0">
					<tr>
						<td class="TableHeader" width="5%" nowrap><%=MessageResourceUtil.getTextDescription(request, "PRIORITY") %></td>
						<td class="TableHeader" width="7%" nowrap><%=MessageResourceUtil.getTextDescription(request, "LATEST_UPDATE") %></td>
						<td class="TableHeader" width="7%" nowrap><%=MessageResourceUtil.getTextDescription(request, "APPLICATION_NO") %></td>
						<td class="TableHeader" width="6%" nowrap><%=MessageResourceUtil.getTextDescription(request, "THAI_FNAME") %></td>
						<td class="TableHeader" width="6%" nowrap><%=MessageResourceUtil.getTextDescription(request, "THAI_LNAME") %></td>
						<td class="TableHeader" width="6%" nowrap><%=MessageResourceUtil.getTextDescription(request, "CITIZEN_ID") %></td>
						<td class="TableHeader" width="7%" nowrap><%=MessageResourceUtil.getTextDescription(request, "LOAN_TYPE") %></td>
						<td class="TableHeader" width="5%" nowrap><%=MessageResourceUtil.getTextDescription(request, "LAST_USER_ID") %></td>
						<td class="TableHeader" width="7%" nowrap><%=MessageResourceUtil.getTextDescription(request, "MAIN_CUSTOMER_GUARANTOR_TYPE") %></td>
						<td class="TableHeader" width="8%" nowrap><%=MessageResourceUtil.getTextDescription(request, "DEALER_CODE_NAME") %></td>						
						<td class="TableHeader" width="7%" nowrap><%=MessageResourceUtil.getTextDescription(request, "CHANNEL") %></td>
						<td class="TableHeader" width="7%" nowrap><%=MessageResourceUtil.getTextDescription(request, "REQUEST_ID") %></td>						
						<td class="TableHeader" width="7%" nowrap><%=MessageResourceUtil.getTextDescription(request, "CMR_CODE_OR_NAME") %></td>
						<td class="TableHeader" width="6%" nowrap><%=MessageResourceUtil.getTextDescription(request, "PERSONAL_TYPE") %></td>
						<td class="TableHeader" width="5%" nowrap><%=MessageResourceUtil.getTextDescription(request, "APPLICATION_STATUS") %></td>
						<td class="TableHeader" width="3%" nowrap><%=MessageResourceUtil.getTextDescription(request, "ROLE") %></td>
						<%if( OrigConstant.SEARCH_TYPE_ENQUIRY.equals(searchType)){ %>
						<td class="TableHeader" width="5%" nowrap><%=MessageResourceUtil.getTextDescription(request, "CONTRACT_NO") %></td>
						<%}%>
					</tr>
					<%
						Vector valueList = VALUE_LIST.getResult(); 
						if(valueList != null && valueList.size() > 1){
							String cmrCode;
							String dealerCode;
							String srcofcus;
							String customerType;
							String loanType;
							String mktCode;
							for(int i=1;i<valueList.size();i++){
								Vector elementList = (Vector)valueList.get(i);
								cmrCode = cacheUtil.getORIGMasterDisplayNameDataM("UserName", (String) elementList.elementAt(15));
								//System.out.println("elementList.elementAt(8) = "+elementList.elementAt(10));
								dealerCode = cacheUtil.getORIGCacheDisplayNameFormDB((String) elementList.elementAt(10), OrigConstant.CacheName.CACHE_NAME_DEALER);
								srcofcus = cacheUtil.getORIGCacheDisplayNameDataMByType((String) elementList.elementAt(11), "SRCOFCST");
								customerType = cacheUtil.getORIGMasterDisplayNameDataM("CustomerType", (String) elementList.elementAt(9));
								loanType = cacheUtil.getORIGMasterDisplayNameDataM("LoanType", (String) elementList.elementAt(7));
								mktCode = cacheUtil.getORIGMasterDisplayNameDataM("LoanOfficer", (String) elementList.elementAt(17));
					%>
								<tr bgcolor="#F4F4F4" onmouseover="this.style.cursor='hand' ;this.style.background='#D7D6D7'" onmouseout=" this.style.background='#F4F4F4'"onclick="javascritp:gotoAppForm('<%=elementList.elementAt(12) %>', '<%=elementList.elementAt(13) %>', '<%=elementList.elementAt(14) %>')">
									<td align="center"><%=ORIGDisplayFormatUtil.displayHTML(utility.getPriorityDescription((String) elementList.elementAt(1))) %></td>
									<td align="center"><%=ORIGDisplayFormatUtil.stringDateTimeValueListForThai((String) elementList.elementAt(2))%></td>
									<td align="center"><%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(3)) %></td>
									<td>&nbsp;<%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(4)) %></td>
									<td>&nbsp;<%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(5)) %></td>
									<td align="center"><%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(6)) %></td>
									<td>&nbsp;<%=ORIGDisplayFormatUtil.displayHTML(loanType) %></td>
									<td>&nbsp;<%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(8)) %></td> 
									<td>&nbsp;<%=ORIGDisplayFormatUtil.displayHTML(customerType) %></td>
									<td>&nbsp;<%=ORIGDisplayFormatUtil.displayHTML(dealerCode) %></td>
									<td>&nbsp;<%=ORIGDisplayFormatUtil.displayHTML(srcofcus) %></td>
									<td>&nbsp;<%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(18)) %></td>
									<td>&nbsp;<%=ORIGDisplayFormatUtil.displayHTML(mktCode) %></td>
									<td align="center"><%=ORIGDisplayFormatUtil.displayHTML(utility.getPersonalTypeDesc((String) elementList.elementAt(16))) %></td>
									<td>&nbsp;<%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(13)) %></td>
									<td align="center"><%=ORIGDisplayFormatUtil.displayHTML(ORIGUtility.getRoleByJobState((String) elementList.elementAt(14))) %></td>
									<%if( OrigConstant.SEARCH_TYPE_ENQUIRY.equals(searchType)){ %>
									<td align="center"><%=ORIGDisplayFormatUtil.displayHTML( (String) elementList.elementAt(19)) %></td>
									<%} %>
								</tr>
					<% 
							}
						}else{
					%>
							<tr>
								<td colspan="14" align="center"><font color="#FF0000"><b>Results Not Found.</b></font></td>
							</tr>
					<%
						}
					%>
						<tr>
							<td colspan="14" align="right" height="50">
								<div align="center"><span class="font2">
									<jsp:include page="../appform/valuelist.jsp" flush="true" />
								</span></div>
							</td>
						</tr>
				</table>
			</div>
		</td>
	</tr>
</table>
<input type="hidden" name="fromSearch" value="N">
<script language="JavaScript">
function gotoAppForm(appRecordID, appStatus, jobState){
	displayLoading('Loading...');
	var form=window.parent.document.appFormName;
	form.formID.value="DE_FORM";
	form.currentTab.value="MAIN_TAB";
	form.action.value="LoadAppForm";
	form.handleForm.value = "N";
	form.appRecordID.value = appRecordID;
	form.appStatus.value = appStatus;
	form.jobState.value = jobState;
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
