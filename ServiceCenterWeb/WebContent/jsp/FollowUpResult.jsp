<!DOCTYPE html>
<%@page contentType="text/html; charset=UTF-8"%>
<html>
	<script>
		$(document).ready(function(){
			console.clear();
			init();
			FollowUpResultRequestPanel();
		});
		
		function init(){
			$.ajax({
				url : "/ServiceCenterWeb/FollowUpResultServlet?process=default",
				type : "get",
				success : function(data){
					var obj = $.parseJSON(data);
					console.log(obj);
					$("#FollowUpResultRequestPanel input[name=UrlWebService]").val(obj.url);
					$("#FollowUpResultPropertiesPanel input[name=UrlWebService]").val(obj.url);
				}
			});
			$("#FollowUpResultJsonPanel").hide();
		}
		
		function FollowUpResultRequestPanel(){
			$("#FollowUpResultRequestPanel").show();
			$("#FollowUpResultPropertiesPanel").hide();
		}
		
		function FollowUpResultProptiesPanel(){
			$("#FollowUpResultRequestPanel").hide();
			$("#FollowUpResultPropertiesPanel").show();
		}
		
		function FollowUpResult(){
			$.ajax({
				url : "/ServiceCenterWeb/FollowUpResultServlet?process=request",
				type : "get",
				data : $("#FollowUpResultRequestPanel input").serialize(),
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
				url : "/ServiceCenterWeb/FollowUpResultServlet?process=save",
				type : "get",
				data : $("#FollowUpResultPropertiesPanel input").serialize(),
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
	  		<li class="active"><a data-toggle="tab" onclick="FollowUpResultRequestPanel();">Send FollowUpResult</a></li>
			<li><a data-toggle="tab" onclick="FollowUpResultProptiesPanel();">Edit Props.</a></li>
	  	</ul>
	  	<br>
		<div id="FollowUpResultRequestPanel">
			<Strong>Follow Up Result</Strong>
			<div class="well">
				<table>
					<tr>
						<td>URL Web Service : </td>
						<td colspan="3"><input type="text" class="form-control input-sm" size="75" name="UrlWebService"></td>						
					</tr>
					<tr>
					<td>Case ID : </td>
						<td><input type="text" class="form-control input-sm" size="75" name="CaseID"></td>
					</tr>
					<tr>
						<td>Follow Up Status : </td>
						<td><input type="text" class="form-control input-sm" size="75" name="FollowUpStatus"></td>
						<td>Branch Code : </td>
						<td><input type="text" class="form-control input-sm" size="75" name="BranchCode"></td>
					</tr>
					<tr>
						<td>Recommender Emp ID : </td>
						<td><input type="text" class="form-control input-sm" size="75" name="RecommenderEmpID"></td>
						<td>Cust Available Time : </td>
						<td><input type="text" class="form-control input-sm" size="75" name="CustAvailableTime"></td>
					</tr>
					<tr>
						<td>Tel Type : </td>
						<td><input type="text" class="form-control input-sm" size="75" name="TelType"></td>
						<td>Tel No : </td>
						<td><input type="text" class="form-control input-sm" size="75" name="TelNo"></td>
					</tr>
					<tr>
						<td>Tel Ext : </td>
						<td><input type="text" class="form-control input-sm" size="75" name="TelExt"></td>
					</tr>
				</table>
			</div>
			<button type="button" class="btn btn-info" onclick="FollowUpResult()">Request</button>
			<button type="button" class="btn btn-info btn-md" onclick="jsonFormat()">Service Data</button>
		</div>
		<div id="FollowUpResultPropertiesPanel">
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
		
		<div class="well" id="FollowUpResultJsonPanel">
	  		<Strong>Request/Response JSON Format</Strong><br>
	  		Request :
			<textarea class="form-control" name="requestJson" rows="5" readonly></textarea>
			Response :
			<textarea class="form-control" name="responseJson" rows="5" readonly></textarea>
  		</div>
	</body>
</html>