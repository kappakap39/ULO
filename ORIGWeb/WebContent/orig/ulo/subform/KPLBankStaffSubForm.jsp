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
<%@page import="com.eaf.orig.ulo.app.view.util.kpl.KPLUtil"%>
<%@page import="com.eaf.orig.ulo.control.util.PersonalInfoUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>

<script type="text/javascript" src="orig/ulo/subform/js/KPLBankStaffSubForm.js?v=1"></script>
<%
	String subformId = "KPL_BANK_STAFF_SUBFORM";
	String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	Logger logger = Logger.getLogger(this.getClass());
	String SALE_TYPE_NORMAL_SALES = SystemConstant.getConstant("SALE_TYPE_NORMAL_SALES");

	SaleInfoDataM saleInfoNormalSale  =  ORIGForm.getObjectForm().getSaleInfoByType(SALE_TYPE_NORMAL_SALES);
	if(Util.empty(saleInfoNormalSale)){
		saleInfoNormalSale = new SaleInfoDataM();
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
	
	ArrayList<String> products = ORIGForm.getObjectForm().getProducts(ORIGForm.getObjectForm().getMaxLifeCycle());
	String SEARCH_SALE_ID = SystemConstant.getConstant("SEARCH_SALE_ID");
	String SEARCH_SALE_NAME = SystemConstant.getConstant("SEARCH_SALE_NAME");
	
	boolean isKPL = KPLUtil.isKPL(applicationGroup);
	
 %>
 <div class="row form-horizontal nopadding-subform">
	<div class="col-sm-12">
		<div class="panel panel-default">
			<div class="panel-heading">
    			<%=LabelUtil.getText(request, "SALE_DATA")%>
			</div>
			<div class="panel-body">
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.getSubFormLabel(request, subformId, "CODENAME_SALES", "CODENAME_SALES", "col-sm-4 col-md-5 control-label")%>
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
				</div>
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.getSubFormLabel(request, subformId, "SALES_PHONE_NO", "SALES_PHONE_NO", "col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.textBoxMobile("SALES_PHONE_NO","","SALES_PHONE_NO_"+PERSONAL_TYPE,"SALES_PHONE_NO",
							saleInfoNormalSale.getSalesPhoneNo(),"","","col-sm-8 col-md-7",applicationGroup,formUtil)%>
					</div>
				</div>
			</div>
		</div>
</div>

<script>
$("[name='SAVING_PLUS']").change(function() {
		var mode;
        if($(this).is(":checked")) 
        {
            mode = MODE_EDIT;
        }
        else
        {
        	mode = MODE_VIEW;
        }
        targetDisplayHtml('SALES_NAME',mode,'SALES_NAME','Y');
		targetDisplayHtml('SALES_PHONE_NO',mode,'SALES_PHONE_NO','Y');
		targetDisplayHtml('SALES_BRANCH_CODE',mode,'SALES_BRANCH_CODE','Y');
		targetDisplayHtml('SALES_INFO_NAME',mode,'SALES_INFO_NAME','Y');
});
</script>