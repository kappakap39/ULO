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
	String subformId = "HOME_ADDRESS_POPUP_1";
	Logger logger = Logger.getLogger(this.getClass());
	String PERSONAL_TYPE = ModuleForm.getRequestData("PERSONAL_TYPE");
	String PERSONAL_SEQ = ModuleForm.getRequestData("PERSONAL_SEQ");
	String ADDRESS_TYPE_DOCUMENT = SystemConstant.getConstant("ADDRESS_TYPE_DOCUMENT");		
	HashMap<String,AddressDataM> hashAddress = (HashMap<String,AddressDataM>)ModuleForm.getObjectForm();
	AddressDataM address = hashAddress.get(ADDRESS_TYPE_DOCUMENT);
	if(null == address){
		address = new AddressDataM();
	}	
	String displayMode = HtmlUtil.EDIT;
// 	String TAG_SMART_DATA_DOCUMENT_ADDRESS = FormatUtil.getSmartDataEntryId(AddressDataM.ADDRESS_TYPE.DOCUMENT,PERSONAL_TYPE);
	String addressElementId = FormatUtil.getAddressElementId(PERSONAL_TYPE,PERSONAL_SEQ,ADDRESS_TYPE_DOCUMENT);	
	
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	String TOPUP_FLAG = KPLUtil.getTopUpFlag(applicationGroup);
	
	FormUtil formUtil = new FormUtil(subformId,request);
%>
<div class="panel panel-default">
<div class="panel-heading"><%=LabelUtil.getText(request, "ADDRESS_DOCUMENT_POPUP")%></div>
<div class="panel-body"> 
<div class="row form-horizontal">
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"PROVINCE"+TOPUP_FLAG,"PROVINCE","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBox("PROVINCE", "", "PROVINCE_"+addressElementId, "PROVINCE", address.getProvinceDesc(), 
			 "", "40", "", "col-sm-8 col-md-7", address, formUtil) %>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"ZIPCODE"+TOPUP_FLAG,"ZIPCODE","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBoxZipCode("ZIPCODE", "", "ZIPCODE_"+addressElementId, "ZIPCODE", address.getZipcode(), "",
			 "5", "", "col-sm-8 col-md-7", address, formUtil) %>
		</div>
	</div>
</div>
</div>
</div>
<%=HtmlUtil.hidden("ADDRESS_ELEMENT_ID", addressElementId) %>
<%=HtmlUtil.hidden("THIS_PAGE",ADDRESS_TYPE_DOCUMENT) %>
<%=HtmlUtil.hidden("COPY_ADDRESS","") %>
<%=HtmlUtil.hidden("TOPUP_FLAG",TOPUP_FLAG) %>
