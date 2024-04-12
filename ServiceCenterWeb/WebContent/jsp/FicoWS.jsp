<!DOCTYPE html>
<%@page contentType="text/html; charset:UTF-8;"%>
<html lang="en">
<head>
	<script>
		$(document).ready(function(){
			console.clear();
// 			$.ajax({
// 				url : "/ServiceCenterWeb/Fico?process=defualt",
// 				type : "GET",
// 				success : function(data) {
// 					var obj = $.parseJSON(data);
// 					$("input[name=urlWebService_input]").val(obj.urlWebService);
// 				}
// 			});
		});
		
		function request_service(){
			$("#result-status").html("processing");
			$.ajax({
				url : "/ServiceCenterWeb/Fico?process=request",
				type : "get",
				dataType : "json",
				data : $("#applicationGroupForm").serialize(),
				success : function(data){
					$("textarea[name=requestJson]").val(data.jsonRq);
					$("textarea[name=responseJson]").val(data.jsonRs);
					$("#result-status").html("finished");
				}
			});
		}
	</script>
</head>

<body>
	<form class="form-inline well" id="applicationGroupForm">
		<Strong>Input</Strong>
		<table>
			<tr>
				<td>Application Group No</td>
				<td><input type="text" class="form-control input-sm" size="60" name="applicationGroupNo_input"></td>
				<td>Decision Station</td>
				<td>
					<select style="min-width:100px;" name="decision_station">
						<option value="IA1">IA1</option>
						<option value="PB1">PB1</option>
						<option value="DE1">DE1</option>
						<option value="DV1">DV1</option>
						<option value="DV2">DV2</option>
						<option value="FI">FI</option>
						<option value="DE2">DE2</option>
						<option value="DC">DC</option>
					</select>
				</td>
				<td id="result-status">
				</td>
			</tr>
<!-- 			<tr> -->
<!-- 				<td>URL Web Service</td> -->
<!-- 				<td> -->
<!-- 					<input type="text" class="form-control input-sm" size="60" name="urlWebService_input"> -->
<!-- 				</td> -->
<!-- 			</tr> -->
		</table>
	</form>
	<div class="well" id="createKVIApplication_RqRs" style="display: none;">
  		<Strong>Request/Response JSON Format</Strong><br>
  		Request :
		<textarea class="form-control" name="requestJson" rows="5" readonly></textarea>
		Response :
		<textarea class="form-control" name="responseJson" rows="5" readonly></textarea>
  	</div>
	<button type="button" class="btn btn-info" onclick="request_service()">Request Service</button>
  	<button type="button" class="btn btn-info btn-md" onclick="jsonFormat()">Service Data</button>
	<br><br>
</body>
</html>