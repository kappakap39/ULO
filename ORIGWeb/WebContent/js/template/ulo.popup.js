/**
 * Rawi Songchaisin
 */
var $dialog,$dialogLayer2,$dialogEmpty;

function openPopupAction(action,width,higth){	
    var centerWidth = (window.screen.width - width)/2;
    var centerHeight = ((window.screen.height - higth)/2);
	var url = CONTEXT_PATH_ORIG+"/FrontController?action="+action;
	var childwindow = window.open(url,'','width='+width+',height='+higth+',top='+centerHeight+',left='+centerWidth
							+',scrollbars=1,location=no,directories=no,resizable=no');
	window.onfocus = function(){if (childwindow.closed == false){childwindow.focus();};};	
	childwindow.onunload = function(){alert('closing');};
}
function openPopupDialogCloseButton(action,width,higth,title) {	
	var url = CONTEXT_PATH_ORIG+"/FrontController?action="+action;
	$dialog = $dialogEmpty;	
	$(".plan-dialog").remove();
	$dialog = $('<div class="plan-dialog" id="plan-dialog"/>').load(url);
	var frameModule = (document.getElementById('frame-module')== null)? document.body :'.frame-module';
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
				        of: $(frameModule)
		    		},
		    buttons :{
			        "Close" : function() {						        	
			        	$dialog.dialog("close");	
			        	closeDialog();
			        }
		    	},
		    close: function(){
		    	closeDialog();
		    }
	    });
	$dialog.dialog('open');
}
function closeDialog(){
	try{
		$dialog = $dialogEmpty;
		$('.plan-dialog').remove();
	}catch(e){
	}
}
function fullclosedialog(){
//	fullclosedialog2();
//	var root = $('#plan-dialog').parent();
//	root.children().remove();
}
function closeDialogLayer2(){
//	$dialogLayer2 = $dialogEmpty;
//	$('.plan-dialogLayer2').remove();
//	$('div.AddressLayer2').remove();
}
function fullclosedialog2(){
//	var root = $('#plan-dialog-2').parent();
//	root.children().remove();
}
function openPopupActionAddress(action,addressType,personalType,mode){	
	var width = 1350;
	var higth = 450;
    var centerWidth = (window.screen.width - width)/2;
    var centerHeight = ((window.screen.height - higth)/2);
	var url = CONTEXT_PATH_ORIG+"/FrontController?action="+action+"&addressType="+addressType+"&personalType="+personalType+"&mode="+mode;
	var childwindow = window.open(url,'','width='+width+',height='+higth+',top='+centerHeight+',left='+centerWidth
							+',scrollbars=no,location=no,directories=no,resizable=no');
		window.onfocus = function(){if (childwindow.closed == false){childwindow.focus();};};
		childwindow.onunload = function(){alert('closing');};
}
function openPopupActionSaving(action,mode){	
	var width = 650;
	var higth = 450;
    var centerWidth = (window.screen.width - width)/2;
    var centerHeight = ((window.screen.height - higth)/2);
	var url = CONTEXT_PATH_ORIG+"/FrontController?action="+action+"&mode"+mode;
	var childwindow = window.open(url,'','width='+width+',height='+higth+',top='+centerHeight+',left='+centerWidth
							+',scrollbars=no,location=no,directories=no,resizable=no');
	window.onfocus = function(){if (childwindow.closed == false){childwindow.focus();};};	
	childwindow.onunload = function(){alert('closing');};
}
function DestoryTextBoxButtonSearchDescEngine(textboxid,buttonId,filedID){
	$(textboxid).unbind();
	$(buttonId).unbind();
}
function TextBoxButtonSearchDescEngine(textboxid,buttonId,webaction,filedID,hiddenField,title){
	DestoryTextBoxButtonSearchDescEngine(textboxid,buttonId,filedID);
	$(textboxid).blur(function(){
		var descValue = $(this).val();
		if(descValue != ''){
			OpenPopupSearchDescEngine(textboxid,webaction,title);
		}
	});
	$(buttonId).click(function(){
		OpenPopupSearchDescEngine(textboxid,webaction,title);
	});
}
function OpenPopupSearchDescEngine(textboxid,webaction,title){
	var searchValue = $(textboxid).val();
	var dataString = 'search-textbox-id='+$(textboxid).attr('name')+'&module-webaction='
						+webaction+'&type-search='+SEARCH_TYPE_DESC+'&search-value='+encodeURI(searchValue);
	var url = CONTEXT_PATH_ORIG+'/FrontController?action=PopupMasterWebAction&'+dataString;
	$dialog = $dialogEmpty;	
	var width = 1000;
	var higth = $(window).height()-50;
	$(".plan-dialog").remove();
	$dialog = $('<div class="plan-dialog" id="plan-dialog"/>').load(url);
	var frameModule = ($('#frame-module')== undefined)? document.body :'.frame-module';
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
				        of: $(frameModule)
		    		},
		    buttons :{
			        "Close" : function(){						        	
			        	$dialog.dialog("close");	
			        	closeDialog();
			        }
		    	},
		    close: function(){
		    	closeDialog();
		    }
	    });
	$dialog.dialog('open');
}

