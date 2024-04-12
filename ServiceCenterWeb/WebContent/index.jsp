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
<%

		ArrayList<JobCodeDataM> jobList  = new ArrayList<JobCodeDataM>();
		JobCodeDataM jobCode= new JobCodeDataM();
		jobCode.setJobCode("SD3A");
		jobList.add(jobCode);
		
		NotificationCondition noticond = new NotificationCondition();
		System.out.println(new Gson().toJson(noticond.getSendToVCEmpManagers("06", jobList)));
 %>

<body>
	<div class="topbar">
		<font color="white" size="6" style="padding-left: 20px;">Web
			Services Center</font>
	</div>
	<div class="nav-left-bar" style="position: absolute;" id="left_bar">
		<ul>
			<li><a href="#" onclick="loadScreen('SMSServiceWS');"><%=SMSServiceProxy.serviceId%></a></li>
			<li><a href="#" onclick="loadScreen('CIS0222I01WS');"><%=CIS0222I01ServiceProxy.serviceId%></a></li>
			<li><a href="#" onclick="loadScreen('CBS1215I01WS');"><%=CBS1215I01ServiceProxy.serviceId%></a></li>
			<li><a href="#" onclick="loadScreen('CIS0314I01WS');"><%=CIS0314I01ServiceProxy.serviceId%></a></li>
			<li><a href="#" onclick="loadScreen('CIS1046A01WS');"><%=CIS1046A01ServiceProxy.serviceId%></a></li>
			<li><a href="#" onclick="loadScreen('CIS1035A01WS');"><%=CIS1035A01ServiceProxy.serviceId%></a></li>
			<li><a href="#" onclick="loadScreen('CIS0315U01WS');"><%=CIS0315U01ServiceProxy.serviceId%></a></li>
			<li><a href="#" onclick="loadScreen('CIS1036A01WS');"><%=CIS1036A01ServiceProxy.serviceId%></a></li>
			<li><a href="#" onclick="loadScreen('CIS1034A01WS');"><%=CIS1034A01ServiceProxy.serviceId%></a></li>
			<li><a href="#" onclick="loadScreen('CIS1037A01WS');"><%=CIS1037A01ServiceProxy.serviceId%></a></li>
			<!-- New CR 4 -->
			<li><a href="#" onclick="loadScreen('CIS1044U01WS');"><%=CIS1044U01ServiceProxy.serviceId%></a></li>
			<li><a href="#" onclick="loadScreen('CIS1047O01WS');"><%=CIS1047O01ServiceProxy.serviceId%></a></li>
			<li><a href="#" onclick="loadScreen('CIS1048O01WS');"><%=CIS1048O01ServiceProxy.serviceId%></a></li>
			<li><a href="#" onclick="loadScreen('CVRSDQ0001WS');"><%=CVRSDQ0001ServiceProxy.serviceId%></a></li>
			<li><a href="#" onclick="loadScreen('FindManager');">Find Manager</a></li>
			
			<li><a href="#" onclick="loadScreen('Fico');">Fico</a></li>
			<!-- 			<li><a href="#" onclick="loadScreen('CreateFicoResponse');">Create Fico Response XML</a></li> -->
			<li><a href="#" onclick="loadScreen('WFInquiryApp');">WFInquiryApp</a></li>
			<li><a href="#" onclick="loadScreen('CreateKVIApplication');">CreateKVIApplication</a></li>
			<li><a href="#" onclick="loadScreen('EditKVIApplication');">EditKVIApplication</a></li>
			<li><a href="#" onclick="loadScreen('ImportExportData');">Import/Export</a></li>
			<li><a href="#" onclick="loadScreen('BatchReport');">BatchReport</a></li>
			<li><a href="#" onclick="loadScreen('UpdateApprovalStatus');">UpdateApprovalStatus</a></li>
			<li><a href="#" onclick="loadScreen('FollowUp');">FollowUp</a></li>
			<li><a href="#" onclick="loadScreen('UpdateCallOperator');">UpdateCallOperator</a></li>
			<li><a href="#" onclick="loadScreen('FollowUpResult');">FollowUpResult</a></li>
			<li><a href="#" onclick="loadScreen('CallIM');">Call IM</a></li>
			<li><a href="#" onclick="loadScreen('CreateCISCustomer');">Create
					CIS Customer</a></li>
			<li><a href="#" onclick="loadScreen('Iibservice');">IIB
					Service</a></li>
			<li><a href="#" onclick="loadScreen('SubmitApplication');">Submit Appliation</a></li>
			<li><a href="#" onclick="loadScreen('CheckProductDup');">Check Dup</a></li>
			<li><a href="#" onclick="loadScreen('InfBatchLog');">InfBatch Log</a></li>
		</ul>
	</div>
	<%
		ScreenFlow screenFlow = (ScreenFlow) request.getSession()
				.getAttribute("ScreenFlow");
		if (null == screenFlow) {
			screenFlow = new ScreenFlow();
		}
		String screenId = screenFlow.getScreenId();
		String pageUrl = ServiceController.getScreenController(screenId);
		System.out.println("pageUrl >> " + pageUrl);
	%>
	<div class="container" id="showTab"
		style="margin-left: 180px; padding-top: 20px;">
		<%
			if (!Util.empty(pageUrl)) {
				pageContext.include(pageUrl);
			}
		%>
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
