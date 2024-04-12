<%@page import="com.eaf.orig.shared.dao.utility.ObjectDAOFactory"%>
<%@page import="com.eaf.orig.shared.dao.utility.UtilityDAO"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.Vector"%>
<%@ page import="com.eaf.orig.ulo.pl.formcontrol.view.form.SearchHandler"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.CardMaintenanceUtility"%>
<%@ page import="com.eaf.orig.shared.util.OrigUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLAccountCardDataM"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLAccountDataM"%>
<%@ page import="com.eaf.orig.profile.model.UserDetailM"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<script type="text/javascript" src="orig/js/ss/cardMaintenanceDetail.js"></script>

<% 
	org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger("orig/ss/card_maintenance.jsp");
    
    String displayMode = HTMLRenderUtil.DISPLAY_MODE_EDIT;
	
	SearchHandler HandlerM = (SearchHandler) request.getSession().getAttribute("SEARCH_DATAM");
	if(HandlerM == null){
		HandlerM = new SearchHandler();
	}
	SearchHandler.SearchDataM searchDataM = HandlerM.getSearchM();
	if(searchDataM == null){
		searchDataM = new SearchHandler.SearchDataM();
	}
		
	PLAccountDataM accountM = (PLAccountDataM) request.getSession().getAttribute("accountDataM");
	if(accountM == null){
		accountM = new PLAccountDataM();
	}
	Vector<PLAccountCardDataM> accCardVector = accountM.getAccCardVect();
	if(accCardVector == null){
		accCardVector = new Vector<PLAccountCardDataM>();
	}
	
	PLAccountCardDataM accCardM = searchDataM.getAccCardDataM();
	if(OrigUtil.isEmptyObject(accCardM)){
		accCardM = new PLAccountCardDataM();
		searchDataM.setAccCardDataM(accCardM);
	}
	
	CardMaintenanceUtility cardU = new CardMaintenanceUtility();
	int cardLinkStatus = cardU.loadCardLinkStatus(accCardM.getAccountId());
	
	if(cardLinkStatus != 4){
		displayMode = HTMLRenderUtil.DISPLAY_MODE_VIEW;
	}
	ORIGCacheUtil origCache = ORIGCacheUtil.getInstance();
	Vector vUser = (Vector)origCache.loadUserNameCache(OrigConstant.ACTIVE_FLAG);
	
	Vector<String[]> resultVect = new Vector<String[]>();
		resultVect = cardU.sortLog(accCardM.getAccountId());
	if(!OrigUtil.isEmptyObject(resultVect)){
		String[] resultStr = resultVect.get(0);
		try{
			accCardM.setCardNo(resultStr[0]);
		}catch(Exception e){						
		}
	}
	String FLAG = "";
	try{
		UtilityDAO DAO = ObjectDAOFactory.getUtilityDAO();	
		FLAG = DAO.getICDCFLAG(searchDataM.getAppRecId());
	}catch(Exception e){		
	}	
	
	String displayMode_ReCusNo = displayMode;
	if(HTMLRenderUtil.DISPLAY_MODE_EDIT.equals(displayMode_ReCusNo)){
		if(OrigConstant.FLAG_N.equals(accountM.getEnabelReissueCusNo()) || OrigConstant.FLAG_Y.equals(FLAG)){
			displayMode_ReCusNo = HTMLRenderUtil.DISPLAY_MODE_VIEW;
		}
	}
	
	String displayMode_Cancle = displayMode;
	if(HTMLRenderUtil.DISPLAY_MODE_EDIT.equals(displayMode)){
		if(OrigConstant.FLAG_Y.equals(FLAG)){
			displayMode = HTMLRenderUtil.DISPLAY_MODE_VIEW;
		}
	}
