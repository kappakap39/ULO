<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.core.ulo.common.properties.ListBoxControl"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.CashTransferDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.LoanDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<script type="text/javascript" src="/orig/ulo/subform/increase/js/CashTranferSubForm.js"></script>
<%
	String subformId = "INCREASE_CASH_TRANFER_SUBFORM";
	Logger logger = Logger.getLogger(this.getClass());
	logger.debug("subformId >> "+subformId);
	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	ApplicationDataM applicationItem = ORIGForm.getObjectForm().getApplication(0);
	if(null == applicationItem){
		applicationItem = new ApplicationDataM();
	}
	String businessClassId= "ALL_ALL_ALL";
	String CALL_FOR_CASH = SystemConstant.getConstant("CALL_FOR_CASH");	
	CashTransferDataM cashTransfer = applicationItem.getCashTransfer(CALL_FOR_CASH);
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
// 	String TAG_SMART_DATA_PERSONAL = FormatUtil.getSmartDataEntryId(personalInfo.getPersonalType(),PERSONAL_SEQ);
	String cashTranserElementId = FormatUtil.getCashTransferElementId(personalInfo,CALL_FOR_CASH);
	String SUFFIX_CALL_FOR_CASH = CALL_FOR_CASH;		
	FormUtil formUtil = new FormUtil(subformId,request);
%>

<div class="panel panel-default">
	 <div class="panel-body">
		<div class="row form-horizontal">
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.checkBox("CASH_1_HOUR", SUFFIX_CALL_FOR_CASH, "CASH_1_HOUR_"+cashTranserElementId, "CASH_1_HOUR_"+SUFFIX_CALL_FOR_CASH, 
						cashTransfer.getCashTransferType(), "", MConstant.FLAG.YES, "", "CALL_FOR_CASH", "col-sm-8 col-md-7", formUtil) %>
				</div>
			</div>
			<div class="clearfix"></div>				
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request,subformId,"ACCOUNT_NO_"+SUFFIX_CALL_FOR_CASH,"ACCOUNT_NO","col-sm-4 col-md-5 control-label")%>
					<%=HtmlUtil.textBoxAccountNo("ACCOUNT_NO", SUFFIX_CALL_FOR_CASH,"ACCOUNT_NO_"+cashTranserElementId,"ACCOUNT_NO_"+SUFFIX_CALL_FOR_CASH,
					cashTransfer.getTransferAccount(),"","","col-sm-8 col-md-7",formUtil)%>						
				</div>
			</div>				
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request,subformId,"ACC_NAME_"+SUFFIX_CALL_FOR_CASH,"ACC_NAME","col-sm-4 col-md-5 control-label")%>
					<%=FormatUtil.display(cashTransfer.getAccountName())%>
				</div>
			</div>
			<div class="clearfix"></div>
		</div>
	</div>
</div>

