<%@page import="com.eaf.orig.ulo.control.util.ApplicationUtil"%>
<%@page import="java.math.BigDecimal"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="java.util.Vector"%>
<%@page import="java.util.List"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.math.BigDecimal"%>
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
<script type="text/javascript" src="orig/ulo/search/js/searchEnquiry.js"></script>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM" />
<jsp:useBean id="SearchForm" scope="session" class="com.eaf.core.ulo.common.engine.SearchFormHandler" />
<%
	Logger logger = Logger.getLogger(this.getClass());
	ArrayList<HashMap<String, Object>> results = SearchForm.getResults();
	int TOTAL = SearchForm.getCount();

	FlowControlDataM flowControl = (FlowControlDataM) request.getSession().getAttribute(SessionControl.FlowControl);
	String roleId = flowControl.getRole();
	logger.debug("role : " + roleId);
	String FIELD_ID_PRODUCT_TYPE = SystemConstant.getConstant("FIELD_ID_PRODUCT_TYPE");
	String FIELD_ID_APPLICATION_STATUS = SystemConstant.getConstant("FIELD_ID_APPLICATION_STATUS");
	String CACHE_APPLY_CHANNEL = SystemConstant.getConstant("CACHE_APPLY_CHANNEL");
	String TOTAL_RECORD = LabelUtil.getText(request, "TOTAL_RECORD");
	String SALE_IDS = SystemConstant.getConstant("SALE_IDS");
	String SEARCH_BRANCH_INFO = SystemConstant.getConstant("SEARCH_BRANCH_INFO");
%>
<%= HtmlUtil.hidden("searchEnquiryPage", "SearchIAEnquiry") %>
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
					FormatUtil.toDate(SearchForm.getString("SCAN_DATE_FROM"), "EN"), "", HtmlUtil.EDIT, "", FormatUtil.EN,
					"col-sm-8 col-md-7", request)%>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<%=HtmlUtil.getFieldLabel(request, "EQ_SCAN_DATE_TO", "col-sm-4 col-md-5 control-label")%>
								<%=HtmlUtil.calendar("SCAN_DATE_TO", "", "SCAN_DATE_TO", FormatUtil.toDate(SearchForm.getString("SCAN_DATE_TO"), "EN"),
					"", HtmlUtil.EDIT, "", FormatUtil.EN, "col-sm-8 col-md-7", request)%>
							</div>
						</div>
					</div>
					<div class="row form-horizontal">
						<div class="col-sm-4">
							<div class="form-group">
								<%=HtmlUtil.getFieldLabel(request, "PROJECT_CODE_DESC", "col-sm-4 col-md-5 control-label")%>
								<%=HtmlUtil.textBox("PROJECT_CODE", "", SearchForm.getString("PROJECT_CODE"), "", "4", "", "", "col-sm-8 col-md-7", request)%>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<%=HtmlUtil.getFieldLabel(request, "BRANCH_NO", "col-sm-4 col-md-5 control-label")%>
								<%=HtmlUtil.search("BRANCH_NO", "", SEARCH_BRANCH_INFO, "", "", "", "", "", "",
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
		<div class="titlesearch">
			<%=TOTAL_RECORD%> : <%=SearchForm.getCount()%>
		</div>
	</div>
</div>
<div class="row padding-top">
	<div class="col-md-12">
		<table class="table table-hover table-inbox enqtable">
			<thead class="header">
				<tr>
					<th class="dncol" ></th>
					<th class="text-center" style="min-width: 160px;"><%=LabelUtil.getText(request, "APPLICATION_NO") %></th>
					<th class="text-center" style="width: 126px;"><%=LabelUtil.getText(request, "PRODUCT") %></th>
					<th class="text-center" style="min-width: 200px;"><%=LabelUtil.getText(request, "FULL_NAME_TH") %></th>
					<th class="text-center" style=""><%=LabelUtil.getText(request, "ID_NO") %></th>
					<th class="text-center" style="min-width: 86px;"><%=LabelUtil.getText(request, "BRANCH_NO") %></th>
					<th class="text-center" style="min-width: 100px;"><%=LabelUtil.getText(request, "SCAN_DATE") %></th>
					<th class="text-center" style="min-width: 145px;"><%=LabelUtil.getText(request, "APPLICATION_STATUS") %></th>
					<th class="text-center" style="min-width: 150px;"><%=LabelUtil.getText(request, "LAST_UPDATED") %></th>
				</tr>
			</thead>
			<tbody>
				<%
					if (!Util.empty(results)) {
						for (HashMap<String, Object> Row : results) {
							String updateDateTime = FormatUtil.display(Row, "UPDATE_DATE");
// 							logger.debug("type : " +  Row.get("UPDATE_DATE").getClass().getName());
// 							String lastupdate = "";
// 							if (!Util.empty(updateDateTime)) {
// 								lastupdate = LabelUtil.getText(request, "LAST_UPDATE");
// 							}
							String loadApplicationActionJS = "onclick=loadApplicationActionJS('" + FormatUtil.display(Row, "APPLICATION_GROUP_ID")
									+ "')";
				%>
				<tr <%=loadApplicationActionJS%>>
					<td class="text-center"><%//=InquiryHelper.iconDownload("", "") %></td>
					<td class="text-center"><%=FormatUtil.display(ApplicationUtil.getApplicationGroupNo(Row))%></td>
					<td class="text-center"><%=FormatUtil.displayText((String) Row.get("PRODUCT_NAME"))%></td>
					<td class="text-center"><%=FormatUtil.displayText((String) Row.get("FIRST_NAME"))%></td>
					<td class="text-center"><%=FormatUtil.displayText((String) Row.get("ID_NO"))%></td>
					<td class="text-center"><%=CacheControl.getName(SEARCH_BRANCH_INFO, (String)Row.get("BRANCH_NO"))%></td>
					<td class="text-center"><%=FormatUtil.display(Row, "APPLICATION_DATE",FormatUtil.Format.ddMMyyyy,FormatUtil.TH)%></td>
					<td class="text-center"><%=CacheControl.getName(FIELD_ID_APPLICATION_STATUS, FormatUtil.display(Row, "APPLICATION_STATUS"))%></td>
					<td class="text-center"><%=updateDateTime%></td>
				</tr>
				<%}}else{%>
				<tr>
					<td class="dnicon"></td>
					<td colspan="8" class="text-center"><%=LabelUtil.getText(request, "NO_RECORD_FOUND")%></td>
				</tr>
				<%}%>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="9"></td>
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