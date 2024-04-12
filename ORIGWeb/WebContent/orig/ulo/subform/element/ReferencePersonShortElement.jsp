<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ReferencePersonDataM"%>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<%
	Logger logger =  Logger.getLogger(this.getClass());
	String refPersonSeq = request.getParameter("refPersonSeq");
	String subformId = request.getParameter("subFormId");
	if(Util.empty(subformId)){
		subformId = request.getParameter("subformId");
	}
	int numPerson = Integer.parseInt(refPersonSeq);
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	ReferencePersonDataM referencePerson = applicationGroup.getReferencePersons(numPerson);
	if(null == referencePerson){
		referencePerson = new ReferencePersonDataM();
	}
	FormUtil formUtil = new FormUtil(subformId,request);
	logger.debug("subformId >> "+subformId);
	logger.debug("refFormAction >> "+formUtil.getActionType());
	String refPerson = "PERSON_"+refPersonSeq;
%>
	<div class="panel-heading"><%=LabelUtil.getText(request, "REFERENCE_"+refPersonSeq)%></div>
	<div class="panel-body">
		<div class="row form-horizontal">
			<div class="col-sm-5">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request,subformId,"PHONE1","PHONE1", "col-sm-4 col-md-5 control-label")%>
					<%=HtmlUtil.textBoxTel("REF_PHONE1",refPerson,"REF_PERSON_PHONE1_"+refPersonSeq,"REF_PHONE1",referencePerson.getPhone1(),"","","col-sm-8 col-md-7",formUtil)%>
				</div>
			</div>
			<div class="col-sm-7">
				<div class="form-group">
				<div class="col-sm-8 col-md-12">
					<%=HtmlUtil.getSubFormLabel(request,subformId,"OFFICE_PHONE","OFFICE_PHONE","col-sm-2 col-md-4 control-label")%>
							<div class="col-xs-8">
								<%=HtmlUtil.textBoxTel("OFFICE_PHONE",refPerson,"REF_PERSON_OFFICE_PHONE_"+refPersonSeq,"OFFICE_PHONE",
									referencePerson.getOfficePhone(),"","","col-xs-6 col-xs-padding",formUtil)%>
								<%=HtmlUtil.getFieldLabel(request,"PHONE_EXT","col-xs-3 control-label")%>
								<%=HtmlUtil.textBoxExt("OFFICE_PHONE_EXT1",refPerson,"REF_PERSON_OFFICE_PHONE_EXT1_"+refPersonSeq,"OFFICE_PHONE",
									referencePerson.getOfficePhoneExt(),"","5","","col-xs-3 col-xs-padding",formUtil)%>
							</div>
					</div>
				</div>
			</div>
			<div class="clearfix"></div>
			<div class="col-sm-5">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request,subformId,"MOBILE","MOBILE","col-sm-4 col-md-5 control-label")%>
					<%=HtmlUtil.textBoxMobile("MOBILE",refPerson,"REF_PERSON_MOBILE_"+refPersonSeq,"MOBILE",
						referencePerson.getMobile(),"","","col-sm-8 col-md-7",formUtil)%>
				</div>
			</div>
			<div class="clearfix"></div>
		</div>
	</div>