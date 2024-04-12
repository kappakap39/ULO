<%@page import="java.util.HashMap"%>
<%@page import="com.eaf.orig.shared.dao.utility.ObjectDAOFactory"%>
<%@page import="com.eaf.orig.shared.dao.utility.UtilityDAO"%>
<%@page import="com.eaf.orig.ulo.pl.formcontrol.view.form.SearchHandler"%>
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
<%@ page import="com.eaf.orig.shared.util.OrigUtil"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler" /> 
<jsp:useBean id="ORIGInbox" scope="session" class="com.eaf.orig.ulo.pl.model.app.ORIGInboxDataM"/>

<%  org.apache.log4j.Logger log=org.apache.log4j.Logger.getLogger("orig/vc/inbox_screen.jsp");
    log.debug("Inbox Screen");	 
    Vector<ORIGJobDataM> jobVect = ORIGInbox.getWfJobVect();
     String currentUser = ORIGUser.getUserName();
    String CurrentRole = ORIGUser.getCurrentRole();
%>
<script type="text/javascript" src="orig/js/inbox/pl/vc.inbox.js"></script>
<script type="text/javascript" src="js/popcalendar-screen.js"></script>
<div class="nav-inbox">
	<div class="PanelFirst">
		<div class="PanelSecond TextHeaderNormal">
			<%=SearchHandler.DisplayErrorMessage(request)%>
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
						<td class="textR text-label-bold" width="20%"><%=PLMessageResourceUtil.getTextDescription(request, "TARGET_POINT_PER_DAY")%> :</td>
						<td class="textL" width="10%" id="target-point-day"></td>
						<td width="20%"></td>
						<td class="textR text-label-bold" width="20%"><%=PLMessageResourceUtil.getTextDescription(request, "DAY_POINT")%> :</td>
						<td class="textL" width="30%" id="total-job-done"></td>
					</tr>
				</table>	
			</div>
				<div class="PanelThird">
				<table class="TableFrame">
					<tr class="Header">
						<td colspan="2"><%=PLMessageResourceUtil.getTextDescription(request, "USER_TIME")%></td>
						<td colspan="11"><%=PLMessageResourceUtil.getTextDescription(request, "APPLICATION_INFORMATION")%></td>
						<td colspan="2"><%=PLMessageResourceUtil.getTextDescription(request, "APPLICATION_TIME")%></td>
					</tr>
					<tr class="Header">
						<td><%=PLMessageResourceUtil.getTextDescription(request, "DATE_RECEIVE_WORK")%></td>
						<td>&nbsp;<%=PLMessageResourceUtil.getTextDescription(request, "REMAINING_TIME")%>&nbsp;</td>
						<td><%=PLMessageResourceUtil.getTextDescription(request, "SALE_TYPE")%></td>
						<td><%=PLMessageResourceUtil.getTextDescription(request, "PRIORITY")%></td>
						<td><%=PLMessageResourceUtil.getTextDescription(request, "APPLICATION_NO")%></td>
						<td><%=PLMessageResourceUtil.getTextDescription(request, "APP_STATUS")%></td>
						<td><%=PLMessageResourceUtil.getTextDescription(request, "VETO")%></td>						
						<td nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "CALL_CUST")%></td>
						<td nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "CALL_HR")%></td>
						<td><%=PLMessageResourceUtil.getTextDescription(request, "LAST_UPDATE_BY")%></td>
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
			 		UtilityDAO DAO =  ObjectDAOFactory.getUtilityDAO();
			 		
			 	for(int i=0; i<jobVect.size(); i++){
		 		 	ORIGJobDataM jobModel =(ORIGJobDataM) jobVect.get(i);
		 		 	StringBuilder style = new StringBuilder();
		 		 		style.append((i%2==0)?"ResultEven":"ResultOdd");
		 		 		style.append(" ").append(("O".equals(jobModel.getFlagAppTime())?"text-red":"text-black"));
			 	%>			 	
		 			<tr class="Result-Obj <%=style.toString()%>" id="<%=jobModel.getAppRecordID()%>">
		 				<td><%=HTMLRenderUtil.displayHTML(jobModel.getStartQueueDate())%></td>
		 				<td><%=HTMLRenderUtil.displayHTML(jobModel.getRemainUserTime())%></td>
		 				<td><%=HTMLRenderUtil.displaySaleType(jobModel.getInfoSaleType())%></td>
		 				<td><%=HTMLRenderUtil.displayHTML(jobModel.getPriority())%></td>
		 				<td><%=HTMLRenderUtil.displayHTML(jobModel.getInfoAppNo()) %></td>
		 				<td><%=HTMLRenderUtil.displayHTML(jobModel.getAppStatus())%></td>
						<td><%=HTMLRenderUtil.displayHTML(jobModel.getInfoVetoFlag()) %></td>	
						<%
							HashMap<String,String> h = DAO.getVcInboxData(jobModel.getAppRecordID());
							String callVerCus = h.get("VERIFY_CUS_COUNT");
							String countDateVerCus = h.get("VERIFY_CUS");
							String callVerHR = h.get("VERIFY_HR_COUNT");
							String countDateHR = h.get("VERIFY_HR");
							String updateBy = h.get("UPDATE_BY");
							
							String verCus = "No";
							String verHR = "No";
							if(!"0".equalsIgnoreCase(callVerCus)){
								verCus = "Yes";
							}
							if(!"0".equalsIgnoreCase(callVerHR)){
								verHR = "Yes";
							}
							if(null != countDateVerCus){
								verCus += "("+countDateVerCus+")";
							}
							if(null != countDateHR){
								verHR += "("+countDateHR+")";
							}
						 %>						
		 				<td nowrap="nowrap"><%=HTMLRenderUtil.displayHTML(verCus) %></td>	 				
		 				<td nowrap="nowrap"><%=HTMLRenderUtil.displayHTML(verHR) %></td>
		 				<td><%=HTMLRenderUtil.displayHTML(HTMLRenderUtil.displayThFullName(updateBy,null))%></td>		 				
		 				<td>
		 					<%=HTMLRenderUtil.displayHTML(jobModel.getInfoFirstName()) %>&nbsp;&nbsp;
		 					<%=HTMLRenderUtil.displayHTML(jobModel.getInfoLastName()) %>
		 				</td>
		 				<td><%=HTMLRenderUtil.displayHTML(jobModel.getInfoIdno()) %></td>
		 				<td><%=HTMLRenderUtil.displayHTML(HTMLRenderUtil.DisplayProduct(jobModel.getInfoProductName())) %></td>
		 				<td><%=HTMLRenderUtil.displayHTML(jobModel.getAppDate())%></td>
		 				<td><%=HTMLRenderUtil.displayHTML(jobModel.getRemainAppTime())%></td>
		 			</tr>
			 	<%}%>
			 <%}else{%>
					<tr class="ResultNotFound" id="notFoundTable">
						<td colspan="15">No record found</td>
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
<%=HTMLRenderUtil.displayHiddenField("","roleElement")%>
<%=HTMLRenderUtil.displayHiddenField(currentUser,"currentUser")%>
<%=HTMLRenderUtil.displayHiddenField(CurrentRole,"CurrentRole")%>
