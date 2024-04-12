<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.core.ulo.common.properties.FormControl"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.orig.ulo.app.view.form.subform.product.manual.CardInfoUtil"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.WisdomDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.LoanDataM"%>
<%@page import="com.eaf.orig.cache.properties.CardTypeProperties"%>
<%@page import="com.eaf.orig.ulo.model.app.CardDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ModuleForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler"/>

<script type="text/javascript" src="orig/ulo/product/js/OthersInfoSubForm.js"></script>
<%
	String subformId = "K_EXPRESS_CACH_PRODUCT_POPUP_2";
	Logger logger = Logger.getLogger(this.getClass());
	String PRODUCT_K_EXPRESS_CASH =SystemConstant.getConstant("PRODUCT_K_EXPRESS_CASH");	
	ApplicationDataM applicationItem = (ApplicationDataM)ModuleForm.getObjectForm();
	CardDataM cardM = applicationItem.getCard();
	String CACHE_CARD_INFO = SystemConstant.getConstant("CACHE_CARD_INFO");	
	if(Util.empty(cardM)){
		cardM = new CardDataM();
	}
	String displayMode = HtmlUtil.EDIT;
	HashMap<String,Object> CardInfo = CardInfoUtil.getCardInfo(cardM.getCardType());	
		String cardCode =(String)CardInfo.get("CARD_CODE");	
		String cardLevel =(String)CardInfo.get("CARD_LEVEL");			
 	
	String FIELD_ID_CARD_TYPE = SystemConstant.getConstant("FIELD_ID_CARD_TYPE");
	String FIELD_ID_CARD_LEVEL = SystemConstant.getConstant("FIELD_ID_CARD_LEVEL");
	String CARD_TYPE_DESC =	CacheControl.getName(FIELD_ID_CARD_TYPE, cardCode, "DISPLAY_NAME");
	String CARD_LEVEL_DESC = CacheControl.getName(FIELD_ID_CARD_LEVEL, cardLevel, "DISPLAY_NAME");
	
	String applicationRecordId = applicationItem.getApplicationRecordId();
	ApplicationGroupDataM applicationGroup = FormControl.getOrigObjectForm(request);
	PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfoByRelation(applicationRecordId);
	if(null == personalInfo){
		personalInfo = new PersonalInfoDataM();
	}
	String productElementId = FormatUtil.getProductElementId(personalInfo,PRODUCT_K_EXPRESS_CASH);	
		
	FormUtil formUtil = new FormUtil(subformId,request);
%>

	<div class="panel panel-default">
		 <div class="panel-body">
			<div class="row form-horizontal">
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.getSubFormLabel(request,subformId,"DM_FIRST_NAME","DM_FIRST_NAME","col-sm-4 col-md-5 control-label")%>
						<div class="col-sm-8 col-md-7"><%=FormatUtil.display("")%></div>
					</div>
				</div>
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.getSubFormLabel(request,subformId,"CARD_TYPE","CARD_TYPE","col-sm-4 col-md-5 control-label")%>
						<div class="col-sm-8 col-md-7"><%=FormatUtil.display("")%></div>
					</div>
				</div>
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.getSubFormLabel(request,subformId,"GROUP_NO","GROUP_NO","col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.textBox("GROUP_NO",PRODUCT_K_EXPRESS_CASH,"GROUP_NO_"+productElementId,"GROUP_NO",
							cardM.getGangNo(),"","5","","col-sm-8 col-md-7",formUtil)%>
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.getFieldLabel(request, "NO_APP_IN_GANG","col-sm-5 col-md-6 control-label")%>
						<%=HtmlUtil.numberBox("NO_APP_IN_GANG", PRODUCT_K_EXPRESS_CASH, "NO_APP_IN_GANG_"+productElementId, "NO_APP_IN_GANG", 
							cardM.getNoAppInGang(), "", "", "", "", false, "5", "", "col-sm-7 col-md-6", formUtil) %>
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.getFieldLabel(request, "CAMPAIGN_CODE","col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.textBox("CAMPAIGN_CODE",PRODUCT_K_EXPRESS_CASH,"CAMPAIGN_CODE_"+productElementId,"CAMPAIGN_CODE",
							cardM.getCampaignCode(),"","5","","col-sm-8 col-md-7",formUtil)%>
					</div>
				</div>
				
			</div>
		</div>
	</div>

<%-- 


<section class="table">
<table>
	<tbody>
		<tr>
			<td>&nbsp;</td>
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "NAME")%></td>
			<td><%=LabelUtil.getText(request,"")%></td>
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "CARD_TYPE")%></td>
			<td><%=LabelUtil.getText(request,"")%></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "GROUP_NO")%></td>
			<td><%=HtmlUtil.textBox("GROUP_NO",cardM.getGangNo(),"","5","","",request)%></td>
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "NO_APP_IN_GANG")%></td>
			<td><%=HtmlUtil.numberBox("NO_APP_IN_GANG",cardM.getNoAppInGang(),"",false,"5","","",request)%></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "CAMPAIGN_CODE")%></td>
			<td><%=HtmlUtil.textBox("CAMPAIGN_CODE",cardM.getCampaignCode(),"","5","","",request)%></td>
			<td></td>
			<td></td>
		</tr>
	</tbody>
</table>
</section>
 --%>
