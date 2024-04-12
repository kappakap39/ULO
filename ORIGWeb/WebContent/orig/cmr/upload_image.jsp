
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">


<HEAD>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>	
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv="Content-Style-Type" content="text/css">
<TITLE></TITLE>
</HEAD>

<form name="UploadImageForm" method="post" enctype="multipart/form-data" action="">
<TABLE cellSpacing=0 cellPadding=0 width="100%"  border="0">
	<TR><TD vAlign=top>
	<table cellpadding="0" cellspacing="0" width="100%" align="center" bgcolor="#FFFFFF">
		<tr><td colspan="2" class="sidebar8">
			<table cellSpacing="1" cellPadding="0" width="100%" border="0"> 
		    	<tr><td class="sidebar9">
					<table cellspacing=0 cellpadding=0 width="100%" align=center border=0>	     		
			 			<tr> <td  height="10">
			 				<table width="100%" border="0" cellspacing="0" cellpadding="0">
                          	<tr><td class="text-header-detail">Upload Image </td>
                            <td width="330">
                            	<table width="50" border="0" align="right" cellpadding="2" cellspacing="0">
                              		<tr height="30"><td>
                                    	<input type="button" name="Attachement" value="<%=MessageResourceUtil.getTextDescription(request, "OK") %>" onClick="upload(this.form);" class="button"> 
                                    </td><td>
                                    	<input type="submit" name="cancel" value="<%=MessageResourceUtil.getTextDescription(request, "CANCEL") %>" class="button">
                                    </td></tr>
                                </table>
                            </td></tr>	
                            </table>
                        </td></tr>
                        <tr class="sidebar10"> <td align="center">
							<table width="100%" align=center cellpadding="0" cellspacing="1" border="0">
								<tr>
 									<TD class="textColS" align="center"><%=MessageResourceUtil.getTextDescription(request, "FILE_NAME") %> :&nbsp;&nbsp;<input type="file" name="fileName" value="" onchange="checkFileFormat()"></TD>
 			
 								</tr>
 								<TR>
									<TD colspan="4" align="center">		
										<div id="PlsWaitMessage" style="visibility: visible"><font	color='red' > <b>&nbsp;&nbsp;&nbsp; </b></font></div>
										<br><!--img src="/NaosSgWeb/en_US/images/progressbar.gif"-->
									</TD>
 								</TR>
							</table>
						</td></tr>
					</table>
				</td></tr>
			</table>
 			</td></tr>		
	</table>
	</TD></TR>
</TABLE> 		
<input type="hidden" name="redirectUrl" value="http://<%=request.getServerName()%>:<%=request.getServerPort()%><%=request.getContextPath()%>/FrontController?page=UPLOAD_IMAGE">  
</form>

<SCRIPT language="JavaScript">
	function upload(form){
		//alert(form.name);
		var filename = form.fileName.value;
		var name = filename.substring(filename.lastIndexOf('\\')+1,filename.length);
		if(name.length>255){
			alert('File name of the attached must less than 255 character');
			return false;
		}
		
		if(form.fileName.value!=""){
			showDiv();
			form.action = "http://<%=request.getServerName()%>:<%=request.getServerPort()%><%=request.getContextPath()%>/UploadImageServlet";
		
			form.submit(); 
		}else {
		  	alert('can\'t upload');
		} 
	}
 	function hideDiv() { 
		document.getElementById('PlsWaitMessage').style.visibility = 'hidden'; 
	} 
	
	function showDiv() { 
		document.getElementById('PlsWaitMessage').innerHTML ='<font color=\'red\' >Please wait ...<b></b></font>'; 
	} 
	
	function reWriteMsg(message) { 
		document.getElementById('PlsWaitMessage').innerHTML =''; 
		alert(message);
	}
	function checkFileFormat(){
	  var form=document.UploadImageForm;
 	  var vfileName=form.fileName.value;
 	   if(vfileName!=''){
 	    var extension=vfileName.substring(vfileName.lastIndexOf('.')+1,vfileName.length);
 	    if(extension!='' && !(extension.toLowerCase()=='tif'||extension.toLowerCase()=='tiff' )){
 	       alert("Extension of image file must be .tif/.tiff only.");
 	       form.reset();
         }  
         
       } 
	}

<%
String success = request.getParameter("result");
//System.out.println("success = " +success);
//System.out.println("count = " +request.getParameter("count"));
if ("success".equals(success)) {   	  
%>          
      reWriteMsg("Upload completed.");      
<%	  
}else if("First".equals(success)){  %>
      hideDiv(); 
<%
}else{
%>
	reWriteMsg("Can't upload Image file."); 
<%}%>
</SCRIPT>
