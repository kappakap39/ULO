<%@page import="com.google.gson.Gson"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.ListBoxControl"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page contentType="text/html; charset=UTF-8"%>
<script type="text/javascript" src="orig/ulo/subform/js/OccupationSubForm.js"></script>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>

<%
	String subformId = "INCREASE_OCCUPATION_SUBFORM";
	Logger logger = Logger.getLogger(this.getClass());	
	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	PersonalInfoDataM personalInfo = ORIGForm.getObjectForm().getPersonalInfo(PERSONAL_TYPE);
	if(null == personalInfo){
		personalInfo = new PersonalInfoDataM();
	}
	int PERSONAL_SEQ = personalInfo.getSeq();	
	String displayMode = HtmlUtil.EDIT;
	String personalElementId = FormatUtil.getPersonalElementId(personalInfo);
		
	String FIELD_ID_CONTACT_TIME = SystemConstant.getConstant("FIELD_ID_CONTACT_TIME");
	String FIELD_ID_POSITION_CODE = SystemConstant.getConstant("FIELD_ID_POSITION_CODE");
	String FIELD_ID_OCCUPATION = SystemConstant.getConstant("FIELD_ID_OCCUPATION");
	String FIELD_ID_PROFESSION = SystemConstant.getConstant("FIELD_ID_PROFESSION");	
	String FIELD_ID_BUSINESS_TYPE = SystemConstant.getConstant("FIELD_ID_BUSINESS_TYPE");	
	String FIELD_ID_POSITION_LEVEL = SystemConstant.getConstant("FIELD_ID_POSITION_LEVEL");
	String OCCUPATION_OTHER = SystemConstant.getConstant("OCCUPATION_OTHER");
	String PROFESSION_OTHER = SystemConstant.getConstant("PROFESSION_OTHER");	
	String BUSINESS_TYPE_OTHER = SystemConstant.getConstant("BUSINESS_TYPE_OTHER");	
	
	String TEXT_OCCUPATION = ListBoxControl.getName(FIELD_ID_OCCUPATION,personalInfo.getOccupation());
	logger.debug("TEXT_OCCUPATION >> "+TEXT_OCCUPATION);
	logger.debug("personalInfo.getOccupation() >> "+personalInfo.getOccupation());
	if(OCCUPATION_OTHER.equals(personalInfo.getOccupation())){
		TEXT_OCCUPATION = personalInfo.getOccpationOther();
	}
	String TEXT_PROFESSION = ListBoxControl.getName(FIELD_ID_PROFESSION,personalInfo.getProfession());
	if(PROFESSION_OTHER.equals(personalInfo.getProfession())){
		TEXT_PROFESSION = personalInfo.getProfessionOther();
	}
	String TEXT_BUSINESS_TYPE = ListBoxControl.getName(FIELD_ID_BUSINESS_TYPE,personalInfo.getBusinessType());
	if(BUSINESS_TYPE_OTHER.equals(personalInfo.getBusinessType())){
		TEXT_BUSINESS_TYPE = personalInfo.getBusinessTypeOther();
	}
	String OCCUPATION_CODE = personalInfo.getOccupation();
	FormUtil formUtil = new FormUtil(subformId,request);
%>
<div class="panel panel-default">
	<div class="panel-heading"><%=LabelUtil.getText(request, "CURRENT_WORKPLACE")%></div>
	<div class="panel-body">  		
		<div class="row form-horizontal">
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.hidden("OCCUPATION_CODE", OCCUPATION_CODE) %>
					<%=HtmlUtil.getSubFormLabel(request,subformId,"OCCUPATION","OCCUPATION","col-sm-4 col-md-5 control-label")%>
					<%=HtmlUtil.popupList("OCCUPATION", "OCCUPATION_"+personalElementId, "OCCUPATION", 
						TEXT_OCCUPATION, "", "20", "", "col-sm-8 col-md-7",personalInfo, formUtil) %>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request,subformId,"PROFESSION","PROFESSION","col-sm-4 col-md-5 control-label")%>
					<%=HtmlUtil.popupList("PROFESSION", "PROFESSION_"+personalElementId, "PROFESSION", 
						TEXT_PROFESSION, "", "20", "", "col-sm-8 col-md-7",personalInfo, formUtil) %>
				</div>
			</div>
			<div class="clearfix"></div>
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request,subformId,"POSITION_CODE","POSITION_CODE","col-sm-4 col-md-5 control-label")%>
					<%=HtmlUtil.dropdown("POSITION_CODE", "", "POSITION_CODE_"+personalElementId, "POSITION_CODE", "", 
						personalInfo.getPositionCode(), "",FIELD_ID_POSITION_CODE,"", "", "col-sm-8 col-md-7", personalInfo, formUtil)%>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request,subformId,"POSITION_LEVEL","POSITION_LEVEL","col-sm-4 col-md-5 control-label")%>
					<%=HtmlUtil.dropdown("POSITION_LEVEL", "", "POSITION_LEVEL_"+personalElementId, "POSITION_LEVEL", "", 
						personalInfo.getPositionLevel(), "",FIELD_ID_POSITION_LEVEL,"ALL_ALL_ALL", "", "col-sm-8 col-md-7", personalInfo, formUtil)%>
				</div>
			</div>
			<div class="clearfix"></div>
		</div>
	</div>
</div>