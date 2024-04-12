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
		var action = $('#searchAction').val();
		var title = $('#title-search-popup').val();
		OpenPopupSearchCode(action,title);
	});	
	$('.tr-master-popup').click(function(e){
		var code = $(this).attr('id');
		var desc = $('#code_'+code).val();
		displayField(code,desc);
	});
});

function displayField(code,desc){
	var textbox_code = $('#textbox_code').val();
	var textbox_desc = $('#textbox_desc').val();
	$('#'+textbox_code).val(code);
	$('#'+textbox_desc).val(desc);
	$('#popup-action').val('');
	LogicFieldCode(textbox_code);
	$dialog.dialog("close");	
	closeDialog();
}
function LogicFieldCode(textbox_code){
	if('seller_branch_code' == textbox_code || 'ref_branch_code' == textbox_code || 'service_seller_branch_code' == textbox_code){
		var ref_code01 = $('#ref_code01').val();
		var ref_desc01 = $('#ref_desc01').val();
		var textbox_desc = $('#textbox_desc').val();
		if(textbox_code == 'seller_branch_code'){
			getChannelDropdown();
		}
		TextboxBranchLogic(textbox_code,textbox_desc,ref_code01,ref_desc01,false);
	}
}
function OpenPopupSearchCode(webaction ,titleName){
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
	var title = $('#title-search-popup').val();
	OpenPopupSearchCode('ValueListPopupWebAction',title);
}