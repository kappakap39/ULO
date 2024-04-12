<!DOCTYPE html>
<%@page contentType="text/html; chatset=UTF-8" %>
<html lang=en>
<head>
	<script>
		$(document).ready(function(){
			console.clear();
			$("#createKVIApplication_RqRs").hide();
			$("#createKVIApplication_setting").hide();
			
			$.ajax({
				url : "/ServiceCenterWeb/CreateKVIApp?process=defualt",
				type : "get",
				success : function(data){
					var obj = $.parseJSON(data);
					console.log(obj);
					$("input[name=url_input]").val(obj.urlWebService);
					$("input[name=url_setting]").val(obj.urlWebService);
				 	$("input[name=fId_setting]").val(obj.fId);
					$("input[name=tokenId_setting]").val(obj.tokenId); 
				}
			});
		});
		
		function goToInput(){
			$("#createKVIApplication_input").show();
			$("#createKVIApplication_output").show();
			$("#createKVIApplication_setting").hide();
		}
		
		function goToSetting(){
			$("#createKVIApplication_input").hide();
			$("#createKVIApplication_output").hide();
			$("#createKVIApplication_setting").show();
		}
		
		function requestService(){
			$.ajax({
				url : "/ServiceCenterWeb/CreateKVIApp?process=request",
				type : "get",
				data : $("#createKVIApplicationForm_input").serialize(),
				success : function(data){
					var obj = $.parseJSON(data);
					console.log(obj);
					$("#createKVIApplication_output > table > tbody > tr:nth-child(1) > td:nth-child(2) > input ").val(obj.fId);
					$("#createKVIApplication_output > table > tbody > tr:nth-child(2) > td:nth-child(2) > input ").val(obj.tokenId);
					$("textarea[name=requestJson]").val(obj.jsonRq);
  					$("textarea[name=responseJson]").val(obj.jsonRs);
				}
			});
		}
		
		function clearInput(){
			$("#createKVIApplicationForm_input").trigger("reset");
		}
		
		function editProperties(){
			$.ajax({
				url : "/ServiceCenterWeb/CreateKVIApp?process=edit",
				type : "post",
				data : $("#createKVIApplicationForm_setting").serialize(),
				success : function(data){
// 					alert("save data");
				}
			});
		}
		
		function clearSetting(){
			$("#createKVIApplicationForm_setting").trigger("reset");
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
  		<li class="active"><a data-toggle="tab" onclick="goToInput();">Send CreateKVI</a></li>
		<li><a data-toggle="tab" onclick="goToSetting();">Edit Props.</a></li>
  	</ul>
	<br>
	<div id="createKVIApplication_input">
		<form class="form-inline well" id="createKVIApplicationForm_input">
		<Strong>Input</Strong>
			<table>
				<tbody>
					<tr>
						<td>URL Web Service</td>
						<td colspan="3"><input type="text" class="form-control input-sm" size="100" name="url_input"></td>
					</tr>
					<tr>
						<td>FG App No</td>
						<td><input type="text" class="form-control input-sm" size="50" name="fgAppNo_input"></td>
						<td>FC Dept</td>
						<td><input type="text" class="form-control input-sm" size="50" name="fcDept_input"></td>
					</tr>
					<tr>
						<td>FC Input Id</td>
						<td><input type="text" class="form-control input-sm" size="50" name="fcInputId_input"></td>
						<td>FG Requestor</td>
						<td><input type="text" class="form-control input-sm" size="50" name="fgRequestor_input"></td>
					</tr>
					<tr>
						<td>FG Requestor L</td>
						<td><input type="text" class="form-control input-sm" size="50" name="fgRequestorL_input"></td>
						<td>FG Type</td>
						<td><input type="text" class="form-control input-sm" size="50" name="fgType_input"></td>
					</tr>
					<tr>
						<td>FG Id</td>
						<td><input type="text" class="form-control input-sm" size="50" name="fgId_input"></td>
						<td>FG Cis No</td>
						<td><input type="text" class="form-control input-sm" size="50" name="fgCisNo_input"></td>
					</tr>
					<tr>
						<td>FG Requestor1</td>
						<td><input type="text" class="form-control input-sm" size="50" name="fgRequestor1_input"></td>
						<td>FG Requestor L1</td>
						<td><input type="text" class="form-control input-sm" size="50" name="fgRequestorL1_input"></td>
					</tr>
					<tr>
						<td>FG Type1</td>
						<td><input type="text" class="form-control input-sm" size="50" name="fgType1_input"></td>
						<td>FG Id1</td>
						<td><input type="text" class="form-control input-sm" size="50" name="fgId1_input"></td>
					</tr>
					<tr>
						<td>FG Cis No1</td>
						<td><input type="text" class="form-control input-sm" size="50" name="fgCisNo1_input"></td>
						<td>FG Requestor2</td>
						<td><input type="text" class="form-control input-sm" size="50" name="fgRequestor2_input"></td>
					</tr>
					<tr>
						<td>FG Requestor L2</td>
						<td><input type="text" class="form-control input-sm" size="50" name="fgRequestorL2_input"></td>
						<td>FG Type2</td>
						<td><input type="text" class="form-control input-sm" size="50" name="fgType2_input"></td>
					</tr>
					<tr>
						<td>FG Id2</td>
						<td><input type="text" class="form-control input-sm" size="50" name="fgId2_input"></td>
						<td>FG Cis No2</td>
						<td><input type="text" class="form-control input-sm" size="50" name="fgCisNo2_input"></td>
					</tr>
					<tr>
						<td>FC Business</td>
						<td><input type="text" class="form-control input-sm" size="50" name="fcBusiness_input"></td>
						<td>FC Business Desc</td>
						<td><input type="text" class="form-control input-sm" size="50" name="fcBusinessDesc_input"></td>
					</tr>
				</tbody>
			</table>
		</form>
		<button type="button" class="btn btn-info" onclick="requestService()">Request Service</button>
		<button type="button" class="btn btn-info" onclick="clearInput()">Clear</button>
		<button type="button" class="btn btn-info btn-md" onclick="jsonFormat()">Service Data</button>
		<br><br>
	</div>
	<div class="well" id="createKVIApplication_output">
		<Strong>Output</Strong>
		<table>
			<tr>
				<td>F Id</td>
				<td><input type="text" class="form-control input-sm" size="50" readonly></td>
			</tr>
			<tr>
				<td>Token Id</td>
				<td><input type="text" class="form-control input-sm" size="50" readonly></td>
			</tr>
		</table>
	</div>
	<div class="well" id="createKVIApplication_RqRs">
  		<Strong>Request/Response JSON Format</Strong><br>
  		Request :
		<textarea class="form-control" name="requestJson" rows="5" readonly></textarea>
		Response :
		<textarea class="form-control" name="responseJson" rows="5" readonly></textarea>
  	</div>
	<div id="createKVIApplication_setting">
		<form class="well form-inline" id="createKVIApplicationForm_setting">
		<Strong>setting</Strong>
			<table>
				<tr>
					<td>URL Web Service</td>
					<td><input type="text" class="form-control input-sm" size="70" name="url_setting"></td>
				</tr>
				<tr>
					<td>F Id</td>
					<td><input type="text" class="form-control input-sm" size="50" name="fId_setting"></td>
				</tr>
				<tr>
					<td>Token Id</td>
					<td><input type="text" class="form-control input-sm" size="50" name="tokenId_setting"></td>
				</tr>
			</table>
		</form>
		<button type="button" class="btn btn-info" onclick="editProperties()">Edit</button>
		<button type="button" class="btn btn-info" onclick="clearSetting()">Clear</button>
	</div>
	
</body>
</html>
