<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.model.PersonalInfoDataM" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.orig.shared.model.VehicleDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM" />
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler" />

<%
	ORIGUtility utility = new ORIGUtility();
	PersonalInfoDataM personalInfoDataM = utility.getPersonalInfoByType(ORIGForm.getAppForm(), OrigConstant.PERSONAL_TYPE_APPLICANT);
	ORIGFormUtil formUtil = new ORIGFormUtil();
	String searchType = (String) request.getSession().getAttribute("searchType");
	String displayMode = formUtil.getDisplayMode("CAR_INFO_SUBFORM", ORIGUser.getRoles(), ORIGForm.getAppForm().getJobState(), ORIGForm.getFormID(), searchType);	
	VehicleDataM vehicleDataM = ORIGForm.getAppForm().getVehicleDataM();
	String disabledBtn = "";
	if(vehicleDataM == null){
		vehicleDataM = new VehicleDataM();
	}else{
		if(!"NEW".equals(vehicleDataM.getDrawDownStatus()) && !ORIGUtility.isEmptyString(vehicleDataM.getDrawDownStatus())){
			disabledBtn = ORIGDisplayFormatUtil.DISABLED;
		}
	}
%>
	<TABLE height=1 cellSpacing=0 cellPadding=0 width="100%" bgColor=#FFFFFF border=0 align="center">   
		<TR><TD height="10"></TD></TR>
	    <TR>
	    	<td width="80%">&nbsp;</td>
			<TD width="6%">
				<INPUT class="button" onclick="javascript:mandatoryPopupField('<%=personalInfoDataM.getCustomerType() %>','<%=OrigConstant.PopupName.POPUP_CAR %>')" type="button" value="<%=MessageResourceUtil.getTextDescription(request, "SAVE") %>" name="saveCarBtn" <%=disabledBtn %>>
			</td><td width="6%">
				<INPUT class="button" onclick="javascript:closeCar()" type="button" value="<%=MessageResourceUtil.getTextDescription(request, "CANCEL") %>" name="closeAppBtn"> 
			</TD><td width="2%">&nbsp;</td>
		</TR>
		<TR><TD height="10"></TD></TR>
	</TABLE>
<script language="JavaScript">
function saveData(){
	var form = document.appFormName;
	//form.formID.value = "CAR_DETAIL_FORM";
	//form.currentTab.value = "DE_TAB";
	form.action.value = "SaveCarDetailPopUp";
	form.handleForm.value = "Y";			
	form.submit(); 
}
function closeCar(){
	window.close(); 
}
</script>