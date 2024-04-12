<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.AddressDataM"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.orig.ulo.app.view.util.kpl.KPLUtil"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ModuleForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>

<%
	String subformId = "CURRENT_ADDRESS_TYPE_SUBFORM";
	Logger logger = Logger.getLogger(this.getClass());
	String PERSONAL_TYPE = ModuleForm.getRequestData("PERSONAL_TYPE");
	String PERSONAL_SEQ = ModuleForm.getRequestData("PERSONAL_SEQ");
	String ADDRESS_TYPE_CURRENT = SystemConstant.getConstant("ADDRESS_TYPE_CURRENT");
	logger.debug("PERSONAL_TYPE : "+PERSONAL_TYPE);
		
	HashMap<String,AddressDataM> hashAddress = (HashMap<String,AddressDataM>)ModuleForm.getObjectForm();
	if(null == hashAddress){
		hashAddress = new HashMap<String,AddressDataM>();
	}
	AddressDataM currentAddress = hashAddress.get(ADDRESS_TYPE_CURRENT);
	if(null == currentAddress){
		currentAddress = new AddressDataM();
	}	
	String displayMode = HtmlUtil.EDIT;
// 	String TAG_SMART_DATA_CURRENT_ADDRESS = FormatUtil.getSmartDataEntryId(CURRENT_ADDRESS, PERSONAL_TYPE);
	
	String addressElementId = FormatUtil.getAddressElementId(PERSONAL_TYPE,PERSONAL_SEQ,ADDRESS_TYPE_CURRENT);		
	
	String FIELD_ID_ADDRESS_STYLE = SystemConstant.getConstant("FIELD_ID_ADDRESS_STYLE");
	String FIELD_ID_ADRSTS = SystemConstant.getConstant("FIELD_ID_ADRSTS");
		
	FormUtil formUtil = new FormUtil("POPUP_CURRENT_ADDRESS_FORM_2",subformId,request);
	
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	String TOPUP_FLAG = KPLUtil.getTopUpFlag(applicationGroup);
%>

<div class="panel-heading"><%=LabelUtil.getText(request, "ADDRESS_CURRENT")%></div>
<div class="panel-body"> 
	<div class="row form-horizontal">
		<!-- <div class="col-sm-6">
			<div class="form-group">
				<%=HtmlUtil.getSubFormLabel(request,subformId,"ADDRESS_STYLE","ADDRESS_STYLE","col-sm-4 col-md-5 control-label")%>
				<%=HtmlUtil.dropdown("ADDRESS_STYLE", "", "ADDRESS_STYLE_"+addressElementId, "ADDRESS_STYLE", "",
				  currentAddress.getBuildingType(), "", FIELD_ID_ADDRESS_STYLE, "ALL_ALL_ALL", "", "col-sm-8 col-md-7", currentAddress, formUtil)%>
			</div>
		</div> -->
		<div class="col-sm-6">
			<div class="form-group">
				<%=HtmlUtil.getSubFormLabel(request,subformId,"ADRSTS"+TOPUP_FLAG,"ADRSTS","col-sm-4 col-md-5 control-label")%>
				<%=HtmlUtil.dropdown("ADRSTS", "", "ADRSTS_"+addressElementId, "ADRSTS", "",
				  currentAddress.getAdrsts(), "", FIELD_ID_ADRSTS, "ALL_ALL_ALL", "", "col-sm-8 col-md-7", currentAddress, formUtil)%>
			</div>
		</div>
		<div class="col-sm-6">
			<div class="form-group">
				<%=HtmlUtil.getSubFormLabel(request,subformId,"RENTS","RENTS","col-sm-4 col-md-5 control-label")%>
				<div class="col-sm-8 col-md-7">
					<div class="row">
						<div class="col-xs-12">
							<%=HtmlUtil.currencyBox("RENTS", "", "RENTS_"+addressElementId, "RENTS",
							  currentAddress.getRents(), "", true, "25", "", "col-xs-9 col-xs-padding", currentAddress, formUtil) %>
							<%=HtmlUtil.getLabel(request,"BAHT_MONTH","col-xs-3 control-label")%>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="clearfix"></div>
		<div class="col-sm-6">
			<div class="form-group">
				<%=HtmlUtil.getSubFormLabel(request,subformId,"RESIDE","RESIDE","col-sm-4 col-md-5 control-label")%>
				<div class="col-sm-8 col-md-7">
					<div class="row">
						<div class="col-xs-12">
							<%=HtmlUtil.numberBox("RESIDEY", "", "RESIDEY_"+addressElementId, "RESIDEY",
							  currentAddress.getResidey(), "", "##", "", "", true, "2", "", "col-xs-4 col-xs-padding", currentAddress, formUtil) %>											
							<%=HtmlUtil.getLabel(request, "TOT_WORK_YEAR","col-xs-2 control-label")%>
							<%=HtmlUtil.numberBox("RESIDEM", "", "RESIDEM_"+addressElementId, "RESIDEM",
							  currentAddress.getResidem(), "", "##", "", "", true, "2", "", "col-xs-4 col-xs-padding", currentAddress, formUtil) %>	
							<%=HtmlUtil.getLabel(request,"TOT_WORK_MONTH","col-xs-2 control-label")%>
						</div>
					</div>
				</div>
			</div>
		</div><div class="col-sm-6"><div class="form-group"></div></div>
	</div>
</div>
<%=HtmlUtil.hidden("ADDRESS_ELEMENT_ID", addressElementId) %>
<%=HtmlUtil.hidden("TOPUP_FLAG", TOPUP_FLAG) %>