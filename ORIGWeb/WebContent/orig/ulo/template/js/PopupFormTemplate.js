$(document).ready(function(){
	if(FLAG_NO!=$('[name="autoHightFlag"]').val()){
		 $('.modal-dialog').css('height','90%');
		 $('.modal-content').css('height','100%');
		 resizeContent();	 
		 $('.PopupFormHandlerWrapper').css('height',$(window).height()*0.7);
		 window.addEventListener('resize',resizeContent,false);
	}	
});

function resizeContent(){
	 $('.PopupFormHandlerWrapper').css('height',$('.modal-content').height()*0.8);
}