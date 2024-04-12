<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ReferencePersonDataM"%>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<%
	String refPersonSeq = request.getParameter("refPersonSeq");
	String subformId = request.getParameter("subFormId");	
	int numPerson = Integer.parseInt(refPersonSeq);
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	ReferencePersonDataM referencePerson = applicationGroup.getReferencePersons(numPerson);
	if(null == referencePerson){
		referencePerson = new ReferencePersonDataM();
	}
	FormUtil formUtil = new FormUtil(subformId,request);
	
	String refPerson = "PERSON_"+refPersonSeq;
%>
<div class="panel-heading"><%=LabelUtil.getText(request, "REFERENCE_"+refPersonSeq)%></div>
<div class="panel-body">
	<div class="row form-horizontal">
		<div class="col-sm-6">
			<div class="form-group">
				<%=HtmlUtil.getSubFormLabel(request,subformId,"PHONE1","PHONE1", "col-sm-4 col-md-5 control-label")%>
				<%=HtmlUtil.textBoxTel("REF_PHONE1",refPerson,"REF_PERSON_REF_PHONE1_"+refPersonSeq,"PHONE1",referencePerson.getPhone1(),"","","col-sm-8 col-md-7",formUtil)%>
			</div>
		</div>
		<div class="clearfix"></div>
	</div>
</div>