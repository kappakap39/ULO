<%@page import="com.eaf.core.ulo.common.properties.FormControl"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.CashTransferDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<script type="text/javascript" src="orig/ulo/subform/increase/js/CashTransferSubForm.js"></script>
<%
	String subformId = "INCREASE_CASH_TRANSFER_SUBFORM";
	Logger logger = Logger.getLogger(this.getClass());
	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");	
	String PRODUCT_K_EXPRESS_CASH = SystemConstant.getConstant("PRODUCT_K_EXPRESS_CASH");
	ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)FormControl.getObjectForm(request);
	ApplicationDataM applicationM = applicationGroup.getApplicationProduct(PRODUCT_K_EXPRESS_CASH);
	if(null == applicationM){
		applicationM = new ApplicationDataM();
	}
	String CALL_FOR_CASH = SystemConstant.getConstant("CALL_FOR_CASH");	
	CashTransferDataM cashTransfer = applicationM.getCashTransfer(CALL_FOR_CASH);
	String CALL_FOR_CASH_FLAG = MConstant.FLAG.YES;
	if(null == cashTransfer){
		cashTransfer = new CashTransferDataM();
		CALL_FOR_CASH_FLAG = MConstant.FLAG.NO;
	}

	PersonalInfoDataM personalInfo = ORIGForm.getObjectForm().getPersonalInfo(PERSONAL_TYPE);
	if(null == personalInfo){
		personalInfo = new PersonalInfoDataM();
	}
	int PERSONAL_SEQ = personalInfo.getSeq();
	String displayMode = HtmlUtil.EDIT;
// 	String TAG_SMART_DATA_PERSONAL = FormatUtil.getSmartDataEntryId(PERSONAL_TYPE,PERSONAL_SEQ);
	String cashTranserElementId = FormatUtil.getCashTransferElementId(personalInfo,PRODUCT_K_EXPRESS_CASH,CALL_FOR_CASH);
	String SUFFIX_CALL_FOR_CASH = PRODUCT_K_EXPRESS_CASH+"_"+CALL_FOR_CASH;
	
	FormUtil formUtil = new FormUtil(subformId,request);
%>
<div class="panel panel-default">
	<div class="panel-body"> 
		<div class="row form-horizontal">
			<div class="col-md-12">
				<div class="form-group">
				<%=HtmlUtil.checkBoxInline("CASH_1_HOUR", SUFFIX_CALL_FOR_CASH, "CASH_1_HOUR_"+cashTranserElementId, "CASH_1_HOUR_"+SUFFIX_CALL_FOR_CASH,
					CALL_FOR_CASH_FLAG,"", MConstant.FLAG.YES, "", "CALL_FOR_CASH", "col-sm-8 col-md-7",applicationM, formUtil) %>
				</div>
			</div>
			<div class="clearfix"></div>
			<div class="col-md-6">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request,subformId,"ACCOUNT_NO_"+SUFFIX_CALL_FOR_CASH,"ACCOUNT_NO","col-sm-4 col-md-5 control-label")%>
					<%=HtmlUtil.textBoxAccountNo("ACCOUNT_NO", SUFFIX_CALL_FOR_CASH,"ACCOUNT_NO_"+cashTranserElementId,"ACCOUNT_NO_"+SUFFIX_CALL_FOR_CASH,
						cashTransfer.getTransferAccount(),"","","col-sm-8 col-md-7",applicationM,formUtil)%>	
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.hidden("COMPLETE_DATA_"+SUFFIX_CALL_FOR_CASH, cashTransfer.getCompleteData()) %>
					<%=HtmlUtil.getSubFormLabel(request,subformId,"ACC_NAME_"+SUFFIX_CALL_FOR_CASH,"ACC_NAME","col-sm-4 col-md-5 control-label")%>
					<div id="ACCOUNT_NAME_<%=SUFFIX_CALL_FOR_CASH %>"><%=FormatUtil.display(cashTransfer.getAccountName())%></div>
				</div>
			</div>
		</div>
	</div>
</div>