<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" %>
<html lang="en">
<script>
	$(document).ready(function(){
		console.clear();
		$("#CBS1215I01_RqRs").hide();
		$("#CBS1215I01_edit").hide();
		$("#CBS1215I01_add").hide();
		
		$.ajax({
			url : "/ServiceCenterWeb/CBS1215I01?process=defualt",
			type : "GET",
			success : function(data){
				var obj = $.parseJSON(data);
				$("#urlWebService").val(obj.urlWebService);
				
				$("#urlWebServiceEdit").val(obj.urlWebService);
				$("#accountEdit").val(obj.account);
				$("#totalRententionEdit").val(obj.totalRententionAmount);
				$("#moreDataIndicatorEdit").val(obj.moreDataIndicator);
				$("#numberOfRecordEdit").val(obj.numberRecord);
				$("#totalRecordEdit").val(obj.totalRecord);
				
				$("#resultListEdit").empty();
				console.log(obj.retentionType);
				for(var i=0;i<obj.retentionType.length;i++){
					var htmlBuild = "<tr>";
					htmlBuild += "<td>"+obj.retentionType[i]+"</td>";
					htmlBuild += "<td>"+obj.retentionTypeDesc[i]+"</td>";
					htmlBuild += "<td><a onclick=rententionViewEdit('"+(i+1)+"')>Edit</td>";
					htmlBuild += "<td><a onclick=deleteRentention('"+(i+1)+"')>Delete</td>";
					htmlBuild += "</tr>";
					$("#resultListEdit").append(htmlBuild);
				}
			}
		});
	});
	
	function CBS1215I01Show(){
		$("#CBS1215I01_input").show();
		$("#CBS1215I01_output").show();
		$("#CBS1215I01_edit").hide();
		$("#CBS1215I01_add").hide();
	}
	
	function CBS1215I01EditShow(){
		$("#CBS1215I01_input").hide();
		$("#CBS1215I01_output").hide();
		$("#CBS1215I01_edit").show();
		$("#CBS1215I01_add").show();
	}
	
  	function submitCBS1215I01(){
  		$.ajax({
  			url : "/ServiceCenterWeb/CBS1215I01?process=request",
  			type : "GET",
  			data : $("#CBS1215I01Form").serialize(),
  			beforeSend : console.log($("#CBS1215I01Form").serialize()),
  			success : function(data){
  				var obj = $.parseJSON(data);
  				console.log(obj);
  				$("#accountOutput").val(obj.account);
  				$("#totalRententionOutput").val(obj.totalRetentionAmount);
  				$("#moreDataIndicatorOutput").val(obj.moreDataIndicator);
  				$("#numberOfRecordOutput").val(obj.retentionInfomantionList.length);
  				$("#totalRecordOutput").val(obj.totalRecord);
  				
				$("#resultList").empty();
				for(var i=0;i<obj.retentionInfomantionList.length;i++){
					var typeDescription = encodeURI(obj.retentionInfomantionList[i].retentionTypeDesc);
					console.log(typeDescription);
					var htmlBuild = "<tr>";
					htmlBuild += "<td>"+obj.retentionInfomantionList[i].retentionType+"</td>";
					htmlBuild += "<td>"+obj.retentionInfomantionList[i].retentionTypeDesc+"</td>";
					htmlBuild += "<td><a onclick=rententionView(\""+obj.retentionInfomantionList[i].retentionType+"\",\""+typeDescription+"\")>View</a></td>";
					htmlBuild += "</tr>";
					$("#resultList").append(htmlBuild);
				}
				$("textarea[name=requestJson]").val(obj.jsonRq);
				$("textarea[name=responseJson]").val(obj.jsonRs);
  			}
  		});
  	}
  	
  	function rententionView(retentionType,retentionTypeDesc){
// 		$('#dialog').html('');
// 		$('#dialog').attr('title','Rentention Details');
// 		$('#dialog').dialog({modal: true, width: 1050, draggable: true, resizable: true});
// 		$.ajax({
// 			url : "/ServiceCenterWeb/jsp/retentionPopup.jsp",
// 			type : 'GET',
// 			success : function(data) {
// 				$("#retentionType_hidden").val(retentionType);
// 				$("#retentionTypeDescription_hidden").val(retentionTypeDesc);
// 				$('#dialog').html(data);
// 			}
// 		});
		$("#retentionType_hidden").val(retentionType);
		$("#retentionTypeDescription_hidden").val(retentionTypeDesc);
		var options = {
	        url: "/ServiceCenterWeb/jsp/retentionPopup.jsp",
	        title:'Rentention Details',
	        size: eModal.size.lg
	    };
		eModal.ajax(options);
  	}
  	
  	function rententionViewEdit(row){
// 		$('#dialog').html('');
// 		$('#dialog').attr('title','Rentention Details');
// 		$('#dialog').dialog({modal: true, width: 1050, draggable: true, resizable: true});
// 		$.ajax({
// 			url : "/ServiceCenterWeb/jsp/retentionPopup.jsp",
// 			type : 'GET',
// 			success : function(data) {
// 				$("#rowEdit").val(row);
// 				$('#dialog').html(data);
// 			}
// 		});
		$("#rowEdit").val(row);
		var options = {
	        url: "/ServiceCenterWeb/jsp/retentionPopup.jsp",
	        title:'Edit Rentention',
	        size: eModal.size.lg,
	          buttons: [
	            {text: 'Save', style: 'info', click: editRetention},
	            {text: 'Close', style: 'danger', close: true}
	        ]
	    };
		eModal.ajax(options);
  	}
