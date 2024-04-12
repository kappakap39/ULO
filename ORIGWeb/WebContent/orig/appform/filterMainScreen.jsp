<%@ page contentType="text/html;charset=UTF-8"%>

<%@ page import="java.util.Vector" %>
<%@ page import="com.eaf.orig.shared.model.PopulatePopupM" %>
<SCRIPT language="JavaScript">
<%
	Vector popMs = (Vector)request.getSession(true).getAttribute("POPULATE_POPUP");
	request.getSession(true).removeAttribute("POPULATE_POPUP");
	if(popMs == null) popMs = new Vector();
%>
	function populateTarget(){
<%
		PopulatePopupM popTmp;
		for(int i = 0; i < popMs.size(); i++){
			popTmp = (PopulatePopupM)popMs.get(i);
			System.out.println(">>>popTmp="+popTmp);
			if(PopulatePopupM.INNER_HTML.equals(popTmp.getType())){
%>
				if(opener.document.getElementById("<%= popTmp.getTarget() %>") != null){
					opener.document.getElementById("<%= popTmp.getTarget() %>").innerHTML = "<%= popTmp.getResult() %>";
				}
<%
			}else if(PopulatePopupM.VALUE.equals(popTmp.getType())){
%>
				opener.window.document.appFormName.<%= popTmp.getTarget() %>.value = "<%= popTmp.getResult() %>";
				
				//For rewrite car detail
				if("<%= popTmp.getTarget() %>" == "car_gear"){
					if("<%= popTmp.getResult() %>" == "A"){
						opener.window.document.appFormName.<%= popTmp.getTarget() %>[0].checked = true;
					}else{
						opener.window.document.appFormName.<%= popTmp.getTarget() %>[1].checked = true;
					}
				}
<%
			}
		}
%>
		window.close();
	}
	
</SCRIPT>
<BODY onLoad="javascript:populateTarget()">
<%	//set current screen to main Form
	com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager =
	(com.eaf.j2ee.pattern.control.ScreenFlowManager) request.getSession(true).getAttribute(
		"screenFlowManager");
	screenFlowManager.setCurrentScreen("MAIN_APPFORM");
%>