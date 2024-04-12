<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="java.util.Vector"%>
<%@page import="com.eaf.orig.ulo.pl.app.utility.WorkflowTool"%>
<%@page import="com.eaf.orig.ulo.pl.app.utility.WebActionUtil"%>
<%@page import="com.eaf.orig.ulo.pl.formcontrol.view.form.SearchHandler"%>
<%@page import="com.eaf.orig.ulo.pl.app.utility.PLMessageResourceUtil"%>
<%@page import="com.eaf.orig.ulo.pl.app.utility.DataFormatUtility"%>
<%@page import="com.eaf.orig.shared.model.SearchDataM"%>
<%@page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"/>
<jsp:useBean id="VALUE_LIST" scope="session" class="com.eaf.orig.shared.model.ValueListM" />

<script type="text/javascript" src="orig/js/sup/set_priority.screen.js"></script>
<script type="text/javascript" src="js/ulo.application.javascript.js"></script>
<%  
	org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger("orig/sup/set_priority.jsp");
    log.debug("Set priority");	 
    
    String displayMode = HTMLRenderUtil.DISPLAY_MODE_EDIT;
    
	SearchHandler HandlerM = (SearchHandler) request.getSession().getAttribute("SEARCH_DATAM");
	if(HandlerM == null){
		HandlerM = new SearchHandler();
	}
	SearchHandler.SearchDataM searchDataM = HandlerM.getSearchM();
	if(searchDataM == null){
		searchDataM = new SearchHandler.SearchDataM();
	}
	if("".equals(searchDataM.getProductCode()) || null==searchDataM.getProductCode()){
		searchDataM.setProductCode(OrigConstant.PRODUCT_KEC);
	}
	
	ORIGCacheUtil origCache = ORIGCacheUtil.getInstance();
	Vector vPriority = (Vector)origCache.getNaosCacheDataMs("ALL_ALL_ALL", 61);
	Vector vUser = (Vector)origCache.loadUserNameCacheByRole(WebActionUtil.getUnderRole(ORIGUser.getCurrentRole()));
	Vector vProduct = origCache.loadCacheByActive("MainProduct");
	
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
                        	<td class="textR"><%=PLMessageResourceUtil.getTextDescription(request, "APPLICATION_NO")%> :</td>
                            <td class="textL"><%=HTMLRenderUtil.displayInputTagScriptAction(searchDataM.getApplicationNo(),displayMode,"","application_no","textbox","","20") %></td>
                            <td class="textR"><%=PLMessageResourceUtil.getTextDescription(request, "PROJECT_CODE")%> :</td>
                            <td><%=HTMLRenderUtil.displayPopUpTagNotSetScriptAction(searchDataM.getProjectCode(), displayMode,"13","project_code","textbox","onkeypress= \"return keyPressInteger(event)\"","13","...","buttonNew") %></td>
                            <td class="textR" ><%=PLMessageResourceUtil.getTextDescription(request, "PRODUCT")%><span style="color: red">*</span> :</td>
                            <td class="textL"><%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(vProduct,searchDataM.getProductCode(),"product",displayMode,"") %></td>
                        </tr>
                        <tr>
                            <td class="textR"><%=PLMessageResourceUtil.getTextDescription(request, "FIRST_NAME_CUST_TH")%> :</td>
                            <td class="textL"><%=HTMLRenderUtil.displayInputTagScriptAction(searchDataM.getCustomerName(),displayMode,"","firstname","textbox","onblur=\"countChar2(this)\"","120") %></td>
                            <td class="textR"><%=PLMessageResourceUtil.getTextDescription(request, "LAST_NAME_CUST_TH")%> :</td>
                            <td><%=HTMLRenderUtil.displayInputTagScriptAction(searchDataM.getCustomerLName(),displayMode,"","lastname","textbox","onblur=\"countChar2(this)\"","50") %></td>
                            <td class="textR"><%=PLMessageResourceUtil.getTextDescription(request, "ID_NO")%> :</td>
                            <td><%=HTMLRenderUtil.displayInputTagScriptAction(searchDataM.getCitizenID(),displayMode,"","citizen_id","textbox","","20") %></td>
                        </tr>
                        <tr>
                            <td class="textR"><%=PLMessageResourceUtil.getTextDescription(request, "EMPLOYEE_FIRST_NAME")%> :</td>
                            <td class="textL"><%=HTMLRenderUtil.displayInputTagScriptAction(searchDataM.getEmpFirstName(),displayMode,"","empFirstName","textbox","onblur=\"countChar2(this)\"","120") %></td>
                            <td class="textR"><%=PLMessageResourceUtil.getTextDescription(request, "EMPLOYEE_LAST_NAME")%> :</td>
                            <td><%=HTMLRenderUtil.displayInputTagScriptAction(searchDataM.getEmpLastName(),displayMode,"","empLastName","textbox","onblur=\"countChar2(this)\"","50") %></td>
                            <td class="textR"><%=PLMessageResourceUtil.getTextDescription(request, "EMPLOYEE_ID")%> :</td>
                        	<td><%=HTMLRenderUtil.displayInputTagScriptAction(searchDataM.getEmpId(),displayMode,"","empId","textbox","","20") %></td>
                        </tr>
                        <tr>
                            <td class="textR"><%=PLMessageResourceUtil.getTextDescription(request, "PRIORITY")%> :</td>
                            <td><%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(vPriority, searchDataM.getPriority(),"priority",displayMode,"")%></td>
                            <td colspan="3">&nbsp;</td>
                        </tr>
                        <tr>
                        	<td colspan="3">&nbsp;</td>
                            <td valign="top" colspan="3"><input type="button" id="button-search" class="button" value="Search" onClick="javascript:searchPriority()" /></td>
                        </tr>
					</table>
				</fieldset>
			</div>
			<div class="PanelThird">
				<table class="TableFrame">
					<%Vector valueList = VALUE_LIST.getResult();
					if((valueList!=null)&&(valueList.size()>1)){%>
						<tr height="30"><td class="textL" nowrap="nowrap" colspan="11">Total Records Found : <%=VALUE_LIST.getCount() %></td></tr>
					<%}else{%>
						<tr height="30"><td class="textL" nowrap="nowrap" colspan="11">Total Records Found : 0</td></tr>
					<%}%>
                    <tr class="Header">
                    	<td><input type="checkbox" name="checkBoxAll" onclick="checkboxAll('checkBoxAll')" id="checkBoxAll"></td>
                    	<td><%=PLMessageResourceUtil.getTextDescription(request, "PRIORITY")%></td>
                        <td><%=PLMessageResourceUtil.getTextDescription(request, "APPLICATION_NO")%></td>
                        <td><%=PLMessageResourceUtil.getTextDescription(request, "APPLICATION_STATUS")%></td>
                        <td><%=PLMessageResourceUtil.getTextDescription(request, "FULL_NAME")%></td>
                        <td><%=PLMessageResourceUtil.getTextDescription(request, "ID_NO")%></td>
                        <td><%=PLMessageResourceUtil.getTextDescription(request, "PRODUCT")%></td>
                        <td><%=PLMessageResourceUtil.getTextDescription(request, "SYSTEM_ACCEPT_DATE")%></td>
                        <td><%=PLMessageResourceUtil.getTextDescription(request, "UPDATE_DATE")%></td>
                        <td><%=PLMessageResourceUtil.getTextDescription(request, "OWNER")%></td>
                        <td><%=PLMessageResourceUtil.getTextDescription(request, "CLAIM_BY")%></td>
                    </tr>
					<%if((valueList!=null)&&(valueList.size()>1)){
						WorkflowTool wt = new WorkflowTool();
						for(int i=1 ; i<valueList.size() ; i++){
							Vector elementList = (Vector)valueList.get(i);
							String styleTr = ((i-1)%2==0)?"ResultEven":"ResultOdd";%>
					<tr class="Result-Obj <%=styleTr%>" id="<%=(String) elementList.elementAt(11)+"|"+(String) elementList.elementAt(2)%>">
		            	<td>
		                <%if((String) elementList.elementAt(10)==null){%>
		                	<%=HTMLRenderUtil.displayCheckBoxTagDesc((String) elementList.elementAt(11)+"|"+(String) elementList.elementAt(2),"",displayMode,"checkAppRecId","","","checkBoxAll") %>
		                <%}else{%>
		                	<input type="checkbox" value="<%=(String) elementList.elementAt(11)+"|"+(String) elementList.elementAt(2) %>" name="checkAppRecIdDisable" disabled="disabled">
		                <%}%>
		                </td>
		                <td><%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(1)) %></td>
		                <td><%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(2)) %></td>
		                <td><%=HTMLRenderUtil.displayHTML(wt.GetMessageAppStatus(request, (String) elementList.elementAt(3))) %></td>
		                <td><%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(4)) %></td>
		                <td><%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(5)) %></td>
		                <td><%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(6)) %></td>
		                <td><%=DataFormatUtility.stringDateTimeValueListForThai((String) elementList.elementAt(7)) %></td>
		                <td><%=DataFormatUtility.stringDateTimeValueListForThai((String) elementList.elementAt(8)) %></td>
		                <td><%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(9)) %></td>
		                <td><%=HTMLRenderUtil.displayHTML(HTMLRenderUtil.displayThFullName((String) elementList.elementAt(10), vUser)) %></td>
		                <%=HTMLRenderUtil.displayHiddenField("check_"+(String) elementList.elementAt(11), (String) elementList.elementAt(10)) %>
					</tr>									  		
					<%}}else{%>
	                    <tr class="ResultNotFound">
			            	<td colspan="12" >No record found</td>
			            </tr>
                    <%}%>
				</table>
				<div class="PanelValueList">			
					<jsp:include page="../appform/pl/valueList.jsp" flush="true" />
				</div>
			</div>
			<div class="PanelThird">
				<table class="TableFrame">
					<tr align="center">
                    	<td class="textR" width="50%"><%=PLMessageResourceUtil.getTextDescription(request, "PRIORITY")%> :</td>
                        <td width="80" align="left">&nbsp;<%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(vPriority, searchDataM.getSetPriority(),"setPriority",displayMode,"")%></td>
                        <td align="left" valign="top">&nbsp;<input type="button" value="Save" class="button" onclick="savePriority()"/></td>
                    </tr>
				</table>
			</div>
		</div>
	</div>
</div>
<%=HTMLRenderUtil.displayHiddenField("", "clickSearch") %>
<%=HTMLRenderUtil.displayHiddenField("", "searchType") %>
