<%@page import="com.eaf.core.ulo.common.util.FormEffects"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.AddressDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.orig.ulo.control.util.PersonalAddressUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ModuleForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler"/>

<script type="text/javascript" src="orig/ulo/popup/js/AddressPopup.js"></script>
<%
	String subformId = "CARDLINK_HOME_ADDRESS_POPUP";
	Logger logger = Logger.getLogger(this.getClass());
// 	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String PERSONAL_TYPE = ModuleForm.getRequestDataString("PERSONAL_TYPE");
	String PERSONAL_SEQ = ModuleForm.getRequestDataString("PERSONAL_SEQ");
		
	String ADDRESS_TYPE_DOCUMENT_CARDLINK = SystemConstant.getConstant("ADDRESS_TYPE_DOCUMENT_CARDLINK");
	int  MIN_WORK_CARD_LINK_LINE_1 = Integer.parseInt(SystemConstant.getConstant("MIN_WORK_CARD_LINK_LINE_1"));
	int  MIN_WORK_CARD_LINK_LINE_2 = Integer.parseInt(SystemConstant.getConstant("MIN_WORK_CARD_LINK_LINE_2"));
	
	HashMap<String,AddressDataM> hashAddress = (HashMap<String,AddressDataM>)ModuleForm.getObjectForm();
	AddressDataM AddressDocumentCardlink = hashAddress.get(ADDRESS_TYPE_DOCUMENT_CARDLINK);
	if(null == AddressDocumentCardlink){
		AddressDocumentCardlink = new AddressDataM();
	}
	
	String displayMode = HtmlUtil.EDIT;
	
// 	String TAG_SMART_DATA_DOCUMENT_CARDLINK_ADDRESS = FormatUtil.getSmartDataEntryId(ADDRESS_TYPE_DOCUMENT_CARDLINK,PERSONAL_TYPE);
	
	String addressElementId = FormatUtil.getAddressElementId(PERSONAL_TYPE,PERSONAL_SEQ,ADDRESS_TYPE_DOCUMENT_CARDLINK);
	
	FormUtil formUtil = new FormUtil(subformId,request);
	FormEffects formEffect = new FormEffects(subformId,FormEffects.ConfigType.FORM,request);
	
	String CARDLINK_PREFIX = "CL_";
		
// 	PersonalAddressUtil addressUtil = new PersonalAddressUtil();	
%>
<%=HtmlUtil.hidden("CARD_LINK", "") %>
<div class="panel panel-default" id="cardlinkTable">
<div class="panel-heading">
	<%=LabelUtil.getText(request, "ADDRESS1")%>
