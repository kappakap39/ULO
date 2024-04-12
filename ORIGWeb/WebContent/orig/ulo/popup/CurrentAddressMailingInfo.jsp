<%@page import="com.eaf.core.ulo.common.util.FormEffects"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.AddressDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.orig.ulo.app.view.util.kpl.KPLUtil"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page contentType="text/html;charset=UTF-8"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM" />
<jsp:useBean id="ModuleForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler" />
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>

<script type="text/javascript" src="orig/ulo/popup/js/AddressPopup.js"></script>
<%
	String subformId = "CURRENT_ADDRESS_MAILING_INFO_SUBFORM";
	Logger logger = Logger.getLogger(this.getClass());
	String PERSONAL_TYPE = ModuleForm.getRequestDataString("PERSONAL_TYPE");
	String PERSONAL_SEQ = ModuleForm.getRequestDataString("PERSONAL_SEQ");
	String ADDRESS_TYPE_CURRENT = SystemConstant.getConstant("ADDRESS_TYPE_CURRENT");

	HashMap<String, AddressDataM> hashAddress = (HashMap<String, AddressDataM>) ModuleForm.getObjectForm();
	AddressDataM currentAddress = hashAddress.get(ADDRESS_TYPE_CURRENT);	
	if (null == currentAddress) {
		currentAddress = new AddressDataM();
	}
	String displayMode = HtmlUtil.EDIT;
	
// 	String TAG_SMART_DATA_CURRENT_ADDRESS = FormatUtil.getSmartDataEntryId(CURRENT_ADDRESS, PERSONAL_TYPE);
// 	TAG_SMART_DATA_CURRENT_ADDRESS = FormatUtil.getSmartDataEntryId(TAG_SMART_DATA_CURRENT_ADDRESS, PERSONAL_SEQ);
	
// 	logger.debug("TAG_SMART_DATA_CURRENT_ADDRESS : "+TAG_SMART_DATA_CURRENT_ADDRESS);

	String addressElementId = FormatUtil.getAddressElementId(PERSONAL_TYPE,PERSONAL_SEQ,ADDRESS_TYPE_CURRENT);
	
	String FIELD_ID_ADDRESS_STYLE = SystemConstant.getConstant("FIELD_ID_ADDRESS_STYLE");
	String FIELD_ID_ADRSTS = SystemConstant.getConstant("FIELD_ID_ADRSTS");
	String FIELD_ID_ADDRESS_TYPE = SystemConstant.getConstant("FIELD_ID_ADDRESS_TYPE");
	String FIELD_ID_COUNTRY = SystemConstant.getConstant("FIELD_ID_COUNTRY");
	
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	String TOPUP_FLAG = KPLUtil.getTopUpFlag(applicationGroup);
	
	FormUtil formUtil = new FormUtil("POPUP_CURRENT_ADDRESS_FORM_2",subformId,request);
	FormEffects formEffect = new FormEffects(subformId,FormEffects.ConfigType.FORM,request);
%>

