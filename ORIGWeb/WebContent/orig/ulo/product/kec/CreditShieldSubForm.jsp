<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.ListBoxControl"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.SpecialAdditionalServiceDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>

<script type="text/javascript" src="orig/ulo/product/js/CreditShirldSubForm.js"></script>
<%
	String subformId = "KEC_CREDIT_SHIELD_SUBFORM";
	Logger logger = Logger.getLogger(this.getClass());
	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String PRODUCT_K_EXPRESS_CASH= SystemConstant.getConstant("PRODUCT_K_EXPRESS_CASH");
	String FIELD_ID_SPECIAL_ADDITIONAL_SERVICE = SystemConstant.getConstant("FIELD_ID_SPECIAL_ADDITIONAL_SERVICE");
	String SPECIAL_ADDITIONAL_SERVICE_CREDIT_SHIELD = SystemConstant.getConstant("SPECIAL_ADDITIONAL_SERVICE_CREDIT_SHIELD");
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	String FLAG_YES = SystemConstant.getConstant("FLAG_YES");
	String CREDIT_SHIELD_FLAG="";
	PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
	if(Util.empty(personalInfo)){
		personalInfo = new PersonalInfoDataM();
	}
	int PERSONAL_SEQ = personalInfo.getSeq();
	String personalId = personalInfo.getPersonalId();
 	if(!Util.empty(applicationGroup.getSpecialAdditionalServiceLifeCycleByPersonalId(personalId, PRODUCT_K_EXPRESS_CASH, SPECIAL_ADDITIONAL_SERVICE_CREDIT_SHIELD))){
		CREDIT_SHIELD_FLAG = FLAG_YES;
	}
	
	String displayMode = HtmlUtil.EDIT;
// 	String TAG_SMART_DATA_PERSONAL = FormatUtil.getSmartDataEntryId(personalInfo.getPersonalType(),PERSONAL_SEQ);
	String productElementId = FormatUtil.getProductElementId(personalInfo,PRODUCT_K_EXPRESS_CASH);
	String  SpecialAdditionalDESC = ListBoxControl.getName(FIELD_ID_SPECIAL_ADDITIONAL_SERVICE, SPECIAL_ADDITIONAL_SERVICE_CREDIT_SHIELD, "REMARK");
		
	FormUtil formUtil = new FormUtil("PRODUCT_FORM_KEC",subformId,request);
%>
	<div class="panel panel-default">
		 <div class="panel-body">
			<div class="row form-horizontal">
				<div class="col-sm-12">
					<div class="form-group">
					<%=HtmlUtil.checkBoxInline("CREDIT_SHIRLD_FLAG",PRODUCT_K_EXPRESS_CASH, "CREDIT_SHIRLD_FLAG_"+productElementId, "CREDIT_SHIRLD_FLAG", 
					CREDIT_SHIELD_FLAG, "", MConstant.FLAG.YES, "", "CONFIRM_KEC", "col-xs-12 control-label", formUtil) %>
					</div>
				</div>
			</div>
		</div>
	</div>



