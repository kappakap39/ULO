<%@page import="com.eaf.core.ulo.security.encryptor.EncryptorFactory"%>
<%@page import="com.eaf.core.ulo.security.encryptor.Encryptor"%>
<%@page import="java.util.Arrays"%>
<%@page import="com.eaf.core.ulo.common.util.SessionControl"%>
<%@page import="com.eaf.orig.ulo.model.control.FlowControlDataM"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalRelationDataM"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.LoanDataM"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.CardDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="org.apache.log4j.Logger"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>

<script type="text/javascript" src="orig/ulo/subform/summary/increase/js/SummaryIncreaseKCCSubForm.js"></script>
<%
 Logger logger = Logger.getLogger(this.getClass());
 Encryptor encryptor = EncryptorFactory.getDIHEncryptor();
 String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
 String FIELD_ID_APPLICATION_CARD_TYPE =SystemConstant.getConstant("FIELD_ID_APPLICATION_CARD_TYPE");
 String APPLICATION_CARD_TYPE_BORROWER = SystemConstant.getConstant("APPLICATION_CARD_TYPE_BORROWER");
 String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
 String APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
 String APPLICATION_CARD_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("APPLICATION_CARD_TYPE_SUPPLEMENTARY");
 String[] DECISION_FINAL_DECISION_CONDITION = SystemConstant.getArrayConstant("DECISION_FINAL_DECISION_CONDITION");
 ArrayList<String> FINAL_DECISION_CONDITION_LIST = new ArrayList<String>(Arrays.asList(DECISION_FINAL_DECISION_CONDITION));
 
 FlowControlDataM flowControl = (FlowControlDataM) request.getSession().getAttribute(SessionControl.FlowControl);	
 ApplicationGroupDataM  applicationGroup =  ORIGForm.getObjectForm();
 if(Util.empty(applicationGroup)){
	applicationGroup = new ApplicationGroupDataM();
 }
  ArrayList<ApplicationDataM> borrowerApplications = applicationGroup.filterApplicationProductLifeCycles(PRODUCT_CRADIT_CARD, APPLICATION_CARD_TYPE_BORROWER);
 
%>
<div class="panel panel-default">
	<div class="panel-heading"><%=LabelUtil.getText(request, "SUMMARY_INCREASE_KCC_SUBFORM")%></div>
	<div class="panel-body">
		<div class="row form-horizontal">
		<%
			if(!Util.empty(borrowerApplications)){
				 	ApplicationDataM borrowerApplication = borrowerApplications.get(0);
				 	PersonalInfoDataM borrowerPersonalInfo = applicationGroup.getPersonalInfoByRelation(borrowerApplication.getApplicationRecordId());
				if (Util.empty(borrowerPersonalInfo)) {
					borrowerPersonalInfo = new PersonalInfoDataM();
				}
		%>
			<div class="col-sm-6">
				<div class="form-group">
					<div class="col-sm-12 col-md-12 "><%=FormatUtil.display(borrowerPersonalInfo.getEnFirstName())%>&nbsp;<%=FormatUtil.display(borrowerPersonalInfo.getEnLastName())%></div>
				</div>
			</div>
			<div class="clearfix"></div>	
		<%} %>
		
		<% ArrayList<ApplicationDataM> applications = applicationGroup.filterApplicationLifeCycle(PRODUCT_CRADIT_CARD); 
		if(!Util.empty(applications)) {
			for(ApplicationDataM applicationItem:applications){
				if(!FINAL_DECISION_CONDITION_LIST.contains(applicationItem.getFinalAppDecision())){
						String applicationRecordId = applicationItem.getApplicationRecordId();
						String mainCardRecordId = applicationItem.getMaincardRecordId();
						CardDataM card = applicationItem.getCard();
						if(Util.empty(card)){
							card = new CardDataM();
						}
						LoanDataM loan = applicationItem.getLoan();
						if(Util.empty(loan)){
							loan = new LoanDataM();
						}
						PersonalInfoDataM borrowerPersonalInfo = applicationGroup.getPersonalInfoByRelation(applicationRecordId);
						if(Util.empty(borrowerPersonalInfo)){
							borrowerPersonalInfo = new PersonalInfoDataM();
						}
						String CARD_ENCRP = card.getCardNo();
						String CARD_NO="";
				  		if(!Util.empty(CARD_ENCRP)){
				 			try{
								CARD_NO = encryptor.decrypt(CARD_ENCRP);
							}catch(Exception e){
								logger.debug("ERROR",e);
							}
						}
						String TITLE_CARD_TYPE = CacheControl.getName(FIELD_ID_APPLICATION_CARD_TYPE, card.getApplicationType());
		%>
			<div class="col-sm-6">
				<div class="form-group">
					<div class="col-sm-8 col-md-7 "><%=FormatUtil.getCardNo(CARD_NO)%></div>
					<div class="textdisplay"><%=HtmlUtil.getLabel(TITLE_CARD_TYPE, "text_center")%></div>
					
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getLabel(request,"FINAL_CREDIT_LIMIT","col-sm-4 col-md-5")%>
					<div class="col-sm-8 col-md-7 "><%=FormatUtil.display(loan.getLoanAmt(),FormatUtil.Format.CURRENCY_FORMAT)%></div>
				</div>
			</div>
		   <div class="clearfix"></div>
		
		 <%		}
			}
		} %>
	
	 </div>
	</div>
</div>