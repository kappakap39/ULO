<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.properties.ListBoxControl" %>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>

<script type="text/javascript" src="orig/ulo/subform/js/OccupationSubForm.js"></script>
<%
	String subformId = "BUNDLING_SME_OCCUPATION_SUBFORM_3";
	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");	
	Logger logger = Logger.getLogger(this.getClass());
	PersonalInfoDataM personalInfo = ORIGForm.getObjectForm().getPersonalInfo(PERSONAL_TYPE);
	if(null == personalInfo){
		personalInfo = new PersonalInfoDataM();
	}
	int PERSONAL_SEQ = personalInfo.getSeq();	
	String displayMode = HtmlUtil.EDIT;
// 	String TAG_SMART_DATA_PERSONAL = FormatUtil.getSmartDataEntryId(PERSONAL_TYPE,PERSONAL_SEQ);
	String personalElementId = FormatUtil.getPersonalElementId(personalInfo);
		
	String FIELD_ID_OCCUPATION = SystemConstant.getConstant("FIELD_ID_OCCUPATION");
	String FIELD_ID_BUSINESS_NATURE = SystemConstant.getConstant("FIELD_ID_BUSINESS_NATURE");
	String OCCUPATION_OTHER = SystemConstant.getConstant("OCCUPATION_OTHER");
	String PROFESSION_OTHER = SystemConstant.getConstant("PROFESSION_OTHER");	
	String BUSINESS_TYPE_OTHER = SystemConstant.getConstant("BUSINESS_TYPE_OTHER");	
	
	String textOCCUPATION = ListBoxControl.getName(FIELD_ID_OCCUPATION,personalInfo.getOccupation());
	if(OCCUPATION_OTHER.equals(personalInfo.getOccupation())){
		textOCCUPATION = personalInfo.getOccpationOther();
	}
	
	String occupationCode = personalInfo.getOccupation();
	
	FormUtil formUtil = new FormUtil(subformId,request);
 %>
 
<div class="panel panel-default">
	
<div class="panel-heading"><%=LabelUtil.getText(request, "WORKPLACE")%></div>
<div class="panel-body">  		
<div class="row form-horizontal">
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.hidden("OCCUPATION_CODE", occupationCode) %>
			<%=HtmlUtil.getSubFormLabel(request,subformId,"OCCUPATION","OCCUPATION","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.popupList("OCCUPATION", "OCCUPATION_"+personalElementId, "OCCUPATION", 
				textOCCUPATION, "", "20", "", "col-sm-8 col-md-7",personalInfo, formUtil) %>
		</div>
	</div>
	<!-- <div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request, subformId,"BUSINESS_NATURE","BUSINESS_NATURE", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.dropdown("BUSINESS_NATURE", "","BUSINESS_NATURE_"+personalElementId, "BUSINESS_NATURE", "", 
				personalInfo.getBusinessNature(), "",FIELD_ID_BUSINESS_NATURE,"ALL_ALL_ALL", "", "col-sm-8 col-md-7",personalInfo, formUtil)%>
		</div>
	</div> -->
	<div class="clearfix"></div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"POSITION","POSITION","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBox("POSITION","","POSITION_"+personalElementId,"POSITION",
				personalInfo.getPositionDesc(),"","100","","col-sm-8 col-md-7",personalInfo,formUtil)%>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"WORKPLACE_OLD","WORKPLACE_OLD","col-sm-4 col-md-5 control-label")%>
			<div class="col-sm-8 col-md-7">
				<div class="row">
					<div class="col-xs-12">
						<%=HtmlUtil.numberBox("TOT_WORK_YEAR","", "TOT_WORK_YEAR_"+personalElementId, "TOT_WORK",
							personalInfo.getTotWorkYear(), "", "##","","", true, "2", "", "col-xs-4 col-xs-padding",personalInfo, formUtil) %>
						<%=HtmlUtil.getLabel(request, "TOT_WORK_YEAR","col-xs-2 control-label")%>
						<%=HtmlUtil.numberBox("TOT_WORK_MONTH","", "TOT_WORK_MONTH_"+personalElementId, "TOT_WORK",
							personalInfo.getTotWorkMonth(), "", "##","","", true, "2", "", "col-xs-4 col-xs-padding",personalInfo, formUtil) %>
						<%=HtmlUtil.getLabel(request,"TOT_WORK_MONTH","col-xs-2 control-label")%>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
</div>
</div>

</div>
 


<%=HtmlUtil.hidden("PERSONAL_SEQ",String.valueOf(PERSONAL_SEQ)) %>
