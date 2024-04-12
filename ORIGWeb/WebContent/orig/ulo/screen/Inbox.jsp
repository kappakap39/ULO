<%@page import="com.eaf.service.common.util.ServiceUtil"%>
<%@page import="com.eaf.orig.ulo.control.util.ApplicationUtil"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.core.ulo.common.util.SessionControl"%>
<%@page import="com.eaf.orig.ulo.model.control.FlowControlDataM"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.orig.profile.model.UserDetailM"%>
<%@page import="com.eaf.orig.ulo.app.dao.WorkflowDAOFactory" %>

<link rel="styleSheet" href="bootstrap/css/bootstrap-switch.min.css" type="text/css">
<script src="bootstrap/js/bootstrap-switch.min.js"></script>

<jsp:useBean id="SearchForm" scope="session" class="com.eaf.core.ulo.common.engine.SearchFormHandler" />
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<script type="text/javascript" src="orig/ulo/screen/js/Inbox.js"></script>
<%
	FlowControlDataM flowControl = (FlowControlDataM) request.getSession().getAttribute(SessionControl.FlowControl);
	Logger logger = Logger.getLogger(this.getClass());
	
	ArrayList<HashMap<String, Object>> results = SearchForm.getResults();
	HashMap<String, String> searchData = SearchForm.getSearchs();
	
	String PRIORITY_PROCESS = SearchForm.getSearchs("PRIORITY_PROCESS", "0");
	String ALL_PROCESS = SearchForm.getSearchs("ALL_PROCESS", "0");
	String ROLE_PRIORITY_PROCESS = SearchForm.getSearchs("ROLE_PRIORITY_PROCESS", "0");
	String ROLE_PROCESS = SearchForm.getSearchs("ROLE_PROCESS", "0");
	String ACTUAL_POINT = SearchForm.getSearchs("ACTUAL_POINT", "0");
	String TARGET_POINT = SearchForm.getSearchs("TARGET_POINT", "0");
	String APPLICANT_INFO_TH_FULL_NAME = SystemConstant.getConstant("APPLICANT_INFO_TH_FULL_NAME");
	String APPLICANT_INFO_IDNO = SystemConstant.getConstant("APPLICANT_INFO_IDNO");
	
	UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
	
	String INBOX_FLAG = flowControl.getStoreAction("INBOX_FLAG");
	if(Util.empty(INBOX_FLAG)){
		INBOX_FLAG = WorkflowDAOFactory.getInboxDAO().getInboxFlag(userM.getUserName()); //SearchForm.getSearchs("INBOX_FLAG",MConstant.FLAG.NO);
	}
	logger.debug("INBOX_FLAG : " + INBOX_FLAG);
	String stylePriorityProcess="";
	if(Integer.parseInt(PRIORITY_PROCESS)>0){
		stylePriorityProcess ="textred";
	}
	
	String styleRolePriorityProcess="";
	if(Integer.parseInt(ROLE_PRIORITY_PROCESS)>0){
		styleRolePriorityProcess ="textred";
	}
	
	String stylePoint="";
	if(Float.parseFloat(ACTUAL_POINT)>=Float.parseFloat(TARGET_POINT)){
		stylePoint="textgreen";
	}else if(Float.parseFloat(ACTUAL_POINT)<Float.parseFloat(TARGET_POINT)){
		stylePoint="textred";
	}
	String PRO_ACT_GET_JOB = SystemConstant.getConstant("PRO_ACT_GET_JOB");
	String processAction = flowControl.getStoreAction("processAction");
	
