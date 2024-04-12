<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page contentType="text/html; charset:UTF-8;" %>
<script type="text/javascript" src="orig/js/ld/uploadForecast.js"></script>
<jsp:useBean id="SearchForm" scope="session" class="com.eaf.core.ulo.common.engine.SearchFormHandler" />

<section class="work_area padding-top">
<!-- 	<h3>Upload Forecast App In</h3> -->
	<div class="panel panel-default">
		<div class="panel-body" style="padding:15px;">
			<div class="col-sm-12">
				<div class="row form-horizontal">
<!-- 					<div class="col-sm-12"> -->
<%-- 						<%=HtmlUtil.getFieldLabel(request, "UPLOAD_FORECAST_FILENAME","col-sm-2") %> --%>
<%-- 						<%=HtmlUtil.textBox("FILE_NAME", "", "", "", "20", "", "readonly", "col-sm-4 readonly", request) %> --%>
<!-- 						<span class="btn btn-default btn-file"> -->
<!--    							Browse <input type="file" name="IMPORT_FILE" accept="application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"> -->
<!-- 						</span> -->
<!-- 					</div> -->
					<div class="col-sm-12">
						<div class="form-group">
							<%=HtmlUtil.getMandatoryLabel(request, "UPLOAD_FORECAST_FILENAME","col-sm-3 control-label")%>
							<div class="col-sm-9 col-md-8">
								<div id="UPLOAD_FORECAST_FILENAME">Browse</div>
							</div>
							<%=HtmlUtil.hidden("FILE_NAME", "")%>
						</div>
					</div>
				</div>
				<div class="row form-horizontal">
					<div class="col-sm-12" align="center">
						<%=HtmlUtil.button("UPLOAD_FORECAST_IMPORT_BUTTON", "IMPORT_OT_DATA_IMPORT_BUTTON", HtmlUtil.EDIT, "btn2 btn2-green", "", request) %>
						<%=HtmlUtil.button("UPLOAD_FORECAST_CANCEL_BUTTON", "IMPORT_OT_DATA_CANCEL_BUTTON", HtmlUtil.EDIT, "btn2", "", request) %>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="panel-body">
		<div class="col-md-6 col-md-offset-4">
			<%=HtmlUtil.getLabel(request, "IMPORT_OT_DATA_TOTAL", "col-sm-2 control-label") %>
			<span id="TOTAL"></span>
		</div>
		<div class="col-md-6 col-md-offset-4">
			<%=HtmlUtil.getLabel(request, "IMPORT_OT_DATA_SUCCESS", "col-sm-2 control-label") %>
			<span id="SUCCESS"></span>
		</div>
		<div class="col-md-6 col-md-offset-4">
			<%=HtmlUtil.getLabel(request, "IMPORT_OT_DATA_REJECT", "col-sm-2 control-label") %>
			<span id="REJECT"></span>
			
		</div>
		<div class="col-md-6 col-md-offset-4">
			<%=HtmlUtil.getLabel(request, "IMPORT_OT_DATA_EXPORT_REJECT", "col-sm-4 control-label") %>		
			<a id="DOWNLOAD_EXCEL" href="#" onclick="downloadExcel()">Excel</a>
		</div>
	</div>
</section>