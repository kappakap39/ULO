<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.AddressDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<%@page import="com.eaf.core.ulo.common.properties.ListBoxControl" %>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.orig.ulo.app.view.util.kpl.KPLUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>

<script type="text/javascript" src="orig/ulo/subform/js/OccupationSubForm.js"></script>
<%
	String subformId = "ALL_OCCUPATION_SUBFORM_3";
	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	Logger logger = Logger.getLogger(this.getClass());
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	PersonalInfoDataM personalInfo = ORIGForm.getObjectForm().getPersonalInfo(PERSONAL_TYPE);
	if(null == personalInfo){
		personalInfo = new PersonalInfoDataM();
	}
	int PERSONAL_SEQ = personalInfo.getSeq();
	String displayMode = HtmlUtil.EDIT;
	
// 	String TAG_SMART_DATA_PERSONAL = FormatUtil.getSmartDataEntryId(PERSONAL_TYPE,PERSONAL_SEQ);
	String personalElementId = FormatUtil.getPersonalElementId(personalInfo);
	
	String FIELD_ID_OCCUPATION = SystemConstant.getConstant("FIELD_ID_OCCUPATION");
	String FIELD_ID_PROFESSION = SystemConstant.getConstant("FIELD_ID_PROFESSION");
	String FIELD_ID_CONTACT_TIME = SystemConstant.getConstant("FIELD_ID_CONTACT_TIME");
	String FIELD_ID_BUSINESS_NATURE = SystemConstant.getConstant("FIELD_ID_BUSINESS_NATURE");
	String FIELD_ID_BUSINESS_TYPE = SystemConstant.getConstant("FIELD_ID_BUSINESS_TYPE");
	String FIELD_ID_COMPANY_TITLE = SystemConstant.getConstant("FIELD_ID_COMPANY_TITLE");	
	String FIELD_ID_POSITION_CODE = SystemConstant.getConstant("FIELD_ID_POSITION_CODE");	
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
	
	String ADDRESS_TYPE_WORK = SystemConstant.getConstant("ADDRESS_TYPE_WORK");
	String SUFFIX_WORK_ADDRESS = ADDRESS_TYPE_WORK;
	
	//KPL Addtional
	String FIELD_ID_SPECIAL_MERCHANT_TYPE = SystemConstant.getConstant("FIELD_ID_SPECIAL_MERCHANT_TYPE");
	Boolean isKPL = KPLUtil.isKPL(applicationGroup);
	Boolean isKPLTopup = KPLUtil.isKPL_TOPUP(applicationGroup);
	String TOPUP_FLAG = KPLUtil.getTopUpFlag(applicationGroup);
	String isKFC = KPLUtil.isKFC(applicationGroup);
	
	FormUtil formUtil = new FormUtil(subformId,request);
	
	logger.debug("personalInfo.getPrevCompany() : "+personalInfo.getPrevCompany());
	
%>
<div class="panel panel-default">
	
