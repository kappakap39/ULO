<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.LoanDataM"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.LoanTierDataM"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.FormControl"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.display.ElementInf"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.control.util.PersonalInfoUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.VerificationResultDataM"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="com.eaf.core.ulo.common.properties.ImplementControl"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="EntityForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler"/>
<script type="text/javascript" src="orig/ulo/subform/js/DiffRequestSubForm.js?v=1"></script>
<%
	Logger logger = Logger.getLogger(this.getClass());
	ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)EntityForm.getObjectForm();
	String[] DISPLAY_PRODUCT  = SystemConstant.getArrayConstant("DISPLAY_PRODUCT");
	ArrayList<String> products = applicationGroup.getMaxLifeCycleProducts(DISPLAY_PRODUCT);
	String PRODUCT_K_PERSONAL_LOAN = SystemConstant.getConstant("PRODUCT_K_PERSONAL_LOAN");	
	String FIELD_ID_TERM = SystemConstant.getConstant("FIELD_ID_TERM");
	String FIELD_ID_DIFF_REQUEST_CONTACT_RESULT = SystemConstant.getConstant("FIELD_ID_DIFF_REQUEST_CONTACT_RESULT");
	ApplicationDataM applicationM = applicationGroup.filterApplicationProductLifeCycle(PRODUCT_K_PERSONAL_LOAN);
	if (Util.empty(applicationM)) {
		applicationM = new ApplicationDataM();
	}
	
	ArrayList<LoanDataM>  loans = applicationM.getLoans();
	
	if(Util.empty(loans)){
	
		loans= new ArrayList<LoanDataM>();
	}
	LoanDataM loan = new LoanDataM();
	for(LoanDataM loanMTmp:loans){
		loan = loanMTmp;
	}
	
	BigDecimal reqLoanAmt = loan.getRequestLoanAmt();
	BigDecimal recLoanAmt = loan.getRecommendLoanAmt();
	//Initialize loanAmt as recomLoanAmt if loanAmt is empty
	BigDecimal loanAmt = (Util.empty(loan.getLoanAmt())) ? recLoanAmt : loan.getLoanAmt();
	
	String reqTerm = String.valueOf(loan.getRequestTerm());
	String recomTerm = String.valueOf(loan.getRecommendTerm());
	//Initialize term as recomTerm if term is empty
	String term = (loan.getTerm() != null && !(BigDecimal.ZERO.compareTo(loan.getTerm()) == 0)) ? String.valueOf(loan.getTerm()) : recomTerm;
	
	BigDecimal intAmt =  BigDecimal.ZERO;
	BigDecimal intRate = BigDecimal.ZERO;
	
	 BigDecimal interestRate = BigDecimal.ZERO;
	 if(null != loan.getLoanTiers())
	 {
		for (LoanTierDataM loanTier : loan.getLoanTiers()) 
		{
			intAmt = loanTier.getMonthlyInstallment();
			intRate = loanTier.getIntRateAmount();
		}
	 }
	 
	//#Defect3263
	int minCreditLimit = (loan.getMinCreditLimit() != null) ? loan.getMinCreditLimit().intValue() : 0;
	
	String styleTag = " style=\"text-align:right;\" ";
	String disableTag = " disabled ";
	
	String ROLE_DV =SystemConstant.getConstant("ROLE_DV");
	String userRole = (String) ORIGUser.getRoles().elementAt(0);
	String mode = (ROLE_DV.equals(userRole)) ? HtmlUtil.EDIT : HtmlUtil.VIEW;
	
	//Defect#2400
	String VER_CUS_COMPLETE_CANNOT_CONTACT_CUSTOMER = SystemConstant.getConstant("VER_CUS_COMPLETE_CANNOT_CONTACT_CUSTOMER");
	PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
	if(Util.empty(personalInfo)){
		personalInfo = new PersonalInfoDataM();
	}
	VerificationResultDataM verificationResult  = personalInfo.getVerificationResult();
	if(Util.empty(verificationResult)){
		verificationResult = new VerificationResultDataM();
	}
	
	String diffReqResultOption = "";
	String verComplete = verificationResult.getVerCusComplete();
	diffReqResultOption = (VER_CUS_COMPLETE_CANNOT_CONTACT_CUSTOMER.equals(verComplete)) ? verComplete : applicationM.getDiffRequestResult();
    
	String subformId ="DIFF_REQ_SUBFORM";
	FormUtil formUtil = new FormUtil(subformId,request);
