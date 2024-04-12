<!DOCTYPE html>
<%@page contentType="text/html; charset=UTF-8" %>
<html lang="en">
<head>
	<script>
		$(document).ready(function(){
			console.clear();
			$("#CIS1046A01_RqRs").hide();
			$("#CIS1046A01_edit").hide();
			$("#CIS1046A01_add").hide();
			$("#button-edit").hide();
			
			$.ajax({
				url : "/ServiceCenterWeb/CIS1046A01?process=defualt",
				type : "GET",
				success : function(data){
					var obj = $.parseJSON(data);
					console.clear();
					console.log(obj);
					$("#urlWebService").val(obj.urlWebService);
					$("#urlWebServiceEdit").val(obj.urlWebService);
					$("#customerIdEdit").val(obj.customerId);
					$("#addressIdEdit").val(obj.addressId);
					$("#accountRefIdEdit").val(obj.accountReferenceId);
					for(var i=0;i<obj.addressType.length;i++){
						var htmlBuild = "<tr>";
						htmlBuild += "<td>"+obj.addressType[i]+"</td>";
						htmlBuild += "<td>"+obj.successFlag[i]+"</td>";
						htmlBuild += "<td>"+obj.errorDescription[i]+"</td>";
						htmlBuild += "<td><a onclick=editProp('"+(i+1)+"')>Edit</a></td>";
						htmlBuild += "<td><a onclick=deleteProp('"+(i+1)+"')>Delete</a></td>";
						htmlBuild += "</tr>";
						$("#resultListEdit").append(htmlBuild);
					}
				}
			});
			
		});
		
		function CIS1046A01Show(){
			$("#CIS1046A01_input").show();
			$("#CIS1046A01_output").show();
			$("#CIS1046A01_edit").hide();
			$("#CIS1046A01_add").hide();
		}
		
		function CIS1046A01EditShow(){
			$("#CIS1046A01_input").hide();
			$("#CIS1046A01_output").hide();
			$("#CIS1046A01_edit").show();
			$("#CIS1046A01_add").show();
		}
		
		function saveCIS1046A01Edit(){
			$.ajax({
				url : "/ServiceCenterWeb/CIS1046A01?process=save",
				type : "POST",
				beforeSend : console.log($("#CIS1046A01EditForm").serialize()),
				data : $("#CIS1046A01EditForm").serialize(),
				success : function(data) {
// 					alert("save data");
				}
			});
		}
		
		function clearInput(){
			$("#urlWebServiceEdit").val("");
			$("#userId").val("");
			$("#hubNo").val("");
			$("#branchNo").val("");
			$("#confirmFlag").val("");
			$("#customerType").val("");
			$("#customerId").val("");
			$("#addressId").val("");
			$("#accountId").val("");
			$("#accountRefId").val("");
			$("#accountLv").val("");
			$("input[name=addressType]").val("");
		}
		
		function addAddressType(){
			var row = $("#addressTypeList > tr ").length+1;
			var htmlBuild = "<tr>";
			htmlBuild += "<td>"+row+"</td>" ;
			htmlBuild += "<td><input type='text' class='form-control input-sm' name='addressType' size='60'></td>" ;
			htmlBuild += "</tr>";
			$("#addressTypeList").append(htmlBuild);
		}
		
		function submitCIS1046A01(){
			$.ajax({
				url : "/ServiceCenterWeb/CIS1046A01?process=request",
				type : "get",
				data : $("#CIS1046A01Form").serialize(),
				beforeSend : console.log($("#CIS1046A01Form").serialize()),
				success : function(data){
					var obj = $.parseJSON(data);
					console.log(obj);
					$("#resultList").empty();
					$("#cusId_output").val(obj.customerId);
					$("#addrId_output").val(obj.addressId);
					$("#accRefId_output").val(obj.accountReferenceId);
					for(var i=0;i<obj.CISAddrRelation.length;i++){
						var htmlBuild = "<tr>";
						htmlBuild += "<td>"+obj.CISAddrRelation[i].addressType+"</td>";
						htmlBuild += "<td>"+obj.CISAddrRelation[i].successFlag+"</td>";
						htmlBuild += "<td>"+obj.CISAddrRelation[i].errorDescription+"</td>";
						htmlBuild += "</tr>";
						$("#resultList").append(htmlBuild);
					}
					$("textarea[name=requestJson]").val(obj.jsonRq);
  					$("textarea[name=responseJson]").val(obj.jsonRs);
				}
			});
		}
		
		function clearSetting(){
			$("#urlWebServiceEdit").val("");
			$("#customerIdEdit").val("");
			$("#addressIdEdit").val("");
			$("#accountRefIdEdit").val("");
		}
		
		function editProp(row){
			$("#button-add").hide();
			$("#button-edit").show();
			$("#hiddenRow").val(row);
			$("#addrType_add").val($("#resultListEdit > tr:nth-child("+row+") >  td:nth-child(1)").text());
			$("#succFlag_add").val($("#resultListEdit > tr:nth-child("+row+") >  td:nth-child(2)").text());
			$("#errDesc_add").val($("#resultListEdit > tr:nth-child("+row+") >  td:nth-child(3)").text());
		}
		
		function deleteProp(row){
			$.ajax({
				url : "/ServiceCenterWeb/CIS1046A01?process=delete&row="+row,
				type : "post",
				success : function(data){
					$("#resultListEdit > tr:nth-child("+row+")").empty();
				}
			});
		}
		
		function addCIS1046A01(){
			var row = $("#resultListEdit tr").length+1;
			$.ajax({
				url : "/ServiceCenterWeb/CIS1046A01?process=add",
				type : "post",
				data : $("#CIS1046A01AddForm").serialize(),
				success : function(data){
					var htmlBuild = "<tr>";
					htmlBuild += "<td>"+$("#addrType_add").val()+"</td>";
					htmlBuild += "<td>"+$("#succFlag_add").val()+"</td>";
					htmlBuild += "<td>"+$("#errDesc_add").val()+"</td>";
					htmlBuild += "<td><a onclick=editProp('"+row+"')>Edit</a></td>";
					htmlBuild += "<td><a onclick=deleteProp('"+row+"')>Delete</td>";
					htmlBuild += "</tr>";
					$("#resultListEdit").append(htmlBuild);
					$("#addrType_add").val("");
					$("#succFlag_add").val("");
					$("#errDesc_add").val("");
				}
			});
		}
		
		function updateCIS1046A01(){
			$.ajax({
				url : "/ServiceCenterWeb/CIS1046A01?process=edit&row="+$("#hiddenRow").val(),
				type : "post",
				data : $("#CIS1046A01AddForm").serialize(),
				success : function(data){
					$("#resultListEdit > tr:nth-child("+$("#hiddenRow").val()+") > td:nth-child(1)").text($("#addrType_add").val());
					$("#resultListEdit > tr:nth-child("+$("#hiddenRow").val()+") > td:nth-child(2)").text($("#succFlag_add").val());
					$("#resultListEdit > tr:nth-child("+$("#hiddenRow").val()+") > td:nth-child(3)").text($("#errDesc_add").val());
					$("#hiddenRow").val("");
					$("#button-edit").hide();
					$("#button-add").show();
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
  		<li class="active"><a data-toggle="tab" onclick="CIS1046A01Show();">Send CIS1046A01</a></li>
		<li><a data-toggle="tab" onclick="CIS1046A01EditShow();">Edit Props.</a></li>
  	</ul>
  	<br>
  	<div id="CIS1046A01_input">
  		<form class="well form-inline" id="CIS1046A01Form">
  			<Strong>Input</Strong>
  			<table>
  				<tr>
	  				<td>URL Web Service</td>
      				<td>
      					<input type="text" class="form-control input-sm" id="urlWebService" name="urlWebService" size="75">
      				</td>
      			</tr>
  				<tr>
	  				<td>User Id</td>
      				<td>
      					<input type="text" class="form-control input-sm" id="userId" name="userId" size="60">
      				</td>
      			</tr>
      			<tr>
	      			<td>Hub No.</td>
      				<td>
	      				<input type="text" class="form-control input-sm" id="hubNo" name="hubNo" size="60">
      				</td>	
      			<tr>
		      		<td>Branch No.</td>
      				<td>
		      			<input type="text" class="form-control input-sm" id="branchNo" name="branchNo" size="60">
    	  			</td>
      			</tr>
      			<tr>
		      		<td>Confirm Flag</td>
      				<td>
		      			<input type="text" class="form-control input-sm" id="confirmFlag" name="confirmFlag" size="60">
    	  			</td>
      			</tr>
      			<tr>
		      		<td>Customer Type</td>
      				<td>
		      			<input type="text" class="form-control input-sm" id="customerType" name="customerType" size="60">
    	  			</td>
      			</tr>
      			<tr>
		      		<td>Customer Id</td>
      				<td>
		      			<input type="text" class="form-control input-sm" id="customerId" name="customerId" size="60">
    	  			</td>
      			</tr>
      			<tr>
		      		<td>Address Id</td>
      				<td>
		      			<input type="text" class="form-control input-sm" id="addressId" name="addressId" size="60">
    	  			</td>
      			</tr>
      			<tr>
		      		<td>Account Id</td>
      				<td>
		      			<input type="text"class="form-control input-sm"id="accountId"name="accountId" size="60">
    	  			</td>
      			</tr>
      			<tr>
		      		<td>Account Reference Id</td>
      				<td>
		      			<input type="text"class="form-control input-sm"id="accountRefId"name="accountRefId" size="60">
    	  			</td>
      			</tr>
      			<tr>
		      		<td>Account Level</td>
      				<td>
		      			<input type="text"class="form-control input-sm"id="accountLv"name="accountLv" size="60">
    	  			</td>
      			</tr>
      		</table>
      		<table class="table table-striped">
      			<thead>
      				<tr>
     					<th>Row</th>
      					<th>Address Type</th>
      				</tr>
      			</thead>
      			<tbody id="addressTypeList">
					<tr>
						<td>1</td>
						<td><input type="text" class="form-control input-sm" name="addressType" size="60"></td>
					</tr>
      			</tbody>
      		</table>
   			<button type="button" class="btn btn-info btn-md" onclick="addAddressType()">add</button>			      		
    	</form>
  		<button type="button" class="btn btn-info btn-md" onclick="submitCIS1046A01()">Request Service</button>
  		<button type="button" class="btn btn-info btn-md" onclick="clearInput()">Clear</button>
  		<button type="button" class="btn btn-info btn-md" onclick="jsonFormat()">Service Data</button>
  		<br><br>
  	</div>
  	<div class="well" id="CIS1046A01_output">
  		<Strong>Output</Strong>
  		<table>
  			<tr>
  				<td>Customer Id</td>
  				<td><input type="text" class="form-control input-sm" id="cusId_output" size="60" readonly></td>
  			</tr>
  			<tr>
	  			<td>Address Id</td>
  				<td><input type="text" class="form-control input-sm" id="addrId_output" size="60" readonly></td>
  			</tr>
  			<tr>
  				<td>Account Reference Id</td>
  				<td><input type="text" class="form-control input-sm" id="accRefId_output" size="60" readonly></td>
  			</tr>
  		</table>
  		<table class="table table-striped">
    		<thead>
      			<tr>
        			<th>Address Type</th>
        			<th>Success Flag</th>
        			<th>Error Description</th>
      			</tr>
    		</thead>
    		<tbody id="resultList">
    		</tbody>
  		</table>
  	</div>
  	<div class="well" id="CIS1046A01_RqRs">
  		<Strong>Request/Response JSON Format</Strong><br>
  		Request :
		<textarea class="form-control" name="requestJson" rows="5" readonly></textarea>
		Response :
		<textarea class="form-control" name="responseJson" rows="5" readonly></textarea>
  	</div>
  	<div id="CIS1046A01_edit">
  		<form class="well form-inline" id="CIS1046A01EditForm">
  			<Strong>Setting</Strong>
  			<table>
  				<tr>
  					<td>URL Web Service</td>
  					<td><input type="text" class="form-control input-sm" name="urlWebServiceEdit" id="urlWebServiceEdit" size="75"></td>
  				</tr>
  				<tr>
  					<td>Customer Id</td>
  					<td><input type="text" class="form-control input-sm" name="customerIdEdit" id="customerIdEdit" size="60"></td>
  				</tr>
  				<tr>
  					<td>Address Id</td>
  					<td><input type="text" class="form-control input-sm" name="addressIdEdit" id="addressIdEdit" size="60"></td>
  				</tr>
  				<tr>
  					<td>Account Reference Id</td>
  					<td><input type="text" class="form-control input-sm" name="accountRefIdEdit" id="accountRefIdEdit" size="60"></td>
  				</tr>
  			</table>
  		</form>
  		<button type="button" class="btn btn-info btn-md" onclick="saveCIS1046A01Edit()">Save</button>
  		<button type="button" class="btn btn-info btn-md" onclick="clearSetting()">Clear</button>
  		<br><br>
  	</div>
  	
	<div class="well" id="CIS1046A01_add">
		<form class="form-inline" id="CIS1046A01AddForm" role="form">
			<Strong>Add Properties</Strong>
			<table>
				<tr>
					<td>Address Type</td>
					<td><input type="text" class="form-control input-sm" name="addrType_add" id="addrType_add" size="60"></td>
				</tr>
				<tr>
					<td>Success Flag</td>
					<td><input type="text" class="form-control input-sm" name="succFlag_add" id="succFlag_add" size="60"></td>
				</tr>
				<tr>
					<td>Error Description</td>
					<td><input type="text" class="form-control input-sm" name="errDesc_add" id="errDesc_add" size="60"></td>
				</tr>
			</table>
			<input type="hidden" id="hiddenRow">
			<button type="button" class="btn btn-info btn-md" onclick="addCIS1046A01();" id="button-add">Add</button>
			<button type="button" class="btn btn-info btn-md" onclick="updateCIS1046A01();" id="button-edit">Edit</button>
		</form>
		<table class="table table-striped">
			<thead>
				<tr>
					<th>Address Type</th>
					<th>Success Flag</th>
					<th>Error Description</th>
					<th>Edit</th>
					<th>Delete</th>
				</tr>
			</thead>
			<tbody id="resultListEdit">
			</tbody>
		</table>
	</div>
</body>
</html>