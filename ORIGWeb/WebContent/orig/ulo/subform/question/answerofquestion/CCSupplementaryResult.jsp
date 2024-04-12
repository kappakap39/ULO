
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.orig.ulo.app.view.util.dih.DIHProxy"%>
<%@page import="com.eaf.core.ulo.common.engine.SQLQueryEngine"%>
<%@page import="com.eaf.orig.ulo.app.view.form.subform.product.manual.CardInfoUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.CardDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.LoanDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<%@page import="com.eaf.orig.ulo.model.ncb.NcbInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.ncb.NcbAddressDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page contentType="text/html;charset=UTF-8"%>
<jsp:useBean id="ModuleForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler"/>
<% 
	Logger logger = Logger.getLogger(this.getClass());
	String FIELD_ID_CARD_TYPE = SystemConstant.getConstant("FIELD_ID_CARD_TYPE");
	String displayMode = HtmlUtil.EDIT;
	HashMap<String,String> hSupCardCount= (HashMap<String,String>)ModuleForm.getObjectForm();
	 if(Util.empty(hSupCardCount)){
	 	hSupCardCount = new HashMap<String,String>();
	 }
%>
<div class="panel panel-default">
	<div class="panel-body">
		<div class="row form-horizontal">
				<table class="table table-bordered">
					<tbody>
					<tr class="tabletheme_header">
						<th><%=HtmlUtil.getLabel(request, "NUMBER_ABBR", "col-sm-8 col-md-12") %></th>
						<th><%=HtmlUtil.getLabel(request, "CARD_TYPE", "col-sm-8 col-md-12") %></th>
						<th><%=HtmlUtil.getLabel(request, "SUP_AMT", "col-sm-8 col-md-12") %></th>
					</tr>
						<%
						if(!Util.empty(hSupCardCount)){
							int seq =1;
							for (String CARD_GROUP : hSupCardCount.keySet()) {
								 String cardTypeDesc = CacheControl.getName(FIELD_ID_CARD_TYPE, CARD_GROUP);
								String COUNT =hSupCardCount.get(CARD_GROUP);
						%>
						<tr>
							<td><%=FormatUtil.display(String.valueOf(seq))%></td>
							<td><%=FormatUtil.display(cardTypeDesc)%></td>
							<td><%=FormatUtil.display(COUNT) %></td>
						</tr>
						<%
							seq++;
						}
					}else{%>
						<tr ><td colspan="999" style="text-align: center;"><%=LabelUtil.getText(request, "NO_RECORD_FOUND")%></td></tr>
					<%} %>
				</tbody>
			</table>
		</div>
	</div>
</div>
	
