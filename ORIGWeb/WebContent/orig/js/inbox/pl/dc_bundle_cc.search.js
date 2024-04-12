/**
 * Pipe
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
		var obj = $(this).attr('id').split("|");
		DCBundleClaimJob(obj[0],obj[1],obj[2]);
	});
	//===========Enter Submit==================
	 var form =  document.appFormName;
	 if($(form).length > 0)
	 {
	     $(form).keypress(function (e){
	         code = e.keyCode ? e.keyCode : e.which;
	           if(code.toString() == 13) 
	           {
	        	   searchBundling();
	           }
	     });
	 }
	//=============================
	 
//	 #septem focus screen
	 $('#button-search').focus();		 
});

function searchBundling(){
	appFormName.action.value="DCSearchBundle";
	appFormName.clickSearch.value="Y";
	appFormName.handleForm.value = "N";
	appFormName.submit();
}

function clickPageList(atPage){
	var form = window.document.appFormName;
	form.atPage.value = atPage;
	form.action.value = "ValueListWebAction";
	form.handleForm.value = "N";
	form.submit();
}
function clickItemPerPageList(atItem){
	var form = window.document.appFormName;
	form.itemsPerPage.value = atItem;
	form.action.value = "ValueListWebAction";
	form.handleForm.value = "N";
	form.submit();  
}

function DCBundleClaimJob(appRecID,ccResult,ccAppScore){     
	var url = "/ORIGWeb/FrontController?action=DCSearchBundlePopup&appRecID="+appRecID;
		url = url+"&ccResult="+ccResult+"&ccAppScore="+ccAppScore;
	$dialog = $dialogEmpty;
	var width = 800;
	var higth = 250;
	var title = 'Credit Card';
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
			        "Claim Job":function(){
			        	var creditCardResult = $('#creditCardResult').val();
			            var creditCardAppScore = $('#creditCardAppScore').val();
		            	if(creditCardResult==""){
		            		alertMassage("\u0E01\u0E23\u0E38\u0E13\u0E32\u0E23\u0E30\u0E1A\u0E38\u0E02\u0E49\u0E2D\u0E21\u0E39\u0E25 Credit Card Result");
			            	return;
			            }
		            	if(creditCardResult=="APPROVED" || creditCardResult=="OVERIDE"){
		            		if(creditCardAppScore==""){
		            			alertMassage("\u0E01\u0E23\u0E38\u0E13\u0E32\u0E23\u0E30\u0E1A\u0E38\u0E02\u0E49\u0E2D\u0E21\u0E39\u0E25 Credit Card App Score");
		            			return ;
		            		}
		            	}
			        	claimBundle(appRecID,creditCardResult,creditCardAppScore);			          
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
function claimBundle(appRecID,creditCardResult,creditCardAppScore){
	try{
		var dataString = "ClassPackage=com.eaf.orig.ulo.pl.ajax&ClassName=DCBundleAction&appRecId="+appRecID; 
			dataString = dataString+"&creditCardResult="+creditCardResult+"&creditCardAppScore="+creditCardAppScore;
		blockScreen();
		$.post('AjaxServlet',dataString
			,function(data,status,xhr){				
				if(data == 'Claim'){
					LoadApplication(appRecID);
				}else{
					unblockScreen();
					$('#div-error').html(data);
				}
		   }).error(function(){
			   unblockScreen();
			   alertMassageERROR('Network or Connection Error, please try again');
		   });
	}catch(e){
		unblockScreen();
		var msg = ERROR_JS+e.message;
		alertMassageERROR(msg);
	}
}
function LoadApplication(appRecID){
    var form = document.appFormName;
	form.formID.value="KEC_FORM";
	form.currentTab.value="MAIN_TAB";
	form.action.value="LoadPLAppForm";
	form.handleForm.value = "N";
	form.appRecordID.value = appRecID;
	form.submit();	
}