
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
		OpenPopupSearchProjectCode('LoadProjectCode');
	});
	$('.tr-project-code-obj').click(function(e){
		var projectCode = $(this).attr('id');
		DisplayFieldProjectCode(projectCode);
	});
});
function DisplayFieldProjectCode(projectCode){
	var textbox_code = '#'+$('#textbox_code').val();
	$(textbox_code).val(projectCode);
	$('#popup-action').val('DISPLAY_VALUE');
	LoadProjectCodeInformation();
	SensitiveFieldEngine(textbox_code);
	$dialog.dialog("close");	
	closeDialog();
}
function OpenPopupSearchProjectCode(action){
	var url = CONTEXT_PATH_ORIG+"/FrontController?action="+action+"&product_feature="+encodeURI($.trim($('#product_feature').val()))
						+"&"+$("#div-valuelist-master-popup *").serialize()+"&exception_project="+encodeURI($.trim($('#exception_project').val()));
	var frameModule = (document.getElementById('frame-module')== null)? document.body :'.frame-module';
	if($dialog != null || $dialog != undefined){
		$dialog.dialog("close");
	}
	$dialog = $dialogEmpty;
	var width = 1000;
	var higth = $(window).height()-50;
	var title = PROJECT_CODE;
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
		        	closeDialog();
		        }
	    	},
		    position: { 
		        my: 'center',
		        at: 'center',
		        of: $(frameModule)
    		},
		    close: function(){
		    	closeDialog();
		    	if($('#popup-action').val() != "SEARCH_CODE"){
		    		$('#project_code').focus();
		    		$('#project_code').setCursorToTextEnd();
		    		$('#popup-action').val('');
		    	}
		    }
	});
   $dialog.dialog("open");
}
function clickPageList(atPage){
	$('#popup-action').val('SEARCH_CODE');
	$('#atPage').val(atPage);
	OpenPopupSearchProjectCode('ValueListPopupWebAction');
}