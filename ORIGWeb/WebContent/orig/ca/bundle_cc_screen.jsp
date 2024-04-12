<%@page import="com.eaf.orig.ulo.pl.formcontrol.view.form.SearchHandler"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.Vector"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.PLMessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.cache.data.CacheDataM" %>
<%@ page import="com.eaf.orig.ulo.pl.model.app.search.PLSearchDataM" %>
<%@page import="com.eaf.orig.ulo.pl.app.utility.DataFormatUtility"%>
<%@page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.search.PLSearchDataM" %>
<%@ page import="com.eaf.j2ee.pattern.util.ErrorUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.WorkflowTool"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"/>
<jsp:useBean id="VALUE_LIST" scope="session" class="com.eaf.orig.shared.model.ValueListM" />
<%  
	ErrorUtil errUtil = new ErrorUtil();
	PLSearchDataM searchBundleCCDataM = (PLSearchDataM) request.getSession().getAttribute("SEARCH_BUNDLE_CC_DATAM");
	org.apache.log4j.Logger log=org.apache.log4j.Logger.getLogger("orig/ca/bundle_cc_screen.jsp");
	String displayMode = HTMLRenderUtil.DISPLAY_MODE_EDIT;
	WorkflowTool workflowTool = new WorkflowTool();
%>
<script type="text/javascript" src="orig/js/inbox/pl/ca.bundle.cc.js"></script>
<div class="nav-inbox">
	<div class="PanelFirst">
		<div class="PanelSecond TextHeaderNormal">
			<%=SearchHandler.DisplayErrorMessage(request)%>
			<div class="PanelThird">
				<fieldset class="field-set">
                	<legend>Search Criteria</legend>
                   	<table class="FormFrame">
                   		<tr>
	 						<td width="16.5%" class="textR text-nomal-detail"><%=PLMessageResourceUtil.getTextDescription(request, "APPLICATION_NO") %> :&nbsp;</td>
	 						<td width="16.7%" class="textL"><%=HTMLRenderUtil.displayInputTagScriptAction(searchBundleCCDataM.getApplicationNo(),displayMode,"","appNo","textbox","","20") %></td>
	 						<td width="16.7%" class="textR text-nomal-detail"><%=PLMessageResourceUtil.getTextDescription(request, "REFERENCE_NO")%> :&nbsp;</td>
	 						<td width="16.7%" class="textL"><%=HTMLRenderUtil.displayInputTagScriptAction(searchBundleCCDataM.getRefNo(),displayMode,"","refNo","textbox200","","30") %></td>
	 						<td width="16.7%" class="textR text-nomal-detail"><%=PLMessageResourceUtil.getTextDescription(request, "IDENTIFICATION_NO")%> :&nbsp;</td>
	 						<td width="16.7%" class="textL"><%=HTMLRenderUtil.displayInputTagScriptAction(searchBundleCCDataM.getCitizenID(),displayMode,"","idNo","textbox","","20") %></td>
	 					</tr>
                   		<tr>
	 						<td width="16.5%" class="textR text-nomal-detail"><%=PLMessageResourceUtil.getTextDescription(request, "THAI_FNAME") %> :&nbsp;</td>
	 						<td width="16.7%" class="textL"><%=HTMLRenderUtil.displayInputTagScriptAction(searchBundleCCDataM.getFirstName(),displayMode,"","firstName","textbox","onblur=\"countFirstNameChar(this)\"","120") %></td>
	 						<td width="16.7%" class="textR text-nomal-detail"><%=PLMessageResourceUtil.getTextDescription(request, "SURNAME_THAI")%> :&nbsp;</td>
	 						<td width="16.7%" class="textL"><%=HTMLRenderUtil.displayInputTagScriptAction(searchBundleCCDataM.getLastName(),displayMode,"","lastName","textbox","onblur=\"countLastNameChar(this)\"","50") %></td>
	 						<td></td>
	 						<td></td>
	 					</tr>
	 					<tr height="25">
	 						<td colspan="6"></td>
	 					</tr>
                        <tr height="25">
                            <td colspan="5" valign="top" align="center"><input type="button" id="button-search" name="SearchBundleCCBtn" value="<%=PLMessageResourceUtil.getTextDescription(request, "SEARCH") %>" onClick="searchApp()" class="button"></td>
                        </tr>
                	</table>
            	</fieldset>	
			</div>
			<div class="PanelThird">
				<table class="TableFrame">
					<tr>
						<td colspan="10" class="textL text-nomal-detail"><%=PLMessageResourceUtil.getTextDescription(request, "TOTAL_RECORD_FOUND")%> : <%=VALUE_LIST.getCount()%></td>
					</tr>
					<tr class="Header">
						<td width="7%"><%=PLMessageResourceUtil.getTextDescription(request, "SALE_TYPE")%></td>
						<td width="7%"><%=PLMessageResourceUtil.getTextDescription(request, "PRIORITY")%></td>
						<td width="11%"><%=PLMessageResourceUtil.getTextDescription(request, "APPLICATION_NO")%></td>
						<td width="11%"><%=PLMessageResourceUtil.getTextDescription(request, "APP_STATUS")%></td>
						<td width="11%"><%=PLMessageResourceUtil.getTextDescription(request, "NAME_SURNAME")%></td>
						<td width="11%"><%=PLMessageResourceUtil.getTextDescription(request, "ID_NO")%></td>
						<td width="7%"><%=PLMessageResourceUtil.getTextDescription(request, "PRODUCT")%></td>
						<td width="7%"><%=PLMessageResourceUtil.getTextDescription(request, "DATE_RECEIVE_APP")%></td>
						<td width="7%"><%=PLMessageResourceUtil.getTextDescription(request, "LAST_UPDATE_DATE")%></td>
						<td width="7%"><%=PLMessageResourceUtil.getTextDescription(request, "LAST_UPDATE_BY")%></td>
					</tr>
			 	<%
				Vector valueList = VALUE_LIST.getResult(); 
				request.getSession().setAttribute("resultSearchVec",valueList);
				if(valueList != null && valueList.size() > 1){
					for(int i=1;i<valueList.size();i++){
						String styleTr = ((i+1)%2==0)?"ResultEven":"ResultOdd";
						String spanClass = "BigtodotextBlackC";
						Vector elementList = (Vector)valueList.get(i);
				%>
			 	
		 			<tr class="Result-Obj <%=styleTr%>" id="<%=elementList.elementAt(1)%>">
						<td><span class="<%=spanClass%>"><%=HTMLRenderUtil.displaySaleTypeCash((String) elementList.elementAt(2),null)%></span></td>
                        <td><span class="<%=spanClass%>"><%=HTMLRenderUtil.displayHTML(HTMLRenderUtil.displayPriority(Integer.parseInt((String) elementList.elementAt(4))))%></span></td>
                        <td><span class="<%=spanClass%>"><%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(5))%></span></td>
                        <td><span class="<%=spanClass%>"><%=HTMLRenderUtil.displayHTML(workflowTool.GetMessageAppStatus(request,(String) elementList.elementAt(6))) %></span></td>
                        <td><span class="<%=spanClass%>"><%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(7))%></span></td>
                        <td><span class="<%=spanClass%>"><%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(8))%></span></td>
                        <td><span class="<%=spanClass%>"><%=HTMLRenderUtil.DisplayProduct((String) elementList.elementAt(9))%></span></td>
                        <td><span class="<%=spanClass%>"><%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(10))%></span></td>
                        <td><span class="<%=spanClass%>"><%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(11))%></span></td>
                        <td><span class="<%=spanClass%>"><%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(12))%></span></td>
		 			</tr>
		 		<% } %>
			 <%}else{%>
					<tr class="ResultNotFound" id="notFoundTable">
						<td colspan="10">No record found</td>
					</tr>
			<%}%>
				</table>	
				<div class="PanelValueList">			
					<jsp:include page="../appform/pl/valueList.jsp" flush="true" />
				</div>
			</div>
		</div>
	</div>
