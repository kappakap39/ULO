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
<script type="text/javascript" src="orig/js/export/excel/audit_log.js"></script>
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
String OUTPUT_FILENAME = "FLS-WF_Report_AuditLog_Request_"+strDate.replace("/", "");
 %>
 <input type="hidden" name="DEAFULT_DATE_FROM" value="<%=FormatUtil.display(DATE_FROM_CALENDAR, FormatUtil.TH, FormatUtil.Format.ddMMyyyy)%>"/>
 <input type="hidden" name="DEAFULT_DATE_TO" value="<%=FormatUtil.display(DATE_TO_CALENDAR, FormatUtil.TH, FormatUtil.Format.ddMMyyyy)%>"/>
 <input type="hidden" name="DEAFULT_TIME_HOUR" value="<%=timeHour%>"/>
 <input type="hidden" name="DEAFULT_TIME_MIN" value="<%=timeMin%>"/>

 <input type="hidden" name="DATE_NOW" value="<%=ApplicationDate.getDate()%>"/>
 <input type="hidden" name="OUTPUT_FILENAME" value="<%=OUTPUT_FILENAME%>"/>
 <section class="work_area padding-top" id="AUDIT_LOG_REPORT">
	<div class="row">
		<div class="col-xs-12">
			<div class="panel panel-default">
				<div class="panel-body" style="padding: 15px;">
					<div class="row form-horizontal">
						<div class="col-sm-4">
							<div class="form-group">
								<%=HtmlUtil.getFieldLabel(request,"AUDIT_LOG_USERNAME","col-sm-2 col-md-5 control-label") %>
								<%=HtmlUtil.textBox("USER_NAME_BOX", "", SearchForm.getString("USER_NAME_BOX"), "", "20", "", "", "col-sm8 col-md-7", request)%>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<%=HtmlUtil.getFieldLabel(request, "AUDIT_LOG_ROLE","col-sm-4 col-md-5 control-label") %>
								<%=HtmlUtil.dropdown("ROLE_BOX", "", "", "", "","UserRole", "ALL", "", "", "col-sm8 col-md-7", request)%>
							</div>
						</div>
					</div>
					<div class="row form-horizontal">
						<div class="col-sm-4">
							<div class="form-group">
								<%=HtmlUtil.getMandatoryLabel(request, "AUDIT_LOG_DATE_FROM","col-sm-2 col-md-5 control-label") %>
								<%=HtmlUtil.calendar("DATE_FROM_CALENDAR", "", "DATE_FROM_BOX",DATE_FROM_CALENDAR,
									"", HtmlUtil.EDIT, "", FormatUtil.TH, "col-sm-8 col-md-7", request)%>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<%=HtmlUtil.getMandatoryLabel(request, "AUDIT_LOG_TIME_FROM","col-sm-2 col-md-5 control-label") %>
								<div class="col-sm-10 col-md-7">
									<div class="row">
										<div class="col-xs-12">
											<%=HtmlUtil.dropdown("TIME_FROM_HOUR_BOX", "", "TIME_HOUR", timeHour, "","", "", HtmlUtil.EDIT, "", "col-xs-4 col-xs-padding", request)%>
											<%=HtmlUtil.getLabel(request,"","col-xs-1 control-label")%>
											<%=HtmlUtil.dropdown("TIME_FROM_MIN_BOX", "", "TIME_MIN", timeMin, "","", "",  HtmlUtil.EDIT, "", "col-xs-4 col-xs-padding", request)%>
											<%=HtmlUtil.getLabel(request,"AUDIT_LOG_HH_MM","col-xs-3 control-label")%>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="row form-horizontal">
						<div class="col-sm-4">
							<div class="form-group">
								<%=HtmlUtil.getMandatoryLabel(request, "AUDIT_LOG_DATE_TO","col-sm-2 col-md-5 control-label") %>
								<%=HtmlUtil.calendar("DATE_TO_CALENDAR", "", "DATE_TO_BOX",DATE_TO_CALENDAR,
									"", HtmlUtil.EDIT, "", FormatUtil.TH, "col-sm-8 col-md-7", request)%>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<%=HtmlUtil.getMandatoryLabel(request, "AUDIT_LOG_TIME_TO","col-sm-2 col-md-5 control-label") %>
								<div class="col-sm-10 col-md-7">
									<div class="row">
										<div class="col-xs-12">
											<%=HtmlUtil.dropdown("TIME_TO_HOUR_BOX", "", "TIME_HOUR", timeHour, "","", "", "", "", "col-xs-4 col-xs-padding", request)%>
											<%=HtmlUtil.getLabel(request,"","col-xs-1 control-label")%>
											<%=HtmlUtil.dropdown("TIME_TO_MIN_BOX", "", "TIME_MIN", timeMin, "","", "", "", "", "col-xs-4 col-xs-padding", request)%>
											<%=HtmlUtil.getLabel(request,"AUDIT_LOG_HH_MM","col-xs-3 control-label")%>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="row form-horizontal">
						<div class="col-md-12 text-center">
							<%=HtmlUtil.button("AUDIT_LOG_EXPORT_BUTTON","AUDIT_LOG_EXPORT_BUTTON",HtmlUtil.EDIT,"btn2 btn2-green","",request) %>
							<%=HtmlUtil.button("AUDIT_LOG_RESET_BUTTON","AUDIT_LOG_RESET_BUTTON",HtmlUtil.EDIT,"btn2","",request) %>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>

