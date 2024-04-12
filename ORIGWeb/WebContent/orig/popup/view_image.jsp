<%-- <%@page import="com.eaf.orig.ulo.pl.service.PLORIGEJBService"%> --%>
<%-- <%@ page import="java.util.Vector" %> --%>
<%-- <%@ page import="com.eaf.img.shared.model.ImgAttachmentDataM" %> --%>
<%-- <%@ page import="com.eaf.orig.shared.dao.ORIGDAOFactory"%> --%>
<%-- <%@ page import="com.eaf.j2ee.system.LoadXML" %> --%>
<%-- <%@ page import="com.klop.fax.plugin.constants.NaosImgConstants" %> --%>
 
<%-- <% --%>
<!-- 	String CM_SERVER_NAME_EXT = String.valueOf(LoadXML.getCmServerEx().get("1")); -->
<!-- 	String userid = String.valueOf(LoadXML.getCmUserid().get("1")); -->
<!-- 	String password = String.valueOf(LoadXML.getCmPassword().get("1")); -->
<!-- 	String server = String.valueOf(LoadXML.getCmServerName().get("1")); -->
<!-- 	String imageItemType = String.valueOf(LoadXML.getCmDatatype().get("1")); -->
<!-- 	String serverCmProject = String.valueOf(LoadXML.getServerCmProject().get("1")); -->
<!-- 	String dataType = String.valueOf(LoadXML.getCmMergeItemtype().get("1")); -->
<!-- 	System.out.println("request.getParameter(attachID) = "+request.getParameter("attachID")); -->
<!-- 	String attachId = request.getParameter("attachID").toString(); -->
<!-- 	String requestNo = request.getParameter("requestID").toString(); -->
	
<!-- 	String queryString = "?userid=" + userid + "&password=" + password + "&server=" + server + "&imageId=" + requestNo + "&attachId=" + attachId  + "&dataType=" + dataType; -->
<!-- 	String action="http://" + CM_SERVER_NAME_EXT + "/"+serverCmProject+"/ConnectCM"; -->
	
<!-- 	System.out.println("requestNo = "+requestNo); -->
<!-- 	System.out.println("attachId = "+attachId); -->
<!-- 	System.out.println("dataType = "+dataType); -->
<!-- 	System.out.println("queryString = "+queryString); -->
<!-- 	System.out.println("action = "+action);  -->
<!-- //    Vector vAttachFiles=ORIGDAOFactory.getUtilityDAO().getImageAttachedFiles(requestNo); -->
<!-- 	Vector vAttachFiles= PLORIGEJBService.getORIGDAOUtilLocal().getImageAttachedFiles(requestNo); -->
<!-- %>	 -->
<%-- <% --%>
<!-- String path = request.getContextPath() +"/en_US/images/";    -->
<!-- String attid=""; -->
<!-- /*if(attid==""){ -->
<!-- 	if( requestDataM.getRequestNo() != null){ -->
<!-- 		Vector attachs= requestDataM.getTheImgAttachmentDataM(); -->
 
<!-- 		if(attachs!=null&&!attachs.isEmpty()){ -->
<!-- 			int t=0; -->
 
<!-- 			while(t<attachs.size()){	// find mainapp -->
 
<!-- 				ImgAttachmentDataM attachmentDataM=(ImgAttachmentDataM)attachs.get(t); -->
<!-- 				if(attachmentDataM.getAttachType().equals(NaosImgConstants.FILE_TYPE_ID_MAINAPP)){ -->
<!-- 					attid=String.valueOf(attachmentDataM.getAttachId()); -->
<!-- 					break; -->
<!-- 				} -->
<!-- 				t++; -->
<!-- 			} -->
<!-- 		} -->
<!-- 	} -->
<!-- //}*/ -->
<!-- %> -->

<!-- <HTML> -->
<!-- <title>UNIVERSAL LOAN ORIGINATION SYSTEM   -->
<%-- <% --%>
<!-- //if(requestDataM!=null){ -->
<!-- //	if(requestDataM.getRequestNo()!=null){  -->
<!-- 		try{ -->
<!-- 			out.write("Request No "+requestNo); -->
<!-- 		}catch(Exception e){ -->
<!-- 		} -->
<!-- 	//} -->
<!-- //} -->
<!-- %></title> -->
<!-- <BODY> -->
 
<div align="right"><input type="button" value="Close" onclick="closeWindow()"></div>
<!-- <SCRIPT language="JavaScript"> -->
<!-- 	window.opener=""; -->
	
