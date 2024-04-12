<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.eaf.orig.master.shared.model.ScoreTypeM" %>
<%@ page import="com.eaf.orig.master.shared.model.ScoreCharacterM" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="java.util.Vector"%>

<jsp:useBean id="ORIGMasterForm" scope="session" class="com.eaf.orig.master.formcontrol.view.form.ORIGMasterFormHandler" />
<jsp:useBean id="formHandlerManager" scope="session" class="com.eaf.j2ee.pattern.view.form.FormHandleManager" />
<jsp:setProperty name="formHandlerManager" property="currentFormHandler" value="ORIGMasterForm" />

<%
	HashMap charTypeHash = (HashMap)request.getSession().getAttribute("HASH_CHAR_TYPE");
	HashMap scoreTypeHash = (HashMap)request.getSession().getAttribute("HASH_SCORE_TYPE");
	
%>

<input type="hidden" name="shwAppScore" value="">	
<input type="hidden" name="shwAddFrm" value="">
<table cellpadding="0" cellspacing="0" width="100%" align="center" bgcolor="#FFFFFF">
	<tr><td>
		<div id="KLTable">
			<table class="gumayframe3" cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
				<tr><td class="TableHeader">
				<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
						<tr>
							<td class="Bigtodotext3" width="7%"><%=MessageResourceUtil.getTextDescription(request, "WEIGHT") %></td>
						    <td class="Bigtodotext3" width="20%"><%=MessageResourceUtil.getTextDescription(request, "TYPE") %></td>
						    <td class="Bigtodotext3" width="25%"><%=MessageResourceUtil.getTextDescription(request, "CHARACTERISTIC") %></td>
						    <td class="Bigtodotext3" width="5%"><%=MessageResourceUtil.getTextDescription(request, "MIN") %></td>
						    <td class="Bigtodotext3" width="5%"><%=MessageResourceUtil.getTextDescription(request, "MAX") %></td>
						    <td class="Bigtodotext3" width="33%"><%=MessageResourceUtil.getTextDescription(request, "SPECIFIC_VAL") %></td>
						    <td class="Bigtodotext3" width="5%"><%=MessageResourceUtil.getTextDescription(request, "SCORE") %></td>
						</tr>
					</table> 
				</td></tr>

