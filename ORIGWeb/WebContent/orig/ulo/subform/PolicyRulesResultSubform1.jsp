<%@page import="com.eaf.orig.ulo.control.util.ApplicationUtil"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.google.gson.Gson"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.eaf.core.ulo.common.util.FormEffects"%>
<%@page import="java.util.HashSet"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.Comparator"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.core.ulo.common.properties.ListBoxControl"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil.Format"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.util.SessionControl"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.orig.ulo.app.view.util.kpl.KPLUtil"%>
<%@page import="com.eaf.orig.ulo.control.util.PersonalInfoUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PolicyRulesDetailDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PolicyRulesDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ORPolicyRulesDetailDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ORPolicyRulesDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.VerificationResultDataM"%>
<%@page import="com.eaf.orig.ulo.model.ncb.NcbInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.LoanDataM" %>
<%@page import="com.eaf.orig.ulo.model.app.BScoreDataM" %>
<%@page import="com.eaf.orig.ulo.model.app.ProductFactor" %>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<script type="text/javascript" src="orig/ulo/subform/js/PolicyRulesResultSubform1.js"></script>
<%
	String subformId  ="POLICY_RULES_RESULT_SUBFORM_1";
	int MAX_LIFE_CYCLE = Integer.parseInt(SystemConstant.getConstant("MAX_LIFE_CYCLE"));	 
	int LOAN_INDEX=0;
	int POLICY_RULES_INDEX=0;
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String FIELD_ID_PERSONAL_TYPE = SystemConstant.getConstant("FIELD_ID_PERSONAL_TYPE");
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY"); 
	String APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	String[] SWITCH_OR_POLICY_CODE  = SystemConstant.getArrayConstant("SWITCH_OR_POLICY_CODE");
	String CACHE_CARD_INFO = SystemConstant.getConstant("CACHE_CARD_INFO");
	String OR_RESULT_PASS =SystemConstant.getConstant("OR_RESULT_PASS");
	String OR_RESULT_REFER =SystemConstant.getConstant("OR_RESULT_REFER");
	String OR_RESULT_POLICY_EXCP=SystemConstant.getConstant("OR_RESULT_POLICY_EXCEPTION");
	String OR_RESULT_FAIL=SystemConstant.getConstant("OR_RESULT_FAIL");
	String POLICY_VER_RESULT_REFER=SystemConstant.getConstant("POLICY_VER_RESULT_REFER");
	String POLICY_VER_RESULT_FAIL=SystemConstant.getConstant("POLICY_VER_RESULT_FAIL");
	String GUIDE_LINE_VER_RESULT_FAIL=SystemConstant.getConstant("GUIDE_LINE_VER_RESULT_FAIL");
	String GUIDE_LINE_VER_RESULT_BLANK=SystemConstant.getConstant("GUIDE_LINE_VER_RESULT_BLANK");
	
	String FIELD_ID_PRODUCT_TYPE = SystemConstant.getConstant("FIELD_ID_PRODUCT_TYPE");
	String FIELD_ID_POLICY_RESULT = SystemConstant.getConstant("FIELD_ID_POLICY_RESULT");
	String FIELD_ID_OR_GUIDELINE = SystemConstant.getConstant("FIELD_ID_OR_GUIDELINE");
	String FIELD_ID_POLICY_RULE = SystemConstant.getConstant("FIELD_ID_POLICY_RULE");
	String FIELD_ID_OR_GUIDELINE_VER_RESULT = SystemConstant.getConstant("FIELD_ID_OR_POLICY_VER_RESULT");
	String FIELD_ID_POLICY_VER_RESULT = SystemConstant.getConstant("FIELD_ID_POLICY_VER_RESULT");
	String FIELD_ID_REJECTED = SystemConstant.getConstant("FIELD_ID_REJECTED");
	String NHSO_RD_POLICY_CODE = SystemConstant.getConstant("NHSO_RD_POLICY_CODE");
	ArrayList<String> listSwitchPolicyCode = new ArrayList<String>(Arrays.asList(SWITCH_OR_POLICY_CODE));
	ArrayList<String> listSwitchOrPolicyCode = new ArrayList<String>(Arrays.asList(NHSO_RD_POLICY_CODE));
	ArrayList<String> CA_DLA_FORM = new ArrayList<String>(Arrays.asList(SystemConstant.getArrayConstant("CA_DLA_FORM")));
	
	String CONFIG_ID_OR_RESULT_FAIL_REFER = SystemConstant.getConstant("CONFIG_ID_OR_RESULT_FAIL_REFER");
	String CONFIG_ID_OR_RESULT_BLANK = SystemConstant.getConstant("CONFIG_ID_OR_RESULT_BLANK");
	String CONFIG_ID_OR_RESULT_FAIL = SystemConstant.getConstant("CONFIG_ID_OR_RESULT_FAIL");
	
	String PRODUCT_K_PERSONAL_LOAN = SystemConstant.getConstant("PRODUCT_K_PERSONAL_LOAN");
	
	String FIELD_ID_SORCE_OF_INCOME = SystemConstant.getConstant("FIELD_ID_SORCE_OF_INCOME");
	String INCOME_SOURCE_OLD_INCOME_CARDLINK = SystemConstant.getConstant("INCOME_SOURCE_OLD_INCOME_CARDLINK");
	String APPLICATION_TYPE_ADD = SystemConstant.getConstant("APPLICATION_TYPE_ADD");
	Logger logger = Logger.getLogger(this.getClass());
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	logger.debug("MaxLifeCycle : "+ applicationGroup.getMaxLifeCycleFromApplication());	   	
	logger.debug("FormId : "+ORIGForm.getFormId());	   	   	
	
	PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
	
	//start set refer criteria
	ArrayList<ApplicationDataM> applicationApps = applicationGroup.getApplications();
	logger.debug("application.size : "+applicationApps.size());
	List<String> criteriaList = new ArrayList<String>();
	Set<String> hashSet = new HashSet<String>();
	if(Util.empty(applicationApps)){
		applicationApps = new ArrayList<ApplicationDataM>();
	}
	ArrayList<ApplicationDataM> applicationAppcriteria = applicationGroup.filterApplicationLifeCycle();
	if(Util.empty(applicationAppcriteria)){
		applicationAppcriteria = new ArrayList<ApplicationDataM>();
	}
	// constance LoadDataM and VerificationResultDataM for display
	LoanDataM appLoan = new LoanDataM() ;
	// cal 
	VerificationResultDataM verResult = new VerificationResultDataM();
	for(ApplicationDataM applicationApp : applicationAppcriteria){
		// get VerificationResultDataM refer aScore form applicationApp
		 verResult= applicationApp.getVerificationResult();
		if(!Util.empty(applicationApp.getReferCriteria())){
			String[] criteriaApps = applicationApp.getReferCriteria().split("\\|");
			if(!Util.empty(criteriaApps)){
				for(String criteriaApp : criteriaApps){
					criteriaList.add(criteriaApp);
				}
			}
		}
	}
		// get loan refer Product Factor from applicationApp
		ArrayList<ApplicationDataM> applicationAppSorts = applicationGroup.getSortLifeCycleApplicationDesc();
		for(ApplicationDataM applicationAppSort :applicationAppSorts){
			if(!"SUP".equals(applicationAppSort.getApplicationType())){
				appLoan = applicationAppSort.getLoan();
				break;
			}
		}
		
		// get all Lone ArrayList for Product factor
		ArrayList<LoanDataM> loadArrayList = new ArrayList<LoanDataM>();
		for(ApplicationDataM applicationObject : applicationAppSorts) {
			ArrayList<LoanDataM> tmpLoanArray = applicationObject.getLoans();
			for(LoanDataM loanObject: tmpLoanArray){
				loadArrayList.add(loanObject);
			}
		}
		applicationGroup.getSortLifeCycleApplication();
	if(Util.empty(appLoan)){
		appLoan = new LoanDataM();
	}
	if(Util.empty(verResult)){
		verResult = new VerificationResultDataM();
	}
	hashSet.addAll(criteriaList);
	criteriaList.clear();
	criteriaList.addAll(hashSet);
	//end set refer criteria
	// get ncbInfos for fico score var
	ArrayList<NcbInfoDataM> ficoScoreScoreList = personalInfo.getNcbInfos();
		if(Util.empty(ficoScoreScoreList)){
			ficoScoreScoreList = new ArrayList<NcbInfoDataM>();
		}
	if(Util.empty(personalInfo)){
		personalInfo = new PersonalInfoDataM();
	}
	int ItemMaxlifeCycle = applicationGroup.getMaxLifeCycleFromApplication();
	HashMap<String, ArrayList<String>> productMap = new HashMap<String,ArrayList<String>>();
	//ArrayList<String> products = applicationGroup.getProducts(ItemMaxlifeCycle);
	ArrayList<String> products = applicationGroup.filterProductPersonal(personalInfo.getPersonalId(), APPLICATION_LEVEL);
	logger.debug("products : "+products);	   	   	
	Collections.sort(products);
	productMap.put(personalInfo.getPersonalType()+"_"+personalInfo.getPersonalId(), products);
	if(PERSONAL_TYPE_APPLICANT.equals(personalInfo.getPersonalType())) {
		ArrayList<PersonalInfoDataM> personalInfoSupList = applicationGroup.getPersonalInfos(PERSONAL_TYPE_SUPPLEMENTARY);
		if(!Util.empty(personalInfoSupList)) {
			for(PersonalInfoDataM personalInfoSup : personalInfoSupList) {
				String personalId = personalInfoSup.getPersonalId();
				ArrayList<String> supProducts = applicationGroup.filterProductPersonal(personalId, APPLICATION_LEVEL);
				Collections.sort(supProducts);
				productMap.put(personalInfoSup.getPersonalType()+"_"+personalId, supProducts);
			}
		}
	}
	
	FormUtil formUtil = new FormUtil(subformId,request);
	FormEffects formEffect  = new FormEffects(subformId,request);
	String actionJS ="onclick='LinkText_NCB_REPORT_BTNActionJS()'";
	// get productFactor in new 
	ArrayList<ProductFactor> productFactors = applicationGroup.getProductFactor();
	String sourceOfIncome = personalInfo.getSorceOfIncome();
	if(APPLICATION_TYPE_ADD.equals(applicationGroup.getApplicationType())){
		sourceOfIncome = INCOME_SOURCE_OLD_INCOME_CARDLINK;
	}
	
