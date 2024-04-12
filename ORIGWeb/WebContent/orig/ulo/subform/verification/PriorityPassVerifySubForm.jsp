<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.WisdomDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.CardDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalRelationDataM"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.core.ulo.common.properties.ImplementControl"%>
<%@page import="com.eaf.core.ulo.common.display.ElementInf"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<%
	Logger logger = Logger.getLogger(this.getClass());
	logger.debug("PRIORITY_PASS_SUBFORM");
	String PRODUCT_CRADIT_CARD =SystemConstant.getConstant("PRODUCT_CRADIT_CARD");	
	String FIELD_ID_REQ_PRIORITY_PASS_SUP= SystemConstant.getConstant("FIELD_ID_REQ_PRIORITY_PASS_SUP");
	String FIELD_ID_PRIORITY_PASS_MAIN= SystemConstant.getConstant("FIELD_ID_PRIORITY_PASS_MAIN");
	String REQ_PRIORITY_PASS_SUP_DEFAULT =SystemConstant.getConstant("REQ_PRIORITY_PASS_SUP_DEFAULT");
	
	String subformId ="VERIFICATION_CUSTOMER_INFO_SUBFORM";
	FormUtil formUtil = new FormUtil(subformId, request);	
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	WisdomDataM wisdomCard = applicationGroup.findWisdom(PRODUCT_CRADIT_CARD);
	if(null==wisdomCard){
		wisdomCard = new WisdomDataM();
	}
	if(Util.empty(wisdomCard.getReqPriorityPassSup())){
		wisdomCard.setReqPriorityPassSup(REQ_PRIORITY_PASS_SUP_DEFAULT);
	}
%>
<div class=" panel-default panel" >
<div class="panel-heading"><%=HtmlUtil.getLabel(request, "PRIORITY_PASS_SUBFORM","")%></div>	
<div class="panel-body">
	<div class="row form-horizontal">
		<div class="col-sm-6">
			<div class="form-group">
				<%=HtmlUtil.getMandatoryLabel(request, "REQ_PRIORITY_PASS_SUP", "col-sm-4 col-md-5") %>
				<%=HtmlUtil.dropdown("REQ_PRIORITY_PASS_SUP", "", "", "REQ_PRIORITY_PASS_SUP", "REQ_PRIORITY_PASS_SUP", wisdomCard.getReqPriorityPassSup(), "",FIELD_ID_REQ_PRIORITY_PASS_SUP, "", "REQ_PRIORITY_PASS_SUP", "col-sm-8 col-md-7", formUtil) %>
			</div>
		</div>
		<div class="col-sm-6">
			<div class="form-group">
				<%=HtmlUtil.getFieldLabel(request, "PRIORITY_PASS_FLAG","col-sm-4 col-md-5") %>
				<%=HtmlUtil.checkBox("PRIORITY_PASS_FLAG", "", wisdomCard.getPriorityPassMemo(), MConstant.FLAG.YES, "", "", "", "col-sm-8 col-md-7", request) %>
			</div>
		</div>
	</div>
	
	<div class="row form-horizontal">
		<div class="col-sm-6">
			<div class="form-group">
				<%=HtmlUtil.getFieldLabel(request, "PRIORITY_PASS_MAIN","col-sm-4 col-md-5") %>
				<%=HtmlUtil.dropdown("PRIORITY_PASS_MAIN", "", "", "PRIORITY_PASS_MAIN", "", wisdomCard.getPriorityPassMain(), "", FIELD_ID_PRIORITY_PASS_MAIN, "", "", "col-sm-8 col-md-7", formUtil) %>
			</div>
		</div>
		<div class="col-sm-6">
			<div class="form-group">
				<%=HtmlUtil.getFieldLabel(request, "NO_PRIORITY_PASS_SUP","col-sm-4 col-md-5") %>
				<%=HtmlUtil.numberBox("NO_PRIORITY_PASS_SUP", "","NO_PRIORITY_PASS_SUP", FormatUtil.toBigDecimal(wisdomCard.getNoPriorityPassSup(), true), "", "#",true, "1", "", "col-sm-7 col-md-6", formUtil)%>
				<div class="col-sm-1 col-md-1"><%=LabelUtil.getText(request,"NO_PRIORITY_PASS_UNIT")%></div>
				</div>
			</div>
		</div>
	</div>
</div>

	