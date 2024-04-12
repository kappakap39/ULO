<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.core.ulo.common.properties.ImplementControl"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.VerificationResultDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.IncomeCalculationDataM"%>
<%@page import="com.eaf.core.ulo.common.display.ElementInf"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<jsp:useBean id="ModuleForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler"/>
<script type="text/javascript" src="orig/ulo/popup/js/InternalIncomePopup.js"></script>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<%
	Logger logger = Logger.getLogger(this.getClass());
	String subformId ="NCB_REPORT_SUBFORM";
	FormUtil formUtil = new FormUtil(subformId,request);
	ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM) ModuleForm.getObjectForm();
	ArrayList<PersonalInfoDataM> personalInfos = applicationGroup.getUsingPersonalInfo();
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	String FIELD_ID_SORCE_OF_INCOME = SystemConstant.getConstant("FIELD_ID_SORCE_OF_INCOME");
%>
<div class="panel panel-default">
	<table class="table table-documentchecklist">
		<tbody>
			<tr class="docgroup">
				<td class="docgroup-text-left" colspan="2">
					<%=LabelUtil.getText(request, "POPUP_TABLE_INTERNAL_INCOME")%>
				</td>
			</tr>
<%
			int rowCount = 0;
			if(null != personalInfos && personalInfos.size() > 0) {
				for(PersonalInfoDataM personalInfo : personalInfos) {
					ArrayList<IncomeCalculationDataM> incomeCalculations = personalInfo.getVerificationResult().getIncomeCalculations();
					if(null != incomeCalculations && incomeCalculations.size() > 0) {
						for(IncomeCalculationDataM incomeCalculation : incomeCalculations) {
							rowCount++;
		%>
							<tr class="doctype">
								<td width="50%">
									<%=FormatUtil.display(CacheControl.getName(FIELD_ID_SORCE_OF_INCOME, incomeCalculation.getIncomeSourceType()))%>
								</td>
								<td width="50%">
									<%=FormatUtil.display(incomeCalculation.getVerifiedIncome(), FormatUtil.Format.CURRENCY_FORMAT)%>
								</td>
							</tr>
		<%
						}
					}
				}
			}
			if(rowCount <= 0) {
%>
				<tr>
					<td colspan="2">
						<%=LabelUtil.getText(request, "NO_RECORD_FOUND")%>
					</td>
				</tr>
<%
			}
%>
		</tbody>
	</table>
</div>