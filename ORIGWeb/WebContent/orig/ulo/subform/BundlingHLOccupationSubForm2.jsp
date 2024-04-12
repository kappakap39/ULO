<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.core.ulo.common.properties.ListBoxControl" %>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>

<script type="text/javascript" src="orig/ulo/subform/js/OccupationSubForm.js"></script>
<%
	String subformId = "BUNDLING_HL_OCCUPATION_SUBFORM_2";
	Logger logger = Logger.getLogger(this.getClass());
	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");	
	PersonalInfoDataM personalInfo = ORIGForm.getObjectForm().getPersonalInfo(PERSONAL_TYPE);
	if(null == personalInfo){
		personalInfo = new PersonalInfoDataM();
	}
	int PERSONAL_SEQ = personalInfo.getSeq();
	
	String displayMode = HtmlUtil.EDIT;
// 	String TAG_SMART_DATA_PERSONAL = FormatUtil.getSmartDataEntryId(PERSONAL_TYPE,PERSONAL_SEQ);
	String personalElementId = FormatUtil.getPersonalElementId(personalInfo);
	
	String FIELD_ID_OCCUPATION = SystemConstant.getConstant("FIELD_ID_OCCUPATION");
	String FIELD_ID_POSITION_LEVEL = SystemConstant.getConstant("FIELD_ID_POSITION_LEVEL");
	String OCCUPATION_OTHER = SystemConstant.getConstant("OCCUPATION_OTHER");
		
	String textOCCUPATION = ListBoxControl.getName(FIELD_ID_OCCUPATION,personalInfo.getOccupation());
	if(OCCUPATION_OTHER.equals(personalInfo.getOccupation())){
		textOCCUPATION = personalInfo.getOccpationOther();
	}
	
	FormUtil formUtil = new FormUtil(subformId,request);
%>
<div class="panel panel-default">
	
<div class="panel-heading"><%=LabelUtil.getText(request, "COMPANY_ADDRESS_DATA")%></div>
<div class="panel-body">  		
<div class="row form-horizontal">
<!-- 	<div class="col-sm-9"> -->
<!-- 		<div class="form-group"></div> -->
<!-- 	</div> -->
<!-- 	<div class="col-sm-3"> -->
<!-- 		<div class="form-group"> -->
<%-- 			<%=HtmlUtil.checkBox("K_GROUP", "K_GROUP_"+TAG_SMART_DATA_PERSONAL, "K_GROUP",  --%>
<%-- 				personalInfo.getkGroupFlag(), "", MConstant.FLAG.YES, "", "K_GROUP", "col-xs-3 control-label col-xs-padding", formUtil) %> --%>
<!-- 		</div> -->
<!-- 	</div> -->
<!-- 	<div class="clearfix"></div> -->
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"OCCUPATION","OCCUPATION","col-sm-4 col-md-5 control-label")%>
			<div class="col-sm-8 col-md-7">
				<div class="row">
					<div class="col-xs-12">
						<%=HtmlUtil.hidden("OCCUPATION_CODE", occupationCode) %>
						<%=HtmlUtil.popupList("OCCUPATION", "OCCUPATION_"+personalElementId, "OCCUPATION", 
							textOCCUPATION, "", "20", "", "col-xs-6 col-xs-padding", formUtil) %>
						<%=HtmlUtil.getFieldLabel(request,"LEVEL","col-xs-3 control-label")%>
						<%=HtmlUtil.textBox("LEVEL","LEVEL_"+personalElementId,"LEVEL",
							personalInfo.getPositionLevel(),"","","","col-xs-3 col-xs-padding",formUtil)%>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"POSITION","POSITION","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBox("POSITION","POSITION_"+personalElementId,"POSITION",
				personalInfo.getPositionDesc(),"","100","","col-sm-8 col-md-7",formUtil)%>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"POSITION_LEVEL","POSITION_LEVEL","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.dropdown("POSITION_LEVEL", "POSITION_LEVEL_"+personalElementId, "POSITION_LEVEL", "", 
				personalInfo.getPositionLevel(), "",FIELD_ID_POSITION_LEVEL,"ALL_ALL_ALL", "", "col-sm-8 col-md-7", formUtil)%>
		</div>
	</div>
	<div class="clearfix"></div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,personalInfo,"WORKPLACE_OLD","WORKPLACE_OLD","col-sm-4 col-md-5 control-label")%>
			<div class="col-sm-8 col-md-7">
				<div class="row">
					<div class="col-xs-12">
						<%=HtmlUtil.numberBox("TOT_WORK_YEAR", "TOT_WORK_YEAR_"+personalElementId, "TOT_WORK",
							personalInfo.getTotWorkYear(), "", "##", true, "2", "", "col-xs-4 col-xs-padding", formUtil) %>
						<%=HtmlUtil.getLabel(request, "TOT_WORK_YEAR","col-xs-2 control-label")%>
						<%=HtmlUtil.numberBox("TOT_WORK_MONTH", "TOT_WORK_MONTH_"+personalElementId, "TOT_WORK",
							personalInfo.getTotWorkMonth(), "", "##", true, "2", "", "col-xs-4 col-xs-padding", formUtil) %>
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