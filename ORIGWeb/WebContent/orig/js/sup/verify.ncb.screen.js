/**
 * 
 */
function countChar2(word){
	var textBoxValue = word.value;
	if(textBoxValue.length < 2 && textBoxValue.length != 0){
		alertMassageFuncParam1(CHAR_MORE_2, setCaretPosition, word);
		return false;
	}
	return true;
}
function setCaretPosition(text){
	$('#'+$(text).attr('id')).focus();
	$('#'+$(text).attr('id')).setCursorToTextEnd();
}
$(document).ready(function (){
	var height = $('.PanelSecond').outerHeight();
	if(height < $(window).height()-60){
		$('.PanelSecond').css({
			'height': $(window).height()-60
		});
	}
	$('.nav-inbox').slimScroll({
		height: $(window).height()+'px'
	});	
//	$("Tr.ResultOdd").hover(
//    	 function(){$(this).addClass("ResultOdd-haver-non-pointer");},
//    	 function(){$(this).removeClass("ResultOdd-haver-non-pointer");}
//    );
//	$("Tr.ResultEven").hover(
//    	 function(){$(this).addClass("ResultEven-haver-non-pointer");},
//    	 function(){$(this).removeClass("ResultEven-haver-non-pointer");}
//	);
	
	$("tr.ResultOdd").hover(
	    function(){$(this).addClass("ResultOdd-haver");},
	    function(){$(this).removeClass("ResultOdd-haver");}
	);
	$("tr.ResultEven").hover(
	     function(){$(this).addClass("ResultEven-haver");},
	     function(){$(this).removeClass("ResultEven-haver");}
	);
	$("td.load-view-mode").click(function(e){
		e.preventDefault();
		try{
			var object = $(this).attr('class').split(/\s+/);
			var appRecID = object[1];
			if(appRecID != undefined && appRecID != ''){
				blockScreen();
				$("#formID").val('KEC_FORM');
				$("#currentTab").val('MAIN_TAB');
				$("#handleForm").val('N');
				$("#action").val('LoadViewModeAppForm');
				$("#appRecordID").val(appRecID);
				$("#appFormName").submit();
			}
		}catch(e){
			unblockScreen();
			var msg = ERROR_JS+e.message;
			alertMassageERROR(msg);
		}
	});
	
	$('img.viewImg').click(function(e){
		e.preventDefault();
		var obj = $(this).attr('class').split(/\s+/);
		navinbox(190);
		parent.leftFrame.closeMenuFrame();
		openNcbImage(obj[1]);
	});
	
	navinbox(0);
	
	TotalJobCBSupCQ();
	
	//===========Enter Submit==================
	 var form =  document.appFormName;
	 if($(form).length > 0)
	 {
	     $(form).keypress(function (e){
	         code = e.keyCode ? e.keyCode : e.which;
	           if(code.toString() == 13) 
	           {
	        	   searchVerifyNCB();
	           }
	     });
	 }
	//=============================
//	 #septem focus screen
	 $('#button-search').focus();
});
function navinbox(add){
	if($('.scroll-div').attr('class') != undefined){
		$('.nav-inbox').css({
			'width': $(window).width()+add
		});
	}
}
function verifyAction(appRecID){
	var url = CONTEXT_PATH_ORIG+"/FrontController?action=VerifyNCBPopupWebAction&cb-comment="+encodeURI($('#comment-'+appRecID).html());
	$dialog = $dialogEmpty;
	var width = 670;
	var higth = 240;
	var title = 'Action';
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
		    position: { 
		        my: 'center',
		        at: 'center',
		        of: $(document.body)
    		},
		    buttons : {
			        "Ok":function(){
			        	saveAction(appRecID);
			        },
			        "Cancel" : function() {
			        	$dialog.dialog("close");
			        	closeDialog();
			        }
		    	},
		    close: function(){
		    	closeDialog();
		    }
	});
   $dialog.dialog("open");
}
function saveAction(appRecID){
	try{
		blockScreen();
		var action = $('#cb-action').val();
		var comment = $('#cb-comment').val();
		$('#param-action').val(action);
		$('#param-comment').val(comment);
		$('#param-apprecid').val(appRecID);
		$('#action').val('SaveVerifyCbWebAction');
		$('#handleForm').val('N');
		$('#appFormName').submit();
	}catch(e){
		unblockScreen();
		var msg = ERROR_JS+e.message;
		alertMassageERROR(msg);
	}
}
function openNcbImage(appRedId){
	var url = CONTEXT_PATH_ORIG+"/FrontController?action=NCB_IMAGES&appRecId="+appRedId;
	$dialog = $dialogEmpty;
	var width = 1200;
	var higth = $(window).height()-71;
	var title = 'NCB';
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
		    position: { 
		        my: 'center',
		        at: 'center',
		        of: $(document.body)
    		},
		    buttons : {
			        "Close" : function() {
			        	$dialog.dialog("close");
			        	closeDialog();
			        }
		    	},
		    close: function(){
		    	closeDialog();
		    }
	});
   $dialog.dialog("open");
}
function searchVerifyNCB(){
	try{
		if(validate()){
			blockScreen();
			$('#action').val('SearchVerifyNCBWebAction');
			$('#handleForm').val('N');
			$('#searchType').val('Y');
			$('#appFormName').submit();
		}
	}catch(e){
		unblockScreen();
		var msg = ERROR_JS+e.message;
		alertMassageERROR(msg);
	}
}
function validate(){
//	#septem validate countChar2
	if(!countChar2(appFormName.firstname)){
		return false;
	}
	if(!countChar2(appFormName.lastname)){
		return false;
	}
//	end #septem validate countChar2
	return true;
}
function clickPageList(atPage){
	try{
		blockScreen();
		var form = window.document.appFormName;
		form.atPage.value = atPage;
		form.action.value = "ValueListWebAction";
		form.handleForm.value = "N";
		form.submit();
	}catch(e){
		unblockScreen();
		var msg = ERROR_JS+e.message;
		alertMassageERROR(msg);
	}
}
function clickItemPerPageList(atItem){
	try{
		blockScreen();
		var form = window.document.appFormName;
		form.itemsPerPage.value = atItem;
		form.action.value = "ValueListWebAction";
		form.handleForm.value = "N";
		form.submit();  
	}catch(e){
		unblockScreen();
		var msg = ERROR_JS+e.message;
		alertMassageERROR(msg);
	}
}
function TotalJobCBSupCQ(){
	var dataString = "className=TotalJobCentralQueue&packAge=3&returnType=0";
	$.ajax( {
		type :"POST",
		url :"AjaxDisplayServlet",
		data :dataString,
		async :true,
		success : function(data,status,xhr){
			$.map(data, function(item){
				$('#cb-total-job').html(item.value);				
			});
		},
		error : function(data){
		}
	});
}