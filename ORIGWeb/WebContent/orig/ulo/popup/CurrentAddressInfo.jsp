<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.AddressDataM"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.orig.ulo.app.view.util.kpl.KPLUtil"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ModuleForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>

<script type="text/javascript" src="orig/ulo/popup/js/AddressPopup.js"></script>
<%
	String subformId = "CURRENT_ADDRESS_INFO_SUBFORM";
	Logger logger = Logger.getLogger(this.getClass());
	String PERSONAL_TYPE = ModuleForm.getRequestDataString("PERSONAL_TYPE");
	String PERSONAL_SEQ = ModuleForm.getRequestDataString("PERSONAL_SEQ");
	String ADDRESS_TYPE_CURRENT = SystemConstant.getConstant("ADDRESS_TYPE_CURRENT");
		
	HashMap<String,AddressDataM> hashAddress = (HashMap<String,AddressDataM>)ModuleForm.getObjectForm();
	if(null == hashAddress){
		hashAddress = new HashMap<String,AddressDataM>();
	}
	AddressDataM currentAddress = hashAddress.get(ADDRESS_TYPE_CURRENT);
	if(null == currentAddress){
		currentAddress = new AddressDataM();
	}	
	String displayMode = HtmlUtil.EDIT;
	
// 	String TAG_SMART_DATA_CURRENT_ADDRESS = FormatUtil.getSmartDataEntryId(CURRENT_ADDRESS, PERSONAL_TYPE);

	String addressElementId = FormatUtil.getAddressElementId(PERSONAL_TYPE,PERSONAL_SEQ,ADDRESS_TYPE_CURRENT);
	
	String FIELD_ID_ADDRESS_STYLE = SystemConstant.getConstant("FIELD_ID_ADDRESS_STYLE");
	String FIELD_ID_ADRSTS = SystemConstant.getConstant("FIELD_ID_ADRSTS");
	String FIELD_ID_COUNTRY = SystemConstant.getConstant("FIELD_ID_COUNTRY");	
	
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	
	FormUtil formUtil = new FormUtil(subformId,request);
%>
<div class="panel-heading"><%=LabelUtil.getText(request, "ADDRESS1")%></div>
<div class="panel-body"> 
	<div class="row form-horizontal">
		<div class="col-sm-6">
			<div class="form-group">
				<%=HtmlUtil.getSubFormLabel(request,subformId,"PROVINCE","PROVINCE","col-sm-4 col-md-5 control-label")%>
				<%=HtmlUtil.popupList("PROVINCE","","PROVINCE_"+addressElementId, "PROVINCE",
					currentAddress.getProvinceDesc(), "","40",HtmlUtil.VIEW,"","col-sm-8 col-md-7",currentAddress,formUtil) %>				
			</div>
		</div>
		<div class="col-sm-6">
			<div class="form-group">
				<%=HtmlUtil.getSubFormLabel(request,subformId,"ZIPCODE","ZIPCODE","col-sm-4 col-md-5 control-label")%>
				<%=HtmlUtil.textBoxZipCode("ZIPCODE", "ZIPCODE_"+addressElementId, "ZIPCODE", currentAddress.getZipcode(), "", "5", HtmlUtil.READ_ONLY, "col-sm-8 col-md-7", formUtil) %>
			</div>
		</div>
		<%=HtmlUtil.hidden("AMPHUR", currentAddress.getAmphur()) %>
		<%=HtmlUtil.hidden("TAMBOL", currentAddress.getTambol()) %>
	</div>
<div class="row form-horizontal">
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"COUNTRY","COUNTRY","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.dropdown("COUNTRY","", "COUNTRY_"+addressElementId, "COUNTRY", "", 
				currentAddress.getCountry(), "",FIELD_ID_COUNTRY,"ALL_ALL_ALL","","col-sm-8 col-md-7",currentAddress, formUtil)%>
		</div>
	</div>
	<div class="col-sm-6"></div>
</div>
</div>