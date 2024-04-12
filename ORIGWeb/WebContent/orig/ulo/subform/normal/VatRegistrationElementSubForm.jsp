<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.Collections"%>
<%@page import="com.eaf.orig.ulo.control.address.util.DisplayAddressUtil"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.orig.ulo.model.app.AddressDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM" %>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>

<script type="text/javascript" src="orig/ulo/subform/normal/js/VatRegistrationElementSubForm.js"></script>
<%
	String subformId = "NORMAL_ADDRESS_SUBFORM_3";
	Logger logger = Logger.getLogger(this.getClass());
	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");	
	String ADDRESS_TYPE_VAT = SystemConstant.getConstant("ADDRESS_TYPE_VAT");
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
	if(null == personalInfo){
		personalInfo = new PersonalInfoDataM();
	}
	int PERSONAL_SEQ = personalInfo.getSeq();		
	AddressDataM addressV = personalInfo.getAddress(ADDRESS_TYPE_VAT);
	if(null == addressV){
		addressV = new AddressDataM();
	}	
	String displayMode = HtmlUtil.EDIT;
		
// 	String TAG_SMART_DATA_PERSONAL = FormatUtil.getSmartDataEntryId(PERSONAL_TYPE,PERSONAL_SEQ);
// 	String TAG_SMART_DATA_VAT_ADDRESS = FormatUtil.getSmartDataEntryId(TAG_SMART_DATA_PERSONAL,ADDRESS_TYPE_VAT);	
// 	String tagConfig = "A_";
	String addressVatElementId = FormatUtil.getAddressElementId(personalInfo,ADDRESS_TYPE_VAT);
	
// 	String SUFFIX_VAT_ADDRESS = ADDRESS_TYPE_VAT;
	String[] DISPLAY_ADDRESS_TYPE = SystemConstant.getConstant("DISPLAY_ADDRESS_TYPE").split("\\,");	
	String FIELD_ID_ADDRESS_TYPE = SystemConstant.getConstant("FIELD_ID_ADDRESS_TYPE");
	String FIELD_ID_SEND_DOC = SystemConstant.getConstant("FIELD_ID_SEND_DOC");
	String FIELD_ID_PLACE_RECEIVE_CARD = SystemConstant.getConstant("FIELD_ID_PLACE_RECEIVE_CARD");
	String FIELD_ID_COUNTRY = SystemConstant.getConstant("FIELD_ID_COUNTRY");
	String SEARCH_BRANCH_INFO = SystemConstant.getConstant("SEARCH_BRANCH_INFO");
	String FIELD_ID_VAT_REGIST_FLAG = SystemConstant.getConstant("FIELD_ID_VAT_REGIST_FLAG");
	String FIELD_ID_ESTABLISHMENT_ADDR_FLAG = SystemConstant.getConstant("FIELD_ID_ESTABLISHMENT_ADDR_FLAG");	
	FormUtil formUtil = new FormUtil(subformId,request);
%>
<div class="panel-heading"><%=LabelUtil.getText(request, "VAT_DATA")%></div>
<div class="panel-body"> 
<div class="row form-horizontal">
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"VAT_REGIST_FLAG_"+ADDRESS_TYPE_VAT,"VAT_REGIST_FLAG", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.dropdown("VAT_REGIST_FLAG",ADDRESS_TYPE_VAT, "VAT_REGIST_FLAG_"+addressVatElementId, "VAT_REGIST_FLAG_"+ADDRESS_TYPE_VAT, "",
				addressV.getVatRegistration(), "", FIELD_ID_VAT_REGIST_FLAG,"ALL_ALL_ALL", "", "col-sm-8 col-md-7", formUtil)%>
		</div>					
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"ESTABLISHMENT_ADDR_FLAG_"+ADDRESS_TYPE_VAT,"ESTABLISHMENT_ADDR_FLAG", "col-sm-4 col-md-5 control-label")%>	
			<div class="col-sm-8 col-md-7">
				<div class="row">
					<div class="col-xs-12">
						<%=HtmlUtil.dropdown("ESTABLISHMENT_ADDR_FLAG",ADDRESS_TYPE_VAT, "ESTABLISHMENT_ADDR_FLAG_"+addressVatElementId, "ESTABLISHMENT_ADDR_FLAG_"+ADDRESS_TYPE_VAT, "",
							addressV.getBranchType(), "dropdown-textbox-1", FIELD_ID_ESTABLISHMENT_ADDR_FLAG,"ALL_ALL_ALL", "", "col-xs-4 col-xs-padding", formUtil)%>
						<%=HtmlUtil.textBox("ESTABLISHMENT_ADDR_FLAG_txt",ADDRESS_TYPE_VAT,"ESTABLISHMENT_ADDR_FLAG_txt_"+addressVatElementId,"ESTABLISHMENT_ADDR_FLAG_txt_"+ADDRESS_TYPE_VAT,
							addressV.getBranchName(),"dropdown-textbox-2","100","","col-xs-8 col-xs-padding",formUtil)%>
					</div>
				</div>
 			</div>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getLabel(request, "ESTABLISHMENT_ADDR","control-label")%>
		</div>					
	</div>
	<div class="clearfix"></div>
	<div class="col-md-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"ADDRESS1","ADDRESS1", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBox("ADDRESS1",ADDRESS_TYPE_VAT,"ADDRESS1_"+addressVatElementId,"ADDRESS1_"+ADDRESS_TYPE_VAT,
							addressV.getBranchName(),"dropdown-textbox-2","200","","col-sm-8 col-md-7",formUtil)%>
		</div>
	</div>
	<div class="clearfix"></div>
	<div class="col-md-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"PROVINCE","PROVINCE", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBox("PROVINCE",ADDRESS_TYPE_VAT,"PROVINCE_"+addressVatElementId,"PROVINCE_"+ADDRESS_TYPE_VAT,
				addressV.getProvinceDesc(),"","30","","col-sm-8 col-md-7",formUtil)%>
		</div>
	</div>
	<div class="col-md-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"ZIPCODE","ZIPCODE", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBoxZipCode("ZIPCODE", ADDRESS_TYPE_VAT, "ZIPCODE_"+addressVatElementId, "ZIPCODE_"+ADDRESS_TYPE_VAT,
			    addressV.getZipcode(), "", "5", "", "col-sm-8 col-md-7", formUtil)  %>
		</div>
	</div>
	<div class="clearfix"></div>
	<div class="col-md-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"VAT_CODE","VAT_CODE", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBox("VAT_CODE",ADDRESS_TYPE_VAT,"VAT_CODE_"+addressVatElementId,"VAT_CODE_"+ADDRESS_TYPE_VAT,
				addressV.getVatCode(),"","10","","col-sm-8 col-md-7",formUtil)%>
		</div>
	</div>
	<div class="clearfix"></div>
</div>
</div>