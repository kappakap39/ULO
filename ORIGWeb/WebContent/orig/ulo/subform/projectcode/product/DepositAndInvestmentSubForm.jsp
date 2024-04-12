<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.util.FormEffects"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.PrivilegeProjectCodeProductSavingDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PrivilegeProjectCodeDataM"%>
<%@page import="java.util.ArrayList"%>
<script type="text/javascript" src="orig\ulo\subform\projectcode\product\js\DepositAndInvestmentSubForm.js"></script>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="EntityForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler"/>
<%
	String subformId="VERIFY_PRIVILEGE_PRODUCT_PROJECT_CODE_SUBFORM"; 
	
	PrivilegeProjectCodeDataM privilegeProjectCode = (PrivilegeProjectCodeDataM)EntityForm.getObjectForm();		
	if(Util.empty(privilegeProjectCode)){
		privilegeProjectCode = new PrivilegeProjectCodeDataM();
	}
	
	ArrayList<PrivilegeProjectCodeProductSavingDataM>  prvlgProjectCodeProductSavings=  privilegeProjectCode.getPrivilegeProjectCodeProductSavings();
	if(Util.empty(prvlgProjectCodeProductSavings)){
		prvlgProjectCodeProductSavings = new ArrayList<PrivilegeProjectCodeProductSavingDataM>();
	}
	FormUtil formUtil = new FormUtil(subformId,request);
	FormEffects formEffect = new FormEffects(subformId,request);

 %>
<div class="panel panel-default">
	<div class="panel-body">
		<div class="row form-horizontal">
			<div class="col-sm-12">
				<div class="form-group">
					<div class="col-sm-7 col-md8 bold"><%=LabelUtil.getText(request, "DEPOSIT_AND_INVESTMENT")%></div>
				</div>
			</div>
		</div>
		<div class="clearfix"></div>
		<div class="row form-horizontal">
			<div class="col-sm-12">
				<table class="table table-hover table-bordered" style="table-layout:fixed">
					<tbody>
						<tr class="tabletheme_header">
							<th width="5%"></th>
							<th><%=HtmlUtil.getHeaderLabel(request, subformId, "TYPE_OF_DEPOSIT", "TYPE_OF_DEPOSIT")%></th>
							<th><%=HtmlUtil.getHeaderLabel(request,subformId, "ACCOUNT_NO", "ACCOUNT_NO") %></th>
							<th><%=HtmlUtil.getHeaderLabel(request,subformId, "OWNER_PART", "OWNER_PART") %></th>
							<th><%=HtmlUtil.getHeaderLabel(request,subformId, "AMOUNT_OF_MONEY_START", "AMOUNT_OF_MONEY_START") %></th> 
							<th><%=HtmlUtil.getHeaderLabel(request,subformId, "AMOUNT_OF_MONEY", "AMOUNT_OF_MONEY_CURRENT") %></th>
							<th><%=HtmlUtil.getHeaderLabel(request,subformId, "CIS_NUMBER", "CIS_NUMBER") %></th>
							<th><%=HtmlUtil.getHeaderLabel(request,subformId, "RELATIONSHIP_TRANSFER", "RELATIONSHIP_TRANSFER") %></th>
							<td width="5%" rowspan="9999" style="vertical-align: bottom; padding-bottom: 15px;"><%=HtmlUtil.button("ADD_PRODUCT_BTN","ADD_PRODUCT_BTN","","btnsmall_add","", formEffect) %></td>
						</tr>
						<tr>
					<%if(!Util.empty(prvlgProjectCodeProductSavings)){ 
						for(PrivilegeProjectCodeProductSavingDataM prvlgProjectCodeProductSaving :prvlgProjectCodeProductSavings){
							String SEQ = FormatUtil.getString(prvlgProjectCodeProductSaving.getSeq());
							String onclickActionJS= "onclick=DELETE_PRODUCT_BTNActionJS('"+SEQ+"')";%>
							<td><%=HtmlUtil.icon("DELETE_PRODUCT_BTN", "DELETE_PRODUCT_BTN","btnsmall_delete", onclickActionJS, formEffect) %></td>
							<td>
								<%=HtmlUtil.dropdown("PRODUCT_TYPE",SEQ, "PRODUCT_TYPE_"+SEQ, "TYPE_OF_DEPOSIT", "", 
								prvlgProjectCodeProductSaving.getProductType(), "",SystemConstant.getConstant("FIELD_ID_PRVLG_INVEST_TYPE"),"", "", "", formUtil)%>
							</td>
							<td>
								<%=HtmlUtil.textBox("ACCOUNT_NO",SEQ,"ACCOUNT_NO_"+SEQ,"ACCOUNT_NO",
								prvlgProjectCodeProductSaving.getAccountNo(),"","20","","",formUtil)%>
							</td>
							<td>
								<%=HtmlUtil.numberBox("HOLDING_RATIO",SEQ, "HOLDING_RATIO_"+SEQ, "OWNER_PART",
									prvlgProjectCodeProductSaving.getHoldingRatio(), "", "##","","", true, "1", "", "", formUtil) %>
							</td>
							<td>
								<%=HtmlUtil.currencyBox("ACCOUNT_BALANCE", SEQ, "ACCOUNT_BALANCE_"+SEQ, "ACCOUNT_BALANCE", 
								prvlgProjectCodeProductSaving.getAccountBalanceStart(), "", true, "15",  "", "", formUtil)%>
							</td>
							<td>
								<%=HtmlUtil.currencyBox("ACCOUNT_BALANCE_CURRENT", SEQ, "ACCOUNT_BALANCE_CURRENT_"+SEQ, "ACCOUNT_BALANCE_CURRENT", 
								prvlgProjectCodeProductSaving.getAccountBalance(), "", true, "15",  "", "", formUtil)%>
							</td>
							<td>
								<%=HtmlUtil.textBox("CIS_NUMBER",SEQ,"CIS_NUMBER_"+SEQ,"CIS_NUMBER",prvlgProjectCodeProductSaving.getCisNo(),"","10","","",formUtil)%>
							</td>
							<td>
								<%=HtmlUtil.dropdown("RELATIONSHIP_TRANSFER",SEQ, "RELATIONSHIP_TRANSFER_"+SEQ, "RELATIONSHIP_TRANSFER", "", 
								prvlgProjectCodeProductSaving.getRelationshipTransfer(), "",SystemConstant.getConstant("FIELD_ID_PRVLG_RELATIONSHIP"),"", "", "", formUtil)%>
							</td>
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