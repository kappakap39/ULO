<%@page import="com.eaf.core.ulo.common.model.ResponseData"%>
<%@page import="com.eaf.orig.ulo.app.view.util.dih.model.DIHQueryResult"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.Date"%>
<%@page import="com.eaf.orig.ulo.app.view.util.dih.DIHProxy"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.orig.ulo.model.dm.DMCardInfoDataM"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.DMFormHandler"%>
<%@page import="com.eaf.orig.ulo.model.dm.DocumentManagementDataM"%>
<%@page import="com.eaf.orig.ulo.model.dm.DMApplicationInfoDataM"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<script type="text/javascript" src="orig/ulo/subform/js/DMApplicationInfoSubForm.js"></script>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="DMForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.DMFormHandler"/>
 <%
 	String subformId = "DM_STORE_DOCUMENT_FORM";
 	String KCC = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
 	String KEC = SystemConstant.getConstant("PRODUCT_K_EXPRESS_CASH");
 	String KPL = SystemConstant.getConstant("PRODUCT_K_PERSONAL_LOAN");
	Logger logger = Logger.getLogger(this.getClass());
	DocumentManagementDataM dmManageDataM = DMFormHandler.getObjectForm(request);
	if(Util.empty(dmManageDataM)){
		dmManageDataM = new DocumentManagementDataM();
	}
 	ArrayList<DMApplicationInfoDataM> dmApplicationInfoList  = dmManageDataM.getApplicationInfos();
	if(Util.empty(dmApplicationInfoList)){
		dmApplicationInfoList = new ArrayList<DMApplicationInfoDataM>();
	}
	String CACHE_BRANCH_INFO = SystemConstant.getConstant("CACHE_BRANCH_INFO");
	String branchName="";
	DIHQueryResult<String> dihBranchName = DIHProxy.getKbankBranchData(dmManageDataM.getBranchId(),"TH_CNTR_NM");	
	if(ResponseData.SUCCESS.equals(dihBranchName.getStatusCode())){
		branchName = dihBranchName.getResult();
	}
%>
<div class="panel panel-default">
<div class="panel-body">
	<div class="row form-horizontal">
		<div class="col-sm-6">
			 <div class="form-group">
				<%=HtmlUtil.getFieldLabel(request,"DM_APPLICATION_NO","col-sm-4 col-md-5 control-label")%>
				<div class="col-sm-8 col-md-7"><%=FormatUtil.display(dmManageDataM.getRefNo2()) %></div>
			</div>
		</div>
		<div class="col-sm-6">
			<div class="form-group">
				<%=HtmlUtil.getFieldLabel(request,"DM_UPDATE_DATE","col-sm-4 col-md-5 control-label")%>
				<div class="col-sm-8 col-md-7"><%=FormatUtil.display(dmManageDataM.getUpdateDate(),FormatUtil.EN,"dd/mm/yyyy")%></div>
			</div> 
		</div>
		 <div class="col-sm-6">
			<div class="form-group">
				<%=HtmlUtil.getFieldLabel(request,"DM_BRANCH_NO","col-sm-4 col-md-5 control-label")%>
				<div class="col-sm-8 col-md-7"><%=FormatUtil.display(branchName)%></div>
			</div> 
		</div>
		<div class="col-sm-6">
			<div class="form-group">
				<%=HtmlUtil.getFieldLabel(request,"DM_BRANCH_TEL","col-sm-4 col-md-5 control-label")%>
				<div class="col-sm-8 col-md-7"><%=FormatUtil.display("")%></div>
			</div>
		</div> 
	</div>
</div>
</div>
<div class="panel panel-default">
<table class="table table-striped table-hover">
<%
if(!Util.empty(dmApplicationInfoList)){
	String product="";
for(DMApplicationInfoDataM dmAppInfo : dmApplicationInfoList){
	ArrayList<DMCardInfoDataM> dmCardInfoList  =  dmAppInfo.getCardInfos();	
	String productName = dmAppInfo.getProduct();
		   productName = Util.empty(productName)?"":productName;
	String productDesc="";
	if(KCC.equals(productName)){
		productDesc =LabelUtil.getText(request, "DM_CARD_CREDIT_DESC");
	}else if(KEC.equals(productName)){
		productDesc =LabelUtil.getText(request, "DM_CARD_EXPRESS_DESC");
	}else if(KPL.equals(productName)){
		productDesc =LabelUtil.getText(request, "DM_CARD_PERSONAL_DESC");
	}
	
	String finalDecision = FormatUtil.display(dmAppInfo.getFinalAppDecision());	
%>
	<%if(!product.equals(productName)){%>
	<tr>
		<th colspan="4"><%=productDesc%></th>
	</tr>
	<%} %>
	<%		
	  	if(!Util.empty(dmCardInfoList)){
			for(DMCardInfoDataM dmCardInfo :dmCardInfoList){			
	%>
	<tr>
		<td><%=CacheControl.getName(SystemConstant.getConstant("DM_CACHE_NAME_CARDTYPE"), dmCardInfo.getCardType())%></td>
		<td style="font-weight: bold;"><%=CacheControl.getName(SystemConstant.getConstant("FIELD_ID_DM_PERSONAL_TYPE"), dmCardInfo.getPersonalType())%></td>
		<td><%=FormatUtil.display(dmCardInfo.getThFullName() +" - "+dmCardInfo.getIdNo())%></td>
		<% if(finalDecision.equals("Approve")) {%>
			<td><font color=green><%=finalDecision%></font></td>
		<% }else{ %>
			<td><font color=red><%=finalDecision%></font></td>
		<% } %>
	</tr>
	<%}
	}
	product =productName;
 }%>
<%}else{%>
	<tr>
		<td colspan="4" align="center"><%=LabelUtil.getText(request,"NO_RECORD_FOUND")%></td>
	</tr>
<%}%>
</table>
</div>
