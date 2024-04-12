<!DOCTYPE HTML>
<%@page contentType="text/html; charset=UTF-8" %>
<html lang="en">

<head>
	<script>
		$(document).ready(function(){
			console.clear();
			$("#FindManager_RqRs").hide();
			$("#FindManager_edit").hide();
			
			$.ajax({
				url : "/ServiceCenterWeb/FindManager?process=defualt",
				type : "get",
				success : function(data){
					console.log(data);
					var obj = $.parseJSON(data);
					$("#urlWebService").val(obj.urlWebService);
					
					$("#urlWebServiceEdit").val(obj.urlWebService);
					$("#customerIdEdit").val(obj.customerId);
					$("#customerTypeEdit").val(obj.customerType);
					$("#thTitleNameEdit").val(obj.thaiTitleName);
					$("#thNameEdit").val(obj.thaiName);
					$("#thSurnameEdit").val(obj.thaiSurname);
					$("#engTitleNameEdit").val(obj.englishTitleName);
					$("#engNameEdit").val(obj.englishName);
					$("#engSurnameEdit").val(obj.englishSurname);
					$("#scrAstCodeEdit").val(obj.soucreAssetCode);
					$("#scrAstOthDescEdit").val(obj.soucreAssetOtherDescription);
					$("#policalPosDescEdit").val(obj.policalPostionDescription);
					$("#astValAmtEdit").val(obj.assetValueAmount);
					$("#riskReaCodeEdit").val(obj.riskReasonCode);
					$("#astValCodeEdit").val(obj.assetValueCode);
					$("#astValDescEdit").val(obj.assetValueDescription);
					$("#riskLvCodeEdit").val(obj.riskLevelCode);
				}
			});
			
		});
		
		function FindManagerShow(){
			$("#FindManager_input").show();
			$("#FindManager_output").show();
			$("#FindManager_edit").hide();
		}
		
		function FindManagerEditShow(){
			$("#FindManager_input").hide();
			$("#FindManager_output").hide();
			$("#FindManager_edit").show();
		}
		
		function submitFindManager(){
			$.ajax({
				url : "/ServiceCenterWeb/FindManager?process=request",
				type : "get",
				data : $("#FindManagerForm").serialize(),
				success : function(data){
					var obj = $.parseJSON(data);
					console.log(obj);
					$("#customerIdOutput").val(obj.customerId);
					$("#customerTypeOutput").val(obj.customerType);
					$("#thTitleName").val(obj.thaiTitleName);
					$("#thName").val(obj.thaiName);
					$("#thSurname").val(obj.thaiSurname);
					$("#engTitleName").val(obj.englishTitleName);
					$("#engName").val(obj.englishName);
					$("#engSurname").val(obj.englishSurname);
					$("#scrAstCode").val(obj.soucreAssetCode);
					$("#scrAstOthDesc").val(obj.soucreAssetOtherDescription);
					$("#policalPosDesc").val(obj.policalPostionDescription);
					$("#astValAmt").val(obj.assetValueAmount);
					$("#riskReaCode").val(obj.riskReasonCode);
					$("#astValCode").val(obj.assetValueCode);
					$("#astValDesc").val(obj.assetValueDescription);
					$("#riskLvCode").val(obj.riskLevelCode);
					$("textarea[name=requestJson]").val(obj.jsonRq);
  					$("textarea[name=responseJson]").val(obj.jsonRs);
  					
				}
			});
		}
		
		function clearInput(){
			$("#urlWebService").val("");
			$("#customerTypeInput").val("");
			$("#customerIdInput").val("");
		}
		
		function saveFindManagerEdit(){
			$.ajax({
				url : "/ServiceCenterWeb/FindManager?process=save",
				type : "post",
				data : $("#FindManagerEditForm").serialize(),
				beforeSend : console.log($("#FindManagerEditForm").serialize()),
				success : function(data){
// 					alert("save data");
				}
			});
		}
		
		function clearSetting(){
			$("#urlWebServiceEdit").val("");
			$("#customerIdEdit").val("");
			$("#customerTypeEdit").val("");
			$("#thTitleNameEdit").val("");
			$("#thNameEdit").val("");
			$("#thSurnameEdit").val("");
			$("#engTitleNameEdit").val("");
			$("#engNameEdit").val("");
			$("#engSurnameEdit").val("");
			$("#scrAstCodeEdit").val("");
			$("#scrAstOthDescEdit").val("");
			$("#policalPosDescEdit").val("");
			$("#astValAmtEdit").val("");
			$("#riskReaCodeEdit").val("");
			$("#astValCodeEdit").val("");
			$("#astValDescEdit").val("");
			$("#riskLvCodeEdit").val("");
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
  		<li class="active"><a data-toggle="tab" onclick="FindManagerShow();">Send FindManager</a></li>
		<li><a data-toggle="tab" onclick="FindManagerEditShow();">Edit Props.</a></li>
  	</ul>
  	<br>
  	<div id="FindManager_input">
  		<form class="well form-inline" id="FindManagerForm">
  			<Strong>Input</Strong>
  			<table>
  				<tr>
	  				<td>URL Web Service</td>
      				<td>
      					<input type="text" class="form-control input-sm" id="urlWebService" name="urlWebService" size="75">
      				</td>
      			</tr>
  				<tr>
	  				<td>Customer Type</td>
      				<td>
      					<input type="text" class="form-control input-sm" id="customerTypeInput" name="customerTypeInput" size="60">
      				</td>
      			</tr>
      			<tr>
	      			<td>Customer Id</td>
      				<td>
	      				<input type="text" class="form-control input-sm" id="customerIdInput" name="customerIdInput" size="60">
      				</td>
      			</tr>
      		</table>
    	</form>
  		<button type="button" class="btn btn-info btn-md" onclick="submitFindManager()">Request Service</button>
  		<button type="button" class="btn btn-info btn-md" onclick="clearInput()">Clear</button>
  		<button type="button" class="btn btn-info btn-md" onclick="jsonFormat()">Service Data</button>
  		<br><br>
  	</div>
  	<div class="well" id="FindManager_output">Output
  		<table>
  			<tr>
  				<td>Customer Id</td>
  				<td><input type="text" class="form-control input-sm" id="customerIdOutput" size="60" readonly></td>
  			</tr>
  			<tr>
  				<td>Customer Type</td>
  				<td><input type="text" class="form-control input-sm" id="customerTypeOutput" size="60" readonly></td>
  			</tr>
  			<tr>
  				<td>Thai Title name</td>
  				<td><input type="text" class="form-control input-sm" id="thTitleName" size="60" readonly></td>
  			</tr>
  			<tr>
  				<td>Thai Name</td>
  				<td><input type="text" class="form-control input-sm" id="thName" size="60" readonly></td>
  			</tr>
  			<tr>
  				<td>Thai Surname</td>
  				<td><input type="text" class="form-control input-sm" id="thSurname" size="60" readonly></td>
  			</tr>
  			<tr>
  				<td>English Title Name</td>
  				<td><input type="text" class="form-control input-sm" id="engTitleName" size="60" readonly></td>
  			</tr>
  			<tr>
  				<td>English Name</td>
  				<td><input type="text" class="form-control input-sm" id="engName" size="60" readonly></td>
  			</tr>
  			<tr>
  				<td>English Surname</td>
  				<td><input type="text" class="form-control input-sm" id="engSurname" size="60" readonly></td>
  			</tr>
  			<tr>
  				<td>Soucre Asset Code</td>
  				<td><input type="text" class="form-control input-sm" id="scrAstCode" size="60" readonly></td>
  			</tr>
  			<tr>
  				<td>Soucre Asset Other Description</td>
  				<td><input type="text" class="form-control input-sm" id="scrAstOthDesc" size="60" readonly></td>
  			</tr>
  			<tr>
  				<td>Polical Position Description</td>
  				<td><input type="text" class="form-control input-sm" id="policalPosDesc" size="60" readonly></td>
  			</tr>
  			<tr>
  				<td>Asset Value Amount</td>
  				<td><input type="text" class="form-control input-sm" id="astValAmt" size="60" readonly></td>
  			</tr>
  			<tr>
  				<td>Risk Reason Code</td>
  				<td><input type="text" class="form-control input-sm" id="riskReaCode" size="60" readonly></td>
  			</tr>
  			<tr>
  				<td>Asset Value Code</td>
  				<td><input type="text" class="form-control input-sm" id="astValCode" size="60" readonly></td>
  			</tr>
  			<tr>
  				<td>Asset Value Description</td>
  				<td><input type="text" class="form-control input-sm" id="astValDesc" size="60" readonly></td>
  			</tr>
  			<tr>
  				<td>Risk Level Code</td>
  				<td><input type="text" class="form-control input-sm" id="riskLvCode" size="60" readonly></td>
  			</tr>
  		</table>
  	</div>
  	<div class="well" id="FindManager_RqRs">
 			<Strong>Request/Response JSON Format</Strong><br>
 			Request :
		<textarea class="form-control" name="requestJson" rows="5" readonly></textarea>
		Response :
		<textarea class="form-control" name="responseJson" rows="5" readonly></textarea>
	</div>
  	<div id="FindManager_edit">
  		<form class="well form-inline" id="FindManagerEditForm">
  			<Strong>Setting</Strong>
  			<table>
  				<tr>
  					<td>URL Web Service </td>
  					<td><input type="text" class="form-control input-sm" name="urlWebServiceEdit" id="urlWebServiceEdit" size="75"></td>
  				</tr>
	  			<tr>
	  				<td>Customer Id</td>
	  				<td><input type="text" class="form-control input-sm" name="customerIdEdit" id="customerIdEdit" size="60"></td>
	  			</tr>
	  			<tr>
	  				<td>Customer Type</td>
	  				<td><input type="text" class="form-control input-sm" name="customerTypeEdit" id="customerTypeEdit" size="60"></td>
	  			</tr>
	  			<tr>
	  				<td>Thai Title name</td>
	  				<td><input type="text" class="form-control input-sm" name="thTitleNameEdit" id="thTitleNameEdit" size="60"></td>
	  			</tr>
	  			<tr>
	  				<td>Thai Name</td>
	  				<td><input type="text" class="form-control input-sm" name="thNameEdit" id="thNameEdit" size="60"></td>
	  			</tr>
	  			<tr>
	  				<td>Thai Surname</td>
	  				<td><input type="text" class="form-control input-sm" name="thSurnameEdit" id="thSurnameEdit" size="60"></td>
	  			</tr>
	  			<tr>
	  				<td>English Title Name</td>
	  				<td><input type="text" class="form-control input-sm" name="engTitleNameEdit" id="engTitleNameEdit" size="60"></td>
	  			</tr>
	  			<tr>
	  				<td>English Name</td>
	  				<td><input type="text" class="form-control input-sm" name="engNameEdit" id="engNameEdit" size="60"></td>
	  			</tr>
	  			<tr>
	  				<td>English Surname</td>
	  				<td><input type="text" class="form-control input-sm" name="engSurnameEdit" id="engSurnameEdit" size="60"></td>
	  			</tr>
	  			<tr>
	  				<td>Soucre Asset Code</td>
	  				<td><input type="text" class="form-control input-sm" name="scrAstCodeEdit" id="scrAstCodeEdit" size="60"></td>
	  			</tr>
	  			<tr>
	  				<td>Soucre Asset Other Description</td>
	  				<td><input type="text" class="form-control input-sm" name="scrAstOthDescEdit" id="scrAstOthDescEdit" size="60"></td>
	  			</tr>
	  			<tr>
	  				<td>Polical Position Description</td>
	  				<td><input type="text" class="form-control input-sm" name="policalPosDescEdit" id="policalPosDescEdit" size="60"></td>
	  			</tr>
	  			<tr>
	  				<td>Asset Value Amount</td>
	  				<td><input type="text" class="form-control input-sm" name="astValAmtEdit" id="astValAmtEdit" size="60"></td>
	  			</tr>
	  			<tr>
	  				<td>Risk Reason Code</td>
	  				<td><input type="text" class="form-control input-sm" name="riskReaCodeEdit" id="riskReaCodeEdit" size="60"></td>
	  			</tr>
	  			<tr>
	  				<td>Asset Value Code</td>
	  				<td><input type="text" class="form-control input-sm" name="astValCodeEdit" id="astValCodeEdit" size="60"></td>
	  			</tr>
	  			<tr>
	  				<td>Asset Value Description</td>
	  				<td><input type="text" class="form-control input-sm" name="astValDescEdit" id="astValDescEdit" size="60"></td>
	  			</tr>
	  			<tr>
	  				<td>Risk Level Code</td>
	  				<td><input type="text" class="form-control input-sm" name="riskLvCodeEdit" id="riskLvCodeEdit" size="60"></td>
	  			</tr>
  			</table>
  		</form>
  		<button type="button" class="btn btn-info btn-md" onclick="saveFindManagerEdit()">Save</button>
  		<button type="button" class="btn btn-info btn-md" onclick="clearSetting()">Clear</button>
  		<br><br>
  	</div>
</body>

</html>