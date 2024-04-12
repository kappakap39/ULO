<%@page import="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="com.eaf.j2ee.pattern.util.MessageResourceUtil"%>
<%@ page import="com.eaf.orig.shared.util.OrigUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.WorkflowTool"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HistoryPopupUtility"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.search.PLSearchHistoryActionLogDataM"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.DataFormatUtility"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import="java.util.Vector"%>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"/>

<%  
	org.apache.log4j.Logger log=org.apache.log4j.Logger.getLogger("orig/popup/audit_trail_popup.jsp");  
	
    String displayMode = HTMLRenderUtil.DISPLAY_MODE_EDIT;
    
	Vector<PLSearchHistoryActionLogDataM> historyVect = (Vector<PLSearchHistoryActionLogDataM>) request.getSession().getAttribute("searchHistoryLog");
	if(historyVect==null){
		historyVect = new Vector<PLSearchHistoryActionLogDataM>();
	}
	
	ORIGCacheUtil cacheU = new ORIGCacheUtil();
	Vector vUser = (Vector)cacheU.loadUserNameCache(OrigConstant.Status.STATUS_ACTIVE);
	WorkflowTool wfTool = new WorkflowTool();
		
%>
<div class="nav-inbox">
	<div class="PanelFirst">
		<div class="PanelSecond TextHeaderNormal">
			<div class="PanelThird">
				<table class="TableFrame">
					<tr class="Header">
                        <td><%=MessageResourceUtil.getTextDescription(request, "CUS_NUM")%></td>
                        <td><%=MessageResourceUtil.getTextDescription(request, "APPLICATION_STATUS")%></td>
                        <td><%=MessageResourceUtil.getTextDescription(request, "ACTION")%></td>
                        <td><%=MessageResourceUtil.getTextDescription(request, "REASON")%></td>
                        <td><%=MessageResourceUtil.getTextDescription(request, "UPDATE_BY")%></td>
                        <td><%=MessageResourceUtil.getTextDescription(request, "UPDATE_DATE")%></td>
                    </tr>
                    <%                    
					HistoryPopupUtility hisUtility = new HistoryPopupUtility();
                    if(!OrigUtil.isEmptyVector(historyVect)){
						for(int i=0 ; i<historyVect.size() ; i++){
							PLSearchHistoryActionLogDataM historyM = historyVect.get(i);
							String styleTr = (i%2==0)?"ResultEven":"ResultOdd";
					%>
					<tr class="Result-Obj <%=styleTr%>" id="">
						<td><%=i+1%></td>
						<td><%=HTMLRenderUtil.displayHTML(wfTool.GetMessageAppStatus(request, historyM.getStatus())) %></td>
						<td><%=hisUtility.historyAction(request,historyM.getJobState(),historyM.getActionDesc())%></td>
		                <td><%=hisUtility.Reason(historyM.getReason(),historyM.getAction()) %></td>
		                <td><%=HTMLRenderUtil.displayHTML(HTMLRenderUtil.displayThFullName(historyM.getCreateBy(), vUser)) %></td>
		                <td><%=HTMLRenderUtil.stringDateTimeValueListForThai(String.valueOf(historyM.getCreateDate())) %></td>
		            </tr>									  		
						<%}%>                                     
                   <%}else{%>
					<tr class="ResultNotFound">
		            	<td colspan="6" >No record found</td>
		            </tr>
                   <%}%>
				</table>
			</div>
		</div>
	</div>
</div>
<script language="JavaScript">
$(document).ready(function (){
	$("Tr.ResultOdd").hover(
    	 function(){$(this).addClass("ResultOdd-haver-non-pointer");},
    	 function(){$(this).removeClass("ResultOdd-haver-non-pointer");}
    );
	$("Tr.ResultEven").hover(
    	 function(){$(this).addClass("ResultEven-haver-non-pointer");},
    	 function(){$(this).removeClass("ResultEven-haver-non-pointer");}
	);
});
</script>

<%	//set current screen to main Form
	com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager =
	(com.eaf.j2ee.pattern.control.ScreenFlowManager) request.getSession(true).getAttribute("screenFlowManager");
	screenFlowManager.setCurrentScreen(PLOrigFormHandler.PL_MAIN_APPFORM_SCREEN);
%>

