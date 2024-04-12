<%@page import="java.sql.Date"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.ListBoxControl"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM"%>
<%@page import="com.eaf.orig.ulo.control.util.PersonalInfoUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="EntityForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>

<script type="text/javascript" src="orig/ulo/subform/js/PersonalInfoSubForm.js"></script>
<%
	String subformId = "SUP_PERSONAL_INFO_SUBFROM_3";
	String CIDTYPE_PASSPORT = SystemConstant.getConstant("CIDTYPE_PASSPORT");
	String ROLE_DE2 = SystemConstant.getConstant("ROLE_DE2");
	Logger logger = Logger.getLogger(this.getClass());
	PersonalApplicationInfoDataM personalApplicationInfo = (PersonalApplicationInfoDataM)EntityForm.getObjectForm();
	PersonalInfoDataM personalInfo = personalApplicationInfo.getPersonalInfo();
	if(Util.empty(personalInfo)){
		personalInfo = new PersonalInfoDataM();
	}
	
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	if(Util.empty(applicationGroup)){
		applicationGroup = new ApplicationGroupDataM();
	}
	int PERSONAL_SEQ = personalInfo.getSeq();
	String PERSONAL_TYPE = personalInfo.getPersonalType();	
		
	String displayMode = HtmlUtil.EDIT;
// 	String personalElementId = FormatUtil.getSmartDataEntryId(PERSONAL_TYPE,PERSONAL_SEQ);
	String personalElementId = FormatUtil.getPersonalElementId(personalInfo);
	String TAG_ID_TH_MID_NAME = HtmlUtil.elementTagId("TH_MID_NAME", personalElementId)+" tabindex='-1' placeholder='"+LabelUtil.getText(request, "SHOW_TH_MID_NAME")+"'";
	String TAG_ID_EN_MID_NAME = HtmlUtil.elementTagId("EN_MID_NAME", personalElementId)+" tabindex='-1' placeholder='"+LabelUtil.getText(request, "SHOW_TH_MID_NAME")+"'";
	String CIDTYPE_IDCARD=SystemConstant.getConstant("CIDTYPE_IDCARD");
	String FIELD_ID_TH_TITLE_CODE = SystemConstant.getConstant("FIELD_ID_TH_TITLE_CODE");
	String FIELD_ID_EN_TITLE_CODE = SystemConstant.getConstant("FIELD_ID_EN_TITLE_CODE");
	String FIELD_ID_CID_TYPE = SystemConstant.getConstant("FIELD_ID_CID_TYPE");
	String FIELD_ID_GENDER = SystemConstant.getConstant("FIELD_ID_GENDER");
	String FIELD_ID_NATIONALITY = SystemConstant.getConstant("FIELD_ID_NATIONALITY");
	String FIELD_ID_VISA_TYPE = SystemConstant.getConstant("FIELD_ID_VISA_TYPE");
	String FIELD_ID_MARRIED = SystemConstant.getConstant("FIELD_ID_MARRIED");
	String FIELD_ID_OCCUPATION = SystemConstant.getConstant("FIELD_ID_OCCUPATION");
	String FIELD_ID_RELATION_WITH_APPLICANT = SystemConstant.getConstant("FIELD_ID_RELATION_WITH_APPLICANT");
	String OCCUPATION_OTHER = SystemConstant.getConstant("OCCUPATION_OTHER");
	String FIELD_ID_DISCLOSURE_FLAG = SystemConstant.getConstant("FIELD_ID_DISCLOSURE_FLAG");
	
	String textOCCUPATION = ListBoxControl.getName(FIELD_ID_OCCUPATION,personalInfo.getOccupation());
	if(OCCUPATION_OTHER.equals(personalInfo.getOccupation())){
		textOCCUPATION = personalInfo.getOccpationOther();
	}
	String CID_TYPE = personalInfo.getCidType();
	String roleId = ORIGForm.getRoleId();
// 	Date passportExp = personalInfo.getCidExpDate();
// 	Date idCardExp =  personalInfo.getCidExpDate();
// 	if(CIDTYPE_IDCARD.equals(CID_TYPE)){
// 	 	passportExp =null;
// 	}else {
// 		idCardExp=null;
// 	}
	FormUtil formUtil = new FormUtil(subformId,request);
	logger.debug("personalInfo.getRelationshipType() : "+personalInfo.getRelationshipType());
	
	if(Util.empty(personalInfo.getDisclosureFlag())){
		personalInfo.setDisclosureFlag("0");
	}
