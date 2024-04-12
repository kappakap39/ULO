<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.SaleInfoDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.control.util.SaleInfoUtil"%>
<%@page import="com.eaf.orig.ulo.control.util.PersonalInfoUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>

<script type="text/javascript" src="orig/ulo/subform/js/BankStaffSubForm.js"></script>
<%
	String subformId = "BANK_STAFF_SUBFORM_1";
	String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	Logger logger = Logger.getLogger(this.getClass());
	String SALE_TYPE_NORMAL_SALES = SystemConstant.getConstant("SALE_TYPE_NORMAL_SALES");
	String SALE_TYPE_REFERENCE_SALES = SystemConstant.getConstant("SALE_TYPE_REFERENCE_SALES");
	String SALE_TYPE_CASH_DAY_ONE_SALES = SystemConstant.getConstant("SALE_TYPE_CASH_DAY_ONE_SALES");
	String SALE_TYPE_ALLIANCE_SALES = SystemConstant.getConstant("SALE_TYPE_ALLIANCE_SALES");
	SaleInfoDataM saleInfoRefSale  =  ORIGForm.getObjectForm().getSaleInfoByType(SALE_TYPE_REFERENCE_SALES);
	if(Util.empty(saleInfoRefSale)){
		saleInfoRefSale = new SaleInfoDataM();
	}
	
	SaleInfoDataM saleInfoNormalSale  =  ORIGForm.getObjectForm().getSaleInfoByType(SALE_TYPE_NORMAL_SALES);
	if(Util.empty(saleInfoNormalSale)){
		saleInfoNormalSale = new SaleInfoDataM();
	}
	SaleInfoDataM saleInfoAllianceSale  =  ORIGForm.getObjectForm().getSaleInfoByType(SALE_TYPE_ALLIANCE_SALES);
	if(Util.empty(saleInfoAllianceSale)){
		saleInfoAllianceSale = new SaleInfoDataM();
	}
	SaleInfoDataM saleInfoCashDayOneSale  =  ORIGForm.getObjectForm().getSaleInfoByType(SALE_TYPE_CASH_DAY_ONE_SALES);
	if(Util.empty(saleInfoCashDayOneSale)){
		saleInfoCashDayOneSale = new SaleInfoDataM();
	}
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	if (null == applicationGroup) {
		applicationGroup = new ApplicationGroupDataM();
	}	
	PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
	if(null == personalInfo){
		personalInfo = new PersonalInfoDataM();
	}
	String PERSONAL_TYPE = personalInfo.getPersonalType();
	
	String displayMode = HtmlUtil.EDIT;
	
	FormUtil formUtil = new FormUtil(subformId,request);
	
	String saleCacheId;
	//if ()
	
	ArrayList<String> products = ORIGForm.getObjectForm().getProducts(ORIGForm.getObjectForm().getMaxLifeCycle());
	String SEARCH_SALE_ID = SystemConstant.getConstant("SEARCH_SALE_ID");
	String SEARCH_SALE_NAME = SystemConstant.getConstant("SEARCH_SALE_NAME");
 %>
 <div class="row form-horizontal nopadding-subform">
	<div class="col-sm-12">
		<div class="panel panel-default">
			<div class="panel-heading">
    			<%=LabelUtil.getText(request, "REF_TXT")%>
			</div>
			<div class="panel-body">
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.getSubFormLabel(request, subformId, "REF_NAME", "REF_CODE", "col-sm-4 col-md-5 control-label")%>
<%-- 						<%=HtmlUtil.numberBox("REF_NAME","","REF_NAME_"+PERSONAL_TYPE,"SEARCH_SALE_NAME", --%>
<%-- 							saleInfoRefSale.getSalesId(), "", "##", false, "2", "", "col-sm-8 col-md-7", formUtil) %> --%>
						<%=HtmlUtil.textBoxOnlyNum("REF_NAME","REF_NAME_"+PERSONAL_TYPE,"REF_NAME",saleInfoRefSale.getSalesId(),"","20","","col-sm-8 col-md-7",formUtil)%>
					</div>
				</div>
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.getSubFormLabel(request, subformId, "REF_BRANCH_CODE", "REF_BRANCH_CODE", "col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.textBox("REF_BRANCH_CODE", "", "REF_BRANCH_CODE_"+PERSONAL_TYPE,"REF_BRANCH_CODE",
							SaleInfoUtil.showBranchDetp(saleInfoRefSale),"","120","","col-sm-8 col-md-7", applicationGroup,formUtil)%>
					</div>
				</div>
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.getSubFormLabel(request, subformId, "REF_INFO_NAME", "REF_INFO_NAME", "col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.textBox("REF_INFO_NAME","","REF_INFO_NAME_"+PERSONAL_TYPE,"REF_INFO_NAME",
							saleInfoRefSale.getSalesName(),"","50","","col-sm-8 col-md-7", applicationGroup,formUtil)%>
					</div>
				</div>
			</div>			
			<div class="panel-heading">
    			<%=LabelUtil.getText(request, "SALE_DATA")%>
			</div>
			<div class="panel-body">
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.getSubFormLabel(request, subformId, "REF_NAME", "REF_CODE", "col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.textBoxOnlyNum("SALES_NAME","","SALES_NAME_"+PERSONAL_TYPE,"SEARCH_SALE_NAME",
							saleInfoNormalSale.getSalesId(),"","50","","col-sm-8 col-md-7", applicationGroup,formUtil)%>
					</div>
				</div>
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.getSubFormLabel(request, subformId, "SALES_BRANCH_CODE", "SALES_BRANCH_CODE", "col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.textBox("SALES_BRANCH_CODE","","SALES_BRANCH_CODE_"+PERSONAL_TYPE,"SALES_BRANCH_CODE",
							SaleInfoUtil.showBranchDetp(saleInfoNormalSale),"","120","","col-sm-8 col-md-7", applicationGroup,formUtil)%>
					</div>
				</div>
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.getSubFormLabel(request, subformId, "REF_INFO_NAME", "SALES_INFO_NAME", "col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.textBox("SALES_INFO_NAME","","SALES_INFO_NAME_"+PERSONAL_TYPE,"SALES_INFO_NAME",
							saleInfoNormalSale.getSalesName(),"","50","","col-sm-8 col-md-7", applicationGroup,formUtil)%>
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.getSubFormLabel(request, subformId, "SALES_PHONE_NO", "SALES_PHONE_NO", "col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.textBoxMobile("SALES_PHONE_NO","","SALES_PHONE_NO_"+PERSONAL_TYPE,"SALES_PHONE_NO",
							saleInfoNormalSale.getSalesPhoneNo(),"","","col-sm-8 col-md-7",applicationGroup,formUtil)%>
					</div>
				</div>
			</div>
			<% if(SystemConstant.lookup("SALE_TYPE_ALLIANCE_APPLICATION_TEMPLATE", ORIGForm.getObjectForm().getApplicationTemplate())) { %>
			<div class="panel-heading">
    			<%=LabelUtil.getText(request, "ALLIANCE_INFO")%>
			</div>
			<div class="panel-body">
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.getSubFormLabel(request, subformId, "SALES_CODE_"+SALE_TYPE_ALLIANCE_SALES, "ALLIANCE_CODE_NAME", "col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.textBoxOnlyNum("SALES_CODE_"+SALE_TYPE_ALLIANCE_SALES,"","SALES_CODE_"+SALE_TYPE_ALLIANCE_SALES+"_"+PERSONAL_TYPE,"SALES_CODE_"+SALE_TYPE_ALLIANCE_SALES,
							saleInfoAllianceSale.getSalesId(),"","50","","col-sm-8 col-md-7", applicationGroup,formUtil)%>
					</div>
				</div>
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.getSubFormLabel(request, subformId, "SALES_BRANCH_CODE_"+SALE_TYPE_ALLIANCE_SALES, "ALLIANCE_BRANCH_CODE", "col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.textBox("SALES_BRANCH_CODE_"+SALE_TYPE_ALLIANCE_SALES,"","SALES_BRANCH_CODE_"+SALE_TYPE_ALLIANCE_SALES+"_"+PERSONAL_TYPE,"SALES_BRANCH_CODE_"+SALE_TYPE_ALLIANCE_SALES,
							saleInfoAllianceSale.getSalesBranchCode(),"","10","","col-sm-8 col-md-7", applicationGroup,formUtil)%>
					</div>
				</div>
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.getSubFormLabel(request, subformId, "SALES_NAME_"+SALE_TYPE_ALLIANCE_SALES, "ALLIANCE_SALES_NAME", "col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.textBox("SALES_NAME_"+SALE_TYPE_ALLIANCE_SALES,"","SALES_NAME_"+SALE_TYPE_ALLIANCE_SALES+"_"+PERSONAL_TYPE,"SALES_NAME_"+SALE_TYPE_ALLIANCE_SALES,
							saleInfoAllianceSale.getSalesName(),"","100","","col-sm-8 col-md-7", applicationGroup,formUtil)%>
					</div>
				</div>
			</div>
			<% } %>
		<%if(!Util.empty(products) && !products.contains(PRODUCT_CRADIT_CARD)){%>
			<div class="panel-heading">
    			<%=LabelUtil.getText(request, "CASH_DAYONE")%>
			</div>
			<div class="panel-body">
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.getSubFormLabel(request, subformId, "SALES_CODE_"+SALE_TYPE_ALLIANCE_SALES, "ALLIANCE_CODE_NAME", "col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.textBox("CASH_DAYONE_NAME","","CASH_DAYONE_NAME_"+"_"+PERSONAL_TYPE,"CASH_DAYONE_NAME",
							saleInfoCashDayOneSale.getSalesId(),"","50","","col-sm-8 col-md-7", applicationGroup,formUtil)%>
					</div>
				</div>
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.getSubFormLabel(request, subformId, "CASH_DAYONE_BRANCH_CODE", "CASH_DAYONE_BRANCH_CODE", "col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.textBox("CASH_DAYONE_BRANCH_CODE","","CASH_DAYONE_BRANCH_CODE_"+PERSONAL_TYPE,"CASH_DAYONE_BRANCH_CODE",
							SaleInfoUtil.showBranchDetp(saleInfoCashDayOneSale),"","120","","col-sm-8 col-md-7",applicationGroup,formUtil)%>
					</div>
				</div>
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.getSubFormLabel(request, subformId, "CASH_DAYONE_INFO_NAME", "ALLIANCE_SALES_NAME", "col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.textBox("CASH_DAYONE_INFO_NAME","","CASH_DAYONE_INFO_NAME_"+PERSONAL_TYPE,"CASH_DAYONE_INFO_NAME",
							saleInfoCashDayOneSale.getSalesName(),"","50","","col-sm-8 col-md-7", applicationGroup,formUtil)%>
					</div>
				</div>
				<div class="clearfix"></div>
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.getSubFormLabel(request, subformId, "SALES_COMMENT", "SALES_COMMENT", "col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.textBox("SALES_COMMENT","","SALES_COMMENT_"+PERSONAL_TYPE,"SALES_COMMENT",
							saleInfoCashDayOneSale.getSalesComment(),"","250","","col-sm-8 col-md-7",applicationGroup,formUtil)%>
					</div>
				</div>
			</div>
		<%} %>
		</div>
	</div>
</div>
