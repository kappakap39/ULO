<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.app.view.util.kpl.KPLUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.properties.FormControl"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.PaymentMethodDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<script type="text/javascript" src="orig/ulo/product/js/LoanFinanceSubForm.js?v=1"></script>
<%
	String subformId = "KPL_INSTALLMENT_SUBFORM";
	Logger logger = Logger.getLogger(this.getClass());
	String roleId = ORIGForm.getRoleId();
	String ROLE_DV = SystemConstant.getConstant("ROLE_DV");	
	String ROLE_DE2 = SystemConstant.getConstant("ROLE_DE2");	
	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");	
	String PRODUCT_K_PERSONAL_LOAN = SystemConstant.getConstant("PRODUCT_K_PERSONAL_LOAN");
	ApplicationGroupDataM applicationGroup= (ApplicationGroupDataM)FormControl.getObjectForm(request);	
	PersonalInfoDataM personalInfo = ORIGForm.getObjectForm().getPersonalInfo(PERSONAL_TYPE);
	if(null == personalInfo){
		personalInfo = new PersonalInfoDataM();
	}
	String personalId = personalInfo.getPersonalId();
	PaymentMethodDataM paymentMethod = applicationGroup.getPaymentMethodLifeCycleByPersonalId(personalId,PRODUCT_K_PERSONAL_LOAN);
	if(Util.empty(paymentMethod)){
		paymentMethod = new PaymentMethodDataM();
	}
	
	ApplicationDataM kplApplication = KPLUtil.getKPLApplication(applicationGroup);
	
	int PERSONAL_SEQ = personalInfo.getSeq();
	String displayMode = HtmlUtil.EDIT;
	String productElementId = FormatUtil.getProductElementId(personalInfo,PRODUCT_K_PERSONAL_LOAN);	
	FormUtil formUtil = new FormUtil("PRODUCT_FORM_KPL",subformId,request);
	
	//KPL Additional
	String payMethod = LabelUtil.getText(request, "PAY_METHOD");
	String receiveMethod = LabelUtil.getText(request, "RECEIVE_METHOD");
	Boolean isKPLTopup = KPLUtil.isKPL_TOPUP(applicationGroup);
	Boolean isKPLMarket = KPLUtil.isKPL_MARKET(applicationGroup);
	Boolean foundPayroll = KPLUtil.isFoundPayroll(applicationGroup);
	Boolean isDSA = KPLUtil.isDSA(applicationGroup);
	String accountNo = (foundPayroll && Util.empty(paymentMethod.getAccountNo())) ? personalInfo.getPayrollAccountNo() : FormatUtil.removeDash(paymentMethod.getAccountNo());
	
if(!isKPLMarket)
{
%>
<div class="panel panel-default">
	 <div class="panel-body">
		<div class="row form-horizontal">
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request,subformId,"ACCOUNT_NO","ACCOUNT_NO","col-sm-4 col-md-5 control-label")%>
					<%=HtmlUtil.textBoxAccountNo("ACCOUNT_NO", PRODUCT_K_PERSONAL_LOAN, "ACCOUNT_NO_"+productElementId, "ACCOUNT_NO",
						accountNo, "", "", "col-sm-8 col-md-7",paymentMethod, formUtil) %>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.hidden("COMPLETE_DATA_"+PRODUCT_K_PERSONAL_LOAN, paymentMethod.getCompleteData()) %>
					<%=HtmlUtil.getSubFormLabel(request,subformId,"ACC_NAME_"+PRODUCT_K_PERSONAL_LOAN,"ACC_NAME","col-sm-4 col-md-5 control-label")%>
					<div id="ACCOUNT_NAME_<%=PRODUCT_K_PERSONAL_LOAN %>" class="col-sm-8 col-md-7"><%=FormatUtil.display(paymentMethod.getAccountName())%></div>
				</div>
			</div>
			<% if(isDSA) { %>
			<div class="clearfix"></div>
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.checkBoxInline("SAVING_PLUS", "" , "SAVING_PLUS_"+productElementId, "SAVING_PLUS", 
					kplApplication.getSavingPlusFlag(), "", MConstant.FLAG.ACTIVE, "", LabelUtil.getText(request, "SAVING_PLUS"), "col-sm-8 col-md-7", formUtil) %>
				</div>
			</div>
			<% } %>
		</div>
	</div>
