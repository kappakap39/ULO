<%@page import="com.eaf.orig.ulo.pl.app.utility.ORIGLogic"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.Vector"%>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"%>
<%@ page import="com.eaf.orig.shared.util.OrigUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.DataFormatUtility"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLAuditTrailDataM"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"/>

<% 
	org.apache.log4j.Logger log=org.apache.log4j.Logger.getLogger("orig/popup/audit_trail_popup.jsp");
    
    String displayMode = HTMLRenderUtil.DISPLAY_MODE_EDIT;
    
	Vector<PLAuditTrailDataM> auditVect = (Vector<PLAuditTrailDataM>) request.getSession().getAttribute("searchAudit");
	if(auditVect==null){
		auditVect = new Vector<PLAuditTrailDataM>();
	}
	
	ORIGCacheUtil cacheU = new ORIGCacheUtil();
	Vector vUser = (Vector)cacheU.loadUserNameCache(OrigConstant.Status.STATUS_ACTIVE);
		
%>
<div class="nav-inbox">
	<div class="PanelFirst">
		<div class="PanelSecond TextHeaderNormal">
			<div class="PanelThird">
				<table class="TableFrame">
					<tr class="Header">
                    	<td><%=MessageResourceUtil.getTextDescription(request, "FIELD_NAME")%></td>
                        <td><%=MessageResourceUtil.getTextDescription(request, "OLD_VALUE")%></td>
                        <td><%=MessageResourceUtil.getTextDescription(request, "NEW_VALUE")%></td>
                        <td><%=MessageResourceUtil.getTextDescription(request, "UPDATE_DATE_TIME")%></td>
                        <td><%=MessageResourceUtil.getTextDescription(request, "UPDATE_BY")%></td>
                        <td><%=MessageResourceUtil.getTextDescription(request, "ROLE")%></td>
                    </tr>
                    <%if(!OrigUtil.isEmptyVector(auditVect)){
						for(int i=0 ; i<auditVect.size() ; i++){
							PLAuditTrailDataM auditM = auditVect.get(i);
							String styleTr = ((i)%2==0)?"ResultEven":"ResultOdd";
							String ROLE_VARIABLE = ORIGLogic.getROLE_VARIABLE(auditM.getRole());
					%>
						<tr class="Result-Obj <%=styleTr%>">
			                <td><%=HTMLRenderUtil.displayHTML(auditM.getFieldName()) %></td>
			                <td><%=HTMLRenderUtil.displayHTML(auditM.getOldValue()) %></td>
			                <td><%=HTMLRenderUtil.displayHTML(auditM.getNewValue()) %></td>
			                <td><%=DataFormatUtility.stringDateTimeValueListForThai(String.valueOf(auditM.getCreateDate())) %></td>
			                <td><%=HTMLRenderUtil.displayHTML(HTMLRenderUtil.displayThFullName(auditM.getCreateBy(), vUser)) %></td>
			                <td><%=HTMLRenderUtil.displayHTML(MessageResourceUtil.getTextDescription(request,ROLE_VARIABLE))%></td>
			            </tr>									  		
						<%}%>                                    
                    <%}else{%>
                   		 <tr class="ResultNotFound"><td colspan="6" >No record found</td></tr>
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