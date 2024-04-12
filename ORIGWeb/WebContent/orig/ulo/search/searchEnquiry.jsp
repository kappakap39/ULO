<%@page import="com.eaf.orig.ulo.control.util.ApplicationUtil"%>
<%@page import="java.math.BigDecimal"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="java.util.Vector"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.sql.Date"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConfig" %>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.orig.ulo.model.control.FlowControlDataM"%>
<%@page import="com.eaf.core.ulo.common.util.SessionControl"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.display.InquiryHelper"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.service.common.util.ServiceUtil"%>
<%@page import="com.eaf.orig.ulo.app.view.util.kpl.KPLUtil"%>
<script type="text/javascript" src="orig/ulo/search/js/searchEnquiry.js"></script>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM" />
<jsp:useBean id="SearchForm" scope="session" class="com.eaf.core.ulo.common.engine.SearchFormHandler" />
<%
	Logger logger = Logger.getLogger(this.getClass());
	ArrayList<HashMap<String, Object>> results = SearchForm.getResults();
	int TOTAL = SearchForm.getCount();

	FlowControlDataM flowControl = (FlowControlDataM) request.getSession().getAttribute(SessionControl.FlowControl);
	String ACTION_TYPE = flowControl.getActionType();
	String roleId = flowControl.getRole();
	String FIELD_ID_PRODUCT_TYPE = SystemConstant.getConstant("FIELD_ID_PRODUCT_TYPE");
	String FIELD_ID_APPLICATION_STATUS = SystemConstant.getConstant("FIELD_ID_APPLICATION_STATUS");
	String CACHE_APPLY_CHANNEL = SystemConstant.getConstant("CACHE_APPLY_CHANNEL");
	String TOTAL_RECORD = LabelUtil.getText(request, "TOTAL_RECORD");
	ArrayList<String> WIP_JOBSTATE_END = SystemConfig.getArrayListGeneralParam("WIP_JOBSTATE_END");
	String JOB_STATE_DE2_APPROVE_SUBMIT = SystemConfig.getGeneralParam("JOB_STATE_DE2_APPROVE_SUBMIT");
	String WF_STATE_RUNNING = SystemConstant.getConstant("WF_STATE_RUNNING");
	String PROCESSING = SystemConstant.getConstant("PROCESSING");
	String SEARCH_SALE_ID = SystemConstant.getConstant("SEARCH_SALE_NAME");
	String APPLICANT_INFO_TH_FULL_NAME = SystemConstant.getConstant("APPLICANT_INFO_TH_FULL_NAME");
	String APPLICANT_INFO_IDNO = SystemConstant.getConstant("APPLICANT_INFO_IDNO");
	String FIELD_ID_CARD_TYPE = SystemConstant.getConstant("FIELD_ID_CARD_TYPE");
	String KPL = LabelUtil.getText(request, "KPL");
	String ACTION_TYPE_REPROCESS = SystemConstant.getConstant("ACTION_TYPE_REPROCESS");
	String JOBSTATE_PENDING_FULLFRAUD = SystemConstant.getConstant("JOBSTATE_PENDING_FULLFRAUD");
	
	logger.debug("SearchEnquiryPage - Role : " + roleId);
	
