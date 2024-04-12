 <%String autourl=request.getParameter("url"); 
//System.out.println("autourl="+autourl);
  autourl+="?"+request.getQueryString(); 
%>
<applet  code="openIEApplet" width=0 height=0 archive="orig/img/SignedTest.jar" codebase="">
<PARAM NAME="ie"   VALUE="C:/Program Files/Internet Explorer/IEXPLORE.EXE">
<PARAM NAME="autourl"   VALUE="<%=autourl%>">
<!-- <PARAM NAME="mode"   VALUE="-k">-->
<PARAM NAME="mode"   VALUE="">
</applet>

<SCRIPT language="JavaScript">
	window.setTimeout('closeWindow()',3*1000);
	
	function closeWindow(){
		window.close();
	}
</SCRIPT>