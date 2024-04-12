<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.orig.ulo.app.view.util.kpl.KPLUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.ListBoxControl" %>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<script type="text/javascript" src="orig/ulo/subform/js/OccupationSubForm.js?v=1"></script>
<%
	String subformId = "ALL_OCCUPATION_SUBFORM_1";
	Logger logger = Logger.getLogger(this.getClass());	
	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	PersonalInfoDataM personalInfo = ORIGForm.getObjectForm().getPersonalInfo(PERSONAL_TYPE);
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	if(null == personalInfo){
		personalInfo = new PersonalInfoDataM();
	}
	int PERSONAL_SEQ = personalInfo.getSeq();	
	String displayMode = HtmlUtil.EDIT;
	
// 	String TAG_SMART_DATA_PERSONAL = FormatUtil.getSmartDataEntryId(PERSONAL_TYPE,PERSONAL_SEQ);
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
	
	//KPL Addtional
	String FIELD_ID_SPECIAL_MERCHANT_TYPE = SystemConstant.getConstant("FIELD_ID_SPECIAL_MERCHANT_TYPE");
	Boolean isKPL = KPLUtil.isKPL(applicationGroup);
	Boolean isKPLTopup = KPLUtil.isKPL_TOPUP(applicationGroup);
	String TOPUP_FLAG = KPLUtil.getTopUpFlag(applicationGroup);
	String isKFC = KPLUtil.isKFC(applicationGroup);
	
	FormUtil formUtil = new FormUtil(subformId,request);
%>