%>
<%= HtmlUtil.hidden("searchEnquiryPage", "SearchAllEnquiry") %>
<section class="work_area padding-top">
	<div class="row">
		<div class="col-xs-12">
			<div class="panel panel-default">
				<div class="panel-body" style="padding: 15px;">
					<div class="row form-horizontal">
						<div class="col-sm-4">
							<div class="form-group">
								<%=HtmlUtil.getFieldLabel(request, "APPLICATION_NO", "col-sm-4 col-md-5 control-label")%>
								<%=HtmlUtil.textBox("APPLICATION_GROUP_NO", "", SearchForm.getString("APPLICATION_GROUP_NO"), "", "20", "", "",
					"col-sm-8 col-md-7", request)%>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<%=HtmlUtil.getFieldLabel(request, "EQ_CARD_LINK_REF_NO", "col-sm-4 col-md-5 control-label")%>
								<%=HtmlUtil.textBox("CARD_LINK_REF_NO", "", SearchForm.getString("CARD_LINK_REF_NO"), "", "9", "", "",
					"col-sm-8 col-md-7", request)%>								
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<%=HtmlUtil.getFieldLabel(request, "EQ_PRODUCT", "col-sm-4 col-md-5 control-label")%>
								<%=HtmlUtil.dropdown("PRODUCT", "", "", SearchForm.getString("PRODUCT"), "", FIELD_ID_PRODUCT_TYPE, "", "", "",
					"col-sm-8 col-md-7", request)%>
							</div>
						</div>
					</div>
					<div class="row form-horizontal">
						<div class="col-sm-4">
							<div class="form-group">
								<%=HtmlUtil.getFieldLabel(request, "FIRST_NAME", "col-sm-4 col-md-5 control-label")%>
								<%=HtmlUtil.textBox("TH_FIRST_NAME", "", SearchForm.getString("TH_FIRST_NAME"), "", "120", "", "", "col-sm-8 col-md-7",
					request)%>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<%=HtmlUtil.getFieldLabel(request, "LAST_NAME", "col-sm-4 col-md-5 control-label")%>
								<%=HtmlUtil.textBox("TH_LAST_NAME", "", SearchForm.getString("TH_LAST_NAME"), "", "50", "", "", "col-sm-8 col-md-7",
					request)%>

							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<%=HtmlUtil.getFieldLabel(request, "ID_NO", "col-sm-4 col-md-5 control-label")%>
								<%=HtmlUtil.textBox("IDNO", "", SearchForm.getString("IDNO"), "", "20", "", "", "col-sm-8 col-md-7", request)%>
							</div>
						</div>
					</div>
					<div class="row form-horizontal">
						<div class="col-sm-4">
							<div class="form-group">
								<%=HtmlUtil.getFieldLabel(request, "APPLICATION_STATUS", "col-sm-4 col-md-5 control-label")%>
								<%=HtmlUtil.dropdown("APPLICATION_STATUS", "", "", SearchForm.getString("APPLICATION_STATUS"), "",
					FIELD_ID_APPLICATION_STATUS, "", "", "", "col-sm-8 col-md-7", request)%>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<%=HtmlUtil.getFieldLabel(request, "EQ_SCAN_DATE_FROM", "col-sm-4 col-md-5 control-label")%>
								<%=HtmlUtil.calendar("SCAN_DATE_FROM", "", "SCAN_DATE_FROM",
					FormatUtil.toDate(SearchForm.getString("SCAN_DATE_FROM"),FormatUtil.TH,FormatUtil.Format.ddMMyyyy), "", HtmlUtil.EDIT, "", FormatUtil.TH,
					"col-sm-8 col-md-7", request)%>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<%=HtmlUtil.getFieldLabel(request, "EQ_SCAN_DATE_TO", "col-sm-4 col-md-5 control-label")%>
								<%=HtmlUtil.calendar("SCAN_DATE_TO", "", "SCAN_DATE_TO",FormatUtil.toDate(SearchForm.getString("SCAN_DATE_TO"),FormatUtil.TH,FormatUtil.Format.ddMMyyyy),
					"", HtmlUtil.EDIT, "", FormatUtil.TH, "col-sm-8 col-md-7", request)%>
							</div>
						</div>
					</div>
					<div class="row form-horizontal">
						<div class="col-sm-4">
							<div class="form-group">
								<%=HtmlUtil.getFieldLabel(request, "PROJECT_CODE_DESC", "col-sm-4 col-md-5 control-label")%>
								<%=HtmlUtil.numberBox("PROJECT_CODE", "", FormatUtil.toBigDecimal(SearchForm.getString("PROJECT_CODE"), true),
					"", "####", "", "", true, "4", "", "", "col-sm-8 col-md-7", request)%>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<%=HtmlUtil.getFieldLabel(request, "BRANCH_NO", "col-sm-4 col-md-5 control-label")%>
								<%=HtmlUtil.textBox("BRANCH_NO", "", SearchForm.getString("BRANCH_NO"), "", "8", HtmlUtil.EDIT, "", "col-sm-8 col-md-7", request)%>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<%=HtmlUtil.getFieldLabel(request, "EQ_SELLER_NO", "col-sm-4 col-md-5 control-label")%>
								<%=HtmlUtil.search("SALES_ID","",SEARCH_SALE_ID,SearchForm.getString("SALES_ID"),"","","",HtmlUtil.EDIT,"","col-sm-8 col-md-7",request)%>
							</div>
						</div>
					</div>
					<div class="row form-horizontal">
						<div class="col-sm-4">
							<div class="form-group">
								<%=HtmlUtil.getFieldLabel(request, "EQ_COMPANY_NO", "col-sm-4 col-md-5 control-label")%>
								<%=HtmlUtil.textBox("COMPANY", "", SearchForm.getString("COMPANY"), "", "100", "", "", "col-sm-8 col-md-7", request)%>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<%=HtmlUtil.getFieldLabel(request, "FINAL_DECISION_DATE_FROM", "col-sm-4 col-md-5 control-label")%>
								<%=HtmlUtil.calendar("FINAL_DECISION_DATE_FROM", "", "FINAL_DECISION_DATE_FROM",
								FormatUtil.toDate(SearchForm.getString("FINAL_DECISION_DATE_FROM"),FormatUtil.TH,FormatUtil.Format.ddMMyyyy), "", HtmlUtil.EDIT, "", FormatUtil.TH,
								"col-sm-8 col-md-7", request)%>
						</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<%=HtmlUtil.getFieldLabel(request, "FINAL_DECISION_DATE_TO", "col-sm-4 col-md-5 control-label")%>
								<%=HtmlUtil.calendar("FINAL_DECISION_DATE_TO", "", "FINAL_DECISION_DATE_TO",FormatUtil.toDate(SearchForm.getString("FINAL_DECISION_DATE_TO"),FormatUtil.TH,FormatUtil.Format.ddMMyyyy),
								"", HtmlUtil.EDIT, "", FormatUtil.TH, "col-sm-8 col-md-7", request)%>
							</div>
						</div>
					</div>
					<div class="row form-horizontal">
						<div class="col-sm-4">
							<div class="form-group">
								<%=HtmlUtil.getFieldLabel(request, "FRAUD_CARD_TYPE", "col-sm-4 col-md-5 control-label")%>
								<%=HtmlUtil.dropdown("CARD_TYPE", "", "", SearchForm.getString("CARD_TYPE"), "",
								FIELD_ID_CARD_TYPE, "", "", "", "col-sm-8 col-md-7", request)%>
							</div>
						</div>
						<div class="col-sm-4"></div>
						<div class="col-sm-4"></div>
					</div>
					<div class="row">
						<div class="col-md-12 text-center">
							<%=HtmlUtil.button("EQ_SEARCH_BTN", "EQ_SEARCH_BTN", HtmlUtil.EDIT, "btn2 btn2-green", "", request)%>
							<%=HtmlUtil.button("EQ_RESET_BTN", "EQ_RESET_BTN", HtmlUtil.EDIT, "btn2", "", request)%>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
