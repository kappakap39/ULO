<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.core.ulo.common.properties.FormControl"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.core.ulo.common.engine.SQLQueryEngine"%>
<%@page import="com.eaf.orig.ulo.app.view.form.subform.product.manual.CardInfoUtil"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.LoanDataM"%>
<%@page import="com.eaf.orig.cache.properties.CardTypeProperties"%>
<%@page import="com.eaf.orig.ulo.model.app.CardDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.apache.log4j.Logger"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<script type="text/javascript" src="../../../ulo/product/js/AdditionalServiceSubForm.js"></script>
<jsp:useBean id="ModuleForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler"/>
<%
	String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	Logger logger = Logger.getLogger(this.getClass());
	String FIELD_ID_PERCENT_LIMIT_MAINCARD = SystemConstant.getConstant("FIELD_ID_PERCENT_LIMIT_MAINCARD");
	String FIELD_ID_CARD_LEVEL = SystemConstant.getConstant("FIELD_ID_CARD_LEVEL");
	ApplicationDataM applicationItem = (ApplicationDataM)ModuleForm.getObjectForm();
	CardDataM cardM = applicationItem.getCard();
	if(Util.empty(cardM)){
		cardM = new CardDataM();
	}	
	String applicationRecordId = applicationItem.getApplicationRecordId();
	ApplicationGroupDataM applicationGroup = FormControl.getOrigObjectForm(request);
	PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfoByRelation(applicationRecordId);
	if(null == personalInfo){
		personalInfo = new PersonalInfoDataM();
	}
	String productElementId = FormatUtil.getProductElementId(personalInfo,PRODUCT_CRADIT_CARD);
	String displayMode = HtmlUtil.VIEW;
	logger.debug("cardM.getCardId() >>>>> "+cardM.getCardType());
	HashMap<String,Object> CardInfo = CardInfoUtil.getCardInfo(cardM.getCardType());	
	String CardCode = SQLQueryEngine.display(CardInfo,"CARD_CODE");
	String CardLevel = SQLQueryEngine.display(CardInfo,"CARD_LEVEL");
	logger.debug("CardCode >> "+CardCode); 	
	logger.debug("CardLevel >> "+CardLevel);
 %>
 
 <div class="panel panel-default">
		 <div class="panel-body">
			<div class="row form-horizontal">
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.getFieldLabel(request, "DM_FIRST_NAME" ,"col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.dropdown("SUB_NAME", PRODUCT_CRADIT_CARD, "", "", "", "", "", displayMode
							, HtmlUtil.elementTagId("SUB_NAME",productElementId), "col-sm-8 col-md-7", request)%>
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.getFieldLabel(request, "CARD_LEVEL","col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.dropdown("CARD_LEVEL", PRODUCT_CRADIT_CARD,"", CardLevel, "", FIELD_ID_CARD_LEVEL, "", displayMode
							, HtmlUtil.elementTagId("CARD_LEVEL",productElementId), "col-sm-8 col-md-7", request)%>
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.getFieldLabel(request, "PERCENT_LIMIT_MAINCARD","col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.dropdown("PERCENT_LIMIT_MAINCARD", PRODUCT_CRADIT_CARD, "", cardM.getPercentLimitMaincard(), "", FIELD_ID_PERCENT_LIMIT_MAINCARD
						, "", displayMode,  HtmlUtil.elementTagId("PERCENT_LIMIT_MAINCARD",productElementId), "col-sm-8 col-md-7", request)%>
						
					</div>
				</div>
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.getFieldLabel(request, "PERCENT_LIMIT","col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.currencyBox("PERCENT_LIMIT", PRODUCT_CRADIT_CARD, FormatUtil.toBigDecimal("",false), "", false, "13", displayMode
							, HtmlUtil.elementTagId("PERCENT_LIMIT",productElementId), "col-sm-8 col-md-7", request)%>
					
					</div>
				</div>
			</div>
		</div>
</div>
