<%@ page import="java.util.*" %>
<%@ page import="com.eaf.orig.shared.model.AttachmentHistoryDataM" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.j2ee.system.LoadXML" %>
<%@ page import="com.eaf.orig.shared.model.ApplicationDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>


<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>

<% 
	ApplicationDataM applicationDataM = ORIGForm.getAppForm();
	if(applicationDataM == null){
		applicationDataM = new ApplicationDataM();
	}
	ORIGFormUtil formUtil = new ORIGFormUtil();
	
	MessageResourceUtil msgUtil = MessageResourceUtil.getInstance();
	//String displayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
	String searchType = (String) request.getSession().getAttribute("searchType");
	String displayMode = formUtil.getDisplayMode("ATTACHMENT_SUBFORM", ORIGUser.getRoles(), applicationDataM.getJobState(), ORIGForm.getFormID(), searchType);
	Vector attachmentVect = ORIGForm.getAppForm().getAttachmentVect();
	String path = request.getContextPath() +"/en_US/images/pic1.gif ";
	StringBuffer aaBuf = new StringBuffer();
	StringBuffer bbBuf = new StringBuffer();
	StringBuffer reBuf = new StringBuffer();
	AttachmentHistoryDataM historyDataM;
	//String url =  "http://"+request.getServerName()+":"+ request.getServerPort() +"" +NaosConstant.OFFER_PATH;
	String url = "";
	
	String disabledBtn = "";
	if(!displayMode.equals(ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT)){
		disabledBtn = ORIGDisplayFormatUtil.DISABLED;
	}
%>

<table cellpadding="" cellspacing="1" width="100%" align="center" border="0">
	<TR height="40"><TD><div id="msgErr"></div> </TD></TR>
	<TR>
		<TD colspan="3" align="center" class="text-header-action1">
			<div name="image_div" id="image_div" class="inputformnew2" style="OVERFLOW: scroll; width: 500; height: 100px;">
			</div>
		</TD>
	</TR>
	<TR>
		<td width="66%"></td>
		<TD width="7%">
			<input type="button" class="smallbtn2" name="attachButton" value="<%=MessageResourceUtil.getTextDescription(request, "ATTACH") %>" onclick="uploadImage();"  >
		</td>
		<td width="7%">
			<input type="button" class="smallbtn2" name="delAttachButton" value="<%=MessageResourceUtil.getTextDescription(request, "DELETE") %>" onclick="deleteImage(appFormName)"  >
		</TD>
		<td width="20%" height="30">&nbsp;</td>
	</TR>
	<TR><td height="5"></td></TR>
