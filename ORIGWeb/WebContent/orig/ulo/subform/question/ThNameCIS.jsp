<%@page import="com.eaf.orig.ulo.control.util.PersonalInfoUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.orig.ulo.model.app.VerificationResultDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.AddressDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.eaf.core.ulo.common.properties.FormControl"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.display.ElementInf"%>
<%@page import="com.eaf.core.ulo.common.properties.ImplementControl"%>
<%@page import="com.eaf.orig.ulo.model.app.IdentifyQuestionDataM"%>


<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="EntityForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler"/>

<%
		String FIELD_ID_TH_TITLE_CODE = SystemConstant.getConstant("FIELD_ID_TH_TITLE_CODE");
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)EntityForm.getObjectForm();		
		String personalId =request.getParameter("PERSONAL_ID");
		PersonalInfoDataM personalInfo	 =  applicationGroup.getPersonalInfoId(personalId);
		if(Util.empty(personalInfo)){
			personalInfo = new PersonalInfoDataM();
		}
		String displayMode = HtmlUtil.EDIT;	
%>	
<div class="col-sm-12">
		<div class="form-group">
			<%=HtmlUtil.getLabel(request, "TH_FIRST_LAST_NAME", "col-sm-2 col-md-5 marge-label control-label") %>
			<div class="col-sm-10 col-md-9 marge-field">
				<div class="row">
					<div class="col-xs-12">
						<div class="col-xs-9">
							<div class="input-group">
	                			<%=HtmlUtil.textBoxTH("TH_FIRST_NAME",personalInfo.getPersonalId(),personalInfo.getThFirstName(),"textbox-name-1","120",displayMode,HtmlUtil.elementTagId("TH_FIRST_NAME"),"input-group-btn",request)%>
               					<%=HtmlUtil.textBoxTH("TH_MID_NAME",personalInfo.getPersonalId(),personalInfo.getThMidName(),"textbox-name-2","60",displayMode,HtmlUtil.elementTagId("TH_MID_NAME"),"input-group-btn",request)%>
	             				<%=HtmlUtil.textBoxTH("TH_LAST_NAME",personalInfo.getPersonalId(),personalInfo.getThLastName(),"textbox-name-3","50",displayMode,HtmlUtil.elementTagId("TH_LAST_NAME"),"input-group-btn",request)%>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>