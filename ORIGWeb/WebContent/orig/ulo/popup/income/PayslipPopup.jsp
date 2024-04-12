<%@page contentType="text/html;charset=UTF-8"%>

<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.PayslipDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.IncomeInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.IncomeCategoryDataM"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ModuleForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler"/>

<script type="text/javascript" src="orig/ulo/popup/income/js/PayslipPopup.js"></script>
<%
	String displayMode = HtmlUtil.EDIT;
	String subformId = "PAYSLIP_POPUP";
	String tagId = "";
	IncomeInfoDataM incomeM = (IncomeInfoDataM) ModuleForm.getObjectForm();
	ArrayList<IncomeCategoryDataM> incomeList = incomeM.getAllIncomes();
	PayslipDataM payslipM = null;
	if(incomeList.size() > 0){
		payslipM = (PayslipDataM)incomeList.get(0);
	} else {
		payslipM = new PayslipDataM();
		payslipM.setSeq(incomeList.size() + 1);
		incomeList.add(payslipM);
	}
	String bonusMonth = payslipM.getBonusMonth();
	String bobusYear = payslipM.getBonusYear();
	String genDisplayMode = displayMode;
	if(payslipM.getNoMonth() == 0) {
		genDisplayMode = HtmlUtil.VIEW;
		bonusMonth = "";
		bobusYear = "";
	}
	FormUtil formUtil = new FormUtil(subformId,request);
	String FIELD_ID_MONTH = SystemConstant.getConstant("FIELD_ID_MONTH");
	String FIELD_ID_ACCUMULATED_MONTH = SystemConstant.getConstant("FIELD_ID_ACCUMULATED_MONTH");
 %>
<div class="panel panel-default">
	<div class="panel-heading"><%=LabelUtil.getText(request,"ACCUMULATED_INCOME")%></div>
	<div class="panel-body">
		<div class="row form-horizontal">
			<div class="col-sm-6">
				<div class="form-group">
				<%=HtmlUtil.getSubFormLabel(request,subformId,"ACCUMULATED_MONTH","ACCUMULATED_MONTH","col-sm-4 col-md-5 control-label")%>
				<%=HtmlUtil.dropdown("ACCUMULATED_MONTH","ACCUMULATED_MONTH","ACCUMULATED_MONTH","",FormatUtil.getString(payslipM.getNoMonth()),"",FIELD_ID_ACCUMULATED_MONTH,"","","col-sm-8 col-md-7",formUtil) %>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group">
				<%=HtmlUtil.getSubFormLabel(request,subformId,"ACCUMULATED_INCOME","ACCUMULATED_INCOME","col-sm-4 col-md-5 control-label")%>
				<div class="input-group">
				<%=HtmlUtil.currencyBox("ACCUMULATED_INCOME","ACCUMULATED_INCOME","",payslipM.getSumIncome(),"",true,"15","","col-sm-8 col-md-7",formUtil)%>
				<%=LabelUtil.getText(request, "BAHT") %>
				</div></div>
			</div>
			<div class="clearfix"></div>
			<div class="col-sm-6">
				<div class="form-group">
				<%=HtmlUtil.getSubFormLabel(request,subformId,"ACCUMULATED_SSO","ACCUMULATED_SSO","col-sm-4 col-md-5 control-label")%>
				<div class="input-group">
				<%=HtmlUtil.currencyBox("ACCUMULATED_SSO","ACCUMULATED_SSO","",payslipM.getSumSso(),"",true,"15","","col-sm-8 col-md-7",formUtil)%>
				<%=LabelUtil.getText(request, "BAHT") %>
				</div></div>
			</div>
			<div class="col-sm-6">
				<div class="form-group">
				<%=HtmlUtil.getSubFormLabel(request,subformId,"OTH_ACCUMULATED_INCOME","OTH_ACCUMULATED_INCOME","col-sm-4 col-md-5 control-label")%>
				<div class="input-group">
				<%=HtmlUtil.currencyBox("OTH_ACCUMULATED_INCOME","OTH_ACCUMULATED_INCOME","",payslipM.getSumOthIncome(),"",true,"15","","col-sm-8 col-md-7",formUtil)%>
				<%=LabelUtil.getText(request, "BAHT") %>
				</div></div>
			</div>
			<div class="clearfix"></div>
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request,subformId,"MONTH_YR_BONUS","MONTH_YR_BONUS","col-sm-4 col-md-5 control-label")%>
					<div class="col-sm-8 col-md-7">
						<div class="input-group">
						<%=HtmlUtil.dropdown("MONTH_BONUS","","","",bonusMonth,"",FIELD_ID_MONTH,"","","input-group-btn",formUtil)%>
						<span class="input-group-btn text-center">/</span>
						<%=HtmlUtil.dropdown("YEAR_BONUS","","","YEAR_TYPE",bobusYear,"","","","","input-group-btn",formUtil)%>
						</div>
					</div>
				</div>
			</div>			
			<div class="col-sm-6">
				<div class="form-group">
				<%=HtmlUtil.getSubFormLabel(request,subformId,"ACCUMULATED_BONUS","ACCUMULATED_BONUS","col-sm-4 col-md-5 control-label")%>
				<div class="input-group">
				<%=HtmlUtil.currencyBox("ACCUMULATED_BONUS","ACCUMULATED_BONUS","",payslipM.getBonus(),"",true,"15","","col-sm-8 col-md-7",formUtil)%>
				<%=LabelUtil.getText(request, "BAHT") %>
				</div></div>
			</div>
			<div class="clearfix"></div>
			<div class="col-sm-6">
				<div class="form-group">
				<%=HtmlUtil.getSubFormLabel(request,subformId,"SALARY_DATE","SALARY_DATE","col-sm-4 col-md-5 control-label")%>
				<%=HtmlUtil.calendar("SALARY_DATE","SALARY_DATE","SALARY_DATE",payslipM.getSalaryDate(),"","",HtmlUtil.TH,"col-sm-8 col-md-7",formUtil)%>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group">
				<%=HtmlUtil.getSubFormLabel(request,subformId,"KBANK_PENSION","KBANK_PENSION","col-sm-4 col-md-5 control-label")%>
				<div class="input-group">
				<%=HtmlUtil.currencyBox("KBANK_PENSION","KBANK_PENSION","KBANK_PENSION",payslipM.getSpacialPension(),"",true,"15","","col-sm-8 col-md-7",formUtil)%>			
				<%=LabelUtil.getText(request, "BAHT") %>
				</div></div>
			</div>
		</div>
	</div>
</div>