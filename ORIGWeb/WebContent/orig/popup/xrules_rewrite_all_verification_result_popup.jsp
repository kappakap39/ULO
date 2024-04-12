<%@ page import="com.eaf.xrules.shared.constant.XRulesConstant"%>
<%@ page import="com.eaf.orig.shared.utility.OrigXRulesUtil"%>
<%@ page import="java.util.*" %>
<%@ page import="com.eaf.xrules.shared.model.RequiredModuleServiceM"%>  
<%@ page import="com.eaf.xrules.shared.constant.XRulesConstant"%>
<%	 
	HashMap hExecResult = (HashMap)request.getSession().getAttribute("hExecResult");
    com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager =(com.eaf.j2ee.pattern.control.ScreenFlowManager) request.getSession(true).getAttribute("screenFlowManager");
    System.out.println("ProductSubForm:Screen current ===>"+screenFlowManager.getCurrentScreen());		
	screenFlowManager.setCurrentScreen("MAIN_APPFORM");
     Iterator itAppKey=hExecResult.keySet().iterator();

%>
<script language="JavaScript">
   <% while(	itAppKey.hasNext()){
     String serviceId=(String)itAppKey.next();   
     String serviceResult=(String)hExecResult.get(serviceId);   
     String resBoxName=(String)XRulesConstant.hResultBoxName.get(serviceId);
	 %>
	   if( window.opener.appFormName.<%=resBoxName%>!=undefined){
 	       window.opener.appFormName.<%=resBoxName%>.value = '<%=serviceResult%>';	 
	     }
	<%}	  
	%>
   window.close();
</script>