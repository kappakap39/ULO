<%@ page import="com.eaf.orig.ulo.pl.ajax.NotePadNotification"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.DataFormatUtility"%>
<%@ page import="java.util.Vector"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil"%>
<%@ page import="com.eaf.orig.shared.util.OrigUtil"%>
<%@ page import="com.eaf.orig.shared.model.NotePadDataM"%>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant"%>
<%@ page contentType="text/html;charset=UTF-8"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>

<%
	Vector<NotePadDataM> noteVect = (Vector<NotePadDataM>) request.getSession().getAttribute("NOTE_PAD_DATAM");
	ORIGCacheUtil origCache = ORIGCacheUtil.getInstance();
	String searchType = (String) request.getSession().getAttribute("searchType");
	String displayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
	String disabledBtn = "";
	if(OrigConstant.SEARCH_TYPE_ENQUIRY.equals(searchType)){
		disabledBtn = ORIGDisplayFormatUtil.DISABLED;
	}

%>
<div id="div-notepad">
	<div class="PanelFirst">
		<div class="PanelSecond TextHeaderNormal">
			<div class="div-error-mandatory" id="div-vercus-madatory"></div>
			<div class="PanelThird">
				<table class="FormFrame">
					<tr>
						<td valign="top"><%=MessageResourceUtil.getTextDescription(request, "NOTEPAD") %> : </td>
						<td><textarea name="notepad-input" id="notepad-input" cols="54" rows="5" maxlength="300" onkeyup="return ismaxlength(this)"></textarea></td>
						<td valign="top"><div class='textL'></div><input class="button" type="button" id="add-notepad" value="Add" <%=disabledBtn %>/></div></td>
					</tr>
				</table>
			</div>
			<div class="PanelThird" id="notepad-div">
				<table class="TableFrame" id="notepad-table">
					<tr class="Header">
		                <td width="20%"><%=MessageResourceUtil.getTextDescription(request, "DATE_TIME") %></td>
		                <td width="15%"><%=MessageResourceUtil.getTextDescription(request, "USER_ID") %></td>
		                <td width="60%"><%=MessageResourceUtil.getTextDescription(request, "NOTEPAD") %></td>				
					</tr>
					<% if(!OrigUtil.isEmptyVector(noteVect)){ %>
						<%
							for(int i=noteVect.size()-1; i >= 0; --i){ 
							NotePadDataM noteM = (NotePadDataM) noteVect.get(i);
							String styleTr = (i%2==0)?"ResultEven":"ResultOdd";
						%>
						<tr class="<%=styleTr%>">
							<td><%=DataFormatUtility.DateEnToStringDateTh(noteM.getCreateDate(), DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S_HHMMSS) %></td>
							<td><%=HTMLRenderUtil.displayHTML(origCache.GetUserNameByuserID(noteM.getCreateBy()))%></td>
							<td><div class='textL'><%=NotePadNotification.DisplayNotePad(noteM.getNotePadDesc())%></div></td>
						</tr>
						<%}%>
					<%}else{%>
						<tr class="ResultNotFound" id="notepad-notfound">
							<td colspan="3">No record found</td>
						</tr>
					<%}%>
				</table>
			</div>
		</div>
	</div>
</div>
<%
	com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager = 
	(com.eaf.j2ee.pattern.control.ScreenFlowManager) request.getSession(true).getAttribute("screenFlowManager");
	screenFlowManager.setCurrentScreen("CARD_MAINTENANCE_DETAIL");
%>
<script type="text/javascript">
function ismaxlength(obj){
	var mlength=obj.getAttribute? parseInt(obj.getAttribute("maxlength")) : "";
	if (obj.getAttribute && obj.value.length>mlength)
		obj.value=obj.value.substring(0,mlength);
}
$(document).ready(function (){
	$('#add-notepad').click(function(){
		if($("#notepad-input").val()== ''){
			alertMassage(ERROR_INPUT_NOTEPAD);
			return false;
		}
		$('#notepad-notfound').remove();	
		var notepad = $("#notepad-input").attr("value");
			notepad = notepad.replace(/\n\r?/g,'<br/>'); 
		var dataString = 'className=com.eaf.orig.ulo.pl.ajax.AddNotePadData&returnType=0&notepad-input='+encodeURI(notepad);
		$.ajax({
			type :"POST",
			url :"AjaxDisplayServlet",
			data :dataString,
			async :false,	
			dataType: "json",
			success : function(data ,status ,xhr){
				jsonDisplayElementById(data);
				$("#notepad-input").val('');
			},
			error : function(data){
			}
		});
	});
});
</script>