<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.core.ulo.common.properties.ListBoxControl"%>
<%@page import="com.eaf.orig.ulo.model.app.SpecialAdditionalServiceDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="EntityForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>

<script type="text/javascript" src="orig/ulo/product/js/CreditShirldSubForm.js"></script>
<%
	String subformId = "SUP_CREDIT_SHIELD_SUBFORM";
	Logger logger = Logger.getLogger(this.getClass());
	String PRODUCT_CRADIT_CARD= SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	String FIELD_ID_SPECIAL_ADDITIONAL_SERVICE = SystemConstant.getConstant("FIELD_ID_SPECIAL_ADDITIONAL_SERVICE");
	String SPECIAL_ADDITIONAL_SERVICE_CREDIT_SHIELD = SystemConstant.getConstant("SPECIAL_ADDITIONAL_SERVICE_CREDIT_SHIELD");
	String FLAG_YES = SystemConstant.getConstant("FLAG_YES");
	String CREDIT_SHIELD_FLAG="";
	
	PersonalApplicationInfoDataM personalApplicationInfo = (PersonalApplicationInfoDataM)EntityForm.getObjectForm();
	PersonalInfoDataM personalInfo = personalApplicationInfo.getPersonalInfo();
	if(null == personalInfo){
		personalInfo = new PersonalInfoDataM();
	}
	int PERSONAL_SEQ = personalInfo.getSeq();
	String PERSONAL_TYPE = personalInfo.getPersonalType();
	String personalId = personalInfo.getPersonalId();
	if(!Util.empty(personalApplicationInfo.getSpecialAdditionalServiceLifeCycleByPersonalId(personalId, PRODUCT_CRADIT_CARD, SPECIAL_ADDITIONAL_SERVICE_CREDIT_SHIELD))){
		CREDIT_SHIELD_FLAG = FLAG_YES;
	}
	logger.debug("CREDIT_SHIELD_FLAG >>>"+CREDIT_SHIELD_FLAG);
	String displayMode = HtmlUtil.EDIT;
// 	String TAG_SMART_DATA_PERSONAL = FormatUtil.getSmartDataEntryId(PERSONAL_TYPE, PERSONAL_SEQ);
	String personalElementId = FormatUtil.getPersonalElementId(personalInfo);
		
	String  SpecialAdditionalDESC = ListBoxControl.getName(FIELD_ID_SPECIAL_ADDITIONAL_SERVICE, SPECIAL_ADDITIONAL_SERVICE_CREDIT_SHIELD, "REMARK");
		
	FormUtil formUtil = new FormUtil("SUP_PRODUCT_FORM_CC",subformId,request);
%>
	<div class="panel panel-default">
		 <div class="panel-body">
			<div class="row form-horizontal">
				<div class="col-sm-12">
					<div class="form-group">
					<%=HtmlUtil.checkBoxInline("CREDIT_SHIRLD_FLAG", PRODUCT_CRADIT_CARD, "CREDIT_SHIRLD_FLAG_"+personalElementId, "CREDIT_SHIRLD_FLAG",
					 CREDIT_SHIELD_FLAG, "", MConstant.FLAG.YES,  "", "CC_SUP", "col-xs-12 control-label ", formUtil)%>
					</div>
				</div>
			</div>
		</div>
	</div>

		