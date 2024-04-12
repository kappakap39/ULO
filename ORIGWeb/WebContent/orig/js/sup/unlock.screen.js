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
	$('td.nev-unlock').click(function(e){
		e.preventDefault();
		try{
			var appRecID = $(this).attr('id');
			if(appRecID != undefined && appRecID != ''){
				blockScreen();
				$("#formID").val('KEC_FORM');
				$("#currentTab").val('MAIN_TAB');
				$("#handleForm").val('N');
				$("#action").val('LoadUnLockAppForm');
				$("#appRecordID").val(appRecID);
				$("#appFormName").submit();
			}
		}catch(e){
			unblockScreen();
			var msg = ERROR_JS+e.message;
			alertMassageERROR(msg);
		}
	});
	$("Tr.ResultOdd").hover(
    	 function(){
    		 $('td',this).each(function(){
    			 if($(this).hasClass('nev-unlock')){
    				 $(this).addClass('unlock-haver');
    				 $(this).css({
    					'color':'#FFFFFF' 
    				 });
    			 }
    		 });
    	 },
    	 function(){
    		 $('td',this).each(function(){
    			 $(this).removeClass("unlock-haver");
    			 $(this).css({
  					'color':'#000000' 
  				 });
    		 });
    	}
    );
	$("Tr.ResultEven").hover(
    	 function(){
    		 $('td',this).each(function(){
    			 if($(this).hasClass('nev-unlock')){
    				 $(this).addClass('unlock-haver');
    				 $(this).css({
     					'color':'#FFFFFF' 
     				 });
    			 }    			 
    		 });
    	 },
    	 function(){
    		 $('td',this).each(function(){
    			 $(this).removeClass("unlock-haver");
				 $(this).css({
 					'color':'#000000' 
 				 });
    		 });
    	}
	);
});
function unlockAction(UnblockAppRecId,BlockAppRecIdArr,AppNoUnblock,AppNo){
	var BlockAppNo = AppNo.split(",");
	var BlockAppRecId = BlockAppRecIdArr.split(",");
	for(var i=0; i<BlockAppRecId.length; i++){
		if(UnblockAppRecId == BlockAppRecId[i]){
			BlockAppRecId.splice(i,1);
		}
		if(AppNoUnblock == BlockAppNo[i]){
			BlockAppNo.splice(i,1);
		}
	}
	var total = BlockAppNo.length;
	var message = CONFIRM_UNLOCK_PRE+' '+AppNoUnblock+'<br>'+CONFIRM_UNLOCK_SUF+' '+BlockAppNo;
	alertMassageUnLock(message,confirmUnlock,null,UnblockAppRecId,BlockAppRecId,total);
}
function confirmUnlock(UnblockAppRecId, BlockAppRecId){
	try{
		blockScreen();
		appFormName.action.value = "UnlockAction";
		appFormName.UnblockAppRecId.value = UnblockAppRecId;
		appFormName.BlockAppRecId.value = BlockAppRecId;
		appFormName.handleForm.value = "N";
		appFormName.submit();
	}catch(e){
		unblockScreen();
		var msg = ERROR_JS+e.message;
		alertMassageERROR(msg);
	}
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
function alertMassageUnLock(massage,yesFunc,noFunc,param1,param2,total){
	$('.boxy-wrapper').remove();
	$('.boxy-modal-blackout').remove();
	 Boxy.ask(massage, ["Yes", "No"], function(val){
		      if("Yes" == val){
		    	  if(yesFunc != null) new yesFunc(param1,param2);
		      }
		      if("No" == val){
		    	  if(noFunc != null) new noFunc();
		      }
		  	$('.boxy-wrapper').remove();
			$('.boxy-modal-blackout').remove();
	    },{title:'Message'});
	if(total > 2){
		$('.boxy-wrapper .question').css({
			'width':'450px',
			'height':'55px'
		});
	}
}