<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.Vector"%>
<%@ page import="com.eaf.orig.ulo.pl.formcontrol.view.form.SearchHandler"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.WorkflowTool"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.DataFormatUtility"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil"%>
<%@ page import="com.eaf.orig.shared.util.OrigUtil"%>

<script type="text/javascript" src="orig/js/inbox/pl/dc_bundle_cc.search.js"></script>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="VALUE_LIST" scope="session" class="com.eaf.orig.shared.model.ValueListM"/>

<%  
	org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger("orig/dc/search_bundle_screen.jsp");
    	log.debug("DC Search Bundle Screen");	 
	
	SearchHandler HandlerM = (SearchHandler) request.getSession().getAttribute("SEARCH_DATAM");
	if(HandlerM == null){
		HandlerM = new SearchHandler();
	}
	SearchHandler.SearchDataM searchDataM = HandlerM.getSearchM();
	if(searchDataM == null){
		searchDataM = new SearchHandler.SearchDataM();
	}
	
    Vector resultVect = VALUE_LIST.getResult();   
    String displayMode = HTMLRenderUtil.DISPLAY_MODE_EDIT;
   
%>

<div class="nav-inbox">
	<div class="PanelFirst">
		<div class="PanelSecond TextHeaderNormal">
			<%=SearchHandler.DisplayErrorMessage(request)%>
			<div class="PanelThird">
			<fieldset class="field-set">
				<legend>Search Criteria</legend>
				<table class="FormFrame">
					<tr>
						<td class="textR" width="20%"><%=MessageResourceUtil.getTextDescription(request, "REFERENCE_NO")%> :</td>
						<td class="textL" width="10%" id="button-job-status"><%=HTMLRenderUtil.displayInputTagScriptAction(searchDataM.getRefNo(), displayMode, "", "refNo", "textbox200", "", "30")%></td>
						<td width="40%" class="textR"><%=MessageResourceUtil.getTextDescription(request, "ID_NO")%> :</td>
						<td width="20%"><%=HTMLRenderUtil.displayInputTagScriptAction(searchDataM.getCitizenID(), displayMode, "", "citizen_id", "textbox", "", "20")%></td>
						<td width="20%">&nbsp;</td>
					</tr>
					<tr><td colspan="5" align="center"><input type="button" id="button-search" class="button" value="Search" onClick="javascript:searchBundling()" />&nbsp;&nbsp;</tr>				 			
				</table>
			</fieldset>		
			</div>
				<div class="PanelThird">
				<table class="TableFrame">	
				<%if(!OrigUtil.isEmptyVector(resultVect) && resultVect.size()>0) {%>
				<tr>
					<td colspan="8" align="left" class="textL"> <%=MessageResourceUtil.getTextDescription(request, "TOTAL_RECORD_FOUND")%> :<%=DataFormatUtility.displayIntegerToString(VALUE_LIST.getCount()) %></td>
				</tr>
				<%}%>			 
					<tr class="Header">
						<td><%=MessageResourceUtil.getTextDescription(request, "SALE_TYPE")%></td>
						<td>&nbsp;<%=MessageResourceUtil.getTextDescription(request, "PRIORITY")%>&nbsp;</td>
						<td><%=MessageResourceUtil.getTextDescription(request, "APPLICATION_NO")%></td>
						<td><%=MessageResourceUtil.getTextDescription(request, "APP_STATUS")%></td>
						<td><%=MessageResourceUtil.getTextDescription(request, "NAME_SURNAME")%></td>
						<td><%=MessageResourceUtil.getTextDescription(request, "ID_NO")%></td>
						<td><%=MessageResourceUtil.getTextDescription(request, "PRODUCT")%></td>
						<td><%=MessageResourceUtil.getTextDescription(request, "DATE_RECEIVE_APP")%></td>
					</tr>
			 <%			 
			 	if(!OrigUtil.isEmptyVector(resultVect) && resultVect.size()>1) {
			 	 WorkflowTool wfTool = new WorkflowTool();
			 %>
			 	<%
			 	for(int i=1; i<resultVect.size(); i++){
		 		 	Vector elementList = (Vector)resultVect.get(i); 
		 		 	StringBuilder style = new StringBuilder("");
		 		 		style.append((i%2==0)?"ResultEven":"ResultOdd");
		 		 		
		 		 	String  appRecId = (String) elementList.elementAt(1);	
		 		 	String  saleType = (String) elementList.elementAt(2);
		 		 	String  priority = (String) elementList.elementAt(3);
		 		 	String  applicationNo = (String) elementList.elementAt(4);
		 		 	String  applicationStatus = (String) elementList.elementAt(5);
		 		 	String  custName = (String) elementList.elementAt(6);
		 		 	String  idNo = (String) elementList.elementAt(7);
		 		 	String  product = (String) elementList.elementAt(8);
		 		    String  receiveDate =  (String) elementList.elementAt(9);		 		    
		 		    String  creditCardAppScore = (String) elementList.elementAt(10);
		 		    String  creditCardResult = (String) elementList.elementAt(11);
		 		    
		 		    if(creditCardResult==null){creditCardResult="";};
		 		    if(creditCardAppScore==null){creditCardAppScore="";};
		 		   
			 	%>			 	
		 			<tr class="Result-Obj <%=style.toString()%>" id="<%=appRecId%>|<%=creditCardResult%>|<%=creditCardAppScore%>">
		 				<td><%=HTMLRenderUtil.displayHTML(saleType)%></td>
		 				<td><%=HTMLRenderUtil.displayPriority(DataFormatUtility.StringToInt(priority))%></td>
		 				<td><%=HTMLRenderUtil.displayHTML(applicationNo)%></td>
		 				<td><%=wfTool.GetMessageAppStatus(request,applicationStatus)%></td>
		 				<td><%=HTMLRenderUtil.displayHTML(custName) %></td>
		 				<td><%=HTMLRenderUtil.displayHTML(idNo)%></td>
						<td><%=HTMLRenderUtil.displayHTML(product) %></td>
		 				<td><%=HTMLRenderUtil.displayHTML(receiveDate) %></td>		 				 
		 			</tr>
			 	<%}%>
			 <%}else{%>
					<tr class="ResultNotFound" id="notFoundTable">
						<td colspan="12">No record found</td>
					</tr>
			 <%}%>
				</table>	
			</div>
			<div class="PanelValueList">			
				<jsp:include page="../appform/pl/valueList.jsp" flush="true" />
			</div>
		</div>
	</div>
</div>  

<input type="hidden" value="" name="clickSearch">
<input name="jobStatus" type="hidden" value="N">
<input name="appRecordID" type="hidden" value="">
<input name="appStatus" type="hidden" value="">
<input name="jobState" type="hidden" value=""> 
<input type="hidden" name="fromSearch" value="N">
<input name="roleElement" type="hidden" value="">