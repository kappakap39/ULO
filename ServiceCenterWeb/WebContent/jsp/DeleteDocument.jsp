<!DOCTYPE html>
<%@page contentType="text/html; charset=UTF-8"%>
<html>
	<script>
		$(document).ready(function(){
			console.clear();
			init();
			DeleteDocumentRequestPanel();
		});
		
		function init(){
			$.ajax({
				url : "/ServiceCenterWeb/DeleteDocumentServlet?process=default",
				type : "get",
				success : function(data){
					var obj = $.parseJSON(data);
					console.log(obj);
					$("#DeleteDocumentRequestPanel input[name=UrlWebService]").val(obj.url);
					$("#DeleteDocumentPropertiesPanel input[name=UrlWebService]").val(obj.url);
				}
			});
			$("#DeleteDocumentJsonPanel").hide();
		}
		
		function DeleteDocumentRequestPanel(){
			$("#DeleteDocumentRequestPanel").show();
			$("#DeleteDocumentPropertiesPanel").hide();
		}
		
		function DeleteDocumentProptiesPanel(){
			$("#DeleteDocumentRequestPanel").hide();
			$("#DeleteDocumentPropertiesPanel").show();
		}
		
		function DeleteDocument(){
			$.ajax({
				url : "/ServiceCenterWeb/DeleteDocumentServlet?process=request",
				type : "get",
				data : $("#DeleteDocumentRequestPanel input").serialize(),
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
				url : "/ServiceCenterWeb/DeleteDocumentServlet?process=save",
				type : "get",
				data : $("#DeleteDocumentPropertiesPanel input").serialize(),
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
	  		<button type="button" class="btn btn-info btn-md" onclick="DeleteDocumentRequestPanel();">Send Delete Document</button>
	  		<button type="button" class="btn btn-info btn-md" onclick="DeleteDocumentProptiesPanel();">Edit Props.</button>
	  	</div>
	  	<br><br>
		<div id="DeleteDocumentRequestPanel">
			<Strong>Delete Document</Strong>
			<div class="well">
				<table>
					<tr>
						<td>URL Web Service : </td>
						<td><input type="text" class="form-control input-sm" size="75" name="UrlWebService"></td>
					</tr>
					<tr>
						<td>Authorization : </td>
						<td><input type="text" class="form-control input-sm" size="75" name="Authorization"></td>
					</tr>
					<tr>
						<td>Document Chronicle ID : </td>
						<td><input type="text" class="form-control input-sm" size="75" name="DocumentChronicleID"></td>
					</tr>
					<tr>
						<td>Document Object ID : </td>
						<td><input type="text" class="form-control input-sm" size="75" name="DocumentObjectID"></td>
					</tr>
					<tr>
						<td>Docbase : </td>
						<td><input type="text" class="form-control input-sm" size="75" name="Docbase"></td>
					</tr>
				</table>
			</div>
			<button type="button" class="btn btn-info" onclick="DeleteDocument()">Request</button>
			<button type="button" class="btn btn-info btn-md" onclick="jsonFormat()">Json Format</button>
		</div>
		<div id="DeleteDocumentPropertiesPanel">
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
		
		<div class="well" id="DeleteDocumentJsonPanel">
	  		<Strong>Request/Response JSON Format</Strong><br>
	  		Request :
			<textarea class="form-control" name="requestJson" rows="5" readonly></textarea>
			Response :
			<textarea class="form-control" name="responseJson" rows="5" readonly></textarea>
  		</div>
	</body>
</html>