%>
<div class="panel panel-default">
<div class="panel-body">
<div class="row form-horizontal">
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"CIS_NUMBER","CIS_NUMBER","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBox("CIS_NUMBER","","CIS_NUMBER_"+personalElementId,"CIS_NUMBER",
				personalInfo.getCisNo(),"","10","","col-sm-8 col-md-7",personalInfo,formUtil)%>
		</div>
	</div>
	<div class="clearfix"></div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"CID_TYPE","CID_TYPE", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.dropdown("CID_TYPE", "", "CID_TYPE_"+personalElementId, "CID_TYPE", "", 
				personalInfo.getCidType(), "", FIELD_ID_CID_TYPE,"ALL_ALL_ALL", "", "col-sm-8 col-md-7", personalInfo, formUtil)%>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"ID_NO","ID_NO","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBox("ID_NO","","ID_NO_"+personalElementId,"ID_NO",
				personalInfo.getIdno(),"","20","","col-sm-8 col-md-7",personalInfo,formUtil)%>
		</div>
		
		
	</div>
	<div class="col-sm-12">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"TH_FIRST_LAST_NAME","TH_FIRST_LAST_NAME", "col-sm-2 col-md-5 marge-label control-label")%>
			<div class="col-sm-10 col-md-9 marge-field">
				<div class="row">
					<div class="col-xs-12">
						<%=HtmlUtil.hidden("TH_TITLE_CODE", personalInfo.getThTitleCode())%>
						<%if(CIDTYPE_PASSPORT.equals(CID_TYPE)){%>
							<%=HtmlUtil.autoCompleteLocal("TH_TITLE_DESC", "", "TH_TITLE_DESC_"+personalElementId, "TH_TITLE_DESC", "ThTitleCodeFilter",
						  		personalInfo.getThTitleDesc(), "", FIELD_ID_TH_TITLE_CODE, "ALL_ALL_ALL", "", "col-xs-3 col-xs-padding", HtmlUtil.TH+HtmlUtil.EN, personalInfo, formUtil) %>
						<%}else{ %>
							<%=HtmlUtil.autoCompleteLocal("TH_TITLE_DESC", "", "TH_TITLE_DESC_"+personalElementId, "TH_TITLE_DESC", "ThTitleCodeFilter",
						  		personalInfo.getThTitleDesc(), "", FIELD_ID_TH_TITLE_CODE, "ALL_ALL_ALL", "", "col-xs-3 col-xs-padding", HtmlUtil.TH, personalInfo, formUtil) %>
						<%} %>					
						<div class="col-xs-9">
							<div class="input-group">
	                			<%=HtmlUtil.textBoxTH("TH_FIRST_NAME", "", "TH_FIRST_NAME_"+personalElementId,"TH_FIRST_NAME",
	                				personalInfo.getThFirstName(),"textbox-name-1","120","","input-group-btn", personalInfo, formUtil)%>
	                			<%=HtmlUtil.textBoxTH("TH_MID_NAME", "", "TH_MID_NAME_"+personalElementId,"TH_MID_NAME",
	                				personalInfo.getThMidName(),"textbox-name-2","60",TAG_ID_TH_MID_NAME,"input-group-btn", personalInfo, formUtil)%>
	                			<%=HtmlUtil.textBoxTH("TH_LAST_NAME", "", "TH_LAST_NAME_"+personalElementId,"TH_LAST_NAME",
	                				personalInfo.getThLastName(),"textbox-name-3","50","","input-group-btn", personalInfo, formUtil)%>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="col-sm-12">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"EN_FIRST_LAST_NAME","EN_FIRST_LAST_NAME", "col-sm-2 col-md-5 marge-label control-label")%>
			<div class="col-sm-10 col-md-9 marge-field">
				<div class="row">
					<div class="col-xs-12">
						<%=HtmlUtil.hidden("EN_TITLE_CODE", personalInfo.getThTitleCode())%>
						<%=HtmlUtil.autoCompleteLocal("EN_TITLE_DESC", "", "EN_TITLE_DESC_"+personalElementId, "EN_TITLE_DESC", "",
							personalInfo.getEnTitleDesc(), "", FIELD_ID_EN_TITLE_CODE, "ALL_ALL_ALL", "", "col-xs-3 col-xs-padding", HtmlUtil.EN, personalInfo, formUtil) %>
						<div class="col-xs-9">
							<div class="input-group">
	  							<%=HtmlUtil.textBoxEN("EN_FIRST_NAME", "", "EN_FIRST_NAME_"+personalElementId,"EN_FIRST_NAME",
	  								personalInfo.getEnFirstName(),"textbox-name-1","120","","input-group-btn", personalInfo,formUtil)%>
	                			<%=HtmlUtil.textBoxEN("EN_MID_NAME", "", "EN_MID_NAME_"+personalElementId,"EN_MID_NAME",
	                				personalInfo.getEnMidName(),"textbox-name-2","60",TAG_ID_EN_MID_NAME,"input-group-btn",personalInfo,formUtil)%>
	                			<%=HtmlUtil.textBoxEN("EN_LAST_NAME", "","EN_LAST_NAME_"+personalElementId,"EN_LAST_NAME",
	                				personalInfo.getEnLastName(),"textbox-name-3","50","","input-group-btn",personalInfo,formUtil)%>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
