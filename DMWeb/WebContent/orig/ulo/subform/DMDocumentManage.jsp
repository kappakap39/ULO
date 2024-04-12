<%@page import="com.eaf.orig.ulo.dm.util.DocumentManageUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.orig.ulo.model.dm.HistoryDataM"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConfig"%>
<%@page import="com.eaf.orig.profile.model.UserDetailM"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.DMFormHandler"%>
<%@page import="com.eaf.orig.ulo.model.dm.DocumentManagementDataM"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.date.ApplicationDate"%>
<%@ page import="org.apache.log4j.Logger"%>
<script type="text/javascript" src="orig/ulo/subform/js/DMDocumentManage.js"></script>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="DMForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.DMFormHandler"/>

<%
	Logger logger = Logger.getLogger(this.getClass());
	UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
	DocumentManagementDataM dmManageDataM = DMFormHandler.getObjectForm(request);
	
	logger.debug("employee login no ::"+userM.getUserNo());	
	if(Util.empty(dmManageDataM)){
		dmManageDataM = new DocumentManagementDataM();
	}
	
	HistoryDataM history = dmManageDataM.getHistory();
	if(Util.empty(history)){
		history = new HistoryDataM();
	}
	
	HistoryDataM historyLastAction = dmManageDataM.getLastActionHistory();
	if(Util.empty(historyLastAction)){
		historyLastAction = new HistoryDataM();
	}
	logger.debug("##########logger dm flag#######"+userM.getDmWithdrawalAuth());
	logger.debug("##########logger user name #######"+userM.getUserName());
	logger.debug("##########logger user name #######"+dmManageDataM.getStatus());
 
	
	String BR_MODE = HtmlUtil.VIEW;
	String WD_MODE = HtmlUtil.VIEW;
	String REQ_MODE= HtmlUtil.VIEW;
	String RT_MODE = HtmlUtil.VIEW;
	
	if(MConstant.DM_STATUS.AVAILABLE.equals(dmManageDataM.getStatus())){
		BR_MODE = HtmlUtil.EDIT;
		if(DocumentManageUtil.isCustomerRequestDocument(dmManageDataM)){
			REQ_MODE= HtmlUtil.EDIT;	
		}
	}else if(MConstant.DM_STATUS.BORROWED.equals(dmManageDataM.getStatus())){
		RT_MODE = HtmlUtil.EDIT;
	}
	String SEARCH_SALE_ID = SystemConstant.getConstant("SEARCH_SALE_ID");
	
