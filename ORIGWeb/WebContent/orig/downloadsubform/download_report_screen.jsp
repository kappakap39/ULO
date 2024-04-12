<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.apache.log4j.Logger"%>
<%@ page import="java.util.List"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.DownLoadTool"%>
<%@ page import="com.eaf.cache.data.CacheDataM"%>
<%@ page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@ page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@ page import="com.eaf.orig.profile.model.UserDetailM"%>
<%@ page import="com.eaf.orig.master.shared.model.ReportParam" %>
<%@ page import="com.eaf.orig.shared.model.DownloadReportFormHandler"%>
<%@ page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@ page import="com.eaf.core.ulo.common.properties.SystemConfig"%>
<%@ page import="com.eaf.core.ulo.common.util.Util"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.DownloadReportController"%>
<script type="text/javascript" src="orig/js/downloadsubform/download_report_screen.js"></script>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="DownloadReportForm" scope="session" class="com.eaf.orig.shared.model.DownloadReportFormHandler"/>
<%
	Logger logger = Logger.getLogger(this.getClass());
	List<CacheDataM> reportDates = DownloadReportForm.getReportDates();
	List<ReportParam> reportParams = DownloadReportForm.getReportParams();
	String reportDate = FormatUtil.replece(DownloadReportForm.getReportDate());
%>

<section class="work_area padding-top" id="DOWNLOAD_REPORT">
<div class="row">
<div class="col-xs-12">
<!-- 	<h3>Download Report</h3> -->
	<div class="panel panel-default">
		<div class="panel-body" style="padding: 15px;">
			<div class="row text-center">
				<div class="col-sm-4">
					<div class="form-group">
						<%=HtmlUtil.getFieldLabel(request, "SEARCH_DATE_REPORT", "col-sm-4 col-md-3 control-label")%>
						<div class="col-sm8 col-md-7""><%=HtmlUtil.dropdown("reportDate","DATE_REPORT",reportDate,"","",HtmlUtil.EDIT,"", request)%></div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12 text-center">
					<%=HtmlUtil.button("SEARCH_BUTTON_REPORT","SEARCH_BUTTON_REPORT",HtmlUtil.EDIT,"btn2 btn2-green","",request)%>
				</div>
			</div>
		</div>
	</div>
</div>
</div>
</section>
<h3>Download Report as of <%=reportDate%></h3>
<div class="row padding-top">
	<div class="col-md-12">
		<table class="table table-bordered table-striped">
			<thead style="background-color: #00A950; color: white;">
				<tr>
					<th>Report Name</th>
					<th>Report File</th>
					<th>Download</th>
				</tr>
			</thead>
			<tbody>
					<%
					if(!Util.empty(reportParams)){
						for(int i=0; i<reportParams.size(); i++){
							ReportParam reportParam = (ReportParam)reportParams.get(i);
					%>
						<%=DownloadReportController.displayHtml(reportParam,reportDate) %>
					<%}}else{%>
						<tr>
						    <td colspan="3" style="text-align:center;">No record found</td>
						</tr>	
					<%}%>
			</tbody>
		</table>
	</div>
</div>