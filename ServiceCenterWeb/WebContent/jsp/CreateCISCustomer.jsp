<!DOCTYPE html>
<%@page contentType="text/html; charset:UTF-8;"%>
<html lang="en">
<head>
	<script>
		function request_service(){
			$("#result-status").html("processing");
			$.ajax({
				url : "/ServiceCenterWeb/CreateCISCustomer?process=request",
				type : "get",
				dataType : "json",
				data : $("#applicationGroupForm").serialize(),
				success : function(data){
// 					$("textarea[name=requestJson]").val(data.jsonRq);
// 					$("textarea[name=responseJson]").val(data.jsonRs);
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
				<td id="result-status">
				</td>
			</tr>
		</table>
	</form>
	<button type="button" class="btn btn-info" onclick="request_service()">Request Service</button>
<!--   	<button type="button" class="btn btn-info btn-md" onclick="jsonFormat()">Service Data</button> -->
	<br><br>
</body>
</html>