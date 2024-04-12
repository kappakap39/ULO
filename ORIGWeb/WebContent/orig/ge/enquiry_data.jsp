<%@page import="com.eaf.orig.shared.dao.utility.ObjectDAOFactory"%>
<%@page import="com.eaf.orig.shared.dao.utility.UtilityDAO"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.Vector"%>
<%@page import="com.eaf.orig.ulo.pl.app.utility.WorkflowTool"%>
<%@page import="com.eaf.orig.ulo.pl.formcontrol.view.form.SearchHandler"%>
<%@page import="com.eaf.orig.ulo.pl.app.utility.PLMessageResourceUtil"%>
<%@page import="com.eaf.orig.shared.model.SearchDataM"%>
<%@page import="com.eaf.orig.ulo.pl.app.utility.RenderSubTableUtility"%>
<%@page import="com.eaf.orig.ulo.pl.app.utility.DataFormatUtility"%>
<%@page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@page import="com.eaf.orig.shared.utility.ORIGUtility" %>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="VALUE_LIST" scope="session" class="com.eaf.orig.shared.model.ValueListM" />
<script type="text/javascript" src="orig/js/ge/enquiry.data.screen.js"></script>
<%
	Logger log = Logger.getLogger(this.getClass());
    String first = (String) request.getParameter("first");
    
    SearchHandler HandlerM = (SearchHandler) request.getSession().getAttribute("SEARCH_DATAM");
	if(HandlerM == null){
		HandlerM = new SearchHandler();
	}
	SearchHandler.SearchDataM searchDataM = HandlerM.getSearchM();
	if(searchDataM == null){
		searchDataM = new SearchHandler.SearchDataM();
	}
	if(ORIGUtility.isEmptyString(searchDataM.getProductCode())){
		searchDataM.setProductCode(OrigConstant.PRODUCT_KEC);
	}
