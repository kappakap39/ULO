<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.ListBoxControl"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.SpecialAdditionalServiceDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>

<script type="text/javascript" src="orig/ulo/product/js/AdditionalServiceSubForm.js"></script>
<%
	Logger logger = Logger.getLogger(this.getClass());
	String subformId = "KCC_ADDITIONAL_SERVICE_SUBFORM4";
	logger.debug("subformId >>>"+subformId);
	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");	
	String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	String SPECIAL_ADDITIONAL_SERVICE_SPENDING_ALERT = SystemConstant.getConstant("SPECIAL_ADDITIONAL_SERVICE_SPENDING_ALERT");
	String SPECIAL_ADDITIONAL_SERVICE_DUE_ALERT = SystemConstant.getConstant("SPECIAL_ADDITIONAL_SERVICE_DUE_ALERT");
	String SPECIAL_ADDITIONAL_SERVICE_POSTAL = SystemConstant.getConstant("SPECIAL_ADDITIONAL_SERVICE_POSTAL");
	String SPENDING_ALERT_FLAG ="";
	String DUE_ALERT_FLAG ="";
	String POSTAL_FLAG ="";
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
	if(null == personalInfo){
		personalInfo = new PersonalInfoDataM();
	}
	String personalId = personalInfo.getPersonalId();
	int PERSONAL_SEQ = personalInfo.getSeq();
	if(!Util.empty(applicationGroup.getSpecialAdditionalServiceLifeCycleByPersonalId(personalId, PRODUCT_CRADIT_CARD, SPECIAL_ADDITIONAL_SERVICE_SPENDING_ALERT)) ){
		SPENDING_ALERT_FLAG ="Y";
	}
	if(!Util.empty(applicationGroup.getSpecialAdditionalServiceLifeCycleByPersonalId(personalId, PRODUCT_CRADIT_CARD, SPECIAL_ADDITIONAL_SERVICE_DUE_ALERT))){
		DUE_ALERT_FLAG ="Y";
	}
	if(!Util.empty(applicationGroup.getSpecialAdditionalServiceLifeCycleByPersonalId(personalId, PRODUCT_CRADIT_CARD, SPECIAL_ADDITIONAL_SERVICE_POSTAL))){
		POSTAL_FLAG ="Y";
	}
	String displayMode = HtmlUtil.EDIT;
	
// 	String TAG_SMART_DATA_PERSONAL = FormatUtil.getSmartDataEntryId(personalInfo.getPersonalType(),PERSONAL_SEQ);
	
	String productElementId = FormatUtil.getProductElementId(personalInfo,PRODUCT_CRADIT_CARD);	
	
	String FIELD_ID_SPECIAL_ADDITIONAL_SERVICE = SystemConstant.getConstant("FIELD_ID_SPECIAL_ADDITIONAL_SERVICE");
	
	logger.debug("SPENDING_ALERT_FLAG :"+SPENDING_ALERT_FLAG);
	logger.debug("DUE_ALERT_FLAG :"+DUE_ALERT_FLAG);
	logger.debug("POSTAL_FLAG :"+POSTAL_FLAG);
	
	String SpendingAlertDetail = ListBoxControl.getName(FIELD_ID_SPECIAL_ADDITIONAL_SERVICE, SPECIAL_ADDITIONAL_SERVICE_SPENDING_ALERT, "REMARK");
	String DueAlert = ListBoxControl.getName(FIELD_ID_SPECIAL_ADDITIONAL_SERVICE, SPECIAL_ADDITIONAL_SERVICE_DUE_ALERT, "REMARK");
	String postal = ListBoxControl.getName(FIELD_ID_SPECIAL_ADDITIONAL_SERVICE, SPECIAL_ADDITIONAL_SERVICE_POSTAL, "REMARK");
	
	FormUtil formUtil = new FormUtil("PRODUCT_FORM_CC",subformId,request);	
%>
	<div class="panel panel-default" id="ADDITIONAL_SERVICE_CC_MAIN_SUBFORM">
		 <div class="panel-body">
			<div class="row form-horizontal">
				<div class="col-sm-12">
					<div class="form-group">
						<%=HtmlUtil.checkBoxInline("SERVICE_TYPE_POSTAL_FLAG", PRODUCT_CRADIT_CARD ,"SERVICE_TYPE_POSTAL_FLAG_"+productElementId, "SERVICE_TYPE_POSTAL_FLAG", 
							POSTAL_FLAG, "", MConstant.FLAG.YES, "", postal, "col-xs-12 control-label", formUtil) %>
					</div>
				</div>
			
			
				<div class="col-sm-12">
					<div class="form-group">
						<%=HtmlUtil.checkBoxInline("SERVICE_TYPE_SPENDING_ALERT", PRODUCT_CRADIT_CARD ,"SERVICE_TYPE_SPENDING_ALERT_"+productElementId, "SERVICE_TYPE_SPENDING_ALERT", 
							SPENDING_ALERT_FLAG, "", MConstant.FLAG.YES, "", SpendingAlertDetail, "col-xs-12 control-label", formUtil) %>
					</div>
				</div>
				<div class="col-sm-12">
					<div class="form-group">
						<%=HtmlUtil.checkBoxInline("SERVICE_TYPE_DUE_ALERT", PRODUCT_CRADIT_CARD ,"SERVICE_TYPE_DUE_ALERT_"+productElementId, "SERVICE_TYPE_DUE_ALERT", 
							DUE_ALERT_FLAG, "", MConstant.FLAG.YES, "", DueAlert, "col-xs-12 control-label", formUtil) %>
					</div>
				</div>
			</div>
		</div>
	</div>



