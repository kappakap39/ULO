<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="java.sql.Date"%>
<%@page import="com.ibm.xtq.ast.nodes.If"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<%@page import="com.eaf.core.ulo.common.properties.ListBoxControl"%>
<%@page import="com.eaf.orig.ulo.control.util.PersonalInfoUtil" %>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="EntityForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<script type="text/javascript" src="orig/ulo/subform/js/NCBInfoSubForm.js"></script>
<%
	String subformId = "IA_INCREASE_NCB_APPLICANT_INFO_SUBFORM";
	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	Logger logger = Logger.getLogger(this.getClass());
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	String template = applicationGroup.getApplicationTemplate();
	PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
	if(null == applicationGroup){
		applicationGroup = new ApplicationGroupDataM();
	}

	if(null == personalInfo){
		personalInfo = new PersonalInfoDataM();
	}
	String NCB_RESULT = MConstant.FLAG.YES;
	int PERSONAL_SEQ = personalInfo.getSeq();
	String PRODUCT_K_EXPRESS_CASH = SystemConstant.getConstant("PRODUCT_K_EXPRESS_CASH");
	String displayMode = HtmlUtil.EDIT;
// 	String TAG_SMART_DATA_PERSONAL = FormatUtil.getSmartDataEntryId(PERSONAL_TYPE,PERSONAL_SEQ);
	String personalElementId = FormatUtil.getPersonalElementId(personalInfo);
	String MAIN_PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String TAG_ID_TH_MID_NAME = HtmlUtil.elementTagId("TH_MID_NAME",personalElementId)+" tabindex='-1' placeholder='"+LabelUtil.getText(request, "SHOW_TH_MID_NAME")+"'";
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String CIDTYPE_IDCARD=SystemConstant.getConstant("CIDTYPE_IDCARD");
	String CIDTYPE_PASSPORT=SystemConstant.getConstant("CIDTYPE_PASSPORT");
	String FIELD_ID_TH_TITLE_CODE = SystemConstant.getConstant("FIELD_ID_TH_TITLE_CODE");
	String FIELD_ID_CID_TYPE = SystemConstant.getConstant("FIELD_ID_CID_TYPE");
	String FIELD_ID_VISA_TYPE = SystemConstant.getConstant("FIELD_ID_VISA_TYPE");
	String FIELD_ID_CONSENT_MODEL = SystemConstant.getConstant("FIELD_ID_CONSENT_MODEL");
	FormUtil formUtil = new FormUtil(subformId,request);	
	
	String FIELD_ID_PERSONAL_TYPE = SystemConstant.getConstant("FIELD_ID_PERSONAL_TYPE");
	String TEXT_PERSONAL_TYPE = ListBoxControl.getName(FIELD_ID_PERSONAL_TYPE,personalInfo.getPersonalType());
	String CID_TYPE = personalInfo.getCidType();
%>
<div class="panel panel-default">
<div class="panel-body">
<div class="row form-horizontal">
	<div class="col-sm-6"> 
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"ID_NO_CONSENT","ID_NO_CONSENT","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBoxIdno("ID_NO_CONSENT","","ID_NO_CONSENT_"+personalElementId, "ID_NO_CONSENT", personalInfo.getIdNoConsent(),"","15","","col-sm-8 col-md-7",personalInfo,formUtil)%>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">			
			<%=HtmlUtil.getSubFormLabel(request,subformId,"DATE_CONSENT","DATE_CONSENT", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.calendar("DATE_CONSENT", "DATE_CONSENT_"+personalElementId, "DATE_CONSENT", 
				personalInfo.getConsentDate(), "", "", HtmlUtil.TH, "col-sm-8 col-md-7", formUtil)%>
		</div>
	</div>
	<div class="clearfix"></div>
	<div class="col-sm-6">
		<div class="form-group">			
			<%=HtmlUtil.getSubFormLabel(request,subformId,"TH_BIRTH_DATE_CONSENT","TH_BIRTH_DATE_CONSENT", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.calendar("TH_BIRTH_DATE_CONSENT", "TH_BIRTH_DATE_CONSENT_"+personalElementId, "TH_BIRTH_DATE_CONSENT", 
				personalInfo.getBirthDateConsent(), "", "", HtmlUtil.TH, "col-sm-8 col-md-7", formUtil)%>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"EN_BIRTH_DATE_CONSENT","EN_BIRTH_DATE_CONSENT", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.calendar("EN_BIRTH_DATE_CONSENT","EN_BIRTH_DATE_CONSENT_"+personalElementId, "EN_BIRTH_DATE_CONSENT",
				personalInfo.getBirthDateConsent(), "", "", HtmlUtil.EN, "col-sm-8 col-md-7", formUtil)%>
		</div>
	</div>
	
	<div class="col-sm-6">
		<div class="form-group">
		<%=HtmlUtil.getMandatoryLabel(request, "PLACE_CONSENT", "col-sm-4 col-md-5 control-label") %>
			<div class="col-sm-8 col-md-7"  style="padding-left: 0px; padding-right: 0px;">
				<%=HtmlUtil.radioInline("PLACE_CONSENT", "PLACE_CONSENT_"+personalElementId,"PLACE_CONSENT" , personalInfo.getPlaceConsent(), "", MConstant.FLAG.YES,"" , "CONSENT_Y", "", formUtil) %>
				<%=HtmlUtil.radioInline("PLACE_CONSENT", "PLACE_CONSENT_"+personalElementId,"PLACE_CONSENT" , personalInfo.getPlaceConsent(), "", MConstant.FLAG.NO, "", "CONSENT_N","", formUtil) %>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
	<div class="col-sm-6">
		<div class="form-group">
		<%=HtmlUtil.getMandatoryLabel(request, "WITNESS_CONSENT", "col-sm-4 col-md-5 control-label") %>
			<div class="col-sm-8 col-md-7"  style="padding-left: 0px; padding-right: 0px;">
				<%=HtmlUtil.radioInline("WITNESS_CONSENT", "WITNESS_CONSENT_"+personalElementId,"WITNESS_CONSENT" , personalInfo.getWitnessConsent(), "", MConstant.FLAG.YES,"" , "CONSENT_Y", "", formUtil) %>
				<%=HtmlUtil.radioInline("WITNESS_CONSENT", "WITNESS_CONSENT_"+personalElementId,"WITNESS_CONSENT" , personalInfo.getWitnessConsent(), "", MConstant.FLAG.NO, "", "CONSENT_N","", formUtil) %>
			</div>
		</div>
	</div>
</div>
</div>
</div>
<%=HtmlUtil.hidden("THIS_PAGE",subformId) %>
<%=HtmlUtil.hidden("PERSONAL_USER_TYPE",PERSONAL_TYPE) %>
<%=HtmlUtil.hidden("APPLY_DATE_EN",FormatUtil.display(applicationGroup.getApplyDate(), HtmlUtil.EN)) %>