// 	if(ORIGUtility.isEmptyString(searchDataM.getSaleType()) && first != null && first.equals("true")){
// 		searchDataM.setSaleType(OrigConstant.ShortSaleType.NORMAL);
// 	}
	
    String displayMode = HTMLRenderUtil.DISPLAY_MODE_EDIT;
	
	ORIGCacheUtil origCache = ORIGCacheUtil.getInstance();
	Vector vProduct = origCache.loadCacheByActive("MainProduct");
	Vector vPriority = (Vector)origCache.getNaosCacheDataMs("ALL_ALL_ALL", 61);
	Vector vSaleType = origCache.loadCacheByName("MainSaleType");	
	Vector vUser = (Vector)origCache.loadUserNameCache(OrigConstant.Status.STATUS_ACTIVE);

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
                    	<td class="textR"><%=PLMessageResourceUtil.getTextDescription(request, "APPLICATION_NO")%> :</td>
                        <td class="textL"><%=HTMLRenderUtil.displayInputTagScriptAction(searchDataM.getApplicationNo(),displayMode,"","appNo","textbox","","20")%></td>
                        <td class="textR"><%=PLMessageResourceUtil.getTextDescription(request, "REFERENCE_NO")%> :</td>
                        <td class="textL"><%=HTMLRenderUtil.displayInputTagScriptAction(searchDataM.getRefNo(),displayMode,"","refNo","textbox200","","30")%></td>
                        <td class="textR" ><%=PLMessageResourceUtil.getTextDescription(request, "PRODUCT")%><font color="#FF0000">*</font> :</td>
                        <td class="textL"><%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(vProduct,searchDataM.getProductCode(),"product",displayMode,"")%></td>
                    </tr>
                    <tr height="25">
                        <td class="textR"><%=PLMessageResourceUtil.getTextDescription(request, "NAME_THAI")%> :</td>
                        <td class="textL"><%=HTMLRenderUtil.displayInputTagScriptAction(searchDataM.getCustomerName(),displayMode,"","firstName","textbox","onblur=\"countChar2(this)\"","120")%></td>
                        <td class="textR"><%=PLMessageResourceUtil.getTextDescription(request, "SURNAME_THAI")%> :</td>
                        <td class="textL"><%=HTMLRenderUtil.displayInputTagScriptAction(searchDataM.getCustomerLName(),displayMode,"","lastName","textbox","onblur=\"countChar2(this)\"","50")%></td>
                        <td class="textR"><%=PLMessageResourceUtil.getTextDescription(request, "IDENTIFICATION_NO")%> :</td>
                        <td class="textL"><%=HTMLRenderUtil.displayInputTagScriptAction(searchDataM.getCitizenID(),displayMode,"","IDNO","textbox","","20")%></td>
                    </tr>
                    <tr height="25">
                        <td class="textR" ><%=PLMessageResourceUtil.getTextDescription(request, "SALE_TYPE")%> :</td>
                        <td class="textL"><%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(vSaleType, searchDataM.getSaleType(),"saleType",displayMode,"")%></td>
                        <td class="textR"><%=PLMessageResourceUtil.getTextDescription(request, "PRIORITY")%> :</td>
                        <td class="textL"><%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(vPriority, searchDataM.getPriority(),"priority",displayMode,"")%></td>
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
				<div class="scroll-div">
					<table class="TableFrame">
						<%
							Vector valueList = VALUE_LIST.getResult();
							if((valueList!=null) && (valueList.size()>1)){
						%>
							<tr height="30"><td class="textL" nowrap="nowrap" colspan="13">Total Records Found : <%=VALUE_LIST.getCount()%></td></tr>
						<%}else{%>
							<tr height="30"><td class="textL" nowrap="nowrap" colspan="13">Total Records Found : 0</td></tr>
						<%}%>
							<tr class="Header">
		                    	<td width="5%"><%=PLMessageResourceUtil.getTextDescription(request, "PRIORITY")%></td>
		                        <td width="5%"><%=PLMessageResourceUtil.getTextDescription(request, "APPLICATION_NO")%></td>
		                        <td width="10%"><%=PLMessageResourceUtil.getTextDescription(request, "REFERENCE_NO")%></td>
		                        <td width="10%"><%=PLMessageResourceUtil.getTextDescription(request, "APPLICATION_STATUS")%></td>
		                        <td width="7%"><%=PLMessageResourceUtil.getTextDescription(request, "REASON")%></td>
		                        <td width="13%"><%=PLMessageResourceUtil.getTextDescription(request, "FULL_NAME")%></td>
		                        <td width="5%"><%=PLMessageResourceUtil.getTextDescription(request, "IDENTIFICATION_NO")%></td>
		                        <td width="10%"><%=PLMessageResourceUtil.getTextDescription(request, "PRODUCT")%></td>
		                        <td width="5%"><%=PLMessageResourceUtil.getTextDescription(request, "SALE_TYPE")%></td>
		                        <td width="5%"><%=PLMessageResourceUtil.getTextDescription(request, "SYSTEM_ACCEPT_DATE")%></td>
		                        <td width="5%"><%=PLMessageResourceUtil.getTextDescription(request, "LAST_UPDATE_DATE")%></td>
		                        <td width="7%"><%=PLMessageResourceUtil.getTextDescription(request, "LAST_UPDATE_BY")%></td>
		                        <td width="8%"><%=PLMessageResourceUtil.getTextDescription(request, "OWNER")%></td>
		                    </tr>
	                    <%
	                    if((valueList!=null)&&(valueList.size()>1)){
	                    	WorkflowTool wt = new WorkflowTool();
	                    	UtilityDAO DAO =  ObjectDAOFactory.getUtilityDAO();
	                    	for(int i=1 ; i<valueList.size() ; i++){
	                    		Vector elementList = (Vector)valueList.get(i);
	                    		String styleTr = ((i-1)%2==0)?"ResultEven":"ResultOdd";
	                    %>
							<tr class="Result-Obj <%=styleTr%>" id="<%=(String) elementList.elementAt(12)%>">
				            	<td nowrap="nowrap"><%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(1))%></td>
				                <td nowrap="nowrap"><%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(2))%></td>
				                <td nowrap="nowrap"><%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(3))%></td>
				                <td><%=HTMLRenderUtil.displayHTML(wt.GetMessageAppStatus(request, (String) elementList.elementAt(4)))%></td>
				                <td><%=DAO.getReasonDesc((String) elementList.elementAt(12))%></td>
				                <td><%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(5)) %></td>
				                <td nowrap="nowrap"><%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(6)) %></td>
				                <td><%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(7)) %></td>
				                <td nowrap="nowrap"><%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(8)) %></td>
				                <td><%=DataFormatUtility.stringDateTimeValueListForThai((String) elementList.elementAt(9)) %></td>
				                <td><%=DataFormatUtility.stringDateTimeValueListForThai((String) elementList.elementAt(10)) %></td>
				                <td><%=HTMLRenderUtil.displayHTML(HTMLRenderUtil.displayThFullName((String) elementList.elementAt(11), vUser)) %></td>
				                <td><%=HTMLRenderUtil.displayHTML(HTMLRenderUtil.displayThFullName((String) elementList.elementAt(13), vUser)) %></td>
				            </tr>									  		
						<%}}else{%>
		                    <tr class="ResultNotFound">
				            	<td colspan="13" >No record found</td>
				            </tr>		
	                    <%}%>
					</table>
				</div>			
			</div>
			<div class="PanelValueList">			
				<jsp:include page="../appform/pl/valueList.jsp" flush="true" />
			</div>
		</div>
	</div>
</div>

<%=HTMLRenderUtil.displayHiddenField("", "roleElement") %>
<%=HTMLRenderUtil.displayHiddenField("", "appRecordID") %>
<%=HTMLRenderUtil.displayHiddenField("", "searchType") %>
