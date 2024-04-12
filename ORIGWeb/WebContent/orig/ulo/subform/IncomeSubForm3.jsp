<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.app.view.util.kpl.KPLUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>

<script type="text/javascript" src="orig/ulo/subform/js/IncomeSubForm.js"></script>
<%
	String subformId = "INCOME_SUBFORM_3";
	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");	
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
	if(null == personalInfo){
		personalInfo = new PersonalInfoDataM();
	}
	int PERSONAL_SEQ = personalInfo.getSeq();	
	String displayMode = HtmlUtil.EDIT;
	
// 	String personalElementId = FormatUtil.getSmartDataEntryId(PERSONAL_TYPE,PERSONAL_SEQ);
	
	String personalElementId = FormatUtil.getPersonalElementId(personalInfo);	
	
	String FIELD_ID_RECEIVE_INCOME_METHOD = SystemConstant.getConstant("FIELD_ID_RECEIVE_INCOME_METHOD");
	String FIELD_ID_RECEIVE_INCOME_BANK = SystemConstant.getConstant("FIELD_ID_RECEIVE_INCOME_BANK");
	
	FormUtil formUtil = new FormUtil(subformId,request);
	
	boolean isKPL = KPLUtil.isKPL(applicationGroup);
	String TOPUP_FLAG = KPLUtil.getTopUpFlag(applicationGroup);
%>
<div class="panel panel-default">
<div class="panel-heading"><%=HtmlUtil.getHeaderLabel(request,subformId,"SALARY"+TOPUP_FLAG,"INCOME_SUBFORM")%></div>
<div class="panel-body"> 
<div class="row form-horizontal">
<!-- 	<div class="col-sm-2"> -->
<!-- 		<div class="col-sm-12"> -->
<!-- 			<div class="form-group"> -->
<%-- 				<%=HtmlUtil.getLabel(request, "SALARY_DESC","control-label")%> --%>
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 	</div> -->
<!-- 	<div class="col-sm-10"> -->
<!-- 		<div class="col-sm-12"> -->
<!-- 			<div class="col-sm-6"> -->
<!-- 				<div class="form-group"> -->
<%-- 					<%=HtmlUtil.getSubFormLabel(request,subformId,"","SALARY","col-sm-4 col-md-5 control-label")%> --%>
<!-- 					<div class="col-sm-8 col-md-7"> -->
<!-- 						<div class="col-xs-12"> -->
<%-- 							<%=HtmlUtil.currencyBox("SALARY", "", "SALARY_"+personalElementId, "SALARY",  --%>
<%-- 								personalInfo.getSalary(), "", true, "15", "", "col-xs-9 col-xs-padding",personalInfo, formUtil) %> --%>
<%-- 							<%=HtmlUtil.getLabel(request, "BAHT","col-xs-3 control-label")%> --%>
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 	</div> -->
<!-- 	<div class="clearfix"></div> -->
	
<!-- 	<div class="col-sm-2"> -->
<!-- 		<div class="col-sm-12"> -->
<!-- 			<div class="form-group"> -->
<%-- 				<%=HtmlUtil.getLabel(request, "OWNER","control-label")%> --%>
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 	</div> -->
<!-- 	<div class="col-sm-10"> -->
<!-- 		<div class="col-sm-12"> -->
<!-- 			<div class="col-sm-6"> -->
<!-- 				<div class="form-group"> -->
<%-- 					<%=HtmlUtil.getSubFormLabel(request, subformId,"","CIRCULATION_INCOME", "col-sm-4 col-md-5 control-label")%> --%>
<!-- 					<div class="col-sm-8 col-md-7"> -->
<!-- 						<div class="col-xs-12"> -->
<%-- 							<%=HtmlUtil.currencyBox("CIRCULATION_INCOME", "", "CIRCULATION_INCOME_"+personalElementId, "CIRCULATION_INCOME",  --%>
<%-- 								personalInfo.getCirculationIncome(), "", true, "15", "", "col-xs-9 col-xs-padding", personalInfo, formUtil) %> --%>
<%-- 							<%=HtmlUtil.getLabel(request, "BAHT","col-xs-3 control-label")%> --%>
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 			<div class="col-sm-6"> -->
<!-- 				<div class="form-group"> -->
<%-- 					<%=HtmlUtil.getSubFormLabel(request, subformId,"","NET_PROFIT_INCOME", "col-sm-4 col-md-5 control-label")%> --%>
<!-- 					<div class="col-sm-8 col-md-7"> -->
<!-- 						<div class="col-xs-12"> -->
<%-- 							<%=HtmlUtil.currencyBox("NET_PROFIT_INCOME", "", "NET_PROFIT_INCOME_"+personalElementId, "NET_PROFIT_INCOME",  --%>
<%-- 								personalInfo.getNetProfitIncome(), "", true, "15", "", "col-xs-9 col-xs-padding", personalInfo, formUtil) %> --%>
<%-- 							<%=HtmlUtil.getLabel(request, "BAHT","col-xs-3 control-label")%> --%>
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 	</div> -->
<!-- 	<div class="clearfix"></div> -->
	
