<!DOCTYPE html>
<%@page import="com.google.gson.Gson"%>
<%@page import="com.eaf.inf.batch.ulo.notification.condition.NotificationCondition"%>
<%@page import="com.eaf.inf.batch.ulo.notification.model.JobCodeDataM"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.service.module.manual.CVRSDQ0001ServiceProxy"%>
<%@page import="com.eaf.service.module.manual.CIS1048O01ServiceProxy"%>
<%@page import="com.eaf.service.module.manual.CIS1047O01ServiceProxy"%>
<%@page import="com.eaf.service.module.manual.CIS1044U01ServiceProxy"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.service.common.controller.ServiceController"%>
<%@page import="com.eaf.j2ee.pattern.model.ScreenFlow"%>
<%@page import="com.eaf.service.module.manual.CIS1034A01ServiceProxy"%>
<%@page import="com.eaf.service.module.manual.CIS1036A01ServiceProxy"%>
<%@page import="com.eaf.service.module.manual.CIS0315U01ServiceProxy"%>
<%@page import="com.eaf.service.module.manual.CIS1035A01ServiceProxy"%>
<%@page import="com.eaf.service.module.manual.CIS1046A01ServiceProxy"%>
<%@page import="com.eaf.service.module.manual.CIS0314I01ServiceProxy"%>
<%@page import="com.eaf.service.module.manual.CBS1215I01ServiceProxy"%>
<%@page import="com.eaf.service.module.manual.CIS0222I01ServiceProxy"%>
<%@page import="com.eaf.service.module.manual.CIS1037A01ServiceProxy"%>
<%@page import="com.eaf.service.module.manual.SMSServiceProxy"%>
<%@page import="com.eaf.service.common.servlet.SmsService"%>
<%@page contentType="text/html; charset=UTF-8"%>
<html lang="en">
<head>
<title>Web Service</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="/ServiceCenterWeb/libs/bootstrap-3.3.4-dist/css/bootstrap.min.css">
<script src="/ServiceCenterWeb/libs/jquery-1.11.2.min.js"></script>
<link rel="stylesheet"
	href="/ServiceCenterWeb/libs/jquery-ui-1.11.4.custom/jquery-ui.css">
<script
	src="/ServiceCenterWeb/libs/jquery-ui-1.11.4.custom/jquery-ui.js"></script>
	
<link rel="stylesheet"
	href="/ServiceCenterWeb/libs/bootstrap-datepicker/bootstrap-datepicker3.css">
<script
	src="/ServiceCenterWeb/libs/bootstrap-datepicker/bootstrap-datepicker.min.js"></script>
	
<script
	src="/ServiceCenterWeb/libs/bootstrap-3.3.4-dist/js/bootstrap.min.js"></script>
<link href="/ServiceCenterWeb/css/index.css" rel="stylesheet"
	type="text/css">
<script src="/ServiceCenterWeb/js/eModal.js"></script>
<script>
	(function($) {
		$.fn.serializeFormJSON = function() {
			var o = {};
			var a = this.serializeArray();
			$.each(a, function() {
				if (o[this.name]) {
					if (!o[this.name].push) {
						o[this.name] = [ o[this.name] ];
					}
					o[this.name].push(this.value || '');
				} else {
					o[this.name] = this.value || '';
				}
			});
			return o;
		};
	})(jQuery);
	function loadScreen(screenId) {
		var url = '/ServiceCenterWeb/ServiceController?screenId=' + screenId;
		$.post(url, function(data, status, xhr) {
			try {
				$('#showTab').html(data);
			} catch (e) {
			}
		});
	}
</script>
<style type="text/css">
a:hover {
	cursor: pointer;
}
</style>
</head>
<%-- <%

		ArrayList<JobCodeDataM> jobList  = new ArrayList<JobCodeDataM>();
		JobCodeDataM jobCode= new JobCodeDataM();
		jobCode.setJobCode("SD3A");
		jobList.add(jobCode);
		
		NotificationCondition noticond = new NotificationCondition();
		System.out.println(new Gson().toJson(noticond.getSendToVCEmpManagers("06", jobList)));
%> --%>
<%
	String screenId = request.getParameter("screen");
	System.out.println("screenId: " + screenId);
	if(!Util.empty(screenId))
	{
		%>
			<script>
				loadScreen('<%=screenId%>');
				$('head title').text('WS - ' + '<%=screenId%>');
			</script>
		<%
	}
