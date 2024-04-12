<!DOCTYPE html>
<%@page contentType="text/html; charset=UTF-8"%>
<html>
	<script>
		$(document).ready(function(){
			console.clear();
			init();
			UpdateCallOperatorRequestPanel();
		});
		
		function init(){
			$.ajax({
				url : "/ServiceCenterWeb/UpdateCallOperatorServlet?process=default",
				type : "get",
				success : function(data){
					var obj = $.parseJSON(data);
					console.log(obj);
					$("#UpdateCallOperatorRequestPanel input[name=UrlWebService]").val(obj.url);
					$("#UpdateCallOperatorPropertiesPanel input[name=UrlWebService]").val(obj.url);
				}
			});
			$("#UpdateCallOperatorJsonPanel").hide();
		}
		
		function UpdateCallOperatorRequestPanel(){
			$("#UpdateCallOperatorRequestPanel").show();
			$("#UpdateCallOperatorPropertiesPanel").hide();
		}
		
		function UpdateCallOperatorProptiesPanel(){
			$("#UpdateCallOperatorRequestPanel").hide();
			$("#UpdateCallOperatorPropertiesPanel").show();
		}
		
		function UpdateCallOperator(){
			$.ajax({
				url : "/ServiceCenterWeb/UpdateCallOperatorServlet?process=request",
				type : "get",
				data : $("#UpdateCallOperatorRequestPanel input").serialize(),
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
				url : "/ServiceCenterWeb/UpdateCallOperatorServlet?process=save",
				type : "get",
				data : $("#UpdateCallOperatorPropertiesPanel input").serialize(),
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
	  		<li class="active"><a data-toggle="tab" onclick="UpdateCallOperatorRequestPanel();">Send Update Call Operator</a></li>
			<li><a data-toggle="tab" onclick="UpdateCallOperatorProptiesPanel();">Edit Props.</a></li>
	  	</ul>
	  	<br>
		<div id="UpdateCallOperatorRequestPanel">
			<Strong>Update Call Operator</Strong>
			<div class="well">
				<table>
					<tr>
						<td>URL Web Service : </td>
						<td><input type="text" class="form-control input-sm" size="100" name="UrlWebService"></td>
					</tr>
					<tr>
						<td>Case ID : </td>
						<td><input type="text" class="form-control input-sm" size="75" name="CaseID"></td>
					</tr>
					<tr>
						<td>Call Operator : </td>
						<td><input type="text" class="form-control input-sm" size="75" name="CallOperator"></td>
					</tr>
				</table>
			</div>
			<button type="button" class="btn btn-info" onclick="UpdateCallOperator()">Request</button>
			<button type="button" class="btn btn-info btn-md" onclick="jsonFormat()">Service Data</button>
		</div>
		<div id="UpdateCallOperatorPropertiesPanel">
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
		
		<div class="well" id="UpdateCallOperatorJsonPanel">
	  		<Strong>Request/Response JSON Format</Strong><br>
	  		Request :
			<textarea class="form-control" name="requestJson" rows="5" readonly></textarea>
			Response :
			<textarea class="form-control" name="responseJson" rows="5" readonly></textarea>
  		</div>
	</body>
</html>