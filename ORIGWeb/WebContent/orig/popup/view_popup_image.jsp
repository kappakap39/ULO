<% 
 String queryString=request.getQueryString();
 String url="http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/orig/popup/view_image.jsp"+"?"+queryString;
%>
<script language="javaScript">  
  var width = screen.availWidth-10
  var height =screen.availHeight-60;
	window.open("<%=url%>","Image_Windows","toolbar=no,status=yes,resizable=yes,width="+width+", height="+height+", top=0, left=0");	 
	window.setTimeout('closeWindow()',1*1000);	
	function closeWindow(){		
    	window.opener = window.self;
	    window.self.close();
	}
</script> 
