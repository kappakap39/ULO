<!DOCTYPE html>
<%@page contentType="text/html; charset=UTF-8"%>
<html>
	<script>
		$(document).ready(function(){
			console.clear();
			init();
			UpdateApprovalStatusRequestPanel();
		});
		
		function init(){
			$.ajax({
				url : "/ServiceCenterWeb/UpdateApprovalStatusServlet?process=default",
				type : "get",
				success : function(data){
					var obj = $.parseJSON(data);
					console.log(obj);
					$("#UpdateApprovalStatusRequestPanel input[name=UrlWebService]").val(obj.url);
					$("#UpdateApprovalStatusPropertiesPanel input[name=UrlWebService]").val(obj.url);
				}
			});
			$("#UpdateApprovalStatusJsonPanel").hide();
		}
		
		function UpdateApprovalStatusRequestPanel(){
			$("#UpdateApprovalStatusRequestPanel").show();
			$("#UpdateApprovalStatusPropertiesPanel").hide();
		}
		
		function UpdateApprovalStatusProptiesPanel(){
			$("#UpdateApprovalStatusRequestPanel").hide();
			$("#UpdateApprovalStatusPropertiesPanel").show();
		}
		
		function UpdateApprovalStatus(){
			$.ajax({
				url : "/ServiceCenterWeb/UpdateApprovalStatusServlet?process=request",
				type : "get",
				data : $("#UpdateApprovalStatusRequestPanel input").serialize(),
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
				url : "/ServiceCenterWeb/UpdateApprovalStatusServlet?process=save",
				type : "get",
				data : $("#UpdateApprovalStatusPropertiesPanel input").serialize(),
				success : function(data){
					console.log("save properties success");
				}
			});
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
	<body>
	  	<ul class="nav nav-tabs">
	  		<li class="active"><a data-toggle="tab" onclick="UpdateApprovalStatusRequestPanel();">Send Update Approval Status</a></li>
			<li><a data-toggle="tab" onclick="UpdateApprovalStatusProptiesPanel();">Edit Props.</a></li>
	  	</ul>
	  	<br>
		<div id="UpdateApprovalStatusRequestPanel">
			<Strong>Update Approval Status (PEGA)</Strong>
			<div class="well">
				<table>
					<tr>
						<td>URL Web Service : </td>
						<td><input type="text" class="form-control input-sm" size="100" name="UrlWebService"></td>
					</tr>
					<tr>
						<td>Application Group Id : </td>
						<td><input type="text" class="form-control input-sm" size="75" name="ApplicationGroupId"></td>
					</tr>
				</table>
			</div>
			<button type="button" class="btn btn-info" onclick="UpdateApprovalStatus()">Request</button>
			<button type="button" class="btn btn-info btn-md" onclick="jsonFormat()">Service Data</button>
		</div>
		<div id="UpdateApprovalStatusPropertiesPanel">
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
		
		<div class="well" id="UpdateApprovalStatusJsonPanel">
	  		<Strong>Request/Response JSON Format</Strong><br>
	  		Request :
			<textarea class="form-control" name="requestJson" rows="5" readonly></textarea>
			Response :
			<textarea class="form-control" name="responseJson" rows="5" readonly></textarea>
  		</div>
	</body>
</html>