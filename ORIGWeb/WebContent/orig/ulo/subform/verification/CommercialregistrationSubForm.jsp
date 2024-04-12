<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.VerificationResultDataM"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<script type="text/javascript" src="orig/ulo/subform/verification/js/CommercialregistrationSubForm.js"></script>
<%
 String subformId="VERIFICATION_CUSTOMER_INFO_SUBFORM";
 String displayMode = HtmlUtil.EDIT;
 String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");	
 PersonalInfoDataM personalInfo = ORIGForm.getObjectForm().getPersonalInfo(PERSONAL_TYPE);
	 if(Util.empty(personalInfo)){
	 	personalInfo = new  PersonalInfoDataM();
	 }
 VerificationResultDataM verficationResult = personalInfo.getVerificationResult();
 	if(Util.empty(verficationResult)){
 		verficationResult = new VerificationResultDataM();
 	}
 	
 	FormUtil formUtil = new FormUtil(subformId,request);
 	
 ArrayList<String> documenTypes=ORIGForm.getObjectForm().getImageSplitsDocType();
 %>
 <%if(!Util.empty(documenTypes) && documenTypes.contains(SystemConstant.getConstant("VERIFICATION_COMMERCIAL_DOC_TYPE"))){%>
<div class=" panel-default panel" >
	<div class="panel-heading"><%=HtmlUtil.getLabel(request, "COMMERCIAL_REGISTRATION","")%></div>	
	<div class="panel-body">
		<div class="row form-horizontal">
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request, subformId, "REGISTRATION_NO", "REGISTRATION_NO","col-sm-4 col-md-5 control-label")%>
					<%=HtmlUtil.textBox("REGISTRATION_NO", "",personalInfo.getBusRegistId(), "", "13", displayMode, "", "col-sm-8 col-md-7", request)%>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request,subformId, "REQUEST_NO","REQUEST_NO", "col-sm-4 col-md-5 control-label")%>
					<%=HtmlUtil.textBox("REQUEST_NO", "",personalInfo.getBusRegistReqNo(), "", "13", displayMode, "", "col-sm-8 col-md-7", request)%>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request, subformId,"REGISTRATION_DATE","REGISTRATION_DATE", "col-sm-4 col-md-5 control-label")%>
					<%=HtmlUtil.calendar("REGISTRATION_DATE", "REGISTRATION_DATE", "REGISTRATION_DATE", personalInfo.getBusRegistDate(), "", "", HtmlUtil.TH, "col-sm-8 col-md-7", formUtil)%>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request,subformId, "VERIFY_RESULT","VERIFY_RESULT", "col-sm-4 col-md-5 control-label")%>
					<%=HtmlUtil.dropdown("VERIFY_RESULT", "", "",verficationResult.getBusRegistVerResult(), "",SystemConstant.getConstant("FIELD_ID_VERIFY_COMMER_RESULT"), "", displayMode, "", "col-sm-8 col-md-7", request)%>
				</div>
			</div>			
		</div>
	</div>
</div>
<%}%>