</table>
<input type="hidden" name="imageAttachSubmit" value="<%=displayMode%>"> 
<script language="javascript">
var div_image = "<div name=\"image_div\" id=\"image_div\"  class=\"inputformnew2\" style=\"OVERFLOW: scroll; width: 300; height: 70px\"></div>";
var tab_image_h = "<TABLE border=\"0\" width=\"300\" bgColor=\"#ffffff\">";
var tab_image_l = "<TR width = 300>";
var aa = "<%=aaBuf.toString()%>";
var bb = "<%=bbBuf.toString()%>";
var tab_name_h = "</TR>";
var tab_name_l = "</TABLE>"  
image_div.innerHTML = tab_image_h+aa+tab_image_l+tab_name_h+bb+tab_name_l;
reWriteLoadImage();
function addImage(fileno, filename,filesize){
	// add to model	 	 	 var isNav=(navigator.appName.indexOf("Netscape")!=-1);
	aa += "<TD><CENTER>";
	aa += "<a href=\"javascript:showImage('"+fileno+"')\" >";	
	aa += "<img src=\"<%=request.getContextPath()%>/en_US/images/pic1.gif\" border=\"0\" >";
	aa += "</a>";
	aa += "</CENTER><input type=\"checkbox\" name=\"docId\" value=\""+fileno+"\">";
	aa += "<font class=\"font2\">";
	aa += filename+" :"+filesize+" bytes</font></TD>";
	
	document.getElementById("image_div").innerHTML = tab_image_h+aa+tab_image_l+tab_name_h+bb+tab_name_l ;
}
function reWriteImage(){
	<%
	int loop=0;
	String count = request.getParameter("count");
	if(count!=null && count.length()>0) 
		loop = Integer.parseInt(count);
	if(loop>0){
	reBuf.append("");
	reBuf.append("<TABLE><TR>");
		for(int index=1; index<=loop ;index++){
	String docId =  request.getParameter("docId"+index+"");
	String fileName =  java.net.URLDecoder.decode(request.getParameter("fileName"+index+""));
	String size =  request.getParameter("size"+index+"");
	int sizeKB = (int)Math.round(Double.parseDouble(size)/1024);
	String docType = request.getParameter("docType"+index+"");
	reBuf.append("<TD>");
	reBuf.append("<TABLE>");
	reBuf.append("<TR><TD><CENTER>");
	reBuf.append("<a href=javascript:showImage('"+docId+"')><img src="+path+" border=0></a>");
	reBuf.append("</CENTER</TD></TR>");
	reBuf.append("<input type=hidden name='docId"+index+"' value='"+docId+"' id='docId'> ");
	reBuf.append(fileName+":"+sizeKB+" KB</TD>");
	reBuf.append("</TR></TABLE>");
	reBuf.append("</TD>");
	reBuf.append("<input type=hidden name='docId"+index+"' value='"+docId+"'> ");
	reBuf.append("<input type=hidden name='fileName"+index+"' value=\\"+"\""+java.net.URLEncoder.encode(fileName)+"\\"+"\""+"> ");
	reBuf.append("<input type=hidden name='size"+index+"' value='"+size+"'> ");
	reBuf.append("<input type=hidden name='docType"+index+"' value='"+docType+"'>");
	} // end for
	reBuf.append("<input type=hidden name='loop'  value='"+count+"' id='loop'>");
	reBuf.append("</TR></TABLE>");
	} // end if 1
	%> 
	var isNav=(navigator.appName.indexOf("Netscape")!=-1);
	var newaa = "<%=reBuf.toString()%>";
	
	document.getElementById("image_div").innerHTML = tab_image_h+newaa+tab_image_l+tab_name_h+bb+tab_name_l ;

}
function uploadImage(){
	var form = document.appFormName;
	if(form.imageAttachSubmit.value=="<%=ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT%>"){ 	
// 	form.submitAppBtn.disabled = true;
//  form.saveAppBtn.disabled = true;
 	}
//  if(form.cancelAppBtn){
// 	form.cancelAppBtn.disabled = true;
// 	}
	form.attachButton.disabled = true;
	form.delAttachButton.disabled = true;
	document.getElementById("msgErr").innerHTML='';
	var url = "<%=request.getContextPath()%>/orig/appform/app_addimage.jsp";
	openDialog(url, 380, 150,scrollbars=0, setPrefs);
}
function showImage(docId){
	var uri = "http://<%=request.getServerName()%>:<%=request.getServerPort()%><%=request.getContextPath()%>/orig/img/view_doc.jsp?docId="+docId+"";
	//alert(uri);
   window.open(uri,"mywindow","toolbar=0,resizable=yes,toolbar=0");
}
function showImageServlet(imageFileID){
   //window.open("/NaosPHPLWeb/servlet/com.ge.xenoz.servlet.image.ShowImageServlet?IMAGE_FILE_ID="+imageFileID+"","mywindow",'width=100%,height=100%',status=1,toolbar=1);
 }
