<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.Vector"%>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.cache.data.CacheDataM" %>
<%@ page import="com.eaf.orig.shared.model.SearchUWDataM" %>
<%@ page import="com.eaf.orig.wf.shared.ORIGWFConstant" %>


<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>
<jsp:useBean id="VALUE_LIST" scope="session" class="com.eaf.orig.shared.model.ValueListM" />

<%
	SearchUWDataM SEARCH_UW_DATAM = (SearchUWDataM) request.getSession().getAttribute("SEARCH_UW_DATAM");
	if(SEARCH_UW_DATAM == null){
		SEARCH_UW_DATAM = new SearchUWDataM();
	}
	String displayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
	String errMsg = (String) request.getSession(true).getAttribute(OrigConstant.ERR_MESSAGE_SESSION);
	if (errMsg != null && !"".equals(errMsg)) {
		out.println("<font class=TextWarningNormal>*&nbsp;"+errMsg+"</font>");
		request.getSession(true).removeAttribute(OrigConstant.ERR_MESSAGE_SESSION);
	}
	
	String searchType = (String) request.getSession(true).getAttribute("searchType"); 	
 	if(searchType==null || searchType.equals("")){
 			searchType = request.getParameter("searchType");
 			request.getSession(true).setAttribute("searchType",searchType);
 	}
	
	ORIGUtility utility = new ORIGUtility();
	ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
	
	String dealer = cacheUtil.getORIGCacheDisplayNameFormDB(SEARCH_UW_DATAM.getDealerCode(), OrigConstant.CacheName.CACHE_NAME_DEALER);
	
	Vector dealer_codeVect = utility.loadCacheByName("Dealer");
	Vector customer_typeVect = utility.loadCacheByName("CustomerType");
	Vector channelVect = utility.getMasterDataFormCache("SRCOFCST");	
	
	Vector priorityVect = new Vector();
	CacheDataM cacheDataM = new CacheDataM();
	cacheDataM.setCode(OrigConstant.Priority.NORMAL);
	cacheDataM.setShortDesc("Normal");
	priorityVect.add(cacheDataM);
	cacheDataM = new CacheDataM();
	cacheDataM.setCode(OrigConstant.Priority.MEDIUM);
	cacheDataM.setShortDesc("Medium");
	priorityVect.add(cacheDataM);
	cacheDataM = new CacheDataM();
	cacheDataM.setCode(OrigConstant.Priority.HIGH);
	cacheDataM.setShortDesc("High");
	priorityVect.add(cacheDataM);
		
	Vector sort_byVect = new Vector();
	cacheDataM = new CacheDataM();
	cacheDataM.setCode("IDNO");
	cacheDataM.setShortDesc("Citizen ID");
	sort_byVect.add(cacheDataM);
	cacheDataM = new CacheDataM();
	cacheDataM.setCode("APPLICATION_NO");
	cacheDataM.setShortDesc("Application No");
	sort_byVect.add(cacheDataM);
	cacheDataM = new CacheDataM();
	cacheDataM.setCode("THNAME");
	cacheDataM.setShortDesc("Thai First Name/Company Name ");
	sort_byVect.add(cacheDataM);
	cacheDataM = new CacheDataM();
	cacheDataM.setCode("THSURN");
	cacheDataM.setShortDesc("Thai Last Name ");
	sort_byVect.add(cacheDataM);
	cacheDataM = new CacheDataM();
	cacheDataM.setCode("REGISTER_NUMBER");
	cacheDataM.setShortDesc("Vehicle Registration No");
	sort_byVect.add(cacheDataM);
	cacheDataM = new CacheDataM();
	cacheDataM.setCode("DEALER_CODE");
	cacheDataM.setShortDesc("Dealer Code/name");
	sort_byVect.add(cacheDataM);
	cacheDataM = new CacheDataM();
	cacheDataM.setCode("CUSTYP");
	cacheDataM.setShortDesc("Main Customer Type");
	sort_byVect.add(cacheDataM);
	cacheDataM = new CacheDataM();
	cacheDataM.setCode("SRCOFCUS");
	cacheDataM.setShortDesc("Source of Customer");
	sort_byVect.add(cacheDataM);
	cacheDataM = new CacheDataM();
	cacheDataM.setCode("PRIORITY");
	cacheDataM.setShortDesc("Priority");
	sort_byVect.add(cacheDataM);
	
	String menu = (String)request.getSession(true).getAttribute("PROPOSAL_MENU");
	System.out.println("menu = "+menu);
	
