<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.ListBoxControl"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.orig.ulo.control.util.ApplicationUtil"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<script type="text/javascript" src="orig/ulo/subform/verification/js/CurrentWorkplaceSubForm.js"></script>
<%
Logger logger = Logger.getLogger(this.getClass());
String subformId = "VERIFICATION_CUSTOMER_INFO_SUBFORM";
String displayMode = HtmlUtil.EDIT;
String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");	
String FIELD_ID_OCCUPATION = SystemConstant.getConstant("FIELD_ID_OCCUPATION");
String FIELD_ID_PROFESSION = SystemConstant.getConstant("FIELD_ID_PROFESSION");	
String OCCUPATION_OTHER = SystemConstant.getConstant("OCCUPATION_OTHER");
String PROFESSION_OTHER = SystemConstant.getConstant("PROFESSION_OTHER");	

PersonalInfoDataM personalInfo = ORIGForm.getObjectForm().getPersonalInfo(PERSONAL_TYPE);
if(Util.empty(personalInfo)){
 	personalInfo = new  PersonalInfoDataM();
}
ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	if (null == applicationGroup) {
		applicationGroup = new ApplicationGroupDataM();
	}
String textOCCUPATION = ListBoxControl.getName(FIELD_ID_OCCUPATION,personalInfo.getOccupation());
if(OCCUPATION_OTHER.equals(personalInfo.getOccupation())){
	textOCCUPATION = personalInfo.getOccpationOther();
}
String textPROFESSION = ListBoxControl.getName(FIELD_ID_PROFESSION,personalInfo.getProfession());
if(PROFESSION_OTHER.equals(personalInfo.getProfession())){
	textPROFESSION = personalInfo.getProfessionOther();
}
FormUtil formUtil = new FormUtil(subformId,request);
String source = applicationGroup.getSource();

 %>
 <div class=" panel-default panel">
	 <div class="panel-heading"><%=HtmlUtil.getLabel(request, "CURRENT_WORKPLACE","")%></div>	
		<div class="panel-body">
			<div class="row form-horizontal">
				<div class="clearfix"></div> 
					<div class="col-sm-6">
						<div class="form-group">
							<%=HtmlUtil.getSubFormLabel(request,subformId,"OCCUPATION","OCCUPATION","col-sm-4 col-md-5 control-label")%>
							<%=HtmlUtil.popupList("OCCUPATION", "", "OCCUPATION", textOCCUPATION, "", "20", "", "col-sm-8 col-md-7", formUtil) %>
						</div>
					</div>
					<div class="col-sm-6">
						<div class="form-group">
							<%=HtmlUtil.getSubFormLabel(request,subformId,"PROFESSION","PROFESSION","col-sm-4 col-md-5 control-label")%>
							<%=HtmlUtil.popupList("PROFESSION", "", "PROFESSION",textPROFESSION, "", "20", "", "col-sm-8 col-md-7", formUtil) %>
						</div>
					</div>
					<div class="col-md-4 col-md-offset-5">
						<div class="form-group">
						<%if(Util.empty(personalInfo.getDisplayEditBTN()) || personalInfo.getDisplayEditBTN().equals(MConstant.FLAG_N)){%>
							<%if((ApplicationUtil.eApp(source)) || (ApplicationUtil.cjd(source))){%>
								<%=HtmlUtil.button("EDIT_BTN", "EDIT_BTN", HtmlUtil.VIEW, "btn btn-primary", "", request) %>
							<%} else {%>
								<%=HtmlUtil.button("EDIT_BTN", "EDIT_BTN", HtmlUtil.EDIT, "btn btn-primary", "", request) %>
							<%} %>
						<%} else if(personalInfo.getDisplayEditBTN().equals(MConstant.FLAG_Y)){%>
							<%=HtmlUtil.button("EXECUTE_BTN", "EXECUTE_BTN", HtmlUtil.EDIT, "btn btn-primary", "", request) %> 
						<%} %>
						</div>
					</div>
			</div>
		</div>
</div>
<%=HtmlUtil.hidden("VERIFICATION_PERSONAL_SEQ",String.valueOf(personalInfo.getSeq())) %>