function deleteImage(form){
	if(form.imageAttachSubmit.value=="<%=ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT%>"){  
// 	form.submitAppBtn.disabled = true;
// 	form.saveAppBtn.disabled = true;
    } 
//  if(form.cancelAppBtn){
// 	form.cancelAppBtn.disabled = true;
// 	}
	form.attachButton.disabled = true;
	form.delAttachButton.disabled = true;
	var del = false;	
	var str="";
	if(form.imageAttachSubmit.value=="<%=ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT%>"){
	  str ="redirectUrl=http://<%=request.getServerName()%>:<%=request.getServerPort()%><%=request.getContextPath()%>/orig/appform/app_deleteimage.jsp";
	}else{
	  str ="redirectUrl=http://<%=request.getServerName()%>:<%=request.getServerPort()%><%=request.getContextPath()%>/DeleteAttachmentServlet";
	}
	var seq = 1;
	var docId = eval("document.appFormName.docId");
	if(docId != null){ // if1
	str += "&loop="+document.getElementById("loop").value;
	//alert(docId.value);
	for (i = 0; i < docId.length; i++) {
		if (docId[i].checked){
			del = true ;
			//str += "&docId=" + form.docId[i].value; //single img
			str += "&docId"+seq+"=" + docId[i].value; //mutilple img
			seq++;
		}
	}
	//alert(docId.length);
	if(docId!=undefined && docId.value!=undefined && docId.length==undefined){
		//alert(docId.value);
		str += "&docId"+seq+"=" + docId.value; //mutilple img
	}
	if(docId.checked){
		del = true ;
	}	
	if(del){
		var isdelete = confirm('confirm delete image(s)');
		if(isdelete){
			var url = "http://<%=String.valueOf(LoadXML.getCmServerEx().get("1"))%>/<%=String.valueOf(LoadXML.getServerCmProject().get("1"))%>/UploadDoc?"+str;
			//alert(url);
			window.open(url,"deleteImageForm",'width=1,height=1,status=0,toolbar=0,top=2000,left=2000');
			//form.target = "deleteImageForm";
		}else{
			if(form.imageAttachSubmit.value=="<%=ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT%>"){ 
// 			form.submitAppBtn.disabled = false;
// 			form.saveAppBtn.disabled = false;
			 }
// 	 	if(form.cancelAppBtn){
// 			form.cancelAppBtn.disabled = false;
// 		}
			form.attachButton.disabled = false;
			form.delAttachButton.disabled = false;			 
		}
	}else{
    	if(form.imageAttachSubmit.value=="<%=ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT%>"){  
// 		form.submitAppBtn.disabled = false;
// 		form.saveAppBtn.disabled = false;
	    } 
// 	    if(form.cancelAppBtn){
// 		form.cancelAppBtn.disabled = false;
// 		}
		form.attachButton.disabled = false;
		form.delAttachButton.disabled = false;
		alert('select image');
	}	
	}else{
    	if(form.imageAttachSubmit.value=="<%=ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT%>"){  
// 		form.submitAppBtn.disabled = false;
// 		form.saveAppBtn.disabled = false;
	    } 
// 	   if(form.cancelAppBtn){
// 		form.cancelAppBtn.disabled = false;
// 		}
		form.attachButton.disabled = false;
		form.delAttachButton.disabled = false;
		alert('please select image');		
		return false ;
	}
}	
function deleteAllImage(form){
	var del = false;	
	if(form.imageID != null){ // if1
		var isdelete = confirm('confirm delete image(s)');
		if(isdelete){
			if (form.imageID.length > 1) {
				for (i = 0; i < form.imageID.length; i++) {
					form.imageID[i].checked = true;
				}
			} else {
				form.imageID.checked = true;
			}	
			window.open("","deleteImageForm",'width=1,height=1',status=1,toolbar=1);
			
			form.action.value = 'deleteImage' ;
			form.handleForm.value = "N";
			//formName.calculate.value = cal ;
			form.target = 'deleteImageForm';
			form.submit();
		}	
	}else{
		alert('please select image');
		return false ;
	}
}
function refreshImages(form){
	window.open("FrontController?action=loadAllImage","mywindow",'width=400,height=250,status=yes,toolbar=1'); 
}
function setFocusAttach(){
	var obj = document.getElementById("image_div").focus();
}

function reWriteLoadImage()	{
<%
int loop1=0;
int i=0;
String count1 = String.valueOf(attachmentVect.size());

if(count1!=null && count1.length()>0) 
	loop1 = Integer.parseInt(count1);
if(loop1>0){
reBuf.append(""); 
reBuf.append("<TABLE><TR>");
	for(int index=0; index<loop1 ;index++){
	historyDataM = new AttachmentHistoryDataM();
	historyDataM = (AttachmentHistoryDataM)attachmentVect.elementAt(index);
	i = index +1;
String docId1 = String.valueOf(historyDataM.getAttachId());
String fileName1 =  historyDataM.getFileName();
String size1 =  String.valueOf(historyDataM.getFileSize());
int sizeKB = (int)Math.round(Double.parseDouble(size1)/1024);
String docType1 = historyDataM.getFileName().substring(historyDataM.getFileName().length()-3,historyDataM.getFileName().length());
reBuf.append("<TD>");
reBuf.append("<TABLE>");
reBuf.append("<TR><TD><CENTER>");
reBuf.append("<a href=javascript:showImage('"+docId1+"')><img src="+path+" border=0></a>");
reBuf.append("</CENTER></TD></TR>");
reBuf.append("<TR><TD><input type=checkbox name='docId' value='"+docId1+"'>");
reBuf.append(fileName1+":"+sizeKB+" KB</TD>");
reBuf.append("</TR></TABLE>");
reBuf.append("</TD>");
reBuf.append("<input type=hidden name='docId"+i+"' value='"+docId1+"'> ");
reBuf.append("<input type=hidden name='fileName"+i+"' value=\\"+"\""+java.net.URLEncoder.encode(fileName1)+"\\"+"\""+"> ");
reBuf.append("<input type=hidden name='size"+i+"' value='"+size1+"'> ");
reBuf.append("<input type=hidden name=docType'"+i+"' value='"+docType1+"'>");
} // end for
reBuf.append("<input type=hidden name=loop  value='"+count1+"' id='loop'>");
reBuf.append("</TR></TABLE>");
} // end if 1
%> 
var isNav=(navigator.appName.indexOf("Netscape")!=-1);
var newaa = "<%=reBuf.toString()%>";

document.getElementById("image_div").innerHTML = tab_image_h+newaa+tab_image_l+tab_name_h+bb+tab_name_l ;

}

function gotoFile(fileName){
	//alert(fileName);
	//var url = "<%= url%>"+fileName; 
	window.open("<%=request.getContextPath()%>/ReaderPDFServlet?FileName="+fileName);
}
</script>