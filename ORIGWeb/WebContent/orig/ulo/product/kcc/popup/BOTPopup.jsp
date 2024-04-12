<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.core.ulo.common.properties.FormControl"%>
<%@page import="com.eaf.orig.ulo.app.view.form.subform.product.manual.CardInfoUtil"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.engine.SQLQueryEngine"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.LoanDataM"%>
<%@page import="com.eaf.orig.cache.properties.CardTypeProperties"%>
<%@page import="com.eaf.orig.ulo.model.app.CardDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ModuleForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler"/>
<script type="text/javascript" src="orig/ulo/product/kcc/popup/js/KccPopup.js"></script>

<%
	String subformId = "BOT_POPUP";
	Logger logger = Logger.getLogger(this.getClass());
	String APPLICATION_CARD_TYPE_SUPPLEMENTARY= SystemConstant.getConstant("APPLICATION_CARD_TYPE_SUPPLEMENTARY");
	String FIELD_ID_CARD_TYPE = SystemConstant.getConstant("FIELD_ID_CARD_TYPE");
	String FIELD_ID_CARD_LEVEL =  SystemConstant.getConstant("FIELD_ID_CARD_LEVEL");
	String FIELD_ID_PERCENT_LIMIT_MAINCARD = SystemConstant.getConstant("FIELD_ID_PERCENT_LIMIT_MAINCARD");
	
	String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	String displayMode = HtmlUtil.EDIT;
	
	ApplicationDataM applicationItem = (ApplicationDataM)ModuleForm.getObjectForm();
	String applicationRecordId = applicationItem.getApplicationRecordId();
	ApplicationGroupDataM applicationGroup = FormControl.getOrigObjectForm(request);
	PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfoByRelation(applicationRecordId);
	if(null == personalInfo){
		personalInfo = new PersonalInfoDataM();
	}
	String productElementId = FormatUtil.getProductElementId(personalInfo,PRODUCT_CRADIT_CARD);	
	CardDataM cardM = applicationItem.getCard();
		HashMap<String,Object> CardInfo = CardInfoUtil.getCardInfo(cardM.getCardType());	
		String cardCode =(String)CardInfo.get("CARD_CODE");	
		String cardLevel =(String)CardInfo.get("CARD_LEVEL");		
 	
	String CARD_TYPE_DESC=	 	CacheControl.getName(FIELD_ID_CARD_TYPE, cardCode, "DISPLAY_NAME");
	String CARD_LEVEL_DESC = CacheControl.getName(FIELD_ID_CARD_LEVEL, cardLevel, "DISPLAY_NAME");
	FormUtil formUtil = new FormUtil(subformId,request);
 %>
	<div class="panel panel-default">
		 <div class="panel-body">
			<div class="row form-horizontal">
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.getSubFormLabel(request,subformId,"CARD_TYPE","CARD_TYPE","col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.span("", FormatUtil.display(CARD_TYPE_DESC), "subform-nopadding-right col-md-7")%>
					</div>
				</div>
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.getSubFormLabel(request,subformId,"CARD_LEVEL","CARD_LEVEL","col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.span("", FormatUtil.display(CARD_LEVEL_DESC), "col-md-7")%>
					</div>
				</div>
				<div class="clearfix"></div>
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.getSubFormLabel(request,applicationItem, subformId,"MEMBERSHIP_NO","MEMBERSHIP_NO","col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.textBox("MEMBERSHIP_NO",PRODUCT_CRADIT_CARD,"MEMBERSHIP_NO_"+productElementId,"MEMBERSHIP_NO",
							cardM.getMembershipNo(),"","50","","col-sm-8 col-md-7", applicationItem, formUtil)%>
					</div>
				</div>
				<div class="clearfix"></div>
			</div>
		</div>
	</div>