function getPopupTitle(action){
	if(action=='LoadTitleEngNew'){return TITLE_ENG;}
	else if(action=='LoadTitleThaiNew'){return TITLE_THAI;}
	else if(action=='LoadTitleWorkPlace'){return WORKPLACE_NAME;}
	else if(action=='LoadProjectCode'){return PROJECT_CODE;}
	else if(action=='LoadPLZipCode'){return ZIPCODE;}
	else if(action=='LoadNational'){return COUNTRY;}
	else if(action=='LoadSubDistrict'){return TAMBOL;}
	else if(action=='LoadDistrict'){return AMPHUR;}
	else if(action=='LoadProvince'){return PROVINCE;}
	else if(action=='LoadUserNameWebAction'){return FULL_NAME;}
	else if(action=='LoadProjectCodeAll'){return PROJECT_CODE;}
	else {return BRANCH_CODE;}
}



/**PopUpTextBoxDescFieldID #SepteMWi
 * obj hiddenID
 * obj[0] = fieldID
 * obj[1] = fieldIDName
 * obj[2] = textBoxName
 * obj[3] = hiddenName
 * obj[4] = action
 * obj[5] = title
 * */
function PopUpTextBoxDescFieldIDEngine(textboxID){
	$(textboxID).blur(function(){
		var textbox_code = $(this).attr('name');		
		$(this).val($.trim($(this).val()));		
		var textbox_value = $(this).val();		
		LoadDataDescFieldID(textbox_code,textbox_value);
	});
	$(textboxID+'_popup').click(function(e){
		var textboxid = $.trim($(this).attr('id').replace('_popup',''));		
		$('#'+textboxid).val($.trim($('#'+textboxid).val()));		
		var textbox_code = $('#'+textboxid).attr('name');
		var textbox_value = $('#'+textboxid).val();
		var obj = $('#'+textboxid+'_obj').val().split('|');		
		$('#'+obj[3]).val($.trim($('#'+obj[3]).val()));		
		OpenPopupTextBoxDescFieldID(textbox_code,textbox_value,obj);
	});
}
function LoadDataDescFieldID(textbox_code,textbox_value){
	var obj = $('#'+textbox_code+'_obj').val().split('|');
	
	$('#'+obj[3]).val($.trim($('#'+obj[3]).val()));
	
	if(textbox_value == ''){
		$('#'+obj[3]).val('');	
		return;	
	}
	var dataString = "className=GetDataDescFieldID&packAge=2&returnType=0&textbox_value="+encodeURI($.trim(textbox_value))
										+'&obj_value='+encodeURI($.trim($('#'+textbox_code+'_obj').val()));
	jQuery.ajax( {
		type :"POST",
		url :"AjaxDisplayServlet",
		data :dataString,
		async :true,	
		dataType: "json",
		success : function(data ,status ,xhr){
			if(data!= null && data.length >0){
				jsonDisplayElementById(data);	
				DisplayReferenceFieldID(textbox_code);
			}else{
				$('#'+obj[3]).val('');	
				OpenPopupTextBoxDescFieldID(textbox_code,textbox_value,obj);
			}
		},
		error : function(response){
		}
	});
}
function OpenPopupTextBoxDescFieldID(textbox_code,textbox_value,obj){
	
	var url = CONTEXT_PATH_ORIG+'/FrontController?action='+obj[4]+'&textbox_code='+encodeURI($.trim(textbox_code))+'&textbox_desc='+encodeURI($.trim(obj[3]))
						+'&textbox_value='+encodeURI($.trim(textbox_value))+'&hidden_value='+encodeURI($.trim($('#'+obj[3]).val()));

	var frameModule = (document.getElementById('frame-module')== null)? document.body :'.frame-module';
	$dialog = $dialogEmpty;
	var width = 1000;
	var higth = $(window).height()-50;
	var title = obj[5];
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
		    		$('#'+textbox_code).focus();
		    		$('#'+textbox_code).setCursorToTextEnd();
		    		$('#popup-action').val('');
		    	}
		    }
	});
   $dialog.dialog("open");
}

function DisplayReferenceFieldID(textbox_code){
	if(textbox_code == 'titleThai'){
		var obj = $('#'+textbox_code+'_obj').val().split('|');	
		var value = $('#'+obj[3]).val();
		if(value != null && value != ''){
			var dataString = 'textbox_code='+encodeURI($.trim(textbox_code))
									+'&choice_no='+encodeURI($.trim(value));
			ajaxDisplayElementJsonAsync('GetReferenceData','0',dataString);
		}
	}else if(textbox_code == 'titleEng'){
		var obj = $('#'+textbox_code+'_obj').val().split('|');
		var value = $('#'+obj[3]).val();
		if(value != null && value != ''){
			var dataString = 'textbox_code='+encodeURI($.trim(textbox_code))
									+'&choice_no='+encodeURI($.trim(value));
			ajaxDisplayElementJsonAsync('GetReferenceData','0',dataString);
		}
	}
}
