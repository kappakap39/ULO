/**
 *Rawi Songchaisin 
 */
$(document).ready(function (){	
	$('#ncbContainer').css({
		'height': $('.plan-dialog').height(),
		'width':'100%'
	});	
	$('#ncbContainer').layout({
		 west__size:	"60%"
		,useStateCookie:false
		,spacing_open: 1
		,closable:	false
		,resizable:	false
	});	
	LoadNcbImage();	
	$("img.target").hover(
	    function () {$(this).addClass("target-hover");},
	    function () {$(this).removeClass("target-hover");}
	);
	$("img.target").click(function() {
        $("img.target").removeClass("target-active");
        $(this).addClass("target-active");
    });
	if($($("img.target")[0]).attr('id') != undefined){
		$($("img.target")[0]).addClass("target-active");
	}
});
function LoadNcbImage(){
	ajaxDisplayElementHtml('DisplayNcbImage','0',null,'ncbImgThumbnail'); 
	if($('#ncb-active-image').attr('id') != undefined){
		LoadNcbImg($('#ncb-active-image').val());	
	}
}
function LoadNcbImg(imgID){
	try{
		$("#ncbImg").html('<img id="imageNcb" src="GetImage?imgID='+imgID+'&type=L&'+new Date().getTime()+'" >'); 	
		var width = $('#imageNcb').outerWidth(); 	
		var height =$('#imageNcb').outerHeight();
		try{
			if(height>width){ 
			   	$("#imageNcb").width("100%");
			}else{
			    $("#imageNcb").height("100%");   
			}
		}catch (e) {}
		var imageHeight = $('#ncbImgView').outerHeight();
		var imageWidth = $('#ncbImgView').outerWidth();
		$('#imageNcb').smoothZoom('destroy').smoothZoom({
			width: imageWidth - 4,
		   	height: imageHeight - 4
		});	
	}catch(e){
		
	}
}
function SaveNcb(){
	var dataString = $("#ncbContainer *").serialize();
	$AjaxFrontController('SaveNcbWebAction','N',null,dataString,DisplayNcbResult);
}
function DisplayNcbResult(data){
	jsonDisplayElementById(data);
	$dialog.dialog("close");
	closeDialog();
}