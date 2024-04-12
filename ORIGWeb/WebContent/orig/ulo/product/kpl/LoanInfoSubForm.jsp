<%@page import="java.math.BigDecimal"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.CashTransferDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.LoanDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.orig.ulo.app.view.util.kpl.KPLUtil"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>


<jsp:useBean id="ORIGUser" scope="session"class="com.eaf.orig.profile.model.UserDetailM" />
<jsp:useBean id="ORIGForm" scope="session"class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler" />

<script type="text/javascript"
	src="orig/ulo/product/js/LoanFinanceSubForm.js"></script>
<%
	String subformId = "KPL_LOAN_INFO_SUBFORM";
	Logger logger = Logger.getLogger(this.getClass());
	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");	
	String PRODUCT_K_PERSONAL_LOAN = SystemConstant.getConstant("PRODUCT_K_PERSONAL_LOAN");
	String FIELD_ID_TERM = SystemConstant.getConstant("FIELD_ID_TERM");
	String ROLE_DE1_1 = SystemConstant.getConstant("ROLE_DE1_1");
	String ROLE_DE1_2 = SystemConstant.getConstant("ROLE_DE1_2");
	logger.debug("FIELD_ID_TERM >>> "+FIELD_ID_TERM);
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	ApplicationDataM applicationM = applicationGroup.filterApplicationProductLifeCycle(PRODUCT_K_PERSONAL_LOAN);
	String userRole = (String) ORIGUser.getRoles().elementAt(0);
	PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
	if(null == personalInfo){
		personalInfo = new PersonalInfoDataM();
	}
	int PERSONAL_SEQ = personalInfo.getSeq();	
	
	//KPL Addtional
	boolean isKPLTopUp = KPLUtil.isKPL_TOPUP(applicationGroup);
	LoanDataM loan = KPLUtil.getKPLLoanDataM(applicationGroup);
	BigDecimal reqLoanAmt = loan.getRequestLoanAmt();
	String reqTerm = String.valueOf(loan.getRequestTerm());
	
	String displayMode = HtmlUtil.EDIT;
	String productElementId = FormatUtil.getProductElementId(personalInfo,PRODUCT_K_PERSONAL_LOAN);	
	FormUtil formUtil = new FormUtil("PRODUCT_FORM_KPL",subformId,request);
%>
<div class="panel panel-default">
	 <div class="panel-body">
		<div class="row form-horizontal">

			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request, subformId,"AMOUNT_LOAN","AMOUNT_LOAN", "col-sm-4 col-md-5 control-label")%>
					<%=HtmlUtil.currencyBox("AMOUNT_LOAN", "", "AMOUNT_LOAN_"+productElementId, "AMOUNT_LOAN", 
					reqLoanAmt, "", false, "15", "", "col-sm-8 col-md-7",loan, formUtil) %>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request,subformId,"TERM","TERM","col-sm-4 col-md-5 control-label")%>
					<%=HtmlUtil.dropdown("TERM", "", "TERM_"+productElementId, "TERM", "", 
					reqTerm, "",FIELD_ID_TERM,"", "", "col-sm-8 col-md-7",loan, formUtil)%>
				</div>
			</div>
			
		</div>
	</div>
</div>

<% if(isKPLTopUp){%>
<script>
		//Hide element
		targetDisplayHtml('AMOUNT_LOAN',MODE_VIEW,'AMOUNT_LOAN','Y');
		targetDisplayHtml('TERM',MODE_VIEW,'TERM','Y');
</script>
<% } %>
<% if(ROLE_DE1_1.equals(userRole) || ROLE_DE1_2.equals(userRole)){ %>
<script>
$("[name='AMOUNT_LOAN']").on('change', function() {
		if($(this).val() == "0" || $(this).val() == "0.00")
		{
			var selectObj = $("[name='TERM']");
			if(!(selectObj[0].selectize.isLocked))
			{selectObj[0].selectize.setValue("60");}
		}
    });
$("[name='AMOUNT_LOAN']").change();
</script>
<% } %>