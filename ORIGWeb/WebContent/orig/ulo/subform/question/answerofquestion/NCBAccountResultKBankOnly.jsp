
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.orig.ulo.app.ncb.dao.OrigNCBInfoDAO"%>
<%@page import="com.eaf.orig.ulo.model.ncb.NcbAccountDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.orig.ulo.model.ncb.NcbInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.ncb.NcbAddressDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page contentType="text/html;charset=UTF-8"%>
<script type="text/javascript" src="orig/ulo/subform/question/answerofquestion/js/NCBAccountResultKBankOnly.js"></script>
<jsp:useBean id="ModuleForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler"/>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<% 
	Logger logger = Logger.getLogger(this.getClass());
	String displayMode = HtmlUtil.EDIT;
	ArrayList<NcbAccountDataM> accountList	= (ArrayList<NcbAccountDataM>)ModuleForm.getObjectForm();
	if(Util.empty(accountList)){
	 	accountList = new  ArrayList<NcbAccountDataM>();
	}
	
%>
<div class="ncb-background">
	<div class="col-sm-6 ncb-watermark"><span class="ncb-watermark-text" data-bg-text="<%=ORIGUser.getUserName()%>"></span></div>
</div>
<div class="panel panel-default">
	<div class="panel-body">
		<div class="row form-horizontal">
			
				<table class="table table-bordered">
					<tbody>
					<tr class="tabletheme_header">
						<th><%=HtmlUtil.getLabel(request, "NUMBER_ABBR", "col-sm-8 col-md-12") %></th>
						<th><%=HtmlUtil.getLabel(request, "LOAN_TYPE", "col-sm-8 col-md-12") %></th>
						<th><%=HtmlUtil.getLabel(request, "LIMIT", "col-sm-8 col-md-12") %></th>
						
					</tr>
						<%	
						int seq = 0;
						if(!Util.empty(accountList)){
							for(NcbAccountDataM ncbAccount:accountList){
							seq++;
						%>
						<tr>
							<td><%=FormatUtil.display(seq)%></td>
							<td><%=FormatUtil.display(CacheControl.getName(SystemConstant.getConstant("NCB_ACC_TYPE_CACHE"), ncbAccount.getAccountType())) %></td>
							<td><%=FormatUtil.displayCurrency(ncbAccount.getCreditlimOrloanamt(),true) %></td>
						
						</tr>
						<%
							}
						}else{
						%>
						<tr >
							<td colspan="999" align="center"><%=HtmlUtil.getText(request, "NO_RECORD_FOUND") %> </td>
						</tr>
												
						<%}%>
					</tbody>
				</table>
			</div>
		</div>
	</div>