%>

<input name="appRecordID" type="hidden" value="">
<input name="appStatus" type="hidden" value="">
<input name="jobState" type="hidden" value="">
<TABLE cellSpacing=0 cellPadding=0 width="100%"  border="0">
	<TR><TD vAlign=top>
	<table cellpadding="0" cellspacing="0" width="100%" align="center" bgcolor="#FFFFFF">
		<tr><td class="sidebar8">
			<table cellSpacing="1" cellPadding="0" width="100%" border="0"> 
		    	<tr><td class="sidebar9">
					<table cellspacing=0 cellpadding=0 width="100%" align=center border=0>	     		
			 			<tr> <td  height="10">
			 				<table width="100%" border="0" cellspacing="0" cellpadding="0">
                          	<tr><td class="text-header-detail">&nbsp;&nbsp;&nbsp;&nbsp; </td>
                            <td width="330">
                            	<table width="50" border="0" align="right" cellpadding="2" cellspacing="0">
                              		<tr height="30"><td>
                                    	<input type="button" name="SearchBtn" value="<%=MessageResourceUtil.getTextDescription(request, "SEARCH") %>" onClick="searchApp()" class="button">
                                    </td></tr>
                                </table>
                            </td></tr>	
                            </table>
                        </td></tr>
                        <tr> <td  height="15"></td></tr>                        
						<tr class="sidebar10"> <td align="center">
							<table width="100%" align=center cellpadding="0" cellspacing="1" border="0">
								<tr>
								 	<td class="textColS" width="25%"><%=MessageResourceUtil.getTextDescription(request, "CITIZEN_ID") %>:</td>
								 	<td class="inputCol" width="25%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(SEARCH_UW_DATAM.getCitizenID(), displayMode, "", "citizen_id", "textbox", "", "")%></td>
								 	<td class="textColS" width="25%"><%=MessageResourceUtil.getTextDescription(request, "APPLICATION_NO") %> :</td>
								 	<td class="inputCol" width="25%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(SEARCH_UW_DATAM.getApplicationNo(), displayMode, "", "application_no", "textbox", "", "")%></td>
								 </tr>
								 <tr>
								 	<td class="textColS" ><%=MessageResourceUtil.getTextDescription(request, "THAI_FNAME") %> :</td>
								 	<td class="inputCol" ><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(SEARCH_UW_DATAM.getCustomerName(), displayMode, "", "company_name", "textbox", "", "")%></td>
								 	<td class="textColS" ><%=MessageResourceUtil.getTextDescription(request, "THAI_LNAME") %> :</td>
								 	<td class="inputCol" ><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(SEARCH_UW_DATAM.getCustomerLName(), displayMode, "", "lastname", "textbox", "", "")%></td>			 				 	
								 </tr>
								 <tr>
								 	<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "VEHICLE_REGIS_NO") %> :</td>
								 	<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(SEARCH_UW_DATAM.getRegisterNo(), displayMode, "", "register_no", "textbox", "", "")%></td>			 				 				 	
								 	<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "DEALER_CODE_NAME") %> :</td>
								 	<td class="inputCol"><%=ORIGDisplayFormatUtil.displayPopUpTagScriptAction(SEARCH_UW_DATAM.getDealerCode(),displayMode,"5","dealer_code","30","dealer_desc","textbox","","50","...","button_text","LoadDealer",ORIGDisplayFormatUtil.displayHTML(dealer),"Dealer") %></td>
								 </tr>			 
								 <tr>
								  	<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "MAIN_CUSTOMER_TYPE") %> :</td>
								 	<td class="inputCol"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(customer_typeVect,SEARCH_UW_DATAM.getCustomerType(),"customer_type",displayMode,"") %></td>
									<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "CHANNEL") %> :</td>
								 	<td class="inputCol"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(channelVect,SEARCH_UW_DATAM.getChannel(),"channel",displayMode,"") %></td>
								 </tr>
								 <tr>			 	
								 	<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "PRIORITY") %> :</td>
								 	<td class="inputCol"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction(priorityVect,SEARCH_UW_DATAM.getPriority(),"priority",displayMode,"") %></td>		 				 				 	
								 	<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "SORT_BY") %> :</td>
								 	<td class="inputCol"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction(sort_byVect,SEARCH_UW_DATAM.getSortBy(),"sort_by",displayMode,"") %></td>			 				 	
								 </tr>
								 <tr>
					  			    <td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "REQUEST_ID") %> :</td>
								 	<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction( ORIGDisplayFormatUtil.displayHTML(SEARCH_UW_DATAM.getRequestID()), displayMode, "", "request_id", "textbox", "", "")%></td>		 				 				 	
									<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "GROUP_ID") %> :</td>
									<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(SEARCH_UW_DATAM.getGroupID(), displayMode, "", "group_id", "textbox", "", "")%></td>
					
								 </tr>
					
								 
								 <!-- Wiroon 20100315 -->
								 <tr>
								 	<td class="textColS" width="25%"><%=MessageResourceUtil.getTextDescription(request, "CO_CITIZEN_ID") %>:</td>
								 	<td class="inputCol" width="25%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(SEARCH_UW_DATAM.getCo_citizenID(), displayMode, "", "co_citizen_id", "textbox", "", "")%></td>
								 	<td class="textColS" width="25%"></td>
								 	<td class="inputCol" width="25%"></td>
								 </tr>
								 <tr>
								 	<td class="textColS" width="25%"><%=MessageResourceUtil.getTextDescription(request, "CO_FNAME") %>:</td>
								 	<td class="inputCol" width="25%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(SEARCH_UW_DATAM.getCo_customerFName(), displayMode, "", "co_fname", "textbox", "", "")%></td>
								 	<td class="textColS" width="25%"><%=MessageResourceUtil.getTextDescription(request, "CO_LNAME") %> :</td>
								 	<td class="inputCol" width="25%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(SEARCH_UW_DATAM.getCo_customerLName(), displayMode, "", "co_lname", "textbox", "", "")%></td>
								 </tr>
			 					<!-- 20100315 -->
			 				</table>
						</td></tr>
					</table>
				</td></tr>
			</table>
		</td></tr>		
	</table>
	</TD></TR>
