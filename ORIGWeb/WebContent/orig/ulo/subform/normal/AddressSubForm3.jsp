<%@page import="com.eaf.core.ulo.common.properties.SystemConfig"%>
<%@page import="com.eaf.core.ulo.common.properties.ListBoxControl"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.Collections"%>
<%@page import="com.eaf.core.ulo.common.display.ElementInf"%>
<%@page import="com.eaf.core.ulo.common.properties.ImplementControl"%>
<%@page import="com.eaf.orig.ulo.control.address.util.DisplayAddressUtil"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.orig.ulo.model.app.AddressDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.orig.ulo.app.view.util.kpl.KPLUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM" %>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>

<script type="text/javascript" src="orig/ulo/subform/js/AddressSubForm.js"></script>
<%
	
	String subformId = "NORMAL_ADDRESS_SUBFORM_3";
	Logger logger = Logger.getLogger(this.getClass());
	String VAT_REGISTRATION_IMPLEMENT_TYPE = SystemConstant.getConstant("VAT_REGISTRATION_IMPLEMENT_TYPE");
	String ADDRESS_TYPE_NATION = SystemConstant.getConstant("ADDRESS_TYPE_NATION");
	String ADDRESS_TYPE_VAT = SystemConstant.getConstant("ADDRESS_TYPE_VAT");
	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");	
	String ROLE_CA = SystemConstant.getConstant("ROLE_CA");
	String ROLE_VT = SystemConstant.getConstant("ROLE_VT");
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	String roleId = ORIGForm.getRoleId();
	boolean showSectionOwnCountry = true;
	if(ROLE_CA.equals(roleId) || ROLE_VT.equals(roleId)){
		showSectionOwnCountry = false;
	}
	boolean matchesCardLink = applicationGroup.matchesCardLinkByproduct();
	String applicationStatus = applicationGroup.getApplicationStatus();
	logger.debug("matchesCardLink : "+matchesCardLink);
	PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
	if(null == personalInfo){
		personalInfo = new PersonalInfoDataM();
	}
	int PERSONAL_SEQ = personalInfo.getSeq();
	
	AddressDataM addressN = personalInfo.getAddress(ADDRESS_TYPE_NATION);
	if(null == addressN){
		addressN = new AddressDataM();
	}
	
	AddressDataM addressV = personalInfo.getAddress(ADDRESS_TYPE_VAT);
	if(null == addressV){
		addressV = new AddressDataM();
	}
	
	String displayMode = HtmlUtil.EDIT;
	
	/*Defect FUT (DF832, 835) Add Edit icon address */
	ArrayList<String> WIP_JOBSTATE_END = (ArrayList<String>)SystemConfig.getArrayListGeneralParam("WIP_JOBSTATE_END");
	String JOB_STATE_BEFORE_CARDLINK = SystemConfig.getGeneralParam("JOB_STATE_BEFORE_CARDLINK");
	String COMPLETE_FLAG_Y = SystemConstant.getConstant("COMPLETE_FLAG_Y");
	String applicationJobState = applicationGroup.getJobState();
	boolean displayAddress = false;
	if(WIP_JOBSTATE_END.contains(applicationJobState) || JOB_STATE_BEFORE_CARDLINK.equals(applicationJobState)){
		displayAddress=true;
	}
	
// 	String TAG_SMART_DATA_PERSONAL = FormatUtil.getSmartDataEntryId(PERSONAL_TYPE,PERSONAL_SEQ);
// 	String TAG_SMART_DATA_NATION_ADDRESS = FormatUtil.getSmartDataEntryId(ADDRESS_TYPE_NATION,TAG_SMART_DATA_PERSONAL);
// 	String TAG_SMART_DATA_VAT_ADDRESS = FormatUtil.getSmartDataEntryId(TAG_SMART_DATA_PERSONAL,ADDRESS_TYPE_VAT);
	
	String personalElementId = FormatUtil.getPersonalElementId(personalInfo);
	String addressNationElementId = FormatUtil.getAddressElementId(personalInfo,ADDRESS_TYPE_NATION);
	String addressVatElementId = FormatUtil.getAddressElementId(personalInfo,ADDRESS_TYPE_VAT);
	
