<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.CardDataM"%>
<%@page import="com.eaf.orig.ulo.app.view.form.subform.product.manual.CardInfoUtil"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<jsp:useBean id="ModuleForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler"/>

<script type="text/javascript" src="orig/ulo/product/js/OthersInfoSubForm.js"></script>
<%
	String subformId = "K_EXPRESS_CACH_PRODUCT_POPUP_1";
	Logger logger = Logger.getLogger(this.getClass());
	String PRODUCT_K_EXPRESS_CASH =SystemConstant.getConstant("PRODUCT_K_EXPRESS_CASH");	
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	ApplicationDataM applicationItem = (ApplicationDataM)ModuleForm.getObjectForm();
	CardDataM cardM = applicationItem.getCard();
	if(Util.empty(cardM)){
		cardM = new CardDataM();
	}
	String displayMode = HtmlUtil.EDIT;
	HashMap<String,Object> CardInfo = CardInfoUtil.getCardInfo(cardM.getCardType());	
	String cardCode =(String)CardInfo.get("CARD_CODE");				
 	PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfoRelation(applicationItem.getApplicationRecordId(),PERSONAL_TYPE_APPLICANT,APPLICATION_LEVEL);
	if (null == personalInfo) {
		personalInfo = new PersonalInfoDataM();
	}
	String FIELD_ID_CARD_TYPE = SystemConstant.getConstant("FIELD_ID_CARD_TYPE");
	String FIELD_ID_CARD_LEVEL =  SystemConstant.getConstant("FIELD_ID_CARD_LEVEL");
	String CARD_TYPE_DESC =	CacheControl.getName(FIELD_ID_CARD_TYPE, cardCode, "DISPLAY_NAME");
	String CACHE_CARD_INFO = SystemConstant.getConstant("CACHE_CARD_INFO");
	
	String productElementId = FormatUtil.getProductElementId(personalInfo,PRODUCT_K_EXPRESS_CASH);	
	
	FormUtil formUtil = new FormUtil(subformId,request);
%>
	<div class="panel panel-default">
		 <div class="panel-body">
			<div class="row form-horizontal">
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.getSubFormLabel(request,subformId,"DM_FIRST_NAME", "DM_FIRST_NAME", "col-sm-4 col-md-5 control-label")%>
						<%=FormatUtil.display(personalInfo.getThName())%>
					</div>
				</div>
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.getFieldLabel(request, "CARD_TYPE","col-sm-4 col-md-5 control-label")%>
						<%=FormatUtil.display(CARD_TYPE_DESC)%>
					</div>
				</div>
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.getSubFormLabel(request,subformId,"GROUP_NO", "GROUP_NO", "col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.textBox("GROUP_NO", PRODUCT_K_EXPRESS_CASH,"GROUP_NO_"+productElementId,"GROUP_NO",
							cardM.getGangNo(),"","5","","col-sm-8 col-md-7",formUtil)%>
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.getSubFormLabel(request,subformId,"NO_APP_IN_GANG", "NO_APP_IN_GANG", "col-sm-5 col-md-6 control-label")%>
						<%=HtmlUtil.numberBox("NO_APP_IN_GANG", PRODUCT_K_EXPRESS_CASH, "NO_APP_IN_GANG_"+productElementId, "NO_APP_IN_GANG", 
							cardM.getNoAppInGang(), "", "", "", "", false, "5", "", "col-sm-7 col-md-6", formUtil) %>
					</div>
				</div>
			</div>
		</div>
	</div>
