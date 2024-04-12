<%@ page import="com.eaf.orig.shared.util.OrigUtil"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.DataFormatUtility"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.PLMessageResourceUtil"%>
<%@ page import="com.eaf.orig.cache.properties.UserNameProperties"%>
<%@ page import="com.eaf.orig.shared.model.SearchDataM"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import="java.util.Vector"%>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="java.util.Properties"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="VALUE_LIST" scope="session" class="com.eaf.orig.shared.model.ValueListM"/>

<script type="text/javascript" src="js/popcalendar-screen.js"></script>
<script type="text/javascript" src="orig/js/cb/consent.enquiry.screen.js"></script>
<%  
	org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger("orig/cb/consent_enquiry.jsp");
	  
    String displayMode = HTMLRenderUtil.DISPLAY_MODE_EDIT;
    
	ORIGCacheUtil cacheUtil = new ORIGCacheUtil();

	String dateFrom = (String)request.getSession().getAttribute("FIRST_SEARCH_dateFrom");
	String dateTo = (String)request.getSession().getAttribute("FIRST_SEARCH_dateTo");
	if(OrigUtil.isEmptyString(dateFrom)){
		dateFrom = "";
	}
	if(OrigUtil.isEmptyString(dateTo)){
		dateTo = "";
	}

%>
<div class="nav-inbox">
	<div class="PanelFirst">
		<div class="PanelSecond TextHeaderNormal">
			<div class="PanelThird">
				<fieldset class="field-set">
				<legend>Search Criteria</legend>
					<table class="FormFrame">
						<tr>
                        	<td class="textR" width="20%"><%=PLMessageResourceUtil.getTextDescription(request, "CONSENT_REF_NO_DATE")%> <%=PLMessageResourceUtil.getTextDescription(request, "FROM")%><span style="color: red">*</span> :</td>
                            <td class="textL" width="30%"><%=HTMLRenderUtil.displayInputTagJsDate("", HTMLRenderUtil.displayHTML(dateFrom), displayMode, "15", "dateFrom", "textbox","", "") %></td><%// onblur=\"javascript:checkDate('sendCardDate');\" onkeydown=\"javascript:DateFormat(this,this.value,event,false,'3')\" %>
                            <td class="textR" width="25%"><%=PLMessageResourceUtil.getTextDescription(request, "TO")%><span style="color: red">*</span> :</td>
                            <td class="textL" width="25%"><%=HTMLRenderUtil.displayInputTagJsDate("", HTMLRenderUtil.displayHTML(dateTo), displayMode, "15", "dateTo", "textbox","left", "") %></td>
                        </tr>
                        <tr>
                        	<td colspan="4" align="center"><input type="button" id="button-search" class="button" value="Search" onClick="javascript:search()" /></td>
                        </tr>
					</table>
				</fieldset>
			</div>
			<div class="PanelThird">
				<table class="TableFrame">
					<%
						Vector valueList = VALUE_LIST.getResult();
					%>
					<%if(valueList!=null&&valueList.size()>1){%>
						<tr height="30">
		                    <td class="textL" nowrap="nowrap" colspan="12">Total Records Found : <%=VALUE_LIST.getCount() %></td>
		                </tr>
					<%}else{%>
						<tr height="30">
		                    <td class="textL" nowrap="nowrap" colspan="12">Total Records Found : 0</td>
		                </tr>
					<%}%>
                    <tr class="Header">
                    	<td><%=PLMessageResourceUtil.getTextDescription(request, "CONSENT_REF_NO_DATE")%></td>
                    	<td><%=PLMessageResourceUtil.getTextDescription(request, "RECEIVE_JOB_DATE")%></td>
                        <td><%=PLMessageResourceUtil.getTextDescription(request, "APPLICATION_NO")%></td>
                        <td><%=PLMessageResourceUtil.getTextDescription(request, "SALE_TYPE")%></td>
                        <td><%=PLMessageResourceUtil.getTextDescription(request, "FULL_NAME")%></td>
                        <td><%=PLMessageResourceUtil.getTextDescription(request, "ID_NO")%></td>
                        <td><%=PLMessageResourceUtil.getTextDescription(request, "BIRTH_DATE")%></td>
                        <td><%=PLMessageResourceUtil.getTextDescription(request, "BUREAU_REQUESTER")%></td>
                        <td><%=PLMessageResourceUtil.getTextDescription(request, "APPROVE_BUREAU_BY")%></td>
                        <td><%=PLMessageResourceUtil.getTextDescription(request, "CONSENT_REF_NO")%></td>
                        <td><%=PLMessageResourceUtil.getTextDescription(request, "DOCUMENT")%></td>
                    </tr>
                    <%
                    if((valueList!=null)&&(valueList.size()>1)){
						for(int i=1 ; i<valueList.size() ; i++){
							Vector elementList = (Vector)valueList.get(i);
							String styleTr = ((i-1)%2==0)?"ResultEven":"ResultOdd"; 
					%>
						<tr class="Result-Obj <%=styleTr%>" id="">
			            	<td><%=DataFormatUtility.stringDateTimeValueListForThai((String) elementList.elementAt(1)) %></td>
			                <td><%=DataFormatUtility.stringDateTimeValueListForThai((String) elementList.elementAt(10)) %></td>
			                <td><%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(2)) %></td>
			                <td><%=HTMLRenderUtil.displaySaleType((String) elementList.elementAt(12))%></td>
			                <td>
			                	<%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(3)) %> 
			                	<%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(4)) %>
			                </td>
			                <td><%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(5)) %></td>
			                <td><%=DataFormatUtility.stringDateValueListForThai((String) elementList.elementAt(6)) %></td>
			                <td><%=HTMLRenderUtil.displayHTML(HTMLRenderUtil.displayThFullName((String) elementList.elementAt(7))) %></td>
			                <td><%=HTMLRenderUtil.displayHTML(HTMLRenderUtil.displayThFullName((String) elementList.elementAt(8))) %></td>
			                <td><%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(9)) %></td>
			                <td><img src="images/doc.png" style="cursor:pointer;" class="viewImg <%=(String) elementList.elementAt(11)%>"></td>
			            </tr>									  		
					<%}%>                                      
                    <%}else{%>
	                    <tr class="ResultNotFound">
			            	<td colspan="11" >No record found</td>
			            </tr>
                    <%}%>
              	</table>
				<div class="PanelValueList">			
					<jsp:include page="../appform/pl/valueList.jsp" flush="true" />
				</div>
			</div>
			<div class="PanelThird">
				<table class="TableFrame">
					<tr>
                    	<td><input type="button" value="Export to Excel" onclick="exportExcel()" class="button"></td>
                    </tr>
				</table>
			</div>
		</div>
	</div>
</div>
