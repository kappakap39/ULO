<!DOCTYPE html>
<%@ page import="com.eaf.core.ulo.common.properties.SystemConfig"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="com.eaf.service.common.servlet.IIBService"%>
<%@ page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<html lang="en">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta charset="utf-8">

<!-- <script type="text/javascript" -->
<!-- 	src="https://code.jquery.com/jquery-1.11.3.min.js"></script> -->
<!--Font Awesome (added because you use icons in your prepend/append)-->
<!-- <link rel="stylesheet" -->
<!-- 	href="https://formden.com/static/cdn/font-awesome/4.4.0/css/font-awesome.min.css" /> -->	
	<!-- Special version of Bootstrap that only affects content wrapped in .bootstrap-iso -->
<!-- <link rel="stylesheet" -->
<!-- 	href="https://formden.com/static/cdn/bootstrap-iso.css" /> -->

<style>
.bootstrap-iso .formden_header h2,.bootstrap-iso .formden_header p,.bootstrap-iso form
	{
	font-family: Arial, Helvetica, sans-serif;
	color: black
}

select {
	display: inline-block;
	border: 2px solid #bbb;
	padding: 4px 3px 3px 5px;
	margin: 0;
	font: inherit;
	outline: none;
	line-height: 1.2;
	background: #f8f8f8;
	-webkit-border-radius: 6px;
	-moz-border-radius: 6px;
	border-radius: 6px;
}

