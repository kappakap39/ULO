<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConfig"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.properties.ListBoxControl"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.display.InquiryHelper"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.util.SessionControl"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.service.common.util.ServiceUtil"%>
<%@page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@page import="com.eaf.orig.ulo.app.view.util.kpl.KPLUtil"%>
<link rel="stylesheet" type="text/css" href="wm/WMTask.css">
<script type="text/javascript" src="wm/WMTask.js"></script>
<jsp:useBean id="SearchForm" scope="session" class="com.eaf.core.ulo.common.engine.SearchFormHandler"/>
<%
	Logger logger = Logger.getLogger(this.getClass());
	logger.debug("WM Task Explorer");
	String FIELD_ID_WM_TASK = SystemConstant.getConstant("FIELD_ID_WM_TASK");
	String APPLICATION_STATIC_CANCELLED = SystemConstant.getConstant("APPLICATION_STATIC_CANCELLED");
	String mode = HtmlUtil.EDIT;
	BigDecimal TOTAL = FormatUtil.toBigDecimal(String.valueOf(SearchForm.getCount()));
	ArrayList<HashMap<String, Object>> results = SearchForm.getResults();
%>
<section class="work_area padding-top">
	<div class="row">
		<div class="col-xs-12">
			<div class="panel panel-default">
				<div class="panel-body">
					<div class="row form-horizontal">
						<div class="col-sm-4">
							<div class="form-group">
								<%=HtmlUtil.getFieldLabel(request, "APPLICATION_NO", "col-sm-4 col-md-5 control-label")%>
								<%=HtmlUtil.textBox("APPLICATION_GROUP_NO", "", SearchForm.getString("APPLICATION_GROUP_NO"), "", "20", mode, "", "col-sm-8 col-md-7", request)%>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<%=HtmlUtil.getFieldLabel(request, "DM_SEARCH_DOC_STATUS", "col-sm-4 col-md-5 control-label")%>
								<%=HtmlUtil.dropdown("STATUS", "", "", SearchForm.getString("STATUS"), "", FIELD_ID_WM_TASK, "", "", "", "col-sm-8 col-md-7", request)%>
							</div>
						</div>
					</div>
					<div class="col-sm-12">
						<div class="form-group">
							<div class="row">
								<div class="col-md-12 text-center">
									<%=HtmlUtil.button("WM_SEARCH_BTN", "SEARCH_BTN", HtmlUtil.EDIT, "btn2 btn2-green", "", request)%>
									<%=HtmlUtil.button("WM_RESET_BTN", "RESET_BTN", HtmlUtil.EDIT, "btn2", "", request)%>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>

<div class="row padding-top">
	<div class="col-md-12 wm_toolbar">
		<div class="titlesearch">
			<%=HtmlUtil.getFieldLabel(request, "TOTAL_RECORD")%><%=FormatUtil.display(TOTAL, FormatUtil.Format.NUMBER_FORMAT)%>
		</div>
		<div class="wm_collapse_control">
		<!-- 
		<span class="BTN_COLLAPSE_ALL clickable">
			<i class="glyphicon glyphicon-collapse-up"></i>
		</span>
		<span class="BTN_EXPAND_ALL clickable">
			<i class="glyphicon glyphicon-collapse-down"></i>
		</span>
		 -->
		</div>
	</div>
</div>

