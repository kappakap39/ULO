<%@ page import="com.eaf.orig.shared.util.OrigUtil"%>
<%@ page import="com.eaf.orig.shared.model.ProcessMenuM"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.Vector"%>
<%@ page import="com.eaf.orig.ulo.pl.constant.WorkflowConstant"%>
<%@ page import="com.eaf.orig.profile.model.UserDetailM"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.DataFormatUtility"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.ORIGJobDataM"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.ORIGInboxDataM"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.PLMessageResourceUtil"%>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="java.util.Properties"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGInbox" scope="session" class="com.eaf.orig.ulo.pl.model.app.ORIGInboxDataM"/>
<% 
   org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger("orig/df/reject_inbox_screen.jsp");
   log.debug(">>>DF Reject Inbox Screen");    
   Vector<ORIGJobDataM> jobVect = ORIGInbox.getWfJobVect();
   
   ProcessMenuM currentMenuM = (ProcessMenuM) request.getSession().getAttribute("CurrentMenuM");
   if(currentMenuM == null) currentMenuM = new ProcessMenuM();;
   String menuId = currentMenuM.getMenuID();
   String currentUser = ORIGUser.getUserName();
   String CurrentRole = ORIGUser.getCurrentRole();
   log.debug("ROLE_DEJECT= "+CurrentRole);
%>
<script language="JavaScript" src="orig/js/inbox/pl/df.reject.inbox.js"></script>
<div class="nav-inbox">
	<div class="PanelFirst">
		<div class="PanelSecond TextHeaderNormal">
			<div class="PanelThird">
				<table class="FormFrame">
					<tr>
						<td class="textR text-label-bold" width="20%"><%=PLMessageResourceUtil.getTextDescription(request, "JOB_STATUS")%></td>
						<td class="textL" width="10%" id="button-job-status"></td>
						<td width="20%"></td>
						<td width="20%"></td>
						<td width="30%"></td>
					</tr>
					<tr>
						<td class="textR text-label-bold" width="20%">
						<%=PLMessageResourceUtil.getTextDescription(request, "TARGET_POINT_PER_DAY")%> :</td>
						<td class="textL" width="10%" id="target-point-day"></td>
						<td width="20%"></td>
						<td class="textR text-label-bold" width="20%">
						<%=PLMessageResourceUtil.getTextDescription(request, "DAY_POINT")%> :</td>
						<td class="textL" width="30%" id="total-job-done"></td>
						<td class="textL" width="30%"></td>
					</tr>
				</table>	
			</div>
			<div class="PanelThird">
				<table class="TableFrame">
					<tr class="Header">
						<td colspan="2"><%=PLMessageResourceUtil.getTextDescription(request, "USER_TIME")%></td>
						<td colspan="8"><%=PLMessageResourceUtil.getTextDescription(request, "APPLICATION_INFORMATION")%></td>
						<td colspan="3"><%=PLMessageResourceUtil.getTextDescription(request, "APPLICATION_TIME")%></td>
					</tr>
					<tr class="Header">
						<td><%=PLMessageResourceUtil.getTextDescription(request, "DATE_RECEIVE_WORK")%></td>
						<td><%=PLMessageResourceUtil.getTextDescription(request, "REMAINING_TIME")%></td>
						<td><%=PLMessageResourceUtil.getTextDescription(request, "SALE_TYPE")%></td>
						<td><%=PLMessageResourceUtil.getTextDescription(request, "PRIORITY")%></td>
						<td><%=PLMessageResourceUtil.getTextDescription(request, "APPLICATION_NO")%></td>
						<td><%=PLMessageResourceUtil.getTextDescription(request, "APP_STATUS")%></td>
						<td><%=PLMessageResourceUtil.getTextDescription(request, "VETO")%></td>
						<td><%=PLMessageResourceUtil.getTextDescription(request, "NAME_SURNAME")%></td>
						<td><%=PLMessageResourceUtil.getTextDescription(request, "ID_NO")%></td>
						<td><%=PLMessageResourceUtil.getTextDescription(request, "PRODUCT")%></td>
						<td><%=PLMessageResourceUtil.getTextDescription(request, "DATE_RECEIVE_APP")%></td>
						<td><%=PLMessageResourceUtil.getTextDescription(request, "REMAINING_ALL_TIME")%></td>
					</tr>
			 <%
			 	if(!OrigUtil.isEmptyVector(jobVect)) {
			 %>
			 	<%
			 	for(int i=0; i<jobVect.size(); i++){
		 		 	ORIGJobDataM jobModel =(ORIGJobDataM) jobVect.get(i);
		 		 	StringBuilder styleTr = new StringBuilder();
		 		 		styleTr.append((i%2==0)?"ResultEven":"ResultOdd");
		 		 		styleTr.append(" ").append(("O".equals(jobModel.getFlagAppTime())?"text-red":"text-black"));
			 	%>	
		 			<tr class="Result-Obj <%=styleTr.toString()%>" id="<%=jobModel.getAppRecordID()%>">
		 				<td><%=HTMLRenderUtil.displayHTML(jobModel.getStartQueueDate())%></td>
		 				<td><%=HTMLRenderUtil.displayHTML(jobModel.getRemainUserTime())%></td>
		 				<td><%=HTMLRenderUtil.displaySaleType(jobModel.getInfoSaleType())%></td>
		 				<td><%=HTMLRenderUtil.displayHTML(jobModel.getPriority())%></td>
		 				<td><%=HTMLRenderUtil.displayHTML(jobModel.getInfoAppNo()) %></td>
		 				<td><%=HTMLRenderUtil.displayHTML(jobModel.getAppStatus())%></td>
		 				<td><%=HTMLRenderUtil.displayHTML(jobModel.getInfoVetoFlag()) %></td>
		 				<td>
		 					<%=HTMLRenderUtil.displayHTML(jobModel.getInfoFirstName()) %>&nbsp;&nbsp;
		 					<%=HTMLRenderUtil.displayHTML(jobModel.getInfoLastName()) %>
		 				</td>
		 				<td><%=HTMLRenderUtil.displayHTML(jobModel.getInfoIdno()) %></td>
		 				<td><%=HTMLRenderUtil.displayHTML(jobModel.getInfoProductName()) %></td>
		 				<td><%=HTMLRenderUtil.displayHTML(jobModel.getAppDate())%></td>
		 				<td><%=HTMLRenderUtil.displayHTML(jobModel.getRemainAppTime())%></td>
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
				<jsp:include page="../appform/pl/inboxPagination.jsp" flush="true" />
			</div>
		</div>
	</div>
</div>
<%=HTMLRenderUtil.displayHiddenField("","appRecordID")%>
<%=HTMLRenderUtil.displayHiddenField(currentUser,"currentUser")%>
<%=HTMLRenderUtil.displayHiddenField(CurrentRole,"CurrentRole")%>