<!-- 	<div class="col-sm-12"> -->
<!-- 		<div class="form-group"> -->
<%-- 			<%=HtmlUtil.getSubFormLabel(request,subformId,"EMBOSS_NAME","EMBOSS_NAME", "col-sm-2 col-md-5 marge-label control-label")%> --%>
<!-- 			<div class="col-sm-10 col-md-9 marge-field"> -->
<!-- 				<div class="row"> -->
<!-- 					<div class="col-xs-10"> -->
<%-- 						<%=HtmlUtil.textBox("EMBOSS_NAME_1","","EMBOSS_NAME_1_"+personalElementId,"EMBOSS_NAME", --%>
<%-- 							personalInfo.getCisNo(),"textbox-name-1","60","","input-group-btn",personalInfo,formUtil)%> --%>
<%-- 						<%=HtmlUtil.textBox("EMBOSS_NAME_2","","EMBOSS_NAME_2_"+personalElementId,"EMBOSS_NAME", --%>
<%-- 							personalInfo.getCisNo(),"textbox-name-3","50","","input-group-btn",personalInfo,formUtil)%> --%>
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 	</div> -->
	<div class="col-sm-6">
		<div class="form-group">			
			<%=HtmlUtil.getSubFormLabel(request,subformId,"TH_BIRTH_DATE","TH_BIRTH_DATE", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.calendar("TH_BIRTH_DATE", "", "TH_BIRTH_DATE_"+personalElementId, "TH_BIRTH_DATE", 
				personalInfo.getBirthDate(), "", "", HtmlUtil.TH, "col-sm-8 col-md-7", personalInfo, formUtil)%>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"EN_BIRTH_DATE","EN_BIRTH_DATE", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.calendar("EN_BIRTH_DATE", "","EN_BIRTH_DATE_"+personalElementId, "TH_BIRTH_DATE",
				personalInfo.getBirthDate(), "", "", HtmlUtil.EN, "col-sm-8 col-md-7", personalInfo,formUtil)%>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"GENDER","GENDER", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.dropdown("GENDER", "", "GENDER_"+personalElementId, "GENDER", "",
				personalInfo.getGender(), "", FIELD_ID_GENDER,"ALL_ALL_ALL", "", "col-sm-8 col-md-7", personalInfo, formUtil)%>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"NATIONALITY","NATIONALITY", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.dropdown("NATIONALITY", "", "NATIONALITY_"+personalElementId, "NATIONALITY", "", 
				personalInfo.getNationality(), "", FIELD_ID_NATIONALITY,"ALL_ALL_ALL", "", "col-sm-8 col-md-7", personalInfo, formUtil)%>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request, subformId, "VISA_TYPE", "VISA_TYPE", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.dropdown("VISA_TYPE", "", "VISA_TYPE_"+personalElementId, "VISA_TYPE", "", 
				personalInfo.getVisaType(), "", FIELD_ID_VISA_TYPE,"ALL_ALL_ALL", "", "col-sm-8 col-md-7", personalInfo, formUtil)%>
		</div>
	</div>
	
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request, subformId, "VISA_EXPIRE_DATE", "VISA_EXPIRE_DATE", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.calendar("VISA_EXPIRE_DATE", "","VISA_EXPIRE_DATE_"+personalElementId, "VISA_EXPIRE_DATE",
				personalInfo.getVisaExpDate(), "", "", HtmlUtil.EN, "col-sm-8 col-md-7", personalInfo, formUtil)%>
		</div>
	</div>
		<div class="clearfix"></div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request, subformId, "WORK_PERMIT_NO", "WORK_PERMIT_NO", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBox("WORK_PERMIT_NO", "","WORK_PERMIT_NO_"+personalElementId,"WORK_PERMIT_NO",
				personalInfo.getWorkPermitNo(),"","20","","col-sm-8 col-md-7", personalInfo,formUtil)%>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request, subformId, "WORK_PERMIT_EXPIRE_DATE", "WORK_PERMIT_EXPIRE_DATE", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.calendar("WORK_PERMIT_EXPIRE_DATE","","WORK_PERMIT_EXPIRE_DATE_"+personalElementId, "WORK_PERMIT_EXPIRE_DATE",
				personalInfo.getWorkPermitExpDate(), "", "", HtmlUtil.TH, "col-sm-8 col-md-7",personalInfo, formUtil)%>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request, subformId, "PASSPORT_EXPIRE_DATE", "PASSPORT_EXPIRE_DATE", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.calendar("PASSPORT_EXPIRE_DATE","",PersonalInfoUtil.getIdFieldExpireDocument(personalInfo.getCidType(),personalInfo), "PASSPORT_EXPIRE_DATE",
				personalInfo.getCidExpDate(), "", "", HtmlUtil.EN, "col-sm-8 col-md-7",personalInfo, formUtil)%>
		</div>
	</div>
	<%if(ROLE_DE2.equals(roleId)){ %>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request, subformId, "ISSUE_DOCUMENT_DATE", "ISSUE_DOCUMENT_DATE", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.calendar("ISSUE_DOCUMENT_DATE","ISSUE_DOCUMENT_DATE_"+personalElementId, "ISSUE_DOCUMENT_DATE",
				personalInfo.getCidIssueDate(), "", "", HtmlUtil.EN, "col-sm-8 col-md-7", formUtil)%>
		</div>
	</div>
<%}%>
	<div class="clearfix"></div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request, subformId, "MARRIED", "MARRIED", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.dropdown("MARRIED", "","MARRIED_"+personalElementId, "MARRIED", "", 
				personalInfo.getMarried(), "", FIELD_ID_MARRIED,"ALL_ALL_ALL", "", "col-sm-8 col-md-7",personalInfo, formUtil)%>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"OCCUPATION","OCCUPATION", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.popupList("OCCUPATION", "OCCUPATION_"+personalElementId, "OCCUPATION", 
				textOCCUPATION, "", "20", "","col-sm-8 col-md-7",personalInfo, formUtil) %>
		</div>
	</div>
	<div class="clearfix"></div>
	<div class="col-md-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request, subformId,"RELATION_WITH_APPLICANT","RELATION_WITH_APPLICANT", "col-sm-4 col-md-5 control-label")%>
			<div class="col-sm-8 col-md-7">
				<div class="row">
					<div class="col-xs-12">
						<%=HtmlUtil.dropdown("RELATION_WITH_APPLICANT","", "RELATION_WITH_APPLICANT_"+personalElementId, "RELATION_WITH_APPLICANT", "", 
							personalInfo.getRelationshipType(), "", FIELD_ID_RELATION_WITH_APPLICANT,"ALL_ALL_ALL", "","col-xs-4 col-xs-padding",personalInfo, formUtil)%>
						<%=HtmlUtil.textBox("RELATION_WITH_APPLICANT_OTHER","","RELATION_WITH_APPLICANT_OTHER_"+personalElementId,"RELATION_WITH_APPLICANT_OTHER",
							personalInfo.getRelationshipTypeDesc(),"","50","","col-xs-8 col-xs-padding",personalInfo,formUtil)%>
					</div>
				</div>
			</div>
		</div>
	</div>

	<%-- <div class="col-sm-12">
		<div class="col-sm-6">
			<div class="form-group">
				<%=HtmlUtil.getSubFormLabel(request, subformId, "DISCLOSURE_FLAG", "MAIN_ACCEPT", "col-sm-4 col-md-5 control-label")%>
				<%=HtmlUtil.dropdown("DISCLOSURE_FLAG", "DISCLOSURE_FLAG_"+personalElementId, "DISCLOSURE_FLAG", "", 
						personalInfo.getDisclosureFlag(), "",FIELD_ID_DISCLOSURE_FLAG,"ALL_ALL_ALL", "", "col-sm-8 col-md-7", personalInfo,formUtil)%>
			</div>
		</div>
		</div> --%>
</div>
</div>
</div>

<%=HtmlUtil.hidden("PERSONAL_SEQ",String.valueOf(PERSONAL_SEQ)) %>
<%=HtmlUtil.hidden("APPLY_DATE_EN",FormatUtil.display(applicationGroup.getApplyDate(), HtmlUtil.EN)) %>	
<%=HtmlUtil.hidden("CONSENT_DATE",FormatUtil.display(personalInfo.getConsentDate(), HtmlUtil.EN)) %>