<section class="work_area padding-top">
<%
	if (!Util.empty(results)) {
		for (HashMap<String, Object> Row : results) {
			String refCode = (String) Row.get("REF_CODE");
			String applicationStatus = (String) Row.get("APPLICATION_STATUS");
			ArrayList<HashMap<String, Object>> data = (ArrayList<HashMap<String, Object>>) Row.get("DATA");
%>
	<div class="panel panel-default">
		<div class="panel-heading wm_item_header">
			<div class="wm_item_header_content">
				<h3 class="panel-title"><%=refCode%></h3>
			</div>
			<div class="wm_item_header_side">
				<%
					String resubmitTask = "onclick=WM_RESUBMIT_BTN_CLICK('" + refCode + "')";
				%>
				<span title="Resubmit Application" class="BTN_RESUBMIT_APP clickable" <%=resubmitTask%>>
					<i class="glyphicon glyphicon-fast-backward"></i>
				</span>
				<% 
					if(!APPLICATION_STATIC_CANCELLED.equals(applicationStatus)) {
				%>
				<span title="Cancel Application" class="BTN_CANCEL_APP clickable" row-data="{rowtype:item,uniqueid:<%=FormatUtil.display((String) Row.get("APPLICATION_GROUP_ID"))%>, 
						itemid:<%=FormatUtil.display((String) Row.get("APPLICATION_RECORD_ID"))%>}" href="#">
					<i class="glyphicon glyphicon-remove"></i>
				</span>
				<% 
					} else {
				%>
				<span>
					<i class="glyphicon glyphicon-remove" style="opacity: .25;"></i>
				</span>
				<% 
					}
				%>
				<span class="BTN_COLLAPSE clickable">
					<i class="glyphicon glyphicon-chevron-up"></i>
				</span>
			</div>
		</div>
		<div class="special_collapse">
			<table class="table table-hover">
				<thead>
					<tr class="tabletheme_header wm_table">
						<th style="width: 15%;">Task</th>
						<th style="width: 25%;"><%=LabelUtil.getText(request, "APPLICATION_NO") %></th>
						<th style="width: 25%;">Last Run Time</th>
						<th style="width: 15%;">Error Total</th>
						<th style="width: 15%;">Status</th>
						<th style="width: 5%;">Retry</th>
					</tr>
				</thead>
				<tbody>
					<% 
						if (!Util.empty(data)) {
							int index = 0;
							int dataSize = data.size();
							for (HashMap<String, Object> dataRow : data) {
								String statusString = (String) dataRow.get("STATUS");
								if(null == statusString) {
									statusString = "0";
								}
								int status = Integer.parseInt(statusString);
								String lastRunTime = (String) dataRow.get("LAST_RUN_TIME");
								if(null == lastRunTime) {
									lastRunTime = "";
								}
								String errorTotalString = (String) dataRow.get("ERROR_TOTAL");
								if(null == errorTotalString) {
									errorTotalString = "0";
								}
								int errorTotal = Integer.parseInt(errorTotalString);
								boolean isRetry = false;
								if(++index == dataSize) {
									isRetry = true;
								}
								String viewError = "";
								if(status == 2) {
									viewError = "onclick=WM_VIEW_ERROR_ROW_CLICK('" + dataRow.get("TASK_ID") + "')";					
								}
					%>
					<tr class="wm_table">
						<td style="text-align: left !important;" <%=viewError%>>
							<%=dataRow.get("TASK")%>
						</td>
						<td <%=viewError%>>
							<%=dataRow.get("REF_CODE")%>
						</td>
						<td <%=viewError%>>
							<%=lastRunTime%>
						</td>
						<td <%=viewError%>>
							<%=errorTotal%>
						</td>
						<td <%=viewError%>>
							<%=ListBoxControl.getName(FIELD_ID_WM_TASK, String.valueOf(status))%>
						</td>
						<td>
							<%
								String retryTask = "";
								if(isRetry && !APPLICATION_STATIC_CANCELLED.equals(applicationStatus)) {
									retryTask = "onclick=WM_RETRY_BTN_CLICK('" + dataRow.get("TASK_ID") + "')";
							%>
							<span title="Retry Task" class="clickable" <%=retryTask%>>
								<i class="glyphicon glyphicon-repeat"></i>
							</span>
							<%
								}
							%>
						</td>
					</tr>
					<% 
							}
						}
					%>
				</tbody>
			</table>
		</div>
	</div>
<% 
		}
	}
%>
</section>
<section class="btnbarlong">
	<jsp:include page="/orig/ulo/util/valuelist.jsp" />
</section>

</body>