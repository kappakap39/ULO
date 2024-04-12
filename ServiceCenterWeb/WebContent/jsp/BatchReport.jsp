<!DOCTYPE html>
<%@page import="java.util.HashSet"%>
<%@page import="java.util.Set"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.core.ulo.common.cont.InfBatchConstant"%>
<%@page import="java.util.List"%>
<%@page import="com.eaf.core.ulo.common.cache.InfBatchProperty"%>
<%@page import="com.eaf.core.ulo.common.util.PathUtil"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<html lang="en">
<script src="/ServiceCenterWeb/libs/jquery-1.11.2.min.js"></script>
<link rel="stylesheet" href="/ServiceCenterWeb/libs/bootstrap-3.3.4-dist/css/bootstrap.min.css">
<link rel="stylesheet" href="/ServiceCenterWeb/libs/jquery-ui-1.11.4.custom/jquery-ui.css">
<link href="/ServiceCenterWeb/css/index.css" rel="stylesheet" type="text/css">  
<style>
.blink {
	-webkit-animation-name: blinker;
	-webkit-animation-duration: 1s;
	-webkit-animation-timing-function: linear;
	-webkit-animation-iteration-count: infinite;

	-moz-animation-name: blinker;
	-moz-animation-duration: 1s;
	-moz-animation-timing-function: linear;
	-moz-animation-iteration-count: infinite;

	animation-name: blinker;
	animation-duration: 1s;
	animation-timing-function: linear;
	animation-iteration-count: infinite;
	color: graytext;
}
@-moz-keyframes blinker {  
	0% { opacity: 1.0; }
	50% { opacity: 0.0; }
	100% { opacity: 1.0; }
}
@-webkit-keyframes blinker {  
	0% { opacity: 1.0; }
	50% { opacity: 0.0; }
	100% { opacity: 1.0; }
}
@keyframes blinker {  
	0% { opacity: 1.0; }
	50% { opacity: 0.0; }
	100% { opacity: 1.0; }
}
</style>
<script>
	$(document).ready(function(){
		
	});
	
	function generateReport(batchId, butObj){
// 		$('.EXECUTE-RESULT').html('');
		butObj.disabled = true;
		try{
			$('#'+batchId).html(batchId+", PLEASE WAIT...");
		}catch(e){}
		$.ajax({
  			url : "/ServiceCenterWeb/GenerateReportServlet?batchId="+batchId,
  			type : "get",
  			data : $("#ReportForm").serialize(),
  			async: true, 
  			beforeSend : console.log($("#ReportForm").serialize()),
  			success : function(data){
  				butObj.disabled = false;
  				try{
					$('#'+batchId).html('SUCCESS.');
				}catch(e){}
  			}
  		});
	}
