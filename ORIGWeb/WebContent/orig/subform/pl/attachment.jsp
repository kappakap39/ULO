<%@page import="com.eaf.orig.profile.model.UserDetailM"%>
<%@page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.AttachmentUtility"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import="java.util.Vector"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLAttachmentHistoryDataM"%>
<%@ page import="com.eaf.j2ee.pattern.util.DisplayFormatUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLApplicationDataM"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM" />
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler" />
<script type="text/javascript" src="orig/js/subform/pl/attachment.js"></script>
<% 
  org.apache.log4j.Logger log=org.apache.log4j.Logger.getLogger("orig/subform/pl/attachment.jsp");	 
  PLApplicationDataM plApplicationDataM = PLORIGForm.getAppForm();
  ORIGFormUtil formUtil = new ORIGFormUtil();
  String searchType = (String) request.getSession().getAttribute("searchType");
  String displayMode=formUtil.getDisplayMode("ATTACHMENT_SUBFORM", ORIGUser.getRoles(), plApplicationDataM.getJobState(), PLORIGForm.getFormID(), searchType);

  ORIGCacheUtil cacheUtil = ORIGCacheUtil.getInstance();
  Vector<PLAttachmentHistoryDataM> attachmentVect = plApplicationDataM.getAttachmentHistoryVect();
  
  plApplicationDataM.setAttachmentHistoryVect(attachmentVect);

  AttachmentUtility attachmentUtil = AttachmentUtility.getInstance();
%>

 <table class="TableFrame">    
	<tr>
	  <td>
	 	<%=HTMLRenderUtil.displayButtonTagScriptAction("Attach", displayMode, "button", "btnAttach", "buttonNew", "id=btnAttach ", "") %>
	 	<%=HTMLRenderUtil.displayButtonTagScriptAction("Delete", displayMode, "button", "btnDelete", "buttonNew", "id=btnDelete ", "") %>
	 </td>
	 <td width="70%"></td>
	</tr>
	<tr>
		<td colspan="2"> 
			 <div id="attachTable">
				 <%=attachmentUtil.drawAttachmentTable(attachmentVect, ORIGUser.getCurrentRole(), displayMode, ORIGUser.getUserName())%>
			 </div>
		</td>
	</tr>	
</table>
<script language="javaScript">
function uploadAttach(){
	var url = "<%=request.getContextPath()%>/orig/popup/pl/add_attachment.jsp";
	// Sankom Change to Normol Popup
	//openDialog(url,'400' , 'width=400,height=250,status=yes,toolbar=0',);
	var width=600;
	var height=150;
	var left=(screen.width - width) / 2;
	var top=(screen.height - height) / 2;
	//alert(left +' '+top);
	window.open(url,"Attachment",'left='+left+',top='+top+',width='+width+',height='+height+',status=yes,toolbar=0');
}
function deleteAttach(){
	  var  checkBox = document.getElementsByName("chkAttachmentId");
	  var attachmentIds = "";
	  for(var i=0 ; i<checkBox.length ; i++){
	        if(checkBox[i].checked){
	           if(attachmentIds == ""){
	           		attachmentIds = checkBox[i].value;
	           }else{
	           		attachmentIds = attachmentIds+","+checkBox[i].value;
	           }
	        }
	  }
	 if(attachmentIds != ""){
	 	var isdelete = confirm(CONFIRM_DELETE);
	 	if(isdelete){    
	   	    deleteAttachment(attachmentIds);
	    }
	 }else{
	     alert(SELECT_DATA_TO_DELETE);
	 }
}
function drawAttachTable(table) {
	var fields = document.getElementById("attachTable");
	fields.innerHTML = table;
	//$dialog.dialog("close");
}
</script>