<%@page import="com.eaf.orig.shared.dao.utility.ObjectDAOFactory"%>
<%@page import="com.eaf.orig.shared.dao.utility.UtilityDAO"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.Vector"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.WorkflowTool"%>
<%@ page import="com.eaf.orig.ulo.pl.formcontrol.view.form.SearchHandler"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.PLMessageResourceUtil" %>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.orig.ulo.pl.model.app.search.PLSearchDataM" %>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.DataFormatUtility"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"/>
<jsp:useBean id="VALUE_LIST" scope="session" class="com.eaf.orig.shared.model.ValueListM" />

<% 
	PLSearchDataM searchReopenDataM = (PLSearchDataM) request.getSession().getAttribute("SEARCH_REOPEN_DATAM");
	if(searchReopenDataM == null){
		searchReopenDataM = new PLSearchDataM();
	}
	String displayMode = HTMLRenderUtil.DISPLAY_MODE_EDIT;
	
	ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
	
	Vector productVT = cacheUtil.loadCacheByActive("MainProduct");
	Vector priorityVT = cacheUtil.getNaosCacheDataMs("ALL_ALL_ALL",61);
	String defaultProduct = searchReopenDataM.getProductCode();
	if(defaultProduct == null || "".equals(defaultProduct)){
		defaultProduct = OrigConstant.PRODUCT_KEC;
	}
	Vector saleTypeVT = cacheUtil.loadCacheByActive("MainSaleType");
