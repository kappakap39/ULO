<%@ page import="java.util.*" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.model.AddressDataM" %>
<%@ page import="com.eaf.orig.shared.model.PersonalInfoDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.orig.shared.model.ApplicationDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
<%@ page import="com.eaf.orig.cache.CacheDataInf"%>
<%@ page import="com.eaf.orig.shared.model.LoanDataM"%>



<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler" />
<jsp:useBean id="formHandlerManager" scope="session" class="com.eaf.j2ee.pattern.view.form.FormHandleManager" />
<jsp:setProperty name="formHandlerManager" property="currentFormHandler" value="ORIGForm" />

<% 
	Vector vAppMs = ORIGForm.getResultAppMs();

%>

<table cellpadding="" cellspacing="0" width="100%" align="center" bgcolor="#FFFFFF">
	<tr> 
		<td>
			<div id="KLTable">
				<div id="Address">
					<table  class="gumayframe3" cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
						<tr><td class="TableHeader">
							<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
								<tr>
									<td class="Bigtodotext3" align="center" width="18%"><%=MessageResourceUtil.getTextDescription(request, "LOAN_TYPE") %></td>
									<td class="Bigtodotext3" align="center" width="5%"><%=MessageResourceUtil.getTextDescription(request, "CAMPAIGN") %></td>
									<td class="Bigtodotext3" align="center" width="15%"><%=MessageResourceUtil.getTextDescription(request, "SCHEME_COME") %></td>
									<td class="Bigtodotext3" align="center" width="12%"><%=MessageResourceUtil.getTextDescription(request, "LOAN_AMOUNT") %></td>
								</tr>
							</table> 
						</td>
						</tr>
<% 
						ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
						ORIGUtility utility = ORIGUtility.getInstance();
						Vector vProduct = utility.loadCacheByName("Product");
						if (vAppMs.size() > 0)	{
							for (int i =0;i <vAppMs.size();i++) {
								ApplicationDataM appDataM = (ApplicationDataM)vAppMs.get(i); 									
								Vector vLoans = appDataM.getLoanVect();
								String loanType ="";
								for (int j = 0; j < vProduct.size(); j++) {
									CacheDataInf obj = (CacheDataInf)vProduct.get(j);		
									if (obj.getCode().equals(appDataM.getLoanType())) {
										loanType = obj.getThDesc();
									}
								}
								String campaign = "";
								String schemeCode = "";
								String loanAmount = "";
								for (int j = 0; j < vLoans.size(); j++) {
									LoanDataM loanDataM = (LoanDataM)vLoans.get(j);
									campaign = loanDataM.getCampaign();
									schemeCode = loanDataM.getSchemeCode();
									loanAmount = loanDataM.getLoanAmt().toString();
								}
								String campaignDesc = cacheUtil.getORIGMasterDisplayNameDataM("Campaign", campaign);
								String schemeCodeDese = cacheUtil.getORIGMasterDisplayNameDataM("IntScheme", schemeCode);
								
%>						
							<tr><td align="center" class="gumaygrey2">
								<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
									<tr>			
										<td class="jobopening2" width="18%"><%=loanType%></td>
										<td class="jobopening2" width="5%"><%=campaignDesc%></td>
										<td class="jobopening2" width="15%"><%=schemeCodeDese%></td>
										<td class="jobopening2" width="12%"><%=loanAmount%></td>		
									</tr>
								</table> 
							</td></tr>					
<% 						
							}	
						}else{
%>
							<tr><td align="center" class="gumaygrey2">
								<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
									<tr>
										<td class="jobopening2" colspan="4" align="center" >No Record</td>
									</tr>
								</table> 
							</td></tr>
						<%} %>
					</table>
				</div>
			</div>
		</td>
	</tr>
</table>


<table cellpadding="" cellspacing="0" width="100%" align="center">
	<tr>
		<td align="right">&nbsp;</td>
	</tr>
	<tr>
		<td align="right">
			<input type="button" value="<%=MessageResourceUtil.getTextDescription(request, "SAVE") %>" class="button_text" name="saveAllApps" onclick="submitAllApp()"> 
		</td>
	</tr>
</table>


<script language="JavaScript">

function submitAllApp(){
<%
	if(vAppMs.size() > 1) {
%>	
		appFormName.action.value = "saveAllApp";
<%
	} else {
%>
		appFormName.action.value = "loadRekeyApp";
<%
	}
%>
	appFormName.handleForm.value = "N";
	displayLoading('Processing...');
	appFormName.submit();
}
</script>

