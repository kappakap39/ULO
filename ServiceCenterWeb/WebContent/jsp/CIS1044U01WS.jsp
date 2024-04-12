<!DOCTYPE html>
<%@page contentType="text/html; charset:UTF-8;" %>
<html lang=en>
<head>
	<script>
		$(document).ready(function(){
			console.clear();
			$("#CIS1044U01_RqRs").hide();
			$("#CIS1044U01_setting").hide();
						
			$.ajax({
				url : "/ServiceCenterWeb/CIS1044U01?process=defualt",
				type : "get",
				success : function(data){
					var obj = $.parseJSON(data);
					console.log(obj);
					$("input[name=url_input]").val(obj.urlWebService);
					$("input[name=url_setting]").val(obj.urlWebService);
					$("input[name=cusId_setting]").val(obj.customerId);
					$("input[name=status_setting]").val(obj.status);
					
					//defual input value
					
					$("input[name=hub_number]").val("0935");
					$("input[name=branch_number]").val("0935");
					$("input[name=function_flag]").val("C"); //C=Add Customer ,P=Add Prospect		
					$("input[name=customer_type]").val("I"); //I=Individual ,O=Organization, U=Unidentified
					$("input[name=customer_id]").val("0000020456");
					$("input[name=customer_type_code]").val("0702");//'0704' = Individual '0601' = Limited Company
					$("input[name=owner_branch]").val("0799");
					$("input[name=doc_type]").val("01");
					$("input[name=doc_id]").val("0487411155841");
					$("input[name=doc_expired_date]").val("2019-12-11");
					$("input[name=issued_date]").val("2015-12-11");
					$("input[name=issue_by]").val("");
					$("input[name=issue_card]").val("048741115584120191211");
					$("input[name=birth_date]").val("1988-11-11");
					$("input[name=official_address_country]").val("");
					$("input[name=contact_address_contry]").val("01");
					$("input[name=gender]").val("M");
					$("input[name=marital_status]").val("2");
					$("input[name=religion]").val("1");
					$("input[name=occupation]").val("01");
					$("input[name=job_position]").val("");
					$("input[name=business_type]").val("01");
					$("input[name=salary]").val("10000");
					$("input[name=start_work_date]").val("2014-11-11");
					$("input[name=degree]").val("1");
					$("input[name=nationality]").val("TH");
					$("input[name=race]").val("TH");
					$("input[name=book_number]").val("สจ.4068931");
					$("input[name=seq_number]").val("");
					$("input[name=change_name_date]").val("2015-11-11");
					$("input[name=consent_flag]").val("Y");
					$("input[name=source_consent]").val("14162/2550");
					$("input[name=kyc_branch]").val("0799");
					$("input[name=complete_doc_flag]").val("Y");
					$("input[name=complete_kyc_doc_flag]").val("Y");
					$("input[name=profession_code]").val("01/02/03/04/05");
					$("input[name=num_of_employee]").val("000012");
					$("input[name=asset_exclude_land]").val("00000004567890200");
					$("input[name=multi_contact_chanel]").val("0");
					$("input[name=amt_child]").val("03");
					$("input[name=age_of_oldes_child]").val("12");
					$("input[name=age_of_youngest_child]").val("05");
					$("input[name=family_income_Range]").val("01");
					$("input[name=customer_segment]").val("MB");
					$("input[name=forced_save_flag]").val("01");
					$("input[name=flag_same_address]").val("0");
					$("input[name=vip_flag]").val("");
					$("input[name=death_flag]").val("");
					$("input[name=death_date]").val("");
					$("input[name=tax_id]").val("");
				}
			});
		});
		
		function CIS1044U01_input(){
			$("#CIS1044U01_input").show();
			$("#CIS1044U01_output").show();
			$("#CIS1044U01_setting").hide();
		}
		
		function CIS1044U01_properties(){
			$("#CIS1044U01_input").hide();
			$("#CIS1044U01_output").hide();
			$("#CIS1044U01_setting").show();
		}
		
		function saveSetting(){
			$.ajax({
				url : "/ServiceCenterWeb/CIS1044U01?process=save",
				type : "post",
				data : $("#CIS1044U01Form_setting").serialize(),
				success : function(data){
// 					alert("save data");
				}
			});
		}
		
		function clearSetting(){
			$("input[name=url_setting]").val("");
			$("input[name=cusId_setting]").val("");
			$("input[name=status_setting]").val("");
		}
		
		function requestService(){
			$.ajax({
				url : "/ServiceCenterWeb/CIS1044U01?process=request",
				type : "get",
				data : $("#CIS1044U01Form_input").serialize(),
				success : function(data){
					var obj = $.parseJSON(data);
					console.log(obj);
					$("#cusId_output").val(obj.customerId);
					$("#status_output").val(obj.status);
					$("textarea[name=requestJson]").val(obj.jsonRq);
  					$("textarea[name=responseJson]").val(obj.jsonRs);
				}
			});
		}
		
		function clearInput(){
			$("input[name=url_input]").val("");
			$("input[name=hub_number]").val("");
			$("input[name=branch_number]").val("");
			$("input[name=function_flag]").val("");
			$("input[name=customer_type]").val("");
			$("input[name=customer_id]").val("");
			$("input[name=thai_title_name]").val("");
			$("input[name=thai_name]").val("");
			$("input[name=thai_middle_name]").val("");
			$("input[name=thai_last_name]").val("");
			$("input[name=eng_title_name]").val("");
			$("input[name=eng_name]").val("");
			$("input[name=eng_middle_name]").val("");
			$("input[name=eng_last_name]").val("");
			$("input[name=customer_type_code]").val("");
			$("input[name=owner_branch]").val("");
			$("input[name=doc_type]").val("");
			$("input[name=doc_id]").val("");
			$("input[name=doc_expired_date]").val("");
			$("input[name=issued_date]").val("");
			$("input[name=issue_by]").val("");
			$("input[name=issue_card]").val("");
			$("input[name=birth_date]").val("");
			$("input[name=official_address_country]").val("");
			$("input[name=contact_address_contry]").val("");
			$("input[name=gender]").val("");
			$("input[name=marital_status]").val("");
			$("input[name=religion]").val("");
			$("input[name=occupation]").val("");
			$("input[name=job_position]").val("");
			$("input[name=business_type]").val("");
			$("input[name=salary]").val("");
			$("input[name=start_work_date]").val("");
			$("input[name=degree]").val("");
			$("input[name=nationality]").val("");
			$("input[name=race]").val("");
			$("input[name=book_number]").val("");
			$("input[name=seq_number]").val("");
			$("input[name=change_name_date]").val("");
			$("input[name=consent_flag]").val("");
			$("input[name=source_consent]").val("");
			$("input[name=kyc_branch]").val("");
			$("input[name=complete_doc_flag]").val("");
			$("input[name=complete_kyc_doc_flag]").val("");
			$("input[name=profession_code]").val("");
			$("input[name=num_of_employee]").val("");
			$("input[name=asset_exclude_land]").val("");
			$("input[name=multi_contact_chanel]").val("");
			$("input[name=amt_child]").val("");
			$("input[name=age_of_oldes_child]").val("");
			$("input[name=age_of_youngest_child]").val("");
			$("input[name=family_income_Range]").val("");
			$("input[name=customer_segment]").val("");
			$("input[name=forced_save_flag]").val("");
			$("input[name=flag_same_address]").val("");
			$("input[name=vip_flag]").val("");
			$("input[name=death_flag]").val("");
			$("input[name=death_date]").val("");
			$("input[name=tax_id]").val("");
		}
		
 
	</script>
