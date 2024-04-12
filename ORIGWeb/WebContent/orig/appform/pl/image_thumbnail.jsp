<%@ page contentType="text/html;charset=UTF-8"%>
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"/>
<jsp:useBean id="formHandlerManager" scope="session" class="com.eaf.j2ee.pattern.view.form.FormHandleManager" />
<jsp:setProperty name="formHandlerManager" property="currentFormHandler" value="PLORIGForm" />
<script language="JavaScript" src="orig/js/appform/pl/image.thumbnail.js"></script>
<div id="main-image-thumbnail" style="color: green;">
	Loading...
</div>