</div>
<% } else { %>
<script>
 $('#SECTION_KPL_INSTALLMENT_SUBFORM').find("div").eq(0).text(<%=receiveMethod%>);
</script>

<div class="panel panel-default">
	 <div class="panel-body">
		<div class="row form-horizontal">
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request,subformId,"ACCOUNT_NO","ACCOUNT_NO","col-sm-4 col-md-5 control-label")%>
					<%=HtmlUtil.textBoxAccountNo("ACCOUNT_NO", PRODUCT_K_PERSONAL_LOAN+"_MARKET_RECEIVE", "ACCOUNT_NO_"+productElementId, "ACCOUNT_NO",
						FormatUtil.removeDash(paymentMethod.getAccountNo()), "", "", "col-sm-8 col-md-7",paymentMethod, formUtil) %>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.hidden("COMPLETE_DATA_"+PRODUCT_K_PERSONAL_LOAN, paymentMethod.getCompleteData()) %>
					<%=HtmlUtil.getSubFormLabel(request,subformId,"ACC_NAME_"+PRODUCT_K_PERSONAL_LOAN+"_MARKET_RECEIVE","ACC_NAME","col-sm-4 col-md-5 control-label")%>
					<div id="ACCOUNT_NAME_<%=PRODUCT_K_PERSONAL_LOAN %>_MARKET_RECEIVE" class="col-sm-8 col-md-7"><%=FormatUtil.display(paymentMethod.getAccountName())%></div>
				</div>
			</div>
		</div>
	</div>
</div>

<div class="subtitlecontent"><%=payMethod%></div>
<div class="clearfix"></div>
<div class="panel panel-default">
	 <div class="panel-body">
		<div class="row form-horizontal">
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request,subformId,"ACCOUNT_NO","ACCOUNT_NO","col-sm-4 col-md-5 control-label")%>
					<%=HtmlUtil.textBoxAccountNo("ACCOUNT_NO", PRODUCT_K_PERSONAL_LOAN+ "_MARKET_PAY", "ACCOUNT_NO_"+productElementId, "ACCOUNT_NO",
						FormatUtil.removeDash(paymentMethod.getAccountNo()), "", "", "col-sm-8 col-md-7",paymentMethod, formUtil) %>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.hidden("COMPLETE_DATA_"+PRODUCT_K_PERSONAL_LOAN, paymentMethod.getCompleteData()) %>
					<%=HtmlUtil.getSubFormLabel(request,subformId,"ACC_NAME_"+PRODUCT_K_PERSONAL_LOAN+ "_MARKET_PAY","ACC_NAME","col-sm-4 col-md-5 control-label")%>
					<div id="ACCOUNT_NAME_<%=PRODUCT_K_PERSONAL_LOAN %>" class="col-sm-8 col-md-7"><%=FormatUtil.display(paymentMethod.getAccountName())%></div>
				</div>
			</div>
		</div>
	</div>
</div>

