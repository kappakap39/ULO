<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<%@page contentType="text/html;charset=UTF-8"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>

<script type="text/javascript" src="orig/ulo/subform/js/ProjectSubform.js"></script>
<%
	String subformId = "PROJECT_SUBFORM";
	
	Logger logger = Logger.getLogger(this.getClass());
	ArrayList<ApplicationDataM>  applicaationList   = ORIGForm.getObjectForm().filterApplicationLifeCycle();
%>
<section class="table">
	<table width='100%'>
		<tbody>
			<tr>
				<td colspan="3" class="subtoppic label"><%=LabelUtil.getText(request, "PROJECT_CODE")%></td>
			</tr>	
<%if(!Util.empty(applicaationList)){ 
		for(ApplicationDataM appData :applicaationList){ %>		
			<tr>
				<td>&nbsp;&nbsp;</td>
				<td><%=HtmlUtil.getFieldLabel(request, "PROJECT_CODE_DESC")%></td>
				<td><%=FormatUtil.display(appData.getProjectCode()) %></td>
			</tr>
<%		}
	} %>			
		</tbody>
	</table>
</section>