</script>
<%	String OUTPUT_PATH = InfBatchConstant.ReportParam.OUTPUT_PATH; %>
<body>
     <div id="Batch"> 
  		<form class="well form-inline" id="ReportForm">
  			<Strong>Generate Report</Strong>
  			<table>
  				<tr>
  					<td width="350px">Daily Report1</td>
  					<td width="100px"><button type="button" class="btn btn-info btn-md" onclick="generateReport('DAILY_REPORT1', this)">Generate</button></td>
  					<td id="DAILY_REPORT1" class='EXECUTE-RESULT'></td>
  				</tr>
  				<tr>
  					<td colspan=3>
  						<table>
  							<tr>
  								<td style="vertical-align: initial;  padding-left: 0px;">
  									<strong>OUTPUT PATH : </strong>
  								<td>
  								<td>
			  						<%
			  							List<String> taskIds = InfBatchProperty.getListInfBatchConfig("DAILY_REPORT1_BATCH_ID");
			  							List<String> paths = new ArrayList<String>();
			  							Set<String> set = new HashSet<String>();
			  							for(String taskId : taskIds){
			  								paths.add(PathUtil.getPath(taskId+"_"+OUTPUT_PATH));
			  							}
			  							set.addAll(paths);
			  							paths.clear();
			  							paths.addAll(set);
			  						%>
			  						<%=StringUtils.join(paths, ",<br>") %>
			  					</td>
			  				</tr>
			  			</table>
  					</td>
  				</tr>
  				<tr>
  					<td width="350px">Daily Report2</td>
  					<td width="100px"><button type="button" class="btn btn-info btn-md" onclick="generateReport('DAILY_REPORT2', this)">Generate</button></td>
  					<td id="DAILY_REPORT2" class='EXECUTE-RESULT'></td>
  				</tr>
  				<tr>
  					<td colspan=3>
  						<table>
  							<tr>
  								<td style="vertical-align: initial;  padding-left: 0px;">
  									<strong>OUTPUT PATH : </strong>
  								<td>
  								<td>
			  						<%
			  							taskIds = InfBatchProperty.getListInfBatchConfig("DAILY_REPORT2_BATCH_ID");
			  							paths = new ArrayList<String>();
			  							set = new HashSet<String>();
			  							for(String taskId : taskIds){
			  								paths.add(PathUtil.getPath(taskId+"_"+OUTPUT_PATH));
			  							}
			  							set.addAll(paths);
			  							paths.clear();
			  							paths.addAll(set);
			  						%>
			  						<%=StringUtils.join(paths, ",<br>") %>
			  					</td>
			  				</tr>
			  			</table>
  					</td>
  				</tr>
  				<tr>
  					<td>Weekly Report </td>
  					<td><button type="button" class="btn btn-info btn-md" onclick="generateReport('WEEKLY_REPORT', this)">Generate</button></td>
  					<td id="WEEKLY_REPORT" class='EXECUTE-RESULT'></td>
  				</tr>
  				<tr>
  					<td colspan=3>
  						<table>
  							<tr>
  								<td style="vertical-align: initial;  padding-left: 0px;">
  									<strong>OUTPUT PATH : </strong>
  								<td>
  								<td>
			  						<%
			  							taskIds = InfBatchProperty.getListInfBatchConfig("WEEKLY_REPORT_BATCH_ID");
			  							paths = new ArrayList<String>();
			  							set = new HashSet<String>();
			  							for(String taskId : taskIds){
			  								paths.add(PathUtil.getPath(taskId+"_"+OUTPUT_PATH));
			  							}
			  							set.addAll(paths);
			  							paths.clear();
			  							paths.addAll(set);
			  						%>
			  						<%=StringUtils.join(paths, ",<br>") %>
			  					</td>
			  				</tr>
			  			</table>
  					</td>
  				</tr>
  				<tr>
  					<td>Bi Weekly Report </td>
  					<td><button type="button" class="btn btn-info btn-md" onclick="generateReport('BI_WEEKLY_REPORT', this)">Generate</button></td>
  					<td id="BI_WEEKLY_REPORT" class='EXECUTE-RESULT'></td>
  				</tr>
  				<tr>
  					<td colspan=3>
  						<table>
  							<tr>
  								<td style="vertical-align: initial;  padding-left: 0px;">
  									<strong>OUTPUT PATH : </strong>
  								<td>
  								<td>
			  						<%
			  							taskIds = InfBatchProperty.getListInfBatchConfig("BI_WEEKLY_REPORT_BATCH_ID");
			  							paths = new ArrayList<String>();
			  							set = new HashSet<String>();
			  							for(String taskId : taskIds){
			  								paths.add(PathUtil.getPath(taskId+"_"+OUTPUT_PATH));
			  							}
			  							set.addAll(paths);
			  							paths.clear();
			  							paths.addAll(set);
			  						%>
			  						<%=StringUtils.join(paths, ",<br>") %>
			  					</td>
			  				</tr>
			  			</table>
  					</td>
  				</tr>
  				<tr>
  					<td>Monthly Report </td>
  					<td><button type="button" class="btn btn-info btn-md" onclick="generateReport('MONTHLY_REPORT', this)">Generate</button></td>
  					<td id="MONTHLY_REPORT" class='EXECUTE-RESULT'></td>
  				</tr>
  				<tr>
  					<td colspan=3>
  						<table>
  							<tr>
  								<td style="vertical-align: initial;  padding-left: 0px;">
  									<strong>OUTPUT PATH : </strong>
  								<td>
  								<td>
			  						<%
			  							taskIds = InfBatchProperty.getListInfBatchConfig("MONTHLY_REPORT_BATCH_ID");
			  							paths = new ArrayList<String>();
			  							set = new HashSet<String>();
			  							for(String taskId : taskIds){
			  								paths.add(PathUtil.getPath(taskId+"_"+OUTPUT_PATH));
			  							}
			  							set.addAll(paths);
			  							paths.clear();
			  							paths.addAll(set);
			  						%>
			  						<%=StringUtils.join(paths, ",<br>") %>
			  					</td>
			  				</tr>
			  			</table>
  					</td>
  				</tr>
  			</table>
  			<br>
  			<Strong>Generate Batch</Strong>
  			<table>
  				<tr>
  					<td width="350px">CardLink: Account Setup</td>
  					<td width="100px"><button type="button" class="btn btn-info btn-md" onclick="generateReport('CARDLINK_ACCOUNT_SETUP', this)">Generate</button></td>
  					<td id="CARDLINK_ACCOUNT_SETUP" class='EXECUTE-RESULT'></td>
  				</tr>
  				<tr>
  					<td colspan=3>
  						<Strong>OUTPUT PATH : </Strong>
  						<%=PathUtil.getPath("CARDLINK_ACCOUNT_SETUP_OUTPUT_PATH") %>
  					</td>
  				<tr>
  					<td>CardLink: Cash Day 1</td>
  					<td><button type="button" class="btn btn-info btn-md" onclick="generateReport('CARDLINK_CASH_DAY1', this)">Generate</button></td>
  					<td id="CARDLINK_CASH_DAY1" class='EXECUTE-RESULT'></td>
  				</tr>
  				<tr>
  					<td colspan=3>
  						<Strong>OUTPUT PATH : </Strong>
  						<%=PathUtil.getPath("CARDLINK_CASH_DAY1_OUTPUT_PATH") %>
  					</td>
  				<tr>
  				<tr>
  					<td>CardLink: Customer occupation</td>
  					<td><button type="button" class="btn btn-info btn-md" onclick="generateReport('CARDLINK_CUSTOMER_OCCUPATION', this)">Generate</button></td>
  					<td id="CARDLINK_CUSTOMER_OCCUPATION" class='EXECUTE-RESULT'></td>
  				</tr>
  				<tr>
  					<td colspan=3>
  						<Strong>OUTPUT PATH : </Strong>
  						<%=PathUtil.getPath("CARDLINK_CUSTOMER_OCCUPATION_OUTPUT_PATH") %>
  					</td>
  				<tr>
  				<tr>
  					<td>CRM-Vlink-RT: Sales performance</td>
  					<td><button type="button" class="btn btn-info btn-md" onclick="generateReport('CRM_VLINK_RT_SALE_PERFORMANCE', this)">Generate</button></td>
  					<td id="CRM_VLINK_RT_SALE_PERFORMANCE" class='EXECUTE-RESULT'></td>
  				</tr>
  				<tr>
  					<td colspan=3>
  						<Strong>OUTPUT PATH : </Strong>
  						<%=PathUtil.getPath("CRM_VLINK_RT_SALE_PERFORMANCE_OUTPUT_PATH") %>
  					</td>
  				<tr>
  				<tr>
  					<td>K-mAlert: Register K-mAlert services</td>
  					<td><button type="button" class="btn btn-info btn-md" onclick="generateReport('KM_ALERT_PERFORMANCE', this)">Generate</button></td>
  					<td id="KM_ALERT_PERFORMANCE" class='EXECUTE-RESULT'></td>
  				</tr>
  				<tr>
  					<td colspan=3>
  						<Strong>OUTPUT PATH : </Strong>
  						<%=PathUtil.getPath("KM_ALERT_PERFORMANCE_OUTPUT_PATH") %>
  					</td>
  				<tr>
  				<tr>
  					<td>TCB: Get Account no. for KPL from DIH</td>
  					<td><button type="button" class="btn btn-info btn-md" onclick="generateReport('UPDATE_ACCOUNT', this)">Generate</button></td>
  					<td id="UPDATE_ACCOUNT" class='EXECUTE-RESULT'></td>
  				</tr>
