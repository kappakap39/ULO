<%@page import="com.eaf.orig.ulo.control.util.ApplicationUtil"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.FormControl"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.CashTransferDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>

<script type="text/javascript" src="orig/ulo/product/js/CashDay1SubForm.js"></script>
<%
	String subformId = "KEC_CASH_DAY1_SUBFORM";
	Logger logger = Logger.getLogger(this.getClass());
	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");	
	String PRODUCT_K_EXPRESS_CASH = SystemConstant.getConstant("PRODUCT_K_EXPRESS_CASH");
	ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)FormControl.getObjectForm(request);
	ApplicationDataM applicationItem = applicationGroup.filterApplicationProductLifeCycle(PRODUCT_K_EXPRESS_CASH);
	if(null == applicationItem){
		applicationItem = new ApplicationDataM();
	}
	/* String businessClassId= applicationItem.getBusinessClassId(); */
	String businessClassId= "ALL_ALL_ALL";
// 	String CASH_DAY_1 = SystemConstant.getConstant("CASH_DAY_1");	
// 	String CASH_DAY_5 = SystemConstant.getConstant("CASH_DAY_5");
// 	String CASH_1_HOUR = SystemConstant.getConstant("CASH_1_HOUR");
	String CALL_FOR_CASH = SystemConstant.getConstant("CALL_FOR_CASH");
	String []cashTransFerType = ApplicationUtil.getCashTranferTypes();
	String FIELD_ID_CASH_TRANSFER_TYPE = SystemConstant.getConstant("FIELD_ID_CASH_TRANSFER_TYPE");
	String FIELD_ID_PERCENT_TRANSFER = SystemConstant.getConstant("FIELD_ID_PERCENT_TRANSFER");
	
	logger.debug("CASH_TRANSFER >> "+cashTransFerType);
	logger.debug("CALL_FOR_CASH >> "+CALL_FOR_CASH);
	
	CashTransferDataM cashTransfer = applicationItem.getCashTransfer(cashTransFerType);	
	System.out.println("cashTransfer = " + cashTransfer);
	
	CashTransferDataM callForCash = applicationItem.getCashTransfer(CALL_FOR_CASH);
	String CALL_FOR_CASH_FLAG = MConstant.FLAG.YES;

	if(null == callForCash && null == cashTransfer){
		CALL_FOR_CASH_FLAG = MConstant.FLAG.NO;
	}
	
	if(null == callForCash){
		callForCash = new CashTransferDataM();
	}
	if(null == cashTransfer){
		cashTransfer = new CashTransferDataM();
	}
	PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
	if(null == personalInfo){
		personalInfo = new PersonalInfoDataM();
	}
	int PERSONAL_SEQ = personalInfo.getSeq();
	String displayMode = HtmlUtil.EDIT;
	
// 	String TAG_SMART_DATA_PERSONAL = FormatUtil.getSmartDataEntryId(personalInfo.getPersonalType(),PERSONAL_SEQ);
	
	String cashTranserElementId = FormatUtil.getCashTransferElementId(personalInfo,PRODUCT_K_EXPRESS_CASH,"CASH_TRANSFER");
	String callForCashElementId = FormatUtil.getCashTransferElementId(personalInfo,PRODUCT_K_EXPRESS_CASH,CALL_FOR_CASH);
	
	String SUFFIX_CASH_TRANSFER = PRODUCT_K_EXPRESS_CASH+"_CASH_TRANSFER";
	String SUFFIX_CALL_FOR_CASH = PRODUCT_K_EXPRESS_CASH+"_"+CALL_FOR_CASH;
	
	FormUtil formUtil = new FormUtil("PRODUCT_FORM_KEC", subformId,request);
	
