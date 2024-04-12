<!DOCTYPE html>
<%@page contentType="text/html; charset:UTF-8;"%>
<html lang=en>
<head>
	<script>
		
		var appGroupId = $("input[name=app_no_hidden]").val();
		var appRecordId = $("input[name=app_item_no]").val();
		$(document).ready(function(){
			var orCodeLength = $("#"+appGroupId+" > #applicationM > #"+appRecordId+" > #verResultM > #orCodeM ").length;
			var loanLength = $("#"+appGroupId+" > #applicationM > #"+appRecordId+" > #loanM ").length;
			var personalRefLoanLength = $("#"+appGroupId+" > #applicationM > #"+appRecordId+" > #loanM > #personalRefM").length;
			var personalRefAppLength = $("#"+appGroupId+" > #applicationM > #"+appRecordId+" > #personalRefM ").length;
			console.clear();
			$("#WFInquiryApp_popup > table:eq(0) > tbody > tr:nth-child(1) > td:nth-child(2) > input ").val($("#"+appGroupId+" > #applicationM > #"+appRecordId+" > input[name=app_item_no]").val());
			$("#WFInquiryApp_popup > table:eq(0) > tbody > tr:nth-child(1) > td:nth-child(4) > input ").val($("#"+appGroupId+" > #applicationM > #"+appRecordId+" > input[name=app_status]").val());
			$("#WFInquiryApp_popup > table:eq(0) > tbody > tr:nth-child(2) > td:nth-child(2) > input ").val($("#"+appGroupId+" > #applicationM > #"+appRecordId+" > input[name=app_status_date]").val());
			$("#WFInquiryApp_popup > table:eq(0) > tbody > tr:nth-child(2) > td:nth-child(4) > input ").val($("#"+appGroupId+" > #applicationM > #"+appRecordId+" > input[name=project_code]").val());
			$("#WFInquiryApp_popup > table:eq(0) > tbody > tr:nth-child(3) > td:nth-child(2) > input ").val($("#"+appGroupId+" > #applicationM > #"+appRecordId+" > input[name=kbank_product_code]").val());
			$("#WFInquiryApp_popup > table:eq(0) > tbody > tr:nth-child(3) > td:nth-child(4) > input ").val($("#"+appGroupId+" > #applicationM > #"+appRecordId+" > input[name=veto_flag]").val());
			$("#WFInquiryApp_popup > table:eq(0) > tbody > tr:nth-child(4) > td:nth-child(2) > input ").val($("#"+appGroupId+" > #applicationM > #"+appRecordId+" > input[name=reject_code]").val());
			$("#WFInquiryApp_popup > table:eq(0) > tbody > tr:nth-child(4) > td:nth-child(4) > input ").val($("#"+appGroupId+" > #applicationM > #"+appRecordId+" > input[name=apply_type]").val());
			$("#WFInquiryApp_popup > table:eq(0) > tbody > tr:nth-child(5) > td:nth-child(2) > input ").val($("#"+appGroupId+" > #applicationM > #"+appRecordId+" > input[name=bundle_product]").val());
			$("#WFInquiryApp_popup > table:eq(0) > tbody > tr:nth-child(5) > td:nth-child(4) > input ").val($("#"+appGroupId+" > #applicationM > #"+appRecordId+" > input[name=bundle_product_credit_limit]").val());
			$("#WFInquiryApp_popup > table:eq(0) > tbody > tr:nth-child(6) > td:nth-child(2) > input ").val($("#"+appGroupId+" > #applicationM > #"+appRecordId+" > input[name=policy_program_id]").val());
			
			if(orCodeLength!=null){
				for(var i=0;i<orCodeLength;i++){
					var htmlBuild = "<tr>";
					htmlBuild += "<td>"+$("#"+appGroupId+" > #applicationM > #"+appRecordId+" > #verResultM > #orCodeM > div:eq("+i+") > input[name=orCode]").val()+"</td>";
					htmlBuild += "</tr>";
					$("#WFInquiryApp_popup > table:eq(1) > tbody ").append(htmlBuild);
				}
			}
			
			if(loanLength!=null){
				for(var i=0;i<loanLength;i++){
					var htmlBuild = "<tr>";
					htmlBuild += "<td>"+$("#"+appGroupId+" > #applicationM > #"+appRecordId+" > #loanM > div:eq("+i+") > input[name=account_no] ").val()+"</td>";
					htmlBuild += "<td>"+$("#"+appGroupId+" > #applicationM > #"+appRecordId+" > #loanM > div:eq("+i+") > input[name=final_credit_limit] ").val()+"</td>";
					htmlBuild += "<td>"+$("#"+appGroupId+" > #applicationM > #"+appRecordId+" > #loanM > div:eq("+i+") > input[name=installment_amt] ").val()+"</td>";
					htmlBuild += "<td>"+$("#"+appGroupId+" > #applicationM > #"+appRecordId+" > #loanM > div:eq("+i+") > input[name=booked_flag] ").val()+"</td>";
					htmlBuild += "<td>"+$("#"+appGroupId+" > #applicationM > #"+appRecordId+" > #loanM > div:eq("+i+") > input[name=cycle_cut] ").val()+"</td>";
					htmlBuild += "<td><a onclick=viewLoanDetail("+(i+1)+")>View</a></td>";
					htmlBuild += "</tr>"
					$("#WFInquiryApp_popup > table:eq(2) > tbody ").append(htmlBuild);
				}
			}
			
			if(personalRefAppLength!=null){
				for(var i=0;i<personalRefAppLength;i++){
					var htmlBuild = "<tr>";
					htmlBuild += "<td>"+$("#"+appGroupId+" > #applicationM > #"+appRecordId+" > #personalRefM > div:eq("+i+") > input[name=personal_id]").val()+"</td>";
					htmlBuild += "<td>"+$("#"+appGroupId+" > #applicationM > #"+appRecordId+" > #personalRefM > div:eq("+i+") > input[name=applicant_type]").val()+"</td>";
					htmlBuild += "</tr>";		
					$("#WFInquiryApp_popup > table:eq(5) > tbody ").append(htmlBuild);
				}
			}
		});
		
		function viewLoanDetail(row){
		$("#WFInquiryApp_popup > table:eq(3) > tbody ").empty();
			var personalRefLength = $("#"+appGroupId+" > #applicationM > #"+appRecordId+" > #loanM > div:eq("+(row-1)+") > #personalRefM ").length;
			if(personalRefLength!=null){
				for(var i=0;i<personalRefLength;i++){
					var htmlBuild1 = "<tr>";
					htmlBuild1 += "<td>"+$("#"+appGroupId+" > #applicationM > #"+appRecordId+" > #loanM > div:eq("+(row-1)+") > #personalRefM > div:eq("+i+") > input[name=personal_id] ").val()+"</td>";
					htmlBuild1 += "<td>"+$("#"+appGroupId+" > #applicationM > #"+appRecordId+" > #loanM > div:eq("+(row-1)+") > #personalRefM > div:eq("+i+") > input[name=applicant_type] ").val()+"</td>";
					htmlBuild1 += "</tr>";
					$("#WFInquiryApp_popup > table:eq(3) > tbody ").append(htmlBuild1);
				}
			}
			$("#WFInquiryApp_popup > table:eq(4) > tbody > tr:nth-child(1) > td:nth-child(2) > input ").val($("#"+appGroupId+" > #applicationM > #"+appRecordId+" > #loanM > div:eq("+(row-1)+") > #cardM > input[name=card_code]").val());
			$("#WFInquiryApp_popup > table:eq(4) > tbody > tr:nth-child(1) > td:nth-child(4) > input ").val($("#"+appGroupId+" > #applicationM > #"+appRecordId+" > #loanM > div:eq("+(row-1)+") > #cardM > input[name=card_level]").val());
			$("#WFInquiryApp_popup > table:eq(4) > tbody > tr:nth-child(2) > td:nth-child(2) > input ").val($("#"+appGroupId+" > #applicationM > #"+appRecordId+" > #loanM > div:eq("+(row-1)+") > #cardM > input[name=plastic_type]").val());
			$("#WFInquiryApp_popup > table:eq(4) > tbody > tr:nth-child(3) > td:nth-child(2) > input ").val($("#"+appGroupId+" > #applicationM > #"+appRecordId+" > #loanM > div:eq("+(row-1)+") > #cardM > input[name=doc_no]").val());
			$("#WFInquiryApp_popup > table:eq(4) > tbody > tr:nth-child(3) > td:nth-child(4) > input ").val($("#"+appGroupId+" > #applicationM > #"+appRecordId+" > #loanM > div:eq("+(row-1)+") > #cardM > input[name=card_apply_type]").val());
			$("#WFInquiryApp_popup > table:eq(4) > tbody > tr:nth-child(4) > td:nth-child(2) > input ").val($("#"+appGroupId+" > #applicationM > #"+appRecordId+" > #loanM > div:eq("+(row-1)+") > #cardM > input[name=card_no]").val());
		}		
	</script>
