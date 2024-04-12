<%@page import="com.eaf.core.ulo.common.properties.ListBoxControl"%>
<%@page import="com.eaf.orig.ulo.control.address.util.DisplayAddressUtil"%>
<%@page import="java.util.Collections"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.HashMap"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.orig.ulo.model.app.AddressDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM"%>

<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM" %>
<%@page import="com.eaf.core.ulo.common.properties.SystemConfig"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="EntityForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>

<script type="text/javascript" src="orig/ulo/subform/js/AddressSubForm.js"></script>
<%
	String subformId = "SUP_ADDRESS_SUBFORM_2";
	Logger logger = Logger.getLogger(this.getClass());
	PersonalApplicationInfoDataM personalApplicationInfo = (PersonalApplicationInfoDataM)EntityForm.getObjectForm();
	PersonalInfoDataM personalInfo = personalApplicationInfo.getPersonalInfo();
	if(null == personalInfo){
		personalInfo = new PersonalInfoDataM();
	}
	int PERSONAL_SEQ = personalInfo.getSeq();
	String PERSONAL_TYPE = personalInfo.getPersonalType();	
	
	String displayMode = HtmlUtil.EDIT;
// 	String TAG_SMART_DATA_PERSONAL = FormatUtil.getSmartDataEntryId(PERSONAL_TYPE,PERSONAL_SEQ);
// 	String tagConfig = "S_";
	String personalElementId = FormatUtil.getPersonalElementId(personalInfo);
	
	String[] DISPLAY_ADDRESS_TYPE = SystemConstant.getConstant("DISPLAT_ADDRESS_TYPE_SUPPLEMENT").split("\\,");
	
	String FIELD_ID_ADDRESS_TYPE = SystemConstant.getConstant("FIELD_ID_ADDRESS_TYPE");
	String FIELD_ID_SEND_DOC = SystemConstant.getConstant("FIELD_ID_SEND_DOC");
	String FIELD_ID_PLACE_RECEIVE_CARD = SystemConstant.getConstant("FIELD_ID_PLACE_RECEIVE_CARD");
	String SEARCH_BRANCH_INFO = SystemConstant.getConstant("SEARCH_BRANCH_INFO");
	String FIELD_ID_COUNTRY = SystemConstant.getConstant("FIELD_ID_COUNTRY");
	FormUtil formUtil = new FormUtil(subformId,request);
	
	String DEFAULT_MAILING_ADDRESS = SystemConstant.getConstant("DEFAULT_MAILING_ADDRESS");
	if(Util.empty(personalInfo.getMailingAddress())){
		personalInfo.setMailingAddress(DEFAULT_MAILING_ADDRESS);
	}
	if(Util.empty(personalInfo.getPlaceReceiveCard())){
		 personalInfo.setPlaceReceiveCard(DEFAULT_MAILING_ADDRESS);
	}
	
	/*Defect FUT (DF832, 835) Add Edit icon address */
	ArrayList<String> WIP_JOBSTATE_END = (ArrayList<String>)SystemConfig.getArrayListGeneralParam("WIP_JOBSTATE_END");
	String JOB_STATE_BEFORE_CARDLINK = SystemConfig.getGeneralParam("JOB_STATE_BEFORE_CARDLINK");
	String COMPLETE_FLAG_Y = SystemConstant.getConstant("COMPLETE_FLAG_Y");
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();	
	String applicationJobState = applicationGroup.getJobState();
	boolean displayAddress = false;
	if(WIP_JOBSTATE_END.contains(applicationJobState) || JOB_STATE_BEFORE_CARDLINK.equals(applicationJobState)){
		displayAddress=true;
	}
	String applicationStatus = applicationGroup.getApplicationStatus();
%>
<div class="panel panel-default">
	
<!-- <div class="panel-body">  		 -->
<!-- <div class="row form-horizontal"> -->
<!-- 	<div class="col-sm-12"> -->
<!-- 		<div class="form-group"> -->
<%-- 			<%=HtmlUtil.getSubFormLabel(request,subformId,"ADDRESS_TYPE","ADDRESS_TYPE","col-sm-2 col-md-5 marge-label control-label")%> --%>
<!-- 			<div class="col-sm-10 col-md-9 marge-field"> -->
<!-- 				<div class="row"> -->
<!-- 					<div class="col-xs-12"> -->
<%-- 						<%=HtmlUtil.dropdown("ADDRESS_TYPE","","","","",FIELD_ID_ADDRESS_TYPE,"ALL_ALL_ALL",formUtil.getDisplayMode("ADDRESS_TYPE",""),HtmlUtil.tagId(TAG_SMART_DATA_PERSONAL,"ADDRESS_TYPE"),"col-sm-5 col-xs-10 col-xs-padding",request)%> --%>
<%-- 						<div class="col-xs-2"><%=HtmlUtil.button("BTN_ADD_ADDRESS","",formUtil.getDisplayMode("BTN_ADD_ADDRESS",""),"btnsmall_add","",request) %></div> --%>
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 	</div> -->
<!-- 	<div class="clearfix"></div> -->
<!-- </div> -->
<!-- </div> -->

<div class="panel-body"> 
<div class="row form-horizontal">
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"SEND_DOC","SEND_DOC_SUB", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.dropdown("SEND_DOC", "SEND_DOC_"+personalElementId, "SEND_DOC","PLACE_RECEIVE_CARD_"+PERSONAL_TYPE, 
				personalInfo.getPlaceReceiveCard(), "",FIELD_ID_SEND_DOC,"ACTIVE","","col-sm-8 col-md-7", formUtil)%>
		</div>
	</div>
<%-- 	<div class="clearfix"></div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"PLACE_RECEIVE_CARD","PLACE_RECEIVE_CARD_SUB", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.dropdown("PLACE_RECEIVE_CARD", "PLACE_RECEIVE_CARD_"+personalElementId, "PLACE_RECEIVE_CARD","PLACE_RECEIVE_CARD_"+PERSONAL_TYPE, 
 				personalInfo.getPlaceReceiveCard(), "",FIELD_ID_PLACE_RECEIVE_CARD,"ALL_ALL_ALL","","col-sm-8 col-md-7", formUtil)%>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"BRANCH_RECEIVE_CARD","BRANCH_RECEIVE_CARD_SUB", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.search("BRANCH_RECEIVE_CARD", "BRANCH_RECEIVE_CARD_"+personalElementId, "BRANCH_RECEIVE_CARD",SEARCH_BRANCH_INFO, 
				personalInfo.getBranchReceiveCard(), "","","","","col-sm-8 col-md-7", formUtil)%>
		</div>					
	</div> --%>
	
	
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
				<%if(displayAddress){%>
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
					<img class="cursorHand edit_pencil_icon"  src="images\ulo\compareFlag2.png" title="Complete" onclick="EDIT_ADDRESSActionJS('<%=address.getAddressType()%>')">
				<%}else{ %>
					<img class="cursorHand edit_pencil_icon"  src="images\ulo\compareFlag2Edit3.png"  title="Edit" onclick="EDIT_ADDRESSActionJS('<%=address.getAddressType()%>')">
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

<%=HtmlUtil.hidden("BRANCH_RECEIVE_CARD_NAME", personalInfo.getBranchReceiveCardName()) %>
<%=HtmlUtil.hidden("PERSONAL_SEQ",String.valueOf(PERSONAL_SEQ)) %>
<%=HtmlUtil.hidden("PERSONAL_TYPE",PERSONAL_TYPE) %>
<%=HtmlUtil.hidden("APPLICATION_STATUS",applicationStatus) %>