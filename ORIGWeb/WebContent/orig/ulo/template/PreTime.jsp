<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page contentType="text/html;charset=UTF-8"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM" />
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<script src="orig/ulo/template/js/PreTime.js"></script>
<%
	Logger logger = Logger.getLogger(this.getClass());
	String roleId = ORIGForm.getRoleId();
	String CACHE_PRE_TIME = SystemConstant.getConstant("CACHE_PRE_TIME");
%>
 <div class="pull-right timeremain">
	<div class="text-danger">
		<span class="timeremain-label"><%=LabelUtil.getText(request,"TIME_REMAINING")%>: </span>
		<span class="timeremain-tick" timeleft="--:--">88:88</span>
	</div>
</div>

<script>
<%
	int time = Integer.parseInt(CacheControl.getName(CACHE_PRE_TIME, roleId));
	logger.debug("time : "+time);
	out.print("var time = "+time+";");
	out.print("preTime(time);");
%>
</script>