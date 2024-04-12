<%@page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import = "java.util.Vector"%>
<%@ page import = "java.util.HashMap"%>  
<%@ page import = "com.eaf.j2ee.system.LoadXML"%>
<%@ page import="com.eaf.orig.shared.model.ValueListM"%>
<%@page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@page import="com.eaf.orig.shared.valuelist.view.webaction.ValueListWebAction" %>
<%@ page import="com.eaf.orig.shared.utility.TooltipResourceUtil" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%
	// setup value list
    TooltipResourceUtil tooltipUtil=TooltipResourceUtil.getInstance();
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


<%
	String code = (String)request.getSession(true).getAttribute(ValueListWebAction.VALUE_LIST_SEARCH_CODE);
	request.getSession(true).removeAttribute(ValueListWebAction.VALUE_LIST_SEARCH_CODE);
// 	System.out.println("code ----->" + code);
// 	System.out.println("Contect Path " + request.getContextPath());
// 	System.out.println("URL " + request.getRequestURI());
 %>
<table cellpadding="0" cellspacing="0" width="100%" border="0">
<tr>
	<td width="18%" align="center">
	
	</td>
<td align="left" >
<input type="text" name="code" class="textbox" value="<%= (code != null && !"".equals(code))? code:"" %>" size="10" onMouseOver="<%=tooltipUtil.getTooltip(request,"popup_search")%>">&nbsp;
<input type="button" name="" class="buttonNew" value="<%=MessageResourceUtil.getTextDescription(request, "SEARCH") %>" onclick="javascript:clickSearch()">
</td>
<!-- </tr> -->
<!-- <tr>  -->
<td class="textR">
<%=MessageResourceUtil.getTextDescription(request, "PAGE") %> : <%=atPage%> / <%=pageCount%>
<!-- </td> -->
<td class="inputL">
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
%> </td>
<td>&nbsp; 
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
</SELECT> </td>
</tr>
</table>
<%}else{
	String code = (String)request.getSession(true).getAttribute(ValueListWebAction.VALUE_LIST_SEARCH_CODE);
	System.out.println("code ----->" + code);	
	%>
<input type="text" name="code" class="textbox" value="<%= (code != null && !"".equals(code))? code:"" %>" size="10"  onMouseOver="<%=tooltipUtil.getTooltip(request,"popup_search")%>">
<input type="button" name="Search" class="button_text" value="<%=MessageResourceUtil.getTextDescription(request, "SEARCH") %>" onclick="javascript:clickSearch()">
<%}%> 
<!-- End Page List of Value List -->
<%=HTMLRenderUtil.displayHiddenField("", "atPage") %>
<%=HTMLRenderUtil.displayHiddenField("", "itemsPerPage") %>
<%=HTMLRenderUtil.displayHiddenField("", "bank") %>
<%=HTMLRenderUtil.displayHiddenField("", "car_brand") %>
<%=HTMLRenderUtil.displayHiddenField(valueListM.getCacheName(), "cacheName") %>
<%=HTMLRenderUtil.displayHiddenField(valueListM.getTextboxCode(), "textboxCode") %>
<%=HTMLRenderUtil.displayHiddenField(valueListM.getTextboxDesc(), "textboxDesc") %>
<SCRIPT language="JavaScript">
function clickPageList(atPage){
	var form=document.appFormName;
	form.atPage.value = atPage;
	form.action.value = "ValueListWebAction";
	form.handleForm.value = "N";
	form.submit();
}
function clickItemPerPageList(atItem){
	var form=document.appFormName;
	form.itemsPerPage.value = atItem;
	form.action.value = "ValueListWebAction";
	form.handleForm.value = "N";
	form.submit();  
}
function clickSearch(){
	var form=document.appFormName;
	var openForm = window.opener.appFormName;
	form.action.value = "<%=valueListM.getSearchAction()%>";
	form.handleForm.value = "N";
	
	//if(openForm.bank != null){
	//	form.bank.value = openForm.bank.value;
	//}
	
	//if(form.action.value == 'LoadCarBrand'){
	//	form.car_brand.value = '';
		//alert(form.car_brand.value);
	//}
	/*if(form.province != null){
		if(form.code.value == ''){
			form.province.value = "";
		}
	}*/
	form.submit();  
}
function setData(code, desc){
//  	alert("In setData");
 	var textboxCode = '<%=valueListM.getTextboxCode()%>'; 
 	var textboxDesc = '<%=valueListM.getTextboxDesc()%>';
//  	alert("textboxCode= "+textboxCode);
//  	alert("textboxDesc= "+textboxDesc);      
 	var obj1 = window.opener.document.getElementById(textboxCode);  
 	obj1.value = code; 
 	if(textboxDesc != null && textboxDesc != "" && textboxDesc != "undefined"){ 
 		var obj2 = window.opener.document.getElementById(textboxDesc); 
 		if(textboxCode == 'campaign'){ 
 			if (obj2!=undefined && obj2.value!=desc){clearSchemeByCampaign();} 
 		} 
 		if(obj2 != null){obj2.value = desc;} 
	}
	window.close();

 	if(textboxCode == 'project_code'){ 
 		window.opener.getvalue();
 	} 
	if(textboxCode == 'seller_branch_code'){
		displayChannelElement();
	}
 	if(obj1.type != "hidden"){ 
 		obj1.focus(); 
	}else{ 
		obj2.focus(); 
 	}
 	if(textboxCode == 'tambol'){ 
		window.opener.getZipCode();
// 		window.opener.$('#tambol_desc').val(desc);
	} 
 	window.close();
 } 
