<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.core.ulo.common.util.FormEffects"%>
<%@page import="java.text.Normalizer.Form"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.eaf.core.ulo.common.display.ListBoxFilterInf"%>
<%@page import="com.eaf.core.ulo.common.display.ListBoxFilter"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.CardDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.LoanDataM"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<script type="text/javascript" src="orig/ulo/product/js/ProductInfoSubForm.js?v=001"></script>
<%	
	String subformId = "PRODUCT_INFO_SUBFORM";	
// 	int PERSONAL_SEQ = 1;
	Logger logger = Logger.getLogger(this.getClass());
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	if(null == applicationGroup){
		applicationGroup = new ApplicationGroupDataM();
	}
 	String displayMode = HtmlUtil.EDIT;
//  String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");	
// 	PersonalInfoDataM personalInfo = ORIGForm.getObjectForm().getPersonalInfo(PERSONAL_TYPE);
// 	if(null == personalInfo){
// 		personalInfo = new PersonalInfoDataM();
// 	}
	String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
// 	String TAG_SMART_DATA_PERSONAL = FormatUtil.getSmartDataEntryId(MConstant.Product.CREDIT_CARD,PERSONAL_SEQ);
// 	String productElementId = FormatUtil.getProductElementId(personalInfo, PRODUCT_CRADIT_CARD);
	String FIELD_ID_PRODUCT_TYPE = SystemConstant.getConstant("FIELD_ID_PRODUCT_TYPE");
	String FIELD_ID_CARD_LEVEL = SystemConstant.getConstant("FIELD_ID_CARD_LEVEL");
	String FIELD_ID_CARD_TYPE = SystemConstant.getConstant("FIELD_ID_CARD_TYPE");	 
// 	String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	String APPLICATION_CARD_TYPE_BORROWER = SystemConstant.getConstant("APPLICATION_CARD_TYPE_BORROWER");
	String createProductInfoActionJS = "onclick=debouncedCreateProductInfoActionJS(this)";
	
	FormUtil formUtil = new FormUtil("PRODUCT_FORM_INFO", subformId,request);
	FormEffects formEffects = new FormEffects(subformId,request);
	formEffects.setFormId("PRODUCT_FORM_INFO");
%>
<div class="panel panel-default">
<div class="panel-body">
<div class="row form-horizontal">
	<div class="col-sm-4">
		<div class="form-group">
			<%=HtmlUtil.getFieldLabel(request,"PRODUCTS_CARD_TYPE","col-sm-4 col-md-5 control-label")%>
<%-- 			<%=HtmlUtil.dropdown("PRODUCTS_CARD_TYPE","SELECT_MAIN_PRODUCTS_TYPE","","","",FIELD_ID_PRODUCT_TYPE,"ALL_ALL_ALL",displayMode,"","col-sm-8 col-md-7",formUtil)%> --%>
			<%=HtmlUtil.dropdown("PRODUCTS_CARD_TYPE", "SELECT_MAIN_PRODUCTS_TYPE", "PRODUCTS_CARD_TYPE", "", "", "", FIELD_ID_PRODUCT_TYPE, "ACTIVE", "","col-sm-8 col-md-7", formUtil) %>
		</div>
	</div>
	<div class="col-sm-4">
		<div class="form-group">
			<%=HtmlUtil.getFieldLabel(request,"CARD_TYPE","col-sm-3 col-md-4 control-label")%>
			<%=HtmlUtil.dropdown("CARD_TYPE","SELECT_MAIN_CARD_TYPE","","","","","ALL",displayMode,"","col-sm-9 col-md-8",formUtil)%>
		</div>
	</div>	
	<div class="col-sm-3">
		<div class="form-group">
			<%=HtmlUtil.getFieldLabel(request,"CARD_LEVEL","col-sm-3 col-md-4 control-label")%>
			<%=HtmlUtil.dropdown("CARD_LEVEL","SELECT_MAIN_CARD_LEVEL","","","","","ALL_ALL_ALL",displayMode,"","col-sm-9 col-md-8",formUtil)%>			
		</div>
	</div>	
	<div class="col-sm-1">
		<div class="form-group">
			<%=HtmlUtil.icon("ADD_CARD_INFO_BORROWER", "ADD_CARD_INFO_BORROWER", "btnsmall_add", createProductInfoActionJS, formEffects) %> 		
		</div>
	</div>	
</div>
</div>
</div>