<%@page import="com.eaf.core.ulo.common.properties.SystemConfig"%>
<%@page import="com.ibm.jvm.dtfjview.SystemProperties"%>
<%@page contentType="text/html;charset=UTF-8"%>

<%@page import="java.util.ArrayList"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.common.utility.IncomeTypeUtility"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.core.ulo.common.properties.FormControl"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.orig.ulo.control.util.PersonalInfoUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.BundleSMEDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.BundleKLDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.BundleHLDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.IncomeInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.IncomeCategoryDataM"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="EntityForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler"/>

<script type="text/javascript" src="orig/ulo/subform/income/js/VerifyIncomeSubForm.js"></script>
<%
	String FIELD_ID_REVENUE_CATEGORY =  SystemConstant.getConstant(IncomeTypeUtility.FIELD_ID_REVENUE_CATEGORY);
	String VERIFIED_INCOME_NOT_AVAILABLE_VALUE =  SystemConstant.getConstant("VERIFIED_INCOME_NOT_AVAILABLE_VALUE");
	String displayMode = HtmlUtil.EDIT;
	String tagId = "";
	Logger logger = Logger.getLogger(this.getClass());
	String roleId = FormControl.getFormRoleId(request);
	ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM) EntityForm.getObjectForm();
	PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
	if(null == personalInfo){
		personalInfo = new PersonalInfoDataM();
	}
	ArrayList<IncomeInfoDataM> incomelist = personalInfo.getIncomeInfos();
	if(incomelist == null) {
		incomelist = new ArrayList<IncomeInfoDataM>();
		personalInfo.setIncomeInfos(incomelist);
	}
	ArrayList<ApplicationDataM> appList = ((ApplicationGroupDataM) EntityForm.getObjectForm()).filterApplicationLifeCycle();
	if(!Util.empty(appList)){
	
	}else{
		appList = new ArrayList<ApplicationDataM>();
		((ApplicationGroupDataM) EntityForm.getObjectForm()).setApplications(appList);
	}
	String ROLE_DE1_2 = SystemConstant.getConstant("ROLE_DE1_2");
	//String INC_TYPE_BUNDLE_HL = SystemConstant.getConstant("INC_TYPE_BUNDLE_HL");
	//String INC_TYPE_BUNDLE_KL = SystemConstant.getConstant("INC_TYPE_BUNDLE_KL");
	//String INC_TYPE_BUNDLE_SME = SystemConstant.getConstant("INC_TYPE_BUNDLE_SME");
	//String INC_TYPE_OTH_INCOME = SystemConstant.getConstant("INC_TYPE_OTH_INCOME");
	ArrayList<String> INCOME_TYPE_EXCEPTION = SystemConfig.getArrayListGeneralParam("INCOME_TYPE_EXCEPTION");
