<%@page import="com.eaf.service.common.api.ServiceCache"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="java.sql.Timestamp" %>
<%@page import="java.sql.Date" %>
<%@page import="com.eaf.orig.ulo.pl.app.utility.DataFormatUtility" %>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.date.ApplicationDate"%>
<script type="text/javascript" src="orig/js/export/excel/itsi_report.js"></script>
<jsp:useBean id="SearchForm" scope="session" class="com.eaf.core.ulo.common.engine.SearchFormHandler" />
<%
String timeStamp = ApplicationDate.getTimestamp().toString();
String timeHour = "";
String timeMin = "";
if(timeStamp.indexOf(":")>0){
timeHour = timeStamp.substring(timeStamp.indexOf(":")-2, timeStamp.indexOf(":"));
timeMin = timeStamp.substring(timeStamp.indexOf(":")+1, timeStamp.indexOf(":")+3);
}
Date DATE_FROM_CALENDAR = FormatUtil.toDate(SearchForm.getString("DATE_FROM_BOX"), FormatUtil.EN);
if(Util.empty(DATE_FROM_CALENDAR)){
	DATE_FROM_CALENDAR = ApplicationDate.getDate();
}
Date DATE_TO_CALENDAR = FormatUtil.toDate(SearchForm.getString("DATE_TO_BOX"), FormatUtil.EN);
if(Util.empty(DATE_TO_CALENDAR)){
	DATE_TO_CALENDAR = ApplicationDate.getDate();
}
String strDate = FormatUtil.display(ApplicationDate.getDate(), FormatUtil.TH, FormatUtil.Format.yyyyMMdd);
String OUTPUT_FILENAME = "FLS-WF_Report_ITSI_Report_Request_"+strDate.replace("/", "");
 %>
 <input type="hidden" name="DEAFULT_DATE_FROM" value="<%=FormatUtil.display(DATE_FROM_CALENDAR, FormatUtil.TH, FormatUtil.Format.ddMMyyyy)%>"/>
 <input type="hidden" name="DEAFULT_DATE_TO" value="<%=FormatUtil.display(DATE_TO_CALENDAR, FormatUtil.TH, FormatUtil.Format.ddMMyyyy)%>"/>
 <input type="hidden" name="DEAFULT_TIME_HOUR" value="<%=timeHour%>"/>
 <input type="hidden" name="DEAFULT_TIME_MIN" value="<%=timeMin%>"/>

 <input type="hidden" name="DATE_NOW" value="<%=ApplicationDate.getDate()%>"/>
 <input type="hidden" name="OUTPUT_FILENAME" value="<%=OUTPUT_FILENAME%>"/>
 <section class="work_area padding-top" id="ITSI_REPORT">
	<div class="row">
		<div class="col-xs-12">
			<div class="panel panel-default">
				<div class="panel-body" style="padding: 15px;">
					
					<div class="row form-horizontal">
						<div class="col-sm-4">
							<div class="form-group">
								<%=HtmlUtil.getMandatoryLabel(request, "ITSI_REPORT_TRAN_DATE_FROM","col-sm-2 col-md-5 control-label") %>
								<%=HtmlUtil.calendar("DATE_FROM_CALENDAR", "", "DATE_FROM_BOX",DATE_FROM_CALENDAR,
									"", HtmlUtil.EDIT, "", FormatUtil.TH, "col-sm-8 col-md-7", request)%>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<%=HtmlUtil.getMandatoryLabel(request, "ITSI_REPORT_TRAN_DATE_TO","col-sm-2 col-md-5 control-label") %>
								<%=HtmlUtil.calendar("DATE_TO_CALENDAR", "", "DATE_TO_BOX",DATE_TO_CALENDAR,
									"", HtmlUtil.EDIT, "", FormatUtil.TH, "col-sm-8 col-md-7", request)%>
							</div>
						</div>
					</div>
					<div class="row form-horizontal">
						<div class="col-sm-4">
							<div class="form-group">
								<%=HtmlUtil.getFieldLabel(request, "ITSI_REPORT_TRAN_DETAIL","col-sm-4 col-md-5 control-label") %>
								<%=HtmlUtil.dropdown("TRAN_DETAIL_BOX", "", "", "", "","TranDetail", "ALL", "", "", "col-sm8 col-md-7", request)%>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<%=HtmlUtil.getFieldLabel(request,"ITSI_REPORT_OPERATE_BY","col-sm-2 col-md-5 control-label") %>
								<%=HtmlUtil.textBox("OPERATE_BY_BOX", "", SearchForm.getString("USER_NAME_BOX"), "", "20", "", "", "col-sm8 col-md-7", request)%>
							</div>
						</div>
					</div>
					<div class="row form-horizontal">
						<div class="col-md-12 text-center">
							<%=HtmlUtil.button("ITSI_REPORT_EXPORT_BUTTON","ITSI_REPORT_EXPORT_BUTTON",HtmlUtil.EDIT,"btn2 btn2-green","",request) %>
							<%=HtmlUtil.button("ITSI_REPORT_RESET_BUTTON","ITSI_REPORT_RESET_BUTTON",HtmlUtil.EDIT,"btn2","",request) %>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>

