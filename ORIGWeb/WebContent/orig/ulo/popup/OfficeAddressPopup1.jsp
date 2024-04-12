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
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>

<script type="text/javascript" src="orig/ulo/popup/js/AddressPopup.js"></script>
<%
	String subformId = "OFFICE_ADDRESS_POPUP_1";
	Logger logger = Logger.getLogger(this.getClass());
	String PERSONAL_TYPE = ModuleForm.getRequestData("PERSONAL_TYPE");
	String PERSONAL_SEQ = ModuleForm.getRequestData("PERSONAL_SEQ");
	String ADDRESS_TYPE_WORK = SystemConstant.getConstant("ADDRESS_TYPE_WORK");
	HashMap<String,AddressDataM> hashAddress = (HashMap<String,AddressDataM>)ModuleForm.getObjectForm();	
	AddressDataM address = hashAddress.get(ADDRESS_TYPE_WORK);
	if(null == address){
		address = new AddressDataM();
	}	
	String displayMode = HtmlUtil.EDIT;	
// 	String TAG_SMART_DATA_WORK_ADDRESS = FormatUtil.getSmartDataEntryId(AddressDataM.ADDRESS_TYPE.WORK,PERSONAL_TYPE);
	String addressElementId = FormatUtil.getAddressElementId(PERSONAL_TYPE,PERSONAL_SEQ,ADDRESS_TYPE_WORK);	
	String FIELD_ID_COMPANY_TITLE = SystemConstant.getConstant("FIELD_ID_COMPANY_TITLE");	
	String FIELD_ID_COUNTRY = SystemConstant.getConstant("FIELD_ID_COUNTRY");	
	
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	String TOPUP_FLAG = KPLUtil.getTopUpFlag(applicationGroup);
	
	FormUtil formUtil = new FormUtil(subformId,request);
%>

<div class="panel panel-default">
<div class="panel-heading"><%=LabelUtil.getText(request, "COMPANY_ADDRESS_DATA")%></div>
<div class="panel-body"> 
<div class="row form-horizontal">
	<div class="col-sm-12">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"COMPANY_NAME"+TOPUP_FLAG,"COMPANY_NAME", "col-sm-2 col-md-5 marge-label control-label")%>	
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
</div>
</div>
<div class="panel-heading"><%=LabelUtil.getText(request, "ADDRESS1")%></div>
<div class="panel-body"> 
<div class="row form-horizontal">
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"PROVINCE"+TOPUP_FLAG,"PROVINCE","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.popupList("PROVINCE","","PROVINCE_"+addressElementId, "PROVINCE",
					address.getProvinceDesc(), "","40",HtmlUtil.VIEW,"","col-sm-8 col-md-7",address,formUtil) %>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"ZIPCODE"+TOPUP_FLAG,"ZIPCODE","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBoxZipCode("ZIPCODE", "", "ZIPCODE_"+addressElementId, "ZIPCODE", address.getZipcode(), "", "5", HtmlUtil.READ_ONLY, "col-sm-8 col-md-7", address, formUtil) %>
		</div>
	</div>
			<%=HtmlUtil.hidden("AMPHUR", address.getAmphur()) %>
			<%=HtmlUtil.hidden("TAMBOL", address.getTambol()) %>
</div>
<div class="row form-horizontal">
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"COUNTRY"+TOPUP_FLAG,"COUNTRY","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.dropdown("COUNTRY","", "COUNTRY_"+addressElementId, "COUNTRY", "", 
				address.getCountry(), "",FIELD_ID_COUNTRY,"ALL_ALL_ALL","","col-sm-8 col-md-7",address, formUtil)%>
		</div>
	</div>
	<div class="col-sm-6"></div>
</div>
</div>

</div>
<%=HtmlUtil.hidden("ADDRESS_ELEMENT_ID", addressElementId) %>
<%=HtmlUtil.hidden("THIS_PAGE",ADDRESS_TYPE_WORK)%>
<%=HtmlUtil.hidden("COPY_ADDRESS","") %>
<%=HtmlUtil.hidden("TOPUP_FLAG",TOPUP_FLAG) %>

