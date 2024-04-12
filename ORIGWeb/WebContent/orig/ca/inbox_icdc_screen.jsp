<%@page import="com.eaf.orig.ulo.pl.formcontrol.view.form.SearchHandler"%>
<%@page import="com.eaf.orig.shared.model.ProcessMenuM"%>
<%@page import="com.eaf.orig.ulo.pl.app.utility.DataFormatUtility"%>
<%@page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.ORIGJobDataM"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.ORIGInboxDataM"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.Vector"%>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="java.util.Properties"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.UserPointDataM" %>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.PLMessageResourceUtil" %>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGInbox" scope="session" class="com.eaf.orig.ulo.pl.model.app.ORIGInboxDataM"/>

<%  org.apache.log4j.Logger log=org.apache.log4j.Logger.getLogger("orig/ca/inbox_icdc_screen.jsp");
    log.debug("Inbox Screen");	 
    ORIGCacheUtil cashUtil = new ORIGCacheUtil();
    
    log.debug("DE Inbox...");
   	Vector<ORIGJobDataM> jobVect = ORIGInbox.getWfJobVect();
   	
    UserPointDataM userPointM = (UserPointDataM)session.getAttribute("PL_USER_POINT");
    String userName = ORIGUser.getUserName();
    
    ProcessMenuM currentMenuM = (ProcessMenuM) request.getSession().getAttribute("CurrentMenuM");
    if(currentMenuM == null) currentMenuM = new ProcessMenuM();;
    String menuId = currentMenuM.getMenuID();
