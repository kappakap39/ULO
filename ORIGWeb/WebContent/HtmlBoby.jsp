<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.core.ulo.common.util.NotifyForm"%>
<%@page import="com.eaf.orig.logon.LogonEngine"%>
<jsp:useBean id="screenFlowManager" class="com.eaf.j2ee.pattern.control.ScreenFlowManager" scope="session"/>
<jsp:useBean id="FlowControl" class="com.eaf.orig.ulo.model.control.FlowControlDataM" scope="session"/>  
<%
	String randerPage = "Avale_template.jsp";
	if(!LogonEngine.auth(request)){
		randerPage = "timeout.jsp";
	}else if(MConstant.FLAG_Y.equals(FlowControl.getOutsideUrl())){
		String contextPath =  FlowControl.getContext();
		out.print(HtmlUtil.hidden("OUTSIDE_CONTEXT", contextPath));
		randerPage = "/"+contextPath+"/index.jsp?v=007";
	}
	String currentScreen = screenFlowManager.getCurrentScreen();	
%>
<nav><jsp:include page="NavigationBar.jsp" /></nav>
<%if(MConstant.FLAG_Y.equals(FlowControl.getOutsideUrl())){%>
<div class="row page-frame">
	<iframe src="<%=randerPage%>" id="external-page" class="external-page" onload='javascript:resizeIframe(this);'></iframe>
<!-- 	<iframe src="/SmartData/admin/index.bak.jsp" id="external-page" class="external-page" onload='javascript:resizeIframe(this);'></iframe> -->
</div>
<%}else{%>
<div class="row page-frame">
	<div class="col-xs-12">
	<div class="notifyHandlerManager">
	<%
		if(!"MASTER_FORM_TEMPLATE".equals(currentScreen)){
			NotifyForm.write(request,out);
		}
	%>
	</div>
	<jsp:include page="<%=randerPage%>" />
	</div>	
</div>
<%}%>