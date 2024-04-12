<%@page import="com.eaf.core.ulo.common.util.FormEffects"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.AddressDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.orig.ulo.app.view.util.kpl.KPLUtil"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ModuleForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>

<script type="text/javascript" src="orig/ulo/popup/js/AddressPopup.js"></script>
<%	
	Logger logger = Logger.getLogger(this.getClass());
	String subformId = "OFFICE_ADDRESS_POPUP_2";	
	String ADDRESS_TYPE_WORK = SystemConstant.getConstant("ADDRESS_TYPE_WORK");	
	String PERSONAL_TYPE = ModuleForm.getRequestData("PERSONAL_TYPE");
	String PERSONAL_SEQ = ModuleForm.getRequestData("PERSONAL_SEQ");
	
// 	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
		
	HashMap<String,AddressDataM> hashAddress = (HashMap<String,AddressDataM>)ModuleForm.getObjectForm();
	AddressDataM address = hashAddress.get(ADDRESS_TYPE_WORK);
	if(null == address){
		address = new AddressDataM();
	}
	
	String displayMode = HtmlUtil.EDIT;
	
// 	String TAG_SMART_DATA_WORK_ADDRESS = FormatUtil.getSmartDataEntryId(ADDRESS_TYPE_WORK,PERSONAL_TYPE);
// 	if(PERSONAL_TYPE_APPLICANT.equals(PERSONAL_TYPE)){
// 		TAG_SMART_DATA_WORK_ADDRESS = FormatUtil.getSmartDataEntryId(TAG_SMART_DATA_WORK_ADDRESS, PERSONAL_SEQ);
// 	}
	
	String addressElementId = FormatUtil.getAddressElementId(PERSONAL_TYPE,PERSONAL_SEQ,ADDRESS_TYPE_WORK);
	
	String FIELD_ID_ADDRESS_TYPE = SystemConstant.getConstant("FIELD_ID_ADDRESS_TYPE");
	String FIELD_ID_COUNTRY = SystemConstant.getConstant("FIELD_ID_COUNTRY");
	String FIELD_ID_COMPANY_TITLE = SystemConstant.getConstant("FIELD_ID_COMPANY_TITLE");
	
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	String TOPUP_FLAG = KPLUtil.getTopUpFlag(applicationGroup);
	
	FormUtil formUtil = new FormUtil(subformId,request);
	FormEffects formEffect = new FormEffects(subformId,FormEffects.ConfigType.FORM,request);
	logger.debug("address.getCompanyTitle() : "+address.getCompanyTitle());
%>

<div class="panel panel-default" id="addressTable">
<div class="panel-heading"><%=LabelUtil.getText(request, "COMPANY_ADDRESS_DATA")%></div>
<div class="panel-body"> 
<div class="row form-horizontal">
	<div class="col-sm-12">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"COMPANY_NAME"+TOPUP_FLAG,"COMPANY_NAME", "col-sm-2 col-md-5 marge-label control-label")%>	
			<div class="col-sm-10 col-md-9 marge-field">
				<div class="row">
					<div class="col-xs-12">
						<%=HtmlUtil.dropdown("COMPANY_TITLE","", "COMPANY_TITLE_"+addressElementId, "COMPANY_TITLE", "", 
							address.getCompanyTitle(), "dropdown-textbox-1",FIELD_ID_COMPANY_TITLE,"ALL_ALL_ALL","","col-xs-2 col-xs-padding",address, formUtil)%>
						<%=HtmlUtil.autoComplete("COMPANY_NAME", "", "COMPANY_NAME_"+addressElementId, "COMPANY_NAME", "COMPANY_NAME_LISTBOX", 
							address.getCompanyName(), "", "","ALL_ALL_ALL","","col-xs-5 col-xs-padding", "100",address, formUtil)%>
					</div>
				</div>
 			</div>
		</div>
	</div>
	<div class="clearfix"></div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"DEPARTMENT"+TOPUP_FLAG,"DEPARTMENT","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBox("DEPARTMENT","","DEPARTMENT_"+addressElementId,"DEPARTMENT",
				address.getDepartment(),"","20","","col-sm-8 col-md-7",address,formUtil)%>
		</div>
	</div>
	<div class="clearfix"></div>

</div>
</div>

