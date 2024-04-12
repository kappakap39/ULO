<%@page import="com.eaf.orig.shared.constant.OrigConstant"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.eaf.ncb.model.output.IQRespM"%>
<%@page import="com.eaf.orig.shared.utility.ORIGCacheUtil"%>
<%@page import="com.eaf.orig.shared.util.OrigUtil"%>
<%@page contentType="text/html;charset=UTF-8"%> 
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.orig.ulo.pl.model.app.PLNCBDocDataM"%>
<%@page import="com.eaf.j2ee.pattern.util.MessageResourceUtil"%>
<%@page import="com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesNCBDataM"%>
<%@page import="com.eaf.ncb.model.output.TLRespM"%>
<%@page import="com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesNCBRestructureDebtDataM"%>
<%@page import="com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesNCBOverdueHistoryDataM"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesNCBVerificationDataM"%>
<%@page import="com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesFICODataM"%>
<%@page import="com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM"%>
<%@page import="com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.pl.model.app.PLApplicationDataM"%>
<%@page import="com.eaf.ncb.model.output.PARespM"%>
<%@page import="com.eaf.orig.ulo.pl.app.utility.DataFormatUtility"%>
<%@page import="com.eaf.ncb.model.output.IDRespM"%>
<%@page import="com.eaf.orig.ulo.pl.app.utility.NCBMappUtil"%>
<%@page import="com.eaf.ncb.model.NCBReqRespConsentDataM"%>
<%@page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@page import="com.eaf.ncb.model.output.PNRespM"%>
<%@page import="java.util.Vector"%>
<%@page import="com.eaf.ncb.model.output.NCBOutputDataM"%>
<%@page import="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM" />
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler" />
<jsp:useBean id="searchType" scope="session" class="java.lang.String"/>
<%
	Logger logger = Logger.getLogger(this.getClass());
	
	PLApplicationDataM appM = PLORIGForm.getAppForm();	
	if(null == appM) appM = new PLApplicationDataM();	
	
	PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);

	PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();	
	if(null == xrulesVerM) xrulesVerM = new PLXRulesVerificationResultDataM();		
	
	//Vector<PLXRulesNCBDataM> vXRulesNCBVect = xrulesVerM.getVXRulesNCBDataM();
	Vector<PLXRulesNCBDataM> vXRulesNCBVect = xrulesVerM.getVNCBAdjust();
	if(null == vXRulesNCBVect) vXRulesNCBVect = new Vector<PLXRulesNCBDataM>();
	
	NCBOutputDataM ncbOutputM = xrulesVerM.getNcbOutPutM();	
	if(null == ncbOutputM) ncbOutputM = new NCBOutputDataM();
	
	Vector<PNRespM> nPersonVect = ncbOutputM.getPnRespMs();	
	PNRespM nPersonM = new PNRespM();	
	if(!OrigUtil.isEmptyVector(nPersonVect)) nPersonM = nPersonVect.lastElement();
	
	NCBReqRespConsentDataM ncbReqRespM = ncbOutputM.getNcbReqResp();	
	if(null == ncbReqRespM) ncbReqRespM = new NCBReqRespConsentDataM();
	
	Vector<IDRespM> nIdVect = ncbOutputM.getIdRespMs();	
	
	Vector<PARespM> nAddressVect = ncbOutputM.getPaRespMs();	
	PARespM nAddressM = new PARespM();	
	if(!OrigUtil.isEmptyVector(nAddressVect)){
	 	nAddressM = NCBMappUtil.GetAddressType(nAddressVect,NCBMappUtil.Consent.HOME_ADDRESS);
	}
	
	Vector<IQRespM> nEnquiryVect = ncbOutputM.getIqRespMs();
	IQRespM enquiry = new IQRespM();
	if(!OrigUtil.isEmptyVector(nEnquiryVect)){
		//See sql order by
	 	enquiry = nEnquiryVect.firstElement();
	}
	
	PLXRulesFICODataM xrulesFicoM = xrulesVerM.getXrulesFICODataM();
	if(null == xrulesFicoM) xrulesFicoM = new PLXRulesFICODataM();
	
	Vector<PLXRulesNCBVerificationDataM> ncbVerifyVect = xrulesVerM.getvXrulesNCBVerifyVect();
	
	Vector<PLXRulesNCBRestructureDebtDataM> ncbRestructVect = xrulesVerM.getvXrulesNCBRestructureDebtVect();
	Vector<PLXRulesNCBOverdueHistoryDataM> ncbOverdueVect = xrulesVerM.getvXrulesNCBOverdueHistoryVect();
	
	Vector<PLNCBDocDataM> ncbDocVect = personalM.getNcbDocVect();
			
	ORIGCacheUtil cacheUtil = new ORIGCacheUtil();		
			
