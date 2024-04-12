<!DOCTYPE html>
<%@page contentType="text/html; charset:UTF-8;"%>
<html lang="en">
<head>
<script>
		
		//for keep html list
		var personalApplicant_list = null;
		var previousEmployment_list = null;
		var currentEmployment_list = null;
		var websiteVerificationResponse_list = null;
		var inc_info_list = null;
		var inc_payslip_list = null;
		var inc_payslip_monthly_list = null;
		var inc_payslip_monthly_detail_list = null;
		var inc_yearly_tawi = null;
		var inc_saving_acc_list = null;
		var inc_salary_cert_list = null;
		var inc_opn_end_fund_list = null;
		var inc_opn_end_fund_detail_list = null;
		var inc_monthly_tawi_list = null; 
		var	inc_monthly_tawi_detail_list = null;
		var inc_bank_statement_list = null;
		var inc_bank_statement_detail_list = null;
		var inc_payroll_file_info_list = null;
		var documentList_list = null;
		var reference_list = null;
		var creditDetail_list = null;
		
		$(document).ready(function(){
			console.clear();
			personalApplicant_list = $("#personalApplicant_list_1").prop("outerHTML");
			previousEmployment_list = $("#previousEmployment_list_1").prop("outerHTML");
			currentEmployment_list = $("#currentEmployment_list_1").prop("outerHTML");
			websiteVerificationResponse_list = $("#websiteVerificationResponse_list_1").html();
			inc_info_list = $("#inc_info_list_1").prop("outerHTML");
			inc_payslip_list = $("#inc_payslip_list_1").prop("outerHTML");
			inc_payslip_monthly_list = $("#inc_payslip_monthly_list_1 ").prop("outerHTML");
			inc_payslip_monthly_detail_list = $("#inc_payslip_monthly_detail > table > tbody").html();
			inc_yearly_tawi = $("#inc_info_list_1 > table > tbody:eq(1)").html();
			inc_saving_acc_list = $("#inc_info_list_1 > #inc_saving_acc").html();
			inc_saving_acc_detail_list = $("#inc_info_list_1 > #inc_saving_acc > #inc_saving_acc_list_1 #inc_saving_acc_detail_list_1").html();
			inc_salary_cert_list = $("#inc_info_list_1 > table > tbody:eq(3)").html();
			inc_opn_end_fund_list = $("#inc_info_list_1 > #inc_opn_end_fund > div").prop("outerHTML");
			inc_opn_end_fund_detail_list = $("#inc_info_list_1 > #inc_opn_end_fund > div > table > tbody#inc_opn_end_fund_detail_list_1").html();
			inc_monthly_tawi_list = $("#inc_monthly_tawi_list_1").prop("outerHTML");
			inc_monthly_tawi_detail_list = $("#inc_monthly_tawi_detail_list_1").html();
			inc_bank_statement_list = $("#inc_bank_statement_list_1").prop("outerHTML");
			inc_bank_statement_detail_list = $("#inc_bank_statement_detail_list_1").html();
			inc_payroll_file_info_list = $("#inc_payroll_file_info_list_1").html();
			documentList_list = $("#documentList_1 > table > tbody").html();
			reference_list = $("#reference_1 > table > tbody ").html();
			creditDetail_list = $("#creditDetail_list_1").prop("outerHTML");
			
			$.ajax({
				url : "/ServiceCenterWeb/Fico?process=defualt",
				type : "GET",
				success : function(data) {
					var obj = $.parseJSON(data);
					$("input[name=urlWebService_input]").val(obj.urlWebService);
				}
			});
		});
		
		function addPersonalApplicant(personalApplicant){
			var nextRow = $("#personalApplicant > div ").length+1;
			$("#personalApplicant").append(personalApplicant_list);
			$("#personalApplicant > div:last-child ").attr("id","personalApplicant_list_"+nextRow);
			
			//change ul li id 
			$("#personalApplicant > div:last-child > form > ul > li:eq(0) > a").attr("href", "#documentSlaType_"+nextRow);
			$("#personalApplicant > div:last-child > form > ul > li:eq(1) > a").attr("href", "#previousEmployment_"+nextRow);
			$("#personalApplicant > div:last-child > form > ul > li:eq(2) > a").attr("href", "#idAddress_"+nextRow);
			$("#personalApplicant > div:last-child > form > ul > li:eq(3) > a").attr("href", "#currentResidence_"+nextRow);
			$("#personalApplicant > div:last-child > form > ul > li:eq(4) > a").attr("href", "#person_"+nextRow);
			$("#personalApplicant > div:last-child > form > ul > li:eq(5) > a").attr("href", "#currentEmployment_"+nextRow);
			$("#personalApplicant > div:last-child > form > ul > li:eq(6) > a").attr("href", "#response_"+nextRow);
			$("#personalApplicant > div:last-child > form > ul > li:eq(7) > a").attr("href", "#inc_info_"+nextRow);
			$("#personalApplicant > div:last-child > form > ul > li:eq(8) > a").attr("href", "#documentList_"+nextRow);
			$("#personalApplicant > div:last-child > form > ul > li:eq(9) > a").attr("href", "#reference_"+nextRow);
			
			//change div id
			$("#personalApplicant > div:last-child > form > div > div > div:eq(0)").attr("id","documentSlaType_"+nextRow);
			$("#personalApplicant > div:last-child > form > div > div > div:eq(1)").attr("id","previousEmployment_"+nextRow);
			$("#personalApplicant > div:last-child > form > div > div > div:eq(2)").attr("id","idAddress_"+nextRow);
			$("#personalApplicant > div:last-child > form > div > div > div:eq(3)").attr("id","currentResidence_"+nextRow);
			$("#personalApplicant > div:last-child > form > div > div > div:eq(4)").attr("id","person_"+nextRow);
			$("#personalApplicant > div:last-child > form > div > div > div:eq(5)").attr("id","currentEmployment_"+nextRow);
			$("#personalApplicant > div:last-child > form > div > div > div:eq(6)").attr("id","response_"+nextRow);
			$("#personalApplicant > div:last-child > form > div > div > div:eq(7)").attr("id","inc_info_"+nextRow);
			$("#personalApplicant > div:last-child > form > div > div > div:eq(8)").attr("id","documentList_"+nextRow);
			$("#personalApplicant > div:last-child > form > div > div > div:eq(9)").attr("id","reference_"+nextRow);

			//set button variable
			var previousEmploymentButton = '<button type="button" class="btn btn-info" onclick="addPreviousEmployment(\'personalApplicant_list_'+nextRow+'\')">Add Previous Employment</button>';
			var currentEmploymentButton = '<button type="button" class="btn btn-info" onclick="addCurrentEmployment(\'personalApplicant_list_'+nextRow+'\')">Add Previous Employment</button>';
			var websiteVerificationResponseButton = '<button type="button" class="btn btn-info" onclick="addWebsiteVerificationResponse(\'personalApplicant_list_'+nextRow+'\')">Add Previous Employment</button>';
			var incPayslipButton = '<button type="button" class="btn btn-info" onclick="addIncPayslip(\'personalApplicant_list_'+nextRow+'\',\'inc_info_list_1\')">Add Inc Payslip</button>';
			var incPayslipMonthlyButton = '<button type="button" class="btn btn-info" onclick="addIncPayslipMonthly(\'personalApplicant_list_'+nextRow+'\',\'inc_info_list_1\',\'inc_payslip_list_1\')">Add Inc Payslip Monthly</button>';
			var incPayslipMonthlyDetailButton = '<button type="button" class="btn btn-info" onclick="addIncPayslipMonthlyDetail(\'personalApplicant_list_'+nextRow+'\',\'inc_info_list_1\',\'inc_payslip_list_1\',\'inc_payslip_monthly_list_1\')">Add Inc Payslip Monthly Detail</button>';
			var incYearlyTawiButton = '<button type="button" class="btn btn-info" onclick="addIncYearlyTawi(\'personalApplicant_list_'+nextRow+'\',\'inc_info_list_1\')">Add Yearly Tawi</button>';
			var incSavingAccButton = '<button type="button" class="btn btn-info" onclick="addIncSavingAcc(\'personalApplicant_list_'+nextRow+'\',\'inc_info_list_1\')">Add Inc Saving Acc</button>';
			var incSavingAccDetailButton = '<button type="button" class="btn btn-info" onclick="addIncSavingAccDetail(\'personalApplicant_list_'+nextRow+'\',\'inc_info_list_1\',\'inc_saving_acc_list_1\')">Add Inc Saving Acc Detail</button>';
			var incSalaryCertButton = '<button type="button" class="btn btn-info" onclick="addIncSalaryCert(\'personalApplicant_list_'+nextRow+'\',\'inc_info_list_1\')">Add Inc Salary Cert</button>';
			var incOpnEndFundButton = '<button type="button" class="btn btn-info" onclick="addIncOpnEndFund(\'personalApplicant_list_'+nextRow+'\',\'inc_info_list_1\')">Add Inc Opn End Fund</button>';
			var incOpnEndFundDetailButton = '<button type="button" class="btn btn-info" onclick="addIncOpnEndFundDetail(\'personalApplicant_list_'+nextRow+'\',\'inc_info_list_1\',\'inc_opn_end_fund_list_1\')">Add Inc Opn End Fund Detail</button>';
			var incMonthlyTawiButton = '<button type="button" class="btn btn-info" onclick="addIncMonthlyTawi(\'personalApplicant_list_'+nextRow+'\',\'inc_info_list_1\')">Add Inc Monthly Tawi</button>';
			var incMonthlyTawiDetailButton = '<button type="button" class="btn btn-info" onclick="addIncMonthlyTawiDetail(\'personalApplicant_list_'+nextRow+'\',\'inc_info_list_1\',\'inc_monthly_tawi_list_1\')">Add Inc Monthly Tawi Detail</button>';
			var incBankStatementButton = '<button type="button" class="btn btn-info" onclick="addIncBankStatement(\'personalApplicant_list_'+nextRow+'\',\'inc_info_list_1\')">Add Inc Bank Statement</button>';
			var incBankStatementDetailButton = '<button type="button" class="btn btn-info" onclick="addIncBankStatementDetail(\'personalApplicant_list_'+nextRow+'\',\'inc_info_list_1\',\'inc_bank_statement_list_1\')">Add Inc Bank Statement Detail</button>';
			var incPayrollFileInfoButton = '<button type="button" class="btn btn-info" onclick="addIncPayrollFileInfo(\'personalApplicant_list_'+nextRow+'\',\'inc_info_list_1\')">Add Inc Monthly Tawi Detail</button>';
			var incInfoButton = '<button type="button" class="btn btn-info" onclick="addIncInfo(\'personalApplicant_list_'+nextRow+'\',\'inc_info_list_1\')">Add Inc Info</button>';
			var documentListButton = '<button type="button" class="btn btn-info" onclick="addDocumentList(\'personalApplicant_list_'+nextRow+'\')">Add Previous Employment</button>';
			var referenceButton = '<button type="button" class="btn btn-info" onclick="addReference(\'personalApplicant_list_'+nextRow+'\')">Add Previous Employment</button>';
			
			//change button variable
			$("#personalApplicant > div:last-child button:eq(0)").prop("outerHTML",previousEmploymentButton);
			$("#personalApplicant > div:last-child button:eq(1)").prop("outerHTML",currentEmploymentButton);
			$("#personalApplicant > div:last-child button:eq(2)").prop("outerHTML",websiteVerificationResponseButton);
			$("#personalApplicant > div:last-child button:eq(3)").prop("outerHTML",incPayslipMonthlyDetailButton);
			$("#personalApplicant > div:last-child button:eq(4)").prop("outerHTML",incPayslipMonthlyButton);
			$("#personalApplicant > div:last-child button:eq(5)").prop("outerHTML",incPayslipButton);
			$("#personalApplicant > div:last-child button:eq(6)").prop("outerHTML",incYearlyTawiButton);
			$("#personalApplicant > div:last-child button:eq(7)").prop("outerHTML",incSavingAccDetailButton);
			$("#personalApplicant > div:last-child button:eq(8)").prop("outerHTML",incSavingAccButton);
			$("#personalApplicant > div:last-child button:eq(9)").prop("outerHTML",incSalaryCertButton);
			$("#personalApplicant > div:last-child button:eq(10)").prop("outerHTML",incOpnEndFundDetailButton);
			$("#personalApplicant > div:last-child button:eq(11)").prop("outerHTML",incOpnEndFundButton);
			$("#personalApplicant > div:last-child button:eq(12)").prop("outerHTML",incMonthlyTawiDetailButton);
			$("#personalApplicant > div:last-child button:eq(13)").prop("outerHTML",incMonthlyTawiButton);
			$("#personalApplicant > div:last-child button:eq(14)").prop("outerHTML",incBankStatementDetailButton);
			$("#personalApplicant > div:last-child button:eq(15)").prop("outerHTML",incBankStatementButton);
			$("#personalApplicant > div:last-child button:eq(16)").prop("outerHTML",incPayrollFileInfoButton);
			$("#personalApplicant > div:last-child button:eq(17)").prop("outerHTML",incInfoButton);
			$("#personalApplicant > div:last-child button:eq(18)").prop("outerHTML",documentListButton);
			$("#personalApplicant > div:last-child button:eq(19)").prop("outerHTML",referenceButton);
		}
		
		function addPreviousEmployment(personalApplicant){
			var nextRow = $("#"+personalApplicant+" #previousEmployment > table ").length+1;
			$("#"+personalApplicant+" #previousEmployment ").append(previousEmployment_list);
			$("#"+personalApplicant+" #previousEmployment > table:last-child ").attr("id","previousEmployment_list_"+nextRow);
		}
		
		function addCurrentEmployment(personalApplicant){
			var nextRow = $("#"+personalApplicant+" #currentEmployment > table ").length+1;
			$("#"+personalApplicant+" #currentEmployment ").append(currentEmployment_list);
			$("#"+personalApplicant+" #currentEmployment > table:last-child ").attr("id", "currentEmployment_list_"+nextRow);
		}
		
		function addWebsiteVerificationResponse(personalApplicant){
			$("#"+personalApplicant+" #websiteVerificationResponse > tbody ").append(websiteVerificationResponse_list);
		}
		
		function addIncInfo(personalApplicant){
			var nextRow = $("#"+personalApplicant+" #inc_info > div").length+1;
			$("#"+personalApplicant+" #inc_info").append(inc_info_list);
			$("#"+personalApplicant+" #inc_info > div:last-child").attr("id", "inc_info_list_"+nextRow);
			
			var incPayslipButton = '<button type="button" class="btn btn-info" onclick="addIncPayslip(\''+personalApplicant+'\',\'inc_info_list_'+nextRow+'\')">Add Inc Payslip</button>';
			var incPayslipMonthlyButton = '<button type="button" class="btn btn-info" onclick="addIncPayslipMonthly(\''+personalApplicant+'\',\'inc_info_list_'+nextRow+'\',\'inc_payslip_list_1\')">Add Inc Payslip Monthly</button>';
			var incPayslipMonthlyDetailButton = '<button type="button" class="btn btn-info" onclick="addIncPayslipMonthlyDetail(\''+personalApplicant+'\',\'inc_info_list_'+nextRow+'\',\'inc_payslip_list_1\',\'inc_payslip_monthly_list_1\')">Add Inc Payslip Monthly Detail</button>';
			var incYearlyTawiButton = '<button type="button" class="btn btn-info" onclick="addIncYearlyTawi(\''+personalApplicant+'\',\'inc_info_list_'+nextRow+'\')">Add Yearly Tawi</button>';
			var incSavingAccButton = '<button type="button" class="btn btn-info" onclick="addIncSavingAcc(\''+personalApplicant+'\',\'inc_info_list_'+nextRow+'\')">Add Inc Saving Acc</button>';
			var incSavingAccDetailButton = '<button type="button" class="btn btn-info" onclick="addIncSavingAcc(\''+personalApplicant+'\',\'inc_info_list_'+nextRow+'\',\'inc_saving_acc_list_1\')">Add Inc Saving Acc Detail</button>';
			var incSalaryCertButton = '<button type="button" class="btn btn-info" onclick="addIncSalaryCert(\''+personalApplicant+'\',\'inc_info_list_'+nextRow+'\')">Add Inc Salary Cert</button>';
			var incOpnEndFundButton = '<button type="button" class="btn btn-info" onclick="addIncOpnEndFund(\''+personalApplicant+'\',\'inc_info_list_'+nextRow+'\')">Add Inc Opn End Fund</button>';
			var incOpnEndFundDetailButton = '<button type="button" class="btn btn-info" onclick="addIncOpnEndFundDetail(\''+personalApplicant+'\',\'inc_info_list_'+nextRow+'\',\'inc_opn_end_fund_list_1\')">Add Inc Opn End Fund Detail</button>';
			var incMonthlyTawiButton = '<button type="button" class="btn btn-info" onclick="addIncMonthlyTawi(\''+personalApplicant+'\',\'inc_info_list_'+nextRow+'\')">Add Inc Monthly Tawi</button>';
			var incMonthlyTawiDetailButton = '<button type="button" class="btn btn-info" onclick="addIncMonthlyTawiDetail(\''+personalApplicant+'\',\'inc_info_list_'+nextRow+'\',\'inc_monthly_tawi_list_1\')">Add Inc Monthly Tawi Detail</button>';
			var incBankStatementButton = '<button type="button" class="btn btn-info" onclick="addIncBankStatement(\''+personalApplicant+'\',\'inc_info_list_'+nextRow+'\')">Add Inc Bank Statement</button>';
			var incBankStatementDetailButton = '<button type="button" class="btn btn-info" onclick="addIncBankStatementDetail(\''+personalApplicant+'\',\'inc_info_list_'+nextRow+'\',\'inc_bank_statement_list_1\')">Add Inc Bank Statement Datail</button>';
			var incPayrollFileInfoButton = '<button type="button" class="btn btn-info" onclick="addIncPayrollFileInfo(\''+personalApplicant+'\',\'inc_info_list_'+nextRow+'\')">Add Inc Payroll File Info</button>';
			var incInfoButton = '<button type="button" class="btn btn-info" onclick="addIncInfo(\''+personalApplicant+'\',\'inc_info_list_'+nextRow+'\')">Add Inc Info</button>';
			
			$("#"+personalApplicant+" #inc_info > div:last-child button:eq(0)").prop("outerHTML", incPayslipMonthlyDetailButton);
			$("#"+personalApplicant+" #inc_info > div:last-child button:eq(1)").prop("outerHTML", incPayslipMonthlyButton);
			$("#"+personalApplicant+" #inc_info > div:last-child button:eq(2)").prop("outerHTML",incPayslipButton);
			$("#"+personalApplicant+" #inc_info > div:last-child button:eq(3)").prop("outerHTML",incYearlyTawiButton);
			$("#"+personalApplicant+" #inc_info > div:last-child button:eq(4)").prop("outerHTML",incSavingAccDetailButton);
			$("#"+personalApplicant+" #inc_info > div:last-child button:eq(5)").prop("outerHTML",incSavingAccButton);
			$("#"+personalApplicant+" #inc_info > div:last-child button:eq(6)").prop("outerHTML",incSalaryCertButton);
			$("#"+personalApplicant+" #inc_info > div:last-child button:eq(7)").prop("outerHTML",incOpnEndFundDetailButton);
			$("#"+personalApplicant+" #inc_info > div:last-child button:eq(8)").prop("outerHTML",incOpnEndFundButton);
			$("#"+personalApplicant+" #inc_info > div:last-child button:eq(9)").prop("outerHTML",incMonthlyTawiDetailButton);
			$("#"+personalApplicant+" #inc_info > div:last-child button:eq(10)").prop("outerHTML",incMonthlyTawiButton);
			$("#"+personalApplicant+" #inc_info > div:last-child button:eq(11)").prop("outerHTML",incBankStatementDetailButton);
			$("#"+personalApplicant+" #inc_info > div:last-child button:eq(12)").prop("outerHTML",incBankStatementButton);
			$("#"+personalApplicant+" #inc_info > div:last-child button:eq(13)").prop("outerHTML",incPayrollFileInfoButton);
			$("#"+personalApplicant+" #inc_info > div:last-child button:eq(14)").prop("outerHTML",incInfoButton);
			
		}
		
		function addIncPayslip(personalApplicant,inc_info){
			var nextRow = $("#"+personalApplicant+" #inc_payslip").length+1;
			$("#"+personalApplicant+" #"+inc_info+" #inc_payslip").append(inc_payslip_list);
			$("#"+personalApplicant+" #"+inc_info+" #inc_payslip > div:last-child").attr("id","inc_payslip_list_"+nextRow);
			
			var incPayslipMonthlyDetailButton = '<button type="button" class="btn btn-info" onclick="addIncPayslipMonthlyDetail(\''+personalApplicant+'\',\''+inc_info+'\',\'inc_payslip_list_'+nextRow+'\',\'inc_payslip_monthly_list_1\')">Add Inc Payslip Monthly Detail</button>';
			var incPayslipMonthlyButton = '<button type="button" class="btn btn-info" onclick="addIncPayslipMonthly(\''+personalApplicant+'\',\''+inc_info+'\',\'inc_payslip_list_'+nextRow+'\')">Add Inc Payslip Monthly</button>';
			$("#"+personalApplicant+" #"+inc_info+" > div >div:last-child button:eq(0)").prop("outerHTML", incPayslipMonthlyDetailButton);
			$("#"+personalApplicant+" #"+inc_info+" > div > div:last-child button:eq(1)").prop("outerHTML", incPayslipMonthlyButton);
		}
		
		function addIncPayslipMonthly(personalApplicant,inc_info,inc_payslip){
			var nextRow = $("#"+personalApplicant+" #"+inc_info+" #"+inc_payslip+" #inc_payslip ").length+1;
			$("#"+personalApplicant+" #"+inc_info+" #"+inc_payslip+" #inc_payslip_monthly").append(inc_payslip_monthly_list);
			$("#"+personalApplicant+" #"+inc_info+" #"+inc_payslip+" #inc_payslip_monthly > div:last-child").attr("id","inc_payslip_monthly_list_"+nextRow);
			var nextDetailRow = $("#"+personalApplicant+" #"+inc_info+" #"+inc_payslip+" #inc_payslip_monthly > div").length;
			$("#"+personalApplicant+" #"+inc_info+" #"+inc_payslip+" > #inc_payslip_monthly > div:last-child").attr("id","inc_payslip_monthly_list_"+nextDetailRow);
			var incPayslipMonthlyDetailButton = '<button type="button" class="btn btn-info" onclick="addIncPayslipMonthlyDetail(\''+personalApplicant+'\',\''+inc_info+'\',\''+inc_payslip+'\',\'inc_payslip_monthly_list_'+nextDetailRow+'\')">Add Inc Payslip Monthly Detail</button>';
			$("#"+personalApplicant+" #"+inc_info+" #"+inc_payslip+" > #inc_payslip_monthly > div:last-child > button ").prop("outerHTML", incPayslipMonthlyDetailButton);
		}
		
		function addIncPayslipMonthlyDetail(personalApplicant,inc_info,inc_payslip,incPayslipMonthly){
			$("#"+personalApplicant+" #"+inc_info+" #"+inc_payslip+" #"+incPayslipMonthly+" > div > table > tbody").append(inc_payslip_monthly_detail_list);
		}
		
		function addIncYearlyTawi(personalApplicant,inc_info){
			$("#"+personalApplicant+" #"+inc_info+" > table > tbody:eq(1)").append(inc_yearly_tawi);
		}
		
		function addIncSavingAcc(personalApplicant,inc_info){
			$("#"+personalApplicant+" #"+inc_info+" > #inc_saving_acc ").append(inc_saving_acc_list);
			
			var nextRow = $("#"+personalApplicant+" #"+inc_info+" #inc_saving_acc > div").length;
			$("#"+personalApplicant+" #"+inc_info+" #inc_saving_acc > div:last-child").attr("id","inc_saving_acc_list_"+nextRow);
			var incSavingAccDetailButton = '<button type="button" class="btn btn-info" onclick="addIncSavingAccDetail(\''+personalApplicant+'\',\''+inc_info+'\',\'inc_saving_acc_list_'+nextRow+'\')">Add Inc Saving Acc Detail</button>';
			$("#"+personalApplicant+" #"+inc_info+" #inc_saving_acc > div:last-child button").prop("outerHTML", incSavingAccDetailButton);
		}
		
		function addIncSavingAccDetail(personalApplicant,inc_info,incSavingAcc){
			$("#"+personalApplicant+" #"+inc_info+" #"+incSavingAcc+" #inc_saving_acc_detail_list_1").append(inc_saving_acc_detail_list);
		}
		
		function addIncSalaryCert(personalApplicant,inc_info){
			$("#"+personalApplicant+" #"+inc_info+" > table > tbody:eq(3)").append(inc_salary_cert_list);
		}
		
		function addIncOpnEndFund(personalApplicant,inc_info){
			var nextRow = $("#"+personalApplicant+" #"+inc_info+" #inc_opn_end_fund > div").length+1;
			$("#"+personalApplicant+" #"+inc_info+" #inc_opn_end_fund ").append(inc_opn_end_fund_list);
			$("#"+personalApplicant+" #"+inc_info+" #inc_opn_end_fund > div:last-child").attr("id","inc_opn_end_fund_list_"+nextRow);
			
			var incOpnEndFundDetailButton = '<button type="button" class="btn btn-info" onclick="addIncOpnEndFundDetail(\''+personalApplicant+'\',\''+inc_info+'\',\'inc_opn_end_fund_list_'+nextRow+'\')">Add Inc Opn End Fund Detail</button>';
			$("#"+personalApplicant+" #"+inc_info+" #inc_opn_end_fund > div:last-child button").prop("outerHTML", incOpnEndFundDetailButton);
		}
		
		function addIncOpnEndFundDetail(personalApplicant,inc_info,inc_opn_end_fund){
			$("#"+personalApplicant+" #"+inc_info+" #"+inc_opn_end_fund+" > table:eq(1) > tbody").append(inc_opn_end_fund_detail_list);
		}
		
		function addIncMonthlyTawi(personalApplicant,inc_info){
			var nextRow = $("#"+personalApplicant+" #"+inc_info+" #inc_monthly_tawi > div").length+1;
			$("#"+personalApplicant+" #"+inc_info+" #inc_monthly_tawi").append(inc_monthly_tawi_list);
			$("#"+personalApplicant+" #"+inc_info+" #inc_monthly_tawi > div:last-child").attr("id","inc_monthly_tawi_list_"+nextRow);
			var nextDetailRow = $("#"+personalApplicant+" #"+inc_info+" #"+inc_payslip+" #inc_payslip_monthly > div").length;
			$("#"+personalApplicant+" #"+inc_info+" #"+inc_payslip+" > #inc_payslip_monthly > div:last-child").attr("id","inc_payslip_monthly_list_"+nextDetailRow);
			var incMonthlyTawiDetailButton = '<button type="button" class="btn btn-info" onclick="addIncMonthlyTawiDetail(\''+personalApplicant+'\',\''+inc_info+'\',\'inc_monthly_tawi_list_'+nextRow+'\')">Add Inc Monthly Tawi Detail</button>';
			$("#"+personalApplicant+" #"+inc_info+" #inc_monthly_tawi > div:last-child  button").prop("outerHTML", incMonthlyTawiDetailButton);
		}
		
		function addIncMonthlyTawiDetail(personalApplicant,inc_info,inc_monthly_tawi){
			$("#"+personalApplicant+" #"+inc_info+" #"+inc_monthly_tawi+" > table:eq(1) > tbody").append(inc_monthly_tawi_detail_list);
		}
		
		function addIncBankStatement(personalApplicant,inc_info){
			$("#"+personalApplicant+" #"+inc_info+" > #inc_bank_statement ").append(inc_bank_statement_list);

			var nextRow = $("#"+personalApplicant+" #"+inc_info+" #inc_bank_statement > div").length;
			$("#"+personalApplicant+" #"+inc_info+" #inc_bank_statement > div:last-child").attr("id","inc_bank_statement_list_"+nextRow);
			var incBankStatementDetailButton = '<button type="button" class="btn btn-info" onclick="addIncBankStatementDetail(\''+personalApplicant+'\',\''+inc_info+'\',\'inc_bank_statement_list_'+nextRow+'\')">Add Inc Bank Statement Detail</button>';
			$("#"+personalApplicant+" #"+inc_info+" #inc_bank_statement > div:last-child button").prop("outerHTML", incBankStatementDetailButton);
		}
		
		function addIncBankStatementDetail(personalApplicant,inc_info,inc_bank_statement){
			$("#"+personalApplicant+" #"+inc_info+" #"+inc_bank_statement+" > table:eq(1) > tbody").append(inc_bank_statement_detail_list);
		}
		
		function addIncPayrollFileInfo(personalApplicant,inc_info){
			$("#"+personalApplicant+" #"+inc_info+" #inc_payroll_file_info_list_1").append(inc_payroll_file_info_list);			
		}
		
		function addDocumentList(personalApplicant){
			$("#"+personalApplicant+" #documentList > tbody ").append(documentList_list);
		}
		
		function addReference(personalApplicant){
			$("#"+personalApplicant+" #reference > tbody ").append(reference_list);
		}
		
		function addCreditDetails(){
			var nextRow = $("#creditDetail > div").length+1;
			$("#creditDetail").append(creditDetail_list);
			$("#creditDetail > div:last-child").attr("id","creditDetail_list_"+nextRow);
			
			var creditDetailsButton = '<button type="button" class="btn btn-info" onclick="addAssociatedApplicant(\'creditDetail_list_'+nextRow+'\')">Add Associated Applicant</button>';
			$("#creditDetail > div:last-child button").prop("outerHTML",creditDetailsButton);
		}
		
		function addAssociatedApplicant(creditDetail){
			var nextRow = $("#"+creditDetail+" #associatedApplicant_list_1 > tr").length+1;
			var htmlBuild = "<tr>";
			htmlBuild += "<td>"+nextRow+"</td>";
			htmlBuild += '<td><input type="text" class="form-control input-sm" size="60" name="associatedApplicant_applicantType_input"></td>';
			htmlBuild += "</tr>";
			$("#"+creditDetail+" #associatedApplicant_list_1").append(htmlBuild);
		}
		
		function request_service(){
			var personalAppList = [];
			var personalAppObj = {};
			var personalAppLength = $("#personalApplicant > div").length;
			for(var i=0;i<personalAppLength;i++){
				personalAppObj = $("#personalApplicant > #personalApplicant_list_"+(i+1)+" table:eq(0) input").serializeFormJSON();
				personalAppObj.documentSlaType = $("#documentSlaType_"+(i+1)+" input").serializeFormJSON();
				
				var previousEmploymentLength = $("#previousEmployment_"+(i+1)+" > div > table").length;
				var previousEmploymentList = [];
				for(var j=0;j<previousEmploymentLength;j++){
					previousEmploymentList.push($("#previousEmployment_"+(i+1)+" #previousEmployment_list_"+(j+1)+" input").serializeFormJSON());
				}
				personalAppObj.previousEmployment = previousEmploymentList;
				
				personalAppObj.idAddress = $("#idAddress_"+(i+1)+" input").serializeFormJSON();
				personalAppObj.currentResidence = $("#currentResidence_"+(i+1)+" input").serializeFormJSON();
				personalAppObj.person = $("#person_"+(i+1)+" input").serializeFormJSON();
				
				var currentEmploymentLength = $("#currentEmployment_"+(i+1)+" > div > table").length;
				var currentEmploymentList = [];
				for(var j=0;j<currentEmploymentLength;j++){
					currentEmploymentList.push($("#currentEmployment_"+(i+1)+" #currentEmployment_list_"+(j+1)+" input").serializeFormJSON());
				}
				personalAppObj.currentEmployment = currentEmploymentList;
				
				personalAppObj.response = $("#response_"+(i+1)+" input").serializeFormJSON();
				var incInfoLength = $("#inc_info_"+(i+1)+" #inc_info > div").length;
				var incInfoList = [];
				var incInfo = {};
				for(var j=0;j<incInfoLength;j++){
					incInfo = $("#inc_info_"+(i+1)+" #inc_info_list_"+(j+1)+" > table > tbody > tr > td > input[name=inc_info_income_type_input]").serializeFormJSON();
					
					var payslipLength = $("#inc_info_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_payslip > div").length;
					var payslipList = [];
					var payslip = {};
					for(var k=0;k<payslipLength;k++){
						payslip = $("#inc_info_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_payslip_list_"+(k+1)+" > table > tbody > tr > td > input").serializeFormJSON();
						var payslipMonthlyLength = $("#inc_info_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_payslip_list_"+(k+1)+" #inc_payslip_monthly > div ").length;
						var payslipMonthlyList = [];
						var payslipMonthly = {};
						for(var l=0;l<payslipMonthlyLength;l++){
							payslipMonthly = $("#inc_info_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_payslip_list_"+(k+1)+" #inc_payslip_monthly_list_"+(l+1)+" > table > tbody input").serializeFormJSON();
							payslipMonthly.detail = $("#inc_info_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_payslip_list_"+(k+1)+" #inc_payslip_monthly_list_"+(l+1)+" #inc_payslip_monthly_detail_list_1 > tr > td > input").serializeFormJSON();
							payslipMonthlyList.push(payslipMonthly);
						}
						payslip.monthly = payslipMonthlyList;
						payslipList.push(payslip);
					}
					incInfo.payslip = payslipList;
					
					incInfo.yearlyTawi = $("#inc_info_"+(i+1)+" #inc_info_list_"+(j+1)+" > table > #inc_yearly_tawi > tr > td > input").serializeFormJSON();
					incInfo.taweesap = $("#inc_info_"+(i+1)+" #inc_info_list_"+(j+1)+" > table > tbody > tr > td > input[name=inc_taweesap_aum_input]").serializeFormJSON();
					var savingAccLength = $("#inc_info_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_saving_acc > div").length;
					var savingAccList = [];
					var savingAcc = {};
					for(var k=0;k<savingAccLength;k++){
						savingAcc = $("#inc_info_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_saving_acc_list_"+(k+1)+" > tr > td > input").serializeFormJSON();
						savingAcc.detail = $("#inc_info_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_saving_acc_list_"+(k+1)+" #inc_saving_acc_detail_list_1 > tr > td > input").serializeFormJSON();
						savingAccList.push(savingAcc);
					}
					incInfo.savingAcc = savingAccList;
					
					incInfo.salaryCert = $("#inc_info_"+(i+1)+" #inc_info_list_"+(j+1)+" > table > #inc_salary_cert_list_1 > tr > td > input").serializeFormJSON();
					incInfo.previousIncome = $("#inc_info_"+(i+1)+" #inc_info_list_"+(j+1)+" > table > tbody > tr > td > input[name=inc_previous_income_income_input]").serializeFormJSON();
					incInfo.payroll = $("#inc_info_"+(i+1)+" #inc_info_list_"+(j+1)+" > table:eq(5) > tbody > tr > td > input").serializeFormJSON();
					
					var opnEndFundLength = $("#inc_info_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_payslip > div").length;
					var opnEndFundList = [];
					var opnEndFund = {};
					for(var k=0;k<opnEndFundLength;k++){						
						opnEndFund = $("#inc_info_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_opn_end_fund_list_"+(k+1)+" > table:eq(0) > tbody > tr > td > input").serializeFormJSON();
						opnEndFund.detail = $("#inc_info_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_opn_end_fund_list_"+(k+1)+" #inc_opn_end_fund_detail_list_1 > tr > td > input").serializeFormJSON();
						opnEndFundList.push(opnEndFund);
					}
					incInfo.opnEndFund = opnEndFundList;
					
					var monthlyTawiLength = $("#inc_info_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_monthly_tawi > div").length;
					var monthlyTawiList = [];
					var monthlyTawi = {};
					for(var k=0;k<monthlyTawiLength;k++){						
						monthlyTawi = $("#inc_info_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_monthly_tawi_list_"+(k+1)+" > table:eq(0) > tbody > tr > td > input").serializeFormJSON();
						monthlyTawi.detail = $("#inc_info_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_monthly_tawi_list_"+(k+1)+" #inc_monthly_tawi_detail_list_1 > tr > td > input").serializeFormJSON();
						monthlyTawiList.push(monthlyTawi);
					}
					incInfo.monthlyTawi = monthlyTawiList;
					
					incInfo.kvi = $("#inc_info_"+(i+1)+" #inc_info_list_"+(j+1)+" > table:eq(6) > tbody > tr > td > input").serializeFormJSON();
					incInfo.fixedGuarantee = $("#inc_info_"+(i+1)+" #inc_info_list_"+(j+1)+" > table:eq(7) > tbody > tr > td > input").serializeFormJSON();
					incInfo.fixedAcc = $("#inc_info_"+(i+1)+" #inc_info_list_"+(j+1)+" > table:eq(8) > tbody > tr > td > input").serializeFormJSON();
					incInfo.finInstrument = $("#inc_info_"+(i+1)+" #inc_info_list_"+(j+1)+" > table:eq(9) > tbody > tr > td > input").serializeFormJSON();
					incInfo.clsEndFund = $("#inc_info_"+(i+1)+" #inc_info_list_"+(j+1)+" > table:eq(10) > tbody > tr > td > input").serializeFormJSON();
					
					var bankStatementLength = $("#inc_info_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_bank_statement > div").length;
					var bankStatementList = [];
					var bankStatement = {};
					for(var k=0;k<bankStatementLength;k++){
						bankStatement = $("#inc_info_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_bank_statement_list_"+(k+1)+" > table:eq(0) > tbody > tr > td > input").serializeFormJSON();
						bankStatement.detail = $("#inc_info_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_bank_statement_list_"+(k+1)+" > table:eq(1) > tbody > tr > td > input").serializeFormJSON();
						bankStatementList.push(bankStatement);
					}
					incInfo.bankStatement = bankStatementList;
					
					incInfo.bundleSME = $("#inc_info_"+(i+1)+" #inc_info_list_"+(j+1)+" > table:eq(11) > tbody > tr > td > input").serializeFormJSON();
					incInfo.bundleKL = $("#inc_info_"+(i+1)+" #inc_info_list_"+(j+1)+" > table:eq(12) > tbody > tr > td > input").serializeFormJSON();
					incInfo.bundleHL = $("#inc_info_"+(i+1)+" #inc_info_list_"+(j+1)+" > table:eq(13) > tbody > tr > td > input").serializeFormJSON();
					incInfo.payrollFile = $("#inc_info_"+(i+1)+" #inc_info_list_"+(j+1)+" > table:eq(14) > tbody > tr > td > input").serializeFormJSON();					
					
					incInfoList.push(incInfo);
				}
				personalAppObj.incInfo = incInfoList;
				personalAppObj.documentList = $("#documentList_"+(i+1)+" input").serializeFormJSON();
				personalAppObj.reference = $("#reference_"+(i+1)+" input").serializeFormJSON();
				personalAppList.push(JSON.stringify(personalAppObj));
			}
			
			var creditDetail = {};
			var creditDetailList = [];
			var creditDetailLength = $("#creditDetail > div").length;
			for(var i=0;i<creditDetailLength;i++){
				creditDetail = $("#creditDetail > #creditDetail_list_"+(i+1)+" table:eq(0) > tbody > tr > td > input").serializeFormJSON();
				creditDetail.applicant = $("#creditDetail > #creditDetail_list_"+(i+1)+" table:eq(1) > tbody > tr > td > input").serializeFormJSON();
				creditDetailList.push(JSON.stringify(creditDetail));
			}
			
			console.log(personalAppList);
			console.log(creditDetailList);
			var input = $("table:eq(1) input").serialize();
			var priorityPassQuota = $("table:eq(40) input").serialize();
			$.ajax({
				url : "/ServiceCenterWeb/Fico?process=request&"+input+"&"+priorityPassQuota+"&decision_station="+$('select[name=decision_station]').val()+"&applicationGroupId_input="+$('input[name=applicationGroupId_input]').val(),
				type : "get",
				dataType : "json",
				data : {personalApp : personalAppList, creditDetail : creditDetailList},
				success : function(data){
					console.log("data >> "+data);
// 					var obj = $.parseJSON(data);
// 					console.log("obj >> "+obj);
					$("textarea[name=requestJson]").val(data.jsonRq);
					$("textarea[name=responseJson]").val(data.jsonRs);
				}
			});
		}
		
		function loadData(){
			$.ajax({
				url : "/ServiceCenterWeb/Fico?process=loadData",
				type : "get",
				data : $("#applicationGroupForm").serialize(),
				success : function(data){
					console.clear();
					var obj = $.parseJSON(data);
					console.log(obj);
					//input
					$("input[name=applicationId_input]").val(obj.applicationGroupId);
					$("input[name=templateCode_input]").val(obj.applicationTemplate);
					$("input[name=isVETO_input]").val(obj.vetoRemark);
					$("input[name=testLogicalDate_input]").val();
// 					$("input[name=applicationCreateDate_input]").val(obj.applicationDate);
					$("input[name=channelCode_input]").val(obj.applyChannel);
					$("input[name=strategySelRandomNum1_input]").val();
					$("input[name=isFraudApplication_input]").val();
					$("input[name=isFraudCompany_input]").val();
					
					if(obj.personalInfos!=null){
						for(var i=0;i<obj.personalInfos.length;i++){
							if(i>0)	addPersonalApplicant('personalApplicant_list_1');
							//personalApplicant
							$("#personalApplicant_list_"+(i+1)+" input[name=idType_input]").val(obj.personalInfos[i].personalType);
							$("#personalApplicant_list_"+(i+1)+" input[name=idNumber_input]").val(obj.personalInfos[i].personalId);
							$("#personalApplicant_list_"+(i+1)+" input[name=cisID_input]").val(obj.personalInfos[i].idno);
							$("#personalApplicant_list_"+(i+1)+" input[name=applicantType_input]").val(obj.applicationType);
							$("#personalApplicant_list_"+(i+1)+" input[name=educationLevel_input]").val(obj.personalInfos[i].degree);
							$("#personalApplicant_list_"+(i+1)+" input[name=isKGroupStaff_input]").val(obj.personalInfos[i].kGroupFlag);
							if(obj.personalInfos[i].specialAdditionalServices!=null)
								$("#personalApplicant_list_"+(i+1)+" input[name=isInformationComplete_input]").val(obj.personalInfos[i].specialAdditionalServices[0].completeData);
							$("#personalApplicant_list_"+(i+1)+" input[name=cisPrimarySegment_input]").val(obj.personalInfos[i].cisPrimSegment);
							$("#personalApplicant_list_"+(i+1)+" input[name=cisPrimarySubSegment_input]").val(obj.personalInfos[i].cisPrimSubSegment);
							$("#personalApplicant_list_"+(i+1)+" input[name=cisSecondarySegment_input]").val(obj.personalInfos[i].cisSecSegment);
							$("#personalApplicant_list_"+(i+1)+" input[name=cisSecondarySubSegment_input]").val(obj.personalInfos[i].cisSecSubSegment);
							$("#personalApplicant_list_"+(i+1)+" input[name=incomeReceiveCode_input]").val(obj.personalInfos[i].receiveIncomeMethod);
							
							//Document SLA Type
							$("#personalApplicant_list_"+(i+1)+" input[name=code_input]").val();

							//Previous Employment
							$("#personalApplicant_list_"+(i+1)+" input[name=previousEmploymentYears_input]").val(obj.personalInfos[i].prevWorkYear);
							$("#personalApplicant_list_"+(i+1)+" input[name=previousEmploymentMonths_input]").val(obj.personalInfos[i].prevWorkMonth);
							
							//ID Address, Current Residence, Current Employment
							if(obj.personalInfos[i].addresses!=null){
								for(var j=0;j<obj.personalInfos[i].addresses.length;j++){
									if(obj.personalInfos[i].addresses[j].addressType=="03"){
										$("#personalApplicant_list_"+(i+1)+" input[name=IDAddress_address_houseNumber_input]").val(obj.personalInfos[i].addresses[j].address);
										$("#personalApplicant_list_"+(i+1)+" input[name=IDAddress_address_village_input]").val(obj.personalInfos[i].addresses[j].vilapt);
										$("#personalApplicant_list_"+(i+1)+" input[name=IDAddress_address_buildingName_input]").val(obj.personalInfos[i].addresses[j].building);
										$("#personalApplicant_list_"+(i+1)+" input[name=IDAddress_address_subDistrict_input]").val(obj.personalInfos[i].addresses[j].tambol);
										$("#personalApplicant_list_"+(i+1)+" input[name=IDAddress_address_district_input]").val(obj.personalInfos[i].addresses[j].amphur);
										$("#personalApplicant_list_"+(i+1)+" input[name=IDAddress_address_province_input]").val(obj.personalInfos[i].addresses[j].provinceDesc);
										$("#personalApplicant_list_"+(i+1)+" input[name=IDAddress_address_postalCode_input]").val(obj.personalInfos[i].addresses[j].zipcode);
										$("#personalApplicant_list_"+(i+1)+" input[name=IDAddress_address_country_input]").val(obj.personalInfos[i].addresses[j].country);
										$("#personalApplicant_list_"+(i+1)+" input[name=IDAddress_address_fixedLineNumber_input]").val(obj.personalInfos[i].addresses[j].phone1);
										$("#personalApplicant_list_"+(i+1)+" input[name=IDAddress_address_floorNumber_input]").val(obj.personalInfos[i].addresses[j].floor);
										$("#personalApplicant_list_"+(i+1)+" input[name=IDAddress_address_moo_input]").val(obj.personalInfos[i].addresses[j].moo);
										$("#personalApplicant_list_"+(i+1)+" input[name=IDAddress_address_soi_input]").val(obj.personalInfos[i].addresses[j].soi);
										$("#personalApplicant_list_"+(i+1)+" input[name=IDAddress_address_street_input]").val(obj.personalInfos[i].addresses[j].road);
									}else if(obj.personalInfos[i].addresses[j].addressType=="01"){
										$("#personalApplicant_list_"+(i+1)+" input[name=currentResidence_monthsAtAddress_input]").val(obj.personalInfos[i].addresses[j].residem);
										$("#personalApplicant_list_"+(i+1)+" input[name=currentResidence_yearsAtAddress_input]").val(obj.personalInfos[i].addresses[j].residey);
										$("#personalApplicant_list_"+(i+1)+" input[name=currentResidence_currentResidentialStatus_input]").val(obj.personalInfos[i].addresses[j].adrsts);
										$("#personalApplicant_list_"+(i+1)+" input[name=currentResidence_address_houseNumber_input]").val(obj.personalInfos[i].addresses[j].addressId);
										$("#personalApplicant_list_"+(i+1)+" input[name=currentResidence_address_village_input]").val(obj.personalInfos[i].addresses[j].vilapt);
										$("#personalApplicant_list_"+(i+1)+" input[name=currentResidence_address_buildingName_input]").val(obj.personalInfos[i].addresses[j].building);
										$("#personalApplicant_list_"+(i+1)+" input[name=currentResidence_address_subDistrict_input]").val(obj.personalInfos[i].addresses[j].amphur);
										$("#personalApplicant_list_"+(i+1)+" input[name=currentResidence_address_district_input]").val(obj.personalInfos[i].addresses[j].tambol);
										$("#personalApplicant_list_"+(i+1)+" input[name=currentResidence_address_province_input]").val(obj.personalInfos[i].addresses[j].provinceDesc);
										$("#personalApplicant_list_"+(i+1)+" input[name=currentResidence_address_postalCode_input]").val(obj.personalInfos[i].addresses[j].zipcode);
										$("#personalApplicant_list_"+(i+1)+" input[name=currentResidence_address_country_input]").val(obj.personalInfos[i].addresses[j].country);
										$("#personalApplicant_list_"+(i+1)+" input[name=currentResidence_address_fixedLineNumber_input]").val(obj.personalInfos[i].addresses[j].phone1);
										$("#personalApplicant_list_"+(i+1)+" input[name=currentResidence_address_type_input]").val(obj.personalInfos[i].addresses[j].addressType);
										$("#personalApplicant_list_"+(i+1)+" input[name=currentResidence_address_floorNumber_input]").val(obj.personalInfos[i].addresses[j].floor);
										$("#personalApplicant_list_"+(i+1)+" input[name=currentResidence_address_moo_input]").val(obj.personalInfos[i].addresses[j].moo);
										$("#personalApplicant_list_"+(i+1)+" input[name=currentResidence_address_soi_input]").val(obj.personalInfos[i].addresses[j].soi);
										$("#personalApplicant_list_"+(i+1)+" input[name=currentResidence_address_street_input]").val(obj.personalInfos[i].addresses[j].road);
									}else if(obj.personalInfos[i].addresses[j].addressType=="02"){
										$("#personalApplicant_list_"+(i+1)+" input[name=currentEmployment_employment_monthsWithEmployer_input]").val();
										$("#personalApplicant_list_"+(i+1)+" input[name=currentEmployment_employment_occupationCode_input]").val();
										$("#personalApplicant_list_"+(i+1)+" input[name=currentEmployment_employment_professionCode_input]").val(obj.personalInfos[i].addresses[j].profession);
										$("#personalApplicant_list_"+(i+1)+" input[name=currentEmployment_employment_positionType_input]").val(obj.personalInfos[i].addresses[j].positionCode);
										$("#personalApplicant_list_"+(i+1)+" input[name=currentEmployment_employment_employerTitleCode_input]").val();
										$("#personalApplicant_list_"+(i+1)+" input[name=currentEmployment_employment_currentEmploymentYears_input]").val();
										$("#personalApplicant_list_"+(i+1)+" input[name=currentEmployment_employment_companyName_input]").val(obj.personalInfos[i].addresses[j].companyName);
										$("#personalApplicant_list_"+(i+1)+" input[name=currentEmployment_employment_employerAddress_address_houseNumber_input]").val(obj.personalInfos[i].addresses[j].addressId);
										$("#personalApplicant_list_"+(i+1)+" input[name=currentEmployment_employment_employerAddress_address_village_input]").val(obj.personalInfos[i].addresses[j].vilapt);
										$("#personalApplicant_list_"+(i+1)+" input[name=currentEmployment_employment_employerAddress_address_buildingName_input]").val(obj.personalInfos[i].addresses[j].building);
										$("#personalApplicant_list_"+(i+1)+" input[name=currentEmployment_employment_employerAddress_address_subDistrict_input]").val(obj.personalInfos[i].addresses[j].amphur);
										$("#personalApplicant_list_"+(i+1)+" input[name=currentEmployment_employment_employerAddress_address_district_input]").val(obj.personalInfos[i].addresses[j].tambol);
										$("#personalApplicant_list_"+(i+1)+" input[name=currentEmployment_employment_employerAddress_address_province_input]").val(obj.personalInfos[i].addresses[j].provinceDesc);
										$("#personalApplicant_list_"+(i+1)+" input[name=currentEmployment_employment_employerAddress_address_postalCode_input]").val(obj.personalInfos[i].addresses[j].zipcode);
										$("#personalApplicant_list_"+(i+1)+" input[name=currentEmployment_employment_employerAddress_address_country_input]").val(obj.personalInfos[i].addresses[j].country);
										$("#personalApplicant_list_"+(i+1)+" input[name=currentEmployment_employment_employerAddress_address_fixedLineNumber_input]").val(obj.personalInfos[i].addresses[j].phone1);
										$("#personalApplicant_list_"+(i+1)+" input[name=currentEmployment_employment_employerAddress_address_type_input]").val(obj.personalInfos[i].addresses[j].addressType);
										$("#personalApplicant_list_"+(i+1)+" input[name=currentEmployment_employment_employerAddress_address_floorNumber_input]").val(obj.personalInfos[i].addresses[j].floor);
										$("#personalApplicant_list_"+(i+1)+" input[name=currentEmployment_employment_employerAddress_address_moo_input]").val(obj.personalInfos[i].addresses[j].moo);
										$("#personalApplicant_list_"+(i+1)+" input[name=currentEmployment_employment_employerAddress_address_soi_input]").val(obj.personalInfos[i].addresses[j].soi);
										$("#personalApplicant_list_"+(i+1)+" input[name=currentEmployment_employment_employerAddress_address_street_input]").val(obj.personalInfos[i].addresses[j].road);
									}
								}
							}
							
							//Person
// 							$("#personalApplicant_list_"+(i+1)+" input[name=person_birthDate_input]").val(obj.personalInfos[i].birthDate);
							$("#personalApplicant_list_"+(i+1)+" input[name=person_numberOfDependents_input]").val(obj.personalInfos[i].noOfChild);
							$("#personalApplicant_list_"+(i+1)+" input[name=person_maritalStatus_input]").val(obj.personalInfos[i].married);
							$("#personalApplicant_list_"+(i+1)+" input[name=person_phoneNumber_input]").val(obj.personalInfos[i].mobileNo);
							$("#personalApplicant_list_"+(i+1)+" input[name=person_nationality_input]").val(obj.personalInfos[i].nationality);
							if(obj.personalInfos[i].debtInfos!=null){
								console.log(obj.personalInfos[i].debtInfos);
								var debtLength = obj.personalInfos[i].debtInfos.length;
								for(var j=0;j<debtLength;j++){
									if(obj.personalInfos[i].debtInfos[j].debtType == "02"){
										$("#personalApplicant_list_"+(i+1)+" input[name=otherDebt_commercial_amount_input]").val(obj.personalInfos[i].debtInfos[j].debtAmt);
									}else if(obj.personalInfos[i].debtInfos[j].debtType == "01"){
										$("#personalApplicant_list_"+(i+1)+" input[name=otherDebt_welfate_amount_input]").val(obj.personalInfos[i].debtInfos[j].debtAmt);
									}else if(obj.personalInfos[i].debtInfos[j].debtType == "99"){
										$("#personalApplicant_list_"+(i+1)+" input[name=otherDebt_other_debt_amount_input]").val(obj.personalInfos[i].debtInfos[j].debtAmt);
									}
								}
							}
							$("#personalApplicant_list_"+(i+1)+" input[name=person_name_prefix_input]").val(obj.personalInfos[i].thTitleCode);
							$("#personalApplicant_list_"+(i+1)+" input[name=person_name_first_input]").val(obj.personalInfos[i].thFirstName);
							$("#personalApplicant_list_"+(i+1)+" input[name=person_name_middle_input]").val(obj.personalInfos[i].thMidName);
							$("#personalApplicant_list_"+(i+1)+" input[name=person_name_last_input]").val(obj.personalInfos[i].thLastName);
							
							//Response
							if(obj.personalInfos[i].verificationResult!=null){
								$("#personalApplicant_list_"+(i+1)+" input[name=HRVerificationResponse_HRVerificationResult_input]").val(obj.personalInfos[i].verificationResult.verHrResultCode);
								$("#personalApplicant_list_"+(i+1)+" input[name=customerVerificationResponse_customerVerificationResult_input]").val(obj.personalInfos[i].verificationResult.verCusResultCode);
								if(obj.personalInfos[i].verificationResult.webVerification!=null){
									var webVerificationLength = obj.personalInfos[i].verificationResult.webVerification.length;
									for(var j=0;j<webVerificationLength;j++){
										addWebsiteVerificationResponse('personalApplicant_list_1');
										$("#personalApplicant_list_"+(i+1)+" input[name=websiteVerificationResponse_websiteCode_input]").val(obj.personalInfos[i].verificationResult.webVerification[j].webCode);
										$("#personalApplicant_list_"+(i+1)+" input[name=websiteVerificationResponse_websiteVerificationResult_input]").val(obj.personalInfos[i].verificationResult.webVerification[j].verResultId);
									}
								}
							}
							$("#personalApplicant_list_"+(i+1)+" input[name=HRVerificationResponse_bonus_input]").val(obj.personalInfos[i].hrBonus);
// 							$("#personalApplicant_list_"+(i+1)+" input[name=HRVerificationResponse_otherVariableIncome_input]").val();
							$("#personalApplicant_list_"+(i+1)+" input[name=HRVerificationResponse_verifiedIncome_input]").val(obj.personalInfos[i].hrIncome);

							//Inc Info
							if(obj.personalInfos[i].incomeInfos!=null){
								for(var j=0;obj.personalInfos[i].incomeInfos.length;j++){
									if(j>0)	addIncInfo('personalApplicant_list_1');
									$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" input[name=inc_info_income_type_input]").val(obj.personalInfos[i].incomeInfos[j].incomeType);
									//Inc Payslip
									for(var k=0,a=0;a<obj.personalInfos[i].incomeInfos[j].allIncomes.length;a++){
										if(k>0)	addIncPayslip('personalApplicant_list_1','inc_info_list_1');
										if(obj.personalInfos[i].incomeInfos[j].allIncomes[a].incomeType=="01"){
											$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_payslip_list_"+(k+1)+" input[name=inc_payslip_no_month_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].noMonth);
											$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_payslip_list_"+(k+1)+" input[name=inc_payslip_accum_income_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].sumIncome);
											$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_payslip_list_"+(k+1)+" input[name=inc_payslip_bonus_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].bonus);
											$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_payslip_list_"+(k+1)+" input[name=inc_payslip_sum_sso_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].sumSso);
											$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_payslip_list_"+(k+1)+" input[name=inc_payslip_special_pension_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].spacialPension);
											$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_payslip_list_"+(k+1)+" input[name=inc_payslip_accum_oth_income_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].sumOthIncome);
											$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_payslip_list_"+(k+1)+" input[name=inc_payslip_bonus_month_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].bonusMonth);
											$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_payslip_list_"+(k+1)+" input[name=inc_payslip_bonus_year_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].bonusYear);
											//Inc Payslip Monthly
											for(var l=0;l<obj.personalInfos[i].incomeInfos[j].allIncomes[a].payslipMonthlys.length;l++){
												if(l>0)	addIncPayslipMonthly('personalApplicant_list_1','inc_info_list_1','inc_payslip_list_1');
												$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_payslip_list_"+(k+1)+" #inc_payslip_monthly_list_"+(l+1)+" > table > tbody > tr > td > input[name=inc_payslip_monthly_income_type_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].payslipMonthlys[l].incomeType);
												$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_payslip_list_"+(k+1)+" #inc_payslip_monthly_list_"+(l+1)+" > table > tbody > tr > td > input[name=inc_payslip_monthly_income_desc_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].payslipMonthlys[l].incomeDesc);
												$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_payslip_list_"+(k+1)+" #inc_payslip_monthly_list_"+(l+1)+" > table > tbody > tr > td > input[name=inc_payslip_monthly_income_oth_desc_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].payslipMonthlys[l].incomeOthDesc);
												//Inc Payslip Monthly Detail
												for(var m=0;m<obj.personalInfos[i].incomeInfos[j].allIncomes[a].payslipMonthlys[l].payslipMonthlyDetails.length;m++){
													if(m>0)	addIncPayslipMonthlyDetail('personalApplicant_list_1','inc_info_list_1','inc_payslip_list_1','inc_payslip_monthly_list_1');
													$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_payslip_list_"+(k+1)+" inc_payslip_monthly_list_"+(l+1)+" inc_payslip_monthly_detail_list_1 > table > tbody > tr:eq("+m+") > td > input[name=inc_payslip_monthly_detail_month_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].payslipMonthlys[l].payslipMonthlyDetails[m].month);
													$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_payslip_list_"+(k+1)+" inc_payslip_monthly_list_"+(l+1)+" inc_payslip_monthly_detail_list_1 > table > tbody > tr:eq("+m+") > td > input[name=inc_payslip_monthly_detail_year_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].payslipMonthlys[l].payslipMonthlyDetails[m].year);
													$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_payslip_list_"+(k+1)+" inc_payslip_monthly_list_"+(l+1)+" inc_payslip_monthly_detail_list_1 > table > tbody > tr:eq("+m+") > td > input[name=inc_payslip_monthly_detail_amount_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].payslipMonthlys[l].payslipMonthlyDetails[m].amount);
												}
											}
											k++;
										}
									}
									//Inc Monthly Tawi
									for(var k=0,a=0;a<obj.personalInfos[i].incomeInfos[j].allIncomes.length;a++){
										if(k>0)	addIncYearlyTawi('personalApplicant_list_1','inc_info_list_1');
										if(obj.personalInfos[i].incomeInfos[j].allIncomes[a].incomeType=="02"){
											$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_yearly_tawi > tr:eq("+k+") > td > input[name=inc_yearly_tawi_company_name_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].companyName);
											$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_yearly_tawi > tr:eq("+k+") > td > input[name=inc_yearly_tawi_year_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].year);
											$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_yearly_tawi > tr:eq("+k+") > td > input[name=inc_yearly_tawi_no_month_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].month);
											$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_yearly_tawi > tr:eq("+k+") > td > input[name=inc_yearly_tawi_income40_1_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].income401);
											$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_yearly_tawi > tr:eq("+k+") > td > input[name=inc_yearly_tawi_income40_2_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].income402);
											$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_yearly_tawi > tr:eq("+k+") > td > input[name=inc_yearly_tawi_sum_sso_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].sumSso);
											k++;
										}
									}
									
									//Inc Taweesap
									for(var a=0;a<obj.personalInfos[i].incomeInfos[j].allIncomes.length;a++){
										if(obj.personalInfos[i].incomeInfos[j].allIncomes[a].incomeType=="06")
											$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" input[name=inc_taweesap_aum_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].aum);
									}
									//Inc Saving Acc
									for(var k=0,a=0;a<obj.personalInfos[i].incomeInfos[j].allIncomes.length;a++){
										if(k>0)	addIncSavingAcc('personalApplicant_list_1','inc_info_list_1');
										if(obj.personalInfos[i].incomeInfos[j].allIncomes[a].incomeType=="12"){
// 											$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_saving_acc_list_"+(k+1)+" > table > tbody > tr > td > input[name=inc_saving_acc_open_date_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].openDate);
											$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_saving_acc_list_"+(k+1)+" > table > tbody > tr > td > input[name=inc_saving_acc_account_no_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].accountNo);
											$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_saving_acc_list_"+(k+1)+" > table > tbody > tr > td > input[name=inc_saving_acc_account_name_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].accountName);
											$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_saving_acc_list_"+(k+1)+" > table > tbody > tr > td > input[name=inc_saving_acc_holding_ratio_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].holdingRatio);
											$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_saving_acc_list_"+(k+1)+" > table > tbody > tr > td > input[name=inc_saving_acc_bank_code_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].bankCode);
											for(var l=0;l<obj.personalInfos[i].incomeInfos[j].allIncomes[a].savingAccountDetails.length;l++){
												if(l>0) addIncSavingAccDetail('personalApplicant_list_1','inc_info_list_1','inc_saving_acc_list_1');
												$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_saving_acc_list_"+(k+1)+" > #inc_saving_acc_detail > tbody > tr("+(l+1)+") > td > input[name=inc_saving_acc_inc_saving_acc_detail_month_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].savingAccountDetails[l].month);
												$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_saving_acc_list_"+(k+1)+" > #inc_saving_acc_detail > tbody > tr("+(l+1)+") > td > input[name=inc_saving_acc_inc_saving_acc_detail_year_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].savingAccountDetails[l].year);
												$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_saving_acc_list_"+(k+1)+" > #inc_saving_acc_detail > tbody > tr("+(l+1)+") > td > input[name=inc_saving_acc_inc_saving_acc_detail_amount_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].savingAccountDetails[l].amount);
											}
											k++;
										}
									}
									//Inc Salary Cert
									for(var k=0,a=0;a<obj.personalInfos[i].incomeInfos[j].allIncomes.length;a++){
										if(k>0)	addIncSalaryCert('personalApplicant_list_1','inc_info_list_1');
										if(obj.personalInfos[i].incomeInfos[j].allIncomes[a].incomeType=="04"){
											$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_salary_cert_list_1 > tr:eq("+k+") > td > input[name=inc_salary_cert_income_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].income);
											$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_salary_cert_list_1 > tr:eq("+k+") > td > input[name=inc_salary_cert_company_name_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].companyName);
											k++;
										}
									}
									
									//Inc Previous Income
									for(var a=0;a<obj.personalInfos[i].incomeInfos[j].allIncomes.length;a++){
										if(obj.personalInfos[i].incomeInfos[j].allIncomes[a].incomeType=="16")
											$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" input[name=inc_previous_income_income_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].income);
									}
									
									//Inc Payroll
									for(var a=0;a<obj.personalInfos[i].incomeInfos[j].allIncomes.length;a++){
										if(obj.personalInfos[i].incomeInfos[j].allIncomes[a].incomeType=="07"){
											$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" input[name=inc_payroll_lowestIncome_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].income);
											$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" input[name=inc_payroll_no_of_employee_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].noOfEmployee);
										}
									}									
									
									//Inc Opn End Fund
									for(var k=0,a=0;a<obj.personalInfos[i].incomeInfos[j].allIncomes.length;a++){
										if(k>0)	addIncOpnEndFund('personalApplicant_list_1','inc_info_list_1');
										if(obj.personalInfos[i].incomeInfos[j].allIncomes[a].incomeType=="10"){
											$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_opn_end_fund_list_"+(k+1)+" table > tbody > tr > td > input[name=inc_opn_end_fund_open_date_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].openDate);
											$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_opn_end_fund_list_"+(k+1)+" table > tbody > tr > td > input[name=inc_opn_end_fund_account_no_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].accountNo);
											$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_opn_end_fund_list_"+(k+1)+" table > tbody > tr > td > input[name=inc_opn_end_fund_account_name_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].accountName);
											$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_opn_end_fund_list_"+(k+1)+" table > tbody > tr > td > input[name=inc_opn_end_fund_holding_ratio_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].holdingRatio);
											$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_opn_end_fund_list_"+(k+1)+" table > tbody > tr > td > input[name=inc_opn_end_fund_bank_code_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].bankCode);
											$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_opn_end_fund_list_"+(k+1)+" table > tbody > tr > td > input[name=inc_opn_end_fund_fund_name_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].fundName);
											//Inc Opn End Fund Detail
											for(var l=0;l<obj.personalInfos[i].incomeInfos[j].allIncomes[a].openEndFundDetails.length;l++){
												if(l>0)	addIncOpnEndFundDetail('personalApplicant_list_1','inc_info_list_1','inc_opn_end_fund_list_1');
												$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_opn_end_fund_list_"+(k+1)+" #inc_opn_end_fund_detail_list_1 > tr:eq("+l+") > td > input[name=inc_opn_end_fund_detail_month_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].openEndFundDetails[l].month);
												$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_opn_end_fund_list_"+(k+1)+" #inc_opn_end_fund_detail_list_1 > tr:eq("+l+") > td > input[name=inc_opn_end_fund_detail_year_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].openEndFundDetails[l].year);
												$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_opn_end_fund_list_"+(k+1)+" #inc_opn_end_fund_detail_list_1 > tr:eq("+l+") > td > input[name=inc_opn_end_fund_detail_amount_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].openEndFundDetails[l].amount);
											}
											k++;
										}
									}
									
									//Inc Monthly Tawi
									for(var k=0,a=0;a<obj.personalInfos[i].incomeInfos[j].allIncomes.length;a++){
										if(k>0)	addIncMonthlyTawi('personalApplicant_list_1','inc_info_list_1');
										if(obj.personalInfos[i].incomeInfos[j].allIncomes[a].incomeType=="03"){
											$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_monthly_tawi_list_"+(k+1)+" table > tbody > tr > td > input[name=inc_monthly_tawi_income_type_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].tawiIncomeType);
											$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_monthly_tawi_list_"+(k+1)+" table > tbody > tr > td > input[name=inc_monthly_tawi_company_name_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].companyName);
											$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_monthly_tawi_list_"+(k+1)+" table > tbody > tr > td > input[name=inc_monthly_tawi_compare_flag_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].compareFlag);
											//Inc Monthly Tawi Detail
											for(var l=0;l<obj.personalInfos[i].incomeInfos[j].allIncomes[a].monthlyTaxi50Details.length;l++){
												if(l>0)	addIncMonthlyTawiDetail('personalApplicant_list_1','inc_info_list_1','inc_monthly_tawi_list_1');
												$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_monthly_tawi_list_"+(k+1)+" #inc_monthly_tawi_detail_list_1 > table > tr:eq("+l+") > td > input[name=inc_monthly_tawi_detail_month_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].monthlyTaxi50Details[l].month);
												$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_monthly_tawi_list_"+(k+1)+" #inc_monthly_tawi_detail_list_1 > table > tr:eq("+l+") > td > input[name=inc_monthly_tawi_detail_year_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].monthlyTaxi50Details[l].year);
												$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_monthly_tawi_list_"+(k+1)+" #inc_monthly_tawi_detail_list_1 > table > tr:eq("+l+") > td > input[name=inc_monthly_tawi_detail_amount_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].monthlyTaxi50Details[l].amount);
											}
											k++;
										}
									}
									
									//Inc Kvi
									for(var a=0;a<obj.personalInfos[i].incomeInfos[j].allIncomes.length;a++){
										if(obj.personalInfos[i].incomeInfos[j].allIncomes[a].incomeType=="15"){
											$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" input[name=inc_kvi_percent_cheque_return_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].percentChequeReturn);
											$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" input[name=inc_kvi_verified_income_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].verifiedIncome);
										}
									}
									
									//Inc Fixed Guarantee
									for(var a=0;a<obj.personalInfos[i].incomeInfos[j].allIncomes.length;a++){
										if(obj.personalInfos[i].incomeInfos[j].allIncomes[a].incomeType=="08"){
											$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" input[name=inc_fixed_guarantee_account_no_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].accountNo);
											$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" input[name=inc_fixed_guarantee_sub_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].sub);
											$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" input[name=inc_fixed_guarantee_account_name_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].accountName);
// 											$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" input[name=inc_fixed_guarantee_retention_date_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].retentionDate);
											$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" input[name=inc_fixed_guarantee_branch_no_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].branchNo);
											$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" input[name=inc_fixed_guarantee_rentention_type_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].retentionType);
											$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" input[name=inc_fixed_guarantee_rentention_amt_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].retentionAmt);
										}
									}
									
									//Inc Fixed Acc
									for(var a=0;a<obj.personalInfos[i].incomeInfos[j].allIncomes.length;a++){
										if(obj.personalInfos[i].incomeInfos[j].allIncomes[a].incomeType=="11"){
// 											$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" input[name=inc_fixed_acc_open_date_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].openDate);
											$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" input[name=inc_fixed_acc_account_no_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].accountNo);
											$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" input[name=inc_fixed_acc_account_name_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].accountName);
											$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" input[name=inc_fixed_acc_holding_ratio_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].holdingRatio);
											$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" input[name=inc_fixed_acc_account_balance_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].accountBalance);
											$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" input[name=inc_fixed_acc_bank_code_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].bankCode);
										}
									}
									
									//Inc Fin Instrument
									for(var a=0;a<obj.personalInfos[i].incomeInfos[j].allIncomes.length;a++){
										if(obj.personalInfos[i].incomeInfos[j].allIncomes[a].incomeType=="14"){
// 											$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" input[name=inc_fin_instrument_open_date_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].openDate);
// 											$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" input[name=inc_fin_instrument_expire_date_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].expireDate);
											$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" input[name=inc_fin_instrument_issuer_name_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].issuerName);
											$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" input[name=inc_fin_instrument_instrument_type_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].instrumentType);
											$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" input[name=inc_fin_instrument_holder_name_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].holderName);
											$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" input[name=inc_fin_instrument_holding_ratio_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].holdingRatio);
											$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" input[name=inc_fin_instrument_current_balance_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].currentBalance);
											$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" input[name=inc_fin_instrument_instrument_type_desc_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].instrumentTypeDesc);
										}
									}
									
									//Inc Cls End Fund
									for(var a=0;a<obj.personalInfos[i].incomeInfos[j].allIncomes.length;a++){
										if(obj.personalInfos[i].incomeInfos[j].allIncomes[a].incomeType=="09"){
											$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" input[name=inc_cls_end_fund_bank_code_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].bankCode);
											$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" input[name=inc_cls_end_fund_fund_name_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].fundName);
											$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" input[name=inc_cls_end_fund_account_no_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].accountNo);
											$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" input[name=inc_cls_end_fund_account_name_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].accountName);
											$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" input[name=inc_cls_end_fund_holding_ratio_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].holdingRatio);
											$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" input[name=inc_cls_end_fund_account_balance_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].accountBalance);
										}
									}
									
									//Inc Bank Statement
									for(var k=0,a=0;a<obj.personalInfos[i].incomeInfos[j].allIncomes.length;a++){
										if(k>0) addIncBankStatement('personalApplicant_list_1','inc_info_list_1');
										if(obj.personalInfos[i].incomeInfos[j].allIncomes.incomeType=="05"){
											$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_bank_statement_list_"+(k+1)+" input[name=inc_bank_statement_bank_code_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].bankCode);
											$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_bank_statement_list_"+(k+1)+" input[name=inc_bank_statement_statement_code_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].statementCode);
											$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_bank_statement_list_"+(k+1)+" input[name=inc_bank_statement_additional_code_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].additionalCode);
											for(var l=0;l<obj.personalInfos[i].incomeInfos[j].allIncomes[a].bankStatementDetails.length;l++){
												if(l>0)	addIncBankStatementDetail('personalApplicant_list_1','inc_info_list_1','inc_bank_statement_list_1');
												$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_bank_statement_list_"+(k+1)+" #inc_bank_statement_detail_list_1 > tr:eq("+(l+1)+") > td > input[name=inc_bank_statement_detail_month_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].bankStatementDetails[l].month);
												$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_bank_statement_list_"+(k+1)+" #inc_bank_statement_detail_list_1 > tr:eq("+(l+1)+") > td > input[name=inc_bank_statement_detail_year_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].bankStatementDetails[l].year);
												$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_bank_statement_list_"+(k+1)+" #inc_bank_statement_detail_list_1 > tr:eq("+(l+1)+") > td > input[name=inc_bank_statement_detail_amount_input]").val(obj.personalInfos[i].incomeInfos[j].allIncomes[a].bankStatementDetails[l].amount);
											}
											k++;
										}
									}
									
									//Orig Bundle SME
									$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" input[name=orig_bundle_sme_applicant_quality_input]").val(obj.applications[0].bundleSME.applicantQuality);
									$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" input[name=orig_bundle_sme_approval_limit_input]").val(obj.applications[0].bundleSME.approvalLimit);
									$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" input[name=orig_bundle_sme_business_owner_flag_input]").val(obj.applications[0].bundleSME.busOwnerFlag);
									$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" input[name=orig_bundle_sme_corporate_ratio_input]").val(obj.applications[0].bundleSME.corporateRatio);
									$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" input[name=orig_bundle_sme_g_dscr_req_input]").val(obj.applications[0].bundleSME.gDscrReq);
									$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" input[name=orig_bundle_sme_g_total_exist_payment_input]").val(obj.applications[0].bundleSME.gTotExistPayment);
									$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" input[name=orig_bundle_sme_g_total_newpay_req_input]").val(obj.applications[0].bundleSME.gTotNewPayReq);
									$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" input[name=orig_bundle_sme_sme_borrower_type_input]").val(obj.applications[0].bundleSME.borrowingType);
									$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" input[name=orig_bundle_sme_individual_ratio_input]").val(obj.applications[0].bundleSME.individualRatio);
