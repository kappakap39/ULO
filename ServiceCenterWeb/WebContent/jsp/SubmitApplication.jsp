<!DOCTYPE html>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page contentType="text/html; charset=UTF-8"%>
<html>
	<script>
		$(document).ready(function(){
			console.clear();
			init();
			SubmitApplicationPanel();
		});
		
		function init(){
			$.ajax({
				url : "/ServiceCenterWeb/SubmitApplicationServlet?process=default",
				type : "get",
				success : function(data){
					var obj = $.parseJSON(data);
					console.log(obj);
					$("#SubmitApplicationPanel input[name=UrlWebService]").val(obj.url);
				}
			});
			$("#SubmitApplicationJsonPanel").hide();
		}
		
		function SubmitApplicationPanel(){
			$("#SubmitApplicationPanel").show();
			$("#FollowUpResultPropertiesPanel").hide();
		}

		
		function FollowUpResult(){
			$.ajax({
				url : "/ServiceCenterWeb/SubmitApplicationServlet?process=request",
				type : "get",
				data : $("#SubmitApplicationPanel input").serialize(),
				success : function(data){
					var obj = $.parseJSON(data);
					console.log(obj);
					$("textarea[name=requestJson]").val(obj.jsonRq);
					$("textarea[name=responseJson]").val(obj.jsonRs);
				}
			});
		}

		function jsonFormat(){
	  		$("#dialog").html("");
	  		$("#dialog").attr("title","JSON Format");
	  		$("#dialog").dialog({modal:true, width:1050, minHeight:400, draggable:true, resizable:true, autoResize:true});
	  		$.ajax({
	  			url : "/ServiceCenterWeb/jsp/jsonPopup.jsp",
	  			type : "get",
	  			success : function(data){
	  				$("#dialog").html(data);
	  			}
	  		});
	  	}
	</script>
	<body>
		<div class="btn-group">
	  		<button type="button" class="btn btn-info btn-md" onclick="SubmitApplicationPanel();">Submit Application</button>
	  	</div>
	  	<br><br>
		<div id="SubmitApplicationPanel">
			<Strong>Submit Application</Strong>
			<div class="well">
				<table>
					<tr>
						<td>URL Web Service : </td>
						<td><input type="text" class="form-control input-sm" size="75" name="UrlWebService"></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>QR1 Pre Printed: </td>
						<td><input type="text" class="form-control input-sm" size="75" name="qr1"></td>
						<td>QR2 Running No. : </td>
						<td><input type="text" class="form-control input-sm" size="75" name="qr2"></td>
					</tr>
					<tr>
						<td>RC Code : </td>
						<td><input type="text" class="form-control input-sm" size="75" name="rcCode"></td>
						<td>Branch Code : </td>
						<td><input type="text" class="form-control input-sm" size="75" name="branchCode"></td>
					</tr>
					<tr>
						<td>Channel: </td>
						<td><input type="text" class="form-control input-sm" size="75" name="channel"></td>
						<td>Date at scan Document (yyyy-MM-dd HH:mm:ss): </td>
						<td><input type="text" class="form-control input-sm" size="75" name="scanDate"></td>
					</tr>
					<tr>
						<td>WebScan UserID : </td>
						<td><input type="text" class="form-control input-sm" size="75" name="WebScanUser"></td>
						<td> </td>
						<td></td>
					</tr>
				</table>
			</div>
			<button type="button" class="btn btn-info" onclick="FollowUpResult()">Request</button>
			<button type="button" class="btn btn-info btn-md" onclick="jsonFormat()">Json Format</button>
		</div>
		<div class="well" id="SubmitApplicationJsonPanel">
	  		<Strong>Request/Response JSON Format</Strong><br>
	  		Request :
			<textarea class="form-control" name="requestJson" rows="5" readonly></textarea>
			Response :
			<textarea class="form-control" name="responseJson" rows="5" readonly></textarea>
  		</div>
	</body>
</html>