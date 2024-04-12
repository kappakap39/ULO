<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.PrivilegeProjectCodeDocDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.VerificationResultDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PrivilegeProjectCodeKassetDocDataM"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.orig.ulo.model.app.PrivilegeProjectCodeDataM"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<script type="text/javascript" src="orig\ulo\subform\projectcode\document\js\DocumentfForKAssetSubForm.js"></script>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="EntityForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler"/>
<%
String subformId ="VERIFY_PRIVILEGE_DOC_PROJECT_CODE_SUBFORM";
String displayMode = HtmlUtil.EDIT;
int PRVLG_PROJECT_DOC_INDEX=0;
int PROJECT_DOC_ASSET_INDEX=0;
	
PrivilegeProjectCodeDataM privilegeProjectCode = (PrivilegeProjectCodeDataM)EntityForm.getObjectForm();		
if(Util.empty(privilegeProjectCode)){
	privilegeProjectCode = new PrivilegeProjectCodeDataM();
}	
 PrivilegeProjectCodeDocDataM  prvlgProjectDoc = privilegeProjectCode.getPrivilegeProjectCodeDoc(PRVLG_PROJECT_DOC_INDEX);
if(Util.empty(prvlgProjectDoc)){
	prvlgProjectDoc = new PrivilegeProjectCodeDocDataM();
}
	
PrivilegeProjectCodeKassetDocDataM	prvlgProjectCodeKasset =prvlgProjectDoc.getPrivilegeProjectCodeKassetDoc(PROJECT_DOC_ASSET_INDEX);
if(Util.empty(prvlgProjectCodeKasset)){
	prvlgProjectCodeKasset = new PrivilegeProjectCodeKassetDocDataM();
}
FormUtil formUtil = new FormUtil(subformId,request);
 %>
	<div class="panel panel-default">
		<div class="panel-body" style="padding-bottom : 15px">
			<div class="row form-horizontal">
				<div class="col-sm-12">
					<div class="form-group">
						<div class="col-sm-7 col-md8 bold"><label for="05_DOCUMENT_TYPE"><%=LabelUtil.getText(request, "DOCUMENT_FOR_K_ASSET")%></label></div>
					</div>
				</div>
			</div>
			
			
			<div class="row form-horizontal element-body collapse" aria-expanded="false">
					<div class="col-sm-12">
					<div class="form-group">
					<div class="col-sm-5">
							<%=HtmlUtil.radioInline("KASSET_TYPE", "", "KASSET_TYPE", "", prvlgProjectCodeKasset.getKassetType(), "", "1", HtmlUtil.elementTagId("KASSET_TYPE", "1"), "KASSET_TYPE_RADIO_1", "", formUtil)%>
<!-- 							radio("KASSET_TYPE","", "KASSET_TYPE", prvlgProjectCodeKasset.getKassetType(), "", "1", HtmlUtil.elementTagId("KASSET_TYPE", "1"), "", "", formUtil)%> -->
					</div>
					</div>
<!-- 					<div class="col-sm-4"> -->
<%-- 							<label for="KASSET_TYPE_RADIO_1"><%=LabelUtil.getText(request, "KASSET_TYPE_RADIO_1")%></label> --%>
<!-- 					</div> -->
					</div>
						<div class="clearfix"></div>
						<div class="col-sm-offset-1 col-sm-10">
							<div class="form-group">
								<table class="table table-bordered"> 
									<tbody>
										<tr class="tabletheme_header">
											<th><%=HtmlUtil.getHeaderLabel(request, subformId, "MONTH_1", "MONTH_1")%></th>
											<th><%=HtmlUtil.getHeaderLabel(request, subformId, "MONTH_2", "MONTH_2")%></th>
											<th><%=HtmlUtil.getHeaderLabel(request, subformId, "MONTH_3", "MONTH_3")%></th>
											<th><%=HtmlUtil.getHeaderLabel(request, subformId, "MONTH_4", "MONTH_4")%></th>
											<th><%=HtmlUtil.getHeaderLabel(request, subformId, "MONTH_5", "MONTH_5")%></th>
											<th><%=HtmlUtil.getHeaderLabel(request, subformId, "MONTH_6", "MONTH_6")%></th>
										</tr>
										<tr>
											<td><%=HtmlUtil.currencyBox("MONTH1_AMT", "MONTH1_AMT", "MONTH1_AMT", FormatUtil.toBigDecimal(prvlgProjectCodeKasset.getMonth1m(), true), "", true, "15", "", "", formUtil) %></td>
											<td><%=HtmlUtil.currencyBox("MONTH2_AMT", "MONTH2_AMT", "MONTH2_AMT", FormatUtil.toBigDecimal(prvlgProjectCodeKasset.getMonth2m(), true), "", true, "15", "", "", formUtil) %></td>
											<td><%=HtmlUtil.currencyBox("MONTH3_AMT", "MONTH3_AMT", "MONTH3_AMT", FormatUtil.toBigDecimal(prvlgProjectCodeKasset.getMonth3m(), true), "", true, "15", "", "", formUtil) %></td>
											<td><%=HtmlUtil.currencyBox("MONTH4_AMT", "MONTH4_AMT", "MONTH4_AMT", FormatUtil.toBigDecimal(prvlgProjectCodeKasset.getMonth4m(), true), "", true, "15", "", "", formUtil) %></td>
											<td><%=HtmlUtil.currencyBox("MONTH5_AMT", "MONTH5_AMT", "MONTH5_AMT", FormatUtil.toBigDecimal(prvlgProjectCodeKasset.getMonth5m(), true), "", true, "15", "", "", formUtil) %></td>
											<td><%=HtmlUtil.currencyBox("MONTH6_AMT", "MONTH6_AMT", "MONTH6_AMT", FormatUtil.toBigDecimal(prvlgProjectCodeKasset.getMonth6m(), true), "", true, "15", "", "", formUtil) %></td>
										</tr>
									</tbody>
								</table>	
							</div>					
						</div>
					
					
					<div class ="clearfix"></div>
					<div class="col-sm-12">
						<div class="form-group">
							<div class="col-sm-5">
								<%=HtmlUtil.radioInline(request, "KASSET_TYPE", "", "KASSET_TYPE", "FUNDS_OWN_UNDER_6_MONTH", prvlgProjectCodeKasset.getKassetType(), subformId, "", "2", HtmlUtil.elementTagId("KASSET_TYPE", "2"), "KASSET_TYPE_RADIO_2", "control-label", formUtil)%>
<!--  								radio("KASSET_TYPE","","KASSET_TYPE", prvlgProjectCodeKasset.getKassetType(),"", "2", HtmlUtil.elementTagId("KASSET_TYPE", "2"), "", "", formUtil)%> -->
							</div>				
	<%-- 							<label for="KASSET_TYPE_RADIO_2"><%=LabelUtil.getText(request, "KASSET_TYPE_RADIO_2")%></label> --%>
							<div class="col-sm-7">
								<%=HtmlUtil.currencyBox("FUND_6M", "", "FUND_6M", prvlgProjectCodeKasset.getFund6m(), "", true, "15", "", "col-sm-2 col-md-3", formUtil)%>
							</div>
						</div>
				</div>	
			</div>
		</div>
	</div>