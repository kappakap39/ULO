
<%@page import="com.eaf.orig.ulo.model.ncb.NcbAccountDataM"%>
<%@page import="com.eaf.orig.ulo.app.ncb.dao.OrigNCBInfoDAO"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
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
<jsp:useBean id="ModuleForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler"/>
<% 
	Logger logger = Logger.getLogger(this.getClass());
	String displayMode = HtmlUtil.EDIT;
	String CACHE_PRODUCT_INFO = SystemConstant.getConstant("CACHE_PRODUCT_INFO");
	String NCB_MEMBER_CODE_KBANK =SystemConstant.getConstant("NCB_MEMBER_CODE_KBANK");
	String NCB_ACC_TYPE_CACHE = SystemConstant.getConstant("NCB_ACC_TYPE_CACHE");
 	ArrayList<NcbAccountDataM> accountList	= (ArrayList<NcbAccountDataM>)ModuleForm.getObjectForm();
	if(Util.empty(accountList)){
	 	accountList = new  ArrayList<NcbAccountDataM>();
	}
 
%>

<div class="panel panel-default">
	<div class="panel-body">
		<div class="row form-horizontal">
				<table class="table table-bordered">
					<tbody>
					<tr class="tabletheme_header">
						<th><%=HtmlUtil.getLabel(request, "NUMBER_ABBR", "col-sm-8 col-md-12") %></th>
						<th><%=HtmlUtil.getLabel(request, "LOAN_TYPE", "col-sm-8 col-md-12") %></th>
					</tr>
						<%
							int seq = 0;
							if(!Util.empty(accountList)){
								for(NcbAccountDataM ncbAccount:accountList){	
									String product=CacheControl.getName(NCB_ACC_TYPE_CACHE, ncbAccount.getAccountType(), "VALUE");
									seq++;
											%>
										<tr>
											<td><%=FormatUtil.display(FormatUtil.getString(seq))%></td>
											<td><%=FormatUtil.display(product)%></td>
										</tr>
									<%
									}
								}else{%>
									<tr >
										<td colspan="999" align="center"><%=HtmlUtil.getText(request, "NO_RECORD_FOUND") %> </td>
									</tr>
								<%} %>
					</tbody>
				</table>
			</div>
		</div>
	</div>