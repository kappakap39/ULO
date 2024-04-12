<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.AddressDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.orig.ulo.app.view.util.kpl.KPLUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<script type="text/javascript" src="orig/ulo/subform/js/AddressSubForm.js"></script>
<%
	String subformId = "NORMAL_ADDRESS_SUBFORM_1";
	Logger logger = Logger.getLogger(this.getClass());	
	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	String applicationStatus = applicationGroup.getApplicationStatus(); 
	
	PersonalInfoDataM personalInfo = ORIGForm.getObjectForm().getPersonalInfo(PERSONAL_TYPE);
	if(null == personalInfo){
		personalInfo = new PersonalInfoDataM();
	}
	int PERSONAL_SEQ = personalInfo.getSeq();
	
	String ADDRESS_TYPE_CURRENT = SystemConstant.getConstant("ADDRESS_TYPE_CURRENT");
	String ADDRESS_TYPE_WORK = SystemConstant.getConstant("ADDRESS_TYPE_WORK");
	String ADDRESS_TYPE_DOCUMENT = SystemConstant.getConstant("ADDRESS_TYPE_DOCUMENT");
	
	AddressDataM currentAddress = personalInfo.getAddress(ADDRESS_TYPE_CURRENT);
	if(null == currentAddress){
		currentAddress = new AddressDataM();
	}
	AddressDataM workAddress = personalInfo.getAddress(ADDRESS_TYPE_WORK);
	if(null == workAddress){
		workAddress = new AddressDataM();
	}	
	AddressDataM documentAddress = personalInfo.getAddress(ADDRESS_TYPE_DOCUMENT);
	if(null == documentAddress){
		documentAddress = new AddressDataM();
	}
	
	String displayMode = HtmlUtil.EDIT;	
	
	String personalElementId = FormatUtil.getPersonalElementId(personalInfo);
	String addressCurrentElementId = FormatUtil.getAddressElementId(personalInfo,ADDRESS_TYPE_CURRENT);
	String addressWorkElementId = FormatUtil.getAddressElementId(personalInfo,ADDRESS_TYPE_WORK);
	String addressDocumentElementId = FormatUtil.getAddressElementId(personalInfo,ADDRESS_TYPE_DOCUMENT);
	
	String FIELD_ID_ADDRESS_STYLE = SystemConstant.getConstant("FIELD_ID_ADDRESS_STYLE");
	String FIELD_ID_ADRSTS = SystemConstant.getConstant("FIELD_ID_ADRSTS");
	String FIELD_ID_SEND_DOC = SystemConstant.getConstant("FIELD_ID_SEND_DOC");
	String FIELD_ID_PLACE_RECEIVE_CARD = SystemConstant.getConstant("FIELD_ID_PLACE_RECEIVE_CARD");
	String SEARCH_BRANCH_INFO = SystemConstant.getConstant("SEARCH_BRANCH_INFO");
	String FIELD_ID_COMPANY_TITLE = SystemConstant.getConstant("FIELD_ID_COMPANY_TITLE");
	
	FormUtil formUtil = new FormUtil(subformId,request);
	
	boolean isKPL = KPLUtil.isKPL(applicationGroup);
	String TOPUP_FLAG = KPLUtil.getTopUpFlag(applicationGroup);
	
%>

<div class="panel panel-default">

