<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.apache.log4j.Logger"%>
<%@ page import="com.eaf.orig.ulo.pl.formcontrol.view.form.SearchHandler"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.PLMessageResourceUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.DataFormatUtility"%>
<%@ page import="java.util.Vector" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLApplicationDataM"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLSaleInfoDataM"%>
<%@ page import="com.eaf.j2ee.pattern.util.ErrorUtil"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"/>
<jsp:setProperty name="formHandlerManager" property="currentFormHandler" value="PLORIGForm" />

<script type="text/javascript" src="orig/js/appform/create_app_form.js"></script>
<%
	Logger logger = Logger.getLogger(this.getClass());
	
	String displayMode = HTMLRenderUtil.DISPLAY_MODE_EDIT;
	ORIGCacheUtil origc = ORIGCacheUtil.getInstance();

	PLApplicationDataM applicationM = PLORIGForm.getAppForm();
	if(null==applicationM){
		applicationM = new PLApplicationDataM();
		PLORIGForm.setAppForm(applicationM);
	}
	
	PLSaleInfoDataM saleInfoM = applicationM.getSaleInfo();
	if(null==saleInfoM){
		saleInfoM = new PLSaleInfoDataM();
		applicationM.setSaleInfo(saleInfoM);
	}
	Vector vMainProductDomain 	= (Vector)origc.loadCacheByName("MainProductDomain");
	Vector vMainProductGroup 	= (Vector)origc.loadCacheByName("MainProductGroup");
 	Vector vCustomerType = (Vector)origc.getNaosCacheDataMs("ALL_ALL_ALL", 1);
	String role = ORIGUser.getCurrentRole();
%>
<div class="PanelFirst">
	<div class="PanelSecond TextHeaderNormal">
		<%=SearchHandler.DisplayErrorMessage(request)%>	
		<div class="PanelThird">								
			<table class="FormFrame">
				  <tr> 
                      <td class="textR" nowrap="nowrap" width="40%"><%=PLMessageResourceUtil.getTextDescription(request, "PRODUCT_DOMAIN") %>&nbsp;&nbsp;:&nbsp;</td>
                      <td class="inputL" colspan="2" id="div_productdomain"><%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(vMainProductDomain,OrigConstant.FUNDING_BORROWING ,"productdomain",displayMode," style='width:165px' onChange=\"javascript:getProductGroup()\"")%></td>
                  </tr>	
                  <tr> 
                      <td class="textR" nowrap="nowrap" width="40%"><%=PLMessageResourceUtil.getTextDescription(request, "PRODUCT_GROUP") %><font color="#FF0000">*&nbsp;</FONT>:&nbsp;</TD>
                      <td class="inputL" colspan="2" id="div_productgroup"><%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(null,"" ,"productgroup",displayMode," style='width:165px' onChange=\"javascript:getProductFamily()\"")%></td>
                  </tr>
                  <tr> 
                    <td class="textR" nowrap="nowrap" width="40%"><%=PLMessageResourceUtil.getTextDescription(request, "PRODUCT_FAMILY") %><font color="#FF0000">*&nbsp;</FONT>:&nbsp;</td>
                    <td class="inputL" colspan="2" id="div_productfamily"><%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(null, "","productfamily",displayMode," style='width:165px' ") %></td>
                  </tr>
                  <tr>
                    <td class="textR" nowrap="nowrap" width="40%"><%=PLMessageResourceUtil.getTextDescription(request, "PRODUCT") %><font color="#FF0000">*&nbsp;</FONT>:&nbsp;</td>
                    <td class="inputL" colspan="2" id="div_product"><%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(null,"","product",displayMode," style='width:165px' ")%></td>
                  </tr>
                  <tr>
                    <td class="textR" nowrap="nowrap" width="40%"><%=PLMessageResourceUtil.getTextDescription(request, "CUSTOMER_TYPE2") %><font color="#FF0000">*&nbsp;</FONT>:&nbsp;</td>
                    <td class="inputL" colspan="2" id="div_customerType"><%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(vCustomerType,OrigConstant.CUSTOMER_TYPE_INDIVIDUAL,"customertype",displayMode," style='width:165px' ")%></td>
                  </tr>
                  <tr>
                    <td class="textR" nowrap="nowrap" width="40%"><%=PLMessageResourceUtil.getTextDescription(request, "SALE_TYPE") %><font color="#FF0000">*&nbsp;</FONT>:&nbsp;</td>
                    <td class="inputL" colspan="2" id="div_saleType"><%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(null,"","saleType",displayMode," style='width:165px' ")%></td>
                  </tr>
                  <tr>
                    <td class="textR" nowrap="nowrap" class="seller_mandatory"><%=PLMessageResourceUtil.getTextDescription(request, "BRANCH_CODE")%>						
					<span class="seller_mandatory_check"></span><span class="smecheck"><font color="#ff0000">*</font></span>&nbsp;:&nbsp;</td>
					<td class="inputL" nowrap="nowrap" align="left">
						<%=HTMLRenderUtil.displayPopUpTagNotSetScriptAction(saleInfoM.getSalesBranchCode(),displayMode,"8","seller_branch_code","textboxCode","","8","...","button-dialog")%>&nbsp;
						<%=HTMLRenderUtil.displayInputTagScriptAction("","VIEW","25","seller_branch_name","textbox textboxReadOnly","","25")%>
						<%=HTMLRenderUtil.displayHiddenField(saleInfoM.getSalesBranchCode(),"seller_branch_Title") %>
					</td>
				  </tr>
                  <tr> 
   					<td class="textR" nowrap="nowrap" width="40%"></td>
                    <td class="inputL" colspan="2"><input type="button" name="okBtn" value=" <%=PLMessageResourceUtil.getTextDescription(request, "OK") %> " class="button" onClick="javascript:validate()"></td>
             	 </tr>			                          
             </table>		               
		</div>
	</div>
</div>
