<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
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
<%@page import="com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="EntityForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler"/>

<script type="text/javascript" src="orig/ulo/subform/sup/js/PhoneNoSubForm.js"></script>
<%
	String subformId = "SUP_PHONE_NO_SUBFORM_3";
	Logger logger = Logger.getLogger(this.getClass());
	
	PersonalApplicationInfoDataM personalApplicationInfo = (PersonalApplicationInfoDataM)EntityForm.getObjectForm();
	PersonalInfoDataM personalInfo = personalApplicationInfo.getPersonalInfo();
	if(null == personalInfo){
		personalInfo = new PersonalInfoDataM();
	}
	int PERSONAL_SEQ = personalInfo.getSeq();
	String PERSONAL_TYPE = personalInfo.getPersonalType();	
	
	String ADDRESS_TYPE_CURRENT = SystemConstant.getConstant("ADDRESS_TYPE_CURRENT");
	String ADDRESS_TYPE_WORK = SystemConstant.getConstant("ADDRESS_TYPE_WORK");
	
	AddressDataM currentAddress = personalInfo.getAddress(ADDRESS_TYPE_CURRENT);
	if(null == currentAddress){
		currentAddress = new AddressDataM();
	}
	AddressDataM workAddress = personalInfo.getAddress(ADDRESS_TYPE_WORK);
	if(null == workAddress){
		workAddress = new AddressDataM();
	}
	
	String displayMode = HtmlUtil.EDIT;
// 	String TAG_SMART_DATA_PERSONAL = FormatUtil.getSmartDataEntryId(PERSONAL_TYPE,PERSONAL_SEQ);
// 	String TAG_SMART_DATA_CURRENT_ADDRESS = FormatUtil.getSmartDataEntryId(AddressDataM.ADDRESS_TYPE.CURRENT, TAG_SMART_DATA_PERSONAL);
// 	String TAG_SMART_DATA_WORK_ADDRESS = FormatUtil.getSmartDataEntryId(AddressDataM.ADDRESS_TYPE.WORK, TAG_SMART_DATA_PERSONAL);
	
// 	String SUFFIX_CURRENT_ADDRESS = AddressDataM.ADDRESS_TYPE.CURRENT;
// 	String SUFFIX_WORK_ADDRESS = AddressDataM.ADDRESS_TYPE.WORK;
	
	String personalElementId = FormatUtil.getPersonalElementId(personalInfo);
	String addressCurrentElementId = FormatUtil.getAddressElementId(personalInfo,ADDRESS_TYPE_CURRENT);
	String addressWorkElementId = FormatUtil.getAddressElementId(personalInfo,ADDRESS_TYPE_WORK);
	
	FormUtil formUtil = new FormUtil(subformId,request);
%>
<div class="panel panel-default">
	
	<div class="panel-heading"><%=LabelUtil.getText(request, "MOBILE")%></div>
	<div class="panel-body">  		
		<div class="row form-horizontal">
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request,subformId,"MOBILE","MOBILE","col-sm-4 col-md-5 control-label")%>
					<%=HtmlUtil.textBoxMobile("PHONE_NUMBER","","PHONE_NUMBER_"+personalElementId,"PHONE_NUMBER",
						personalInfo.getMobileNo(),"","","col-sm-8 col-md-7",personalInfo,formUtil)%>
				</div>
			</div>
			
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request,subformId,"EMAIL","EMAIL","col-sm-5 col-md-2 control-label")%>
					<%=HtmlUtil.textBoxEmail("EMAIL", "EMAIL_"+personalElementId, "EMAIL",
					 personalInfo.getEmailPrimary(), "", "", "", "col-sm-8 col-md-7", formUtil)%>
				</div>
			</div>
			<div class="clearfix"></div>
		</div>
	</div>
	
	<div class="panel-heading"><%=LabelUtil.getText(request,"ADDRESS_CURRENT")%></div>
	<div class="panel-body">  		
		<div class="row form-horizontal">
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request,subformId,"PHONE1", "PHONE1", "col-sm-4 col-md-5 control-label")%>
					<div class="col-sm-8 col-md-7">
						<div class="row">
							<div class="col-xs-12">
								<%=HtmlUtil.textBoxTel("PHONE1",ADDRESS_TYPE_CURRENT,"PHONE1_"+addressCurrentElementId,"HOME_PHONE",
									currentAddress.getPhone1(),"","","col-xs-6 col-xs-padding",currentAddress,formUtil)%>
								<%=HtmlUtil.getFieldLabel(request,"PHONE_EXT","col-xs-3 control-label")%>
								<%=HtmlUtil.textBoxExt("EXT1",ADDRESS_TYPE_CURRENT,"EXT1_"+addressCurrentElementId,"HOME_PHONE_EXT",
									currentAddress.getExt1(),"","5","","col-xs-3 col-xs-padding",currentAddress,formUtil)%>						
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="clearfix"></div>
		</div>
	</div>
	
	<div class="panel-heading"><%=LabelUtil.getText(request, "COMPANY_ADDRESS")%></div>
	<div class="panel-body">  		
		<div class="row form-horizontal">
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request,subformId,"OFFICE_PHONE","OFFICE_PHONE","col-sm-4 col-md-5 control-label")%>
					<div class="col-sm-8 col-md-7">
						<div class="row">
							<div class="col-xs-12">
								<%=HtmlUtil.textBoxTel("PHONE1",ADDRESS_TYPE_WORK,"PHONE1_"+addressWorkElementId,"PHONE1_WORK",
									workAddress.getPhone1(),"","","col-xs-6 col-xs-padding",workAddress,formUtil)%>
								<%=HtmlUtil.getFieldLabel(request,"PHONE_EXT","col-xs-3 control-label")%>
								<%=HtmlUtil.textBoxExt("EXT1",ADDRESS_TYPE_WORK,"EXT1_"+addressWorkElementId,"PHONE1_WORK_EXT",
									workAddress.getExt1(),"","5","","col-xs-3 col-xs-padding",workAddress,formUtil)%>						
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="clearfix"></div>
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request,subformId,"FAX","FAX","col-sm-4 col-md-5 control-label")%>
					<div class="col-sm-8 col-md-7">
						<div class="row">
							<div class="col-xs-12">
								<%=HtmlUtil.textBoxTel("FAX",ADDRESS_TYPE_WORK,"FAX_"+addressWorkElementId,"FAX",
									workAddress.getFax(),"","","col-xs-6 col-xs-padding",workAddress,formUtil)%>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
</div>