%>

	<div class="row padding-top">
		<div class="col-md-4 col-lg-5">
			<%
				if(PRO_ACT_GET_JOB.equals(processAction)){
			 %>
				<div class="col-xs-12 ">
				 	<section class="info textgreen" >
				 		<div class="col-xs-5">
				 			<%=HtmlUtil.hidden("CENTRAL_Q_NO", SearchForm.getCentralQueue())%>
							<h4 style="display: inline-block;"><%=LabelUtil.getText(request, "CENTRAL_Q")%>(<%=SearchForm.getCentralQueue() %>)</h4> 
						</div>
						<div class="col-xs-4">
							<%=HtmlUtil.button("GET_JOB", "GET_JOB_BTN", HtmlUtil.EDIT, "btn btn-block btn-default", "style='display: inline-block;'", request)%>
						</div>
					</section>
				</div>
			<%	}else{
			%>
			<div class="deployswitch">
				<input id="ON_OFF_BTN" type="checkbox" checked data-size="small">
			</div>
			<%
				}
			 %>
		</div>
		<div class="col-md-8 col-lg-7">
			<div class="row">
				<div class="col-xs-4">
					<% 
				if(Util.empty(processAction)){
					if(flowControl.getRole().equals(SystemConstant.getConstant("ROLE_IA"))){ %>
						<div class="inbox-widget" style="display:none;">
							<h4 class="inbox-widget-heading"><%=LabelUtil.getText(request, "WAIT_ALL_PROCESS")%></h4>
							<div class="inbox-widget-body">
								<div class="col-xs-5 col-md-6 text-right nopadding">
									<img src="images/ulo/inbox/inbox_job.png">
								</div>
								<div class="col-xs-7 col-md-6 text-right nopadding">
									<div class="inbox-widget-line1">
										<h4 class="nomargin">
											<span class="<%=stylePriorityProcess%> textinbox"><%=FormatUtil.display(PRIORITY_PROCESS)%></span>/<%=FormatUtil.display(ALL_PROCESS)%></h4>
									</div>
									<div class="inbox-widget-line2">
										<span><%=LabelUtil.getText(request, "PRIORITY_TOTAL")%></span>
									</div>
								</div>
							</div>
						</div>
					<% }else{ %>
						<div class="inbox-widget">
							<h4 class="inbox-widget-heading"><%=LabelUtil.getText(request, "WAIT_ALL_PROCESS")%></h4>
							<div class="inbox-widget-body">
								<div class="col-xs-5 col-md-6 text-right nopadding">
									<img src="images/ulo/inbox/inbox_job.png">
								</div>
								<div class="col-xs-7 col-md-6 text-right nopadding">
									<div class="inbox-widget-line1">
										<h4 class="nomargin">
											<span class="<%=stylePriorityProcess%> textinbox"><%=FormatUtil.display(PRIORITY_PROCESS)%></span>/<%=FormatUtil.display(ALL_PROCESS)%></h4>
									</div>
									<div class="inbox-widget-line2">
										<span><%=LabelUtil.getText(request, "PRIORITY_TOTAL")%></span>
									</div>
								</div>
							</div>
						</div>
					<% } 
					
					}%>
				</div>
				<div class="col-xs-4">
					<div class="inbox-widget">
						<h4 class="inbox-widget-heading"><%=LabelUtil.getText(request, "WAIT_TEAM_PROCESS")%></h4>
						<div class="inbox-widget-body">
							<div class="col-xs-5 col-md-6 text-right nopadding">
								<img src="images/ulo/inbox/inbox_team.png">
							</div>
							<div class="col-xs-7 col-md-6 text-right nopadding">
								<div class="inbox-widget-line1">
									<h4 class="nomargin">
										<span class="<%=styleRolePriorityProcess %> textinbox"><%=FormatUtil.display(ROLE_PRIORITY_PROCESS)%></span>/<%=FormatUtil.display(ROLE_PROCESS)%></h4>
								</div>
								<div class="inbox-widget-line2">
									<span><%=LabelUtil.getText(request, "PRIORITY_TOTAL")%></span>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="col-xs-4">
					<div class="inbox-widget">
						<h4 class="inbox-widget-heading"><%=LabelUtil.getText(request, "POINT")%></h4>
						<div class="inbox-widget-body">
							<div class="col-xs-5 col-md-6 text-right nopadding"></div>
							<div class="col-xs-7 col-md-6 text-right nopadding">
								<div class="inbox-widget-line1">
									<h4 class="nomargin">
										<span class="<%=stylePoint %> textinbox"><%=FormatUtil.display(ACTUAL_POINT)%></span>/<%=FormatUtil.display(TARGET_POINT)%></h4>
								</div>
								<div class="inbox-widget-line2">
									<span><%=LabelUtil.getText(request, "ACTUAL_TARGET")%></span>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="row padding-top">
		<div class="col-md-12">
			<table class="table table-hover table-striped table-inbox">
				<thead>
					<tr>
						<th style="max-width: 60px; min-width: 60px;"></th>
						<th style="max-width: 40px; min-width: 40px;"></th>
						<th style="max-width: 40px; min-width: 40px;"></th>
						<th style="width: 40%"></th>
						<th style="width: 20%"></th>
						<th style="width: 20%"></th>
						<th style="min-width: 140px"></th>
					</tr>
				</thead>
				<tbody>
					<%
						if (!Util.empty(results)) {
							int ROW_NUM = 1;
							for (HashMap<String, Object> Row : results) {
								String APPLICATION_GROUP_ID = FormatUtil.display(Row, "APPLICATION_GROUP_ID");
								String TASK_ID = FormatUtil.display(Row, "TASK_ID");
								String APPLICATION_TYPE = FormatUtil.display(Row, "APPLICATION_TYPE");
								String JOB_STATE = FormatUtil.display(Row, "JOB_STATE");
								String APPLICATION_TEMPLATE = FormatUtil.display(Row, "APPLICATION_TEMPLATE");
								String SOURCE = FormatUtil.display(Row, "SOURCE");
								String loadApplicationActionJS = "onclick=loadApplicationActionJS('"+APPLICATION_GROUP_ID+"','"+TASK_ID+"','"+APPLICATION_TYPE+"','"+JOB_STATE+"','"+APPLICATION_TEMPLATE+"','"+SOURCE+"')";
								
					%>
					<tr <%=loadApplicationActionJS%>>
						<td class="text-center"><img class="imginbox" src="<%=CacheControl.getName(SystemConstant.getConstant("FIELD_ID_PRIORITY"), FormatUtil.display(Row, "PRIORITY"), "SYSTEM_ID1")%>"></td>
						<td class="text-center"><img  class="imginbox" style="margin-left:-15px;margin-right:-15px" src="<%=CacheControl.getName(SystemConstant.getConstant("FIELD_ID_JOB_TYPE"), FormatUtil.display(Row, "JOB_TYPE"), "SYSTEM_ID1")%>"></td>
						<td class="text-center"><img  class="imginbox" style="margin-left:-15px;margin-right:-15px" src="<%=CacheControl.getName(SystemConstant.getConstant("FIELD_ID_REPROCESS"), FormatUtil.display(Row, "REPROCESS_FLAG"), "SYSTEM_ID1")%>"></td>
						<td>
							<div class="inboxitemcard bold"><%=FormatUtil.display(ApplicationUtil.getApplicationGroupNo(Row))%></div>
							<div class="inboxitemcard"><%=FormatUtil.display(Row, "PROCUCT_NAME")%></div>
						</td>
						<%
							String CUSTOMER_NAME = FormatUtil.display(Row, "CUSTOMER_NAME");
							String ID_NO = FormatUtil.display(Row, "ID_NO");
						%>
						<td>
							<%= HtmlUtil.displaySearchApplicant(Row, "PERSONAL_APPLICANT_LIST", APPLICANT_INFO_TH_FULL_NAME, "bold") %>
							<%= HtmlUtil.displaySearchApplicant(Row, "PERSONAL_SUPPLEMENTARY_LIST", APPLICANT_INFO_TH_FULL_NAME, "bold") %>
						</td>
						<td>
							<%= HtmlUtil.displaySearchApplicant(Row, "PERSONAL_APPLICANT_LIST", APPLICANT_INFO_IDNO, "bold") %>
							<%= HtmlUtil.displaySearchApplicant(Row, "PERSONAL_SUPPLEMENTARY_LIST", APPLICANT_INFO_IDNO, "bold") %>
						</td>
						<td>
							<div class="inboxitemcard bold text-right"><%=LabelUtil.getText(request, "SLA")%>&nbsp;
								<%
									String OVER_SLA_FLAG = FormatUtil.display(Row, "OVER_SLA_FLAG");
											String SLA_DAY = FormatUtil.display(Row, "SLA_DAY");
											String SLA_USED_DAY = FormatUtil.display(Row, "SLA_USED_DAY");
											String styleClass = Integer.parseInt(SLA_USED_DAY) > Integer.parseInt(SLA_DAY) ? "textred" : "textgreen";
								%>
								<span class="<%=styleClass%>"><%=SLA_USED_DAY%></span>/<%=SLA_DAY%>&nbsp;&nbsp;
								<%=LabelUtil.getText(request, "DAY")%></div>
							<div class="inboxitemcard text-right">
								<img class="lastupdate" src="css/ulo/images/lastupdate.png" /><span><%=FormatUtil.display(Row, "SLA_DATE")%></span>
							</div>
						</td>
					</tr>
					<%
						}
						} else {
					%>
					<tr>
						<td colspan="6" align="center"><%=LabelUtil.getText(request, "NO_RECORD_FOUND")%></td>
					</tr>

					<%
						}
					%>

				</tbody>
				<tfoot>
					<tr>
						<td colspan="6"></td>
					</tr>
				</tfoot>
			</table>
		</div>
	</div>

<!-- old layout -->
<!-- <section> -->
<!-- 	<div class="row"> -->
<!-- 		<div class="col-md-12"> -->
<%-- 			<jsp:include page="/orig/ulo/util/InboxValueList.jsp" /> --%>
<!-- 		</div> -->
<!-- 	</div> -->
<!-- </section> -->
<%=HtmlUtil.hidden("MAIN_SCREEN_INBOX", "INBOX")%>
<%=HtmlUtil.hidden("APPLICATION_GROUP_ID", "")%>
<%=HtmlUtil.hidden("TASK_ID", "")%>
<%=HtmlUtil.hidden("APPLICATION_TYPE", "")%>
<%=HtmlUtil.hidden("JOB_STATE", "")%>
<%=HtmlUtil.hidden("APPLICATION_TEMPLATE", "")%>
<%=HtmlUtil.hidden("INBOX_BTN", INBOX_FLAG)%>
<%=HtmlUtil.hidden("SOURCE", "")%>