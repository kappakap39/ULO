<%@ page contentType="text/html;charset=UTF-8"%>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler" />
<jsp:useBean id="formHandlerManager" scope="session" class="com.eaf.j2ee.pattern.view.form.FormHandleManager" />
<jsp:setProperty name="formHandlerManager" property="currentFormHandler" value="ORIGForm" />
<script language="JavaScript" src="js/image_thumbnail.js"></script>
<div id="contentThumbnail">
	<font color="green">Loading...</font>
	<input id="activeImgID" value="" type="hidden"/>
	<input id="imgThumbnailID" value="" type="hidden"/>
	<input id="categoryID" value="" type="hidden"/>
</div>

<script type="text/javascript">
	if(!isFirst){		
		$("#contentThumbnail")
		.load("DisplayThumbnailImageServlet",
		function(response, status, xhr){
			$("#contentThumbnail").html(response);
			var activeImgID = document.getElementById('activeImgID').value;
			var imgThumbnailID = document.getElementById('imgThumbnailID').value;
			var imgLview = document.getElementById(activeImgID+'imgLPath').value;
			activeThumbnail(activeImgID);			
			appendRotateImg(activeImgID,imgThumbnailID);	
			displayImg(imgLview+'?'+ new Date());
			isFirst = true;
		});	
	}
</script>
