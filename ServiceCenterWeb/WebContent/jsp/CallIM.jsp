<!DOCTYPE html>
<%@page contentType="text/html; charset=UTF-8"%>
<html>
	<script>
		$(document).ready(function(){
			console.clear();
			init();
			CallIMRequestPanel();
		});
		
		function init(){
			$.ajax({
				url : "/ServiceCenterWeb/CallIMServlet?process=default",
				type : "get",
				success : function(data){
					var obj = $.parseJSON(data);
					console.log(obj);
					$("#CallIMRequestPanel input[name=UrlWebService]").val(obj.url);
					$("#CallIMPropertiesPanel input[name=UrlWebService]").val(obj.url);
				}
			});
			$("#CallIMJsonPanel").hide();
		}
		
		function CallIMRequestPanel(){
			$("#CallIMRequestPanel").show();
			$("#CallIMPropertiesPanel").hide();
		}
		
		function CallIMProptiesPanel(){
			$("#CallIMRequestPanel").hide();
			$("#CallIMPropertiesPanel").show();
		}
		
		function CallIM(){
			$.ajax({
				url : "/ServiceCenterWeb/CallIMServlet?process=request",
				type : "get",
				data : $("#CallIMRequestPanel input").serialize(),
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
				url : "/ServiceCenterWeb/CallIMServlet?process=save",
				type : "get",
				data : $("#CallIMPropertiesPanel input").serialize(),
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
	  		<li class="active"><a data-toggle="tab" onclick="CallIMRequestPanel();">Send CallIM</a></li>
			<li><a data-toggle="tab" onclick="CallIMProptiesPanel();">Edit Props.</a></li>
	  	</ul>
	  	<br>
		<div id="CallIMRequestPanel">
			<Strong>Follow Up Result</Strong>
			<div class="well">
				<table>
					<tr>
						<td>URL Web Service : </td>
						<td colspan="3"><input type="text" class="form-control input-sm" size="75" name="UrlWebService"></td>						
					</tr>
					<tr>
					<td>Set ID : </td>
						<td><input type="text" class="form-control input-sm" size="75" name="SetID"></td>
					</tr>
					<tr>
						<td>Rc Code : </td>
						<td><input type="text" class="form-control input-sm" size="75" name="RcCode"></td>
						<td>Branch Code : </td>
						<td><input type="text" class="form-control input-sm" size="75" name="BranchCode"></td>
					</tr>
					<tr>
						<td>ScanChannel : </td>
						<td><input type="text" class="form-control input-sm" size="75" name="ScanChannel"></td>
						<td>RequestTokenOnly : </td>
						<td><input type="text" class="form-control input-sm" size="75" name="RequestTokenOnly"></td>
					</tr>
				</table>
			</div>
			<button type="button" class="btn btn-info" onclick="CallIM()">Request</button>
			<button type="button" class="btn btn-info btn-md" onclick="jsonFormat()">Service Data</button>
		</div>
		<div id="CallIMPropertiesPanel">
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
		
		<div class="well" id="CallIMJsonPanel">
	  		<Strong>Request/Response JSON Format</Strong><br>
	  		Request :
			<textarea class="form-control" name="requestJson" rows="5" readonly></textarea>
			Response :
			<textarea class="form-control" name="responseJson" rows="5" readonly></textarea>
  		</div>
	</body>
</html>