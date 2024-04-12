<%@page import="com.eaf.orig.ulo.control.util.PersonalInfoUtil"%>
<%@page import="com.eaf.orig.profile.model.UserDetailM"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemCache"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.Date"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.orig.ulo.app.view.form.privilegeprojectcode.util.PrivilegeUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.IdentifyQuestionSetDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.AddressDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PhoneVerificationDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.HRVerificationDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.VerificationResultDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.LoanDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.CardDataM"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.orig.ulo.app.view.util.kpl.KPLUtil"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.display.ElementInf"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.core.ulo.common.properties.FormControl"%>
<%@page import="com.eaf.core.ulo.common.properties.ImplementControl"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page contentType="text/html;charset=UTF-8"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler" />
<script type="text/javascript" src="orig/ulo/subform/js/FollowDetailSubform.js"></script>
<%
	String subformId = "FOLLOW_DETAIL_SUBFORM";
	Logger logger = Logger.getLogger(this.getClass());
	String CACHE_NAME_USER = SystemConstant.getConstant("CACHE_NAME_USER");
	String LOCAL_TH = SystemConstant.getConstant("LOCAL_TH");
	String FIELD_ID_CONTACT_TIME = SystemConstant.getConstant("FIELD_ID_CONTACT_TIME");
	String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");	
	String VERIFY_CUSTOMER_DISPLAY = SystemConstant.getConstant("VERIFY_CUSTOMER_DISPLAY");
	String displayMode = HtmlUtil.EDIT;
	UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
	ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)ORIGForm.getObjectForm();
	PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
	if(Util.empty(personalInfo)){
		personalInfo = new PersonalInfoDataM();
	}
	VerificationResultDataM verificationResult = personalInfo.getVerificationResult();
	if(Util.empty(verificationResult)){
	 verificationResult = new VerificationResultDataM();
	}
	ApplicationDataM applicationInfo = applicationGroup.getApplicationProduct(PRODUCT_CRADIT_CARD);
	if(Util.empty(applicationInfo)){
	 applicationInfo = new ApplicationDataM();
	}
	CardDataM borrowerCard = applicationInfo.getCard();
	if(Util.empty(borrowerCard)){
	 borrowerCard = new CardDataM();
	}
	
	boolean isKPL = KPLUtil.isKPL(applicationGroup);
	
	FormUtil formUtil = new FormUtil(subformId,request);	
%>
		<%
			ElementInf elementCus = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.VERIFICATION,"CUSTOMER_INFO_APPLICANT");
		 		elementCus.writeElement(pageContext,null);
		%>

<div class="panel panel-default">
	<div class="panel-body" id="ContactCustomerHR" >
		<div class="row form-horizontal">
			<%if(!isKPL){ %>
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getFieldLabel(request, "CARD_TYPE","")%>
					<%=FormatUtil.display(PrivilegeUtil.getCardTypeDesc(borrowerCard.getCardType())) %>
				</div>
			</div>
			 <% } else { %>
		   	<div class="col-sm-6">
		    	<div class="form-group">
		    	 	<%=HtmlUtil.getFieldLabel(request, "LOAN_TYPE","")%>
		     		<%=FormatUtil.display("Xpress Loan")%>
		   		</div>
		    </div>
		    <% } %>
			<!-- <div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getFieldLabel(request, "CONTACT_TIME","col-sm-5")%>
<%-- 					<%=HtmlUtil.dropdown("CONTACT_TIME", "", "", personalInfo.getContactTime(), "", FIELD_ID_CONTACT_TIME, "ALL", displayMode, HtmlUtil.tagId("CONTACT_TIME"), "col-sm-6", request)%> --%>
					<%=HtmlUtil.dropdown("CONTACT_TIME", "", "CONTACT_TIME",  "CONTACT_TIME", "", personalInfo.getContactTime(), "", FIELD_ID_CONTACT_TIME,  "ALL", "",  "col-sm-6", formUtil) %>
				</div>
			</div>-->
		</div>
	</div>
</div>

<div class="panel panel-default">
	<div class="panel-heading"><%=LabelUtil.getText(request,"PHONE_CONTACT")%></div>
	<table class="table table-striped table-hover">
		<tbody>
			<%	ArrayList<ElementInf> elements = ImplementControl.getElements(MConstant.IMPLEMENT_TYPE.VERIFY_PHONE_NO);
				for(ElementInf elementInf:elements){
					elementInf.setObjectRequest(VERIFY_CUSTOMER_DISPLAY);
					elementInf.writeElement(pageContext, personalInfo);
				}
			%>
	 	</tbody>
	</table>
</div>