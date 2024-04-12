<!DOCTYPE html>
<%@page contentType="text/html; charset:UTF-8;" %>
<html lang=en>
<head>
	<script>
		$(document).ready(function(){
			console.clear();
			$("#CIS0315U01_RqRs").hide();
			$("#CIS0315U01_setting").hide();
						
			$.ajax({
				url : "/ServiceCenterWeb/CIS0315U01?process=defualt",
				type : "get",
				success : function(data){
					var obj = $.parseJSON(data);
					console.log(obj);
					$("input[name=url_input]").val(obj.urlWebService);
					$("input[name=url_setting]").val(obj.urlWebService);
					$("input[name=cusId_setting]").val(obj.customerId);
				}
			});
		});
		
		function CIS0315U01_input(){
			$("#CIS0315U01_input").show();
			$("#CIS0315U01_output").show();
			$("#CIS0315U01_setting").hide();
		}
		
		function CIS0315U01_properties(){
			$("#CIS0315U01_input").hide();
			$("#CIS0315U01_output").hide();
			$("#CIS0315U01_setting").show();
		}
		
		function saveSetting(){
			$.ajax({
				url : "/ServiceCenterWeb/CIS0315U01?process=save",
				type : "post",
				data : $("#CIS0315U01Form_setting").serialize(),
				success : function(data){
// 					alert("save data");
				}
			});
		}
		
		function clearSetting(){
			$("input[name=url_setting]").val("");
			$("input[name=cusId_setting]").val("");
		}
		
		function requestService(){
			$.ajax({
				url : "/ServiceCenterWeb/CIS0315U01?process=request",
				type : "get",
				data : $("#CIS0315U01Form_input").serialize(),
				success : function(data){
					var obj = $.parseJSON(data);
					console.log(obj);
					$("#cusId_output").val(obj.customerId);
					$("textarea[name=requestJson]").val(obj.jsonRq);
  					$("textarea[name=responseJson]").val(obj.jsonRs);
				}
			});
		}
		
		function clearInput(){
			$("input[name=url_input]").val("");
			$("input[name=cusId_input]").val("");
			$("input[name=scrAsstCode_input]").val("");
			$("input[name=scrAsstOthDesc_input]").val("");
			$("input[name=policalPosDesc_input]").val("");
			$("input[name=asstValAmt_input]").val("");
			$("input[name=asstValCode_input]").val("");
		}
		
// 		function jsonFormat(){
// 	  		$("#dialog").html("");
// 	  		$("#dialog").attr("title","JSON Format");
// 	  		$("#dialog").dialog({modal:true, width:1050, minHeight:400, draggable:true, resizable:true, autoResize:true});
// 	  		$.ajax({
// 	  			url : "/ServiceCenterWeb/jsp/jsonPopup.jsp",
// 	  			type : "get",
// 	  			success : function(data){
// 	  				$("#dialog").html(data);
// 	  			}
// 	  		});
// 	  	}
	</script>
</head>
<body>
	 <ul class="nav nav-tabs">
  		<li class="active"><a data-toggle="tab" onclick="CIS0315U01_input();">Send CIS0315U01</a></li>
		<li><a data-toggle="tab" onclick="CIS0315U01_properties();">Edit Props.</a></li>
  	</ul>
	<br>
	<div id="CIS0315U01_input">
		<form id="CIS0315U01Form_input" class="well form-inline" >
			<strong>Input</strong>
			<table>
				<tr>
					<td>URL Webservice</td>
					<td><input type="text" name="url_input" class="form-control input-sm" size="75"></td>
				</tr>
				<tr>
					<td>Customer Id</td>
					<td><input type="text" name="cusId_input" class="form-control input-sm" size="60"></td>
				</tr>
				<tr>
					<td>Source Asst. Code</td>
					<td><input type="text" name="scrAsstCode_input" class="form-control input-sm" size="60"></td>
				</tr>
				<tr>
					<td>Source Asst. Other Desc</td>
					<td><input type="text" name="scrAsstOthDesc_input" class="form-control input-sm" size="60"></td>
				</tr>
				<tr>
					<td>Polical Position Desc</td>
					<td><input type="text" name="policalPosDesc_input" class="form-control input-sm" size="60"></td>
				</tr>
				<tr>
					<td>Asset Value Amount</td>
					<td><input type="text" name="asstValAmt_input" class="form-control input-sm" size="60"></td>
				</tr>
				<tr>
					<td>Asset Value Code</td>
					<td><input type="text" name="asstValCode_input" class="form-control input-sm" size="60"></td>
				</tr>
			</table>
		</form>
		<button type="button" class="btn btn-info" onclick="requestService()">Request Service</button>
		<button type="button" class="btn btn-info" onclick="clearInput()">Clear</button>
		<button type="button" class="btn btn-info btn-md" onclick="jsonFormat()">Save Data</button>
		<br><br>
	</div>
	<div class="well" id="CIS0315U01_output"><strong>Output</strong>
		<table>
			<tr>
				<td>Customer Id</td>
				<td><input type="text" id="cusId_output" class="form-control input-sm" size="60" readonly></td>
			</tr>
		</table>
	</div>
	<div class="well" id="CIS0315U01_RqRs">
 			<Strong>Request/Response JSON Format</Strong><br>
  		Request :
		<textarea class="form-control" name="requestJson" rows="5" readonly></textarea>
		Response :
		<textarea class="form-control" name="responseJson" rows="5" readonly></textarea>
  	</div>
	<div id="CIS0315U01_setting">
		<form id="CIS0315U01Form_setting" class="well form-inline">
			<Strong>Setting</Strong>
			<table>
				<tr>
					<td>URL Webservice</td>
					<td><input type="text" name="url_setting" class="form-control input-sm" size="75"></td>
				</tr>
				<tr>
					<td>Customer Id</td>
					<td><input type="text" name="cusId_setting" class="form-control input-sm" size="60"></td>
				</tr>
			</table>
		</form>
		<button type="button" class="btn btn-info" onclick="saveSetting()">Save</button>
		<button type="button" class="btn btn-info" onclick="clearSetting()">Clear</button>
	</div>
</body>
</html>
