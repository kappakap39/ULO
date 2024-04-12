<!DOCTYPE html>
<%@page contentType="text/html; charset=UTF-8"%>
<html>
	<script>
		$(document).ready(function(){
			console.clear();
			init();
			CreateDocSetRequestPanel();
		});
		
		function init(){
			$.ajax({
				url : "/ServiceCenterWeb/CreateDocSetServlet?process=default",
				type : "get",
				success : function(data){
					var obj = $.parseJSON(data);
					console.log(obj);
					$("#CreateDocSetRequestPanel input[name=UrlWebService]").val(obj.url);
					$("#CreateDocSetPropertiesPanel input[name=UrlWebService]").val(obj.url);
				}
			});
			$("#CreateDocSetJsonPanel").hide();
		}
		
		function CreateDocSetRequestPanel(){
			$("#CreateDocSetRequestPanel").show();
			$("#CreateDocSetPropertiesPanel").hide();
		}
		
		function CreateDocSetProptiesPanel(){
			$("#CreateDocSetRequestPanel").hide();
			$("#CreateDocSetPropertiesPanel").show();
		}
		
		function CreateDocSet(){
			$.ajax({
				url : "/ServiceCenterWeb/CreateDocSetServlet?process=request",
				type : "get",
				data : $("#CreateDocSetRequestPanel input").serialize(),
				success : function(data){
					var obj = $.parseJSON(data);
					console.log(obj);
					$("textarea[name=requestJson]").val(obj.jsonRq);
					$("textarea[name=responseJson]").val(obj.jsonRs);
				}
			});
		}
		
		function SaveProperties(){
			$.ajax({
				url : "/ServiceCenterWeb/CreateDocSetServlet?process=save",
				type : "get",
				data : $("#CreateDocSetPropertiesPanel input").serialize(),
				success : function(data){
					console.log("save properties success");
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
	  		<button type="button" class="btn btn-info btn-md" onclick="CreateDocSetRequestPanel();">Send Create Doc Set</button>
	  		<button type="button" class="btn btn-info btn-md" onclick="CreateDocSetProptiesPanel();">Edit Props.</button>
	  	</div>
	  	<br><br>
		<div id="CreateDocSetRequestPanel">
			<Strong>Create Document Set</Strong>
			<div class="well">
				<table>
					<tr>
						<td>URL Web Service : </td>
						<td><input type="text" class="form-control input-sm" size="75" name="UrlWebService"></td>
						<td>Set ID : </td>
						<td><input type="text" class="form-control input-sm" size="75" name="SetID"></td>
					</tr>
					<tr>
						<td>Form QR Code : </td>
						<td><input type="text" class="form-control input-sm" size="75" name="FormQRCode"></td>
						<td>Format Type No : </td>
						<td><input type="text" class="form-control input-sm" size="75" name="FormatTypeNo"></td>
					</tr>
					<tr>
						<td>IT Purpose : </td>
						<td><input type="text" class="form-control input-sm" size="75" name="ITPurpose"></td>
						<td>Form Location : </td>
						<td><input type="text" class="form-control input-sm" size="75" name="FormLocation"></td>
					</tr>
					<tr>
						<td>Product Type No : </td>
						<td><input type="text" class="form-control input-sm" size="75" name="ProductTypeNo"></td>
						<td>Form Type No : </td>
						<td><input type="text" class="form-control input-sm" size="75" name="FormTypeNo"></td>
					</tr>
					<tr>
						<td>Form Version No : </td>
						<td><input type="text" class="form-control input-sm" size="75" name="FormVersionNo"></td>
						<td>IP Address : </td>
						<td><input type="text" class="form-control input-sm" size="75" name="IPAddress"></td>
					</tr>
					<tr>
						<td>Event Flag : </td>
						<td><input type="text" class="form-control input-sm" size="75" name="EventFlag"></td>
						<td>Priority : </td>
						<td><input type="text" class="form-control input-sm" size="75" name="Priority"></td>
					</tr>
					<tr>
						<td>Web Scan User : </td>
						<td><input type="text" class="form-control input-sm" size="75" name="WebScanUser"></td>
						<td>Document Creation Date : </td>
						<td><input type="text" class="form-control input-sm" size="75" name="DocumentCreationDate"></td>
					</tr>
					<tr>
						<td>Document Chronicle ID : </td>
						<td><input type="text" class="form-control input-sm" size="75" name="DocumentChronicleID"></td>
						<td>Document Type : </td>
						<td><input type="text" class="form-control input-sm" size="75" name="DocumentType"></td>
					</tr>
					<tr>
						<td>Document Name  : </td>
						<td><input type="text" class="form-control input-sm" size="75" name="DocumentName"></td>
						<td>Document Format : </td>
						<td><input type="text" class="form-control input-sm" size="75" name="DocumentFormat"></td>
					</tr>
				</table>
			</div>
			<button type="button" class="btn btn-info" onclick="CreateDocSet()">Request</button>
			<button type="button" class="btn btn-info btn-md" onclick="jsonFormat()">Json Format</button>
		</div>
		<div id="CreateDocSetPropertiesPanel">
			<Strong>Properties</Strong>
			<div class="well">
				<table>
					<tr>
						<td>URL Web Service : </td>
						<td><input type="text" class="form-control input-sm" size="75" name="UrlWebService"></td>
					</tr>
				</table>
			</div>
			<button type="button" class="btn btn-info" onclick="SaveProperties()">Save</button>
		</div>
		
		<div class="well" id="CreateDocSetJsonPanel">
	  		<Strong>Request/Response JSON Format</Strong><br>
	  		Request :
			<textarea class="form-control" name="requestJson" rows="5" readonly></textarea>
			Response :
			<textarea class="form-control" name="responseJson" rows="5" readonly></textarea>
  		</div>
	</body>
</html>