<%@page import="com.eaf.core.ulo.common.properties.ListBoxControl"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.Collections"%>
<%@page import="com.eaf.core.ulo.common.display.ElementInf"%>
<%@page import="com.eaf.core.ulo.common.properties.ImplementControl"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
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

<script type="text/javascript" src="orig/ulo/subform/js/cardMaintenanceSubForm.js"></script>
<%
	String subformId = "CARD_MAINTENANCE_SUBFORM";
	Logger logger = Logger.getLogger(this.getClass());
	FormUtil formUtil = new FormUtil(subformId,request);
%>

<div class="panel panel-default">
<div class="panel-body"> 
<div class="row form-horizontal">
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"CM_REGISTER_NO","CM_REGISTER_NO", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBox("CM_REGISTER_NO", "",null, "", "", HtmlUtil.VIEW, HtmlUtil.elementTagId("CM_REGISTER_NO"), "col-sm-8 col-md-7", request) %>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"CM_CARDLINK_REF_NO","CM_CARDLINK_REF_NO", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBox("CM_CARDLINK_REF_NO", "",null, "", "", HtmlUtil.VIEW, HtmlUtil.elementTagId("CM_CARDLINK_REF_NO"), "col-sm-8 col-md-7", request) %>
		</div>					
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"CM_CARDLINK_FLAG","CM_CARDLINK_FLAG", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBox("CM_CARDLINK_FLAG", "",null, "", "", HtmlUtil.VIEW, HtmlUtil.elementTagId("CM_CARDLINK_FLAG"), "col-sm-8 col-md-7", request) %>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"CM_SEND_LATEST_CARD_REF_NO_DATE","CM_SEND_LATEST_CARD_REF_NO_DATE", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBox("CM_SEND_LATEST_CARD_REF_NO_DATE", "",null, "", "", HtmlUtil.VIEW, HtmlUtil.elementTagId("CM_SEND_LATEST_CARD_REF_NO_DATE"), "col-sm-8 col-md-7", request) %>
		</div>
	</div>
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
				<td>
					<%=address.getAddress1()%> 
					<%=Util.empty(address.getAddress2()) ? "" : " "+address.getAddress2()%>
				</td>
				<td><%=ListBoxControl.getName(FIELD_ID_COUNTRY, address.getCountry())%></td>
		 	</tr>
		<%}
		}else{%>
			 	<tr>
			 		<td colspan="5" align="center"><%=LabelUtil.getText(request,"NO_RECORD_FOUND")%></td>
			 	</tr>			
		<%}%>
</table>
</div>
<%if(!CIDTYPE_IDCARD.equals(personalInfo.getCidType())){%>
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
			<%ElementInf element = ImplementControl.getElement(VAT_REGISTRATION_IMPLEMENT_TYPE,"VAT_REGISTRATION");
			 element.writeElement(pageContext,addressV);%>
</div>
<%}%>
<%=HtmlUtil.hidden("PERSONAL_SEQ",String.valueOf(PERSONAL_SEQ)) %>
<%=HtmlUtil.hidden("PERSONAL_TYPE",PERSONAL_TYPE) %>
<%=HtmlUtil.hidden("MATCHES_CARDLINK",String.valueOf(matchesCardLink).toUpperCase()) %>