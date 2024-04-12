<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.Vector"%>
<%@page import="com.eaf.orig.ulo.pl.formcontrol.view.form.SearchHandler"%>
<%@page import="com.eaf.orig.shared.util.OrigUtil"%>
<%@page import="com.eaf.orig.profile.model.UserDetailM"%>
<%@page import="com.eaf.orig.ulo.pl.app.utility.DataFormatUtility"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="VALUE_LIST" scope="session" class="com.eaf.orig.shared.model.ValueListM" />

<script type="text/javascript" src="js/popcalendar-screen.js"></script>
<script type="text/javascript" src="orig/js/ss/cardMaintenance.js"></script>

<% 
	org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger("orig/ss/card_maintenance.jsp");
    
    String searchType = (String) request.getSession().getAttribute("searchType");
    String displayMode = HTMLRenderUtil.DISPLAY_MODE_EDIT;
    
	SearchHandler HandlerM = (SearchHandler) request.getSession().getAttribute("SEARCH_DATAM");
	if(HandlerM == null){
		HandlerM = new SearchHandler();
	}
	SearchHandler.SearchDataM searchDataM = HandlerM.getSearchM();
	if(searchDataM == null){
		searchDataM = new SearchHandler.SearchDataM();
	}
	if(OrigUtil.isEmptyString(searchDataM.getProductCode())){
		searchDataM.setProductCode(OrigConstant.PRODUCT_KEC);
	}
	
	ORIGCacheUtil origCache = ORIGCacheUtil.getInstance();
	Vector vProduct = origCache.loadCacheByActive("MainProduct");	
	Vector vPriority = (Vector)origCache.getNaosCacheDataMs("ALL_ALL_ALL", 63);
	Vector vUser = (Vector)origCache.loadUserNameCache(OrigConstant.ACTIVE_FLAG);
	