// 	String tagConfig = "A_";
// 	String SUFFIX_NATION_ADDRESS = ADDRESS_TYPE_NATION;
// 	String SUFFIX_VAT_ADDRESS = ADDRESS_TYPE_VAT;
	String[] DISPLAY_ADDRESS_TYPE = SystemConstant.getConstant("DISPLAY_ADDRESS_TYPE").split("\\,");
	String CIDTYPE_IDCARD = SystemConstant.getConstant("CIDTYPE_IDCARD");
	String FIELD_ID_ADDRESS_TYPE = SystemConstant.getConstant("FIELD_ID_ADDRESS_TYPE");
	String FIELD_ID_SEND_DOC = SystemConstant.getConstant("FIELD_ID_SEND_DOC");
	String FIELD_ID_PLACE_RECEIVE_CARD = SystemConstant.getConstant("FIELD_ID_PLACE_RECEIVE_CARD");
	String FIELD_ID_COUNTRY = SystemConstant.getConstant("FIELD_ID_COUNTRY");
	String SEARCH_BRANCH_INFO = SystemConstant.getConstant("SEARCH_BRANCH_INFO");
	String FIELD_ID_VAT_REGIST_FLAG = SystemConstant.getConstant("FIELD_ID_VAT_REGIST_FLAG");
	String FIELD_ID_ESTABLISHMENT_ADDR_FLAG = SystemConstant.getConstant("FIELD_ID_ESTABLISHMENT_ADDR_FLAG");
	
	boolean isKPL = KPLUtil.isKPL(applicationGroup);
	
	FormUtil formUtil = new FormUtil(subformId,request);
%>

<div class="panel panel-default">
<div class="panel-body"> 
<div class="row form-horizontal">
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"SEND_DOC","SEND_DOC"+KPLUtil.getKPLFlag(applicationGroup), "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.dropdown("SEND_DOC", "SEND_DOC_"+personalElementId, "SEND_DOC","PLACE_RECEIVE_CARD_"+PERSONAL_TYPE, 
				personalInfo.getPlaceReceiveCard(), "",FIELD_ID_SEND_DOC,"ACTIVE","","col-sm-8 col-md-7", formUtil)%>
		</div>
	</div>
<%-- 	<div class="clearfix"></div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"PLACE_RECEIVE_CARD","PLACE_RECEIVE_CARD", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.dropdown("PLACE_RECEIVE_CARD", "", "PLACE_RECEIVE_CARD_"+personalElementId, "PLACE_RECEIVE_CARD","PLACE_RECEIVE_CARD_"+PERSONAL_TYPE, 
				personalInfo.getPlaceReceiveCard(), "",FIELD_ID_PLACE_RECEIVE_CARD,"ALL_ALL_ALL","","col-sm-8 col-md-7", personalInfo, formUtil)%>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"BRANCH_RECEIVE_CARD","BRANCH_RECEIVE_CARD", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.search("BRANCH_RECEIVE_CARD", "", "BRANCH_RECEIVE_CARD_"+personalElementId, "BRANCH_RECEIVE_CARD",SEARCH_BRANCH_INFO, 
				personalInfo.getBranchReceiveCard(), "","","","","col-sm-8 col-md-7", personalInfo, formUtil)%>
		</div>					
	</div>
			<%=HtmlUtil.hidden("BRANCH_RECEIVE_CARD_NAME", personalInfo.getBranchReceiveCardName()) %> --%>
</div>
</div>
</div>

