<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.Vector"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.PLMessageResourceUtil"%>
<%@ page import="com.eaf.j2ee.pattern.util.ErrorUtil"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLCardDataM"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLApplicationDataM"%>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM" />
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler" /> 

<script type="text/javascript" src="orig/js/subform/pl/card_information.js"></script>

<%
	org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger("orig/subform/pl/card_information.jsp");	
  	PLApplicationDataM applicationM = PLORIGForm.getAppForm();
	
  	ORIGFormUtil formUtil = new ORIGFormUtil();
  	String searchType = (String) request.getSession().getAttribute("searchType");
  	String displayMode = formUtil.getDisplayMode("CARD_ICDC_SUBFORM", ORIGUser.getRoles(), applicationM.getJobState(), PLORIGForm.getFormID(), searchType);
   
   	PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(OrigConstant.PERSONAL_TYPE_APPLICANT);
	PLCardDataM cardM = personalM.getCardInformation();
  	if(cardM==null){
  		cardM = new PLCardDataM();
  	}
  	
  	String CardType = cardM.getCardType();
  	String CardFace = cardM.getCardFace();
  	
  	if(ORIGUtility.isEmptyString(CardType)){CardType = OrigConstant.PRODUCT_KEC;}
  	if(ORIGUtility.isEmptyString(CardFace)){CardFace = OrigConstant.PRODUCT_KEC_NM;}
  	
  	String oldCreditDisplayMode = HTMLRenderUtil.DISPLAY_MODE_VIEW;  
  	
%>
<table class="FormFrame" id="card_icdc_div">
      <tr>
        <td width="20%" class="textR" nowrap="nowrap">
        	<%=PLMessageResourceUtil.getTextDescription(request, "CARD_INFO_CARD_NO",ORIGUser,personalM.getCustomerType(),"CARD_ICDC_SUBFORM","card_info_card_no") %><%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"CARD_ICDC_SUBFORM","card_info_card_no")%>&nbsp;:
        </td>
        <td width="25%" class="inputL" nowrap="nowrap">
        	<%=HTMLRenderUtil.displayInputTagScriptAction(cardM.getCardNo(),displayMode,"","card_info_card_no","textbox","onchange=\"javascript:validateICDCCardNo()\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onKeyPress=\"keyPressInteger();\"","16") %>
        </td>
        <td width="25%" class="textR" nowrap="nowrap"></td>
        <td width="30%" class="inputL" nowrap="nowrap"></td>
      </tr>
      <tr>
        <td class="textR" nowrap="nowrap">
        	<%=PLMessageResourceUtil.getTextDescription(request, "CARD_INFO_CARD_TYPE",ORIGUser,personalM.getCustomerType(),"CARD_ICDC_SUBFORM","card_info_card_type") %><%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"CARD_ICDC_SUBFORM","card_info_card_type")%>&nbsp;:
        </td>
        <td class="inputL" nowrap="nowrap" id='td_card_info_card_type'>
        	<%=HTMLRenderUtil.displaySelectTagScriptActionORIG(OrigConstant.CacheName.CARD_TYPE,HTMLRenderUtil.displayHTML(CardType),"card_info_card_type","VIEW","") %>
        </td>
        <td class="textR" nowrap="nowrap">
        	<%=PLMessageResourceUtil.getTextDescription(request, "CARD_INFO_CARD_FACE",ORIGUser,personalM.getCustomerType(),"CARD_ICDC_SUBFORM","card_info_card_face") %><%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"CARD_ICDC_SUBFORM","card_info_card_face")%>&nbsp;:
        </td>
        <td class="inputL" nowrap="nowrap" id='td_card_info_card_face'>
        	<%=HTMLRenderUtil.displaySelectTagScriptActionORIG(OrigConstant.CacheName.CARD_FACE,HTMLRenderUtil.displayHTML(CardFace),"card_info_card_face","VIEW","") %>
        </td>
      </tr>
      <tr>
        <td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "CREDIT_LINE_REQUEST",ORIGUser,personalM.getCustomerType(),"CARD_ICDC_SUBFORM","card_info_credit_request") %><%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"CARD_ICDC_SUBFORM","card_info_credit_request")%>&nbsp;:</td>
        <td class="inputCol" width="30%"><%=HTMLRenderUtil.DisplayInputCurrency(cardM.getReqCreditLine(),displayMode,"########0.00","card_info_credit_request","textbox-currency","","12",false) %> </td>
        <td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "OLD_CREDIT_LINE",ORIGUser,personalM.getCustomerType(),"CARD_ICDC_SUBFORM","card_info_current_credit") %><%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"CARD_ICDC_SUBFORM","card_info_current_credit")%>&nbsp;:</td>
        <td class="inputCol" width="30%"><%=HTMLRenderUtil.DisplayInputCurrency(cardM.getCurCreditLine(),oldCreditDisplayMode,"########0.00","card_info_current_credit","textbox-currency","","12",false) %> </td>
      </tr>
</table>
<%=HTMLRenderUtil.displayHiddenField(cardM.getCardNo(), "card_no_icdc_hidden")%>