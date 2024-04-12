<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.AddressDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.orig.ulo.app.view.util.kpl.KPLUtil"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ModuleForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler"/>
<jsp:useBean id="formHandlerManager" scope="session" class="com.eaf.j2ee.pattern.view.form.FormHandleManager"/>
<script type="text/javascript" src="orig/pa/MailingAddressPopup.js"></script>
<%
	String subformId = "MAILING_ADDRESS_POPUP";
	Logger logger = Logger.getLogger(this.getClass());
	
	ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM) ModuleForm.getObjectForm();
	
	logger.debug("MAILING_ADDRESS_POPUP - QR : " + applicationGroup.getApplicationGroupNo());
	
	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String ADDRESS_TYPE_WORK = SystemConstant.getConstant("ADDRESS_TYPE_WORK");
	String FIELD_ID_SEND_DOC = SystemConstant.getConstant("FIELD_ID_SEND_DOC");
	String FIELD_ID_COUNTRY = SystemConstant.getConstant("FIELD_ID_COUNTRY");
	String FIELD_ID_COMPANY_TITLE = SystemConstant.getConstant("FIELD_ID_COMPANY_TITLE");	
	String PERSONAL_SEQ;
	
	PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
	if(null == personalInfo){
		personalInfo = new PersonalInfoDataM();
	}
	
	PERSONAL_SEQ = String.valueOf(personalInfo.getSeq());
	
	AddressDataM address = personalInfo.getAddress(ADDRESS_TYPE_WORK);
	String personalElementId = FormatUtil.getPersonalElementId(personalInfo);
	if(null == address){
		address = new AddressDataM();
	}	
	String displayMode = HtmlUtil.EDIT;	
	String addressElementId = FormatUtil.getAddressElementId(PERSONAL_TYPE,PERSONAL_SEQ,ADDRESS_TYPE_WORK);	
	FormUtil formUtil = new FormUtil(subformId,request);
	
	String isMyTask = applicationGroup.getClaimBy();
	System.out.println("isMyTask = " + isMyTask);
	String mode = (isMyTask != null && isMyTask.equals("true")) ? HtmlUtil.EDIT : HtmlUtil.VIEW ;
	
	String formName = formHandlerManager.getCurrentFormHandler();
	
%>
<div class="col-sm-6">
			<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"SEND_DOC","SEND_DOC"+KPLUtil.getKPLFlag(applicationGroup), "col-sm-4 col-md-5 control-label")%>				
			<%=HtmlUtil.dropdown("SEND_DOC", "SEND_DOC_"+personalElementId, "SEND_DOC","PLACE_RECEIVE_CARD_"+PERSONAL_TYPE, 
				personalInfo.getPlaceReceiveCard(), "",FIELD_ID_SEND_DOC,"ACTIVE","","col-sm-8 col-md-7", formUtil)%>
			</div>
</div>
<div class="clearfix"></div>
<div class="panel panel-default">
<div class="panel-heading"><%=LabelUtil.getText(request, "COMPANY_ADDRESS_DATA")%></div>
<div class="panel-body"> 
	<div class="row form-horizontal">
		<div class="col-sm-12">
			<div class="form-group">
				<%=HtmlUtil.getSubFormLabel(request,subformId,"COMPANY_NAME","COMPANY_NAME", "col-sm-2 col-md-5 marge-label control-label")%>	
				<div class="col-sm-10 col-md-9 marge-field">
					<div class="row">
						<div class="col-xs-12">
							<%=HtmlUtil.dropdown("COMPANY_TITLE", "", "COMPANY_TITLE_"+addressElementId, "COMPANY_NAME", "",
							   address.getCompanyTitle(), "dropdown-textbox-1", FIELD_ID_COMPANY_TITLE, "ALL_ALL_ALL", "", "col-xs-2 col-xs-padding", address, formUtil) %>		
							<%=HtmlUtil.autoComplete("COMPANY_NAME", "", "COMPANY_NAME_"+addressElementId, "COMPANY_NAME", "COMPANY_NAME_LISTBOX",
							  address.getCompanyName(), "", "", "ALL_ALL_ALL", "", "col-xs-5 col-xs-padding", "100", address, formUtil) %>
						</div>
					</div>
	 			</div>
			</div>
		</div>
		<div class="clearfix"></div>
		<div class="col-sm-6">
			<div class="form-group">
				<%=HtmlUtil.getSubFormLabel(request,subformId,"DEPARTMENT","DEPARTMENT","col-sm-4 col-md-5 control-label")%>
				<%=HtmlUtil.textBox("DEPARTMENT","","DEPARTMENT_"+addressElementId,"DEPARTMENT",
					address.getDepartment(),"","20","","col-sm-8 col-md-7",address,formUtil)%>
			</div>
		</div>
	</div>