%>
<div id="ncbPopup">
	<div class="PanelFirst">
		<div class="PanelSecond TextHeaderNormal">
			<%=MessageResourceUtil.getTextDescription(request, "NCB_PERSONAL_INFO") %>
			<div class="PanelThird">
				<table class="FormFrame">
					<tr>
						<td class="textR" nowrap="nowrap" width="20%"><%=MessageResourceUtil.getTextDescription(request, "NCB_FIRSTNAME") %>&nbsp;:</td>
						<td class="textL" nowrap="nowrap" width="20%">
						<%=HTMLRenderUtil.replaceNull(nPersonM.getTitle()) %>&nbsp;<%=HTMLRenderUtil.replaceNull(nPersonM.getFirstName())%>
						</td>
						<td class="textR" nowrap="nowrap" width="10%"><%=MessageResourceUtil.getTextDescription(request, "NCB_LASTNAME") %>&nbsp;:</td>
						<td class="textL" nowrap="nowrap" width="20%"><%=HTMLRenderUtil.replaceNull(nPersonM.getFamilyName1())%></td>
						<td class="textR" nowrap="nowrap" width="10%"><%=MessageResourceUtil.getTextDescription(request, "NCB_ISCONSENT") %>&nbsp;:</td>
						<td class="textL" nowrap="nowrap" width="20%"><%=NCBMappUtil.NcbConsent(nPersonM.getConsentToEnq())%></td>
					</tr>
				</table>
			</div>
		</div>
		<div class="PanelSecond TextHeaderNormal">
			<%=MessageResourceUtil.getTextDescription(request, "NCB_DOCNO") %>
			<div class="PanelThird">
				<table class="FormFrame">
					<tr>
						<td class="textR" nowrap="nowrap" width="20%"><%=MessageResourceUtil.getTextDescription(request, "NCB_IDNO") %>&nbsp;:</td>
						<td class="textL" nowrap="nowrap" width="20%"><%=NCBMappUtil.IdNo(NCBMappUtil.Consent.IDNO,nIdVect)%></td>
						<td class="textR" nowrap="nowrap" width="10%"><%=MessageResourceUtil.getTextDescription(request, "NCB_PUBLICNO") %>&nbsp;:</td>
						<td class="textL" nowrap="nowrap" width="20%"><%=NCBMappUtil.IdNo(NCBMappUtil.Consent.PUBLICNO,nIdVect)%></td>
						<td class="textR" nowrap="nowrap" width="10%"><%=MessageResourceUtil.getTextDescription(request, "NCB_PASSPORTID") %>&nbsp;:</td>
						<td class="textL" nowrap="nowrap" width="20%"><%=NCBMappUtil.IdNo(NCBMappUtil.Consent.PASSPORTNO,nIdVect)%></td>
					</tr>
				</table>
			</div>
		</div>
		<div class="PanelSecond TextHeaderNormal">
			<%=MessageResourceUtil.getTextDescription(request, "NCB_PERSONALDETAIL") %>
			<div class="PanelThird">
				<table class="FormFrame">
					<tr>
						<td class="textR" nowrap="nowrap" width="20%"><%=MessageResourceUtil.getTextDescription(request, "NCB_BIRTHDAY") %>&nbsp;:</td>
						<td class="textL" nowrap="nowrap" width="20%"><%=DataFormatUtility.DateEnToStringDateTh(nPersonM.getDateOfBirth(),DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S) %></td>
						<td class="textR" nowrap="nowrap" width="10%"><%=MessageResourceUtil.getTextDescription(request, "NCB_NACTIONLITY") %>&nbsp;:</td>
						<td class="textL" nowrap="nowrap" width="20%"><%=NCBMappUtil.GetNCBParamMaster(NCBMappUtil.Consent.Nationtality,nPersonM.getNationality())%></td>
						<td class="textR" nowrap="nowrap" width="10%"><%=MessageResourceUtil.getTextDescription(request, "NCB_GENDER") %>&nbsp;:</td>
						<td class="textL" nowrap="nowrap" width="20%"><%=NCBMappUtil.GetNCBParamMaster(NCBMappUtil.Consent.Gender,String.valueOf(nPersonM.getGender()))%></td>
					</tr>
					<tr>
						<td class="textR" nowrap="nowrap" width="20%"><%=MessageResourceUtil.getTextDescription(request, "NCB_MARRY_STATUS") %>&nbsp;:</td>
						<td class="textL" nowrap="nowrap" width="20%"><%=NCBMappUtil.GetNCBParamMaster(NCBMappUtil.Consent.MarialStatus,nPersonM.getMaritalStatus())%></td>
						<td class="textR" nowrap="nowrap" width="10%"><%=MessageResourceUtil.getTextDescription(request, "NCB_DEPENDENCE") %>&nbsp;:</td>
						<td class="textL" nowrap="nowrap" width="20%"><%=nPersonM.getNumberOfChild()%></td>
						<td class="textR" nowrap="nowrap" width="10%"><%=MessageResourceUtil.getTextDescription(request, "NCB_OCCUPATION") %>&nbsp;:</td>
						<td class="textL" nowrap="nowrap" width="20%"><%=NCBMappUtil.GetNCBParamMaster(NCBMappUtil.Consent.Occupation,nPersonM.getOccupation())%></td>
					</tr>
				</table>
			</div>
		</div>
		<div class="PanelSecond TextHeaderNormal">
			<%=MessageResourceUtil.getTextDescription(request, "NCB_TELNO") %>
			<div class="PanelThird">
				<table class="FormFrame">
					<tr>
						<td class="textR" nowrap="nowrap" width="20%"><%=MessageResourceUtil.getTextDescription(request, "NCB_TELHOME") %>&nbsp;:</td>
						<td class="textL" nowrap="nowrap" width="20%"><%=NCBMappUtil.TelephoneNo(NCBMappUtil.Consent.TELHOME,nAddressM.getTelephoneType(),nAddressM.getTelephoneNO())%></td>
						<td class="textR" nowrap="nowrap" width="10%"><%=MessageResourceUtil.getTextDescription(request, "NCB_TELWORK") %>&nbsp;:</td>
						<td class="textL" nowrap="nowrap" width="20%"><%=NCBMappUtil.TelephoneNo(NCBMappUtil.Consent.TELWORK,nAddressM.getTelephoneType(),nAddressM.getTelephoneNO())%></td>
						<td class="textR" nowrap="nowrap" width="10%"><%=MessageResourceUtil.getTextDescription(request, "NCB_MOBILE") %>&nbsp;:</td>
						<td class="textL" nowrap="nowrap" width="20%"><%=NCBMappUtil.TelephoneNo(NCBMappUtil.Consent.TELMOBLIE,nAddressM.getTelephoneType(),nAddressM.getTelephoneNO())%></td>
					</tr>
				</table>
			</div>
		</div>
		<div class="PanelSecond TextHeaderNormal">
			<%=MessageResourceUtil.getTextDescription(request, "NCB_ADDRESS") %>
			<div class="PanelThird">
				<table class="FormFrame">
					<tr>
						<td class="textR" nowrap="nowrap" width="20%"><%=MessageResourceUtil.getTextDescription(request, "NCB_HOMEADDRESS") %>&nbsp;:</td>
						<td class="textL" nowrap="nowrap" width="80%" colspan="5">
							<%=NCBMappUtil.Address(nAddressM)%>
						</td>						
					</tr>
					<tr>
						<td class="textR" nowrap="nowrap" width="20%"><%=MessageResourceUtil.getTextDescription(request, "NCB_ADDRESSTYPE") %>&nbsp;:</td>
						<td class="textL" nowrap="nowrap" width="20%"><%=NCBMappUtil.GetNCBParamMaster(NCBMappUtil.Consent.AddressType,nAddressM.getAddressType())%></td>
						<td class="textR" nowrap="nowrap" width="10%"><%=MessageResourceUtil.getTextDescription(request, "NCB_ADDRESSSTAUS") %>&nbsp;:</td>
						<td class="textL" nowrap="nowrap" width="20%"><%=NCBMappUtil.GetNCBParamMaster(NCBMappUtil.Consent.ResidentialStatus,nAddressM.getResidentStatus())%></td>
						<td class="textR" nowrap="nowrap" width="10%"><%=MessageResourceUtil.getTextDescription(request, "NCB_TIME") %>&nbsp;:</td>
						<td class="textL" nowrap="nowrap" width="20%"><%=DataFormatUtility.DateEnToStringDateTh(nAddressM.getReportDate(),DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S)%></td>
					</tr>
				</table>
			</div>
		</div>
		<div class="PanelSecond TextHeaderNormal">
			<%=MessageResourceUtil.getTextDescription(request, "NCB_CALLHISTORY") %>
			<div class="PanelThird">
				<table class="FormFrame">
					<tr>
						<td class="textR" nowrap="nowrap" width="20%"><%=MessageResourceUtil.getTextDescription(request, "NCB_ALL_CALL") %>&nbsp;:</td>
						<td class="textL" nowrap="nowrap" width="20%"><%=DataFormatUtility.StringToInt(enquiry.getTimeOfEnq()) %></td>
						<td class="textR" nowrap="nowrap" width="10%"><%=MessageResourceUtil.getTextDescription(request, "NCB_SIX_CALL") %>&nbsp;:</td>
						<td class="textL" nowrap="nowrap" width="20%"><%=DataFormatUtility.DisplayNumber(new BigDecimal(enquiry.getEnqAmt()),"0",true) %></td>
						<td class="textR" nowrap="nowrap" width="10%"></td>
						<td class="textL" nowrap="nowrap" width="20%"></td>
					</tr>
				</table>
			</div>
		</div>
		<div class="PanelSecond TextHeaderNormal">
			<%=MessageResourceUtil.getTextDescription(request, "NCB_FICOINFORMATION") %>
			<div class="PanelThird">
				<table class="FormFrame">
					<tr>
						<td class="textR" nowrap="nowrap" width="20%"><%=MessageResourceUtil.getTextDescription(request, "NCB_REFERENCE") %>&nbsp;:</td>
						<td class="textL" nowrap="nowrap" width="20%"><%=HTMLRenderUtil.replaceNull(xrulesFicoM.getFicoReference()) %></td>
						<td class="textR" nowrap="nowrap" width="10%"><%=MessageResourceUtil.getTextDescription(request, "NCB_SCORE") %>&nbsp;:</td>
						<td class="textL" nowrap="nowrap" width="20%"><%=DataFormatUtility.LongToString(xrulesFicoM.getScore())%></td>
						<td class="textR" nowrap="nowrap" width="10%"></td>
						<td class="textL" nowrap="nowrap" width="20%"></td>
					</tr>
					<tr>
						<td class="textR" nowrap="nowrap" width="20%"><%=MessageResourceUtil.getTextDescription(request, "NCB_REASON") %>&nbsp;:</td>
						<td class="textL" nowrap="nowrap" width="80%" colspan="5">
							<%if(!OrigUtil.isEmptyString(ncbReqRespM.getFicoReason1Code())) {%>
								<%=HTMLRenderUtil.replaceNull(ncbReqRespM.getFicoReason1Code())%>&nbsp;:&nbsp;
								<%=HTMLRenderUtil.replaceNull(ncbReqRespM.getFicoReason1Desc())%>
							<%} %>
						</td>					
					</tr>					
					<tr>
						<td class="textR" nowrap="nowrap" width="20%"></td>
						<td class="textL" nowrap="nowrap" width="80%" colspan="5">
						<%if(!OrigUtil.isEmptyString(ncbReqRespM.getFicoReason2Code())) {%>
							<%=HTMLRenderUtil.replaceNull(ncbReqRespM.getFicoReason2Code())%>&nbsp;:&nbsp;
							<%=HTMLRenderUtil.replaceNull(ncbReqRespM.getFicoReason2Desc())%>
						<%} %>
						</td>						
					</tr>
					<tr>
						<td class="textR" nowrap="nowrap" width="20%"></td>
						<td class="textL" nowrap="nowrap" width="80%" colspan="5">
						<%if(!OrigUtil.isEmptyString(ncbReqRespM.getFicoReason3Code())) {%>
							<%=HTMLRenderUtil.replaceNull(ncbReqRespM.getFicoReason3Code())%>&nbsp;:&nbsp;
							<%=HTMLRenderUtil.replaceNull(ncbReqRespM.getFicoReason3Desc())%>
						<%} %>
						</td>						
					</tr>
					<tr>
						<td class="textR" nowrap="nowrap" width="20%"></td>
						<td class="textL" nowrap="nowrap" width="80%" colspan="5">
						<%if(!OrigUtil.isEmptyString(ncbReqRespM.getFicoReason4Code())) {%>
							<%=HTMLRenderUtil.replaceNull(ncbReqRespM.getFicoReason4Code())%>&nbsp;:&nbsp;
							<%=HTMLRenderUtil.replaceNull(ncbReqRespM.getFicoReason4Desc())%>
						<%} %>
						</td>						
					</tr>
				</table>
			</div>
		</div>
		<div class="PanelSecond TextHeaderNormal">
			<%=MessageResourceUtil.getTextDescription(request, "NCB_SUMMARY_RESULT") %>
			<div class="PanelThird">
				<table class="FormFrame">
					<tr>
						<td class="textR" nowrap="nowrap" width="20%"><%=MessageResourceUtil.getTextDescription(request, "NCB_SUMMARY_CREDIT") %>&nbsp;:</td>
						<td class="textL" nowrap="nowrap" width="80%"><%=HTMLRenderUtil.replaceNull(NCBMappUtil.GetResultDesc(xrulesVerM.getNCBCode()))%></td>
					</tr>
				</table>
				<table class="TableFrame">
					<tr class="Header">
						<td><%=MessageResourceUtil.getTextDescription(request, "NCB_TYPE_CREDIT") %></td>
						<td><%=MessageResourceUtil.getTextDescription(request, "NCB_OWNERSHIP_INDICATOR") %></td>
						<td><%=MessageResourceUtil.getTextDescription(request, "NCB_MEMBER_SHORT_NAME") %></td>
						<td><%=MessageResourceUtil.getTextDescription(request, "NCB_PAY_LASTDATE") %></td>
						<td><%=MessageResourceUtil.getTextDescription(request, "NCB_CREDIT_LIMIT") %></td>
						<td><%=MessageResourceUtil.getTextDescription(request, "NCB_OUTSTANDING") %></td>
						<td><%=MessageResourceUtil.getTextDescription(request, "NCB_ACCOUNT_STATUS") %></td>
						<td><%=MessageResourceUtil.getTextDescription(request, "NCB_MA_DATE") %></td>
						<td><%=MessageResourceUtil.getTextDescription(request, "NCB_HISTORY_PAY") %></td>
					</tr>
					<%if(!OrigUtil.isEmptyVector(ncbVerifyVect)){%>
						<%for(int i=0; i< ncbVerifyVect.size();i++){
							PLXRulesNCBVerificationDataM ncbVerM =(PLXRulesNCBVerificationDataM) ncbVerifyVect.get(i);
			 				String styleTr = (i%2==0)?"ResultEven":"ResultOdd";						
						%>
							<tr class="<%=styleTr%>">
								<td><%=HTMLRenderUtil.replaceNull(NCBMappUtil.GetNCBAccountType(ncbVerM.getAccountType()))%></td>
								<td><%=HTMLRenderUtil.replaceNull(NCBMappUtil.GetNCBParamMaster(OrigConstant.NCBfieldId.OWNERSHIP_INDICATOR,ncbVerM.getOwnershipIndicator()))%></td>
								<td><%=HTMLRenderUtil.replaceNull(ncbVerM.getMemberShortName())%></td>
								<td><%=DataFormatUtility.DateEnToStringDateTh(ncbVerM.getDateOfLastPay(),DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S)%></td>
								<td><%=DataFormatUtility.displayCommaNumber(new BigDecimal(ncbVerM.getCreditLimOriLoanAmt()))%></td>
								<td><%=DataFormatUtility.displayCommaNumber(new BigDecimal(ncbVerM.getAmtOwed()))%></td>
								<td><%=HTMLRenderUtil.replaceNull(NCBMappUtil.GetNCBAccountStatus(ncbVerM.getAccountStatus()))%></td>
								<td><%=DataFormatUtility.DateEnToStringDateTh(ncbVerM.getAsOfDate(),DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S)%></td>
								<td><%=HTMLRenderUtil.replaceNull(ncbVerM.getPaymentHist())%></td>
							</tr>			 				
						<%} %>
					<%}else{%>
						<tr class="ResultNotFound">
							<td colspan="9">No record found</td>
						</tr>
					<%} %>
				</table>
				<table class="FormFrame">
					<tr>
						<td class="textR" nowrap="nowrap" width="20%"><%=MessageResourceUtil.getTextDescription(request, "NCB_FICO_SCORE") %>&nbsp;:</td>
						<td class="textL" nowrap="nowrap" width="80%"><%=HTMLRenderUtil.replaceNull(NCBMappUtil.GetResultDesc(xrulesVerM.getNcbFicoCode()))%></td>
					</tr>
					<tr>
						<td class="textR" nowrap="nowrap" width="20%" valign="top"><%=MessageResourceUtil.getTextDescription(request, "NCB_FICO_REASON") %>&nbsp;:</td>
						<td class="textL" nowrap="nowrap" width="80%">
							<table class="FormFrame">
								<%if(!OrigUtil.isEmptyString(xrulesVerM.getNcbFicoReason())){ %>
								<%
									String []ficoReason = xrulesVerM.getNcbFicoReason().split("\\|");
									for(String reason:ficoReason){
										if(null == reason) continue;
								 %>
									<tr valign="top"><td class="textL"><%=cacheUtil.getNaosCacheDisplayNameDataM(53,reason)%></td></tr>	
									<%} %>
								<%} %>							
							</table>	
						</td>
					</tr>
				</table>	
				<table class="FormFrame">
					<tr>
						<td class="textR" nowrap="nowrap" width="20%"><%=MessageResourceUtil.getTextDescription(request, "NCB_HISTORY_PAY_RESULT") %>&nbsp;:</td>
						<td class="textL" nowrap="nowrap" width="80%"><%=HTMLRenderUtil.replaceNull(NCBMappUtil.GetResultDesc(xrulesVerM.getNcbOverdueHistoryCode()))%></td>
					</tr>
				</table>
				<table class="TableFrame">
					<tr class="Header">
						<td><%=MessageResourceUtil.getTextDescription(request, "NCB_TYPE_CREDIT") %></td>
						<td><%=MessageResourceUtil.getTextDescription(request, "NCB_OWNERSHIP_INDICATOR") %></td>
						<td><%=MessageResourceUtil.getTextDescription(request, "NCB_MEMBER_SHORT_NAME") %></td>
						<td><%=MessageResourceUtil.getTextDescription(request, "NCB_PAY_LASTDATE") %></td>
						<td><%=MessageResourceUtil.getTextDescription(request, "NCB_CREDIT_LIMIT") %></td>
						<td><%=MessageResourceUtil.getTextDescription(request, "NCB_OUTSTANDING") %></td>
						<td><%=MessageResourceUtil.getTextDescription(request, "NCB_ACCOUNT_STATUS") %></td>
						<td><%=MessageResourceUtil.getTextDescription(request, "NCB_MA_DATE") %></td>
						<td><%=MessageResourceUtil.getTextDescription(request, "NCB_HISTORY_PAY") %></td>
						<td><%=MessageResourceUtil.getTextDescription(request, "REASON") %></td>
					</tr>
					<%if(!OrigUtil.isEmptyVector(ncbOverdueVect)){%>
						<%for(int i=0; i< ncbOverdueVect.size();i++){
							PLXRulesNCBOverdueHistoryDataM ncbOverdueVerM =(PLXRulesNCBOverdueHistoryDataM) ncbOverdueVect.get(i);
			 				String styleTr = (i%2==0)?"ResultEven":"ResultOdd";						
						%>
							<tr class="<%=styleTr%>">
								<td><%=HTMLRenderUtil.replaceNull(NCBMappUtil.GetNCBAccountType(ncbOverdueVerM.getAccountType()))%></td>
								<td><%=HTMLRenderUtil.replaceNull(NCBMappUtil.GetNCBParamMaster(OrigConstant.NCBfieldId.OWNERSHIP_INDICATOR,ncbOverdueVerM.getOwnershipIndicator()))%></td>
								<td><%=HTMLRenderUtil.replaceNull(ncbOverdueVerM.getMemberShortName())%></td>
								<td><%=DataFormatUtility.DateEnToStringDateTh(ncbOverdueVerM.getDateOfLastPay(),DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S)%></td>
								<td><%=DataFormatUtility.displayCommaNumber(new BigDecimal(ncbOverdueVerM.getCreditLimOriLoanAmt()))%></td>
								<td><%=DataFormatUtility.displayCommaNumber(new BigDecimal(ncbOverdueVerM.getAmtOwed()))%></td>
								<td><%=HTMLRenderUtil.replaceNull(NCBMappUtil.GetNCBAccountStatus(ncbOverdueVerM.getAccountStatus()))%></td>
								<td><%=DataFormatUtility.DateEnToStringDateTh(ncbOverdueVerM.getAsOfDate(),DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S)%></td>
								<td><%=HTMLRenderUtil.replaceNull(ncbOverdueVerM.getPaymentHist())%></td>
								<td><%=HTMLRenderUtil.replaceNull(ncbOverdueVerM.getReasonDesc())%></td>
							</tr>			 				
						<%} %>
					<%}else{%>
						<tr class="ResultNotFound">
							<td colspan="10">No record found</td>
						</tr>
					<%} %>
				</table>
				<table class="FormFrame">
					<tr>
						<td class="textR" nowrap="nowrap" width="20%"><%=MessageResourceUtil.getTextDescription(request, "NCB_RESTURCT") %>&nbsp;:</td>
						<td class="textL" nowrap="nowrap" width="80%"><%=HTMLRenderUtil.replaceNull(NCBMappUtil.GetResultDesc(xrulesVerM.getNcbRestructureCode()))%></td>
					</tr>
				</table>
				<table class="TableFrame">
					<tr class="Header">
						<td><%=MessageResourceUtil.getTextDescription(request, "NCB_RESTRUCT_DATE") %></td>
						<td><%=MessageResourceUtil.getTextDescription(request, "NCB_RESTRUCT_ACCSTATUS") %></td>
						<td><%=MessageResourceUtil.getTextDescription(request, "NCB_RESTRUCT_TYPECREDIT") %></td>
						<td><%=MessageResourceUtil.getTextDescription(request, "NCB_MEMBER_SHORT_NAME") %></td>
						<td><%=MessageResourceUtil.getTextDescription(request, "NCB_RESTRUCT_LIMIT") %></td>
						<td><%=MessageResourceUtil.getTextDescription(request, "NCB_RESTRUCT_OUTSTANDING") %></td>
						<td><%=MessageResourceUtil.getTextDescription(request, "NCB_RESTRUCT_LASTPAY") %></td>
						<td><%=MessageResourceUtil.getTextDescription(request, "NCB_RESTRUCT_MADATE") %></td>
						<td><%=MessageResourceUtil.getTextDescription(request, "NCB_RESTRUCT_CLOSEACC") %></td>
						<td><%=MessageResourceUtil.getTextDescription(request, "NCB_RESTRUCT_HISTORY") %></td>
						<td><%=MessageResourceUtil.getTextDescription(request, "NCB_RESTRUCT_DEBT") %></td>
					</tr>
					<%if(!OrigUtil.isEmptyVector(ncbRestructVect)){%>
						<%for(int i=0; i< ncbRestructVect.size();i++){
							PLXRulesNCBRestructureDebtDataM ncbRestructVerM =(PLXRulesNCBRestructureDebtDataM) ncbRestructVect.get(i);
			 				String styleTr = (i%2==0)?"ResultEven":"ResultOdd";						
						%>
							<tr class="<%=styleTr%>">
								<td><%=DataFormatUtility.DateEnToStringDateTh(ncbRestructVerM.getDateOfLastDebtRes(),DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S)%></td>
								<td><%=HTMLRenderUtil.replaceNull(NCBMappUtil.GetNCBAccountStatus(ncbRestructVerM.getAccountStatus()))%></td>
								<td><%=MessageResourceUtil.getTextDescription(request, "NCB_RESTRUCT_TYPECREDIT_DATA") %></td>
								<td><%=HTMLRenderUtil.replaceNull(ncbRestructVerM.getMemberShortName())%></td>
								<td><%=DataFormatUtility.displayCommaNumber(new BigDecimal(ncbRestructVerM.getCreditLimOriLoanAmt()))%></td>
								<td><%=DataFormatUtility.displayCommaNumber(new BigDecimal(ncbRestructVerM.getAmtOwed()))%></td>
								<td><%=DataFormatUtility.DateEnToStringDateTh(ncbRestructVerM.getDateOfLastPay(),DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S)%></td>
								<td><%=DataFormatUtility.DateEnToStringDateTh(ncbRestructVerM.getAsOfDate(),DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S)%></td>
								<td><%=DataFormatUtility.DateEnToStringDateTh(ncbRestructVerM.getDateAccountClosed(),DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S)%></td>
								<td><%=HTMLRenderUtil.replaceNull(ncbRestructVerM.getPaymentHist())%></td>
								<td><%=HTMLRenderUtil.replaceNull(ncbRestructVerM.getLoanClass())%></td>
							</tr>			 				
						<%} %>
					<%}else{%>
						<tr class="ResultNotFound">
							<td colspan="11">No record found</td>
						</tr>
					<%} %>
				</table>	
				<table class="FormFrame">
					<tr>
						<td class="textR" nowrap="nowrap" width="20%"><%=MessageResourceUtil.getTextDescription(request, "NCB_FAIL_HISTORY") %>&nbsp;:</td>
						<td class="textL" nowrap="nowrap" width="80%"><%=HTMLRenderUtil.replaceNull(NCBMappUtil.GetResultDesc(xrulesVerM.getNcbFailHistoryCode()))%></td>
					</tr>
					<tr>
						<td class="textR" nowrap="nowrap" width="20%" valign="top"><%=MessageResourceUtil.getTextDescription(request, "NCB_FAIL_HISTORY_REASON") %>&nbsp;:</td>
						<td class="textL" nowrap="nowrap" width="80%">
							<table class="FormFrame">
								<%if(!OrigUtil.isEmptyString(xrulesVerM.getNcbFailHistoryReason())){ %>
								<%
									String []ncbHisReason = xrulesVerM.getNcbFailHistoryReason().split("\\|");
									for(String reason: ncbHisReason){
										if(null == reason) continue;
								 %>
									<tr valign="top"><td class="textL"><%=NCBMappUtil.GetNCBAccountStatus(reason)%></td></tr>	
									<%} %>
								<%} %>							
							</table>	
						</td>
					</tr>
				</table>			
			</div>
		</div>	
		<div class="PanelSecond TextHeaderNormal">
			<%=MessageResourceUtil.getTextDescription(request, "NCB_DEBT_DETAIL") %>
			<div class="PanelThird">
				<table class="TableFrame">
					<tr class="Header">
						<td><%=MessageResourceUtil.getTextDescription(request, "NCB_DEBT_MODULE") %></td>
						<td><%=MessageResourceUtil.getTextDescription(request, "NCB_DEBT_CREDIT_TYPE") %></td>
						<td><%=MessageResourceUtil.getTextDescription(request, "NCB_DEBT_KBANK") %></td>
						<td><%=MessageResourceUtil.getTextDescription(request, "NCB_DEBT_NON_KBANK") %></td>
						<td><%=MessageResourceUtil.getTextDescription(request, "NCB_DEBT_TOTAL") %></td>
					</tr>
					<%if(!OrigUtil.isEmptyVector(vXRulesNCBVect)){%>
						<%BigDecimal grandTotal = new BigDecimal(0);
						  List<String> debtTypeList = new ArrayList<String>();
						  for(int i=0; i< vXRulesNCBVect.size();i++){
								PLXRulesNCBDataM xrulesNcbM =(PLXRulesNCBDataM) vXRulesNCBVect.get(i);
				 				String styleTr = (i%2==0)?"ResultEven":"ResultOdd";
				 				
				 				if(!OrigUtil.isEmptyBigDecimal(xrulesNcbM.getKbankDebt())){
				 					grandTotal = grandTotal.add(xrulesNcbM.getKbankDebt());
				 				}
				 				
				 				if(!OrigUtil.isEmptyBigDecimal(xrulesNcbM.getNonKbankDebt())){
				 					grandTotal = grandTotal.add(xrulesNcbM.getNonKbankDebt());
				 				}
				 					
							%>											
							<%--tr class="<%=styleTr%>">
								<td><%=HTMLRenderUtil.replaceNull(xrulesNcbM.getDebtType())%></td>
								<td><%=HTMLRenderUtil.replaceNull(xrulesNcbM.getCreditType())%></td>
								<td><%=DataFormatUtility.displayCommaNumber(xrulesNcbM.getKbankDebt())%></td>
								<td><%=DataFormatUtility.displayCommaNumber(xrulesNcbM.getNonKbankDebt())%></td>
								<td><%=DataFormatUtility.displayCommaNumber(xrulesNcbM.getTotal())%></td>
							</tr--%>
							
							<tr class="<%=styleTr%>">
							<%if( !debtTypeList.contains(xrulesNcbM.getDebtType()) ){%>
								<td><%=HTMLRenderUtil.replaceNull(xrulesNcbM.getDebtType())%></td>
								<td><%=HTMLRenderUtil.replaceNull(xrulesNcbM.getCreditType())%></td>
								<td><%=DataFormatUtility.displayCommaNumber(xrulesNcbM.getKbankDebt())%></td>
								<td><%=DataFormatUtility.displayCommaNumber(xrulesNcbM.getNonKbankDebt())%></td>
								<td><%=DataFormatUtility.displayCommaNumber(xrulesNcbM.getTotal())%></td>
							<%  debtTypeList.add(xrulesNcbM.getDebtType());
							}else{ %>
								<%if( "Total".equals(xrulesNcbM.getCreditType())){ %>
									<td></td>
									<td style="font-weight: bold;"><%=MessageResourceUtil.getTextDescription(request, "NCB_DEBT_GROUP_TOTAL") %></td>
									<td></td>
									<td></td>
									<td><%=DataFormatUtility.displayCommaNumber(xrulesNcbM.getTotal())%></td>
								<%}else{ %>
									<td></td>
									<td><%=HTMLRenderUtil.replaceNull(xrulesNcbM.getCreditType())%></td>
									<td><%=DataFormatUtility.displayCommaNumber(xrulesNcbM.getKbankDebt())%></td>
									<td><%=DataFormatUtility.displayCommaNumber(xrulesNcbM.getNonKbankDebt())%></td>
									<td><%=DataFormatUtility.displayCommaNumber(xrulesNcbM.getTotal())%></td>
								<%} %>
							<%} %>
							</tr>
						<%} %>
							<tr class="ResultEven">
								<td style="font-weight: bold;"><%=MessageResourceUtil.getTextDescription(request, "NCB_DEBT_GRAND_TOTAL") %></td>
								<td colspan="3"></td>
								<td><%=DataFormatUtility.displayCommaNumber(grandTotal)%></td>
							</tr>
					<%}else{%>
						<tr class="ResultNotFound">
							<td colspan="12">No record found</td>
						</tr>
					<%} %>
				</table>	
			</div>
		</div>
	</div>
</div>
<%	//set current screen to main Form
	com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager =
	(com.eaf.j2ee.pattern.control.ScreenFlowManager) request.getSession(true).getAttribute("screenFlowManager");
	screenFlowManager.setCurrentScreen(PLOrigFormHandler.PL_MAIN_APPFORM_SCREEN);
%>