<div class="panel panel-default">
<table class="table table-striped table-hover">
	<%	
		ArrayList<AddressDataM> addresses = personalInfo.getAddress(DISPLAY_ADDRESS_TYPE);
		int count=0;
		if(!Util.empty(addresses)){
			personalInfo.setSortType(PersonalInfoDataM.SORT_TYPE.ASC);
			Collections.sort(addresses, new PersonalInfoDataM());
			for (AddressDataM address:addresses) {
				count++;
				String cacheId = SystemConstant.getConstant("FIELD_ID_ADDRESS_TYPE");
				String AddressTypeDesc = CacheControl.getName(cacheId,address.getAddressType());
				DisplayAddressUtil.setAddressLine(address);
		%>
			<tr>
		 		<td><%=count %></td>
				<td>
					<a href="#" onclick="EDIT_ADDRESSActionJS('<%=address.getAddressType()%>')"  >
						<%=AddressTypeDesc %>
					</a>
				</td>
				<%if(displayAddress){ %>	
				<td>
					<%=address.getAddress1()%> 
					<%=Util.empty(address.getAddress2()) ? "" : " "+address.getAddress2()%>
				</td>
				<td><%=ListBoxControl.getName(FIELD_ID_COUNTRY, address.getCountry())%></td>
				<%}else{%>
					<td></td>
					<td></td>
				<%}%>
				<td>
				<%if(COMPLETE_FLAG_Y.equals(address.getEditFlag())) {%>
					<img class="cursorHand edit_pencil_icon" src="images\ulo\compareFlag2.png" title="Complete" onclick="EDIT_ADDRESSActionJS('<%=address.getAddressType()%>')">
				<%}else{ %>
					<img class="cursorHand edit_pencil_icon" src="images\ulo\compareFlag2Edit3.png"  title="Edit" onclick="EDIT_ADDRESSActionJS('<%=address.getAddressType()%>')">
				<%}%>
				</td>
		 	</tr>
		<%}
		}else{%>
			 	<tr>
			 		<td colspan="5" align="center"><%=LabelUtil.getText(request,"NO_RECORD_FOUND")%></td>
			 	</tr>			
		<%}%>
</table>
</div>
<%if(!CIDTYPE_IDCARD.equals(personalInfo.getCidType()) && showSectionOwnCountry){%>
<div class="panel panel-default">
<div class="panel-heading"><%=LabelUtil.getText(request, "MOTHERLAND_ADDRESS")%></div>
<div class="panel-body"> 
<div class="row form-horizontal">	
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"ADDRESS1_"+ADDRESS_TYPE_NATION,"ADDRESS1", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBox("ADDRESS1",ADDRESS_TYPE_NATION,"ADDRESS1_"+addressNationElementId,"ADDRESS1",
				addressN.getAddress1(),"","200","","col-sm-8 col-md-7", addressN,formUtil)%>
		</div>					
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"STATE_"+ADDRESS_TYPE_NATION,"STATE", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBox("STATE",ADDRESS_TYPE_NATION,"STATE_"+addressNationElementId,"STATE",
				addressN.getState(),"","50","","col-sm-8 col-md-7", addressN,formUtil)%>
		</div>					
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"COUNTRY_"+ADDRESS_TYPE_NATION,"COUNTRY", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.dropdown("COUNTRY",ADDRESS_TYPE_NATION, "COUNTRY_"+addressNationElementId, "COUNTRY", "",
				addressN.getCountry(), "", FIELD_ID_COUNTRY,"ALL_ALL_ALL", "", "col-sm-8 col-md-7",addressN, formUtil)%>
		</div>					
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"ZIPCODE_"+ADDRESS_TYPE_NATION,"ZIPCODE", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBox("ZIPCODE", ADDRESS_TYPE_NATION, "ZIPCODE_"+addressNationElementId, "ZIPCODE", 
				addressN.getZipcode(), "", "20", "", "col-sm-8 col-md-7", addressN, formUtil) %>
		</div>					
	</div>
</div>
</div>
</div>
<%}%>
		<div class="panel panel-default">
			<%ElementInf element = ImplementControl.getElement(VAT_REGISTRATION_IMPLEMENT_TYPE,"VAT_REGISTRATION");
			 element.writeElement(pageContext,addressV);%>
		</div>
<%=HtmlUtil.hidden("BRANCH_RECEIVE_CARD_NAME", personalInfo.getBranchReceiveCardName()) %>
<%=HtmlUtil.hidden("PERSONAL_SEQ",String.valueOf(PERSONAL_SEQ)) %>
<%=HtmlUtil.hidden("PERSONAL_TYPE",PERSONAL_TYPE) %>
<%=HtmlUtil.hidden("MATCHES_CARDLINK",String.valueOf(matchesCardLink).toUpperCase()) %>
<%=HtmlUtil.hidden("APPLICATION_STATUS",applicationStatus) %>