$(document).ready(function (){
	$('#ncbContainer').css({
		'height': $('.plan-dialog').height(),
		'width':'100%'
	});
	$('#ncbContainer').layout({
		west__size:	"50%"
		,useStateCookie:false
		,spacing_open:0.1
		,closable:	false
		,resizable:	false
	});
	$('div.ui-layout-center').layout({
		 center__paneSelector: ".center-center"
		,south__paneSelector: ".center-south"
		,south__size: 125
		,closable:	false
		,spacing_open:0.1
		,resizable:	false
	});
	$('.center-center').css({
		'border': '1px solid rgb(236, 238, 236)'
	});
	$('.center-south').css({
		'border': '1px solid rgb(236, 238, 236)'
	});
	$('div.ui-layout-west').layout({
		center__paneSelector: ".west-center"
		,south__paneSelector: ".west-south"
		,south__size: 125
		,closable:	false
		,spacing_open: 0.1
		,resizable:	false
	});
	$('.west-center').css({
		'border': '1px solid rgb(236, 238, 236)'
	});
	$('.west-south').css({
		'border': '1px solid rgb(236, 238, 236)'
	});		
	LoadNcbImage();
});

function LoadNcbImage(){	
	var dataString = "className=NcbSelectedImages&packAge=0&returnType=0";
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
				$('#'+item.id).html(item.value);
			});
			ImageEngine();
			try{			
				if($($('div.img-ncb-thumbnail1')[0]).attr('class') != undefined){	
					var imgID = $($('div.img-ncb-thumbnail1')[0]).attr('class').split(/\s+/)[1];
					LoadNcbImg1(imgID);
				}
				if($($('div.img-ncb-thumbnail2')[0]).attr('class') != undefined){	
					var imgID = $($('div.img-ncb-thumbnail2')[0]).attr('class').split(/\s+/)[1];
					LoadNcbImg2(imgID);	
				}
			}catch(e){
			}
		},
		error : function(data){
		}
	});	
}
function ImageEngine(){
	$("div.img-ncb-thumbnail1").hover(
		function(){$(this).addClass("img-ncb-thumbnail-haver");},
		function(){$(this).removeClass("img-ncb-thumbnail-haver");}
	);			
	$("div.img-ncb-thumbnail2").hover(
		function(){$(this).addClass("img-ncb-thumbnail-haver");},
		function(){$(this).removeClass("img-ncb-thumbnail-haver");}
	);	
	$("div.img-ncb-thumbnail1").click(function(){
		if($(this).hasClass('img-ncb-thumbnail-active')){
			return false;
		}
		$('div.img-ncb-thumbnail1').removeClass("img-ncb-thumbnail-active");
		$(this).addClass("img-ncb-thumbnail-active");
		try{			
			var imgID = $(this).attr('class').split(/\s+/)[1];		
			DisplayRotation(imgID);
			LoadNcbImg1(imgID);
		}catch(e){					
		}
	});
	$("div.img-ncb-thumbnail2").click(function(){
		if($(this).hasClass('img-ncb-thumbnail-active')){
			return false;
		}
		$('div.img-ncb-thumbnail2').removeClass("img-ncb-thumbnail-active");
		$(this).addClass("img-ncb-thumbnail-active");	
		try{
			var imgID = $(this).attr('class').split(/\s+/)[1];
			LoadNcbImg2(imgID);
		}catch(e){					
		}
	});	
}
function LoadNcbImg1(imgID){
	try{
		$("#imageNcb-1").smoothZoom('destroy');
		$("#view-img-1").html('<img id="imageNcb-1" src="GetImage?imgID='+imgID+'&type=L&'+new Date().getTime()+'" >'); 	
		var width  = $('#imageNcb-1').outerWidth();	
		var height = $('#imageNcb-1').outerHeight();
		try{
			if(height>width){ 
			   	$("#imageNcb-1").width("100%");
			}else{
			    $("#imageNcb-1").height("100%");
			}
		}catch (e) {}
		var imageHeight = $('#div-ncbImg-1').outerHeight();
		var imageWidth = $('#div-ncbImg-1').outerWidth();
		$('#imageNcb-1').smoothZoom('destroy').smoothZoom({
			width: imageWidth - 4,
		   	height: imageHeight - 4
		});	
	}catch(e){
	}	
}
function DisplayRotation(imgID){
	$("div.img-main-thumbnail-overlay").remove();
	$('#'+imgID+'-1').append("<div class='img-main-thumbnail-overlay'></div>");	
	var width  = $('#'+imgID+'-1').attr('width');
	var height = $('#'+imgID+'-1').attr('height');
	$('#'+imgID+'-1'+" div.img-main-thumbnail-overlay")
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
	var dataString  = "className=com.eaf.orig.ulo.pl.ajax.RotateImage&returnType=0&rotation=NCB&imgID="+imgID;
 	$.ajax({
		type :"POST",
		url :"AjaxDisplayServlet",
		data :dataString,
		async :true,	
		dataType: "json",
		success : function(data ,status ,xhr){	
			displayJSON(data);
			$("div.img-ncb-thumbnail1").unbind();
			$("div.img-ncb-thumbnail2").unbind();
			$('div.img-ncb-thumbnail1').removeClass("img-ncb-thumbnail-active");
			$('div.img-ncb-thumbnail2').removeClass("img-ncb-thumbnail-active");
			$('#'+imgID+'-1').addClass("img-ncb-thumbnail-active");
			$('#'+imgID+'-2').addClass("img-ncb-thumbnail-active");
			LoadNcbImg1(imgID);
			LoadNcbImg2(imgID);
			ImageEngine();
			DisplayRotation(imgID);
			data = null;
		},
		error : function(data){
		}
 	});
}

function LoadNcbImg2(imgID){
	try{
		$("#imageNcb-2").smoothZoom('destroy');
		$("#view-img-2").html('<img id="imageNcb-2" src="GetImage?imgID='+imgID+'&type=L&'+new Date().getTime()+'" >'); 	
		var width  = $('#imageNcb-2').outerWidth();	
		var height = $('#imageNcb-2').outerHeight();
		try{
			if(height>width){ 
			   	$("#imageNcb-2").width("100%");
			}else{
			    $("#imageNcb-2").height("100%");   
			}
		}catch (e) {}
		var imageHeight = $('#div-ncbImg-2').outerHeight();
		var imageWidth = $('#div-ncbImg-2').outerWidth();
		$('#imageNcb-2').smoothZoom('destroy').smoothZoom({
			width: imageWidth - 4,
		   	height: imageHeight - 4
		});
	}catch(e){		
	}
}
