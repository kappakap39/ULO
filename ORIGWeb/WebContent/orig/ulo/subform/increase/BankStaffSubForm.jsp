<%@page import="com.eaf.orig.ulo.control.util.SaleInfoUtil"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.SaleInfoDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.control.util.PersonalInfoUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>

<script type="text/javascript" src="orig/ulo/subform/increase/js/BankStaffSubForm.js"></script>
<%
	String subformId = "INCREASE_BANK_STAFF_SUBFORM";
// 	String CACHE_SALE_INFO = SystemConstant.getConstant("CACHE_SALE_INFO");
	Logger logger = Logger.getLogger(this.getClass());
	String SALE_TYPE_NORMAL_SALES = SystemConstant.getConstant("SALE_TYPE_NORMAL_SALES");
	SaleInfoDataM saleInfo =  ORIGForm.getObjectForm().getSaleInfoByType(SALE_TYPE_NORMAL_SALES);
	if(Util.empty(saleInfo)){
		saleInfo = new SaleInfoDataM();
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
	String SEARCH_SALE_ID = SystemConstant.getConstant("SEARCH_SALE_ID");
	String SEARCH_SALE_NAME = SystemConstant.getConstant("SEARCH_SALE_NAME");
 %>
<div class="panel panel-default">
<div class="panel-heading"><%=LabelUtil.getText(request, "BRANCH_STAFF_INFO")%></div>
<div class="panel-body"> 
<div class="row form-horizontal">
	<div class="col-sm-6">
<!-- 		<div class="form-group"> -->
<%-- 			<%=HtmlUtil.getSubFormLabel(request,subformId,"BRANCH_STAFF_NAME","BRANCH_STAFF_NAME","col-sm-4 col-md-5 control-label")%>			 --%>
<%-- 			<%=HtmlUtil.search("BRANCH_STAFF_NAME", "BRANCH_STAFF_NAME_"+PERSONAL_TYPE, "BRANCH_STAFF_NAME", SEARCH_SALE_NAME --%>
<%-- 			, saleInfo.getSalesId(), "","","ALL_ALL_ALL", "", "col-sm-8 col-md-7", formUtil)%>		 --%>
<!-- 		</div> -->
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request, subformId, "BRANCH_STAFF_NAME", "BRANCH_STAFF_CODE", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBoxOnlyNum("BRANCH_STAFF_NAME","","BRANCH_STAFF_NAME_"+PERSONAL_TYPE,"BRANCH_STAFF_NAME",
				saleInfo.getSalesId(),"","50","","col-sm-8 col-md-7", applicationGroup,formUtil)%>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"SALES_BRANCH_CODE","SALES_BRANCH_CODE","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBox("SALES_BRANCH_CODE","SALES_BRANCH_CODE_"+PERSONAL_TYPE,"SALES_BRANCH_CODE",SaleInfoUtil.showBranchDetp(saleInfo),"","8","","col-sm-8 col-md-7",formUtil)%>
		</div>
	</div>
<!-- 	<div class="clearfix"></div> -->
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request, subformId, "BRANCH_STAFF_INFO_NAME", "BRANCH_STAFF_INFO_NAME", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBox("BRANCH_STAFF_INFO_NAME","","BRANCH_STAFF_INFO_NAME_"+PERSONAL_TYPE,"BRANCH_STAFF_INFO_NAME",
				saleInfo.getSalesName(),"","50","","col-sm-8 col-md-7", applicationGroup,formUtil)%>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"BRANCH_PHONE_NO","BRANCH_PHONE_NO","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBoxTel("BRANCH_PHONE_NO", "BRANCH_PHONE_NO_"+PERSONAL_TYPE, "BRANCH_PHONE_NO", saleInfo.getSalesPhoneNo(), "", "", "col-sm-8 col-md-7", formUtil)%>
		</div>
	</div>
	<div class="clearfix"></div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"SALES_COMMENT","SALES_COMMENT","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBox("SALES_COMMENT","SALES_COMMENT_"+PERSONAL_TYPE,"SALES_COMMENT",
				saleInfo.getSalesComment(),"","250","","col-sm-8 col-md-7",formUtil)%>
		</div>
	</div>
</div>

</div>
</div>