<div class="panel-heading"><%=LabelUtil.getText(request, "ADDRESS_SUBFORM_1")%></div>
<div class="panel-body">  		
<div class="row form-horizontal">
	<!-- <div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"ADDRESS_STYLE_"+ADDRESS_TYPE_CURRENT+TOPUP_FLAG,"ADDRESS_STYLE", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.dropdown("ADDRESS_STYLE",ADDRESS_TYPE_CURRENT, "ADDRESS_STYLE_"+addressCurrentElementId, "ADDRESS_STYLE_"+ADDRESS_TYPE_CURRENT, "", 
				currentAddress.getBuildingType(), "",FIELD_ID_ADDRESS_STYLE,"ALL_ALL_ALL","","col-sm-8 col-md-7", formUtil)%>
		</div>
	</div> -->
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"ADRSTS_"+ADDRESS_TYPE_CURRENT+TOPUP_FLAG,"ADRSTS", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.dropdown("ADRSTS",ADDRESS_TYPE_CURRENT, "ADRSTS_"+addressCurrentElementId, "ADRSTS_"+ADDRESS_TYPE_CURRENT, "", 
				currentAddress.getAdrsts(), "",FIELD_ID_ADRSTS,"ALL_ALL_ALL","","col-sm-8 col-md-7", formUtil)%>			
		</div>
	</div>
	<div class="clearfix"></div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"PROVINCE_"+ADDRESS_TYPE_CURRENT,"PROVINCE", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.popupList("PROVINCE",ADDRESS_TYPE_CURRENT,"PROVINCE_"+addressCurrentElementId, "PROVINCE_"+ADDRESS_TYPE_CURRENT,
			currentAddress.getProvinceDesc(), "","50",HtmlUtil.VIEW,"","col-sm-8 col-md-7",currentAddress,formUtil) %>
			<%=HtmlUtil.hidden("PROVINCE_"+ADDRESS_TYPE_CURRENT+"_TYPE", ADDRESS_TYPE_CURRENT) %>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"ZIPCODE_"+ADDRESS_TYPE_CURRENT,"ZIPCODE", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBoxZipCode("ZIPCODE", ADDRESS_TYPE_CURRENT, "ZIPCODE_"+addressCurrentElementId, "ZIPCODE_"+ADDRESS_TYPE_CURRENT, currentAddress.getZipcode(),
			  "", "5", HtmlUtil.READ_ONLY, "col-sm-8 col-md-7", formUtil)%>
		</div>
	</div>
	<%=HtmlUtil.hidden("AMPHUR_"+ADDRESS_TYPE_CURRENT, currentAddress.getAmphur()) %>
	<%=HtmlUtil.hidden("TAMBOL_"+ADDRESS_TYPE_CURRENT, currentAddress.getTambol()) %>
	<div class="clearfix"></div>
</div>
</div>

<div class="panel-heading"><%=LabelUtil.getText(request, "COMPANY_ADDRESS")%></div>
<div class="panel-body">  		
<div class="row form-horizontal">
	<div class="col-sm-12">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"COMPANY_NAME_"+ADDRESS_TYPE_WORK+TOPUP_FLAG,"COMPANY_NAME", "col-sm-2 col-md-5 marge-label control-label")%>	
			<div class="col-sm-10 col-md-9 marge-field">
				<div class="row">
					<div class="col-xs-12">
						<%=HtmlUtil.dropdown("COMPANY_TITLE",ADDRESS_TYPE_WORK, "COMPANY_TITLE_"+addressWorkElementId, "COMPANY_TITLE_"+ADDRESS_TYPE_WORK, "", 
							workAddress.getCompanyTitle(), "",FIELD_ID_COMPANY_TITLE,"ALL_ALL_ALL","","col-xs-2 col-xs-padding", formUtil)%>
						<%=HtmlUtil.autoComplete("COMPANY_NAME",ADDRESS_TYPE_WORK, "COMPANY_NAME_"+addressWorkElementId, "COMPANY_NAME_"+ADDRESS_TYPE_WORK, "COMPANY_NAME_LISTBOX",
							workAddress.getCompanyName(), "", "","ALL_ALL_ALL","","col-xs-5 col-xs-padding", "100", formUtil)%>
					</div>
				</div>
 			</div>
		</div>
	</div>
	<div class="clearfix"></div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"PROVINCE_"+ADDRESS_TYPE_WORK,"PROVINCE", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.popupList("PROVINCE",ADDRESS_TYPE_WORK,"PROVINCE_"+addressWorkElementId, "PROVINCE_"+ADDRESS_TYPE_WORK,
				workAddress.getProvinceDesc(), "","50",HtmlUtil.VIEW,"","col-sm-8 col-md-7",workAddress,formUtil) %>
			<%=HtmlUtil.hidden("PROVINCE_"+ADDRESS_TYPE_WORK+"_TYPE", ADDRESS_TYPE_WORK) %>
		</div>
	</div>	
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"ZIPCODE_"+ADDRESS_TYPE_WORK,"ZIPCODE", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBoxZipCode("ZIPCODE", ADDRESS_TYPE_WORK, "ZIPCODE_"+addressWorkElementId, "ZIPCODE_"+ADDRESS_TYPE_WORK,
			   workAddress.getZipcode(), "", "5", HtmlUtil.READ_ONLY, "col-sm-8 col-md-7", formUtil) %>
		</div>
	</div>
	<%=HtmlUtil.hidden("AMPHUR_"+ADDRESS_TYPE_WORK, workAddress.getAmphur()) %>
	<%=HtmlUtil.hidden("TAMBOL_"+ADDRESS_TYPE_WORK, workAddress.getTambol()) %>
