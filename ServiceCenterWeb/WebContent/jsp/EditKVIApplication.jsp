<!DOCTYPE html>
<%@page contentType="text/html; chatset=UTF-8" %>
<html lang=en>
<head>
	<script>
		$(document).ready(function(){
			console.clear();
			$("#editKVIApplication_RqRs").hide();
			$("#editKVIApplication_setting").hide();
			
			$.ajax({
				url : "/ServiceCenterWeb/EditKVIApp?process=defualt",
				type : "get",
				success : function(data){
					var obj = $.parseJSON(data);
					console.log(obj);
					$("input[name=url_input]").val(obj.urlWebService);
					$("input[name=url_setting]").val(obj.urlWebService);
					$("input[name=fId_setting]").val(obj.fId);
					$("input[name=tokenId_setting]").val(obj.tokenId);
				}
			});
		});
		
		function goToInput(){
			$("#editKVIApplication_input").show();
			$("#editKVIApplication_output").show();
			$("#editKVIApplication_setting").hide();
		}
		
		function goToSetting(){
			$("#editKVIApplication_input").hide();
			$("#editKVIApplication_output").hide();
			$("#editKVIApplication_setting").show();
		}
		
		function requestService(){
			$.ajax({
				url : "/ServiceCenterWeb/EditKVIApp?process=request",
				type : "get",
				data : $("#editKVIApplicationForm_input").serialize(),
				success : function(data){
					var obj = $.parseJSON(data);
					console.log(obj);
					$("#editKVIApplication_output > table > tbody > tr:nth-child(1) > td:nth-child(2) > input ").val(obj.fId);
					$("#editKVIApplication_output > table > tbody > tr:nth-child(2) > td:nth-child(2) > input ").val(obj.tokenId);
					$("textarea[name=requestJson]").val(obj.jsonRq);
  					$("textarea[name=responseJson]").val(obj.jsonRs);
				}				
			});
		}
		
		function clearInput(){
			$("#editKVIApplicationForm_input").trigger("reset");
		}
		
		function editSetting(){
			$.ajax({
				url : "/ServiceCenterWeb/EditKVIApp?process=edit",
				type : "post",
				data : $("#editKVIApplicationForm_setting").serialize(),
				success : function(data){
// 					alert("save data");
				}
			});
		}
		
		function clearSetting(){
			$("#editKVIApplicationForm_setting").trigger("reset");
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
  		<li class="active"><a data-toggle="tab" onclick="goToInput();">Send EditKVI</a></li>
		<li><a data-toggle="tab" onclick="goToSetting();">Edit Props.</a></li>
  	</ul>
	<br>
	<div id="editKVIApplication_input">
		<form class="form-inline well" id="editKVIApplicationForm_input">
			<Strong>Input</Strong>
			<table>
				<tr>
					<td>URL Web Service</td>
					<td><input type="text" class="form-control input-sm" size="100" name="url_input"></td>
				</tr>
				<tr>
					<td>F Id</td>
					<td><input type="text" class="form-control input-sm" size="50" name="fId_input"></td>
				</tr>
			</table>
		</form>
		<button type="button" class="btn btn-info" onclick="requestService()">Request Service</button>
		<button type="button" class="btn btn-info" onclick="clearInput()">Clear</button>
		<button type="button" class="btn btn-info btn-md" onclick="jsonFormat()">Service Data</button>
		<br>
	</div>
	<br>
	<div class="well" id="editKVIApplication_output">
		<Strong>Output</Strong>
		<table>
			<tr>
				<td>F Id</td>
				<td><input type="text" class="form-control input-sm" size="50" readonly></td>
			</tr>
			<tr>
				<td>Token Id</td>
				<td><input type="text" class="form-control input-sm" size="50" readonly></td>
			</tr>
		</table>
	</div>
	<div class="well" id="editKVIApplication_RqRs">
  		<Strong>Request/Response JSON Format</Strong><br>
  		Request :
		<textarea class="form-control" name="requestJson" rows="5" readonly></textarea>
		Response :
		<textarea class="form-control" name="responseJson" rows="5" readonly></textarea>
  	</div>
	<div id="editKVIApplication_setting">
		<form class="well form-inline" id="editKVIApplicationForm_setting">
			<Strong>Setting</Strong>
			<table>
				<tr>
					<td>Url Web Service</td>
					<td><input type="text" class="form-control input-sm" size="70" name="url_setting"></td>
				</tr>
				<tr>
					<td>F Id</td>
					<td><input type="text" class="form-control input-sm" size="50" name="fId_setting"></td>
				</tr>
				<tr>
					<td>Token Id</td>
					<td><input type="text" class="form-control input-sm" size="50" name="tokenId_setting"></td>
				</tr>
			</table>
		</form>
		<button type="button" class="btn btn-info" onclick="editSetting()">Edit</button>
		<button type="button" class="btn btn-info" onclick="clearSetting()">Clear</button>
	</div>
</body>
</html>