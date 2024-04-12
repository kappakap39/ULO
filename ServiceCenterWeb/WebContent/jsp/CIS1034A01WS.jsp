<!DOCTYPE html>
<%@page contentType="text/html; charset=UTF-8" %>
<html lang=en>
<head>
	<script>
		$(document).ready(function(){	
			console.clear();
			$("#CIS1034A01_RqRs").hide();
			$("#CIS1034A01_setting").hide();
			$("#CIS1034A01_add").hide();
			$("button[name=button_edit]").hide();
			$.ajax({
				url : "/ServiceCenterWeb/CIS1034A01?process=defualt",
				type : "get",
				success : function(data){
					var obj = $.parseJSON(data);
					console.log(obj);
					$("input[name=url_input]").val(obj.urlWebService);
					$("input[name=url_setting]").val(obj.urlWebService);
					$("input[name=cusId_setting]").val(obj.customerId);
					
					for(var i=0;i<obj.documentNo.length;i++){
						var htmlBuild = "<tr>";
						htmlBuild += "<td>"+obj.documentType[i]+"</td>";	
						htmlBuild += "<td>"+obj.documentNo[i]+"</td>";	
						htmlBuild += "<td>"+obj.successFlag[i]+"</td>";	
						htmlBuild += "<td>"+obj.errorDescription[i]+"</td>";	
						htmlBuild += "<td><a onclick=edit('"+(i+1)+"')>Edit</a></td>";
						htmlBuild += "<td><a onclick=delete_setting('"+(i+1)+"')>Delete</a></td>";
						$("#list_edit").append(htmlBuild);
					}
				}
			});
		});
		
		function CIS1034A01_input(){
			$("#CIS1034A01_input").show();
			$("#CIS1034A01_output").show();
			$("#CIS1034A01_setting").hide();
			$("#CIS1034A01_add").hide();
		}
		
		function CIS1034A01_setting(){
			$("#CIS1034A01_input").hide();
			$("#CIS1034A01_output").hide();
			$("#CIS1034A01_setting").show();
			$("#CIS1034A01_add").show();
		}
		
		function requestService(){
			$.ajax({
				url : "/ServiceCenterWeb/CIS1034A01?process=request",
				type : "get",
				data : $(".CIS1034A01Form_input").serialize(),
				success : function(data){
					var obj = $.parseJSON(data);
					console.log(obj);
					$("input[name=cusId_output]").val(obj.customerId);
					$("#list_output").empty();
					for(var i=0;i<obj.docInfoList.length;i++){
						var htmlBuild = "<tr>";
						htmlBuild += "<td>"+obj.docInfoList[i].documentType+"</td>";
						htmlBuild += "<td>"+obj.docInfoList[i].documentNo+"</td>";
						htmlBuild += "<td>"+obj.docInfoList[i].successFlag+"</td>";
						htmlBuild += "<td>"+obj.docInfoList[i].errorDescription+"</td>";
						htmlBuild += "</tr>";
						$("#list_output").append(htmlBuild);
					}
					$("textarea[name=requestJson]").val(obj.jsonRq);
  					$("textarea[name=responseJson]").val(obj.jsonRs);
				}
			});
		}
		
		function clearInput(){
			$(".CIS1034A01Form_input").trigger("reset");
		}
		
		function addInput(){
			var htmlBuild = "<tr>";
			htmlBuild += '<td><input type="text" class="form-control input-sm" name="documentNo_input"></td>';
			htmlBuild += '<td><input type="text" class="form-control input-sm" name="documentType_input"></td>';
			htmlBuild += '<td><input type="text" class="form-control input-sm" name="placeIssue_input"></td>';
			htmlBuild += '<td><input type="text" class="form-control input-sm" name="issueDate_input"></td>';
			htmlBuild += '<td><input type="text" class="form-control input-sm" name="expiryDate_input"></td>';
			htmlBuild += "</tr>";
			$("#list_input").append(htmlBuild);
		}
		
		function saveSetting(){
			$.ajax({
				url : "/ServiceCenterWeb/CIS1034A01?process=save",
				type : "post",
				data : $("#CIS1034A01Form_setting").serialize(),
				success : function(data){
// 					alert("saving data");
				}
			});
		}
		
		function clearSetting(){
			$("#CIS1034A01Form_setting").trigger("reset");
		}
		
		function addSetting(){
			$.ajax({
				url : "/ServiceCenterWeb/CIS1034A01?process=add",
				type : "post",
				data : $("#CIS1034A01Form_add").serialize(),
				beforeSend : console.log($("#CIS1034A01Form_add").serialize()),
				success : function(data){
// 					alert("add data success");
					var row = $('#list_edit > tr').length+1;
					var htmlBuild = "<tr>";
					htmlBuild += "<td>"+$("input[name=docType_add]").val()+"</td>";
					htmlBuild += "<td>"+$("input[name=docNo_add]").val()+"</td>";
					htmlBuild += "<td>"+$("input[name=succFlag_add]").val()+"</td>";
					htmlBuild += "<td>"+$("input[name=errDesc_add]").val()+"</td>";
					htmlBuild += "<td><a onclick=edit('"+row+"')></a></td>";
					htmlBuild += "<td><a onclick=delete_setting('"+row+"')></td>";
					htmlBuild += "</tr>";
					$("#list_edit").append(htmlBuild);
				}
			});
		}
		
		function edit(row){
			$("button[name=button_add]").hide();
			$("button[name=button_edit]").show();
			$("input[name=docType_add]").val($("#list_edit > tr:nth-child("+row+") > td:nth-child(1)").text());
			$("input[name=docNo_add]").val($("#list_edit > tr:nth-child("+row+") > td:nth-child(2)").text());
			$("input[name=succFlag_add]").val($("#list_edit > tr:nth-child("+row+") > td:nth-child(3)").text());
			$("input[name=errDesc_add]").val($("#list_edit > tr:nth-child("+row+") > td:nth-child(4)").text());
			$("input[name=row_hidden]").val(row);
		}
		
		function editSetting(){	
			var row = $("input[name=row_hidden]").val();
			$.ajax({
				url : "/ServiceCenterWeb/CIS1034A01?process=edit",
				type : "post",
				data : $("#CIS1034A01Form_add").serialize(),
				beforeSend : console.log($("#CIS1034A01Form_add").serialize()),
				success : function(data){	
// 					alert("Edit data success");
					$("#list_edit > tr:nth-child("+row+") > td:nth-child(1)").text($("input[name=docType_add]").val());
					$("#list_edit > tr:nth-child("+row+") > td:nth-child(2)").text($("input[name=docNo_add]").val());
					$("#list_edit > tr:nth-child("+row+") > td:nth-child(3)").text($("input[name=succFlag_add]").val());
					$("#list_edit > tr:nth-child("+row+") > td:nth-child(4)").text($("input[name=errDesc_add]").val());
					
					$("input[name=docType_add]").val("");
					$("input[name=docNo_add]").val("");
					$("input[name=succFlag_add]").val("");
					$("input[name=errDesc_add]").val("");
					$("input[name=row_hidden]").val("");
					
					$("button[name=button_add]").show();
					$("button[name=button_edit]").hide();
				}
			});
		}
		
		function delete_setting(row){
			$.ajax({
				url : "/ServiceCenterWeb/CIS1034A01?process=delete&row="+row,
				type : "get",
				success : function(data){
// 					alert("delete success");
					$("#list_edit > tr:nth-child("+row+")").remove();
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
</head>
<body>
	<ul class="nav nav-tabs">
  		<li class="active"><a data-toggle="tab" onclick="CIS1034A01_input();">Send CIS1034A01</a></li>
		<li><a data-toggle="tab" onclick="CIS1034A01_setting();">Edit Props.</a></li>
  	</ul>
	<br>
	<div id="CIS1034A01_input">
		<form class="form-inline well CIS1034A01Form_input"><strong>Input</strong>
			<table>
				<tr>
					<td>URL Web Service</td>
					<td><input type="text" class="form-control input-sm" name="url_input" size="75"></td>
				</tr>
				<tr>
					<td>User Id</td>
					<td><input type="text" class="form-control input-sm" name="userId_input" size="60"></td>
				</tr>
				<tr>
					<td>Hub No</td>
					<td><input type="text" class="form-control input-sm" name="hubNo_input" size="60"></td>
				</tr>
				<tr>
					<td>Branch No</td>
					<td><input type="text" class="form-control input-sm" name="branchNo_input" size="60"></td>
				</tr>
				<tr>
					<td>Confirm Flag</td>
					<td><input type="text" class="form-control input-sm" name="confirmFlag_input" size="60"></td>
				</tr>
				<tr>
					<td>Validate Flag</td>
					<td><input type="text" class="form-control input-sm" name="validateFlag_input" size="60"></td>
				</tr>
				<tr>
					<td>Total Record</td>
					<td><input type="text" class="form-control input-sm" name="totalRecord_input" size="60"></td>
				</tr>
				<tr>
					<td>Customer Type</td>
					<td><input type="text" class="form-control input-sm" name="cusType_input" size="60"></td>
				</tr>
				<tr>
					<td>Customer Id</td>
					<td><input type="text" class="form-control input-sm" name="cusId_input" size="60"></td>
				</tr>
			</table>
		</form>
		<form class="form-inline well CIS1034A01Form_input"><strong>Input Document Info.</strong>
			<table class="table table-striped">
				<thead>
					<tr>
						<th>Document No</th>
						<th>Document Type</th>
						<th>Place Issue</th>
						<th>Issue Date</th>
						<th>Expiry Date</th>
					</tr>
				</thead>
				<tbody id="list_input">
					<tr>
						<td><input type="text" class="form-control input-sm" name="documentNo_input"></td>
						<td><input type="text" class="form-control input-sm" name="documentType_input"></td>
						<td><input type="text" class="form-control input-sm" name="placeIssue_input"></td>
						<td><input type="text" class="form-control input-sm" name="issueDate_input"></td>
						<td><input type="text" class="form-control input-sm" name="expiryDate_input"></td>
					</tr>
				</tbody>
			</table>
			<button type="button" class="btn btn-info" onclick="addInput()">Add Doc.</button>
		</form>
		<button type="button" class="btn btn-info" onclick="requestService();">Request Service</button>
		<button type="button" class="btn btn-info" onclick="clearInput();">Clear</button>
		<button type="button" class="btn btn-info btn-md" onclick="jsonFormat()">Service Data</button>
		<br><br>
	</div>
	<div class="well" id="CIS1034A01_output"><strong>Output</strong>
		<table>
			<tr>
				<td>Customer Id</td>
				<td><input type="text" class="form-control input-sm" name="cusId_output" size="60" readonly></td>
			</tr>
		</table>
		<table class="table table-striped">
			<thead>
				<tr>
					<th>Document Type</th>
					<th>Document No</th>
					<th>Success Flag</th>
					<th>Error Description</th>
				</tr>
			</thead>
			<tbody id="list_output">
			</tbody>
		</table>
	</div>
	<div class="well" id="CIS1034A01_RqRs">
  		<Strong>Request/Response JSON Format</Strong><br>
  		Request :
		<textarea class="form-control" name="requestJson" rows="5" readonly></textarea>
		Response :
		<textarea class="form-control" name="responseJson" rows="5" readonly></textarea>
  	</div>
	<div id="CIS1034A01_setting" >
		<form class="form-inline well" id="CIS1034A01Form_setting"><Strong>Setting</Strong>
			<table>
				<tr>
					<td>URL Webservice</td>
					<td><input type="text" class="form-control input-sm" name="url_setting" size="75"></td>
				</tr>
				<tr>
					<td>Customer Id</td>
					<td><input type="text" class="form-control input-sm" name="cusId_setting" size="60"></td>
				</tr>
			</table>
		</form>
		<button type="button" class="btn btn-info" onclick="saveSetting();">Save</button>
		<button type="button" class="btn btn-info" onclick="clearSetting();">Clear</button>
		<br><br>
	</div>
	<div id="CIS1034A01_add" class="well"><strong>Add Document Info.</strong>
		<form class="form-inline" id="CIS1034A01Form_add">
			<table>
				<tr>
					<td>Document Type</td>
					<td><input type="text" class="form-control input-sm" name="docType_add" size="60"></td>
				</tr>
				<tr>
					<td>Document No</td>
					<td><input type="text" class="form-control input-sm" name="docNo_add" size="60"></td>
				</tr>
				<tr>
					<td>Success Flag</td>
					<td><input type="text" class="form-control input-sm" name="succFlag_add" size="60"></td>
				</tr>
				<tr>
					<td>Error Description</td>
					<td><input type="text" class="form-control input-sm" name="errDesc_add" size="60"></td>
				</tr>
			</table>
			<input type="hidden" name="row_hidden">
			<button type="button" class="btn btn-info" name="button_add" onclick="addSetting();">Add</button>
			<button type="button" class="btn btn-info" name="button_edit" onclick="editSetting();">Edit</button>
		</form>
		<table class="table table-striped">
			<thead>
				<tr>
					<th>Document Type</th>
					<th>Document No</th>
					<th>Success Flag</th>
					<th>Error Description</th>
					<th>Edit</th>
					<th>Delete</th>
				</tr>
			</thead>
			<tbody id="list_edit">
			</tbody>
		</table>
	</div>		
</body>
</html>