<div class="panel-heading"><%=LabelUtil.getText(request, "ADDRESS1")%></div>
<div class="panel-body"> 
	<div class="row form-horizontal" id="addressTable" >
		<div class="col-sm-6">
			<div class="form-group">
				<%=HtmlUtil.getSubFormLabel(request,subformId,"COPY_ADDRESS_TYPE","COPY_ADDRESS_TYPE","col-sm-4 col-md-5 control-label")%>
				<div class="col-sm-8 col-md-7">
					<div class="row">
						<div class="col-xs-12">
							<%=HtmlUtil.dropdown("COPY_ADDRESS_TYPE", "COPY_ADDRESS_TYPE_"+addressElementId, "COPY_ADDRESS_TYPE", "COPY_ADDRESS_TYPE_CURRENT", 
								null, "",FIELD_ID_ADDRESS_TYPE,"ALL_ALL_ALL","","col-xs-8 col-xs-padding", formUtil)%>
							<div class="col-xs-4"><%=HtmlUtil.button("BTN_COPY_ADDRESS","COPY_CURRENT_BTN","COPY_BTN","","",formEffect) %></div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="col-sm-6">
			<div class="form-group">
				<%=HtmlUtil.getSubFormLabel(request,subformId,"ADDRESS_NAME","ADDRESS_NAME","col-sm-4 col-md-5 control-label")%>
				<%=HtmlUtil.textBox("ADDRESS_NAME","","ADDRESS_NAME_"+addressElementId,"ADDRESS_NAME",
					currentAddress.getVilapt(),"","40","","col-sm-8 col-md-7",currentAddress,formUtil)%>
			</div>
		</div>
		<div class="clearfix"></div>
		<div class="col-sm-6">
			<div class="form-group">
				<%=HtmlUtil.getSubFormLabel(request,subformId,"BUILDING","BUILDING","col-sm-4 col-md-5 control-label")%>
				<%=HtmlUtil.textBox("BUILDING","","BUILDING_"+addressElementId,"BUILDING",
					currentAddress.getBuilding(),"","40","","col-sm-8 col-md-7",currentAddress,formUtil)%>
			</div>
		</div>
		<div class="col-sm-6">
			<div class="form-group">
				<%=HtmlUtil.getSubFormLabel(request,subformId,"ROOM_ID","ROOM_ID","col-sm-4 col-md-5 control-label")%>
				<%=HtmlUtil.textBox("ROOM","","ROOM_"+addressElementId,"ROOM",
					currentAddress.getRoom(),"","10","","col-sm-8 col-md-7",currentAddress,formUtil)%>
			</div>
		</div>
		<div class="col-sm-6">
			<div class="form-group">
				<%=HtmlUtil.getSubFormLabel(request,subformId,"FLOOR","FLOOR","col-sm-4 col-md-5 control-label")%>
				<%=HtmlUtil.textBox("FLOOR","","FLOOR_"+addressElementId,"FLOOR",
				currentAddress.getFloor(),"","8","","col-sm-8 col-md-7",currentAddress,formUtil)%>
			</div>
		</div>
		<div class="col-sm-6">
			<div class="form-group">
				<%=HtmlUtil.getSubFormLabel(request,subformId,"ADDRESS_ID"+TOPUP_FLAG,"ADDRESS_ID","col-sm-4 col-md-5 control-label")%>
				<%=HtmlUtil.textBox("ADDRESS_ID","","ADDRESS_ID_"+addressElementId,"ADDRESS",
					currentAddress.getAddress(),"","15","","col-sm-8 col-md-7",currentAddress,formUtil)%>
			</div>
		</div>
		<div class="col-sm-6">
			<div class="form-group">
				<%=HtmlUtil.getSubFormLabel(request,subformId,"MOO","MOO","col-sm-4 col-md-5 control-label")%>
				<%=HtmlUtil.textBox("MOO","","MOO_"+addressElementId,"MOO",
					currentAddress.getMoo(),"","10","","col-sm-8 col-md-7",currentAddress,formUtil)%>
			</div>
		</div>
		<div class="col-sm-6">
			<div class="form-group">
				<%=HtmlUtil.getSubFormLabel(request,subformId,"SOI","SOI","col-sm-4 col-md-5 control-label")%>
				<%=HtmlUtil.textBox("SOI","","SOI_"+addressElementId,"SOI",
					currentAddress.getSoi(),"","40","","col-sm-8 col-md-7",currentAddress,formUtil)%>
			</div>
		</div>
		<div class="col-sm-6">
			<div class="form-group">
				<%=HtmlUtil.getSubFormLabel(request,subformId,"ROAD","ROAD","col-sm-4 col-md-5 control-label")%>
				<%=HtmlUtil.textBox("ROAD","","ROAD_"+addressElementId,"ROAD",
					currentAddress.getRoad(),"","40","","col-sm-8 col-md-7",currentAddress,formUtil)%>
			</div>
		</div>
		<div class="col-sm-6">
			<div class="form-group">
				<%=HtmlUtil.getSubFormLabel(request,subformId,"COUNTRY","COUNTRY","col-sm-4 col-md-5 control-label")%>
				<%=HtmlUtil.dropdown("COUNTRY","", "COUNTRY_"+addressElementId, "COUNTRY", "", 
					currentAddress.getCountry(), "",FIELD_ID_COUNTRY,"ALL_ALL_ALL","","col-sm-8 col-md-7",currentAddress, formUtil)%>
			</div>
		</div>
		<div class="clearfix"></div>
		<div class="col-sm-6">
			<div class="form-group">
				<%=HtmlUtil.getSubFormLabel(request,subformId,"TAMBOL"+TOPUP_FLAG,"TAMBOL","col-sm-4 col-md-5 control-label")%>
				<%=HtmlUtil.hidden("ADDRESS_FORMAT", currentAddress.getAddressFormat()) %>
			<%=HtmlUtil.hidden("ADDRESS_ELEMENT_ID", addressElementId) %>
<%-- 				<%=HtmlUtil.popupList("TAMBOL", "TAMBOL_"+TAG_SMART_DATA_CURRENT_ADDRESS, "TAMBOL",  --%>
<%-- 					currentAddress.getTambol(), "", "30", "", "col-sm-8 col-md-7", formUtil) %> --%>
				<%=HtmlUtil.popupList("TAMBOL","","TAMBOL_"+addressElementId, "TAMBOL",
					currentAddress.getTambol(), "","30",HtmlUtil.VIEW,"","col-sm-8 col-md-7",currentAddress,formUtil) %>
			</div>
		</div>
		<div class="col-sm-6">
			<div class="form-group">
				<%=HtmlUtil.getSubFormLabel(request,subformId,"AMPHUR"+TOPUP_FLAG,"AMPHUR","col-sm-4 col-md-5 control-label")%>
				<%=HtmlUtil.textBox("AMPHUR","","AMPHUR_"+addressElementId,"AMPHUR",
					currentAddress.getAmphur(),"","30","","col-sm-8 col-md-7",currentAddress,formUtil)%>
			</div>
		</div>
		<div class="clearfix"></div>
		<div class="col-sm-6">
			<div class="form-group">
				<%=HtmlUtil.getSubFormLabel(request,subformId,"PROVINCE"+TOPUP_FLAG,"PROVINCE","col-sm-4 col-md-5 control-label")%>
				<%=HtmlUtil.textBox("PROVINCE","","PROVINCE_"+addressElementId,"PROVINCE",
					currentAddress.getProvinceDesc(),"","40","","col-sm-8 col-md-7",currentAddress,formUtil)%>
			</div>
		</div>
		<div class="col-sm-6">
			<div class="form-group">
				<%=HtmlUtil.getSubFormLabel(request,subformId,"ZIPCODE"+TOPUP_FLAG,"ZIPCODE","col-sm-4 col-md-5 control-label")%>
				<%=HtmlUtil.textBoxZipCode("ZIPCODE","", "ZIPCODE_"+addressElementId, "ZIPCODE", currentAddress.getZipcode(), "", "5", "", "col-sm-8 col-md-7",currentAddress, formUtil) %>
			</div>
		</div>
	</div>
</div>
<%=HtmlUtil.hidden("TOPUP_FLAG",TOPUP_FLAG)%>