<div class="panel panel-default">
	
	<div class="panel-heading"><%=LabelUtil.getText(request, "CURRENT_WORKPLACE")%></div>
	<div class="panel-body">  		
		<div class="row form-horizontal">
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.hidden("OCCUPATION_CODE", OCCUPATION_CODE)%>
					<%=HtmlUtil.getSubFormLabel(request,subformId,"OCCUPATION"+TOPUP_FLAG,"OCCUPATION","col-sm-4 col-md-5 control-label")%>
					<%=HtmlUtil.popupList("OCCUPATION", "OCCUPATION_"+personalElementId, "OCCUPATION", 
						TEXT_OCCUPATION, "", "20", "", "col-sm-8 col-md-7", formUtil)%>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request,subformId,"PROFESSION"+TOPUP_FLAG,"PROFESSION","col-sm-4 col-md-5 control-label")%>
					<%=HtmlUtil.popupList("PROFESSION", "PROFESSION_"+personalElementId, "PROFESSION", 
						TEXT_PROFESSION, "", "20", "", "col-sm-8 col-md-7", formUtil)%>
				</div>
			</div>
			<div class="clearfix"></div>
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request,subformId,"POSITION"+TOPUP_FLAG,"POSITION","col-sm-4 col-md-5 control-label")%>
					<%=HtmlUtil.textBox("POSITION","POSITION_"+personalElementId,"POSITION",
						personalInfo.getPositionDesc(),"","100","","col-sm-8 col-md-7",formUtil)%>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request,subformId,"POSITION_CODE"+TOPUP_FLAG,"POSITION_CODE","col-sm-4 col-md-5 control-label")%>
					<%=HtmlUtil.dropdown("POSITION_CODE", "POSITION_CODE_"+personalElementId, "POSITION_CODE", "", 
						personalInfo.getPositionCode(), "",FIELD_ID_POSITION_CODE,"", "", "col-sm-8 col-md-7", formUtil)%>
				</div>
			</div>
			<div class="clearfix"></div>
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request,subformId,"POSITION_LEVEL","POSITION_LEVEL","col-sm-4 col-md-5 control-label")%>
					<%=HtmlUtil.dropdown("POSITION_LEVEL", "POSITION_LEVEL_"+personalElementId, "POSITION_LEVEL", "", 
						personalInfo.getPositionLevel(), "",FIELD_ID_POSITION_LEVEL,"ALL_ALL_ALL", "", "col-sm-8 col-md-7", formUtil)%>
				</div>
			</div>
			<%
				if(!isKPL){
			%>
			<!-- <div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request,subformId,"CONTACT_TIME","CONTACT_TIME","col-sm-4 col-md-5 control-label")%>
					<%=HtmlUtil.dropdown("CONTACT_TIME", "CONTACT_TIME_"+personalElementId, "CONTACT_TIME", "", 
						personalInfo.getContactTime(), "",FIELD_ID_CONTACT_TIME,"ALL_ALL_ALL", "", "col-sm-8 col-md-7", formUtil)%>
				</div>
			</div>  -->
			<%
				}
			%>
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request,subformId,personalInfo,"TOT_WORK"+TOPUP_FLAG,"TOT_WORK","col-sm-4 col-md-5 control-label")%>
					<div class="col-sm-8 col-md-7">
						<div class="row">
							<div class="col-xs-12">
								<%=HtmlUtil.numberBox("TOT_WORK_YEAR", "TOT_WORK_YEAR_"+personalElementId, "TOT_WORK",
									FormatUtil.toBigDecimal(personalInfo.getTotWorkYear(), true), "", "##", true, "2", "", "col-xs-4 col-xs-padding", formUtil)%>
								<%=HtmlUtil.getLabel(request, "TOT_WORK_YEAR","col-xs-2 control-label")%>
								<%=HtmlUtil.numberBox("TOT_WORK_MONTH", "TOT_WORK_MONTH_"+personalElementId, "TOT_WORK",
									FormatUtil.toBigDecimal(personalInfo.getTotWorkMonth(), true), "", "##", true, "2", "", "col-xs-4 col-xs-padding", formUtil)%>
								<%=HtmlUtil.getLabel(request,"TOT_WORK_MONTH","col-xs-2 control-label")%>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="clearfix"></div>
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request,subformId,"BUSINESS_TYPE","BUSINESS_TYPE","col-sm-4 col-md-5 control-label")%>
					<%=HtmlUtil.popupList("BUSINESS_TYPE", "BUSINESS_TYPE_"+personalElementId, "BUSINESS_TYPE", 
						TEXT_BUSINESS_TYPE, "", "100", "", "col-sm-8 col-md-7", formUtil)%>
				</div>
			</div>
			<%
				if(isKPL){
			%>
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request,subformId,"SPECIAL_MERCHANT_TYPE","SPECIAL_MERCHANT_TYPE","col-sm-4 col-md-5 control-label")%>
					<%=HtmlUtil.dropdown("SPECIAL_MERCHANT_TYPE", "SPECIAL_MERCHANT_TYPE_"+personalElementId, "SPECIAL_MERCHANT_TYPE", "", 
						personalInfo.getSpecialMerchantType(), "",FIELD_ID_SPECIAL_MERCHANT_TYPE,"ALL_ALL_ALL", "", "col-sm-8 col-md-7", formUtil)%>
				</div>
			</div>
			<%
				}
			%>
		</div>
	</div>
		
	<!--<div class="panel-heading"><%=LabelUtil.getText(request,"COMPANY_OLD")%></div>
	 <div class="panel-body">  		
		<div class="row form-horizontal">
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request,subformId,personalInfo,"PREV_WORK","PREV_WORK","col-sm-4 col-md-5 control-label")%>
					<div class="col-sm-8 col-md-7">
						<div class="row">
							<div class="col-xs-12">
								<%=HtmlUtil.numberBox("PREV_WORK_YEAR", "PREV_WORK_YEAR_"+personalElementId, "PREV_WORK",
									personalInfo.getPrevWorkYear(), "", "##", true, "2", "", "col-xs-4 col-xs-padding", formUtil)%>
								<%=HtmlUtil.getLabel(request, "TOT_WORK_YEAR","col-xs-2 control-label")%>
								<%=HtmlUtil.numberBox("PREV_WORK_MONTH", "PREV_WORK_MONTH_"+personalElementId, "PREV_WORK",
									personalInfo.getPrevWorkMonth(), "", "##", true, "2", "", "col-xs-4 col-xs-padding", formUtil)%>
								<%=HtmlUtil.getLabel(request, "TOT_WORK_MONTH","col-xs-2 control-label")%>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="clearfix"></div>
		</div>
	</div> -->	
</div>
<%=HtmlUtil.hidden("PERSONAL_SEQ",String.valueOf(PERSONAL_SEQ))%>
<%=HtmlUtil.hidden("KFC",isKFC)%>
<% if(isKPLTopup){ %>
	<script>
  		//Make element mode = EDIT
  		targetDisplayHtml('POSITION_CODE',MODE_EDIT,'POSITION_CODE','Y');
	</script>
<% } %>
<% if(isKPL){ %>
<script>
//KPL Additional Detect Occupation for Speical Business Type Dropdown
$("[name='OCCUPATION']").on('change', function() {
		SpecialMerchantConfig();
});
$("[name='OCCUPATION']").change();
</script>
<% } %>