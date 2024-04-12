<%@page import="com.eaf.orig.ulo.pl.formcontrol.view.form.SearchHandler"%>
<%@page import="com.eaf.orig.ulo.pl.app.utility.PLMessageResourceUtil"%>
<%@page import="com.eaf.orig.ulo.pl.model.app.PLImportSpecialPointDataM"%>
<%@page import="com.eaf.orig.ulo.pl.app.utility.DataFormatUtility"%>
<%@page import="com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.Vector"%>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="java.util.Properties"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"/>
<jsp:useBean id="VALUE_LIST" scope="session" class="com.eaf.orig.shared.model.ValueListM" />

<script type="text/javascript" src="js/popcalendar-screen.js"></script>
<script type="text/javascript" src="orig/js/sup/import.special.point.js"></script>

<%  org.apache.log4j.Logger log=org.apache.log4j.Logger.getLogger("orig/sup/import_special_point.jsp");
    
    String respone = (String)request.getSession().getAttribute("respone");
    String displayMode = HTMLRenderUtil.DISPLAY_MODE_EDIT;
    
	ORIGUtility utility = new ORIGUtility();
	Vector vProduct = utility.loadCacheByActive("MainProduct");
	
	ORIGCacheUtil origCache = ORIGCacheUtil.getInstance();
	Vector vPriority = (Vector)origCache.getNaosCacheDataMs("ALL_ALL_ALL", 61);
	
	SearchHandler HandlerM = (SearchHandler) request.getSession().getAttribute("SEARCH_DATAM");
	if(HandlerM == null){
		HandlerM = new SearchHandler();
	}
	SearchHandler.SearchDataM searchDataM = HandlerM.getSearchM();
	if(searchDataM == null){
		searchDataM = new SearchHandler.SearchDataM();
	}
	if(HandlerM.getErrorMessage() !=null && !"".equals(HandlerM.getErrorMessage())){
		%>
			<table width="100%" style="background-color: white;">
				<tr>
					<td style="background-color: white; color: red;" class="textL">*<%=HTMLRenderUtil.displayHTML(searchDataM.getPrefixErrorMsg())
					+HandlerM.getErrorMessage()+" "
					+HTMLRenderUtil.displayHTML(searchDataM.getSuffixErrorMsg())%></td>
				</tr>
			</table>
			
		<%
	}
	
%>
<div class="nav-inbox">
	<div class="PanelFirst">
		<div class="PanelSecond TextHeaderNormal">
			<div class="PanelThird">
				<fieldset class="field-set">
				<legend>Import</legend>
					<table class="FormFrame">
						<tr>
                        	<td class="textColS" width="40%"><%=PLMessageResourceUtil.getTextDescription(request, "DATA_DATE")%><span style="color: red">*</span> :</td>
                        	<td class="inputCol" width="60%"><%=HTMLRenderUtil.displayInputTagJsDate("", "", displayMode, "15", "dataDate", "textbox","", "") %></td>
                        </tr>
                        <tr height="25">
                            <td class="textColS"><%=PLMessageResourceUtil.getTextDescription(request, "FILE_IMPORT_POINT")%><span style="color: red">*</span> :</td>
                            <td class="inputCol"><div id="iFile"><input type="file" name="importFile" id="fileName"></div></td>
                        </tr>
                        <tr height="25">
                            <td align="center" colspan="2"><input type="button" name="browse" value="Import" class="button" onclick="importExcelfile('<%=OrigConstant.ExcelType.IMPORT_SPECIAL_POINT%>',this.form)">&nbsp;<input type="button" name="browse" value="Cancel" class="button" onclick="cancel()"></td>
                        </tr>
                	</table>
				</fieldset>
			</div>
			<%if(respone!=null){ %>
            	<span id="resporn"><%=respone%></span>
            <%} %>
		</div>
	</div>
</div>
<%=HTMLRenderUtil.displayHiddenField("", "requestType") %>
