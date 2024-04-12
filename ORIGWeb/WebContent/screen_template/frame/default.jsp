<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/lib/JStartTagLib.tld" prefix="taglib"%>
<%@ page import="com.eaf.j2ee.pattern.control.*"%>
<%@ page import="com.eaf.j2ee.system.LoadXML"%>
<jsp:useBean id="screenFlowManager" class="com.eaf.j2ee.pattern.control.ScreenFlowManager" scope="session" />
<%ScreenDefinition sd = (ScreenDefinition) LoadXML.getScreenDefinitionMap().get(screenFlowManager.getCurrentScreen());%>
<html>
<head>
<title><%=sd.getHtmlTitle()%></title>
<meta content="text/html; charset=UTF-8" http-equiv="Content-Type"/>
<script type="text/javascript" src="../../js/template/jquery-1.7.1.js"></script>
<script type="text/javascript" src="../../js/template/DisableInvalidKey.js"></script>
<script language="javascript" type="text/javascript">
var frameopen=true;
var framebotopen=true;
var openApplication = false;
function forceHide() {
		window.document.getElementById("leftframeset").rows = "0,*";
		frameopen=false;
}
function toggleMenu() {
	closeAttr();
	if (frameopen) {
		try{
			parent.mainFrame.navinbox(190);
		}catch(e){		
		}
		window.document.getElementById("leftframeset").rows = "0,*";
		top.document.getElementById("bodyFrameset").cols = "25,*";
		disableResize(menuheader);
		menuframe.document.location.href = "hideLeftNav.jsp";
		frameopen=false;
	}else{
		try{
			parent.mainFrame.navinbox(-200);
		}catch(e){		
		}
		window.document.getElementById("leftframeset").rows = "38,*";
		top.document.getElementById("bodyFrameset").cols = "220,*";
		enableResize(menuheader);
		frameopen=true;
		menuframe.document.location.href = "leftNav.jsp";
	}
}
/**#SeptemWi Function Close Attr*/
function closeAttr(){
	try{
		parent.mainFrame.fullclosedialog();
		if(parent.mainFrame.hideCalendar != null)
				parent.mainFrame.hideCalendar();
		if(parent.mainFrame.clearDate != null)
				parent.mainFrame.clearDate();
	}catch(e){		
	}
}
/**#SeptemWi*/
function closeMenuFrame(){
	closeAttr();
	window.document.getElementById("leftframeset").rows = "0,*";
	top.document.getElementById("bodyFrameset").cols = "25,*";
	disableResize(menuheader);
	frameopen=false;openApplication = true;
	$('#menuframe').attr('src','hideLeftNav.jsp');
}
function openMenuFrame(){
	closeAttr();
	window.document.getElementById("leftframeset").rows = "38,*";
	top.document.getElementById("bodyFrameset").cols = "220,*";
	enableResize(menuheader);
	frameopen=true;openApplication = false;
	$('#menuframe').attr('src','leftNav.jsp');
}
function toggleBotMenu() {
	if (framebotopen) {
		window.document.getElementById("screenframeset").rows = "69,*,19";
		framebotopen=false;
		window.footer.document.images["arrow"].src = "images/arrow_up.gif";
	} else {
		window.document.getElementById("screenframeset").rows = "69,*,80";
		framebotopen=true;
		window.footer.document.images["arrow"].src = "images/arrow_down.gif";
	}
}
//------ Frame function ------
function disableResize(theObj) {
	theObj.noResize=true;
}
function enableResize(theObj) {
	theObj.noResize=false;
}

//------------------
function createPath(thePath) {
	if(top.mainheader.document.all.appPath)	top.mainheader.document.all.appPath.innerText = thePath;
}
function writeConsole(message,color) {
	if(color){
		if(top.footer.document.all.textMessage)	top.footer.document.all.textMessage.innerHTML += "<font color=\""+color+"\">"+message+"</font><br/>";
	}else{
		if(top.footer.document.all.textMessage)	top.footer.document.all.textMessage.innerHTML += message+"<br/>";
	}
}
function clearConsole() {
	if(top.footer.document.all.textMessage)	top.footer.document.all.textMessage.innerHTML = "";
}

