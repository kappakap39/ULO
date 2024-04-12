/**
 * #SeptemWi
 */
$(document).ready(function () {	
	$("Tr.ResultOdd").hover(
    	 function(){$(this).addClass("ResultOdd-haver");},
    	 function(){$(this).removeClass("ResultOdd-haver");}
    );    
	$("Tr.ResultEven").hover(
    	 function(){$(this).addClass("ResultEven-haver");},
    	 function(){$(this).removeClass("ResultEven-haver");}
	);
	$('#popup-action').val('');
	$('#button-search-code').click(function(e){
		$('#atPage').val('');
		$('#popup-action').val('SEARCH_CODE');
		var textbox_code = $('#textbox_code').val();
		var obj = $('#'+textbox_code+'_obj').val().split('|');
		OpenPopupSearchDescFieldID(obj[4],obj[5]);
	});	
	$('.tr-master-popup').click(function(e){
		var descvalue = $(this).attr('id');
		var codevalue = $('#choiceno_'+descvalue).val();
		DisplayFieldDesc(codevalue,descvalue);
	});
});

function DisplayFieldDesc(codevalue,descvalue){	
	var textbox_code = $('#textbox_code').val();
	var textbox_desc = $('#textbox_desc').val();
	$('#'+textbox_code).val(codevalue);
	$('#'+textbox_desc).val(descvalue);
	$('#popup-action').val('');
	LoadDataDescFieldID(textbox_code,codevalue);
	SensitiveAttrNameEngine(textbox_code);
	$dialog.dialog("close");	
	closeDialog();
}
function OpenPopupSearchDescFieldID(webaction ,titleName){
	var url = CONTEXT_PATH_ORIG+'/FrontController?action='+webaction+'&'+$("#div-valuelist-master-popup *").serialize();
	var frameModule = (document.getElementById('frame-module')== null)? document.body :'.frame-module';
	$dialog = $dialogEmpty;
	var width = 1000;
	var higth = $(window).height()-50;
	var title = titleName;
	$('#popup-action').val('');
	$(".plan-dialog").remove();
	$dialog = $('<div class="plan-dialog" id="plan-dialog"/>').load(url);
	$dialog.dialog({
		    resizable : false,
		    modal : true,
		    autoOpen: false,
		    draggable: true,
		    width: width,
		    height: higth,
		    title:title,
		    buttons :{
		        "Close" : function() {						        	
		        	$dialog.dialog("close");
		        }
	    	},
		    position: { 
		        my: 'center',
		        at: 'center',
		        of: $(frameModule)
    		},
		    close: function(){
		    	var textbox_code = $('#textbox_code').val();
		    	closeDialog();		    	
		    	if($('#popup-action').val() != "SEARCH_CODE"){
		    		$('#'+textbox_code).focus();
		    		$('#'+textbox_code).setCursorToTextEnd();
		    		$('#popup-action').val('');
		    	}
		    }
	});
   $dialog.dialog("open");
}
function clickPageList(atPage){
	$('#popup-action').val('SEARCH_CODE');
	$('#atPage').val(atPage);
	var textbox_code = $('#textbox_code').val();
	var obj = $('#'+textbox_code+'_obj').val().split('|');
	OpenPopupSearchDescFieldID('ValueListPopupWebAction',obj[5]);
}