</head>
<body>
	 <ul class="nav nav-tabs">
  		<li class="active"><a data-toggle="tab" onclick="CIS1044U01_input();">Send CIS1044U01</a></li>
		<li><a data-toggle="tab" onclick="CIS1044U01_properties();">Edit Props.</a></li>
  	</ul>
	<br>
	<div id="CIS1044U01_input">
		<form id="CIS1044U01Form_input" class="well form-inline" >
			<strong>Input</strong>
			<table>
				<tr>
					<td>URL Webservice</td>
					<td colspan="3"><input type="text" name="url_input" class="form-control input-sm" size="150"></td>
				</tr>
				<tr>
					<td>Hub number</td>
					<td><input type="text" name="hub_number" class="form-control input-sm" size="50"></td>
					<td>Branch number</td>
					<td><input type="text" name="branch_number" class="form-control input-sm" size="50"></td>
				</tr>
				 
				<tr>
					<td>Function flag</td>
					<td><input type="text" name="function_flag" class="form-control input-sm" size="50"></td>
					<td>Customer Type</td>
					<td><input type="text" name="customer_type" class="form-control input-sm" size="50"></td>
				</tr>
				 
				<tr>
					<td>Customer Id</td>
					<td><input type="text" name="customer_id" class="form-control input-sm" size="50"></td>
					<td>Thai title name</td>
					<td><input type="text" name="thai_title_name" class="form-control input-sm" size="50"></td>
				</tr>
 
				<tr>
					<td>Thai name</td>
					<td><input type="text" name="thai_name" class="form-control input-sm" size="50"></td>
					<td>Thai middle name</td>
					<td><input type="text" name="thai_middle_name" class="form-control input-sm" size="50"></td>
				</tr>
				<tr>
					<td>Thai last name</td>
					<td><input type="text" name="thai_last_name" class="form-control input-sm" size="50"></td>
					<td>English title name</td>
					<td><input type="text" name="eng_title_name" class="form-control input-sm" size="50"></td>
				</tr>
				<tr>
					<td>English name</td>
					<td><input type="text" name="eng_name" class="form-control input-sm" size="50"></td>
					<td>English middle name</td>
					<td><input type="text" name="eng_middle_name" class="form-control input-sm" size="50"></td>
				</tr>
				<tr>
					<td>English last name</td>
					<td><input type="text" name="eng_last_name" class="form-control input-sm" size="50"></td>
					<td>Type of customer code (XXXX)</td>
					<td><input type="text" name="customer_type_code" class="form-control input-sm" size="50"></td>
				</tr>
				<tr>
					<td>Owner branch number</td>
					<td><input type="text" name="owner_branch" class="form-control input-sm" size="50"></td>
					<td>Document Type-Important Document Type for individual</td>
					<td><input type="text" name="doc_type" class="form-control input-sm" size="50"></td>
				</tr>
				<tr>
					<td>Document ID</td>
					<td><input type="text" name="doc_id" class="form-control input-sm" size="50"></td>
					<td>The expiry date of document(yyyy-mm-dd)</td>
					<td><input type="text" name="doc_expired_date" class="form-control input-sm" size="50"></td>
				</tr>
				<tr>
					<td>Issued Date(yyyy-mm-dd)</td>
					<td><input type="text" name="issued_date" class="form-control input-sm" size="50"></td>
					<td>Issued By</td>
					<td><input type="text" name="issue_by" class="form-control input-sm" size="50"></td>
				</tr>
				<tr>
					<td>Issued Card Description</td>
					<td><input type="text" name="issue_card" class="form-control input-sm" size="50"></td>
					<td>Birth date(yyyy-mm-dd)</td>
					<td><input type="text" name="birth_date" class="form-control input-sm" size="50"></td>
				</tr>
				<tr>
					<td>Official Address Country(DIH)</td>
					<td><input type="text" name="official_address_country" class="form-control input-sm" size="50"></td>
					<td>Contact Address Country(DIH)</td>
					<td><input type="text" name="contact_address_contry" class="form-control input-sm" size="50"></td>
				</tr>
				<tr>
					<td>Gender</td>
					<td><input type="text" name="gender" class="form-control input-sm" size="50"></td>
				</tr>
				<tr>
					<td>Marital Status</td>
					<td><input type="text" name="marital_status" class="form-control input-sm" size="50"></td>
					<td>Religion(DIH)</td>
					<td><input type="text" name="religion" class="form-control input-sm" size="50"></td>
				</tr>
				<tr>
					<td>Occupation</td>
					<td><input type="text" name="occupation" class="form-control input-sm" size="50"></td>
					<td>Job Position(DIH)</td>
					<td><input type="text" name="job_position" class="form-control input-sm" size="50"></td>
				</tr>
				<tr>
					<td>Business type code(DIH)</td>
					<td><input type="text" name="business_type" class="form-control input-sm" size="50"></td>
					<td>Salary</td>
					<td><input type="text" name="salary" class="form-control input-sm" size="50"></td>
				</tr>
				<tr>
					<td>Start work date(yyyy-mm-dd)(DIH)</td>
					<td><input type="text" name="start_work_date" class="form-control input-sm" size="50"></td>
					<td>Degree</td>
					<td><input type="text" name="degree" class="form-control input-sm" size="50"></td>
				</tr>
				<tr>
					<td>Nationality</td>
					<td><input type="text" name="nationality" class="form-control input-sm" size="50"></td>
					<td>Race(DIH)</td>
					<td><input type="text" name="race" class="form-control input-sm" size="50"></td>
				</tr>
				<tr>
					<td>Book number for change name(DIH)</td>
					<td><input type="text" name="book_number" class="form-control input-sm" size="50"></td>
					<td>Sequence number for change name(DIH)</td>
					<td><input type="text" name="seq_number" class="form-control input-sm" size="50"></td>
				</tr>
				<tr>
					<td>The date change name(yyyy-mm-dd)(DIH)</td>
					<td><input type="text" name="change_name_date" class="form-control input-sm" size="50"></td>
					<td>Consent flag(DIH)</td>
					<td><input type="text" name="consent_flag" class="form-control input-sm" size="50"></td>
				</tr>
				<tr>
					<td>Source of consent(DIH)</td>
					<td><input type="text" name="source_consent" class="form-control input-sm" size="50"></td>
					<td>KYC branch</td>
					<td><input type="text" name="kyc_branch" class="form-control input-sm" size="50"></td>
				</tr>
				<tr>
					<td>Complete document flag</td>
					<td><input type="text" name="complete_doc_flag"class="form-control input-sm" size="50"></td>
					<td>Complete KYC document flag</td>
					<td><input type="text" name="complete_kyc_doc_flag" class="form-control input-sm" size="50"></td>
				</tr>
				<tr>
					<td>Profession code</td>
					<td><input type="text" name="profession_code"class="form-control input-sm" size="50"></td>
					<td>Number of employee</td>
					<td><input type="text" name="num_of_employee"class="form-control input-sm" size="50"></td>
				</tr>
				<tr>
					<td>Asset exclude land</td>
					<td><input type="text" name="asset_exclude_land"class="form-control input-sm" size="50"></td>
					<td>Multiple Contact Channel</td>
					<td><input type="text" name="multi_contact_chanel"class="form-control input-sm" size="50"></td>
				</tr>
				<tr>
					<td>Amount of children</td>
					<td><input type="text" name="amt_child"class="form-control input-sm" size="50"></td>
					<td>Age of oldest child</td>
					<td><input type="text" name="age_of_oldes_child"class="form-control input-sm" size="50"></td>
				</tr>
				<tr>
					<td>Age of youngest child</td>
					<td><input type="text" name="age_of_youngest_child"class="form-control input-sm" size="50"></td>
					<td>Family Income Range</td>
					<td><input type="text" name="family_income_Range" class="form-control input-sm" size="50"></td>
				</tr>
				<tr>
					<td>Customer segment</td>
					<td><input type="text" name="customer_segment"class="form-control input-sm" size="50"></td>
					<td>Title forced save flag</td>
					<td><input type="text" name="forced_save_flag" class="form-control input-sm" size="50"></td>
				</tr>
				<tr>
					<td>Address flag using the same address</td>
					<td><input type="text" name="flag_same_address" class="form-control input-sm" size="50"></td>
					<td>VIP Flag</td>
					<td><input type="text" name="vip_flag" class="form-control input-sm" size="50"></td>
				</tr>
				<tr>
					<td>Death flag</td>
					<td><input type="text" name="death_flag" class="form-control input-sm" size="50"></td>
					<td>Death date(yyyy-mm-dd)</td>
					<td><input type="text" name="death_date" class="form-control input-sm" size="50"></td>
				</tr>
				<tr>
					<td>Tax ID</td>
					<td><input type="text" name="tax_id" class="form-control input-sm" size="50"></td>
					<td colspan="2"></td>
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
			<tr>
				<td>Status Code</td>
				<td><input type="text" id="status_output" class="form-control input-sm" size="60" readonly></td>			
			</tr>
		</table>
	</div>
	<div class="well" id="CIS1044U01_RqRs">
 			<Strong>Request/Response JSON Format</Strong><br>
  		Request :
		<textarea class="form-control" name="requestJson" rows="5" readonly></textarea>
		Response :
		<textarea class="form-control" name="responseJson" rows="5" readonly></textarea>
  	</div>
	<div id="CIS1044U01_setting">
		<form id="CIS1044U01Form_setting" class="well form-inline">
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
				<tr>
					<td>Status Code</td>
					<td><input type="text" name="status_setting" class="form-control input-sm" size="60"></td>
				</tr>
			</table>
		</form>
		<button type="button" class="btn btn-info" onclick="saveSetting()">Save</button>
		<button type="button" class="btn btn-info" onclick="clearSetting()">Clear</button>
	</div>
</body>
</html>