%>
<section id="SECTION_DIFF_REQ_KPL" class="warpSubFormTemplate SECTION_PRODUCT_KPL">
	<header class="titlecontent"><%=LabelUtil.getText(request,PRODUCT_K_PERSONAL_LOAN)%></header>
		<section class ="work_area">
			<div class=" panel-default panel">
			 <table class="table table-striped table-hover">
			 	<thead>
						<tr>
							<th width="15%"></th>
							<th width="20%"><%=HtmlUtil.getText(request,"REG_INFO")%></th>
							<th width="30%"><%=HtmlUtil.getText(request,"RES_BEFORE_TEL")%></th>
							<th width="30%"><%=HtmlUtil.getText(request,"RES_AFTER_TEL")%></th>
							<th width="5%"></th>
						</tr>
				</thead>
				<tbody>
<% 
	for(String product : products){ 
%>
						<tr>
							<th width="15%"><%=HtmlUtil.getText(request,"KPL_CREDIT_LINE")%></th>
							<th width="20%"><%=reqLoanAmt%></th>
							<th width="30%"><%=(recLoanAmt != null) ? recLoanAmt : "0"%></th>
							<th width="30%">
								<%=HtmlUtil.currencyBox("AMOUNT_LOAN", PRODUCT_K_PERSONAL_LOAN, "AMOUNT_LOAN", "AMOUNT_LOAN", 
								loanAmt, "", false, "15", styleTag, "",applicationM, formUtil) %>
							</th>
							<th></th>
						</tr>
						<tr>
							<th width="15%"><%=HtmlUtil.getText(request,"TERM_LENGTH")%></th>
							<th width="20%"><%=reqTerm%></th>
							<th width="30%"><%=(recomTerm != null) ? recomTerm : "-"%></th>
							<th width="30%">
								<%=HtmlUtil.dropdown("TERM", PRODUCT_K_PERSONAL_LOAN, "TERM", "TERM", "", 
						 		term , "",FIELD_ID_TERM,"", "", "",applicationM, formUtil)%>
							</th>
							<th width="5%">
								<%=HtmlUtil.button("CALCULATE_BTN", "Calculate", mode , "btn2 btn2-green", "", request)%>
							</th>
						</tr>
						<tr>
							<th width="15%"><%=HtmlUtil.getText(request,"KPL_INSTALLMENT_TH")%></th>
							<th width="20%"><%="-"%></th>
							<th width="30%"><%=(!Util.empty(intAmt)) ? intAmt.toString() : "-"%></th>
							<th width="30%">
							<%=HtmlUtil.currencyBox("INSTALLMENT_AMT", PRODUCT_K_PERSONAL_LOAN, "INSTALLMENT_AMT", "", 
							   (Util.empty(loan.getInstallmentAmt())) ? intAmt : loan.getInstallmentAmt(), "", false, "15", styleTag + disableTag, "",applicationM, formUtil) %>
							</th>
							<th width="5%"></th>
						</tr>
						<tr>
							<th width="15%"><%=HtmlUtil.getText(request,"INT_RATE")%></th>
							<th width="20%"><%="-"%></th>
							<th width="30%"><%=(!Util.empty(intRate)) ? intRate.toString() : "-"%></th>
							<th width="30%">
							<%=HtmlUtil.numberBox("INT_RATE_" + PRODUCT_K_PERSONAL_LOAN,(Util.empty(loan.getInterestRate())) ? intRate : loan.getInterestRate(),"",false,"15", "",styleTag + disableTag,request)%>
							</th>
							<th width="5%"></th>
						</tr>
<% } %>
				</tbody>
			 </table>
		</div>
		<div class="row form-horizontal">
		<div class="col-sm-9">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request,subformId,"DIFF_REQ_CONTACT_RESULT","DIFF_REQ_CONTACT_RESULT","col-sm-4 col-md-5 control-label")%>
					<%=HtmlUtil.dropdown("DIFF_REQ_CONTACT_RESULT", PRODUCT_K_PERSONAL_LOAN, "DIFF_REQ_CONTACT_RESULT", "DIFF_REQ_CONTACT_RESULT", "", 
					diffReqResultOption, "",FIELD_ID_DIFF_REQUEST_CONTACT_RESULT,"", "", "col-sm-8 col-md-7",applicationM, formUtil)%>						
				</div>
		</div>
		</div>
	</section>
</section>
<%=HtmlUtil.hidden("calcu",null) %>
<%=HtmlUtil.hidden("minCL",minCreditLimit) %>
<script>
$("[name='AMOUNT_LOAN_KPL']").on('change', function() {
	clearInstallInterestField();	
});
$("[name='TERM_KPL']").on('change', function() {
	clearInstallInterestField();		
});
</script>