<!DOCTYPE html>
<%@page import="com.eaf.service.module.manual.SMSServiceProxy"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<html lang="en">
<script>
	$(document).ready(function(){
		console.clear();
		$("#SMS_RqRs").hide();
		$("#SMS_edit").hide();
		
		$.ajax({
			url : "/ServiceCenterWeb/SmsService?process=defualt",
			type : "GET",
			success : function(data){
				var obj = jQuery.parseJSON(data);
				$("#urlWebService").val(obj.<%=SMSServiceProxy.url%>);
				$("#mobileNoElement").val("0812225555");
				$("#msg").val("ยินดีต้อนรับสู่บริการ K-mAlert");
				$("#templateId").val("600");
				$("#smsLang").val("TH");
				$("#departmentCode").val("RE-OTP");
				$("#priority").val("0");
				$("#messageType").val("0605040B8423F0");
				$("#clientId").val("KBank");
				$("#urlWebServiceEdit").val(obj.urlWebService);
				$("#responseCodeEdit").val(obj.responseCode);
  				$("#responseDetailEdit").val(obj.responseDetail);
			}
		});
	});
	
	function SMSShow(){
		$("#SMS_input").show();
		$("#SMS_output").show();
		$("#SMS_edit").hide();
	}
	
	function SMSEditShow(){
		$("#SMS_input").hide();
		$("#SMS_output").hide();
		$("#SMS_RqRs").hide();
		$("#SMS_edit").show();
	}
	
  	function submitSmsAlert(){
  		$.ajax({
  			url : "/ServiceCenterWeb/SmsService?process=requestSMS",
  			type : "GET",
  			data : $("#SmsAlertForm").serialize(),
  			beforeSend : console.log($("#SmsAlertForm").serialize()),
  			success : function(data){
				var obj = jQuery.parseJSON(data);
				console.log(obj);
  				$("#responseCode").val(obj.responseCode);
  				$("#responseDetail").val(obj.responseDetail);
  				
  				$("textarea[name=requestJson]").val(obj.jsonRq);
  				$("textarea[name=responseJson]").val(obj.jsonRs);
  			}
  		});
  	}
  	
  	function saveSmsEdit(){
  		$.ajax({
  			url : "/ServiceCenterWeb/SmsService?process=editSMS",
  			type : "POST",
  			beforeSend : console.log($("#SmsAlertEditForm").serialize()),
  			data : $("#SmsAlertEditForm").serialize(),
  			success : function(data){
//   				alert("Saving");
  			}
  		});
  	}
  	
  	function clearInput(){
  		$("#urlWebService").val("");
		$("#mobileNoElement").val("");
		$("#msg").val("");
		$("#templateId").val("");
		$("#smsLang").val("");
		$("#departmentCode").val("");
		$("#priority").val("");
		$("#messageType").val("");
		$("#clientId").val("");
  	}
  	
  	function clearSetting(){
  		$("#urlWebServiceEdit").val("");
		$("#responseCodeEdit").val("");
  		$("#responseDetailEdit").val("");
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
  		<li class="active"><a data-toggle="tab" onclick="SMSShow();">Send SMS</a></li>
		<li><a data-toggle="tab" onclick="SMSEditShow();">Edit Props.</a></li>
  	</ul>
  	<br>
  	<div id="SMS_input">
  		<form class="well form-inline" id="SmsAlertForm">
  			<Strong>Input</Strong>
  			<table>
  				<tr>
	  				<td>URL Web Service</td>
      				<td>
      					<input type="text" class="form-control input-sm" id="urlWebService"name="urlWebService" size="75">
      				</td>
      			</tr>
  				<tr>
	  				<td>Mobile No Element</td>
      				<td>
      					<input type="text" class="form-control input-sm" id="mobileNoElement"name="mobileNoElement" size="60">
      				</td>
      			</tr>
      			<tr>
	      			<td>Message</td>
      				<td>
	      				<input type="text" class="form-control input-sm" id="msg"name="msg" size="60">
      				</td>
      			</tr>
      			<tr>
		      		<td>Template Id</td>
      				<td>
		      			<input type="text" class="form-control input-sm" id="templateId"name="templateId" size="60">
    	  			</td>
      			</tr>
	      		<tr>	
	    	  		<td>SMS Language</td>
    	  			<td>	
      					<input type="text" class="form-control input-sm" id="smsLang"name="smsLang" size="60">
      				</td>	
      			</tr>
      			<tr>
		      		<td>Department Code</td>
	      			<td>
	    	  			<input type="text" class="form-control input-sm" id="departmentCode"name="departmentCode" size="60">
      				</td>
      			</tr>
      			<tr>	
      				<td>Priority</td>
      				<td>
	      				<input type="text" class="form-control input-sm" id="priority"name="priority" size="60">
      				</td>
      			</tr>
      			<tr>
	      			<td>Message Type</td>
      				<td>
	      				<input type="text" class="form-control input-sm" id="messageType"name="messageType" size="60">
      				</td>
      			</tr>
      			<tr>
	      			<td>Client Id</td>	
      				<td>
		      			<input type="text" class="form-control input-sm" id="clientId"name="clientId" size="60">
    	  			</td>
      			</tr>
      		</table>
    	</form>
  		<button type="button" class="btn btn-info btn-md" onclick="submitSmsAlert()">Request Service</button>
  		<button type="button" class="btn btn-info btn-md" onclick="clearInput()">Clear</button>
  		<button type="button" class="btn btn-info btn-md" onclick="jsonFormat()">Service Data</button> 
  		<br><br>
  	</div>
  	<div class="well" id="SMS_output">
  		<Strong>Output</Strong>
  		<table>
  			<tr>
  				<td>Response Code</td>
  				<td><input type="text" class="form-control input-sm" id="responseCode" size="60" readonly></td>
  			</tr>
  			<tr>
  				<td>Response Detail </td>
  				<td><input type="text" class="form-control input-sm" id="responseDetail" size="60" readonly></td>
  			</tr>
  		</table>
  	</div>
  	<div class="well" id="SMS_RqRs">
  		<Strong>Request/Response JSON Format</Strong><br>
  		Request :
		<textarea class="form-control" name="requestJson" rows="5" readonly></textarea>
		Response :
		<textarea class="form-control" name="responseJson" rows="5" readonly></textarea>
  	</div>
  	<div id="SMS_edit"> 
  		<form class="well form-inline" id="SmsAlertEditForm">
  			<Strong>Setting</Strong>
  			<table>
  				<tr>
  					<td>URL Web Service</td>
  					<td><input type="text" class="form-control input-sm" name="urlWebServiceEdit" id="urlWebServiceEdit" size="75"></td>
  				</tr>
  				<tr>
  					<td>Response Code</td>
  					<td><input type="text" class="form-control input-sm" name="responseCodeEdit" id="responseCodeEdit" size="60"></td>
  				</tr>
  				<tr>
  					<td>Response Detail</td>
  					<td><input type="text" class="form-control input-sm" name="responseDetailEdit" id="responseDetailEdit" size="60"></td>
  				</tr>
  			</table>
  		</form>
  		<button type="button" class="btn btn-info btn-md" onclick="saveSmsEdit()">Save</button>
  		<button type="button" class="btn btn-info btn-md" onclick="clearSetting()">Clear</button>
  		<br><br>
  	</div>
</body>
</html>
