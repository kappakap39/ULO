<%@page import="com.eaf.ulo.cache.constant.CacheConstant"%>
<%@page import="com.eaf.ulo.cache.controller.CacheController"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemCache"%>
<%@page import="com.eaf.core.ulo.common.date.ApplicationDate"%>
<%@page import="com.eaf.orig.ulo.model.control.FlowControlDataM"%>
<%@page import="com.eaf.core.ulo.common.util.SessionControl"%>
<%@page import="com.eaf.orig.profile.model.UserDetailM"%>
<%@page import="com.eaf.core.ulo.common.message.MessageErrorUtil"%>
<%@page import="com.eaf.core.ulo.common.message.MessageUtil"%>
<%@page import="java.util.Locale"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<script type="text/javascript">
<%
	DateFormat FORMAT_DATE = new SimpleDateFormat("dd/MM/yyyy",java.util.Locale.US);
	String CURRENT_DATE = FORMAT_DATE.format(ApplicationDate.getDate());
	String ObjectDate[] = CURRENT_DATE.split("/");
	String DAY = ObjectDate[0];
	String MONTH = ObjectDate[1];
	String TH_YEAR = String.valueOf(Integer.parseInt(ObjectDate[2])+543);
	String EN_YEAR = ObjectDate[2];	
	out.println("var DAY = "+Integer.parseInt(DAY)+";");
	out.println("var MONTH = "+Integer.parseInt(MONTH)+";");
	out.println("var TH_YEAR = "+Integer.parseInt(TH_YEAR)+";");
	out.println("var EN_YEAR = "+Integer.parseInt(EN_YEAR)+";");
	out.println("var EN_CURRENT_DATE = '"+DAY+"/"+MONTH+"/"+EN_YEAR+"';");
	out.println("var TH_CURRENT_DATE = '"+DAY+"/"+MONTH+"/"+TH_YEAR+"';");
	out.println("var EN_CURRENT_DATE_JS = new Date("+Integer.parseInt(EN_YEAR)+","+(Integer.parseInt(MONTH)-1)+","+Integer.parseInt(DAY)+");");
	out.println("var CONTEXT_PATH = '"+request.getContextPath()+"';");
	out.println("var SCHEME = '"+request.getScheme()+"';");
	out.println("var SERVER_PORT = '"+request.getServerPort()+"';");
	
	UserDetailM userM = (UserDetailM)session.getAttribute(SessionControl.ORIGUser);
	FlowControlDataM flowControl = (FlowControlDataM)session.getAttribute(SessionControl.FlowControl);	
	
	out.println("var USER_ID = '"+userM.getUserName()+"';");
	out.println("var ROLE_ID = '"+flowControl.getRole()+"';");
	out.println("var MENU_ID = '"+flowControl.getMenuId()+"';");
		
	try{
		out.println(CacheController.getCacheData(CacheConstant.CacheName.JAVASCRIPT_VARIABLE,"JAVASCRIPT_SYSTEM_CONSTANT"));		
		out.println(CacheController.getCacheData(CacheConstant.CacheName.JAVASCRIPT_VARIABLE,"JAVASCRIPT_MESSAGE"));
		out.println(CacheController.getCacheData(CacheConstant.CacheName.JAVASCRIPT_VARIABLE,"JAVASCRIPT_ERROR"));
	}catch(Exception e){
	}
%>
</script>