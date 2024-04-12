<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.KYCDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.orig.ulo.app.view.util.kpl.KPLUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>

<script type="text/javascript" src="orig/ulo/subform/js/KYCSubForm.js"></script>
<%
	String subformId = "KYC_SUBFORM";
	Logger logger = Logger.getLogger(this.getClass());
	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");	
	PersonalInfoDataM personalInfo  = ORIGForm.getObjectForm().getPersonalInfo(PERSONAL_TYPE);
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	if(Util.empty(personalInfo)){
		personalInfo = new PersonalInfoDataM();
	}
	int PERSONAL_SEQ = personalInfo.getSeq();	
	
	KYCDataM kycd = personalInfo.getKyc();
	if(Util.empty(kycd)){
		kycd = new KYCDataM();
	}
	
	String displayMode = HtmlUtil.EDIT;
// 	String TAG_SMART_DATA_PERSONAL = FormatUtil.getSmartDataEntryId(PERSONAL_TYPE,PERSONAL_SEQ);

	String personalElementId = FormatUtil.getPersonalElementId(personalInfo);
	String FIELD_ID_TH_TITLE_CODE = SystemConstant.getConstant("FIELD_ID_TH_TITLE_CODE");
	
	String TOPUP_FLAG = KPLUtil.getTopUpFlag(applicationGroup);
		
	FormUtil formUtil = new FormUtil(subformId,request);
	if(Util.empty(kycd.getRelationFlag())){
		kycd.setRelationFlag(MConstant.FLAG.NA);
	}
 %>
 
<div class="panel panel-default">
<div class="panel-body">
<div class="row form-horizontal" id="KYC">
	<div class="col-md-12">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request, subformId,"RELATION_FLAG"+TOPUP_FLAG,"RELATION_FLAG", "col-sm-5 col-md-3 control-label")%>
			<div class="col-sm-7 col-md-5">
				<div class="row">
					<div class="col-xs-10">
						<%=HtmlUtil.radio("RELATION_FLAG", "", "RELATION_FLAG_"+personalElementId, "RELATION_FLAG", 
							kycd.getRelationFlag(), "", MConstant.FLAG.YES, "", "YES", "col-xs-3 col-xs-padding", personalInfo, formUtil) %>
						<%=HtmlUtil.radio("RELATION_FLAG", "", "RELATION_FLAG_"+personalElementId, "RELATION_FLAG", 
							kycd.getRelationFlag(), "", MConstant.FLAG.NO, "", "NO", "col-xs-3 col-xs-padding", personalInfo, formUtil) %>
						<%=HtmlUtil.radio("RELATION_FLAG", "", "RELATION_FLAG_"+personalElementId, "RELATION_FLAG", 
							kycd.getRelationFlag(), "", MConstant.FLAG.NA, "", "NA", "col-xs-3 col-xs-padding", personalInfo, formUtil) %>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
	<div class="col-md-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request, subformId,"REFERENCE_NAME","REFERENCE_NAME", "col-sm-4 col-md-5 control-label")%>
			<div class="col-sm-8 col-md-7">
				<div class="row">
					<div class="col-xs-12">
						<%=HtmlUtil.hidden("REL_TITLE_NAME", kycd.getRelTitleName())%>
						<%=HtmlUtil.autoComplete("REL_TITLE_DESC", "", "REL_TITLE_DESC_"+personalElementId, "REFERENCE_NAME", "REFERENCE_NAME", 
							kycd.getRelTitleDesc(), "dropdown-textbox-1",FIELD_ID_TH_TITLE_CODE,"ALL_ALL_ALL","","col-xs-4 col-xs-padding", "", personalInfo, formUtil)%>
						<%=HtmlUtil.textBox("REL_NAME","","REL_NAME_"+personalElementId,"REFERENCE_NAME",
							kycd.getRelName(),"dropdown-textbox-2","120","","col-xs-8 col-xs-padding",personalInfo,formUtil)%>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"REL_SURNAME","REL_SURNAME","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBox("REL_SURNAME","","REL_SURNAME_"+personalElementId,"REL_SURNAME",
				kycd.getRelSurname(),"","50","","col-sm-8 col-md-7",personalInfo,formUtil)%>
		</div>
	</div>
	<div class="clearfix"></div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"REL_POSITION","REL_POSITION","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBox("REL_POSITION","","REL_POSITION_"+personalElementId,"REL_POSITION",
				kycd.getRelPosition(),"","100","","col-sm-8 col-md-7",personalInfo,formUtil)%>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"REL_DETAIL","REL_DETAIL","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBox("REL_DETAIL","","REL_DETAIL_"+personalElementId,"REL_DETAIL",
				kycd.getRelDetail(),"","100","","col-sm-8 col-md-7",personalInfo,formUtil)%>
		</div>
	</div>
	<div class="clearfix"></div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request, subformId, "WORK_START_DATE", "WORK_START_DATE", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.calendar("WORK_START_DATE", "", "WORK_START_DATE_"+personalElementId, "WORK_START_DATE", 
				kycd.getWorkStartDate(), "", "", HtmlUtil.EN, "col-sm-8 col-md-7",personalInfo, formUtil)%>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request, subformId, "WORK_END_DATE", "WORK_END_DATE", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.calendar("WORK_END_DATE", "","WORK_END_DATE_"+personalElementId, "WORK_END_DATE", 
				kycd.getWorkEndDate(), "", "", HtmlUtil.EN, "col-sm-8 col-md-7",personalInfo, formUtil)%>
		</div>
	</div>
</div>
</div>
 
</div>