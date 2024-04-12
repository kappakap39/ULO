<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.core.ulo.common.properties.FormControl"%>
<%@page import="com.eaf.orig.ulo.app.view.form.subform.product.manual.CardInfoUtil"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.engine.SQLQueryEngine"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.LoanDataM"%>
<%@page import="com.eaf.orig.cache.properties.CardTypeProperties"%>
<%@page import="com.eaf.orig.ulo.model.app.CardDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<script type="text/javascript" src="orig/ulo/product/kcc/popup/js/SupplementaryCardPopup1.js"></script>
<jsp:useBean id="ModuleForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler"/>
<%
	String subformId = "SUPPLEMENTARY_CARD_POPUP_1";
	Logger logger = Logger.getLogger(this.getClass());
	String FIELD_ID_PERCENT_LIMIT_MAINCARD = SystemConstant.getConstant("FIELD_ID_PERCENT_LIMIT_MAINCARD");
	String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	String FIELD_ID_CARD_LEVEL = SystemConstant.getConstant("FIELD_ID_CARD_LEVEL");
	ApplicationDataM applicationItem = new  ApplicationDataM();
	Object objectModule = ModuleForm.getObjectForm();
	if(objectModule instanceof HashMap){
	HashMap<String,Object> hObjectModule = (HashMap<String,Object>)objectModule;
	applicationItem = (ApplicationDataM)hObjectModule.get("APPLICATION");
	}else{
		applicationItem = (ApplicationDataM)objectModule;
	}
	
	String applicationRecordId = applicationItem.getApplicationRecordId();
	ApplicationGroupDataM applicationGroup = FormControl.getOrigObjectForm(request);
	
	
	PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfoByRelation(applicationRecordId);
	if(null == personalInfo){
		personalInfo = new PersonalInfoDataM();
	}
	String productElementId = FormatUtil.getProductElementId(personalInfo,PRODUCT_CRADIT_CARD);	
	CardDataM cardM= applicationItem.getCard();
	if(Util.empty(cardM)){
		cardM = new CardDataM();
	}
	
	String displayMode = HtmlUtil.EDIT;
	logger.debug("cardM.getCardId() >>>>> "+cardM.getCardType());
	HashMap<String,Object> CardInfo = CardInfoUtil.getCardInfo(cardM.getCardType());	
	String CardCode = SQLQueryEngine.display(CardInfo,"CARD_CODE");
	String CardLevel = SQLQueryEngine.display(CardInfo,"CARD_LEVEL");
	logger.debug("CardCode >> "+CardCode); 	
	logger.debug("CardLevel >> "+CardLevel);
	
	FormUtil formUtil = new FormUtil(subformId,request);
 %>
 
 <div class="panel panel-default">
	 <div class="panel-body">
		<div class="row form-horizontal">
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request,subformId,"DM_FIRST_NAME","DM_FIRST_NAME","col-sm-4 col-md-5 control-label")%>
					<%=HtmlUtil.dropdown("SUB_NAME",PRODUCT_CRADIT_CARD,"SUB_NAME_"+productElementId,"SUB_NAME", "",null, "","","", "", "col-sm-8 col-md-7", applicationItem, formUtil)%>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request,subformId,"CARD_LEVEL","CARD_LEVEL","col-sm-4 col-md-5 control-label")%>
					<%=HtmlUtil.dropdown("CARD_LEVEL",PRODUCT_CRADIT_CARD, "CARD_LEVEL_"+productElementId, "CARD_LEVEL", "", CardLevel, "",FIELD_ID_CARD_LEVEL,"ALL_ALL_ALL", "", "col-sm-8 col-md-7", applicationItem, formUtil)%>
				</div>
			</div>
			<div class="clearfix"></div>				
		</div>
	</div>
</div>
 