%>
<div class="nav-inbox">
	<div class="PanelFirst">
		<div class="PanelSecond TextHeaderNormal">
			<div class="PanelThird">
				<fieldset class="field-set">
				<legend><%=MessageResourceUtil.getTextDescription(request, "CARD_INFO")%></legend>
					<table class="FormFrame">
						<tr>
                        	<td class="textR"><%=MessageResourceUtil.getTextDescription(request, "APPLICATION_NO")%> :</td>
                            <td class="textL"><%=HTMLRenderUtil.displayHTML(accountM.getApplicationNo()) %></td>
                            <td class="textR"><%=MessageResourceUtil.getTextDescription(request, "CARD_LINK_REF_NO")%> :</td>
                            <td class="textL"><%=HTMLRenderUtil.displayHTML(searchDataM.getAppRecId()) %></td>
                            <td class="textR"><%=MessageResourceUtil.getTextDescription(request, "PRODUCT")%> :</td>
                            <td class="textL"><%=HTMLRenderUtil.displayHTML(HTMLRenderUtil.DisplayProduct(accountM.getBusinessId())) %></td>
                        </tr>
                        <tr height="25">
                        	<td class="textR"><%=MessageResourceUtil.getTextDescription(request, "FULL_NAME")%> :</td>
                            <td class="textL"><%=HTMLRenderUtil.displayHTML(accountM.getThFirstName() +" "+accountM.getThLastName()) %></td>
                            <td class="textR"><%=MessageResourceUtil.getTextDescription(request, "IDENTIFICATION_NO")%> :</td>
                            <td class="textL"><%=HTMLRenderUtil.displayHTML(accountM.getIDNO()) %></td>
                            <td class="textR"><%=MessageResourceUtil.getTextDescription(request, "CARD_NO")%> :</td>
                            <td class="textL"><%=HTMLRenderUtil.displayHTML(accCardM.getCardNo()) %></td>
                        </tr>
                        <tr height="25">
                        	<td class="textR" ><%=MessageResourceUtil.getTextDescription(request, "CARD_TYPE")%> :</td>
 							<td class="textL"><%=HTMLRenderUtil.displayHTML(accCardM.getCardType()) %></td>
                            <td class="textR"><%=MessageResourceUtil.getTextDescription(request, "CARD_FACE")%> :</td>
                            <td class="textL"><%=HTMLRenderUtil.displayHTML(accCardM.getCardFace()) %></td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                        </tr>
					</table>
				</fieldset>
			</div>
			<div class="PanelThird">
				<fieldset class="field-set">
					<legend><%=MessageResourceUtil.getTextDescription(request, "CARD_HISTORY")%></legend>
					<table class="TableFrame">
						<tr class="Header">
	                    	<td width="15"><%=MessageResourceUtil.getTextDescription(request, "NO")%></td>
	                        <td><%=MessageResourceUtil.getTextDescription(request, "CARD_NO")%></td>
	                        <td><%=MessageResourceUtil.getTextDescription(request, "CARD_TYPE")%></td>
	                        <td><%=MessageResourceUtil.getTextDescription(request, "CARD_FACE")%></td>
	                        <td><%=MessageResourceUtil.getTextDescription(request, "PAYROLL_CUSTOMER_NUMBER")%></td>
	                        <td><%=MessageResourceUtil.getTextDescription(request, "ISSUED_BY")%></td>
	                        <td><%=MessageResourceUtil.getTextDescription(request, "CREATE_DATE")%></td>
	                    </tr>
	                    <%
	                    if((resultVect!=null)&&(resultVect.size()>0)){
							for(int i=0 ; i<resultVect.size() ; i++){
								String[] resultStr = resultVect.get(i);
								String styleTr = ((i)%2==0)?"ResultEven":"ResultOdd";
						%>
							<tr class="Result-Obj <%=styleTr%>" id="<%//=accCardM.getCardNo()%>">
				            	<td><%=i+1%></td>
				                <td><%=HTMLRenderUtil.displayHTML(resultStr[0]) %></td>
				                <td><%=HTMLRenderUtil.displayHTML(resultStr[1]) %></td>
				                <td><%=HTMLRenderUtil.displayHTML(resultStr[2]) %></td>
				                <td><%=HTMLRenderUtil.displayHTML(resultStr[3]) %></td>
				                <td><%=HTMLRenderUtil.displayHTML(HTMLRenderUtil.displayThFullName(resultStr[4], vUser)) %></td>
				                <td><%=HTMLRenderUtil.displayHTML(resultStr[5]) %> <input type="hidden" value=<%=resultStr[3]%> name="custNo"></td>			               
				            </tr>									  		
						<%}}else{%>
		                    <tr class="ResultNotFound" id="notFoundTable">
				            	<td colspan="11" >No record found</td>
				            </tr>		
	                    <%}%>
	                </table>
				</fieldset>
			</div>
			<div class="PanelThird">
				<div class='div-error-mandatory' id="msg"></div>
				<%=SearchHandler.DisplayErrorMessage(request)%>
				<table class="TableFrame" style="width: 300px;" align="center">
					<tr align="center">
						<td class="textR"><%=MessageResourceUtil.getTextDescription(request, "ACTION")%><font color="red">*</font> :</td>
	                    <td class="textR"><%=HTMLRenderUtil.DisplayRadio(OrigConstant.cardMaintenance.RE_ISSUE_CARD_NO, displayMode,"SSaction","","")%></td>
	                    <td class="textL"><%=MessageResourceUtil.getTextDescription(request, "RE_ISSUE_CARD_NO")%></td>
	                </tr>
	                <tr align="center">
						<td>&nbsp;</td>
	                    <td class="textR"><%=HTMLRenderUtil.DisplayRadio(OrigConstant.cardMaintenance.RE_ISSUE_CUST_NO, displayMode_ReCusNo,"SSaction","","")%></td>
	                    <td class="textL"><%=MessageResourceUtil.getTextDescription(request, "RE_ISSUE_CUSTOMER_NO")%></td>
	                </tr>
	                <tr align="center">
						<td>&nbsp;</td>
	                    <td class="textR"><%=HTMLRenderUtil.DisplayRadio(OrigConstant.cardMaintenance.CANCEL, displayMode_Cancle, "SSaction","","")%></td>
	                    <td class="textL"><%=MessageResourceUtil.getTextDescription(request, "CANCEL_APPLICATION")%></td>
	                </tr>
	            </table>
	        </div>
	        <div class="PanelThird">
	            <table class="TableFrame" style="width: 100%" align="center">
	                <tr align="center">
						<td width="90%" align="left">
							<input type="button" value="Notepad" name="notepad" class="button" onclick="notePad('<%=searchDataM.getAppRecId()%>')">
						</td>
						<%if(!OrigUtil.isEmptyString(displayMode_Cancle) && displayMode_Cancle.equals(HTMLRenderUtil.DISPLAY_MODE_EDIT)){%>
							<td align="right">
								<input type="button" value="Submit" name="Submit" class="button" 
										onclick="javascript:doActionSS('<%=accountM.getApplicationNo()%>','<%=accCardM.getAccountId()%>')">
							</td>
						<%}%>
	                    <td align="right">
	                    	<input type="button" value="Close" name="close" class="button" 
	                    			onclick="javascript:closeButton('<%=ORIGUser.getCurrentRole()%>')">
	                    </td>
	                </tr>
				</table>
			</div>
		</div>
	</div>
</div>
<%=HTMLRenderUtil.displayHiddenField(accountM.getApplicationNo(), "appNo")%>
<%=HTMLRenderUtil.displayHiddenField(accCardM.getAccountId(), "accId") %>
<%=HTMLRenderUtil.displayHiddenField("", "msgSS") %>