</div>
<div class="panel-body"> 
<div class="row form-horizontal">
	<div class="col-sm-8">
		<div class="form-group"></div>
	</div>
	<div class="col-sm-4">
		<div class="form-group">
			<%=HtmlUtil.button("BTN_COPPY_HOME","COPY_HOME_BTN","COPPY_HOME","","",formEffect) %>
		</div>
	</div>
	<div class="clearfix"></div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"ADDRESS_ID_"+ADDRESS_TYPE_DOCUMENT_CARDLINK,"ADDRESS_ID","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBox(CARDLINK_PREFIX+"ADDRESS_ID","","ADDRESS_ID_"+addressElementId,"ADDRESS_ID",
				AddressDocumentCardlink.getAddress(),"","8","","col-sm-8 col-md-7",AddressDocumentCardlink,formUtil)%>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"MOO_"+ADDRESS_TYPE_DOCUMENT_CARDLINK,"MOO","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBox(CARDLINK_PREFIX+"MOO","","MOO_"+addressElementId,"MOO",
				AddressDocumentCardlink.getMoo(),"","2","","col-sm-8 col-md-7",AddressDocumentCardlink,formUtil)%>
		</div>
	</div>
	<div class="clearfix"></div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"MOO_BUILDING_SOI_"+ADDRESS_TYPE_DOCUMENT_CARDLINK,"MOO_BUILDING_SOI","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBox(CARDLINK_PREFIX+"SOI","","SOI_"+addressElementId,"MOO_BUILDING_SOI",
				AddressDocumentCardlink.getSoi(),"","15","","col-sm-8 col-md-7",AddressDocumentCardlink,formUtil)%>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"ROAD_"+ADDRESS_TYPE_DOCUMENT_CARDLINK,"ROAD","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBox(CARDLINK_PREFIX+"ROAD","","ROAD_"+addressElementId,"ROAD",
				AddressDocumentCardlink.getRoad(),"","20","","col-sm-8 col-md-7",AddressDocumentCardlink,formUtil)%>
		</div>
	</div>
	<div class="clearfix"></div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"TAMBOL_"+ADDRESS_TYPE_DOCUMENT_CARDLINK,"TAMBOL","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.hidden(CARDLINK_PREFIX+"ADDRESS_FORMAT", AddressDocumentCardlink.getAddressFormat()) %>
			<%=HtmlUtil.textBox(CARDLINK_PREFIX+"TAMBOL","", "TAMBOL_"+addressElementId, "TAMBOL", 
				AddressDocumentCardlink.getTambol(), "", "15", "", "col-sm-8 col-md-7",AddressDocumentCardlink, formUtil) %>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"AMPHUR_"+ADDRESS_TYPE_DOCUMENT_CARDLINK,"AMPHUR","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBox(CARDLINK_PREFIX+"AMPHUR","","AMPHUR_"+addressElementId,"AMPHUR",
				AddressDocumentCardlink.getAmphur(),"","15","","col-sm-8 col-md-7",AddressDocumentCardlink,formUtil)%>
		</div>
	</div>
	<div class="clearfix"></div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"PROVINCE_"+ADDRESS_TYPE_DOCUMENT_CARDLINK,"PROVINCE","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBox(CARDLINK_PREFIX+"PROVINCE","","PROVINCE_"+addressElementId,"PROVINCE",
				AddressDocumentCardlink.getProvinceDesc(),"","15","","col-sm-8 col-md-7",AddressDocumentCardlink,formUtil)%>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"ZIPCODE_"+ADDRESS_TYPE_DOCUMENT_CARDLINK,"ZIPCODE","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBoxZipCode(CARDLINK_PREFIX+"ZIPCODE","", "ZIPCODE_"+addressElementId, "ZIPCODE", 
			AddressDocumentCardlink.getZipcode(), "", "5", "", "col-sm-8 col-md-7", AddressDocumentCardlink, formUtil) %>
		</div>
	</div>
	<div class="clearfix"></div>
</div>
</div>
<%
	String styleCardLinkLine1 ="color: #00EE00;";
	String styleCardLinkLine2 ="color: #00EE00;";
	String cardlinkLine1 = PersonalAddressUtil.getDocCardlinkLine1(request,AddressDocumentCardlink);
	String cardlinkLine2 = PersonalAddressUtil.getCardlinkLine2(request,AddressDocumentCardlink);	
  	if(!Util.empty(cardlinkLine1) && cardlinkLine1.length() > MIN_WORK_CARD_LINK_LINE_1 ){
		styleCardLinkLine1="color: #FF0000;";
	}
	if(!Util.empty(cardlinkLine2) && cardlinkLine2.length() > MIN_WORK_CARD_LINK_LINE_2){
		styleCardLinkLine2 ="color: #FF0000;";
	}  
 %>
<div class="panel-heading"><%=LabelUtil.getText(request, "EXP_CARD_LINK")%></div>
<div class="panel-body"> 
<div class="row form-horizontal">
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getFieldLabel(request, "LINE1", "col-sm-4 col-md-5 control-label")%>
			<div class="col-sm-8 col-md-7" id="ADDRESS_CARD_LINK_LINE1_<%=addressElementId %>"><%=FormatUtil.display(cardlinkLine1)%></div>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getFieldLabel(request, "LENGTH", "col-sm-4 col-md-5 control-label")%>
			<div class="col-sm-8 col-md-7"  id="COUNT_ADDRESS_CARD_LINK_LINE1_<%=addressElementId %>"  style="<%=styleCardLinkLine1 %>"><%=FormatUtil.display(cardlinkLine1.length())%></div>
		</div>
	</div>
	<div class="clearfix"></div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getFieldLabel(request, "LINE2", "col-sm-4 col-md-5 control-label")%>
			<div class="col-sm-8 col-md-7" id="ADDRESS_CARD_LINK_LINE2_<%=addressElementId %>"><%=FormatUtil.display(cardlinkLine2)%></div>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getFieldLabel(request, "LENGTH", "col-sm-4 col-md-5 control-label")%>
			<div class="col-sm-8 col-md-7"  id="COUNT_ADDRESS_CARD_LINK_LINE2_<%=addressElementId %>" style="<%=styleCardLinkLine2 %>"><%=FormatUtil.display(cardlinkLine2.length())%></div>
		</div>
	</div>
	<div class="clearfix"></div>
</div>
</div>


</div>
