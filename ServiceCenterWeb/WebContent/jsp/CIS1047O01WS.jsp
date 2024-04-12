<!DOCTYPE html>
<%@page contentType="text/html; charset:UTF-8;" %>
<html lang=en>
<head>
	<script>
		$(document).ready(function(){
			console.clear();
			$("#CIS1047O01_RqRs").hide();
			$("#CIS1047O01_setting").hide();
						
			$.ajax({
				url : "/ServiceCenterWeb/CIS1047O01?process=defualt",
				type : "get",
				success : function(data){
					var obj = $.parseJSON(data);
					console.log(obj);
					$("input[name=url_input]").val(obj.urlWebService);
					$("input[name=url_setting]").val(obj.urlWebService);
					$("input[name=cusId_setting]").val(obj.customerId);
					
					//defual input value
					$("input[name=hub_number]").val("0799");
					$("input[name=branch_code]").val("0799");
					$("input[name=data_type]").val("A");   //E=email,P=Phone, A=Address
					$("input[name=customer_id]").val("0000020034");
					$("input[name=contact_address_id]").val("000000000304050");
					$("input[name=contact_type]").val("01");
					$("input[name=contact_function_type]").val("1");
				}
			});
		});
		
		function CIS1047O01_input(){
			$("#CIS1047O01_input").show();
			$("#CIS1047O01_output").show();
			$("#CIS1047O01_setting").hide();
		}
		
		function CIS1047O01_properties(){
			$("#CIS1047O01_input").hide();
			$("#CIS1047O01_output").hide();
			$("#CIS1047O01_setting").show();
		}
		
		function saveSetting(){
			$.ajax({
				url : "/ServiceCenterWeb/CIS1047O01?process=save",
				type : "post",
				data : $("#CIS1047O01Form_setting").serialize(),
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
				url : "/ServiceCenterWeb/CIS1047O01?process=request",
				type : "get",
				data : $("#CIS1047O01Form_input").serialize(),
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
			$("input[name=hub_number]").val("");
			$("input[name=branch_code]").val("");
			$("input[name=data_type]").val("");
			$("input[name=customer_id]").val("");
			$("input[name=contact_address_id]").val("");
			$("input[name=contact_type]").val("");
			$("input[name=contact_function_type]").val("");		
		}
		
 
	</script>
</head>
<body>
	 <ul class="nav nav-tabs">
  		<li class="active"><a data-toggle="tab" onclick="CIS1047O01_input();">Send CIS1047O01</a></li>
		<li><a data-toggle="tab" onclick="CIS1047O01_properties();">Edit Props.</a></li>
  	</ul>
	<br>
	<div id="CIS1047O01_input">
		<form id="CIS1047O01Form_input" class="well form-inline" >
			<strong>Input</strong>
			<table>
				<tr>
					<td>URL Webservice</td>
					<td ><input type="text" name="url_input" class="form-control input-sm" size="75"></td>
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
					<td>Data type Code</td>
					<td><input type="text" name="data_type" class="form-control input-sm" size="75"></td>					
				</tr>
				<tr>
					<td>Customer Id</td>
					<td><input type="text" name="customer_id" class="form-control input-sm" size="75"></td>					
				</tr>
				 <tr>
				 	<td>Contact Address Id</td>
					<td><input type="text" name="contact_address_id" class="form-control input-sm" size="75"></td>
				 </tr>
				<tr>
					<td>Contact Type</td>
					<td><input type="text" name="contact_type" class="form-control input-sm" size="75"></td>					
				</tr>
				<tr>
					<td>Contact FuncType Code</td>
					<td><input type="text" name="contact_function_type" class="form-control input-sm" size="75"></td>					
				</tr>
			</table>
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
	<div class="well" id="CIS1047O01_RqRs">
 			<Strong>Request/Response JSON Format</Strong><br>
  		Request :
		<textarea class="form-control" name="requestJson" rows="5" readonly></textarea>
		Response :
		<textarea class="form-control" name="responseJson" rows="5" readonly></textarea>
  	</div>
	<div id="CIS1047O01_setting">
		<form id="CIS1047O01Form_setting" class="well form-inline">
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
