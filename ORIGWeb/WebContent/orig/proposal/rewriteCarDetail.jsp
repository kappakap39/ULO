<%String rewriteFlag  = (String)request.getSession().getAttribute("REWRITE_FLAG");
System.out.println("Rewrite flag is:"+rewriteFlag);
request.removeAttribute("REWRITE_FLAG");
%>

<Script type="text/javascript">
<%if(rewriteFlag.equals("Y")){%>
rewriteCarDetail();
<%}%>
function rewriteCarDetail(){
	window.opener.rewriteVehicle();
	window.close();
}
</Script>