%>
<script type="text/javascript" src="orig/js/inbox/pl/ca_icdc.inbox.js"></script>
<script type="text/javascript" src="js/popcalendar-screen.js"></script>
<div class="nav-inbox">
	<div class="PanelFirst">
		<div class="PanelSecond TextHeaderNormal">
			<%=SearchHandler.DisplayErrorMessage(request)%>
			<div class="PanelThird">
				<table class="FormFrame">
					<tr>
						<td class="textR text-label-bold" width="20%"><%=PLMessageResourceUtil.getTextDescription(request, "JOB_STATUS")%> :&nbsp;</td>
						<td class="textL" width="10%" id="button-job-status"></td>
						<td width="20%"></td>
						<td width="20%"></td>
						<td width="30%"></td>
					</tr>
					<tr height="25">
	                    <td width="25%" class="textR text-label-bold"><%=PLMessageResourceUtil.getTextDescription(request, "CA_TARGET_POINT_PER_DAY")%> :&nbsp;</td>
	                    <td width="10%" class="textL"><font color="green"><%= DataFormatUtility.displayDigitsNumber(2, userPointM.getDailyPointTarget())%></font></td>
	                    <td width="10%"></td>
	                    <td width="35%" class="textR text-label-bold"><%=PLMessageResourceUtil.getTextDescription(request, "CA_DAY_POINT")%> :&nbsp;</td>
	                    <%if (userPointM.getDailyPoint() < userPointM.getDailyPointTarget()){%>
	                    <td width="20%" class="textL"><font color="red"><%= DataFormatUtility.displayDigitsNumber(2, userPointM.getDailyPoint()) %></font></td>
	                    <%}else{ %>
	                    <td width="20%" class="textL"><font color="green"><%= DataFormatUtility.displayDigitsNumber(2, userPointM.getDailyPoint()) %></font></td>
	                    <%} %>
	                  </tr>
	                  <tr height="25">
	                    <td width="25%" class="textR text-label-bold"><%=PLMessageResourceUtil.getTextDescription(request, "TARGET_POINT_PER_WEEK")%> :&nbsp;</td>
	                    <td width="10%" class="textL"><font color="green"><%= DataFormatUtility.displayDigitsNumber(2, userPointM.getWeeklyPointTarget()) %></font></td>
	                    <td width="10%"></td>
	                    <td width="35%" class="textR text-label-bold"><%=PLMessageResourceUtil.getTextDescription(request, "WEEK_POINT")%> :&nbsp;</td>
	                    <%if (userPointM.getWeeklyPoint() < userPointM.getWeeklyPointTarget()){%>
	                    <td width="20%" class="textL"><font color="red"><%= DataFormatUtility.displayDigitsNumber(2, userPointM.getWeeklyPoint()) %></font></td>
	                    <%}else{ %>
	                    <td width="20%" class="textL"><font color="green"><%= DataFormatUtility.displayDigitsNumber(2, userPointM.getWeeklyPoint()) %></font></td>
	                    <%} %>
                	</tr>
				</table>	
			</div>
			<div class="PanelThird">
				<table class="TableFrame">
					<tr class="Header">
						<td colspan="2"><%=PLMessageResourceUtil.getTextDescription(request, "USER_TIME")%></td>
						<td colspan="9"><%=PLMessageResourceUtil.getTextDescription(request, "APPLICATION_INFORMATION")%></td>
						<td colspan="2"><%=PLMessageResourceUtil.getTextDescription(request, "APPLICATION_TIME")%></td>
					</tr>
					<tr class="Header">
						<td width="8%"><%=PLMessageResourceUtil.getTextDescription(request, "DATE_RECEIVE_WORK")%></td>
						<td width="8%"><%=PLMessageResourceUtil.getTextDescription(request, "REMAINING_TIME")%></td>
						<td width="5%"><%=PLMessageResourceUtil.getTextDescription(request, "TYPE")%></td>
						<td width="8%"><%=PLMessageResourceUtil.getTextDescription(request, "SALE_TYPE")%></td>
						<td width="7%"><%=PLMessageResourceUtil.getTextDescription(request, "PRIORITY")%></td>
						<td width="8%"><%=PLMessageResourceUtil.getTextDescription(request, "APPLICATION_NO")%></td>
						<td width="9%"><%=PLMessageResourceUtil.getTextDescription(request, "APP_STATUS")%></td>
						<td width="5%"><%=PLMessageResourceUtil.getTextDescription(request, "VETO")%></td>
						<td width="10%"><%=PLMessageResourceUtil.getTextDescription(request, "NAME_SURNAME")%></td>
						<td width="9%"><%=PLMessageResourceUtil.getTextDescription(request, "ID_NO")%></td>
						<td width="7%"><%=PLMessageResourceUtil.getTextDescription(request, "PRODUCT")%></td>
						<td width="8%"><%=PLMessageResourceUtil.getTextDescription(request, "DATE_RECEIVE_APP")%></td>
						<td width="8%"><%=PLMessageResourceUtil.getTextDescription(request, "REMAINING_ALL_TIME")%></td>
					</tr>
			 <%if(!ORIGUtility.isEmptyVector(jobVect)) { %>
			 	<%for(int i = 0; i < jobVect.size() ; i++){
			 		ORIGJobDataM jobModel =(ORIGJobDataM) jobVect.get(i);
			 		String spanClass = "BigtodotextBlackC";
			 		if(OrigConstant.SLA.OVER_TIME.equals(jobModel.getFlagAppTime())){
						spanClass = "BigtodotextRedC";
					}
			 		String styleTr = (i%2==0)?"ResultEven":"ResultOdd";
			 	%>
			 	
		 			<tr class="Result-Obj <%=styleTr%>" id="<%=jobModel.getAppRecordID()%>">
		 				<td><span class="<%=spanClass%>"><%=HTMLRenderUtil.displayHTML(jobModel.getStartQueueDate())%></span></td>
		 				<td><span class="<%=spanClass%>"><%=HTMLRenderUtil.displayHTML(jobModel.getRemainUserTime())%></span></td>
		 				<td><span class="<%=spanClass%>"><%=HTMLRenderUtil.displayTypeColor(jobModel.getInfoJobType())%></span></td>
		 				<td><span class="<%=spanClass%>"><%=HTMLRenderUtil.displaySaleTypeCash(jobModel.getInfoSaleType(),jobModel.getInfoCashDayType())%></span></td>
		 				<td><span class="<%=spanClass%>"><%=HTMLRenderUtil.displayHTML(jobModel.getPriority())%></span></td>
		 				<td><span class="<%=spanClass%>"><%=HTMLRenderUtil.displayHTML(jobModel.getInfoAppNo()) %></span></td>
		 				<td><span class="<%=spanClass%>"><%=HTMLRenderUtil.displayHTML(jobModel.getAppStatus())%></span></td>
		 				<td><span class="<%=spanClass%>"><%=HTMLRenderUtil.displayHTML(jobModel.getInfoVetoFlag()) %></span></td>
		 				<td><span class="<%=spanClass%>"><%=HTMLRenderUtil.displayHTML(jobModel.getInfoFirstName())%>&nbsp;<%=HTMLRenderUtil.displayHTML(jobModel.getInfoLastName()) %></span></td>
		 				<td><span class="<%=spanClass%>"><%=HTMLRenderUtil.displayHTML(jobModel.getInfoIdno()) %></span></td>
		 				<td><span class="<%=spanClass%>"><%=HTMLRenderUtil.DisplayProduct(jobModel.getInfoProductName()) %></span></td>
		 				<td><span class="<%=spanClass%>"><%=HTMLRenderUtil.displayHTML(jobModel.getAppDate())%></span></td>
		 				<td><span class="<%=spanClass%>"><%=HTMLRenderUtil.displayHTML(jobModel.getRemainAppTime())%></span></td>
		 			</tr>
			 	<%} %>
			 <%}else{%>
					<tr class="ResultNotFound" id="notFoundTable">
						<td colspan="13">No record found</td>
					</tr>
			<%} %>
				</table>	
				<div class="PanelValueList">			
					<jsp:include page="../appform/pl/inboxPagination.jsp" flush="true" />
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
<input type="hidden" name="itemsPerPage" value="">
<input type="hidden" name="atPage" value="">
<input name="roleElement" type="hidden" value="">
