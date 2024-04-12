<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.sql.Date"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.ListBoxControl" %>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM"%>
<%@page import="com.eaf.orig.ulo.control.util.PersonalInfoUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<jsp:useBean id="EntityForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<script type="text/javascript" src="orig/ulo/subform/js/PersonalInfoSubForm.js"></script>
<%
	String subformId = "SUP_PERSONAL_INFO_SUBFROM_1";
	String CIDTYPE_IDCARD=SystemConstant.getConstant("CIDTYPE_IDCARD");
	String CIDTYPE_PASSPORT = SystemConstant.getConstant("CIDTYPE_PASSPORT");
	String FIELD_ID_VISA_TYPE = SystemConstant.getConstant("FIELD_ID_VISA_TYPE");
	Logger logger = Logger.getLogger(this.getClass());		
	PersonalApplicationInfoDataM personalApplicationInfo = (PersonalApplicationInfoDataM)EntityForm.getObjectForm();
	logger.debug("personalApplicationInfo >> " + personalApplicationInfo);
	PersonalInfoDataM personalInfo = personalApplicationInfo.getPersonalInfo();
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	if(null == personalInfo){
		personalInfo = new PersonalInfoDataM();
	}
	int PERSONAL_SEQ = personalInfo.getSeq();
	String PERSONAL_ID = personalInfo.getPersonalId();
	String PERSONAL_TYPE = personalInfo.getPersonalType();
		
	String displayMode = HtmlUtil.EDIT;
// 	String TAG_SMART_DATA_PERSONAL = FormatUtil.getSmartDataEntryId(PERSONAL_TYPE,PERSONAL_SEQ);

	String personalElementId = FormatUtil.getPersonalElementId(personalInfo);

	String TAG_ID_TH_MID_NAME = HtmlUtil.elementTagId("TH_MID_NAME", personalElementId)+" tabindex='-1' placeholder='"+LabelUtil.getText(request, "SHOW_TH_MID_NAME")+"'";
	String TAG_ID_EN_MID_NAME = HtmlUtil.elementTagId("EN_MID_NAME", personalElementId)+" tabindex='-1' placeholder='"+LabelUtil.getText(request, "SHOW_TH_MID_NAME")+"'";
	
	String FIELD_ID_TH_TITLE_CODE = SystemConstant.getConstant("FIELD_ID_TH_TITLE_CODE");
	String FIELD_ID_EN_TITLE_CODE = SystemConstant.getConstant("FIELD_ID_EN_TITLE_CODE");
	String FIELD_ID_CID_TYPE = SystemConstant.getConstant("FIELD_ID_CID_TYPE");
	String FIELD_ID_GENDER = SystemConstant.getConstant("FIELD_ID_GENDER");
	String FIELD_ID_NATIONALITY = SystemConstant.getConstant("FIELD_ID_NATIONALITY");
	String FIELD_ID_MARRIED = SystemConstant.getConstant("FIELD_ID_MARRIED");
	String FIELD_ID_OCCUPATION = SystemConstant.getConstant("FIELD_ID_OCCUPATION");	
	String OCCUPATION_OTHER = SystemConstant.getConstant("OCCUPATION_OTHER");
	
	String textOCCUPATION = ListBoxControl.getName(FIELD_ID_OCCUPATION,personalInfo.getOccupation());
	if(OCCUPATION_OTHER.equals(personalInfo.getOccupation())){
		textOCCUPATION = personalInfo.getOccpationOther();
	}
	
	FormUtil formUtil = new FormUtil(subformId,request);
	String CID_TYPE = personalInfo.getCidType();
	
// 	Date passportExp = personalInfo.getCidExpDate();
// 	Date idCardExp =  personalInfo.getCidExpDate();
// 	if(CIDTYPE_IDCARD.equals(CID_TYPE)){
// 	 	passportExp =null;
// 	}else {
// 		idCardExp=null;
// 	}
	
