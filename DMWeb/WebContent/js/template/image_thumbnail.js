/**
 *  Image Thumbnail Javascript 
 */

function mouseOverThumbnail(imgId){
	var activeImgID = document.getElementById('activeImgID').value;	
	if (imgId != activeImgID){
		document.getElementById(imgId).style.cursor='hand' ;
		document.getElementById(imgId).style.background='#D7D6D7';
	}else{
		$(document.getElementById(imgId)).css({cursor:'none'});
	}
}

function mouseOutThumbnail(imgId){
	var activeImgID = document.getElementById('activeImgID').value;
	if (imgId != activeImgID){
		document.getElementById(imgId).style.background='#F4F4F4';
	}else{
		$(document.getElementById(imgId)).css({cursor:'none'});
	}
}

function activeThumbnail(activeImgID){
	document.getElementById(activeImgID).style.background='#D7D6D7';
}

function activeOutThumbnail(activeImgID){
	document.getElementById(activeImgID).style.background='#F4F4F4';	
}	

function appendRotateImg(activeImgID,imgThumbnailID){	
	
	var imgSize = getImageSize('SVIEW',activeImgID).split("|");	
	
	var height = imgSize[0];
	var width = imgSize[1];
	
	$('.child').remove();
	
	$(document.getElementById(activeImgID)).css({cursor:'none'});
	$('<div class="child"></div>')
		 .appendTo(document.getElementById(activeImgID))
	 	 .css({
	 	 		position : 'absolute',
	 	 		left : 0,
				width : width + 'px',
				height : height	+ 'px',
				align : 'center'																		
	 		});
	$('<img onclick="rotatedImage()" src="images/transform_rotate_90.png"></img>')
		.appendTo('.child')
		.addClass('buttonRotate');
}

function displayImg(path){
	
	$("#plantImg").html('<img id = "imageViewer" alt="" src="'+path+'" >'); 
		
	var width = document.getElementById('imageViewer').offsetWidth; 	
	var height = document.getElementById('imageViewer').offsetHeight;

	var windowsWidth = 0;
	var windowsHeight = 0;

	try{
		if( height > width ){
		   	windowsWidth = "100%"; //Fix With Frame image_viewer At Start
		   	$("#imageViewer").width(windowsWidth);
		}else{
		    windowsHeight = "100%";
		    $("#imageViewer").height(windowsHeight);   
		}
	}catch (e) {}
	
	var imageViewersHeight = document.getElementById('image_Viewers').offsetHeight;	
	
		$('#imageViewer').smoothZoom({
			width: "100%",
		   	height: imageViewersHeight - 17
		});
}

function getImageSize(typeImg ,activeImgID){	
	var sizeImg;
 	var categoryID = document.getElementById('categoryID').value; 	
	var dataString = "activeImgID="+activeImgID+"&categoryID="+categoryID+"&typeImg="+typeImg; 		
	var uri = "GetImageSizeServlet";
	jQuery.ajax( {
		type :"POST",
		url :uri,
		data :dataString,	
		async :false,
		success : function(data, status, xhr) {
			sizeImg = xhr.responseText;
		},
		error:function(){
			
		}
	});			
	
	return sizeImg;
}

function rotatedImage(){
	var categoryID = document.getElementById('categoryID').value;	
 	var activeImgID = document.getElementById('activeImgID').value; 
 	
	var dataString = "categoryID="+categoryID+"&activeImgID="+activeImgID+"&angle=90"; 
	var uri = "RotatedImageServlet";
	jQuery.ajax( {
		type :"POST",
		url :uri,
		data :dataString,	
		async :false,
		success : function(data, status, xhr) {
			updateImage();
		},
		error:function(){
			alert("Rotated Image Error !");
		}
	});	
}

function updateImage(){
	
	var imgThumbnailID = document.getElementById('imgThumbnailID').value;
	var activeImgID = document.getElementById('activeImgID').value;
	
	var imgSPath = document.getElementById(activeImgID+'imgSPath').value;
 	var imgLPath = document.getElementById(activeImgID+'imgLPath').value; 	
 	 	
    var imageThumb = document.getElementById(imgThumbnailID);
    var imageView  =  document.getElementById('imageViewer');
    
    if(imageThumb.complete){ 
    	$('.child').remove();    	
		$(imageThumb).attr('src','');
		var urlThumbnail = imgSPath+'?'+ new Date();
		$(imageThumb).attr('src',urlThumbnail);
		appendRotateImg(activeImgID,imgThumbnailID);
    } 
    
    if(imageView.complete){
    	$(imageView).attr('src','');
		var urlView = imgLPath+'?'+ new Date();
		$(imageView).attr('src',urlView);		
		displayImg(urlView);
    }
}

