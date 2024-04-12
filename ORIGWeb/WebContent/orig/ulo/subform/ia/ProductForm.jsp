<%@page import="com.eaf.core.ulo.common.util.SessionControl"%>
<%@page import="com.eaf.orig.ulo.model.control.FlowControlDataM"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlProperties"%>
<%@page import="com.eaf.core.ulo.common.properties.ImplementControl"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.properties.FormControl"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.core.ulo.common.display.ElementInf"%>
<%@page import="com.eaf.core.ulo.common.util.FormEffects"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.app.view.util.kpl.KPLUtil"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<script type="text/javascript" src="orig/ulo/subform/ia/js/ProductFormIA.js?v=1"></script>
<%
	String subformId = "IA_PRODUCT_FORM";
	Logger logger = Logger.getLogger(this.getClass());
	FlowControlDataM flowControl = (FlowControlDataM) request.getSession().getAttribute(SessionControl.FlowControl);
	String FIELD_ID_PRODUCT_TYPE = SystemConstant.getConstant("FIELD_ID_PRODUCT_TYPE");
	String roleId = ORIGForm.getRoleId();
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();	
	FormUtil formUtil = new FormUtil(subformId,request);
	String displayMode = HtmlUtil.EDIT;
	String ACTION_TYPE = flowControl.getActionType();
	FormEffects formEffect = new FormEffects(subformId,request);

	Boolean isKPL = KPLUtil.isKPL(applicationGroup);

	ArrayList<PersonalInfoDataM> personals = applicationGroup.getPersonalInfos();
%>
<%=HtmlUtil.hidden("isKPL", String.valueOf(isKPL))%>
<%
ArrayList<ElementInf> elements = ImplementControl.getElements(MConstant.IMPLEMENT_TYPE.PRODUCT_FORM);
for(ElementInf elementInf:elements){
	elementInf.setObjectRequest(applicationGroup);
		elementInf.writeElement(pageContext,subformId);
}
 %>
<!-- <div class="panel panel-default"> -->
<!-- 	<div class="panel-body"> -->
<!-- 		<div class="row form-horizontal"> -->
<!-- 			<div class="col-sm-6"> -->
<!-- 				<div class="form-group"> -->
<%-- 					<%=HtmlUtil.getFieldLabel(request, "PRODUCTS_CARD_TYPE", "col-sm-4 col-md-5 control-label")%> --%>
<%-- 					<%=HtmlUtil.dropdown("PRODUCTS_CARD_TYPE", "", "", "", "", FIELD_ID_PRODUCT_TYPE, "ALL", displayMode, "", --%>
<%-- 					"col-sm-8 col-md-7", request)%> --%>
<%-- 					<%=HtmlUtil.dropdown("PRODUCTS_CARD_TYPE", "SELECT_PRODUCTS_CARD_TYPE_B", "PRODUCTS_CARD_TYPE", "", --%>
<%-- 					 "", "", FIELD_ID_PRODUCT_TYPE, "ALL", "","col-sm-8 col-md-7", formUtil) %> --%>
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 			<div class="col-sm-6"> -->
<!-- 				<div class="form-group"> -->
<%-- 					<%=HtmlUtil.getFieldLabel(request, "PROJECT_CODE_DESC", "col-sm-4 control-label")%> --%>
<%-- 					<%=HtmlUtil.textBox("PROJECT_CODE", "SELECT_PROJECT_CODE_B", "PROJECT_CODE_DESC",  --%>
<%-- 					null, "", "4", "", "col-sm-6", formUtil)%> --%>
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 		<div class="row form-horizontal"> -->
<!-- 			<div class="col-sm-6"> -->
<!-- 				<div class="form-group"> -->
<%-- 					<%=HtmlUtil.getFieldLabel(request, "CARD_TYPE", "col-sm-4 col-md-5 control-label")%> --%>
<%-- 					<%=HtmlUtil.dropdown("CARD_TYPE", "SELECT_CARD_TYPE_B", "CARD_TYPE", "", --%>
<%-- 					 "", "", "", "ALL", "","col-sm-8 col-md-7", formUtil) %> --%>
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 			<div class="col-sm-6"> -->
<!-- 				<div class="form-group"> -->
<%-- 					<%=HtmlUtil.getFieldLabel(request, "CARD_LEVEL", "col-sm-4 control-label")%> --%>
<%-- 					<%=HtmlUtil.dropdown("CARD_LEVEL", "SELECT_CARD_LEVEL_B", "CARD_LEVEL", "", --%>
<%-- 					 "", "", "", "ALL_ALL_ALL", "","col-sm-6", formUtil) %> --%>
<%-- 					<%=HtmlUtil.icon("ADD_CARD_INFO_BORROWER", HtmlUtil.EDIT, "btnsmall_add", "onclick=createProductInfoActionJS()", request)%> --%>
<%-- 					<%=HtmlUtil.button("ADD_CARD_INFO_BORROWER","ADD_CARD_INFO_BORROWER","","btnsmall_add","onclick=createProductInfoActionJS()",formEffect)%> --%>
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 	</div> -->
<!-- </div> -->
<%
ArrayList<String> products = new ArrayList<String>();
if(SystemConstant.lookup("ACTION_TYPE_ENQUIRY",ACTION_TYPE)){
			products=applicationGroup.getDisplayEnquiryProducts(SystemConstant.getArrayConstant("DISPLAY_PRODUCT"));
}else{
	products = applicationGroup.getProducts(SystemConstant.getArrayConstant("DISPLAY_PRODUCT"));
}

if(!Util.empty(products)){
%>
<table class="table table-hover">
<tr style="display: none">
	<th style="width: 44px"></th>
	<th style="width: 20%"></th>
	<th style="width: 35%"></th>
	<th></th>
	<th style="width: 25%"></th>
	<th style="width: 18%"></th>
</tr>
<%
	for(String product:products){
		ElementInf element = ImplementControl.getElement("PRODUCT_DISPLAY", product);
		element.writeElement(pageContext, null);
	}
%>
</table>
<% } else {
%>
<div class="panel panel-default">
	<table class="table table-striped">
		<tr>
	 		<td align="center" colspan="999"><%=LabelUtil.getText(request,"NO_RECORD_FOUND")%></td>
	 	</tr>
	</table>
</div>
<% } %>

<div class="row">
	<div class="col-md-12 text-right">
		<%=HtmlUtil.button("BTN_EXECUTE","BTN_EXECUTE","","btn button green","",request) %>
<%-- 		<%=HtmlUtil.button(new HtmlProperties().setName("EXECUTE_BTN").setLabelId("EXECUTE_BTN").setCssClass("btn").setRequest(request)) %> --%>
	</div>
</div>
