<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import="org.apache.log4j.Logger"%>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="VALUE_LIST" scope="session" class="com.eaf.orig.shared.model.ValueListM" />

<script type="text/javascript" src="orig/js/popup/pl/master-dialog-popup.js"></script>

<div class="PanelFirst">
	<div class="PanelSecond TextHeaderNormal">
		<table width="100%">
			<tr> 
				<td colspan="4">
					<div class="PanelThird">
						<table class="TableFrame">
								<tr class="Header">
									<td align="center" width="20%"><%=MessageResourceUtil.getTextDescription(request, "CODE") %></td>
									<td align="center" width="80%"><%=MessageResourceUtil.getTextDescription(request, "DESCRIPTION") %></td>
								</tr> 
						<%
							Vector valueList = VALUE_LIST.getResult(); 
							if(valueList != null && valueList.size() > 1){
								for(int i=1;i<valueList.size();i++){
									Vector elementList = (Vector)valueList.get(i);
									String styleTr = ((i-1)%2==0)?"ResultEven":"ResultOdd";
							%>		
							    <%if("LoadPLZipCode".equals(VALUE_LIST.getSearchAction())){%>
								<tr  class="Result-Obj <%=styleTr%>" 
											onclick="javascript:setDataZipCode(
															'<%=elementList.elementAt(1)%>',
															'<%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(3))%>',
															'<%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(4))%>',
															'<%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(5))%>',
															'<%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(6))%>',
															'<%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(7))%>',
															'<%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(8))%>')">
								<%}else if("LoadSubDistrict".equals(VALUE_LIST.getSearchAction())){%>
										<tr  class="Result-Obj <%=styleTr%>" 
											onclick="javascript:setDataTambol('<%=elementList.elementAt(1)%>',
															'<%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(2))%>',
															'<%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(3))%>')">
								<%}else{ %>
									<tr class="Result-Obj <%=styleTr%>" 
											onclick="javascript:setData('<%=elementList.elementAt(1)%>',
															'<%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(2))%>')">
								<%} %>
									<td width="20%"><%=elementList.elementAt(1)%></td>
									<td width="80%" class="TextTDLeft"><%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(2))%></td>		
								</tr>
							<%}%>
						<%}else{%>
								<tr class="ResultNotFound">
							  		<td align="center" colspan="2" >No record found</td>
							  	</tr>						
						<%}%>
						</table>
					</div>			
				</td>
			</tr>
			<tr>
				<td>
					<table width="100%">
						<tr>
							<td align="left" height="50">
									<jsp:include page="dialog_valuelist.jsp" flush="true" />
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
</div>
<%=HTMLRenderUtil.displayHiddenField("","province") %>
<%=HTMLRenderUtil.displayHiddenField("","amphur") %>
<%	//set current screen to main Form
	com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager =
	(com.eaf.j2ee.pattern.control.ScreenFlowManager) request.getSession(true).getAttribute("screenFlowManager");
	screenFlowManager.setCurrentScreen("PL_MAIN_APPFORM");
%>