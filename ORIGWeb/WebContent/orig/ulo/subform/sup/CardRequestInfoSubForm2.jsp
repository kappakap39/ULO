<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.util.SessionControl"%>
<%@page import="com.eaf.core.ulo.common.engine.SQLQueryEngine"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.CardDataM"%>
<%@page import="com.eaf.orig.ulo.model.control.FlowControlDataM"%>
<%@page import="com.eaf.core.ulo.security.encryptor.EncryptorFactory"%>
<%@page import="com.eaf.core.ulo.security.encryptor.Encryptor"%>
<%@page import="com.eaf.orig.ulo.app.view.form.subform.product.manual.CardInfoUtil"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<script type="text/javascript" src="orig/ulo/subform/sup/js/SupCardRequestInfoSubForm.js"></script>
<%
	String subformId = "SUP_CARD_REQUEST_INFO_SUBFROM_2";
	Logger logger = Logger.getLogger(this.getClass());
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	FlowControlDataM flowControl = (FlowControlDataM) request.getSession().getAttribute(SessionControl.FlowControl);
	String ACTION_TYPE = flowControl.getActionType();
	String displayMode = HtmlUtil.EDIT;	
	String FIELD_ID_CARD_TYPE = SystemConstant.getConstant("FIELD_ID_CARD_TYPE"); 
	String FIELD_ID_CARD_LEVEL = SystemConstant.getConstant("FIELD_ID_CARD_LEVEL");	
	String FIELD_ID_APPLICATION_CARD_TYPE = SystemConstant.getConstant("FIELD_ID_APPLICATION_CARD_TYPE");	
	String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	String SUPPLEMENTARY = SystemConstant.getConstant("APPLICATION_CARD_TYPE_SUPPLEMENTARY");
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	String APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");

	ArrayList<ApplicationDataM> applications  = new ArrayList<ApplicationDataM>();
	logger.debug("ACTION_TYPE >> "+ACTION_TYPE);

	applications = applicationGroup.filterApplicationCardTypeLifeCycle(PRODUCT_CRADIT_CARD,SUPPLEMENTARY);

	if(null == applications){
		applications = new ArrayList<ApplicationDataM>();
	}
	String MAIN_CARD_ENCRP= applicationGroup.getMainCardNo();
	String MAIN_CARD_NO="";
	if(!Util.empty(MAIN_CARD_ENCRP)){		
		Encryptor enc = EncryptorFactory.getDIHEncryptor();
		MAIN_CARD_NO = enc.decrypt(MAIN_CARD_ENCRP);
	}	
	FormUtil formUtil = new FormUtil(subformId,request);
%>
<div class="panel panel-default">
<div class="panel-body">
	<div class="row form-horizontal">
		<div class="col-sm-6">
			<div class="form-group">
				<%=HtmlUtil.getSubFormLabel(request,subformId,"MAIN_CARD_NUMBER","MAIN_CARD_NUMBER", "col-sm-4 col-md-5 control-label")%>
				<%=HtmlUtil.textBoxCardNo("MAIN_CARD_NUMBER", "", "MAIN_CARD_NUMBER", "MAIN_CARD_NUMBER", 
	 				MAIN_CARD_NO, "", "", "col-sm-8 col-md-7",formUtil) %>
			</div>
	 	</div>
	 	<div class="col-sm-6">
			<div class="form-group">
	 			<%=HtmlUtil.getSubFormLabel(request,subformId,"MAIN_CARD_NAME","MAIN_CARD_NAME","col-sm-4 col-md-5 control-label")%>
	 			<%=FormatUtil.display(applicationGroup.getMainCardHolderName())%> 
			</div>
		</div>
	</div>