<div class="subtitlecontent"><%=HtmlUtil.getFieldLabel(request, "COOP_MARKET", "col-sm-4 control-label")%></div>
<div class="clearfix"></div>
<div class="panel panel-default">
	 <div class="panel-body">
		<div class="row form-horizontal">
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getFieldLabel(request, "MERCHANT_NAME", "col-sm-4 control-label")%>
					<%=HtmlUtil.textBox("MERCHANT_NAME", "MERCHANT_NAME"+productElementId, "MERCHANT_NAME", 
					null, "", "300", "", "col-sm-6", formUtil)%>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getFieldLabel(request, "MERCHANT_ID", "col-sm-4 control-label")%>
					<%=HtmlUtil.numberBox("MERCHANT_ID", productElementId, FormatUtil.toBigDecimal("", true),
					"", "##########", "", "", true, "10", "", "", "col-sm-8 col-md-7", request)%>
				</div>
			</div>
			<div class="clearfix"></div>
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getFieldLabel(request, "ACCOUNT_NO", "col-sm-4 control-label")%>
					<%=HtmlUtil.textBoxAccountNo("ACCOUNT_NO", PRODUCT_K_PERSONAL_LOAN+"_MERCHANT", "ACCOUNT_NO_"+productElementId, "ACCOUNT_NO",
						FormatUtil.removeDash(paymentMethod.getAccountNo()), "", "", "col-sm-8 col-md-7",paymentMethod, formUtil) %>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.hidden("COMPLETE_DATA_"+PRODUCT_K_PERSONAL_LOAN, paymentMethod.getCompleteData()) %>
					<%=HtmlUtil.getSubFormLabel(request,subformId,"ACC_NAME_"+PRODUCT_K_PERSONAL_LOAN+"_MERCHANT","ACC_NAME","col-sm-4 col-md-5 control-label")%>
					<div id="ACCOUNT_NAME_<%=PRODUCT_K_PERSONAL_LOAN %>" class="col-sm-8 col-md-7"><%=FormatUtil.display(paymentMethod.getAccountName())%></div>
				</div>
			</div>
			<div class="clearfix"></div>
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getFieldLabel(request, "MARKET_CAMPAIGN_CODE", "col-sm-4 control-label")%>
					<%=HtmlUtil.numberBox("MARKET_CAMPAIGN_CODE", productElementId, FormatUtil.toBigDecimal("", true),
					"", "##########", "", "", true, "10", "", "", "col-sm-8 col-md-7", request)%>
				</div>
			</div>
		</div>
	</div>
</div>

<% } %>
<%=HtmlUtil.hidden("ROLE_ID",roleId)%>
<% if(isKPLTopup || foundPayroll || ROLE_DV.equals(roleId) || ROLE_DE2.equals(roleId)){%>
<script>
		//disable element
		targetDisplayHtml('SAVING_PLUS',MODE_VIEW,'SAVING_PLUS','N');
</script>
<% } 
  if(foundPayroll && Util.empty(paymentMethod.getAccountNo())){ %>
<script>
		if($("[name='ACCOUNT_NO_KPL']").val().length > 0 && !($("[name='SAVING_PLUS']").prop('checked')))
		{
			accountValidation('ACCOUNT_NO_KPL', MODE_EDIT, 'ACCOUNT_NO');
		}
</script>
<% } %>
<!-- KPL Logic -->
<script>
function SAVING_PLUSActionJS(element, mode, name)
{	
	var check = $("[name='SAVING_PLUS']").prop('checked');
	//alert('SAVING_PLUS clicked : checked = ' + check);
	
	if(check)
	{
		//Disable & Clear Account No KPL
		targetDisplayHtml('ACCOUNT_NO_KPL',MODE_VIEW,'ACCOUNT_NO','Y');
		//Clear Account Name and COMPLETE_DATA
		$("#ACCOUNT_NAME_KPL").empty();
		$("[name='COMPLETE_DATA_KPL']").val('');
	}
	else
	{
		targetDisplayHtml('ACCOUNT_NO_KPL',MODE_EDIT,'ACCOUNT_NO','Y');
	}
}
</script>
<script>
$("[name='ACCOUNT_NO_KPL']").on('input', function() 
{
	var role = $("[name='ROLE_ID']").val();
	if(role != 'DV')
	{
    	if($(this).val().length == 0)
		{
			targetDisplayHtml('SAVING_PLUS',MODE_EDIT,'SAVING_PLUS','N');
		}
		else
		{
			$("[name='SAVING_PLUS']").removeAttr("checked");
			$("[name='SAVING_PLUS']").val("");
		}
	}
});
</script>