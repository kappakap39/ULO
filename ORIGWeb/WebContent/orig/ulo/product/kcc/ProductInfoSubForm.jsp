<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.orig.ulo.model.app.CardDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.LoanDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<!-- <script type="text/javascript" src="orig/ulo/product/js/ProductInfoSubForm.js"></script> -->
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>

<%
	//String subformId = "KCC_PRODUCT_INFO_SUBFORM";
	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");	
	Logger logger = Logger.getLogger(this.getClass());
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	if(null == applicationGroup){
		applicationGroup = new ApplicationGroupDataM();
	}
	ApplicationDataM applicationM= applicationGroup.getApplicationProduct(MConstant.Product.CREDIT_CARD);
	
	PersonalInfoDataM personalInfo = ORIGForm.getObjectForm().getPersonalInfo(PERSONAL_TYPE);
	if(null == personalInfo){
		personalInfo = new PersonalInfoDataM();
	}
	int PERSONAL_SEQ = personalInfo.getSeq();	
	
	if(Util.empty(applicationM)){
		applicationM = new ApplicationDataM();
		applicationGroup.addApplications(applicationM);
	}
	 ArrayList<LoanDataM> loans = applicationM.getLoans();
	 if(Util.empty(loans)){
	 	 loans = new ArrayList<LoanDataM>();
	 	applicationM.setLoans(loans);
	 }
	CardDataM	 card = new CardDataM();
	if(!Util.empty(loans)){
		 for(LoanDataM loan:loans){
	 			card= loan.getCard();
	 		if(Util.empty(card)){
		 		card = new CardDataM();
		 		loan.setCard(card);
	 		}
	 	 }
	}
	// TODO: DF000000000649
	String displayMode = HtmlUtil.EDIT;
	
// 	String TAG_SMART_DATA_PERSONAL = FormatUtil.getSmartDataEntryId(MConstant.Product.CREDIT_CARD,PERSONAL_SEQ);
	
	String FIELD_ID_PRODUCT_TYPE = SystemConstant.getConstant("FIELD_ID_PRODUCT_TYPE");
	String FIELD_ID_CARD_LEVEL = SystemConstant.getConstant("FIELD_ID_CARD_LEVEL");
	String FIELD_ID_CARD_TYPE = SystemConstant.getConstant("FIELD_ID_CARD_TYPE");
	//  logger.debug("subformId >>>> "+subformId);
	String tag ="ADD_CARD_INFO_BORROWERActionJS()";
	logger.debug("tag >>> "+tag);
%>
<%-- <div class="col-xs-12">
	<div class="panel panel-default">
		 <div class="panel-body">
				<div class="row form-horizontal">
					<div class="col-sm-6">
						<div class="form-group">
							<%=HtmlUtil.getFieldLabel(request, "PRODUCTS_CARD_TYPE", "col-sm-4 col-md-5 control-label")%>
						<div class="col-sm-8 col-md-7"><%=HtmlUtil.dropdown("PRODUCTS_CARD_TYPE", applicationM.getBusinessClassId(), FIELD_ID_PRODUCT_TYPE, applicationM.getBusinessClassId(), displayMode, HtmlUtil.tagId(TAG_SMART_DATA_PERSONAL, "PRODUCTS_CARD_TYPE"), request)%></div>
						</div>
					</div>
				
					<div class="col-sm-6">
						<div class="form-group">
							<%=HtmlUtil.getFieldLabel(request, "CARD_TYPE", "col-sm-4 col-md-5 control-label")%>
						<div class="col-sm-8 col-md-7"><%=HtmlUtil.dropdown("CARD_TYPE", card.getCardType(), FIELD_ID_CARD_TYPE, applicationM.getBusinessClassId(), displayMode, HtmlUtil.tagId(TAG_SMART_DATA_PERSONAL, "CARD_TYPE"), request)%></div>
						</div>
					</div>
					
					<div class="col-sm-6">
						<div class="form-group">
							<%=HtmlUtil.getFieldLabel(request, "CARD_LEVEL", "col-sm-4 col-md-5 control-label")%>
						<div class="col-sm-8 col-md-7"><%=HtmlUtil.dropdown("CARD_LEVEL","", FIELD_ID_CARD_LEVEL, applicationM.getBusinessClassId(), displayMode, HtmlUtil.tagId(TAG_SMART_DATA_PERSONAL, "CARD_LEVEL"), request)%></div>
						</div>
					</div>
					
					
					<div class="col-sm-6">
						<div class="form-group">
							<%=HtmlUtil.icon("ADD_CARD_INFO_BORROWER", HtmlUtil.EDIT, "btn_add", tag, request) %> 
							
						</div>
					</div>
					<%=HtmlUtil.hidden("subformId", subformId)%>
				</div>
			</div>
		</div>
	</div> --%>