</div>
</div>

<div class="panel-heading"><%=LabelUtil.getText(request, "ADDRESS_DOCUMENT_POPUP")%></div>
<div class="panel-body">  		
<div class="row form-horizontal">
  	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"PROVINCE_"+ADDRESS_TYPE_DOCUMENT,"PROVINCE", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.popupList("PROVINCE",ADDRESS_TYPE_DOCUMENT,"PROVINCE_"+addressDocumentElementId, "PROVINCE_"+ADDRESS_TYPE_DOCUMENT,
				documentAddress.getProvinceDesc(), "","50",HtmlUtil.VIEW,"","col-sm-8 col-md-7",documentAddress,formUtil) %>
				<%=HtmlUtil.hidden("PROVINCE_"+ADDRESS_TYPE_DOCUMENT+"_TYPE", ADDRESS_TYPE_DOCUMENT) %>
		</div>
	</div>
 	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"ZIPCODE_"+ADDRESS_TYPE_DOCUMENT,"ZIPCODE", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBoxZipCode("ZIPCODE", ADDRESS_TYPE_DOCUMENT, "ZIPCODE_"+addressDocumentElementId, "ZIPCODE_"+ADDRESS_TYPE_DOCUMENT,
			   documentAddress.getZipcode(), "", "5", HtmlUtil.READ_ONLY, "col-sm-8 col-md-7", formUtil) %>
		</div>
	</div>
		<%=HtmlUtil.hidden("AMPHUR_"+ADDRESS_TYPE_DOCUMENT, documentAddress.getAmphur()) %>
		<%=HtmlUtil.hidden("TAMBOL_"+ADDRESS_TYPE_DOCUMENT, documentAddress.getTambol()) %>
	<div class="clearfix"></div>
 	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"SEND_DOC","SEND_DOC"+KPLUtil.getKPLFlag(applicationGroup), "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.dropdown("SEND_DOC", "SEND_DOC_"+personalElementId, "SEND_DOC","PLACE_RECEIVE_CARD_"+PERSONAL_TYPE, 
				personalInfo.getPlaceReceiveCard(), "",FIELD_ID_PLACE_RECEIVE_CARD,"ACTIVE","","col-sm-8 col-md-7", formUtil)%>
		</div>
	</div>
<%-- 	<div class="clearfix"></div>
 	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"PLACE_RECEIVE_CARD","PLACE_RECEIVE_CARD", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.dropdown("PLACE_RECEIVE_CARD", "PLACE_RECEIVE_CARD_"+personalElementId, "PLACE_RECEIVE_CARD","PLACE_RECEIVE_CARD_"+PERSONAL_TYPE, 
				personalInfo.getPlaceReceiveCard(), "",FIELD_ID_PLACE_RECEIVE_CARD,"ALL_ALL_ALL","","col-sm-8 col-md-7", formUtil)%>
		</div>
	</div>
 	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"BRANCH_RECEIVE_CARD","BRANCH_RECEIVE_CARD", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.search("BRANCH_RECEIVE_CARD", "BRANCH_RECEIVE_CARD_"+personalElementId, "BRANCH_RECEIVE_CARD",SEARCH_BRANCH_INFO, 
				personalInfo.getBranchReceiveCard(), "","","","","col-sm-8 col-md-7", formUtil)%>
		</div>					
	</div>
		<%=HtmlUtil.hidden("BRANCH_RECEIVE_CARD_NAME", personalInfo.getBranchReceiveCardName()) %> --%>
</div>
</div>
</div>
<%=HtmlUtil.hidden("PERSONAL_SEQ",String.valueOf(PERSONAL_SEQ)) %>
<%=HtmlUtil.hidden("PERSONAL_TYPE", PERSONAL_TYPE) %>
<%=HtmlUtil.hidden("APPLICATION_STATUS",applicationStatus) %>