<% 		
if(charTypeHash!=null && charTypeHash.size()>0 && scoreTypeHash!=null && scoreTypeHash.size()>0){

		Set scTypekeySet = scoreTypeHash.keySet();
		Iterator scTypekeyIt = scTypekeySet.iterator();
		String scTypekey;
		
		int round=0;
		int count=0;
		while(scTypekeyIt.hasNext()){
			
			
			//System.out.println("!!!round = "+(++round));
			scTypekey = (String)scTypekeyIt.next();
			ScoreTypeM scoreTypeM2 = (ScoreTypeM)scoreTypeHash.get(scTypekey);
			boolean print=true;
			
			
			Set chTypekeySet = charTypeHash.keySet();
			Iterator chTypekeyIt = chTypekeySet.iterator();
			String chTypekey;
			
			while(chTypekeyIt.hasNext()){
			//System.out.println("!!!round while2= "+round);
				chTypekey = (String)chTypekeyIt.next();
				 Vector charTypeVect = (Vector)charTypeHash.get(chTypekey);
			
				if(charTypeVect!=null && charTypeVect.size()>0){
					
					ScoreCharacterM characterM2;
					
					for(int i=0;i<charTypeVect.size();i++){
						characterM2 = (ScoreCharacterM)charTypeVect.get(i);
						if(scTypekey.equals(characterM2.getScoreCode())){
							if(print){
								if("R".equals(characterM2.getCharType())){
%>
				<tr><td align="center" class="gumaygrey2">
					<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
						<tr>
							<td class="jobopening2" width="7%"><%=scoreTypeM2.getScoreWeight() %></td>
							<td class="jobopening2" width="20%"><%=++count%>. <%=scoreTypeM2.getScoreType() %></td>
							<td class="jobopening2" width="25%"><%=characterM2.getCharDesc() %></td>
							<td class="jobopening2" width="5%"><%=characterM2.getMinRange() %></td>
							<td class="jobopening2" width="5%"><%=characterM2.getMaxRange() %></td>
							<td class="jobopening2" width="33%"></td>
							<td class="jobopening2" width="5%"><%=characterM2.getScore() %></td>
						</tr>
					</table> 
				</td></tr>
								<%}else if("S".equals(characterM2.getCharType())){ %>
				<tr><td align="center" class="gumaygrey2">
					<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
						<tr>
							<td class="jobopening2" width="7%"><%=scoreTypeM2.getScoreWeight() %></td>
							<td class="jobopening2" width="20%"><%=++count%>. <%=scoreTypeM2.getScoreType() %></td>
							<td class="jobopening2" width="25%"><%=characterM2.getCharDesc() %></td>
							<td class="jobopening2" width="5%"></td>
							<td class="jobopening2" width="5%"></td>
							<td class="jobopening2" width="33%"><%=characterM2.getSpecDesc() %></td>
							<td class="jobopening2" width="5%"><%=characterM2.getScore() %></td>
						</tr>
					</table> 
				</td></tr>
								<%}else{ %>
				<tr><td align="center" class="gumaygrey2">
					<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
						<tr>
							<td class="jobopening2" width="7%"><%=scoreTypeM2.getScoreWeight() %></td>
							<td class="jobopening2" width="20%"><%=++count%>. <%=scoreTypeM2.getScoreType() %></td>
							<td class="jobopening2" width="25%"><%=characterM2.getCharDesc() %></td>
							<td class="jobopening2" width="5%"></td>
							<td class="jobopening2" width="5%"></td>
							<td class="jobopening2" width="33%"></td>
							<td class="jobopening2" width="5%"><%=characterM2.getScore() %></td>
						</tr>
					</table> 
				</td></tr>
								<%} %>

<%
							print = false;
							}else{
								if("R".equals(characterM2.getCharType())){
%>							
				<tr><td align="center" class="gumaygrey2">
					<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
						<tr>
							<td class="jobopening2" width="7%"></td>
							<td class="jobopening2" width="20%"></td>
							<td class="jobopening2" width="25%"><%=characterM2.getCharDesc() %></td>
							<td class="jobopening2" width="5%"><%=characterM2.getMinRange() %></td>
							<td class="jobopening2" width="5%"><%=characterM2.getMaxRange() %></td>
							<td class="jobopening2" width="33%"></td>
							<td class="jobopening2" width="5%"><%=characterM2.getScore() %></td>
						</tr>
					</table> 
				</td></tr>
								<%}else if("S".equals(characterM2.getCharType())){ %>
				<tr><td align="center" class="gumaygrey2">
					<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
						<tr>
							<td class="jobopening2" width="7%"></td>
							<td class="jobopening2" width="20%"></td>
							<td class="jobopening2" width="25%"><%=characterM2.getCharDesc() %></td>
							<td class="jobopening2" width="5%"></td>
							<td class="jobopening2" width="5%"></td>
							<td class="jobopening2" width="33%"><%=characterM2.getSpecDesc() %></td>
							<td class="jobopening2" width="5%"><%=characterM2.getScore() %></td>
						</tr>
					</table> 
				</td></tr>
								<%}else{ %>
				<tr><td align="center" class="gumaygrey2">
					<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
						<tr>
							<td class="jobopening2" width="7%"></td>
							<td class="jobopening2" width="20%"></td>
							<td class="jobopening2" width="25%"><%=characterM2.getCharDesc() %></td>
							<td class="jobopening2" width="5%"></td>
							<td class="jobopening2" width="5%"></td>
							<td class="jobopening2" width="33%"></td>
							<td class="jobopening2" width="5%"><%=characterM2.getScore() %></td>
						</tr>
					</table> 
				</td></tr>
								<%} %>
								
<%
							}
						}
					}
				}
			}
		}
}else{
 %>
 				<tr><td align="center" class="gumaygrey2">
					<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
						<tr>
				  			<td class="jobopening2" align="center">Results Not Found.</td>
				  		</tr>
					</table> 
				</td></tr>
 							
 <%} %>
				<tr>
					<td align="right">
						<input type="button"  class ="button_text" name="saveAppScoreBtn" value="<%=MessageResourceUtil.getTextDescription(request, "SAVE") %>" onclick="saveAppScore()" >
						<input type="button"  class ="button_text" name="backBtn" value="<%=MessageResourceUtil.getTextDescription(request, "BACK") %>" onclick="backFromSummary()" >
					</td>
				</tr>
			</table>
		</div>
	</TD></TR>
</TABLE>

<script language="JavaScript">	
function backFromSummary(){
	window.close(); 
}
function saveAppScore(){
	appFormName.action.value = "SaveAppScore";
	appFormName.handleForm.value = "Y";
	appFormName.submit();
	//backFromSummary();
}
</script>

<%	//set current screen to main Form
	com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager =
	(com.eaf.j2ee.pattern.control.ScreenFlowManager) request.getSession(true).getAttribute(
		"screenFlowManager");
	screenFlowManager.setCurrentScreen("MAIN_APPFORM");
%>
