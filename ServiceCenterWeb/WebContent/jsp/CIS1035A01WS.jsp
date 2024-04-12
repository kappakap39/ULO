<!DOCTYPE HTML>
<%@page contentType="text/html; chatset=UTF-8" %>
<html lang=en>
<head>
	<script>
		$(document).ready(function(){
			console.clear();
			$("#CIS1035A01_RqRs").hide();
			$("#CIS1035A01_edit").hide();
			$("#CIS1035A01_add").hide();
			$("#button_edit").hide();
			
			$.ajax({
				url : "/ServiceCenterWeb/CIS1035A01?process=defualt",
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
						htmlBuild += "<td><a onclick=deleteCIS1035A01('"+(i+1)+"')>Delete</a></td>";
						htmlBuild += "</tr>";
						$("#resultList_edit").append(htmlBuild);
					}
				}
			});
		});
		
		function CIS1035A01Show_input(){
			$("#CIS1035A01_input").show();
			$("#CIS1035A01_output").show();
			$("#CIS1035A01_edit").hide();
			$("#CIS1035A01_add").hide();
		}
		
		function CIS1035A01Show_edit(){
			$("#CIS1035A01_input").hide();
			$("#CIS1035A01_output").hide();
			$("#CIS1035A01_edit").show();
			$("#CIS1035A01_add").show();
		}
		
		function clearInput(){
			$("#urlWebService_input").val("");
			$("#userId_input").val("");
			$("#hubNo_input").val("");
			$("#branchNo_input").val("");
			$("#confirmFlag_input").val("");
			$("#validateFlag_input").val("");
			$("#totalRecord_input").val("");
			$("#customerType_input").val("");
			$("#customerId_input").val("");
			$("input[name=contact_typeCode]").val("");
			$("input[name=contact_locationCode]").val("");
			$("input[name=contact_contactNo]").val("");
			$("input[name=contact_contactExtNo]").val("");
			$("input[name=contact_avaTime]").val("");
			$("input[name=contact_name]").val("");
		}
		
		function clearSetting(){
			$("#urlWebService_edit").val("");
			$("#customerId_edit").val("");
		}
		
		function saveCIS1035A01(){
			$.ajax({
				url : "/ServiceCenterWeb/CIS1035A01?process=save",
				type : "post",
				data : $("#CIS1035A01Form_edit").serialize(),
				success : function(data){
// 					alert("save data");
				}
			});
		}
		
		function addCIS1035A01(){
			$.ajax({
				url : "/ServiceCenterWeb/CIS1035A01?process=add",
				type : "post",
				data : $("#CIS1035A01Form_add").serialize(),
				success : function(data){
// 					alert("add data success");
					var htmlBuild = "<tr>";
					var row = $("#resultList_edit > tr").length+1;
					htmlBuild += "<td>"+$("#contactId_add").val()+"</td>";
					htmlBuild += "<td>"+$("#contactTp_add").val()+"</td>";
					htmlBuild += "<td>"+$("#contactLocation_add").val()+"</td>";
					htmlBuild += "<td>"+$("#contactNumber_add").val()+"</td>";
					htmlBuild += "<td>"+$("#contactExtension_add").val()+"</td>";
					htmlBuild += "<td>"+$("#contactSequence_add").val()+"</td>";
					htmlBuild += "<td>"+$("#successFlag_add").val()+"</td>";
					htmlBuild += "<td>"+$("#errorDescription_add").val()+"</td>";
					htmlBuild += "<td><a onclick=edit('"+row+"')>Edit</a></td>";
					htmlBuild += "<td><a onclick=deleteCIS1035A01('"+row+"')>Delete</a></td>";
					htmlBuild += "</tr>";
					$("#resultList_edit").append(htmlBuild);
				} 
			});
		}
		
		function edit(row){
			$("#button_edit").show();
			$("#button_add").hide();
			$("#contactId_add").val($("#resultList_edit > tr:nth-child("+row+") > td:nth-child(1)").text());
			$("#contactTp_add").val($("#resultList_edit > tr:nth-child("+row+") > td:nth-child(2)").text());
			$("#contactLocation_add").val($("#resultList_edit > tr:nth-child("+row+") > td:nth-child(3)").text());
			$("#contactNumber_add").val($("#resultList_edit > tr:nth-child("+row+") > td:nth-child(4)").text());
			$("#contactExtension_add").val($("#resultList_edit > tr:nth-child("+row+") > td:nth-child(5)").text());
			$("#contactSequence_add").val($("#resultList_edit > tr:nth-child("+row+") > td:nth-child(6)").text());
			$("#successFlag_add").val($("#resultList_edit > tr:nth-child("+row+") > td:nth-child(7)").text());
			$("#errorDescription_add").val($("#resultList_edit > tr:nth-child("+row+") > td:nth-child(8)").text());
			$("#row_hidden").val(row);
		}
		
		function editCIS1035A01(){
			$.ajax({
				url : "/ServiceCenterWeb/CIS1035A01?process=edit&row="+$("#row_hidden").val(),
				type : "post",
				data : $("#CIS1035A01Form_add").serialize(),
				success : function(data){
// 					alert("edit data success");
					var row = $("#row_hidden").val();
					$("#button_add").show();
					$("#button_edit").hide();
					$("#resultList_edit > tr:nth-child("+row+") > td:nth-child(1)").text($("#contactId_add").val());
					$("#resultList_edit > tr:nth-child("+row+") > td:nth-child(2)").text($("#contactTp_add").val());
					$("#resultList_edit > tr:nth-child("+row+") > td:nth-child(3)").text($("#contactLocation_add").val());
					$("#resultList_edit > tr:nth-child("+row+") > td:nth-child(4)").text($("#contactNumber_add").val());
					$("#resultList_edit > tr:nth-child("+row+") > td:nth-child(5)").text($("#contactExtension_add").val());
					$("#resultList_edit > tr:nth-child("+row+") > td:nth-child(6)").text($("#contactSequence_add").val());
					$("#resultList_edit > tr:nth-child("+row+") > td:nth-child(7)").text($("#successFlag_add").val());
					$("#resultList_edit > tr:nth-child("+row+") > td:nth-child(8)").text($("#errorDescription_add").val());
					
					$("#contactId_add").val("");
					$("#contactTp_add").val("");
					$("#contactLocation_add").val("");
					$("#contactNumber_add").val("");
					$("#contactExtension_add").val("");
					$("#contactSequence_add").val("");
					$("#successFlag_add").val("");
					$("#errorDescription_add").val("");
					$("#row_hidden").val("");
				}  
			});
		}
		
		function deleteCIS1035A01(row){
			$.ajax({
				url : "/ServiceCenterWeb/CIS1035A01?process=delete&row="+row,
				type : "post",
				success : function(data){	
// 					alert("delete data success");
					$("#resultList_edit > tr:nth-child("+row+")").empty();
				}
			});
		}
		
		function addContact(){
			var htmlBuild = "<tr>";
			htmlBuild += "<td><input type='text' class='form-control input-sm' name='contact_typeCode'></td>";
			htmlBuild += "<td><input type='text' class='form-control input-sm' name='contact_locationCode'></td>";
			htmlBuild += "<td><input type='text' class='form-control input-sm' name='contact_contactNo'></td>";
			htmlBuild += "<td><input type='text' class='form-control input-sm' name='contact_contactExtNo'></td>";
			htmlBuild += "<td><input type='text' class='form-control input-sm' name='contact_avaTime'></td>";
			htmlBuild += "<td><input type='text' class='form-control input-sm' name='contact_name'></td>";
			htmlBuild += "</tr>";
			$("#CISContactList").append(htmlBuild);
		}
		
		function submitCIS1035A01(){
			$.ajax({
				url : "/ServiceCenterWeb/CIS1035A01?process=request",
				type : "get",
				data : $("#CIS1035A01Form_input").serialize(),
				beforeSend : console.log($("#CIS1035A01Form_input").serialize()),
				success : function(data){
					var obj = $.parseJSON(data);
					console.log(obj);
					$("#resultList").empty();
					$("#customerId_output").val(obj.customerId);
					for(var i=0;i<obj.CISContactList.length;i++){
						var htmlBuild = "<tr>";
						htmlBuild += "<td>"+obj.CISContactList[i].contactId+"</td>";
						htmlBuild += "<td>"+obj.CISContactList[i].contactTypeCode+"</td>";
						htmlBuild += "<td>"+obj.CISContactList[i].contactLocation+"</td>";
						htmlBuild += "<td>"+obj.CISContactList[i].contactNo+"</td>";
						htmlBuild += "<td>"+obj.CISContactList[i].contactExtNo+"</td>";
						htmlBuild += "<td>"+obj.CISContactList[i].contactSequence+"</td>";
						htmlBuild += "<td>"+obj.CISContactList[i].successFlag+"</td>";
						htmlBuild += "<td>"+obj.CISContactList[i].errorDescription+"</td>";
						htmlBuild += "</tr>";
						$("#resultList").append(htmlBuild);
					}
					$("textarea[name=requestJson]").val(obj.jsonRq);
  					$("textarea[name=responseJson]").val(obj.jsonRs);
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
  		<li class="active"><a data-toggle="tab" onclick="CIS1035A01Show_input();">Send CIS1035A01</a></li>
		<li><a data-toggle="tab" onclick="CIS1035A01Show_edit();">Edit Props.</a></li>
  	</ul>
  	<br>
  	<div id="CIS1035A01_input">
  		<form class="well form-inline" id="CIS1035A01Form_input">
			<Strong>Input</Strong>
  			<table>
  				<tr>
	  				<td>URL Web Service</td>
      				<td>
      					<input type="text" class="form-control input-sm" id="urlWebService_input" name="urlWebService_input" size="75">
      				</td>
      			</tr>
  				<tr>
	  				<td>User Id</td>
      				<td>
      					<input type="text" class="form-control input-sm" id="userId_input" name="userId_input" size="60">
      				</td>
      			</tr>
      			<tr>
	      			<td>Hub No</td>
      				<td>
	      				<input type="text" class="form-control input-sm" id="hubNo_input" name="hubNo_input" size="60">
      				</td>
      			</tr>
      			<tr>
	      			<td>Branch No</td>
      				<td>
	      				<input type="text" class="form-control input-sm" id="branchNo_input" name="branchNo_input" size="60">
      				</td>
      			</tr>
      			<tr>
	      			<td>Confirm Flag</td>
      				<td>
	      				<input type="text" class="form-control input-sm" id="confirmFlag_input" name="confirmFlag_input" size="60">
      				</td>
      			</tr>
      			<tr>
	      			<td>Validate Flag</td>
      				<td>
	      				<input type="text" class="form-control input-sm" id="validateFlag_input" name="validateFlag_input" size="60">
      				</td>
      			</tr>
      			<tr>
	      			<td>Total Record</td>
      				<td>
	      				<input type="text" class="form-control input-sm" id="totalRecord_input" name="totalRecord_input" size="60">
      				</td>
      			</tr>
      			<tr>
	      			<td>Customer Type</td>
      				<td>
	      				<input type="text" class="form-control input-sm" id="customerType_input" name="customerType_input" size="60">
      				</td>
      			</tr>
      			<tr>
	      			<td>Customer Id</td>
      				<td>
	      				<input type="text" class="form-control input-sm" id="customerId_input" name="customerId_input" size="60">
      				</td>
      			</tr>
      		</table>
			<Strong>CISContact</Strong>
			<table class="table table-striped">
				<thead>
					<tr>
						<th>Type Code</th>
						<th>Location Code</th>
						<th>Contact No</th>
						<th>Contact Ext. No</th>
						<th>Contact Availability Time</th>
						<th>Contact Name</th>
					</tr>
				</thead>
				<tbody id="CISContactList">
					<tr>
						<td><input type="text" class="form-control input-sm" name="contact_typeCode"></td>
						<td><input type="text" class="form-control input-sm" name="contact_locationCode"></td>
						<td><input type="text" class="form-control input-sm" name="contact_contactNo"></td>
						<td><input type="text" class="form-control input-sm" name="contact_contactExtNo"></td>
						<td><input type="text" class="form-control input-sm" name="contact_avaTime"></td>
						<td><input type="text" class="form-control input-sm" name="contact_name"></td>
					</tr>
				</tbody>
			</table>
			<button type="button" class="btn btn-info btn-sm" onclick="addContact()">Add</button>
  		</form>
  		<button type="button" class="btn btn-info btn-md" onclick="submitCIS1035A01()">Request Service</button>
  		<button type="button" class="btn btn-info btn-md" onclick="clearInput()">Clear</button>
  		<button type="button" class="btn btn-info btn-md" onclick="jsonFormat()">Service Data</button>
  		<br><br>
	</div>
  	<div class="well" id="CIS1035A01_output">Output
  		<table>
  			<tr>
  				<td>Customer Id</td>
  				<td><input type="text" class="form-control input-sm" id="customerId_output" size="60" readonly></td>
  			</tr>
  		</table>
  		<table class="table table-striped">
    		<thead>
      			<tr>
        			<th>Contact Id</th>
        			<th>Type Code</th>
        			<th>Location Code</th>
        			<th>Contact No</th>
        			<th>Contact Ext. No</th>
        			<th>Contact Sequence</th>
        			<th>Success Flag</th>
        			<th>Error Description</th>
      			</tr>
    		</thead>
    		<tbody id="resultList">
    		</tbody>
   		</table>
  	</div>
  	<div class="well" id="CIS1035A01_RqRs">
  		<Strong>Request/Response JSON Format</Strong><br>
  		Request :
		<textarea class="form-control" name="requestJson" rows="5" readonly></textarea>
		Response :
		<textarea class="form-control" name="responseJson" rows="5" readonly></textarea>
  	</div>
  	<div id="CIS1035A01_edit"> 
  		<form class="well form-inline" id="CIS1035A01Form_edit">
  			<Strong>Setting</Strong>
  			<table>
  				<tr>
  					<td>URL Webservice </td>
  					<td><input type="text" class="form-control input-sm" name="urlWebService_edit" id="urlWebService_edit" size="75"></td>
  				</tr>
  				<tr>
  					<td>Customer Id</td>
  					<td><input type="text" class="form-control input-sm" name="customerId_edit" id="customerId_edit" size="60"></td>
  				</tr>
  			</table>
  		</form>
  		<button type="button" class="btn btn-info btn-md" onclick="saveCIS1035A01()">Save</button>
  		<button type="button" class="btn btn-info btn-md" onclick="clearSetting()">Clear</button>
  		<br><br>
  	</div>
  	<div class="well" id="CIS1035A01_add"> Add Properties
  		<form class="form-inline" id="CIS1035A01Form_add">
  			<table>
  				<tr>
  					<td>Contact Id</td>
  					<td><input type="text" class="form-control input-sm" name="contactId_add" id="contactId_add" size="75"></td>
  				</tr>
  				<tr>
  					<td>Type Code</td>
  					<td><input type="text" class="form-control input-sm" name="contactTp_add" id="contactTp_add" size="60"></td>
  				</tr>
  				<tr>
  					<td>Location Code</td>
  					<td><input type="text" class="form-control input-sm" name="contactLocation_add" id="contactLocation_add" size="60"></td>
  				</tr>
  				<tr>
  					<td>Contact No</td>
  					<td><input type="text" class="form-control input-sm" name="contactNumber_add" id="contactNumber_add" size="60"></td>
  				</tr>
  				<tr>
  					<td>Contact Ext. No</td>
  					<td><input type="text" class="form-control input-sm" name="contactExtension_add" id="contactExtension_add" size="60"></td>
  				</tr>
  				<tr>
  					<td>Contact Sequence</td>
  					<td><input type="text" class="form-control input-sm" name="contactSequence_add" id="contactSequence_add" size="60"></td>
  				</tr>
  				<tr>
  					<td>Success Flag</td>
  					<td><input type="text" class="form-control input-sm" name="successFlag_add" id="successFlag_add" size="60"></td>
  				</tr>
  				<tr>
  					<td>Error Description</td>
  					<td><input type="text" class="form-control input-sm" name="errorDescription_add" id="errorDescription_add" size="60"></td>
  				</tr>
  			</table>
  		</form>
  		<input type="hidden" id="row_hidden">
  		<button type="button" class="btn btn-info btn-md" id="button_add" onclick="addCIS1035A01()">Add</button>
  		<button type="button" class="btn btn-info btn-md" id="button_edit" onclick="editCIS1035A01()">Edit</button>
  		<table class="table table-striped">
    		<thead>
      			<tr>
        			<th>Contact Id</th>
        			<th>Type Code</th>
        			<th>Location Code</th>
        			<th>Contact No</th>
        			<th>Contact Ext. No</th>
        			<th>Contact Sequence</th>
        			<th>Success Flag</th>
        			<th>Error Description</th>
        			<th>Edit</th>
        			<th>Delete</th>
      			</tr>
    		</thead>
    		<tbody id="resultList_edit">
    		</tbody>
   		</table>
  	</div>
</body>
</html>