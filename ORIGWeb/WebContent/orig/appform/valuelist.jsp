<%@ page import = "java.util.Vector"%>
<%@ page import = "java.util.HashMap"%>  
<%@ page import = "com.eaf.j2ee.system.LoadXML"%>
<%@ page import="com.eaf.orig.shared.model.ValueListM"%>
<%@page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%
	// setup value list
	Vector resultVt=null;
	ValueListM valueListM = (ValueListM)request.getSession().getAttribute("VALUE_LIST");
   	if (valueListM == null) {
	    valueListM = new  ValueListM();
	}
	resultVt=valueListM.getResult();  
	if(resultVt==null){
		resultVt=new Vector();
	}
%>


<!-- Start Page List of Value List -->
<%	
if((resultVt != null) && (resultVt.size() > 1)){

HashMap pagePerPageMap = LoadXML.getPagePerPageMap();
HashMap itemPerPageMap = LoadXML.getItemPerPageMap();
		
int pagePerPage = (pagePerPageMap != null && pagePerPageMap.get("1") != null)?Integer.parseInt((String)pagePerPageMap.get("1")):1;
int allItemCount = valueListM.getCount();
int itemsPerPage = valueListM.getItemsPerPage();
int pageCount = ((allItemCount%itemsPerPage)==0)?(allItemCount/itemsPerPage):(allItemCount/itemsPerPage)+1;
int periodCount = ((pageCount%pagePerPage)==0)?(pageCount/pagePerPage):(pageCount/pagePerPage)+1;
int atPage = valueListM.getAtPage();
int atPeriod = ((atPage%pagePerPage)==0)?(atPage/pagePerPage):((atPage/pagePerPage)+1);
int beginPage = ((atPeriod-1)*pagePerPage)+1;
int endPage = ((beginPage+pagePerPage-1)>pageCount)?pageCount:(beginPage+pagePerPage-1);
%> 
<font class="textColS">
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
%>&nbsp;&nbsp;<A href="javascript:clickPageList('<%=(beginPage+pagePerPage)%>')">&gt;&gt;</A>&nbsp;&nbsp;
<%
}
%> 
&nbsp;&nbsp;&nbsp;&nbsp; 
<%
// remove select item per page by Vikrom
 %>
<!-- <SELECT class="ComboBoxTW" name="select2" onchange="javascript:clickItemPerPageList(this.value)"> -->
<%-- <% --%>
<!-- if (itemPerPageMap != null){ -->
<!-- 	for(int i=1;i<=itemPerPageMap.size();i++){ -->
<!--   		if(Integer.parseInt((String)itemPerPageMap.get(String.valueOf(i)))==itemsPerPage){ -->
<!-- %> -->
<%-- <OPTION selected value="<%=(String)itemPerPageMap.get(String.valueOf(i))%>"><%=(String)itemPerPageMap.get(String.valueOf(i))%> --%>
<%-- <% --%>
<!-- 	}else{ -->
<!-- %> -->
<!-- </OPTION> -->
<%-- <OPTION value="<%=(String)itemPerPageMap.get(String.valueOf(i))%>"><%=(String)itemPerPageMap.get(String.valueOf(i))%> --%>
<%-- <% --%>
<!-- 		} -->
<!-- 	} -->
<!-- } -->
<!-- %> -->
<!-- </OPTION> -->
<!-- </SELECT>  -->
<%}%> 
&nbsp;&nbsp;&nbsp;&nbsp;</font>
<input type="hidden" name="itemsPerPage" value="">
<input type="hidden" name="atPage" value="">
<!-- End Page List of Value List -->

