<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="java.util.Vector"%>
<%@page import="com.eaf.orig.ulo.pl.formcontrol.view.form.SearchHandler"%>
<%@page import="com.eaf.orig.ulo.pl.model.app.ORIGJobDataM"%>
<%@page import="com.eaf.orig.shared.util.OrigUtil"%>
<%@page import="com.eaf.orig.ulo.pl.app.utility.PLMessageResourceUtil"%>
<%@page import="com.eaf.orig.ulo.pl.service.PLORIGEJBService"%>
<%@page import="com.eaf.orig.ulo.pl.constant.WorkflowConstant"%>
<%@page import="com.eaf.orig.ulo.pl.app.utility.DataFormatUtility"%>
<%@page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGInbox" scope="session" class="com.eaf.orig.ulo.pl.model.app.ORIGInboxDataM"/>

<script type="text/javascript" src="js/ulo.application.javascript.js"></script>
<script language="JavaScript" src="orig/js/inbox/pl/cb.inbox.js"></script>

<% 	
	org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger("orig/cb/inbox_screen.jsp");
    
    Vector<ORIGJobDataM> jobVect = ORIGInbox.getWfJobVect();
    
    String userName = ORIGUser.getUserName();

	ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
	
	Vector reasonVect = (Vector)cacheUtil.getNaosCacheDataMs("ALL_ALL_ALL", 67);

	SearchHandler HandlerM = (SearchHandler) request.getSession().getAttribute("SEARCH_DATAM");
	if(HandlerM == null){
		HandlerM = new SearchHandler();
	}
	SearchHandler.SearchDataM searchDataM = HandlerM.getSearchM();
	if(searchDataM == null){
		searchDataM = new SearchHandler.SearchDataM();
	}
    String displayMode = HTMLRenderUtil.DISPLAY_MODE_EDIT;
%>

<span id="errorMessages"></span>
<div class="nav-inbox">
	<div class="PanelFirst">	
		<div class="PanelSecond TextHeaderNormal">	
			<%=SearchHandler.DisplayErrorMessage(request)%>	
			<div class="PanelThird">
				<table class="FormFrame">
					<tr>
						<td class="textR text-label-bold" width="15%"><%=PLMessageResourceUtil.getTextDescription(request, "RETRIEVE_JOB")%> :</td>
						<td class="textL" width="30%">
							<%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(reasonVect, "5","totalJob",displayMode,"")%>
							&nbsp;&nbsp;
							<input type="button" value="Retrieve" class="button" onclick="retrieveJob()" />
						</td>
						<td class="textR text-label-bold" width="40%"><%=PLMessageResourceUtil.getTextDescription(request, "CENTRAL_JOB")%> :</td>
						<td class="textL" width="15%" id="cb-total-job"></td>
					</tr>
				</table>
			</div>			
			<div class="PanelThird">
				<table class="TableFrame">
					<tr class="Header">
      					 <td><input type="checkbox" name="checkBoxAll" onclick="checkboxAll('checkBoxAll')" id="checkBoxAll"></td>
      					 <td><%=PLMessageResourceUtil.getTextDescription(request, "SEND_BACK")%></td>
                         <td><%=PLMessageResourceUtil.getTextDescription(request, "RECEIVE_JOB_DATE")%></td>
                         <td><%=PLMessageResourceUtil.getTextDescription(request, "PRIORITY")%></td>
                         <td><%=PLMessageResourceUtil.getTextDescription(request, "APPLICATION_NO")%></td>
                         <td><%=PLMessageResourceUtil.getTextDescription(request, "FULL_NAME")%></td>
                         <td><%=PLMessageResourceUtil.getTextDescription(request, "IDENTIFICATION_NO")%></td>
                         <td><%=PLMessageResourceUtil.getTextDescription(request, "BIRTH_DATE")%></td>
                         <td><%=PLMessageResourceUtil.getTextDescription(request, "PERSONAL_INFO_BUREAU")%></td>
                         <td><%=PLMessageResourceUtil.getTextDescription(request, "DOCUMENT")%></td>
                         <td><%=PLMessageResourceUtil.getTextDescription(request, "JOB_TYPE")%></td>
                         <td><%=PLMessageResourceUtil.getTextDescription(request, "COMMENT")%></td>
					</tr>
					 <%if(!OrigUtil.isEmptyVector(jobVect)) {
                        for(int i=0 ; i<jobVect.size() ; i++){
							ORIGJobDataM jobModel =(ORIGJobDataM) jobVect.get(i);
		 		 			StringBuilder style = new StringBuilder();
                            String styleTr = (i%2==0)?"ResultEven":"ResultOdd";
                        %>
                        	<tr class="Result-Obj <%=styleTr%>">
                        	   <td><%=HTMLRenderUtil.displayCheckBoxTagDesc(jobModel.getAppRecordID()+"|"+jobModel.getInfoAppNo(),"",displayMode,"checkAppRecId","","","checkBoxAll") %></td>
                        	   <td><img src="images/send-back2.png" width="25px" height="25px" style="cursor: pointer;" onclick="CBsendBack(<%=jobModel.getAppRecordID()%>)"></td>
                   	           <td><%=HTMLRenderUtil.displayHTML(jobModel.getStartQueueDate())%></td>
                               <td><%=HTMLRenderUtil.displayHTML(jobModel.getPriority()) %></td>
                               <td><%=HTMLRenderUtil.displayHTML(jobModel.getInfoAppNo()) %></td>
                               <td><%=HTMLRenderUtil.displayHTML(jobModel.getInfoFirstName()) %> <%=HTMLRenderUtil.displayHTML(jobModel.getInfoLastName()) %></td>
                               <td><%=HTMLRenderUtil.displayHTML(jobModel.getInfoIdno()) %></td>
                               <td><%=DataFormatUtility.datetoStringForThai(jobModel.getInfoBirthDate()) %></td>
                               <td><%=HTMLRenderUtil.displayHTML(HTMLRenderUtil.displayThFullName(jobModel.getInfoNcbRequester())) %></td>
                               <td><img src="images/doc.png" style="cursor: pointer;" class="viewImg <%=jobModel.getAppRecordID()%>"></td>
                               <td><%=HTMLRenderUtil.displayHTML(jobModel.getInfoNcbJobType()) %></td>
                               <td><%=HTMLRenderUtil.displayHTML(jobModel.getInfoNcbSupComment()) %></td>
                        	</tr>
                        <%}%>
                     <%}else{%>
                     	<tr class="ResultNotFound" id="notFoundTable">
							<td colspan="12">No record found</td>
						</tr>
                     <%} %>
				</table>
				<div class="PanelValueList">		
					<jsp:include page="../appform/pl/inboxPagination.jsp" flush="true" />
				</div>
			</div>
			<div class="PanelThird">
				<table class="FormFrame">
					<tr>
						<td class="textL" width="10px"><input type="button" value="Send to KCBS" class="button" onclick="CBsendToKBank()"></td>
						<td class="textL" width="5px"></td>
						<td class="textL" width="10px"><input type="button" value="Re-Allocate All" class="button" onclick="reAllocate()"></td>
						<td></td>
					</tr>
				</table>
			</div>
		</div>		
	</div>
</div>

<%=HTMLRenderUtil.displayHiddenField("", "appRecId") %>
<%=HTMLRenderUtil.displayHiddenField("", "sendReasonCode") %>
<%=HTMLRenderUtil.displayHiddenField("", "sendRemark") %>
