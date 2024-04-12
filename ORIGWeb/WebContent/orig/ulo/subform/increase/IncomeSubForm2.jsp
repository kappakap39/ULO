<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>

<script type="text/javascript" src="orig/ulo/subform/increase/js/IncomeSubForm.js"></script>
<%
	String subformId = "INCREASE_INCOME_SUBFORM_2";
	Logger logger = Logger.getLogger(this.getClass());
	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	PersonalInfoDataM personalInfo = ORIGForm.getObjectForm().getPersonalInfo(PERSONAL_TYPE);
	if(null == personalInfo){
		personalInfo = new PersonalInfoDataM();
	}
	int PERSONAL_SEQ = personalInfo.getSeq();		
	String displayMode = HtmlUtil.EDIT;
// 	String TAG_SMART_DATA_PERSONAL = FormatUtil.getSmartDataEntryId(PERSONAL_TYPE,PERSONAL_SEQ);
	String personalElementId = FormatUtil.getPersonalElementId(personalInfo);
	String FIELD_ID_RECEIVE_INCOME_METHOD = SystemConstant.getConstant("FIELD_ID_RECEIVE_INCOME_METHOD");
	String FIELD_ID_RECEIVE_INCOME_BANK = SystemConstant.getConstant("FIELD_ID_RECEIVE_INCOME_BANK");
	
	FormUtil formUtil = new FormUtil(subformId,request);
%>
<div class="panel panel-default">
<div class="panel-heading"><%=LabelUtil.getText(request, "INCOME_SUBFORM")%></div>
<div class="panel-body"> 
<div class="row form-horizontal">
	<div class="col-sm-2">
		<div class="col-sm-12">
			<div class="form-group">
				<%=HtmlUtil.getLabel(request, "SALARY_DESC","control-label")%>
			</div>
		</div>
	</div>
	<div class="col-sm-10">
		<div class="col-sm-12">
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request,subformId,"SALARY","SALARY","col-sm-4 col-md-5 control-label")%>
					<div class="col-sm-8 col-md-7">
						<div class="col-xs-12">
							<%=HtmlUtil.currencyBox("SALARY", "SALARY_"+personalElementId, "SALARY", 
								personalInfo.getSalary(), "", true, "15", "", "col-xs-9 col-xs-padding", formUtil) %>
							<%=HtmlUtil.getLabel(request, "BAHT","col-xs-3 control-label")%>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
	
	<div class="col-sm-2">
		<div class="col-sm-12">
			<div class="form-group">
				<%=HtmlUtil.getLabel(request, "OWNER","control-label")%>
			</div>
		</div>
	</div>
	<div class="col-sm-10">
		<div class="col-sm-12">
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request, subformId,"CIRCULATION_INCOME","CIRCULATION_INCOME", "col-sm-4 col-md-5 control-label")%>
					<div class="col-sm-8 col-md-7">
						<div class="col-xs-12">
							<%=HtmlUtil.currencyBox("CIRCULATION_INCOME", "CIRCULATION_INCOME_"+personalElementId, "CIRCULATION_INCOME", 
								personalInfo.getCirculationIncome(), "", true, "15", "", "col-xs-9 col-xs-padding", formUtil) %>
							<%=HtmlUtil.getLabel(request, "BAHT","col-xs-3 control-label")%>
						</div>
					</div>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request, subformId,"NET_PROFIT_INCOME","NET_PROFIT_INCOME", "col-sm-4 col-md-5 control-label")%>
					<div class="col-sm-8 col-md-7">
						<div class="col-xs-12">
							<%=HtmlUtil.currencyBox("NET_PROFIT_INCOME", "NET_PROFIT_INCOME_"+personalElementId, "NET_PROFIT_INCOME", 
								personalInfo.getNetProfitIncome(), "", true, "15", "", "col-xs-9 col-xs-padding", formUtil) %>
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
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request, subformId,"PROCEDURE_TYPE","PROCEDURE_TYPE", "col-sm-4 col-md-5 control-label")%>
					<div class="col-sm-8 col-md-7">
						<div class="col-xs-12">
							<%=HtmlUtil.textBox("PROCEDURE_TYPE","","PROCEDURE_TYPE_"+personalElementId, "PROCEDURE_TYPE", personalInfo.getProcedureTypeOfIncome(), "", "", "100"
							,"", "col-xs-9 col-xs-padding", formUtil)%>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
	
	<div class="col-sm-2">
		<div class="col-sm-12">
			<div class="form-group">
				<%=HtmlUtil.getLabel(request, "SAVING_INCOME","control-label")%>
			</div>
		</div>
	</div>
	<div class="col-sm-10">
		<div class="col-sm-12">
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request, subformId,"SAVING_INCOME","SAVING_INCOME", "col-sm-4 col-md-5 control-label")%>
					<div class="col-sm-8 col-md-7">
						<div class="col-xs-12">
							<%=HtmlUtil.currencyBox("SAVING_INCOME", "SAVING_INCOME_"+personalElementId, "SAVING_INCOME", 
								personalInfo.getSavingIncome(), "", true, "15", "", "col-xs-9 col-xs-padding", formUtil) %>
							<%=HtmlUtil.getLabel(request, "BAHT","col-xs-3 control-label")%>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
	
	<div class="col-sm-2">
		<div class="col-sm-12">
			<div class="form-group">
				<%=HtmlUtil.getLabel(request, "SELF_EMPLOYED","control-label")%>
			</div>
		</div>
	</div>
	<div class="col-sm-10">
		<div class="col-sm-12">
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request, subformId,"FREELANCE_INCOME","FREELANCE_INCOME", "col-sm-4 col-md-5 control-label")%>
					<div class="col-sm-8 col-md-7">
						<div class="col-xs-12">
							<%=HtmlUtil.currencyBox("FREELANCE_INCOME", "FREELANCE_INCOME_"+personalElementId, "FREELANCE_INCOME", 
								personalInfo.getFreelanceIncome(), "", true, "15", "", "col-xs-9 col-xs-padding", formUtil) %>
							<%=HtmlUtil.getLabel(request, "BAHT","col-xs-3 control-label")%>
						</div>
					</div>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request, subformId,"FREELANCE_TYPE","FREELANCE_TYPE", "col-sm-4 col-md-5 control-label")%>
					<div class="col-sm-8 col-md-7">
						<div class="col-xs-12">
							<%=HtmlUtil.textBox("FREELANCE_TYPE","","FREELANCE_TYPE_"+personalElementId, "FREELANCE_TYPE", ""
							, personalInfo.getFreelanceType(), "", "100","", "col-xs-9 col-xs-padding", formUtil)%>
						</div>
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
							<%=HtmlUtil.currencyBox("MONTHLY_INCOME", "MONTHLY_INCOME_"+personalElementId, "MONTHLY_INCOME", 
								personalInfo.getMonthlyIncome(), "", true, "15", "", "col-xs-9 col-xs-padding", formUtil) %>
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
			<div class="col-sm-8">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request, subformId,"OTHER_INCOME","OTHER_INCOME", "col-sm-4 control-label")%>
					<div class="col-sm-8">
						<%=HtmlUtil.checkBoxInline("SRC_OTH_INC_BONUS", "SRC_OTH_INC_BONUS_"+personalElementId, "OTHER_INCOME", 
							personalInfo.getSrcOthIncBonus(), "", MConstant.FLAG.YES, "","BONUS","col-xs-3 control-label col-xs-padding", formUtil) %>
						<%=HtmlUtil.checkBoxInline("SRC_OTH_INC_COMMISSION", "SRC_OTH_INC_COMMISSION_"+personalElementId, "OTHER_INCOME", 
							personalInfo.getSrcOthIncCommission(), "", MConstant.FLAG.YES, "","COMMISSION","col-xs-4 control-label col-xs-padding", formUtil) %>
						<%=HtmlUtil.checkBoxInline("SRC_OTH_INC_OTHER", "SRC_OTH_INC_OTHER_"+personalElementId, "OTHER_INCOME", 
							personalInfo.getSrcOthIncOther(), "", MConstant.FLAG.YES, "","OTHER","col-xs-3 control-label col-xs-padding", formUtil) %>
					</div>
				</div>
			</div>			
			<div class="col-sm-4">
				<div class="form-group">
					<%=HtmlUtil.textBox("OTHER_INCOME_DESC","OTHER_INCOME_DESC_"+personalElementId,"OTHER_INCOME_DESC",
						personalInfo.getSorceOfIncomeOther(),"","100","","col-sm-8 col-md-7",formUtil)%>
		 		</div>
			</div>
		</div>
	</div>
	
