<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="java.util.Vector"%>
<%@page import="com.eaf.orig.shared.util.OrigUtil"%>
<%@page import="com.eaf.orig.ulo.pl.app.utility.ORIGLogic"%>
<%@page import="com.eaf.j2ee.pattern.util.MessageResourceUtil"%>
<%@page import="com.eaf.orig.ulo.pl.app.utility.WebActionUtil"%>
<%@page import="com.eaf.orig.ulo.pl.app.utility.WorkflowTool"%>
<%@page import="com.eaf.orig.ulo.pl.formcontrol.view.form.SearchHandler"%>
<%@page import="com.eaf.orig.ulo.pl.app.utility.DataFormatUtility"%>
<%@page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="VALUE_LIST" scope="session" class="com.eaf.orig.shared.model.ValueListM" />

<script type="text/javascript" src="orig/js/sup/reassign.screen.js"></script>
<script type="text/javascript" src="js/ulo.application.javascript.js"></script>

<%  
	org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger("orig/sup/re_assign.jsp");
    
    String displayMode = HTMLRenderUtil.DISPLAY_MODE_EDIT;
    
    String current_role = ORIGUser.getCurrentRole();
    
    SearchHandler HandlerM = (SearchHandler) request.getSession().getAttribute("SEARCH_DATAM");
	if(HandlerM == null){
		HandlerM = new SearchHandler();
	}
	SearchHandler.SearchDataM searchDataM = HandlerM.getSearchM();
	if(searchDataM == null){
		searchDataM = new SearchHandler.SearchDataM();
	}
	if(OrigUtil.isEmptyString(searchDataM.getProductCode())){
		searchDataM.setProductCode(OrigConstant.PRODUCT_KEC);
	}
	
	ORIGCacheUtil origCache = ORIGCacheUtil.getInstance();
	Vector vUser = (Vector)origCache.loadUserNameCache(OrigConstant.Status.STATUS_ACTIVE);
	
	Vector vProduct = origCache.loadCacheByActive("MainProduct");
	Vector vPriority = (Vector)origCache.getNaosCacheDataMs("ALL_ALL_ALL", 61);
	Vector vOverSLA = (Vector)origCache.getNaosCacheDataMs("ALL_ALL_ALL", 58);
	Vector vRole = (Vector)origCache.getRoleReAssign();
