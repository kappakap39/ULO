<!DOCTYPE html>
<%@page contentType="text/html; charset=UTF-8"%>
<html>
	<script>
		$(document).ready(function(){
			console.clear();
			init();
			FollowUpRequestPanel();
		});
		
		function init(){
			$.ajax({
				url : "/ServiceCenterWeb/FollowUpServlet?process=default",
				type : "get",
				success : function(data){
					var obj = $.parseJSON(data);
					console.log(obj);
					$("#FollowUpRequestPanel input[name=UrlWebService]").val(obj.url);
					$("#FollowUpPropertiesPanel input[name=UrlWebService]").val(obj.url);
				}
			});
			$("#FollowUpJsonPanel").hide();
		}
		
		function FollowUpRequestPanel(){
			$("#FollowUpRequestPanel").show();
			$("#FollowUpPropertiesPanel").hide();
		}
		
		function FollowUpProptiesPanel(){
			$("#FollowUpRequestPanel").hide();
			$("#FollowUpPropertiesPanel").show();
		}
		
		function FollowUp(){
			$.ajax({
				url : "/ServiceCenterWeb/FollowUpServlet?process=request",
				type : "get",
				data : $("#FollowUpRequestPanel input").serialize(),
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
				url : "/ServiceCenterWeb/FollowUpServlet?process=save",
				type : "get",
				data : $("#FollowUpPropertiesPanel input").serialize(),
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
	  		<li class="active"><a data-toggle="tab" onclick="FollowUpRequestPanel();">Send FollowUp</a></li>
			<li><a data-toggle="tab" onclick="FollowUpProptiesPanel();">Edit Props.</a></li>
	  	</ul>
	  	
	  	<br>
		<div id="FollowUpRequestPanel">
			<Strong>Follow Up</Strong>
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
						<td>PEGA Operator ID : </td>
						<td><input type="text" class="form-control input-sm" size="75" name="PegaOperatorID"></td>
					</tr>
					<tr>
						<td>CSV Content : </td>
						<td><input type="text" class="form-control input-sm" size="75" name="CSVContent"></td>
					</tr>
				</table>
			</div>
			<button type="button" class="btn btn-info" onclick="FollowUp()">Request</button>
			<button type="button" class="btn btn-info btn-md" onclick="jsonFormat()">Service Data</button>
		</div>
		<div id="FollowUpPropertiesPanel">
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
		
		<div class="well" id="FollowUpJsonPanel">
	  		<Strong>Request/Response JSON Format</Strong><br>
	  		Request :
			<textarea class="form-control" name="requestJson" rows="5" readonly></textarea>
			Response :
			<textarea class="form-control" name="responseJson" rows="5" readonly></textarea>
  		</div>
	</body>
</html>