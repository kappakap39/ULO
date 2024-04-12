
<%@page import="java.math.BigDecimal"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.orig.ulo.model.dih.VcCardDataM"%>
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
						<th><%=HtmlUtil.getLabel(request, "REQUEST_LOAN_AMT", "col-sm-8 col-md-12") %></th>
					</tr>
						<%
						if(!Util.empty(vcCardDataList)){
							int seq =1;
							for(VcCardDataM vcCardData : vcCardDataList){
								int cardTypeCode = vcCardData.getCARD_TP();
								String cardTempLimitAMT_Str = FormatUtil.toString(vcCardData.getTEMP_CR_LMT_AMT());
								BigDecimal CARD_TEMP_LIMIT_AMT = FormatUtil.toBigDecimal(cardTempLimitAMT_Str); %>
								<tr>
									<td><%=FormatUtil.display(String.valueOf(seq))%></td>
									<td><%=FormatUtil.display(cardTypeCode)%></td>
									<td><%=FormatUtil.display(CARD_TEMP_LIMIT_AMT,FormatUtil.Format.CURRENCY_FORMAT) %></td>
								</tr>
								<% seq++;
							}
						}else{%>
							<tr ><td colspan="999" style="text-align: center;"><%=LabelUtil.getText(request, "NO_RECORD_FOUND")%></td></tr>
						<%} %>
					</tbody>
				</table>
			</div>
		</div>
	</div>