</div>
</div>
<!--<div class="panel-heading"><%=LabelUtil.getText(request, "RECEIVE_INCOME")%></div>
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
							<%=HtmlUtil.dropdown("RECEIVE_INCOME_METHOD", "RECEIVE_INCOME_METHOD_"+personalElementId, "RECEIVE_INCOME_METHOD", "", 
								personalInfo.getReceiveIncomeMethod(), "",FIELD_ID_RECEIVE_INCOME_METHOD,"ALL_ALL_ALL", "", "", formUtil)%>
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
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request,subformId,"RECEIVE_INCOME_BANK","RECEIVE_INCOME_BANK","col-sm-4 col-md-5 control-label")%>
					<div class="col-sm-8 col-md-7">
						<div class="col-xs-12">
							<%=HtmlUtil.dropdown("RECEIVE_INCOME_BANK", "RECEIVE_INCOME_BANK_"+personalElementId, "RECEIVE_INCOME_BANK", "", 
								personalInfo.getReceiveIncomeBank(), "",FIELD_ID_RECEIVE_INCOME_BANK,"ALL_ALL_ALL", "", "", formUtil)%>
						</div>
					</div>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request,subformId,"BRANCH","BRANCH","col-sm-4 col-md-5 control-label")%>
					<div class="col-sm-8 col-md-7">
						<div class="col-xs-12">
							<%=HtmlUtil.textBox("RECEIVE_INCOME_BANK_BRANCH","RECEIVE_INCOME_BANK_BRANCH_"+personalElementId,"BRANCH",
								personalInfo.getReceiveIncomeBankBranch(),"","100","","",formUtil)%>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
</div>
</div> -->
</div>


