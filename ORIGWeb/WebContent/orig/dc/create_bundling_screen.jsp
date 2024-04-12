<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.eaf.orig.ulo.pl.formcontrol.view.form.SearchHandler"%>
<%@ page import="com.eaf.j2ee.pattern.util.ErrorUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.PLMessageResourceUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.DataFormatUtility"%>
<%@ page import="org.apache.log4j.Logger"%>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Vector" %> 
<%@ page import="com.eaf.cache.TableLookupCache" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<script type="text/javascript" src="orig/js/appform/create_app_bundling.js"></script>

<%
	Logger logger = Logger.getLogger(this.getClass());	
	String displayMode = HTMLRenderUtil.DISPLAY_MODE_EDIT;
	ORIGCacheUtil origc = ORIGCacheUtil.getInstance();
	Vector vMainProductDomain 	= (Vector)origc.loadCacheByName("MainProductDomain");
	Vector vMainProductGroup 	= (Vector)origc.loadCacheByName("MainProductGroup");
 	Vector vCustomerType = (Vector)origc.getNaosCacheDataMs("ALL_ALL_ALL", 1);
	String role = ORIGUser.getCurrentRole();
	Vector creditCardResult = (Vector)origc.getNaosCacheDataMs("ALL_ALL_ALL", 100);
	Vector creditCardAppScore = (Vector)origc.getNaosCacheDataMs("ALL_ALL_ALL", 101); 	 
	
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
                  	<td class="inputL" colspan="2" id="div_saleType"><%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(null,"","saleType",displayMode," style='width:165px' onChange=\"changeSaleType()\" ")%></td>
                </tr>
                <tr>
                  	<td class="textR" nowrap="nowrap" width="40%"><%=PLMessageResourceUtil.getTextDescription(request, "SALLER_BRANCH") %><font color="#FF0000">*&nbsp;</FONT>:&nbsp;</td>
                  	<td class="inputL" colspan="2" id="div_branchCode">
                  	<%=HTMLRenderUtil.displayPopUpTagNotSetScriptAction("",displayMode,"8","createbundling_branch_code","textboxCode","","8","...","button-dialog")%>&nbsp;
  					<%=HTMLRenderUtil.displayInputTagScriptAction(HTMLRenderUtil.displayHTML(""), "VIEW", "25", "createbundling_branch_name","textbox textboxReadOnly","","25")%>
  					<%=HTMLRenderUtil.displayHiddenField("","createbundling_branch_Title") %>
  				</td>
                </tr>
              	<tr>
                  	<td class="textR" nowrap="nowrap" width="40%">Credit Card Result :&nbsp;</td>
                  	<td class="inputL" colspan="2" id="div_customerType"><%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(creditCardResult, "","creditCardResult","EDIT"," style='width:165px' disabled  onChange=\"creditCardResultChange()\"")%></td>
                </tr>
                <tr>
                  	<td class="textR" nowrap="nowrap" width="40%">Credit Card App Score</td> 						 
                  	<td class="inputL" colspan="2" id="div_customerType"><%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(creditCardAppScore, "","creditCardAppScore","EDIT"," style='width:165px' disabled ")%></td>
                </tr>                                                
               	<tr> 
					<td class="textR" nowrap="nowrap" width="40%"></td>
                 	<td class="inputL" colspan="2"><input type="button" name="okBtn" value=" <%=PLMessageResourceUtil.getTextDescription(request, "OK") %> " class="button" onClick="javascript:validate()"></td>
                </tr>			                          
             </table>		               
		</div>
	</div>
</div>
<script>
 var ERROR_SELLER_BRANCH_CODE  = "<%=ErrorUtil.getShortErrorMessage(request,"ERROR_BUNDLE_SELLER_BRANCH_CODE")%>";
 var ERROR_CREDIT_CARD_RESULT  ="<%=ErrorUtil.getShortErrorMessage(request,"ERROR_CREDIT_CARD_RESULT")%>" ;
 var ERROR_CREDIT_CARD_APP_SCORE  ="<%=ErrorUtil.getShortErrorMessage(request,"MSG_CREDIT_CARD_APP_SCORE")%>" ;
</script>
