<%@page import="com.eaf.orig.shared.dao.utility.ObjectDAOFactory"%>
<%@page import="com.eaf.orig.shared.dao.utility.UtilityDAO"%>
<%@page import="com.eaf.orig.ulo.pl.app.utility.WorkflowTool"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.Vector"%>
<%@	page import="com.eaf.j2ee.pattern.util.MessageResourceUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.formcontrol.view.form.SearchHandler"%>
<%@ page import="com.eaf.orig.shared.util.OrigUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.ORIGJobDataM"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.DataFormatUtility"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGInbox" scope="session" class="com.eaf.orig.ulo.pl.model.app.ORIGInboxDataM"/>

<script type="text/javascript" src="orig/js/sup/confirm.reject.js"></script>
<script type="text/javascript" src="js/ulo.application.javascript.js"></script>

<%  
	org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger("orig/de/confirm_reject.jsp");
    
    Vector<ORIGJobDataM> jobVect = ORIGInbox.getWfJobVect();    
    String displayMode = HTMLRenderUtil.DISPLAY_MODE_EDIT;
    
%>
<div class="nav-inbox">
	<div class="PanelFirst">
		<div class="PanelSecond TextHeaderNormal">
			<%=SearchHandler.DisplayErrorMessage(request)%>
			<div class="PanelThird">
				<table class="FormFrame">
					<tr>
						<td class="textR text-label-bold" width="15%" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "JOB_STATUS")%></td>
						<td class="textL" width="18%" id="button-job-status"></td>
                        <td width="15%" class="textR text-label-bold" align="right" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "AMOUNT_OF_JOB_DONE_TODAY")%> :</td>
                        <td width="18%" class="textL"><span id="countJobComplete"></span></td>
                        <td width="20%" class="textR text-label-bold" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "AMOUNT_OF_JOB_IN_AUTO_QUEUE")%> :</td>
                        <td width="14%" class="textL" id="autoQueue"></td>
                    </tr>
				</table>	
			</div>
            <div class="PanelThird">
				<table class="TableFrame">
					<tr class="Header">
						<td colspan="3"><%=MessageResourceUtil.getTextDescription(request, "USER_TIME")%></td>
                        <td colspan="9"><%=MessageResourceUtil.getTextDescription(request, "APPLICATION_INFORMATION")%></td>
                        <td colspan="2"><%=MessageResourceUtil.getTextDescription(request, "APPLICATION_TIME")%></td>
                    </tr>
                    <tr class="Header">
						<td><input type="checkbox" name="checkBoxAll" onclick="checkboxAll('checkBoxAll')" id="checkBoxAll"></td>
                        <td><%=MessageResourceUtil.getTextDescription(request, "RECEIVE_JOB_DATE")%></td>
                        <td><%=MessageResourceUtil.getTextDescription(request, "REMAINING_TIME")%></td>
                        <td><%=MessageResourceUtil.getTextDescription(request, "SALE_TYPE")%></td>
                        <td><%=MessageResourceUtil.getTextDescription(request, "PRIORITY")%></td>
                        <td><%=MessageResourceUtil.getTextDescription(request, "APPLICATION_NO")%></td>
                        <td><%=MessageResourceUtil.getTextDescription(request, "APPLICATION_STATUS")%></td>
                        <td><%=MessageResourceUtil.getTextDescription(request, "REASON_FOR_REJECT")%></td>
                        <td><%=MessageResourceUtil.getTextDescription(request, "VETO")%></td>
                        <td><%=MessageResourceUtil.getTextDescription(request, "NAME")%></td>
                        <td><%=MessageResourceUtil.getTextDescription(request, "ID_NO")%></td>
                        <td><%=MessageResourceUtil.getTextDescription(request, "PRODUCT")%></td>
                        <td><%=MessageResourceUtil.getTextDescription(request, "RECEIVED_TIME")%></td>
                        <td><%=MessageResourceUtil.getTextDescription(request, "REMAINING_TIME")%></td>
					</tr>
                    <%
                    if(!OrigUtil.isEmptyVector(jobVect)) {
                    UtilityDAO DAO =  ObjectDAOFactory.getUtilityDAO();
                        for(int i=0 ; i<jobVect.size() ; i++){
							ORIGJobDataM jobModel =(ORIGJobDataM) jobVect.get(i);
				 		 	StringBuilder styleTr = new StringBuilder();
				 		 	styleTr.append((i%2==0)?"ResultEven":"ResultOdd");
				 		 	styleTr.append(" ").append(("O".equals(jobModel.getFlagAppTime())?"text-red":"text-black"));
                    %>
                    <tr class="Result-Obj <%=styleTr%>">
	                	<td><%=HTMLRenderUtil.displayCheckBoxTagDesc(jobModel.getAppRecordID(),"",displayMode,"check","","","checkBoxAll") %></td>
	                    <td class="result <%=styleTr%>" onclick="loadRejectApplication('<%=jobModel.getAppRecordID()%>')">
	                    	<%=HTMLRenderUtil.displayHTML(jobModel.getStartQueueDate())%>
	                    </td>
	                    <td class="result <%=styleTr%>" onclick="loadRejectApplication('<%=jobModel.getAppRecordID()%>')">
	                    	<%=HTMLRenderUtil.displayHTML(String.valueOf(jobModel.getRemainUserTime()))%>
	                    </td>
	                    <td class="result <%=styleTr%>" onclick="loadRejectApplication('<%=jobModel.getAppRecordID()%>')">
	                    	<%=HTMLRenderUtil.displaySaleType(String.valueOf(jobModel.getInfoSaleType()))%>
	                    </td>
	                    <td class="result <%=styleTr%>" onclick="loadRejectApplication('<%=jobModel.getAppRecordID()%>')">
	                    	<%=HTMLRenderUtil.displayHTML(String.valueOf(jobModel.getPriority()))%>
	                    </td>
	                    <td class="result <%=styleTr%>" onclick="loadRejectApplication('<%=jobModel.getAppRecordID()%>')">
	                    	<%=HTMLRenderUtil.displayHTML(jobModel.getInfoAppNo()) %>
	                    </td>
	                    <td class="result <%=styleTr%>" onclick="loadRejectApplication('<%=jobModel.getAppRecordID()%>')">
	                    	<%=HTMLRenderUtil.displayHTML(jobModel.getAppStatus()) %>
	                    </td>
	                    <td class="result <%=styleTr%>" onclick="loadRejectApplication('<%=jobModel.getAppRecordID()%>')">
	                    	<%=DAO.getReasonDesc(jobModel.getAppRecordID())%>
	                    </td>
	                    <td class="result <%=styleTr%>" onclick="loadRejectApplication('<%=jobModel.getAppRecordID()%>')">
	                    	<%=HTMLRenderUtil.displayHTML(jobModel.getInfoVetoFlag()) %>
	                    </td>
	                    <td class="result <%=styleTr%>" onclick="loadRejectApplication('<%=jobModel.getAppRecordID()%>')">
	                    	<%=HTMLRenderUtil.displayHTML(jobModel.getInfoFirstName()) %> <%=HTMLRenderUtil.displayHTML(jobModel.getInfoLastName()) %>
	                    </td>
	                    <td class="result <%=styleTr%>" onclick="loadRejectApplication('<%=jobModel.getAppRecordID()%>')">
	                    	<%=HTMLRenderUtil.displayHTML(jobModel.getInfoIdno()) %>
	                    </td>
	                    <td class="result <%=styleTr%>" onclick="loadRejectApplication('<%=jobModel.getAppRecordID()%>')">
	                    	<%=HTMLRenderUtil.displayHTML(jobModel.getInfoProductName()) %>
	                    </td>
	                    <td class="result <%=styleTr%>" onclick="loadRejectApplication('<%=jobModel.getAppRecordID()%>')">
	                    	<%=DataFormatUtility.dateTimetoStringForThai(jobModel.getInfoApplicationDate())%>
	                    </td>
	                    <td class="result <%=styleTr%>" onclick="loadRejectApplication('<%=jobModel.getAppRecordID()%>')">
	                    	<%=HTMLRenderUtil.displayHTML(String.valueOf(jobModel.getRemainAppTime())) %>
	                    </td>
	                </tr>
	                <%}}else{%>                                        
	                 <tr class="ResultNotFound">
			            <td colspan="14" >No record found</td>
			         </tr>		
                    <%}%>
				</table>
				<div class="PanelValueList">			
					<jsp:include page="../appform/pl/inboxPagination.jsp" flush="true" />
				</div>
			</div>
			<div class="PanelThird">
				<table class="TableFrame">
					<tr align="center">
	                   	<td align="left"><input type="button" value="Confirm Reject" class="button" onclick="confirmReject()" /></td>
	                </tr>
				</table>
			</div>
		</div>
	</div>
</div>
<%=HTMLRenderUtil.displayHiddenField("", "appRecordID") %>
<%=HTMLRenderUtil.displayHiddenField(ORIGUser.getCurrentRole(), "roleElement") %>
