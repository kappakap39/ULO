<!DOCTYPE html>
<%@page contentType="text/html; charser:UTF-8" %>
<html lang=en>
<head>
	<script>
		$(document).ready(function(){
			console.clear();
			$("#CIS1036A01_RqRs").hide();
			$("#CIS1036A01_setting").hide();
			
			$.ajax({
				url : "/ServiceCenterWeb/CIS1036A01?process=defualt",
				type : "get",
				success : function(data){
					var obj = $.parseJSON(data);
					console.log(obj);
					$("input[name=url_input]").val(obj.urlWebService);
					$("input[name=url_setting]").val(obj.urlWebService);
					$("input[name=cusId_setting]").val(obj.customerId);
					$("input[name=addrId_setting]").val(obj.addressId);
				}
			});
		});
		
		function requestService(){
			$.ajax({
				url : "/ServiceCenterWeb/CIS1036A01?process=request",
				type : "get",
				data : $(".CIS1036A01Form_input").serialize(),
				success : function(data){
					var obj = $.parseJSON(data);
					console.log(obj);
					$("input[name=cusId_output]").val(obj.customerId);
					$("input[name=addrId_output]").val(obj.addressId);
					$("textarea[name=requestJson]").val(obj.jsonRq);
  					$("textarea[name=responseJson]").val(obj.jsonRs);
				}
			});
		}
		
		function clearInput(){
			$(".CIS1036A01Form_input").trigger("reset");	
		}
		
		function CIS1036A01_input(){
			$("#CIS1036A01_input").show();
			$("#CIS1036A01_output").show();
			$("#CIS1036A01_setting").hide();
		}
		
		function CIS1036A01_setting(){
			$("#CIS1036A01_input").hide();
			$("#CIS1036A01_output").hide();
			$("#CIS1036A01_setting").show();
		}
		
		function saveSetting(){
			$.ajax({
				url : "/ServiceCenterWeb/CIS1036A01?process=save",
				type : "get",
				data : $("#CIS1036A01Form_setting").serialize(),
				success : function(data){
					alert("save data");
				}
			});
		}
		
		function clearSetting(){
			$("#CIS1036A01Form_setting").trigger("reset");
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
  		<li class="active"><a data-toggle="tab" onclick="CIS1036A01_input();">Send CIS1036A01</a></li>
		<li><a data-toggle="tab" onclick="CIS1036A01_setting();">Edit Props.</a></li>
  	</ul>
	<br>
	<div id="CIS1036A01_input">
		<form class="well form-inline CIS1036A01Form_input"><strong>Input</strong>
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
					<td><input type="text" class="form-control input-sm" name="brNo_input" size="60"></td>
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
					<td>Customer Type</td>
					<td><input type="text" class="form-control input-sm" name="cusType_input" size="60"></td>
				</tr>
				<tr>
					<td>Customer Id</td>
					<td><input type="text" class="form-control input-sm" name="cusId_input" size="60"></td>
				</tr>
				<tr>
					<td>Customer Type Code</td>
					<td><input type="text" class="form-control input-sm" name="cusTypeCode_input" size="60"></td>
				</tr>
			</table>
		</form>
		<form class="well form-inline CIS1036A01Form_input"><Strong>Address Input</Strong>
			<table>
				<tr>
					<td>Address Id</td>
					<td><input type="text" class="form-control input-sm" name="addrId_input" size="50"></td>
					<td>Address Name</td>
					<td><input type="text" class="form-control input-sm" name="addrName_input" size="50"></td>
				</tr>
				<tr>
					<td>Mail Box No</td>
					<td><input type="text" class="form-control input-sm" name="mailBoxNo_input" size="50"></td>
					<td>Mail No</td>
					<td><input type="text" class="form-control input-sm" name="mailNo_input" size="50"></td>
				</tr>
				<tr>
					<td>Moo</td>
					<td><input type="text" class="form-control input-sm" name="moo_input" size="50"></td>
					<td>Mooban</td>
					<td><input type="text" class="form-control input-sm" name="mooban_input" size="50"></td>
				</tr>
				<tr>
					<td>Building</td>
					<td><input type="text" class="form-control input-sm" name="build_input" size="50"></td>
					<td>Room</td>
					<td><input type="text" class="form-control input-sm" name="room_input" size="50"></td>
				</tr>
				<tr>
					<td>Floor</td>
					<td><input type="text" class="form-control input-sm" name="floor_input" size="50"></td>
					<td>Soi</td>
					<td><input type="text" class="form-control input-sm" name="soi_input" size="50"></td>
				</tr>
				<tr>
					<td>Road</td>
					<td><input type="text" class="form-control input-sm" name="road_input" size="50"></td>
					<td>Tumbol</td>
					<td><input type="text" class="form-control input-sm" name="tunbol_input" size="50"></td>
				</tr>
				<tr>
					<td>Amphur</td>
					<td><input type="text" class="form-control input-sm" name="amphur_input" size="50"></td>
					<td>Province</td>
					<td><input type="text" class="form-control input-sm" name="province_input" size="50"></td>
				</tr>
				<tr>
					<td>Postcode</td>
					<td><input type="text" class="form-control input-sm" name="postcode_input" size="50"></td>
					<td>Country</td>
					<td><input type="text" class="form-control input-sm" name="country_input" size="50"></td>
				</tr>
				<tr>
					<td>Line 1</td>
					<td><input type="text" class="form-control input-sm" name="line1_input" size="50"></td>
					<td>Line 2</td>
					<td><input type="text" class="form-control input-sm" name="line2_input" size="50"></td>
				</tr>
			</table>
		</form>
		<button type="button" class="btn btn-info" onclick="requestService()">Request Service</button>
		<button type="button" class="btn btn-info" onclick="clearInput()">Clear</button>
		<button type="button" class="btn btn-info btn-md" onclick="jsonFormat()">Service Data</button>
		<br><br>
	</div>
	<div id="CIS1036A01_output" class="well"><strong>Output</strong>
		<table>
			<tr>
				<td>Customer Id</td>
				<td><input type="text" class="form-control input-sm" name="cusId_output" size="50" readonly></td>
			</tr>
			<tr>
				<td>Address Id</td>
				<td><input type="text" class="form-control input-sm" name="addrId_output" size="50" readonly></td>
			</tr>
		</table>
	</div>
	<div class="well" id="CIS1036A01_RqRs">
  		<Strong>Request/Response JSON Format</Strong><br>
  		Request :
		<textarea class="form-control" name="requestJson" rows="5" readonly></textarea>
		Response :
		<textarea class="form-control" name="responseJson" rows="5" readonly></textarea>
  	</div>
	<div id="CIS1036A01_setting"><strong>Setting</strong>
		<form class="form-inline well" id="CIS1036A01Form_setting">
			<table>
				<tr>
					<td>URL Web Service</td>
					<td><input type="text" class="form-control input-sm" name="url_setting" size="75"></td>
				</tr>
				<tr>
					<td>Customer Id</td>
					<td><input type="text" class="form-control input-sm" name="cusId_setting" size="60"></td>
				</tr>
				<tr>
					<td>Address Id</td>
					<td><input type="text" class="form-control input-sm" name="addrId_setting" size="60"></td>
				</tr>
			</table>
		</form>
		<button type="button" class="btn btn-info" onclick="saveSetting()">Save</button>
		<button type="button" class="btn btn-info" onclick="clearSetting()">Clear</button>
	</div>
</body>
</html>
