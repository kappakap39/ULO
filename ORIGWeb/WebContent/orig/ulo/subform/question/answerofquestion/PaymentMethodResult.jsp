
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.ListBoxControl"%>
<%@page import="com.eaf.orig.ulo.model.dih.VcCardDataM"%>
<%@page import="com.eaf.orig.ulo.app.view.util.dih.DIHProxy"%>
<%@page import="com.eaf.core.ulo.common.engine.SQLQueryEngine"%>
<%@page import="com.eaf.orig.ulo.app.view.form.subform.product.manual.CardInfoUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.CardDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.LoanDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PaymentMethodDataM"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.orig.ulo.model.ncb.NcbInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.ncb.NcbAddressDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page contentType="text/html;charset=UTF-8"%>
<jsp:useBean id="ModuleForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler"/>
<% 
	Logger logger = Logger.getLogger(this.getClass());
	String displayMode = HtmlUtil.EDIT;
	String PRODUCT_CRADIT_CARD =SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	String FIELD_ID_CARD_TYPE =SystemConstant.getConstant("FIELD_ID_CARD_TYPE");
	String FIELD_ID_PAYMENT_METHOD = SystemConstant.getConstant("FIELD_ID_PAYMENT_METHOD");
	 ArrayList<VcCardDataM>  vcCardDataList = (ArrayList<VcCardDataM>)ModuleForm.getObjectForm();
	 if(Util.empty(vcCardDataList)){
	 	vcCardDataList = new ArrayList<VcCardDataM>();
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
						<th><%=HtmlUtil.getLabel(request, "PAYMENT_METHOD", "col-sm-8 col-md-12") %></th>
					</tr>
				<%
				if(!Util.empty(vcCardDataList)){	
				int seq =1;				
				for(VcCardDataM vcCardData : vcCardDataList){
					int CARD_TYPE = vcCardData.getCARD_TP();
					String PAYMENT_METHOD_DESC = ListBoxControl.getName(FIELD_ID_PAYMENT_METHOD,"SYSTEM_ID2",vcCardData.getACH_F(),"DISPLAY_NAME");%>
						<tr>
							<td><%=FormatUtil.display(String.valueOf(seq))%></td>
							<td><%=FormatUtil.display(CARD_TYPE)%></td>
							<td><%=PAYMENT_METHOD_DESC%></td>
						</tr>
				<%	seq++;					
					}
				}else{%>
						<tr ><td colspan="999" style="text-align: center;"><%=LabelUtil.getText(request, "NO_RECORD_FOUND")%></td></tr>
						<%} %>
					</tbody>
				</table>
			</div>
		</div>
	</div>