<div class="panel-heading"><%=LabelUtil.getText(request, "CURRENT_WORKPLACE")%></div>
<div class="panel-body">
<!-- <div class="row form-horizontal"> -->
<!-- 	<div class="col-sm-6 pull-right"> -->
<!-- 		<div class="row"> -->
<!-- 			<div class="col-sm-6 pull-right nopadding-left"> -->
<!-- 				<div class="form-group"> -->
<%-- 					<%=HtmlUtil.checkBoxInline("K_GROUP", "K_GROUP_"+TAG_SMART_DATA_PERSONAL, "K_GROUP",  --%>
<%-- 						personalInfo.getkGroupFlag(), "", MConstant.FLAG.YES, "", "K_GROUP", "control-label", formUtil) %> --%>
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 	</div> -->
<!-- </div>	 -->
<div class="row form-horizontal">
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.hidden("OCCUPATION_CODE", OCCUPATION_CODE) %>
			<%=HtmlUtil.getSubFormLabel(request,subformId,"OCCUPATION","OCCUPATION","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.popupList("OCCUPATION", "OCCUPATION_"+personalElementId, "OCCUPATION", 
				TEXT_OCCUPATION, "", "20", "", "col-sm-8 col-md-7", personalInfo, formUtil) %>
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
			<%=HtmlUtil.getSubFormLabel(request,subformId,"POSITION","POSITION","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBox("POSITION","","POSITION_"+personalElementId,"POSITION_DESC",
				personalInfo.getPositionDesc(),"","100","","col-sm-8 col-md-7",personalInfo,formUtil)%>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"POSITION_CODE","POSITION_CODE","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.dropdown("POSITION_CODE", "", "POSITION_CODE_"+personalElementId, "POSITION_CODE", "", 
				personalInfo.getPositionCode(), "",FIELD_ID_POSITION_CODE,"", "", "col-sm-8 col-md-7", personalInfo,formUtil)%>
		</div>
	</div>
	<div class="clearfix"></div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"POSITION_LEVEL","POSITION_LEVEL","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.dropdown("POSITION_LEVEL", "","POSITION_LEVEL_"+personalElementId, "POSITION_LEVEL", "", 
				personalInfo.getPositionLevel(), "",FIELD_ID_POSITION_LEVEL,"ALL_ALL_ALL", "", "col-sm-8 col-md-7", personalInfo, formUtil)%>
		</div>
	</div>
	<% if(!isKPL){ %>
	<!-- <div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"CONTACT_TIME","CONTACT_TIME","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.dropdown("CONTACT_TIME", "", "CONTACT_TIME_"+personalElementId, "CONTACT_TIME", "", 
				personalInfo.getContactTime(), "",FIELD_ID_CONTACT_TIME,"ALL_ALL_ALL", "", "col-sm-8 col-md-7", personalInfo, formUtil)%>
		</div>
	</div> -->
	<% } %>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId, personalInfo, "TOT_WORK","TOT_WORK","col-sm-4 col-md-5 control-label")%>
			<div class="col-sm-8 col-md-7">
				<div class="row">
					<div class="col-xs-12">
						<%=HtmlUtil.numberBox("TOT_WORK_YEAR", "", "TOT_WORK_YEAR_"+personalElementId, "TOT_WORK_YEAR",
							personalInfo.getTotWorkYear(), "", "##", "", "", true, "2", "", "col-xs-4 col-xs-padding", personalInfo, formUtil) %>
						<%=HtmlUtil.getLabel(request, "TOT_WORK_YEAR","col-xs-2 control-label")%>
						<%=HtmlUtil.numberBox("TOT_WORK_MONTH", "","TOT_WORK_MONTH_"+personalElementId, "TOT_WORK_MONTH",
							personalInfo.getTotWorkMonth(), "", "##", "", "", true, "2", "", "col-xs-4 col-xs-padding", personalInfo, formUtil) %>
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
				TEXT_BUSINESS_TYPE, "", "100", "", "col-sm-8 col-md-7",personalInfo, formUtil) %>
		</div>
	</div>
	<div class="clearfix"></div>
	<!-- <div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request, subformId,"BUSINESS_NATURE","BUSINESS_NATURE", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.dropdown("BUSINESS_NATURE", "", "BUSINESS_NATURE_"+personalElementId, "BUSINESS_NATURE", "", 
						personalInfo.getBusinessNature(), "",FIELD_ID_BUSINESS_NATURE,"ALL_ALL_ALL", "", "col-sm-8 col-md-7", personalInfo, formUtil)%>
		</div>
	</div> -->
	<% if(isKPL){ %>
	<div class="clearfix"></div>
		<div class="col-sm-6">
			<div class="form-group">
				<%=HtmlUtil.getSubFormLabel(request,subformId,"SPECIAL_MERCHANT_TYPE","SPECIAL_MERCHANT_TYPE","col-sm-4 col-md-5 control-label")%>
				<%=HtmlUtil.dropdown("SPECIAL_MERCHANT_TYPE", "SPECIAL_MERCHANT_TYPE_"+personalElementId, "SPECIAL_MERCHANT_TYPE", "", 
				personalInfo.getSpecialMerchantType(), "",FIELD_ID_SPECIAL_MERCHANT_TYPE,"ALL_ALL_ALL", "", "col-sm-8 col-md-7", formUtil)%>
			</div>
		</div>
	<% }%>
	<div class="clearfix"></div>
