<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.eaf.orig.ulo.model.app.AddressDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.display.ElementInf"%>
<%@page import="com.eaf.core.ulo.common.properties.ImplementControl"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.orig.ulo.model.control.FlowControlDataM"%>
<%@page import="com.eaf.core.ulo.common.util.SessionControl"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ModuleForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler"/>

<script type="text/javascript" src="orig/ulo/subform/js/ReKeySensitiveField.js"></script>

<%
	String subformId = "RE_KEY_SENSITIVE_FIELDS";
	Logger logger = Logger.getLogger(this.getClass());
// 	logger.debug("====HELLO WORLD==");
	FlowControlDataM flowControl = (FlowControlDataM) request.getSession().getAttribute(SessionControl.FlowControl);	
	
	ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)ModuleForm.getObjectForm();
	if(null == applicationGroup){  
		applicationGroup = new ApplicationGroupDataM();
	}
	
// 	ArrayList<ApplicationDataM> appList  =  new ArrayList<ApplicationDataM>();
// 	ApplicationDataM  applicationTest  =  new ApplicationDataM();
// 	applicationTest.setApplicationType(MConstant.APPLICATION_TYPE.NORMAL);	
// 	appList.add(applicationTest);
// 	applicationGroup.setApplications(appList);
		 
// 	PersonalInfoDataM personalInfo = new PersonalInfoDataM();
// 	personalInfo.setPersonalType(MConstant.PERSONAL_TYPE_APPLICANT);
// 	personalInfo.setPersonalId("1");	
// 	personalInfo.setBirthDate(new java.sql.Date(System.currentTimeMillis()));
	
// 	ArrayList<AddressDataM> addressList  = new  ArrayList<AddressDataM>();
// 	AddressDataM address = new  AddressDataM();
// 	address.setAddressType(AddressDataM.ADDRESS_TYPE.CURRENT);
// 	addressList.add(address);
	
// 	address = new  AddressDataM();
// 	address.setAddressType(AddressDataM.ADDRESS_TYPE.DOCUMENT);
// 	addressList.add(address);
	
// 	address = new  AddressDataM();
// 	address.setAddressType(AddressDataM.ADDRESS_TYPE.CURRENT);
// 	addressList.add(address);
	
// 	address = new  AddressDataM();
// 	address.setAddressType(AddressDataM.ADDRESS_TYPE.WORK);
// 	addressList.add(address);
	
// 	personalInfo.setAddresses(addressList);
	
// 	ArrayList<PersonalInfoDataM> personalList = new ArrayList<PersonalInfoDataM>();
// 	personalList.add(personalInfo);
// 	applicationGroup.setPersonalInfos(personalList);
	
// 	ArrayList<ElementInf>   sensitiveFields = new ArrayList<ElementInf>();	
// 	sensitiveFields = ImplementControl.getElements("COMPARE_SENSITIVE",SystemConstant.getConstant("COMPARE_SENSITIVE_TEST").split(","));
%>


<jsp:include page="/orig/ulo/subform/sensitive/NormalRekeySensitiveField.jsp"/>
<jsp:include page="/orig/ulo/subform/sensitive/IncreaseReKeySensitiveField.jsp"/>
<jsp:include page="/orig/ulo/subform/sensitive/AddSupRekeySensitiveField.jsp"/>
<jsp:include page="/orig/ulo/button/ReKeySensitiveFieldButton.jsp"/>