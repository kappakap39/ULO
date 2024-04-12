<!DOCTYPE html>
<%@page contentType="text/html; charset:UTF-8;" %>
<html lang=en>
<head>
	<script>
		$(document).ready(function(){
			console.clear();
			$("#CIS1048O01_RqRs").hide();
			$("#CIS1048O01_setting").hide();
						
			$.ajax({
				url : "/ServiceCenterWeb/CIS1048O01?process=defualt",
				type : "get",
				success : function(data){
					var obj = $.parseJSON(data);
					console.log(obj);
					$("input[name=url_input]").val(obj.urlWebService);
					$("input[name=url_setting]").val(obj.urlWebService);
					$("input[name=cusId_setting]").val(obj.customerId);
					
					//defual input value
					$("input[name=kbank_emp_id]").val("002020");
					$("input[name=hub_number]").val("0799");
					$("input[name=branch_code]").val("0799");
					$("input[name=customer_type]").val("I");//I=Individual O=Organization U=Unidentified
					$("input[name=customer_id]").val("0000200104");
					$("input[name=contact_address_id]").val("000000000450104");		
				}
			});
		});
		
		function CIS1048O01_input(){
			$("#CIS1048O01_input").show();
			$("#CIS1048O01_output").show();
			$("#CIS1048O01_setting").hide();
		}
		
		function CIS1048O01_properties(){
			$("#CIS1048O01_input").hide();
			$("#CIS1048O01_output").hide();
			$("#CIS1048O01_setting").show();
		}
		
		function saveSetting(){
			$.ajax({
				url : "/ServiceCenterWeb/CIS1048O01?process=save",
				type : "post",
				data : $("#CIS1048O01Form_setting").serialize(),
				success : function(data){
// 					alert("save data");
				}
			});
		}
		
		function clearSetting(){
			$("input[name=url_setting]").val("");
			$("input[name=cusId_setting]").val("");
		}
		
		function requestService(){
			$.ajax({
				url : "/ServiceCenterWeb/CIS1048O01?process=request",
				type : "get",
				data : $("#CIS1048O01Form_input").serialize(),
				success : function(data){
					var obj = $.parseJSON(data);
					console.log(obj);
					$("#cusId_output").val(obj.customerId);
					$("textarea[name=requestJson]").val(obj.jsonRq);
  					$("textarea[name=responseJson]").val(obj.jsonRs);
				}
			});
		}
		
		function clearInput(){
			$("input[name=url_input]").val("");
			$("input[name=kbank_emp_id]").val("");
			$("input[name=hub_number]").val("");
			$("input[name=branch_code]").val("");
			$("input[name=customer_type]").val("");
			$("input[name=customer_id]").val("");
			$("input[name=contact_address_id]").val("");
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
	</script>
</head>
<body>
	 <ul class="nav nav-tabs">
  		<li class="active"><a data-toggle="tab" onclick="CIS1048O01_input();">Send CIS1048O01</a></li>
		<li><a data-toggle="tab" onclick="CIS1048O01_properties();">Edit Props.</a></li>
  	</ul>
	<br>
	<div id="CIS1048O01_input">
		<form id="CIS1048O01Form_input" class="well form-inline" >
			<strong>Input</strong>
			<table>
				<tr>
					<td>URL Webservice</td>
					<td ><input type="text" name="url_input" class="form-control input-sm" size="75"></td>
				</tr>
				<tr>
					<td>KBANK Employee ID</td>
					<td><input type="text" name="kbank_emp_id" class="form-control input-sm" size="75"></td>					
				</tr>
				<tr>
					<td>Hub number</td>
					<td><input type="text" name="hub_number" class="form-control input-sm" size="75"></td>					
				</tr>
				 <tr>
				 	<td>Branch Code</td>
					<td><input type="text" name="branch_code" class="form-control input-sm" size="75"></td>
				 </tr>
				<tr>
					<td>Customer Type</td>
					<td><input type="text" name="customer_type" class="form-control input-sm" size="75"></td>					
				</tr>
				<tr>
					<td>Customer Id</td>
					<td><input type="text" name="customer_id" class="form-control input-sm" size="75"></td>					
				</tr>
				 <tr>
				 	<td>Contact Address Id</td>
					<td><input type="text" name="contact_address_id" class="form-control input-sm" size="75"></td>
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
		<button type="button" class="btn btn-info" onclick="requestService()">Request Service</button>
		<button type="button" class="btn btn-info" onclick="clearInput()">Clear</button>
		<button type="button" class="btn btn-info btn-md" onclick="jsonFormat()">Save Data</button>
		<br><br>
	</div>
	
	
	
	
	<div class="well" id="CIS0315U01_output"><strong>Output</strong>
		<table>
			<tr>
				<td>Customer Id</td>
				<td><input type="text" id="cusId_output" class="form-control input-sm" size="60" readonly></td>
			</tr>
		</table>
	</div>
	<div class="well" id="CIS1048O01_RqRs">
 			<Strong>Request/Response JSON Format</Strong><br>
  		Request :
		<textarea class="form-control" name="requestJson" rows="5" readonly></textarea>
		Response :
		<textarea class="form-control" name="responseJson" rows="5" readonly></textarea>
  	</div>
	<div id="CIS1048O01_setting">
		<form id="CIS1048O01Form_setting" class="well form-inline">
			<Strong>Setting</Strong>
			<table>
				<tr>
					<td>URL Webservice</td>
					<td><input type="text" name="url_setting" class="form-control input-sm" size="75"></td>
				</tr>
				<tr>
					<td>Customer Id</td>
					<td><input type="text" name="cusId_setting" class="form-control input-sm" size="60"></td>
				</tr>
			</table>
		</form>
		<button type="button" class="btn btn-info" onclick="saveSetting()">Save</button>
		<button type="button" class="btn btn-info" onclick="clearSetting()">Clear</button>
	</div>
</body>
</html>
