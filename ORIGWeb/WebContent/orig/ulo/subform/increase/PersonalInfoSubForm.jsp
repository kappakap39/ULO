<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="org.apache.tools.ant.taskdefs.Javadoc.Html"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.orig.ulo.control.util.PersonalInfoUtil" %>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>

<script type="text/javascript" src="orig/ulo/subform/js/PersonalInfoSubForm.js"></script>
<%
	String subformId = "INCREASE_PERSONAL_INFO_SUBFORM";
	Logger logger = Logger.getLogger(this.getClass());
	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String CIDTYPE_PASSPORT = SystemConstant.getConstant("CIDTYPE_PASSPORT");
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	String template = applicationGroup.getApplicationTemplate();
	PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
	if(null == personalInfo){
		personalInfo = new PersonalInfoDataM();
	}
	int PERSONAL_SEQ = personalInfo.getSeq();
	String displayMode = HtmlUtil.EDIT;
// 	String TAG_SMART_DATA_PERSONAL = FormatUtil.getSmartDataEntryId(PERSONAL_TYPE,PERSONAL_SEQ);
	String personalElementId = FormatUtil.getPersonalElementId(personalInfo);
	String TAG_ID_TH_MID_NAME = HtmlUtil.elementTagId("TH_MID_NAME",personalElementId)+" tabindex='-1' placeholder='"+LabelUtil.getText(request, "SHOW_TH_MID_NAME")+"'";
	
	String FIELD_ID_TH_TITLE_CODE = SystemConstant.getConstant("FIELD_ID_TH_TITLE_CODE");
	String FIELD_ID_CID_TYPE = SystemConstant.getConstant("FIELD_ID_CID_TYPE");
	String FIELD_ID_VISA_TYPE = SystemConstant.getConstant("FIELD_ID_VISA_TYPE");
	String FIELD_ID_CONSENT_MODEL = SystemConstant.getConstant("FIELD_ID_CONSENT_MODEL");
	FormUtil formUtil = new FormUtil(subformId,request);
	String CID_TYPE = personalInfo.getCidType();
%>
<div class="panel panel-default">
<div class="panel-body">
<div class="row form-horizontal">
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"CIS_NUMBER","CIS_NUMBER","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBox("CIS_NUMBER", "", "CIS_NUMBER_"+personalElementId, "CIS_NUMBER", personalInfo.getCisNo(), "", "10", "", "col-sm-8 col-md-7", personalInfo, formUtil) %>

		</div>
	</div>
	<div class="clearfix"></div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"CID_TYPE","CID_TYPE", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.dropdown("CID_TYPE", "", "CID_TYPE_"+personalElementId, "CID_TYPE","", personalInfo.getCidType(), "", FIELD_ID_CID_TYPE,
				 "ALL_ALL_ALL", "", "col-sm-8 col-md-7", personalInfo, formUtil) %>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"ID_NO","ID_NO","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBox("ID_NO", "", "ID_NO_"+personalElementId, "ID_NO", personalInfo.getIdno(), "", "15", "", "col-sm-8 col-md-7", personalInfo, formUtil)%>
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
						<%}else{%>
						 <%=HtmlUtil.autoCompleteLocal("TH_TITLE_DESC", "", "TH_TITLE_DESC_"+personalElementId, "TH_TITLE_DESC", "ThTitleCodeFilter",
						    personalInfo.getThTitleDesc(), "", FIELD_ID_TH_TITLE_CODE, "ALL_ALL_ALL", "", "col-xs-3 col-xs-padding", HtmlUtil.TH, personalInfo, formUtil) %>
						
						<%} %>						
						<div class="col-xs-9">
							<div class="input-group">
	                			<%=HtmlUtil.textBoxTH("TH_FIRST_NAME", "","TH_FIRST_NAME_"+personalElementId, "TH_FIRST_NAME",
	                			 personalInfo.getThFirstName(), "textbox-name-1", "120", "", "input-group-btn", personalInfo, formUtil)%>	
	                			<%=HtmlUtil.textBoxTH("TH_MID_NAME", "","TH_MID_NAME_"+personalElementId, "TH_MID_NAME",
	                			 personalInfo.getThMidName(), "textbox-name-1", "60", TAG_ID_TH_MID_NAME, "input-group-btn", personalInfo, formUtil)%>	
 	                			<%=HtmlUtil.textBoxTH("TH_LAST_NAME", "","TH_LAST_NAME_"+personalElementId, "TH_LAST_NAME",
	                			 personalInfo.getThLastName(), "textbox-name-1", "50", "", "input-group-btn", personalInfo, formUtil)%>	
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
			<%=HtmlUtil.calendar("TH_BIRTH_DATE", "", "TH_BIRTH_DATE_"+personalElementId, "TH_BIRTH_DATE",
			  personalInfo.getBirthDate(), "", "",  HtmlUtil.TH, "col-sm-8 col-md-7", personalInfo, formUtil) %>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"EN_BIRTH_DATE","EN_BIRTH_DATE", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.calendar("EN_BIRTH_DATE", "", "EN_BIRTH_DATE_"+personalElementId, "EN_BIRTH_DATE",
			  personalInfo.getBirthDate(), "", "",  HtmlUtil.EN, "col-sm-8 col-md-7", personalInfo, formUtil) %>
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
	<% // if(PERSONAL_TYPE.equals(personalInfo.getPersonalType()) && !SystemConstant.lookup("CONSENT_MODEL_EXCEPTION_APPLICATION_TEMPLATE", template)) {%>
	<div class="clearfix"></div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,personalInfo,subformId,"CONSENT_MODEL","CONSENT_MODEL","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.dropdown("CONSENT_MODEL", "CONSENT_MODEL_"+personalElementId, "CONSENT_MODEL", "", 
					personalInfo.getConsentModelFlag(), "",FIELD_ID_CONSENT_MODEL,"ALL_ALL_ALL", "", "col-sm-8 col-md-7",personalInfo, formUtil)%>
		</div>
	</div>
	<% //} %>
	<!-- <div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"ID_NO_CONSENT","ID_NO_CONSENT","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBoxIdno("ID_NO_CONSENT","","ID_NO_CONSENT"+personalElementId, "ID_NO_CONSENT", personalInfo.getIdNoConsent(),"","15","","col-sm-8 col-md-7",personalInfo,formUtil)%>
		</div>
	</div> 
	<div class="clearfix"></div>-->
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request, subformId, "PASSPORT_EXPIRE_DATE", "PASSPORT_EXPIRE_DATE", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.calendar("PASSPORT_EXPIRE_DATE", "",PersonalInfoUtil.getIdFieldExpireDocument(personalInfo.getCidType(),personalInfo), "PASSPORT_EXPIRE_DATE", personalInfo.getCidExpDate(),
			  "", "", HtmlUtil.EN, "col-sm-8 col-md-7", personalInfo, formUtil)%>
		</div>
	</div>
</div>
</div>
</div>
<%=HtmlUtil.hidden("THIS_PAGE",subformId) %>
<%=HtmlUtil.hidden("APPLY_DATE_EN",FormatUtil.display(applicationGroup.getApplyDate(), HtmlUtil.EN)) %>	
<%=HtmlUtil.hidden("CONSENT_DATE",FormatUtil.display(personalInfo.getConsentDate(), HtmlUtil.EN)) %>