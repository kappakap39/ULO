<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.core.ulo.common.util.FormEffects"%>
<%@page import="com.eaf.core.ulo.common.util.FormDisplayModeUtil"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.PrivilegeProjectCodeProductInsuranceDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PrivilegeProjectCodeDataM"%>

<script type="text/javascript" src="orig/ulo/subform/projectcode/product/js/InsuranceProductSubForm.js"></script>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="EntityForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler"/>
<%
String subformId="VERIFY_PRIVILEGE_PRODUCT_PROJECT_CODE_SUBFORM";


PrivilegeProjectCodeDataM privilegeProjectCode = (PrivilegeProjectCodeDataM)EntityForm.getObjectForm();		
if(Util.empty(privilegeProjectCode)){
	privilegeProjectCode = new PrivilegeProjectCodeDataM();
}

ArrayList<PrivilegeProjectCodeProductInsuranceDataM> prvlgProjectCodeProductInsurances = privilegeProjectCode.getPrivilegeProjectCodeProductInsurances();
if(Util.empty(prvlgProjectCodeProductInsurances)){
	prvlgProjectCodeProductInsurances = new ArrayList<PrivilegeProjectCodeProductInsuranceDataM>();
}
FormUtil formUtil = new FormUtil(subformId,request);
FormEffects formEffect = new FormEffects(subformId,request);

 %>
<div class="panel panel-default">
	<div class="panel-body">
		<div class="row form-horizontal">
			<div class="col-sm-12">
				<div class="form-group">
					<div class="col-sm-7 col-md8 bold"><%=LabelUtil.getText(request, "LIFE_INSURANCE")%></div>
				</div>
			</div>
		</div>
		<div class="row form-horizontal">
			<div class="col-sm-12">
				<table class="table table-bordered" style="table-layout:fixed">
					<tbody>
						<tr class="tabletheme_header">
							<th width="5%"></th>
							<th><%=HtmlUtil.getHeaderLabel(request, subformId,"TYPE","TYPE") %></th>
							<th><%=HtmlUtil.getHeaderLabel(request, subformId,"POLICY_CODE","POLICY_CODE") %></th>
							<th><%=HtmlUtil.getHeaderLabel(request, subformId,"INSURANCE_PREMIUM_PER_YEAR","INSURANCE_PREMIUM_PER_YEAR") %></th>
							<th><%=HtmlUtil.getHeaderLabel(request, subformId,"CIS_NUMBER","CIS_NUMBER") %></th>
							<th><%=HtmlUtil.getHeaderLabel(request, subformId,"RELATIONSHIP_TRANSFER","RELATIONSHIP_TRANSFER") %></th>
							<td  width="5%" rowspan="9999" style="vertical-align: bottom; padding-bottom: 15px;"><%=HtmlUtil.button("ADD_PRODUCT_BTN_2","ADD_PRODUCT_BTN_2","","btnsmall_add","", formEffect) %></td>
						</tr>
					<%if(!Util.empty(prvlgProjectCodeProductInsurances)) {
						for(PrivilegeProjectCodeProductInsuranceDataM  prvlgProjectCodeProductInsurance :prvlgProjectCodeProductInsurances){
							String SEQ =FormatUtil.getString(prvlgProjectCodeProductInsurance.getSeq());
							String onclickActionJS="onclick=DELETE_PRODUCT_BTN2ActionJS('"+SEQ+"')";%>
						<tr>
							<td><%=HtmlUtil.icon("DELETE_PRODUCT_BTN2", "DELETE_PRODUCT_BTN2", "btnsmall_delete", onclickActionJS, formEffect) %></td>
							<td><%=HtmlUtil.dropdown("INSURANCE_TYPE", SEQ, "","INSURANCE_TYPE", "", prvlgProjectCodeProductInsurance.getInsuranceType(), "", SystemConstant.getConstant("FIELD_ID_PRVLG_INSURANCE_TYPE"), "", "", "", formUtil)%></td>
							<td><%=HtmlUtil.textBox("POLICY_NO", SEQ, "POLICY_NO", "", prvlgProjectCodeProductInsurance.getPolicyNo(), "", "20", "", "", formUtil)%>
							<td><%=HtmlUtil.currencyBox("PREMIUM", SEQ, "", "PREMIUM", prvlgProjectCodeProductInsurance.getPremium(), "", true, "15", "", "", formUtil)%></td>
							<td><%=HtmlUtil.textBox("CIS_NUMBER_INSURANCE",SEQ,"CIS_NUMBER_INSURANCE","CIS_NUMBER_INSURANCE",prvlgProjectCodeProductInsurance.getCisno(),"","10","","",formUtil)%></td>
							<td><%=HtmlUtil.dropdown("RELATIONSHIP_TRANSFER_INSURANCE",SEQ, "RELATIONSHIP_TRANSFER_INSURANCE", "RELATIONSHIP_TRANSFER_INSURANCE", 
							"", prvlgProjectCodeProductInsurance.getRelationTranfer(), "",SystemConstant.getConstant("FIELD_ID_PRVLG_RELATIONSHIP"),"", "", "", formUtil)%></td>
						</tr>
						<%}
					}else{ %>	
						<tr><td colspan="6" align="center"><%=LabelUtil.getText(request, "NO_RECORD_FOUND")%></td></tr>
					<%} %>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>