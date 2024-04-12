<%@page import="com.eaf.service.module.model.CheckProductDupRequestDataM"%>
<!DOCTYPE html>
<%@page contentType="text/html; charset:UTF-8;"%>
<html lang="en">
<head>
	<script>
		function request_service()
		{
			if(!$("#idNo_input").val())
			{
				alert('Please input IDNO.');
			}
			else
			{
				$.ajax({
					url : "/ServiceCenterWeb/CheckDup?idNo=" + $("#idNo_input").val(),
					type : "get",
					success : function(data){
						var obj = $.parseJSON(data);
						var req = $.parseJSON(obj.jsonRq);
						var resp = $.parseJSON(obj.jsonRs);
						$("#req_textarea").val(JSON.stringify(req));
						$("#resp_textarea").val(JSON.stringify(resp));
					}
				});
			}
		}
	</script>
</head>

<body>
	<Strong>CheckProductDup</Strong>
	<form class="form-inline well" id="idNoForm">
		<table>
			<tr>
				<td>IDNO</td>
				<td><input id="idNo_input" type="text" class="form-control input-sm" size="60" name="idNo_input"></td>
				<td id="result-status">
				</td>
			</tr>
		</table>
	</form>
	<button type="button" class="btn btn-info" onclick="request_service()">Request Service</button>
	<br><br>
	<table>
	<tr>
		<td><Strong>Request</Strong></td>
		<td><Strong>Response</Strong></td>
	</tr>
	<tr>
		<td><textarea id="req_textarea" rows="12" cols="50"></textarea></td>
		<td><textarea id="resp_textarea" rows="12" cols="50"></textarea></td>
	<tr>
	</table>
	<br><br>
</body>
</html>