.asteriskField {
	color: red;
}
</style>
<script>
	$(document).ready(function() {
				$("#responeData").hide();		
				
				var date_input = $('input[name="applicationDate"]');
				var date_input2 = $('input[name="birthDate"]');
				var date_input3 = $('input[name="applyDate"]');
			
				var container = $('.bootstrap-iso form').length > 0 ? $(
						'.bootstrap-iso form').parent() : "body";
				
				date_input.datepicker({
					format : 'yyyymmdd',
					container : container,
					todayHighlight : true,
					autoclose : true
				});
				date_input2.datepicker({
					format : 'yyyymmdd',
					container : container,
					todayHighlight : true,
					autoclose : true
				});
				date_input3.datepicker({
					format : 'yyyymmdd',
					container : container,
					todayHighlight : true,
					autoclose : true
				});
			});

	var status = "";
	var editprops = "";

	function sendData() {
		$("#select").show();
		$("#editProps").hide();

	}
	
	function changService(status) {
		this.status = status;
		$(".well").hide();
		$("#" + status).show();
		$("#select").show();
		var dataSend = "processCode=default";
		dataSend += "&Status=" + status;
		$.ajax({
			url : "/ServiceCenterWeb/Iibservice?process=request",
			type : "GET",
			data : dataSend,
			success : function(data) {
				var obj = jQuery.parseJSON(data);
				$("#" + status + "url").val(
						obj.
<%=IIBService.CONFIG_NAME.UrlWebService%>
	);
				editprops = obj.
<%=IIBService.CONFIG_NAME.UrlWebService%>
	;
			}
		});

	}

	function saveURL() {
		var dataSend = "processCode=save";
		dataSend += "&Status=" + status + "&";
		$.ajax({
			url : "/ServiceCenterWeb/Iibservice?process=request",
			type : "GET",
			data : dataSend += $("#Propsform").serialize(),
			success : function(data) {
				// 				var obj = jQuery.parseJSON(data);
				// 				$("#" + status + "url").val(obj[status]);
				// 				editprops = obj[status];
				// 				$("#urlProps").val(editprops);
				// 				alert(editprops);

			}
		});
		$("#" + this.status + "url").val($("#urlProps").val());
	}

	function clearURL() {
		$("#urlProps").val("");
	}

	function editPro() {
		$("#select").hide();
		$("#editProps").show();
		$("#Propsform").show();
		$("#urlProps").val(editprops);
	}

	function btClear() {
		var list = document.getElementsByTagName("input");
		for ( var i = 0; i < list.length; i++) {
			list[i].value = "";
		}
	}

	function sbMit() {
		var dataSend = "processCode=request&Status";
		if (this.status == "CIS0368I01") {
			dataSend += "=CIS0368I01&";
			dataSend += $("#" + this.status + "form").serialize();
			dataSend += "&";
		} else if (this.status == "CVRS1312I01") {
			dataSend += "=CVRS1312I01&";
			dataSend += $("#" + this.status + "form").serialize();
		} else if (this.status == "Fraud") {
			dataSend += "=Fraud&";
			dataSend += $("#" + this.status + "form").serialize();
		} else if (this.status == "KBANK1211I01") {
			dataSend += "=KBANK1211I01&";
			dataSend += $("#" + this.status + "form").serialize();
		} else if (this.status == "KBANK1550I01") {
			dataSend += "=KBANK1550I01&";
			dataSend += $("#" + this.status + "form").serialize();
		} else if (this.status == "KCBS") {
			dataSend += "=KCBS&";
			dataSend += $("#" + this.status + "form").serialize();
		} else if (this.status == "KOC") {
			dataSend += "=KOC&";
			dataSend += $("#" + this.status + "form").serialize();
		} else if (this.status == "HRIS") {
			dataSend += "=HRIS&";
			dataSend += $("#" + this.status + "form").serialize();
		} else if (this.status == "CARDLINK") {
			dataSend += "=CARDLINK&";
			dataSend += $("#" + this.status + "form").serialize();
		} else if (this.status == "TCBLoan") {
			dataSend += "=TCBLoan&";
			dataSend += $("#" + this.status + "form").serialize();
		} else if (this.status == "SAFELoan") {
			dataSend += "=SAFELoan&";
			dataSend += $("#" + this.status + "form").serialize();
		} else if (this.status == "LPM") {
			dataSend += "=LPM&";
			dataSend += $("#" + this.status + "form").serialize();
		} else if (this.status == "BScore") {
			dataSend += "=BScore&";
			dataSend += $("#" + this.status + "form").serialize();
		} else if (this.status == "KAsset") {
			dataSend += "=KAsset&";
			dataSend += $("#" + this.status + "form").serialize();
		} else if (this.status == "SavingAccount") {
			dataSend += "=SavingAccount&";
			dataSend += $("#" + this.status + "form").serialize();
		} else if (this.status == "FixAccount") {
			dataSend += "=FixAccount&";
			dataSend += $("#" + this.status + "form").serialize();
		} else if (this.status == "CurrentAccount") {
			dataSend += "=CurrentAccount&";
			dataSend += $("#" + this.status + "form").serialize();
		} else if (this.status == "BOL") {
			dataSend += "=BOL&";
			dataSend += $("#" + this.status + "form").serialize();
		} else if (this.status == "Wealth") {
			dataSend += "=Wealth&";
			dataSend += $("#" + this.status + "form").serialize();
		} else if (this.status == "Payroll") {
			dataSend += "=Payroll&";
			dataSend += $("#" + this.status + "form").serialize();
		}

		$.ajax({
			url : "/ServiceCenterWeb/Iibservice?process=request",
			type : "GET",
			data : dataSend,
			success : function(data) {
				var obj = jQuery.parseJSON(data);
				console.log("data response:" + data);
				$("textarea[name=requestJson]").val(obj.jsonRq);
				$("textarea[name=responseJson]").val(obj.jsonRs);
			}
		});

	}