%>
<body>
	<div class="topbar">
		<font color="white" size="6" style="padding-left: 20px;">Web
			Services Center</font>
		<button type="button" onclick="location.href='/ServiceCenterWeb/logout';" style="float: right; margin-right: 0.8%; margin-top: 0.5%;" class="btn btn-success">
        	<span class="glyphicon glyphicon-off" ></span> Logout
        </button>
		<font color="white" size="4" style="float: right; margin-right: 1%; margin-top: 1%;">
			<%=request.getSession().getAttribute("userName")%>
		</font>
	</div>
	<div class="nav-left-bar" style="position: absolute;" id="left_bar">
		<ul>
			<li><a href="?screen=SMSServiceWS"><%=SMSServiceProxy.serviceId%></a></li>
			<li><a href="?screen=CIS0222I01WS"><%=CIS0222I01ServiceProxy.serviceId%></a></li>
			<li><a href="?screen=CBS1215I01WS"><%=CBS1215I01ServiceProxy.serviceId%></a></li>
			<li><a href="?screen=CIS0314I01WS"><%=CIS0314I01ServiceProxy.serviceId%></a></li>
			<li><a href="?screen=CIS1046A01WS"><%=CIS1046A01ServiceProxy.serviceId%></a></li>
			<li><a href="?screen=CIS1035A01WS"><%=CIS1035A01ServiceProxy.serviceId%></a></li>
			<li><a href="?screen=CIS0315U01WS"><%=CIS0315U01ServiceProxy.serviceId%></a></li>
			<li><a href="?screen=CIS1036A01WS"><%=CIS1036A01ServiceProxy.serviceId%></a></li>
			<li><a href="?screen=CIS1034A01WS"><%=CIS1034A01ServiceProxy.serviceId%></a></li>
			<li><a href="?screen=CIS1037A01WS"><%=CIS1037A01ServiceProxy.serviceId%></a></li>
			<!-- New CR 4 -->
			<li><a href="?screen=CIS1044U01WS"><%=CIS1044U01ServiceProxy.serviceId%></a></li>
			<li><a href="?screen=CIS1047O01WS"><%=CIS1047O01ServiceProxy.serviceId%></a></li>
			<li><a href="?screen=CIS1048O01WS"><%=CIS1048O01ServiceProxy.serviceId%></a></li>
			<li><a href="?screen=CVRSDQ0001WS"><%=CVRSDQ0001ServiceProxy.serviceId%></a></li>
			<li><a href="?screen=FindManager">Find Manager</a></li>
			
			<li><a href="?screen=Fico">Fico</a></li>
			<!--<li><a href="?screen=CreateFicoResponse">Create Fico Response XML</a></li>-->
			<li><a href="?screen=WFInquiryApp">WFInquiryApp</a></li>
			<li><a href="?screen=CreateKVIApplication">CreateKVIApplication</a></li>
			<li><a href="?screen=EditKVIApplication">EditKVIApplication</a></li>
			<li><a href="?screen=ImportExportData">Import/Export</a></li>
			<li><a href="?screen=BatchReport">BatchReport</a></li>
			<li><a href="?screen=UpdateApprovalStatus">UpdateApprovalStatus</a></li>
			<li><a href="?screen=FollowUp">FollowUp</a></li>
			<li><a href="?screen=UpdateCallOperator">UpdateCallOperator</a></li>
			<li><a href="?screen=FollowUpResult">FollowUpResult</a></li>
			<li><a href="?screen=CallIM">Call IM</a></li>
			<li><a href="?screen=CreateCISCustomer">Create CIS Customer</a></li>
			<li><a href="?screen=Iibservice">IIB Service</a></li>
			<li><a href="?screen=SubmitApplication">Submit Appliation</a></li>
			<li><a href="?screen=CheckProductDup">Check Dup</a></li>
			<li><a href="?screen=InfBatchLog">InfBatch Log</a></li>
			<li><a href="?screen=QRInfo">QRInfo</a></li>
			<li><a href="?screen=TransactionLog">Performance Report</a></li>
			<li><a href="?screen=TransactionLog">Transaction Log</a></li>
			<li><a href="?screen=ConnectivityTest">Connectivity Test</a></li>
			<li><a href="?screen=MLPInfo">MLPInfo</a></li>
			<li><a href="?screen=QueryBox">QueryBox</a></li>
			<li><a href="?screen=LogBrowser">LogBrowser</a></li>
			<li><a href="?screen=DashboardConfig">DashboardConfig</a></li>
			<li><a href="?screen=UnlockIADupSubmit">UnlockIADupSubmit</a></li>
			<li><a href="?screen=PatchSetupDate">PatchSetupDate</a></li>
			<li><a href="?screen=BPMUtil">BPMUtil</a></li>
		</ul>
	</div>
	<%
		/* ScreenFlow screenFlow = (ScreenFlow) request.getSession()
				.getAttribute("ScreenFlow");
		if (null == screenFlow) {
			screenFlow = new ScreenFlow();
		}
		String screenId = screenFlow.getScreenId();
		String pageUrl = ServiceController.getScreenController(screenId); */
	%>
	<div class="container" id="showTab"
		style="margin-left: 180px; padding-top: 20px;">
		<%-- <%
			if (!Util.empty(pageUrl)) {
				pageContext.include("/" + pageUrl);
			}
		%> --%>
	</div>
	<div id="dialog"></div>
	<div id="dialog2"></div>
	<div id="dialog3"></div>
</body>
<script type="text/javascript">
	function jsonFormat() {
	console.log("JsonFomat#######");
		var options = {
			url : "/ServiceCenterWeb/jsp/jsonPopup.jsp",
			title : 'Service Data',
			size : eModal.size.lg
		};
		eModal.ajax(options);
	}
</script>
</html>
