<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.ReferencePersonDataM"%>
<%@page import="java.util.ArrayList" %>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<%
	Logger logger = Logger.getLogger(this.getClass());
	String refPersonSeq = request.getParameter("refPersonSeq");
	String subformId = request.getParameter("subFormId");	
	int numPerson = Integer.parseInt(refPersonSeq);
	String FIELD_ID_TH_TITLE_CODE = SystemConstant.getConstant("FIELD_ID_TH_TITLE_CODE");
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	ReferencePersonDataM referencePerson = applicationGroup.getReferencePersons(numPerson);
	if(null == referencePerson){
		referencePerson = new ReferencePersonDataM();
	}
	FormUtil formUtil = new FormUtil(subformId,request);
	
	String refPerson = "PERSON_"+refPersonSeq;
	logger.debug("refPerson : "+refPerson);
	logger.debug("referencePerson.getPhone1() : "+referencePerson.getPhone1());
%>
<%=HtmlUtil.hidden("REF_PERSON",refPerson)%>
<div class="panel-heading"><%=LabelUtil.getText(request, "REFERENCE_"+refPersonSeq)%></div>
<div class="panel-body">
<div class="row form-horizontal">
	<div class="col-sm-12">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"REFERENCE_NAME","REFERENCE_NAME", "col-sm-2 col-md-5 marge-label control-label")%>
			<div class="col-sm-10 col-md-9 marge-field">
				<div class="row">
					<div class="col-xs-12">
<%-- 					<%=HtmlUtil.hidden("REF_TITLE_CODE_"+refPerson, referencePerson.getThTitleDesc())%> --%>
						<%=HtmlUtil.autoComplete("REF_TITLE_DESC", refPerson,"REF_PERSON_REF_TITLE_DESC_"+refPersonSeq, "REFERENCE_NAME", "", 
							referencePerson.getThTitleDesc(), "", FIELD_ID_TH_TITLE_CODE,"ALL_ALL_ALL", "", "col-xs-3 col-xs-padding","",applicationGroup, formUtil)%>
						<div class="col-xs-9">
							<div class="input-group">
								<%=HtmlUtil.textBox("TH_FIRST_NAME_PERSON",refPersonSeq,"REF_PERSON_TH_FIRST_NAME_"+refPersonSeq,"REFERENCE_NAME",referencePerson.getThFirstName(),"textbox-name-1","120","","input-group-btn",applicationGroup,formUtil)%>
	                			<%=HtmlUtil.textBox("TH_LAST_NAME_PERSON",refPersonSeq,"REF_PERSON_TH_LAST_NAME_"+refPersonSeq,"REFERENCE_NAME",referencePerson.getThLastName(),"textbox-name-3","50","","input-group-btn",applicationGroup,formUtil)%>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"RELATION","RELATION","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBox("RELATION",refPerson,"REF_PERSON_RELATION_"+refPersonSeq,"RELATION",
				referencePerson.getRelation(),"","50","","col-sm-8 col-md-7",applicationGroup,formUtil)%>
		</div>
	</div>
	<div class="clearfix"></div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"PHONE1","PHONE1","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBoxTel("PHONE1",refPerson,"REF_PERSON_PHONE1_"+refPersonSeq,"REF_PHONE1_" + refPerson,
				referencePerson.getPhone1(),"","","col-sm-8 col-md-7",applicationGroup,formUtil)%>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"OFFICE_PHONE", "OFFICE_PHONE", "col-sm-4 col-md-5 control-label")%>
			<div class="col-sm-8 col-md-7">
				<div class="row">
					<div class="col-xs-12">
						<%=HtmlUtil.textBoxTel("OFFICE_PHONE",refPerson,"REF_PERSON_OFFICE_PHONE_"+refPersonSeq,"OFFICE_PHONE",
							referencePerson.getOfficePhone(),"","","col-xs-6 col-xs-padding",applicationGroup,formUtil)%>
						<%=HtmlUtil.getFieldLabel(request,"PHONE_EXT","col-xs-3 control-label")%>
						<%=HtmlUtil.textBoxExt("OFFICE_PHONE_EXT",refPerson,"REF_PERSON_OFFICE_PHONE_EXT_"+refPersonSeq,"OFFICE_PHONE",
							referencePerson.getOfficePhoneExt(),"","6","","col-xs-3 col-xs-padding",applicationGroup,formUtil)%>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"MOBILE","MOBILE","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBoxMobile("MOBILE",refPerson,"REF_PERSON_MOBILE_"+refPersonSeq,"MOBILE",
				referencePerson.getMobile(),"","","col-sm-8 col-md-7",referencePerson,formUtil)%>
		</div>
	</div>
</div>
</div>