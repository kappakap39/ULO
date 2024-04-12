<!DOCTYPE html>
<%@page contentType="text/html; charset=UTF-8" %>
<html>
<head>
	<script>
		$(document).ready(function(){
			console.clear();
			$("#CIS1037A01_RqRs").hide();
			$("#CIS1037A01_setting").hide();
			$("#CIS1037A01_add").hide();
			$("#edit_button").hide();
			$("#left_bar").height($("#showTab").height()+40);
			$.ajax({
				url : "/ServiceCenterWeb/CIS1037A01?process=defualt",
				type : "get",
				success : function(data){
					var obj = $.parseJSON(data);
					console.log(obj);
					$("input[name=url_input]").val(obj.urlWebService);
					$("input[name=url_setting]").val(obj.urlWebService);
					
					$("input[name=contactAddrId_setting]").val(obj.addressId);
					$("input[name=phoneId_setting]").val(obj.phoneId);
					$("input[name=eAddrId_setting]").val(obj.eAddressId);
					$("input[name=totalNumReg_setting]").val(obj.regulationAmout);
					$("input[name=riskCode_setting]").val(obj.riskCode);
					$("input[name=cusId_setting]").val(obj.customerId);
					$("input[name=offiAddrId_setting]").val(obj.officialAddressId);
					
					for(var i=0;i<obj.regulationSubType.length;i++){
						var htmlBuild = "<tr>";
						htmlBuild += "<td>"+obj.regulationSubType[i]+"</td>";	
						htmlBuild += "<td>"+obj.regulationType[i]+"</td>";	
						htmlBuild += "<td><a onclick=edit('"+(i+1)+"')>Edit</a></td>";
						htmlBuild += "<td><a onclick=delete('"+(i+1)+"')>Delete</a></td>";
						$("#list_edit").append(htmlBuild);
					}
				}
			});
			

		});

		function CIS1037A01_input(){
			$("#CIS1037A01_input").show();
			$("#CIS1037A01_output").show();
			$("#CIS1037A01_setting").hide();
			$("#CIS1037A01_add").hide();
		}
		function CIS1037A01_setting(){
			$("#CIS1037A01_input").hide();
			$("#CIS1037A01_output").hide();
			$("#CIS1037A01_setting").show();
			$("#CIS1037A01_add").show();
		}
		
		function edit(row){
			$("#add_button").hide();
			$("#edit_button").show();
			$("input[name=regSubType_add]").val($("#list_edit > tr:nth-child("+row+") > td:nth-child(1)").text());
			$("input[name=regType_add]").val($("#list_edit > tr:nth-child("+row+") > td:nth-child(2)").text());
			$("input[name=row_hidden]").val(row);
		}
		
		function edit_properties(){
			var row = $("input[name=row_hidden]").val();
			$.ajax({
				url : "/ServiceCenterWeb/CIS1037A01?process=edit",
				type : "post",
				data : $("#CIS1037A01Form_add").serialize(),
				beforeSend : console.log($("#CIS1037A01Form_add").serialize()),
				success : function(data) {
					$("#list_edit > tr:nth-child("+row+") > td:nth-child(1)").text($("input[name=regSubType_add]").val());
					$("#list_edit > tr:nth-child("+row+") > td:nth-child(2)").text($("input[name=regType_add]").val());
					$("input[name=regSubType_add]").val("");
					$("input[name=regType_add]").val("");
					$("input[name=row_hidden]").val("");
				}
			});
		}
		
		function delete_properties(row){
			$.ajax({
				url : "/ServiceCenterWeb/CIS1037A01?process=delete&row="+row,
				type : "post",
				success : function(data){
// 					alert("delete success");
					$("#list_edit > tr:nth-child("+row+")").empty();
				}
			});
		}
		
		function add_properties(){
			var row = $('#list_edit > tr').length+1;
			$.ajax({
				url : "/ServiceCenterWeb/CIS1037A01?process=add",
				type : "post",
				data : $("#CIS1037A01Form_add").serialize(),
				success : function(data){
					var htmlBuild = "<tr>";
					htmlBuild += "<td>"+$("input[name=regSubType_add]").val()+"</td>";
					htmlBuild += "<td>"+$("input[name=regType_add]").val()+"</td>";
					htmlBuild += "<td><a onclick=edit('"+row+"')>Edit</a></td>";
					htmlBuild += "<td><a onclick=delete_properties('"+row+"')>Delete</a></td>";
					htmlBuild += "</tr>";
					$("#list_edit").append(htmlBuild);
					$("input[name=regSubType_add]").val("");
					$("input[name=regType_add]").val("");
				}
			});
		}
		
		function save_setting(){
			$.ajax({
				url : "/ServiceCenterWeb/CIS1037A01?process=save",
				type : "post",
				data : $("#CIS1037A01Form_setting").serialize(),
				success : function(data){
// 					alert("save data");
				}				
			});
		}
		
		function clear_setting(){
			$("#CIS1037A01Form_setting").trigger("reset");
		}
		
		function clear_input(){
			$(".CIS1037A01Form_input").trigger("reset");
		}
		
		function requestService(){
			$.ajax({
				url : "/ServiceCenterWeb/CIS1037A01?process=request",
				type : "get",
				data : $(".CIS1037A01Form_input").serialize(),
				success : function(data){
					var obj = $.parseJSON(data);
					console.log(obj);
					$("input[name=contactAddrId_output]").val(obj.addressId);	
					$("input[name=phoneId_output]").val(obj.phoneId);
					$("input[name=eAddrId_output]").val(obj.eAddressId);
					$("input[name=totalNumReg_output]").val(obj.regulationAmout);		
					$("input[name=riskCode_output]").val(obj.riskCode);
					$("input[name=cusId_output]").val(obj.customerId);
					$("input[name=offiAddrId_output]").val(obj.officialAddressId);
					$("#list_output").empty();
					for(var i=0;i<parseInt(obj.regulationAmout);i++){
						var htmlBuild = "<tr>";
						htmlBuild += "<td>"+obj.customerRegulationInformation[i].regulationSubType+"</td>";
						htmlBuild += "<td>"+obj.customerRegulationInformation[i].regulationType+"</td>";
						htmlBuild += "<tr></tr>";
						$("#list_output").append(htmlBuild);
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
  		<li class="active"><a data-toggle="tab" onclick="CIS1037A01_input();">Send CIS1037A01</a></li>
		<li><a data-toggle="tab" onclick="CIS1037A01_setting();">Edit Props.</a></li>
  	</ul>
	<br>
	<div id="CIS1037A01_input">
		<form class="well form-inline CIS1037A01Form_input" ><strong>Input</strong>
			<table>
				<tr>
					<td>URL Web Service </td>
					<td><input type="text" class="form-control input-sm" size="100" name="url_input"></td>
				</tr>
				<tr>
					<td>Branch No</td>
					<td><input type="text" class="form-control input-sm" size="50" name="branchNo_input"></td>
				</tr>
				<tr>
					<td>Confirm Flag</td>
					<td><input type="text" class="form-control input-sm" size="50" name="confirmFlag_input"></td>
				</tr>
				<tr>
					<td>Hub No</td>
					<td><input type="text" class="form-control input-sm" size="50" name="hubNo_input"></td>
				</tr>
				<tr>
					<td>User Id</td>
					<td><input type="text" class="form-control input-sm" size="50" name="userId_input"></td>
				</tr>
			</table>
		</form>
		<div>
			<ul class="nav nav-tabs" >
			    <li class="active"><a data-toggle="tab" href="#CISIndivCust">CISIndivCust</a></li>
			    <li><a data-toggle="tab" href="#contactAddrObj">ContactAddrObj</a></li>
			    <li><a data-toggle="tab" href="#contactVect">ContactVect</a></li>
			    <li><a data-toggle="tab" href="#KYCObj">KYCObj</a></li>
			    <li><a data-toggle="tab" href="#officialAddress">OfficialAddress</a></li>
  			</ul>
  			
 					<br>
 				<form class="form-inline CIS1037A01Form_input well">
 				
 					<div class="tab-content">
  					<div class="tab-pane fade in active" id="CISIndivCust">
  						<Strong>CISIndivCust</Strong>
  						<table>
  							<tr>
  								<td>Address Flag</td>
  								<td><input type="text" class="form-control input-sm" size="40" name="addrFlag_input"></td>
  								<td>Asset Exclude Land Amount</td>
  								<td><input type="text" class="form-control input-sm" size="40" name="assetExcludeLand_input"></td>
  							</tr>
  							<tr>
								<td>Birth Date</td>
								<td><input type="text" class="form-control input-sm" size="40" name="birthDate_input"></td>
								<td>Children Amount</td>
								<td><input type="text" class="form-control input-sm" size="40" name="childrenAmount_input"></td>
							</tr>
							<tr>
								<td>Consend Flag</td>
								<td><input type="text" class="form-control input-sm" size="40" name="consendFlag_input"></td>
								<td>Consent Source</td>
								<td><input type="text" class="form-control input-sm" size="40" name="consentSource_input"></td>
							</tr>
							<tr>
								<td>Multiple Contact Channel</td>
								<td><input type="text" class="form-control input-sm" size="40" name="multipleContactChannel_input"></td>
								<td>Customer Segment</td>
								<td><input type="text" class="form-control input-sm" size="40" name="customerSegment_input"></td>
							</tr>
							<tr>
								<td>Customer Type Code</td>
								<td><input type="text" class="form-control input-sm" size="40" name="customerType_input"></td>
								<td>Deposit Flag</td>
								<td><input type="text" class="form-control input-sm" size="40" name="depositCode_input"></td>
							</tr>
							<tr>
								<td>Document Id</td>
								<td><input type="text" class="form-control input-sm" size="40" name="documentId_input"></td>
								<td>Document Type</td>
								<td><input type="text" class="form-control input-sm" size="40" name="documentType_input"></td>
							</tr>
							<tr>
								<td>Degree</td>
								<td><input type="text" class="form-control input-sm" size="40" name="degree_input"></td>
								<td>Number of Employee</td>
								<td><input type="text" class="form-control input-sm" size="40" name="employeeAmount_input"></td>
							</tr>
							<tr>
								<td>English Name</td>
								<td><input type="text" class="form-control input-sm" size="40" name="englishName_input"></td>
								<td>English Last Name</td>
								<td><input type="text" class="form-control input-sm" size="40" name="englishLastName_input"></td>
							</tr>
							<tr>
								<td>English Middle Name</td>
								<td><input type="text" class="form-control input-sm" size="40" name="englishMiddleName_input"></td>
								<td>English Title Name</td>
								<td><input type="text" class="form-control input-sm" size="40" name="englishTitleName_input"></td>
							</tr>
							<tr>
								<td>Family Income Range</td>
								<td><input type="text" class="form-control input-sm" size="40" name="familyIncome_input"></td>
								<td>Front Review Flag</td>
								<td><input type="text" class="form-control input-sm" size="40" name="reviewFlag_input"></td>
							</tr>
							<tr>
								<td>Issued Card Description</td>
								<td><input type="text" class="form-control input-sm" size="40" name="cardDescription_input"></td>
								<td>Card Expiry Date(yyyy-MM-dd)</td>
								<td><input type="text" class="form-control input-sm" size="40" name="cardExpiryDate_input"></td>
							</tr>
							<tr>
								<td>Issued Date(yyyy-MM-dd)</td>
								<td><input type="text" class="form-control input-sm" size="40" name="issueDate_input"></td>
								<td>Issued By</td>
								<td><input type="text" class="form-control input-sm" size="40" name="issueBy_input"></td>
							</tr>
							<tr>
								<td>Salary</td>
								<td><input type="text" class="form-control input-sm" size="40" name="salary_input"></td>
								<td>Marital Status</td>
								<td><input type="text" class="form-control input-sm" size="40" name="maritalStatus_input"></td>
							</tr>
							<tr>
								<td>Menu Add Flag</td>
								<td><input type="text" class="form-control input-sm" size="40" name="menuAddFlag_input"></td>
								<td>Nationality</td>
								<td><input type="text" class="form-control input-sm" size="40" name="nationalityCode_input"></td> 
							</tr>
							<tr>
								<td>Book Number</td>
								<td><input type="text" class="form-control input-sm" size="40" name="changeBookNumber_input"></td>
								<td>Change Date</td>
								<td><input type="text" class="form-control input-sm" size="40" name="changeDate_input"></td>
							</tr>
							<tr>
								<td>Reason for Change</td>
								<td><input type="text" class="form-control input-sm" size="40" name="changeReasonCode_input"></td>
								<td>Sequence for Change</td>
								<td><input type="text" class="form-control input-sm" size="40" name="changeSequenceNumber_input"></td>
							</tr>
							<tr>
								<td>Customer Id</td>
								<td><input type="text" class="form-control input-sm" size="40" name="customerId_input"></td>
							</tr>
							<tr>
								<td>Contact Address Line1</td>
								<td><input type="text" class="form-control input-sm" size="40" name="contactAddressLine1_input"></td>
								<td>Contact Address Line2</td>
								<td><input type="text" class="form-control input-sm" size="40" name="contactAddressLine2_input"></td>
							</tr>
							<tr>
								<td>Official Address Line1</td>
								<td><input type="text" class="form-control input-sm" size="40" name="officialAddressLine1_input"></td>
								<td>Official Address Line2</td>
								<td><input type="text" class="form-control input-sm" size="40" name="officialAddressLine2_input"></td>
							</tr>
							<tr>
								<td>Occupation</td>
								<td><input type="text" class="form-control input-sm" size="40" name="occupation_input"></td>
							</tr>
							<tr>
								<td>Oldest Child Age</td>
								<td><input type="text" class="form-control input-sm" size="40" name="oldestChildAge_input"></td>
								<td>Job Position</td>
								<td><input type="text" class="form-control input-sm" size="40" name="positionCode_input"></td>
							</tr>
							<tr>
								<td>Profession Code</td>
								<td><input type="text" class="form-control input-sm" size="40" name="professionCode_input"></td>
								<td>Prospect Flag</td>
								<td><input type="text" class="form-control input-sm" size="40" name="prospectFlag_input"></td>
							</tr>
							<tr>
								<td>Race Code</td>
								<td><input type="text" class="form-control input-sm" size="40" name="raceCode_input"></td>
								<td>Religion</td>
								<td><input type="text" class="form-control input-sm" size="40" name="religionCode_input"></td>
							</tr>
							<tr>
								<td>Reverse Flag</td>
								<td><input type="text" class="form-control input-sm" size="40" name="reverseFlag_input"></td>
								<td>Gender</td>
								<td><input type="text" class="form-control input-sm" size="40" name="gender_input"></td>
							</tr>
							<tr>
								<td>Owner Branch Number</td>
								<td><input type="text" class="form-control input-sm" size="40" name="ownerBrNo_input"></td>
								<td>Tax Id</td>
								<td><input type="text" class="form-control input-sm" size="40" name="taxId_input"></td>
							</tr>
							<tr>
								<td>Business Type Code</td>
								<td><input type="text" class="form-control input-sm" size="40" name="businessType_input"></td>
								<td>Thai Name</td>
								<td><input type="text" class="form-control input-sm" size="40" name="thaiName_input"></td>
							</tr>
							<tr>
								<td>Thai Last Name</td>
								<td><input type="text" class="form-control input-sm" size="40" name="thaiLastName_input"></td>
								<td>Thai Middle Name</td>
								<td><input type="text" class="form-control input-sm" size="40" name="thaiMiddleName_input"></td>
							</tr>
							<tr>
								<td>Thai Title Name</td>
								<td><input type="text" class="form-control input-sm" size="40" name="thaiTitleName_input"></td>
								<td>Title Name Code</td>
								<td><input type="text" class="form-control input-sm" size="40" name="titleNameCode_input"></td>
							</tr>
							<tr>
								<td>Customer Type</td>
								<td><input type="text" class="form-control input-sm" size="40" name="customerTypeCode_input"></td>
								<td>Validate Flag</td>
								<td><input type="text" class="form-control input-sm" size="40" name="validateFlag_input"></td>
							</tr>
							<tr>
								<td>Vip Flag</td>
								<td><input type="text" class="form-control input-sm" size="40" name="vipFlag_input"></td>
							</tr>
							<tr>
								<td>Work Start Date</td>
								<td><input type="text" class="form-control input-sm" size="40" name="workStartDate_input"></td>
								<td>Youngest Child Age</td>
								<td><input type="text" class="form-control input-sm" size="40" name="youngestChildAge_input"></td>
							</tr>
							
  						</table>
  					</div>
  					<div class="tab-pane fade" id="contactAddrObj">
  						<Strong>Contact Address</Strong>
  						<table>
 								<tr>
 									<td>Amphur</td>
 									<td><input type="text" class="form-control input-sm" size="50" name="contactAddressAmphur_input"></td>
 								</tr>
 								<tr>
 									<td>Building</td>
 									<td><input type="text" class="form-control input-sm" size="50" name="contactAddressBuilding_input"></td>
 								</tr>
 								<tr>
 									<td>Country</td>
 									<td><input type="text" class="form-control input-sm" size="50" name="contactAddressCountry_input"></td>
 								</tr>
 								<tr>
 									<td>Floor</td>
 									<td><input type="text" class="form-control input-sm" size="50" name="contactAddressFloor_input"></td>
 								</tr>
 								<tr>
 									<td>Number</td>
 									<td><input type="text" class="form-control input-sm" size="50" name="contactAddressNumber_input"></td>
 								</tr>
 								<tr>
 									<td>Moo</td>
 									<td><input type="text" class="form-control input-sm" size="50" name="contactAddressMoo_input"></td>
 								</tr>
 								<tr>
 									<td>Name</td>
 									<td><input type="text" class="form-control input-sm" size="50" name="contactAddressName_input"></td>
 								</tr>
 								<tr>
									<td>Box No</td>
									<td><input type="text" class="form-control input-sm" size="50" name="contactAddressBoxNo_input"></td>
								</tr>
								<tr>
									<td>Post Code</td>
									<td><input type="text" class="form-control input-sm" size="50" name="contactAddressPostCode_input"></td>
								</tr>
								<tr>
									<td>Prefered Flag</td>
									<td><input type="text" class="form-control input-sm" size="50" name="contactAddressPreferedFlag_input"></td>
								</tr>
								<tr>
									<td>Province</td>
									<td><input type="text" class="form-control input-sm" size="50" name="contactAddressProvince_input"></td>
								</tr>
								<tr>
									<td>Road</td>
									<td><input type="text" class="form-control input-sm" size="50" name="contactAddressRoad_input"></td>
								</tr>
								<tr>
									<td>Room</td>
									<td><input type="text" class="form-control input-sm" size="50" name="contactAddressRoom_input"></td>
								</tr>
								<tr>
									<td>Soi</td>
									<td><input type="text" class="form-control input-sm" size="50" name="contactAddressSoi_input"></td>
								</tr>
								<tr>
									<td>Tumbol</td>
									<td><input type="text" class="form-control input-sm" size="50" name="contactAddressTumbol_input"></td>
								</tr>
								<tr>
									<td>Mooban</td>
									<td><input type="text" class="form-control input-sm" size="50" name="contactAddressMooban_input"></td>
								</tr>
  						</table>
  					</div>
  					<div class="tab-pane fade" id="contactVect">
  						<br>
  						<strong>Contact Vector1</strong>
  						<table>
  							<tr>
  								<td>Location</td>
  								<td><input type="text" class="form-control input-sm" size="50" name="locationCode_input"></td>
  							</tr>
  							<tr>
  								<td>Available Time</td>
  								<td><input type="text" class="form-control input-sm" size="50" name="availabilityTime_input"></td>
  							</tr>
  							<tr>
  								<td>Telephone Ext. Number</td>
  								<td><input type="text" class="form-control input-sm" size="50" name="phoneExtensionNumber_input"></td>
  							</tr>
  							<tr>
  								<td>Telephone Number</td>
  								<td><input type="text" class="form-control input-sm" size="50" name="phoneNumber_input"></td>
  							</tr>
  							<tr>
  								<td>Telephone Type</td>
  								<td><input type="text" class="form-control input-sm" size="50" name="typeCode_input"></td>
  							</tr>
  						</table>
  						<strong>Contact Vector2</strong>
  						<table>
  							<tr>
  								<td>E-Address Location</td>
  								<td><input type="text" class="form-control input-sm" size="50" name="locationCode_input"></td>
  							</tr>
  							<tr>
  								<td>E-Address Name</td>
  								<td><input type="text" class="form-control input-sm" size="50" name="name_input"></td>
  							</tr>
  							<tr>
  								<td>E-Address Type</td>
  								<td><input type="text" class="form-control input-sm" size="50" name="typeCode_input"></td>
  							</tr>
  						</table>
  					</div>
					<div class="tab-pane fade" id="KYCObj">
						<strong>KYCObj</strong>
						<table>
							<tr>
								<td>Complete Document Flag</td>
								<td><input type="text" class="form-control input-sm" size="50" name="completeDocumentFlag_input"></td>
							</tr>
							<tr>
								<td>Complete KYC Doucument Flag</td>
								<td><input type="text" class="form-control input-sm" size="50" name="kycCompleteDocumentFlag_input"></td>
							</tr>
							<tr>
								<td>KYC Branch Code</td>
								<td><input type="text" class="form-control input-sm" size="50" name="kycBranchCode_input"></td>
							</tr>
						</table>
					</div>
					<div class="tab-pane fade" id="officialAddress">
						<strong>Official Address</strong>
						<table>
							<tr>
								<td>Amphur</td>
								<td><input type="text" class="form-control input-sm" size="50" name="officialAddressAmphur_input"></td>
							</tr>
							<tr>
								<td>Building</td>
								<td><input type="text" class="form-control input-sm" size="50" name="officialAddressBuilding_input"></td>
							</tr>
							<tr>
								<td>Country</td>
								<td><input type="text" class="form-control input-sm" size="50" name="officialAddressCountry_input"></td>
							</tr>
							<tr>
								<td>Floor</td>
								<td><input type="text" class="form-control input-sm" size="50" name="officialAddressFloor_input"></td>
							</tr>
							<tr>
								<td>Number</td>
								<td><input type="text" class="form-control input-sm" size="50" name="officialAddressNumber_input"></td>
							</tr>
							<tr>
								<td>Moo</td>
								<td><input type="text" class="form-control input-sm" size="50" name="officialAddressMoo_input"></td>
							</tr>
							<tr>
								<td>Name</td>
								<td><input type="text" class="form-control input-sm" size="50" name="officialAddressName_input"></td>
							</tr>
							<tr>
								<td>Box No</td>
								<td><input type="text" class="form-control input-sm" size="50" name="officialAddressBoxNo_input"></td>
							</tr>
							<tr>
								<td>Post Code</td>
								<td><input type="text" class="form-control input-sm" size="50" name="officialAddressPostCode_input"></td>
							</tr>
							<tr>
								<td>Prefered Flag</td>
								<td><input type="text" class="form-control input-sm" size="50" name="officialAddressPreferedFlag_input"></td>
							</tr>
							<tr>
								<td>Province</td>
								<td><input type="text" class="form-control input-sm" size="50" name="officialAddressProvince_input"></td>
							</tr>
							<tr>
								<td>Road</td>
								<td><input type="text" class="form-control input-sm" size="50" name="officialAddressRoad_input"></td>
							</tr>
							<tr>
								<td>Room</td>
								<td><input type="text" class="form-control input-sm" size="50" name="officialAddressRoom_input"></td>
							</tr>
							<tr>
								<td>Soi</td>
								<td><input type="text" class="form-control input-sm" size="50" name="officialAddressSoi_input"></td>
							</tr>
							<tr>
								<td>Tumbol</td>
								<td><input type="text" class="form-control input-sm" size="50" name="officialAddressTumbol_input"></td>
							</tr>
							<tr>
								<td>Mooban</td>
								<td><input type="text" class="form-control input-sm" size="50" name="officialAddressMooban_input"></td>
							</tr>
						</table>
					</div>
  				</div>
 				</form>
 			</div>
 			
 			<button type="button" class="btn btn-info" onclick="requestService()">Request Service</button>
 			<button type="button" class="btn btn-info" onclick="clear_input()">Clear</button>
 			<button type="button" class="btn btn-info btn-md" onclick="jsonFormat()">Service Data</button>
 			
	</div>
<br>
	<div class="container well" id="CIS1037A01_output">
		<strong>Output</strong>
		<table>
			<tr>
				<td>Contact Address Id</td>
				<td><input type="text" class="form-control input-sm" size="50" name="contactAddrId_output" readonly></td>
			</tr>
			<tr>
				<td>Phone Id</td>
				<td><input type="text" class="form-control input-sm" size="50" name="phoneId_output" readonly></td>
			</tr>
			<tr>
				<td>E-Address Id</td>
				<td><input type="text" class="form-control input-sm" size="50" name="eAddrId_output" readonly></td>
			</tr>
			<tr>
				<td>Total Number of Regulation</td>
				<td><input type="text" class="form-control input-sm" size="50" name="totalNumReg_output" readonly></td>
			</tr>
			<tr>
				<td>Risk Code</td>
				<td><input type="text" class="form-control input-sm" size="50" name="riskCode_output" readonly></td>
			</tr>
			<tr>
				<td>Customer Id</td>
				<td><input type="text" class="form-control input-sm" size="50" name="cusId_output" readonly></td>
			</tr>
			<tr>
				<td>Official Address Id</td>
				<td><input type="text" class="form-control input-sm" size="50" name="offiAddrId_output" readonly></td>
			</tr>
		</table>
		<table class="table table-striped">
			<thead>
				<tr>
					<th>Regulation Sub Type</th>
					<th>Regulation Type</th>
				</tr>
			</thead>
			<tbody id="list_output">
			</tbody>
		</table>
	</div>
	<div class="well" id="CIS1037A01_RqRs">
  		<Strong>Request/Response JSON Format</Strong><br>
  		Request :
		<textarea class="form-control" name="requestJson" rows="5" readonly></textarea>
		Response :
		<textarea class="form-control" name="responseJson" rows="5" readonly></textarea>
  	</div>
	<div id="CIS1037A01_setting">
		<form class="form-inline well" id="CIS1037A01Form_setting"><strong>Setting</strong>
			<table>
				<tr>
					<td>URL Webservice</td>
					<td><input type="text" class="form-control input-sm" size="75" name="url_setting"></td>
				</tr>
				<tr>
					<td>Contact Address Id</td>
					<td><input type="text" class="form-control input-sm" size="50" name="contactAddrId_setting"></td>
				</tr>
				<tr>
					<td>Phone Id</td>
					<td><input type="text" class="form-control input-sm" size="50" name="phoneId_setting"></td>
				</tr>
				<tr>
					<td>E-Address Id</td>
					<td><input type="text" class="form-control input-sm" size="50" name="eAddrId_setting"></td>
				</tr>
				<tr>
					<td>Total Number of Regulation</td>
					<td><input type="text" class="form-control input-sm" size="50" name="totalNumReg_setting"></td>
				</tr>
				<tr>
					<td>Risk Code</td>
					<td><input type="text" class="form-control input-sm" size="50" name="riskCode_setting"></td>
				</tr>
				<tr>
					<td>Customer Id</td>
					<td><input type="text" class="form-control input-sm" size="50" name="cusId_setting"></td>
				</tr>
				<tr>
					<td>Official Address Id</td>
					<td><input type="text" class="form-control input-sm" size="50" name="offiAddrId_setting"></td>
				</tr>
			</table>
		</form>
		<button type="button" class="btn btn-info" onclick="save_setting()">Save</button>
		<button type="button" class="btn btn-info" onclick="clear_setting()">Clear</button>
	</div>
	<br>
	<div id="CIS1037A01_add">
		<form class="well form-inline" id="CIS1037A01Form_add"><strong>Add Properties</strong>
			<table>
				<tr>
					<td>Regulation Sub Type</td>
					<td><input type="text" class="form-control input-sm" size="50" name="regSubType_add"></td>
				</tr>
				<tr>
					<td>Regulation Type</td>
					<td><input type="text" class="form-control input-sm" size="50" name="regType_add"></td>
				</tr>
			</table>
			<input type="hidden" name="row_hidden">
			<button type="button" class="btn btn-info" onclick="add_properties()" id="add_button">Add</button>
			<button type="button" class="btn btn-info" onclick="edit_properties()" id="edit_button">Edit</button>
			<table class="table table-striped">
				<thead>
					<th>Regulation Sub Type</th>
					<th>Regulation Type</th>
					<th>Edit</th>
					<th>Delete</th>
				</thead>
				<tbody id="list_edit">
				</tbody>
			</table>
		</form>
	</div>
</body>
</html>