</div>
<%-- <div class="panel-heading"><%=LabelUtil.getText(request, "TYPE_SUP_CARD")%></div> --%>
<!-- <div class="panel-body"> -->
<!-- <div class="row form-horizontal"> -->
<!-- 	<div class="col-sm-4"> -->
<!-- 		<div class="form-group"> -->
<%-- 			<%=HtmlUtil.getSubFormLabel(request,subformId,"PERSONAL_SUPPLEMENTARY","PERSONAL_SUPPLEMENTARY", "col-sm-4 col-md-5 control-label")%> --%>
<%-- 			<%=HtmlUtil.dropdown("PERSONAL_SUPPLEMENTARY", "PERSONAL_SUPPLEMENTARY", "PERCENT_RELATION", "",  --%>
<%-- 				null, "","","", "", "col-sm-8 col-md-7", formUtil)%> --%>
<!-- 		</div> -->
<!-- 	</div> -->
<!-- 	<div class="col-sm-4"> -->
<!-- 		<div class="form-group"> -->
<%-- 			<%=HtmlUtil.getSubFormLabel(request,subformId,"CARD_TYPE_SUP","CARD_TYPE_SUP", "col-sm-2 col-md-3 control-label")%> --%>
<%-- 			<%=HtmlUtil.dropdown("CARD_TYPE", "CARD_TYPE", "CARD_TYPE", "",  --%>
<%-- 				null, "",FIELD_ID_CARD_TYPE,"ALL", "", "col-sm-10 col-md-9", formUtil)%> --%>
<!-- 		</div> -->
<!-- 	</div>	 -->
<!-- 	<div class="col-sm-3"> -->
<!-- 		<div class="form-group"> -->
<%-- 			<%=HtmlUtil.getSubFormLabel(request,subformId,"CARD_LEVEL_SUP","CARD_LEVEL_SUP", "col-sm-3 col-md-4 control-label")%> --%>
<%-- 			<%=HtmlUtil.dropdown("CARD_LEVEL", "CARD_LEVEL", "CARD_LEVEL", "",  --%>
<%-- 				null, "",FIELD_ID_CARD_LEVEL,"ALL_ALL_ALL", "", "col-sm-9 col-md-8", formUtil)%> --%>
<!-- 		</div> -->
<!-- 	</div>	 -->
<!-- 	<div class="col-sm-1"> -->
<!-- 		<div class="form-group"> -->
<%-- 			<%=HtmlUtil.button("ADD_CARD_REQUEST","","","btnsmall_add","",request) %> --%>
<!-- 		</div> -->
<!-- 	</div>	 -->
<!-- </div> -->
<!-- </div> -->
</div>
<div class="panel panel-default">
<table class="table table-striped table-hover">
	<%
		int ROWNUM = 1;
		if(!Util.empty(applications)){
			for (ApplicationDataM applicationItem : applications) {
				CardDataM card = applicationItem.getCard();				
				String uniqueId = applicationItem.getApplicationRecordId();
				String applicationCardType = card.getApplicationType();
				String cardTypeId = card.getCardType();
				HashMap<String, Object> cardInfo = CardInfoUtil.getCardInfo(cardTypeId);
				String cardCode = SQLQueryEngine.display(cardInfo, "CARD_CODE");
				String cardLevel = SQLQueryEngine.display(cardInfo, "CARD_LEVEL");
				PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfoRelation(uniqueId,PERSONAL_TYPE_SUPPLEMENTARY, APPLICATION_LEVEL);
				if (null == personalInfo) {
					personalInfo = new PersonalInfoDataM();
				}
				String onclickActionJS="onclick=DELETE_SUP_CARDActionJS('"+uniqueId+"')";
	%>
	<tr>
<%-- 		<td width="5%"><%=HtmlUtil.icon("DELETE_SUP_CARD", "", "btnsmall_delete",onclickActionJS, request)%></td> --%>
		<td width="5%"><%=ROWNUM%></td>		
		<td width="15%"><%=CacheControl.displayName(FIELD_ID_APPLICATION_CARD_TYPE, applicationCardType)%></td>
		<td width="30%"><%=CacheControl.getName(FIELD_ID_CARD_TYPE, cardCode, "DISPLAY_NAME")%></td>
		<td width="20%"><%=CacheControl.getName(FIELD_ID_CARD_LEVEL, cardLevel, "DISPLAY_NAME")%></td>
		<td width="25%"><%=FormatUtil.displayText(personalInfo.getThName())%></td>
		<td width="5%"><%=HtmlUtil.getFinalDecisionText(applicationItem.getFinalAppDecision(), request)%></td>
	</tr>
	<%ROWNUM++;}%>
	<%}else{%>
		<tr>
	 		<td><%=LabelUtil.getText(request,"NO_RECORD_FOUND")%></td>
	 	</tr>
	<%}%>
</table>
</div>