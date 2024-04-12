<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.Vector"%>
<%@ page import="com.eaf.orig.ulo.pl.formcontrol.view.form.SearchHandler"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.DataFormatUtility"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler" /> 

<script type="text/javascript" src="orig/js/appform/create_icdc.js"></script>
<%  
	org.apache.log4j.Logger log=org.apache.log4j.Logger.getLogger("orig/dc/create_icdc_screen.jsp");
%>
<div class="PanelFirst">
	<div class="PanelSecond TextHeaderNormal">
		<%=SearchHandler.DisplayErrorMessage(request)%>	
		<div class="PanelThird">
			<table class="FormFrame">
				  <tr> 
                      <td class="textR" nowrap="nowrap" width="40%">
                     	 <%=MessageResourceUtil.getTextDescription(request, "CARD_NO")%> :&nbsp;                      	
                      </td>
                      <td class="inputL" colspan="2">
                      	<%=HTMLRenderUtil.displayInputTagScriptAction("",HTMLRenderUtil.DISPLAY_MODE_EDIT,"","card_no","textbox"," onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onKeyPress=\"keyPressInteger();\"","16") %>
                      	<input type="hidden" name="customertype" id="customertype" value="<%=OrigConstant.CUSTOMER_TYPE_INDIVIDUAL%>">
                      </td>
                  </tr>	
                  <tr>
	                   <td class="textR" nowrap="nowrap" width="40%"></td>
	                   <td class="inputL" colspan="2">
	                      	<input class="button" type="button" name="bt_ok" value="OK" onclick="javascript:createICDC()">
	                   </td>
                  </tr>
            </table>
		</div>
	</div>
</div>