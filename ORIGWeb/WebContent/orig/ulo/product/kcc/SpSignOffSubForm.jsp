<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<%@page import="com.eaf.core.ulo.common.properties.FormControl"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>

<script type="text/javascript" src="orig/ulo/product/js/SPSignOffSubForm.js"></script>
<%
	String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	Logger logger = Logger.getLogger(this.getClass());
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	if(null == applicationGroup){
		applicationGroup = new ApplicationGroupDataM();
	}
	//Fix 917315 defect Change getApplicationProduct	to filterApplicationProductLifeCycle
	ApplicationDataM applicationItem = applicationGroup.filterApplicationProductLifeCycle(PRODUCT_CRADIT_CARD);
	if(Util.empty(applicationItem)){
		applicationItem = new ApplicationDataM();
	}

	String displayMode = HtmlUtil.EDIT;
	String subformId = "KCC_SP_SIGN_OFF_SUBFORM";

	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");	
	PersonalInfoDataM personalInfo = ORIGForm.getObjectForm().getPersonalInfo(PERSONAL_TYPE);
	if(null == personalInfo){
		personalInfo = new PersonalInfoDataM();
	}
	
	int PERSONAL_SEQ = personalInfo.getSeq();
	
// 	String TAG_SMART_DATA_PERSONAL = FormatUtil.getSmartDataEntryId(PERSONAL_TYPE, PERSONAL_SEQ);
	
	String productElementId = FormatUtil.getProductElementId(personalInfo,PRODUCT_CRADIT_CARD);
	logger.debug("SpSignOffSubForm.productElementId : "+productElementId);
	String formId = "PRODUCT_FORM_CC";
	FormUtil formUtil = new FormUtil(formId , subformId, request);
%>
<!-- <table> -->
<!-- 	<tr><td> -->
<%-- 		<header class="titlecontent"><%=LabelUtil.getText(request,PRODUCT_CRADIT_CARD)%></header> --%>
<!-- 	</td></tr> -->
<!-- </table> -->
<div class="panel panel-default">
	<div class="panel-body">
		<div class="row form-horizontal">
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request,subformId, "SP_SIGN_OFF_DATE", "SP_SIGN_OFF_DATE", "col-sm-4 col-md-5 control-label")%>
					<%=HtmlUtil.calendar("SP_SIGN_OFF_DATE", PRODUCT_CRADIT_CARD,"SP_SIGN_OFF_DATE_"+productElementId, "SP_SIGN_OFF_DATE", 
						applicationItem.getSpSignoffDate(), "", "", HtmlUtil.TH, "col-sm-8 col-md-7",applicationItem, formUtil)%>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request,subformId,"SP_SIGN_OFF_AUTHORIZED_BY","SP_SIGN_OFF_AUTHORIZED_BY","col-sm-4 col-md-5 control-label")%>
					<%=HtmlUtil.textBox("SP_SIGN_OFF_AUTHORIZED_BY",PRODUCT_CRADIT_CARD,"SP_SIGN_OFF_AUTHORIZED_BY_"+productElementId,"SP_SIGN_OFF_AUTHORIZED_BY",
						applicationItem.getSpSignoffAuthBy(),"","100","","col-sm-8 col-md-7",applicationItem,formUtil)%>
				</div>
			</div>
		</div>
	</div>
</div>