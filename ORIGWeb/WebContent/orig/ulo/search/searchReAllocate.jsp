<%@page import="com.eaf.orig.ulo.control.util.ApplicationUtil"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.apache.log4j.Logger"%>
<jsp:useBean id="SearchForm" scope="session" class="com.eaf.core.ulo.common.engine.SearchFormHandler"/>
<script type="text/javascript" src="orig/ulo/search/js/searchReAllocate.js"></script>
<%
	Logger logger = Logger.getLogger(this.getClass());
	ArrayList<HashMap<String,Object>> results = SearchForm.getResults();
	BigDecimal TOTAL = FormatUtil.toBigDecimal(String.valueOf(SearchForm.getCount()));
	String mode = HtmlUtil.EDIT;
	String CONFIG_ID_ROLE = SystemConstant.getConstant("CONFIG_ID_FILTER_ROLE");
	String CONFIG_ID_FILTER_USER = SystemConstant.getConstant("CONFIG_ID_FILTER_USER");
	String APPLICANT_INFO_TH_FULL_NAME = SystemConstant.getConstant("APPLICANT_INFO_TH_FULL_NAME");
	String APPLICANT_INFO_IDNO = SystemConstant.getConstant("APPLICANT_INFO_IDNO");
	
	String STATUS_APP = "Y";
