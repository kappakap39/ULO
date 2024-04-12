<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.util.FormEffects"%>
<%
	String subformId = request.getParameter("subformId");
	String FIELD_ID_PRODUCT_TYPE = SystemConstant.getConstant("FIELD_ID_PRODUCT_TYPE");
	FormUtil formUtil = new FormUtil(subformId,request);
	FormEffects formEffect = new FormEffects(subformId,request);
 %>
<div class="panel panel-default">
	<div class="panel-body">
		<div class="row form-horizontal">
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getFieldLabel(request, "PRODUCTS_CARD_TYPE", "col-sm-4 col-md-5 control-label")%>
<%-- 					<%=HtmlUtil.dropdown("PRODUCTS_CARD_TYPE", "", "", "", "", FIELD_ID_PRODUCT_TYPE, "ALL", displayMode, "", --%>
<%-- 					"col-sm-8 col-md-7", request)%> --%>
					<%=HtmlUtil.dropdown("PRODUCTS_CARD_TYPE", "SELECT_MAIN_PRODUCTS_TYPE", "PRODUCTS_CARD_TYPE", "",
					 "", "", FIELD_ID_PRODUCT_TYPE, "ACTIVE", "","col-sm-8 col-md-7", formUtil) %>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getFieldLabel(request, "PROJECT_CODE_DESC", "col-sm-4 control-label")%>
					<%=HtmlUtil.textBox("PROJECT_CODE", "SELECT_MAIN_PROJECT_CODE", "PROJECT_CODE_DESC", 
					null, "", "4", "", "col-sm-6", formUtil)%>
				</div>
			</div>
		</div>
		<div class="row form-horizontal">
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getFieldLabel(request, "CARD_TYPE", "col-sm-4 col-md-5 control-label")%>
					<%=HtmlUtil.dropdown("CARD_TYPE", "SELECT_MAIN_CARD_TYPE", "CARD_TYPE", "",
					 "", "", "", "ALL", "","col-sm-8 col-md-7", formUtil) %>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getFieldLabel(request, "CARD_LEVEL", "col-sm-4 control-label")%>
					<%=HtmlUtil.dropdown("CARD_LEVEL", "SELECT_MAIN_CARD_LEVEL", "CARD_LEVEL", "",
					 "", "", "", "ALL_ALL_ALL", "","col-sm-6", formUtil) %>
<%-- 					<%=HtmlUtil.icon("ADD_CARD_INFO_BORROWER", HtmlUtil.EDIT, "btnsmall_add", "onclick=createProductInfoActionJS()", request)%> --%>
					<%=HtmlUtil.button("ADD_CARD_INFO_BORROWER","ADD_CARD_INFO_BORROWER","","btnsmall_add","onclick=createProductInfoActionJS()",formEffect)%>
				</div>
			</div>
		</div>
	</div>
</div>