</script>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a data-toggle="tab" onclick="sendData();">Send
				Data</a></li>
		<li><a data-toggle="tab" onclick="editPro();">Edit Props.</a></li>
	</ul>

	<br>
	<div class="well" id="select">
		<p>Choose Service</p>
		<select name="colour" onchange="changService(value)">
			<option value="dropdown">Please Select
			<option value="CIS0368I01">CIS0368I01
			<option value="CVRS1312I01">CVRS1312I01
			<option value="Fraud">Fraud
			<option value="KBANK1211I01">KBANK1211I01
			<option value="KBANK1550I01">KBANK1550I01
			<option value="KCBS">KCBS
			<option value="KOC">KOC
			<option value="HRIS">HRIS
			<option value="CARDLINK">CARDLINK
			<option value="TCBLoan">TCBLoan
			<option value="SAFELoan">SAFELoan
			<option value="LPM">LPM
			<option value="BScore">BScore
			<option value="KAsset">KAsset
			<option value="SavingAccount">SavingAccount
			<option value="FixAccount">FixAccount
			<option value="CurrentAccount">CurrentAccount
			<option value="BOL">BOL
			<option value="Wealth">Wealth
			<option value="Payroll">PayRoll
		</select> <br> <br>
		<%
			String Optional = SystemConstant.getConstant("OPTIONAL");
		%>
		<div hidden class="well" id="CIS0368I01">
			<form id="CIS0368I01form">
				<table>
					<tr>
						<td>URL Web Service :</td>
						<td><input type="text" id="CIS0368I01url"
							name="urlWebService" size="75"></td>
					</tr>
					<tr>
						<td>Customer Type :</td>
						<td><input type="text" name="customerType" size="60"></td>
					</tr>
					<tr>
						<td>CustomerId :</td>
						<td><input type="text" name="cisNo" size="60"></td>
					</tr>

					<tr>
						<td><%=Optional%> Kbank Product Code :</td>
						<td><input type="text" name="prodCode" size="60"></td>
					</tr>
					<tr>
						<td>Username :</td>
						<td><input type="text" name="userName" size="60"></td>
					</tr>
					<tr>
						<td>rqUID :</td>
						<td><input type="text" name="rqUid" size="60"></td>
					</tr>
					<tr>
						<td>Terminal ID :</td>
						<td><input type="text" name=terminalId size="60"></td>
					</tr>
				</table>
			</form>
		</div>

		<div hidden class="well" id="CVRS1312I01">
			<form id="CVRS1312I01form">
				<table>
					<tr>
						<td>URL Web Service :</td>
						<td><input type="text" id="CVRS1312I01url"
							name="urlWebService" size="75"></td>
					</tr>
					<tr>
						<td><%=Optional%> Birth date :</td>
						<td><div class="col-sm-6" style="padding-left: 0px;">
								<input class="form-control" id="date" name="birthDate"
									placeholder="YYYYMMDD" type="text">
							</div></td>
					</tr>

					<tr>
						<td><%=Optional%> Official Address Country :</td>
						<td><input type="text" name="offAddress" size="60"></td>
					</tr>

					<tr>
						<td><%=Optional%> Document ID :</td>
						<td><input type="text" name="idNo" size="60"></td>
					</tr>
					<tr>
						<td><%=Optional%> English name :</td>
						<td><input type="text" name="enFirstName" size="60"></td>
					</tr>
					<tr>
						<td><%=Optional%> English last name :</td>
						<td><input type="text" name="enLastName" size="60"></td>
					</tr>
					<tr>
						<td><%=Optional%> English middle name :</td>
						<td><input type="text" name="enMidName" size="60"></td>
					</tr>
					<tr>
						<td><%=Optional%> Nationality :</td>
						<td><input type="text" name="nationality" size="60"></td>
					</tr>
					<tr>
						<td><%=Optional%> Thai name :</td>
						<td><input type="text" name="thFirstName" size="60"></td>
					</tr>
					<tr>
						<td><%=Optional%> Thai last name :</td>
						<td><input type="text" name="thLastName" size="60"></td>
					</tr>
					<tr>
						<td><%=Optional%> Thai middle name :</td>
						<td><input type="text" name="thMidName" size="60"></td>
					</tr>
					<tr>
						<td>Customer Type :</td>
						<td><input type="text" name="customerType" size="60"></td>
					</tr>
					<tr>
						<td>Username :</td>
						<td><input type="text" name="userName" size="60"></td>
					</tr>
					<tr>
						<td>rqUID :</td>
						<td><input type="text" name="rqUid" size="60"></td>
					</tr>
					<tr>
						<td>Terminal ID :</td>
						<td><input type="text" name=terminalId size="60"></td>
					</tr>
				</table>
			</form>
		</div>

		<div hidden class="well" id="Fraud">
			<form id="Fraudform">
				<table>
					<tr>
						<td>URL Web Service :</td>
						<td><input type="text" id="Fraudurl" name="urlWebService"
							size="75"></td>
					</tr>
					<tr>
						<td>Application No. from Requester :</td>
						<td><input type="text" name="applicationGroupNo" size="60"></td>
					</tr>

					<tr>
						<td>Application Date :</td>
						<td><div class="col-sm-6" style="padding-left: 0px;">
								<input class="form-control" id="date" name="applicationDate"
									placeholder="YYYYMMDD" type="text">
							</div></td>
					</tr>
					<tr>
						<td>Organization ID :</td>
						<td><input type="text" name="orgId" size="60"></td>
					</tr>
					<tr>
						<td>Fraud Type :</td>
						<td><input type="text" name="fraudType" size="60"></td>
					</tr>
					<tr>
						<td><%=Optional%> ID Number or Document ID :</td>
						<td><input type="text" name="idNo" size="60"></td>
					</tr>
					<tr>
						<td><%=Optional%> ID Type / Document Type :</td>
						<td><input type="text" name="cisType" size="60"></td>
					</tr>
					<tr>
						<td><%=Optional%> Surname (Thai) :</td>
						<td><input type="text" name="thLastName" size="60"></td>
					</tr>
					<tr>
						<td><%=Optional%> First name (Thai) :</td>
						<td><input type="text" name="thFirstName" size="60"></td>
					</tr>
					<tr>
						<td><%=Optional%> Middle name (Thai) :</td>
						<td><input type="text" name="thMidName" size="60"></td>
					</tr>
					<tr>
						<td><%=Optional%> Birth Date (Christian) :</td>
						<td><div class="col-sm-6" style="padding-left: 0px;">
								<input class="form-control" id="date" name="birthDate"
									placeholder="YYYYMMDD" type="text">
							</div></td>
					</tr>
					<tr>
						<td><%=Optional%> Company Name :</td>
						<td><input type="text" name="companyName" size="60"></td>
					</tr>
					<tr>
						<td><%=Optional%> English Surname :</td>
						<td><input type="text" name="enLastName" size="60"></td>
					</tr>
					<tr>
						<td><%=Optional%> English First Name :</td>
						<td><input type="text" name="enFirstName" size="60"></td>
					</tr>
					<tr>
						<td>Username :</td>
						<td><input type="text" name="userName" size="60"></td>
					</tr>
					<tr>
						<td>rqUID :</td>
						<td><input type="text" name="rqUid" size="60"></td>
					</tr>
					<tr>
						<td>Terminal ID :</td>
						<td><input type="text" name=terminalId size="60"></td>
					</tr>
				</table>
			</form>
		</div>

		<div hidden class="well" id="KBANK1211I01">
			<form id="KBANK1211I01form">
				<table>
					<tr>
						<td>URL Web Service :</td>
						<td><input type="text" id="KBANK1211I01url"
							name="urlWebService" size="75"></td>
					</tr>
					<tr>
						<td><%=Optional%> Birth date :</td>
						<td><div class="col-sm-6" style="padding-left: 0px;">
								<input class="form-control" id="date" name="birthDate"
									placeholder="YYYYMMDD" type="text">
							</div></td>
					</tr>
					<tr>
						<td>Document Id :</td>
						<td><input type="text" name="idNo" size="60"></td>
					</tr>
					<tr>
						<td>Document type :</td>
						<td><input type="text" name="cisType" size="60"></td>
					</tr>
					<tr>
						<td>Customer name (Thai) :</td>
						<td><input type="text" name="thFirstName" size="60"></td>
					</tr>
					<tr>
						<td>Customer Last Name (Thai) :</td>
						<td><input type="text" name="thLastName" size="60"></td>
					</tr>
					<tr>
						<td>Username :</td>
						<td><input type="text" name="userName" size="60"></td>
					</tr>
					<tr>
						<td>rqUID :</td>
						<td><input type="text" name="rqUid" size="60"></td>
					</tr>
					<tr>
						<td>Terminal ID :</td>
						<td><input type="text" name=terminalId size="60"></td>
					</tr>
				</table>
			</form>
		</div>

		<div hidden class="well" id="KBANK1550I01">
			<form id="KBANK1550I01form">
				<table>
					<tr>
						<td>URL Web Service :</td>
						<td><input type="text" id="KBANK1550I01url"
							name="urlWebService" size="75"></td>
					</tr>
					
					<tr>
						<td>Company Name :</td>
						<td><input type="text" name="companyName" size="60"></td>
					</tr>
					<tr>
						<td><%=Optional%> User Name :</td>
						<td><input type="text" name="userName" size="60"></td>
					</tr>
					<tr>
						<td>rqUID :</td>
						<td><input type="text" name="rqUid" size="60"></td>
					</tr>
					<tr>
						<td>Terminal ID :</td>
						<td><input type="text" name=terminalId size="60"></td>
					</tr>
				</table>
			</form>
		</div>

		<div hidden class="well" id="KCBS">
			<form id="KCBSform">
				<table>
					<tr>
						<td>URL Web Service :</td>
						<td><input type="text" id="KCBSurl" name="urlWebService"
							size="75"></td>
					</tr>
					<tr>
						<td>RequireBureau :</td>
						<td><input type="text" name="requireBureau" size="60"></td>
					</tr>
					<tr>
						<td>Document type :</td>
						<td><input type="text" name="IdType" size="60"></td>
					</tr>
					<tr>
						<td>Document Id :</td>
						<td><input type="text" name="idNo" size="60"></td>
					</tr>
					<tr>
						<td>Customer Type :</td>
						<td><input type="text" name="cisType" size="60"></td>
					</tr>
					<tr>
						<td>Prefix</td>
						<td><input type="text" name="thTitleCode" size="60"></td>
					</tr>
					<tr>
						<td>Employer Name :</td>
						<td><input type="text" name="thFirstName" size="60"></td>
					</tr>
					<tr>
						<td>Employer LastName :</td>
						<td><input type="text" name="thLastName" size="60"></td>
					</tr>
					<tr>
						<td>DateOfBirth</td>
						<td><div class="col-sm-6" style="padding-left: 0px;">
								<input class="form-control" id="date" name="birthDate"
									placeholder="YYYYMMDD" type="text">
							</div></td>
					</tr>
					<tr>
						<td>GroupNo :</td>
						<td><input type="text" name="applicationGroupNo" size="60"></td>
					</tr>
					
					<tr>
						<td>Number of Submission :</td>
						<td><input type="text" name="numberOfSubmission" size="60"></td>
					</tr>
					<tr>
						<td>ApplicantType :</td>
						<td><input type="text" name="applicantType" size="60"></td>
					</tr>
					<tr>
						<td>User Name :</td>
						<td><input type="text" name="userName" size="60"></td>
					</tr>
					<tr>
						<td>rqUID :</td>
						<td><input type="text" name="rqUid" size="60"></td>
					</tr>
					<tr>
						<td>Terminal ID :</td>
						<td><input type="text" name="terminalId" size="60"></td>
					</tr>
					<tr>
						<td>Org ID :</td>
						<td><input type="text" name="orgId" size="60"></td>
					</tr>				
				</table>
			</form>
		</div>

		<div hidden class="well" id="KOC">
			<form id="KOCform">
				<table>
					<tr>
						<td>URL Web Service :</td>
						<td><input type="text" id="KOCurl" name="urlWebService"
							size="75"></td>
					</tr>
					<tr>
						<td>Idno :</td>
						<td><input type="text" name="idNo" size="60"></td>
					</tr>
					<tr>
						<td>Cis No :</td>
						<td><input type="text" name="cisNo" size="60"></td>
					</tr>
					<tr>
						<td>Apply Date :</td>
						<td><div class="col-sm-6" style="padding-left: 0px;">
								<input class="form-control" id="date" name="applyDate"
									placeholder="YYYYMMDD" type="text">
						</div></td>
					</tr>
					<tr>
						<td>FirstName (Thai) :</td>
						<td><input type="text" name="thFirstName" size="60"></td>
					</tr>
					<tr>
						<td>LastName (Thai) :</td>
						<td><input type="text" name="thLastName" size="60"></td>
					</tr>
					<tr>
						<td>FirstName (En) :</td>
						<td><input type="text" name="enFirstName" size="60"></td>
					</tr>
					<tr>
						<td>LastName (En) :</td>
						<td><input type="text" name="enLastName" size="60"></td>
					</tr>
					<tr>
						<td>Username :</td>
						<td><input type="text" name="userName" size="60"></td>
					</tr>
					<tr>
						<td>rqUID :</td>
						<td><input type="text" name="rqUid" size="60"></td>
					</tr>
					<tr>
						<td>Terminal ID :</td>
						<td><input type="text" name=terminalId size="60"></td>
					</tr>
				</table>
			</form>
		</div>

		<div hidden class="well" id="HRIS">
			<form id="HRISform">
				<table>
					<tr>
						<td>URL Web Service :</td>
						<td><input type="text" id="HRISurl" name="urlWebService"
							size="75"></td>
					</tr>
					<tr>
						<td>Idno:</td>
						<td><input type="text" name="idNo" size="60"></td>
					</tr>
					<tr>
						<td>FirstName (Thai) :</td>
						<td><input type="text" name="thFirstName" size="60"></td>
					</tr>
					<tr>
						<td>LastName (Thai) :</td>
						<td><input type="text" name="thLastName" size="60"></td>
					</tr>
					<tr>
						<td>FirstName (En) :</td>
						<td><input type="text" name="enFirstName" size="60"></td>
					</tr>
					<tr>
						<td>Nationality :</td>
						<td><input type="text" name="nationality" size="60"></td>
					</tr>
					<tr>
						<td>LastName (En) :</td>
						<td><input type="text" name="enLastName" size="60"></td>
					</tr>
					<tr>
						<td>BirthDate :</td>
						<td><div class="col-sm-6" style="padding-left: 0px;">
								<input class="form-control" id="date" name="birthDate"
									placeholder="YYYYMMDD" type="text">
							</div></td>
					</tr>
					<tr>
						<td>Username :</td>
						<td><input type="text" name="userName" size="60"></td>
					</tr>
					<tr>
						<td>rqUID :</td>
						<td><input type="text" name="rqUid" size="60"></td>
					</tr>
					<tr>
						<td>Terminal ID :</td>
						<td><input type="text" name=terminalId size="60"></td>
					</tr>
				</table>
			</form>
		</div>

		<div hidden class="well" id="CARDLINK">
			<form id="CARDLINKform">
				<table>
					<tr>
						<td>URL Web Service :</td>
						<td><input type="text" id="CARDLINKurl" name="urlWebService"
							size="75"></td>
					</tr>
					<tr>
						<td>Cis No :</td>
						<td><input type="text" name="cisNo" size="60"></td>
					</tr>
					<tr>
						<td>Username :</td>
						<td><input type="text" name="userName" size="60"></td>
					</tr>
					<tr>
						<td>rqUID :</td>
						<td><input type="text" name="rqUid" size="60"></td>
					</tr>
					<tr>
						<td>Terminal ID :</td>
						<td><input type="text" name=terminalId size="60"></td>
					</tr>
				</table>
			</form>
		</div>


		<div hidden class="well" id="TCBLoan">
			<form id="TCBLoanform">
				<table>
					<tr>
						<td>URL Web Service :</td>
						<td><input type="text" id="TCBLoanurl" name="urlWebService"
							size="75"></td>
					</tr>
					<tr>
						<td>Cis No :</td>
						<td><input type="text" name="cisNo" size="60"></td>
					</tr>
					<tr>
						<td>Username :</td>
						<td><input type="text" name="userName" size="60"></td>
					</tr>
					<tr>
						<td>rqUID :</td>
						<td><input type="text" name="rqUid" size="60"></td>
					</tr>
					<tr>
						<td>Terminal ID :</td>
						<td><input type="text" name=terminalId size="60"></td>
					</tr>
				</table>
			</form>
		</div>

		<div hidden class="well" id="SAFELoan">
			<form id="SAFELoanform">
				<table>
					<tr>
						<td>URL Web Service :</td>
						<td><input type="text" id="SAFELoanurl" name="urlWebService"
							size="75"></td>
					</tr>
					<tr>
						<td>Cis No :</td>
						<td><input type="text" name="cisNo" size="60"></td>
					</tr>
					<tr>
						<td>Username :</td>
						<td><input type="text" name="userName" size="60"></td>
					</tr>
					<tr>
						<td>rqUID :</td>
						<td><input type="text" name="rqUid" size="60"></td>
					</tr>
					<tr>
						<td>Terminal ID :</td>
						<td><input type="text" name=terminalId size="60"></td>
					</tr>
				</table>
			</form>
		</div>
		<div hidden class="well" id="LPM">
			<form id="LPMform">
				<table>
					<tr>
						<td>URL Web Service :</td>
						<td><input type="text" id="LPMurl" name="urlWebService"
							size="75"></td>
					</tr>
					<tr>
						<td>Document No :</td>
						<td><input type="text" name="cisNo" size="60"></td>
					</tr>
					<tr>
						<td>Username :</td>
						<td><input type="text" name="userName" size="60"></td>
					</tr>
					<tr>
						<td>rqUID :</td>
						<td><input type="text" name="rqUid" size="60"></td>
					</tr>
					<tr>
						<td>Terminal ID :</td>
						<td><input type="text" name=terminalId size="60"></td>
					</tr>
				</table>
			</form>
		</div>

		<div hidden class="well" id="BScore">
			<form id="BScoreform">
				<table>
					<tr>
						<td>URL Web Service :</td>
						<td><input type="text" id="BScoreurl" name="urlWebService"
							size="75"></td>
					</tr>
					<tr>
						<td>Cis No :</td>
						<td><input type="text" name="cisNo" size="60"></td>
					</tr>
					<tr>
						<td>Username :</td>
						<td><input type="text" name="userName" size="60"></td>
					</tr>
					<tr>
						<td>rqUID :</td>
						<td><input type="text" name="rqUid" size="60"></td>
					</tr>
					<tr>
						<td>Terminal ID :</td>
						<td><input type="text" name=terminalId size="60"></td>
					</tr>
				</table>
			</form>
		</div>

		<div hidden class="well" id="KAsset">
			<form id="KAssetform">
				<table>
					<tr>
						<td>URL Web Service :</td>
						<td><input type="text" id="KAsseturl" name="urlWebService"
							size="75"></td>
					</tr>
					<tr>
						<td>ID NO :</td>
						<td><input type="text" name="idNo" size="60"></td>
					</tr>

					<tr>
						<td>Cis Type :</td>
						<td><input type="text" name="cisType" size="60"></td>
					</tr>
					<tr>
						<td>Username :</td>
						<td><input type="text" name="userName" size="60"></td>
					</tr>
					<tr>
						<td>rqUID :</td>
						<td><input type="text" name="rqUid" size="60"></td>
					</tr>
					<tr>
						<td>Terminal ID :</td>
						<td><input type="text" name=terminalId size="60"></td>
					</tr>
				</table>
			</form>
		</div>

		<div hidden class="well" id="SavingAccount">
			<form id="SavingAccountform">
				<table>
					<tr>
						<td>URL Web Service :</td>
						<td><input type="text" id="SavingAccounturl"
							name="urlWebService" size="75"></td>
					</tr>
					<tr>
						<td>Cis No :</td>
						<td><input type="text" name="cisNo" size="60"></td>
					</tr>
					<tr>
						<td><%=Optional%> Nationality :</td>
						<td><input type="text" name="nationality" size="60"></td>
					</tr>
					<tr>
						<td>Username :</td>
						<td><input type="text" name="userName" size="60"></td>
					</tr>
					<tr>
						<td>rqUID :</td>
						<td><input type="text" name="rqUid" size="60"></td>
					</tr>
					<tr>
						<td>Terminal ID :</td>
						<td><input type="text" name=terminalId size="60"></td>
					</tr>
				</table>
			</form>
		</div>

		<div hidden class="well" id="FixAccount" size="60">
			<form id="FixAccountform">
				<table>
					<tr>
						<td>URL Web Service :</td>
						<td><input type="text" id="FixAccounturl"
							name="urlWebService" size="75"></td>
					</tr>
					<tr>
						<td>Cis No :</td>
						<td><input type="text" name="cisNo" size="60"></td>
					</tr>
					<tr>
						<td>Username :</td>
						<td><input type="text" name="userName" size="60"></td>
					</tr>
					<tr>
						<td>rqUID :</td>
						<td><input type="text" name="rqUid" size="60"></td>
					</tr>
					<tr>
						<td>Terminal ID :</td>
						<td><input type="text" name=terminalId size="60"></td>
					</tr>
				</table>
			</form>
		</div>

		<div hidden class="well" id="CurrentAccount">
			<form id="CurrentAccountform">
				<table>
					<tr>
						<td>URL Web Service :</td>
						<td><input type="text" id="CurrentAccounturl"
							name="urlWebService" size="75"></td>
					</tr>
					<tr>
						<td>Cis No :</td>
						<td><input type="text" name="cisNo" size="60"></td>
					</tr>
					<tr>
						<td>Username :</td>
						<td><input type="text" name="userName" size="60"></td>
					</tr>
					<tr>
						<td>rqUID :</td>
						<td><input type="text" name="rqUid" size="60"></td>
					</tr>
					<tr>
						<td>Terminal ID :</td>
						<td><input type="text" name=terminalId size="60"></td>
					</tr>
				</table>
			</form>
		</div>

		<div hidden class="well" id="BOL">
			<form id="BOLform">
				<table>
					<tr>
						<td>URL Web Service :</td>
						<td><input type="text" id="BOLurl" name="urlWebService"
							size="75"></td>
					</tr>
					<tr>
						<td>Employer Name :</td>
						<td><input type="text" name="companyName" size="60"></td>
					</tr>
					<tr>
						<td>Username :</td>
						<td><input type="text" name="userName" size="60"></td>
					</tr>
					<tr>
						<td>rqUID :</td>
						<td><input type="text" name="rqUid" size="60"></td>
					</tr>
					<tr>
						<td>Terminal ID :</td>
						<td><input type="text" name=terminalId size="60"></td>
					</tr>
				</table>
			</form>
		</div>

		<div hidden class="well" id="Wealth">
			<form id="Wealthform">
				<table>
					<tr>
						<td>URL Web Service :</td>
						<td><input type="text" id="Wealthurl" name="urlWebService"
							size="75"></td>
					</tr>
					<td>Cis No :</td>
					<td><input type="text" name="cisNo" size="60"></td>
					</tr>
					<tr>
						<td>Username :</td>
						<td><input type="text" name="userName" size="60"></td>
					</tr>
					<tr>
						<td>rqUID :</td>
						<td><input type="text" name="rqUid" size="60"></td>
					</tr>
					<tr>
						<td>Terminal ID :</td>
						<td><input type="text" name=terminalId size="60"></td>
					</tr>
				</table>
			</form>
		</div>

		<div hidden class="well" id="Payroll">
			<form id="Payrollform">
				<table>
					<tr>
						<td>URL Web Service :</td>
						<td><input type="text" id="Payrollurl" name="urlWebService"
							size="75"></td>
					</tr>
					<tr>
						<td>Idno :</td>
						<td><input type="text" name="idNo" size="60"></td>
					</tr>
					<tr>
						<td>Username :</td>
						<td><input type="text" name="userName" size="60"></td>
					</tr>
					<tr>
						<td>rqUID :</td>
						<td><input type="text" name="rqUid" size="60"></td>
					</tr>
					<tr>
						<td>Terminal ID :</td>
						<td><input type="text" name=terminalId size="60"></td>
					</tr>
				</table>
			</form>
		</div>

		<br>
		<button type="button" class="btn btn-info btn-md" id="btSubmit"
			onclick="sbMit();">Request Service</button>
		<button type="button" class="btn btn-info btn-md" id="btClear"
			onclick="btClear();">Clear</button>
		<button type="button" class="btn btn-info btn-md"
			onclick="jsonFormat()">Service Data</button>
	</div>

	<div hidden class="well" id="editProps">
		<form id="Propsform">
			<table>
				<tr>
					<td>URL Web Service :</td>
					<td><input type="text" id="urlProps" name="urlProps" size="75"></td>
			</table>
		</form>
		<br>
		<button type="button" class="btn btn-info btn-md" id=""
			onclick="saveURL();">Save</button>
		<button type="button" class="btn btn-info btn-md" id=""
			onclick="clearURL();">Clear</button>
	</div>
	<div class="well" id="responeData">
		<Strong>Request/Response JSON Format</Strong><br> Request :
		<textarea class="form-control" name="requestJson" rows="5" readonly></textarea>
		Response :
		<textarea class="form-control" name="responseJson" rows="5" readonly></textarea>
	</div>