<!-- 	function closeWindow(){ -->
<!-- 		window.close(); -->
<!-- 	} -->
<!-- 	function changeIframeLoaction(targetLocation,requestID,attachId){ -->
<!-- 	//alert(targetLocation); -->
<!-- 	//alert("Old location"+frames['imgFrame'].window.location); -->
<!-- 	// var oFrame = window.frames["imgFrame"]; -->
<!-- 	 //oFrame.src=targetLocation; -->
<!-- 	// alert("get DiV") -->
<!-- 	//var obj=document.getElementById("divImgFrame"); -->
<!-- 	//alert(obj); -->
<!-- 	//if(obj){ -->
<!-- 	 // var inner="<IFRAME name=\"imgFrame\" SRC=\""+targetLocation+"\" WIDTH=\"100%\" HEIGHT=\"100%\" name=\"imageView\"></IFRAME>"; -->
<!-- 	  //alert(inner); -->
<!-- 	  //obj.innerHTML=inner;	    -->
<!-- 	  window.openIEForm.url.value=targetLocation;  -->
<!-- 	  window.openIEForm.requestID.value=requestID;  -->
<!-- 	  window.openIEForm.attachID.value=attachId;  -->
<!-- 	  window.openIEForm.submit();  -->
<!-- 	 } -->
<!-- </SCRIPT> -->

<!-- <table width="100%" border="0" height="100%"> -->
<%-- <tr valign="top"><td width="10%  height="100%"  ><% --%>
<!-- //if(requestDataM!=null){ -->
<!-- 	//if(requestDataM.getRequestNo() != null){ -->
<!-- 		Vector attachs=vAttachFiles ;//requestDataM.getTheImgAttachmentDataM(); -->
<!-- 		if(attachs!=null&&!attachs.isEmpty()){%> -->
<!-- 			 <DIV id=image_div style="OVERFLOW: scroll;"   name="image_div" > -->
<!-- 			 	<table width="10%" border="0" valign="top" > -->
<%-- <% --%>
<!-- 			int t=0; -->
<!-- 			int attNo=0; -->
<!-- 			while(t<attachs.size()){	// find mainapp -->
<!-- 				ImgAttachmentDataM attachmentDataM=(ImgAttachmentDataM)attachs.get(t); -->
<!-- 				String tmpAttid=String.valueOf(attachmentDataM.getAttachId()); -->
<!-- 				String myStr; -->
<!-- 				if(attachmentDataM.getAttachType().equals(NaosImgConstants.FILE_TYPE_ID_ATTACHMENT)){ -->
<!-- 					attNo++; -->
<!-- 					myStr="Att"+attNo; -->
<!-- 				}else{ -->
<!-- 					myStr="Main"; -->
<!-- 				} -->
				
<!-- 				%> -->
<!-- 				  <tr> -->
<!-- 					<td align="center"><br>  -->
<%--                     <%String url="http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/orig/popup/view_popup_image.jsp"; %>   --%>
<%-- 					<a href="#" onClick="changeIframeLoaction('<%=url%>','<%=requestNo%>','<%=tmpAttid%>')"><img src=<%=path+"pic1.gif"%> border="0"><br><FONT size="2"><%=myStr%></font></a></td> --%>
<!-- 				  </tr> -->
<%-- <%			t++; --%>
<!-- 			}%> -->
<!-- 				</table> -->
<!-- 			 </Div> -->
<%-- <% --%>
<!-- 		} -->
<!-- 	//} -->
<!-- //}%></td> -->
<!-- <td width="90%" valign="top"><DIV id="divImgFrame"> -->
<%-- <IFRAME name="imgFrame" SRC="<%=(request.getContextPath()+"/orig/popup/view_image_attach.jsp?attachID="+attachId+"&requestID="+requestNo )%>" WIDTH="100%" HEIGHT="100%" name="imageView"></IFRAME> --%>
<!-- </DIV> -->
<!-- </td> -->
<!-- </tr> -->
<!-- </table> -->

<%-- <form  name="openIEForm" action="<%=request.getContextPath()%>/openIE.jsp" method="get" target="top"> --%>
<!-- <input type="hidden" name="url"> -->
<!-- <input type="hidden" name="attachID"> -->
<!-- <input type="hidden" name="requestID"> -->
<!-- </form> -->
<!-- </BODY> -->
<!-- </HTML> -->
