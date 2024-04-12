<%@page import="com.eaf.orig.ulo.model.cardMaintenance.CardMaintenanceDataM"%>
<%@page import="com.eaf.orig.ulo.model.cardMaintenance.CardMaintenanceDetailDataM"%>
<%@page import="com.eaf.orig.ulo.model.cardMaintenance.PersonalCardMaintenanceDataM"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="com.eaf.orig.ulo.control.util.ApplicationUtil"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.core.ulo.common.properties.ListBoxControl"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConfig"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.FormControl"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="CardMaintenanceForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.CardMaintenanceFormHandler"/>
<script type="text/javascript" src="orig/ulo/cardMaintenance/js/cardMaintenanceSubForm.js"></script>
<%
	CardMaintenanceDataM cardMaintenance = (CardMaintenanceDataM)CardMaintenanceForm.getObjectForm();
	Logger logger = Logger.getLogger(this.getClass());
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	String success = SystemConstant.getConstant("CARDLINK_FLAG_SUCCESS");
	String fail = SystemConstant.getConstant("CARDLINK_FLAG_FAIL");
	String cancel = SystemConstant.getConstant("CARDLINK_CANCEL_FLAG");
	String wait = SystemConstant.getConstant("CARDLINK_BATCH_WAITING");
	String alert = SystemConfig.getGeneralParam("CARD_MAINTENANCE");
	String mode = HtmlUtil.EDIT;
	String APPLICATION_GROUP_ID = cardMaintenance.getApplicationGroupId();
	String personalValidate = "";
	String cardValidate = "";
	String sendCardlinkFlag = FormatUtil.display(cardMaintenance.getSendCardklinkFlag());
	if(fail.equals(sendCardlinkFlag)){
		sendCardlinkFlag = HtmlUtil.getText(request, "CM_FAIL");
	}else if(success.equals(sendCardlinkFlag)){
		sendCardlinkFlag = HtmlUtil.getText(request, "CM_SUCCESS");
	}else if(wait.equals(sendCardlinkFlag)){
		sendCardlinkFlag = HtmlUtil.getText(request, "CM_BATCH_WAITING");
	}else if(cancel.equals(sendCardlinkFlag)){
		sendCardlinkFlag = HtmlUtil.getText(request, "CM_CANCEL");
	}
 %>