</head>
<body>
	<div class="well" id="WFInquiryApp_popup">
		<Strong>Application Item</Strong>
		<table>
			<tr>
				<td>Application Item No</td>
				<td><input type="text" class="form-control" size="40" readonly></td>
				<td>Application Status</td>
				<td><input type="text" class="form-control" size="40" readonly></td>
			</tr>
			<tr>
				<td>Application Status Date</td>
				<td><input type="text" class="form-control" size="40" readonly></td>
				<td>Project Code</td>
				<td><input type="text" class="form-control" size="40" readonly></td>
			</tr>
			<tr>
				<td>KBank Product Code</td>
				<td><input type="text" class="form-control" size="40" readonly></td>
				<td>Veto Flag</td>
				<td><input type="text" class="form-control" size="40" readonly></td>
			</tr>
			<tr>
				<td>Reject Code</td>
				<td><input type="text" class="form-control" size="40" readonly></td>
				<td>Apply Type</td>
				<td><input type="text" class="form-control" size="40" readonly></td>
			</tr>
			<tr>
				<td>Bundle Product</td>
				<td><input type="text" class="form-control" size="40" readonly></td>
				<td>Bundle Product Credit Limit</td>
				<td><input type="text" class="form-control" size="40" readonly></td>
			</tr>
			<tr>
				<td>Policy Program Id</td>
				<td><input type="text" class="form-control" size="40" readonly></td>
			</tr>
		</table>
		<Strong>OR Code</Strong>
		<table class="table table-striped">
			<thead>
				<tr>
					<th>OR Code</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
		<Strong>Loan</Strong>
		<table class="table table-striped">
			<thead>
				<tr>
					<th>Account No</th>
					<th>Final Credit Limit</th>
					<th>Installment Amt</th>
					<th>Booked Flag</th>
					<th>Cycle Cut</th>
					<th>View</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
		<Strong>Personal Reference(Loan)</Strong>
		<table class="table table-striped">
			<thead>
				<tr>
					<th>Personal Id</th>
					<th>Application Type</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
		<Strong>Card</Strong>
		<table>
			<tr>
				<td>Card Code</td>
				<td><input type="text" class="form-control input-sm" size="40" readonly></td>
				<td>Card Level</td>
				<td><input type="text" class="form-control input-sm" size="40" readonly></td>
			</tr>
			<tr>
				<td>Plastic Type</td>
				<td><input type="text" class="form-control input-sm" size="40" readonly></td>
			</tr>
			<tr>
				<td>Doc No</td>
				<td><input type="text" class="form-control input-sm" size="40" readonly></td>
				<td>Card Apply Type</td>
				<td><input type="text" class="form-control input-sm" size="40" readonly></td>
			</tr>
			<tr>
				<td>Card No</td>
				<td><input type="text" class="form-control input-sm" size="40" readonly></td>
			</tr>
		</table>
		<Strong>Personal Reference(Application)</Strong>
		<table class="table table-striped">
			<thead>
				<tr>
					<th>Personal Id</th>
					<th>Applicant Type</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
	</div>
</body>
</html>