/**#SeptemWi*/
$(document).ready(function(){
	if(!$LoadImg){
		LoadMainImageThumbnail();
		$LoadImg = true;		
	}			
});

function LoadMainImageThumbnail(){
	var dataString = "className=LoadMainImageThumbnail&packAge=0&returnType=0";
	$.ajax({
		type :"POST",
		url :"AjaxDisplayServlet",
		data :dataString,
		async :true,	
		dataType: "json",
		success : function(data ,status ,xhr){	
				if(data == null || data.legth == 0){
					return false;
				}
				$.map(data, function(item){		
					if($('#'+item.id) != undefined){
						$('#'+item.id).html(item.value);				 
					}
				});
				$("div.img-main-thumbnail").hover(
				    function (){$(this).addClass("img-main-thumbnail-haver");},
				    function (){$(this).removeClass("img-main-thumbnail-haver");}
				);
	
				ClickRotation('div.img-main-thumbnail');	
				
				if($($('div.img-main-thumbnail')[0]).attr('class') != undefined){				
					var imgID = $($('div.img-main-thumbnail')[0]).attr('id');
					LoadMainImage(imgID);	
				}
				data = null;			
		},
		error : function(data){
			data = null;
		}
	});	
}
function ClickRotation(imgID){	
	$(imgID).click(function(){
		if(!$(this).hasClass('img-main-thumbnail-active')){
			$('div.img-main-thumbnail').removeClass("img-main-thumbnail-active");
			$(this).addClass("img-main-thumbnail-active");
			var imgID = $(this).attr('id');
			DisplayRotation(imgID);
			LoadMainImage(imgID);
		}
    });
}
function DisplayRotation(imgID){
	$("div.img-main-thumbnail-overlay").remove();
	$('#'+imgID).append("<div class='img-main-thumbnail-overlay'></div>");	
	var width  = $('#'+imgID).attr('width');
	var height = $('#'+imgID).attr('height');
	$('#'+imgID+" div.img-main-thumbnail-overlay")
	  .height(height)
	  .width(width-6)
      .css({
	       'opacity' : 0.8,
	       'position': 'absolute',
	       'top': 1,
	       'background-color': '#D7D6D7',
	       'z-index': 1321
      });
	var rotateID = 'button-rotate-'+imgID;
	$('<img class="'+rotateID+'" src="images/transform_rotate_90.png" align="left"></img>').appendTo('.img-main-thumbnail-overlay');
	$('.'+rotateID).click(function(){	
		var imgID = $(this).attr('class').replace('button-rotate-','');
		Rotation(imgID);
	});
}
function Rotation(imgID){	
	var dataString  = "className=com.eaf.orig.ulo.pl.ajax.RotateImage&returnType=0&imgID="+imgID;
 	$.ajax({
		type :"POST",
		url :"AjaxDisplayServlet",
		data :dataString,
		async :true,	
		dataType: "json",
		success : function(data ,status ,xhr){	
			displayJSON(data);
			$('div.img-main-thumbnail').removeClass("img-main-thumbnail-active");
			$('#'+imgID).addClass("img-main-thumbnail-active");
			$("div.img-main-thumbnail").unbind();
			$("div.img-main-thumbnail").hover(
				function (){$(this).addClass("img-main-thumbnail-haver");},
				function (){$(this).removeClass("img-main-thumbnail-haver");}
			);
			ClickRotation('div.img-main-thumbnail');
			LoadMainImage(imgID);
			DisplayRotation(imgID);	
			data = null;
		},
		error : function(data){
		}
 	});
	
}
function LoadMainImage(imgID){
	$('#img-main').smoothZoom('destroy');
	$("#main-image-viewer").html('<img id="img-main" src="GetImage?imgID='+imgID+'&type=L&'+new Date().getTime()+'">');	
	var width  = $('#'+imgID).attr('width'); 	
	var height = $('#'+imgID).attr('height');
	try{
		if(height>width){ 
			$("#img-main").width("100%");
		}else{
			$("#img-main").height("100%");
		}
	}catch(e){};
 	var imageHeight = $('#image-layout').outerHeight();
	var imageWidth = $('#image-layout').outerWidth();
	$('#img-main').smoothZoom('destroy').smoothZoom({
		width: imageWidth - 4,
	   	height: imageHeight - 4
	});	  
}
