<%@page import="com.eaf.orig.ulo.pl.formcontrol.view.form.SearchHandler"%>
<%@page import="com.eaf.j2ee.pattern.util.ErrorUtil"%>
<%@page import="com.eaf.orig.ulo.pl.app.utility.WorkflowTool"%>
<%@page import="com.eaf.orig.ulo.pl.model.app.search.PLSearchDataM"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.Vector"%>
<%@page import="com.eaf.orig.shared.model.ProcessMenuM"%>
<%@page import="com.eaf.orig.ulo.pl.constant.WorkflowConstant"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.DataFormatUtility"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.ORIGJobDataM"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.ORIGInboxDataM"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.PLMessageResourceUtil"%>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil"%>
<%@ page import="com.eaf.orig.shared.util.OrigUtil"%>
<%@ page import="java.util.Properties"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler" /> 
<jsp:useBean id="SEARCH_DC_CASHDAY5_DATAM" scope="session" class="com.eaf.orig.ulo.pl.model.app.search.PLSearchDataM"/>
<jsp:useBean id="VALUE_LIST" scope="session" class="com.eaf.orig.shared.model.ValueListM"/>
<%  
	org.apache.log4j.Logger log=org.apache.log4j.Logger.getLogger("orig/dc/search_cashday5_screen.jsp");
    log.debug("DC Search Bundle Screen");	 

    Vector resultVect = VALUE_LIST.getResult();   
    String displayMode = HTMLRenderUtil.DISPLAY_MODE_EDIT;
    WorkflowTool wfTool = new WorkflowTool();
