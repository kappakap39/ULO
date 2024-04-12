/**
 * Pipe
 */
$(document).ready(function(){
	$('#cs_download').hover(
	    	 function(){$(this).attr("title","Click to Download.").fadeIn('fast');},
	    	 function(){$(this).removeattr("title").fadeOut('fast');});
	
	$('#cs_download').click(function(){
//		var dataString = "com.eaf.orig.ulo.pl.app.servlet&ClassName=GetDownloadLinkReport"; 
//		var uri = "/ORIGWeb/GetDownloadLinkReport";
//		window.location.href(uri);
//		$.get (uri,function(data) {
//			return data.val();
//			}
//		); 
	});
});