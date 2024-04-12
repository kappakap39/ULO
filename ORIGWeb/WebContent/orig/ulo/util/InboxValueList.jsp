<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.j2ee.system.LoadXML"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.apache.log4j.Logger"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<jsp:useBean id="SearchForm" scope="session" class="com.eaf.core.ulo.common.engine.SearchFormHandler"/>
<%
	Logger logger = Logger.getLogger(this.getClass());
	logger.debug("SearchFormHandler>>");
	ArrayList<HashMap<String,Object>> results = SearchForm.getResults();	
%>

<%if(!Util.empty(results)){
	HashMap pagePerPageMap = LoadXML.getPagePerPageMap();		
	int pagePerPage = (pagePerPageMap != null && pagePerPageMap.get("1") != null)?Integer.parseInt((String)pagePerPageMap.get("1")):1;
	int allItemCount = SearchForm.getCount();
	int itemsPerPage = SearchForm.getItemsPerPage();
	int pageCount = ((allItemCount%itemsPerPage)==0)?(allItemCount/itemsPerPage):(allItemCount/itemsPerPage)+1;
	int periodCount = ((pageCount%pagePerPage)==0)?(pageCount/pagePerPage):(pageCount/pagePerPage)+1;
	int atPage = SearchForm.getAtPage();
	int atPeriod = ((atPage%pagePerPage)==0)?(atPage/pagePerPage):((atPage/pagePerPage)+1);
	int beginPage = ((atPeriod-1)*pagePerPage)+1;
	int endPage = ((beginPage+pagePerPage-1)>pageCount)?pageCount:(beginPage+pagePerPage-1);
%>
<div class="row">
	<div class="col-xs-4">
		<div style="margin: 8px 0px;"><%=FormatUtil.getString(allItemCount)%> <%=HtmlUtil.getText(request,"ITEM_PAGE") %> <%=FormatUtil.getString(atPage)%> <%=HtmlUtil.getText(request,"OF") %> <%=FormatUtil.getString(pageCount) %></div>
	</div>
	<div class="col-xs-8">
		<div class="btnwrap" style="float: right">
			<%if(atPeriod!=1){%>
				<button class="btnpage pagging_first" onclick="clickPageList('<%=(1)%>')"></button>
				<button class="btnpage pagging_previous" onclick="clickPageList('<%=(atPage-1)%>')"></button>
			<%}%>
				
			<%for(int i=beginPage;i<=endPage;i++){%> 
				<%if(i!=atPage){%>
					<button class="btnpage" onclick="clickPageList('<%=i%>')"><%=i%></button>
				<%}else{%> 
					<button class="btnpage btn-selected"><%=i%></button>
				<%}%> 
			<%}%>
		
			<%if(atPeriod!=periodCount){%> 
		   		<button class="btnpage pagging_next" onclick="clickPageList('<%=(atPage+1)%>')"></button>
		   		<button class="btnpage pagging_last" onclick="clickPageList('<%=(beginPage+pagePerPage)%>')"></button>
			<%}%>
			<div style="float: left; padding: 5px 0;">
				<%=HtmlUtil.dropdown("ITEMS_PER_PAGE", "",FormatUtil.getString(itemsPerPage),SystemConstant.getConstant("FIELD_ID_ITEMS_PER_PAGE"), "", HtmlUtil.EDIT,HtmlUtil.elementTagId("ITEMS_PER_PAGE"), request) %>
			</div>
		</div>
	</div>
</div>
   
<%} %>

<%=HtmlUtil.hidden("atPage",FormatUtil.getString(SearchForm.getAtPage()))%>
<script type="text/javascript">
function clickPageList(atPage){
	Pace.block();
	$('[name="atPage"]').val(atPage);
	$('#action').val('Inbox');
	$('#handlerForm').val('N');
	$('#appFormName').submit();
}
function ITEMS_PER_PAGEActionJS(){
	Pace.block();
	$('#action').val('Inbox');
	$('#handlerForm').val('N');
	$('#appFormName').submit();
}
</script>

