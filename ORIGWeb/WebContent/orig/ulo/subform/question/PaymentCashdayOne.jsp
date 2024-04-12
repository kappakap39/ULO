<%@page import="com.eaf.orig.ulo.control.util.ApplicationUtil"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.ListBoxControl"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.CashTransferDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="EntityForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler" />
<script type="text/javascript" src="/orig/ulo/product/js/CashDay1SubForm.js"></script>

<%
	String subformId = "CASH_DAY_ONE";
	Logger logger = Logger.getLogger(this.getClass());
	String PRODUCT_K_EXPRESS_CASH = SystemConstant.getConstant("PRODUCT_K_EXPRESS_CASH");
	ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)EntityForm.getObjectForm();
	ApplicationDataM applicationItem = applicationGroup.getApplicationProduct(PRODUCT_K_EXPRESS_CASH);
	if(null == applicationItem){
		applicationItem = new ApplicationDataM();
	}
	String personalId = request.getParameter("PERSONAL_ID");
	PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfoId(personalId);
	if(null == personalInfo){
		personalInfo = new PersonalInfoDataM();
	}
	String businessClassId = "ALL_ALL_ALL";
	String FIELD_ID_CASH_TRANSFER_TYPE = SystemConstant.getConstant("FIELD_ID_CASH_TRANSFER_TYPE");
	String FIELD_ID_PERCENT_TRANSFER = SystemConstant.getConstant("FIELD_ID_PERCENT_TRANSFER");
	
	String []cashTransFerType = ApplicationUtil.getCashTranferTypes();
	logger.debug("CASH_TRANSFER >> "+cashTransFerType);
	CashTransferDataM cashTransfer = applicationItem.getCashTransfer(cashTransFerType);	
	if(null == cashTransfer){
		cashTransfer = new CashTransferDataM();
	}
	
	int PERSONAL_SEQ = personalInfo.getSeq();
	String displayMode = HtmlUtil.EDIT;
// 	String TAG_SMART_DATA_PERSONAL = FormatUtil.getSmartDataEntryId(personalInfo.getPersonalType(),PERSONAL_SEQ);
	String cashTranserElementId = FormatUtil.getCashTransferElementId(personalInfo,PRODUCT_K_EXPRESS_CASH,"CASH_TRANSFER");
	String SUFFIX_CASH_TRANSFER = PRODUCT_K_EXPRESS_CASH+"_CASH_TRANSFER";
		
	FormUtil formUtil = new FormUtil(subformId,request);
%>
<section id="SECTION_CASHDAY1_<%=personalId%>" class="warpSubFormTemplate SECTION_CASHDAY1_<%=personalId%>">
	<div class="panel panel-default">
		 <div class="panel-body">
			<div class="row form-horizontal">
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.getSubFormLabel(request,subformId,"CASH_TRANSFER_TYPE_"+SUFFIX_CASH_TRANSFER,"CASH_TRANSFER_TYPE","col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.dropdown("CASH_TRANSFER_TYPE",SUFFIX_CASH_TRANSFER, "CASH_TRANSFER_TYPE_"+cashTranserElementId, "CASH_TRANSFER_TYPE", "", 
							cashTransfer.getCashTransferType(), "",FIELD_ID_CASH_TRANSFER_TYPE,businessClassId, "", "col-sm-8 col-md-7", formUtil)%>
					</div>
				</div>
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.getSubFormLabel(request,subformId,"PERCENT_TRANSFER_"+SUFFIX_CASH_TRANSFER,"PERCENT_TRANSFER","col-sm-4 col-md-5 control-label")%>
						
						<%=HtmlUtil.dropdown("PERCENT_TRANSFER",SUFFIX_CASH_TRANSFER, "PERCENT_TRANSFER_"+cashTranserElementId, "PERCENT_TRANSFER", "", 
							FormatUtil.display(cashTransfer.getPercentTransfer()), "",FIELD_ID_PERCENT_TRANSFER,businessClassId, "", "col-sm-8 col-md-7", formUtil)%>
					</div>
				</div>
				<div class="clearfix"></div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.getSubFormLabel(request,subformId,"ACCOUNT_NO_"+SUFFIX_CASH_TRANSFER,"ACCOUNT_NO","col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.textBoxAccountNo("ACCOUNT_NO", PRODUCT_K_EXPRESS_CASH+"_CASH_TRANSFER","ACCOUNT_NO_"+cashTranserElementId,"ACCOUNT_NO",
						cashTransfer.getTransferAccount(),"","","col-sm-8 col-md-7",formUtil)%>
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.hidden("COMPLETE_DATA_"+SUFFIX_CASH_TRANSFER, cashTransfer.getCompleteData()) %>
						<%=HtmlUtil.getSubFormLabel(request,subformId,"ACC_NAME_"+SUFFIX_CASH_TRANSFER,"ACC_NAME","col-sm-4 col-md-5 control-label")%>
						<%=FormatUtil.display(cashTransfer.getAccountName())%>
					
					</div>
				</div>
				<div class="clearfix"></div>
			</div>
		</div>
	</div>
</section>
