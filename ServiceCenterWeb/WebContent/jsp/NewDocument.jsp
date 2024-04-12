<!DOCTYPE html>
<%@page contentType="text/html; charset=UTF-8"%>
<html>
	<script>
		$(document).ready(function(){
			console.clear();
			init();
			NewDocumentRequestPanel();
		});
		
		function init(){
			$.ajax({
				url : "/ServiceCenterWeb/NewDocumentServlet?process=default",
				type : "get",
				success : function(data){
					var obj = $.parseJSON(data);
					console.log(obj);
					$("#NewDocumentRequestPanel input[name=UrlWebService]").val(obj.url);
					$("#NewDocumentPropertiesPanel input[name=UrlWebService]").val(obj.url);
				}
			});
			$("#NewDocumentJsonPanel").hide();
		}
		
		function NewDocumentRequestPanel(){
			$("#NewDocumentRequestPanel").show();
			$("#NewDocumentPropertiesPanel").hide();
		}
		
		function NewDocumentProptiesPanel(){
			$("#NewDocumentRequestPanel").hide();
			$("#NewDocumentPropertiesPanel").show();
		}
		
		function NewDocument(){
			$.ajax({
				url : "/ServiceCenterWeb/NewDocumentServlet?process=request",
				type : "get",
				data : $("#NewDocumentRequestPanel input").serialize(),
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
				url : "/ServiceCenterWeb/NewDocumentServlet?process=save",
				type : "get",
				data : $("#NewDocumentPropertiesPanel input").serialize(),
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
	  		<button type="button" class="btn btn-info btn-md" onclick="NewDocumentRequestPanel();">Send New Document</button>
	  		<button type="button" class="btn btn-info btn-md" onclick="NewDocumentProptiesPanel();">Edit Props.</button>
	  	</div>
	  	<br><br>
		<div id="NewDocumentRequestPanel">
			<Strong>New Document</Strong>
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
						<td>Object Metadata : </td>
						<td><input type="text" class="form-control input-sm" size="75" name="ObjectMetadata"></td>
					</tr>
					<tr>
						<td>Object Type Name : </td>
						<td><input type="text" class="form-control input-sm" size="75" name="ObjectTypeName"></td>
					</tr>
					<tr>
						<td>Content File : </td>
						<td><input type="text" class="form-control input-sm" size="75" name="ContentFile"></td>
					</tr>
					<tr>
						<td>Docbase : </td>
						<td><input type="text" class="form-control input-sm" size="75" name="Docbase"></td>
					</tr>
				</table>
			</div>
			<button type="button" class="btn btn-info" onclick="NewDocument()">Request</button>
			<button type="button" class="btn btn-info btn-md" onclick="jsonFormat()">Json Format</button>
		</div>
		<div id="NewDocumentPropertiesPanel">
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
		
		<div class="well" id="NewDocumentJsonPanel">
	  		<Strong>Request/Response JSON Format</Strong><br>
	  		Request :
			<textarea class="form-control" name="requestJson" rows="5" readonly></textarea>
			Response :
			<textarea class="form-control" name="responseJson" rows="5" readonly></textarea>
  		</div>
	</body>
</html>