%>
<span id="errorMessages"></span>
<div class="nav-inbox" id="div-screen-reassign">
	<div class="PanelFirst">
		<div class="PanelSecond TextHeaderNormal">
			<%=SearchHandler.DisplayErrorMessage(request)%>
			<div class="PanelThird">
				<fieldset class="field-set">
				<legend>Search Criteria</legend>
					<table class="FormFrame">
						<tr>
                        	<td class="textR"><%=MessageResourceUtil.getTextDescription(request, "APPLICATION_NO")%> :</td>
                            <td class="textL"><%=HTMLRenderUtil.displayInputTagScriptAction(searchDataM.getApplicationNo(),displayMode,"","application_no","textbox","","20") %></td>
                            <td class="textR"><%=MessageResourceUtil.getTextDescription(request, "OVER_SLA")%> :</td>
                            <td><%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(vOverSLA, searchDataM.getOverSLA(),"overSLA",displayMode,"")%></td>
                            <td class="textR" ><%=MessageResourceUtil.getTextDescription(request, "PRODUCT")%><font color="#FF0000">*</font> :</td>
                            <td class="textL"><%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(vProduct,searchDataM.getProductCode(),"product",displayMode,"") %></td>
                        </tr>
                        <tr height="25">
                        	<td class="textR"><%=MessageResourceUtil.getTextDescription(request, "FIRST_NAME_CUST_TH")%> :</td>
                            <td class="textL"><%=HTMLRenderUtil.displayInputTagScriptAction(searchDataM.getCustomerName(),displayMode,"","firstname","textbox","onblur=\"countChar2(this)\"","120") %></td>
                            <td class="textR"><%=MessageResourceUtil.getTextDescription(request, "LAST_NAME_CUST_TH")%> :</td>
                            <td><%=HTMLRenderUtil.displayInputTagScriptAction(searchDataM.getCustomerLName(),displayMode,"","lastname","textbox","onblur=\"countChar2(this)\"","50") %></td>
                            <td class="textR"><%=MessageResourceUtil.getTextDescription(request, "ID_NO")%> :</td>
                            <td><%=HTMLRenderUtil.displayInputTagScriptAction(searchDataM.getCitizenID(),displayMode,"","citizen_id","textbox","","20") %></td>
                        </tr>
                        <tr height="25">
                            <td class="textR"><%=MessageResourceUtil.getTextDescription(request, "EMPLOYEE_FIRST_NAME")%> :</td>
                            <td class="textL"><%=HTMLRenderUtil.displayInputTagScriptAction(searchDataM.getEmpFirstName(),displayMode,"","empFirstName","textbox","onblur=\"countChar2(this)\"","120") %></td>
                            <td class="textR"><%=MessageResourceUtil.getTextDescription(request, "EMPLOYEE_LAST_NAME")%> :</td>
                            <td><%=HTMLRenderUtil.displayInputTagScriptAction(searchDataM.getEmpLastName(),displayMode,"","empLastName","textbox","onblur=\"countChar2(this)\"","50") %></td>
                            <td class="textR"><%=MessageResourceUtil.getTextDescription(request, "EMPLOYEE_ID")%> :</td>
                            <td><%=HTMLRenderUtil.displayInputTagScriptAction(searchDataM.getEmpId(),displayMode,"","empId","textbox","","20") %></td>
                        </tr>
                        <tr height="25">
                            <td class="textR"><%=MessageResourceUtil.getTextDescription(request, "PRIORITY")%> :</td>
                            <td><%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(vPriority, searchDataM.getPriority(),"SearchPriority",displayMode,"")%></td>
                        	<%if(ORIGLogic.superReassignSearch(current_role)){ %>
	                           <td class="textR"><%=MessageResourceUtil.getTextDescription(request,"ROLE")%> :</td>
	                           <td><%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(vRole, searchDataM.getRole(),"role_reassign",displayMode,"")%></td>
	                           <td class="textR"></td>
	                           <td></td>      
                            <%}else{%> 
                               <td></td> 
                               <td></td> 
                               <td></td> 
                               <td></td> 
                            <%}%>         
                        </tr>
                        <tr height="25">
                            <td colspan="3">&nbsp;</td>
                            <td valign="top"><input type="button" id="button-search" class="button" value="Search" onClick="javascript:searchReassign()" /></td>
                        </tr>
					</table>
				</fieldset>
			</div>
			<div class="PanelThird">
				<table class="TableFrame">
				<%Vector valueList = VALUE_LIST.getResult();
				if((valueList!=null)&&(valueList.size()>1)){%>
					<tr height="30">
	                    <td class="textL" nowrap="nowrap" colspan="12">Total Records Found : <%=VALUE_LIST.getCount() %></td>
	                </tr>
				<%}else{%>
					<tr height="30">
	                    <td class="textL" nowrap="nowrap" colspan="12">Total Records Found : 0</td>
	                </tr>
				<%}%>
                    <tr class="Header">
                        <td><input type="checkbox" name="checkBoxAll" onclick="checkboxAll('checkBoxAll')" id="checkBoxAll"></td>
                        <td><%=MessageResourceUtil.getTextDescription(request, "APP_JOB_TYPE")%></td>
                        <td><%=MessageResourceUtil.getTextDescription(request, "PRIORITY")%></td>
                        <td><%=MessageResourceUtil.getTextDescription(request, "APPLICATION_NO")%></td>
                        <td><%=MessageResourceUtil.getTextDescription(request, "APPLICATION_STATUS")%></td>
                        <td><%=MessageResourceUtil.getTextDescription(request, "IN_DECREASE_FLAG")%></td>       
                        <td><%=MessageResourceUtil.getTextDescription(request, "FULL_NAME")%></td>
                        <td><%=MessageResourceUtil.getTextDescription(request, "ID_NO")%></td>
                        <td><%=MessageResourceUtil.getTextDescription(request, "PRODUCT")%></td>
                        <td><%=MessageResourceUtil.getTextDescription(request, "SYSTEM_ACCEPT_DATE")%></td>
                        <td><%=MessageResourceUtil.getTextDescription(request, "UPDATE_DATE")%></td>
                        <td><%=MessageResourceUtil.getTextDescription(request, "OWNER")%></td>
                    	<td><%=MessageResourceUtil.getTextDescription(request, "CLAIM_BY")%></td>
					</tr>
                    <%
                    	if((valueList!=null)&&(valueList.size()>1)){
                    	WorkflowTool wt = new WorkflowTool();                    	
						for(int i=1; i<valueList.size(); i++){
							Vector elementList = (Vector)valueList.get(i);
							String styleTr = ((i-1)%2==0)?"ResultEven":"ResultOdd";
							if(OrigConstant.FLAG_Y.equals((String) elementList.elementAt(15))){
								styleTr = "ResultBlue";
							}
					%>
							<tr class="Result-Obj <%=styleTr%>">
								<td>
								<%if(!OrigUtil.isEmptyString((String) elementList.elementAt(10))){%>
									<input type="checkbox" disabled="disabled">
								<%}else{%>
				                    <%=HTMLRenderUtil.displayCheckBoxTagDesc((String) elementList.elementAt(11),"",displayMode,"check_apprecid","","","checkBoxAll")%>
				                	<%if(OrigConstant.ROLE_SP.equals(current_role)){%>
				                		<%=HTMLRenderUtil.displayHiddenField((String)elementList.elementAt(13),"role-"+(String)elementList.elementAt(11))%>
				                		<%=HTMLRenderUtil.displayHiddenField((String)elementList.elementAt(14),"ptid-"+(String)elementList.elementAt(11))%>
				                	<%}%>
				                <%}%>
								</td>
				                <td><%=HTMLRenderUtil.displayTypeColor((String) elementList.elementAt(12)) %></td>
				                <td><%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(1)) %></td>
				                <td><%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(2)) %></td>
				                <td><%=HTMLRenderUtil.displayHTML(wt.GetMessageAppStatus(request, (String) elementList.elementAt(3))) %></td>
				                <td><%=HTMLRenderUtil.displayFlagYesNo((String) elementList.elementAt(15)) %></td>
				                <td><%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(4)) %></td>
				                <td><%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(5)) %></td>
				                <td><%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(6)) %></td>
				                <td><%=DataFormatUtility.stringDateTimeValueListForThai((String) elementList.elementAt(7)) %></td>
				                <td><%=DataFormatUtility.stringDateTimeValueListForThai((String) elementList.elementAt(8)) %></td>
				                <td><%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(9)) %></td>
				                <td>
				                	<%=HTMLRenderUtil.displayHTML(HTMLRenderUtil.displayThFullName((String) elementList.elementAt(10), vUser))%>
				                </td>				                
							</tr>									  		
						<%}%>
					<%}else{%>
                  	  <tr class="ResultNotFound"><td colspan="13" >No record found</td></tr>
                	<%}%>
				</table>
				<div class="PanelValueList">			
					<jsp:include page="../appform/pl/valueList.jsp" flush="true" />
				</div>
			</div>
			<div class="PanelThird">
				<table class="TableFrame">
					<tr align="center">
                    	<td class="textR" width="50%"><%=MessageResourceUtil.getTextDescription(request, "REASSIGN_TO")%> :</td>
						<td align="left" width="200">
							<%=HTMLRenderUtil.DisplayPopUpUserName("",displayMode,"userName","textbox","120","reassignTo") %>							
						</td>
                        <td align="left"><input type="button" value="Re-assign" class="button" onclick="SaveReassign()"/></td>
                    </tr>
                    <tr align="center">
	                   	<td class="textR" width="50%"><%=MessageResourceUtil.getTextDescription(request, "SEND_TO_CQ")%> :</td>
	                	<td align="left"><input type="button" class="button" value="Re-allocate" onclick="reallocate()"></td>
	                	<td></td>
	            	</tr>
	        	</table>
			</div>
		</div>
	</div>
</div>
<%=HTMLRenderUtil.displayHiddenField("", "searchType")%>
<%=HTMLRenderUtil.displayHiddenField(ORIGUser.getCurrentRole(),"role_action")%>
