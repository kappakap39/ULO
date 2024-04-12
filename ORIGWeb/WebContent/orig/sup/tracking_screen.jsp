<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.Vector"%>
<%@page import="com.eaf.orig.ulo.pl.app.utility.ORIGLogic"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.TRACKING"%>
<%@ page import="com.eaf.orig.shared.util.OrigUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.WebActionUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.formcontrol.view.form.SearchHandler"%>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.TrackingTableRenderUtility"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.DataFormatUtility"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="VALUE_LIST" scope="session" class="com.eaf.orig.shared.model.ValueListM"/>
<script type="text/javascript" src="orig/js/sup/tracking.screen.js"></script>

<%
	org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger("orig/sup/tracking_screen.jsp");
    
    String searchType = (String) request.getSession().getAttribute("searchType");
    
    SearchHandler HandlerM = (SearchHandler) request.getSession().getAttribute("SEARCH_DATAM");
	if(HandlerM == null){
		HandlerM = new SearchHandler();
	}
	SearchHandler.SearchDataM searchDataM = HandlerM.getSearchM();
	if(searchDataM == null){
		searchDataM = new SearchHandler.SearchDataM();
	}
	
// 	if(OrigUtil.isEmptyString(searchDataM.getProductCode())){
// 		searchDataM.setProductCode(OrigConstant.PRODUCT_KEC);
// 	}
    
    String current_role = ORIGUser.getCurrentRole();
    
    String displayMode = HTMLRenderUtil.DISPLAY_MODE_EDIT;
	ORIGCacheUtil origCache = ORIGCacheUtil.getInstance();
	Vector vRoleGroup = (Vector)origCache.getNaosCacheDataMs("ALL_ALL_ALL",130);
