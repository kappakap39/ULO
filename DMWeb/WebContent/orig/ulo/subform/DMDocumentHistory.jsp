<%@page import="com.eaf.orig.ulo.app.view.util.dih.model.DIHQueryResult"%>
<%@page import="com.eaf.core.ulo.common.model.ResponseData"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.Collection"%>
<%@page import="com.eaf.orig.ulo.app.view.util.dih.DIHProxy"%>
<%@page import="com.eaf.orig.ulo.app.view.util.dih.model.KbankSaleInfoDataM"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.orig.ulo.model.dm.HistoryDataM"%>
<%@page import="com.eaf.orig.ulo.model.dm.DocumentManagementDataM"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.DMFormHandler"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>

<script type="text/javascript" src="orig/ulo/subform/js/DMDocumentHistory.js"></script>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="DMForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.DMFormHandler"/>

<%
	DocumentManagementDataM dmManageDataM = DMFormHandler.getObjectForm(request);
	ArrayList<HistoryDataM> historyLog  = dmManageDataM.getHistoryLog();
	String CACHE_SALE_INFO = SystemConstant.getConstant("CACHE_SALE_INFO");
%>
<div class="panel panel-default">
	<table class="table table-striped table-hover">
		<tr>
			<th><%=LabelUtil.getText(request,"DM_HISTORY_DATE")%></th>
			<th><%=LabelUtil.getText(request,"DM_HISTORY_ACTION")%></th>
			<th><%=LabelUtil.getText(request,"DM_HISTORY_STATUS")%></th>
			<th><%=LabelUtil.getText(request,"DM_HISTORY_MANAGEMENT_BY")%></th>
			<th><%=LabelUtil.getText(request,"DM_HISTORY_REQ_USER")%></th>
			<th><%=LabelUtil.getText(request,"DM_HISTORY_PHONE_NO")%></th>
			<th><%=LabelUtil.getText(request,"DM_HISTORY_DUE_DATE")%></th>
			<th><%=LabelUtil.getText(request,"DM_HISTORY_REMARK")%></th>
		</tr>
		<%if(!Util.empty(historyLog)){ 
		     Collections.sort(historyLog, new HistoryDataM());
			for(HistoryDataM history :historyLog){%>
			<tr>
				<td><%=FormatUtil.display(history.getActionDate(),FormatUtil.TH,FormatUtil.Format.ddMMyyyy)%>&nbsp;<%=FormatUtil.display(history.getActionDate(),FormatUtil.EN,FormatUtil.Format.HHMM)%></td>
				<td><%=FormatUtil.display(CacheControl.getName(SystemConstant.getConstant("FIELD_ID_DM_ACTION"),history.getAction()))%></td>
				<td><%=FormatUtil.display(CacheControl.getName(SystemConstant.getConstant("FIELD_ID_STATUS_DM"), history.getStatus()))%></td>
				<td><%=FormatUtil.display(history.getCreateBy())%></td>
				<%
				KbankSaleInfoDataM kbankSaleInfo = new KbankSaleInfoDataM();
				DIHQueryResult<KbankSaleInfoDataM> dihKbankSaleInfo= DIHProxy.getKbankSaleInfo(history.getRequestedUser());	
				if(ResponseData.SUCCESS.equals(dihKbankSaleInfo.getStatusCode())){
					kbankSaleInfo =dihKbankSaleInfo.getResult();
				}	
				 %>
				<td><%=FormatUtil.display(kbankSaleInfo.getSaleName())%></td>
				<td><%=FormatUtil.display(history.getPhoneNo())%></td>
				<td><%=FormatUtil.display(history.getDueDate(),FormatUtil.TH)%></td>
				<td><%=FormatUtil.display(history.getRemark())%></td>
			</tr>
			
		<%	}
		}else{ %>
			<tr>
	 			<td colspan="8" align="center"><%=LabelUtil.getText(request,"NO_RECORD_FOUND")%></td>
	 		</tr>
		<%} %>
	</table>
</div>