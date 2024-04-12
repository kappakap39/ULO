<%@page import="com.eaf.core.ulo.common.properties.ListBoxControl"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.SpecialAdditionalServiceDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<jsp:useBean id="EntityForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler"/>
<script type="text/javascript" src="orig/ulo/subform/sup/js/AdditionalServiceSubForm.js"></script>
<%
	Logger logger = Logger.getLogger(this.getClass());
// 	String subformId = "SUP_MAIN_ADDITIONAL_SERVICE_SUBFORM";
	String subformId = "SUP_ADDITIONAL_SERVICE_SUBFORM";
	logger.debug("subformId >>>"+subformId);
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	String PRODUCT_CRADIT_CARD= SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	String SPECIAL_ADDITIONAL_SERVICE_SPENDING_ALERT = SystemConstant.getConstant("SPECIAL_ADDITIONAL_SERVICE_SPENDING_ALERT");
	String SPECIAL_ADDITIONAL_SERVICE_DUE_ALERT = SystemConstant.getConstant("SPECIAL_ADDITIONAL_SERVICE_DUE_ALERT");
	String FIELD_ID_SPECIAL_ADDITIONAL_SERVICE = SystemConstant.getConstant("FIELD_ID_SPECIAL_ADDITIONAL_SERVICE");
	String FLAG_YES = SystemConstant.getConstant("FLAG_YES");
	String SERVICE_SPENDING_ALERT_FLAG="";
	String SERVICE_DUE_ALERT_FLAG="";
	
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY);
	if(null == personalInfo){
		personalInfo = new PersonalInfoDataM();
	}
	String personalId = personalInfo.getPersonalId();
	int PERSONAL_SEQ = personalInfo.getSeq();
		
	if(!Util.empty(applicationGroup.getSpecialAdditionalServiceLifeCycleByPersonalId(personalId, PRODUCT_CRADIT_CARD, SPECIAL_ADDITIONAL_SERVICE_SPENDING_ALERT))){
		SERVICE_SPENDING_ALERT_FLAG = FLAG_YES;
	}
	logger.debug("SERVICE_SPENDING_ALERT_FLAG >>>"+SERVICE_SPENDING_ALERT_FLAG);
	
	if(!Util.empty(applicationGroup.getSpecialAdditionalServiceLifeCycleByPersonalId(personalId, PRODUCT_CRADIT_CARD, SPECIAL_ADDITIONAL_SERVICE_DUE_ALERT))){
		SERVICE_DUE_ALERT_FLAG = FLAG_YES;
	}
	logger.debug("SERVICE_DUE_ALERT_FLAG >>>"+SERVICE_DUE_ALERT_FLAG);
	
	String SpendingAlertDetail = ListBoxControl.getName(FIELD_ID_SPECIAL_ADDITIONAL_SERVICE, SPECIAL_ADDITIONAL_SERVICE_SPENDING_ALERT, "REMARK");
	String DueAlert = ListBoxControl.getName(FIELD_ID_SPECIAL_ADDITIONAL_SERVICE, SPECIAL_ADDITIONAL_SERVICE_DUE_ALERT, "REMARK");
	
	String displayMode = HtmlUtil.EDIT;
	String productElementId = FormatUtil.getProductElementId(personalInfo,PRODUCT_CRADIT_CARD);	
	FormUtil formUtil = new FormUtil("SUP_CARD_SEPARATELY_FORM",subformId,request);
%>
<div class="panel panel-default" id="ADDITIONAL_SERVICE_SUPPLEMENTARY_SUBFORM">
		 <div class="panel-body">
			<div class="row form-horizontal">
				<div class="col-sm-12">
					<div class="form-group">
						<%=HtmlUtil.checkBoxInline("SERVICE_TYPE_SPENDING_ALERT", PRODUCT_CRADIT_CARD ,"SERVICE_TYPE_SPENDING_ALERT_"+productElementId, "SERVICE_TYPE_SPENDING_ALERT", 
							SERVICE_SPENDING_ALERT_FLAG, "", MConstant.FLAG.YES, "", SpendingAlertDetail, "col-xs-12 control-label", formUtil) %>
					</div>
				</div>
				<div class="col-sm-12">
					<div class="form-group">
						<%=HtmlUtil.checkBoxInline("SERVICE_TYPE_DUE_ALERT", PRODUCT_CRADIT_CARD ,"SERVICE_TYPE_DUE_ALERT_"+productElementId, "SERVICE_TYPE_DUE_ALERT", 
							SERVICE_DUE_ALERT_FLAG, "", MConstant.FLAG.YES, "", DueAlert, "col-xs-12 control-label", formUtil) %>
					</div>
				</div>
			</div>
		</div>
	</div>


