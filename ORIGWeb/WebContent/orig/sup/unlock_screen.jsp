<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.Vector"%>
<%@ page import="com.eaf.orig.ulo.pl.formcontrol.view.form.SearchHandler"%>
<%@page import="com.eaf.j2ee.pattern.util.MessageResourceUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.UnBlockRenderUtility"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="VALUE_LIST" scope="session" class="com.eaf.orig.shared.model.ValueListM" />
<script type="text/javascript" src="orig/js/sup/unlock.screen.js"></script>

<%  
	org.apache.log4j.Logger log=org.apache.log4j.Logger.getLogger("orig/sup/unlock_screen.jsp");    
    String userName = ORIGUser.getUserName();
%>
<span id="errorMessages"></span>
<div class="nav-inbox">
	<div class="PanelFirst">
		<div class="PanelSecond TextHeaderNormal">
			<%=SearchHandler.DisplayErrorMessage(request)%>
			<div class="PanelThird">
            	<%
            	Vector valueList = VALUE_LIST.getResult();
               	 if(null != valueList && valueList.size() > 1){
                %>
					<%=UnBlockRenderUtility.renderSubTable(request,valueList,ORIGUser.getCurrentRole(),userName)%>			
                <%}else{%>
					<table class="TableFrame">
						<tr class="Header">
							<td><%=MessageResourceUtil.getTextDescription(request, "LOCK_DATE")%></td>
							<td><%=MessageResourceUtil.getTextDescription(request, "ID_NO")%></td>
							<td><%=MessageResourceUtil.getTextDescription(request, "APPLICATION_NO")%></td>
							<td><%=MessageResourceUtil.getTextDescription(request, "APPLICATION_STATUS")%></td>
							<td><%=MessageResourceUtil.getTextDescription(request, "FULL_NAME")%></td>
							<td><%=MessageResourceUtil.getTextDescription(request, "PRODUCT")%></td>
							<td><%=MessageResourceUtil.getTextDescription(request, "PRIORITY")%></td>
							<td><%=MessageResourceUtil.getTextDescription(request, "SYSTEM_ACCEPT_DATE")%></td>
							<td><%=MessageResourceUtil.getTextDescription(request, "REMAINING_TIME")%></td>
							<td><%=MessageResourceUtil.getTextDescription(request, "OWNER")%></td>
							<td><%=MessageResourceUtil.getTextDescription(request, "UNLOCK")%></td>
						</tr>
	                    <tr class="ResultNotFound">
							<td colspan="11" >No record found</td>
						</tr>
		            </table>
	            <%}%>
	            <div class="PanelValueList">			
					<jsp:include page="../appform/pl/valueList.jsp" flush="true" />
				</div>
			</div>
		</div>
	</div>
</div>
<%=HTMLRenderUtil.displayHiddenField("", "UnblockAppRecId") %>
<%=HTMLRenderUtil.displayHiddenField("", "BlockAppRecId") %>
<%=HTMLRenderUtil.displayHiddenField("", "appRecordID")%>