function setDataAddress(code, desc, sub_districtID, sub_district_desc,districtID, district_desc, proviceID, provice_desc){
//  	alert("In setDataAddress");
	var textboxCode = '<%=valueListM.getTextboxCode()%>';
	var textboxDesc = '<%=valueListM.getTextboxDesc()%>';
	//alert("textboxCode= "+textboxCode);
	//alert("textboxDesc= "+textboxDesc);	
	var obj1 = eval("window.opener.appFormName."+textboxCode);
	obj1.value = code;
	
	if(textboxCode == 'project_code'){
		window.opener.getvalue();
	}

 	if(textboxCode == 'province'){ 
 		window.opener.appFormName.zipcode.value = ""; 
	} 
	
 	if(textboxCode == 'project_code'){ 
 		window.opener.getvalue(); 
 	} 
	
 	if(textboxCode == 'tambol'){ 
 		window.opener.getZipCode();
 	}
 	
 	
	if(textboxDesc != null && textboxDesc != "" && textboxDesc != "undefined"){
		var obj2 = eval("window.opener.appFormName."+textboxDesc);	
		 
		if(obj2 != null){
			obj2.value = desc;
		}
	}
	var openForm = opener.document.appFormName;
	
	 window.opener.$("#province").val(proviceID);
	 window.opener.$("#province_desc").val(provice_desc);
	 window.opener.$("#amphur").val(districtID);
	 window.opener.$("#amphur_desc").val(district_desc);
	 window.opener.$("#tambol").val(sub_districtID);
	 window.opener.$("#tambol_desc").val(sub_district_desc);
	 
	 window.close();
	if(obj1.type != "hidden"){
		obj1.focus();
	}else{
		obj2.focus();
	}
}
function clearSchemeByCampaign(){
	form = window.opener.appFormName;
	if (form.scheme_code!=undefined && form.campaign!=undefined){
		window.opener.clearSchemeByCampaign();
	}
}
function setDataTitle(code, desc, gender, eng_desc){
	var textboxCode = '<%=valueListM.getTextboxCode()%>';
	var textboxDesc = '<%=valueListM.getTextboxDesc()%>';
	var obj1 = eval("window.opener.appFormName."+textboxCode);
//  	alert("In setDataTitle");
	obj1.value = code;
	if(textboxDesc != null && textboxDesc != "" && textboxDesc != "undefined"){
		var obj2 = eval("window.opener.appFormName."+textboxDesc);
		if(textboxCode == 'campaign'){
			if (obj2!=undefined && obj2.value!=desc){
				clearSchemeByCampaign();
			}
		}
		if(obj2 != null){
			obj2.value = desc;
		}
	}
	
	if(obj1.type != "hidden"){
		obj1.focus();
	}else{
		obj2.focus();
	}
	if(gender!=null){
		window.opener.$('#gender').val(gender);
	}
	if(eng_desc!=null){
		window.opener.$('#titleEng').val(eng_desc);
		window.opener.$('#title_eng').val(code);
	}
	window.close();
}
</SCRIPT>
