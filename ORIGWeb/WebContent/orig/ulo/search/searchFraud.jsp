<%@page import="com.eaf.orig.ulo.control.util.ApplicationUtil"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>

<jsp:useBean id="SearchForm" scope="session" class="com.eaf.core.ulo.common.engine.SearchFormHandler"/>
<script type="text/javascript" src="orig/ulo/search/js/searchFraud.js"></script>
<%
	Logger logger = Logger.getLogger(this.getClass());
	ArrayList<HashMap<String,Object>> results = SearchForm.getResults();
	BigDecimal TOTAL = FormatUtil.toBigDecimal(String.valueOf(SearchForm.getCount()));
	String PROCESSING = SystemConstant.getConstant("PROCESSING");
	String WF_STATE_RUNNING = SystemConstant.getConstant("WF_STATE_RUNNING");
%>
<section class="work_area padding-top">
<div class="row">
<div class="col-xs-12">
	<div class="panel panel-default">
		<div class="panel-body">
			<div class="row form-horizontal">
				<div class="col-sm-4">
					<div class="form-group">
						<%=HtmlUtil.getFieldLabel(request,"FRAUD_APP_NO","col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.textBox("APPLICATION_GROUP_NO","",SearchForm.getString("APPLICATION_GROUP_NO"),"","20",HtmlUtil.EDIT,"","col-sm-8 col-md-7",request)%>
					</div>
				</div>
				<div class="col-sm-4">
					<div class="form-group">
						<%=HtmlUtil.getFieldLabel(request,"FRAUD_APP_STATUS","col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.dropdown("APPLICATION_STATUS", "", "",SearchForm.getString("APPLICATION_STATUS"), "", SystemConstant.getConstant("FIELD_ID_APPLICATION_STATUS"), "", "", "","col-sm-8 col-md-7", request)%>
					</div>
				</div>		
				<div class="clearfix"></div> 
				<div class="col-sm-4">
					<div class="form-group">
						<%=HtmlUtil.getFieldLabel(request,"FIRST_NAME_TH","col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.textBox("TH_FIRST_NAME","",SearchForm.getString("TH_FIRST_NAME"),"","120",HtmlUtil.EDIT,"","col-sm-8 col-md-7",request)%>
					</div>
				</div>
				<div class="col-sm-4">
					<div class="form-group">
						<%=HtmlUtil.getFieldLabel(request,"TH_LAST_NAME","col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.textBox("TH_LAST_NAME","",SearchForm.getString("TH_LAST_NAME"),"","50",HtmlUtil.EDIT,"","col-sm-8 col-md-7",request)%>
					</div>
				</div>	
				<div class="col-sm-4">
					<div class="form-group">
						<%=HtmlUtil.getFieldLabel(request,"ID_NO","col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.textBox("ID_NO","",SearchForm.getString("ID_NO"),"","15",HtmlUtil.EDIT,"","col-sm-8 col-md-7",request)%>
					</div>
				</div>	
				<div class="col-sm-4">
					<div class="form-group">
						<%=HtmlUtil.getFieldLabel(request,"CASH_TRANSFER_TYPE","col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.dropdown("CASH_TRANSFER_TYPE","","FR",SearchForm.getString("CASH_TRANSFER_TYPE"),"",SystemConstant.getConstant("FIELD_ID_CASH_TRANSFER_TYPE"),"",HtmlUtil.EDIT,"","col-sm-8 col-md-7",request)%>
					</div>
				</div>
				<div class="col-sm-4">
					<div class="form-group">
						<%=HtmlUtil.getFieldLabel(request,"FRAUD_PRODUCT","col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.dropdown("PRODUCT_NAME","","",SearchForm.getString("PRODUCT_NAME"),"",SystemConstant.getConstant("FIELD_ID_PRODUCT_TYPE"),"",HtmlUtil.EDIT,"","col-sm-8 col-md-7",request)%>
					</div>
				</div>	
				<div class="col-sm-4">
					<div class="form-group">
						<%=HtmlUtil.getFieldLabel(request,"FRAUD_CARD_TYPE","col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.dropdown("CARD_TYPE_ID","","",SearchForm.getString("CARD_TYPE_ID"),"",SystemConstant.getConstant("FIELD_ID_CARD_TYPE"),"",HtmlUtil.EDIT,"","col-sm-8 col-md-7",request)%>						
					</div>
				</div>	
				<div class="col-sm-12">
					<div class="form-group">
						<div class="row">
							<div class="col-md-12 text-center">
							 <%=HtmlUtil.button("FRAUD_SEARCH_BTN","FRAUD_SEARCH_BTN",HtmlUtil.EDIT,"btn2 btn2-green","",request) %>
	 						 <%=HtmlUtil.button("FRAUD_RESET_BTN","FRAUD_RESET_BTN",HtmlUtil.EDIT,"btn2","",request) %>
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
<div class="row padding-top">
	<div class="col-md-12">
		<table class="table table-hover table-striped">
			<tbody>
			<%if(!Util.empty(results)){
				for(HashMap<String,Object> Row:results) {
					String APPLICATION_GROUP_ID = FormatUtil.display(Row, "APPLICATION_GROUP_ID");
					String loadApplicationActionJS = "onclick=loadApplicationActionJS('" + APPLICATION_GROUP_ID + "')";
					String updateDateTime =FormatUtil.display(Row,"LAST_UPDATE");
					String figureTag ="";
					if(!Util.empty(updateDateTime)){
						figureTag="<img class='lastupdate' src='css/ulo/images/lastupdate.png' />";
					}%>
				<tr <%=loadApplicationActionJS%> >
					
					<td class="text_center"><img  src="<%=CacheControl.getName(SystemConstant.getConstant("FIELD_ID_PRIORITY"), FormatUtil.display(Row,"PRIORITY"), "SYSTEM_ID3") %>"></td>
					<td>
						<div class="inboxitemcard bold"><%=FormatUtil.display(ApplicationUtil.getApplicationGroupNo(Row))%></div>
						<div class="inboxitemcard"><%=FormatUtil.display(Row,"PRODUCT_NAME")%></div>
					</td>
					<td>
             			<div class="inboxitemcard bold"><%=FormatUtil.display(Row,"MAIN_CUSTOMER_NAME")%></div>
            			 <div class="inboxitemcard"><%=FormatUtil.display(Row,"SUP_CUSTOMER_NAME")%></div>
        			</td>
        			<td>
          				<div class="inboxitemcard bold"><%=FormatUtil.display(Row,"MAIN_ID_NO")%></div>
           				<div  class="inboxitemcard"><%=FormatUtil.display(Row,"SUP_ID_NO")%></div>
        			</td>
        			<td>
        				<div class="inboxitemcard bold text-right">
        				<%	if(WF_STATE_RUNNING.equals(Row.get("WF_STATE"))){ %>
        					<%=FormatUtil.displayText(PROCESSING) %>	
        				<%	}else{ %>
        					<%=CacheControl.getName(SystemConstant.getConstant("FIELD_ID_APPLICATION_STATUS"), FormatUtil.display(Row,"APPLICATION_STATUS")) %>
        				<%	} %>
        				</div>
        				<div class="inboxitemcard text-right"><%=figureTag %><span><%=updateDateTime%></span></div>
        			</td>
				</tr>
			<%}}else{%>
				<tr>
					<td colspan="5" align="center"><%=LabelUtil.getText(request, "NO_RECORD_FOUND")%></td>
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
<section>
	<div class="row">
		<div class="col-md-12">
		<jsp:include page="/orig/ulo/util/valuelist.jsp"/>
		</div>
	</div>
</section>
<%=HtmlUtil.hidden("APPLICATION_GROUP_ID", "")%>
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