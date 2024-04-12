<%@page import="com.eaf.orig.ulo.dm.util.DocumentManageUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@ page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.ArrayList"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.eaf.core.ulo.common.util.Util"%>
<%@ page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@ page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@ page import="org.apache.log4j.Logger"%>

<script type="text/javascript" src="orig/ulo/search/js/searchWareHouse.js"></script>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="SearchForm" scope="session" class="com.eaf.core.ulo.common.engine.SearchFormHandler"/>

<%
	Logger logger = Logger.getLogger(this.getClass());
	ArrayList<HashMap<String,Object>> results = SearchForm.getResults();
	int TOTAL = SearchForm.getCount();
	String SEARCH_BRANCH_INFO = SystemConstant.getConstant("SEARCH_BRANCH_INFO");
%>
<section class="work_area padding-top">
	<div class="row">
		<div class="col-xs-12">
			<div class="panel panel-default">
				<div class="panel-body">
					<div class="row form-horizontal">
						<div class="col-sm-4">
							<div class="form-group">
								<%=HtmlUtil.getFieldLabel(request,"DM_FIRST_NAME","col-sm-4 col-md-5 control-label")%>
								<%=HtmlUtil.textBox("DM_FIRST_NAME","",SearchForm.getString("DM_FIRST_NAME"),"","120",HtmlUtil.EDIT,"","col-sm-8 col-md-7",request)%>	
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<%=HtmlUtil.getFieldLabel(request,"DM_LAST_NAME","col-sm-4 col-md-5 control-label")%>
								<%=HtmlUtil.textBox("DM_LAST_NAME","",SearchForm.getString("DM_LAST_NAME"),"","50",HtmlUtil.EDIT,"","col-sm-8 col-md-7",request)%>	
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<%=HtmlUtil.getFieldLabel(request,"DM_IMPORTANT_DOC_NO","col-sm-4 col-md-5 control-label")%>
								<%=HtmlUtil.textBox("DM_IMPORTANT_DOC_NO","",SearchForm.getString("DM_IMPORTANT_DOC_NO"),"","20",HtmlUtil.EDIT,"","col-sm-8 col-md-7",request)%>	
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<%=HtmlUtil.getFieldLabel(request,"DM_APPLICATION_NO","col-sm-4 col-md-5 control-label")%>
								<%=HtmlUtil.textBox("DM_APPLICATION_NO","",SearchForm.getString("DM_APPLICATION_NO"),"","20",HtmlUtil.EDIT,"","col-sm-8 col-md-7",request)%>	
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<%=HtmlUtil.getFieldLabel(request,"DM_APPLICATION_STATUS","col-sm-4 col-md-5 control-label")%>
								<%=HtmlUtil.dropdown("DM_APPLICATION_STATUS","","",SearchForm.getString("DM_APPLICATION_STATUS"),"",SystemConstant.getConstant("FIELD_ID_DM_APP_STATUS"),"",HtmlUtil.EDIT,"","col-sm-8 col-md-7",request)%>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<%=HtmlUtil.getFieldLabel(request,"DM_STATUS","col-sm-4 col-md-5 control-label")%>
								<%=HtmlUtil.dropdown("DM_STATUS","","",SearchForm.getString("DM_STATUS"),"",SystemConstant.getConstant("FIELD_ID_STATUS_WM"),"",HtmlUtil.EDIT,"","col-sm-8 col-md-7",request)%>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<%=HtmlUtil.getFieldLabel(request,"DM_BRANCH_NO","col-sm-4 col-md-5 control-label")%>
								<%=HtmlUtil.search("DM_BRANCH_NO", "", SEARCH_BRANCH_INFO, SearchForm.getString("DM_BRANCH_NO"), "", "", "", "", "", "col-sm-8 col-md-7", request)%>
							</div>
						</div>
						<div class="col-sm-12">
							<div class="form-group">
								<div class="row">
									<div class="col-md-12 text-center">
									 <%=HtmlUtil.button("DM_SEARCH_BTN","DM_SEARCH_BTN",HtmlUtil.EDIT,"btn2 btn2-green","",request) %>
									 <%=HtmlUtil.button("DM_RESET_BTN","DM_RESET_BTN",HtmlUtil.EDIT,"btn2","",request) %>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
 
<div class="titlesearch">
	<%=HtmlUtil.getFieldLabel(request,"DM_TOTAL_RECORD")%><%=TOTAL%>
</div>

<div class="row padding-top">
	<div class="col-md-12">
		<table class="table table-hover table-striped table-inbox">
			<tbody>
<%if(!Util.empty(results)){
	for(HashMap<String,Object> Row:results){
%>	
			<tr onclick="loadDmWareHouse(<%=FormatUtil.display(Row,"DM_ID")%>)">
	 			<td class="" width="5%">
	 				<td class=""><img class="imginbox" src="<%=CacheControl.getName(SystemConstant.getConstant("FIELD_ID_DM_APP_STATUS"), FormatUtil.display(Row, "APPLICATION_STATUS"), "SYSTEM_ID1")%>"></td>
				<td>
					<div class="inboxitemcard bold"><%=FormatUtil.display(Row,"APPLICATION_NO")%></div>
					<div class="inboxitemcard"><%=DocumentManageUtil.displayProductDesc(FormatUtil.display(Row,"PRODUCT_NAME"))%></div>
				</td>
				<td>
					<div class="inboxitemcard bold"><%=FormatUtil.display(Row,"APPLICANT_NAME")%></div>
					<div class="inboxitemcard"><%=FormatUtil.display(Row,"ID_NO")%></div>
				</td>
				<td>
					<div class="inboxitemcard bold text-right"><%=CacheControl.getName(SystemConstant.getConstant("FIELD_ID_STATUS_WM"),FormatUtil.display(Row,"STATUS"))%></div>
 					<div class="inboxitemcard text-right"><%=FormatUtil.display(Row,"LAST_STORE_DATE")%></div>
				</td>		 
			</tr>
<%	}
 }else{ %> 
 			<tr>
				<td class="text-center"><%=LabelUtil.getText(request, "NO_RECORD_FOUND")%></td>
			</tr>
 <%} %>
 			</tbody>
 			<tfoot>
				<tr>
					<td colspan="5"></td>
				</tr>
			</tfoot>
	 	</table>
 	</div>
 </div>
 <div  class="btnbarlong">
 	<jsp:include page="/orig/ulo/util/valuelist.jsp"/>
 </div>
<%=HtmlUtil.hidden("DM_ID","")%>
