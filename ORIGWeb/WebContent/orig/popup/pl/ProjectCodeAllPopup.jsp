<%@page import="com.eaf.orig.shared.util.OrigUtil"%>
<%@page import="com.eaf.orig.ulo.pl.model.app.PLApplicationDataM"%>
<%@page import="com.eaf.orig.ulo.pl.app.utility.DataFormatUtility"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import="org.apache.log4j.Logger"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.PLMessageResourceUtil"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.valuelist.view.webaction.ValueListWebAction" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"/>
<jsp:useBean id="VALUE_LIST" scope="session" class="com.eaf.orig.shared.model.ValueListM" />

<script type="text/javascript" src="tooltip_js/wz_tooltip.js"></script>
<script type="text/javascript" src="tooltip_js/tip_balloon.js"></script>
<!-- <script type="text/javascript" src="orig/js/subform/pl/main_applicant.js"></script> -->
<script type="text/javascript" src="js/ulo.application.javascript.js"></script>
<script language="JavaScript">

$(document).ready(function(){
		$("Tr.ResultOdd").hover(
	    	 function(){$(this).addClass("ResultOdd-haver");},
	    	 function(){$(this).removeClass("ResultOdd-haver");}
	    );
	    
		$("Tr.ResultEven").hover(
	    	 function(){$(this).addClass("ResultEven-haver");},
	    	 function(){$(this).removeClass("ResultEven-haver");}
		);	
		
});
</script>

<%  Logger log = Logger.getLogger(this.getClass()); 
	ORIGCacheUtil origc = ORIGCacheUtil.getInstance();
	PLApplicationDataM plAppM = PLORIGForm.getAppForm();
	if(OrigUtil.isEmptyObject(plAppM)){
		plAppM = new PLApplicationDataM();
	}
	String bussClass = plAppM.getBusinessClassId();
	int applicantPropFieldID =110;
	if(!OrigUtil.isEmptyString(bussClass) && bussClass.contains("XS")){applicantPropFieldID=35;}
%>
<div class="PanelFirst">
	<div class="PanelSecond TextHeaderNormal">
			<div class="PanelThird">
				<table class="TableFrame">
						<tr class="Header">
							<td align="center" width="20%"><%=PLMessageResourceUtil.getTextDescription(request, "CODE") %></td>
							<td align="center" width="80%"><%=PLMessageResourceUtil.getTextDescription(request, "DESCRIPTION") %></td>
						</tr>
						<%
							Vector valueList = VALUE_LIST.getResult(); 
							if(valueList != null && valueList.size() > 1){
								for(int i=1;i<valueList.size();i++){
								
									if(valueList.size()==2){
										Vector elementList = (Vector)valueList.get(1);
									}
						
									Vector elementList = (Vector)valueList.get(i);
									String styleTr = ((i-1)%2==0)?"ResultEven":"ResultOdd";
						%>															
						<tr class="Result-Obj <%=styleTr%>" onclick="javascript:setDataProjectCodeAll('<%=elementList.elementAt(1)%>')">
								<td width="20%" >
									<%=elementList.elementAt(1)%>
								</td>
								<td width="80%" class="alignLeft">
									<%=PLMessageResourceUtil.getTextDescription(request, "DETAIL")%>&nbsp;:&nbsp;
									<%=HTMLRenderUtil.displayHTML((String)elementList.elementAt(2))%>
									<br>
										<%=PLMessageResourceUtil.getTextDescription(request, "PROMOTION")%>&nbsp;:&nbsp;
										<%=HTMLRenderUtil.displayHTML((String)elementList.elementAt(3))%>
									<br>
										<%=PLMessageResourceUtil.getTextDescription(request, "DURATION_TIME")%>
										<%=HTMLRenderUtil.displayHTML(DataFormatUtility.StringDateENToStringDateTHFormatYYYY_MM_DD((String)elementList.elementAt(4)))%>&nbsp;,&nbsp;
										<%=HTMLRenderUtil.displayHTML(DataFormatUtility.StringDateENToStringDateTHFormatYYYY_MM_DD((String)elementList.elementAt(6)))%>
									<br>
										<%=PLMessageResourceUtil.getTextDescription(request, "CUSTOMER_PROPERTY")%>&nbsp;:&nbsp;
										<%String applicantProp="";
										if(null!=(String)elementList.elementAt(7)){applicantProp = origc.getORIGCacheDisplayNameDataM(applicantPropFieldID,(String)elementList.elementAt(7));}%>
										<%=HTMLRenderUtil.displayHTML(applicantProp)%>
								</td>
						</tr>
								<%}/*** for end */
									}else{
								%>
						<tr class="ResultNotFound" id="notFoundTable">
							<td colspan="2">No record found</td>
						</tr>
								<%}	%>
			</table>
			<table width="100%">
						<tr>
						<td align="center">
							<td colspan="2" align="left" height="50">
											<jsp:include page="ValuelistLayer1.jsp" flush="true" />
							</td>
			</table>
		</div>
	</div>
</div>

<input type="text" name="hidden_field" style="display:none">
<input type="text" name="hidden_field" style="display:none">
<%	//set current screen to main Form
	com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager =
	(com.eaf.j2ee.pattern.control.ScreenFlowManager) request.getSession(true).getAttribute("screenFlowManager");
	screenFlowManager.setCurrentScreen("PL_MAIN_APPFORM");
%>