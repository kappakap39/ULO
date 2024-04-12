<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.eaf.orig.profile.model.UserDetailM"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLAccountCardDataM"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGAccountUtil"%>
<%@ page import="com.eaf.orig.shared.utility.OrigBusinessClassUtil"%>
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
 	PLApplicationDataM applicationM = PLORIGForm.getAppForm();
 	ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
 	WorkflowTool wfTool = new WorkflowTool();
 	UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
 	PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
 	
 	String cardNo = PLOrigUtility.getInstance().getCardNoForSummary(applicationM);
 %>

<div class="nav-inbox">
	<div class="PanelFirst">	
		<div class="PanelSecond TextHeaderNormal">		
			<div class="PanelThird">
				<table class="FormFrame">
					<tr height="25">
                    	<td class="text-header-detail" colspan="4">Summary Page</td>
                    </tr>
					<tr height="25">
						<td width="20%" class="textR" align="right"><%=PLMessageResourceUtil.getTextDescription(request, "APPLICATION_NO") %>:</td>
                        <td width="25%" class="inputL"><%=HTMLRenderUtil.displayHTML(applicationM.getApplicationNo())%></td>
                        <td width="25%" class="textR" align="right"><%=PLMessageResourceUtil.getTextDescription(request, "PRODUCT") %>:</td>
                        <td width="30%" class="inputL"><%=HTMLRenderUtil.DisplayProduct(applicationM.getProduct()) %></td>
                    </tr>
				  	<tr height="25">
						<td width="20%" class="textR" align="right"><%=PLMessageResourceUtil.getTextDescription(request, "SUM_NAME_SURNAME") %>:</td>
                        <td width="25%" class="inputL"><%=HTMLRenderUtil.displayHTML(personalM.getThaiFirstName()+" "+personalM.getThaiLastName()) %></td>
                        <td width="25%" class="textR" align="right"><%=PLMessageResourceUtil.getTextDescription(request, "IDENTIFICATION_NO") %>:</td>
                        <td width="30%" class="inputL"><%=HTMLRenderUtil.displayHTML(personalM.getIdNo()) %></td>
                    </tr>
				  	<tr height="25">
						<td width="20%" class="textR" align="right"><%=PLMessageResourceUtil.getTextDescription(request, "SUM_LAST_UPDATE_BY") %> :</td>
                        <td width="25%" class="inputL"><%=HTMLRenderUtil.displayHTML(cacheUtil.GetUserNameByuserID(applicationM.getUpdateBy())) %></td>
                        <td width="25%" class="textR" align="right"><%=PLMessageResourceUtil.getTextDescription(request, "SUM_LAST_UPDATE_DATE") %> :</td>
                        <td width="30%" class="inputL"><%=HTMLRenderUtil.displayHTML(DataFormatUtility.dateTimetoStringForThai(applicationM.getUpdateDate())) %></td>
                    </tr>
				  	<tr height="25">
						<td width="20%" class="textR" align="right"><%=PLMessageResourceUtil.getTextDescription(request, "APPLICATION_STATUS") %> :</td>
                        <td width="25%" class="inputL"><%=HTMLRenderUtil.DisplayReplaceLineWithNull(wfTool.GetMessageAppStatus(request, applicationM.getApplicationStatus())) %></td>
                        <td width="25%" class="textR" align="right"><%=PLMessageResourceUtil.getTextDescription(request, "CARD_NO") %> :</td>
                        <td width="30%" class="inputL"><%=HTMLRenderUtil.DisplayReplaceLineWithNull(cardNo)%></td>
                    </tr>
				  	<tr height="25">
						<td width="20%" class="textR" align="right"><%=PLMessageResourceUtil.getTextDescription(request, "REASON") %> :</td>
                        <td width="25%" class="inputL"><%=HTMLRenderUtil.DisplayReplaceLineWithNull(HTMLRenderUtil.DisplayReasonDesc(applicationM.getReasonVect(), applicationM)) %></td>
                        <td width="25%" class="textR" align="right">&nbsp;</td>
                        <td width="30%" class="inputL">&nbsp;</td>
                    </tr>
					<tr height="25">
						<td colspan="4" align="center"><input type="button" value="OK" class="buttonNew" onclick="goToInbox()"/></td>
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