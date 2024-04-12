<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.core.ulo.common.properties.FormControl"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.orig.ulo.app.view.form.subform.product.manual.CardInfoUtil"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.engine.SQLQueryEngine"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.SaleInfoDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.CardDataM"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<script type="text/javascript" src="orig/ulo/product/kcc/popup/js/KccPopup.js"></script>
<jsp:useBean id="ModuleForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler"/>

<script type="text/javascript" src="js/MuanThaiInfoSubForm.js"></script>
<%
	String subformId = "MUANG_THAI_POPUP";
	Logger logger = Logger.getLogger(this.getClass());
	HashMap<String,Object> entityForm = (HashMap<String,Object>)ModuleForm.getObjectForm();
// 	ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM) entityForm.get("APPLICATIONGROUP");
	ApplicationDataM applicationItem = (ApplicationDataM) entityForm.get("APPLICATION");
// 	ApplicationDataM applicationItem = (ApplicationDataM)ModuleForm.getObjectForm();
	String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	String CACHE_CARD_INFO = SystemConstant.getConstant("CACHE_CARD_INFO");
	String FIELD_ID_CARD_TYPE =SystemConstant.getConstant("FIELD_ID_CARD_TYPE");
	String FIELD_ID_CARD_LEVEL =  SystemConstant.getConstant("FIELD_ID_CARD_LEVEL");
	CardDataM card= applicationItem.getCard();
	if(Util.empty(card)){
		card = new CardDataM();
	}
	String displayMode = HtmlUtil.EDIT;
	HashMap<String,String> hashCardInfo = ModuleForm.getRequestData();
	
	SaleInfoDataM saleInfo = (SaleInfoDataM)entityForm.get("SALE_INFO");
	if(Util.empty(saleInfo)){
		saleInfo = new SaleInfoDataM();
	}

	HashMap<String,Object> CardInfo = CardInfoUtil.getCardInfo(card.getCardType());	
	String cardCode =(String)CardInfo.get("CARD_CODE");	
	String cardLevel =(String)CardInfo.get("CARD_LEVEL");			
	String CARD_TYPE_DESC=	 	CacheControl.getName(FIELD_ID_CARD_TYPE, cardCode, "DISPLAY_NAME");
	String CARD_LEVEL_DESC = CacheControl.getName(FIELD_ID_CARD_LEVEL, cardLevel, "DISPLAY_NAME");
	
	String applicationRecordId = applicationItem.getApplicationRecordId();
	ApplicationGroupDataM applicationGroup = FormControl.getOrigObjectForm(request);
	PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfoByRelation(applicationRecordId);
	if(null == personalInfo){
		personalInfo = new PersonalInfoDataM();
	}
	String productElementId = FormatUtil.getProductElementId(personalInfo,PRODUCT_CRADIT_CARD);	
	
	FormUtil formUtil = new FormUtil(subformId,request);
 %>
	<div class="panel panel-default">
		<div class="panel-body">
			<div class="row form-horizontal">
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.getSubFormLabel(request,subformId,"CARD_TYPE","CARD_TYPE","col-sm-4 col-md-5 control-label")%>
						<div class="col-sm-8 col-md-7"><%=FormatUtil.display(CARD_TYPE_DESC)%></div>
					</div>
				</div>
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.getSubFormLabel(request,subformId,"CARD_LEVEL","CARD_LEVEL","col-sm-4 col-md-5 control-label")%>
						<div class="col-sm-8 col-md-7"><%=FormatUtil.display(CARD_LEVEL_DESC)%></div>
					</div>
				</div>
				<div class="clearfix"></div>
			</div>
		</div>		
		<div class="panel-heading"><%=LabelUtil.getText(request, "MUANGTHAI_INFO_SUBFORM")%></div>
		<div class="panel-body">
			<div class="row form-horizontal">
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.getSubFormLabel(request,subformId,"MTL_SALES_ID","MTL_SALES_ID","col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.textBox("MTL_SALES_ID",PRODUCT_CRADIT_CARD,"MTL_SALES_ID_"+productElementId,"MTL_SALES_ID",
							saleInfo.getSalesId(),"","50","","col-sm-8 col-md-7",applicationItem,formUtil)%>
					</div>
				</div>
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.getSubFormLabel(request,subformId,"MTL_SALES_PHONE_NO","MTL_SALES_PHONE_NO","col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.textBoxTel("MTL_SALES_PHONE_NO",PRODUCT_CRADIT_CARD,"MTL_SALES_PHONE_NO_"+productElementId,"MTL_SALES_PHONE_NO",
							saleInfo.getSalesPhoneNo(),"","","col-sm-8 col-md-7",applicationItem,formUtil)%>
					</div>
				</div>
				
				<div class="clearfix"></div>
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.getSubFormLabel(request,applicationItem,subformId,"MTL_CUSTOMER_NO","MTL_CUSTOMER_NO","col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.textBox("MTL_CUSTOMER_NO",PRODUCT_CRADIT_CARD,"MTL_CUSTOMER_NO"+productElementId,"MTL_CUSTOMER_NO",
							card.getMembershipNo(),"","50","","col-sm-8 col-md-7",applicationItem,formUtil)%>
					</div>
				</div>
				
			</div>
		</div>
	</div>
 