<!-- 	<div class="col-sm-2"> -->
<!-- 		<div class="col-sm-12"> -->
<!-- 			<div class="form-group"> -->
<%-- 				<%=HtmlUtil.getLabel(request, "SELF_EMPLOYED","control-label")%> --%>
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 	</div> -->
<!-- 	<div class="col-sm-10"> -->
<!-- 		<div class="col-sm-12"> -->
<!-- 			<div class="col-sm-6"> -->
<!-- 				<div class="form-group"> -->
<%-- 					<%=HtmlUtil.getSubFormLabel(request, subformId,"","FREELANCE_INCOME", "col-sm-4 col-md-5 control-label")%> --%>
<!-- 					<div class="col-sm-8 col-md-7"> -->
<!-- 						<div class="col-xs-12"> -->
<%-- 							<%=HtmlUtil.currencyBox("FREELANCE_INCOME","", "FREELANCE_INCOME_"+personalElementId, "FREELANCE_INCOME",  --%>
<%-- 								personalInfo.getFreelanceIncome(), "", true, "15", "", "col-xs-9 col-xs-padding",personalInfo, formUtil) %> --%>
<%-- 							<%=HtmlUtil.getLabel(request, "BAHT","col-xs-3 control-label")%> --%>
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 	</div> -->
<!-- 	<div class="clearfix"></div> -->
	
<!-- 	<div class="col-sm-2"> -->
<!-- 		<div class="col-sm-12"> -->
<!-- 			<div class="form-group"> -->
<%-- 				<%=HtmlUtil.getLabel(request, "INCOME_PAYMENT","control-label")%> --%>
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 	</div> -->
<!-- 	<div class="col-sm-10"> -->
<!-- 		<div class="col-sm-12"> -->
<!-- 			<div class="col-sm-6"> -->
<!-- 				<div class="form-group"> -->
<%-- 					<%=HtmlUtil.getSubFormLabel(request, subformId,"INCOME_NET","INCOME_NET", "col-sm-4 col-md-5 control-label")%> --%>
<!-- 					<div class="col-sm-8 col-md-7"> -->
<!-- 						<div class="col-xs-12"> -->
<%-- 							<%=HtmlUtil.currencyBox("INCOME_NET", "INCOME_NET_"+personalElementId, "INCOME_NET",  --%>
<%-- 								personalInfo.getGrossIncome(), "", false, "15", "", "col-xs-9 col-xs-padding", formUtil) %> --%>
<%-- 							<%=HtmlUtil.getLabel(request, "BAHT","col-xs-3 control-label")%> --%>
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 	</div> -->

	<div class="col-sm-10">
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request, subformId,"","INCOME_PER_MONTH", "col-sm-4 col-md-5 control-label")%>
					<div class="col-sm-8 col-md-7">
						<div class="col-xs-12">
							<%=HtmlUtil.currencyBox("SALARY", "", "SALARY_"+personalElementId, "SALARY",
 								personalInfo.getSalary(), "", true, "15", "", "col-xs-9 col-xs-padding",personalInfo, formUtil) %>
 							<%=HtmlUtil.getLabel(request, "BAHT","col-xs-3 control-label")%>
						</div>
					</div>
				</div>
			</div>
	</div>
	<div class="clearfix"></div>
	
	<div class="col-sm-2">
		<div class="col-sm-12">
			<div class="form-group">
				<%=HtmlUtil.getLabel(request, "OTHER","control-label")%>
			</div>
		</div>
	</div>
	<div class="col-sm-10">
		<div class="col-sm-12">
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request, subformId,"MONTHLY_INCOME","MONTHLY_INCOME", "col-sm-4 col-md-5 control-label")%>
					<div class="col-sm-8 col-md-7">
						<div class="col-xs-12">
							<%=HtmlUtil.currencyBox("MONTHLY_INCOME", "", "MONTHLY_INCOME_"+personalElementId, "MONTHLY_INCOME", 
								personalInfo.getMonthlyIncome(), "", true, "15", "", "col-xs-9 col-xs-padding", personalInfo, formUtil) %>
							<%=HtmlUtil.getLabel(request, "BAHT","col-xs-3 control-label")%>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
	<div class="col-sm-2"> </div>
	<div class="col-sm-10">
		<div class="col-sm-12">
			<div class="col-sm-10">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request, subformId,"OTHER_INCOME","OTHER_INCOME", "col-sm-3 control-label")%>
					<div class="col-sm-9">
						<%=HtmlUtil.checkBoxInline("SRC_OTH_INC_BONUS", "","SRC_OTH_INC_BONUS_"+personalElementId, "SRC_OTH_INC_BONUS", 
							personalInfo.getSrcOthIncBonus(), "", MConstant.FLAG.YES, "","BONUS","col-xs-4 control-label col-xs-padding",personalInfo, formUtil) %>
						<%=HtmlUtil.checkBoxInline("SRC_OTH_INC_COMMISSION", "", "SRC_OTH_INC_COMMISSION_"+personalElementId, "SRC_OTH_INC_COMMISSION", 
							personalInfo.getSrcOthIncCommission(), "", MConstant.FLAG.YES, "","COMMISSION","col-xs-6 control-label col-xs-padding",personalInfo, formUtil) %>
						<%=HtmlUtil.checkBoxInline("SRC_OTH_INC_OTHER", "", "SRC_OTH_INC_OTHER_"+personalElementId, "SRC_OTH_INC_OTHER", 
							personalInfo.getSrcOthIncOther(), "", MConstant.FLAG.YES, "","OTHER","col-xs-2 control-label col-xs-padding", personalInfo, formUtil) %>
					</div>
				</div>
			</div>			
			<div class="col-sm-2">
				<div class="form-group">
					<%=HtmlUtil.textBox("OTHER_INCOME_DESC", "", "OTHER_INCOME_DESC_"+personalElementId,"OTHER_INCOME_DESC",
						personalInfo.getSorceOfIncomeOther(),"","100","","col-sm-12", personalInfo, formUtil)%>
		 		</div>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>

	<div class="col-sm-2"> </div>
	<div class="col-sm-10">
		<div class="col-sm-12">
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request, subformId,"ASSET","ASSET", "col-sm-4 col-md-5 control-label")%>
					<div class="col-sm-8 col-md-7">
						<div class="col-xs-12">
							<%=HtmlUtil.currencyBox("ASSET","", "ASSET_"+personalElementId, "ASSET", 
								personalInfo.getAssetsAmount(), "", true, "15", "", "col-xs-9 col-xs-padding",personalInfo, formUtil) %>
							<%=HtmlUtil.getLabel(request, "BAHT","col-xs-3 control-label")%>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