%>
<%=HtmlUtil.hidden("INCOME_SEQ", "") %>
<%=HtmlUtil.hidden("INCOME_TYPE", "") %>
<div class="panel panel-default">
<%-- 
	<div class="panel-body">
		<div class="row form-horizontal">
		<div class="col-sm-12">
			<div class="form-group">
				<%=HtmlUtil.getFieldLabel(request,"REVENUE_CATEGORY","col-sm-2 col-md-5 marge-label control-label")%>
				<div class="col-sm-10 col-md-9 marge-field">
					<div class="row">
						<div class="col-xs-12">
							<%=HtmlUtil.dropdown("REVENUE_CATEGORY","","","","",FIELD_ID_REVENUE_CATEGORY,"ALL_ALL_ALL",displayMode,HtmlUtil.tagId(tagId,"REVENUE_CATEGORY"),"col-sm-8 col-md-7",request)%>
							<div class="col-xs-2"><%=HtmlUtil.button("ADD_BTN_INCOME","",displayMode,"btnsmall_add","",request) %></div>
						</div>
					</div>
				</div>
			</div>
		</div>
		</div>
	</div> --%>
	<table id="VerifyIncome" class="table table-striped table-hover">
		<tbody>
		<%-- For --%>
		<%
			int count = 0;
			if(!Util.empty(incomelist)) {
				for(IncomeInfoDataM incomeM: incomelist) {
					String incType = incomeM.getIncomeType();
					if(!INCOME_TYPE_EXCEPTION.contains(incType)){
					/* if(INC_TYPE_OTH_INCOME.equals(incType)) {
						continue;
					} */
					count++;
			%>
			<tr id="ROW_<%=incType%>_<%= incomeM.getSeq()%>" onclick="loadIncomePopup(this,'<%=incomeM.getSeq() %>','<%=incType%>');">
				<td class="loadPopup cursorHand"><%=FormatUtil.getString(count)%></td>			
				<td class="loadPopup cursorHand" width="60%"><%=FormatUtil.display(CacheControl.getName(FIELD_ID_REVENUE_CATEGORY, incType))%></td>
				<td class="loadPopup cursorHand"><%if(IncomeTypeUtility.isIncomeComplete(incomeM, roleId)) {%><img src="images\ulo\compareFlag2.png" title="Complete"><%}else{ %><img src="images\ulo\compareFlag2Edit3.png" title="Edit"><%}%></td></tr>
			<%	}
			 }
			}
			%>
			<%-- Defect UAT : 2347, 4980 Using ORIG_APP.INC_INFO data to display Ver Income instead of ORIG_APP.ORIG_BUNDLE_HL, ORIG_APP.ORIG_BUNDLE_KL, and ORIG_APP.ORIG_BUNDLE_SME
			<%
			for(ApplicationDataM appM : appList) {
				// Check BundlingHL
				BundleHLDataM hlData = appM.getBundleHL();
				if(null != hlData) {
				count++;
			%>
			<tr id="ROW_<%=INC_TYPE_BUNDLE_HL%>_<%=appM.getApplicationRecordId()%>" onclick="loadBundlePopup(this,'<%=appM.getApplicationRecordId() %>','<%=INC_TYPE_BUNDLE_HL %>');">
				<td class="loadBundle cursorHand"><%=FormatUtil.getString(count)%></td>			
				<td class="loadBundle cursorHand" width="60%"><%=FormatUtil.display(CacheControl.getName(FIELD_ID_REVENUE_CATEGORY, INC_TYPE_BUNDLE_HL))%></td>
				<td class="loadBundle cursorHand">
					<%if(MConstant.FLAG.YES.equals(hlData.getCompareFlag()) || 
					MConstant.FLAG.WRONG.equals(hlData.getCompareFlag()) ||
					(ROLE_DE1_2.equals(roleId) && MConstant.FLAG.NO.equals(hlData.getCompareFlag()))) {%><img src="images\ulo\compareFlag2.png" title="Complete"><%}else{ %><img src="images\ulo\compareFlag2Edit3.png" title="Edit"><%}%></td></tr>
			<% } 
			// Check BundlingKL
			BundleKLDataM klData = appM.getBundleKL();
			if(klData != null) {
				count++;
			%>
			<tr id="ROW_<%=INC_TYPE_BUNDLE_KL%>_<%=appM.getApplicationRecordId()%>" onclick="loadBundlePopup(this,'<%=appM.getApplicationRecordId() %>','<%=INC_TYPE_BUNDLE_KL %>');">
				<td class="loadBundle cursorHand"><%=FormatUtil.getString(count)%></td>			
				<td class="loadBundle cursorHand" width="60%"><%=FormatUtil.display(CacheControl.getName(FIELD_ID_REVENUE_CATEGORY, INC_TYPE_BUNDLE_KL))%></td>
				<td class="loadBundle cursorHand"><%if(MConstant.FLAG.YES.equals(klData.getCompareFlag()) || MConstant.FLAG.WRONG.equals(klData.getCompareFlag()) ||
				(ROLE_DE1_2.equals(roleId) && MConstant.FLAG.NO.equals(klData.getCompareFlag()))) {%><img src="images\ulo\compareFlag2.png" title="Complete"><%}else{ %><img src="images\ulo\compareFlag2Edit3.png" title="Edit"><%}%></td></tr>
			<%} 
			// Check BundlingKL
			BundleSMEDataM smeData = appM.getBundleSME();
			if(smeData != null) {
				count++;
			%>
			<tr id="ROW_<%=INC_TYPE_BUNDLE_SME%>_<%=appM.getApplicationRecordId()%>" onclick="loadBundlePopup(this,'<%=appM.getApplicationRecordId() %>','<%=INC_TYPE_BUNDLE_SME %>');">
				<td class="loadBundle cursorHand"><%=FormatUtil.getString(count)%></td>			
				<td class="loadBundle cursorHand" width="60%"><%=FormatUtil.display(CacheControl.getName(FIELD_ID_REVENUE_CATEGORY, INC_TYPE_BUNDLE_SME))%></td>
				<td class="loadBundle cursorHand"><%if(MConstant.FLAG.YES.equals(smeData.getCompareFlag()) || MConstant.FLAG.WRONG.equals(smeData.getCompareFlag()) ||
				(ROLE_DE1_2.equals(roleId) && MConstant.FLAG.NO.equals(smeData.getCompareFlag()))) {%><img src="images\ulo\compareFlag2.png" title="Complete"><%}else{ %><img src="images\ulo\compareFlag2Edit3.png" title="Edit"><%}%></td></tr>
			<% }
			}
			%>
			--%>
			<%
			if(count == 0){
			%>
		 	<tr>
		 		<td align="center" colspan="999"><%=LabelUtil.getText(request,"NO_RECORD_FOUND")%></td>
		 	</tr>
		 	<%}%>
		</tbody>
	</table>
	<br>
	<div class="panel-body">
		<div class="row form-horizontal">
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request,"","VERIFIED_INCOME","VERIFIED_INCOME","col-sm-4 col-md-5 control-label")%>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group">
					<%=Util.empty(personalInfo.getTotVerifiedIncome()) ? "-":
					  (FormatUtil.toBigDecimal(VERIFIED_INCOME_NOT_AVAILABLE_VALUE).compareTo(personalInfo.getTotVerifiedIncome())==0? "N/A": FormatUtil.display(personalInfo.getTotVerifiedIncome(),true)) %>
				</div>
			</div>
		</div>
	</div>
</div>

