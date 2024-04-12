<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil"%>
<%@ page import="java.util.Vector"%>
<%@ page import = "java.util.HashMap"%>  
<%@ page import = "com.eaf.j2ee.system.LoadXML"%>
<jsp:useBean id="SearchM" scope="session" class="com.eaf.orig.shared.search.SearchM"/>
<% 	
    Vector jobVect = SearchM.getResult();
%>

<%	
if(null != jobVect && jobVect.size() > 0){
	HashMap pagePerPageMap = LoadXML.getPagePerPageMap();
	HashMap itemPerPageMap = LoadXML.getItemPerPageMap();
	int pagePerPage = (pagePerPageMap != null && pagePerPageMap.get("1") != null)?Integer.parseInt((String)pagePerPageMap.get("1")):1;
	int allItemCount = SearchM.getCount();
	int itemsPerPage = SearchM.getItemsPerPage();
	int pageCount = ((allItemCount%itemsPerPage)==0)?(allItemCount/itemsPerPage):(allItemCount/itemsPerPage)+1;
	int periodCount = ((pageCount%pagePerPage)==0)?(pageCount/pagePerPage):(pageCount/pagePerPage)+1;
	int atPage = SearchM.getAtPage();
	int atPeriod = ((atPage%pagePerPage)==0)?(atPage/pagePerPage):((atPage/pagePerPage)+1);
	int beginPage = ((atPeriod-1)*pagePerPage)+1;
	int endPage = ((beginPage+pagePerPage-1)>pageCount)?pageCount:(beginPage+pagePerPage-1);
%> 
<div class="textR" class="inbox-page">
	<%=MessageResourceUtil.getTextDescription(request, "PAGE") %> : <%=atPage%> / <%=pageCount%> &nbsp; 
	<%if(atPeriod!=1){%> 
		&nbsp;<a href="javascript:clickPageList('<%=(beginPage-1)%>')" class="valuelist-normal">&lt;&lt;</a>&nbsp;&nbsp;
	<%}%>
	<%for(int i=beginPage;i<=endPage;i++){%> 
		<%if(i!=atPage){%>
			&nbsp;<a href="javascript:clickPageList('<%=i%>')" class="valuelist-normal"><div class="valuelist-normal"><%=i%></div></a>&nbsp;
		<%}else{%> 
			&nbsp;<div class="valuelist-normal-active"><%=i%></div>&nbsp; 
		<%}%> 
	<%}%> 
	<%if(atPeriod!=periodCount){%> 
		&nbsp;&nbsp;<a href="javascript:clickPageList('<%=(beginPage+pagePerPage)%>')" class="valuelist-normal">&gt;&gt;</a>&nbsp;&nbsp;
	<%}%> 
	&nbsp;&nbsp;&nbsp;&nbsp;
	<select class="combobox-valuelist" name="combobox-valuelist" id="combobox-valuelist" onchange="javascript:clickItemPerPageList(this.value)">
		<%if (itemPerPageMap != null){
			for(int i=1;i<=itemPerPageMap.size();i++){
		%>
			<%if(Integer.parseInt((String)itemPerPageMap.get(String.valueOf(i)))==itemsPerPage){%>
				<option selected value="<%=(String)itemPerPageMap.get(String.valueOf(i))%>"><%=(String)itemPerPageMap.get(String.valueOf(i))%></option>
			<%}else{%>
				<option value="<%=(String)itemPerPageMap.get(String.valueOf(i))%>"><%=(String)itemPerPageMap.get(String.valueOf(i))%></option>
			<%}%>
		<%
			}
		}
		%>
	</select> 
</div>
<%}%>
<input type="hidden" name="itemsPerPage" id="itemsPerPage" value="">
<input type="hidden" name="atPage" id="atPage" value="">
