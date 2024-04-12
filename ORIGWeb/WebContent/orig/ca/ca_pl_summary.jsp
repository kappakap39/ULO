<%@ page import="com.eaf.orig.profile.model.UserDetailM"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLAccountCardDataM"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGAccountUtil"%>
<%@ page import="com.eaf.orig.shared.utility.OrigBusinessClassUtil"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.WorkflowTool"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLCardDataM"%>
<%@ page import="com.eaf.orig.cache.properties.ORIGCacheDataM"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.DataFormatUtility"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.PLMessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLApplicationDataM"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.PLOrigUtility" %>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler" /> 
<%
 	PLApplicationDataM appM = PLORIGForm.getAppForm();	
 	ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
 	PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
 	WorkflowTool wfTool = new WorkflowTool();

 	String cardNo = PLOrigUtility.getInstance().getCardNoForSummary(appM);
 %>
<div class="nav-inbox">
	<div class="PanelFirst">	
		<div class="PanelSecond TextHeaderNormal">		
			<div class="PanelThird">
				<table class="FormFrame">
                	<tr height="25">
                    	<td class="text-header-detail">Summary Page</td>
                    </tr>
                    <tr height="25">
						<td width="20%" class="textR" ><%=PLMessageResourceUtil.getTextDescription(request, "APPLICATION_NO")%> :</td>
                        <td width="25%" class="inputL">&nbsp;<%=HTMLRenderUtil.displayHTML(appM.getApplicationNo())%></td>
                        <td width="25%" class="textR" ><%=PLMessageResourceUtil.getTextDescription(request, "PRODUCT")%> :</td>
                        <td width="30%" class="inputL">&nbsp;<%=HTMLRenderUtil.DisplayProduct(appM.getProduct()) %></td>
                    </tr>
				  	<tr height="25">
						<td class="textR" ><%=PLMessageResourceUtil.getTextDescription(request, "NAME_SURNAME")%> :</td>
                        <td class="inputL">&nbsp;<%=HTMLRenderUtil.displayHTML(personalM.getThaiFirstName()+" "+personalM.getThaiLastName()) %></td>
                        <td class="textR" ><%=PLMessageResourceUtil.getTextDescription(request, "ID_NO")%>:</td>
                        <td class="inputL">&nbsp;<%=HTMLRenderUtil.displayHTML(personalM.getIdNo()) %></td>
                    </tr>
				  	<tr height="25">
						<td class="textR" ><%=PLMessageResourceUtil.getTextDescription(request, "LAST_UPDATE_BY")%> :</td>
                        <td class="inputL">&nbsp;<%=HTMLRenderUtil.displayHTML(cacheUtil.GetUserNameByuserID(appM.getUpdateBy())) %></td>
                        <td class="textR" ><%=PLMessageResourceUtil.getTextDescription(request, "LAST_UPDATE_DATE")%> :</td>
                        <td class="inputL">&nbsp;<%=HTMLRenderUtil.displayHTML(DataFormatUtility.dateTimetoStringForThai(appM.getUpdateDate())) %></td>
                    </tr>
					<tr height="25">
						<td class="textR" ><%=PLMessageResourceUtil.getTextDescription(request, "APP_STATUS")%> :</td>
                        <td class="inputL">&nbsp;<%=HTMLRenderUtil.DisplayReplaceLineWithNull(wfTool.GetMessageAppStatus(request,appM.getApplicationStatus())) %></td>
                        <td class="textR" ><%=PLMessageResourceUtil.getTextDescription(request, "CARD_NO") %> :</td>
                        <td class="inputL">&nbsp;<%=HTMLRenderUtil.DisplayReplaceLineWithNull(cardNo)%></td>
                    </tr>
				  	<tr height="25">
						<td class="textR" ><%=PLMessageResourceUtil.getTextDescription(request, "REASON")%> :</td>
                        <td class="inputL">&nbsp;<%=HTMLRenderUtil.DisplayReplaceLineWithNull(HTMLRenderUtil.DisplayReasonDesc(appM.getReasonVect(), appM)) %></td>
                    </tr>
					<tr height="25">
						<td align="center" colspan="4"><input type="button" value="OK" class="buttonNew" onclick="goToInbox()"/></td>
					</tr>
                </table>
			</div>
		</div>
	</div>
</div>
<script language="JavaScript">
function goToInbox(){
	parent.leftFrame.openMenuFrame();
	appFormName.action.value="FristPLApp";
	appFormName.handleForm.value = "N";
	appFormName.submit(); 
}
</script> 