function LoadImageThumbanil(categoryID){
	
	var uri = "LoadImageThumbanilServlet";
	var dataString = "categoryID="+categoryID;
	jQuery.ajax( {
		type :"POST",
		url :uri,
		data :dataString,	
		async :false,
		success : function(data) {
			
			var lastCategoryID = document.getElementById('categoryID').value;
			
			$(document.getElementById("tab"+lastCategoryID)).removeAttr('class');
			
			$('#thumbnailImg').remove();
			
			$(data).appendTo("#contentThumbnail");
			
			var newCategoryID = document.getElementById('categoryID').value;
			
			$(document.getElementById("tab"+newCategoryID)).attr('class','current');
			
			var activeImgID = document.getElementById('activeImgID').value;
			var imgThumbnailID = document.getElementById('imgThumbnailID').value;
			var imgLview = document.getElementById(activeImgID+'imgLPath').value;
			
			activeThumbnail(activeImgID);			
			appendRotateImg(activeImgID,imgThumbnailID);	
			displayImg(imgLview+'?'+ new Date());
		},
		error:function(){
			alert(" Load Image Thumbanil Error !");
		}
	});		
}

function changeImg(imgId,imgThumbnailID){	
	var activeImgID = document.getElementById('activeImgID').value;		
	if (imgId != activeImgID){
		activeOutThumbnail(activeImgID);
		document.getElementById('activeImgID').value = imgId;	
		document.getElementById('imgThumbnailID').value = imgThumbnailID;	
		
		var newActiveImgId = document.getElementById('activeImgID').value;		
		var newimgThumbnailID = document.getElementById('imgThumbnailID').value;		
		var imgLPath = document.getElementById(newActiveImgId+'imgLPath').value;
		
		activeThumbnail(newActiveImgId);	
		appendRotateImg(newActiveImgId,newimgThumbnailID);		
		displayImg(imgLPath+'?'+ new Date());
	}
}
function resizeImg(){
	var activeImgId = document.getElementById('activeImgID').value;		
	if(activeImgId != null && activeImgId != ""){
		var imgLPath = document.getElementById(activeImgId+'imgLPath').value;
		displayImgResize(imgLPath+'?'+ new Date());		
	}
}
function displayImgResize(path){
	
	$('#imageViewer').remove();

	$("#plantImg").html('<img id = "imageViewer" alt="" src="'+path+'">'); 
		
	var width = document.getElementById('imageViewer').offsetWidth; 	
	var height = document.getElementById('imageViewer').offsetHeight;

	var windowsWidth = 0;
	var windowsHeight = 0;

	try{
		if( height > width ){
		   	windowsWidth = "100%"; //Fix With Frame image_viewer At Start
		   	$("#imageViewer").width(windowsWidth);
		}else{
		    windowsHeight = "100%";
		    $("#imageViewer").height(windowsHeight);   
		}
	}catch (e) {}
	
	var imageViewersHeight = document.getElementById('image_Viewers').offsetHeight;	
	
	    $('#imageViewer').smoothZoom({
			width: "100%",
		   	height: imageViewersHeight - 8
		});	
	
}

function displayThumbnailImage(){	
	var uri = "DisplayThumbnailImageServlet";
	var dataString = "";
	jQuery.ajax( {
		type :"POST",
		url :uri,
		data :dataString,	
		async :false,
		success : function(data) {
			if (data != null && data.length > 0) {
				$("#contentThumbnail").html(data);			
				var activeImgID = document.getElementById('activeImgID').value;
				var imgThumbnailID = document.getElementById('imgThumbnailID').value;
				var imgLview = document.getElementById(activeImgID+'imgLPath').value;
				activeThumbnail(activeImgID);			
				appendRotateImg(activeImgID,imgThumbnailID);	
				displayImg(imgLview+'?'+ new Date());	
			}
		},
		error:function(){
			alert(" Display Thumbnail Image Error !");
		}
	});		
}