</TABLE>

<table id="resultTable" height="100%" width="100%">
  <tr>
    <td height="100%"><iframe src="orig/uw/uw_inbox_search_result_screen.jsp" width="100%" height="100%" name="MainAppFrame" allowTransparency="true" style="background-color: #FFFFFF;overflow: auto;" frameborder="0" scrolling="auto"></iframe>&nbsp;</td>
  </tr>
</table>

<input type="hidden" name="fromSearch" value="N">
<input type="hidden" name="itemsPerPage" value="">
<input type="hidden" name="atPage" value="">
<input type="hidden" name="priorityName" value="">
<script language="JavaScript">
resultTable.height = parseInt(window.screen.height) - 245;
//alert(window.screen.height);
function validate(){
	var citizen_id 	= appFormName.citizen_id.value;
	var application_no 	=  appFormName.application_no.value;
	var company_name = appFormName.company_name.value;
	var lastname 	= appFormName.lastname.value;
	var register_no = appFormName.register_no.value;
	var dealer_code = appFormName.dealer_code.value;
	var customer_type = appFormName.customer_type.value;
	var channel 	= appFormName.channel.value;
	var priority 	= appFormName.priority.value;
	var request_id 	= appFormName.request_id.value;
	var groupID 	= appFormName.group_id.value;	
	//Wiroon 20100315
	var co_citizen_id 	= appFormName.co_citizen_id.value;
	var co_fname 	= appFormName.co_fname.value;
	var co_lname 	= appFormName.co_lname.value;
	
	if( (groupID == "") && (citizen_id=="") && (application_no=="") && (company_name=="") && (lastname=="") && (register_no=="") && (dealer_code=="") && (customer_type=="") && (channel=="") && (priority=="")&&(request_id=="") && (co_citizen_id=="") && (co_fname=="") && (co_lname=="") ){
		alert("At Least One Criteria Must Be Selected.");
		return false;
	}else{
		return true;
	}
}
function searchApp(){
	if(validate()){
		<%if(("Y").equals(menu)){%>
			appFormName.formID.value="PROPOSAL_FORM";
		<%}else{%>
			appFormName.formID.value="DE_FORM";
		<%}%>
		appFormName.currentTab.value="MAIN_TAB";
		appFormName.action.value="SearchUW";
		appFormName.handleForm.value = "N";
		appFormName.fromSearch.value = "Y";
		appFormName.submit();	
	}
}

</script>