<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>

<script type="text/javascript" src="orig/ulo/subform/js/SpouseInfoSubForm.js"></script>
<%
	String subformId = "SPOUSE_INFO_SUBFORM";
	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");	
	PersonalInfoDataM personalInfo = ORIGForm.getObjectForm().getPersonalInfo(PERSONAL_TYPE);
	if(null == personalInfo){
		personalInfo = new PersonalInfoDataM();
	}
	int PERSONAL_SEQ = personalInfo.getSeq();	
	String displayMode = HtmlUtil.EDIT;
// 	String TAG_SMART_DATA_PERSONAL = FormatUtil.getSmartDataEntryId(PERSONAL_TYPE,PERSONAL_SEQ);
	String personalElementId = FormatUtil.getPersonalElementId(personalInfo);
	String FIELD_ID_TH_TITLE_CODE = SystemConstant.getConstant("FIELD_ID_TH_TITLE_CODE");
	String TAG_ID_TH_MID_NAME = HtmlUtil.elementTagId("TH_MID_NAME",personalElementId) + " tabindex='-1' placeholder='" 
	+ LabelUtil.getText(request, "SHOW_TH_MID_NAME") + "'";
	
	FormUtil formUtil = new FormUtil(subformId,request);
 %>
<div class="panel panel-default">
<div class="panel-body">
<div class="row form-horizontal">
	<div class="col-sm-12">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"M_TH_NAME","M_TH_NAME", "col-sm-2 col-md-5 marge-label control-label")%>
			<div class="col-sm-10 col-md-9 marge-field">
				<div class="row">
					<div class="col-xs-12">
						<%=HtmlUtil.hidden("M_TITLE_CODE", personalInfo.getmTitleCode())%>
						<%=HtmlUtil.autoComplete("M_TITLE_DESC", "M_TITLE_DESC_"+personalElementId, "M_TITLE_DESC", "", 
							personalInfo.getmTitleDesc(), "", FIELD_ID_TH_TITLE_CODE,"ALL_ALL_ALL", "", "col-xs-3 col-xs-padding", formUtil)%>
						<div class="col-xs-9">
							<div class="input-group">
								<%=HtmlUtil.textBox("M_TH_FIRST_NAME","M_TH_FIRST_NAME_"+personalElementId,"M_TH_FIRST_NAME",
	                				personalInfo.getmThFirstName(),"textbox-name-1","120","","input-group-btn",formUtil)%>
	                			<%=HtmlUtil.textBox("M_TH_LAST_NAME","M_TH_LAST_NAME_"+personalElementId,"M_TH_LAST_NAME",
	                				personalInfo.getmThLastName(),"textbox-name-3","50","","input-group-btn",formUtil)%>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"M_TH_OLD_LAST_NAME","M_TH_OLD_LAST_NAME","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBox("M_TH_OLD_LAST_NAME","M_TH_OLD_LAST_NAME_"+personalElementId,"M_TH_OLD_LAST_NAME",
				personalInfo.getmThOldLastName(),"","50","","col-sm-8 col-md-7",formUtil)%>
		</div>
	</div>
	<div class="clearfix"></div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"M_COMPANY","M_COMPANY","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBox("M_COMPANY","M_COMPANY_"+personalElementId,"M_COMPANY",
				personalInfo.getmCompany(),"","50","","col-sm-8 col-md-7",formUtil)%>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"MTL_SALES_PHONE_NO","MTL_SALES_PHONE_NO","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBoxTel("M_HOME_TEL","M_HOME_TEL_"+personalElementId,"M_HOME_TEL",
				personalInfo.getmHomeTel(),"","","col-sm-8 col-md-7",formUtil)%>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request, subformId,"M_INCOME","M_INCOME", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.currencyBox("M_INCOME", "M_INCOME_"+personalElementId, "M_INCOME", 
				personalInfo.getmIncome(), "", false, "15", "", "col-sm-8 col-md-7", formUtil) %>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request, subformId, "M_POSITION", "M_POSITION", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBox("M_POSITION","M_POSITION_"+personalElementId,"M_POSITION",
				personalInfo.getmPosition(),"","100","","col-sm-8 col-md-7",formUtil)%>
		</div>
	</div>
		
		
</div>
</div>
</div>
