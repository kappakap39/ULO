<%@page import="com.eaf.orig.ulo.app.view.util.dih.model.DIHQueryResult"%>
<%@page import="com.eaf.orig.ulo.app.view.util.dih.DIHProxy"%>
<%@page contentType="text/html;charset=UTF-8"%>

<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.FixedGuaranteeDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>

<jsp:useBean id="ServiceForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler" />

<script type="text/javascript" src="orig/ulo/popup/income/js/CBS1215I01DataPopup.js"></script>
<%
	String displayMode = HtmlUtil.EDIT;
	String tagId = "";
	String FIRST_SEQ="1";
	ArrayList<FixedGuaranteeDataM> fixedList = (ArrayList<FixedGuaranteeDataM>)ServiceForm.getObjectForm();
	if(fixedList == null) {
		fixedList = new ArrayList<FixedGuaranteeDataM>();
	}
	String subFormId = "CBS1215I01_DATA_POPUP";
	
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	if (null == applicationGroup) {
		applicationGroup = new ApplicationGroupDataM();
	}
	String CACHE_BRANCH_INFO = SystemConstant.getConstant("CACHE_BRANCH_INFO");
 %>
<%=HtmlUtil.hidden("subFormID",subFormId)%>

<div class="panel panel-default">
<div class="panel-body">
	<table id="FixedGuarantee" class="table table-striped table-hover">
	<thead>
		<tr>
			<th></th>
			<th><%=HtmlUtil.getLabel(request, "ACCOUNT_NO","")%></th>
			<th><%=HtmlUtil.getLabel(request, "SUB","")%> </th>
			<th><%=HtmlUtil.getLabel(request, "ACCOUNT_NAME_TH","")%></th>
			<th><%=HtmlUtil.getLabel(request, "BRANCH","")%></th>
			<th><%=HtmlUtil.getLabel(request, "HOLD_DATE","")%></th>
			<th><%=HtmlUtil.getLabel(request, "RETENTION_TYPE","")%></th>
			<th><%=HtmlUtil.getLabel(request, "DEPOSIT_GUARANTEE","")%></th>
		</tr>
	</thead>
	<tbody>
		<%
		if(!Util.empty(fixedList)) {
			for(FixedGuaranteeDataM fixedM : fixedList) {
			
			
			DIHQueryResult<String>  dihBranchName =DIHProxy.getKbankBranchData(fixedM.getBranchNo(), "TH_CNTR_NM");
			String branchName =dihBranchName.getResult();
				String delemeter="";
				if(!Util.empty(branchName)){
					delemeter="-";
				}
			%>
		<tr id="ROW_<%=fixedM.getSeq()%>">
			<td ><%=HtmlUtil.radio("FIXED_DATA_SEQ","",FIRST_SEQ,FormatUtil.getString(fixedM.getSeq()),displayMode,"","",request)%></td>
			<td align="center"><%=FormatUtil.display(fixedM.getAccountNo())%></td>			
			<td align="center"><%=FormatUtil.display(fixedM.getSub())%></td>
			<td align="center"><%=FormatUtil.display(fixedM.getAccountName())%></td>
			<td align="center"><%=FormatUtil.display(fixedM.getBranchNo())%>&nbsp;<%=delemeter%>&nbsp;<%=FormatUtil.display(branchName)%></td>
			<td align="center"><%=FormatUtil.display(fixedM.getRetentionDate(), FormatUtil.EN)%></td>
			<td align="center"><%=FormatUtil.display(fixedM.getRetentionTypeDesc())%></td>
			<td align="center"><%=FormatUtil.display(fixedM.getRetentionAmt(),"#,###,###.00")%></td>
		</tr>
		<%	}
		} %>
	</tbody>
	</table>
</div>
</div>