</div>
</div>
<%-- <div class="panel-heading"><%=LabelUtil.getText(request, "RECEIVE_INCOME")%></div>
<div class="panel-body"> 
<div class="row form-horizontal">
	<div class="col-sm-2"> </div>
	<div class="col-sm-10">
		<div class="col-sm-12">
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request,subformId,"RECEIVE_INCOME_METHOD","RECEIVE_INCOME_METHOD","col-sm-4 col-md-5 control-label")%>
					<div class="col-sm-8 col-md-7">
						<div class="col-xs-12">
							<%=HtmlUtil.dropdown("RECEIVE_INCOME_METHOD","", "RECEIVE_INCOME_METHOD_"+personalElementId, "RECEIVE_INCOME_METHOD", "", 
								personalInfo.getReceiveIncomeMethod(), "",FIELD_ID_RECEIVE_INCOME_METHOD,"ALL_ALL_ALL", "", "",personalInfo, formUtil)%>
						</div>
					</div>
				</div>
			</div>			
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request,subformId,"RECEIVE_INCOME_BANK","RECEIVE_INCOME_BANK","col-sm-4 col-md-5 control-label")%>
					<div class="col-sm-8 col-md-7">
						<div class="col-xs-12">
							<%=HtmlUtil.dropdown("RECEIVE_INCOME_BANK","", "RECEIVE_INCOME_BANK_"+personalElementId, "RECEIVE_INCOME_BANK", "", 
								personalInfo.getReceiveIncomeBank(), "",FIELD_ID_RECEIVE_INCOME_BANK,"ALL_ALL_ALL", "", "",personalInfo, formUtil)%>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
	
	<div class="col-sm-2"> </div>
	<div class="col-sm-10">
		<div class="col-sm-12">
			<div class="col-sm-3">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request,subformId,"MONTHLY_EXPENSE","MONTHLY_EXPENSE","col-sm-10 col-md-11 control-label")%>
				</div>
			</div>
				<div class="col-sm-4">
					<div class="form-group">
							<%=HtmlUtil.currencyBox("MONTHLY_EXPENSE", "", "MONTHLY_EXPENSE_"+personalElementId, "MONTHLY_EXPENSE", 
								personalInfo.getMonthlyExpense(), "", true, "15", "", "col-xs-9 col-xs-padding",personalInfo, formUtil) %>
							<%=HtmlUtil.getLabel(request, "BAHT","col-xs-3 control-label")%>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
</div> --%>
<!-- </div> -->

<!-- </div> -->

