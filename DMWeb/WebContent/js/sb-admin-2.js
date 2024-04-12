//$(function() {
//    $('#side-menu').metisMenu();    
//    $('#page-wrapper, .sidebar').resize(onResizePage);
//    $('.FormHeaderManager').resize(resizeFormTemplate);
//});

//Loads the correct sidebar on window load,
//collapses the sidebar on window resize.
// Sets the min-height of #page-wrapper to window size
//var topOffsetDef = 60 + 23;
    
//    var resizeFormHeader = function() {
//        $(".FormHeaderManager").css("width", $(".FormHeaderManager").parent().width() );
//
//        // Popover
//        height = ((this.window.innerHeight > 0) ? this.window.innerHeight : this.screen.height) - 1;
//        var FormHeaderManager = $('.FormHeaderManager');
//        height = height - (FormHeaderManager.height() + FormHeaderManager.outerHeight() );
//        $(".popover-wrapper").css({
//        	"width": $(".FormHeaderManager").parent().width(),
//        	"height": height
//        });
//
//        //console.log( $(".FormHeaderManager").parent().width() );
//    };
//    var resizeSmartDataEntry = function() {
//        $('.SmartDataEntry-inner').css('width', $('.SmartDataEntry.left-side .col-md-12').width());
//        
//        var $window = $('.SmartDataEntry-inner');
//        var width = $window.width();
//        var height = $window.height();
//        var scale;
//
//        // early exit
//        /*if(width >= sdmaxWidth && height >= sdmaxHeight) {
//            $('#prevcanvas').css({'-webkit-transform': ''});
//            $('.carousel-bounding-box').css({ width: '', height: '' });
//            return;
//        }
//        */
//        //scale = Math.min(width/sdmaxWidth, height/sdmaxHeight);
//        //console.log(scale);
//        
//        //$('#prevcanvas').css({'-webkit-transform': 'scale(' + scale + ')'});
//        //$('.carousel-bounding-box').css({ width: sdmaxWidth * scale, height: sdmaxHeight * scale });
//    };
    
//    var resizeFormTemplate = function() {
//    	var FormHeaderManager = $('.FormHeaderManager');
//		var height = ((window.innerHeight > 0) ? window.innerHeight : screen.height) - 1;
//    	height = height - (FormHeaderManager.height() + FormHeaderManager.outerHeight() );
//    	if($('.popover-dontfloat .popover').hasClass('in')) {
//    		var size = (FormHeaderManager.outerHeight() + $('.popover-dontfloat .popover.in').height());
//            $('.FormHandlerManager').css('margin-top', size);
//        } else {
//        	$('.FormHandlerManager').css('margin-top', $('.FormHeaderManager').height() );
//        }
//        
//    };

//    var modalreposition = function(d,c) {

//    	if (c == 'auto') {
//    		if (!isEmpty($('.SmartDataEntry'))) {
//    			c = 'right-side';
//    		} else {
//    			c = 'center';
//    		}
//    	}
//        // right side
//        if (typeof d === 'undefined') {
//            d = '.modal';
//            if (typeof c !== 'undefined') {
//                d += '.' + c;
//            }
//        } else {
//            $(d).addClass(c);
//        }
//
//        var offset = undefined;
//        if($(d).hasClass('right-side')) {
//            offset = $('.MasterForm.right-side').offset();
//            if (offset == undefined) {
//            	offset = $('#page-wrapper').offset();
//                offset.left += 7;
//                offset.top += 2;
//            } else {
//            	offset.left -= 16;
//            }
//        } else if ($(d).hasClass('center')) {
//            offset = $('#page-wrapper').offset();
//            offset.left += 7;
//            offset.top += 2;
//        }
//
//        if (!offset) return;
//
//        $(d).css({
//            left: offset.left,
//            top: offset.top,
//            bottom: 23
//        });

//        return true;
//    };
//    
//    var pageResize = function() {
//        //alert('hello');
//        topOffset = topOffsetDef;
//        width = (this.window.innerWidth > 0) ? this.window.innerWidth : this.screen.width;
//        if (width < 768) {
//            $('div.navbar-collapse').addClass('collapse');
//            topOffset = 100; // 2-row-menu
//        } else {
//            $('div.navbar-collapse').removeClass('collapse');
//
//        }
//
//        height = ((this.window.innerHeight > 0) ? this.window.innerHeight : this.screen.height) - 1;
//        height = height - topOffset;
//        if (height < 1) height = 1;
//        if (height > topOffset) {
//            $("#page-wrapper").css("min-height", (height) + "px");
//            $(".SmartDataEntry-inner .panel-body").css("height", (height -39) + "px");
//        }
//        resizeFormHeader();
//        resizeFormTemplate();
//        resizeSmartDataEntry();
//        modalreposition();
//    };
//
//    $(window).bind("load resize", function() {
//        pageResize();
//    });
//    var onResizePage = function() {
//      //resizeFormHeader();
//      //resizeSmartDataEntry();
//      pageResize();
//    };
    

    /* !!
     * Please do not delete this below Resizing code.
     * but it is not finish yet.
     * Maybe use in future.
     */
    //$('.FormHeaderManager').resize(function () {
    //    height = ((this.window.innerHeight > 0) ? this.window.innerHeight : this.screen.height) - 1;
    //    height = height - topOffsetDef;
    //    
    //   $('.FormHandlerManager').css('min-height', $(this).outerHeight() ) 
    //});
    /*
     * End resize
     */
    
    