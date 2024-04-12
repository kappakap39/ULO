<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.Vector"%>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.cache.data.CacheDataM" %>
<%@ page import="com.eaf.orig.shared.model.SearchDataM" %>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>
<jsp:useBean id="VALUE_LIST" scope="session" class="com.eaf.orig.shared.model.ValueListM" />

<%
	SearchDataM SEARCH_DE_DATAM = (SearchDataM) request.getSession().getAttribute("SEARCH_DE_DATAM");
	if(SEARCH_DE_DATAM == null){
		SEARCH_DE_DATAM = new SearchDataM();
	}
	String displayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
	String errMsg = (String) request.getSession(true).getAttribute(OrigConstant.ERR_MESSAGE_SESSION);
	if (errMsg != null && !"".equals(errMsg)) {
		out.println("<font class=TextWarningNormal>*&nbsp;"+errMsg+"</font>");
		request.getSession(true).removeAttribute(OrigConstant.ERR_MESSAGE_SESSION);
	}
	
	ORIGUtility utility = new ORIGUtility();
	ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
	
	String dealer = cacheUtil.getORIGMasterDisplayNameDataM("Dealer", SEARCH_DE_DATAM.getDealerCode());
	
	Vector dealer_codeVect = utility.loadCacheByName("Dealer");
	Vector customer_typeVect = utility.loadCacheByName("CustomerType");
	Vector channelVect = utility.getMasterDataFormCache("SRCOFCST");	
	
	Vector priorityVect = new Vector();
	CacheDataM cacheDataM = new CacheDataM();
	cacheDataM.setCode("1");
	cacheDataM.setShortDesc("Normal");
	priorityVect.add(cacheDataM);
	cacheDataM = new CacheDataM();
	cacheDataM.setCode("2");
	cacheDataM.setShortDesc("Medium");
	priorityVect.add(cacheDataM);
	cacheDataM = new CacheDataM();
	cacheDataM.setCode("3");
	cacheDataM.setShortDesc("High");
	priorityVect.add(cacheDataM);
		
	Vector sort_byVect = new Vector();
	cacheDataM = new CacheDataM();
	cacheDataM.setCode("NRIC");
	cacheDataM.setShortDesc("Citizen ID");
	sort_byVect.add(cacheDataM);
	cacheDataM = new CacheDataM();
	cacheDataM.setCode("FIRST_NAME");
	cacheDataM.setShortDesc("Thai First Name/Company Name ");
	sort_byVect.add(cacheDataM);
	cacheDataM = new CacheDataM();
	cacheDataM.setCode("LAST_NAME");
	cacheDataM.setShortDesc("Thai Last Name ");
	sort_byVect.add(cacheDataM);
	cacheDataM = new CacheDataM();
	cacheDataM.setCode("MAIN_CUSTOMER_TYPE");
	cacheDataM.setShortDesc("Main Customer Type");
	sort_byVect.add(cacheDataM);
	cacheDataM = new CacheDataM();
	cacheDataM.setCode("REQUEST_ID");
	cacheDataM.setShortDesc("Request ID");
	sort_byVect.add(cacheDataM);
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
                          	<tr><td class="text-header-detail">&nbsp;&nbsp;&nbsp;&nbsp; </td>
                            <td width="330">
                            	<table width="50" border="0" align="right" cellpadding="2" cellspacing="0">
                              		<tr height="30"><td>
                                    	<input type="button" name="SearchBtn" value="<%=MessageResourceUtil.getTextDescription(request, "SEARCH") %>" onClick="searchApp()" class="button"></td>
                                    </tr>
                                </table>
                            </td></tr>	
                            </table>
                        </td></tr>
                        <tr> <td  height="15"></td></tr>                        
						<tr class="sidebar10"> <td align="center">
							<table width="100%" align=center cellpadding="0" cellspacing="1" border="0">
								 <tr>
								 	<td class="textColS" width="25%"><%=MessageResourceUtil.getTextDescription(request, "CITIZEN_ID") %>:</td>
								 	<td class="inputCol" width="25%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(SEARCH_DE_DATAM.getCitizenID(), displayMode, "", "citizen_id", "textbox", "", "")%></td>
								 	<td class="textColS" width="25%"><%=MessageResourceUtil.getTextDescription(request, "REQUEST_ID") %> :</td>
								 	<td class="inputCol" width="25%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(SEARCH_DE_DATAM.getRequestID(), displayMode, "", "request_id", "textbox", "", "")%></td>
								 </tr>
								 <tr>
								 	<td class="textColS" ><%=MessageResourceUtil.getTextDescription(request, "THAI_FNAME") %> :</td>
								 	<td class="inputCol" ><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(SEARCH_DE_DATAM.getCustomerName(), displayMode, "", "company_name", "textbox", "", "")%></td>
								 	<td class="textColS" ><%=MessageResourceUtil.getTextDescription(request, "THAI_LNAME") %> :</td>
								 	<td class="inputCol" ><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(SEARCH_DE_DATAM.getCustomerLName(), displayMode, "", "lastname", "textbox", "", "")%></td>			 				 	
								 </tr>
								 <tr>
								 	<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "MAIN_CUSTOMER_TYPE") %> :</td>
								 	<td class="inputCol"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(customer_typeVect,SEARCH_DE_DATAM.getCustomerType(),"customer_type",displayMode,"") %></td>
								 	<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "SORT_BY") %> :</td>
								 	<td class="inputCol"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction(sort_byVect,SEARCH_DE_DATAM.getSortBy(),"sort_by",displayMode,"") %></td>			 				 	
								 </tr>
							</table>
						</td></tr>
					</table>
				</td>
			</tr>
		</table>
		</td></tr>		
	</table>
	</TD></TR>
