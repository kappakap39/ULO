<!DOCTYPE html>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.Clob"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page contentType="text/html; charset:UTF-8;"%>
<html lang="en">
<%
	Logger logger = Logger.getLogger(this.getClass());
	String CACHE_SERVICE_DATA = SystemConstant.getConstant("CACHE_SERVICE_DATA");
	ArrayList<HashMap> serviceDataList = CacheControl.getCacheList(CACHE_SERVICE_DATA);
%>
<head>
	<script type="text/javascript" src="../js/pace.js"></script>
	<script type="text/javascript" src="../js/vkbeautify.0.99.00.beta.min.js"></script>
	<script>
		var contextPath = "<%=request.getContextPath()%>";
		var indent = 2;
		
		var restServiceData = {};
		var soapServiceData = {};
<%
		for(HashMap serviceData : serviceDataList) {
			String serviceId = (String) serviceData.get("SERVICE_ID");
			String serviceType = (String) serviceData.get("SERVICE_TYPE");
			String path = (String) serviceData.get("PATH");
			String dataHeader = (String) serviceData.get("DATA_HEADER");
 			String dataBody = (String) serviceData.get("DATA_BODY");
%>
			var data = {
				"PATH" : '<%=path%>',
				"DATA_HEADER": '<%=dataHeader%>',
				"DATA_BODY": '<%=dataBody%>'
			};
<%
			if("REST".equals(serviceType)) {
%>
				restServiceData["<%=serviceId%>"] = data;
<%
			} else if("SOAP".equals(serviceType)) {
%>
				soapServiceData["<%=serviceId%>"] = data;
<%
			}
		}
