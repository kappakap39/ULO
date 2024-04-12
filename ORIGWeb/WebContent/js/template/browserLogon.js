/**#SeptemWi*/
window.onbeforeunload = function(){
	if(window.event.clientY < 0){ 
		window.open(CONTEXT_PATH_ORIG+'/logout.jsp','','width=0,height=0,toolbar=0,menubar=0,location=0,status=1,scrollbars=0,resizable=0,left=0,top=0');
	}
};