%>
<div class="col-sm12  text_right text_link_color"><%=HtmlUtil.displaylinkText("NCB_REPORT_BTN", "NCB_REPORT_BTN", LabelUtil.getText(request, "NCB_REPORT_BTN"),actionJS, null, formEffect)%></div>
<div class="panel panel-default">
	<div class="panel-body">
		<div class="row form-horizontal">
			<div class="col-sm-5">
				<div class="form-group">
					<%=HtmlUtil.getFieldLabel(request, "TYPE_OF_FIN", "col-sm-5 col-md-6 control-label") %>
					<div class="col-sm-5 col-md-4"><%=CacheControl.getName(SystemConstant.getConstant("FIELD_ID_TYPE_OF_FIN"), personalInfo.getTypeOfFin())%></div>
				</div>
			</div>
			<div class="col-sm-7">
				<div class="form-group">
					<%=HtmlUtil.getFieldLabel(request, "REFER_7_CRITERIA", "col-sm-5 col-md-6 control-label") %>
					<div class="col-sm-8 col-md-6">
						<%
							if(!Util.empty(criteriaList)){
								for(String criteria : criteriaList){
						%>
<%-- 									<div class="col-sm-12">-<%=CacheControl.getName(SystemConstant.getConstant("FIELD_ID_REFER_7_CRITERIA"), criteria) %></div> --%>
										<div class="col-sm-12">-<%=FormatUtil.replece(criteria)%></div>
						<%  	} 
							}
						%>
					</div>
				</div>
			</div>
			<div class="col-sm-5">
				<div class="form-group">
					<%=HtmlUtil.getFieldLabel(request, "VERIFIED_INCOME", "col-sm-5 col-md-6 control-label") %>
					<div class="col-sm-5 col-md-4"><%=FormatUtil.display(personalInfo.getTotVerifiedIncome(),Format.CURRENCY_FORMAT)%></div>
				</div>
			</div>
			<!--Income Source-->
			<div class="col-sm-7">
				<div class="form-group">
					<%=HtmlUtil.getFieldLabel(request, "INCOME_SOURCE", "col-sm-5 col-md-6 control-label") %>
						<div class="col-sm-8 col-md-6">
							<div class="col-sm-12" id="INCOME_SOURCE_VALUE"><%=FormatUtil.display(CacheControl.getName(FIELD_ID_SORCE_OF_INCOME, sourceOfIncome))%></div>
						</div>
				</div>
			</div>
			<!--end Income Source-->
			<!-- Product Factor-->
			<div class="col-sm-5 ">
				<div class="form-group">
					<%=HtmlUtil.getFieldLabel(request, "PRODUCT_FACTOR", "col-sm-5 col-md-6 control-label") %>
						<div class="col-sm-5 col-md-4">
						
							<% if(productFactors.isEmpty()) { %>
								 -
							<% } else { int lastObject = productFactors.size(); int number = 0; boolean display = false;%>
								<% for(ProductFactor product : productFactors) {  %>
								<%    number++; lastObject += product.loans.size(); %>
								<%if(product.loans.size()!=0) %>
									<% for(LoanDataM loan : product.loans ) { number++; display=true;  %>
										<%=FormatUtil.display(loan.getProductFactor()) %> 
										
											<!-- check last Object --> 
										<%if(number == lastObject) { %>
									
										<% } else {%>
										,
										<% } %>
									<% } %>
									
								<% } %>
								<% if(!display){ %>
										-
								<% } %>
							<% } %>
						</div>
				</div>
			</div>
			<!--end Product Factor -->
			<!--Debt Amount-->
			<div class="col-sm-7">
				<div class="form-group">
					<%=HtmlUtil.getFieldLabel(request, "DEBT_AMOUNT", "col-sm-5 col-md-6 control-label") %>
						<div class="col-sm-8 col-md-6">
							<div class="col-sm-12" id="DEBT_AMOUNT_VALUE"><%=FormatUtil.display(appLoan.getDebtAmount(),Format.CURRENCY_FORMAT)%></div>
						</div>
				</div>
			</div>
			<!--end Debt Amount-->
			<!-- Fico Score -->
			<div class="col-sm-5">
				<div class="form-group">
				<%=HtmlUtil.getFieldLabel(request, "FICO_SCORE", "col-sm-5 col-md-6 control-label") %>
					<div class="col-sm-5 col-md-4">
						<%
							if(!Util.empty(ficoScoreScoreList)){
								for(NcbInfoDataM ficoScoreObject : ficoScoreScoreList){
						%>
									<%=FormatUtil.display(ficoScoreObject.getFicoScore())%>
						<%  		break;
								}
							
						%>
						<%}else{ %>
								-
						<%	} %>
					</div>
				</div>
			</div>
			<!--end Fico Score -->
			<!-- %Debt Burden-->
			<div class="col-sm-7">
				<div class="form-group">
					<%=HtmlUtil.getFieldLabel(request, "DEBT_BURDEN", "col-sm-5 col-md-6 control-label") %>
						<div class="col-sm-8 col-md-6">
							<div class="col-sm-12" id="DEBT_BURDEN_VALUE"><%=FormatUtil.display(appLoan.getDebtBurden()) %></div>
						</div>
				</div>
			</div>
			<!--end %Debt Burden-->
			<!--B Score-->
			<div class="col-sm-5">
				<div class="form-group">
					<%=HtmlUtil.getFieldLabel(request, "B_SCORE", "col-sm-5 col-md-6 control-label") %>
						<div class="col-sm-5 col-md-4">
							<% 	
							// how to display B_SCORE
								ArrayList<BScoreDataM> bScoreList = personalInfo.getBscores(); 
								if (bScoreList == null ){
									bScoreList = new ArrayList<BScoreDataM>();
								}
								HashSet<String> hset = new HashSet<String>();
								ArrayList<String> displayValueList = new ArrayList<String>();
									// set group
									for (BScoreDataM objectGroup : bScoreList) {
										hset.add(objectGroup.getProductType());
									}
									//list group
									for (String temp: hset){
										String valueHset ="";
										valueHset = temp;
										int memberCount = 0;
										valueHset += "   ";
										// con month 3
										String month1Var = "-";
										String month2Var = "-";
										String month3Var = "-";
										Date firstPeriodMonth1 = new Date();
										Date lastPeriodMonth1 = new Date();
										Date firstPeriodMonth2 = new Date();
										Date lastPeriodMonth2 = new Date();
										Date firstPeriodMonth3 = new Date();
										Date lastPeriodMonth3 = new Date();
										Date finalDate = new Date();
										ArrayList<BScoreDataM> member = new ArrayList<BScoreDataM>();
										// set member in group 
										for(BScoreDataM objectGroup : bScoreList){
											if(temp.contains(objectGroup.getProductType())){
												// desc POSTDATE
												member.add(objectGroup);
											}										
										}
										// sort PostDate
										 Collections.sort(member, new Comparator<BScoreDataM>() {
									         public int compare(BScoreDataM p1, BScoreDataM p2) {
									            return   p2.getPostDate().compareTo(p1.getPostDate()); // DESC
									        }
									    });
										for(BScoreDataM valMember :member){
											memberCount++;
											// first create var final Date
											if(memberCount == 1){
												//period date of 3
												finalDate = valMember.getPostDate();
												Calendar cal3 = Calendar.getInstance();
												cal3.setTime(finalDate);
												cal3.set(Calendar.DATE, 1);
												firstPeriodMonth3 = cal3.getTime();
												cal3.set(Calendar.DATE, cal3.getActualMaximum(Calendar.DATE));
												lastPeriodMonth3 = cal3.getTime();
												System.out.println("firstTime 3 >>"+firstPeriodMonth3);
												System.out.println("lastTime 3 >>"+lastPeriodMonth3);
												//period date of 2
												Calendar cal2 = Calendar.getInstance();
												cal2.setTime(finalDate);
												cal2.add(Calendar.MONTH, -1);
												cal2.set(Calendar.DATE, 1);
												firstPeriodMonth2 = cal2.getTime();
												cal2.set(Calendar.DATE, cal2.getActualMaximum(Calendar.DATE));
												lastPeriodMonth2 = cal2.getTime();
												System.out.println("firstTime 2 >>"+firstPeriodMonth2);
												System.out.println("lastTime 2 >>"+lastPeriodMonth2);
												//period date of 1
												Calendar cal1 = Calendar.getInstance();
												cal1.setTime(finalDate);
												cal1.add(Calendar.MONTH, -2);
												cal1.set(Calendar.DATE, 1);
												firstPeriodMonth1 = cal1.getTime();
												cal1.set(Calendar.DATE, cal1.getActualMaximum(Calendar.DATE));
												lastPeriodMonth1 = cal1.getTime();
												System.out.println("firstTime 1 >>"+firstPeriodMonth1);
												System.out.println("lastTime 1 >>"+lastPeriodMonth1);
											}
											Date tmpCheckDate = valMember.getPostDate();
											Calendar cal = Calendar.getInstance();
											cal.setTime(tmpCheckDate);
											Date check = cal.getTime();
											System.out.println("check >>"+check);
											// check 3 month
											if(firstPeriodMonth1.before(check)  && lastPeriodMonth1.after(check) || firstPeriodMonth1.equals(check)|| lastPeriodMonth1.equals(check)){
												month1Var = valMember.getRiskGrade();
												System.out.println("month1Var  >>"+month1Var);
											}
											else if(firstPeriodMonth2.before(check) && lastPeriodMonth2.after(check) || firstPeriodMonth2.equals(check)|| lastPeriodMonth2.equals(check) ){
												month2Var = valMember.getRiskGrade();
												System.out.println("month2Var  >>"+month2Var);
											}
											else if(firstPeriodMonth3.before(check)  && lastPeriodMonth3.after(check) || firstPeriodMonth3.equals(check)|| lastPeriodMonth3.equals(check)  ){
												month3Var = valMember.getRiskGrade();
												System.out.println("month3Var  >>"+month3Var);
											}
										
										}
									valueHset += " : "+ month1Var +"  , "+ month2Var +"  , "+ month3Var;
										// add in arrayList for display
										displayValueList.add(valueHset);
									}
						%>
							<% if(bScoreList.isEmpty()){ %>
								- 
							<% } else {%>
								<%for(String display : displayValueList){ %>
									<%=FormatUtil.display(display)%><br>
								<%} %>
							<% } %>
						</div>
				</div>
			</div>
			<!--end B Score-->
			<!--Debt Rec-->
			<div class="col-sm-7">
				<div class="form-group">
					<%=HtmlUtil.getFieldLabel(request, "DEBT_REC", "col-sm-5 col-md-6 control-label") %>
						<div class="col-sm-8 col-md-6">
							<div class="col-sm-12" id="DEBT_REC_VALUE"><%=FormatUtil.display(appLoan.getDebtBurdenCreditLimit())%></div>
						</div>
				</div>
			</div>
			<!--end Debt Rec -->
			
			
		</div>
	</div>
	<table class="table table-striped table-hover">
	<%if(!Util.empty(productMap)){ 
		ArrayList<String>  sortedKeys  = new ArrayList<String>(productMap.keySet());
		Collections.sort(sortedKeys);
		for(String productKey : sortedKeys) {
		logger.debug("productKey : "+productKey);
		int count=0;
		String personalId = productKey.split("_")[1];
		PersonalInfoDataM prodPersonal = applicationGroup.getPersonalById(personalId);
		String personalTypeDesc = ListBoxControl.getName(FIELD_ID_PERSONAL_TYPE, prodPersonal.getPersonalType());
		ArrayList<String> productList = productMap.get(productKey);
		for(String product : productList){
			logger.debug("product : "+product);
			ArrayList<String> appRecordIdList = applicationGroup.filterPersonalApplicationLifeCycleProduct(prodPersonal.getPersonalId(),APPLICATION_LEVEL,product);
			ArrayList<String> applicationTypes = applicationGroup.getApplicationApplyTypes(appRecordIdList);
			String   applicationTypeDesc ="";
			if(!Util.empty(applicationTypes)){
				String COMMA="";
			 	for(String applicationType:applicationTypes){
				   applicationTypeDesc+=COMMA+applicationType;
				   if(!KPLUtil.isKPL(applicationGroup))
				   {applicationTypeDesc+=" Card";}
				   COMMA=", ";
				}
			}

			HashMap<String,ORPolicyRulesDataM> orPolicyRuleMap = new HashMap<String,ORPolicyRulesDataM>();
		 	for(String filterAppRecordId :appRecordIdList){
				ArrayList<ORPolicyRulesDataM> orPolicyRuleListForApp = applicationGroup.getORPolicyRules(filterAppRecordId, SWITCH_OR_POLICY_CODE);
		 		if(!Util.empty(orPolicyRuleListForApp)) {
		 			for(ORPolicyRulesDataM ruleM : orPolicyRuleListForApp) {
		 				ruleM.setAppRecordId(filterAppRecordId);
		 				orPolicyRuleMap.put(ruleM.getPolicyCode(), ruleM);
		 			}
		 		}
		 	}
		 	ArrayList<ORPolicyRulesDataM> orPolicyRuleList = new ArrayList<ORPolicyRulesDataM>(orPolicyRuleMap.values());
		 	Collections.sort(orPolicyRuleList, new ApplicationGroupDataM());
			String productDesc = ListBoxControl.getName(FIELD_ID_PRODUCT_TYPE, product);
			if(PRODUCT_K_PERSONAL_LOAN.equals(product)){productDesc=LabelUtil.getText(request, "KPL");}
			logger.debug("productDesc : "+productDesc);	   	   	
			%>
			<tr><td style="font-weight: bold;" colspan="6"><%=personalTypeDesc %> &nbsp;-&nbsp;<%=prodPersonal.getThName()%></td></tr>
			<tr>
				<td style="font-weight: bold;" colspan="3" ><%=productDesc%>&nbsp;&nbsp;
				<%=HtmlUtil.getFieldLabel(request, "APPLICATION_TYPE")%>&nbsp;<%=FormatUtil.display(applicationTypeDesc)%></td>
		 		<%for(int i = ItemMaxlifeCycle; i>0 && i<=MAX_LIFE_CYCLE; i--){ 
		 			logger.debug("apply_round=="+i);
		 	 		int apply_round= i;	 	 		 	 		
		 	 		String appRoundTitle ="";
		 	 		if(count==0){
		 	 			appRoundTitle = 1==apply_round?LabelUtil.getText(request,"FIRST_APPLY"): LabelUtil.getText(request,"VETO_ROUND")+FormatUtil.display(String.valueOf(apply_round-1));
		 	 		} 
		 	 	%>	
		 	 	<%if(i == ItemMaxlifeCycle){%> 		
		 			<td></td>
		 		<%}%>
		 		<td style="font-weight: bold;" class="text_center"><%=appRoundTitle %></td>
		 		<td></td>
		 		<% } %>	 		 		
			</tr>
		<% if(!Util.empty(orPolicyRuleList)){
			for(ORPolicyRulesDataM headOrPolicyRule : orPolicyRuleList) {
				String orPolicyCode = headOrPolicyRule.getPolicyCode();
		%>
			<tr class="tabletheme_header">
				<td class="text-left"><%=FormatUtil.display(orPolicyCode) %></td>
				<td><%=FormatUtil.display(CacheControl.getName(SystemConstant.getConstant("FIELD_ID_OR_POLICY"),headOrPolicyRule.getPolicyCode()))%></td>
				<td><%=FormatUtil.display(headOrPolicyRule.getMinApprovalAuth()) %></td>
				<%for(int i=ItemMaxlifeCycle ;i>0 && i<=MAX_LIFE_CYCLE;i--){
					ORPolicyRulesDataM orPolicy =applicationGroup.getORPolicyRulesLifcycle(orPolicyCode, i, headOrPolicyRule.getAppRecordId());
					if(Util.empty(orPolicy)){
						orPolicy = new ORPolicyRulesDataM();
					}
					String verOrPolicyResult =FormatUtil.display(orPolicy.getVerifiedResult());
					String scrVerResult = "";
					if(!Util.empty(verOrPolicyResult)){
						scrVerResult = CacheControl.getName(FIELD_ID_POLICY_VER_RESULT,"MAPPING1",verOrPolicyResult, "SYSTEM_ID1");
					}
					String  imgVerResult = Util.empty(scrVerResult)?"-":"<img src='"+scrVerResult+"'>";
					 					
					String displayResultCode = CacheControl.getName(SystemConstant.getConstant("FIELD_ID_POLICY_RESULT"), orPolicy.getResult());
					
					String suffixPolicy =String.valueOf(ItemMaxlifeCycle)+"_"+orPolicy.getOrPolicyRulesId();
					if(i==ItemMaxlifeCycle && (POLICY_VER_RESULT_REFER.equals(verOrPolicyResult)||POLICY_VER_RESULT_FAIL.equals(verOrPolicyResult))){
						displayResultCode = HtmlUtil.dropdown("OR_POLICY", suffixPolicy, "OR_RESULT_FAIL_REFER", "", CONFIG_ID_OR_RESULT_FAIL_REFER, orPolicy.getResult(), "", FIELD_ID_POLICY_RESULT, "","", "", formUtil);
					} %> 
				<%if(i == ItemMaxlifeCycle){%> 		
		 			<td class="text_center"></td>
		 		<%}%>
		 		<td class="text_center"><%=listSwitchPolicyCode.contains(orPolicyCode)?"":imgVerResult%></td>
				<td><%=listSwitchPolicyCode.contains(orPolicyCode)?displayResultCode:"" %></td>
		 		<%} %>			
			</tr>
							
			<% 	
			ArrayList<ORPolicyRulesDetailDataM> oRPolicyRuleDetails= headOrPolicyRule.getOrPolicyRulesDetails();
				Collections.sort(oRPolicyRuleDetails,new ApplicationGroupDataM());
				if(!Util.empty(oRPolicyRuleDetails)){
					if(listSwitchPolicyCode.contains(headOrPolicyRule.getPolicyCode())){// or 22
						for(ORPolicyRulesDetailDataM switchORPolicyRuleDetail : oRPolicyRuleDetails){
							String policyCode = switchORPolicyRuleDetail.getGuidelineCode();
							if(!Util.empty(CacheControl.getName(FIELD_ID_POLICY_RULE,policyCode))){ %>
				<tr>
					<td></td>
					<%	String policyRuleDescription = "";
						if(SystemConstant.lookup("POLICY_CODE_DISPLAY_REASON",policyCode)){
							PolicyRulesDataM policyRuleApplication = applicationGroup.getPolicyRulesApplication(policyCode,orPolicyCode,headOrPolicyRule.getAppRecordId());
							if(null != policyRuleApplication){
								String reason = policyRuleApplication.getReason();
								if(!Util.empty(reason)){
									policyRuleDescription = CacheControl.getName(FIELD_ID_REJECTED,reason);
								}
							}
						}else{
							policyRuleDescription = CacheControl.getName(FIELD_ID_POLICY_RULE,policyCode);
						}
						
					 %>
					<td><%=FormatUtil.display(policyCode)%>&nbsp;&nbsp;<%=policyRuleDescription%></td>
					<td></td>
					<%for(int i=ItemMaxlifeCycle ;i>0 && i<=MAX_LIFE_CYCLE;i--){	
						PolicyRulesDataM policyRule = applicationGroup.getPolicyRulesLifcycle(policyCode,orPolicyCode, i, headOrPolicyRule.getAppRecordId());
						if(Util.empty(policyRule)){
							policyRule = new PolicyRulesDataM();
						}
						String policyVerResult = policyRule.getVerifiedResult();
						String scrPolicyVerResult = CacheControl.getName(FIELD_ID_POLICY_VER_RESULT,"MAPPING1",policyVerResult, "SYSTEM_ID2");
						String imgPolicyVerResult = Util.empty(scrPolicyVerResult)?"-":"<img src='"+scrPolicyVerResult+"'>";
							
						String policyResult = policyRule.getResult();
						String displayPolicyResultCode = CacheControl.getName(FIELD_ID_POLICY_RESULT, policyResult);
											 
						String suffixPolicy =String.valueOf(ItemMaxlifeCycle)+"_"+policyRule.getPolicyRulesId();
						if(!Util.empty(policyRule.getPolicyRulesId())){
							if(i==ItemMaxlifeCycle && (POLICY_VER_RESULT_REFER.equals(policyVerResult) || (POLICY_VER_RESULT_FAIL.equals(policyVerResult)))){
								displayPolicyResultCode = HtmlUtil.dropdown("POLICY_RESULT", suffixPolicy, "OR_RESULT_FAIL","",CONFIG_ID_OR_RESULT_FAIL, policyRule.getResult(), "", FIELD_ID_POLICY_RESULT, "","", "", formUtil);
							}else if(i==ItemMaxlifeCycle && (Util.empty(policyVerResult))){
								displayPolicyResultCode = HtmlUtil.dropdown("POLICY_RESULT", suffixPolicy, "OR_RESULT_BLANK","",CONFIG_ID_OR_RESULT_BLANK, policyRule.getResult(), "", FIELD_ID_POLICY_RESULT, "", "", "", formUtil);
							}
						}

					 %>
					<%if(i == ItemMaxlifeCycle){%>
						<td class="text_center"></td>
					<%}%>				
					<td class="text_center"><%=SystemConstant.lookup("POLICY_CODE_DISPLAY_REASON",policyCode)?"-":imgPolicyVerResult%></td>				
 					<td><%=listSwitchOrPolicyCode.contains(policyCode)?(SystemConstant.lookup("POLICY_CODE_DISPLAY_REASON",policyCode)?"":"-"):displayPolicyResultCode%></td>
	 				<%} %>
				</tr>
						<%} %>
					<%}
				 }else{ 
				 	int runningNumber = 0;%>
					<%for(ORPolicyRulesDetailDataM orPolicyRuleDetail : oRPolicyRuleDetails){ 
						String guidelineCode = orPolicyRuleDetail.getGuidelineCode();
						if(!Util.empty(CacheControl.getName(FIELD_ID_OR_GUIDELINE,guidelineCode))){
						%>
					<tr>					
						<td></td>
						<td colspan='3'><%=FormatUtil.display(orPolicyCode)+"."+FormatUtil.getString(++runningNumber)%>&nbsp;&nbsp;<%=CacheControl.getName(FIELD_ID_OR_GUIDELINE,guidelineCode)%>
<!-- 						</td> -->
<!-- 						<td></td> -->
						<%for(int i=ItemMaxlifeCycle ;i>0 && i<=MAX_LIFE_CYCLE;i--){				
							String applicationRecordId = headOrPolicyRule.getAppRecordId();
							ORPolicyRulesDetailDataM policyRuleDetail  = applicationGroup.getORPolicyRulesDetailLifcycle(orPolicyCode, guidelineCode, i, applicationRecordId);
							if(Util.empty(policyRuleDetail)){
								policyRuleDetail = new ORPolicyRulesDetailDataM();
							}
							String guidelineVerResult = policyRuleDetail.getVerifiedResult();	
										
							HashMap<String,String> hGuidelineData = ApplicationUtil.getGuidelineNameValues(policyRuleDetail.getGuidelines(), request);
							String guidelineData ="";
							if(!Util.empty(hGuidelineData)){
							 ArrayList<String> guideLineKeys = new ArrayList<String>(hGuidelineData.keySet());
							 Collections.sort(guideLineKeys,Collections.reverseOrder());
							 String COMMA="";
							 for(String nameDesc : guideLineKeys){							 
							   String guideLineVal = hGuidelineData.get(nameDesc);
							   guidelineData +=COMMA+nameDesc;
						   	   if(!Util.empty(guideLineVal)){
						   			guidelineData+=" :"+guideLineVal;
						   		}
							   	COMMA=", ";
							  }							  
							}
														
							String scrGuidelineVerResult = CacheControl.getName(FIELD_ID_OR_GUIDELINE_VER_RESULT,guidelineVerResult, "SYSTEM_ID2");
							String imgVerResult = Util.empty(scrGuidelineVerResult)?"-":"<img src='"+scrGuidelineVerResult+"'>";
														
							String guidelineResult = policyRuleDetail.getResult();
							String displayGuidelineResultCode = CacheControl.getName(FIELD_ID_POLICY_RESULT, guidelineResult);
							String suffixOrDetail = String.valueOf(ItemMaxlifeCycle)+"_"+policyRuleDetail.getOrPolicyRulesDetailId();
							if(!Util.empty(policyRuleDetail.getOrPolicyRulesDetailId())){
								if(i==ItemMaxlifeCycle && (GUIDE_LINE_VER_RESULT_FAIL.equals(guidelineVerResult))){
						 			displayGuidelineResultCode = HtmlUtil.dropdown("GUIDE_LINE_RESULT", suffixOrDetail, "GUIDE_LINE_RESULT_FAIL", "", CONFIG_ID_OR_RESULT_FAIL,policyRuleDetail.getResult(), "", FIELD_ID_POLICY_RESULT, "", "", "", formUtil);
						 	}else if(i==ItemMaxlifeCycle && (GUIDE_LINE_VER_RESULT_BLANK.equals(guidelineVerResult))){
						 			displayGuidelineResultCode =HtmlUtil.dropdown("GUIDE_LINE_RESULT", suffixOrDetail, "GUIDE_LINE_RESULT_BLANK", "", CONFIG_ID_OR_RESULT_BLANK,policyRuleDetail.getResult(), "", FIELD_ID_POLICY_RESULT, "", "", "", formUtil);
						 		}
							}
						 %>
						<%if(i == ItemMaxlifeCycle){%>
<!-- 							<td class="text_center">style="" -->
							<br><div class="guidelineDataHead">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=FormatUtil.displayText(guidelineData) %></div></td>
						<%}%>				
						<td class="text_center"><%=imgVerResult %></td>				
	 					<td><%=displayGuidelineResultCode%></td>
	 					<%} %>					
					</tr>
						<%} %>
					<%} %>
				
				<%} %>
								
			<%} %>
 		
		<%}
		} %>
												
	<%count++;} 
	}
	}else{%>
		<tr>
	 		<td align="center"><%=LabelUtil.getText(request,"NO_RECORD_FOUND")%></td>
	 	</tr>
	<%}%>
	</table>
	<div class="panel-body">
		<div class="row form-horizontal">
			<div class="col-sm-12">
				<div class="form-group">
					<%=HtmlUtil.getFieldLabel(request, "VETO_REMARK", "col-sm-2 col-md-2 control-label") %>
					<%=HtmlUtil.textarea("VETO_REMARK", "VETO_REMARK", "VETO_REMARK", applicationGroup.getVetoRemark(), "5", "100", "1000", "", "col-sm-9 col-md-8", formUtil)%>
				</div>
			</div>
			<div class="clearfix"></div>
			<div class="col-sm-12">
				<div class="form-group" style="float: right;padding-right: 10px;">
				<%if(CA_DLA_FORM.contains(ORIGForm.getFormId())){%>
					 <%=HtmlUtil.button("MORE_INFO_BTN", "MORE_INFO_BTN", HtmlUtil.EDIT, "", "", request)%>
				<%} %>
				</div>
			</div>
		</div>
	</div>			 
</div>