%>
<section class="work_area padding-top">
<div class="row">
<div class="col-xs-12">
	<div class="panel panel-default">
		<div class="panel-body">
			<div class="row form-horizontal">
				<div class="col-sm-4">
					<div class="form-group">
						<%=HtmlUtil.getFieldLabel(request, "APPLICATION_NO", "col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.textBox("APPLICATION_GROUP_NO","",SearchForm.getString("APPLICATION_GROUP_NO"),"","20",mode,"","col-sm-8 col-md-7",request)%> 				
					</div>
				</div>
				<%-- <div class="col-sm-4">
					<div class="form-group">
						<%=HtmlUtil.getFieldLabel(request, "PRIORITY", "col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.dropdown("PRIORITY","","",SearchForm.getString("PRIORITY"),"",SystemConstant.getConstant("FIELD_ID_PRIORITY"),"",mode,"","col-sm-8 col-md-7",request)%>					
					</div>
				</div> --%>		
				<%-- <div class="col-sm-4">
					<div class="form-group">
						<%=HtmlUtil.getFieldLabel(request, "PRODUCTS_CARD_TYPE", "col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.dropdown("PRODUCT", "", "", SearchForm.getString("PRODUCT"), "", SystemConstant.getConstant("FIELD_ID_PRODUCT_TYPE"), "", mode, "", "col-sm-8 col-md-7", request)%>					
					</div>
				</div> --%>
				<%-- <div class="col-sm-4">
					<div class="form-group">
						<%=HtmlUtil.getFieldLabel(request, "FIRST_NAME", "col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.textBox("FIRST_NAME","",SearchForm.getString("FIRST_NAME"),"","120",mode,"","col-sm-8 col-md-7",request)%>					
					</div>
				</div> --%>	
				<%-- <div class="col-sm-4">
					<div class="form-group">
						<%=HtmlUtil.getFieldLabel(request, "LAST_NAME", "col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.textBox("LAST_NAME","",SearchForm.getString("LAST_NAME"),"","50",mode,"","col-sm-8 col-md-7",request)%>					
					</div>
				</div> --%>	
				<%-- <div class="col-sm-4">
					<div class="form-group">
						<%=HtmlUtil.getFieldLabel(request, "ID_NO", "col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.textBox("ID_NO", "",SearchForm.getString("ID_NO"), "", "20", mode, "", "col-sm-8 col-md-7", request)%>					
					</div>
				</div> --%>
				<div class="col-sm-4">
					<div class="form-group">
						<%=HtmlUtil.getFieldLabel(request, "ROLE", "col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.dropdown("ROLE", "", CONFIG_ID_ROLE,SearchForm.getString("ROLE"),"",SystemConstant.getConstant("FIELD_ID_ROLE"),"", mode, "","col-sm-8 col-md-7", request)%>					
					</div>
				</div>	
				<div class="col-sm-4">
					<div class="form-group">
						<%=HtmlUtil.getFieldLabel(request, "STAFF_NAME", "col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.dropdown("STAFF_NAME","",CONFIG_ID_FILTER_USER,SearchForm.getString("STAFF_NAME"),"",SystemConstant.getConstant("CACH_NAME_EMPLOYEE"),"",mode,"","col-sm-8 col-md-7",request)%>										
					</div>
				</div>		
				<%-- <div class="col-sm-4">
					<div class="form-group">
						<%=HtmlUtil.getFieldLabel(request, "APPLICATION_TYPE", "col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.dropdown("APPLICATION_TYPE", "", "", SearchForm.getString("APPLICATION_TYPE"), "",SystemConstant.getConstant("FIELD_ID_APPLICATION_TYPE"), "", mode, "", "col-sm-8 col-md-7", request)%>									
					</div>
				</div> --%>	
				<div class="col-sm-12">
					<div class="form-group">
						<div class="row">
							<div class="col-md-12 text-center">
							<%=HtmlUtil.button("SEARCH_BTN","SEARCH_BTN",HtmlUtil.EDIT,"btn2 btn2-green","",request) %>
							<%=HtmlUtil.button("RESET_BTN","RESET_BTN",HtmlUtil.EDIT,"btn2","",request) %>
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
<div class="row padding-top">
	<div class="col-md-12">
		<div class="titlesearch">
			<%=HtmlUtil.getFieldLabel(request, "TOTAL_RECORD")%><%=FormatUtil.display(TOTAL,FormatUtil.Format.NUMBER_FORMAT)%>
		</div>
	</div>
</div>
<div class=" tabletheme">
		<table class="table table-search-border table-hover table-striped">
			<tr class="tabletheme_header">
				<th><%=HtmlUtil.checkBox("WF_CHECKBOX","ALL", "", "ALL", mode, HtmlUtil.elementTagId("WF_CHECKBOX"),"", request) %></th>
				<th class="text_center" colspan="2"><%=LabelUtil.getText(request, "APPLICATION_NO") %></th>
				<th class="text_center"><%=LabelUtil.getText(request, "APPLICATION_STATUS") %></th>
				<th class="text_center"><%=LabelUtil.getText(request, "FIRST_NAME") %> - <%=LabelUtil.getText(request, "LAST_NAME")%></th>
				<th class="text_center"><%=LabelUtil.getText(request, "ID_NO") %></th>
				<th class="text_center"><%=LabelUtil.getText(request, "PRODUCT") %></th>
				<th class="text_center"><%=LabelUtil.getText(request, "UPDATE_DATE_TIME") %></th>
				<th class="text_center" colspan="2"><%=LabelUtil.getText(request, "TASK_OWNER") %></th>
			</tr>
<%	if(!Util.empty(results)){
		for(HashMap<String,Object> Row :results){
			String claimFlag =FormatUtil.display(Row,"CLAIM_FLAG");
			String ROW_NAME =CacheControl.getName(SystemConstant.getConstant("FILED_ID_JOB_STATE"), FormatUtil.display(Row, "JOB_STATE"), "SYSTEM_ID1");
			String isDisabledCheckBox="";
			String imgReAssign ="<img class='imginbox' style='width: 24px;' src='images/ulo/ReAssign.png'></img>";
			if(!MConstant.FLAG_N.equals(claimFlag)){
				isDisabledCheckBox ="disabled";				
				imgReAssign ="<img class='imginbox' style='width: 24px;' src='images/ulo/UnReAssign.jpg'></img>";
				STATUS_APP = "N";
			}
				String checkBoxValue="APPLICATION_GROUP_ID="+FormatUtil.display(Row,"APPLICATION_GROUP_ID")
								+",INSTANT_ID="+FormatUtil.display(Row,"INSTANT_ID")
								+",ROW_NAME="+ROW_NAME
								+",TASK_ID="+FormatUtil.display(Row,"TASK_ID");%>
			<tr>
				<td><%=HtmlUtil.checkBox("WF_CHECKBOX",FormatUtil.display(Row,"APPLICATION_GROUP_ID"), "",checkBoxValue, mode, HtmlUtil.elementTagId("WF_CHECKBOX")+isDisabledCheckBox,"", request) %></td>
				<td><img  src="<%=CacheControl.getName(SystemConstant.getConstant("FIELD_ID_PRIORITY"), FormatUtil.display(Row,"PRIORITY"), "SYSTEM_ID3") %>"></td>
      			<td><%=FormatUtil.display(ApplicationUtil.getApplicationGroupNo(Row)) %></td>
				<td><%=CacheControl.getName(SystemConstant.getConstant("FIELD_ID_APPLICATION_STATUS"), FormatUtil.display(Row,"APPLICATION_STATUS")) %></td>
				<td>
					<%= FormatUtil.displayApplicantInfo(Row, "PERSONAL_APPLICANT_LIST", APPLICANT_INFO_TH_FULL_NAME, "", "") %>
					<%= FormatUtil.displayApplicantInfo(Row, "PERSONAL_SUPPLEMENTARY_LIST", APPLICANT_INFO_TH_FULL_NAME, "<br>", "") %>
				</td>
				<td>
					<%= FormatUtil.displayApplicantInfo(Row, "PERSONAL_APPLICANT_LIST", APPLICANT_INFO_IDNO, "", "") %>
					<%= FormatUtil.displayApplicantInfo(Row, "PERSONAL_SUPPLEMENTARY_LIST", APPLICANT_INFO_IDNO, "<br>", "") %>
				</td>
				<td><%=FormatUtil.display(Row,"PRODUCT_NAME")%></td>
				<td><%=FormatUtil.display(Row,"LAST_UPDATE")%></td>
				<td class="text_center"><%=ROW_NAME%>
				&nbsp;-&nbsp;<%=CacheControl.getName("User", FormatUtil.display(Row,"OWNER"))%></td>
				<td style="text-align: left;"><%=imgReAssign%></td>
			</tr>
<%		}
	}else{ %>
		<tr><td colspan="10" style="text-align: center;"><%=LabelUtil.getText(request, "NO_RECORD_FOUND")%></td></tr>
	<%} %>
		</table>
</div>
<div class="div-footer">
	<jsp:include page="/orig/ulo/util/valuelist.jsp"/>
</div>
<div class="groupList clear groupList_search col-sm-12" style="margin : 0px !important;">
	<%=HtmlUtil.getFieldLabel(request,"SEND_RETURN_CENTRAL_QUEUE")%>
	<%=HtmlUtil.button("RE_ALLOCATE_BTN","RE_ALLOCATE",HtmlUtil.EDIT,"","",request) %>
	<%=HtmlUtil.hidden("STATUS_APP", STATUS_APP)%>
</div>
<script type="text/javascript">
	$(function(){
	var rx = /INPUT|SELECT/i;
	$(document).bind("keydown keypress keyup",function(e){
		if(e.which == 13){
			if(rx.test(e.target.tagName) || e.target.disabled || e.target.readOnly){
				e.preventDefault();
			}
		}
	}); 
	});
</script> 