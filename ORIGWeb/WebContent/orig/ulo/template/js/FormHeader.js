// Create draggable for popover
(function() {
	
})();

function popover(buttonId) {

	// Create opening popover selector.
	var popel = $('#popover_' + $(buttonId).attr('id'));
	
	// remove ".in" class from all ".popover" except opening popover.
	$('.popover').not(popel).removeClass('in');
	// toggle ".in" on opening popover.
	popel.toggleClass('in');
	try {
		// Try to get Float properties in Constant.
		var isFloat = eval('HEADER_'+$(buttonId).attr('id').toUpperCase()+'_FLOAT');
	} catch (e) {
		// default value must false.
		isFloat = 'FALSE';
	}
	
	// Float by default.
	$('.popover-wrapper').removeClass('popover-dontfloat');
	if (isFloat.toUpperCase() == 'FALSE') {
		// Don't float will add class to Wrapper.
		$('.popover-wrapper').addClass('popover-dontfloat');
	}
	
	// Dynamic arrow
	var arrow = popel.find('.arrow');
	// find parent's offset and button that clicked by user.
	// And then we get center position of button.
	var calcArrowOffset = function(b) {
		var bparent = b.parent().parent();
		var bleftStart = b.offset().left - bparent.offset().left;
		var bcenterPoint = b.width() / 2;
		
		return bleftStart + bcenterPoint;
	};
	arrow.css('left', calcArrowOffset($(buttonId)));
	
	// And now add Dismissing to popover
	$('body').on('click', function(e) {
		// Close all popover when click outside of it.
		if ($(e.target).parents('.popover.in').length === 0 && $(e.target).attr('popover') != 'popover') {
			$('.popover').removeClass('in');
			$('.popover-wrapper').addClass('popover-dontfloat');
			$('body').off('click');
			resizeFormTemplate(buttonId);
		}
	});
	
	// Resize
	resizeFormTemplate(buttonId);
}

// Buttons action
function COMMENTActionJS(buttonId) {
	popover("#" + buttonId);
}

function HISTORYActionJS(buttonId) {
	popover("#" + buttonId);
}

function AUDIT_TRAILActionJS(buttonId) {
	popover("#" + buttonId);
}

function DOCUMENT_LISTActionJS(buttonId){
	try{
		//popover("#" + buttonId);
		var form = $('#FormHandlerManager>#HEADER_SECTION_DOCUMENT_HEADER_FORM');
		if(form.is(':visible')){
			form.hide('fast');
		}else{
			$('.nopadding-right').stop().animate({scrollTop:0}, 200, 'swing');
			setTimeout(function(){form.show('fast');},150);				
		}
	}catch(exception){
		errorException(exception);
	}
}

function COMPARE_SIGNATUREActionJS(){
	try{
		var className = 'com.eaf.orig.ulo.app.view.util.compare.CompareSignature';
		ajax(className,"", COMPARE_SIGNATUREAfterActionJS);
	}catch(exception){
		errorException(exception);
	}
}
function COMPARE_SIGNATUREAfterActionJS(data){
	try{
		var compareSignature = $.parseJSON(data);
		if(FLAG_YES == compareSignature.compareSignatureFlag){
			$("[name='SUBMIT_APPLICATION_BTN']").removeAttr("disabled");
			$("[name='SUBMIT_APPLICATION_BTN']").attr("title","Submit Application");
			var currDocTypeCodes  = compareSignature.currentDocTypeCodes;
			var url = compareSignature.url;
			if(url != undefined){
				var $data = "?";
					if(compareSignature.currentSetID != undefined){
						$data +="CurrentSetID="+compareSignature.currentSetID;
					}
					if(compareSignature.oldSetID != undefined){
						$data +="&OldSetID="+compareSignature.oldSetID;
					}
					if(compareSignature.oldDocTypeCode != undefined){
						$data +="&OldDocTypeCode="+compareSignature.oldDocTypeCode;
					}
					if(compareSignature.tokenID != undefined){
						$data +="&TokenID="+compareSignature.tokenID;
					}
					if(currDocTypeCodes != undefined){
						for(var i=0;i<currDocTypeCodes.length;i++){
							$data +="&CurrentDocTypeCode="+currDocTypeCodes[i];
						}
					}
					if(compareSignature.defaultAppformPage != undefined){
						$data +="&DefaultAppformPage="+compareSignature.defaultAppformPage;
					}					
				loadIframeDialog(url+$data,COMPARE_SIGNATUR_HEADER);
			}
		}else{
			alertBox('ERROR_COMPARE_SIGNATURE');
		}
	}catch(exception){
		errorException(exception);
	}
}