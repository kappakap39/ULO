<!DOCTYPE HTML>
<%@page contentType="text/html; charset:UTF-8;" %>
<html lang=en>
<head>
	<script>
		$(document).ready(function(){
			console.clear();
			$("#WFInquiryApp_RqRs").hide();
			$("#WFInquiryApp_setting").hide();
			$.ajax({
				url : "/ServiceCenterWeb/WFInquiryApp?process=defualt",
				type : "get",
				success : function(data){
					console.log(data);
					$("input[name=url_input]").val(data);
					$("input[name=url_setting]").val(data);
				}
			});
		});
		
		function requestService(){
			$.ajax({
				url : "/ServiceCenterWeb/WFInquiryApp?process=request",
				type : "get",
				data : $("#WFInquiryAppForm_input").serialize(),
				success : function(data){
					var obj = $.parseJSON(data);
					console.log(obj);
					$("textarea[name=requestJson]").val(obj.jsonRq);
  					$("textarea[name=responseJson]").val(obj.jsonRs);
  					
					$("input[name=no_record_output]").val(obj.no_record);
					$("#output_list").empty();
					var response = $.parseJSON(obj.jsonRs);
					
					if(response.kBankHeader.rsError===undefined){
						for(var i=0;i<obj.applicationGroupM.length;i++){
							var selectBuild1 = "<select class='form-control input-sm' style='width:150px;'>";
							for(var j=0;j<obj.applicationGroupM[i].personalInfos.length;j++){
								selectBuild1 += "<option>"+obj.applicationGroupM[i].personalInfos[j].personalId+"</option>";
							}
							selectBuild1 += "</select>";
							
							var selectBuild2 = "<select class='form-control input-sm' style='width:150px;'>";
							for(var j=0;j<obj.applicationGroupM[i].applications.length;j++){
								selectBuild2 += "<option>"+obj.applicationGroupM[i].applications[j].appItemNo+"</option>";
							}
							selectBuild2 += "</select>";
							
							var htmlBuild = "<tr>";
							htmlBuild += "<td>"+(i+1)+"</td>";
							htmlBuild += "<td>"+obj.applicationGroupM[i].appNo+"</td>";
							htmlBuild += "<td>"+selectBuild1+"</td>";
							htmlBuild += "<td>"+selectBuild2+"</td>";
							htmlBuild += "<td><a onclick=viewPersonal("+(i+1)+")>View</td>";
							htmlBuild += "<td><a onclick=viewApplication("+(i+1)+")>View</td>";
							htmlBuild += "</tr>";
							$("#output_list").append(htmlBuild);
						}
					}else{
						var errorLength = response.kBankHeader.rsError.length;
						var errorDetail = "";
						for(var count=0;count<errorLength;count++){
							errorDetail += response.kBankHeader.rsError[count].errorDesc+"\n";
						}
						eModel.alert(errorDetail);
					}
					
					//hidden data from response
					$("#response_hidden").empty();
					for(var i=0;i<obj.applicationGroupM.length;i++){
						
						var htmlBuild = "<div id="+obj.applicationGroupM[i].appNo+">";
							htmlBuild += "<input type=hidden name=app_no value="+obj.applicationGroupM[i].appNo+">";
							htmlBuild += "<input type=hidden name=app_date value="+obj.applicationGroupM[i].appDate.year+"-"+obj.applicationGroupM[i].appDate.month+"-"+obj.applicationGroupM[i].appDate.day+">";
							if(obj.applicationGroupM[i].personalInfos!=null){
								htmlBuild += "<div id=personalInfoM>";
								for(var j=0;j<obj.applicationGroupM[i].personalInfos.length;j++){
									htmlBuild += "<div id="+obj.applicationGroupM[i].personalInfos[j].personalId+">";
										htmlBuild += "<input type=hidden name=personal_id value="+obj.applicationGroupM[i].personalInfos[j].personalId+">";
										htmlBuild += "<input type=hidden name=doc_no value="+obj.applicationGroupM[i].personalInfos[j].docNo+">";
										htmlBuild += "<input type=hidden name=doc_type value="+obj.applicationGroupM[i].personalInfos[j].docType+">";
										htmlBuild += "<input type=hidden name=th_firstname value="+obj.applicationGroupM[i].personalInfos[j].thFirstname+">";
										htmlBuild += "<input type=hidden name=th_midname value="+obj.applicationGroupM[i].personalInfos[j].thMidname+">";
										htmlBuild += "<input type=hidden name=th_lastname value="+obj.applicationGroupM[i].personalInfos[j].thLastname+">";
										htmlBuild += "<input type=hidden name=applicant_type value="+obj.applicationGroupM[i].personalInfos[j].applicantType+">";
										htmlBuild += "<input type=hidden name=final_verified_income value="+obj.applicationGroupM[i].personalInfos[j].finalVerifiedIncome+">";
										htmlBuild += "<input type=hidden name=verify_cust_result value="+obj.applicationGroupM[i].personalInfos[j].verifyCustResult+">";
										if(obj.applicationGroupM[i].personalInfos[j].websites!=null){
											htmlBuild += "<div id=websiteM>";
											for(var k=0;k<obj.applicationGroupM[i].personalInfos[j].websites.length;k++){
												htmlBuild += "<div id=websiteM_"+k+">";
													htmlBuild +="<input type=hidden name=website_code value="+obj.applicationGroupM[i].personalInfos[j].websites[k].websiteCode+">";
													htmlBuild +="<input type=hidden name=website_reusult value="+obj.applicationGroupM[i].personalInfos[j].websites[k].websiteResult+">";
												htmlBuild += "</div>";
											}
											htmlBuild += "</div>";
										}
										htmlBuild += "<input type=hidden name=cardlink_cust_no value="+obj.applicationGroupM[i].personalInfos[j].cardlinkCustNo+">";
										htmlBuild += "<input type=hidden name=org_id value="+obj.applicationGroupM[i].personalInfos[j].orgId+">";
										if(obj.applicationGroupM[i].personalInfos[j].incomeInfos!=null){
											htmlBuild += "<div id=incomeInfoM>";
											for(var k=0;k<obj.applicationGroupM[i].personalInfos[j].incomeInfos.length;k++){
												htmlBuild += "<div id=incomeInfoM_"+k+">";
													htmlBuild += "<input type=hidden name=income_type value="+obj.applicationGroupM[i].personalInfos[j].incomeInfos[k].incomeType+">";
													if(obj.applicationGroupM[i].personalInfos[j].incomeInfos[k].previousIncomes!=null){
														htmlBuild += "<div id=previousIncomeM>";
														for(var l=0;l<obj.applicationGroupM[i].personalInfos[j].incomeInfos[k].previousIncomes.length;l++){
															htmlBuild += "<div id=previousIncomeM_"+l+">";
																htmlBuild += "<input type=hidden name=income value="+obj.applicationGroupM[i].personalInfos[j].incomeInfos[k].previousIncomes[l].income+">";
																htmlBuild += "<input type=hidden name=incomeDate value="+obj.applicationGroupM[i].personalInfos[j].incomeInfos[k].previousIncomes[l].incomeDate.year+"-"+obj.applicationGroupM[i].personalInfos[j].incomeInfos[k].previousIncomes[l].incomeDate.month+"-"+obj.applicationGroupM[i].personalInfos[j].incomeInfos[k].previousIncomes[l].incomeDate.day+">";
															htmlBuild += "</div>";
														}
														htmlBuild += "</div>";
													}
												htmlBuild += "</div>";
											}
											htmlBuild += "</div>";
										}
									htmlBuild += "</div>";
								}
								htmlBuild += "</div>";
							}
							if(obj.applicationGroupM[i].applications!=null){
								htmlBuild += "<div id=applicationM>";
								for(var j=0;j<obj.applicationGroupM[i].applications.length;j++){
									htmlBuild += "<div id="+obj.applicationGroupM[i].applications[j].appItemNo+">";
										htmlBuild += "<input type=hidden name=app_item_no value="+obj.applicationGroupM[i].applications[j].appItemNo+">";
										htmlBuild += "<input type=hidden name=app_status value="+obj.applicationGroupM[i].applications[j].appStatus+">";
										htmlBuild += "<input type=hidden name=app_status_date value="+obj.applicationGroupM[i].applications[j].appStatusDate.year+"-"+obj.applicationGroupM[i].applications[j].appStatusDate.month+"-"+obj.applicationGroupM[i].applications[j].appStatusDate.day+">";
										htmlBuild += "<input type=hidden name=project_code value="+obj.applicationGroupM[i].applications[j].projectCode+">";
										htmlBuild += "<input type=hidden name=kbank_product_code value="+obj.applicationGroupM[i].applications[j].kbankProductCode+">";
										htmlBuild += "<input type=hidden name=veto_flag value="+obj.applicationGroupM[i].applications[j].vetoFlag+">";
										htmlBuild += "<input type=hidden name=reject_code value="+obj.applicationGroupM[i].applications[j].rejectCode+">";
										htmlBuild += "<input type=hidden name=apply_type value="+obj.applicationGroupM[i].applications[j].applyType+">";
										htmlBuild += "<input type=hidden name=bundle_product value="+obj.applicationGroupM[i].applications[j].bundleProduct+">";
										htmlBuild += "<input type=hidden name=bundle_product_credit_limit value="+obj.applicationGroupM[i].applications[j].bundleProductCreditLimit+">";
										htmlBuild += "<input type=hidden name=policy_program_id value="+obj.applicationGroupM[i].applications[j].policyProgramId+">";
										htmlBuild += "<div id=verResultM>";
										if(obj.applicationGroupM[i].applications[j].orCodes!=null){
											htmlBuild += "<div id=orCodeM>";
											for(var k=0;k<obj.applicationGroupM[i].applications[j].orCodes.length;k++){
												htmlBuild += "<div id=orCodeM_"+k+">";
													htmlBuild += "<input type=hidden name=orCode value="+obj.applicationGroupM[i].applications[j].orCodes[k].orCode+">";
												htmlBuild += "</div>";
											}
											htmlBuild += "</div>";
										}
										htmlBuild += "</div>";
										if(obj.applicationGroupM[i].applications[j].loans!=null){
											htmlBuild += "<div id=loanM>";
											for(var k=0;k<obj.applicationGroupM[i].applications[j].loans.length;k++){
												htmlBuild += "<div id=loanM_"+k+">";
													htmlBuild += "<input type=hidden name=account_no value="+obj.applicationGroupM[i].applications[j].loans[k].accountNo+">";
													htmlBuild += "<input type=hidden name=final_credit_limit value="+obj.applicationGroupM[i].applications[j].loans[k].finalCreditLimit+">";
													htmlBuild += "<input type=hidden name=installment_amt value="+obj.applicationGroupM[i].applications[j].loans[k].installmentAmt+">";
													htmlBuild += "<input type=hidden name=booked_flag value="+obj.applicationGroupM[i].applications[j].loans[k].bookedFlag+">";
													htmlBuild += "<input type=hidden name=cycle_cut value="+obj.applicationGroupM[i].applications[j].loans[k].cycleCut+">";
													if(obj.applicationGroupM[i].applications[j].loans[k].personalRefs!=null){
														htmlBuild += "<div id=personalRefM>";
														for(var l=0;l<obj.applicationGroupM[i].applications[j].loans[k].personalRefs.length;l++){
															htmlBuild += "<div id=personalRefM_"+l+">";
																htmlBuild += "<input type=hidden name=personal_id value="+obj.applicationGroupM[i].applications[j].loans[k].personalRefs[l].personalId+">";
																htmlBuild += "<input type=hidden name=applicant_type value="+obj.applicationGroupM[i].applications[j].loans[k].personalRefs[l].applicantType+">";
															htmlBuild += "</div>";
														}
														htmlBuild += "</div>";
													}
													htmlBuild += "<div id=cardM>";
														htmlBuild += "<input type=hidden name=card_code value="+obj.applicationGroupM[i].applications[j].loans[k].cardCode+">";
														htmlBuild += "<input type=hidden name=card_level value="+obj.applicationGroupM[i].applications[j].loans[k].cardLevel+">";
														htmlBuild += "<input type=hidden name=plastic_type value="+obj.applicationGroupM[i].applications[j].loans[k].plasticType+">";
														htmlBuild += "<input type=hidden name=doc_no value="+obj.applicationGroupM[i].applications[j].loans[k].docNo+">";
														htmlBuild += "<input type=hidden name=card_apply_type value="+obj.applicationGroupM[i].applications[j].loans[k].cardApplyType+">";
														htmlBuild += "<input type=hidden name=card_no value="+obj.applicationGroupM[i].applications[j].loans[k].cardNo+">";
													htmlBuild += "</div>";
												htmlBuild += "</div>";
											}
											htmlBuild += "</div>";
										}
										if(obj.applicationGroupM[i].applications[j].personalRefs!=null){
											htmlBuild += "<div id=personalRefM>";
											for(var k=0;k<obj.applicationGroupM[i].applications[j].personalRefs.length;k++){
												htmlBuild += "<div id=personalRefM_"+k+">";
													htmlBuild += "<input type=hidden name=personal_id value="+obj.applicationGroupM[i].applications[j].personalRefs[k].personalId+">";
													htmlBuild += "<input type=hidden name=applicant_type value="+obj.applicationGroupM[i].applications[j].personalRefs[k].applicantType+">";
												htmlBuild += "</div>";
											}
											htmlBuild += "</div>";
										}
									htmlBuild += "</div>";
								}
							}
							htmlBuild += "</div>";
						htmlBuild += "</div>";
						$("#response_hidden").append(htmlBuild);
					}
					
				}
			});
		}
		
		function viewPersonal(row){
			var appGroupId = $("#output_list > tr:nth-child("+row+") > td:nth-child(2)").html();
			var personalId = $("#output_list > tr:nth-child("+row+") > td:nth-child(3) > select > option:selected").html();
			$("#dialog").html("");
			$("#dialog").dialog({modal:true, width:1050, dragable:true, resizable:true, title:"Personal Details" });
			$.ajax({
				url : "/ServiceCenterWeb/jsp/WFInquiryAppPerson_popup.jsp",
				type : "get",
				success : function(data){
					$("input[name=app_no_hidden]").val(appGroupId);
					$("input[name=personal_id_hidden]").val(personalId);
					$("#dialog").html(data);
					$("input[name=app_no_hidden]").val("");
					$("input[name=personal_id_hidden]").val("");
				}
			});
		}
		
		function viewApplication(row){
			var appGroupId = $("#output_list > tr:nth-child("+row+") > td:nth-child(2)").html();
			var appRecordId = $("#output_list > tr:nth-child("+row+") > td:nth-child(4) > select > option:selected").html();
			$("#dialog").html("");
			$("#dialog").dialog({modal:true, width:1050, dragable:true, resizable:true, title: "Application Details" });
			$.ajax({
				url : "/ServiceCenterWeb/jsp/WFInquiryAppApp_popup.jsp",
				type : "get",
				success : function(data){
					$("input[name=app_no_hidden]").val(appGroupId);
					$("input[name=app_item_no]").val(appRecordId);
					$("#dialog").html(data);
					$("input[name=app_no_hidden]").val("");
					$("input[name=app_item_no]").val("");
				}
			});
		}
		
		function WFInquiryApp_input(){
			$("#WFInquiryApp_input").show();
			$("#WFInquiryApp_output").show();
			$("#WFInquiryApp_setting").hide();
		}
		
		function WFInquiryApp_setting(){
			$("#WFInquiryApp_input").hide();
			$("#WFInquiryApp_output").hide();
			$("#WFInquiryApp_setting").show();			
		}
		
		function clearInput(){
			$("#WFInquiryAppForm_input").trigger("reset");
		}
		
		function saveSetting(){
			$.ajax({
				url : "/ServiceCenterWeb/WFInquiryApp?process=save",
				type : "post",
				data : $("#WFInquiryAppForm_setting").serialize(),
				success : function(data){
// 					alert("save data");
				}
			});
		}
		
		function clearSetting(){
			$("#WFInquiryAppForm_setting").trigger("reset");
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
  		<li class="active"><a data-toggle="tab" onclick="WFInquiryApp_input();">Send WFInquiryApp</a></li>
		<li><a data-toggle="tab" onclick="WFInquiryApp_setting();">Edit Props.</a></li>
  	</ul>
	<br>
	<div id="WFInquiryApp_input">
		<form class="form-inline well" id="WFInquiryAppForm_input">
			<Strong>Input</Strong>
			<table>
				<tr>
					<td>URL Web Service</td>
					<td><input type="text" class="form-control input-sm" size="60" name="url_input"></td>
					<td>CIS No</td>
					<td><input type="text" class="form-control input-sm" size="60" name="cis_no_input"></td>
				</tr>
				<tr>
					<td>Doc No</td>
					<td><input type="text" class="form-control input-sm" size="60" name="doc_no_input"></td>
					<td>Doc Type</td>
					<td><input type="text" class="form-control input-sm" size="60" name="doc_type_input"></td>
				</tr>
				<tr>
					<td>TH First Name</td>
					<td><input type="text" class="form-control input-sm" size="60" name="th_first_name_input"></td>
					<td>TH Last Name</td>
					<td><input type="text" class="form-control input-sm" size="60" name="th_last_name_input"></td>
				</tr>
				<tr>
					<td>Dob</td>
					<td><input type="text" class="form-control input-sm" size="60" name="dob_input"></td>
					<td>App Status</td>
					<td><input type="text" class="form-control input-sm" size="60" name="app_status_input"></td>
				</tr>
				<tr>
					<td>Booked Flag</td>
					<td><input type="text" class="form-control input-sm" size="60" name="booked_flag_input"></td>
					<td>Product Code</td>
					<td><input type="text" class="form-control input-sm" size="60" name="product_code_input"></td>
				</tr>
				<tr>
					<td>App From Date</td>
					<td><input type="text" class="form-control input-sm" size="60" name="app_date_from_input"></td>
					<td>App to Date</td>
					<td><input type="text" class="form-control input-sm" size="60" name="app_date_to_input"></td>
				</tr>
			</table>
		</form>
		<button type="button" class="btn btn-info" onclick="requestService()">Request Service</button>
		<button type="button" class="btn btn-info" onclick="clearInput()">Clear</button>
		<button type="button" class="btn btn-info btn-md" onclick="jsonFormat()">Service Data</button>
		<br><br>
	</div>
	<div class="well" id="WFInquiryApp_output">
		<Strong>Output</Strong>
		<table>
			<tr>
				<td>Total Record</td>
				<td><input type="text" class="form-control input-sm" size="60" name="no_record_output" readonly></td>
			</tr>
		</table>
		<input type="hidden" name="app_no_hidden">
		<input type="hidden" name="personal_id_hidden">
		<input type="hidden" name="app_item_no">
		<table class="table table-striped">
			<thead>
				<tr>
					<th class="col-sm-1">Row</th>
					<th class="col-sm-2">Application Group No</th>
					<th class="col-sm-2">Personal Id</th>
					<th class="col-sm-2">Application Record Id</th>
					<th class="col-sm-2">View Personal Details</th>
					<th class="col-sm-2">View Application Details</th>
				</tr>
			</thead>
			<tbody id="output_list">
			</tbody>
		</table>
		<div id=response_hidden></div>
	</div>
	<div class="well" id="WFInquiryApp_RqRs">
  		<Strong>Request/Response JSON Format</Strong><br>
  		Request :
		<textarea class="form-control" name="requestJson" rows="5" readonly></textarea>
		Response :
		<textarea class="form-control" name="responseJson" rows="5" readonly></textarea>
  	</div>
	<div id="WFInquiryApp_setting">
		<form class="form-inline well" id="WFInquiryAppForm_setting">
		<Strong>Setting</Strong>
			<table>
				<tr>
					<td>URL Web Service</td>
					<td><input type="text" class="form-control input-sm" size="60" name="url_setting"></td>
				</tr>
			</table>
		</form>
		<button type="button" class="btn btn-info" onclick="saveSetting()">Save</button>
		<button type="button" class="btn btn-info" onclick="clearSetting()">Clear</button>
	</div>
</body>
</html>