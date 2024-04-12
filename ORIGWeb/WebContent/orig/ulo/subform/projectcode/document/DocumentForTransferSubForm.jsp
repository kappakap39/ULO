<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.util.FormEffects"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.PrivilegeProjectCodeDocDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PrivilegeProjectCodeTransferDocDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PrivilegeProjectCodeDataM"%>

<script type="text/javascript" src="orig\ulo\subform\projectcode\document\js\DocumentForTransferSubForm.js"></script>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="EntityForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler"/>
<%
String subformId ="VERIFY_PRIVILEGE_DOC_PROJECT_CODE_SUBFORM";
Logger logger = Logger.getLogger(this.getClass());
int PRVLG_PROJECT_DOC_INDEX=0;
	
PrivilegeProjectCodeDataM privilegeProjectCode = (PrivilegeProjectCodeDataM)EntityForm.getObjectForm();		
if(Util.empty(privilegeProjectCode)){
	privilegeProjectCode = new PrivilegeProjectCodeDataM();
}	

PrivilegeProjectCodeDocDataM  prvlgProjectDoc = privilegeProjectCode.getPrivilegeProjectCodeDoc(PRVLG_PROJECT_DOC_INDEX);
if(Util.empty(prvlgProjectDoc)){
	prvlgProjectDoc = new PrivilegeProjectCodeDocDataM();
}
	
ArrayList<PrivilegeProjectCodeTransferDocDataM> 	prvlgProjectCodeTransferList =	prvlgProjectDoc.getPrivilegeProjectCodeTransferDocs();
if(Util.empty(prvlgProjectCodeTransferList)){
	prvlgProjectCodeTransferList = new ArrayList<PrivilegeProjectCodeTransferDocDataM>();
}
FormUtil formUtil = new FormUtil(subformId,request);
FormEffects formEffect = new FormEffects(subformId,request);

 %>
<div class="panel panel-default">
	<div class="panel-body">
		<div class="row form-horizontal">
			<div class="col-sm-12">
				<div class="form-group">
					<div class="col-sm-7 col-md8 bold"><label for="06_DOCUMENT_TYPE"><%=LabelUtil.getText(request, "DOCUMENT_FOR_TRANSFER")%></label></div>
				</div>
			</div>
		</div>
		<div class="row form-horizontal element-body collapse">
			<div class="col-sm-12">
				<table class="table table-bordered">
					<tbody>
						<tr class="tabletheme_header">
							<th></th>
							<th><%=HtmlUtil.getHeaderLabel(request, subformId, "TYPE_OF_DEPOSIT", "TYPE_OF_DEPOSIT")%></th>
							<th><%=HtmlUtil.getHeaderLabel(request, subformId, "ACCOUNT_NO", "ACCOUNT_NO")%></th>
							<th><%=HtmlUtil.getHeaderLabel(request, subformId, "OWNER_PART", "OWNER_PART")%></th>
							<th><%=HtmlUtil.getHeaderLabel(request, subformId, "AMOUNT_OF_MONEY", "AMOUNT_OF_MONEY")%></th>
							<th><%=HtmlUtil.getHeaderLabel(request, subformId, "ID_CARD_NO", "ID_CARD_NO")%></th>
							<th><%=HtmlUtil.getHeaderLabel(request, subformId, "CIS_NO", "CIS_NO")%></th>
							<th><%=HtmlUtil.getHeaderLabel(request, subformId, "RELATION_OF_DOC_TRANSFER", "RELATION_OF_DOC_TRANSFER")%></th>
							<td rowspan="9999" style="vertical-align: bottom; padding-bottom: 15px;"><%=HtmlUtil.button("ADD_BTN", "ADD_BTN", "", "btnsmall_add", "", formEffect) %></td>
						</tr>
				<% if(!Util.empty(prvlgProjectCodeTransferList)){ 
						for(PrivilegeProjectCodeTransferDocDataM prvlgProjectCodeTransfer :prvlgProjectCodeTransferList){
							String SEQ = FormatUtil.getString(prvlgProjectCodeTransfer.getSeq());
							String  onclickActionJS ="onclick=DELETE_DOCUMENT_TRANS_BTNActionJS('"+SEQ+"')";%>
						<tr>
							<td><%=HtmlUtil.icon("DELETE_DOCUMENT_TRANS_BTN", "DELETE_DOCUMENT_TRANS_BTN", "btnsmall_delete", onclickActionJS, formEffect) %></td>
							<td><%=HtmlUtil.dropdown("INVEST_TYPE", SEQ, "", "INVEST_TYPE", "", prvlgProjectCodeTransfer.getInvestType(), "", SystemConstant.getConstant("FIELD_ID_PRVLG_INVEST_TYPE"), "", "", "", formUtil)%></td>
							<td><%=HtmlUtil.textBox("ACCOUNT_NO", SEQ, "", "ACCOUNT_NO", prvlgProjectCodeTransfer.getAccountNo(), "", "20", "", "", formUtil)%></td>
							<td><%=HtmlUtil.numberBox("HOLDING_RATIO", SEQ, "", "HOLDING_RATIO", prvlgProjectCodeTransfer.getHoldingRatio(), "", "", "", "", true, "1", "", "", formUtil)%></td>
							<td><%=HtmlUtil.currencyBox("AMOUNT", SEQ, "", "AMOUNT", prvlgProjectCodeTransfer.getAmount(), "", true, "15", "", "", formUtil)%></td>
							<td><%=HtmlUtil.textBox("ID_NO", SEQ, "", "ID_NO", prvlgProjectCodeTransfer.getIdNo(), "", "13", "", "", formUtil)%></td>
							<td><%=HtmlUtil.textBox("CIS_NO", SEQ, "", "CIS_NO", prvlgProjectCodeTransfer.getCisNo(), "", "13", "", "", formUtil)%></td>
							<td><%=HtmlUtil.dropdown("RELATIONSHIP", SEQ, "", "RELATIONSHIP", "", prvlgProjectCodeTransfer.getRelationship(), "", SystemConstant.getConstant("FIELD_ID_PRVLG_RELATIONSHIP"), "", "", "", formUtil)%></td>
						</tr>
				<%	}
				}else{ %>
						<tr><td colspan="8" align="center"><%=LabelUtil.getText(request, "NO_RECORD_FOUND")%></td></tr>
				<%} %>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>