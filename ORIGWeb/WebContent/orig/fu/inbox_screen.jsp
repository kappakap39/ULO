<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.formcontrol.view.form.SearchHandler"%>
<%@ page import="java.util.Vector"%>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.WorkflowTool"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="SearchM" scope="session" class="com.eaf.orig.shared.search.SearchM"/>

<% 	
	org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(this.getClass());
	String displayMode = HTMLRenderUtil.DISPLAY_MODE_EDIT;
	WorkflowTool workflowTool = new WorkflowTool();
%>

<script type="text/javascript" src="orig/js/inbox/pl/fu.inbox.js"></script>
<script type="text/javascript" src="js/popcalendar-screen.js"></script>

<div class="nav-inbox">
	<div class="PanelFirst">
		<div class="PanelSecond TextHeaderNormal">
		<%=SearchHandler.DisplayErrorMessage(request)%>
			<div class="PanelThird">
				<fieldset class="field-set">
                	<legend>Search Criteria</legend>
                   	<table class="FormFrame">
                   		<tr>
	 						<td width="16.5%" class="textR text-nomal-detail"><%=MessageResourceUtil.getTextDescription(request, "THAI_FNAME") %> :&nbsp;</td>
	 						<td width="16.7%" class="textL"><%=HTMLRenderUtil.displayInputTagScriptAction(SearchM.getString("THAI_FNAME"),displayMode,"","firstName","textbox","onblur=\"countFirstNameChar(this)\"","120") %></td>
	 						<td width="16.7%" class="textR text-nomal-detail"><%=MessageResourceUtil.getTextDescription(request, "SURNAME_THAI")%> :&nbsp;</td>
	 						<td width="16.7%" class="textL"><%=HTMLRenderUtil.displayInputTagScriptAction(SearchM.getString("SURNAME_THAI"),displayMode,"","lastName","textbox","onblur=\"countLastNameChar(this)\"","50") %></td>
	 						<td width="16.7%" class="textR text-nomal-detail"><%=MessageResourceUtil.getTextDescription(request, "IDENTIFICATION_NO")%> :&nbsp;</td>
	 						<td width="16.7%" class="textL"><%=HTMLRenderUtil.displayInputTagScriptAction(SearchM.getString("IDENTIFICATION_NO"),displayMode,"","idNo","textbox","","20") %></td>
	 					</tr>
                    	<tr>
							<td class="textR text-nomal-detail"><%=MessageResourceUtil.getTextDescription(request, "APP_DATE_FROM")%>&nbsp;:&nbsp;</td>
                            <td class="textL"><%=HTMLRenderUtil.displayInputTagJsDate("",SearchM.getString("APP_DATE_FROM"),displayMode,"8","app_date_from","textbox","right","")%></td>
                            <td class="textR text-nomal-detail"><%=MessageResourceUtil.getTextDescription(request, "APP_DATE_TO")%>&nbsp;:&nbsp;</td>
                            <td class="textL"><%=HTMLRenderUtil.displayInputTagJsDate("",SearchM.getString("APP_DATE_TO"), displayMode, "8", "app_date_to", "textbox","left", "")%></td>
                            <td class="textR text-nomal-detail"><%=MessageResourceUtil.getTextDescription(request, "APPLICATION_NO")%> :&nbsp;</td>
                        	<td class="textL"><%=HTMLRenderUtil.displayInputTagScriptAction(SearchM.getString("APPLICATION_NO"),displayMode,"","appNo","textbox","","20") %></td>
			  			</tr>
			  			<tr>
		 					<td align="right" class="text-nomal-detail"><%=MessageResourceUtil.getTextDescription(request, "FU_DATE_FROM")%>&nbsp;:&nbsp;</td>
                            <td class="text-nomal-detail"><%=HTMLRenderUtil.displayInputTagJsDate("",SearchM.getString("FU_DATE_FROM"), displayMode, "8", "fu_date_from", "textbox","right", "")%></td>
                            <td align="right" class="text-nomal-detail"><%=MessageResourceUtil.getTextDescription(request, "FU_DATE_TO")%>&nbsp;:&nbsp;</td>
                            <td class="text-nomal-detail"><%=HTMLRenderUtil.displayInputTagJsDate("",SearchM.getString("FU_DATE_TO"), displayMode, "8", "fu_date_to", "textbox","left", "")%></td>
                            <td></td>
                            <td></td>			 				 	
		  				</tr>
                        <tr height="25">
                            <td colspan="6" valign="top" align="center"><input type="button" id="button-search" name="SearchFUInboxBtn" value="<%=MessageResourceUtil.getTextDescription(request, "SEARCH") %>" onClick="searchApp()" class="button"></td>
                        </tr>
                	</table>
            	</fieldset>	
			</div>
			<div class="PanelThird">
				<table class="TableFrame">
					<tr class="Header">
						<td width="7%"><%=MessageResourceUtil.getTextDescription(request, "DATE_RECEIVE_WORK")%></td>
						<td width="7%"><%=MessageResourceUtil.getTextDescription(request, "PRIORITY")%></td>
						<td width="11%"><%=MessageResourceUtil.getTextDescription(request, "APPLICATION_NO")%></td>
						<td width="11%"><%=MessageResourceUtil.getTextDescription(request, "APP_STATUS")%></td>
						<td width="7%"><%=MessageResourceUtil.getTextDescription(request, "VETO")%></td>
						<td width="11%"><%=MessageResourceUtil.getTextDescription(request, "NAME_SURNAME")%></td>
						<td width="11%"><%=MessageResourceUtil.getTextDescription(request, "ID_NO")%></td>
						<td width="7%"><%=MessageResourceUtil.getTextDescription(request, "PRODUCT")%></td>
						<td width="7%"><%=MessageResourceUtil.getTextDescription(request, "DATE_RECEIVE_APP")%></td>
						<td width="7%"><%=MessageResourceUtil.getTextDescription(request, "LAST_FU")%></td>
						<td width="7%"><%=MessageResourceUtil.getTextDescription(request, "LAST_FU_DATE")%></td>
						<td width="7%"><%=MessageResourceUtil.getTextDescription(request, "FU_COUNT")%></td>
						<td width="7%"><%=MessageResourceUtil.getTextDescription(request, "FU_REJECT_DATE")%></td>
					</tr>
			 	<%
				Vector<Vector<String>> searchM = SearchM.getResult();
				if(searchM != null && searchM.size() > 0){
					for(int i=0;i<searchM.size();i++){					
						Vector<String> elementList = searchM.get(i);
						String trStyle = ((i+1)%2==0)?"ResultEven":"ResultOdd";
						if(OrigConstant.ApplicationStatus.RECEIVED_DOCUMENT.equals((String) elementList.elementAt(5))){
							trStyle = "ResultBlue";
						}
						String divStyle = "BigtodotextBlackC";
						if(OrigConstant.FLAG_Y.equals((String)elementList.elementAt(14))){
							divStyle = "BigtodotextRedC";
						}
				%>			 	
			 			<tr class="Result-Obj <%=trStyle%>" id="<%=elementList.elementAt(1)%>">
							<td><div class="<%=divStyle%>"><%=HTMLRenderUtil.displayHTML(elementList.elementAt(2))%></div></td>
	                        <td><div class="<%=divStyle%>"><%=HTMLRenderUtil.displayHTML(HTMLRenderUtil.displayPriority(elementList.elementAt(3)))%></div></td>
	                        <td><div class="<%=divStyle%>"><%=HTMLRenderUtil.displayHTML(elementList.elementAt(4))%></div></td>
	                        <td><div class="<%=divStyle%>"><%=HTMLRenderUtil.displayHTML(workflowTool.GetMessageAppStatus(request,elementList.elementAt(5))) %></div></td>
	                        <td><div class="<%=divStyle%>"><%=HTMLRenderUtil.displayHTML(elementList.elementAt(6))%></div></td>
	                        <td><div class="<%=divStyle%>"><%=HTMLRenderUtil.displayHTML(elementList.elementAt(7))%></div></td>
	                        <td><div class="<%=divStyle%>"><%=HTMLRenderUtil.displayHTML(elementList.elementAt(8))%></div></td>
	                        <td><div class="<%=divStyle%>"><%=HTMLRenderUtil.displayHTML(elementList.elementAt(9))%></div></td>
	                        <td><div class="<%=divStyle%>"><%=HTMLRenderUtil.displayHTML(elementList.elementAt(10))%></div></td>
	                        <td><div class="<%=divStyle%>"><%=HTMLRenderUtil.displayHTML(elementList.elementAt(11))%></div></td>  
	                        <td><div class="<%=divStyle%>"><%=HTMLRenderUtil.displayHTML(elementList.elementAt(12))%></div></td>
                       		<td><div class="<%=divStyle%>"><%=HTMLRenderUtil.displayHTML(elementList.elementAt(13))%></div></td> 
                       		<td><div class="<%=divStyle%>"><%=HTMLRenderUtil.displayHTML(elementList.elementAt(16))%></div></td> 
		 				</tr>
		 			<%}%>
				<%}else{%>
					<tr class="ResultNotFound" id="notFoundTable">
						<td colspan="13">No record found</td>
					</tr>
				 <%}%>
				</table>	
				<div class="PanelValueList">			
					<jsp:include page="../appform/pl/search.jsp" flush="true" />
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