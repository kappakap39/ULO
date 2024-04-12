<%@ page import="com.eaf.orig.shared.utility.ORIGUtility"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.DataFormatUtility"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.PLMessageResourceUtil"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLCardDataM"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM"%>
<%@ page import="java.util.Vector"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLApplicationDataM"%>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.HashMap" %>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM" />
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler" /> 

<script type="text/javascript" src="orig/js/subform/pl/card_information.js"></script>

<%
	org.apache.log4j.Logger log=org.apache.log4j.Logger.getLogger("orig/subform/pl/card_information.jsp");
	String contextPath=request.getContextPath(); 
	PLApplicationDataM applicationM = PLORIGForm.getAppForm();
	
// 	String appRecId = applicationM.getAppRecordID();
	//String errorMsg=(String)session.getAttribute("ATTACH_ERROR_MSG");
	// session.removeAttribute("ATTACH_ERROR_MSG");   
	
	ORIGFormUtil formUtil = new ORIGFormUtil();
	String searchType = (String) request.getSession().getAttribute("searchType");
	String displayMode = formUtil.getDisplayMode("CARD_INFORMATION_SUBFORM",ORIGUser.getRoles(),applicationM.getJobState(),PLORIGForm.getFormID(),searchType);
	
	PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(OrigConstant.PERSONAL_TYPE_APPLICANT);
	PLCardDataM cardM = personalM.getCardInformation();
	if(cardM==null){
		cardM = new PLCardDataM();
	}
	ORIGCacheUtil cacheUtil = ORIGCacheUtil.getInstance();
	//Vector cardInfoMasterVect=cacheUtil.loadCacheByActive(OrigConstant.CacheName.CARD_TYPE);
	
	String cardType = "";
	if(ORIGUtility.isEmptyString(cardM.getCardType())){
		cardType="KEC";
	}else{
		cardType = cardM.getCardType();
	}
	
	String cardFace = "";
	if(ORIGUtility.isEmptyString(cardM.getCardFace())){
		cardFace="KEC_NM";
	}else{
		cardFace = cardM.getCardFace();
	}
	
	//if(cardInfoMasterVect==null){
	//	cardInfoMasterVect=new Vector();
	//}
// 	Vector cardFrontMasterVect=cacheUtil.loadCacheByActive(OrigConstant.CacheName.CARD_FACE);
// 	if(cardFrontMasterVect==null){
// 		cardFrontMasterVect=new Vector();
// 	}
%>
<table class="FormFrame">
     <tr height="25">
         <td class="textR"  width="20%"><%=PLMessageResourceUtil.getTextDescription(request, "CARD_INFO_CARD_NO",ORIGUser,personalM.getCustomerType(),"CARD_INFORMATION_SUBFORM","card_info_card_id") %><%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"CARD_INFORMATION_SUBFORM","card_info_card_id")%>&nbsp;:&nbsp;</td>
         <td class="inputL" width="25%"><%=HTMLRenderUtil.displayInputTagScriptAction(cardM.getCardNo(), HTMLRenderUtil.DISPLAY_MODE_VIEW, "", "card_info_card_id", "textbox textboxReadOnly", "", "16") %>
         </td>
      <td class="textR" width="25%"></td>
      <td class="inputL" width="30%"></td>
      </tr>
      <tr height="25">
         <td class="textR"><%=PLMessageResourceUtil.getTextDescription(request, "CARD_INFO_CARD_TYPE",ORIGUser,personalM.getCustomerType(),"CARD_INFORMATION_SUBFORM","card_info_card_type") %><%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"CARD_INFORMATION_SUBFORM","card_info_card_type")%>&nbsp;:&nbsp;</td>
         <td class="inputL" id="td_card_info_card_type"><%=HTMLRenderUtil.displaySelectTagScriptActionORIG(OrigConstant.CacheName.CARD_TYPE,HTMLRenderUtil.displayHTML(cardType),"card_info_card_type",displayMode,"") %></td>
         <td class="textR"><%=PLMessageResourceUtil.getTextDescription(request, "CARD_INFO_CARD_FACE",ORIGUser,personalM.getCustomerType(),"CARD_INFORMATION_SUBFORM","card_info_card_face") %><%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"CARD_INFORMATION_SUBFORM","card_info_card_face")%>&nbsp;:&nbsp;</td>
         <td class="inputL" id="td_card_info_card_face"><%=HTMLRenderUtil.displaySelectTagScriptActionORIG(OrigConstant.CacheName.CARD_FACE,HTMLRenderUtil.displayHTML(cardFace),"card_info_card_face",displayMode,"") %></td>
  	</tr>
  </table>
<%=HTMLRenderUtil.displayHiddenField(applicationM.getBusinessClassId(),"cardinfo_bussclass") %>