<!DOCTYPE html>
<%@page contentType="text/html; charset=UTF-8"%>
<html>
	<script>
		$(document).ready(function(){
			console.clear();
			init();
			DownloadContentRequestPanel();
		});
		
		function init(){
			$.ajax({
				url : "/ServiceCenterWeb/DownloadContentServlet?process=default",
				type : "get",
				success : function(data){
					var obj = $.parseJSON(data);
					console.log(obj);
					$("#DownloadContentRequestPanel input[name=UrlWebService]").val(obj.url);
					$("#DownloadContentPropertiesPanel input[name=UrlWebService]").val(obj.url);
				}
			});
			$("#DownloadContentJsonPanel").hide();
		}
		
		function DownloadContentRequestPanel(){
			$("#DownloadContentRequestPanel").show();
			$("#DownloadContentPropertiesPanel").hide();
		}
		
		function DownloadContentProptiesPanel(){
			$("#DownloadContentRequestPanel").hide();
			$("#DownloadContentPropertiesPanel").show();
		}
		
		function DownloadContent(){
			$.ajax({
				url : "/ServiceCenterWeb/DownloadContentServlet?process=request",
				type : "get",
				data : $("#DownloadContentRequestPanel input").serialize(),
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
				url : "/ServiceCenterWeb/DownloadContentServlet?process=save",
				type : "get",
				data : $("#DownloadContentPropertiesPanel input").serialize(),
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
	  		<button type="button" class="btn btn-info btn-md" onclick="DownloadContentRequestPanel();">Send Download Content</button>
	  		<button type="button" class="btn btn-info btn-md" onclick="DownloadContentProptiesPanel();">Edit Props.</button>
	  	</div>
	  	<br><br>
		<div id="DownloadContentRequestPanel">
			<Strong>Download Content</Strong>
			<div class="well">
				<table>
					<tr>
						<td>URL Web Service : </td>
						<td><input type="text" class="form-control input-sm" size="75" name="UrlWebService"></td>
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
						<td>CVS Content : </td>
						<td><input type="text" class="form-control input-sm" size="75" name="CVSContent"></td>
					</tr>
				</table>
			</div>
			<button type="button" class="btn btn-info" onclick="DownloadContent()">Request</button>
			<button type="button" class="btn btn-info btn-md" onclick="jsonFormat()">Json Format</button>
		</div>
		<div id="DownloadContentPropertiesPanel">
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
		
		<div class="well" id="DownloadContentJsonPanel">
	  		<Strong>Request/Response JSON Format</Strong><br>
	  		Request :
			<textarea class="form-control" name="requestJson" rows="5" readonly></textarea>
			Response :
			<textarea class="form-control" name="responseJson" rows="5" readonly></textarea>
  		</div>
	</body>
</html>