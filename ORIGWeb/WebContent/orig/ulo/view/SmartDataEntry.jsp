<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.orig.ulo.control.util.SmartDataUtil"%>
<%@page import="com.eaf.orig.ulo.model.control.FlowControlDataM"%>
<%@page contentType="text/html;charset=UTF-8"%>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler" />
<%
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	String uniqueId = applicationGroup.getApplicationGroupNo();
	String CACHE_TEMPLATE = SystemConstant.getConstant("CACHE_TEMPLATE");
	String smartId = CacheControl.getName(CACHE_TEMPLATE,applicationGroup.getApplicationTemplate(),"SM_TEMPLATE_CODE");
	FlowControlDataM flowControl = (FlowControlDataM) request.getSession().getAttribute("FlowControl");		
	pageContext.setAttribute("version", "?v=0008"); 
	pageContext.setAttribute("templateId", smartId);
	pageContext.setAttribute("images", SmartDataUtil.getImageData(applicationGroup, flowControl.getRole()));
 %>
<!DOCTYPE html>
<html>
<head>
	<link href="<c:url value="/css/smartdata/smart-data.css"/>${version}" rel="stylesheet" type="text/css" />
	<script src="<c:url value="/js/smartdata/jquery.mousewheel.min.js"/>${version}" ></script>
	<script src="<c:url value="/js/smartdata/smart-data.min.js"/>${version}" ></script>
	<script src="<c:url value="/js/smartdata/main.js"/>${version}" ></script>
</head>
<body>
	<input type="hidden" name="template_id" id="template_id" value="${templateId}"/>
	<section id="smart_data"></section>
	<script>
		var SmartDataEntry = {
			isShow : true,
			show : function(e) {
				$('.SmartDataEntry').removeClass('collapse');
				if (e != false) {
					SmartDataEntry.isShow = true;
				}
			},
			hide : function(e) {
				$('.SmartDataEntry').addClass('collapse');
				if (e != false) {
					SmartDataEntry.isShow = false;
				}
			},
			toggle : function() {
				$('.SmartDataEntry').toggleClass('collapse');
				if ($('.SmartDataEntry').hasClass('collapse')) {
					SmartDataEntry.isShow = false;
				} else {
					SmartDataEntry.isShow = true;
				}
			}
		};
		$(function(){
			var images = ${images};
			var templateId = "${templateId}";
			SmartInitializer.init(templateId, images, "#smart_data");//Located in main.js
		});
	</script>
</body>
</html>