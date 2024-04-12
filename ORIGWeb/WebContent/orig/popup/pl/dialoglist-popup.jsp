<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.apache.log4j.Logger"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil"%>
<%@ page import="com.eaf.orig.shared.valuelist.view.webaction.ValueListWebAction"%>
<%@ page import="com.eaf.orig.shared.model.ValueListM"%>
<%@ page import = "java.util.Vector"%>
<%@ page import = "java.util.HashMap"%>  
<%@ page import = "com.eaf.j2ee.system.LoadXML"%>
<%
	Logger logger = Logger.getLogger(this.getClass());
	ValueListM valueListM = (ValueListM)request.getSession().getAttribute("DIALOG_LIST");
   	if (valueListM == null) {
	    valueListM = new  ValueListM();
	}
	Vector resultVt = valueListM.getResult();  
	if(null == resultVt){
		resultVt=new Vector();
	}
	String code = (String)request.getSession(true).getAttribute(ValueListWebAction.VALUE_LIST_SEARCH_CODE);	
%>
<div id="div-dialoglist-popup">
<table cellpadding="0" cellspacing="0" width="100%" border="0">
	<tr>
		<td align="left" width="40%">
			<input type="text" name="code" id="code" class="textbox" maxlength="100" value="<%=HTMLRenderUtil.displayHTML(code)%>" size="10">
			<input type="button" name="button-search-code" id="button-search-code" class="button" value="<%=MessageResourceUtil.getTextDescription(request, "SEARCH") %>">	
		</td>
		<td class="inputL">
			<%if((null != resultVt) && (resultVt.size() > 1)){ %>
				<%
					HashMap pagePerPageMap = LoadXML.getPagePerPageMap();
					HashMap itemPerPageMap = LoadXML.getItemPerPageMap();
					int pagePerPage = (pagePerPageMap != null && pagePerPageMap.get("1") != null)?Integer.parseInt((String)pagePerPageMap.get("1")):1;
					int allItemCount = valueListM.getCount();
					int itemsPerPage = (null != itemPerPageMap)?Integer.parseInt((String)itemPerPageMap.get("1")):20;
					int pageCount = ((allItemCount%itemsPerPage)==0)?(allItemCount/itemsPerPage):(allItemCount/itemsPerPage)+1;
					int periodCount = ((pageCount%pagePerPage)==0)?(pageCount/pagePerPage):(pageCount/pagePerPage)+1;
					int atPage = valueListM.getAtPage();
					int atPeriod = ((atPage%pagePerPage)==0)?(atPage/pagePerPage):((atPage/pagePerPage)+1);
					int beginPage = ((atPeriod-1)*pagePerPage)+1;
					int endPage = ((beginPage+pagePerPage-1)>pageCount)?pageCount:(beginPage+pagePerPage-1);
				 %>
					<%=MessageResourceUtil.getTextDescription(request, "PAGE") %> : <%=atPage%> / <%=pageCount%> &nbsp; 
					<%if(atPeriod!=1){%> 
						&nbsp;<a href="javascript:clickPageList('<%=(beginPage-1)%>')" class="valuelist-normal">&lt;&lt;</a>&nbsp;&nbsp;
					<%}%> 
					<%for(int i=beginPage;i<=endPage;i++){%> 
						<%if(i!=atPage){%>
							&nbsp;<div class="valuelist-normal">
									<a href="javascript:clickPageList('<%=i%>')" class="valuelist-normal"><%=i%></a>
								  </div>&nbsp;
						<%}else{%> 
							&nbsp;<div class="valuelist-normal-active"><%=i%></div>&nbsp; 
						<%}%> 
					<%}%> 
					<%if(atPeriod!=periodCount){%> 
						&nbsp;&nbsp;<a href="javascript:clickPageList('<%=(beginPage+pagePerPage)%>')" class="valuelist-normal">&gt;&gt;</a>&nbsp;&nbsp;
					<%}%> 
					&nbsp;&nbsp;&nbsp;&nbsp;
					<select class="itemsPerPage" name="itemsPerPage" id="itemsPerPage">
						<option selected value='<%=itemsPerPage%>'><%=itemsPerPage%></option>
					</select> 									 
			<%}%>			
		</td>
	</tr>
</table>
<%=HTMLRenderUtil.displayHiddenField("", "atPage") %>
<%=HTMLRenderUtil.displayHiddenField(valueListM.getSearchAction(), "searchAction") %>
<%=HTMLRenderUtil.displayHiddenField(valueListM.getCacheName(), "cacheName") %>
<%=HTMLRenderUtil.displayHiddenField(valueListM.getTextboxCode(), "textbox_code") %>
<%=HTMLRenderUtil.displayHiddenField(valueListM.getTextboxDesc(), "textbox_desc") %>
</div>
