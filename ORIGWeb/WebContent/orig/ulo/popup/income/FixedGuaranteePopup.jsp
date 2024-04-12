<%@page import="com.eaf.orig.ulo.app.view.util.dih.model.DIHQueryResult"%>
<%@page import="com.eaf.orig.ulo.app.view.util.dih.DIHProxy"%>
<%@page contentType="text/html;charset=UTF-8"%>

<%@page import="java.sql.Date"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.IncomeInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.IncomeCategoryDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.FixedGuaranteeDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<jsp:useBean id="ModuleForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler"/>

<script type="text/javascript" src="orig/ulo/popup/income/js/FixedGuaranteePopup.js"></script>
<%
	String displayMode = HtmlUtil.EDIT;
	String tagId = "";
	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String roleId = ORIGForm.getRoleId();
	PersonalInfoDataM personalInfo = ORIGForm.getObjectForm().getPersonalInfo(PERSONAL_TYPE);
	if(Util.empty(personalInfo)){
		personalInfo = new PersonalInfoDataM();
	}
	IncomeInfoDataM incomeM = (IncomeInfoDataM)ModuleForm.getObjectForm();
	if(Util.empty(incomeM)){
		incomeM = new IncomeInfoDataM();
	}
	ArrayList<IncomeCategoryDataM> fixedList = incomeM.getAllIncomes();
	if(fixedList == null) {
		fixedList = new ArrayList<IncomeCategoryDataM>();
	}
	String subFormId = "FIXED_GUARANTEE_POPUP";
	FormUtil formUtil = new FormUtil(subFormId,request);	
	
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	if (null == applicationGroup) {
		applicationGroup = new ApplicationGroupDataM();
	}
	String CACHE_BRANCH_INFO = SystemConstant.getConstant("CACHE_BRANCH_INFO");
	 	if(SystemConstant.lookup("ROLE_EXCEPTION_DISPLAY_MODE", roleId)){
				 	displayMode = HtmlUtil.VIEW;
		}
 %>
<%=HtmlUtil.hidden("subFormID",subFormId)%>
<div class="panel panel-default">
<div class="panel-body">
	<div class="row form-horizontal">
		<div class="col-sm-6">
			<div class="form-group">
				<%=HtmlUtil.getSubFormLabel(request, subFormId, "NAME", "NAME", "col-sm-2 control-label")%>
				<%=FormatUtil.display(personalInfo.getThName())%>
			</div>
		</div>
	</div>
	<div class="row form-horizontal">
		<div class="col-sm-4">
			<div class="form-group">
				<%=HtmlUtil.getSubFormLabel(request, subFormId, "ACCOUNT_NO", "ACCOUNT_NO", "col-sm-4 control-label")%>
				<%=HtmlUtil.numberBox("ACCOUNT_NO","","",null,"","####################",true,"20","","col-sm-8 ",formUtil)%>
			</div>
		</div>
		<div class="col-sm-4">
			<div class="form-group">
				<%=HtmlUtil.getSubFormLabel(request, subFormId, "SUB", "SUB", "col-sm-2 control-label")%>
				<%=HtmlUtil.numberBox("SUB","","",null,"","##########",true,"10","","col-sm-8 ",formUtil)%>
				<%=HtmlUtil.button("ADD_FIXED_BTN","",displayMode,"btnsmall_add","",request) %>
			</div>
		</div>
	</div>
</div>
</div>

<div class="panel panel-default">
<div class="panel-body">
	<table id="FixedGuarantee" class="table table-striped table-hover">
	<thead>
		<tr>
			<th></th>
			<th><%=HtmlUtil.getHeaderLabel(request,subFormId,"ACCOUNT_NO","ACCOUNT_NO")%></th>
			<th><%=HtmlUtil.getHeaderLabel(request,subFormId,"SUB","SUB")%> </th>
			<th><%=HtmlUtil.getHeaderLabel(request,subFormId,"ACCOUNT_NAME_TH","ACCOUNT_NAME_TH")%></th>
			<th><%=HtmlUtil.getHeaderLabel(request,subFormId,"BRANCH","BRANCH")%></th>
			<th><%=HtmlUtil.getHeaderLabel(request,subFormId,"HOLD_DATE","HOLD_DATE")%></th>
			<th><%=HtmlUtil.getHeaderLabel(request,subFormId,"RETENTION_TYPE","RETENTION_TYPE")%></th>
			<th><%=HtmlUtil.getHeaderLabel(request,subFormId,"DEPOSIT_GUARANTEE","DEPOSIT_GUARANTEE")%></th>
		</tr>
	</thead>
	<tbody>
		<%
		if(!Util.empty(fixedList)) {
			for(IncomeCategoryDataM incomeTypeObj : fixedList) { 
				FixedGuaranteeDataM fixedM = (FixedGuaranteeDataM)incomeTypeObj;
				DIHQueryResult<String> dihBranchName =DIHProxy.getKbankBranchData(fixedM.getBranchNo(), "TH_CNTR_NM");
				String branchName = dihBranchName.getResult();
				String delemeter="";
				if(!Util.empty(branchName)){
					delemeter="-";
				}
			%>
		<tr id="ROW_<%=fixedM.getSeq()%>">
			<td><%=HtmlUtil.icon("DELETE_BUTTON",displayMode,"btnsmall_delete BtnRemove","",request) %>	</td>
			<td align="center" id="ACCT_NO_<%=fixedM.getSeq()%>"><%=FormatUtil.display(fixedM.getAccountNo())%></td>			
			<td align="center" id="SUB_<%=fixedM.getSeq()%>"><%=FormatUtil.display(fixedM.getSub())%></td>
			<td align="center"><%=FormatUtil.display(fixedM.getAccountName())%></td>
			<td align="center"><%=FormatUtil.display(fixedM.getBranchNo())%>&nbsp;<%=delemeter%>&nbsp;<%=FormatUtil.display(branchName)%></td>
			<td align="center"><%=FormatUtil.display(fixedM.getRetentionDate(), FormatUtil.EN)%></td>
			<td align="center"><%=FormatUtil.display(fixedM.getRetentionType())%></td>
			<td align="center" id="AMOUNT_<%=fixedM.getSeq()%>"><%=FormatUtil.display(fixedM.getRetentionAmt(),"#,###,###.00")%></td>
		</tr>
		<%	}
		} %>
	</tbody>
	</table>
</div>
</div>