<div class="row">
	<div class="col-md-12 nopadding-right" style="border-left: solid 2px #FFF;background-color: #fff;">
		<div class="row">
			<div class="col-xs-12">
				<div class="panel panel-default">
					<div class="panel-body">
						<div class="row form-horizontal">
							<div class="col-sm-6">
								<div class="form-group">
									<%=HtmlUtil.getFieldLabel(request, "CM_APPLICATION_GROUP_NO", "col-sm-4 col-md-5 control-label")%>
									<%=cardMaintenance.getApplicationGroupNo() %>
								</div>
							</div>
							<div class="col-sm-6">
								<div class="form-group">
									<%=HtmlUtil.getFieldLabel(request, "CM_CARDLINK_REF_NO", "col-sm-4 col-md-5 control-label")%>
									<%=cardMaintenance.getCardlinkRefNo() %>
								</div>
							</div>		
							<div class="col-sm-6">
								<div class="form-group">
									<%=HtmlUtil.getFieldLabel(request, "CM_CARDLINK_FLAG", "col-sm-4 col-md-5 control-label")%>
									<%=sendCardlinkFlag %>					
								</div>
							</div>
							<div class="col-sm-6">
								<div class="form-group">
									<%=HtmlUtil.getFieldLabel(request, "CM_SEND_LATEST_CARD_REF_NO_DATE", "col-sm-4 col-md-5 control-label")%>
									<%=cardMaintenance.getProcessingDate() %>
								</div>
							</div>	
						</div>
					</div>
				</div>
			</div>
		</div>
		<div><%=HtmlUtil.getFieldLabel(request, "CM_ACC_SETUP_RESULT", "col-sm-4 col-md-5 control-label")%></div>
		<table class="table table-hover table-striped">
			
				<%if(!Util.empty(cardMaintenance)){ 
 					ArrayList<PersonalCardMaintenanceDataM> personalCardMaintenances = cardMaintenance.getPersonalCardMaintenances();
 				%> 
					<%for(PersonalCardMaintenanceDataM personalCardMaintenance : personalCardMaintenances){ %>
						<%
 							String LABEL_CUSTOMER_TYPE = "";
 							String personalStatus = personalCardMaintenance.getPersonalStatus();
 							String personalErrorDesc = "";
 							String personalStatusLabel = null;
 							String personalErrorLabel = "";
 							String personalErrorDropdown = "";
 							String redLabel = "";
 							String personalCancelReason = "";
 							//String personalActionJS = "onclick=personalActionJS('"+personalCardMaintenance.getPersonalType()+"')";
 							String asterisk = "<span class='text-danger'>*</span>";
 							if(fail.equals(personalStatus)){
 								personalStatusLabel = HtmlUtil.getText(request, "CM_FAIL");
 								redLabel = "color:#fb2628;";
 							}else if(cancel.equals(personalStatus)){
 								personalStatusLabel = HtmlUtil.getText(request, "CM_CANCEL");
 							}else if(wait.equals(personalStatus)){
 								personalStatusLabel = HtmlUtil.getText(request, "CM_BATCH_WAITING");
 							}else{
 								personalStatus = success;
 								personalStatusLabel = HtmlUtil.getText(request, "CM_SUCCESS");
 							}
 							if(PERSONAL_TYPE_APPLICANT.equals(personalCardMaintenance.getPersonalType())){
 								LABEL_CUSTOMER_TYPE =LabelUtil.getText(request, "CM_MAIN_CUSTOMER");
 								personalErrorDesc = personalCardMaintenance.getErrorCustDesc();
 							}else if(PERSONAL_TYPE_SUPPLEMENTARY.equals(personalCardMaintenance.getPersonalType())){
 								LABEL_CUSTOMER_TYPE =LabelUtil.getText(request, "CM_SUP_CUSTOMER");
 								personalErrorDesc = personalCardMaintenance.getErrorSupCustDesc();
 							}
 							if(fail.equals(personalStatus)){
								personalErrorLabel = LabelUtil.getText(request, "CM_FIXING")+asterisk+" : ";
								String fieldId = "PERSONAL_CARD_MAINTENANCE_ACTION_"+personalCardMaintenance.getPersonalType();
								personalErrorDropdown = HtmlUtil.dropdown(fieldId,"",SystemConstant.getConstant("FIELD_ID_FIX_CARDLINK"),mode,"",request);
								personalValidate = fieldId+","+personalValidate;
								personalCancelReason = "<div name='PERSONAL_CARD_CANCEL_REASON_DIV_"+personalCardMaintenance.getPersonalType()+"' style='display: none;' >" + HtmlUtil.getText(request, "REASON", "", "") +
								HtmlUtil.textarea("PERSONAL_CARD_CANCEL_REASON_"+personalCardMaintenance.getPersonalType(), "", "", "4", "8", "250", "", "style='overflow:auto;resize:none;'", "col-sm-12", request)
								+ "</div>";
							}else{
								personalErrorLabel = "";
							}
 						%> 
						<tr>
							<td  class="text_center"><%=LABEL_CUSTOMER_TYPE %></td>
							<td ><%=LabelUtil.getText(request, "CM_CUSTOMER_NO")+" : "+FormatUtil.display(personalCardMaintenance.getCustNo()) +"	"+FormatUtil.display(personalCardMaintenance.getFirstname())+" "+FormatUtil.display(personalCardMaintenance.getLastname())%></td>
							<td class="text_center"></td>
							<td class="text_center"></td>
							<td style="<%=redLabel %>" class="text_center"><%= personalStatusLabel%></td>
							<td class="text_center"><%=personalErrorDesc %></td>
							<td style="text-align: right !important;"><%=personalErrorLabel %></td>
							<td style="text-align: left !important;"><%=personalErrorDropdown %></td>
							<td><%=personalCancelReason%></td>
						</tr>
						<%int cardIndex = 0;
						for(CardMaintenanceDetailDataM cardMaintenanceDetail : personalCardMaintenance.getCardMaintenanceDetails()){ %>
						<%
 							String cardStatus = cardMaintenanceDetail.getCardStatus();
 							String cardStatusLabel = null;
 							String cardErrorLabel = "";
 							String cardErrorDesc = "";
 							redLabel = "";
 							String cardErrorDropdown = "";
 							String cardCancelReason = "";
 							if(fail.equals(cardStatus)){
 								cardStatusLabel = HtmlUtil.getText(request, "CM_FAIL");
 								redLabel = "color:#fb2628;";
 							}else if(cancel.equals(cardStatus)){
 								cardStatusLabel = HtmlUtil.getText(request, "CM_CANCEL");
 							}else if(wait.equals(cardStatus)){
 								cardStatusLabel = HtmlUtil.getText(request, "CM_BATCH_WAITING");
 							}else{
 								cardStatus = success;
 								cardStatusLabel = HtmlUtil.getText(request, "CM_SUCCESS");
 							}
							if(fail.equals(cardMaintenanceDetail.getCardStatus())&&(success.equals(personalStatus)||wait.equals(personalStatus))){
								cardErrorLabel = LabelUtil.getText(request, "CM_FIXING")+asterisk+" : ";
								String fieldId = "CARD_MAINTENANCE_ACTION_"+cardMaintenanceDetail.getApplicationType();
								cardErrorDropdown = HtmlUtil.dropdown(fieldId,cardMaintenanceDetail.getCardType(),"","",SystemConstant.getConstant("FIELD_ID_FIX_CARDLINK"),"",mode,"",request);
								String fullFieldId = fieldId+"_"+cardMaintenanceDetail.getCardType();
								cardValidate = fullFieldId+","+cardValidate;
								String appType_cardType = cardMaintenanceDetail.getApplicationType()+"_"+cardMaintenanceDetail.getCardType();
								cardCancelReason = "<div name='CARD_CANCEL_REASON_DIV_"+appType_cardType+"' style='display: none;' >" + HtmlUtil.getText(request, "REASON", "", "") +
								HtmlUtil.textarea("CARD_CANCEL_REASON_"+ appType_cardType, "", "", "4", "8", "250", "", "style='overflow:auto;resize:none;'", "col-sm-12", request)
								+ "</div>";
							}else{
								cardErrorLabel = "";
							}
							
							if(null!=cardMaintenanceDetail.getCardNo()&&null!=cardMaintenanceDetail.getMainCardNo1()&&cardMaintenanceDetail.getCardNo().equals(cardMaintenanceDetail.getMainCardNo1())){
								cardErrorDesc = cardMaintenanceDetail.getErrorCardDesc1();
							}
							else if(null!=cardMaintenanceDetail.getCardNo()&&null!=cardMaintenanceDetail.getMainCardNo2()&&cardMaintenanceDetail.getCardNo().equals(cardMaintenanceDetail.getMainCardNo2())){
								cardErrorDesc = cardMaintenanceDetail.getErrorCardDesc2();
							}
							else if(null!=cardMaintenanceDetail.getCardNo()&&null!=cardMaintenanceDetail.getMainCardNo3()&&cardMaintenanceDetail.getCardNo().equals(cardMaintenanceDetail.getMainCardNo3())){
								cardErrorDesc = cardMaintenanceDetail.getErrorCardDesc3();
							}
							else if(null!=cardMaintenanceDetail.getCardNo()&&null!=cardMaintenanceDetail.getSupCardNo1()&&cardMaintenanceDetail.getCardNo().equals(cardMaintenanceDetail.getSupCardNo1())){
								cardErrorDesc = cardMaintenanceDetail.getErrorSupCardDesc1();
							}
							else if(null!=cardMaintenanceDetail.getCardNo()&&null!=cardMaintenanceDetail.getSupCardNo2()&&cardMaintenanceDetail.getCardNo().equals(cardMaintenanceDetail.getSupCardNo2())){
								cardErrorDesc = cardMaintenanceDetail.getErrorSupCardDesc2();
							}
							else if(null!=cardMaintenanceDetail.getCardNo()&&null!=cardMaintenanceDetail.getSupCardNo3()&&cardMaintenanceDetail.getCardNo().equals(cardMaintenanceDetail.getSupCardNo3())){
								cardErrorDesc = cardMaintenanceDetail.getErrorSupCardDesc3();
							}
 						%> 
						<tr>
							<td class="text_center"><%=++cardIndex %></td>
							<td ><%=LabelUtil.getText(request, "CM_CARD_NO")+" : "+FormatUtil.display(cardMaintenanceDetail.getCardNoMark())+"    "+FormatUtil.display(cardMaintenanceDetail.getCardDesc())%></td>
							<td ></td>
							<td class="text_center"><%=Util.empty(cardMaintenanceDetail.getApplyType())?FormatUtil.display(""):(LabelUtil.getText(request, "CM_APPLY_TYPE")+" : "+cardMaintenanceDetail.getApplyType())%></td>
							<td style="<%=redLabel %>" class="text_center"><%=FormatUtil.display(cardStatusLabel)%></td>
							<td class="text_center"><%=cardErrorDesc %></td>
							<td style="text-align: right !important;"><%=cardErrorLabel %></td>
							<td style="text-align: left !important;"><%=cardErrorDropdown %></td>
							<td><%=cardCancelReason%></td>
						</tr>
						<tfoot>
						</tfoot>
						<%} %>
					<%} %>
				<%}else{ %>
				<tr>
					<td colspan="8"></td>
				</tr>
				<%} 
					if(personalValidate.lastIndexOf(",")>-1){
						personalValidate = personalValidate.substring(0, personalValidate.length()-1);
					}
					if(cardValidate.lastIndexOf(",")>-1){
						cardValidate = cardValidate.substring(0, cardValidate.length()-1);
					}
				%>
		</table>
		<div></div>
	</div>
</div>
<%=HtmlUtil.hidden("PERSONAL_VALIDATE",personalValidate)%>
<%=HtmlUtil.hidden("CARD_VALIDATE",cardValidate)%>