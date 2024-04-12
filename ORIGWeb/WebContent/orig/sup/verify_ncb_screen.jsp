<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.Vector"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.WorkflowTool"%>
<%@ page import="com.eaf.orig.ulo.pl.formcontrol.view.form.SearchHandler"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.PLMessageResourceUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.DataFormatUtility"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="VALUE_LIST" scope="session" class="com.eaf.orig.shared.model.ValueListM"/>

<script type="text/javascript" src="orig/js/sup/verify.ncb.screen.js"></script>

<%  
	org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger("orig/sup/verify_ncb_screen.jsp");
    
    String displayMode = HTMLRenderUtil.DISPLAY_MODE_EDIT;
    
    SearchHandler HandlerM = (SearchHandler) request.getSession().getAttribute("SEARCH_DATAM");
	if(HandlerM == null){
		HandlerM = new SearchHandler();
	}
	SearchHandler.SearchDataM searchDataM = HandlerM.getSearchM();
	if(searchDataM == null){
		searchDataM = new SearchHandler.SearchDataM();
	}
	
	ORIGCacheUtil origCache = new ORIGCacheUtil();	
%>
<span id="errorMessages"></span>
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
                            <td class="textR"><%=PLMessageResourceUtil.getTextDescription(request, "ID_NO")%> :</td>
                            <td><%=HTMLRenderUtil.displayInputTagScriptAction(searchDataM.getCitizenID(),displayMode,"","citizen_id","textbox","","20") %></td>
                        </tr>
                        <tr height="25">
                        	<td class="textR"><%=PLMessageResourceUtil.getTextDescription(request, "NAME_THAI")%> :</td>
                            <td class="textL"><%=HTMLRenderUtil.displayInputTagScriptAction(searchDataM.getCustomerName(),displayMode,"","firstname","textbox","onblur=\"countChar2(this)\"","120") %></td>
                            <td class="textR"><%=PLMessageResourceUtil.getTextDescription(request, "SURNAME_THAI")%> :</td>
                            <td><%=HTMLRenderUtil.displayInputTagScriptAction(searchDataM.getCustomerLName(),displayMode,"","lastname","textbox","onblur=\"countChar2(this)\"","50") %></td>
                            
                        </tr>
                        <tr height="25">
                            <td valign="top" colspan="4" align="center"><input type="button" id="button-search" class="button" value="Search" onClick="javascript:searchVerifyNCB()" /></td>
                        </tr>
					</table>
				</fieldset>
			</div>
			<div class="PanelThird">
				<div class="scroll-div">
					<table class="TableFrame">
					<%Vector valueList = VALUE_LIST.getResult();%>
					<%if(valueList!=null&&valueList.size()>1){%>
						<tr height="30">
		                    <td class="textL" nowrap="nowrap" colspan="7">Total Records Found : <%=VALUE_LIST.getCount() %></td>
		                     <td class="textR text-label-bold" colspan="3"><%=PLMessageResourceUtil.getTextDescription(request, "CENTRAL_JOB")%> :</td>
							<td class="textL" id="cb-total-job"></td>
		                </tr>
					<%}else{%>
						<tr height="30">
		                    <td class="textL" nowrap="nowrap" colspan="7">Total Records Found : 0</td>
		                    <td class="textR text-label-bold" colspan="3"><%=PLMessageResourceUtil.getTextDescription(request, "CENTRAL_JOB")%> :</td>
							<td class="textL" id="cb-total-job"></td>
		                </tr>
					<%}%>
	                    <tr class="Header">
	                         <td><%=PLMessageResourceUtil.getTextDescription(request, "ACTION")%></td>
	                         <td><%=PLMessageResourceUtil.getTextDescription(request, "RECEIVE_JOB_DATE")%></td>
	                         <td><%=PLMessageResourceUtil.getTextDescription(request, "PRIORITY")%></td>
	                         <td><%=PLMessageResourceUtil.getTextDescription(request, "APPLICATION_NO")%></td>
	                         <td><%=PLMessageResourceUtil.getTextDescription(request, "FULL_NAME")%></td>
	                         <td><%=PLMessageResourceUtil.getTextDescription(request, "IDENTIFICATION_NO")%></td>
	                         <td><%=PLMessageResourceUtil.getTextDescription(request, "BIRTH_DATE")%></td>
	                         <td><%=PLMessageResourceUtil.getTextDescription(request, "PERSONAL_INFO_BUREAU")%></td>
	                         <td><%=PLMessageResourceUtil.getTextDescription(request, "BUREAU_REQUESTER")%></td>
	                         <td><%=PLMessageResourceUtil.getTextDescription(request, "DOCUMENT")%></td>
	                         <td><%=PLMessageResourceUtil.getTextDescription(request, "COMMENT")%></td>
						</tr>
	                    <%
	                    if((valueList!=null) && (valueList.size()>1)){
	                    	WorkflowTool wt = new WorkflowTool();
							for(int i=1; i<valueList.size(); i++){
								Vector elementList = (Vector)valueList.get(i);
								String styleTr = ((i-1)%2==0)?"ResultEven":"ResultOdd";
						%>
							<tr class="Result-Obj <%=styleTr%>" id="<%=(String) elementList.elementAt(1)%>">	
				                <td>
				                	<img src="images/decistion.png" style="cursor:pointer;" onclick="verifyAction('<%=(String)elementList.elementAt(1)%>')">
				                </td>
				                <td class="load-view-mode <%=(String) elementList.elementAt(1)%>"><%=DataFormatUtility.stringDateTimeValueListForThai((String) elementList.elementAt(2)) %></td>
				                <td class="load-view-mode <%=(String) elementList.elementAt(1)%>"><%=HTMLRenderUtil.displayHTML(wt.GetMessagePriority(request, (String) elementList.elementAt(3))) %></td>
				                <td class="load-view-mode <%=(String) elementList.elementAt(1)%>"><%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(4))%></td>
				                <td class="load-view-mode <%=(String) elementList.elementAt(1)%>"> 
				                	<%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(5))%>
				                	&nbsp;&nbsp;
				                	<%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(6))%>
				                </td>
				                <td class="load-view-mode <%=(String) elementList.elementAt(1)%>"><%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(7))%></td>
				                <td class="load-view-mode <%=(String) elementList.elementAt(1)%>"><%=DataFormatUtility.StringDateENToStringDateTHFormatYYYY_MM_DD((String) elementList.elementAt(8))%></td>
				                <td class="load-view-mode <%=(String) elementList.elementAt(1)%>"><%=HTMLRenderUtil.displayHTML(HTMLRenderUtil.displayThFullName((String) elementList.elementAt(9)))%></td>
				                <td class="load-view-mode <%=(String) elementList.elementAt(1)%>"><%=HTMLRenderUtil.displayHTML(HTMLRenderUtil.displayThFullName((String) elementList.elementAt(10)))%></td>
				                <td><img src="images/doc.png" style="cursor:pointer;" class="viewImg <%=(String) elementList.elementAt(1)%>"></td>
				                <td id="comment-<%=(String)elementList.elementAt(1)%>">
				                	<%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(11))%>
				                </td>		               
							</tr>									  		
						<%}%>                                      
						<%}else{%>
		                    <tr class="ResultNotFound">
				            	<td colspan="11" >No record found</td>
				            </tr>
	                	<%}%>
					</table>
				</div>
			</div>
			<div class="PanelValueList">			
				<jsp:include page="../appform/pl/valueList.jsp" flush="true" />
			</div>
		</div>
	</div>
</div>
<%=HTMLRenderUtil.displayHiddenField("", "searchType")%>
<%=HTMLRenderUtil.displayHiddenField("", "param-action")%>
<%=HTMLRenderUtil.displayHiddenField("", "param-comment")%>
<%=HTMLRenderUtil.displayHiddenField("", "param-apprecid")%>
<%=HTMLRenderUtil.displayHiddenField("", "appRecordID")%>
 