%>

	<div class="panel panel-default">
		 <div class="panel-body">
			<div class="row form-horizontal">
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.getSubFormLabel(request,subformId,"CASH_TRANSFER_TYPE","CASH_TRANSFER_TYPE","col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.dropdown("CASH_TRANSFER_TYPE",SUFFIX_CASH_TRANSFER, "CASH_TRANSFER_TYPE_"+cashTranserElementId, "CASH_TRANSFER_TYPE", "", 
							cashTransfer.getCashTransferType(), "",FIELD_ID_CASH_TRANSFER_TYPE,businessClassId, "", "col-sm-8 col-md-7",applicationItem, formUtil)%>
					</div>
				</div>
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.getSubFormLabel(request,subformId,"PERCENT_TRANSFER_"+SUFFIX_CASH_TRANSFER,"PERCENT_TRANSFER","col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.dropdown("PERCENT_TRANSFER",SUFFIX_CASH_TRANSFER, "PERCENT_TRANSFER_"+cashTranserElementId, "PERCENT_TRANSFER", "", 
							FormatUtil.display(cashTransfer.getPercentTransfer()), "",FIELD_ID_PERCENT_TRANSFER,businessClassId, "", "col-sm-8 col-md-7",applicationItem, formUtil)%>
					</div>
				</div>
				<div class="clearfix"></div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.getSubFormLabel(request,subformId,"ACCOUNT_NO_"+SUFFIX_CASH_TRANSFER,"ACCOUNT_NO","col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.textBoxAccountNo("ACCOUNT_NO", PRODUCT_K_EXPRESS_CASH+"_CASH_TRANSFER","ACCOUNT_NO_"+cashTranserElementId,"ACCOUNT_NO",
							cashTransfer.getTransferAccount(),"","","col-sm-8 col-md-7",applicationItem,formUtil)%>
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.hidden("COMPLETE_DATA_"+SUFFIX_CASH_TRANSFER, cashTransfer.getCompleteData()) %>
						<%=HtmlUtil.getSubFormLabel(request,subformId,"ACC_NAME_"+SUFFIX_CASH_TRANSFER,"ACC_NAME","col-sm-4 col-md-5 control-label")%>
						<div class="col-sm-8 col-md-7"  id="ACCOUNT_NAME_<%=SUFFIX_CASH_TRANSFER %>"><%=FormatUtil.display(cashTransfer.getAccountName())%></div>
					
					</div>
				</div>
				<div class="clearfix"></div>
				<div class="col-sm-6">
					<div class="form-group">
					<% System.out.println("CALL_FOR_CASH_FLAG = " + CALL_FOR_CASH_FLAG); %>
						<%=HtmlUtil.checkBoxInline("CASH_1_HOUR", SUFFIX_CALL_FOR_CASH, "CASH_1_HOUR_"+callForCashElementId, "CASH_1_HOUR_"+SUFFIX_CALL_FOR_CASH, 
							CALL_FOR_CASH_FLAG, "", MConstant.FLAG.YES, "", "CALL_FOR_CASH", "col-sm-8 col-md-7",applicationItem, formUtil) %>
					</div>
				</div>
				<div class="clearfix"></div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.getSubFormLabel(request,subformId,"ACCOUNT_NO_"+SUFFIX_CALL_FOR_CASH,"ACCOUNT_NO","col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.textBoxAccountNo("ACCOUNT_NO", SUFFIX_CALL_FOR_CASH,"ACCOUNT_NO_"+callForCashElementId,"ACCOUNT_NO_"+SUFFIX_CALL_FOR_CASH,
							callForCash.getTransferAccount(),"","","col-sm-8 col-md-7",applicationItem,formUtil)%>
						
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.hidden("COMPLETE_DATA_"+SUFFIX_CALL_FOR_CASH, callForCash.getCompleteData()) %>
						<%=HtmlUtil.getSubFormLabel(request,subformId,"ACC_NAME_"+SUFFIX_CALL_FOR_CASH,"ACC_NAME","col-sm-4 col-md-5 control-label")%>
						<div class="col-sm-8 col-md-7" id="ACCOUNT_NAME_<%=SUFFIX_CALL_FOR_CASH %>"><%=FormatUtil.display(callForCash.getAccountName())%></div>
					</div>
				</div>
				<div class="clearfix"></div>
			</div>
		</div>
	</div>

