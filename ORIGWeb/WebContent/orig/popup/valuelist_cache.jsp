<%@ page import = "java.util.Vector"%>
<%@ page import = "java.util.HashMap"%>  
<%@ page import = "com.eaf.j2ee.system.LoadXML"%>
<%@ page import="com.eaf.orig.shared.model.ValueListM"%>
<%@page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@page import="com.eaf.orig.shared.valuelist.view.webaction.ValueListWebAction" %>
<%
	// setup value list
	Vector resultVt=null;
	ValueListM valueListM = (ValueListM)request.getSession().getAttribute("VALUE_LIST");
	
   	if (valueListM == null) {
	    valueListM = new  ValueListM();
	}
	resultVt=valueListM.getResult();  
	if(resultVt == null){
		resultVt = new Vector();
	}
	Vector allDataCache = (Vector)request.getSession().getAttribute("CACHE_POPUP");
	if(allDataCache == null){
		allDataCache = new Vector();
	}
%>


<!-- Start Page List of Value List -->
<%	
if((resultVt != null) && (resultVt.size() > 0)){

HashMap pagePerPageMap = LoadXML.getPagePerPageMap();
HashMap itemPerPageMap = LoadXML.getItemPerPageMap();
		
int pagePerPage = (pagePerPageMap != null && pagePerPageMap.get("1") != null)?Integer.parseInt((String)pagePerPageMap.get("1")):1;
int allItemCount = allDataCache.size();
int itemsPerPage = valueListM.getItemsPerPage();
int pageCount = ((allItemCount%itemsPerPage)==0)?(allItemCount/itemsPerPage):(allItemCount/itemsPerPage)+1;
int periodCount = ((pageCount%pagePerPage)==0)?(pageCount/pagePerPage):(pageCount/pagePerPage)+1;
int atPage = valueListM.getAtPage();
int atPeriod = ((atPage%pagePerPage)==0)?(atPage/pagePerPage):((atPage/pagePerPage)+1);
int beginPage = ((atPeriod-1)*pagePerPage)+1;
int endPage = ((beginPage+pagePerPage-1)>pageCount)?pageCount:(beginPage+pagePerPage-1);
%> 


<%
	String code = (String)request.getSession(true).getAttribute(ValueListWebAction.VALUE_LIST_SEARCH_CODE);
	System.out.println("code ----->" + code);
 %>
<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
<tr> <td>
<input type="text" name="code" class="textbox" value="<%= (code != null && !"".equals(code))? code:"" %>" size="10">&nbsp;
</td><td>
<input type="button" name="" class="button_text" value="<%=MessageResourceUtil.getTextDescription(request, "SEARCH") %>" onclick="javascript:clickSearch()">&nbsp;&nbsp;&nbsp;
</td></tr>
<tr><td></td><td class="textColS">
<%=MessageResourceUtil.getTextDescription(request, "PAGE") %> : <%=atPage%> / <%=pageCount%> &nbsp; 
<%
if(atPeriod!=1){
%> &nbsp;<A href="javascript:clickPageList('<%=(beginPage-1)%>')">&lt;&lt;</A>&nbsp;&nbsp;
												<%
}
%> <%
for(int i=beginPage;i<=endPage;i++){
	if(i!=atPage){
%> &nbsp;<A href="javascript:clickPageList('<%=i%>')"><FONT class="bk"><%=i%></FONT></A>&nbsp;
												<%
	}else{
%> &nbsp;<FONT color="red"><%=i%></FONT>&nbsp; <%
	}
%> <%
}
%> <%
if(atPeriod!=periodCount){
%> &nbsp;&nbsp;<A href="javascript:clickPageList('<%=(beginPage+pagePerPage)%>')">&gt;&gt;</A>&nbsp;&nbsp;
<%
}
%> 
&nbsp;&nbsp;&nbsp;&nbsp; 
<SELECT class="ComboBoxTW" name="select2" onchange="javascript:clickItemPerPageList(this.value)">
<%
if (itemPerPageMap != null){
	for(int i=1;i<=itemPerPageMap.size();i++){
  		if(Integer.parseInt((String)itemPerPageMap.get(String.valueOf(i)))==itemsPerPage){
%>
<OPTION selected value="<%=(String)itemPerPageMap.get(String.valueOf(i))%>"><%=(String)itemPerPageMap.get(String.valueOf(i))%>
<%
	}else{
%>
</OPTION>
<OPTION value="<%=(String)itemPerPageMap.get(String.valueOf(i))%>"><%=(String)itemPerPageMap.get(String.valueOf(i))%>
<%
		}
	}
}
%>
</OPTION>
</SELECT> </td></tr></table>
<% }else{%>
<%
	String code = (String)request.getSession(true).getAttribute(ValueListWebAction.VALUE_LIST_SEARCH_CODE);
	System.out.println("code ----->" + code);
 %>
<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
<tr> <td>
<input type="text" name="code" class="textbox" value="<%= (code != null && !"".equals(code))? code:"" %>" size="10">&nbsp;
</td><td>
<input type="button" name="Search" class="button_text" value="<%=MessageResourceUtil.getTextDescription(request, "SEARCH") %>" onclick="javascript:clickSearch()">
</td></tr></table>
<%}%> 
<!-- End Page List of Value List -->
<input type="hidden" name="atPage">
<input type="hidden" name="itemsPerPage">
<input type="hidden" name="cacheName" value="<%=valueListM.getCacheName()%>">
<input type="hidden" name="textboxCode" value="<%=valueListM.getTextboxCode()%>">
<input type="hidden" name="textboxDesc" value="<%=valueListM.getTextboxDesc()%>">
<SCRIPT language="JavaScript">
function clickPageList(atPage){
	var form=document.appFormName;
	form.atPage.value = atPage;
	form.action.value = "ValueListCacheWebAction";
	form.handleForm.value = "N";
	form.submit();
}
function clickItemPerPageList(atItem){
	var form=document.appFormName;
	form.itemsPerPage.value = atItem;
	form.action.value = "ValueListCacheWebAction";
	form.handleForm.value = "N";
	form.submit();  
}
function clickSearch(){
	var form=document.appFormName;
	form.action.value = "<%=valueListM.getSearchAction()%>";
	form.handleForm.value = "N";
	form.submit();  
}
function setData(code, desc){
	var textboxCode = '<%=valueListM.getTextboxCode()%>';
	var textboxDesc = '<%=valueListM.getTextboxDesc()%>';
	var obj1 = eval("window.opener.appFormName."+textboxCode);
	var obj2 = eval("window.opener.appFormName."+textboxDesc);
	
	obj1.value = code;
	obj2.value = desc;
	
	window.close();
	obj1.focus();
}
</SCRIPT>