</div>
<div class="panel-heading"><%=LabelUtil.getText(request, "ADDRESS_SUBFORM")%></div>
<div class="panel-body"> 
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"BUILDING","BUILDING","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBox("BUILDING","","BUILDING_"+addressElementId,"BUILDING",
				address.getBuilding(),"","40","","col-sm-8 col-md-7",address,formUtil)%>
		</div>
	</div>
	<div class="clearfix"></div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"FLOOR","FLOOR","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBox("FLOOR","","FLOOR_"+addressElementId,"FLOOR",
				address.getFloor(),"","8","","col-sm-8 col-md-7",address,formUtil)%>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"ADDRESS_ID","ADDRESS_ID","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBox("ADDRESS_ID","","ADDRESS_ID_"+addressElementId,"ADDRESS",
				address.getAddress(),"","15","","col-sm-8 col-md-7",address,formUtil)%>
		</div>
	</div>
	<div class="clearfix"></div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"MOO","MOO","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBox("MOO","","MOO_"+addressElementId,"MOO",
				address.getMoo(),"","10","","col-sm-8 col-md-7",address,formUtil)%>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"SOI","SOI","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBox("SOI","","SOI_"+addressElementId,"SOI",
				address.getSoi(),"","40","","col-sm-8 col-md-7",address,formUtil)%>
		</div>
	</div>
	<div class="clearfix"></div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"ROAD","ROAD","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBox("ROAD","","ROAD_"+addressElementId,"ROAD",
				address.getRoad(),"","40","","col-sm-8 col-md-7",address,formUtil)%>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"COUNTRY","COUNTRY","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.dropdown("COUNTRY","", "COUNTRY_"+addressElementId, "COUNTRY", "", 
				address.getCountry(), "",FIELD_ID_COUNTRY,"ALL_ALL_ALL","","col-sm-8 col-md-7",address, formUtil)%>
		</div>
	</div>
	<div class="clearfix"></div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"TAMBOL","TAMBOL","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.hidden("ADDRESS_FORMAT", address.getAddressFormat()) %>
			<%=HtmlUtil.hidden("ADDRESS_ELEMENT_ID", addressElementId) %>
			<%=HtmlUtil.popupList("TAMBOL","","TAMBOL_"+addressElementId, "TAMBOL",
				address.getTambol(), "","30",HtmlUtil.VIEW,"","col-sm-8 col-md-7",address,formUtil) %>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"AMPHUR","AMPHUR","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBox("AMPHUR","","AMPHUR_"+addressElementId,"AMPHUR",
				address.getAmphur(),"","30","","col-sm-8 col-md-7",address,formUtil)%>
		</div>
	</div>
	<div class="clearfix"></div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"PROVINCE","PROVINCE","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBox("PROVINCE","","PROVINCE_"+addressElementId,"PROVINCE",
				address.getProvinceDesc(),"","40","","col-sm-8 col-md-7",address,formUtil)%>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"ZIPCODE","ZIPCODE","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBoxZipCode("ZIPCODE","", "ZIPCODE_"+addressElementId, "ZIPCODE", address.getZipcode(), "", "5", "", "col-sm-8 col-md-7",address, formUtil) %>
		</div>
	</div>
</div>
</div>
<div class="clearfix"></div>
<div class="col-md-12 text-center">
	<%=HtmlUtil.button("MAILING_COMPLETE_BTN", "COMPLETE_BTN", mode,(mode == HtmlUtil.EDIT) ? "btn2 btn2-green" : "btn2" , "", request)%>
	<%=HtmlUtil.button("CANCEL_BTN", "CANCEL_BTN", HtmlUtil.EDIT, "btn2", "", request)%>
</div>
<%=HtmlUtil.hidden("appGroupNo",applicationGroup.getApplicationGroupNo())%>
<script>
targetDisplayHtml('SEND_DOC',MODE_VIEW,'SEND_DOC');
targetDisplayHtml('COMPANY_TITLE',MODE_VIEW,'COMPANY_TITLE');
targetDisplayHtml('COMPANY_NAME',MODE_VIEW,'COMPANY_NAME');
targetDisplayHtml('DEPARTMENT',MODE_VIEW,'DEPARTMENT');
targetDisplayHtml('BUILDING',MODE_VIEW,'BUILDING');
targetDisplayHtml('FLOOR',MODE_VIEW,'FLOOR');
targetDisplayHtml('ADDRESS_ID',MODE_VIEW,'ADDRESS_ID');
targetDisplayHtml('MOO',MODE_VIEW,'MOO');
targetDisplayHtml('SOI',MODE_VIEW,'SOI');
targetDisplayHtml('ROAD',MODE_VIEW,'ROAD');
targetDisplayHtml('COUNTRY',MODE_VIEW,'COUNTRY');
targetDisplayHtml('TAMBOL',MODE_VIEW,'TAMBOL');
targetDisplayHtml('AMPHUR',MODE_VIEW,'AMPHUR');
targetDisplayHtml('PROVINCE',MODE_VIEW,'PROVINCE');
targetDisplayHtml('ZIPCODE',MODE_VIEW,'ZIPCODE');
</script>