</div>  
<input name="jobStatus" type="hidden" value="N">
<input name="appRecordID" type="hidden" value="">
<input name="appStatus" type="hidden" value="">
<input name="jobState" type="hidden" value=""> 
<input type="hidden" name="fromSearch" value="N">
<input name="roleElement" type="hidden" value="">
<script language="JavaScript">
function searchApp(){
	try{
		if(validate()){
			blockScreen();
			document.appFormName.action.value="SearchBundleCC";
			document.appFormName.handleForm.value = "N";
			document.appFormName.fromSearch.value = "Y";
			document.appFormName.submit();	
		}
	}catch(e){
		unblockScreen();
		var msg = ERROR_JS+e.message;
		alertMassageERROR(msg);
	}
}
function validate(){
	if(!countFirstNameChar(appFormName.firstName)){
		return false;
	}
	
	if(!countLastNameChar(appFormName.lastName)){
		return false;
	}
	return true;
}
function countFirstNameChar(word){
   var  textBoxValue=word.value;
	if(textBoxValue.length<2 && textBoxValue.length !=0){
	    var errorMsg = "<%=errUtil.getShortErrorMessage(request,"CHAR_MORE_2")%>";
		alertMassageSelection(errorMsg,word.id);
		return false;
	}
	return true;
}
function countLastNameChar(word){
   var  textBoxValue=word.value;
	if(textBoxValue.length<2 && textBoxValue.length !=0){
		var errorMsg = "<%=errUtil.getShortErrorMessage(request,"CHAR_MORE_2")%>";
		alertMassageSelection(errorMsg,word.id);
		return false;
	}
	return true;
}
</script>
