<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.core.ulo.common.properties.ListBoxControl"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.SpecialAdditionalServiceDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>

<script type="text/javascript" src="orig/ulo/product/js/AdditionalServiceSubForm.js"></script>
<%
	Logger logger = Logger.getLogger(this.getClass());
	String subformId = "KEC_ADDITIONAL_SERVICE_SUBFORM";
	logger.debug("subformId >>>"+subformId);
	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String PRODUCT_K_EXPRESS_CASH = SystemConstant.getConstant("PRODUCT_K_EXPRESS_CASH");
	String SPECIAL_ADDITIONAL_SERVICE_DUE_ALERT = SystemConstant.getConstant("SPECIAL_ADDITIONAL_SERVICE_DUE_ALERT");
	String SPECIAL_ADDITIONAL_SERVICE_POSTAL = SystemConstant.getConstant("SPECIAL_ADDITIONAL_SERVICE_POSTAL");
	String FIELD_ID_SPECIAL_ADDITIONAL_SERVICE = SystemConstant.getConstant("FIELD_ID_SPECIAL_ADDITIONAL_SERVICE");
	String ROLE_DE2 = SystemConstant.getConstant("ROLE_DE2");
	String DUE_ALERT_FLAG ="";
	String POSTAL_FLAG ="";
	String FLAG_YES = SystemConstant.getConstant("FLAG_YES");
	String roleId = ORIGForm.getRoleId();
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
	if(null == personalInfo){
		personalInfo = new PersonalInfoDataM();
	}
	String personalId = personalInfo.getPersonalId();
	int PERSONAL_SEQ = personalInfo.getSeq();
	if(!Util.empty(applicationGroup.getSpecialAdditionalServiceLifeCycleByPersonalId(personalId, PRODUCT_K_EXPRESS_CASH, SPECIAL_ADDITIONAL_SERVICE_DUE_ALERT)) ){
		DUE_ALERT_FLAG =FLAG_YES;
	}
	if(!Util.empty(applicationGroup.getSpecialAdditionalServiceLifeCycleByPersonalId(personalId, PRODUCT_K_EXPRESS_CASH, SPECIAL_ADDITIONAL_SERVICE_POSTAL))){ 
// 	  || (Util.empty(DUE_ALERT_FLAG) && ROLE_DE2.equals(roleId))){
		POSTAL_FLAG =FLAG_YES;
	}

	String displayMode = HtmlUtil.EDIT;
	String productElementId = FormatUtil.getProductElementId(personalInfo,PRODUCT_K_EXPRESS_CASH);	

	String postal = ListBoxControl.getName(FIELD_ID_SPECIAL_ADDITIONAL_SERVICE, SPECIAL_ADDITIONAL_SERVICE_POSTAL, "REMARK");
	String dueAlert = LabelUtil.getText(request, "ADDITIONAL_SERVICE_KEC_DUE_ALERT");	
	FormUtil formUtil = new FormUtil("PRODUCT_FORM_KEC",subformId,request);
	
%>
	<div class="panel panel-default">
		 <div class="panel-body">
			<div class="row form-horizontal">
				<div class="col-sm-12">
					<div class="form-group">
						<%=HtmlUtil.checkBoxInline("SERVICE_TYPE_POSTAL_FLAG", PRODUCT_K_EXPRESS_CASH ,"SERVICE_TYPE_POSTAL_FLAG_"+productElementId, "SERVICE_TYPE_POSTAL_FLAG", 
							POSTAL_FLAG, "", MConstant.FLAG.YES, "", postal, "col-xs-12 control-label", formUtil) %>
					</div>
				</div>
				<div class="col-sm-12">
					<div class="form-group">
						<%=HtmlUtil.checkBoxInline("SERVICE_TYPE_DUE_ALERT", PRODUCT_K_EXPRESS_CASH ,"SERVICE_TYPE_DUE_ALERT_"+productElementId, "SERVICE_TYPE_DUE_ALERT", 
							DUE_ALERT_FLAG, "", MConstant.FLAG.YES, "", dueAlert, "col-xs-12 control-label", formUtil) %>
					</div>
				</div>
 
			</div>
		</div>
	</div>

