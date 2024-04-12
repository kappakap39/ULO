<!DOCTYPE HTML>
<%@page contentType="text/html; chatset=UTF-8" %>
<html lang=en>
<head>
	<script>
		$(document).ready(function(){
			console.clear();
			$("#FindManager_edit").hide();
			$("#FindManager_add").hide();
			$("#button_edit").hide();
			
	/* 		$.ajax({
				url : "/ServiceCenterWeb/FindManager?process=defualt",
				type : "get",
				success : function(data){
					var obj = $.parseJSON(data);
					console.clear();
					console.log(obj);
					$("#urlWebService_input").val(obj.urlWebService);
					$("#urlWebService_edit").val(obj.urlWebService);
					$("#customerId_edit").val(obj.customerId);
					for(var i=0;i<obj.contactId.length;i++){
						var htmlBuild = "<tr>";
						htmlBuild += "<td>"+obj.contactId[i]+"</td>";
						htmlBuild += "<td>"+obj.contactTypeCode[i]+"</td>";
						htmlBuild += "<td>"+obj.contactLocation[i]+"</td>";
						htmlBuild += "<td>"+obj.contactNo[i]+"</td>";
						htmlBuild += "<td>"+obj.contactExtension[i]+"</td>";
						htmlBuild += "<td>"+obj.contactSequence[i]+"</td>";
						htmlBuild += "<td>"+obj.successFlag[i]+"</td>";
						htmlBuild += "<td>"+obj.errorDescription[i]+"</td>";
						htmlBuild += "<td><a onclick=edit('"+(i+1)+"')>Edit</a></td>";
						htmlBuild += "<td><a onclick=deleteFindManager('"+(i+1)+"')>Delete</a></td>";
						htmlBuild += "</tr>";
						$("#resultList_edit").append(htmlBuild);
					}
				}
			}); */
		});
		
		function FindManagerShow_input(){
			$("#FindManager_input").show();
			$("#FindManager_output").show();
			$("#FindManager_edit").hide();
			$("#FindManager_add").hide();
		}

		function addContact(){
			var htmlBuild = "<tr>";
			htmlBuild += "<td><input type='text' class='form-control input-sm' name='JobCode' style='width: 75% !important;'></td>";
			htmlBuild += "<td><input type='text' class='form-control input-sm' name='OPTIONAL1' style='width: 75% !important;'></td>";
			htmlBuild += "<td><input type='text' class='form-control input-sm' name='OPTIONAL2' style='width: 75% !important;'></td>";
			htmlBuild += "<td><input type='text' class='form-control input-sm' name='OPTIONAL3' style='width: 75% !important;'></td>";
			htmlBuild += "<td><input type='text' class='form-control input-sm' name='OPTIONAL4' style='width: 75% !important;'></td>";
			htmlBuild += "<td><input type='text' class='form-control input-sm' name='OPTIONAL5' style='width: 75% !important;'></td>";
			htmlBuild += "<td><input type='text' class='form-control input-sm' name='PATTERN' style='width: 75% !important;'></td>";
			htmlBuild += "</tr>";
			$("#CISContactList").append(htmlBuild);
		}
		
		function submitFindManager(){
			$.ajax({
				url : "/ServiceCenterWeb/FindManager?process=request",
				type : "get",
				data : $("#FindManagerForm_input").serialize(),
				beforeSend : console.log($("#FindManagerForm_input").serialize()),
				success : function(data){
					var obj = $.parseJSON(data);
					console.log(obj);
					$("#resultList").empty();
					$("textarea[name=requestJson]").val(obj.jsonRq);
  					$("textarea[name=responseJson]").val(obj.jsonRs);
				}
			});
		}
		
/* 		function jsonFormat(){
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
	  	} */
	</script>
</head>
<body>
  	 <ul class="nav nav-tabs">
  		<li class="active"><a data-toggle="tab" onclick="FindManagerShow_input();">Send FindManager</a></li>
  	</ul>
  	<br>
  	<div id="FindManager_input">
  		<form class="well form-inline" id="FindManagerForm_input">
			<Strong>Input</Strong>
  			<table>
  				<tr>
	  				<td>Sale Id</td>
      				<td>
      					<input type="text" class="form-control input-sm" id="sale_id" name="sale_id" size="75">
      				</td>
      			</tr>
      		</table>
			<Strong>Job Codes</Strong>
			<table class="table table-striped">
				<thead>
					<tr>
						<th>Job Code</th>
						<th>OPTIONAL1</th>
						<th>OPTIONAL2</th>
						<th>OPTIONAL3</th>
						<th>OPTIONAL4</th>
						<th>OPTIONAL5</th>
						<th>PATTERN</th>
					</tr>
				</thead>
				<tbody id="CISContactList">
					<tr>
						<td><input type="text" class="form-control input-sm" name="JobCode" style="width: 75% !important;"></td>
						<td><input type="text" class="form-control input-sm" name="OPTIONAL1" style="width: 75% !important;"></td>
						<td><input type="text" class="form-control input-sm" name="OPTIONAL2" style="width: 75% !important;"></td>
						<td><input type="text" class="form-control input-sm" name="OPTIONAL3" style="width: 75% !important;"></td>
						<td><input type="text" class="form-control input-sm" name="OPTIONAL4" style="width: 75% !important;"></td>
						<td><input type="text" class="form-control input-sm" name="OPTIONAL5" style="width: 75% !important;"></td>
						<td><input type="text" class="form-control input-sm" name="PATTERN" style="width: 75% !important;"></td>
					</tr>
				</tbody>
			</table>
			<button type="button" class="btn btn-info btn-sm" onclick="addContact()">Add</button>
  		</form>
  		<button type="button" class="btn btn-info btn-md" onclick="submitFindManager()">Submit</button>
  		<br><br>
	</div>
  	<div class="well" id="FindManager_RqRs">
  		<Strong>Request/Response JSON Format</Strong><br>
  		Request :
		<textarea class="form-control" name="requestJson" rows="5" readonly></textarea>
		Response :
		<textarea class="form-control" name="responseJson" rows="5" readonly></textarea>
  	</div>
</body>
</html>