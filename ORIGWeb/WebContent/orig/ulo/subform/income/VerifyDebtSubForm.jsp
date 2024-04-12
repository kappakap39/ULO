<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.core.ulo.common.properties.FormControl"%>
<%@page import="com.eaf.orig.ulo.control.util.PersonalInfoUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.DebtInfoDataM"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="EntityForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler"/>

<script type="text/javascript" src="orig/ulo/subform/income/js/VerifyDebtSubForm.js"></script>
<%
	String displayMode = HtmlUtil.EDIT;
	String subformId = "VERIFY_DEBT_SUBFORM";
	String formId="POPUP_PAYSLIP_FORM";
	String tagId = "";
	Logger logger = Logger.getLogger(this.getClass());
	String roleId = FormControl.getFormRoleId(request);
	ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM) EntityForm.getObjectForm();
	PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
	if(null == personalInfo){
		personalInfo = new PersonalInfoDataM();
	}
	ArrayList<DebtInfoDataM> debtList = personalInfo.getDebtInfos();
	if(debtList == null) {
		debtList = new ArrayList<DebtInfoDataM>();
	}
	FormUtil formUtil = new FormUtil(formId,subformId,request);
%>
<div class="panel panel-default">
	<table id="CreditBureauDebt" class="table table-striped table-hover">
		<caption><%=LabelUtil.getText(request,"OTHER_DEBT_BUREAU")%></caption>
		<tbody>
			<%
			if(!Util.empty(debtList)) {
				for(DebtInfoDataM debtM: debtList) {
					int debtSeq = debtM.getSeq();
					%>
			<tr id="ROW_<%=debtSeq%>">
				<td width="60%" align="right"><%=FormatUtil.display(CacheControl.displayName(SystemConstant.getConstant("FIELD_ID_DEBT_TYPE"), debtM.getDebtType()))%><FONT color = red>*</FONT></td>			
				<td><%=HtmlUtil.currencyBox("DEBT_AMOUNT", FormatUtil.getString(debtSeq), "DEBT_AMOUNT", "", debtM.getDebtAmt(), "#####0.##", true, "15", "", "", formUtil)%></td>
			</tr>
			<%	}	
			} else{%>
		 	<tr>
		 		<td colspan="9999" align="center"><%=LabelUtil.getText(request,"NO_RECORD_FOUND")%></td>
		 	</tr>
		 <%}%>
		</tbody>
	</table>
</div>