<!--   				<tr> -->
<!--   					<td>MakeAFP: Print reject letters (Non-NCB template for letter and email)</td> -->
<!--   					<td><button type="button" class="btn btn-info btn-md" onclick="generateReport('MAKE_AFP_PRINT_REJECT', this)">Generate</button></td> -->
<!--   					<td id="MAKE_AFP_PRINT_REJECT" class='EXECUTE-RESULT'></td> -->
<!--   				</tr> -->
<!--   				<tr> -->
<!--   					<td colspan=3> -->
<!--   						<table> -->
<!--   							<tr> -->
<!--   								<td style="vertical-align: initial;  padding-left: 0px;"> -->
<!--   									<strong>OUTPUT PATH : </strong> -->
<!--   								<td> -->
<!--   								<td> -->
<%-- 			  						<% --%>
<%
// 			  							taskIds = InfBatchProperty.getListInfBatchConfig("MAKE_AFP_PRINT_REJECT_BATCH_ID");
// 			  							paths = new ArrayList<String>();
// 			  							set = new HashSet<String>();
// 			  							for(String taskId : taskIds){
// 			  								paths.add(PathUtil.getPath(taskId+"_"+OUTPUT_PATH));
// 			  							}
// 			  							set.addAll(paths);
// 			  							paths.clear();
// 			  							paths.addAll(set);
%>
<%-- 			  						%> --%>
<%-- 			  						<%=StringUtils.join(paths, ",<br>") %> --%>
<!-- 			  					</td> -->
<!-- 			  				</tr> -->
<!-- 			  			</table> -->
<!--   					</td> -->
<!--   				</tr> -->
<!--   				<tr> -->
<!--   					<td>MakeAFP: Print approve letters (Individual)</td> -->
<!--   					<td><button type="button" class="btn btn-info btn-md" onclick="generateReport('MAKE_AFP_PRINT_APPROVE', this)">Generate</button></td> -->
<!--   					<td id="MAKE_AFP_PRINT_APPROVE" class='EXECUTE-RESULT'></td> -->
<!--   				</tr> -->
<!--   				<tr> -->
<!--   					<td colspan=3> -->
<!--   						<Strong>OUTPUT PATH : </Strong> -->
<%--   						<%=PathUtil.getPath("MAKE_AFP_PRINT_APPROVE_OUTPUT_PATH") %> --%>
<!--   					</td> -->
<!--   				<tr> -->
<!--   				<tr> -->
<!--   					<td>MakePDF: Generate reject letters to PDF</td> -->
<!--   					<td><button type="button" class="btn btn-info btn-md" onclick="generateReport('MAKE_PDF', this)">Generate</button></td> -->
<!--   					<td id="MAKE_PDF" class='EXECUTE-RESULT'></td> -->
<!--   				</tr> -->
<!--   				<tr> -->
<!--   					<td colspan=3> -->
<!--   						<Strong>OUTPUT PATH : </Strong> -->
<%--   						<%=PathUtil.getPath("MAKE_PDF_OUTPUT_PATH") %> --%>
<!--   					</td> -->
<!--   				<tr> -->
  				<tr>
  					<td>KVI: Close application</td>
  					<td><button type="button" class="btn btn-info btn-md" onclick="generateReport('KVI_CLOSE_APPLICATION', this)">Generate</button></td>
  					<td id="KVI_CLOSE_APPLICATION" class='EXECUTE-RESULT'></td>
  				</tr>
  				<tr>
  					<td colspan=3>
  						<Strong>OUTPUT PATH : </Strong>
  						<%=PathUtil.getPath("KVI_CLOSE_APPLICATION_OUTPUT_PATH") %>
  					</td>
  				<tr>
  				<tr>
  					<td>IM: Generate Consent</td>
  					<td><button type="button" class="btn btn-info btn-md" onclick="generateReport('GENERATE_CONSENT', this)">Generate Consent</button></td>
  					<td id="GENERATE_CONSENT" class='EXECUTE-RESULT'></td>
  				</tr>
  				<tr>
  					<td colspan=3>
  						<Strong>OUTPUT PATH : </Strong>
  						<%=PathUtil.getPath("GENERATE_CONSENT_OUTPUT_PATH") %>
  					</td>
  				<tr>
  				<tr>
  					<td>IM: Notify Completed Application(Approved, Rejected, Cancelled) to send image file to EDWS</td>
  					<td><button type="button" class="btn btn-info btn-md" onclick="generateReport('NOTIFY_COMPLETED_APP', this)">Generate</button></td>
  					<td id="NOTIFY_COMPLETED_APP" class='EXECUTE-RESULT'></td>
  				</tr>
  				<tr>
  					<td colspan=3>
  						<Strong>OUTPUT PATH : </Strong>
  						<%=PathUtil.getPath("NOTIFY_COMPLETED_APP_OUTPUT_PATH") %>
  					</td>
  				<tr>
  				<tr>
  					<td>Lotus Notes: Get DSA information</td>
  					<td><button type="button" class="btn btn-info btn-md" onclick="generateReport('LOTUS_NOTE_DSA', this)">Generate</button></td>
  					<td id="LOTUS_NOTE_DSA" class='EXECUTE-RESULT'></td>
  				</tr>
  				<tr>
  					<td colspan=3>
  						<Strong>INPUT PATH : </Strong>
  						<%=PathUtil.getPath("LOTUS_NOTE_DSA_INPUT_PATH") %>
  					</td>
  				<tr>
  				<tr>
  					<td>Refresh Application Date</td>
  					<td><button type="button" class="btn btn-info btn-md" onclick="generateReport('REFRESH_APPLICATION_DATE', this)">Generate</button></td>
  					<td id="REFRESH_APPLICATION_DATE" class='EXECUTE-RESULT'></td>
  				</tr>
  				<tr>
  					<td>Delete old NCB data</td>
  					<td><button type="button" class="btn btn-info btn-md" onclick="generateReport('DELETE_NCB_DATA', this)">Generate</button></td>
  					<td id="DELETE_NCB_DATA" class='EXECUTE-RESULT'></td>
  				</tr>
  				<tr>
  					<td><strong>Card Notification : Check out of Card no.  and send email notification</strong></td>
  					<td><button type="button" class="btn btn-info btn-md" onclick="generateReport('CARD_NOTIFICATION', this)">Generate</button></td>
  					<td id="CARD_NOTIFICATION" class='EXECUTE-RESULT'></td>
  				</tr>
  				<tr>
  					<td>Send data to Document management</td>
  					<td><button type="button" class="btn btn-info btn-md" onclick="generateReport('CREATE_WAREHOUSE', this)">Generate</button></td>
  					<td id="CREATE_WAREHOUSE" class='EXECUTE-RESULT'></td>
  				</tr>
  				<tr>
  					<td>DM dispose rejected application</td>
  					<td><button type="button" class="btn btn-info btn-md" onclick="generateReport('WAREHOUSE_DISPOSE', this)">Generate</button></td>
  					<td id="WAREHOUSE_DISPOSE" class='EXECUTE-RESULT'></td>
  				</tr>
  				<tr>
  					<td>Notification Warehouse : DM has not received document with in x days or incompleted document in x days</td>
  					<td><button type="button" class="btn btn-info btn-md" onclick="generateReport('NOTIFICATION_WARE_HOUSE', this)">Generate</button></td>
  					<td id="NOTIFICATION_WARE_HOUSE" class='EXECUTE-RESULT'></td>
  				</tr>
  				<tr>
  					<td>Close Application when send interface to cardlink </td>
  					<td><button type="button" class="btn btn-info btn-md" onclick="generateReport('CLOSE_APPLICATION_CARD_LINK', this)">Generate</button></td>
  					<td id="CLOSE_APPLICATION_CARD_LINK" class='EXECUTE-RESULT'></td>
  				</tr>
  				<tr>
  					<td>Notify Completed Application to PEGA</td>
  					<td><button type="button" class="btn btn-info btn-md" onclick="generateReport('UPDATE_APPROVAL_STATUS', this)">Generate</button></td>
  					<td id="UPDATE_APPROVAL_STATUS" class='EXECUTE-RESULT'></td>
  				</tr>
  				<tr>
  					<td>EOD Notification</td>
  					<td><button type="button" class="btn btn-info btn-md" onclick="generateReport('NOTIFICATION_EOD', this)">Generate</button></td>
  					<td id="NOTIFICATION_EOD" class='EXECUTE-RESULT'></td>
  				</tr>
  				<tr>
  					<td>TEST CALL  NOTIFICATION</td>
  					<td><button type="button" class="btn btn-info btn-md" onclick="generateReport('NOTIFY_NOTIFICATION_BATCH', this)">Generate</button></td>
  					<td id="NOTIFY_NOTIFICATION_BATCH" class='EXECUTE-RESULT'></td>
  				</tr>
  				<tr>
  					<td>Approve letter</td>
  					<td><button type="button" class="btn btn-info btn-md" onclick="generateReport('APPROVE_LETTER', this)">Generate</button></td>
  					<td id="APPROVE_LETTER" class='EXECUTE-RESULT'></td>
  				</tr>
  				<tr>
  					<td colspan=3>
  						<Strong>OUTPUT PATH : </Strong>
  						<%=PathUtil.getPath("APPROVE_LETTER_OUTPUT_PATH") %>
  					</td>
  				<tr>
  				<tr>
  					<td>Reject letter</td>
  					<td><button type="button" class="btn btn-info btn-md" onclick="generateReport('REJECT_LETTER', this)">Generate</button></td>
  					<td id="REJECT_LETTER" class='EXECUTE-RESULT'></td>
  				</tr>
  				<tr>
  					<td colspan=3>
  						<Strong>OUTPUT PATH : </Strong>
  						<%=PathUtil.getPath("REJECT_LETTER_OUTPUT_PATH") %>
  					</td>
  				<tr>
  				<tr>
  					<td>Reject letter pdf</td>
  					<td><button type="button" class="btn btn-info btn-md" onclick="generateReport('REJECT_LETTER_PDF', this)">Generate</button></td>
  					<td id="REJECT_LETTER_PDF" class='EXECUTE-RESULT'></td>
  				</tr>
  				<tr>
  					<td colspan=3>
  						<Strong>OUTPUT PATH : </Strong>
  						<%=PathUtil.getPath("REJECT_LETTER_PDF_OUTPUT_PATH") %>
  					</td>
  				<tr>
  				
  				<tr>
  					<td>Batch Operation</td>
  					<td><button type="button" class="btn btn-info btn-md" onclick="generateReport('BATCH_OPERATION', this)">Generate</button></td>
  					<td id="WAREHOUSE_DISPOSE" class='EXECUTE-RESULT'></td>
  				</tr>
  				
  				<tr>
  					<td>Batch Payroll</td>
  					<td><button type="button" class="btn btn-info btn-md" onclick="generateReport('BATCH_PAYROLL', this)">Generate</button></td>
  					<td id="WAREHOUSE_DISPOSE" class='EXECUTE-RESULT'></td>
  				</tr>
  				
  				<!-- BATCH CAPPORT -->
  				<tr>
  					<td>Batch Cap Port</td>
  					<td><button type="button" class="btn btn-info btn-md" onclick="generateReport('BATCH_CAP_PORT', this)">Generate</button></td>
  					<td id="WAREHOUSE_DISPOSE" class='EXECUTE-RESULT'></td>
  				</tr>
  				
  				<!-- BATCH BILL CYCLE -->
  				<tr>
  					<td>Batch Bill Cycle</td>
  					<td><button type="button" class="btn btn-info btn-md" onclick="generateReport('BATCH_BILLING_CYCLE', this)">Generate</button></td>
  					<td id="BATCH_BILLING_CYCLE" class='EXECUTE-RESULT'></td>
  				</tr>
  			
  				<tr>
  					<td>Batch DEQ AddressZipCode</td>
  					<td><button type="button" class="btn btn-info btn-md" onclick="generateReport('BATCH_DQE_ADDRESS_ZIPCODE', this)">Generate</button></td>
  					<td id="BATCH_DQE_ADDRESS_ZIPCODE" class='EXECUTE-RESULT'></td>
  				</tr>
  				
  				<tr>
  					<td>Batch Kmobile </td>
  					<td><button type="button" class="btn btn-info btn-md" onclick="generateReport('BATCH_KMOBILE', this)">Generate</button></td>
  					<td id="BATCH_KMOBILE" class='EXECUTE-RESULT'></td>
  				</tr>
  				<tr>
  					<td><strong>Purge Inactive User Data (ORIG_USER)</strong></td>
  					<td><button type="button" class="btn btn-info btn-md" onclick="generateReport('PURGE_INACTIVE_USERDATA', this)">Generate</button></td>
  					<td id="PURGE_INACTIVE_USERDATA" class='EXECUTE-RESULT'></td>
  				</tr>
  				<tr>
  					<td><strong>Purge user Inactive (ORIG_IAS)</strong></td>
  					<td><button type="button" class="btn btn-info btn-md" onclick="generateReport('PURGE_USER_INACTIVE', this)">Generate</button></td>
  					<td id="PURGE_USER_INACTIVE" class='EXECUTE-RESULT'></td>
  				</tr>
  				<tr>
  					<td><strong>Card Link Result</strong></td>
  					<td><button type="button" class="btn btn-info btn-md" onclick="generateReport('CARD_LINK_RESULT', this)">Generate</button></td>
  					<td id="CARD_LINK_RESULT" class='EXECUTE-RESULT'></td>
  				</tr>
  				<tr>
  					<td><strong>SMS Notification Next Day</strong></td>
  					<td><button type="button" class="btn btn-info btn-md" onclick="generateReport('NOTIFICATION_SMS_NEXT_DAY', this)">Generate</button></td>
  					<td id="NOTIFICATION_SMS_NEXT_DAY" class='EXECUTE-RESULT'></td>
  				</tr>
  			</table>  				
  		</form>
  		<br><br>
  	</div>
</body>
</html>
