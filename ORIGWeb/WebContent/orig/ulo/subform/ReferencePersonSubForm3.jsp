<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.core.ulo.common.properties.ImplementControl"%>
<%@page import="com.eaf.core.ulo.common.display.ElementInf"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<script type="text/javascript" src="orig/ulo/subform/js/ReferencePersonSubForm.js"></script>
<%
	String subformId = "REFERENCE_PERSON_SUBFORM_3";
	Logger logger = Logger.getLogger(this.getClass());
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
%>
<div class="panel panel-default">
	<%	
		ArrayList<ElementInf> elements = ImplementControl.getElements(MConstant.IMPLEMENT_TYPE.REFERENCE_PERSON_3);
// 		ElementInf element = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.REFERENCE_PERSON, "REFERENCE_PERSON_1");
		logger.debug("element >> "+elements.size());
		for(ElementInf element:elements){
			element.setObjectRequest(applicationGroup);
			element.writeElement(pageContext,subformId);
		}
	%>
</div>