%>
<div class="nav-inbox" id="div-tracking-search">
	<div class="PanelFirst">
		<div class="PanelSecond TextHeaderNormal">
			<div class="PanelThird">
				<fieldset class="field-set">
				<legend>Search Criteria</legend>
					<table class="FormFrame">
						<tr>
                            <td class="textR"><%=MessageResourceUtil.getTextDescription(request, "EMPLOYEE_FIRST_NAME")%> :</td>
                            <td class="textL"><%=HTMLRenderUtil.displayInputTagScriptAction(searchDataM.getEmpFirstName(),displayMode,"","empFirstName","textbox","onblur=\"countChar2(this)\"","120") %></td>
                        	<td class="textR"><%=MessageResourceUtil.getTextDescription(request, "EMPLOYEE_LAST_NAME")%> :</td>
                            <td><%=HTMLRenderUtil.displayInputTagScriptAction(searchDataM.getEmpLastName(),displayMode,"","empLastName","textbox","onblur=\"countChar2(this)\"","50") %></td>
                            <td class="textR"><%=MessageResourceUtil.getTextDescription(request, "EMPLOYEE_ID")%> :</td>
                            <td><%=HTMLRenderUtil.displayInputTagScriptAction(searchDataM.getEmpId(),displayMode,"","empId","textbox","","20") %></td>
                        </tr>
                        <tr height="25">
	                        <td class="textR"><%=MessageResourceUtil.getTextDescription(request, "LOGON_STATUS")%> :</td>
	                        <td>
                            	<select class="inputformdetailAuto" id="statusLogOn" name="statusLogOn">
                                	<%if(OrigUtil.isEmptyString(searchDataM.getLogOn())){%>                                	
                                    	<option value="" selected="selected">กรุณาเลือก</option>
                                    	<option value="<%=OrigConstant.FLAG_Y%>">Log On</option>
                                    	<option value="<%=OrigConstant.FLAG_N%>">Log Off</option>                                    
                                    <%} else if("Y".equals(searchDataM.getLogOn())){%>                                    
                                    	<option value="">กรุณาเลือก</option>
                                    	<option value="<%=OrigConstant.FLAG_Y%>" selected="selected">Log On</option>
                                    	<option value="<%=OrigConstant.FLAG_N%>">Log Off</option>                                    
                                    <%} else if("N".equals(searchDataM.getLogOn())){%>                                    
                                    	<option value="">กรุณาเลือก</option>
                                    	<option value="<%=OrigConstant.FLAG_Y%>">Log On</option>
                                    	<option value="<%=OrigConstant.FLAG_N%>" selected="selected">Log Off</option>
                                    <%}%>
								</select>
							</td>
                            <td class="textR"><%=MessageResourceUtil.getTextDescription(request, "ONJOB_STATUS")%> :</td>
                            <td>
                             	<select class="inputformdetailAuto" id="statusOnJob" name="statusOnJob">
									<%if(OrigUtil.isEmptyString(searchDataM.getOnJob())){%>                                	
                                    	<option value="" selected="selected">กรุณาเลือก</option>
                                    	<option value="<%=OrigConstant.FLAG_Y%>"><%=OrigConstant.StatusOnJob.ON%></option>
                                    	<option value="<%=OrigConstant.FLAG_N%>"><%=OrigConstant.StatusOnJob.OFF%></option>                                    
                                    <%}else if("Y".equals(searchDataM.getOnJob())){%>                                    
                                   	 	<option value="">กรุณาเลือก</option>
                                    	<option value="<%=OrigConstant.FLAG_Y%>" selected="selected"><%=OrigConstant.StatusOnJob.ON%></option>
                                    	<option value="<%=OrigConstant.FLAG_N%>"><%=OrigConstant.StatusOnJob.OFF%></option>                                    
                                    <%}else if("N".equals(searchDataM.getOnJob())){%>                                    
	                                    <option value="">กรุณาเลือก</option>
	                                    <option value="<%=OrigConstant.FLAG_Y%>"><%=OrigConstant.StatusOnJob.ON%></option>
	                                    <option value="<%=OrigConstant.FLAG_N%>" selected="selected"><%=OrigConstant.StatusOnJob.OFF%></option>
                                    <%}%>
								</select>
							</td>							
							<td colspan="2" class="textL">		
								<%if(ORIGLogic.superTrackingSearch(current_role)){ %>						
									<table cellspacing=0 cellpadding=0 border="0" id="table_tracking_role">
										<tr>
											<td class="textR">&nbsp;<%=MessageResourceUtil.getTextDescription(request,"GROUP_ROLE")%> :</td>
											<td class="textL"><%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(vRoleGroup, searchDataM.getGroupRole(),"tracking_group",displayMode,"")%></td>
											<td class="textR" width="50px"><%=MessageResourceUtil.getTextDescription(request,"ROLE")%> :</td>
											<td class="textL" id="tr_tracking_role" width="120px"><%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(null, searchDataM.getRole(),"tracking_role",displayMode,"")%></td>
										</tr>
									</table>
								<%}%>
							</td>
						</tr>
                        <tr height="25">
                            <td colspan="6" valign="top" align="center"><input type="button" id="button-search" class="button" value="Search" onClick="javascript:searchTracking()" /></td>
                        </tr>
                    </table>
				</fieldset>
			</div>
			<div class="PanelThird">
				<table class="TableFrame">
					<tr>
						<td class="textL" width="300" colspan="13"><%=MessageResourceUtil.getTextDescription(request, "AMOUNT_OF_JOB_IN_AUTO_QUEUE") %> : <span id="autoQueue">0</span></td>
	                </tr>
	                <tr class="Header">	
	                	<td rowspan='2'><%=MessageResourceUtil.getTextDescription(request, "NAME")%><br><span id="countUser"></span></td>
                        <td rowspan='2'><%=MessageResourceUtil.getTextDescription(request, "ROLE")%></td>
                        <td rowspan='2'><%=MessageResourceUtil.getTextDescription(request, "LOGON_STATUS")%><br><span id="countLogOn"></span></td>
                        <td rowspan='2'><%=MessageResourceUtil.getTextDescription(request, "LAST_LOGON")%></td>
                        <td rowspan='2'><%=MessageResourceUtil.getTextDescription(request, "LAST_LOGOFF")%></td>
                        <td rowspan='2'><%=MessageResourceUtil.getTextDescription(request, "ONJOB_STATUS")%><br><span id="countOnJob"></span></td>		
                        <td colspan='3'><%=MessageResourceUtil.getTextDescription(request, "TRACKING_JOB_INPUT")%></td>	
                        <td colspan='3'><%=MessageResourceUtil.getTextDescription(request, "TRACKING_JOB_OUTPUT")%></td>  
                        <td rowspan='2'><%=MessageResourceUtil.getTextDescription(request, "REASSING_JOB")%><br><span id="countReassignJob"></span></td>
                        <td rowspan='2'><%=MessageResourceUtil.getTextDescription(request, "BLOCK_JOB")%><br><span id="countBlockJob"></span></td>                      
                        <td rowspan='2'><%=MessageResourceUtil.getTextDescription(request, "APPLICATION_STATUS")%></td>
                        <td rowspan='2'><%=MessageResourceUtil.getTextDescription(request, "REMAINING_JOB")%><br><span id="countRemainJob"></span></td>																	
					</tr>
                    <tr class="Header">
                        <td><%=MessageResourceUtil.getTextDescription(request, "PREVIOUS_JOB")%><br><span id="countPreviousJob"></span></td>
                        <td><%=MessageResourceUtil.getTextDescription(request, "PREVIOUS_JOB_EDIT")%><br><span id="countPreviousJobEdit"></span></td>
                        <td><%=MessageResourceUtil.getTextDescription(request, "NEW_JOB")%><br><span id="countNewJob"></span></td>
                        <td><%=MessageResourceUtil.getTextDescription(request, "SUBMIT_JOB")%><br><span id="countSubmitJob"></span></td>
                        <td><%=MessageResourceUtil.getTextDescription(request, "SUBMIT_JOB_EDIT")%><br><span id="countSubmitEditJob"></span></td>
                        <td><%=MessageResourceUtil.getTextDescription(request, "CANCLE_JOB")%><br><span id="countCancleJob"></span></td>                       
					</tr>
                    <%
                    Vector valueList = VALUE_LIST.getResult();
                    if(valueList!=null&&valueList.size()>1){
                    	TrackingTableRenderUtility util = new TrackingTableRenderUtility();
                        for(int i=1 ; i<valueList.size(); i++){
							Vector elementList = (Vector)valueList.get(i);
                            String styleTr = ((i-1)%2==0)?"even-result-01":"odd-result-01";
                    %>
                    <tr class="Result-Obj <%=styleTr%>" id="<%=(String) elementList.elementAt(1)%>">
		            	<td>
		            		<%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(1))%>
		            		<%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(2))%>
		            		<br>
		            	</td>
		                <td><%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(3))%></td>
		                <td><%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(5))%></td>
		                <td><%=DataFormatUtility.stringDateTimeValueListForThai((String) elementList.elementAt(6))%></td>
		                <td><%=DataFormatUtility.stringDateTimeValueListForThai((String) elementList.elementAt(7))%></td>
		                <td><%=TRACKING.displayTableLogonFlag((String) elementList.elementAt(8)) %></td>
		                
		                <td><%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(9))%></td>
		                <td><%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(10))%></td>
		                <td><%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(11))%></td>
		                <td><%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(12))%></td>
		                <td><%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(13))%></td>
		                <td><%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(14))%></td>
		                <td><%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(15))%></td>
		                
		                <td><%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(16))%></td>
		                
		                <td width="220"><%=TRACKING.displayTableStatus(request,(String) elementList.elementAt(17))%></td>
		                <td><%=TRACKING.displayTableCountApp((String) elementList.elementAt(17))%></td>
					</tr>									  		
					<%}}else{%>
                   		<tr class="ResultNotFound">
			            	<td colspan="16" nowrap="nowrap">No record found</td>
			            </tr>		
                    <%}%>
                </table>
				<div class="PanelValueList">			
					<jsp:include page="../appform/pl/valueList.jsp" flush="true" />
				</div>
			</div>
		</div>
	</div>
</div>
<%=HTMLRenderUtil.displayHiddenField(searchType, "checkSearch") %>
