<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.sun.xml.internal.ws.util.UtilException"%>
<%@page import="com.eaf.orig.ulo.model.app.SpecialAdditionalServiceDataM"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="org.apache.log4j.Logger"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<script type="text/javascript" src="orig/ulo/product/js/OthersInfoSubForm.js"></script>
<%
	String subformId = "KEC_OTHER_INFO_SUBFORM";
	Logger logger = Logger.getLogger(this.getClass());
	String PRODUCT_K_EXPRESS_CASH = SystemConstant.getConstant("PRODUCT_K_EXPRESS_CASH");
	String SPECIAL_ADDITIONAL_SERVICE_ATM = SystemConstant.getConstant("SPECIAL_ADDITIONAL_SERVICE_ATM");
	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	PersonalInfoDataM personalInfo = ORIGForm.getObjectForm().getPersonalInfo(PERSONAL_TYPE);
	logger.debug("personalInfo >> "+personalInfo);
	if(Util.empty(personalInfo)){
		personalInfo = new PersonalInfoDataM();
	}
	String personalId = personalInfo.getPersonalId();
	SpecialAdditionalServiceDataM specialAdditionalService = applicationGroup.getSpecialAdditionalServiceLifeCycleByPersonalId(personalId, PRODUCT_K_EXPRESS_CASH, SPECIAL_ADDITIONAL_SERVICE_ATM);
	if(null == specialAdditionalService){
		specialAdditionalService =new SpecialAdditionalServiceDataM();
	}

	int PERSONAL_SEQ = personalInfo.getSeq();
	
// 	String TAG_SMART_DATA_PERSONAL = FormatUtil.getSmartDataEntryId(personalInfo.getPersonalType(),PERSONAL_SEQ);
// 	String TAG_SMART_DATA_PRODUCT = FormatUtil.getSmartDataEntryId(PRODUCT_K_EXPRESS_CASH,TAG_SMART_DATA_PERSONAL);

	String productElementId = FormatUtil.getProductElementId(personalInfo,PRODUCT_K_EXPRESS_CASH);
	
	String displayMode = HtmlUtil.EDIT;
		
	FormUtil formUtil = new FormUtil("PRODUCT_FORM_KEC", subformId,request);
	
	
%>
<div class="panel panel-default">
		 <div class="panel-body">				
				<div class="row form-horizontal">
					<div class="col-sm-12">
						<div class="form-group">
							<%=HtmlUtil.getLabel(request, "OTHERS_DATA","control-label")%>
						</div>
					</div>
					<div class="col-sm-6">
						<div class="form-group">
							<%=HtmlUtil.hidden("COMPLETE_DATA_SAVING_"+productElementId, specialAdditionalService.getCompleteDataSaving()) %>
							<%=HtmlUtil.getSubFormLabel(request,subformId,"SAVING_ACC_NO","SAVING_ACC_NO","col-sm-4 col-md-5 control-label")%>
							<%=HtmlUtil.textBoxAccountNo("SAVING_ACC_NO", productElementId, "SAVING_ACC_NO_"+productElementId, "SAVING_ACC_NO",specialAdditionalService.getSavingAccNo(), "", "", "col-sm-8 col-md-7",applicationGroup, formUtil)%>
						</div>
					</div>
					<div class="col-sm-6">
						<div class="form-group">
							<%=HtmlUtil.getSubFormLabel(request,subformId,"ACC_NAME","ACC_NAME", "col-sm-4 col-md-5 control-label")%>
							<div id="SAVING_ACC_NAME_<%=productElementId%>"><%=FormatUtil.display(specialAdditionalService.getSavingAccName())%></div>
						</div>
					</div>
					<div class="clearfix"></div>
					
					<div class="col-sm-6">
						<div class="form-group">
							<%=HtmlUtil.hidden("COMPLETE_DATA_CURRENT_"+productElementId, specialAdditionalService.getCompleteData()) %>
							<%=HtmlUtil.getSubFormLabel(request,subformId,"CURRENT_ACC_NO","CURRENT_ACC_NO","col-sm-4 col-md-5 control-label")%>
							<%=HtmlUtil.textBoxAccountNo("CURRENT_ACC_NO",  productElementId, "CURRENT_ACC_NO_"+productElementId, "CURRENT_ACC_NO",specialAdditionalService.getCurrentAccNo(), "", "", "col-sm-8 col-md-7",applicationGroup, formUtil)%>
						</div>
					</div>
					 <div class="col-sm-6">
						<div class="form-group">
							<%=HtmlUtil.getSubFormLabel(request,subformId,"ACC_NAME", "ACC_NAME", "col-sm-4 col-md-5 control-label")%>
							<div id="CURRENT_ACC_NAME_<%=productElementId %>"><%=FormatUtil.display(specialAdditionalService.getCurrentAccName())%></div>
						</div>
					</div> 
					<div class="clearfix"></div> 
			</div>
		</div>
</div>