%>
<script type="text/javascript" src="orig/js/inbox/pl/dc_cashday5.search.js"></script>
<div class="nav-inbox">
	<div class="PanelFirst">
		<div class="PanelSecond TextHeaderNormal">
			<%=SearchHandler.DisplayErrorMessage(request)%>
			<div class="PanelThird">
			<fieldset class="field-set">
				<legend>Search Criteria</legend>
				<table class="FormFrame">
					<tr>
						<td class="textR" width="20%"><%=PLMessageResourceUtil.getTextDescription(request, "APPLICATION_NO")%> :</td>
						<td class="textL" width="10%" id="button-job-status"><%=HTMLRenderUtil.displayInputTagScriptAction(SEARCH_DC_CASHDAY5_DATAM.getApplicationNo(), displayMode, "", "application_no", "textbox", "", "20")%></td>
						<td width="40%" class="textR"><%=PLMessageResourceUtil.getTextDescription(request, "ID_NO")%> :</td>
						<td width="20%"><%=HTMLRenderUtil.displayInputTagScriptAction(SEARCH_DC_CASHDAY5_DATAM.getCitizenID(), displayMode, "", "citizen_id", "textbox", "", "20")%></td>
						<td width="10%">&nbsp;</td>
					</tr>	
					<tr>
						<td class="textR" width="20%"><%=PLMessageResourceUtil.getTextDescription(request, "NAME_THAI")%> :</td>
						<td class="textL" width="10%" id="button-job-status"><%=HTMLRenderUtil.displayInputTagScriptAction(SEARCH_DC_CASHDAY5_DATAM.getFirstName(), displayMode, "", "first_name", "textbox", "", "120")%></td>
						<td width="20%" class="textR"><%=PLMessageResourceUtil.getTextDescription(request, "SURNAME_THAI")%> :</td>
						<td width="20%"><%=HTMLRenderUtil.displayInputTagScriptAction(SEARCH_DC_CASHDAY5_DATAM.getLastName(), displayMode, "", "last_name", "textbox", "", "50")%></td>
						<td width="30%"></td>
					</tr>	
					<tr><td colspan="5" align="center"><input type="button" id="button-search" class="button" value="Search" onClick="javascript:searchCashDay5()" />&nbsp;&nbsp;
					</tr>								 		
				</table>
			</fieldset>		
			</div>
				<div class="PanelThird">
				<table class="TableFrame">
				   <%if(!OrigUtil.isEmptyVector(resultVect)){%>
				     <tr>
				   		 <td colspan="8" align="right" class="textR"> Total Records Found :<%=DataFormatUtility.displayIntegerToString(resultVect.size()-1) %></td>
				  	 </tr>
				   <%}%>
					<tr class="Header">						 
						<td><%=PLMessageResourceUtil.getTextDescription(request, "APPLICATION_NO")%></td>
						<td><%=PLMessageResourceUtil.getTextDescription(request, "APP_STATUS")%></td>
						<td><%=PLMessageResourceUtil.getTextDescription(request, "NAME_SURNAME")%></td>
						<td><%=PLMessageResourceUtil.getTextDescription(request, "ID_NO")%></td>
						<td><%=PLMessageResourceUtil.getTextDescription(request, "PRODUCT")%></td>
						<td><%=PLMessageResourceUtil.getTextDescription(request, "DATE_RECEIVE_APP")%></td>
					</tr>
			 <%if(!OrigUtil.isEmptyVector(resultVect)){%>
			 	
			 	<%
			 	for(int i=1; i<resultVect.size(); i++){
		 		 	Vector elementList = (Vector)resultVect.get(i); 
		 		 	ORIGJobDataM jobModel =new ORIGJobDataM();
		 		 	StringBuilder style = new StringBuilder();
		 		 		style.append((i%2==0)?"ResultEven":"ResultOdd");
		 		 		style.append(" ").append(("O".equals(jobModel.getFlagAppTime())?"text-red":"text-black"));
		 		 	String  appRecId= (String) elementList.elementAt(1);	
		 		 	String  applicationNo= (String) elementList.elementAt(2);
		 		 	String  applicationStatus= (String) elementList.elementAt(3);
		 		 	String  custName= (String) elementList.elementAt(4);
		 		 	String  idNo= (String) elementList.elementAt(5);
		 		 	String  product= (String) elementList.elementAt(6);
		 		    String  receiveDate= (String) elementList.elementAt(7);
			 	%>			 	
		 			<tr class="Result-Obj <%=style.toString()%>" id="<%=appRecId%>">		 			 
		 				<td><%=HTMLRenderUtil.displayHTML(applicationNo)%></td>
		 				<td><%=HTMLRenderUtil.displayHTML(wfTool.GetMessageAppStatus(request,applicationStatus))%></td>
		 				<td><%=HTMLRenderUtil.displayHTML(custName) %></td>
		 				<td><%=HTMLRenderUtil.displayHTML(idNo)%></td>
						<td><%=HTMLRenderUtil.displayHTML(HTMLRenderUtil.DisplayProduct(product)) %></td>
		 				<td><%=HTMLRenderUtil.displayHTML(receiveDate) %></td>		 				 
		 			</tr>
			 	<%}%>
			 <%}else{%>
					<tr class="ResultNotFound">
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
<script>
var SELECT_MORE_1_FIELD = "<%=ErrorUtil.getShortErrorMessage(request, "SELECT_MORE_1_FIELD")%>";
function cashDay5validate(){
    var citizen_id 	= appFormName.citizen_id.value;
	var application_no 	=  appFormName.application_no.value;
	var firstname =  appFormName.first_name.value;
	var lastname = appFormName.last_name.value;
    if ((application_no=="") && (citizen_id=="") && (firstname=="") && (lastname=="")){
		alertMassage(SELECT_MORE_1_FIELD);
		return false;
	}
// 	septem comment change logic validate countChar2
// 	 else {
// 	   if(firstname!="" && countChar2(appFormName.first_name)){
// 	      return false;
// 	    }else if(lastname!="" && countChar2(appFormName.last_name)){
// 	    return false;
// 	    }
	    
// 		return true;
// 	}

//	#septem validate countChar2
	if(!countChar2(appFormName.first_name)){
		return false;
	}
	if(!countChar2(appFormName.last_name)){
		return false;
	}
//	end #septem validate countChar2
	return true;
}
function countChar2(word){
// septem comment change logic countChar2
//    var  textBoxValue=word.value;
// 	if(textBoxValue.length<2 && textBoxValue.length !=0){
// 		alertMassage(CHAR_MORE_2);
// 		setCaretPosition(word);
// 		return true;
// 	}
// 	return false;
	var textBoxValue = word.value;
	if(null != textBoxValue && textBoxValue.length<2 && textBoxValue.length !=0){
		alertMassageFuncParam1(CHAR_MORE_2, setCaretPosition, word);
		return false;
	}
	return true;
}
function setCaretPosition(text){
//#septem comment change logic setCaretPosition
// 	var textLength = text.value.length;
// 	if(text.setSelectionRange){
// 		text.focus();
// 		text.setSelectionRange(textLength,textLength);
// 	}
// 	else if (text.createTextRange) {
// 		var range = text.createTextRange();
// 		range.collapse(true);
// 		range.moveEnd('character', textLength);
// 		range.moveStart('character', textLength);
// 		range.select();
// 	}
	$('#'+$(text).attr('id')).focus();
	$('#'+$(text).attr('id')).setCursorToTextEnd();
}
</script>