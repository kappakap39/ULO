<!DOCTYPE html>
<%@page import="com.eaf.service.module.manual.CIS0222I01ServiceProxy"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<html lang="en">
<script>
	$(document).ready(function(){
		console.clear();
		$("#CIS0222I01_RqRs").hide();
		$("#CIS0222I01_edit").hide();
		$("#CIS0222I01_add").hide();

		$.ajax({
			url : "/ServiceCenterWeb/CIS0222I01?process=defualt",
			type : "GET",
			success : function(data){
				var obj = jQuery.parseJSON(data);
				var totalResult = obj.zipCode.length;
				console.log(data);
				$("#urlWebService").val(obj.<%=CIS0222I01ServiceProxy.url%>);
				$("#tumbolName").val("");
				$("#amphurName").val("");
				$("#provinceName").val("");
				
				$("#urlWebServiceEdit").val(obj.<%=CIS0222I01ServiceProxy.url%>);
				
				$("#resultListEdit").empty();
		  		for(var i=0;i<totalResult;i++){
		  			var functionBuild = "('"+obj.tumbolDescription[i]+"','"+obj.amphurDescription[i]+"','"+obj.provinceDescription[i]+"','"+obj.zipCode[i]+"','"+(i+1)+"')";
		  			var htmlBuild = "<tr>";
		  			htmlBuild += "<td>"+obj.tumbolDescription[i]+"</td>";
		  			htmlBuild += "<td>"+obj.amphurDescription[i]+"</td>";
		  			htmlBuild += "<td>"+obj.provinceDescription[i]+"</td>";
		  			htmlBuild += "<td>"+obj.zipCode[i]+"</td>";
		  			htmlBuild += "<td><a onclick=editProp"+functionBuild+">Edit</a></td>";
		  			htmlBuild += "<td><a onclick=deleteProp"+functionBuild+">Delete</a></td>";
		  			htmlBuild += "</tr>";
		  			$("#resultListEdit").append(htmlBuild);
		  		}

			}
		});
		
	});
	
	function CIS0222I01Show(){
		$("#CIS0222I01_input").show();
		$("#CIS0222I01_output").show();
		$("#CIS0222I01_edit").hide();
		$("#CIS0222I01_add").hide();
	}
	
	function CIS0222I01EditShow(){
		$("#CIS0222I01_input").hide();
		$("#CIS0222I01_output").hide();
		$("#CIS0222I01_edit").show();
		$("#CIS0222I01_add").show();
		$("#button-edit").hide();
	}
	
	//send request to servlet
  	function submitCIS0222I01(){
  		if(($("#tumbolName").val()!="")||($("#amphurName").val()!="" && $("#provinceName").val()!="")){
	  		$.ajax({
	  			url : "/ServiceCenterWeb/CIS0222I01?process=defualt",
	  			type : "GET",
	  			data : $("#CIS0222I01Form").serialize(),
	  			beforeSend : console.log($("#CIS0222I01Form").serialize()),
	  			success : function(data){
					var obj = jQuery.parseJSON(data);
					console.log(obj);
					var totalResult = obj.addressInfomationList.length;

	  				$("#totalResult").val(obj.totalResult);
	
			  		$("#resultList").empty();
			  		for(var i=0;i<totalResult;i++){
			  			var htmlBuild = "<tr>";
			  			htmlBuild += "<td>"+obj.addressInfomationList[i].tumbol+"</td>";
			  			htmlBuild += "<td>"+obj.addressInfomationList[i].amphur+"</td>";
			  			htmlBuild += "<td>"+obj.addressInfomationList[i].province+"</td>";
			  			htmlBuild += "<td>"+obj.addressInfomationList[i].zipCode+"</td>";
			  			htmlBuild += "</tr>";
			  			$("#resultList").append(htmlBuild);
			  		}
			  		$("textarea[name=requestJson]").val(obj.jsonRq);
  					$("textarea[name=responseJson]").val(obj.jsonRs);
	  			}
	  		});
	  	}else{
	  		eModal.alert("Search by<br>1.tumbol name<br>2.amphur name,province name <br>3.tumbol name,amphur name,province name");
	  	}
  	}
  	
  	//edit file properties
  	function saveCIS0222I01Edit(){
  		$.ajax({
  			url : "/ServiceCenterWeb/CIS0222I01?process=save",
  			type : "POST",
  			beforeSend : console.log($("#CIS0222I01EditForm").serialize()),
  			data : $("#CIS0222I01EditForm").serialize(),
  			success : function(data){
//   				alert("save data");
  			}
  		});
  	}
  	
  	function clearInput(){
  		$("#urlWebService").val("");
		$("#tumbolName").val("");
		$("#amphurName").val("");
		$("#provinceName").val("");
  	}
  	
  	function clearSetting(){
  		$("#urlWebServiceEdit").val("");
  	}
  	
  	function addCIS0222I01(){
  		if($("#zipCodeEdit").val()!="" && $("#provinceDescriptionEdit").val()!="" && $("#amphurDescriptionEdit").val()!="" && $("#tumbolDescriptionEdit").val()!=""){
	  		$.ajax({
	  			url : "/ServiceCenterWeb/CIS0222I01?process=add",
	  			type : "post",
	  			data : $("#CIS0222I01AddForm").serialize(),
	  			beforeSend : console.log($("#CIS0222I01AddForm").serialize()),
	  			success : function(data){
	  				var htmlBuild = "<tr>";
		  			htmlBuild += "<td>"+$("#tumbolDescriptionEdit").val()+"</td>";
		  			htmlBuild += "<td>"+$("#amphurDescriptionEdit").val()+"</td>";
		  			htmlBuild += "<td>"+$("#provinceDescriptionEdit").val()+"</td>";
		  			htmlBuild += "<td>"+$("#zipCodeEdit").val()+"</td>";
		  			htmlBuild += "<td><a onclick=editProp('"+$("#tumbolDescriptionEdit").val()+"','"+$("#amphurDescriptionEdit").val()+"','"+$("#provinceDescriptionEdit").val()+"','"+$("#zipCodeEdit").val()+"','"+($("#resultListEdit > tr").length+1)+"')>Edit</a></td>";
		  			htmlBuild += "<td><a onclick=deleteProp('"+$("#tumbolDescriptionEdit").val()+"','"+$("#amphurDescriptionEdit").val()+"','"+$("#provinceDescriptionEdit").val()+"','"+$("#zipCodeEdit").val()+"','"+($("#resultListEdit > tr").length+1)+"')>Delete</a></td>";
		  			htmlBuild += "</tr>";
		  			$("#resultListEdit").append(htmlBuild);
	  			
	  				$("#tumbolDescriptionEdit").val("");
					$("#amphurDescriptionEdit").val("");
					$("#provinceDescriptionEdit").val("");
					$("#zipCodeEdit").val("");
	  			}
	  		});
	  	}else{
	  		eModal.alert("please fill all value");
	  	}
  	}
  	
  	function deleteProp(tumbol,amphur,province,zipCode,row){
  		$.ajax({
  			url : "/ServiceCenterWeb/CIS0222I01?process=delete&dTumbol="+tumbol+"&dAmphur="+amphur+"&dProvince="+province+"&dZipCode="+zipCode,
  			type : "POST",
  			success : function(data){
  				$("#resultListEdit > tr:nth-child("+row+")").remove();
  			}
  		});
  	}
  	
  	//edit file addressDescription
  	function editProp(tumbol,amphur,province,zipCode,row){
		$("#tumbolDescriptionEdit").val(tumbol);
		$("#amphurDescriptionEdit").val(amphur);
		$("#provinceDescriptionEdit").val(province);
		$("#zipCodeEdit").val(zipCode);
		$("#rowEdit").val(row);
		$("#button-add").hide();
		$("#button-edit").show();
  	}
  	
  	// update file addressDescription
  	function updateCIS0222I01(){
  		$("#button-edit").hide();
  		$("#button-add").show();
  		
  		$.ajax({
  			url : "/ServiceCenterWeb/CIS0222I01?process=update",
  			type : "post",
  			data : $("#CIS0222I01AddForm").serialize(),
  			beforeSend : console.log($("#CIS0222I01AddForm").serialize()),
  			success : function(data){
  				var row = $("#rowEdit").val();
				
				$("#resultListEdit > tr:nth-child("+row+") > td:nth-child(1)").text($("#tumbolDescriptionEdit").val());
				$("#resultListEdit > tr:nth-child("+row+") > td:nth-child(2)").text($("#amphurDescriptionEdit").val());
				$("#resultListEdit > tr:nth-child("+row+") > td:nth-child(3)").text($("#provinceDescriptionEdit").val());
				$("#resultListEdit > tr:nth-child("+row+") > td:nth-child(4)").text($("#zipCodeEdit").val());
				
				$("#tumbolDescriptionEdit").val("");
				$("#amphurDescriptionEdit").val("");
				$("#provinceDescriptionEdit").val("");
				$("#zipCodeEdit").val("");
				$("#rowEdit").val("");
  			} 
  		});
		
  	}
  	