%>
<div class="panel panel-default">
<div class="panel-body">
<div class="row form-horizontal">
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"CIS_NUMBER","CIS_NUMBER","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBox("CIS_NUMBER","CIS_NUMBER_"+personalElementId,"CIS_NUMBER",personalInfo.getCisNo(),"","10","","col-sm-8 col-md-7",formUtil)%>
		</div>
	</div>
	<div class="clearfix"></div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"CID_TYPE","CID_TYPE", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.dropdown("CID_TYPE", "CID_TYPE_"+personalElementId, "CID_TYPE", "", personalInfo.getCidType(), "",
				FIELD_ID_CID_TYPE,"ALL_ALL_ALL", "", "col-sm-8 col-md-7", formUtil)%>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"ID_NO","ID_NO","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBoxIdno("ID_NO","ID_NO_"+personalElementId,"ID_NO",personalInfo.getIdno(),"","15","","col-sm-8 col-md-7",formUtil)%>
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
							<%=HtmlUtil.autoCompleteLocal("TH_TITLE_DESC", "TH_TITLE_DESC_"+personalElementId, "TH_TITLE_DESC", "ThTitleCodeFilter",
						  personalInfo.getThTitleDesc(), "", FIELD_ID_TH_TITLE_CODE, "ALL_ALL_ALL", "", "col-xs-3 col-xs-padding", HtmlUtil.TH+HtmlUtil.EN, formUtil) %>
						<%}else{%>
							<%=HtmlUtil.autoCompleteLocal("TH_TITLE_DESC", "TH_TITLE_DESC_"+personalElementId, "TH_TITLE_DESC", "ThTitleCodeFilter",
						  personalInfo.getThTitleDesc(), "", FIELD_ID_TH_TITLE_CODE, "ALL_ALL_ALL", "", "col-xs-3 col-xs-padding", HtmlUtil.TH, formUtil) %>
						<%} %>						
						<div class="col-xs-9">
							<div class="input-group">
	                			<%=HtmlUtil.textBoxTH("TH_FIRST_NAME","TH_FIRST_NAME_"+personalElementId,"TH_FIRST_LAST_NAME",
	                				personalInfo.getThFirstName(),"textbox-name-1","120","","input-group-btn",formUtil)%>
	                			<%=HtmlUtil.textBoxTH("TH_MID_NAME","TH_MID_NAME_"+personalElementId,"TH_FIRST_LAST_NAME",
	                				personalInfo.getThMidName(),"textbox-name-2","60",TAG_ID_TH_MID_NAME,"input-group-btn",formUtil)%>
	                			<%=HtmlUtil.textBoxTH("TH_LAST_NAME","TH_LAST_NAME_"+personalElementId,"TH_FIRST_LAST_NAME",
	                				personalInfo.getThLastName(),"textbox-name-3","50","","input-group-btn",formUtil)%>
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
						<%=HtmlUtil.hidden("EN_TITLE_CODE", personalInfo.getEnTitleCode())%>
						<%=HtmlUtil.autoCompleteLocal("EN_TITLE_DESC", "EN_TITLE_DESC_"+personalElementId, "EN_TITLE_DESC", "EN_TITLE_CODE",
						  personalInfo.getEnTitleDesc(), "", FIELD_ID_EN_TITLE_CODE, "ALL_ALL_ALL", "", "col-xs-3 col-xs-padding", HtmlUtil.EN, formUtil) %>
						<div class="col-xs-9">
							<div class="input-group">
	  							<%=HtmlUtil.textBoxEN("EN_FIRST_NAME","EN_FIRST_NAME_"+personalElementId,"EN_FIRST_LAST_NAME",
	  								personalInfo.getEnFirstName(),"textbox-name-1","120","","input-group-btn",formUtil)%>
	                			<%=HtmlUtil.textBoxEN("EN_MID_NAME","EN_MID_NAME_"+personalElementId,"EN_FIRST_LAST_NAME",
	                				personalInfo.getEnMidName(),"textbox-name-2","60",TAG_ID_EN_MID_NAME,"input-group-btn",formUtil)%>
	                			<%=HtmlUtil.textBoxEN("EN_LAST_NAME","EN_LAST_NAME_"+personalElementId,"EN_FIRST_LAST_NAME",
	                				personalInfo.getEnLastName(),"textbox-name-3","50","","input-group-btn",formUtil)%>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">			
			<%=HtmlUtil.getSubFormLabel(request,subformId,"TH_BIRTH_DATE","TH_BIRTH_DATE", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.calendar("TH_BIRTH_DATE", "TH_BIRTH_DATE_"+personalElementId, "TH_BIRTH_DATE", 
				personalInfo.getBirthDate(), "", "", HtmlUtil.TH, "col-sm-8 col-md-7", formUtil)%>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"EN_BIRTH_DATE","EN_BIRTH_DATE", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.calendar("EN_BIRTH_DATE","EN_BIRTH_DATE_"+personalElementId, "EN_BIRTH_DATE",
				personalInfo.getBirthDate(), "", "", HtmlUtil.EN, "col-sm-8 col-md-7", formUtil)%>
		</div>
	</div>
	<div class="clearfix"></div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request, subformId, "VISA_TYPE", "VISA_TYPE", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.dropdown("VISA_TYPE", "VISA_TYPE_"+personalElementId, "VISA_TYPE", "", 
				personalInfo.getVisaType(), "", FIELD_ID_VISA_TYPE,"ALL_ALL_ALL", "", "col-sm-8 col-md-7", formUtil)%>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request, subformId, "VISA_EXPIRE_DATE", "VISA_EXPIRE_DATE", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.calendar("VISA_EXPIRE_DATE","VISA_EXPIRE_DATE_"+personalElementId, "VISA_EXPIRE_DATE",
				personalInfo.getVisaExpDate(), "", "", HtmlUtil.EN, "col-sm-8 col-md-7", formUtil)%>
		</div>
	</div>
	<div class="clearfix"></div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request, subformId, "PASSPORT_EXPIRE_DATE", "PASSPORT_EXPIRE_DATE", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.calendar("PASSPORT_EXPIRE_DATE",PersonalInfoUtil.getIdFieldExpireDocument(personalInfo.getCidType(),personalInfo), "PASSPORT_EXPIRE_DATE",
				personalInfo.getCidExpDate(), "", "", HtmlUtil.EN, "col-sm-8 col-md-7", formUtil)%>
		</div>
	</div>
<!-- 	<div class="col-sm-6"> -->
<!-- 		<div class="form-group"> -->
<%-- 			<%=HtmlUtil.getSubFormLabel(request, subformId, "IDNO_EXPIRE_DATE", "IDNO_EXPIRE_DATE", "col-sm-4 col-md-5 control-label")%> --%>
<%-- 			<%=HtmlUtil.calendar("IDNO_EXPIRE_DATE","IDNO_EXPIRE_DATE_"+personalElementId, "IDNO_EXPIRE_DATE", --%>
<%-- 				idCardExp, "", "", HtmlUtil.EN, "col-sm-8 col-md-7", formUtil)%> --%>
<!-- 		</div> -->
<!-- 	</div> -->
<div class="clearfix"></div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"GENDER","GENDER", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.dropdown("GENDER", "GENDER_"+personalElementId, "GENDER", "",
				personalInfo.getGender(), "", FIELD_ID_GENDER,"ALL_ALL_ALL", "", "col-sm-8 col-md-7", formUtil)%>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"NATIONALITY","NATIONALITY", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.dropdown("NATIONALITY", "NATIONALITY_"+personalElementId, "NATIONALITY", "", 
				personalInfo.getNationality(), "", FIELD_ID_NATIONALITY,"ALL_ALL_ALL", "", "col-sm-8 col-md-7", formUtil)%>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"MARRIED","MARRIED", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.dropdown("MARRIED", "MARRIED_"+personalElementId, "MARRIED", "", 
				personalInfo.getMarried(), "", FIELD_ID_MARRIED,"ALL_ALL_ALL", "", "col-sm-8 col-md-7", formUtil)%>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"OCCUPATION","OCCUPATION", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.popupList("OCCUPATION", "OCCUPATION_"+personalElementId, "OCCUPATION", 
						textOCCUPATION, "", "20", "", "col-sm-8 col-md-7", formUtil) %>
		</div>
	</div>
</div>
</div>
</div>
<%=HtmlUtil.hidden("PERSONAL_SEQ",String.valueOf(PERSONAL_SEQ)) %>
<%=HtmlUtil.hidden("PERSONAL_ID",String.valueOf(PERSONAL_SEQ)) %>
<%=HtmlUtil.hidden("APPLY_DATE_EN",FormatUtil.display(applicationGroup.getApplyDate(), HtmlUtil.EN)) %>
<%=HtmlUtil.hidden("CONSENT_DATE",FormatUtil.display(personalInfo.getConsentDate(), HtmlUtil.EN)) %>