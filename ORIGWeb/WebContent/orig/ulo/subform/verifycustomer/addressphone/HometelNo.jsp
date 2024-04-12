<%@page import="com.eaf.orig.ulo.model.app.AddressDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page contentType="text/html;charset=UTF-8"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="EntityForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler"/>

<%
	Logger logger = Logger.getLogger(this.getClass());
	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String displayMode = HtmlUtil.EDIT;
	ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)EntityForm.getObjectForm();
	PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
	if(Util.empty(personalInfo)){
		personalInfo = new PersonalInfoDataM();
	}
	String ADDRESS_TYPE_CURRENT = SystemConstant.getConstant("ADDRESS_TYPE_CURRENT");
	AddressDataM address= personalInfo.getAddress(ADDRESS_TYPE_CURRENT);
	if(Util.empty(address)){
		address = new AddressDataM();
		personalInfo.addAddress(address);
	}
%>
<tr>
	<td width="250px">
		<%=HtmlUtil.radioInline("CONTACT_TYPE_OFFICE_PHONE", "", "", "",ADDRESS_TYPE_CURRENT, displayMode, "", FormatUtil.display(personalInfo.getPersonalType()!=null && personalInfo.getPersonalType().equals(PersonalInfoDataM.PersonalType.APPLICANT)?HtmlUtil.getText(request, "PRIMARY_CARD"):HtmlUtil.getText(request, "SUPPLEMENTARY_CARD") )+"    "+HtmlUtil.getText(request, "M_HOME_TEL"), "col-sm-12", request)%>
	</td>
	<td width="150px"><%=FormatUtil.getPhoneNo(address.getPhone1())%></td>
	<%if(!Util.empty(address.getExt1())){ %>
	<td><%=LabelUtil.getText(request, "TO")%>
		<%=FormatUtil.display(address.getExt1())%>
	</td>
		<%=HtmlUtil.hidden("EXT", address.getExt1()) %>
		<% } %>
		<%=HtmlUtil.hidden("PHONE_NO", FormatUtil.getPhoneNo(address.getPhone1())) %>
</tr>