<div class="panel-heading"><%=LabelUtil.getText(request, "ADDRESS1")%></div>
<div class="panel-body"> 
<div class="row form-horizontal" id="">
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"COPY_ADDRESS_TYPE","COPY_ADDRESS_TYPE","col-sm-4 col-md-5 control-label")%>
			<div class="col-sm-8 col-md-7">
				<div class="row">
					<div class="col-xs-12">
						<%=HtmlUtil.dropdown("COPY_ADDRESS_TYPE", "COPY_ADDRESS_TYPE_"+addressElementId, "COPY_ADDRESS_TYPE", "COPY_ADDRESS_TYPE_OFFICE", 
							null, "",FIELD_ID_ADDRESS_TYPE,"ALL_ALL_ALL","","col-xs-8 col-xs-padding", formUtil)%>
						<div class="col-xs-4"><%=HtmlUtil.button("BTN_COPY_ADDRESS","COPY_OFFICE_BTN","COPY_BTN","","",formEffect) %></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"BUILDING"+TOPUP_FLAG,"BUILDING","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBox("BUILDING","","BUILDING_"+addressElementId,"BUILDING",
				address.getBuilding(),"","40","","col-sm-8 col-md-7",address,formUtil)%>
		</div>
	</div>
	<div class="clearfix"></div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"FLOOR"+TOPUP_FLAG,"FLOOR","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBox("FLOOR","","FLOOR_"+addressElementId,"FLOOR",
				address.getFloor(),"","8","","col-sm-8 col-md-7",address,formUtil)%>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"ADDRESS_ID"+TOPUP_FLAG,"ADDRESS_ID","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBox("ADDRESS_ID","","ADDRESS_ID_"+addressElementId,"ADDRESS",
				address.getAddress(),"","15","","col-sm-8 col-md-7",address,formUtil)%>
		</div>
	</div>
	<div class="clearfix"></div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"MOO"+TOPUP_FLAG,"MOO","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBox("MOO","","MOO_"+addressElementId,"MOO",
				address.getMoo(),"","10","","col-sm-8 col-md-7",address,formUtil)%>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"SOI"+TOPUP_FLAG,"SOI","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBox("SOI","","SOI_"+addressElementId,"SOI",
				address.getSoi(),"","40","","col-sm-8 col-md-7",address,formUtil)%>
		</div>
	</div>
	<div class="clearfix"></div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"ROAD"+TOPUP_FLAG,"ROAD","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBox("ROAD","","ROAD_"+addressElementId,"ROAD",
				address.getRoad(),"","40","","col-sm-8 col-md-7",address,formUtil)%>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"COUNTRY"+TOPUP_FLAG,"COUNTRY","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.dropdown("COUNTRY","", "COUNTRY_"+addressElementId, "COUNTRY", "", 
				address.getCountry(), "",FIELD_ID_COUNTRY,"ALL_ALL_ALL","","col-sm-8 col-md-7",address, formUtil)%>
		</div>
	</div>
	<div class="clearfix"></div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"TAMBOL"+TOPUP_FLAG,"TAMBOL","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.hidden("ADDRESS_FORMAT", address.getAddressFormat()) %>
			<%=HtmlUtil.hidden("ADDRESS_ELEMENT_ID", addressElementId) %>
<%-- 			<%=HtmlUtil.popupList("TAMBOL", "TAMBOL_"+TAG_SMART_DATA_WORK_ADDRESS, "TAMBOL",  --%>
<%-- 				address.getTambol(), "", "30", "", "col-sm-8 col-md-7", formUtil) %> --%>
			<%=HtmlUtil.popupList("TAMBOL","","TAMBOL_"+addressElementId, "TAMBOL",
				address.getTambol(), "","30",HtmlUtil.VIEW,"","col-sm-8 col-md-7",address,formUtil) %>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"AMPHUR"+TOPUP_FLAG,"AMPHUR","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBox("AMPHUR","","AMPHUR_"+addressElementId,"AMPHUR",
				address.getAmphur(),"","30","","col-sm-8 col-md-7",address,formUtil)%>
		</div>
	</div>
	<div class="clearfix"></div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"PROVINCE"+TOPUP_FLAG,"PROVINCE","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBox("PROVINCE","","PROVINCE_"+addressElementId,"PROVINCE",
				address.getProvinceDesc(),"","40","","col-sm-8 col-md-7",address,formUtil)%>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"ZIPCODE"+TOPUP_FLAG,"ZIPCODE","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBoxZipCode("ZIPCODE","", "ZIPCODE_"+addressElementId, "ZIPCODE", address.getZipcode(), "", "5", "", "col-sm-8 col-md-7",address, formUtil) %>
		</div>
	</div>
	<div class="clearfix"></div>
</div>
</div>

</div>

<%=HtmlUtil.hidden("THIS_PAGE",ADDRESS_TYPE_WORK) %>
<%=HtmlUtil.hidden("COPY_ADDRESS","") %>
<%=HtmlUtil.hidden("ADRSTS","") %>
<%=HtmlUtil.hidden("RESIDEY","") %>
<%=HtmlUtil.hidden("RESIDEM","") %>	
<%=HtmlUtil.hidden("TOPUP_FLAG",TOPUP_FLAG) %>
