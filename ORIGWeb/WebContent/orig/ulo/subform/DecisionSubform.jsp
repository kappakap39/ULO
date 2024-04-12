<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.CardDataM"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.orig.ulo.model.app.LoanDataM"%>
<%@page import="java.text.Normalizer.Form"%>
<%@page import="org.apache.tools.ant.taskdefs.Javadoc.Html"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.display.ElementInf"%>
<%@page import="com.eaf.core.ulo.common.properties.ImplementControl"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<script type="text/javascript" src="orig/ulo/subform/js/DecisionSubform.js"></script>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<%
	String displayMode = HtmlUtil.EDIT;	
	Logger logger = Logger.getLogger(this.getClass());
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	if(Util.empty(applicationGroup)){
		applicationGroup = new ApplicationGroupDataM();
	}
	
	ArrayList<String> products = applicationGroup.getProducts();
	if(Util.empty(products)){
		products = new ArrayList<String>();
	}
	String subformId = "DECISION_SUBFORM";
   	FormUtil formUtil = new FormUtil(subformId,request);
%>
<div class="panel panel-default">
 <%if(!Util.empty(products)){ 
	for(String product:products){
		ElementInf element = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.DECISION,"DECISION_"+product+"_SUBFORM");
		if(!Util.empty(element.getImplementId())){

			element.writeElement(pageContext,subformId);
		}%>
		
<%  }
 } %>
	<div class="panel-body">
		<div class="row form-horizontal">
			<div class="col-md-12">
				<div class="form-group">
					<%=HtmlUtil.getFieldLabel(request, "CA_REMARK", "col-sm-2 col-md-2 control-label")%>
					<%=HtmlUtil.textarea("CA_REMARK", "","CA_REMARK","CA_REMARK", applicationGroup.getCaRemark(), "5", "100", "1000", "", "col-sm-9 col-md-8",applicationGroup, formUtil) %>
				</div>
			</div>
		</div>
	</div>
</div>