<div class="row padding-top">
	<div class="col-md-12">
		<%=InquiryHelper.buttonSuspectFraud(roleId, ACTION_TYPE, "EQ_SUSPECT_FRAUD", "EQ_SUSPECT_FRAUD", HtmlUtil.EDIT, "btn2 btn2-green",
					"", request)%>
		<%=InquiryHelper.buttonReprocess(ACTION_TYPE, "REPROCESS", "REPROCESS", HtmlUtil.EDIT, "btn2 btn2-green","", request)%>
	</div>
</div>
<div class="row padding-top">
	<div class="col-md-12">
		<div class="titlesearch">
			<%=TOTAL_RECORD%>
			:
			<%=SearchForm.getCount()%>
			<%-- 			<%=HtmlUtil.getText(request,"SEARCH_RESULT")%> --%>
		</div>
	</div>
</div>

<div class="row padding-top">
	<div class="col-md-12">
		<table class="table table-hover table-striped enqtable">
			<thead>
				<tr>
					<th style="min-width: 40px;"><%=InquiryHelper.checkBoxAll(roleId,ACTION_TYPE)%></th>
					<th style="width: 50px;"></th>
					<th style="min-width: 270px;"></th>
					<th style="width: 40%;"></th>
					<th style="min-width: 150px;"></th>
				</tr>
			</thead>
			<tbody>
				<%
					if (!Util.empty(results)) {
						for (HashMap<String, Object> Row : results) {
							logger.debug("APPLICATION_STATUS : "+Row.get("APPLICATION_STATUS"));
							String updateDateTime = FormatUtil.display(Row, "UPDATE_DATE");
							String productName = FormatUtil.displayText((String) Row.get("PRODUCT_NAME"));
							
							String lastupdate = "";
							if (!Util.empty(updateDateTime)) {
								lastupdate = LabelUtil.getText(request, "LAST_UPDATE");
							}
							String loadApplicationActionJS = "";
							if(SystemConstant.getConstant("ACTION_TYPE_ENQUIRY").equals(ACTION_TYPE)){
								loadApplicationActionJS = "onclick=loadApplicationActionJS('" + FormatUtil.display(Row, "APPLICATION_GROUP_ID")
									+ "')";
							}
							String jobState = FormatUtil.display(Row, "JOB_STATE");
							String interfaceCode = FormatUtil.display(Row, "INTERFACE_CODE");
							Date minFinalAppDecisionDate = (java.sql.Date)Row.get("MIN_FINAL_APP_DECISION_DATE");
							Date dateOfSearch = (java.sql.Date)Row.get("DATE_OF_SEARCH");
							String closeAppFlag = FormatUtil.display(Row, "CLOSE_APP_FLAG");
							String source = FormatUtil.display(Row, "SOURCE");
							logger.debug("closeAppFlag : " + closeAppFlag);
							logger.debug("interfaceCode : " + interfaceCode);
							logger.debug("source : " + source);
							Date dateCheckVeto = !Util.empty(dateOfSearch)?dateOfSearch:minFinalAppDecisionDate;
							
							//logger.debug("Job state : " + jobState);
				%>
				<tr <%=loadApplicationActionJS%>>
					<td class="enqlist">
						
						<% 
							boolean enabledCheck = false;
							
							if(KPL.equals(productName))
							{	
								Date de2SubmitDate = null;
								if(Row.get("DE2_SUBMIT_DATE") != null)
								{de2SubmitDate = (java.sql.Date)Row.get("DE2_SUBMIT_DATE");}
								Date dateCheckVetoKPL = !Util.empty(dateOfSearch)?dateOfSearch:de2SubmitDate;
								enabledCheck = KPLUtil.enabledReprocessCheckbox(ACTION_TYPE,jobState,interfaceCode,de2SubmitDate,closeAppFlag,dateCheckVetoKPL);
							}
							else
							{
								enabledCheck = InquiryHelper.enabledCheckbox(ACTION_TYPE,jobState,interfaceCode,dateCheckVeto,closeAppFlag);
							}
							
							if((ApplicationUtil.eApp(source) && SystemConstant.getConstant("ACTION_TYPE_REPROCESS").equals(ACTION_TYPE)) ||
							   (ApplicationUtil.eApp(source) && (SystemConstant.lookup("SUSPECT_FRAUD_ICON", roleId) || "".equals(roleId)))){
								enabledCheck = false;
							}
							
							if((ApplicationUtil.cjd(source) && SystemConstant.getConstant("ACTION_TYPE_REPROCESS").equals(ACTION_TYPE)) ||
							   (ApplicationUtil.cjd(source) && (SystemConstant.lookup("SUSPECT_FRAUD_ICON", roleId) || "".equals(roleId)))){
								enabledCheck = false;
							}
											
						 %>
							<%=InquiryHelper.checkBox(roleId, ACTION_TYPE, FormatUtil.display(Row, "APPLICATION_GROUP_ID"), !enabledCheck)%>
						<% if (enabledCheck) { %>
							<%=InquiryHelper.iconSuspectFraud(roleId, ACTION_TYPE, FormatUtil.display(Row, "APPLICATION_GROUP_ID"))%>
						<% }
							if (!WIP_JOBSTATE_END.contains(jobState) && !JOB_STATE_DE2_APPROVE_SUBMIT.equals(jobState)) { %>
							<%=InquiryHelper.iconCancelApplication(roleId, FormatUtil.display(Row, "APPLICATION_GROUP_ID"))%>
						<% } %>
					</td>
					<td class="text-center vertical-middle"><img
						src="<%=CacheControl.getName("52", FormatUtil.display(Row, "PRIORITY"), "SYSTEM_ID1")%>"></td>
					<td><div class="inboxitemcard bold"><%=FormatUtil.display(ApplicationUtil.getApplicationGroupNo(Row))%></div>
						<div class="inboxitemcard"><%=FormatUtil.displayText((String) Row.get("PRODUCT_NAME"))%></div>
					</td>
					<td>
						<table class="subtable" style="width: 100%">
							<tr>
								<th style="width: 50%"></th>
								<th style="width: 50%"></th>
							</tr>
							<tr>
								<td>
									<%= HtmlUtil.displaySearchApplicant(Row, "PERSONAL_APPLICANT_LIST", APPLICANT_INFO_TH_FULL_NAME, "bold") %>
									<%= HtmlUtil.displaySearchApplicant(Row, "PERSONAL_SUPPLEMENTARY_LIST", APPLICANT_INFO_TH_FULL_NAME, "bold") %>
								</td>
								<td>
									<%= HtmlUtil.displaySearchApplicant(Row, "PERSONAL_APPLICANT_LIST", APPLICANT_INFO_IDNO, "bold") %>
									<%= HtmlUtil.displaySearchApplicant(Row, "PERSONAL_SUPPLEMENTARY_LIST", APPLICANT_INFO_IDNO, "bold") %>
								</td>
							</tr>
						</table>
					</td>
					<td>
						<div class="inboxitemcard bold text-right">
							<%	if(WF_STATE_RUNNING.equals(Row.get("WF_STATE"))){
							%>
								<%=FormatUtil.displayText(PROCESSING) %>
							<%	}else{ %>
								<%=FormatUtil.display(Row, "APPLICATION_STATUS")%>
							<%	} %>
						</div>
						<div class="inboxitemcard text-right"><%=lastupdate%> <%=updateDateTime%></div>
					</td>
				</tr>
				<%
					}
					} else {
				%>
				<tr>
					<td colspan="5" align="center"><%=LabelUtil.getText(request, "NO_RECORD_FOUND")%></td>
				</tr>
				<%
					}
				%>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="5"></td>
				</tr>
			</tfoot>
		</table>
	</div>
</div>
<section class="btnbarlong">
	<jsp:include page="/orig/ulo/util/valuelist.jsp" />
</section>

<%=HtmlUtil.hidden("APPLICATION_GROUP_ID", "")%>
<script type="text/javascript">
	$(function(){
	var rx = /INPUT|SELECT/i;
	$(document).bind("keydown keypress keyup",function(e){
		if(e.which == 13){
			if(rx.test(e.target.tagName) || e.target.disabled || e.target.readOnly){
				e.preventDefault();
			}
		}
	}); 
	});
</script>