</table>

<table cellpadding="" cellspacing="0" width="100%" align="center" bgcolor="#FFFFFF">
	<tr>
		<td>
			<div id="KLTable">
				<table class="gumayframe3" cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
					<tr><td class="TableHeader">
						<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
							<tr>
								<td class="Bigtodotext3" align="center" width="10%"><%=MessageResourceUtil.getTextDescription(request, "REQUEST_ID") %></td>	
								<td class="Bigtodotext3" align="center" width="15%"><%=MessageResourceUtil.getTextDescription(request, "THAI_FNAME") %></td>
								<td class="Bigtodotext3" align="center" width="15%"><%=MessageResourceUtil.getTextDescription(request, "THAI_LNAME") %></td>
								<td class="Bigtodotext3" align="center" width="10%"><%=MessageResourceUtil.getTextDescription(request, "CITIZEN_ID") %></td>
								<td class="Bigtodotext3" align="center" width="10%"><%=MessageResourceUtil.getTextDescription(request, "LOAN_TYPE") %></td>
								<td class="Bigtodotext3" align="center" width="15%"><%=MessageResourceUtil.getTextDescription(request, "MAIN_CUSTOMER_GUARANTOR_TYPE") %></td>
								<td class="Bigtodotext3" align="center" width="15%"><%=MessageResourceUtil.getTextDescription(request, "LATEST_UPDATE") %></td>
								<td class="Bigtodotext3" align="center" width="10%"><%=MessageResourceUtil.getTextDescription(request, "LAST_USER_ID") %></td>
							</tr>
						</table> 
						</td></tr>
					<%
						Vector valueList = VALUE_LIST.getResult(); 
						if(valueList != null && valueList.size() > 1){
							String customerType;
							String loanType;
							for(int i=1;i<valueList.size();i++){
								Vector elementList = (Vector)valueList.get(i);
								customerType = cacheUtil.getORIGMasterDisplayNameDataM("CustomerType", (String) elementList.elementAt(6));
								loanType = cacheUtil.getORIGMasterDisplayNameDataM("Product", (String) elementList.elementAt(5));
					%>
						<tr><td align="center" class="gumaygrey2">
							<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
								<tr onmouseover="this.style.cursor='hand' ;this.style.background='#D7D6D7'" onmouseout=" this.style.background='#F4F4F4'" 
								onclick="javascritp:gotoImageAppForm('<%=elementList.elementAt(1) %>','<%=elementList.elementAt(2) %>','<%=elementList.elementAt(3) %>','<%=elementList.elementAt(4) %>','<%=elementList.elementAt(5) %>','<%=elementList.elementAt(6) %>','<%=elementList.elementAt(9) %>')">
									<td class="jobopening2" width="10%"><%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(1)) %></td>
									<td class="jobopening2" width="15%"><%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(2)) %></td>
									<td class="jobopening2" width="15%"><%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(3)) %></td>
									<td class="jobopening2" width="10%"><%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(4)) %></td>
									<td class="jobopening2" width="10%"><%=loanType %></td>
									<td class="jobopening2" width="15%"><%=customerType %></td>
									<td class="jobopening2" width="15%"><%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(7)) %></td>
									<td class="jobopening2" width="10%"><%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(8)) %></td>
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
									<td colspan="8" align="center" class="jobopening2">No Record</td>
								</tr>
							</table> 
						</td></tr>
					<%
						}
					%>
						<tr><td align="center" class="gumaygrey2">
							<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
								<tr>
									<td colspan="8" align="right" height="50">
										<div align="center"><span class="font2">
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
<input name="requestID" type="hidden" value="">
<input name="citizenID" type="hidden" value="">
<input name="firstName" type="hidden" value="">
<input name="lastName" type="hidden" value="">
<input name="customerType" type="hidden" value="">
<input name="loanType" type="hidden" value="">
<input name="attachID" type="hidden" value="">
<script language="JavaScript">
function validate(){
	var citizen_id 	= appFormName.citizen_id.value;
	var company_name = appFormName.company_name.value;
	var lastname 	= appFormName.lastname.value;
	var customer_type = appFormName.customer_type.value;
	var request_id 	= appFormName.request_id.value;
	if( (citizen_id=="") && (company_name=="") && (lastname=="") && (customer_type=="") && (request_id=="")){
		alert("At Least One Criteria Must Be Selected.");
		return false;
	}else{
		return true;
	}
}
function searchApp(){
	if(validate()){
		appFormName.formID.value="DE_FORM";
		appFormName.currentTab.value="MAIN_TAB";
		appFormName.action.value="SearchApplication";
		appFormName.handleForm.value = "N";
		appFormName.fromSearch.value = "Y";
		appFormName.submit();	
	}
}
function gotoImageAppForm(requestID, firstName, lastName, citizenID, loanType, customerType, attachID){
	appFormName.formID.value="DE_FORM";
	appFormName.currentTab.value="MAIN_TAB";
	appFormName.action.value="LoadAppImageForm";
	appFormName.handleForm.value = "N";
	appFormName.requestID.value = requestID;
	appFormName.firstName.value = firstName;
	appFormName.lastName.value = lastName;
	appFormName.citizenID.value = citizenID;
	appFormName.loanType.value = loanType;
	appFormName.customerType.value = customerType;
	appFormName.attachID.value = attachID;
	appFormName.submit();	
}
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
