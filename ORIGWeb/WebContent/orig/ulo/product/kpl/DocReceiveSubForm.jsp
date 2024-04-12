<%@page import="java.math.BigDecimal"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.orig.ulo.app.view.util.kpl.KPLUtil"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<jsp:useBean id="ORIGUser" scope="session"class="com.eaf.orig.profile.model.UserDetailM" />
<jsp:useBean id="ORIGForm" scope="session"class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler" />
<%
	String subformId = "KPL_DOC_RECEIVE_SUBFORM";
	Logger logger = Logger.getLogger(this.getClass());
	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");	
	String PRODUCT_K_PERSONAL_LOAN = SystemConstant.getConstant("PRODUCT_K_PERSONAL_LOAN");
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	ApplicationDataM applicationM = applicationGroup.filterApplicationProductLifeCycle(PRODUCT_K_PERSONAL_LOAN);
	FormUtil formUtil = new FormUtil("NORMAL_APPLICATION_FORM",subformId,request);
%>
<div class="panel panel-default">
	 <div class="panel-body">
		<div class="row form-horizontal">
			<div class="col-sm-12">
				<div class="form-group">
					<%-- Defect#2997 --%>
					<%-- <%=HtmlUtil.checkBoxInline("KPL_DOC_RECEIVE", "", applicationM.getLetterChannel(), MConstant.FLAG.YES, HtmlUtil.EDIT, "", LabelUtil.getText(request, "KPL_DOC_RECEIVE_LABEL"), "col-sm-12 col-md-10", request) %> --%>
					<%=HtmlUtil.checkBoxInline("KPL_DOC_RECEIVE", "KPL_DOC_RECEIVE", "KPL_DOC_RECEIVE_SUBFORM", applicationM.getLetterChannel(), "", MConstant.FLAG.YES, "", LabelUtil.getText(request, "KPL_DOC_RECEIVE_LABEL"), "col-sm-12 col-md-10", formUtil)%>
				</div>
			</div>
		</div>
	</div>
</div>