//   	function editRetention(){
//   	}
  	function saveCBS1215I01Edit(){
  		$.ajax({
  			url : "/ServiceCenterWeb/CBS1215I01?process=save",
  			type : "POST",
  			beforeSend : console.log($("#CBS1215I01EditForm").serialize()),
  			data : $("#CBS1215I01EditForm").serialize(),
  			success : function(data){
//   				alert("save data");
  			}
  		});
  	}
  	
  	function clearInput(){
  		$("#urlWebService").val("");
		$("#account").val("");
		$("#retentionSituationCode").val("");
		$("#retentionType").val("");
		$("#subAccountNumber").val("");
		$("#maxRow").val("");
		$("#startIndex").val("");
  	}
  	
  	function clearSetting(){
  		$("#urlWebServiceEdit").val("");
		$("#accountEdit").val("");
		$("#moreDataIndicatorEdit").val("");
  	}
  	
  	function addRetentionPopup(){
// 	  	$("#dialog").empty();
// 	  	$("#dialog").attr("title","Add Rentention");
// 	  	$("#dialog").dialog({ modal : true , width : 1050 , draggable : true , resizable : true});
 
// 		$.ajax({
// 			url : "/ServiceCenterWeb/jsp/retentionPopup.jsp",
// 			type : "get",
// 			success : function(data){
// 				$("#dialog").html(data);
// 			}
// 		});
		var options = {
	        url: "/ServiceCenterWeb/jsp/retentionPopup.jsp",
	        title:'Add Rentention',
	        size: eModal.size.lg,
	          buttons: [
	            {text: 'Add', style: 'info', click: addRetention},
	            {text: 'Close', style: 'danger', close: true}
	        ]
	    };
		eModal.ajax(options);
  	}
  		function addRetention(){
		$.ajax({
			url : "/ServiceCenterWeb/CBS1215I01?process=addDetail",
			type : "post",
			data : $("#retentionDetailForm").serialize(),
			beforeSend : console.log($("#retentionDetailForm").serialize()),
			success : function(data){
				var addRow = $("#resultListEdit > tr").length+1;
				var htmlBuild = "<tr>";
				htmlBuild += "<td>"+$("#reteType").val()+"</td>";
				htmlBuild += "<td>"+$("#reteTypeDesc").val()+"</td>";
				htmlBuild += "<td><a onclick=rententionViewEdit('"+addRow+"')>Edit</a></td>";
				htmlBuild += "<td><a onclick=deleteRentention('"+addRow+"')>Delete</a></td>";
				htmlBuild += "</tr>";
				$("#resultListEdit").append(htmlBuild);
// 				$("#dialog").dialog('close');
eModal.close();
			}
		});
	}
	
	function editRetention(){
		$.ajax({
			url : "/ServiceCenterWeb/CBS1215I01?process=update&row="+row,
			type : "post",
			data : $("#retentionDetailForm").serialize(),
			beforeSend : console.log($("#retentionDetailForm").serialize()),
			success : function(data){
				$("#resultListEdit > tr:nth-child("+row+") > td:nth-child(1)").html($("#reteType").val());
				$("#resultListEdit > tr:nth-child("+row+") > td:nth-child(2)").html($("#reteTypeDesc").val());
// 				$("#dialog").dialog('close');
eModal.close();
			}
		});
	}
  	function deleteRentention(row){
  		$.ajax({
			url : "/ServiceCenterWeb/CBS1215I01?process=delete&row="+row,
			type : "post",
			success : function(data) {
				$("#resultListEdit > tr:nth-child("+row+")").empty();
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
  		<li class="active"><a data-toggle="tab" onclick="CBS1215I01Show();">Send CBS1215I01</a></li>
		<li><a data-toggle="tab" onclick="CBS1215I01EditShow();">Edit Props.</a></li>
  	</ul>
  	<br>
  	<div id="CBS1215I01_input">
  		<form class="well form-inline" id="CBS1215I01Form">
  			<Strong>Input</Strong>
  			<table>
  				<tr>
	  				<td>URL Web Service</td>
      				<td>
      					<input type="text" class="form-control input-sm" id="urlWebService"name="urlWebService" size="75">
      				</td>
      			</tr>
  				<tr>
	  				<td>Account</td>
      				<td>
      					<input type="text" class="form-control input-sm" id="account"name="account" size="60">
      				</td>
      			</tr>
      			<tr>
	      			<td>Retention Situation Code</td>
      				<td>
	      				<input type="text" class="form-control input-sm" id="retentionSituationCode"name="retentionSituationCode" size="60">
      				</td>
      			</tr>
      			<tr>
	      			<td>Retention Type</td>
      				<td>
	      				<input type="text" class="form-control input-sm" id="retentionType"name="retentionType" size="60">
      				</td>
      			</tr>
      			<tr>
		      		<td>Sub Account Number</td>
      				<td>
		      			<input type="text" class="form-control input-sm" id="subAccountNumber"name="subAccountNumber" size="60">
    	  			</td>
      			</tr>
	      		<tr>	
	    	  		<td>Max Row</td>
    	  			<td>	
      					<input type="text" class="form-control input-sm" id="maxRow"name="maxRow" size="60">
      				</td>	
      			</tr>
      			<tr>
		      		<td>Start Index</td>
	      			<td>
	    	  			<input type="text" class="form-control input-sm" id="startIndex"name="startIndex" size="60">
      				</td>
      			</tr>
      		</table>
    	</form>
  		<button type="button" class="btn btn-info btn-md" onclick="submitCBS1215I01()">Request Service</button>
  		<button type="button" class="btn btn-info btn-md" onclick="clearInput()">Clear</button>
  		<button type="button" class="btn btn-info btn-md" onclick="jsonFormat()">Service Data</button>
  		<br><br>
  	</div>
  	<div class="well" id="CBS1215I01_output">
  		<Strong>Output</Strong>
  		<table>
  			<tr>
  				<td>Account</td>
  				<td><input type="text" class="form-control input-sm" id="accountOutput" size="60" readonly readonly></td>
  			</tr>
  			<tr>
  				<td>Total Rentention Amount</td>
  				<td><input type="text" class="form-control input-sm" id="totalRententionOutput" size="60" readonly></td>
  			</tr>
  			<tr>
  				<td>More Data Indicator</td>
  				<td><input type="text" class="form-control input-sm" id="moreDataIndicatorOutput" size="60" readonly></td>
  			</tr>
  			<tr>
  				<td>Number of Record</td>
  				<td><input type="text" class="form-control input-sm" id="numberOfRecordOutput" size="60" readonly></td>
  			</tr>
  			<tr>
  				<td>Total Record</td>
  				<td><input type="text" class="form-control input-sm" id="totalRecordOutput" size="60" readonly></td>
  			</tr>
  		</table>
  		<input type="hidden" id="retentionType_hidden">
  		<input type="hidden" id="retentionTypeDescription_hidden">
  		<table class="table table-striped">
    		<thead>
      			<tr>
        			<th>Rentention Type</th>
        			<th>Retention Type Description</th>
        			<th>View</th>
      			</tr>
    		</thead>
    		<tbody id="resultList">
      			<tr>
        			<td></td>
        			<td></td>
        			<td></td>
      			</tr>
    		</tbody>
 		</table>
  	</div>
  	<div class="well" id="CBS1215I01_RqRs">
  		<Strong>Request/Response JSON Format</Strong><br>
  		Request :
		<textarea class="form-control" name="requestJson" rows="5" readonly></textarea>
		Response :
		<textarea class="form-control" name="responseJson" rows="5" readonly></textarea>
  	</div>
  	<div id="CBS1215I01_edit"> 
  		<form class="well form-inline" id="CBS1215I01EditForm">
  			<Strong>Setting</Strong>
  			<table>
  				<tr>
  					<td>URL Webservice </td>
  					<td><input type="text" class="form-control input-sm" name="urlWebServiceEdit" id="urlWebServiceEdit" size="75"></td>
  				</tr>
  				<tr>
	  				<td>Account</td>
	  				<td><input type="text" class="form-control input-sm" name="accountEdit" id="accountEdit" size="60"></td>
	  			</tr>
	  			<tr>
	  				<td>More Data Indicator</td>
	  				<td><input type="text" class="form-control input-sm" name="moreDataIndicatorEdit" id="moreDataIndicatorEdit" size="60"></td>
	  			</tr>
  			</table>
  		</form>
  		<button type="button" class="btn btn-info btn-md" onclick="saveCBS1215I01Edit()">Save</button>
  		<button type="button" class="btn btn-info btn-md" onclick="clearSetting()">Clear</button>
  		<br><br>
  	</div>
 	<div class="well" id="CBS1215I01_add">
 		<Strong>Add Rentention</Strong>
 		<table class="table table-striped">
 			<thead>
 				<tr>
 					<th>Rentention Type</th>
 					<th>Retention Type Description</th>
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
 				</tr>
 			</tbody>
 		</table>
 		<input type="hidden" id="rowEdit">
		<button type="button" class="btn btn-info btn-md" onclick="addRetentionPopup()">Add</button>
 	</div>
</body>
</html>
