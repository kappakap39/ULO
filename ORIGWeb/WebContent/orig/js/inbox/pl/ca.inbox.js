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
		function(){
 		 	if($(this).hasClass('text-red')){
 		 		$(this).addClass("ResultOdd-haver");
 		 		$(this).addClass("text-red-haver");
		 	}else{
		 		$(this).addClass("ResultOdd-haver");			 		 
		 	}
    	},
    	function(){
		 	$(this).removeClass("ResultOdd-haver");
		 	$(this).removeClass("text-red-haver");
    	}
    );
	$("Tr.ResultEven").hover(
		function(){
			if($(this).hasClass('text-red')){
 		 		$(this).addClass("ResultEven-haver");
 		 		$(this).addClass("text-red-haver");
		 	}else{
		 		$(this).addClass("ResultEven-haver");	
		 	}
	    },
	    function(){
	    	$(this).removeClass("ResultEven-haver");
	    	$(this).removeClass("text-red-haver");
	    }
	);
	$("Tr.ResultBlue").hover(
			function(){
	 		 	if($(this).hasClass('text-red')){
	 		 		$(this).addClass("ResultBlue-haver");
	 		 		$(this).addClass("text-red-haver");
			 	}else{
			 		$(this).addClass("ResultBlue-haver");			 		 
			 	}
	    	},
	    	function(){
			 	$(this).removeClass("ResultBlue-haver");
			 	$(this).removeClass("text-red-haver");
	    	}
	    );
	$("Tr.Result-Obj").click(function(e){	
		e.preventDefault();
		try{
			var obj = $(this).attr('id').split(/\s+/);
			blockScreen();
			var form=document.appFormName;
			form.formID.value="KEC_FORM";
			form.currentTab.value="MAIN_TAB";
			form.action.value="LoadPLAppForm";
			form.handleForm.value = "N";
			form.appRecordID.value = obj[0];
			form.roleElement.value="CA";
			form.submit();
		}catch(e){
			unblockScreen();
			var msg = ERROR_JS+e.message;
			alertMassageERROR(msg);
		}
	});
	LoadButtonInbox();
});
function LoadButtonInbox(){
	ajaxHtmlManualAsync('GetButtonWfInbox',packageDefault,'',DisplayButton);
}
function DisplayButton(data){
	if(data == null || data == '')
		return;
	$('#button-job-status').html(data);
	DestoryButtonNormalEngine('#button-job-wf');
	DestoryButtonRedEngine('#button-job-wf');
	var buttonVal = $("#button-job-wf").val();
	if(buttonVal == JOB_STATUS_ON){
		ButtonNormalEngine("#button-job-wf");
	}else{
		ButtonRedEngine("#button-job-wf");
	}
}
function ChangeButtonStatus(){
	var buttonVal = $("#button-job-wf").val();
	var statusButton = 'N';
	if(buttonVal == JOB_STATUS_OFF){
		statusButton = 'Y';
	}
	var dataString ="statusButton="+statusButton;
	ajaxHtmlManual('ButtonOnJobAction',packageDefault,dataString,DisplayButton);
}

function clickPageList(atPage){
	$("#atPage").val(atPage);
	$("#action").val('CAPLInbox');
	$("#handleForm").val('N');
	$("#appFormName").submit();
}
function clickItemPerPageList(atItem){
	var form = window.document.appFormName;
	form.itemsPerPage.value = atItem;
	form.atPage.value = "";
	form.action.value = "CAPLInbox";
	form.handleForm.value = "N";
	form.submit();  
}