%>
<div class="nav-inbox">
	<div class="PanelFirst">
		<div class="PanelSecond TextHeaderNormal">
			<%=SearchHandler.DisplayErrorMessage(request)%>
			<div class="PanelThird">
				<fieldset class="field-set">
				<legend>Search Criteria</legend>
					<table class="FormFrame">
						<tr>
                        	<td class="textR"><%=MessageResourceUtil.getTextDescription(request, "SEND_CARD_LINK_DATE")%> :</td>
                            <td class="textL"><%=HTMLRenderUtil.displayInputTagJsDate("", HTMLRenderUtil.displayHTML(DataFormatUtility.StringDateENToStringDateTH(searchDataM.getStringDate())), displayMode, "15", "sendCardDate", "textbox","", "") %><input type="hidden" name="hiddenSendCardDate"></td>
                            <td class="textR"><%=MessageResourceUtil.getTextDescription(request, "PRODUCT")%><font color="#FF0000">*</font> :</td>
                            <td class="textL"><%=HTMLRenderUtil.displaySelectTagScriptActionAndCode_ORIG(vProduct,searchDataM.getProductCode(),"product",displayMode,"") %></td>
                            <td class="textR"><%=MessageResourceUtil.getTextDescription(request, "APPLICATION_NO")%> :</td>
                            <td class="textL"><%=HTMLRenderUtil.displayInputTagScriptAction(searchDataM.getApplicationNo(),displayMode,"","application_no","textbox","","12") %></td>
                        </tr>
                        <tr height="25">
                        	<td class="textR"><%=MessageResourceUtil.getTextDescription(request, "CARD_NO")%> :</td>
                            <td class="textL"><%=HTMLRenderUtil.displayInputTagScriptAction(searchDataM.getCardNo(),displayMode,"","cardNo","textbox"," onkeypress= \"return keyPressInteger(event)\"","16") %></td>
                            <td class="textR"><%=MessageResourceUtil.getTextDescription(request, "NAME_THAI")%> :</td>
                            <td class="textL"><%=HTMLRenderUtil.displayInputTagScriptAction(searchDataM.getCustomerName(),displayMode,"","firstName","textbox","onblur=\"countChar2(this)\"","120") %></td>
                            <td class="textR"><%=MessageResourceUtil.getTextDescription(request, "SURNAME_THAI")%> :</td>
                            <td><%=HTMLRenderUtil.displayInputTagScriptAction(searchDataM.getCustomerLName(),displayMode,"","lastName","textbox","onblur=\"countChar2(this)\"","50") %></td>
                        </tr>
                        <tr height="25">
                            <td class="textR" ><%=MessageResourceUtil.getTextDescription(request, "CARD_LINK_REF_NO")%> :</td>
                            <td><%=HTMLRenderUtil.displayInputTagScriptAction(searchDataM.getCardRefNo(),displayMode,"","cardRefNo","textbox","","9") %></td>
                            <td class="textR"><%=MessageResourceUtil.getTextDescription(request, "CARD_LINK_STATUS")%> :</td>
                            <td><%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(vPriority, searchDataM.getCardStatus(),"cardStatus",displayMode,"")%>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                        </tr>
                        <tr height="25">
                            <td colspan="3">&nbsp;</td>
                            <td valign="top"><input type="button" id="button-search" class="button" value="Search" onClick="javascript:search()" /></td>
                        </tr>
					</table>
				</fieldset>
			</div>
			<div class="PanelThird">
				<table class="TableFrame">
					<%Vector valueList = VALUE_LIST.getResult();
					if((valueList!=null)&&(valueList.size()>1)){%>
					<tr height="30"><td class="textL" nowrap="nowrap" colspan="6">Total Records Found : <%=VALUE_LIST.getCount() %></td></tr>
					<%} else {%>
					<tr height="30"><td class="textL" nowrap="nowrap" colspan="6">Total Records Found : 0</td></tr>
					<%}%>
                    <tr class="Header">
                    	<td><%=MessageResourceUtil.getTextDescription(request, "CARD_NO")%></td>
                    	<td><%=MessageResourceUtil.getTextDescription(request, "APPLICATION_NO")%></td>
                    	<td><%=MessageResourceUtil.getTextDescription(request, "CARD_LINK_REF_NO")%></td>
                    	<td><%=MessageResourceUtil.getTextDescription(request, "FULL_NAME")%></td>
                    	<td><%=MessageResourceUtil.getTextDescription(request, "PRODUCT")%></td>
	                    <td><%=MessageResourceUtil.getTextDescription(request, "RECEIVED_TIME")%></td>
                        <td><%=MessageResourceUtil.getTextDescription(request, "CARD_LINK_STATUS")%></td>
                        <td><%=MessageResourceUtil.getTextDescription(request, "SEND_CARD_LINK_DATE")%></td>
                        <td><%=MessageResourceUtil.getTextDescription(request, "GEN_CARD_NO_UPDATE_DATE")%></td>
                        <td><%=MessageResourceUtil.getTextDescription(request, "GEN_CARD_NO_UPDATE_BY")%></td>
                        <td><%=MessageResourceUtil.getTextDescription(request, "ACTION")%></td>
					</tr>
                    <%if((valueList!=null)&&(valueList.size()>1)){
						for(int i=1; i<valueList.size() ; i++){
							Vector elementList = (Vector)valueList.get(i);
							String styleTr = ((i-1)%2==0)?"ResultEven":"ResultOdd";%>
						<tr class="Result-Obj <%=styleTr%>" id="<%=(String) elementList.elementAt(12)%>|<%=(String) elementList.elementAt(3)%>|<%=(String) elementList.elementAt(6)%>|<%=(String) elementList.elementAt(1)%>|<%=(String) elementList.elementAt(15)%>|<%=(String) elementList.elementAt(16)%>|<%=(String) elementList.elementAt(4)%>|<%=(String) elementList.elementAt(5)%>|<%=(String) elementList.elementAt(3)%>|<%=(String) elementList.elementAt(8)%>">						
						<td><%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(1)) %></td>
						<td><%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(2)) %></td>
						<td><%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(3)) %></td>
						<td><%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(4)) %>&nbsp;<%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(5)) %></td>
						<td><%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(6)) %></td>
						<td><%=DataFormatUtility.stringDateTimeValueListForThai((String) elementList.elementAt(7)) %></td>
						<td><%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(8)) %></td>
						<td><%=DataFormatUtility.stringDateTimeValueListForThai((String) elementList.elementAt(9)) %></td>
						<td><%=DataFormatUtility.stringDateTimeValueListForThai((String) elementList.elementAt(10)) %></td>
						<td><%=HTMLRenderUtil.displayHTML(HTMLRenderUtil.displayThFullName((String) elementList.elementAt(11), vUser)) %></td>
						<%
							String action = (String) elementList.elementAt(17);
							String MESSAGE = "";
							if(OrigConstant.Action.CANCEL_APPLICATION_AFTER_CARDLINK.equals(action)){
								MESSAGE = MessageResourceUtil.getTextDescription(request, "CANCEL_APPLICATION");
							}else if(OrigConstant.Action.RE_ISSUE_CARD_NO.equals(action)){
								MESSAGE = MessageResourceUtil.getTextDescription(request, "RE_ISSUE_CARD_NO");
							}else if(OrigConstant.Action.RE_ISSUE_CUSTOMER_NO.equals(action)){
								MESSAGE = MessageResourceUtil.getTextDescription(request, "RE_ISSUE_CUSTOMER_NO");
							}
						 %>
						<td><%=HTMLRenderUtil.displayHTML(MESSAGE)%></td>
					</tr>									  		
					<%}%>                                    
                    <%}else{%>
	                    <tr class="ResultNotFound">
			            	<td colspan="10" >No record found</td>
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
<%=HTMLRenderUtil.displayHiddenField("", "LaccId") %>
<%=HTMLRenderUtil.displayHiddenField("", "LappRecId") %>
<%=HTMLRenderUtil.displayHiddenField("", "LcardType") %>
<%=HTMLRenderUtil.displayHiddenField("", "Lproduct") %>
<%=HTMLRenderUtil.displayHiddenField("", "Lfname") %>
<%=HTMLRenderUtil.displayHiddenField("", "Llname") %>
<%=HTMLRenderUtil.displayHiddenField("", "LcardFace") %>
<%=HTMLRenderUtil.displayHiddenField("", "LcardNo") %>
<%=HTMLRenderUtil.displayHiddenField("", "LcardRefId") %>
<%=HTMLRenderUtil.displayHiddenField("", "LcardStatus") %>