%>
	
		var requestMethodList = [
			"POST",
			"GET",
		    "PUT",
		    "DELETE",
		    "HEAD",
		    "OPTIONS",
		    "TRACE",
		    "PATCH"
		];
		
		var restServiceList = [];
		for(var serviceId in restServiceData) {
			restServiceList.push(serviceId);
		}
		
		var soapServiceList = [];
		for(var serviceId in soapServiceData) {
			soapServiceList.push(serviceId);
		}

		function tabRest() {
			$("#tab_REST").show();
			$("#tab_SOAP").hide();
		}
		function tabSoap() {
			$("#tab_REST").hide();
			$("#tab_SOAP").show();
		}
		
		function request_REST() {
			var url = $("#url_REST").val();
			var method = $("#method_REST :selected").text();
			var header = JSON.stringify(vkbeautify.jsonmin($("#req_header_textarea_REST").val()));
			var data = JSON.stringify(vkbeautify.jsonmin($("#req_textarea_REST").val()));
			var requestData = '{"url":"'+url+'","method":"'+method+'","header":'+header+',"data":'+data+'}';
			console.log(contextPath + "/service/connectivitytest/rest");
			$.ajax({
				url: contextPath + "/rest/controller/service/connectivitytest/rest",
				type: "POST",
				dataType: "json",
				contentType: 'application/json; charset="utf-8"',
				data: requestData,
				success: function(result) {
					var resultString = JSON.stringify(result, null, indent);
					$("#resp_textarea_REST").val(resultString);
				},
				error: function(result, error, errorThrown) {
					var response = error + "\n\n" + errorThrown;
					$("#resp_textarea_REST").val(response);
				}
			});
		}
		
		function request_SOAP() {
			var url = $("#url_SOAP").val();
			var header = JSON.stringify(vkbeautify.jsonmin($("#req_header_textarea_SOAP").val()));
			var data = vkbeautify.xmlmin($("#req_textarea_SOAP").val());
			data = data.replace(/\"/g, '\\"');
			var requestData = '{"url":"'+url+'","header":'+header+',"data":"'+data+'"}';
			console.log(contextPath + "/service/connectivitytest/soap");
			$.ajax({
				url: contextPath + "/rest/controller/service/connectivitytest/soap",
				type: "POST",
				dataType: "xml",
				contentType: 'text/xml; charset="utf-8"',
				data: requestData,
				success: function(result) {
					var resultString = new XMLSerializer().serializeToString(result);
					$("#resp_textarea_SOAP").val(vkbeautify.xml(resultString, indent));
				},
				error: function(result, error, errorThrown) {
					var response = error + "\n\n" + errorThrown;
					$("#resp_textarea_SOAP").val(response);
				}
			});
		}
		
		function populateList(dropdownId, optionList, placeHolder) {
			if(placeHolder) {
				$(dropdownId).append( "<option value=''>" + placeHolder + "</option>" );
			}
			$.each(optionList, function(index, value) {
				$(dropdownId).append($("<option></option>")
						.attr("value", value).text(value) ); 
			});
		}
		
		function getRestServiceUrl() {
			var serviceId = $("#dropdown_service_REST :selected").text();
			var url = restServiceData[serviceId]["PATH"];
			var header = restServiceData[serviceId]["DATA_HEADER"];
			var body = restServiceData[serviceId]["DATA_BODY"];
			$("#url_REST").val(url);
			$("#req_header_textarea_REST").val(vkbeautify.json(header, indent));
			$("#req_textarea_REST").val(vkbeautify.json(body, indent));
		}
		
		function getSoapServiceUrl() {
			var serviceId = $("#dropdown_service_SOAP :selected").text();
			var url = soapServiceData[serviceId]["PATH"];
			var header = soapServiceData[serviceId]["DATA_HEADER"];
			var body = soapServiceData[serviceId]["DATA_BODY"];
			$("#url_SOAP").val(url);
			$("#req_header_textarea_SOAP").val(vkbeautify.json(header, indent));
			$("#req_textarea_SOAP").val(vkbeautify.xml(body, indent));
		}
		
		function escapeQuote(string) {
			return 
		}
	
		$(document).ready(function() {
			$(document).ajaxStart(function() {
				Pace.restart();
			});
			var pleaseSelect = "Please Select";
			populateList("#dropdown_service_REST", restServiceList, pleaseSelect);
			populateList("#dropdown_service_SOAP", soapServiceList, pleaseSelect);
			populateList("#dropdown_method_REST", requestMethodList);
			
			$("#dropdown_service_REST").change(getRestServiceUrl);
			$("#dropdown_service_SOAP").change(getSoapServiceUrl);
		});
	</script>
	<style type="text/css">
		.well > .row {
		   display: flex;
		   align-items: center;
		}
		
		.row > div > textarea {
			width: 100%;
			font-family: Consolas;
			white-space: nowrap;
		}
		
		.label {
			font-size: 85%;
		}
	</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a data-toggle="tab" onclick="tabRest();">REST</a></li>
		<li><a data-toggle="tab" onclick="tabSoap();">SOAP</a></li>
	</ul>
	<div class="well" id="tab_REST">
		<div class="row">
			<div class="col-md-1">
				Service
			</div>
			<div id="service_REST" class="col-md-5">
				<select class="form-control form-control-sm" type="text" id="dropdown_service_REST"></select>
			</div>
			<div class="col-md-2" id="method_REST">
				<select class="form-control form-control-sm" type="text" id="dropdown_method_REST"></select>
			</div>
		</div>
		<br>
		<div class="row">
			<div class="col-md-1">
				URL
			</div>
			<div class="col-md-7">
				<input id="url_REST" type="text" class="form-control input-sm">
			</div>
		</div>
		<br>
		<div class="row">
			<div class="col-md-4">
				<span class="label label-default">Request Header</span>
				<textarea id="req_header_textarea_REST" rows="5" cols="50" style="margin-bottom: 9px;"></textarea>
				<span class="label label-default">Request Body</span>
				<textarea id="req_textarea_REST" rows="14" cols="50"></textarea>
			</div>
			<div class="col-md-4">
				<span class="label label-default">Response</span>
				<textarea id="resp_textarea_REST" rows="21" cols="50"></textarea>
			</div>
		</div>
		<br>
		<div class="row">
			<div class="col-md-4">
				<button type="button" class="btn btn-info" onclick="request_REST()">Send Request</button>
			</div>
		</div>
	</div>
	
	<div hidden class="well" id="tab_SOAP">
		<div class="row">
			<div class="col-md-1">
				Service
			</div>
			<div id="service_SOAP" class="col-md-5">
				<select class="form-control form-control-sm" type="text" id="dropdown_service_SOAP"></select>
			</div>
			<div class="col-md-2">
				<select disabled class="form-control form-control-sm" type="text" id="dropdown_method_SOAP">
					<option value="POST">POST</option>
				</select>
			</div>
		</div>
		<br>
		<div class="row">
			<div class="col-md-1">
				URL
			</div>
			<div class="col-md-7">
				<input id="url_SOAP" type="text" class="form-control input-sm">
			</div>
		</div>
		<br>
		<div class="row">
			<div class="col-md-4">
				<span class="label label-default">Request Header</span>
				<textarea id="req_header_textarea_SOAP" rows="5" cols="50" style="margin-bottom: 9px;"></textarea>
				<span class="label label-default">Request Body</span>
				<textarea id="req_textarea_SOAP" rows="14" cols="50"></textarea>
			</div>
			<div class="col-md-4">
				<span class="label label-default">Response</span>
				<textarea id="resp_textarea_SOAP" rows="21" cols="50"></textarea>
			</div>
		</div>
		<br>
		<div class="row">
			<div class="col-md-4">
				<button type="button" class="btn btn-info" onclick="request_SOAP()">Send Request</button>
			</div>
		</div>
	</div>
</body>
</html>