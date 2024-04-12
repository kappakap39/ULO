/**
 * 
 */
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
	$("Tr.ResultOdd").hover(
    	 function(){$(this).addClass("ResultOdd-haver");},
    	 function(){$(this).removeClass("ResultOdd-haver");}
    );
	$("Tr.ResultEven").hover(
	     function(){$(this).addClass("ResultEven-haver");},
	     function(){$(this).removeClass("ResultEven-haver");}
	 );
	$("Tr.Result-Obj").click(function(e){	
		e.preventDefault();
		var obj = $(this).attr('id').split(/\s+/);
		var form=document.appFormName;
		form.formID.value="KEC_FORM";
		form.currentTab.value="MAIN_TAB";
		form.action.value="LoadSearchBundleCCAppForm";
		form.handleForm.value = "N";
		form.appRecordID.value = obj[0];
		form.roleElement.value="CA";
		alertMassageYesNoFunc(CONFIRM_CLAIM_APP,loadApplication,null);
	});
	
	
	//===========Enter Submit==================
	 var form =  document.appFormName;
	 if($(form).length > 0){
	     $(form).keypress(function (e){
	         code = e.keyCode ? e.keyCode : e.which;
	           if(code.toString() == 13) 
	           {
	        	   searchApp();
	           }
	     });
	 }
	//=============================
//	 #septem focus screen
	 $('#button-search').focus();	
	
});

function loadApplication(){
	try{
		blockScreen();
	    var form = document.appFormName;
		form.submit();	
	}catch(e){
		unblockScreen();
		var msg = ERROR_JS+e.message;
		alertMassageERROR(msg);
	}
}

function clickPageList(atPage){
	$("#atPage").val(atPage);
	$("#action").val('ValueListWebAction');
	$("#handleForm").val('N');
	$("#appFormName").submit();
}
function clickItemPerPageList(atItem){
	var form = window.document.appFormName;
	form.itemsPerPage.value = atItem;
	form.action.value = "ValueListWebAction";
	form.handleForm.value = "N";
	form.submit();  
}
