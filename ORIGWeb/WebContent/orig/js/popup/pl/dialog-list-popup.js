/**
 * 
 */
$(document).ready(function(){
	$("Tr.ResultOdd").hover(
		function(){$(this).addClass("ResultOdd-haver");},
	    function(){$(this).removeClass("ResultOdd-haver");}
	);
	$("Tr.ResultEven").hover(
	    function(){$(this).addClass("ResultEven-haver");},
	    function(){$(this).removeClass("ResultEven-haver");}
	);
	$("Tr.Result-Obj1").click(function(e){
		e.preventDefault();
		var obj = $(this).attr('id').split('|');
		$('#userName').val($.trim(obj[0]));
		$('#reassignTo').val($.trim(obj[1]));
		closeDialog();
	});
	$('#button-search-code').click(function(e){
		$('#atPage').val('');
		$('#popup-action').val('SEARCH_CODE');
		OpenPopupSearchUserName('LoadUserNameWebAction');
	});		
});

function clickPageList(atPage){
	$('#popup-action').val('SEARCH_CODE');
	$('#atPage').val(atPage);
	OpenPopupSearchUserName('ValueListDialog');
}
function OpenPopupSearchUserName(action){
	var	url  = CONTEXT_PATH_ORIG+'/FrontController?action='+action+'&'+$("#div-dialoglist-popup *").serialize();
		url += '&'+$("#div-screen-reassign *").serialize()+'&search_code=SEARCH_CODE';
	$('#popup-action').val('');
	var frameModule = (document.getElementById('frame-module')== null)? document.body :'.frame-module';
	$dialog = $dialogEmpty;
	var width = 800;
	var higth = $(window).height()-50;
	var title = FULL_NAME;
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
		        "Close":function(){
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
		    		$('#userName').focus();
		    		$('#userName').setCursorToTextEnd();
		    	}
		    }
	});
   $dialog.dialog("open");
}