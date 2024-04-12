<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.core.ulo.common.util.FormEffects"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.eaf.core.ulo.common.properties.ListBoxControl"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<script type="text/javascript" src="orig/ulo/subform/js/ListSupPersonalInfoSubForm.js"></script>
<%
	String subformId = "LIST_SUP_PERSONAL_INFO_SUBFORM_1";
	Logger logger = Logger.getLogger(this.getClass());	
	String displayMode = HtmlUtil.EDIT;
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	String FIELD_ID_RELATION_WITH_APPLICANT = SystemConstant.getConstant("FIELD_ID_RELATION_WITH_APPLICANT");
	String COMPLETE_FLAG_FIRST_LOAD_PERSONAL = SystemConstant.getConstant("COMPLETE_FLAG_FIRST_LOAD_PERSONAL");
	logger.debug("PERSONAL_TYPE_SUPPLEMENTARY >> "+PERSONAL_TYPE_SUPPLEMENTARY);
	ArrayList<PersonalInfoDataM> personals = ORIGForm.getObjectForm().getPersonalInfos(PERSONAL_TYPE_SUPPLEMENTARY);	
	
	PersonalInfoDataM personSuplementary = new PersonalInfoDataM();
	if(!Util.empty(personals)){
		personSuplementary = personals.get(0);
	}
	String FIELD_ID_PERSONAL_TYPE = SystemConstant.getConstant("FIELD_ID_PERSONAL_TYPE");
		
	FormUtil formUtil = new FormUtil(subformId,request);
	FormEffects formEffect = new FormEffects(subformId,request);
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
%>
<%if(!applicationGroup.isVeto()){ %>
	<div class="panel panel-default">
		<div class="panel-body">
			<div class="row form-horizontal">
				<div class="col-sm-12">
					<div class="form-group">
						<%=HtmlUtil.getSubFormLabel(request,subformId,"PERSONAL_TYPE","PERSONAL_TYPE","col-sm-2 col-md-5 marge-label control-label")%>
						<div class="col-sm-10 col-md-9 marge-field">
							<div class="row">
								<div class="col-xs-12">
									<%=HtmlUtil.dropdown("PERSONAL_TYPE", "PERSONAL_TYPE", "PERSONAL_TYPE", "PERSONAL_TYPE_SUP", 
										PERSONAL_TYPE_SUPPLEMENTARY, "",FIELD_ID_PERSONAL_TYPE,"ALL_ALL_ALL", "", "col-sm-5 col-xs-10 col-xs-padding", formUtil)%>
									<div class="col-xs-2"><%=HtmlUtil.button("ADD_SUP_PERSONAL_INFO","ADD_SUP_PERSONAL_INFO","","btnsmall_add","",personSuplementary,formEffect)%></div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
<%} %>
<div class="panel panel-default">
	<div class="panel-heading"><%=LabelUtil.getText(request,"SUB_PERSONAL_SUBFORM")%></div>
	<table class="table table-striped table-hover">
		<%
			if(!Util.empty(personals)){
				int ROW_NUM = 1;
				for(PersonalInfoDataM personalInfo:personals){
					String PERSONAL_ID = personalInfo.getPersonalId();
		 			String PERSONAL_SEQ = FormatUtil.getString(personalInfo.getSeq());
		 			String PERSONAL_TYPE = personalInfo.getPersonalType();
		 			String loadActionJS = "onclick=LOAD_SUP_PERSONAL_INFOActionJS('"+PERSONAL_ID+"','"+PERSONAL_SEQ+"','"+PERSONAL_TYPE+"')";
		 			String deleteActionJS = "onclick=DELETE_SUP_PERSONAL_INFOActionJS('"+PERSONAL_ID+"')";
		 			

			if(!COMPLETE_FLAG_FIRST_LOAD_PERSONAL.equals(personalInfo.getCompleteData())){
			
				if(!Util.empty(personalInfo.getThFirstName())){
		 				personalInfo.setLinkFirstName(personalInfo.getThFirstName());
		 			}
		 			
		 			if(!Util.empty(personalInfo.getThLastName())){
		 				personalInfo.setLinkLastName(personalInfo.getThLastName());
		 			}
			%>
		 	<tr>
		 		<td width="5%"><%=HtmlUtil.icon("DELETE_SUP_PERSONAL_INFO","DELETE_SUP_PERSONAL_INFO","btnsmall_delete",deleteActionJS,formEffect) %></td>
		 		<td width="5%"><%= ROW_NUM %></td>
		 		<td><%=HtmlUtil.linkText("LOAD_SUP_PERSONAL_INFO",personalInfo.getThLinkName(),HtmlUtil.EDIT,loadActionJS,request)%></td>
		 		<td><%=ListBoxControl.getName(FIELD_ID_RELATION_WITH_APPLICANT, personalInfo.getRelationshipType())%></td>
		 	</tr>
		 	<%		ROW_NUM++;
		 	}
		 		}
		 	%>
		 <%}else{%>
		 	<tr>
		 		<td align="center" colspan="999"><%=LabelUtil.getText(request,"NO_RECORD_FOUND")%></td>
		 	</tr>
		 <%}%>
	</table>
</div>