<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.Vector"%>
<%@ page import="org.apache.log4j.Logger"%>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import="com.eaf.orig.shared.valuelist.view.webaction.ValueListWebAction" %>

<jsp:useBean id="DIALOG_LIST" scope="session" class="com.eaf.orig.shared.model.ValueListM"/>

<script type="text/javascript" src="orig/js/popup/pl/dialog-list-popup.js"></script>
<% 
	org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger("orig/popup/pl/user_popup.jsp");	
%>

<div class="PanelFirst">
	<div class="PanelSecond TextHeaderNormal">
			<div class="PanelThird">
				<table class="TableFrame">
						<tr class="Header">
							<td align="center" width="100%"><%=MessageResourceUtil.getTextDescription(request, "FULL_NAME") %></td>
						</tr>
						<%
							Vector valueList = DIALOG_LIST.getResult(); 
							if(valueList != null && valueList.size() > 1){
								for(int i=1; i<valueList.size(); i++ ){						
									Vector elementList = (Vector)valueList.get(i);
									String styleTr = ((i-1)%2==0)?"ResultEven":"ResultOdd";
						%>															
							<tr class="Result-Obj1 <%=styleTr%>" 
										id="
										<%=  (String) elementList.elementAt(1)+" "
											+(String) elementList.elementAt(2)+"|"
											+(String) elementList.elementAt(3)%>
										">
								<td class="TextTDLeft">
									<%=HTMLRenderUtil.displayHTML((String)elementList.get(1)) %>
									&nbsp;&nbsp;
									<%=HTMLRenderUtil.displayHTML((String)elementList.get(2)) %>
								</td>
							</tr>
						<%}%>
					<%}else{%>
							<tr class="ResultNotFound">
								<td colspan="2">Results Not Found.</td>
							</tr>
					<%}%>
			</table>
			<table width="100%">
				<tr align="center">
					<td colspan="2" align="left" height="50">
						<jsp:include page="dialoglist-popup.jsp" flush="true" />
					</td>
				</tr>
			</table>
		</div>
	</div>
</div>
<%	//set current screen to main Form
	com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager =
	(com.eaf.j2ee.pattern.control.ScreenFlowManager) request.getSession(true).getAttribute("screenFlowManager");
	screenFlowManager.setCurrentScreen(DIALOG_LIST.getScreenFlow());
%> 