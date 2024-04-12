<!DOCTYPE html>
<%@page contentType="text/html; charset:UTF-8;"%>
<html lang=en>
<head>
	<script>
		
		var appGroupId = $("input[name=app_no_hidden]").val();
		var personalId = $("input[name=personal_id_hidden]").val();
		$(document).ready(function(){
			console.clear();

			var websiteLength = $("#"+personalId+" > #websiteM > div ").length;
			var incomeInfoLength = $("#"+personalId+" > #incomeInfoM > div ").length;
			$("#WFInquiryApp_popup > table:eq(0) > tbody > tr:nth-child(1) > td:nth-child(2) >input ").val($("#"+appGroupId+" > input[name=app_no]").val());
			$("#WFInquiryApp_popup > table:eq(0) > tbody > tr:nth-child(1) > td:nth-child(4) >input ").val($("#"+appGroupId+" > input[name=app_date]").val());
			$("#WFInquiryApp_popup > table:eq(0) > tbody > tr:nth-child(2) > td:nth-child(2) >input ").val($("#"+personalId+" > input[name=personal_id]").val());
			$("#WFInquiryApp_popup > table:eq(0) > tbody > tr:nth-child(2) > td:nth-child(4) >input ").val($("#"+personalId+" > input[name=doc_no]").val());
			$("#WFInquiryApp_popup > table:eq(0) > tbody > tr:nth-child(3) > td:nth-child(2) >input ").val($("#"+personalId+" > input[name=doc_type]").val());
			$("#WFInquiryApp_popup > table:eq(0) > tbody > tr:nth-child(3) > td:nth-child(4) >input ").val($("#"+personalId+" > input[name=th_firstname]").val());
			$("#WFInquiryApp_popup > table:eq(0) > tbody > tr:nth-child(4) > td:nth-child(2) >input ").val($("#"+personalId+" > input[name=th_midname]").val());
			$("#WFInquiryApp_popup > table:eq(0) > tbody > tr:nth-child(4) > td:nth-child(4) >input ").val($("#"+personalId+" > input[name=th_lastname]").val());
			$("#WFInquiryApp_popup > table:eq(0) > tbody > tr:nth-child(5) > td:nth-child(2) >input ").val($("#"+personalId+" > input[name=applicant_type]").val());
			$("#WFInquiryApp_popup > table:eq(0) > tbody > tr:nth-child(5) > td:nth-child(4) >input ").val($("#"+personalId+" > input[name=final_verified_income]").val());
			$("#WFInquiryApp_popup > table:eq(0) > tbody > tr:nth-child(6) > td:nth-child(4) >input ").val($("#"+personalId+" > input[name=verify_cust_result]").val());
			$("#WFInquiryApp_popup > table:eq(0) > tbody > tr:nth-child(7) > td:nth-child(2) >input ").val($("#"+personalId+" > input[name=cardlink_cust_no]").val());
			$("#WFInquiryApp_popup > table:eq(0) > tbody > tr:nth-child(7) > td:nth-child(4) >input ").val($("#"+personalId+" > input[name=org_id]").val());
			for(var i=0;i<websiteLength;i++){
				var htmlBuild = "<tr>";
				htmlBuild += "<td>"+$("#"+personalId+" > #websiteM > div:eq("+i+") > input[name=website_code]").val()+"</td>";
				htmlBuild += "<td>"+$("#"+personalId+" > #websiteM > div:eq("+i+") > input[name=website_reusult]").val()+"</td>";
				htmlBuild += "</tr>";
				$("#WFInquiryApp_popup > table:eq(1) > tbody ").append(htmlBuild);
			}
			
			for(var i=0;i<incomeInfoLength;i++){
				var htmlBuild = "<tr>";
				htmlBuild += "<td>"+$("#"+personalId+" > #incomeInfoM > div:eq("+i+") > input[name=income_type]").val()+"</td>";
				htmlBuild += "<td><a onclick=viewPerviousIncome("+(i+1)+")>View</a></td>";
				htmlBuild += "</tr>";
				$("#WFInquiryApp_popup > table:eq(2) > tbody ").append(htmlBuild);
			}
			$("input[name=app_no_hidden]").val("");
			$("input[name=personal_id_hidden]").val("");
		});
		
		function viewPerviousIncome(row){
			var previousIncomeLength = $("#"+personalId+" > #incomeInfoM > div:eq("+(row-1)+") > div").length;
			$("#WFInquiryApp_popup > table:eq(3) > tbody").empty();
			for(var i=0;i<previousIncomeLength;i++){
				var htmlBuild = "<tr>";
				htmlBuild += "<td>"+$("#"+personalId+" > #incomeInfoM > div:eq("+(row-1)+") > input[name=income_type]").val()+"</td>";
				htmlBuild += "<td>"+$("#"+personalId+" > #incomeInfoM > div:eq("+(row-1)+") > #previousIncomeM >div:eq("+i+") > input[name=income] ").val()+"</td>";
				htmlBuild += "<td>"+$("#"+personalId+" > #incomeInfoM > div:eq("+(row-1)+") > #previousIncomeM >div:eq("+i+") > input[name=incomeDate] ").val()+"</td>"; 
				htmlBuild += "</tr>";
				$("#WFInquiryApp_popup > table:eq(3) > tbody").append(htmlBuild);
			}
			
		}
	</script>
</head>
<body>
	<div class="well" id="WFInquiryApp_popup">
		<Strong>Application Group Segment</Strong>
		<table>
			<tr>
				<td>Application Group Number</td>
				<td><input type="text" class="form-control" size="40" readonly></td>
				<td>Application Date</td>
				<td><input type="text" class="form-control" size="40" readonly></td>
			</tr>
			<tr>
				<td>Personal Id</td>
				<td><input type="text" class="form-control" size="40" readonly></td>
				<td>Document Id</td>
				<td><input type="text" class="form-control" size="40" readonly></td>
			</tr>
			<tr>
				<td>Document Type</td>
				<td><input type="text" class="form-control" size="40" readonly></td>
				<td>Thai First Name</td>
				<td><input type="text" class="form-control" size="40" readonly></td>
			</tr>
			<tr>
				<td>Thai Middle Name</td>
				<td><input type="text" class="form-control" size="40" readonly></td>
				<td>Thai Last Name</td>
				<td><input type="text" class="form-control" size="40" readonly></td>
			</tr>
			<tr>
				<td>Applicant Type</td>
				<td><input type="text" class="form-control" size="40" readonly></td>
				<td>Final Verified Income</td>
				<td><input type="text" class="form-control" size="40" readonly></td>
			</tr>
			<tr>
				<td>Verified Customer Result</td>
				<td><input type="text" class="form-control" size="40" readonly></td>
			</tr>
			<tr>
				<td>Cardlink Customer Id</td>
				<td><input type="text" class="form-control" size="40" readonly></td>
				<td>Organization Code</td>
				<td><input type="text" class="form-control" size="40" readonly></td>
			</tr>
		</table>
		<Strong>Website Verification Result</Strong>
		<table class="table table-striped">
			<thead>
				<tr>
					<th>Website Code</th>
					<th>Website Result</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
		<Strong>Income</Strong>
		<table class="table table-striped">
			<thead>
				<tr>
					<th>Income Type</th>
					<th>View</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
		<Strong>Previous Income</Strong>
		<table class="table table-striped">
			<thead>
				<tr>
					<th>Income Type</th>
					<th>Income</th>
					<th>Income Date</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
	</div>
</body>
</html>
