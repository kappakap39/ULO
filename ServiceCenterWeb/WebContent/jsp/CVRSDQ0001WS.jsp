<!DOCTYPE html>
<%@page contentType="text/html; charset=UTF-8" %>
<html>
<head>
	<script>
		$(document).ready(function(){
			console.clear();
			$("#CVRSDQ0001_RqRs").hide();
			$("#CVRSDQ0001_setting").hide();
			$("#CVRSDQ0001_add").hide();
			$("#edit_button").hide();
			$("#left_bar").height($("#showTab").height()+40);
			$.ajax({
				url : "/ServiceCenterWeb/CVRSDQ0001?process=defualt",
				type : "get",
				success : function(data){
					var obj = $.parseJSON(data);
					console.log(obj);
					$("input[name=url_input]").val(obj.urlWebService);
					$("input[name=url_setting]").val(obj.urlWebService);
					
					//defualt value
					$("input[name=th_first_name]").val("สมาน");
					$("input[name=th_title_name]").val("นาย");
					$("input[name=th_last_name]").val("เมืองศรี");
					$("input[name=th_middle_name]").val("เมืองศรี");
					$("input[name=eng_first_name]").val("SMAN");
					$("input[name=eng_title_name]").val("MS");
					$("input[name=eng_last_name]").val("MUANGSRI");
					$("input[name=eng_middle_name]").val("MUANGSRI");
					$("input[name=gender]").val("1");
					$("input[name=marriage_status]").val("1");
					$("input[name=customer_type]").val("I");
					$("input[name=customer_type_code]").val("0101");
					$("input[name=cid_type]").val("11");
					$("input[name=date_of_birth]").val("1983-12-15");
					$("input[name=id_no]").val("5801100029854");
					$("input[name=issue_by]").val("MB");
					$("input[name=issue_date]").val("1995-02-05");
					$("input[name=expire_date]").val("2017-02-05");
					$("input[name=nationality]").val("TH");
					$("input[name=race]").val("TH");
					$("input[name=occupation]").val("04");
					$("input[name=profession]").val("11");
					$("input[name=position]").val("03");
					$("input[name=education]").val("04");
					$("input[name=salary]").val("06");
					$("input[name=no_of_employee]").val("120");
					$("input[name=consent_flag]").val("Y");
					$("input[name=asset]").val("500000.98");	
									
					$("input[name=regis_address_type]").val("03");
					$("input[name=regis_country_code]").val("TH");
					$("input[name=regis_district]").val("ท่าอิฐ");
					$("input[name=regis_amphur]").val("ปากเกร็ด");
					$("input[name=regis_building]").val("นนทบุรี");
					$("input[name=regis_floor]").val("1");
					$("input[name=regis_moo]").val("9");
					$("input[name=regis_address_no]").val("123");
					$("input[name=regis_address_name]").val("บ้านเฮง");
					$("input[name=regis_po_box]").val("123");
					$("input[name=regis_post_code]").val("11120");
					$("input[name=regis_province]").val("นนทบุรี");
					$("input[name=regis_road]").val("รัตนาธิเบศ");
					$("input[name=regis_room]").val("1123");
					$("input[name=regis_soi]").val("ท่าอิฐ");
					$("input[name=regis_village]").val("หมู่บ้านมณียา");
					
					$("input[name=work_address_type]").val("02");
					$("input[name=work_country_code]").val("TH");
					$("input[name=work_district]").val("ท่าอิฐ");
					$("input[name=work_amphur]").val("ปากเกร็ด");
					$("input[name=work_building]").val("นนทบุรี");
					$("input[name=work_floor]").val("1");
					$("input[name=work_moo]").val("9");
					$("input[name=work_address_no]").val("123");
					$("input[name=work_address_name]").val("บ้านเฮง");
					$("input[name=work_po_box]").val("123");
					$("input[name=work_post_code]").val("11120");
					$("input[name=work_province]").val("นนทบุรี");
					$("input[name=work_road]").val("รัตนาธิเบศ");
					$("input[name=work_room]").val("1123");
					$("input[name=work_soi]").val("ท่าอิฐ");
					$("input[name=work_village]").val("หมู่บ้านมณียา");
					
					$("input[name=contact_address_type]").val("01");
					$("input[name=contact_country_code]").val("TH");
					$("input[name=contact_district]").val("ท่าอิฐ");
					$("input[name=contact_amphur]").val("ปากเกร็ด");
					$("input[name=contact_building]").val("นนทบุรี");
					$("input[name=contact_floor]").val("1");
					$("input[name=contact_moo]").val("9");
					$("input[name=contact_address_no]").val("123");
					$("input[name=contact_address_name]").val("บ้านเฮง");
					$("input[name=contact_po_box]").val("123");
					$("input[name=contact_post_code]").val("11120");
					$("input[name=contact_province]").val("นนทบุรี");
					$("input[name=contact_road]").val("รัตนาธิเบศ");
					$("input[name=contact_room]").val("1123");
					$("input[name=contact_soi]").val("ท่าอิฐ");
					$("input[name=contact_village]").val("หมู่บ้านมณียา");
					
					$("input[name=location_1]").val("2");
					$("input[name=telephone_num_1]").val("024702539");
					$("input[name=telephone_ext_1]").val("1439");
					$("input[name=email_1]").val("somcahi@gmail.com");
					$("input[name=mobile_no_1]").val("0974563445");
					$("input[name=fax_1]").val("024702539");
					$("input[name=fax_ext_1]").val("222");
					
				}
			});
			

		});

		function CVRSDQ0001_input(){
			$("#CVRSDQ0001_input").show();
			$("#CVRSDQ0001_output").show();
			$("#CVRSDQ0001_setting").hide();
			$("#CVRSDQ0001_add").hide();
		}
		function CVRSDQ0001_setting(){
			$("#CVRSDQ0001_input").hide();
			$("#CVRSDQ0001_output").hide();
			$("#CVRSDQ0001_setting").show();
			$("#CVRSDQ0001_add").show();
		}
		
		function edit_properties(){
			var row = $("input[name=row_hidden]").val();
			$.ajax({
				url : "/ServiceCenterWeb/CVRSDQ0001?process=edit",
				type : "post",
				data : $("#CVRSDQ0001Form_add").serialize(),
				beforeSend : console.log($("#CVRSDQ0001Form_add").serialize()),
				success : function(data) {
					/* $("#list_edit > tr:nth-child("+row+") > td:nth-child(1)").text($("input[name=regSubType_add]").val());
					$("#list_edit > tr:nth-child("+row+") > td:nth-child(2)").text($("input[name=regType_add]").val());
					$("input[name=regSubType_add]").val("");
					$("input[name=regType_add]").val("");
					$("input[name=row_hidden]").val(""); */
				}
			});
		}
		
		function delete_properties(row){
			$.ajax({
				url : "/ServiceCenterWeb/CVRSDQ0001?process=delete&row="+row,
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
				url : "/ServiceCenterWeb/CVRSDQ0001?process=add",
				type : "post",
				data : $("#CVRSDQ0001Form_add").serialize(),
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
				url : "/ServiceCenterWeb/CVRSDQ0001?process=save",
				type : "post",
				data : $("#CVRSDQ0001Form_setting").serialize(),
				success : function(data){
// 					alert("save data");
				}				
			});
		}
		
		function clear_setting(){
			$("#CVRSDQ0001Form_setting").trigger("reset");
		}
		
		function clear_input(){
			$(".CVRSDQ0001Form_input").trigger("reset");
		}
		
		function requestService(){
			$.ajax({
				url : "/ServiceCenterWeb/CVRSDQ0001?process=request",
				type : "get",
				data : $(".CVRSDQ0001Form_input").serialize(),
				success : function(data){
					var obj = $.parseJSON(data);
					console.log(obj);
					var jsonFormatOutput= JSON.stringify(JSON.parse(obj.response), null, 4);
					$('#output_json').html(jsonFormatOutput);
					$("textarea[name=requestJson]").val(obj.jsonRq);
  					$("textarea[name=responseJson]").val(obj.jsonRs);
				}
			});
		}
		var rowOtherNum=1;
 		function addOtherAddress(){
 			rowOtherNum = rowOtherNum+1;
 			$('[name=size_of_other_address]').val(rowOtherNum);
			var htmlBuild ="<tr><td colspan='4'></td></tr>";		
			htmlBuild += "<tr>";
			htmlBuild += "<td> #"+rowOtherNum+". Address Type</td>";
			htmlBuild += "<td><input type='text' class='form-control input-sm' name='oth_address_type_"+rowOtherNum+"' size=60></td></tr>";
			htmlBuild += "<td colspan='2'></td><tr>";	
			htmlBuild += "<td> Country Code</td>";	
			htmlBuild += "<td><input type='text' class='form-control input-sm' name='oth_country_code_"+rowOtherNum+"' size=60></td>";	
			htmlBuild += "<td>District</td>";	
			htmlBuild += "<td><input type='text' class='form-control input-sm' name='oth_district_"+rowOtherNum+"' size=60></td>";	
			htmlBuild += "</tr>";	
			htmlBuild += "<tr>";	
			htmlBuild += "<td> Amphur Code</td>";	
			htmlBuild += "<td><input type='text' class='form-control input-sm' name='oth_amphur_"+rowOtherNum+"' size=60></td>";	
			htmlBuild += "<td>Building</td>";	
			htmlBuild += "<td><input type='text' class='form-control input-sm' name='oth_building_"+rowOtherNum+"' size=60></td>";	
			htmlBuild += "</tr>";	
			htmlBuild += "<tr>";	
			htmlBuild += "<td>Floor</td>";	
			htmlBuild += "<td><input type='text' class='form-control input-sm' name='oth_floor_"+rowOtherNum+"' size=60></td>";	
			htmlBuild += "<td>Moo</td>";	
			htmlBuild += "<td><input type='text' class='form-control input-sm' name='oth_moo_"+rowOtherNum+"' size=60></td>";	
			htmlBuild += "</tr>";	
			htmlBuild += "<tr>";	
			htmlBuild += "<td> Address No</td>	";									
			htmlBuild += "<td><input type='text' class='form-control input-sm' name='oth_address_no_"+rowOtherNum+"' size=60></td>";	
			htmlBuild += "<td> Address Name</td>";	
			htmlBuild += "<td><input type='text' class='form-control input-sm' name='oth_address_name_"+rowOtherNum+"' size=60></td>";	
			htmlBuild += "</tr>";	
			htmlBuild += "<tr>";							
			htmlBuild += "<td> PoBox</td>";								 
			htmlBuild += "<td><input type='text' class='form-control input-sm' name='oth_po_box_"+rowOtherNum+"' size=60></td>";
			htmlBuild += "<td> Postcode</td>";
			htmlBuild += "<td><input type='text' class='form-control input-sm' name='oth_post_code_"+rowOtherNum+"' size=60></td>";
			htmlBuild += "</tr>	";
			htmlBuild += "<tr>";
			htmlBuild += "<td> Province</td>";									
			htmlBuild += "<td><input type='text' class='form-control input-sm' name='oth_province_"+rowOtherNum+"' size=60></td>";
			htmlBuild += "<td> Road</td>";
			htmlBuild += "<td><input type='text' class='form-control input-sm' name='oth_road_"+rowOtherNum+"' size=60></td>";
			htmlBuild += "</tr>";
			htmlBuild += "<tr>";
			htmlBuild += "<td> Soi</td>";
			htmlBuild += "<td><input type='text' class='form-control input-sm' name='oth_room_"+rowOtherNum+"' size=60></td>";
			htmlBuild += "<td> Room</td>";
			htmlBuild += "<td><input type='text' class='form-control input-sm' name='oth_soi_"+rowOtherNum+"' size=60></td>";
			htmlBuild += "</tr>";
			htmlBuild += "<tr>";
			htmlBuild += "<td> Village</td>";
			htmlBuild += "<td><input type='text' class='form-control input-sm' name='oth_village_"+rowOtherNum+"' size=60></td><td colspan=2></td>";
			htmlBuild += "</tr>";
			$("#otherAddressList").append(htmlBuild);
		}
		
		var contactRow=1;
		function addContacts(){
			contactRow =contactRow+1;
			$("[name=size_contact]").val(contactRow);
			var htmlBuild ="<tr>";		
				htmlBuild += "<td># "+contactRow+".</td><td colspan='3'></td>";
				htmlBuild += "</tr>";
				htmlBuild += "<tr>";
				htmlBuild += "<td> Location</td>";
				htmlBuild += "<td><input type='text' class='form-control input-sm' name='location_"+contactRow+"' size='60'></td>";
				htmlBuild += "<td colspan='2'></td>	";								 
				htmlBuild += "</tr>	";
				htmlBuild += "<tr>";
				htmlBuild += "<td> Telephone Number</td>";
				htmlBuild += "<td><input type='text' class='form-control input-sm' name='telephone_num_"+contactRow+"' size='60'></td>";
				htmlBuild += "<td> Ext.</td>";
				htmlBuild += "<td><input type='text' class='form-control input-sm' name='telephone_ext_"+contactRow+"' size='60'></td>";
				htmlBuild += "</tr>	";
				htmlBuild += "<tr>";
				htmlBuild += "<td> Email</td>";
				htmlBuild += "<td><input type='text' class='form-control input-sm' name='email_"+contactRow+"' size='60'></td>";
				htmlBuild += "<td> MobileNo</td>";
				htmlBuild += "<td><input type='text' class='form-control input-sm' name='mobile_no_"+contactRow+"' size='60'></td>";
				htmlBuild += "</tr>	";
				htmlBuild += "<tr>";
				htmlBuild += "<td> Fax</td>";
				htmlBuild += "<td><input type='text' class='form-control input-sm' name='fax_"+contactRow+"' size='60'></td>";
				htmlBuild += "<td> Fax Ext.</td>";
				htmlBuild += "<td><input type='text' class='form-control input-sm' name='fax_ext_"+contactRow+"' size='60'></td>";
				htmlBuild += "</tr>";
				$("#contactList").append(htmlBuild);
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
  		<li class="active"><a data-toggle="tab" onclick="CVRSDQ0001_input();">Send CVRSDQ0001</a></li>
		<li><a data-toggle="tab" onclick="CVRSDQ0001_setting();">Edit Props.</a></li>
  	</ul>
	<br>
	<div id="CVRSDQ0001_input">
		<form class="well form-inline CVRSDQ0001Form_input" ><strong>Input</strong>
			<table>
				<tr>
					<td>URL Web Service </td>
					<td><input type="text" class="form-control input-sm" size="100" name="url_input"></td>
				</tr>
			</table>
		</form>
		<div>
			<ul class="nav nav-tabs" >
			    <li class="active"><a data-toggle="tab" href="#CISIndivCust">CustomerProfile</a></li>
			    <li><a data-toggle="tab" href="#regisAddrObj">registrationAddress</a></li>
			    <li><a data-toggle="tab" href="#contactAddrObj">Contact Address</a></li>
			    <li><a data-toggle="tab" href="#workAddrObj">Work Address</a></li>
			    <li><a data-toggle="tab" href="#otherAddrObj">Other Address</a></li>
			    <li><a data-toggle="tab" href="#contactObj">Contact</a></li>
  			</ul>
  			
 					<br>
 				<form class="form-inline CVRSDQ0001Form_input well">
 				
 					<div class="tab-content">
  					<div class="tab-pane fade in active" id="CISIndivCust">
  						<Strong>Customer Profile</Strong>
  						<table>
  							<tr>
								<td>Personal Id</td>
								<td><input type="text" class="form-control input-sm" size="40" name="personal_id"></td>
								<td colspan="2"></td>
							</tr>
							<tr>
								<td>Thai First Name</td>
								<td><input type="text" class="form-control input-sm" size="40" name="th_first_name"></td>
								<td>Thai Title Name</td>
								<td><input type="text" class="form-control input-sm" size="40" name="th_title_name"></td>
							</tr>
							<tr>
								<td>Thai Last Name</td>
								<td><input type="text" class="form-control input-sm" size="40" name="th_last_name"></td>
								<td>Thai Middle Name</td>
								<td><input type="text" class="form-control input-sm" size="40" name="th_middle_name"></td>
							</tr>
							<tr>
								<td> Eng First Name</td>
								<td><input type="text" class="form-control input-sm" size="40" name="eng_first_name"></td>
								<td>Eng Title Name</td>
								<td><input type="text" class="form-control input-sm" size="40" name="eng_title_name"></td>
							</tr>
							<tr>
								<td>Eng Last Name</td>
								<td><input type="text" class="form-control input-sm" size="40" name="eng_last_name"></td>
								<td>Eng Middle Name</td>
								<td><input type="text" class="form-control input-sm" size="40" name="eng_middle_name"></td>
							</tr>
							<tr>
								<td>Gender</td>
								<td><input type="text" class="form-control input-sm" size="40" name="gender"></td>
								<td>Marriage Status</td>
								<td><input type="text" class="form-control input-sm" size="40" name="marriage_status"></td>
							</tr>
							<tr>
								<td>Customer Type</td>
								<td><input type="text" class="form-control input-sm" size="40" name="customer_type"></td>
								<td>Customer Type Code</td>
								<td><input type="text" class="form-control input-sm" size="40" name="customer_type_code"></td>
							</tr>
							<tr>
								<td>Cid Type</td>
								<td><input type="text" class="form-control input-sm" size="40" name="cid_type"></td>
								<td>Date Of Birth(yyyy-mm-dd)</td>
								<td><input type="text" class="form-control input-sm" size="40" name="date_of_birth"></td>
							</tr>
							<tr>
								<td>Id No</td>
								<td><input type="text" class="form-control input-sm" size="40" name="id_no"></td>
								<td>Issue By</td>
								<td><input type="text" class="form-control input-sm" size="40" name="issue_by"></td>
							</tr>
							<tr>
								<td>Issue Date(yyyy-mm-dd)</td>
								<td><input type="text" class="form-control input-sm" size="40" name="issue_date"></td>
								<td>Expire Date(yyyy-mm-dd)</td>
								<td><input type="text" class="form-control input-sm" size="40" name="expire_date"></td>
							</tr>
							<tr>
								<td>Nationality</td>
								<td><input type="text" class="form-control input-sm" size="40" name="nationality"></td>
								<td>Race</td>
								<td><input type="text" class="form-control input-sm" size="40" name="race"></td>
							</tr>
							<tr>
								<td>Occupation</td>
								<td><input type="text" class="form-control input-sm" size="40" name="occupation"></td>
								<td>Profession</td>
								<td><input type="text" class="form-control input-sm" size="40" name="profession"></td>
							</tr>
							<tr>
								<td>Position</td>
								<td><input type="text" class="form-control input-sm" size="40" name="position"></td>
								<td>Education</td>
								<td><input type="text" class="form-control input-sm" size="40" name="education"></td>
							</tr>
							<tr>
								<td>Salary</td>
								<td><input type="text" class="form-control input-sm" size="40" name="salary"></td>
								<td>No Of Employee</td>
								<td><input type="text" class="form-control input-sm" size="40" name="no_of_employee"></td>
							</tr>
							<tr>
								<td>Consent Flag</td>
								<td><input type="text" class="form-control input-sm" size="40" name="consent_flag"></td>
								<td>Asset</td>
								<td><input type="text" class="form-control input-sm" size="40" name="asset"></td>
							</tr>
  						</table>
  					</div>
  					<div class="tab-pane fade" id="regisAddrObj">
  						<Strong>Registration Address</Strong>
  						<table>
 								<tr>
								<td>Address Type</td>
								<td><input type="text" class="form-control input-sm"name="regis_address_type" size="60"></td>
								<td colspan="2"></td>
							</tr>
							<tr>
								<td>Country Code</td>
								<td><input type="text" class="form-control input-sm" name="regis_country_code" size="60"></td>
								<td>District</td>
								<td><input type="text" class="form-control input-sm" name="regis_district" size="60"></td>
							</tr>
							<tr>
								<td>Amphur Code</td>
								<td><input type="text" class="form-control input-sm" name="regis_amphur" size="60"></td>
								<td>Building</td>
								<td><input type="text" class="form-control input-sm" name="regis_building" size="60"></td>
							</tr>
							<tr>
								<td>Floor</td>
								<td><input type="text" class="form-control input-sm" name="regis_floor" size="60"></td>
								<td>Moo</td>
								<td><input type="text" class="form-control input-sm" name="regis_moo" size="60"></td>
							</tr>
							<tr>
								<td>Address No</td>
								<td><input type="text" class="form-control input-sm" name="regis_address_no" size="60"></td>
								<td>Address Name</td>
								<td><input type="text" class="form-control input-sm" name="regis_address_name" size="60"></td>
							</tr>
							<tr>
								<td>PoBox</td>
								<td><input type="text" class="form-control input-sm" name="regis_po_box" size="60"></td>
								<td>Postcode</td>
								<td><input type="text" class="form-control input-sm" name="regis_post_code" size="60"></td>
							</tr>
							<tr>
								<td>Province</td>
								<td><input type="text" class="form-control input-sm" name="regis_province" size="60"></td>
								<td>Road</td>
								<td><input type="text" class="form-control input-sm" name="regis_road" size="60"></td>
							</tr>
							<tr>
								<td>Soi</td>
								<td><input type="text" class="form-control input-sm" name="regis_room" size="60"></td>
								<td>Room</td>
								<td><input type="text" class="form-control input-sm" name="regis_soi" size="60"></td>
							</tr>
							<tr>
								<td>Village</td>
								<td><input type="text" class="form-control input-sm" name="regis_village" size="60"></td>
								<td colspan="2"></td>
							</tr>
  						</table>
  					</div>
  					<div class="tab-pane fade" id="contactAddrObj">
  						<Strong>Contact Address</Strong>
  						<table>
 							<tr>
								<td>Address Type</td>
								<td><input type="text" class="form-control input-sm"name="contact_address_type" size="60"></td>
								<td colspan="2"></td>
							</tr>
							<tr>
								<td>Country Code</td>
								<td><input type="text" class="form-control input-sm" name="contact_country_code" size="60"></td>
								<td>District</td>
								<td><input type="text" class="form-control input-sm" name="contact_district" size="60"></td>
							</tr>
							<tr>
								<td>Amphur Code</td>
								<td><input type="text" class="form-control input-sm" name="contact_amphur" size="60"></td>
								<td>Building</td>
								<td><input type="text" class="form-control input-sm" name="contact_building" size="60"></td>
							</tr>
							<tr>
								<td>Floor</td>
								<td><input type="text" class="form-control input-sm" name="contact_floor" size="60"></td>
								<td>Moo</td>
								<td><input type="text" class="form-control input-sm" name="contact_moo" size="60"></td>
							</tr>
							<tr>
								<td>Address No</td>
								<td><input type="text" class="form-control input-sm" name="contact_address_no" size="60"></td>
								<td>Address Name</td>
								<td><input type="text" class="form-control input-sm" name="contact_address_name" size="60"></td>
							</tr>
							<tr>
								<td>PoBox</td>
								<td><input type="text" class="form-control input-sm" name="contact_po_box" size="60"></td>
								<td>Postcode</td>
								<td><input type="text" class="form-control input-sm" name="contact_post_code" size="60"></td>
							</tr>
							<tr>
								<td>Province</td>
								<td><input type="text" class="form-control input-sm" name="contact_province" size="60"></td>
								<td>Road</td>
								<td><input type="text" class="form-control input-sm" name="contact_road" size="60"></td>
							</tr>
							<tr>
								<td>Soi</td>
								<td><input type="text" class="form-control input-sm" name="contact_room" size="60"></td>
								<td>Room</td>
								<td><input type="text" class="form-control input-sm" name="contact_soi" size="60"></td>
							</tr>
							<tr>
								<td>Village</td>
								<td><input type="text" class="form-control input-sm" name="contact_village" size="60"></td>
								<td colspan="2"></td>
							</tr>
  						</table>
  					</div>
  					<div class="tab-pane fade" id="workAddrObj">
  						<br>
  						<strong>Work Address</strong>
  						<table>
							<tr>
								<td>Address Type</td>
								<td><input type="text" class="form-control input-sm"name="work_address_type" size="60"></td>
								<td colspan="2"></td>
							</tr>
							<tr>
								<td>Country Code</td>
								<td><input type="text" class="form-control input-sm" name="work_country_code" size="60"></td>
								<td>District</td>
								<td><input type="text" class="form-control input-sm" name="work_district" size="60"></td>
							</tr>
							<tr>
								<td>Amphur Code</td>
								<td><input type="text" class="form-control input-sm" name="work_amphur" size="60"></td>
								<td>Building</td>
								<td><input type="text" class="form-control input-sm" name="work_building" size="60"></td>
							</tr>
							<tr>
								<td>Floor</td>
								<td><input type="text" class="form-control input-sm" name="work_floor" size="60"></td>
								<td>Moo</td>
								<td><input type="text" class="form-control input-sm" name="work_moo" size="60"></td>
							</tr>
							<tr>
								<td>Address No</td>
								<td><input type="text" class="form-control input-sm" name="work_address_no" size="60"></td>
								<td>Address Name</td>
								<td><input type="text" class="form-control input-sm" name="work_address_name" size="60"></td>
							</tr>
							<tr>
								<td>PoBox</td>
								<td><input type="text" class="form-control input-sm" name="work_po_box" size="60"></td>
								<td>Postcode</td>
								<td><input type="text" class="form-control input-sm" name="work_post_code" size="60"></td>
							</tr>
							<tr>
								<td>Province</td>
								<td><input type="text" class="form-control input-sm" name="work_province" size="60"></td>
								<td>Road</td>
								<td><input type="text" class="form-control input-sm" name="work_road" size="60"></td>
							</tr>
							<tr>
								<td>Soi</td>
								<td><input type="text" class="form-control input-sm" name="work_room" size="60"></td>
								<td>Room</td>
								<td><input type="text" class="form-control input-sm" name="work_soi" size="60"></td>
							</tr>
							<tr>
								<td>Village</td>
								<td><input type="text" class="form-control input-sm" name="work_village" size="60"></td>
								<td colspan="2"></td>
							</tr>
						</table>
  					</div>
					<div class="tab-pane fade" id="otherAddrObj">
						<strong>Other Address</strong>
						<table class="table table-striped">
							<tbody   id="otherAddressList">
								<tr>
									<td># 1. Address Type</td>
									<td><input type="text" class="form-control input-sm" name="oth_address_type_1" size="60"></td>
									<td colspan="2"><input type="hidden" value="1" name="size_of_other_address"></td>
								</tr>
								<tr>
									<td> Country Code</td>
									<td><input type="text" class="form-control input-sm" name="oth_country_code_1" size="60"></td>
									<td>District</td>
									<td><input type="text" class="form-control input-sm" name="oth_district_1" size="60"></td>
								</tr>
								<tr>
									<td> Amphur Code</td>
									<td><input type="text" class="form-control input-sm" name="oth_amphur_1" size="60"></td>
									<td>Building</td>
									<td><input type="text" class="form-control input-sm" name="oth_building_1" size="60"></td>
								
								</tr>
								<tr>
									<td>Floor</td>
									<td><input type="text" class="form-control input-sm" name="oth_floor_1" size="60"></td>
									<td>Moo</td>
									<td><input type="text" class="form-control input-sm" name="oth_moo_1" size="60"></td>
								</tr>
								<tr>
									<td> Address No</td>									
									<td><input type="text" class="form-control input-sm" name="oth_address_no_1" size="60"></td>
									<td> Address Name</td>
									<td><input type="text" class="form-control input-sm" name="oth_address_name_1" size="60"></td>
								</tr>
								<tr>							
									<td> PoBox</td>								 
									<td><input type="text" class="form-control input-sm" name="oth_po_box_1" size="60"></td>
									<td> Postcode</td>
									<td><input type="text" class="form-control input-sm" name="oth_post_code_1" size="60"></td>
								</tr>	
								<tr>
									 <td> Province</td>									
									 <td><input type="text" class="form-control input-sm" name="oth_province_1" size="60"></td>
									  <td> Road</td>
									<td><input type="text" class="form-control input-sm" name="oth_road_1" size="60"></td>
								</tr>
								<tr>
									<td> Soi</td>
									<td><input type="text" class="form-control input-sm" name="oth_room_1" size="60"></td>
									 <td> Room</td>
									<td><input type="text" class="form-control input-sm" name="oth_soi_1" size="60"></td>
								</tr>
								<tr>
								 	<td> Village</td>
									<td><input type="text" class="form-control input-sm" name="oth_village_1" size="60"></td>
									<td colspan="2"></td>
								</tr>																 
							</tbody>
						</table>
						<button type="button" class="btn btn-info btn-md" onclick="addOtherAddress()">add</button>						
					</div>
					<div class="tab-pane fade" id="contactObj">
						<strong>Contacts</strong>
						<table class="table table-striped">
							<tbody id="contactList">
								<tr>
									<td># 1.</td><td colspan="3"></td>
									<td><input type="hidden" name="size_contact" value="1"></td>
								</tr>
								<tr>
								 	<td> Location</td>
									<td><input type="text" class="form-control input-sm" name="location_1" size="60"></td>
									<td colspan="2"></td>									 
								</tr>	
								<tr>
								 	<td> Telephone Number</td>
									<td><input type="text" class="form-control input-sm" name="telephone_num_1" size="60"></td>
									<td> Ext.</td>
									<td><input type="text" class="form-control input-sm" name="telephone_ext_1" size="60"></td>
								</tr>	
								<tr>
								 	<td> Email</td>
									<td><input type="text" class="form-control input-sm" name="email_1" size="60"></td>
									<td> MobileNo</td>
									<td><input type="text" class="form-control input-sm" name="mobile_no_1" size="60"></td>
								</tr>	
								<tr>
								 	<td> Fax</td>
									<td><input type="text" class="form-control input-sm" name="fax_1" size="60"></td>
									<td> Fax Ext.</td>
									<td><input type="text" class="form-control input-sm" name="fax_ext_1" size="60"></td>
								</tr>	
							</tbody>
						</table>
						<button type="button" class="btn btn-info btn-md" onclick="addContacts()">add</button>						
					</div>
  				</div>
 				</form>
 			</div>
 			
 			<button type="button" class="btn btn-info" onclick="requestService()">Request Service</button>
 			<button type="button" class="btn btn-info" onclick="clear_input()">Clear</button>
 			<button type="button" class="btn btn-info btn-md" onclick="jsonFormat()">Service Data</button>
 			
	</div>
<br>
	<div class="container well" id="CVRSDQ0001_output">
		<strong>Output</strong>
		<table>
			<tr>
				<td id="output_json" width="100%"></td>
			</tr>
		</table>
	</div>
	<div class="well" id="CVRSDQ0001_RqRs">
  		<Strong>Request/Response JSON Format</Strong><br>
  		Request :
		<textarea class="form-control" name="requestJson" rows="5" readonly></textarea>
		Response :
		<textarea class="form-control" name="responseJson" rows="5" readonly></textarea>
  	</div>
	<div id="CVRSDQ0001_setting">
		<form class="form-inline well" id="CVRSDQ0001Form_setting"><strong>Setting</strong>
			<table>
				<tr>
					<td>URL Webservice</td>
					<td><input type="text" class="form-control input-sm" size="75" name="url_setting"></td>
				</tr>
			</table>
		</form>
		<button type="button" class="btn btn-info" onclick="save_setting()">Save</button>
		<button type="button" class="btn btn-info" onclick="clear_setting()">Clear</button>
	</div>
</body>
</html>