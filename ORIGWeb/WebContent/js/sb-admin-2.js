$(function() {
    $('#side-menu').metisMenu();    
    $('#page-wrapper, .sidebar').resize(onResizePage);
//    $('.FormHeaderManager').resize(resizeFormTemplate);
});

//Loads the correct sidebar on window load,
//collapses the sidebar on window resize.
// Sets the min-height of #page-wrapper to window size
var topOffsetDef = 63 + 40;
var getTopOffset = function() {
    var headerHeight = $('nav.navbar').height();
    var navHeight = 0;
    if ($('#navigation-info').length > 0) {
        navHeight = $('#navigation-info').outerHeight();
    }
    return headerHeight + navHeight;
};

var getWindowHeight = function() {
    return ((this.window.innerHeight > 0) ? this.window.innerHeight : this.screen.height) - 1;
};
    
var resizeFormHeader = function() {	
	var callback = function () {
//		$(".FormHeaderManager").css("width", $(".FormHeaderManager").parent().width());
        // Popover
        height = ((this.window.innerHeight > 0) ? this.window.innerHeight : this.screen.height) - 1;
        var headerObject = $('.FormHeaderManager');
        height = height - (headerObject.height() + headerObject.outerHeight() );
//        $(".popover-wrapper").css({
//        	"width": $(".FormHeaderManager").parent().width(),
//        	"height": height
//        });
	};
//	 Make sure resizing is correctly
	callback();
//	#rawi comment not call resizeFormHeader multiple time
//	setTimeout(callback, 10);
//	setTimeout(callback, 30);
//	setTimeout(callback, 100);
};

var resizeSmartDataEntry = function() {
    if (typeof canvassettings === "undefined") {
        return;
    }    
    var sdwidth = $('.SmartDataEntry.left-side').width();
    $('.SmartDataEntry-inner .panel').css('width', sdwidth);    
    var height = getWindowHeight() - topOffsetDef;    
    var remHeight = 120;
    canvassettings['w'] = sdwidth;
    canvassettings['h'] = height - remHeight;
    //$(".SmartDataEntry-inner #canvas").css('width', sdwidth);
    $(".SmartDataEntry-inner #canvas").css('height', height - remHeight);
    if (tempObj != undefined) {
        doFieldMappingCanvas(tempObj);
    }else{
        doFieldMappingCanvasProxy(tempObj);
    }
};

var tempObj;
// Proxy Function    
var doFieldMappingCanvasProxy = function(obj) {
    if (typeof doFieldMappingCanvas != 'function') return;
    doFieldMappingCanvasProxy = doFieldMappingCanvas;
    doFieldMappingCanvas = function(obj) {
        tempObj = obj;
        doFieldMappingCanvasProxy(obj);  
    };        
};
    
var resizeFormTemplate = function(buttonId) {
//	if(buttonId != undefined){ // Displaying of Doc list screen
//		var headerObject = $('.FormHeaderManager #popover_'+$(buttonId).attr('id'));
//		var height = ((window.innerHeight > 0) ? window.innerHeight : screen.height) - 1;
//			height = height - (headerObject.height() + headerObject.outerHeight() );
//		if($('.popover-dontfloat .popover').hasClass('in')){
//			var size = (FormHeaderManager.outerHeight() + $('.popover-dontfloat .popover.in').height());
//			$('.FormHandlerManager').css('margin-top', size);
//	    }else{
//	    	$('.FormHandlerManager').css('margin-top', $('.FormHeaderManager #popover_'+$(buttonId).attr('id')).height() );
//	    } 
//	}else{
//		var headerObject = $('.FormHeaderManager');
//		var height = ((window.innerHeight > 0) ? window.innerHeight : screen.height) - 1;
//			height = height - (headerObject.height() + headerObject.outerHeight() );
//		if($('.popover-dontfloat .popover').hasClass('in')){
//			var size = (FormHeaderManager.outerHeight() + $('.popover-dontfloat .popover.in').height());
//	        $('.FormHandlerManager').css('margin-top', size);
//	    }else{
//	    	$('.FormHandlerManager').css('margin-top', $('.FormHeaderManager').height() );
//	    }    
//	}	 
	var headerObject = $('.FormHeaderManager');
	var height = ((window.innerHeight > 0) ? window.innerHeight : screen.height) - 1;
		height = height - (headerObject.height() + headerObject.outerHeight() );
	if($('.popover-dontfloat .popover').hasClass('in')){
//		var size = (FormHeaderManager.outerHeight() + $('.popover-dontfloat .popover.in').height());
//        $('.FormHandlerManager').css('margin-top', size);
    }else{
//    	$('.FormHandlerManager').css('margin-top', $('.FormHeaderManager').height() );
    }  
};

var modalreposition = function(d,c){
	if(c == 'auto'){
		if(!isEmpty($('.SmartDataEntry'))){
			c = 'right-side';
		}else{
			c = 'center';
		}
	}
    // right side
    if (typeof d === 'undefined') {
        d = '.modal';
        if (typeof c !== 'undefined') {
            d += '.' + c;
        }
    }else{
        $(d).addClass(c);
    }
    var offset = undefined;
    if($(d).hasClass('right-side')) {
        offset = $('.MasterForm.right-side').offset();
        if (offset == undefined) {
        	offset = $('#page-wrapper').offset();
            offset.left += 7;
            offset.top += 2;
        } else {
        	offset.left -= 16;
        }
    } else if ($(d).hasClass('center')) {
        offset = $('#page-wrapper').offset();
        offset.left += 7;
        offset.top += 2;
    }
    if (!offset) return;
    $(d).css({
        left: offset.left,
        top: offset.top,
        bottom: 23
    });
    return true;
};
    
var pageResize = function() {
    topOffset = getTopOffset();
    width = (this.window.innerWidth > 0) ? this.window.innerWidth : this.screen.width;
    if(width < 768){
        $('div.navbar-collapse').addClass('collapse');
    }else{
        $('div.navbar-collapse').removeClass('collapse');
    }
    height = getWindowHeight();
    height = height - topOffset - 20;
    if (height < 1) height = 1;
    if (height > topOffset) {
        $(".page-frame").css("height", (height) + "px");
        try{
            var iframePage = document.getElementById('external-page');
            if(iframePage != undefined){
            	resizeIframe(iframePage);
        	}
        }catch(e){}
//        $(".SmartDataEntry").css("height", (height) + "px");
//        $(".FormHandlerManager").css("height", (height - $('.FormHeaderManager').height() - 1) + "px");
    }
    try{resizeFormHeader();}catch(e){}
    try{resizeFormTemplate();}catch(e){}
    try{resizeSmartDataEntry();}catch(e){}
    try{modalreposition();}catch(e){}   
    
    //Resize form
    var windowHeight = $(window).height();
    var headerFooterHeight = $('.navbar-fixed-top').height()+$('#FormHeaderManager').height()+$('#footer-info').height(); //Should be less overhead though
    var formHeight = windowHeight-headerFooterHeight;
    $('.nopadding-right').height(formHeight);
};

$(window).bind("load resize", function() {
    pageResize();
});
var onResizePage = function(){
	pageResize();
}; 