%>
<div class="panel panel-default">
	<div class="panel-body">
		<div class="row form-horizontal">
			<div class="col-sm-12">
				<div class="form-group" id="div-doc-management-group">
					<%=HtmlUtil.radio("DM_DOC_MANAGEMENT", "", "", MConstant.DM_MANAGEMENT_ACTION.BORROW_ACTION,BR_MODE,HtmlUtil.elementTagId("DM_DOC_MANAGEMENT", "BORROW"), "DM_BORROW_DESC", "col-sm-3 col-md-2 control-label",request)%>
					<%=HtmlUtil.radio("DM_DOC_MANAGEMENT", "", "", MConstant.DM_MANAGEMENT_ACTION.WITHDRAW_ACTION,WD_MODE,HtmlUtil.elementTagId("DM_DOC_MANAGEMENT", "WITHDRAW"), "DM_WITHDRAW_DESC", "col-sm-3 col-md-2 control-label",request)%>
					<%=HtmlUtil.radio("DM_DOC_MANAGEMENT", "", "", MConstant.DM_MANAGEMENT_ACTION.CUSTOMER_REQ_ACTION,REQ_MODE,HtmlUtil.elementTagId("DM_DOC_MANAGEMENT", "CUSTOMER_REQ"), "DM_CUSTOMER_REQ_DOC", "col-sm-3 col-md-2 control-label",request)%>
					<%=HtmlUtil.radio("DM_DOC_MANAGEMENT", "", "", MConstant.DM_MANAGEMENT_ACTION.RETURN_ACTION,RT_MODE,HtmlUtil.elementTagId("DM_DOC_MANAGEMENT", "RETURN_DOC"), "DM_RETURN_DOC_DESC", "col-sm-3 col-md-2 control-label",request)%>
				</div>
			</div>
			<div class="clearfix"></div>
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getMandatoryLabel(request, "DM_REQUESTED_USER", "col-sm-4 col-md-5 control-label")%>
					<%=HtmlUtil.search("DM_REQUESTED_USER", "", SEARCH_SALE_ID, "", "","", "", HtmlUtil.EDIT, HtmlUtil.elementTagId("DM_REQUESTED_USER"),"col-sm-8 col-md-7", request) %>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getFieldLabel(request, "DM_DEPARTMENT", "col-sm-4 col-md-5 control-label")%>
					<%=HtmlUtil.textBox("DM_DEPARTMENT", "",null, "", "", HtmlUtil.VIEW, HtmlUtil.elementTagId("DM_DEPARTMENT"), "col-sm-8 col-md-7", request) %>
				 
				</div>
			</div>
			<div class="clearfix"></div>
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getFieldLabel(request, "DM_OFFICE_PHONE_NO", "col-sm-4 col-md-5 control-label")%>
					<%=HtmlUtil.textBoxTel("DM_OFFICE_PHONE_NO", "", "", "", HtmlUtil.EDIT, HtmlUtil.elementTagId("DM_OFFICE_PHONE_NO"), "col-sm-8 col-md-7", request)%>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getFieldLabel(request, "DM_MOBILE_NO", "col-sm-4 col-md-5 control-label")%>
					<%=HtmlUtil.textBoxMobile("DM_MOBILE_NO", "", "", "", HtmlUtil.EDIT, HtmlUtil.elementTagId("DM_MOBILE_NO"), "col-sm-8 col-md-7", request)%>
				</div>
			</div>
			<div class="clearfix"></div>
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getMandatoryLabel(request, "DM_ACTION_DATE", "col-sm-4 col-md-5 control-label")%>
					<%=HtmlUtil.calendar("DM_ACTION_DATE", "", "DM_ACTION_DATE", null, "", HtmlUtil.EDIT, HtmlUtil.elementTagId("DM_ACTION_DATE"), HtmlUtil.TH, "col-sm-8 col-md-7", request)%>					
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getFieldLabel(request, "DM_DUE_DATE", "col-sm-4 col-md-5 control-label")%>
					<%=HtmlUtil.calendar("DM_DUE_DATE", "", "DM_DUE_DATE", null, "", HtmlUtil.EDIT, HtmlUtil.elementTagId("DM_DUE_DATE"), HtmlUtil.TH, "col-sm-8 col-md-7", request)%>
				</div>
			</div>
			<div class="clearfix"></div>
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getFieldLabel(request, "DM_REMARK", "col-sm-4 col-md-5 control-label")%>
					<%=HtmlUtil.textarea("DM_REMARK", "", "", "5", "100", "1000", HtmlUtil.EDIT, HtmlUtil.elementTagId("DM_REMARK"), "col-sm-8 col-md-7", request) %>
				</div>
			</div>
		</div>
	</div>
</div>
<%=HtmlUtil.hidden("WH_BORROW_DOC_DAY",SystemConfig.getGeneralParam("WH_BORROW_DOC_DAY"))%>
<%=HtmlUtil.hidden("RETURN_DUEDATE_TEMP", FormatUtil.display(historyLastAction.getDueDate(),FormatUtil.TH,FormatUtil.Format.ddMMyyyy)) %>
<%=HtmlUtil.hidden("DM_REQUESTED_USER_DESC", "") %>
<%=HtmlUtil.hidden("NOW_DATE", FormatUtil.display(ApplicationDate.getDate(),FormatUtil.TH,FormatUtil.Format.ddMMyyyy)) %>