function ActivityCallController(querystring) { 
	var bindArgs = { 
		url: "/FrontWeb/ActivityController", 
		method: "POST", 
		content: querystring, 
		handle: function ActivityCallController_dojo(type, data, evt) { }, 
		mimetype: "text/html", 
		encoding: "UTF-8" 
	}; 
	//dojo.io.bind(bindArgs); 
}
function ActivityTheme(desc,event){
	var param = new Array(); 
	param['browser'] = getBrowserVerionTheme(); 
	param['OS'] = getOSTheme(); 
	param['resorution'] = getResolutionTheme(); 
	param['session'] = "<%=request.getSession().getId()%>"; 
	param['user'] = "<%=request.getRemoteUser()%>"; 
	param['desc'] = desc; 
	param['event'] = event; 
	ActivityCallController(param);
}
function getOSTheme(){ 
	var OSName="Unknown OS"; 
	if (navigator.appVersion.indexOf("Win")!=-1) 
		OSName="Windows"; 
	if (navigator.appVersion.indexOf("Mac")!=-1) 
		OSName="MacOS"; 
	if (navigator.appVersion.indexOf("X11")!=-1) 
		OSName="UNIX"; 
	if (navigator.appVersion.indexOf("Linux")!=-1) 
		OSName="Linux";

	return OSName; 
} 

function getResolutionTheme(){ 
	var screenW = 640, screenH = 480; 
	if (parseInt(navigator.appVersion)>3) { 
		screenW = screen.width; 
		screenH = screen.height; 
	} else if (navigator.appName == "Netscape" && parseInt(navigator.appVersion)==3 && navigator.javaEnabled() ) { 
		var jToolkit = java.awt.Toolkit.getDefaultToolkit(); 
		var jScreenSize = jToolkit.getScreenSize(); 
		screenW = jScreenSize.width; 
		screenH = jScreenSize.height; 
	}

	return screenW+" X "+screenH;

}
 

function getBrowserVerionTheme(){ 
	var nVer = navigator.appVersion; 
	var nAgt = navigator.userAgent; 
	var browserName  = ''; 
	var fullVersion  = 0; 
	var majorVersion = 0;

	// In Internet Explorer, the true version is after "MSIE" in userAgent 
	if ((verOffset=nAgt.indexOf("MSIE"))!=-1) { 
		browserName  = "Microsoft Internet Explorer"; 
		fullVersion  = parseFloat(nAgt.substring(verOffset+5)); 
		majorVersion = parseInt(''+fullVersion); 
	}

	// In Opera, the true version is after "Opera" 
	else if ((verOffset=nAgt.indexOf("Opera"))!=-1) { 
		browserName  = "Opera"; 
		fullVersion  = parseFloat(nAgt.substring(verOffset+6)); 
		majorVersion = parseInt(''+fullVersion); 
	}

	// In most other browsers, "name/version" is at the end of userAgent 
	else if ( (nameOffset=nAgt.lastIndexOf(' ')+1) < (verOffset=nAgt.lastIndexOf('/')) ) { 
		browserName  = nAgt.substring(nameOffset,verOffset); 
		fullVersion  = parseFloat(nAgt.substring(verOffset+1)); 
		if (!isNaN(fullVersion)) 
			majorVersion = parseInt(''+fullVersion); 
		else {
			fullVersion  = 0; 
			majorVersion = 0;
		} 
	}

	// Finally, if no name and/or no version detected from userAgent...  
	if (browserName.toLowerCase() == browserName.toUpperCase() || fullVersion==0 || majorVersion == 0 ) { 
		browserName  = navigator.appName; 
		fullVersion  = parseFloat(nVer); 
		majorVersion = parseInt(nVer); 
	} 
	return browserName+" "+fullVersion;		
}
</script>
</head>
	<frameset rows="38,*" id="leftframeset">
		<frame frameborder="0" scrolling="no" marginwidth="0" marginheight="0" name="menuheader" src="leftHeader.jsp" noresize/>
		<frame frameborder="0" scrolling="no" marginwidth="0" marginheight="0" name="menuframe" id="menuframe" src="leftNav.jsp" scrolling="YES" noresize />
	</frameset>
<noframes><body>Your browser does not handle frames!</body></noframes>
</html>