//   	function jsonFormat(){
//   		$("#dialog").html("");
//   		$("#dialog").attr("title","JSON Format");
//   		$("#dialog").dialog({modal:true, width:1050, minHeight:400, draggable:true, resizable:true, autoResize:true});
//   		$.ajax({
//   			url : "/ServiceCenterWeb/jsp/jsonPopup.jsp",
//   			type : "get",
//   			success : function(data){
//   				$("#dialog").html(data);
//   			}
//   		});
//   	}
</script>
<body>
  	 <ul class="nav nav-tabs">
  		<li class="active"><a data-toggle="tab" onclick="CIS0222I01Show();">Send CIS0222I01</a></li>
		<li><a data-toggle="tab" onclick="CIS0222I01EditShow();">Edit Props.</a></li>
  	</ul>
  	<br>
  	<div id="CIS0222I01_input">
  		<form class="well form-inline" id="CIS0222I01Form">
  			<Strong>Input</Strong>
  			<table>
  				<tr>
	  				<td>URL Web Service</td>
      				<td>
      					<input type="text" class="form-control input-sm" id="urlWebService"name="urlWebService" size="75">
      				</td>
      			</tr>
  				<tr>
	  				<td>Tumbol Name</td>
      				<td>
      					<input type="text" class="form-control input-sm" id="tumbolName"name="tumbolName" size="60">
      				</td>
      			</tr>
      			<tr>
	      			<td>Amphur Name</td>
      				<td>
	      				<input type="text" class="form-control input-sm" id="amphurName"name="amphurName" size="60">
      				</td>	
      			<tr>
		      		<td>Province Name</td>
      				<td>
		      			<input type="text" class="form-control input-sm" id="provinceName"name="provinceName" size="60">
    	  			</td>
      			</tr>
      		</table>
    	</form>
  		<button type="button" class="btn btn-info btn-md" onclick="submitCIS0222I01()">Request Service</button>
  		<button type="button" class="btn btn-info btn-md" onclick="clearInput()">Clear</button>
  		<button type="button" class="btn btn-info btn-md" onclick="jsonFormat()">Service Data</button>
  		<br><br>
  	</div>
  	
  	<div class="well" id="CIS0222I01_output">
  		<Strong>Output</Strong>
  		<table>
  			<tr>
	  			<td>Total Result</td>
  				<td><input type="text" class="form-control input-sm" id="totalResult" size="60" readonly></td>
  			</tr>
  		</table>
  		<table class="table table-striped">
    		<thead>
      			<tr>
        			<th>Tumbol Description</th>
        			<th>Amphur Description</th>
        			<th>Province Description</th>
        			<th>Zip Code</th>
      			</tr>
    		</thead>
    		<tbody id="resultList">
      			<tr>
        			<td></td>
        			<td></td>
        			<td></td>
        			<td></td>
        			<td></td>
      			</tr>
    		</tbody>
  		</table>
  	</div>
  	<div class="well" id="CIS0222I01_RqRs">
  		<Strong>Request/Response JSON Format</Strong><br>
  		Request :
		<textarea class="form-control" name="requestJson" rows="5" readonly></textarea>
		Response :
		<textarea class="form-control" name="responseJson" rows="5" readonly></textarea>
	</div>
  	<div id="CIS0222I01_edit"> 
  		<form class="well form-inline" id="CIS0222I01EditForm">
  			<Strong>Setting</Strong>
  			<table>
  				<tr>
  					<td>URL Web Service</td>
  					<td><input type="text" class="form-control input-sm" name="urlWebServiceEdit" id="urlWebServiceEdit" size="75"></td>
  				</tr>
  			</table>
  		</form>
  		<button type="button" class="btn btn-info btn-md" onclick="saveCIS0222I01Edit()">Save</button>
  		<button type="button" class="btn btn-info btn-md" onclick="clearSetting()">Clear</button>
  		<br><br>
  	</div>
  	<div id="CIS0222I01_add">
  		<form class="well form-inline" id="CIS0222I01AddForm"> Add Properties
  			<table>
  				<tr>
  					<td>Zip Code</td>
  					<td><input type="text" class="form-control input-sm" name="zipCodeEdit" id="zipCodeEdit" size="60"></td>
  				</tr>
  				<tr>
  					<td>Province Description</td>
  					<td><input type="text" class="form-control input-sm" name="provinceDescriptionEdit" id="provinceDescriptionEdit" size="60"></td>
  				</tr>
  				<tr>
  					<td>Amphur Description</td>
  					<td><input type="text" class="form-control input-sm" name="amphurDescriptionEdit" id="amphurDescriptionEdit" size="60"></td>
  				</tr>
				<tr>
  					<td>Tumbol Description</td>
  					<td><input type="text" class="form-control input-sm" name="tumbolDescriptionEdit" id="tumbolDescriptionEdit" size="60"></td>
  				</tr>
  			</table>
  			<input type="hidden" class="form-control input-sm" name="rowEdit" id="rowEdit" size="60">
			<button type="button" class="btn btn-info btn-md" onclick="addCIS0222I01();" id="button-add">Add</button>
			<button type="button" class="btn btn-info btn-md" onclick="updateCIS0222I01();" id="button-edit">Save</button>
			<br><br>
		</form>
 		<table class="table table-striped">
    		<thead>
      			<tr>
        			<th>Tumbol Description</th>
        			<th>Amphur Description</th>
        			<th>Province Description</th>
        			<th>Zip Code</th>
        			<th>Edit</th>
        			<th>Delete</th>
      			</tr>
    		</thead>
    		<tbody id="resultListEdit">
      			<tr>
        			<td></td>
        			<td></td>
        			<td></td>
        			<td></td>
        			<td></td>
        			<td></td>
      			</tr>
    		</tbody>
 		</table>
  	</div>
</body>
</html>
