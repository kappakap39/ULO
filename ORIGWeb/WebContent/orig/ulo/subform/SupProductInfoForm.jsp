<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.util.FormEffects"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM" />
<jsp:useBean id="EntityForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler" />
<script type="text/javascript" src="orig/ulo/product/js/SupProductInfoSubForm.js?v=001"></script>
<%
	String subformId = "SUP_PRODUCT_INFO_SUBFORM";
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	String PERSONAL_RELATION_APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
		
	Logger logger = Logger.getLogger(this.getClass());
	PersonalApplicationInfoDataM personalAppInfo = (PersonalApplicationInfoDataM)EntityForm.getObjectForm();
	PersonalInfoDataM personalInfo = personalAppInfo.getPersonalInfo();
	
	String displayMode = HtmlUtil.EDIT;
// 	String TAG_SMART_DATA_PERSONAL = FormatUtil.getSmartDataEntryId(PRODUCT_CRADIT_CARD, personalInfo.getSeq());
	String personalElementId = FormatUtil.getPersonalElementId(personalInfo);
	
	String FIELD_ID_PRODUCT_TYPE = SystemConstant.getConstant("FIELD_ID_PRODUCT_TYPE");
	String FIELD_ID_CARD_LEVEL = SystemConstant.getConstant("FIELD_ID_CARD_LEVEL");
	String FIELD_ID_CARD_TYPE = SystemConstant.getConstant("FIELD_ID_CARD_TYPE");
	String APPLICATION_CARD_TYPE_BORROWER = SystemConstant.getConstant("APPLICATION_CARD_TYPE_BORROWER");
	String createProductInfoActionJS = "onclick=debouncedCreateProductInfoActionJS(this)";
	
	FormUtil formUtil = new FormUtil("SUP_PRODUCT_FORM_INFO", subformId,request);
	FormEffects formEffects = new FormEffects(subformId,request);
	formEffects.setFormId("SUP_PRODUCT_FORM_INFO");
%>
<%=HtmlUtil.hidden("uniqueId", "")%>
<div class="panel panel-default">
	<div class="panel-body">
		<div class="row form-horizontal">
			<div class="col-sm-4">
				<div class="form-group">
					<%=HtmlUtil.getFieldLabel(request, "PRODUCTS_CARD_TYPE", "col-sm-4 col-md-5 control-label")%>
					<%=HtmlUtil.dropdown("PRODUCTS_CARD_TYPE","SELECT_SUP_PRODUCTS_TYPE","PRODUCTS_CARD_TYPE","","","",FIELD_ID_PRODUCT_TYPE,"ALL_ALL_ALL","","col-sm-8 col-md-7",formUtil)%>
				</div>
			</div>
			<div class="col-sm-4">
				<div class="form-group">
					<%=HtmlUtil.getFieldLabel(request, "CARD_TYPE", "col-sm-2 col-md-3 control-label")%>
					<%=HtmlUtil.dropdown("CARD_TYPE", "SELECT_SUP_CARD_TYPE", "CARD_TYPE", "", "", "", "ALL", "", "", "col-sm-10 col-md-9", formUtil)%>
				</div>
			</div>
			<div class="col-sm-3">
				<div class="form-group">
					<%=HtmlUtil.getFieldLabel(request, "CARD_LEVEL", "col-sm-3 col-md-4 control-label")%>
					<%=HtmlUtil.dropdown("CARD_LEVEL", "SELECT_SUP_CARD_LEVEL", "CARD_LEVEL", "", "", "", "ALL_ALL_ALL", "", "", "col-sm-9 col-md-8", formUtil)%>
				</div>
			</div>
			<div class="col-sm-1">
				<div class="form-group">
<%-- 					<%=HtmlUtil.icon("ADD_CARD_INFO_BORROWER", HtmlUtil.EDIT, "btnsmall_add", createProductInfoActionJS, request)%> --%>
					<%=HtmlUtil.icon("ADD_CARD_INFO_BORROWER", "ADD_CARD_INFO_BORROWER", "btnsmall_add", createProductInfoActionJS, formEffects) %> 	
				</div>
			</div>
		</div>
	</div>
</div>