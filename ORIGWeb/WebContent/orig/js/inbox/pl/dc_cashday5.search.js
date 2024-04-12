/**
 * Sankom
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
		try{
			var obj = $(this).attr('id').split(/\s+/);
			blockScreen();
			var form=document.appFormName;
			form.formID.value="KEC_FORM";
			form.currentTab.value="MAIN_TAB";
			form.action.value="LoadCashDay5Application";
			form.handleForm.value = "N";
			form.appRecordID.value = obj[0];
			form.roleElement.value="DC";
			form.submit();
		}catch(e){
			unblockScreen();
			var msg = ERROR_JS+e.message;
			alertMassageERROR(msg);
		}
	});
	
	 var form =  document.appFormName;
	 if($(form).length > 0)
	 {
	     $(form).keypress(function (e){
	         code = e.keyCode ? e.keyCode : e.which;
	           if(code.toString() == 13) 
	           {
	        	   searchCashDay5();
	           }
	     });
	 }
	 
//	 #septem focus screen
	 $('#button-search').focus();
	 
});
function searchCashDay5(){
	if(cashDay5validate()){
		try{
			blockScreen();
			appFormName.action.value="DCSearchCashDay5";
			appFormName.clickSearch.value="Y";
			appFormName.handleForm.value = "N";
			appFormName.submit();	
		}catch(e){
			unblockScreen();
			var msg = ERROR_JS+e.message;
			alertMassageERROR(msg);
		}
	}
}

function clickPageList(atPage){
	$("#atPage").val(atPage);
	$("#action").val('ValueListWebAction');
	$("#handleForm").val('N');
	$("#appFormName").submit();
}
function clickItemPerPageList(atItem){ 
	$("#itemsPerPage").val(atItem);
	$("#action").val('ValueListWebAction');
	$("#handleForm").val('N');
	$("#appFormName").submit();	
}