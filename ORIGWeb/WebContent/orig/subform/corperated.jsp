<%@ page import="java.util.*" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.model.CorperatedDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.orig.shared.model.PersonalInfoDataM" %>
<%@ page import="com.eaf.orig.shared.model.ApplicationDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
	


<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>

<% 
	ApplicationDataM applicationDataM = ORIGForm.getAppForm();
	if(applicationDataM == null){
		applicationDataM = new ApplicationDataM();
	}
	ORIGFormUtil formUtil = new ORIGFormUtil();
	String searchType = (String) request.getSession().getAttribute("searchType");
	String displayMode = formUtil.getDisplayMode("CORPERATED_SUBFORM", ORIGUser.getRoles(), applicationDataM.getJobState(), ORIGForm.getFormID(), searchType);	
	
	ORIGUtility utility = new ORIGUtility();
//	String displayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
	
// 	>>> modify from changeName to othername to corperated by pong <<<<<
	String personalType = (String) request.getSession().getAttribute("PersonalType");
	PersonalInfoDataM personalInfoDataM;
	if(OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)){
		personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("MAIN_POPUP_DATA");
	}else if(OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType)){
		personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("SUPCARD_POPUP_DATA");
	}else{
		personalInfoDataM = utility.getPersonalInfoByType(ORIGForm.getAppForm(),OrigConstant.PERSONAL_TYPE_APPLICANT);
	}
	if(personalInfoDataM == null){
		personalInfoDataM = new PersonalInfoDataM();
	} 	
	
	Vector corperatedVect = personalInfoDataM.getCorperatedVect();	
	
	String disabledChk = "";
	if(ORIGDisplayFormatUtil.DISPLAY_MODE_VIEW.equals(displayMode)){
		disabledChk = "disabled";
	}
%>

<table cellpadding="" cellspacing="0" width="100%" align="center" bgcolor="#FFFFFF">
	<tr> 
		<td colspan="4">
			<div id="KLTable">
				<div id="Corperated">
					<table  class="gumayframe3" cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
						<tr><td class="TableHeader">
							<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
								<tr>
									<td class="Bigtodotext3" align="center" width="4%"></td>
									<td class="Bigtodotext3" align="center" width="6%"><%=MessageResourceUtil.getTextDescription(request, "YEARS") %></td>
									<td class="Bigtodotext3" align="center" width="18%"><%=MessageResourceUtil.getTextDescription(request, "TOTAL_ASSETS") %></td>
									<td class="Bigtodotext3" align="center" width="18%"><%=MessageResourceUtil.getTextDescription(request, "TOTAL_LIABILITIES") %></td>
									<td class="Bigtodotext3" align="center" width="18%"><%=MessageResourceUtil.getTextDescription(request, "TOTAL_SHAREHOLDERS_EQUITY") %></td>
									<td class="Bigtodotext3" align="center" width="18%"><%=MessageResourceUtil.getTextDescription(request, "NET_INCOME") %></td>							
									<td class="Bigtodotext3" align="center" width="18%"><%=MessageResourceUtil.getTextDescription(request, "NET_PROFIT_OR_SALES") %></td>
								</tr>
							</table>
						</td>
						</tr>
						<%if(corperatedVect != null && corperatedVect.size() > 0){
							CorperatedDataM corperatedDataM;
							for(int i=0; i<corperatedVect.size(); i++){
								corperatedDataM = (CorperatedDataM) corperatedVect.get(i);
						%>
						<tr><td align="center" class="gumaygrey2">
							<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
								<tr>
									<td class="jobopening2" width="4%"><%=ORIGDisplayFormatUtil.displayCheckBox("delete","corperatedSeq",String.valueOf(corperatedDataM.getSeq()),disabledChk) %></td>
									<td class="jobopening2" width="6%"><a href="javascript:loadCorperatedPopup('corperated','LoadCorperatedPopup','760','600','200','100','<%=corperatedDataM.getYear()%>','<%=corperatedDataM.getSeq()%>','')"><u><%=corperatedDataM.getYear() %></u></a></td>																						
									<td class="jobopening2" width="18%" align="right"><%=ORIGDisplayFormatUtil.displayCommaNumber(corperatedDataM.getAsstTotalAssets()) %></td>
									<td class="jobopening2" width="18%" align="right"><%=ORIGDisplayFormatUtil.displayCommaNumber(corperatedDataM.getLbTotalLb()) %></td>
									<td class="jobopening2" width="18%" align="right"><%=ORIGDisplayFormatUtil.displayCommaNumber(corperatedDataM.getShdEqTotalShareHdEqity()) %></td>
									<td class="jobopening2" width="18%" align="right"><%=ORIGDisplayFormatUtil.displayCommaNumber(corperatedDataM.getIncStmtNetIncome()) %></td>
									<td class="jobopening2" width="18%" align="right"><%=ORIGDisplayFormatUtil.displayCommaNumber(corperatedDataM.getRatNetProfitSale()) %></td>
								</tr>
							</table>
						</td></tr>
						<%	} 
						  }else{%>
						<tr><td align="center" class="gumaygrey2">
							<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
								<tr>
						  		<td class="jobopening2" colspan="9" align="center">No Record</td>
						  		</tr>
							</table>
						</td></tr>
						<%}%>
					</table>
				</div>
			</div>
		</td>
	</tr>
	<tr><td height="20"></td></tr>
	<%if(ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT.equals(displayMode)){ %>
	<tr>
		<td width="85%"></td>
		<td align="right"><INPUT type="button" name="addCorperatedBnt" value=" <%=MessageResourceUtil.getTextDescription(request, "ADD") %> " onClick="javascript:loadCorperatedPopup('corperated','LoadCorperatedPopup','1000','600','200','100','','','')" class="button_text" <%=disabledChk %>>
		<%//=ORIGDisplayFormatUtil.displayButtonTagScriptAction(msgUtil.getTextDescription(request, "ADD"), displayMode,"button", "addICBnt", "button_text", "onClick=loadPopup('address','LoadChangeNamePopup','500','400','150','100','IC')", "") %>
		</td><td align="right">
		 <INPUT type="button" name="deleteCorperatedNameBnt" value=" <%=MessageResourceUtil.getTextDescription(request, "DELETE") %> " onClick="javascript:deleteTableList('corperatedSeq','Corperated','DeleteCorperatedServlet')" class="button_text" <%=disabledChk %>></td>
		<td width="2%"></td>
	</tr>
	<%} %>
</table>