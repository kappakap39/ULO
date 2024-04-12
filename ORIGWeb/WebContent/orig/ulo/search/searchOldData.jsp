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
<script type="text/javascript" src="orig/ulo/search/js/searchOldData.js"></script>
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
	String TOTAL_RECORD = LabelUtil.getText(request, "TOTAL_RECORD");
	String APPLICANT_INFO_TH_FULL_NAME = SystemConstant.getConstant("APPLICANT_INFO_TH_FULL_NAME");
	String APPLICANT_INFO_IDNO = SystemConstant.getConstant("APPLICANT_INFO_IDNO");
	String FIELD_ID_CARD_TYPE = SystemConstant.getConstant("FIELD_ID_CARD_TYPE");

	logger.debug("SearchOldDataPage - Role : " + roleId);
	
%>
<%= HtmlUtil.hidden("searchOldDataPage", "SearchOldData") %>
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
								FormatUtil.toDate(SearchForm.getString("SCAN_DATE_FROM"),FormatUtil.TH,FormatUtil.Format.ddMMyyyy), "", HtmlUtil.EDIT, "", FormatUtil.TH,"col-sm-8 col-md-7", request)%>
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
								<%=HtmlUtil.getFieldLabel(request, "FRAUD_CARD_TYPE", "col-sm-4 col-md-5 control-label")%>
								<%=HtmlUtil.dropdown("CARD_TYPE", "", "", SearchForm.getString("CARD_TYPE"), "",
								FIELD_ID_CARD_TYPE, "", "", "", "col-sm-8 col-md-7", request)%>
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
	</div>
</div>
<div class="row padding-top">
	<div class="col-md-12">
		<div class="titlesearch">
			<%=TOTAL_RECORD%>
			:
			<%=SearchForm.getCount()%>
		</div>
	</div>
</div>

<div class="row padding-top">
	<div class="col-md-12">
		<table class="table table-hover table-striped enqtable">
			<thead>
				<tr>
					<th style="width: 20%;"></th>
					<th style="min-width: 40%;"></th>
					<th style="width: 30%;"></th>
				</tr>
			</thead>
			<tbody>
				<%
					if (!Util.empty(results)) {
						for (HashMap<String, Object> Row : results) {
							logger.debug("APPLICATION_STATUS : "+Row.get("APPLICATION_STATUS"));
							String archiveDateTime = FormatUtil.display(Row, "ARC_DATE");
							String productName = FormatUtil.displayText((String) Row.get("PRODUCT_NAME"));
							
							String loadOldAppActionJS = "";
							if(SystemConstant.getConstant("ACTION_TYPE_ENQUIRY").equals(ACTION_TYPE)){
								loadOldAppActionJS = "onclick=loadOldAppActionJS('" + FormatUtil.display(Row, "APPLICATION_GROUP_ID")
									+ "')";
							}
							String jobState = FormatUtil.display(Row, "JOB_STATE");
				%>
				<tr <%=loadOldAppActionJS%>>
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
									<div class="inboxitemcard bold"><%=Util.empty(Row.get("PERSONAL_APPLICANT1")) ? "" : Row.get("PERSONAL_APPLICANT1") %></div>
									<div class="inboxitemcard bold"><%=Util.empty(Row.get("PERSONAL_APPLICANT2")) ? "" : Row.get("PERSONAL_APPLICANT2")%></div>
								</td>
								<td>
									<div class="inboxitemcard bold"><%=Util.empty(Row.get("PERSONAL_APPLICANT_ID1")) ? "" : Row.get("PERSONAL_APPLICANT_ID1") %></div>
									<div class="inboxitemcard bold"><%=Util.empty(Row.get("PERSONAL_APPLICANT_ID2")) ? "" : Row.get("PERSONAL_APPLICANT_ID2") %></div>
								</td>
							</tr>
						</table>
					</td>
					<td>
						<div class="inboxitemcard bold text-right">
								<%=FormatUtil.display(Row, "APPLICATION_STATUS")%>
						</div>
						<div class="inboxitemcard text-right"><%="Archive Date"%> <%=archiveDateTime%></div>
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