// 									$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" input[name=orig_bundle_sme_approvedDate_input]").val(obj.applications[0].bundleSME.approvalDate);
									
									//Orig Bundle KL
									$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" input[name=orig_bundle_kl_verfied_income_input]").val(obj.applications[0].bundleKL.verifiedIncome);
// 									$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" input[name=orig_bundle_kl_verfied_date_input]").val(obj.applications[0].bundleKL.verifiedDate);
									$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" input[name=orig_bundle_kl_estimated_income_input]").val(obj.applications[0].bundleKL.estimated_income);
									
									//Orig Bundle HL
									$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" input[name=orig_bundle_hl_approve_credit_line_input]").val(obj.applications[0].bundleHL.approveCreditLine);
									$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" input[name=orig_bundle_hl_verified_income_input]").val(obj.applications[0].bundleHL.verifiedIncome);
// 									$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" input[name=orig_bundle_hl_approved_date_input]").val(obj.applications[0].bundleHL.approvedDate);
									$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" input[name=orig_bundle_hl_cc_approved_amt_input]").val(obj.applications[0].bundleHL.ccApprovedAmt);
									$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" input[name=orig_bundle_hl_kec_approved_amt_input]").val(obj.applications[0].bundleHL.kecApprovedAmt);
									
									//Inc Payroll File Info
									for(var k=0;;k++){
										if(k=0)	addIncPayrollFileInfo('personalApplicant_list_1','inc_info_list_1');
										$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_payroll_file_info_list_1 > tr:eq("+k+") > td > input[name=inc_payroll_file_info_payrollCurrMonth]").val();
										$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_payroll_file_info_list_1 > tr:eq("+k+") > td > input[name=inc_payroll_file_info_payrollPrev1Month]").val();
										$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_payroll_file_info_list_1 > tr:eq("+k+") > td > input[name=inc_payroll_file_info_payrollPrev2Month]").val();
										$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_payroll_file_info_list_1 > tr:eq("+k+") > td > input[name=inc_payroll_file_info_payrollPrev3Month]").val();
										$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_payroll_file_info_list_1 > tr:eq("+k+") > td > input[name=inc_payroll_file_info_payrollPrev4Month]").val();
										$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_payroll_file_info_list_1 > tr:eq("+k+") > td > input[name=inc_payroll_file_info_payrollPrev5Month]").val();
										$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_payroll_file_info_list_1 > tr:eq("+k+") > td > input[name=inc_payroll_file_info_payrollPrev6Month]").val();
										$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_payroll_file_info_list_1 > tr:eq("+k+") > td > input[name=inc_payroll_file_info_payrollPrev7Month]").val();
										$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_payroll_file_info_list_1 > tr:eq("+k+") > td > input[name=inc_payroll_file_info_payrollPrev8Month]").val();
										$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_payroll_file_info_list_1 > tr:eq("+k+") > td > input[name=inc_payroll_file_info_payrollPrev9Month]").val();
										$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_payroll_file_info_list_1 > tr:eq("+k+") > td > input[name=inc_payroll_file_info_payrollPrev10Month]").val();
										$("#personalApplicant_list_"+(i+1)+" #inc_info_list_"+(j+1)+" #inc_payroll_file_info_list_1 > tr:eq("+k+") > td > input[name=inc_payroll_file_info_payrollPrev11Month]").val();
									}
								}
							}
							//Document List
							if(obj.personalInfos[i].applicationImages!=null){
								if(obj.personalInfos[i].applicationImages[0].applicationImageSplits!=null){
									var documentListLenght = obj.applicationImages[0].applicationImageSplits.length;
									for(var j=0;j<documentListLenght;j++){
										$("#personalApplicant_list_"+(i+1)+" #documentList_list_1 > tr:eq("+j+") > td > input[name=documents_documentCode_input]").val(obj.applicationImages[0].applicationImageSplits[j].docType);
		// 								$("#personalApplicant_list_"+(i+1)+" #documentList_list_1 > tr:eq("+j+") > td > input[name=documents_documentType_input]").val(obj.applicationImages[0].applicationImageSplits[j].);
										if(obj.personalInfos[i].applicationImages[0].applicationImageSplits[j].docType =="DOC_ID_01_ID"){
// 											$("#personalApplicant_list_"+(i+1)+" #documentList_list_1 > tr:eq("+j+") > td > input[name=documents_expiryDate_input]").val(obj.applicationImages[0].applicationImageSplits[j].cidExpDate);
										}else if(obj.personalInfos[i].applicationImages[0].applicationImageSplits[j].docType =="DOC_ID_05_ID"){
// 											$("#personalApplicant_list_"+(i+1)+" #documentList_list_1 > tr:eq("+j+") > td > input[name=documents_expiryDate_input]").val(obj.applicationImages[0].applicationImageSplits[j].workPermitExpDate);
										}else{
// 											$("#personalApplicant_list_"+(i+1)+" #documentList_list_1 > tr:eq("+j+") > td > input[name=documents_expiryDate_input]").val(obj.applicationImages[0].applicationImageSplits[j].visaExpDate);
										}
									}
								}
							}
							
							//Reference 
							if(obj.personalInfos[i].referencePerson!=null){
								$("#personalApplicant_list_"+(i+1)+" #reference_list_1 > tr:eq(0) > td > input[name=reference_title_input]").val(obj.personalInfos[i].referencePerson.thTitleCode);
								$("#personalApplicant_list_"+(i+1)+" #reference_list_1 > tr:eq(0) > td > input[name=reference_lastName_input]").val(obj.personalInfos[i].referencePerson.thLastName);
								$("#personalApplicant_list_"+(i+1)+" #reference_list_1 > tr:eq(0) > td > input[name=reference_firstName_input]").val(obj.personalInfos[i].referencePerson.thFirstName);
								$("#personalApplicant_list_"+(i+1)+" #reference_list_1 > tr:eq(0) > td > input[name=reference_relationshipWithBorrower_input]").val(obj.personalInfos[i].referencePerson.relation);
								$("#personalApplicant_list_"+(i+1)+" #reference_list_1 > tr:eq(0) > td > input[name=reference_fixedLineHome_input]").val(obj.personalInfos[i].referencePerson.phone1);
								$("#personalApplicant_list_"+(i+1)+" #reference_list_1 > tr:eq(0) > td > input[name=reference_fixedLineWork_input]").val(obj.personalInfos[i].referencePerson.officePhone);
								$("#personalApplicant_list_"+(i+1)+" #reference_list_1 > tr:eq(0) > td > input[name=reference_mobileNumber_input]").val(obj.personalInfos[i].referencePerson.mobile);
							}
						}
					}

					
					//Credit Detail
					if(obj.applications!=null){
						var applicationLength = obj.applications.length;
						for(var i=0;i<applicationLength;i++){
// 							$("input[name=policyExceptionDate_input]").val(obj.applications[i].policyExSignOffDate);
							if(obj.applications[i].loans!=null){
								if(obj.applications[i].loans[0].cashTransfers!=null){
									$("#creditDetail_list_"+(i+1)+" input[name=creditDetails_cashOnTransfer_input]").val(obj.applications[i].loans[0].cashTransfers[0].cashTransferType);
								}
								if(obj.applications[i].loans[0].paymentMethods!=null){
									$("#creditDetail_list_"+(i+1)+" input[name=creditDetails_isAutoPayment_input]").val(obj.applications[i].loans[0].paymentMethods[0].paymentMethod);
									$("#creditDetail_list_"+(i+1)+" input[name=creditDetails_transferPercentage_input]").val(obj.applications[i].loans[0].paymentMethods[0].paymentRatio);									
									$("#creditDetail_list_"+(i+1)+" input[name=creditDetails_paymentMethod_input]").val(obj.applications[i].loans[0].paymentMethods[0].paymentMethod);
									
								}
								if(obj.applications[i].loans[0].card!=null){
									$("#creditDetail_list_"+(i+1)+" input[name=creditDetails_requestCreditLimitPercent_input]").val(obj.applications[i].loans[0].card.percentLimitMaincard);
									$("#creditDetail_list_"+(i+1)+" input[name=creditDetails_accountNumberMC_input]").val(obj.applications[i].loans[0].card.mainCardNo);
									$("#creditDetail_list_"+(i+1)+" input[name=creditDetails_subProduct_input]").val(obj.applications[i].loans[0].card.applicationType);
									$("#creditDetail_list_"+(i+1)+" input[name=creditDetails_accountNumberInc_input]").val(obj.applications[i].loans[0].card.cardNo);
								}
								
								$("#creditDetail_list_"+(i+1)+" input[name=creditDetails_requestedAmount_input]").val(obj.applications[i].loans[0].requestLoanAmt);
								$("#creditDetail_list_"+(i+1)+" input[name=creditDetails_requestedTerm_input]").val(obj.applications[i].loans[0].requestTerm);
								$("#creditDetail_list_"+(i+1)+" input[name=creditDetails_caFinalDecision_input]").val(obj.applications[i].finalAppDecision);
								$("#creditDetail_list_"+(i+1)+" input[name=creditDetails_caApprovedAmount_input]").val(obj.applications[i].loans[0].loanAmt);
							}
							$("#creditDetail_list_"+(i+1)+" input[name=creditDetails_typeOfKecProgram_input]").val();
							$("#creditDetail_list_"+(i+1)+" input[name=creditDetails_projectCode_input]").val(obj.applications[i].projectCode);
							$("#creditDetail_list_"+(i+1)+" input[name=creditDetails_uniqueProductId_input]").val(obj.applications[i].applicationRecordId);
// 							$("#creditDetail_list_"+(i+1)+" input[name=creditDetails_spSignOffDate_input]").val(obj.applications[i].spSignoffDate);
							$("#creditDetail_list_"+(i+1)+" input[name=creditDetails_productMailingAddressType_input]").val();
							$("#creditDetail_list_"+(i+1)+" input[name=creditDetails_wealthProjectCode_input]").val(obj.applications[i].projectCode);
							$("#creditDetail_list_"+(i+1)+" input[name=creditDetails_isMailingAddressMatchCardlink_input]").val();
							$("#creditDetail_list_"+(i+1)+" input[name=creditDetails_isMailingAddressMatchNcb_input]").val();
							$("#creditDetail_list_"+(i+1)+" input[name=creditDetails_isMailingAddressMatchId_input]").val();
							if(obj.personalInfos[i].addresses!=null){
								var addressLength = obj.personalInfos[i].addresses.length;
								for(var j=0;j<addressLength;j++){
									if(obj.personalInfos[i].addresses[j].addressType==""){
										$("#creditDetail_list_"+(i+1)+" input[name=mailingAddress_address_houseNumber_input]").val(obj.personalInfos[i].addresses[j].address);
										$("#creditDetail_list_"+(i+1)+" input[name=mailingAddress_address_village_input]").val(obj.personalInfos[i].addresses[j].vilapt);
										$("#creditDetail_list_"+(i+1)+" input[name=mailingAddress_address_buildingName_input]").val(obj.personalInfos[i].addresses[j].building);
										$("#creditDetail_list_"+(i+1)+" input[name=mailingAddress_address_subDistrict_input]").val(obj.personalInfos[i].addresses[j].tambol);
										$("#creditDetail_list_"+(i+1)+" input[name=mailingAddress_address_district_input]").val(obj.personalInfos[i].addresses[j].amphur);
										$("#creditDetail_list_"+(i+1)+" input[name=mailingAddress_address_province_input]").val(obj.personalInfos[i].addresses[j].provinceDesc);
										$("#creditDetail_list_"+(i+1)+" input[name=mailingAddress_address_postalCode_input]").val(obj.personalInfos[i].addresses[j].zipCode);
										$("#creditDetail_list_"+(i+1)+" input[name=mailingAddress_address_country_input]").val(obj.personalInfos[i].addresses[j].country);
										$("#creditDetail_list_"+(i+1)+" input[name=mailingAddress_address_fixedLineNumber_input]").val(obj.personalInfos[i].addresses[j].phone1);
										$("#creditDetail_list_"+(i+1)+" input[name=mailingAddress_address_type_input]").val(obj.personalInfos[i].addresses[j].addressType);
										$("#creditDetail_list_"+(i+1)+" input[name=mailingAddress_address_floorNumber_input]").val(obj.personalInfos[i].addresses[j].floor);
										$("#creditDetail_list_"+(i+1)+" input[name=mailingAddress_address_moo_input]").val(obj.personalInfos[i].addresses[j].moo);
										$("#creditDetail_list_"+(i+1)+" input[name=mailingAddress_address_soi_input]").val(obj.personalInfos[i].addresses[j].soi);
										$("#creditDetail_list_"+(i+1)+" input[name=mailingAddress_address_street_input]").val(obj.personalInfos[i].addresses[j].road);
									}
								}
							}
							//Associated Applicant
							if(obj.personalInfos!=null){
								var personalInfosLength = obj.personalInfos.length;
								for(var j=0;j<personalInfosLength;j++){
									if(j>0)	addAssociatedApplicant('creditDetail_list_1');
									$("#creditDetail_list_"+(i+1)+" #associatedApplicant_list_1 > tr:eq("+j+") > td > input[name=associatedApplicant_applicantType_input]").val(obj.personalInfos[j].personalType);
								}
							}
						}
					}
					if(obj.applications[0] !=null){
						$("input[name=priorityPassQuota_existingAssignedCount_input]").val(obj.applications[0].existingPriorityPass);
					}
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
	<!-- <div class="glyphicon glyphicon-circle-arrow-down" onclick="toggle('FicoForm_input')"></div> -->
	<form class="form-inline well" id="applicationGroupForm">
		<Strong>Application Group</Strong>
		<table>
			<tr>
				<td>Application Group Id</td>
				<td><input type="text" class="form-control input-sm" size="60"
					name="applicationGroupId_input"></td>
				<td>Decision Station</td>
				<td>
					<select style="min-width:100px;"
					name="decision_station">
						<option value="IA1">IA1</option>
						<option value="PB1">PB1</option>
						<option value="DE1">DE1</option>
						<option value="DV1">DV1</option>
						<option value="DV2">DV2</option>
						<option value="FI">FI</option>
						<option value="DE2">DE2</option>
						<option value="DC">DC</option>
					</select>
				</td>
			</tr>
		</table>
		<button type="button" class="btn btn-info" onclick="loadData();">Load
			Data</button>
	</form>
	<form class="form-inline well FicoForm_input">
		<Strong>Input</Strong>
		<table>
			<tr>
				<td>URL Web Service</td>
				<td><input type="text" class="form-control input-sm" size="60"
					name="urlWebService_input"></td>
				<td>Application Id</td>
				<td><input type="text" class="form-control input-sm" size="60"
					name="applicationId_input"></td>
			</tr>
			<tr>
				<td>Template Code</td>
				<td><input type="text" class="form-control input-sm" size="60"
					name="templateCode_input"></td>
				<td>IS VETO</td>
				<td><input type="text" class="form-control input-sm" size="60"
					name="isVETO_input"></td>
			</tr>
			<tr>
				<td>Test Logical Date(yyyy-MM-dd)</td>
				<td><input type="text" class="form-control input-sm" size="60"
					name="testLogicalDate_input"></td>
				<td>Application Create Date(yyyy-MM-dd HH:mm:ss)</td>
				<td><input type="text" class="form-control input-sm" size="60"
					name="applicationCreateDate_input"></td>
			</tr>
			<tr>
				<td>Channel Code</td>
				<td><input type="text" class="form-control input-sm" size="60"
					name="channelCode_input"></td>
				<td>Strategy Sel Random Num1</td>
				<td><input type="text" class="form-control input-sm" size="60"
					name="strategySelRandomNum1_input"></td>
			</tr>
			<tr>
				<td>IS Fraud Application</td>
				<td><input type="text" class="form-control input-sm" size="60"
					name="isFraudApplication_input"></td>
				<td>IS Fraud Company</td>
				<td><input type="text" class="form-control input-sm" size="60"
					name="isFraudCompany_input"></td>
			</tr>
			<tr>
				<td>Policy Exception Date(yyyy-MM-dd)</td>
				<td><input type="text" class="form-control input-sm" size="60"
					name="policyExceptionDate_input"></td>
			</tr>
		</table>
	</form>
	<div id="personalApplicant">
		<div id="personalApplicant_list_1">
			<form class="well FicoForm_input form-line">
				<strong>Personal Applicant</strong>
				<table>
					<tr>
						<td>ID Type</td>
						<td><input type="text" class="form-control input-sm"
							size="40" name="idType_input"></td>
						<td>ID Number</td>
						<td><input type="text" class="form-control input-sm"
							size="40" name="idNumber_input"></td>
					</tr>
					<tr>
						<td>CIS ID</td>
						<td><input type="text" class="form-control input-sm"
							size="40" name="cisID_input"></td>
						<td>Applicant Type</td>
						<td><input type="text" class="form-control input-sm"
							size="40" name="applicantType_input"></td>
					</tr>
					<tr>
						<td>Education Level</td>
						<td><input type="text" class="form-control input-sm"
							size="40" name="educationLevel_input"></td>
						<td>IS K Group Staff</td>
						<td><input type="text" class="form-control input-sm"
							size="40" name="isKGroupStaff_input"></td>
					</tr>
					<tr>
						<td>IS Information Complete</td>
						<td><input type="text" class="form-control input-sm"
							size="40" name="isInformationComplete_input"></td>
						<td>CIS Primary Segment</td>
						<td><input type="text" class="form-control input-sm"
							size="40" name="cisPrimarySegment_input"></td>
					</tr>
					<tr>
						<td>CIS Primary Sub Segment</td>
						<td><input type="text" class="form-control input-sm"
							size="40" name="cisPrimarySubSegment_input"></td>
						<td>CIS Secondary Segment</td>
						<td><input type="text" class="form-control input-sm"
							size="40" name="cisSecondarySegment_input"></td>
					</tr>
					<tr>
						<td>CIS Secondary Sub Segment</td>
						<td><input type="text" class="form-control input-sm"
							size="40" name="cisSecondarySubSegment_input"></td>
						<td>Income Receive Code</td>
						<td><input type="text" class="form-control input-sm"
							size="40" name="incomeReceiveCode_input"></td>
					</tr>
				</table>
				<ul class="nav nav-tabs">
					<li class="active"><a data-toggle="tab"
						href="#documentSlaType_1">Document SLA Type</a></li>
					<li><a data-toggle="tab" href="#previousEmployment_1">Previous
							Employment</a></li>
					<li><a data-toggle="tab" href="#idAddress_1">ID Address</a></li>
					<li><a data-toggle="tab" href="#currentResidence_1">Current
							Residence</a></li>
					<li><a data-toggle="tab" href="#person_1">Person</a></li>
					<li><a data-toggle="tab" href="#currentEmployment_1">Current
							Employment</a></li>
					<li><a data-toggle="tab" href="#response_1">Response</a></li>
					<li><a data-toggle="tab" href="#inc_info_1">Inc Info</a></li>
					<li><a data-toggle="tab" href="#documentList_1">Document
							List</a></li>
					<li><a data-toggle="tab" href="#reference_1">Reference</a></li>
				</ul>
				<div class="form-inline">
					<div class="tab-content" style="margin-top: 10px;">
						<div class="tab-pane fade in active" id="documentSlaType_1">
							<table id="docmentSlaType_list_1">
								<tr>
									<td>Code</td>
									<td><input type="text" class="form-control input-sm"
										size="40" name="code_input"></td>
								</tr>
							</table>
						</div>
						<div class="tab-pane fade" id="previousEmployment_1">
							<div id="previousEmployment">
								<table id="previousEmployment_list_1">
									<tr>
										<td>Previous Employment Years</td>
										<td><input type="text" class="form-control input-sm"
											size="40" name="previousEmploymentYears_input"></td>
										<td>Previous Employment Months</td>
										<td><input type="text" class="form-control input-sm"
											size="40" name="previousEmploymentMonths_input"></td>
									</tr>
								</table>
							</div>
							<button type="button" class="btn btn-info"
								onclick="addPreviousEmployment('personalApplicant_list_1')">Add
								Previous Employment</button>
						</div>
						<div class="tab-pane fade" id="idAddress_1">
							<table>
								<tr>
									<td>House Number</td>
									<td><input type="text" class="form-control input-sm"
										size="40" name="IDAddress_address_houseNumber_input"></td>
									<td>Village</td>
									<td><input type="text" class="form-control input-sm"
										size="40" name="IDAddress_address_village_input"></td>
								</tr>
								<tr>
									<td>Building Name</td>
									<td><input type="text" class="form-control input-sm"
										size="40" name="IDAddress_address_buildingName_input"></td>
									<td>Sub District</td>
									<td><input type="text" class="form-control input-sm"
										size="40" name="IDAddress_address_subDistrict_input"></td>
								</tr>
								<tr>
									<td>District</td>
									<td><input type="text" class="form-control input-sm"
										size="40" name="IDAddress_address_district_input"></td>
									<td>Province</td>
									<td><input type="text" class="form-control input-sm"
										size="40" name="IDAddress_address_province_input"></td>
								</tr>
								<tr>
									<td>Postal Code</td>
									<td><input type="text" class="form-control input-sm"
										size="40" name="IDAddress_address_postalCode_input"></td>
									<td>Country</td>
									<td><input type="text" class="form-control input-sm"
										size="40" name="IDAddress_address_country_input"></td>
								</tr>
								<tr>
									<td>Fixed Line Number</td>
									<td><input type="text" class="form-control input-sm"
										size="40" name="IDAddress_address_fixedLineNumber_input"></td>
									<td>Floor Number</td>
									<td><input type="text" class="form-control input-sm"
										size="40" name="IDAddress_address_floorNumber_input"></td>
								</tr>
								<tr>
									<td>Moo</td>
									<td><input type="text" class="form-control input-sm"
										size="40" name="IDAddress_address_moo_input"></td>
									<td>Soi</td>
									<td><input type="text" class="form-control input-sm"
										size="40" name="IDAddress_address_soi_input"></td>
								</tr>
								<tr>
									<td>Street</td>
									<td><input type="text" class="form-control input-sm"
										size="40" name="IDAddress_address_street_input"></td>
								</tr>
							</table>
						</div>
						<div class="tab-pane fade" id="currentResidence_1">
							<table>
								<tr>
									<td>Months At Address</td>
									<td><input type="text" class="form-control input-sm"
										size="40" name="currentResidence_monthsAtAddress_input"></td>
									<td>Years At Address</td>
									<td><input type="text" class="form-control input-sm"
										size="40" name="currentResidence_yearsAtAddress_input"></td>
								</tr>
								<tr>
									<td>Current Residential Status</td>
									<td><input type="text" class="form-control input-sm"
										size="40"
										name="currentResidence_currentResidentialStatus_input"></td>
									<td>House Number</td>
									<td><input type="text" class="form-control input-sm"
										size="40" name="currentResidence_address_houseNumber_input"></td>
								</tr>
								<tr>
									<td>Village</td>
									<td><input type="text" class="form-control input-sm"
										size="40" name="currentResidence_address_village_input"></td>
									<td>Building Name</td>
									<td><input type="text" class="form-control input-sm"
										size="40" name="currentResidence_address_buildingName_input"></td>
								</tr>
								<tr>
									<td>Sub District</td>
									<td><input type="text" class="form-control input-sm"
										size="40" name="currentResidence_address_subDistrict_input"></td>
									<td>District</td>
									<td><input type="text" class="form-control input-sm"
										size="40" name="currentResidence_address_district_input"></td>
								</tr>
								<tr>
									<td>Province</td>
									<td><input type="text" class="form-control input-sm"
										size="40" name="currentResidence_address_province_input"></td>
									<td>Postal Code</td>
									<td><input type="text" class="form-control input-sm"
										size="40" name="currentResidence_address_postalCode_input"></td>
								</tr>
								<tr>
									<td>Country</td>
									<td><input type="text" class="form-control input-sm"
										size="40" name="currentResidence_address_country_input"></td>
									<td>Fixed Line Number</td>
									<td><input type="text" class="form-control input-sm"
										size="40"
										name="currentResidence_address_fixedLineNumber_input"></td>
								</tr>
								<tr>
									<td>Type</td>
									<td><input type="text" class="form-control input-sm"
										size="40" name="currentResidence_address_type_input"></td>
									<td>Floor Number</td>
									<td><input type="text" class="form-control input-sm"
										size="40" name="currentResidence_address_floorNumber_input"></td>
								</tr>
								<tr>
									<td>Moo</td>
									<td><input type="text" class="form-control input-sm"
										size="40" name="currentResidence_address_moo_input"></td>
									<td>Soi</td>
									<td><input type="text" class="form-control input-sm"
										size="40" name="currentResidence_address_soi_input"></td>
								</tr>
								<tr>
									<td>Street</td>
									<td><input type="text" class="form-control input-sm"
										size="40" name="currentResidence_address_street_input"></td>
								</tr>
							</table>
						</div>
						<div class="tab-pane fade" id="person_1">
							<table>
								<tr>
									<td>Birth Date</td>
									<td><input type="text" class="form-control input-sm"
										size="40" name="person_birthDate_input"></td>
									<td>Number Of Dependents</td>
									<td><input type="text" class="form-control input-sm"
										size="40" name="person_numberOfDependents_input"></td>
								</tr>
								<tr>
									<td>Marital Status</td>
									<td><input type="text" class="form-control input-sm"
										size="40" name="person_maritalStatus_input"></td>
									<td>Phone Number</td>
									<td><input type="text" class="form-control input-sm"
										size="40" name="person_phoneNumber_input"></td>
								</tr>
								<tr>
									<td>Nationality</td>
									<td><input type="text" class="form-control input-sm"
										size="40" name="person_nationality_input"></td>
								</tr>
								<tr>
									<td>Commercial Amount</td>
									<td><input type="text" class="form-control input-sm"
										size="40" name="otherDebt_commercial_amount_input"></td>
									<td>Welfare Amount</td>
									<td><input type="text" class="form-control input-sm"
										size="40" name="otherDebt_welfate_amount_input"></td>
								</tr>
								<tr>
									<td>Other Debt Amount</td>
									<td><input type="text" class="form-control input-sm"
										size="40" name="otherDebt_other_debt_amount_input"></td>
									<td>Prefix Name</td>
									<td><input type="text" class="form-control input-sm"
										size="40" name="person_name_prefix_input"></td>
								</tr>
								<tr>
									<td>First Name</td>
									<td><input type="text" class="form-control input-sm"
										size="40" name="person_name_first_input"></td>
									<td>Middle Name</td>
									<td><input type="text" class="form-control input-sm"
										size="40" name="person_name_middle_input"></td>
								</tr>
								<tr>
									<td>Last Name</td>
									<td><input type="text" class="form-control input-sm"
										size="40" name="person_name_last_input"></td>
								</tr>
							</table>
						</div>
						<div class="tab-pane fade" id="currentEmployment_1">
							<Strong>Current Employment</Strong>
							<div id="currentEmployment">
								<table id="currentEmployment_list_1">
									<tr>
										<td>Months With Employer</td>
										<td><input type="text" class="form-control input-sm"
											size="40"
											name="currentEmployment_employment_monthsWithEmployer_input"></td>
										<td>Occupation Code</td>
										<td><input type="text" class="form-control input-sm"
											size="40"
											name="currentEmployment_employment_occupationCode_input"></td>
									</tr>
									<tr>
										<td>Profession Code</td>
										<td><input type="text" class="form-control input-sm"
											size="40"
											name="currentEmployment_employment_professionCode_input"></td>
										<td>Position Type</td>
										<td><input type="text" class="form-control input-sm"
											size="40"
											name="currentEmployment_employment_positionType_input"></td>
									</tr>
									<tr>
										<td>Employer Title Code</td>
										<td><input type="text" class="form-control input-sm"
											size="40"
											name="currentEmployment_employment_employerTitleCode_input"></td>
										<td>Current Employment Years</td>
										<td><input type="text" class="form-control input-sm"
											size="40"
											name="currentEmployment_employment_currentEmploymentYears_input"></td>
									</tr>
									<tr>
										<td>Company Name</td>
										<td><input type="text" class="form-control input-sm"
											size="40"
											name="currentEmployment_employment_companyName_input"></td>
										<td>House Number</td>
										<td><input type="text" class="form-control input-sm"
											size="40"
											name="currentEmployment_employment_employerAddress_address_houseNumber_input"></td>
									</tr>
									<tr>
										<td>Village</td>
										<td><input type="text" class="form-control input-sm"
											size="40"
											name="currentEmployment_employment_employerAddress_address_village_input"></td>
										<td>Building Name</td>
										<td><input type="text" class="form-control input-sm"
											size="40"
											name="currentEmployment_employment_employerAddress_address_buildingName_input"></td>
									</tr>
									<tr>
										<td>Sub District</td>
										<td><input type="text" class="form-control input-sm"
											size="40"
											name="currentEmployment_employment_employerAddress_address_subDistrict_input"></td>
										<td>District</td>
										<td><input type="text" class="form-control input-sm"
											size="40"
											name="currentEmployment_employment_employerAddress_address_district_input"></td>
									</tr>
									<tr>
										<td>Province</td>
										<td><input type="text" class="form-control input-sm"
											size="40"
											name="currentEmployment_employment_employerAddress_address_province_input"></td>
										<td>Postal Code</td>
										<td><input type="text" class="form-control input-sm"
											size="40"
											name="currentEmployment_employment_employerAddress_address_postalCode_input"></td>
									</tr>
									<tr>
										<td>Country</td>
										<td><input type="text" class="form-control input-sm"
											size="40"
											name="currentEmployment_employment_employerAddress_address_country_input"></td>
										<td>Fixed Line Number</td>
										<td><input type="text" class="form-control input-sm"
											size="40"
											name="currentEmployment_employment_employerAddress_address_fixedLineNumber_input"></td>
									</tr>
									<tr>
										<td>Type</td>
										<td><input type="text" class="form-control input-sm"
											size="40"
											name="currentEmployment_employment_employerAddress_address_type_input"></td>
										<td>Floor Number</td>
										<td><input type="text" class="form-control input-sm"
											size="40"
											name="currentEmployment_employment_employerAddress_address_floorNumber_input"></td>
									</tr>
									<tr>
										<td>Moo</td>
										<td><input type="text" class="form-control input-sm"
											size="40"
											name="currentEmployment_employment_employerAddress_address_moo_input"></td>
										<td>Soi</td>
										<td><input type="text" class="form-control input-sm"
											size="40"
											name="currentEmployment_employment_employerAddress_address_soi_input"></td>
									</tr>
									<tr>
										<td>Street</td>
										<td><input type="text" class="form-control input-sm"
											size="40"
											name="currentEmployment_employment_employerAddress_address_street_input"></td>
									</tr>
								</table>
							</div>
							<button type="button" class="btn btn-info"
								onclick="addCurrentEmployment('personalApplicant_list_1')">Add
								Current Employment</button>
						</div>
						<div class="tab-pane fade" id="response_1">
							<Strong>HR Verification Result</Strong>
							<table>
								<tr>
									<td>HR Verification Result</td>
									<td><input type="text" class="form-control input-sm"
										size="40"
										name="HRVerificationResponse_HRVerificationResult_input"></td>
									<td>Bonus</td>
									<td><input type="text" class="form-control input-sm"
										size="40" name="HRVerificationResponse_bonus_input"></td>
								</tr>
								<tr>
									<td>Other Variable Income</td>
									<td><input type="text" class="form-control input-sm"
										size="40"
										name="HRVerificationResponse_otherVariableIncome_input"></td>
									<td>Verified Income</td>
									<td><input type="text" class="form-control input-sm"
										size="40" name="HRVerificationResponse_verifiedIncome_input"></td>
								</tr>
							</table>
							<Strong>Customer Verification Response</Strong>
							<table>
								<tr>
									<td>Customer Verification Result</td>
									<td><input type="text" class="form-control input-sm"
										size="40"
										name="customerVerificationResponse_customerVerificationResult_input"></td>
								</tr>
							</table>
							<Strong>Website Verification Response</Strong>
							<table class="table table-striped"
								id="websiteVerificationResponse">
								<thead>
									<tr>
										<th>Website Code</th>
										<th>Website Verification Result</th>
									</tr>
								</thead>
								<tbody id="websiteVerificationResponse_list_1">
									<tr>
										<td><input type="text" class="form-control input-sm"
											size="40"
											name="websiteVerificationResponse_websiteCode_input"></td>
										<td><input type="text" class="form-control input-sm"
											size="40"
											name="websiteVerificationResponse_websiteVerificationResult_input"></td>
									</tr>
								</tbody>
							</table>
							<button type="button" class="btn btn-info"
								onclick="addWebsiteVerificationResponse('personalApplicant_list_1')">Website
								Verification Response Add</button>
						</div>
						<div class="tab-pane fade" id="inc_info_1">
							<div id="inc_info">
								<div id="inc_info_list_1">
									<Strong>Inc Info</Strong>
									<table>
										<tr>
											<td>Income Type</td>
											<td><input type="text" class="form-control input-sm"
												size="40" name="inc_info_income_type_input"></td>
										</tr>
									</table>
									<div id="inc_payslip">
										<div id="inc_payslip_list_1">
											<Strong>Inc Payslip</Strong>
											<table>
												<tr>
													<td>No Month</td>
													<td><input type="text" class="form-control input-sm"
														size="40" name="inc_payslip_no_month_input"></td>
													<td>Accum Income</td>
													<td><input type="text" class="form-control input-sm"
														size="40" name="inc_payslip_accum_income_input"></td>
												</tr>
												<tr>
													<td>Bonus</td>
													<td><input type="text" class="form-control input-sm"
														size="40" name="inc_payslip_bonus_input"></td>
													<td>Sum sso</td>
													<td><input type="text" class="form-control input-sm"
														size="40" name="inc_payslip_sum_sso_input"></td>
												</tr>
												<tr>
													<td>Special Pesion</td>
													<td><input type="text" class="form-control input-sm"
														size="40" name="inc_payslip_special_pension_input"></td>
													<td>Accum Other Income</td>
													<td><input type="text" class="form-control input-sm"
														size="40" name="inc_payslip_accum_oth_income_input"></td>
												</tr>
												<tr>
													<td>Bonus Month</td>
													<td><input type="text" class="form-control input-sm"
														size="40" name="inc_payslip_bonus_month_input"></td>
													<td>Bonus Year</td>
													<td><input type="text" class="form-control input-sm"
														size="40" name="inc_payslip_bonus_year_input"></td>
												</tr>
											</table>
											<div id="inc_payslip_monthly">
												<div id="inc_payslip_monthly_list_1">
													<Strong>Inc Payslip Monthly</Strong>
													<table class="table table-striped">
														<thead>
															<tr>
																<th>Income Type</th>
																<th>Income Desc</th>
																<th>Income Oth Desc</th>
															</tr>
														</thead>
														<tbody>
															<tr>
																<td><input type="text"
																	class="form-control input-sm" size="20"
																	name="inc_payslip_monthly_income_type_input"></td>
																<td><input type="text"
																	class="form-control input-sm" size="20"
																	name="inc_payslip_monthly_income_desc_input"></td>
																<td><input type="text"
																	class="form-control input-sm" size="20"
																	name="inc_payslip_monthly_income_oth_desc_input"></td>
															</tr>
														</tbody>
													</table>
													<div id="inc_payslip_monthly_detail">
														<Strong>Inc Payslip Monthly Detail</Strong>
														<table class="table table-striped">
															<thead>
																<tr>
																	<th>Month</th>
																	<th>Year</th>
																	<th>Amount</th>
																</tr>
															</thead>
															<tbody id="inc_payslip_monthly_detail_list_1">
																<tr>
																	<td><input type="text"
																		class="form-control input-sm" size="30"
																		name="inc_payslip_monthly_detail_month_input"></td>
																	<td><input type="text"
																		class="form-control input-sm" size="30"
																		name="inc_payslip_monthly_detail_year_input"></td>
																	<td><input type="text"
																		class="form-control input-sm" size="30"
																		name="inc_payslip_monthly_detail_amount_input"></td>
																</tr>
															</tbody>
														</table>
													</div>
													<button type="button" class="btn btn-info"
														onclick="addIncPayslipMonthlyDetail('personalApplicant_list_1','inc_info_list_1','inc_payslip_list_1','inc_payslip_monthly_list_1')">Add
														Inc Payslip Monthly Detail</button>
												</div>
											</div>
											<br>
											<button type="button" class="btn btn-info"
												onclick="addIncPayslipMonthly('personalApplicant_list_1','inc_info_list_1','inc_payslip_list_1')">Add
												Inc Payslip Monthly</button>
											<br>
											<br>
										</div>
									</div>
									<button type="button" class="btn btn-info"
										onclick="addIncPayslip('personalApplicant_list_1','inc_info_list_1')">Add
										Inc Payslip</button>
									<br> <Strong>Inc Yearly Tawi</Strong>
									<table class="table table-striped">
										<thead>
											<tr>
												<th>Company Name</th>
												<th>Year</th>
												<th>No Month</th>
												<th>Income40 1</th>
												<th>Income40 2</th>
												<th>Sum SSO</th>
											</tr>
										</thead>
										<tbody id="inc_yearly_tawi">
											<tr>
												<td><input type="text" class="form-control input-sm"
													size="10" name="inc_yearly_tawi_company_name_input"></td>
												<td><input type="text" class="form-control input-sm"
													size="10" name="inc_yearly_tawi_year_input"></td>
												<td><input type="text" class="form-control input-sm"
													size="10" name="inc_yearly_tawi_no_month_input"></td>
												<td><input type="text" class="form-control input-sm"
													size="10" name="inc_yearly_tawi_income40_1_input"></td>
												<td><input type="text" class="form-control input-sm"
													size="10" name="inc_yearly_tawi_income40_2_input"></td>
												<td><input type="text" class="form-control input-sm"
													size="10" name="inc_yearly_tawi_sum_sso_input"></td>
											</tr>
										</tbody>
									</table>
									<button type="button" class="btn btn-info"
										onclick="addIncYearlyTawi('personalApplicant_list_1','inc_info_list_1')">
										Add	Yearly Tawi</button>
									<br> <Strong>Inc Taweesap</Strong>
									<table>
										<tr>
											<td>Aum</td>
											<td><input type="text" class="form-control input-sm"
												size="40" name="inc_taweesap_aum_input"></td>
										</tr>
									</table>
									<div id="inc_saving_acc">
									<Strong>Inc Saving Acc</Strong>
										<div id="inc_saving_acc_list_1">
											<table class="table table-striped">
												<thead>
													<tr>
														<th>Open Date</th>
														<th>Account No</th>
														<th>Account Name</th>
														<th>Holding Ratio</th>
														<th>Bank Code</th>
													</tr>
												</thead>
												<tbody id="inc_saving_acc_list_1">
													<tr>
														<td><input type="text" class="form-control input-sm"
															size="10" name="inc_saving_acc_open_date_input"></td>
														<td><input type="text" class="form-control input-sm"
															size="10" name="inc_saving_acc_account_no_input"></td>
														<td><input type="text" class="form-control input-sm"
															size="10" name="inc_saving_acc_account_name_input"></td>
														<td><input type="text" class="form-control input-sm"
															size="10" name="inc_saving_acc_holding_ratio_input"></td>
														<td><input type="text" class="form-control input-sm"
															size="10" name="inc_saving_acc_bank_code_input"></td>
													</tr>
												</tbody>
											</table>
											<div id="inc_saving_acc_detail">
												<div id="inc_saving_acc_detail_list">
													<table class="table table-striped">
														<thead>
															<tr>
																<th>Month</th>
																<th>Year</th>
																<th>Amount</th>
															</tr>
														</thead>
														<tbody id="inc_saving_acc_detail_list_1">
															<tr>
																<td><input type="text" class="form-control input-sm" size="10" name="inc_saving_acc_inc_saving_acc_detail_month_input"></td>
																<td><input type="text" class="form-control input-sm" size="10" name="inc_saving_acc_inc_saving_acc_detail_year_input"></td>
																<td><input type="text" class="form-control input-sm" size="10" name="inc_saving_acc_inc_saving_acc_detail_amount_input"></td>
															</tr>
														</tbody>
													</table>
												</div>
												<button type="button" class="btn btn-info" 
												onclick="addIncSavingAccDetail('personalApplicant_list_1','inc_info_list_1','inc_saving_acc_list_1')">
												Add Inc Saving Acc Detail</button>
											</div>
										</div>
									</div>
									<button type="button" class="btn btn-info"
										onclick="addIncSavingAcc('personalApplicant_list_1','inc_info_list_1')">Add
										Inc Saving Acc</button>
									<br> <Strong>Inc Salary Cert</Strong>
									<table class="table table-striped">
										<thead>
											<tr>
												<th>Income</th>
												<th>Company Name</th>
											</tr>
										</thead>
										<tbody id="inc_salary_cert_list_1">
											<tr>
												<td><input type="text" class="form-control input-sm"
													size="40" name="inc_salary_cert_income_input"></td>
												<td><input type="text" class="form-control input-sm"
													size="40" name="inc_salary_cert_company_name_input"></td>
											</tr>
										</tbody>
									</table>
									<button type="button" class="btn btn-info"
										onclick="addIncSalaryCert('personalApplicant_list_1','inc_info_list_1')">Add
										Inc Salary Cert</button>
									<br> <Strong>Inc Previous Income</Strong>
									<table>
										<tr>
											<td>Income</td>
											<td><input type="text" class="form-control input-sm"
												size="40" name="inc_previous_income_income_input"></td>
										</tr>
									</table>
									<Strong>Inc Payroll</Strong>
									<table>
										<tr>
											<td>Lowest Income</td>
											<td><input type="text" class="form-control input-sm"
												size="40" name="inc_payroll_lowestIncome_input"></td>
										</tr>
										<tr>
											<td>No Of Employee</td>
											<td><input type="text" class="form-control input-sm"
												size="40" name="inc_payroll_no_of_employee_input"></td>
										</tr>
									</table>
									<div id="inc_opn_end_fund">
										<div id="inc_opn_end_fund_list_1">
											<Strong>Inc Opn End Fund</Strong>
											<table class="table table-striped">
												<thead>
													<tr>
														<th>Open Date</th>
														<th>Account No</th>
														<th>Account Name</th>
														<th>Holding Ratio</th>
														<th>Bank Code</th>
														<th>Fund Name</th>
													</tr>
												</thead>
												<tbody>
													<tr>
														<td><input type="text" class="form-control input-sm"
															size="15" name="inc_opn_end_fund_open_date_input"></td>
														<td><input type="text" class="form-control input-sm"
															size="15" name="inc_opn_end_fund_account_no_input"></td>
														<td><input type="text" class="form-control input-sm"
															size="15" name="inc_opn_end_fund_account_name_input"></td>
														<td><input type="text" class="form-control input-sm"
															size="15" name="inc_opn_end_fund_holding_ratio_input"></td>
														<td><input type="text" class="form-control input-sm"
															size="15" name="inc_opn_end_fund_bank_code_input"></td>
														<td><input type="text" class="form-control input-sm"
															size="15" name="inc_opn_end_fund_fund_name_input"></td>
													</tr>
												</tbody>
											</table>
											<Strong>Inc Opn End Fund Detail</Strong>
											<table class="table table-striped">
												<thead>
													<tr>
														<th>Month</th>
														<th>Year</th>
														<th>Amount</th>
													</tr>
												</thead>
												<tbody id="inc_opn_end_fund_detail_list_1">
													<tr>
														<td><input type="text" class="form-control input-sm"
															size="20" name="inc_opn_end_fund_detail_month_input"></td>
														<td><input type="text" class="form-control input-sm"
															size="20" name="inc_opn_end_fund_detail_year_input"></td>
														<td><input type="text" class="form-control input-sm"
															size="20" name="inc_opn_end_fund_detail_amount_input"></td>
													</tr>
												</tbody>
											</table>
											<button type="button" class="btn btn-info"
												onclick="addIncOpnEndFundDetail('personalApplicant_list_1','inc_info_list_1','inc_opn_end_fund_list_1')">Add
												Inc Opn End Fund Detail</button>
											<br>
										</div>
									</div>
									<button type="button" class="btn btn-info"
										onclick="addIncOpnEndFund('personalApplicant_list_1','inc_info_list_1')">Add
										Inc Opn End Fund</button>
									<br>
									<div id="inc_monthly_tawi">
										<div id="inc_monthly_tawi_list_1">
											<Strong>Inc Monthly Tawi</Strong>
											<table class="table table-striped">
												<thead>
													<tr>
														<th>Income Type</th>
														<th>Compare Flag</th>
														<th>Company Name</th>
													</tr>
												<tbody>
													<tr>
														<td><input type="text" class="form-control input-sm"
															size="40" name="inc_monthly_tawi_income_type_input"></td>
														<td><input type="text" class="form-control input-sm"
															size="40" name="inc_monthly_tawi_company_name_input"></td>
														<td><input type="text" class="form-control input-sm"
															size="40" name="inc_monthly_tawi_compare_flag_input"></td>
													</tr>
												</tbody>
											</table>
											<Strong>Inc Monthly Tawi Detail</Strong>
											<table class="table table-striped">
												<thead>
													<tr>
														<th>Month</th>
														<th>Year</th>
														<th>Amount</th>
													</tr>
												</thead>
												<tbody id="inc_monthly_tawi_detail_list_1">
													<tr>
														<td><input type="text" class="form-control input-sm"
															size="30" name="inc_monthly_tawi_detail_month_input"></td>
														<td><input type="text" class="form-control input-sm"
															size="30" name="inc_monthly_tawi_detail_year_input"></td>
														<td><input type="text" class="form-control input-sm"
															size="30" name="inc_monthly_tawi_detail_amount_input"></td>
													</tr>
												</tbody>
											</table>
											<button type="button" class="btn btn-info"
												onclick="addIncMonthlyTawiDetail('personalApplicant_list_1','inc_info_list_1','inc_monthly_tawi_list_1')">Add
												Inc Monthly Tawi Detail</button>
											<br>
										</div>
									</div>
									<button type="button" class="btn btn-info"
										onclick="addIncMonthlyTawi('personalApplicant_list_1','inc_info_list_1')">Add
										Inc Monthly Tawi</button>
									<br> <Strong>Inc KVI</Strong>
									<table>
										<tr>
											<td>Percent Cheque Return</td>
											<td><input type="text" class="form-control input-sm"
												size="40" name="inc_kvi_percent_cheque_return_input"></td>
										</tr>
										<tr>
											<td>Percent Cheque Return</td>
											<td><input type="text" class="form-control input-sm"
												size="40" name="inc_kvi_verified_income_input"></td>
										</tr>
									</table>
									<Strong>Inc Fixed Guarantee</Strong>
									<table>
										<tr>
											<td>Account No</td>
											<td><input type="text" class="form-control input-sm"
												size="40" name="inc_fixed_guarantee_account_no_input"></td>
											<td>Sub</td>
											<td><input type="text" class="form-control input-sm"
												size="40" name="inc_fixed_guarantee_sub_input"></td>
										</tr>
										<tr>
											<td>Account Name</td>
											<td><input type="text" class="form-control input-sm"
												size="40" name="inc_fixed_guarantee_account_name_input"></td>
											<td>Retention Date</td>
											<td><input type="text" class="form-control input-sm"
												size="40" name="inc_fixed_guarantee_retention_date_input"></td>
										</tr>
										<tr>
											<td>Branch No</td>
											<td><input type="text" class="form-control input-sm"
												size="40" name="inc_fixed_guarantee_branch_no_input"></td>
											<td>Rentention Type</td>
											<td><input type="text" class="form-control input-sm"
												size="40" name="inc_fixed_guarantee_rentention_type_input"></td>
										</tr>
										<tr>
											<td>Rentention Amt</td>
											<td><input type="text" class="form-control input-sm"
												size="40" name="inc_fixed_guarantee_rentention_amt_input"></td>
										</tr>
									</table>
									<Strong>Inc Fixed Acc</Strong>
									<table>
										<tr>
											<td>Open Date</td>
											<td><input type="text" class="form-control input-sm"
												size="40" name="inc_fixed_acc_open_date_input"></td>
											<td>Account No</td>
											<td><input type="text" class="form-control input-sm"
												size="40" name="inc_fixed_acc_account_no_input"></td>
										</tr>
										<tr>
											<td>Account Name</td>
											<td><input type="text" class="form-control input-sm"
												size="40" name="inc_fixed_acc_account_name_input"></td>
											<td>Holding Ratio</td>
											<td><input type="text" class="form-control input-sm"
												size="40" name="inc_fixed_acc_holding_ratio_input"></td>
										</tr>
										<tr>
											<td>Account Balance</td>
											<td><input type="text" class="form-control input-sm"
												size="40" name="inc_fixed_acc_account_balance_input"></td>
											<td>Bank Code</td>
											<td><input type="text" class="form-control input-sm"
												size="40" name="inc_fixed_acc_bank_code_input"></td>
										</tr>
									</table>
									<Strong>Inc Fin Instrument</Strong>
									<table>
										<tr>
											<td>Open Date</td>
											<td><input type="text" class="form-control input-sm"
												size="40" name="inc_fin_instrument_open_date_input"></td>
											<td>Expire Date</td>
											<td><input type="text" class="form-control input-sm"
												size="40" name="inc_fin_instrument_expire_date_input"></td>
										</tr>
										<tr>
											<td>Issuer Name</td>
											<td><input type="text" class="form-control input-sm"
												size="40" name="inc_fin_instrument_issuer_name_input"></td>
											<td>Instrument Type</td>
											<td><input type="text" class="form-control input-sm"
												size="40" name="inc_fin_instrument_instrument_type_input"></td>
										</tr>
										<tr>
											<td>Holder Name</td>
											<td><input type="text" class="form-control input-sm"
												size="40" name="inc_fin_instrument_holder_name_input"></td>
											<td>Holding Ratio</td>
											<td><input type="text" class="form-control input-sm"
												size="40" name="inc_fin_instrument_holding_ratio_input"></td>
										</tr>
										<tr>
											<td>Current Balance</td>
											<td><input type="text" class="form-control input-sm"
												size="40" name="inc_fin_instrument_current_balance_input"></td>
											<td>Type Desc</td>
											<td><input type="text" class="form-control input-sm"
												size="40"
												name="inc_fin_instrument_instrument_type_desc_input"></td>
										</tr>
									</table>
									<Strong>Inc Cls End Fund</Strong>
									<table>
										<tr>
											<td>Bank Code</td>
											<td><input type="text" class="form-control input-sm"
												size="40" name="inc_cls_end_fund_bank_code_input"></td>
											<td>Fund Name</td>
											<td><input type="text" class="form-control input-sm"
												size="40" name="inc_cls_end_fund_fund_name_input"></td>
										</tr>
										<tr>
											<td>Account No</td>
											<td><input type="text" class="form-control input-sm"
												size="40" name="inc_cls_end_fund_account_no_input"></td>
											<td>Account Name</td>
											<td><input type="text" class="form-control input-sm"
												size="40" name="inc_cls_end_fund_account_name_input"></td>
										</tr>
										<tr>
											<td>Holding Ratio</td>
											<td><input type="text" class="form-control input-sm"
												size="40" name="inc_cls_end_fund_holding_ratio_input"></td>
											<td>Account Balance</td>
											<td><input type="text" class="form-control input-sm"
												size="40" name="inc_cls_end_fund_account_balance_input"></td>
										</tr>
									</table>
									<div id="inc_bank_statement">
										<div id="inc_bank_statement_list_1">
											<Strong>Inc Bank Statement</Strong>
											<table class="table table-striped">
												<thead>
													<tr>
														<th>Bank Code</th>
														<th>Statement Code</th>
														<th>Additional Code</th>
													</tr>
												</thead>
												<tbody>
													<tr>
														<td><input type="text" class="form-control input-sm"
															size="30" name="inc_bank_statement_bank_code_input"></td>
														<td><input type="text" class="form-control input-sm"
															size="30" name="inc_bank_statement_statement_code_input"></td>
														<td><input type="text" class="form-control input-sm"
															size="30" name="inc_bank_statement_additional_code_input"></td>
													</tr>
												</tbody>
											</table>
											<Strong>Inc Bank Statement Detail</Strong>
											<table class="table table-striped">
												<thead>
													<tr>
														<th>Month</th>
														<th>Year</th>
														<th>Amount</th>
													</tr>
												</thead>
												<tbody id="inc_bank_statement_detail_list_1">
													<tr>
														<td><input type="text" class="form-control input-sm"
															size="30" name="inc_bank_statement_detail_month_input"></td>
														
														<td><input type="text" class="form-control input-sm"
															size="30" name="inc_bank_statement_detail_year_input"></td>
														
														<td><input type="text" class="form-control input-sm"
															size="30" name="inc_bank_statement_detail_amount_input"></td>
													</tr>
												</tbody>
											</table>
											<button type="button" class="btn btn-info" 
											onclick="addIncBankStatementDetail('personalApplicant_list_1','inc_info_list_1','inc_bank_statement_list_1')">
											Add Inc Bank Statement Detail</button>
										</div>
									</div>
									<button type="button" class="btn btn-info"
									onclick="addIncBankStatement('personalApplicant_list_1','inc_info_list_1')">Add Inc Bank Statement</button>
									<br>
									<Strong>Orig Bundle SME</Strong>
									<table>
										<tr>
											<td>Applicant Quality</td>
											<td><input type="text" class="form-control input-sm"
												size="40" name="orig_bundle_sme_applicant_quality_input"></td>
											<td>Approval Limit</td>
											<td><input type="text" class="form-control input-sm"
												size="40" name="orig_bundle_sme_approval_limit_input"></td>
										</tr>
										<tr>
											<td>Business Owner Flag</td>
											<td><input type="text" class="form-control input-sm"
												size="40" name="orig_bundle_sme_business_owner_flag_input"></td>
											<td>Corporate Ratio</td>
											<td><input type="text" class="form-control input-sm"
												size="40" name="orig_bundle_sme_corporate_ratio_input"></td>
										</tr>
										<tr>
											<td>G Dscr Reg</td>
											<td><input type="text" class="form-control input-sm"
												size="40" name="orig_bundle_sme_g_dscr_req_input"></td>
											<td>G Total Exist Payment</td>
											<td><input type="text" class="form-control input-sm"
												size="40" name="orig_bundle_sme_g_total_exist_payment_input"></td>
										</tr>
										<tr>
											<td>G Total Newpay Req</td>
											<td><input type="text" class="form-control input-sm"
												size="40" name="orig_bundle_sme_g_total_newpay_req_input"></td>
											<td>SME Borrower Type</td>
											<td><input type="text" class="form-control input-sm"
												size="40" name="orig_bundle_sme_sme_borrower_type_input"></td>
										</tr>
										<tr>
											<td>Individual Ratio</td>
											<td><input type="text" class="form-control input-sm"
												size="40" name="orig_bundle_sme_individual_ratio_input"></td>
											<td>Approved Date</td>
											<td><input type="text" class="form-control input-sm"
												size="40" name="orig_bundle_sme_approvedDate_input"></td>
										</tr>
									</table>
									<Strong>Orig Bundle KL</Strong>
									<table>
										<tr>
											<td>Verfied Income</td>
											<td><input type="text" class="form-control input-sm"
												size="40" name="orig_bundle_kl_verfied_income_input"></td>
											<td>Verfied Date</td>
											<td><input type="text" class="form-control input-sm"
												size="40" name="orig_bundle_kl_verfied_date_input"></td>
										</tr>
										<tr>
											<td>Estimated Income</td>
											<td><input type="text" class="form-control input-sm"
												size="40" name="orig_bundle_kl_estimated_income_input"></td>
										</tr>
									</table>
									<Strong>Orig Bundle HL</Strong>
									<table>
										<tr>
											<td>Approve Credit Line</td>
											<td><input type="text" class="form-control input-sm"
												size="40" name="orig_bundle_hl_approve_credit_line_input"></td>
											<td>Verfied Income</td>
											<td><input type="text" class="form-control input-sm"
												size="40" name="orig_bundle_hl_verified_income_input"></td>
										</tr>
										<tr>
											<td>Approved Date</td>
											<td><input type="text" class="form-control input-sm"
												size="40" name="orig_bundle_hl_approved_date_input"></td>
											<td>CC Approved Amt</td>
											<td><input type="text" class="form-control input-sm"
												size="40" name="orig_bundle_hl_cc_approved_amt_input"></td>
										</tr>
										<tr>
											<td>KEC Approved Amt</td>
											<td><input type="text" class="form-control input-sm"
												size="40" name="orig_bundle_hl_kec_approved_amt_input"></td>
										</tr>
									</table>
									<Strong>Inc Payroll File Info</Strong>
									<table class="table table-striped">
										<thead>
											<tr>
												<th>Curr Month</th>
												<th>Prev 1 Month</th>
												<th>Prev 2 Month</th>
												<th>Prev 3 Month</th>
												<th>Prev 4 Month</th>
												<th>Prev 5 Month</th>
												<th>Prev 6 Month</th>
												<th>Prev 7 Month</th>
												<th>Prev 8 Month</th>
												<th>Prev 9 Month</th>
												<th>Prev 10 Month</th>
												<th>Prev 11 Month</th>
											</tr>
										</thead>
										<tbody id="inc_payroll_file_info_list_1">
											<tr>
												<td><input type="text" class="form-control input-sm"
													size="5" name="inc_payroll_file_info_payrollCurrMonth"></td>
												<td><input type="text" class="form-control input-sm"
													size="5" name="inc_payroll_file_info_payrollPrev1Month"></td>
												<td><input type="text" class="form-control input-sm"
													size="5" name="inc_payroll_file_info_payrollPrev2Month"></td>
												<td><input type="text" class="form-control input-sm"
													size="5" name="inc_payroll_file_info_payrollPrev3Month"></td>
												<td><input type="text" class="form-control input-sm"
													size="5" name="inc_payroll_file_info_payrollPrev4Month"></td>
												<td><input type="text" class="form-control input-sm"
													size="5" name="inc_payroll_file_info_payrollPrev5Month"></td>
												<td><input type="text" class="form-control input-sm"
													size="5" name="inc_payroll_file_info_payrollPrev6Month"></td>
												<td><input type="text" class="form-control input-sm"
													size="5" name="inc_payroll_file_info_payrollPrev7Month"></td>
												<td><input type="text" class="form-control input-sm"
													size="5" name="inc_payroll_file_info_payrollPrev8Month"></td>
												<td><input type="text" class="form-control input-sm"
													size="5" name="inc_payroll_file_info_payrollPrev9Month"></td>
												<td><input type="text" class="form-control input-sm"
													size="5" name="inc_payroll_file_info_payrollPrev10Month"></td>
												<td><input type="text" class="form-control input-sm"
													size="5" name="inc_payroll_file_info_payrollPrev11Month"></td>
											</tr>
										</tbody>
									</table>
									<button type="button" class="btn btn-info"
										onclick="addIncPayrollFileInfo('personalApplicant_list_1','inc_info_list_1')">Add
										Inc Payroll File Info</button>
									<br>
									<br>
								</div>
							</div>
							<button type="button" class="btn btn-info"
								onclick="addIncInfo('personalApplicant_list_1')">Add
								Inc Info</button>
						</div>
						<div class="tab-pane fade" id="documentList_1">
							<table class="table table-striped" id="documentList">
								<thead>
									<tr>
										<th>Document Code</th>
										<th>Document Type</th>
										<th>Expiry Date</th>
									</tr>
								</thead>
								<tbody id="documentList_list_1">
									<tr>
										<td><input type="text" class="form-control input-sm"
											size="40" name="documents_documentCode_input"></td>
										<td><input type="text" class="form-control input-sm"
											size="40" name="documents_documentType_input"></td>
										<td><input type="text" class="form-control input-sm"
											size="40" name="documents_expiryDate_input"></td>
									</tr>
								</tbody>
							</table>
							<button type="button" class="btn btn-info"
								onclick="addDocumentList('personalApplicant_list_1')">Add
								Document List</button>
						</div>
						<div class="tab-pane fade" id="reference_1">
							<table class="table table-striped" id="reference">
								<thead>
									<tr>
										<th>Title</th>
										<th>Last Name</th>
										<th>First Name</th>
										<th>Relationship With Borrower</th>
										<th>Fixed Line Home</th>
										<th>Fixed Line Work</th>
										<th>Mobile Number</th>
									</tr>
								</thead>
								<tbody id="reference_list_1">
									<tr>
										<td><input type="text" class="form-control input-sm"
											size="15" name="reference_title_input"></td>
										<td><input type="text" class="form-control input-sm"
											size="15" name="reference_lastName_input"></td>
										<td><input type="text" class="form-control input-sm"
											size="15" name="reference_firstName_input"></td>
										<td><input type="text" class="form-control input-sm"
											size="15" name="reference_relationshipWithBorrower_input"></td>
										<td><input type="text" class="form-control input-sm"
											size="15" name="reference_fixedLineHome_input"></td>
										<td><input type="text" class="form-control input-sm"
											size="15" name="reference_fixedLineWork_input"></td>
										<td><input type="text" class="form-control input-sm"
											size="15" name="reference_mobileNumber_input"></td>
									</tr>
								</tbody>
							</table>
							<button type="button" class="btn btn-info"
								onclick="addReference('personalApplicant_list_1')">Add
								Reference</button>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
	<button type="button" class="btn btn-info"
		onclick="addPersonalApplicant('personalApplicant_list_1')">Add
		Personal Applicant</button>
	<br>
	<br>
	<div id="creditDetail">
		<div id="creditDetail_list_1" class="well">
			<form class="FicoForm_input form-inline">
				<Strong>Credit Details</Strong>
				<table>
					<tr>
						<td>Cash On Transfer</td>
						<td><input type="text" class="form-control input-sm"
							size="40" name="creditDetails_cashOnTransfer_input"></td>
						<td>Type Of KEC Program</td>
						<td><input type="text" class="form-control input-sm"
							size="40" name="creditDetails_typeOfKecProgram_input"></td>
					</tr>
					<tr>
						<td>Project Code</td>
						<td><input type="text" class="form-control input-sm"
							size="40" name="creditDetails_projectCode_input"></td>
						<td>Unique Product Id</td>
						<td><input type="text" class="form-control input-sm"
							size="40" name="creditDetails_uniqueProductId_input"></td>
					</tr>
					<tr>
						<td>Is Auto Payment</td>
						<td><input type="text" class="form-control input-sm"
							size="40" name="creditDetails_isAutoPayment_input"></td>
						<td>Transfer Percentage</td>
						<td><input type="text" class="form-control input-sm"
							size="40" name="creditDetails_transferPercentage_input"></td>
					</tr>
					<tr>
						<td>Sp Sign Off Date</td>
						<td><input type="text" class="form-control input-sm"
							size="40" name="creditDetails_spSignOffDate_input"></td>
						<td>Request Credit Limit Percent</td>
						<td><input type="text" class="form-control input-sm"
							size="40" name="creditDetails_requestCreditLimitPercent_input"></td>
					</tr>
					<tr>
						<td>Payment Method</td>
						<td><input type="text" class="form-control input-sm"
							size="40" name="creditDetails_paymentMethod_input"></td>
						<td>Product Mailing Address Type</td>
						<td><input type="text" class="form-control input-sm"
							size="40" name="creditDetails_productMailingAddressType_input"></td>
					</tr>
					<tr>
						<td>Wealth Project Code</td>
						<td><input type="text" class="form-control input-sm"
							size="40" name="creditDetails_wealthProjectCode_input"></td>
						<td>Account Number MC</td>
						<td><input type="text" class="form-control input-sm"
							size="40" name="creditDetails_accountNumberMC_input"></td>
					</tr>
					<tr>
						<td>Requested Amount</td>
						<td><input type="text" class="form-control input-sm"
							size="40" name="creditDetails_requestedAmount_input"></td>
						<td>Requested Term</td>
						<td><input type="text" class="form-control input-sm"
							size="40" name="creditDetails_requestedTerm_input"></td>
					</tr>
					<tr>
						<td>Ca Final Decision</td>
						<td><input type="text" class="form-control input-sm"
							size="40" name="creditDetails_caFinalDecision_input"></td>
						<td>Sub Product</td>
						<td><input type="text" class="form-control input-sm"
							size="40" name="creditDetails_subProduct_input"></td>
					</tr>
					<tr>
						<td>Ca Approved Amount</td>
						<td><input type="text" class="form-control input-sm"
							size="40" name="creditDetails_caApprovedAmount_input"></td>
						<td>Account Number Inc</td>
						<td><input type="text" class="form-control input-sm"
							size="40" name="creditDetails_accountNumberInc_input"></td>
					</tr>
					<tr>
						<td>Is Mailing Address Match Cardlink</td>
						<td><input type="text" class="form-control input-sm"
							size="40"
							name="creditDetails_isMailingAddressMatchCardlink_input"></td>
						<td>Is Mailing Address Match NCB</td>
						<td><input type="text" class="form-control input-sm"
							size="40" name="creditDetails_isMailingAddressMatchNcb_input"></td>
					</tr>
					<tr>
						<td>Is Mailing Address Match Id</td>
						<td><input type="text" class="form-control input-sm"
							size="40" name="creditDetails_isMailingAddressMatchId_input"></td>
					</tr>
					<tr>
						<td>House Number</td>
						<td><input type="text" class="form-control input-sm"
							size="40" name="mailingAddress_address_houseNumber_input"></td>
						<td>Village</td>
						<td><input type="text" class="form-control input-sm"
							size="40" name="mailingAddress_address_village_input"></td>
					</tr>
					<tr>
						<td>Building Name</td>
						<td><input type="text" class="form-control input-sm"
							size="40" name="mailingAddress_address_buildingName_input"></td>
						<td>Sub District</td>
						<td><input type="text" class="form-control input-sm"
							size="40" name="mailingAddress_address_subDistrict_input"></td>
					</tr>
					<tr>
						<td>District</td>
						<td><input type="text" class="form-control input-sm"
							size="40" name="mailingAddress_address_district_input"></td>
						<td>Province</td>
						<td><input type="text" class="form-control input-sm"
							size="40" name="mailingAddress_address_province_input"></td>
					</tr>
					<tr>
						<td>Postal Code</td>
						<td><input type="text" class="form-control input-sm"
							size="40" name="mailingAddress_address_postalCode_input"></td>
						<td>Country</td>
						<td><input type="text" class="form-control input-sm"
							size="40" name="mailingAddress_address_country_input"></td>
					</tr>
					<tr>
						<td>Fixed Line Number</td>
						<td><input type="text" class="form-control input-sm"
							size="40" name="mailingAddress_address_fixedLineNumber_input"></td>
						<td>Type</td>
						<td><input type="text" class="form-control input-sm"
							size="40" name="mailingAddress_address_type_input"></td>
					</tr>
					<tr>
						<td>Floor Number</td>
						<td><input type="text" class="form-control input-sm"
							size="40" name="mailingAddress_address_floorNumber_input"></td>
						<td>Moo</td>
						<td><input type="text" class="form-control input-sm"
							size="40" name="mailingAddress_address_moo_input"></td>
					</tr>
					<tr>
						<td>Soi</td>
						<td><input type="text" class="form-control input-sm"
							size="40" name="mailingAddress_address_soi_input"></td>
						<td>Street</td>
						<td><input type="text" class="form-control input-sm"
							size="40" name="mailingAddress_address_street_input"></td>
					</tr>
				</table>
				<table class="table table-striped">
					<thead>
						<tr>
							<th>Row</th>
							<th>Applicant Type</th>
						</tr>
					</thead>
					<tbody id="associatedApplicant_list_1">
						<tr>
							<td>1</td>
							<td><input type="text" class="form-control input-sm"
								size="60" name="associatedApplicant_applicantType_input"></td>
						</tr>
					</tbody>
				</table>
				<button type="button" class="btn btn-info"
					onclick="addAssociatedApplicant('creditDetail_list_1')">Add
					Associated Applicant</button>
			</form>
		</div>
	</div>
	<button type="button" class="btn btn-info" onclick="addCreditDetails()">Add
		Credit Details</button>
	<br>
	<br>
	<form class="well FicoForm_input form-inline">
		<Strong>Priority Pass Quota</Strong>
		<table>
			<tr>
				<td>Existing Assigned Count</td>
				<td><input type="text" class="form-control" size="40"
					name="priorityPassQuota_existingAssignedCount_input"></td>
			</tr>
		</table>
	</form>
	<div class="well" id="createKVIApplication_RqRs" style="display: none;">
  		<Strong>Request/Response JSON Format</Strong><br>
  		Request :
		<textarea class="form-control" name="requestJson" rows="5" readonly></textarea>
		Response :
		<textarea class="form-control" name="responseJson" rows="5" readonly></textarea>
  	</div>
	<button type="button" class="btn btn-info" onclick="request_service()">Request Service</button>
  	<button type="button" class="btn btn-info btn-md" onclick="jsonFormat()">Service Data</button>
	<br><br>
</body>
</html>