</div>
</div>
<!--<div class="panel-heading"><%=LabelUtil.getText(request,"COMPANY_OLD")%></div>
<div class="panel-body">  		
<div class="row form-horizontal">
	<div class="col-md-12">
		<div class="form-group">
			<%--<%=HtmlUtil.getSubFormLabel(request, subformId,"PREV_COMPANY","PREV_COMPANY", "col-sm-2 col-md-5 marge-label control-label")%>
			<div class="col-sm-10 col-md-9 marge-field">
				<div class="row">
					<div class="col-xs-12">
						<%=HtmlUtil.dropdown("PREV_COMPANY_TITLE", "", "PREV_COMPANY_TITLE_"+personalElementId, "PREV_COMPANY_TITLE", "", 
							personalInfo.getPrevCompanyTitle(), "dropdown-textbox-1",FIELD_ID_COMPANY_TITLE,"ALL_ALL_ALL","","col-xs-2 col-xs-padding",personalInfo, formUtil)%>
						<%=HtmlUtil.autoComplete("PREV_COMPANY", "", "PREV_COMPANY_"+personalElementId, "PREV_COMPANY_NAME", "COMPANY_NAME_LISTBOX", 
							personalInfo.getPrevCompany(), "", "","ALL_ALL_ALL","","col-xs-5 col-xs-padding", "100", personalInfo,formUtil)%>
					</div>
				</div>
			</div>
		</div>
	</div>	
	<div class="clearfix"></div>
	<% if(!isKPL) {
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"OFFICE_PHONE","OFFICE_PHONE","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBoxTel("PREV_COMPANY_PHONE_NO","","PREV_COMPANY_PHONE_NO_"+personalElementId,"PREV_COMPANY_OFFICE_PHONE",
				personalInfo.getPrevCompanyPhoneNo(),"","","col-sm-8 col-md-7",personalInfo,formUtil)%>
		</div>
	</div>
	<% } %>
    <div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId, personalInfo, "PREV_WORK","PREV_WORK","col-sm-4 col-md-5 control-label")%>
			<div class="col-sm-8 col-md-7">
				<div class="row">
					<div class="col-xs-12">
						<%=HtmlUtil.numberBox("PREV_WORK_YEAR", "", "PREV_WORK_YEAR_"+personalElementId, "PREV_WORK_YEAR",
							personalInfo.getPrevWorkYear(), "", "##","","", true, "2", "", "col-xs-4 col-xs-padding",personalInfo, formUtil) %>
						<%=HtmlUtil.getLabel(request, "TOT_WORK_YEAR","col-xs-2 control-label")%>
						<%=HtmlUtil.numberBox("PREV_WORK_MONTH", "","PREV_WORK_MONTH_"+personalElementId, "PREV_WORK_MONTH",
							personalInfo.getPrevWorkMonth(), "", "##","","", true, "2", "", "col-xs-4 col-xs-padding",personalInfo, formUtil) %>
						<%=HtmlUtil.getLabel(request,"TOT_WORK_MONTH","col-xs-2 control-label")%>--%>
					</div>
				</div>
			</div>
		</div>
	</div> -->
<!-- 	<div class="clearfix"></div> -->
<!-- </div> -->
<!-- </div> -->

</div>


<%=HtmlUtil.hidden("PERSONAL_SEQ",String.valueOf(PERSONAL_SEQ)) %>
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