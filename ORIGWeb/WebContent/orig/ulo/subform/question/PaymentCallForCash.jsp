<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.ListBoxControl"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.CashTransferDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="EntityForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler" />
<script type="text/javascript" src="/orig/ulo/product/js/CashDay1SubForm.js"></script>
<%
	String subformId = "CALL_FOR_CASH";
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
	
	String businessClassId= "ALL_ALL_ALL";
	String CALL_FOR_CASH = SystemConstant.getConstant("CALL_FOR_CASH");	
	String	FIELD_ID_CASH_TRANSFER_TYPE =SystemConstant.getConstant("FIELD_ID_CASH_TRANSFER_TYPE");
	String FIELD_ID_PERCENT_TRANSFER=SystemConstant.getConstant("FIELD_ID_PERCENT_TRANSFER");
	
	CashTransferDataM callForCash = applicationItem.getCashTransfer(CALL_FOR_CASH);
	if( Util.empty(callForCash)){
		callForCash = new CashTransferDataM();
	}
	
	int PERSONAL_SEQ = personalInfo.getSeq();
	String displayMode = HtmlUtil.EDIT;
// 	String TAG_SMART_DATA_PERSONAL = FormatUtil.getSmartDataEntryId(personalInfo.getPersonalType(),PERSONAL_SEQ);
		
	String cashTranserElementId = FormatUtil.getCashTransferElementId(personalInfo,PRODUCT_K_EXPRESS_CASH,"CASH_TRANSFER");
	String callForCashElementId = FormatUtil.getCashTransferElementId(personalInfo,PRODUCT_K_EXPRESS_CASH,CALL_FOR_CASH);	
		
	String SUFFIX_CASH_TRANSFER = PRODUCT_K_EXPRESS_CASH+"_CASH_TRANSFER";
	String SUFFIX_CALL_FOR_CASH = PRODUCT_K_EXPRESS_CASH+"_"+CALL_FOR_CASH;
	FormUtil formUtil = new FormUtil(subformId,request);
%>
<section id="SECTION_CALL4CASH_<%=personalId%>" class="warpSubFormTemplate SECTION_CALL4CASH_<%=personalId%>">
	<div class="panel panel-default">
		 <div class="panel-body">
			<div class="row form-horizontal">
				<div class="clearfix"></div>
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.checkBox("CASH_1_HOUR", SUFFIX_CALL_FOR_CASH, "CASH_1_HOUR_"+callForCashElementId, "CASH_1_HOUR", callForCash.getCallForCashFlag(), "", MConstant.FLAG.YES, "", "", "col-sm-1 col-md-1", formUtil) %>
					<%=HtmlUtil.getText(request, "CALL_FOR_CASH")%>
					</div>
				</div>
				<div class="clearfix"></div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.getSubFormLabel(request,subformId,"ACCOUNT_NO_"+SUFFIX_CALL_FOR_CASH,"ACCOUNT_NO","col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.textBoxAccountNo("ACCOUNT_NO", SUFFIX_CALL_FOR_CASH,"ACCOUNT_NO_"+callForCashElementId,"ACCOUNT_NO_CASH_1_HOUR",
						callForCash.getTransferAccount(),"","","col-sm-8 col-md-7",formUtil)%>
						
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.hidden("COMPLETE_DATA_"+SUFFIX_CALL_FOR_CASH, callForCash.getCompleteData()) %>
						<%=HtmlUtil.getSubFormLabel(request,subformId,"ACC_NAME_"+SUFFIX_CALL_FOR_CASH,"ACC_NAME","col-sm-4 col-md-5 control-label")%>
						<%=FormatUtil.display(callForCash.getAccountName())%>
					</div>
				</div>
				<div class="clearfix"></div> 
			</div>
		</div>
	</div>
</section>