%>
<script type="text/javascript" src="orig/js/inbox/pl/reopen.inbox.js"></script>
<div class="nav-inbox">
	<div class="PanelFirst">
		<div class="PanelSecond TextHeaderNormal">
			<%=SearchHandler.DisplayErrorMessage(request)%>
			<div class="PanelThird">
				<fieldset class="field-set">
                	<legend>Search Criteria</legend>
                   	<table class="FormFrame">
		  				<tr>
	 						<td width="16.5%" class="textR text-nomal-detail"><%=PLMessageResourceUtil.getTextDescription(request, "APPLICATION_NO") %> :&nbsp;</td>
	 						<td width="16.7%" class="textL"><%=HTMLRenderUtil.displayInputTagScriptAction(searchReopenDataM.getApplicationNo(), displayMode, "", "application_no", "textbox", "", "20")%></td>
	 						<td width="16.7%" class="textR text-nomal-detail"><%=PLMessageResourceUtil.getTextDescription(request, "REFERENCE_NO") %> :&nbsp;</td>
	 						<td width="16.7%" class="textL"><%=HTMLRenderUtil.displayInputTagScriptAction(searchReopenDataM.getRefNo(), displayMode, "", "reference_no", "textbox200", "", "30")%></td>
	 						<td width="16.7%" class="textR text-nomal-detail"><%=PLMessageResourceUtil.getTextDescription(request, "PRODUCT") %><font color="red">*</font>:&nbsp;</td>
	 						<td width="16.7%" class="textL"><%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(productVT,defaultProduct,"product",displayMode," onchange=\"javascript:getSaleType()\" style='width:165px'")%></td>
	 					</tr>
	 					<tr>
						 	<td class="textR text-nomal-detail"><%=PLMessageResourceUtil.getTextDescription(request, "THAI_FNAME") %> :&nbsp;</td>
						 	<td class="textL"><%=HTMLRenderUtil.displayInputTagScriptAction(searchReopenDataM.getFirstName(), displayMode, "", "th_firstname", "textbox", "onblur=\"countChar2(this)\"", "120")%></td>
						 	<td class="textR text-nomal-detail"><%=PLMessageResourceUtil.getTextDescription(request, "THAI_LNAME") %> :&nbsp;</td>
						 	<td class="textL"><%=HTMLRenderUtil.displayInputTagScriptAction(searchReopenDataM.getLastName(), displayMode, "", "th_lastname", "textbox", "onblur=\"countChar2(this)\"", "50")%></td>								 	
						 	<td class="textR text-nomal-detail"><%=PLMessageResourceUtil.getTextDescription(request, "CITIZEN_ID") %> :&nbsp;</td>
						 	<td align="left" class="inputCol" ><%=HTMLRenderUtil.displayInputTagScriptAction(searchReopenDataM.getCitizenID(), displayMode, "", "citizen_id", "textbox", "", "20")%></td>			 				 	
						</tr>
						<tr>
						 	<td class="textR text-nomal-detail"><%=PLMessageResourceUtil.getTextDescription(request, "SALE_TYPE") %> :&nbsp;</td>
						 	<td class="textL" id="div_saleType"><%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(saleTypeVT,searchReopenDataM.getSaleType(),"saleType",displayMode," style='width:165px' ")%></td>			 				 				 	
						 	<td class="textR text-nomal-detail"><%=PLMessageResourceUtil.getTextDescription(request, "PRIORITY") %> :&nbsp;</td>
						 	<td class="textL"><%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(priorityVT,searchReopenDataM.getPriority(),"priority",displayMode," style='width:165px' ")%></td>
						 	<td></td>
						 	<td></td>
						</tr>		  				
		  				<tr height="25"></tr>
                        <tr height="25">
                            <td colspan="6" valign="top" align="center"><input type="button" id="button-search" name="SearchBtn" value="<%=PLMessageResourceUtil.getTextDescription(request, "SEARCH") %>" onClick="searchApp()" class="button"></td>
                        </tr>
                	</table>
            	</fieldset>	
			</div>
			<div class="PanelThird">
				<div class="scroll-div">
					<table class="TableFrame">
						<tr class="Header">
							<td><%=PLMessageResourceUtil.getTextDescription(request, "SALE_TYPE")%></td>
							<td><%=PLMessageResourceUtil.getTextDescription(request, "PRIORITY")%></td>
							<td><%=PLMessageResourceUtil.getTextDescription(request, "CHANNEL")%></td>
							<td><%=PLMessageResourceUtil.getTextDescription(request, "BRANCH_CODE")%></td>
							<td><%=PLMessageResourceUtil.getTextDescription(request, "APPLICATION_NO")%></td>
							<td><%=PLMessageResourceUtil.getTextDescription(request, "REFERENCE_ID")%></td>
							<td><%=PLMessageResourceUtil.getTextDescription(request, "REASON_FOR_REJECT")%></td>
							<td><%=PLMessageResourceUtil.getTextDescription(request, "NAME_SURNAME")%></td>
							<td><%=PLMessageResourceUtil.getTextDescription(request, "ID_NO")%></td>
							<td><%=PLMessageResourceUtil.getTextDescription(request, "PRODUCT")%></td>
							<td><%=PLMessageResourceUtil.getTextDescription(request, "DATE_RECEIVE_APP")%></td>
							<td><%=PLMessageResourceUtil.getTextDescription(request, "LAST_UPDATE_DATE")%></td>
						</tr>
				 	<%
					Vector valueList = VALUE_LIST.getResult(); 
					request.getSession().setAttribute("resultSearchVec",valueList);
					if(valueList != null && valueList.size() > 1){
					  UtilityDAO DAO =  ObjectDAOFactory.getUtilityDAO();
						for(int i=1;i<valueList.size();i++){
							String styleTr = ((i+1)%2==0)?"ResultEven":"ResultOdd";
							Vector elementList = (Vector)valueList.get(i);
					%>
			 			<tr class="Result-Obj <%=styleTr%>" id="<%=(String)elementList.elementAt(12)%>">
			 				<td><%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(1)) %></td>
							<td><%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(2)) %></td>
							<td><%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(3)) %></td>
							<td><%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(4)) %></td>
							<td><%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(5)) %></td>
							<td><%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(6)) %></td>
							<td><%=DAO.getReasonDesc((String)elementList.elementAt(12))%></td>
							<td><%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(7)) %></td>
							<td><%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(8)) %></td>
							<td><%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(9)) %></td>
							<td><%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(10)) %></td> 
							<td><%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(11)) %></td>
			 			</tr>
				 	<%	} %>
				 	<%}else{%>
						<tr class="ResultNotFound" id="notFoundTable">
							<td colspan="12">No record found</td>
						</tr>
				 	<%} %>
					</table>
				</div>	
			</div>
			<div class="PanelValueList">			
				<jsp:include page="../appform/pl/valueList.jsp" flush="true" />
			</div>
		</div>
	</div>
</div>  
<%=HTMLRenderUtil.displayHiddenField("N","jobStatus") %>
<%=HTMLRenderUtil.displayHiddenField("","appRecordID") %>
<%=HTMLRenderUtil.displayHiddenField("","appStatus") %>
<%=HTMLRenderUtil.displayHiddenField("","jobState") %>
<%=HTMLRenderUtil.displayHiddenField("","fromSearch") %>
<%=HTMLRenderUtil.displayHiddenField("","roleElement") %>
