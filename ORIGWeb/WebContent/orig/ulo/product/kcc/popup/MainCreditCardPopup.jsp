<%@page import="com.eaf.orig.ulo.app.view.form.subform.product.manual.CardInfoUtil"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.engine.SQLQueryEngine"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.CardDataM"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ModuleForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler"/>
<%
	String subformId = "MAIN_CREDIT_CARD_POPUP";
	Logger logger = Logger.getLogger(this.getClass());
	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	ApplicationDataM applicationItem = (ApplicationDataM)ModuleForm.getObjectForm();
	CardDataM cardM= applicationItem.getCard();
	if(Util.empty(cardM)){
		cardM = new CardDataM();
	}
	
	String displayMode = HtmlUtil.EDIT;
	logger.debug("cardM.getCardId() >>>>> "+cardM.getCardType());
	HashMap<String,Object> CardInfo = CardInfoUtil.getCardInfo(cardM.getCardType());	
	String CardCode = SQLQueryEngine.display(CardInfo,"CARD_CODE");
	String CardLevel = SQLQueryEngine.display(CardInfo,"CARD_LEVEL");
	
	String FIELD_ID_CARD_TYPE = SystemConstant.getConstant("FIELD_ID_CARD_TYPE");
	String FIELD_ID_CARD_LEVEL =  SystemConstant.getConstant("FIELD_ID_CARD_LEVEL");
	
	String CARD_TYPE_DESC =	CacheControl.getName(FIELD_ID_CARD_TYPE, CardCode, "DISPLAY_NAME");
	String CARD_LEVEL_DESC = "";
	if(!Util.empty(CardLevel)) {
		CARD_LEVEL_DESC = CacheControl.getName(FIELD_ID_CARD_LEVEL, CardLevel, "DISPLAY_NAME");
	}
	logger.debug("CardCode >> "+CardCode); 	
	logger.debug("CardLevel >> "+CardLevel);
	
	FormUtil formUtil = new FormUtil(subformId,request);
 %>
	<div class="panel panel-default">
		<div class="panel-body">
			<div class="row form-horizontal">
			<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.getSubFormLabel(request,subformId,"CARD_TYPE","CARD_TYPE", "col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.span("", FormatUtil.display(CARD_TYPE_DESC), "subform-nopadding-right col-md-7")%>
					</div>
					</div>
					<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.getSubFormLabel(request,subformId,"CARD_LEVEL","CARD_LEVEL", "col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.span("", FormatUtil.display(CARD_LEVEL_DESC), "col-md-7")%>
					</div>
					</div>
			</div>
		</div>
	</div>





